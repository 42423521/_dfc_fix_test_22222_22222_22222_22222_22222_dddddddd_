package com._test_ddddddd_.dfcfix.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;

import com.hbm.tileentity.machine.TileEntityCore;











//////////////////////////////////////////////////////////import com._test_ddddddd_.dfcfix.dnm_jw_son_of_bitch_ddddd_;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  import com._test_ddddddd_.dfcfix.UtilsDFCFix





import com._test_ddddddd_.dfcfix.dnm_jw_son_of_bitch_ddddd_;

import com._test_ddddddd_.dfcfix.UtilsDFCFix ;///////////////////////////////////////////////////////////////////////////////////////////////////////////////








import net.minecraft.block.state.IBlockState;













import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class c_d_s_c_ddddddddddddd_ddddddddddddddddddddd_ extends CommandBase {//.java//CommandDfcSetCore extends CommandBase {

    public c_d_s_c_ddddddddddddd_ddddddddddddddddddddd_(){//class c_d_s_c_ddddddddddddd_ddddddddddddddddddddd_()//CommandDfcSetCore() {
        // 调试用：确认类被加载
        System.out.println("[DFC] CommandDfcSetCore_2_ constructed");
    }












//    //注定来了,(),                                                                                                                                                                                                                                                                                                                                                                                      ,(),
















    @Override
    public String getName() {
        return "dfcsetcore_2_";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dfcsetcore_2_ set_2_ <x> <y> <z> <fuelMax:int>\n"

             + "/dfcsetcore_2_ set_2_2_ <x> <y> <z> <heatCap:int>\n"

             + "/dfcsetcore_2_ enablebomb <x> <y> <z>\n"
             + "/dfcsetcore_2_ disablebomb <x> <y> <z>\n"
             + "/dfcsetcore_2_ add <x> <y> <z> <key> <type:int|bool|string|long|double|byte|short> <value>\n"
             + "/dfcsetcore_2_ edit <x> <y> <z> <key> <type> <value>\n"
             + "/dfcsetcore_2_ clear <x> <y> <z> [key]\n";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("dfcset_2_", "dfccore_2_");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }

        String sub = args[0].toLowerCase();

        try {
            switch (sub) {
                case "set_2_":
                    // /dfcsetcore set x y z heatCap fuelMax      f
                    if (args.length != 5) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    int x = parseIntStrict(args[1]);
                    int y = parseIntStrict(args[2]);
                    int z = parseIntStrict(args[3]);
                //    int heatCap = parseIntStrict(args[4]);
                    int fuelMax = parseIntStrict(args[4]);

                    handleSet_2_(sender, x, y, z,fuelMax);    //, heatCap, fuelMax);
                    break;

                case "set_2_2_":
                    // /dfcsetcore set x y z heatCap fuelMax                   h
                    if (args.length != 5) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    int x_2_2_ = parseIntStrict(args[1]);
                    int y_2_2_ = parseIntStrict(args[2]);
                    int z_2_2_ = parseIntStrict(args[3]);
                    int heatCap = parseIntStrict(args[4]);
            //        int fuelMax = parseIntStrict(args[5]);

//                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////只有相对后一个    才改等,(),                                                                                                                                                                                                   ,(),

                    handleSet_2_2_(sender, x_2_2_, y_2_2_, z_2_2_, heatCap);
                    break;



                case "enablebomb":
                case "disablebomb":
                    // /dfcsetcore enablebomb x y z
                    if (args.length != 4) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    x = parseIntStrict(args[1]);
                    y = parseIntStrict(args[2]);
                    z = parseIntStrict(args[3]);
                    boolean disable = sub.equals("disablebomb");
                    handleSetBomb(sender, x, y, z, disable);
                    break;

                case "add":
                    // /dfcsetcore add x y z key type value
                    if (args.length != 7) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    x = parseIntStrict(args[1]);
                    y = parseIntStrict(args[2]);
                    z = parseIntStrict(args[3]);
                    String addKey = args[4];
                    String addType = args[5].toLowerCase();
                    String addValue = args[6];
                    handleAdd(sender, x, y, z, addKey, addType, addValue);
                    break;

                case "edit":
                    // /dfcsetcore edit x y z key type value
                    if (args.length != 7) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    x = parseIntStrict(args[1]);
                    y = parseIntStrict(args[2]);
                    z = parseIntStrict(args[3]);
                    String editKey = args[4];
                    String editType = args[5].toLowerCase();
                    String editValue = args[6];
                    handleEdit(sender, x, y, z, editKey, editType, editValue);
                    break;

                case "clear":
                    // /dfcsetcore clear x y z [key]
                    if (args.length != 4 && args.length != 5) {
                        sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                        return;
                    }
                    x = parseIntStrict(args[1]);
                    y = parseIntStrict(args[2]);
                    z = parseIntStrict(args[3]);
                    if (args.length == 5) {
                        handleClearKey(sender, x, y, z, args[4]);
                    } else {
                        handleClearDefault(sender, x, y, z);
                    }
                    break;

                default:
                    sender.sendMessage(new TextComponentString(getUsage(sender)));
            }
        } catch (NumberFormatException nfe) {
            sender.sendMessage(new TextComponentString("Number format error: " + nfe.getMessage()));
        } catch (IllegalArgumentException iae) {
            sender.sendMessage(new TextComponentString("Argument error: " + iae.getMessage()));
        } catch (Exception ex) {
            sender.sendMessage(new TextComponentString("Error: " + ex.getClass().getSimpleName() + " " + ex.getMessage()));
            ex.printStackTrace();
        }
    }

    private void handleSet_2_(ICommandSender sender, int x, int y, int z, int fuelMax) {      //int heatCap, int fuelMax) {
    //    World world = sender.getEntityWorld();
    //    BlockPos pos = new BlockPos(x, y, z);




     //   TileEntityCore te = getCoreOrWarn(world, pos, sender);
    //    if (te == null) return;

/////////////////////////        NBTTagCompound data = te.getTileData();
    //    data.setInteger("heatCap", heatCap);
                                                                                                                                                       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        data.setInteger("fuelMax", fuelMax);

   //     te.markDirty();
    //    world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    //    sender.sendMessage(new TextComponentString("Set DFC core at " + x + "," + y + "," + z + " -> heatCap=" + heatCap + " fuelMax=" + fuelMax));
  //  }

    
    ////////////    te.markDirty();
     ////////////////////   world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
      ///////////////////////////////  sender.sendMessage(new TextComponentString("Set DFC core at " + x + "," + y + "," + z + " -> fuelMax=" + fuelMax));





        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x,y,z);



     //   TileEntity teRaw = world.getTileEntity(pos);
  //      if(!(teRaw instanceof TileEntityCore)) {
   //         sender.addChatMessage(new TextComponentString("No core at " + pos));
    //        return;
    //    }
    //    TileEntityCore te = (TileEntityCore) teRaw;
    
    //    te.getTileData().setInteger("tankCap", tankCap); // 写入 tileData
   //     te.markDirty(); // 标记保存
    //    IBlockState state = world.getBlockState(pos);
     //   world.notifyBlockUpdate(pos, state, state, 3); // 通知客户端刷新


   //     // 假设 te 是 TileEntityCore 实例，tankCap 是新的容量值
    //    te.getTileData().setInteger("tankCap", tankCap);
   //     te.markDirty();
   //     IBlockState state = world.getBlockState(pos);
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      world.notifyBlockUpdate(pos, state, state, 3);

        // 下面是“即时应用”的反射实现（可直接放在命令里）
  //      try {
       //     for (com.hbm.inventory.fluid.tank.FluidTank t : te.tanks) {
      //          if (t == null) continue;
      //          boolean applied = false;
        //        // 1) 尝试调用 setter names
        //        String[] setters = new String[] { "setCapacity", "setMaxFill", "setMax", "setMaxAmount", "setCapacityMB" };
        //        for (String s : setters) {
           //         try {
               //         Method ms = t.getClass().getMethod(s, int.class);
                 //       ms.setAccessible(true);
                     //   ms.invoke(t, tankCap);
                   //     applied = true;
                  //      break;
               //     } catch (NoSuchMethodException ignore) { }
           //     }
           //     // 2) 尝试常见字段名
           //     if (!applied) {
           //         String[] fields = new String[] { "capacity", "maxFill", "max_fill", "maxFillAmount", "max", "maxAmount" };
           //         for (String f : fields) {
             //           try {
                //            Field ff = t.getClass().getDeclaredField(f);
                    //        ff.setAccessible(true);
                      //      ff.setInt(t, tankCap);
                     //       applied = true;
                  //          break;
                  //      } catch (NoSuchFieldException ignore) { }
           //         }
      //          }
      //          // 3) 截断当前量
        //        try {
          //          Method getFill = t.getClass().getMethod("getFill");
            //        int now = (Integer) getFill.invoke(t);
              //      if (now > tankCap) {
                //        Method setFill = t.getClass().getMethod("setFill", int.class);
                ////////        setFill.invoke(t, tankCap);
            //        }
           //     } catch (NoSuchMethodException ignore) { }
       //     }
     //       System.out.println("[DFCFIX CMD] applied tankCap realtime=" + tankCap + " pos=" + pos);
   //     } catch (Throwable ex) {
    //        ex.printStackTrace();
  //////////      }


  //      Object[] tanks = /* 用 TileEntityCore.class 反射取出 tanks 字段，和上面 mixin 一致 */;
//for (Object t : tanks) {
//    // 如果是 net.minecraftforge.fluids.FluidTank，直接 (FluidTank) cast 并反射改 capacity 字段或用 setFluid
 //   if (t instanceof net.minecraftforge.fluids.FluidTank) {
  //      net.minecraftforge.fluids.FluidTank ft = (net.minecraftforge.fluids.FluidTank) t;
  //      java.lang.reflect.Field f = net.minecraftforge.fluids.FluidTank.class.getDeclaredField("capacity");
  //      f.setAccessible(true);
   //     f.setInt(ft, tankCap);
  //      net.minecraftforge.fluids.FluidStack fs = ft.getFluid();
  //      if (fs != null && fs.amount > tankCap) ft.setFluid(new net.minecraftforge.fluids.FluidStack(fs.getFluid(), tankCap));
//    } else {
 //       // 回退到通用反射，和 mixin 的 applyCapacityByReflection 一样
 //   }
//}

     //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   UtilsDFCFix.applyTankCapRealtime((Object)te, world, pos, fuelmax);



    
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   sender.addChatMessage(new TextComponentString("Set tankCap=" + tankCap + " at " + pos));
       //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// System.out.println("[DFCFIX CMD] set tankCap=" + tankCap + " at " + pos);
  //  }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sender.addChatMessage(new TextComponentString("Set fuelMax=" + fuelMax + " at " + pos));
         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   System.out.println("[DFCFIX CMD] set fuelMax=" + fuelMax + " at " + pos);
    



  //  }




 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   }






  ///////////////////////////////////////////////////////////  import com._test_ddddddd_.dfcfix.dnm_jw_son_of_bitch_ddddd_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// import com._test_ddddddd_.dfcfix.UtilsDFCFix

        // 需要的 imports（放文件头）
    // import net.minecraft.command.ICommandSender;
    // import net.minecraft.nbt.NBTTagCompound;
    // import net.minecraft.tileentity.TileEntity;
    // import net.minecraft.util.math.BlockPos;
    // import net.minecraft.block.state.IBlockState;
    // import net.minecraft.util.text.TextComponentString;
    // import net.minecraft.world.World;
    // import com.hbm.tileentity.machine.TileEntityCore;

    //import net.minecraft.block.state.IBlockState;
    //import net.minecraft.block.state.IBlockState;
    //import net.minecraft.block.state.IBlockState;

    
    //private void handleSet_2_(ICommandSender sender, int x, int y, int z, int fuelMax) {
 //       World world = sender.getEntityWorld();
    //    BlockPos pos = new BlockPos(x, y, z);
    
        TileEntity teRaw = world.getTileEntity(pos);
        if (!(teRaw instanceof TileEntityCore)) {
            sender.sendMessage(new TextComponentString("No core at " + pos));    //.addChatMessage(new TextComponentString("No core at " + pos));
            return;
        }
    
        TileEntityCore te = (TileEntityCore) teRaw;
    
        try {
            // 1) 写入 NBT（持久化供 mixin/其他逻辑读取）
            NBTTagCompound data = te.getTileData();
            data.setInteger("fuelMax", fuelMax);   // 用于限制每次 drain（你 mixin 里读这个 key）
            data.setInteger("tankCap", fuelMax);   // 同时把 tank 容量也存到 nbt（你 mixin 里 write/read 要一致）
            te.markDirty();
    
            // 2) 通知区块更新，强制客户端刷新显示
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
    
            // 3) 立即在运行时尝试把 tanks 的容量改掉并截断现有液体（即时生效）
            //    这里优先调用你放在项目里的 UtilsDFCFix（如果有），否则用内联方式。
            try {
                // 若你已经添加了 UtilsDFCFix 类，下面这一行就够：
          /////////////////////////      UtilsDFCFix.applyTankCapRealtime((Object) te, world, pos, fuelMax);

           //     dnm_jw_son_of_bitch_ddddd_.applyTankCapRealtime((Object) te, world, pos, fuelMax);


          //      UtilsDFCFix.applyTankCapRealtime((Object) te, world, pos, fuelMax);

                   dnm_jw_son_of_bitch_ddddd_.applyTankCapRealtime((Object) te, world, pos, fuelMax);


                 //   com._test_ddddddd_.dfcfix.dnm_jw_son_of_bitch_ddddd_


            } catch (NoClassDefFoundError e){// | Throwable e) {
                // 如果没有 UtilsDFCFix（或者想直接内联），我们做一个最小回退（best-effort）
                try {
                    // 简短回退：反射取 tanks 字段并尝试写 capacity（不保证所有自定义实现命中）
                    String[] tankFieldNames = new String[] {"tanks", "fluidTanks", "tankArray"};
                    Object[] tanksArr = null;
                    for (String name : tankFieldNames) {
                        try {
                            java.lang.reflect.Field f = te.getClass().getDeclaredField(name);
                            f.setAccessible(true);
                            Object arr = f.get(te);
                            if (arr != null) {
                                int len = java.lang.reflect.Array.getLength(arr);
                                tanksArr = new Object[len];
                                for (int i = 0; i < len; i++) tanksArr[i] = java.lang.reflect.Array.get(arr, i);
                            }
                            break;
                        } catch (NoSuchFieldException ignore) {}
                    }
                    if (tanksArr != null) {
                        for (Object tank : tanksArr) {
                            if (tank == null) continue;
                            // 优先处理官方 Forge FluidTank（反射，不需要编译期依赖）

                                //cnm

                            boolean applied = false;

                            try {
                                Class<?> forgeTankCls = Class.forName("net.minecraftforge.fluids.FluidTank");
                                if (forgeTankCls.isAssignableFrom(tank.getClass())) {
                                    try {
                                        java.lang.reflect.Field capField = forgeTankCls.getDeclaredField("capacity");
                                        capField.setAccessible(true);
                                        capField.setInt(tank, fuelMax);
                                    } catch (NoSuchFieldException ignore) {}
                                    // 截断 FluidStack.amount
                                    try {
                                        java.lang.reflect.Method getFluid = forgeTankCls.getMethod("getFluid");
                                        Object fs = getFluid.invoke(tank);
                                        if (fs != null) {
                                            Class<?> fsCls = Class.forName("net.minecraftforge.fluids.FluidStack");
                                            try {
                                                java.lang.reflect.Field amtF = fsCls.getDeclaredField("amount");
                                                amtF.setAccessible(true);
                                                int now = amtF.getInt(fs);
                                                if (now > fuelMax) {
                                                    java.lang.reflect.Method getFluidMeth = fsCls.getMethod("getFluid");
                                                    Object fluidObj = getFluidMeth.invoke(fs);
                                                    Object newFs = fsCls.getConstructor(fluidObj.getClass(), int.class).newInstance(fluidObj, fuelMax);
                                                    java.lang.reflect.Method setFluid = forgeTankCls.getMethod("setFluid", fsCls);
                                                    setFluid.invoke(tank, newFs);
                                                }
                                            } catch (NoSuchFieldException nf) {}
                                        }
                                    } catch (NoSuchMethodException ignore) {}

                                    applied = true;

                                //    System.out.println("[DFCFIX CMD] applied Forge FluidTank capacity -> " + fuelMax + " on " + tank.getClass().getName());
                                 //   continue;
                                }
                            } catch (ClassNotFoundException cnf) {
                                // 没有 Forge FluidTank 类的话，走下面通用反射
                            }catch (Throwable t) {
                                t.printStackTrace();
                            } 

                            if (applied) {
                                System.out.println("[DFCFIX CMD] applied Forge FluidTank capacity -> " + fuelMax + " on " + tank.getClass().getName());
                                continue;
                            }

    
                            // 通用反射（尝试 setter 或字段）
                            Class<?> cls = tank.getClass();
                     //       boolean applied = false;
                            String[] capSetters = new String[] {"setCapacity", "setMaxFill", "setMax", "setMaxAmount", "setCapacityMB"};
                            String[] capFields = new String[] {"capacity", "maxFill", "max_fill", "maxFillAmount", "max", "maxAmount"};
                            for (String mname : capSetters) {
                                try {
                                    java.lang.reflect.Method m = cls.getMethod(mname, int.class);
                                    m.setAccessible(true);
                                    m.invoke(tank, fuelMax);
                                    applied = true;
                                    break;
                                } catch (NoSuchMethodException ignore) {}
                            }
                            if (!applied) {
                                for (String fname : capFields) {
                                    try {
                                        java.lang.reflect.Field ff = cls.getDeclaredField(fname);
                                        ff.setAccessible(true);
                                        ff.setInt(tank, fuelMax);
                                        applied = true;
                                        break;
                                    } catch (NoSuchFieldException ignore) {}
                                }
                            }
                            // 截断 fill/amount
                            try {
                                Integer now = null;
                                try {
                                    java.lang.reflect.Method gm = cls.getMethod("getFill");
                                    Object v = gm.invoke(tank);
                                    if (v instanceof Integer) now = (Integer) v;
                                } catch (NoSuchMethodException ignore) {}
                                if (now != null && now > fuelMax) {
                                    try {
                                        java.lang.reflect.Method sm = cls.getMethod("setFill", int.class);
                                        sm.invoke(tank, fuelMax);
                                    } catch (NoSuchMethodException ignore) {
                                        String[] altAmt = new String[] {"amount", "fill", "fluidAmount"};
                                        for (String af : altAmt) {
                                            try {
                                                java.lang.reflect.Field f = cls.getDeclaredField(af);
                                                f.setAccessible(true);
                                                f.setInt(tank, fuelMax);
                                                break;
                                            } catch (NoSuchFieldException ignore2) {}
                                        }
                                    }
                                }
                            } catch (Throwable tt) {
                                tt.printStackTrace();
                            }
    
                            System.out.println("[DFCFIX CMD] attempted apply to tank class " + tank.getClass().getName() + " cap=" + fuelMax);
                        } // end for tanks
                    } // end if tanksArr
                } catch (Throwable inlErr) {
                    inlErr.printStackTrace();
                }
            } // end try Utils call
    
            // 最后反馈给命令发起者
            sender.sendMessage(new TextComponentString("Set fuelMax=" + fuelMax + " at " + pos));//.addChatMessage(new TextComponentString("Set fuelMax=" + fuelMax + " at " + pos));
            System.out.println("[DFCFIX CMD] set fuelMax=" + fuelMax + " at " + pos);
    
        } catch (Throwable ex) {
            ex.printStackTrace();
          ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  sender.addChatMessage(new TextComponentString("Error applying fuelMax: " + ex.getMessage()));
            sender.sendMessage(new TextComponentString("Error applying fuelMax: " + ex.getMessage()));

        }
    }


















//           出问题杀了你，垃圾gpt,(),      ,(),




                                                                                     //改了，别重，狗日的，屁事多，杀杀杀,(),                                                                            ,(),







    //set_2_2_
    private void handleSet_2_2_(ICommandSender sender, int x_2_2_, int y_2_2_, int z_2_2_, int heatCap) {//, int fuelMax) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x_2_2_, y_2_2_, z_2_2_);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);


        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        data.setInteger("heatCap", heatCap);
   //     data.setInteger("fuelMax", fuelMax);

     



                                    //这个相对无所谓,(),                                                                                                                                          ,(),


        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString("Set DFC core at " + x_2_2_ + "," + y_2_2_ + "," + z_2_2_ + " -> heatCap=" + heatCap)); //+ " fuelMax=" + fuelMax));
    }



    private void handleSetBomb(ICommandSender sender, int x, int y, int z, boolean disable) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);
        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        data.setBoolean("disableBomb", disable);

        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString((disable ? "Disabled" : "Enabled") + " bomb at " + x + "," + y + "," + z));
    }

    private void handleAdd(ICommandSender sender, int x, int y, int z, String key, String type, String value) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);
        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        if (data.hasKey(key)) {
            sender.sendMessage(new TextComponentString("Key '" + key + "' already exists. Use edit to modify."));
            return;
        }
        setTagByType(data, key, type, value);
        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString("Added NBT '" + key + "' to core at " + x + "," + y + "," + z));
    }

    private void handleEdit(ICommandSender sender, int x, int y, int z, String key, String type, String value) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);
        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        if (!data.hasKey(key)) {
            sender.sendMessage(new TextComponentString("Key '" + key + "' does not exist. Use add to create."));
            return;
        }
        setTagByType(data, key, type, value);
        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString("Edited NBT '" + key + "' at " + x + "," + y + "," + z));
    }

    private void handleClearKey(ICommandSender sender, int x, int y, int z, String key) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);
        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        if (!data.hasKey(key)) {
            sender.sendMessage(new TextComponentString("Key '" + key + "' not present."));
            return;
        }
        data.removeTag(key);
        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString("Removed key '" + key + "' at " + x + "," + y + "," + z));
    }

    private void handleClearDefault(ICommandSender sender, int x, int y, int z) {
        World world = sender.getEntityWorld();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityCore te = getCoreOrWarn(world, pos, sender);
        if (te == null) return;

        NBTTagCompound data = te.getTileData();
        data.removeTag("heatCap");
        data.removeTag("fuelMax");
        data.removeTag("disableBomb");
        te.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        sender.sendMessage(new TextComponentString("Cleared default DFC NBT at " + x + "," + y + "," + z));
    }

    private TileEntityCore getCoreOrWarn(World world, BlockPos pos, ICommandSender sender) {
        if (!world.isBlockLoaded(pos)) {
            sender.sendMessage(new TextComponentString("Chunk not loaded or invalid position."));
            return null;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityCore)) {
            sender.sendMessage(new TextComponentString("Tile at " + pos.getX() + "," + pos.getY() + "," + pos.getZ() + " is not a DFC core."));
            return null;
        }
        return (TileEntityCore) te;
    }

    private int parseIntStrict(String s) throws NumberFormatException {
        return Integer.parseInt(s);
    }

    private boolean parseBooleanStrict(String s) {
        if ("true".equalsIgnoreCase(s)) return true;
        if ("false".equalsIgnoreCase(s)) return false;
        throw new IllegalArgumentException("Boolean must be true or false.");
    }

    private void setTagByType(NBTTagCompound data, String key, String type, String value) {
        switch (type) {
            case "int":
                data.setInteger(key, Integer.parseInt(value));
                break;
            case "bool":
            case "boolean":
                data.setBoolean(key, parseBooleanStrict(value));
                break;
            case "long":
                data.setLong(key, Long.parseLong(value));
                break;
            case "double":
                data.setDouble(key, Double.parseDouble(value));
                break;
            case "byte":
                data.setByte(key, Byte.parseByte(value));
                break;
            case "short":
                data.setShort(key, Short.parseShort(value));
                break;
            case "string":
            case "str":
                data.setString(key, value);
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // 需要 op
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "set_2_", "set_2_2_" ,  "enablebomb", "disablebomb", "add", "edit", "clear");
        }
        return Collections.emptyList();
    }
}
