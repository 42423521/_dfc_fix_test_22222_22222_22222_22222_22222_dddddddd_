





package com._test_ddddddd_.dfcfix.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class jfsdkl_dddddddd_ extends CommandBase{//.java//DumpItemInfoCommand extends CommandBase {

    @Override
    public String getName() { return "dumpiteminfo"; }

    @Override
    public String getUsage(ICommandSender sender) { return "/dumpiteminfo [toSender]"; }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        boolean toSender = args.length > 0 && "true".equalsIgnoreCase(args[0]);
        // 在服务端线程里安全执行
        com._test_ddddddd_.dfcfix.util.DumpUtil.dumpItemInfo(sender, toSender);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                    b y d y d b y l,         d y b d l,(),                                                                                                                                                           ,(),


        if(toSender) {
            sender.sendMessage(new TextComponentString("[dumpiteminfo] 已执行，查看聊天或控制台"));
        } else {
            sender.sendMessage(new TextComponentString("[dumpiteminfo] 已执行，检查服务端控制台"));
        }
    }
}
