




package com._test_ddddddd_.dfcfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;




import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////import org.spongepowered.asm.mixin.injection.ModifyExpressionValue;//.ModifyConstant;





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////import org.spongepowered.asm.mixin.injection








/////////////////////////////////////////////////////////////////////////////////////////import org.spongepowered.asm.mixin.injection.ModifyExpressionValue;








import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

















import com.hbm.tileentity.machine.TileEntityCore;
import com.hbm.entity.logic.EntityNukeExplosionMK3;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Method;

/**
 * Mixin to apply per-core NBT-controlled limits:
 *  - read tile.getTileData() for heatCap, fuelMax, disableBomb
 *  - cap drains to fuelMax
 *  - treat disableBomb=true as jammed (prevent explosion)
 *
 * 注意: 这个 mixin 用反射调用 FluidTank.drain(...) 以避免递归 redirect。
 */
///////////@Mixin(TileEntityCore.class)

@Mixin(value = TileEntityCore.class, remap = false)


public abstract class _t_e_c_m_dddddd_{         //.java//TileEntityCoreMixin {

    // 把 safeTimer 强制触发 1200 改为非常大值 (等于不触发)
  //  @ModifyConstant(method = "update", constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 1200))
//    private int modifySafeTimerCap(int orig) {
 //       return Integer.MAX_VALUE;
  //  }


//    @Mixin(TileEntityCore.class)
//    public abstract class TileEntityCoreMixin {

    /**
     * 把原版 safeTimer > 1200 的 1200 改掉
     */
    @ModifyConstant(
        method = "update",
        constant = @Constant(intValue = 1200)//@org.spongepowered.asm.mixin.injection.Constant(intValue = 1200))
    )
    private int changeSafeTimerLimit(int original) {
        // 例如直接改成 Integer.MAX_VALUE，等于永远不会触发 safeTimer 分支
        return Integer.MAX_VALUE;
    }
//}



  //  @Redirect(
 //       method = "targetMethod",
 //       at = @At(value = "INVOKE", target = "Ljava/lang/Integer;compare(II)I")
 //   )
//    private int forceFalse(int a, int b) {
 //       if (a == this.salftime && b == 1200) {
  //          return -1; // 永远认为 salftime < 1200
    ///////////////////////////////    }
       ////////////////////////////////////////////// return Integer.compare(a, b);
  //////////////////////////////////////////////////////////////////////////////////////////////// }

  ////////  @Mixin(TargetClass.class)
 /////   public class MyMixin {
   //     @ModifyExpressionValue(
    //        method = "targetMethod",
      //      at = @At(value = "INVOKE",
        //             target = "Ljava/lang/Integer;compare(II)I")
     //   )
    //    private boolean forceFalse(boolean original) {
    //        return false; // salttime > 1200 永远 false
   //     }
//    }

    






 //   @Mixin(TargetClass.class)
//public class MyMixin {
    @ModifyExpressionValue(
        method = "targetMethod",
        at = @At(value = "INVOKE",
                 target = "Ljava/lang/Integer;compare(II)I")
    )
    private boolean forceFalse(boolean original) {
        return false; // salttime > 1200 永远 false
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////}



















    // 如果 EntityNukeExplosionMK3.isJammed(...) 被调用，这里优先检查 tile 的 disableBomb NBT
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/hbm/entity/logic/EntityNukeExplosionMK3;isJammed(Lnet/minecraft/world/World;Lcom/hbm/entity/logic/EntityNukeExplosionMK3;)Z"))//        exp是吧
    private boolean redirectIsJammed(World world, EntityNukeExplosionMK3 exp) {
        try {
            // exp 的 pos 没法直接拿，我们用当前 tile 的位置：this is TileEntityCore
            TileEntityCore te = (TileEntityCore) (Object) this;
            BlockPos pos = te.getPos();
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                NBTTagCompound d = tile.getTileData();
                if (d != null && d.hasKey("disableBomb") && d.getBoolean("disableBomb")) {
                    // 当 disableBomb=true 时，视为 jammed（阻止爆炸）

                    System.out.println("[DFCFIX] disableBomb true at " + pos);




                    return true;
                }
            }
        } catch (Throwable t) {
            // 忽略异常，退回原判定

            t.printStackTrace(); // dev 时打印，线上可去掉

        }
        return EntityNukeExplosionMK3.isJammed(world, exp);
    }


//禁爆     kx,狗日的，禁爆,(),

/////////////////////////////////////////////////////////////////////////,(),



    /**
     * 在 burn() 中，调用 tanks[0].drain(demand, true) / tanks[1].drain(demand, true)
     * 我们把对 FluidTank.drain 的调用重定向到这里，先按 tile 的 fuelMax 限制 demand 再反射调用原方法。
     *
     * Target descriptor for FluidTank.drain(int, boolean) is (IZ)Lnet/minecraftforge/fluids/FluidStack;
     */
    @Redirect(method = "burn", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidTank;drain(IZ)Lnet/minecraftforge/fluids/FluidStack;"))
    private FluidStack redirectDrain(FluidTank tank, int amount, boolean doDrain) {
        try {
            // 取得这个 tank 所属 tile entity（容器的上下文是 TileEntityCore.this）
            TileEntityCore te = (TileEntityCore) (Object) this;
            NBTTagCompound d = te.getTileData();
        //    if (d != null && d.hasKey("fuelMax")) {
          //      int fuelMax = d.getInteger("fuelMax");

            if (true){

                int fuelMax = (d != null && d.hasKey("fuelMax")) ? d.getInteger("fuelMax") : 0;
                System.out.println("[DFCFIX DRAIN] pos=" + te.getPos() + " req=" + amount + " fuelMax=" + fuelMax + " doDrain=" + doDrain);


                if (fuelMax > 0) {
                    // 将单次 drain 限制为 fuelMax（防止一次性抽超过你想要的）
                    if (amount > fuelMax) amount = fuelMax; System.out.println("[DFCFIX DRAIN] clamped -> " + amount + " pos=" + te.getPos());


                }

         //        if (fuelMax > 0 && amount > fuelMax) {
             //       amount = fuelMax;
            //    }


              //    if (fuelMax > 0 && amount > fuelMax) {
                 //   amount = fuelMax;
            //    }






//狗超的world,   西海9）《       ,(),



            }

            // 通过反射调用原始 FluidTank.drain(int, boolean) 来避免递归（redirect 会重写直接 bytecode 调用）
            Method m = FluidTank.class.getDeclaredMethod("drain", int.class, boolean.class);
            m.setAccessible(true);
            Object res = m.invoke(tank, amount, doDrain);
            return (FluidStack) res;
        } catch (NoSuchMethodException nsme) {
            // 发生意外（理论不该发生），回退为直接调用（可能导致递归，但概率小）

            //cnm

            nsme.printStackTrace();




            return tank.drain(amount, doDrain);
        } catch (Throwable th) {
            // 兜底：如出错则直接调用，保守处理


            th.printStackTrace();




            try {
                return tank.drain(amount, doDrain);
            } catch (Throwable t2) {

                t2.printStackTrace();



                return null;
            }
        }
    }









    /**
     * 在 burn() 执行完后，cap heat（可选）：
     * 读取 tile 的 heatCap 并截断 heat 字段。
     * 这里使用注入到方法返回点（RETURN），确保原来生成的 powerOutput 正常返回。
     */
    @Inject(method = "burn", at = @At("RETURN"))
    private void capHeatAfterBurn(long joules, CallbackInfoReturnable<Long> cir) {
        try {
            TileEntityCore te = (TileEntityCore) (Object) this;
            NBTTagCompound d = te.getTileData();
            if (d != null && d.hasKey("heatCap")) {
                int heatCap = d.getInteger("heatCap");
                if (heatCap > 0) {
                    // 反射设置私有字段 heat（原类中 heat 字段为 int heat）
                    java.lang.reflect.Field f = TileEntityCore.class.getDeclaredField("heat");
                    f.setAccessible(true);
                    int heat = f.getInt(te);
                    if (heat > heatCap) {
                        f.setInt(te, heatCap);




                        System.out.println("[DFCFIX] capped heat to " + heatCap + " pos=" + te.getPos());







                    }
                }
            }
        } catch (Throwable t) {
            // 忽略异常
        }
    }

    
  //  // 2. 修改 drain() 的取值
 //   @Redirect(
 //       method = "drain",
  //      at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;getInteger(Ljava/lang/String;)I")
//    )
 //   private int redirectDrain(NBTTagCompound nbt, String key) {
  //      if ("fuelMax".equals(key)) {
    //        return nbt.hasKey("fuelMax") ? nbt.getInteger("fuelMax") : 0;
    //    }
  //      return nbt.getInteger(key);
///////////////    }//、、\




    
    // 2. 修改 drain() 的取值
    @Redirect(
        method = "drain",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;getInteger(Ljava/lang/String;)I")
    )
    private int redirectDrain(NBTTagCompound nbt, String key) {
        if ("fuelMax".equals(key)) {
            return nbt.hasKey("fuelMax") ? nbt.getInteger("fuelMax") : 0;
        }
        return nbt.getInteger(key);
    }



























///////////////////////////////////////////////////////////////////////    吼尼玛吼



























    @Inject(method = "readFromNBT", at = @At("TAIL"))
    private void injectReadFromNBT(NBTTagCompound nbt, CallbackInfo ci) {
        TileEntityCore self = (TileEntityCore)(Object)this;
        if(nbt.hasKey("fuelMax")) {
            self.getTileData().setInteger("fuelMax", nbt.getInteger("fuelMax"));
        }
        if(nbt.hasKey("heatCap")) {
            self.getTileData().setInteger("heatCap", nbt.getInteger("heatCap"));
        }

        System.out.println("[DFCFIX MIXIN] readFromNBT pos=" + self.getPos() + " fuelMax=" + self.getTileData().getInteger("fuelMax"));

    }

    @Inject(method = "writeToNBT", at = @At("TAIL"))
 //   private void injectWriteToNBT(NBTTagCompound nbt, CallbackInfo ci) {



    

    private void injectWriteToNBT(NBTTagCompound nbt, CallbackInfoReturnable<NBTTagCompound> cir) {






        TileEntityCore self = (TileEntityCore)(Object)this;
        NBTTagCompound data = self.getTileData();
        if(data.hasKey("fuelMax")) {
            nbt.setInteger("fuelMax", data.getInteger("fuelMax"));
        }
        if(data.hasKey("heatCap")) {
            nbt.setInteger("heatCap", data.getInteger("heatCap"));
        }

        System.out.println("[DFCFIX MIXIN] writeToNBT pos=" + self.getPos() + " fuelMax=" + data.getInteger("fuelMax"));





    }


                  //////////////////////////////////////// /////////////////////////////     、、、、、、、、、、、、、、、、、      传给你们，数量方面                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ，（）《           ，（），














}
