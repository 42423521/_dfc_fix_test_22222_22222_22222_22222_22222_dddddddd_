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

import java.util.Arrays;
import java.util.List;

public class CommandDfcSetCore extends CommandBase {

    @Override
    public String getName() {
        return "dfcsetcore";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dfcsetcore set <x> <y> <z> <heatCap:int> <fuelMax:int> <disableBomb:true|false>\n"
             + "/dfcsetcore clear <x> <y> <z>   (删除自定义 NBT)";//气死了，脑瘫gpt
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("dfcset", "dfccore");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }

        String sub = args[0];
        if ("set".equalsIgnoreCase(sub)) {
            if (args.length != 7) {
                sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            try {
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int z = Integer.parseInt(args[3]);
                int heatCap = Integer.parseInt(args[4]);
                int fuelMax = Integer.parseInt(args[5]);
                boolean disableBomb = Boolean.parseBoolean(args[6]);

                World world = sender.getEntityWorld();
                BlockPos pos = new BlockPos(x, y, z);
                if (!world.isBlockLoaded(pos)) {
                    sender.sendMessage(new TextComponentString("Chunk not loaded or invalid position."));
                    return;
                }

                TileEntity te = world.getTileEntity(pos);
                if (!(te instanceof TileEntityCore)) {
                    sender.sendMessage(new TextComponentString("Tile at " + x + "," + y + "," + z + " is not a DFC core."));
                    return;
                }

                // 使用 getTileData() 存自定义持久数据（1.12 安全做法）
                NBTTagCompound data = te.getTileData();
                data.setInteger("heatCap", heatCap);
                data.setInteger("fuelMax", fuelMax);
                data.setBoolean("disableBomb", disableBomb);

                te.markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                sender.sendMessage(new TextComponentString("DFC core NBT updated at " + x + "," + y + "," + z
                        + " -> heatCap=" + heatCap + " fuelMax=" + fuelMax + " disableBomb=" + disableBomb));
            } catch (NumberFormatException ex) {
                sender.sendMessage(new TextComponentString("Number format error: " + ex.getMessage()));
            } catch (Exception ex) {
                sender.sendMessage(new TextComponentString("Error: " + ex.getMessage()));
            }
        } else if ("clear".equalsIgnoreCase(sub)) {
            if (args.length != 4) {
                sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            try {
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int z = Integer.parseInt(args[3]);

                World world = sender.getEntityWorld();
                BlockPos pos = new BlockPos(x, y, z);
                TileEntity te = world.getTileEntity(pos);
                if (!(te instanceof TileEntityCore)) {
                    sender.sendMessage(new TextComponentString("Tile at " + x + "," + y + "," + z + " is not a DFC core."));
                    return;
                }

                NBTTagCompound data = te.getTileData();
                data.removeTag("heatCap");
                data.removeTag("fuelMax");
                data.removeTag("disableBomb");

                te.markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                sender.sendMessage(new TextComponentString("Cleared DFC custom NBT at " + x + "," + y + "," + z));
            } catch (Exception ex) {
                sender.sendMessage(new TextComponentString("Error: " + ex.getMessage()));
            }
        } else {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // 需要 op
    }
}
