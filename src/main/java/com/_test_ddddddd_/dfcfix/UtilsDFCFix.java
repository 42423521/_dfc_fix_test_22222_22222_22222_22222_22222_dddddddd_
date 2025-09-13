




package com._test_ddddddd_.dfcfix;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utils for applying tankCap at runtime from commands.
 * Put this file under src/main/java/com/_test_ddddddd_/dfcfix/
 */
public class UtilsDFCFix{//dnm_jw_son_of_bitch_ddddd_{//UtilsDFCFix {

    // 在命令里调用： UtilsDFCFix.applyTankCapRealtime((Object)te, world, pos, tankCap);     dnm_jw_son_of_bitch_ddddd_
    public static void applyTankCapRealtime(Object tileEntityCoreObj, World world, BlockPos pos, int tankCap) {
        try {
            // —— 尝试把 tankCap 写入 tileEntity 的 tileData（持久化）
            try {
                Method getTileData = tileEntityCoreObj.getClass().getMethod("getTileData");
                Object tileData = getTileData.invoke(tileEntityCoreObj);
                if (tileData != null) {
                    Method setInteger = tileData.getClass().getMethod("setInteger", String.class, int.class);
                    setInteger.invoke(tileData, "tankCap", tankCap);
                }
                // 尽量 markDirty
                try {
                    Method markDirty = tileEntityCoreObj.getClass().getMethod("markDirty");
                    markDirty.invoke(tileEntityCoreObj);
                } catch (NoSuchMethodException ignore) {}
                // 通知区块更新，触发客户端刷新（best-effort）
                if (world != null && pos != null) {
                    IBlockState s = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, s, s, 3);
                }
            } catch (NoSuchMethodException nsme) {
                // 某些版本可能没有 getTileData()，忽略持久化步骤
                nsme.printStackTrace();
            }

            // —— 反射取出 tanks 数组（尝试几个常见字段名）
            Object[] tanksArr = null;
            String[] tankFieldNames = new String[] {"tanks", "fluidTanks", "tankArray"};
            for (String name : tankFieldNames) {
                try {
                    Field f = tileEntityCoreObj.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    Object arr = f.get(tileEntityCoreObj);
                    if (arr != null) {
                        int len = Array.getLength(arr);
                        tanksArr = new Object[len];
                        for (int i = 0; i < len; i++) tanksArr[i] = Array.get(arr, i);
                    }
                    break;
                } catch (NoSuchFieldException ignore) {}
            }

            // 备选：通过 getter 取
            if (tanksArr == null) {
                try {
                    Method getTanks = tileEntityCoreObj.getClass().getMethod("getTanks");
                    Object arr = getTanks.invoke(tileEntityCoreObj);
                    if (arr != null) {
                        int len = Array.getLength(arr);
                        tanksArr = new Object[len];
                        for (int i = 0; i < len; i++) tanksArr[i] = Array.get(arr, i);
                    }
                } catch (Throwable ignore) {}
            }

            if (tanksArr == null) {
                System.out.println("[DFCFIX CMD] no tanks array found on TileEntityCore, nothing applied.");
                return;
            }

            // 候选名（优先官方）
            String[] capSetters = new String[] {"setCapacity", "setMaxFill", "setMax", "setMaxAmount", "setCapacityMB"};
            String[] capFields = new String[] {"capacity", "maxFill", "max_fill", "maxFillAmount", "max", "maxAmount"};
            String[] getFillNames = new String[] {"getFill", "getFluidAmount", "getAmount"};
            String[] setFillNames = new String[] {"setFill", "setFluidAmount", "setAmount"};
            String[] amtFields = new String[] {"amount", "fill", "fluidAmount"};

            for (Object tank : tanksArr) {
                if (tank == null) continue;
                boolean applied = false;

                // 1) 官方 Forge FluidTank 路径（运行时检测类）
                try {
                    Class<?> forgeTankCls = Class.forName("net.minecraftforge.fluids.FluidTank");
                    if (forgeTankCls.isAssignableFrom(tank.getClass())) {
                        // 修改 protected int capacity（反射）
                        try {
                            Field capField = forgeTankCls.getDeclaredField("capacity");
                            capField.setAccessible(true);
                            capField.setInt(tank, tankCap);
                        } catch (NoSuchFieldException ignore) {}

                        // 截断 FluidStack.amount（若存在）
                        try {
                            Method getFluid = forgeTankCls.getMethod("getFluid");
                            Object fs = getFluid.invoke(tank); // net.minecraftforge.fluids.FluidStack
                            if (fs != null) {
                                Class<?> fsCls = Class.forName("net.minecraftforge.fluids.FluidStack");
                                try {
                                    Field amtF = fsCls.getDeclaredField("amount");
                                    amtF.setAccessible(true);
                                    int now = amtF.getInt(fs);
                                    if (now > tankCap) {
                                        // 构造新 FluidStack 并 setFluid
                                        Method getFluidMeth = fsCls.getMethod("getFluid");
                                        Object fluidObj = getFluidMeth.invoke(fs);
                                        Object newFs = fsCls.getConstructor(fluidObj.getClass(), int.class).newInstance(fluidObj, tankCap);
                                        Method setFluid = forgeTankCls.getMethod("setFluid", fsCls);
                                        setFluid.invoke(tank, newFs);
                                    }
                                } catch (NoSuchFieldException nf) {
                                    // 忽略
                                }
                            }
                        } catch (NoSuchMethodException ignore) {}
                        applied = true;
                    }
                } catch (ClassNotFoundException cnf) {
                    // dev 环境里通常有 Forge 类，不用担心；若没有，继续回退
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                if (applied) continue;

                // 2) 普通反射路径：尝试 setter
                Class<?> cls = tank.getClass();
                try {
                    for (String mname : capSetters) {
                        try {
                            Method m = cls.getMethod(mname, int.class);
                            m.setAccessible(true);
                            m.invoke(tank, tankCap);
                            applied = true;
                            break;
                        } catch (NoSuchMethodException ignore) {}
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                // 3) 若没有 setter，尝试字段
                if (!applied) {
                    try {
                        for (String fname : capFields) {
                            try {
                                Field ff = cls.getDeclaredField(fname);
                                ff.setAccessible(true);
                                ff.setInt(tank, tankCap);
                                applied = true;
                                break;
                            } catch (NoSuchFieldException ignore) {}
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

                // 4) 截断当前量
                try {
                    Integer now = null;
                    for (String g : getFillNames) {
                        try {
                            Method gm = cls.getMethod(g);
                            gm.setAccessible(true);
                            Object v = gm.invoke(tank);
                            if (v instanceof Integer) { now = (Integer) v; break; }
                        } catch (NoSuchMethodException ignore) {}
                    }
                    if (now != null && now > tankCap) {
                        for (String s : setFillNames) {
                            try {
                                Method sm = cls.getMethod(s, int.class);
                                sm.setAccessible(true);
                                sm.invoke(tank, tankCap);
                                now = tankCap;
                                break;
                            } catch (NoSuchMethodException ignore) {}
                        }
                        if (now != tankCap) {
                            for (String af : amtFields) {
                                try {
                                    Field f = cls.getDeclaredField(af);
                                    f.setAccessible(true);
                                    f.setInt(tank, tankCap);
                                    break;
                                } catch (NoSuchFieldException ignore) {}
                            }
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                if (applied) {
                    System.out.println("[DFCFIX CMD] applied tankCap to tank class " + tank.getClass().getName() + " cap=" + tankCap);
                } else {
                    System.out.println("[DFCFIX CMD] failed to apply direct cap to tank class " + tank.getClass().getName() + " (will persist via NBT)");
                }
            }

            System.out.println("[DFCFIX CMD] finished applyTankCapRealtime = " + tankCap + " at " + (pos == null ? "?" : pos.toString()));

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
