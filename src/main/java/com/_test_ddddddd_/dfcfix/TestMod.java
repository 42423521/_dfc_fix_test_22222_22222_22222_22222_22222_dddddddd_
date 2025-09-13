package com._test_ddddddd_.dfcfix;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = TestMod.MODID, name = TestMod.NAME, version = TestMod.VERSION, acceptableRemoteVersions = "*")
public class TestMod {
    public static final String MODID = "dfcfix";
    public static final String NAME = "DFC Fix";
    public static final String VERSION = "1.0";

    //信你个得

    //java内部是吧

                                          ////////////////////////////////////////////////////            不是gradle               就是                                 相对内部              附近开了多少JFK了就是打开链接发可是大家九分快乐健康就是打开链接发开始搭建框架可是登机口附近空手道解放就是打开链接发昆仑山大家看法就士大夫艰苦拉萨的即使对方考虑

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        System.out.println("DFC Fix mod init");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // Register command
        event.registerServerCommand(new com._test_ddddddd_.dfcfix.command.CommandDfcFix());

       


        event.registerServerCommand(new com._test_ddddddd_.dfcfix.command.CommandDfcSetCore());




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////CommandDfcSetCore.java




        event.registerServerCommand(new com._test_ddddddd_.dfcfix.command.c_d_s_c_ddddddddddddd_ddddddddddddddddddddd_());//.java//#CommandDfcFix());





         


//        event.register









        event.registerServerCommand(new com._test_ddddddd_.dfcfix.command._cm_d_t_e_c_s_ddddddd_());//.java());//.c_d_s_c_ddddddddddddd_ddddddddddddddddddddd_());//.java//#CommandDfcFix());






















        

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                                 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// event.registerServerCommand(new com._test_ddddddd_.dfcfix.command.DumpItemInfoCommand());





                















        event.registerServerCommand(new com._test_ddddddd_.dfcfix.command.jfsdkl_dddddddd_());//.java//.DumpItemInfoCommand());







    }
}
