package com._test_ddddddd_.dfcfix.command;

import com._test_ddddddd_.dfcfix.ExplosionControl;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandDfcFix extends CommandBase {

    @Override
    public String getName() {
        return "dfcfix";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dfcfix toggleexplosion - toggle explosion blocking";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 0 && "toggleexplosion".equalsIgnoreCase(args[0])) {
            ExplosionControl.disableExplosions = !ExplosionControl.disableExplosions;
            sender.sendMessage(new TextComponentString("Explosion disable = " + ExplosionControl.disableExplosions));
        } else {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
