









package com._test_ddddddd_.dfcfix;           //.debug;

import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.items.machine.ItemLens;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class _lx_s_q_j_dddddddd_{//.java//StabilizerDebug {

 ///////////////////////////////////////////////////////////////////////////////////   stabilizer v b,(), ,(),       s v ,(),             ,(),

    public static void logLens(TileEntityCoreStabilizer stabilizer) {

        System.out.println("[logLens] called, s_="+stabilizer);       //te);              fdsjfksdjl           //te=" + stabilizer);//te);

        ItemStack stack = stabilizer.inventory.getStackInSlot(0);

        if(stack == null || stack.isEmpty()) {
            System.out.println("[Debug] 槽为空");
            return;
        }

     //   if(stack.getItem() instanceof ItemLens lens) {


        if(stack.getItem() instanceof ItemLens) {
            ItemLens lens = (ItemLens) stack.getItem();
            // 这里继续使用 lens
/////////////////////////////////////////////////////////////////////}


            // 确保 NBT 初始化
            if(stack.getTagCompound() == null) {
                stack.setTagCompound(new NBTTagCompound());
                System.out.println("[Debug] NBT为空，已初始化");
            }

            long dmg = ItemLens.getLensDamage(stack);
            System.out.println("[Debug] 透镜: " + lens + ", maxDamage=" + lens.maxDamage + ", 当前耐久=" + dmg);

        } else {         //狗日的，      gsdsj,(),                                                                                                                                                               ,(),
            System.out.println("[Debug] 槽内不是透镜: " + stack.getItem());
        }
    }
}
