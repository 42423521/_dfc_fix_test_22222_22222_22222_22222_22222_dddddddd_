package com._test_ddddddd_.dfcfix.command;//.debug;

import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.items.machine.ItemLens;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com._test_ddddddd_.dfcfix._lx_s_q_j_dddddddd_;

public class _cm_d_t_e_c_s_ddddddd_ extends CommandBase{//  extends CommandBase//.java//CommandCheckStabilizer extends CommandBase {

    @Override
    public String getName() {
        return "checkstabilizer"; //c t

                                  //大 类                                                                                                                                                                                                                                                                                                                                                                                                             小类    dddd,(),  ,(),
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/checkstabilizer <x> <y> <z>"//;
            //+ "/dfcsetcore_2_ set_2_2_ <x> <y> <z> <heatCap:int>\n"//;

            + "/checkstabilizer_test_  <x> <y> <z>\n";// set_2_2_ <x> <y> <z> <heatCap:int>\n"


    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length != 3) {
            sender.sendMessage(getTextComponent("请提供坐标 x y z"));
            return;
        }

        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);

            World world = sender.getEntityWorld();
            BlockPos pos = new BlockPos(x, y, z);
     ///////////////////////////////////////////////////////////////////////////////////////       if(world.getTileEntity(pos) instanceof TileEntityCoreStabilizer stabilizer) {

            if(world.getTileEntity(pos) instanceof TileEntityCoreStabilizer) {
                TileEntityCoreStabilizer stabilizer = (TileEntityCoreStabilizer) world.getTileEntity(pos);

                ItemStack stack = stabilizer.inventory.getStackInSlot(0);
                sender.sendMessage(getTextComponent("=== Core Stabilizer 信息 ==="));
                sender.sendMessage(getTextComponent("Power: " + stabilizer.getPower()));
                sender.sendMessage(getTextComponent("Watts: " + stabilizer.watts));
                sender.sendMessage(getTextComponent("Beam: " + stabilizer.beam));
                sender.sendMessage(getTextComponent("是否开启: " + stabilizer.isOn));

            //    // 调用外部调试类
          //      StabilizerDebug.logLens(stabilizer, sender);



                   


           ////////////////////////////////////////////////////     _lx_s_q_j_dddddddd_.logLens(stabilizer);//, sender);//log_stabilizer, sender.//.java

                try {
                      // 可能会报错的代码
                    //int x = 1 / 0;  // 除零错误
                
                    _lx_s_q_j_dddddddd_.logLens(stabilizer);//, sender);//log_stabilizer, sender.//.java



                } catch (Exception b) {    //狗日的，还多了
                
                        // 这里相当于 Python 的 except
                    b.printStackTrace();  // 打印堆栈，不影响后续运行
                }






            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    _lx_s_q_j_dddddddd_.logLens(stabilizer, sender);                                                                 // );//, sender);//log_stabilizer, sender.//.java


///////////////////////////////////////////////////////////////////////////////////////////////////////////////                                                                                                       类原 没     查,(),                                                                                                                                           ,(),

                       //理论上不会失败，楼下闹尼玛,(),  ,(),



      //             import com._test_ddddddd_.dfcfix.debug.StabilizerDebug;
//import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
//
//@Mixin(TileEntityCoreStabilizer.class)
//public abstract class TileEntityCoreStabilizerMixin {
//
  //  @Inject(method = "update", at = @At("HEAD"))
  //  private void beforeUpdate(CallbackInfo ci) {
 //       // 调用外部调试类
//        StabilizerDebug.logLens((TileEntityCoreStabilizer)(Object)this);
 //   }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////}








                if(stack.isEmpty()) {
                    sender.sendMessage(getTextComponent("槽为空"));
                } else if(stack.getItem() instanceof ItemLens) {
                    ItemLens lens = (ItemLens) stack.getItem();//else if(stack.getItem() instanceof ItemLens lens) {





        //        else if(stack.getItem() instanceof ItemLens) {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    ItemLens lens = (ItemLens) stack.getItem();
                    








                    System.out.println("[shit_]"+lens.maxDamage);


                    sender.sendMessage(getTextComponent("maxDamage: " + lens.maxDamage));

               //     System.out.println("//[Debug] NBT为空，已初始化");



                    System.out.println("[shit_]"+lens.maxDamage);//"[DFCFIX] applyCapacityByReflection: no candidate field/setter for " + cls.getName());






                    if(stack.getTagCompound() == null)
                        stack.setTagCompound(new NBTTagCompound());

                    long dmg = ItemLens.getLensDamage(stack);
                    sender.sendMessage(getTextComponent("透镜: " + lens));
                    sender.sendMessage(getTextComponent("maxDamage: " + lens.maxDamage));
                    sender.sendMessage(getTextComponent("当前耐久: " + dmg));

                    _lx_s_q_j_dddddddd_.logLens(stabilizer);//, sender);//log_stabilizer, sender.//.java



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                           kill       all       human

                } else {
                    sender.sendMessage(getTextComponent("槽里的物品不是透镜: " + stack.getItem()));
                }

            } else {          //       闹尼玛        死全家,()<   ,(),        急尼玛，死全家，    再闹，       妈死了,()<         ,()<
                sender.sendMessage(getTextComponent("指定方块不是 Core Stabilizer"));
            }

        } catch(NumberFormatException e) {
            sender.sendMessage(getTextComponent("坐标必须是整数"));
        }
    }

    // 简化文本发送
    private net.minecraft.util.text.ITextComponent getTextComponent(String text) {
        return new net.minecraft.util.text.TextComponentString(text);
    }
}


//吵你妈，死全家,(),

//,(),