package com._test_ddddddd_.dfcfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



import com.hbm.tileentity.machine.TileEntityCore;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;




// 必要 imports（你 mixin 文件顶部可能已经有一些）
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//


import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;








//                                                                                                                                                                                                          狗日的           尼玛还是                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   mixin                                                                                                                                                                                                                      的,(),                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ,()<








//////////////////////////////////////////////////////////////////////////////////////////////////// fdklsjfklsdjl

/**
 * Mixin: 在 readFromNBT/writeToNBT/serialize/deserialize 中携带并应用 tankCap。
 * - 编译期不会依赖 com.hbm.* 的具体 FluidTank 类，避免 @Shadow 问题。
 * - 运行时优先检测 net.minecraftforge.fluids.FluidTank 并使用官方接口（或反射改 capacity 字段）。
 * - 如果不是官方类型，再尝试常见字段/方法名称的反射回退。
 */
@Mixin(value = TileEntityCore.class, remap = false)
public abstract class _x_h_sp_zr_s_t_dddddddddd_{//TileEntityCoreTankMixin {

    // 候选名字（根据不同实现扩展即可）
    private static final String[] TANK_FIELD_NAMES = {"tanks", "fluidTanks", "tankArray"};
    private static final String[] CAP_FIELD_CANDIDATES = {"capacity", "maxFill", "max_fill", "max", "maxAmount", "maxFillAmount"};
    private static final String[] CAP_SETTERS = {"setCapacity", "setMaxFill", "setMax", "setMaxAmount", "setCapacityMB"};
    private static final String[] GET_FILL_NAMES = {"getFill", "getFluidAmount", "getAmount", "getFluidAmount"};
    private static final String[] SET_FILL_NAMES = {"setFill", "setFluidAmount", "setAmount"};
 //   private static final String[] GET_CAP_NAMES = {"getMaxFill", "getCapacity", "getMax"};


    //超
    
    private static final String[] GET_CAP_NAMES = new String[] {
        "getCapacity", "getMaxFill", "getMax", "getMaxAmount", "getCapacityMB", "getMaxFillAmount"
    };
    private static final String[] SET_CAP_NAMES = new String[] {
        "setCapacity", "setMaxFill", "setMax", "setMaxAmount", "setCapacityMB", "setMaxFillAmount"
    };
    private static final String[] CAP_FIELD_NAMES = new String[] {
        "capacity", "maxFill", "max_fill", "maxFillAmount", "max", "maxAmount"
    };

    //脑瘫gpt,(),     ,(),

    private Object[] getTanksArray() {
        try {
            for (String name : TANK_FIELD_NAMES) {
                try {
                    Field f = TileEntityCore.class.getDeclaredField(name);
                    f.setAccessible(true);
                    Object arr = f.get(this);
                    if (arr == null) return null;
                    int len = java.lang.reflect.Array.getLength(arr);
                    Object[] out = new Object[len];
                    for (int i = 0; i < len; i++) out[i] = java.lang.reflect.Array.get(arr, i);
                    return out;
                } catch (NoSuchFieldException ignored) {}
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private void clampForgeFluidTank(Object tankObj, int cap) {
        try {
            // tankObj is net.minecraftforge.fluids.FluidTank at runtime
            Class<?> forgeTankClass = Class.forName("net.minecraftforge.fluids.FluidTank");
            if (!forgeTankClass.isAssignableFrom(tankObj.getClass())) return;

            // 改 capacity 字段（protected int capacity）
            try {
                Field capField = forgeTankClass.getDeclaredField("capacity");
                capField.setAccessible(true);
                capField.setInt(tankObj, cap);
            } catch (NoSuchFieldException nsf) {
                // 无法找到 capacity 字段（意外），跳过
            }

            // 截断当前 fluid amount：getFluid() -> FluidStack.amount, 用 setFluid(new FluidStack(...))
            Method getFluid = forgeTankClass.getMethod("getFluid");
            Object fluidStack = getFluid.invoke(tankObj);
            if (fluidStack != null) {
                Class<?> fsClass = Class.forName("net.minecraftforge.fluids.FluidStack");
                Field amtField = fsClass.getDeclaredField("amount");
                amtField.setAccessible(true);
                int now = amtField.getInt(fluidStack);
                if (now > cap) {
                    // setFluid(new FluidStack(fluidStack.getFluid(), cap))
                    Method getFluidMeth = fsClass.getMethod("getFluid");
                    Object fluidObj = getFluidMeth.invoke(fluidStack);
                    Object newFs = fsClass.getConstructor(fluidObj.getClass(), int.class).newInstance(fluidObj, cap);
                    Method setFluid = forgeTankClass.getMethod("setFluid", fsClass);
                    setFluid.invoke(tankObj, newFs);
                }
            }
            System.out.println("[DFCFIX] applied Forge FluidTank capacity=" + cap + " for class " + tankObj.getClass().getName());
        } catch (ClassNotFoundException cnf) {
            // Forge classes not present? 那就走通用反射
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void applyCapacityByReflection(Object tankObj, int cap) {
        if (tankObj == null) return;
        // 优先使用可能的 setter
        Class<?> cls = tankObj.getClass();
        try {
            for (String mname : CAP_SETTERS) {
                try {
                    Method m = cls.getMethod(mname, int.class);
                    m.setAccessible(true);
                    m.invoke(tankObj, cap);
                    clampFillGeneric(tankObj, cap);
                    return;
                } catch (NoSuchMethodException ignored) {}
            }
            // 再尝试字段
            for (String fname : CAP_FIELD_CANDIDATES) {
                try {
                    Field ff = cls.getDeclaredField(fname);
                    ff.setAccessible(true);
                    Class<?> ttype = ff.getType();
                    if (ttype == int.class || ttype == Integer.class) {
                        ff.setInt(tankObj, cap);
                        clampFillGeneric(tankObj, cap);
                        return;
                    }
                } catch (NoSuchFieldException ignored) {}
            }
            System.out.println("[DFCFIX] applyCapacityByReflection: no candidate field/setter for " + cls.getName());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void clampFillGeneric(Object tankObj, int cap) {
        Class<?> cls = tankObj.getClass();
        try {
            Integer now = null;
            for (String g : GET_FILL_NAMES) {
                try {
                    Method gm = cls.getMethod(g);
                    gm.setAccessible(true);
                    Object v = gm.invoke(tankObj);
                    if (v instanceof Integer) { now = (Integer) v; break; }
                } catch (NoSuchMethodException ignored) {}
            }
            if (now != null && now > cap) {
                for (String s : SET_FILL_NAMES) {
                    try {
                        Method sm = cls.getMethod(s, int.class);
                        sm.setAccessible(true);
                        sm.invoke(tankObj, cap);
                        return;
                    } catch (NoSuchMethodException ignored) {}
                }
                // 最后尝试写 amount 字段
                for (String fname : new String[] {"amount", "fill", "fluidAmount"}) {
                    try {
                        Field ff = cls.getDeclaredField(fname);
                        ff.setAccessible(true);
                        ff.setInt(tankObj, cap);
                        return;
                    } catch (NoSuchFieldException ignored) {}
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void applyCapacityToTankObject(Object tankObj, int cap) {
        if (tankObj == null) return;
        // 优先走 Forge official
        try {
            Class<?> forgeTankClass = Class.forName("net.minecraftforge.fluids.FluidTank");
            if (forgeTankClass.isAssignableFrom(tankObj.getClass())) {
                clampForgeFluidTank(tankObj, cap);
                return;
            }
        } catch (ClassNotFoundException cnf) {
            // 没有 Forge FluidTank 类（极不可能在 dev env），继续走反射回退
        } catch (Throwable t) {
            t.printStackTrace();
        }
        // 回退到通用反射
        applyCapacityByReflection(tankObj, cap);
    }

  //  private void applyCapacityToAllTanks(int cap) {
  //      Object[] arr = getTanksArray();
   //    if (arr == null) return;
  //      for (Object t : arr) applyCapacityToTankObject(t, cap);
 //   }

    
    
    private void applyCapacityToAllTanks(int cap) {
        Object[] arr = getTanksArray();
        if (arr == null) return;
        for (Object t : arr) applyCapacityToTankObject(t, cap);
    }

    // readFromNBT: 从实体 nbt 读取 tankCap 并应用
    @Inject(method = "readFromNBT", at = @At("TAIL"))
    private void onReadFromNBT(NBTTagCompound nbt, CallbackInfo ci) {//CallbackInfoReturnable<NBTTagCompound> cir ){//CallbackInfo ci) {
        try {
            if (nbt != null && nbt.hasKey("tankCap")) {
                int cap = nbt.getInteger("tankCap");
                if (cap > 0) {
                    applyCapacityToAllTanks(cap);
                    System.out.println("[DFCFIX MIXIN] readFromNBT applied tankCap=" + cap);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // writeToNBT: 读 tank 当前 cap 并写回 nbt（持久化）
    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void onWriteToNBT(NBTTagCompound nbt, CallbackInfoReturnable<NBTTagCompound> cir){//CallbackInfo ci) {
        try {
            int cap = 0;
            Object[] arr = getTanksArray();
            if (arr != null && arr.length > 0 && arr[0] != null) {
                Object t0 = arr[0];
                Class<?> cls = t0.getClass();
                // 试官方 getCapacity
                try {
                    Class<?> forgeTankClass = Class.forName("net.minecraftforge.fluids.FluidTank");
                    if (forgeTankClass.isAssignableFrom(cls)) {
                        Method gm = forgeTankClass.getMethod("getCapacity");
                        Object v = gm.invoke(t0);
                        if (v instanceof Integer) cap = (Integer) v;
                    }
                } catch (Throwable ignored) {}
                if (cap == 0) {
                    // 回退到常见 getter/field
                    for (String gn : GET_CAP_NAMES) {
                        try {
                            Method gm = cls.getMethod(gn);
                            Object v = gm.invoke(t0);
                            if (v instanceof Integer) { cap = (Integer) v; break; }
                        } catch (Throwable ignored) {}
                    }
                }
                if (cap == 0) {
                    for (String fname : CAP_FIELD_CANDIDATES) {
                        try {
                            Field ff = cls.getDeclaredField(fname);
                            ff.setAccessible(true);
                            Object v = ff.get(t0);
                            if (v instanceof Integer) { cap = (Integer) v; break; }
                        } catch (Throwable ignored) {}
                    }
                }
            }
            if (cap > 0) {
                nbt.setInteger("tankCap", cap);
                System.out.println("[DFCFIX MIXIN] writeToNBT wrote tankCap=" + cap);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // network serialize/deserialize: 在 serialize 写入 cap，deserialize 时应用（使客户端立刻生效）
    @Inject(method = "serialize", at = @At("RETURN"))
    private void onSerialize(ByteBuf buf, CallbackInfo ci) {
        try {
            int cap = 0;
            Object[] arr = getTanksArray();
            if (arr != null && arr.length > 0 && arr[0] != null) {
                Object t0 = arr[0];
                Class<?> cls = t0.getClass();
                try {
                    Class<?> forgeTankClass = Class.forName("net.minecraftforge.fluids.FluidTank");
                    if (forgeTankClass.isAssignableFrom(cls)) {
                        Method gm = forgeTankClass.getMethod("getCapacity");
                        Object v = gm.invoke(t0);
                        if (v instanceof Integer) cap = (Integer) v;
                    }
                } catch (Throwable ignored) {}
                if (cap == 0) {
                    for (String gn : GET_CAP_NAMES) {
                        try {
                            Method gm = cls.getMethod(gn);
                            Object v = gm.invoke(t0);
                            if (v instanceof Integer) { cap = (Integer) v; break; }
                        } catch (Throwable ignored) {}
                    }
                }
            }
            buf.writeInt(cap);
            System.out.println("[DFCFIX MIXIN] serialize wrote tankCap=" + cap);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Inject(method = "deserialize", at = @At("RETURN"))
    private void onDeserialize(ByteBuf buf, CallbackInfo ci) {
        try {
            int cap = buf.readInt();
            if (cap > 0) {
                applyCapacityToAllTanks(cap);
                System.out.println("[DFCFIX MIXIN] deserialize applied tankCap=" + cap);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
