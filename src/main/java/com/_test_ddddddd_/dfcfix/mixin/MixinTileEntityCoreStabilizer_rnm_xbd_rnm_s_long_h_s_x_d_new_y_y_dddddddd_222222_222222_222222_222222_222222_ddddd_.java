





package com._test_ddddddd_.dfcfix.mixin;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCore;


import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
/////////////////////////////////////////////////////////////////////////////////////////import com._test_ddddddd_.dfcfix.util.LensCompat;




import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


import com._test_ddddddd_.dfcfix.util.fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_;////////////////////.java

/**
 * 覆盖 TileEntityCoreStabilizer.update()，把直接访问 lens.maxDamage 的地方改为兼容读取。
 * 这里不声明构造、也不 extends 任何类。直接在方法里把 this 转回目标类实例。
 */
@Mixin(TileEntityCoreStabilizer.class)
public abstract class MixinTileEntityCoreStabilizer_rnm_xbd_rnm_s_long_h_s_x_d_new_y_y_dddddddd_222222_222222_222222_222222_222222_ddddd_{//.java//MixinTileEntityCoreStabilizer {

    @Overwrite
    public void update() {
        // 把 this 转回目标类实例以便调用字段/方法（编译期目标类可见）
        TileEntityCoreStabilizer self = (TileEntityCoreStabilizer) (Object) this;

        if (!self.getWorld().isRemote) {
            self.updateStandardConnections(self.getWorld(), self.getPos());

            self.watts = MathHelper.clamp(self.watts, 1, 100);
            long demand = (long) Math.pow(self.watts, 6);
            self.isOn = false;

            self.beam = 0;

       //     com.hbm.items.machine.ItemLens lens = null;
      //      if (self.inventory.getStackInSlot(0).getItem() instanceof com.hbm.items.machine.ItemLens) {
     //           lens = (com.hbm.items.machine.ItemLens) self.inventory.getStackInSlot(0).getItem();
     //       }

            ItemStack stack = self.inventory.getStackInSlot(0);
            com.hbm.items.machine.ItemLens lens = null;
            if (stack != null && !stack.isEmpty() && stack.getItem() instanceof com.hbm.items.machine.ItemLens) {
                lens = (com.hbm.items.machine.ItemLens) stack.getItem();
            }



            if (lens != null && self.power >= demand * ((long) lens.drainMod)) {
                self.isOn = true;
                EnumFacing dir = EnumFacing.byIndex(self.getBlockMetadata());
                for (int i = 1; i <= TileEntityCoreStabilizer.range; i++) {

                    int x = self.getPos().getX() + dir.getXOffset() * i;
                    int y = self.getPos().getY() + dir.getYOffset() * i;
                    int z = self.getPos().getZ() + dir.getZOffset() * i;
                    BlockPos pos1 = new BlockPos(x, y, z);


                    
                    net.minecraft.tileentity.TileEntity te = self.getWorld().getTileEntity(pos1);
                    if (te != null && te.getClass().getName().equals("com.hbm.tileentity.machine.TileEntityCore")) {

                        // try to update core.field via reflection (safe)
                        try {
                            java.lang.reflect.Field field = te.getClass().getDeclaredField("field");
                            field.setAccessible(true);
                            int cur = field.getInt(te);
                            field.setInt(te, cur + (int) (self.watts * lens.fieldMod));
                        } catch (Throwable ignored) {}

                        self.power -= (long) (demand * lens.drainMod);
                        self.beam = i;

                        long dmg = com.hbm.items.machine.ItemLens.getLensDamage(stack);
                        dmg += self.watts;

                        // --- COMPAT: determine max safely ---
                        int max = com._test_ddddddd_.dfcfix.util._y_g_y_l_n_n_m_s_q_j_d_s_l_s_r_b_d_ddddddddd_.getMaxDamageSafe(stack.getItem(), stack);//////////////////////////////////////////////////////////////////////.java//LensCompat.getMaxDamageSafe(stack.getItem(), stack);


                  //      int max = fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_.getMaxDamageSafe(lens, self.inventory.getStackInSlot(0));       /////////////////////////////////.java
                        
                        int max_22222_ = fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_.getMaxDamageSafe(lens, self.inventory.getStackInSlot(0));       /////////////////////////////////.java

                          
                        /*      Print diagnostics (three sources)    ////// 
                         * 
                         * 
                        */

                //        *、     */


                        ///////////////        */


                        try {
                            System.out.println("[DIAG] old_fjdkls_dddddd_= " + max_22222_);     //(lens != null ? lens.maxDamage : "N/A"));
                        } catch (Throwable ignored) {}




                        // Print diagnostics (three sources)
                        try {
                            System.out.println("[DIAG] lens.maxDamage (field) = " + (lens != null ? lens.maxDamage : "N/A"));
                        } catch (Throwable ignored) {}
                        try {
                            System.out.println("[DIAG] stack.getMaxDamage() = " + stack.getMaxDamage());
                        } catch (Throwable ignored) {}
                        try {
                            System.out.println("[DIAG] ItemLens.getLensDamage(stack) = " + com.hbm.items.machine.ItemLens.getLensDamage(stack));
                        } catch (Throwable ignored) {}

                        // safety: only remove if we have a positive max
                        if (max > 0) {
                            if (dmg >= max)
                                self.inventory.setStackInSlot(0, ItemStack.EMPTY);
                            else
                                com.hbm.items.machine.ItemLens.setLensDamage(stack, dmg);
                        } else {
                            // unknown max -> don't delete, just write back damage value
                            com.hbm.items.machine.ItemLens.setLensDamage(stack, dmg);
                            System.err.println("[CoreStab] Warning: unknown lens maxDamage (0). Skipping removal.");
                        }

                        break;
                    }

                    if (self.getWorld().getTileEntity(pos1) instanceof TileEntityCoreStabilizer)
                        continue;

                    if (self.getWorld().getBlockState(pos1).getBlock() != Blocks.AIR)
                        break;
                }
            }

            PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(self.getPos(), self.beam, 0),
                    new TargetPoint(self.getWorld().provider.getDimension(), self.getPos().getX(), self.getPos().getY(), self.getPos().getZ(), 250));
        }
    }
}



                    /*

                    if (self.getWorld().getTileEntity(pos1) instanceof TileEntityCore) {

                        TileEntityCore core = (TileEntityCore) self.getWorld().getTileEntity(pos1);
                        core.field += (int) (self.watts * lens.fieldMod);
                        self.power -= (long) (demand * lens.drainMod);
                        self.beam = i;

                        long dmg = com.hbm.items.machine.ItemLens.getLensDamage(self.inventory.getStackInSlot(0));
                        dmg += self.watts;

                      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  int max = LensCompat.getMaxDamageSafe(lens, self.inventory.getStackInSlot(0));

               ////////////////////////////////         fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_.java


                        int max = fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_.getMaxDamageSafe(lens, self.inventory.getStackInSlot(0));       /////////////////////////////////.java
//导入了， 没引用也无所谓,(),    ,(),


                        System.out.println("lens.maxDamage = " + lens.maxDamage);
                        System.out.println("stack.getMaxDamage() = " + stack.getMaxDamage());
                        System.out.println("ItemLens.getLensDamage(stack) = " + ItemLens.getLensDamage(stack));

                      //  system.out.printIn("kill_all_human_dddddddddd_"+max);



                        System.out.printIn("kill_all_human_dddddddddd_"+max);

                        if (dmg >= max)
                            self.inventory.setStackInSlot(0, ItemStack.EMPTY);
                        else
                            com.hbm.items.machine.ItemLens.setLensDamage(self.inventory.getStackInSlot(0), dmg);

                        break;
                    }

                    if (self.getWorld().getTileEntity(pos1) instanceof TileEntityCoreStabilizer)
                        continue;

                    if (self.getWorld().getBlockState(pos1).getBlock() != Blocks.AIR)
                        break;
                }
            }

            PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(self.getPos(), self.beam, 0),
                    new TargetPoint(self.getWorld().provider.getDimension(), self.getPos().getX(), self.getPos().getY(), self.getPos().getZ(), 250));
        }
    }
}


























































*/