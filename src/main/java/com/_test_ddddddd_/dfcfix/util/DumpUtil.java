package com._test_ddddddd_.dfcfix.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DumpUtil {

    public static void dumpItemInfo(ICommandSender sender, boolean toSender) {
        try {
            Class<?> itemClass = net.minecraft.item.Item.class;
            Class<?> itemLensClass = null;
            try {
                itemLensClass = Class.forName("com.hbm.items.machine.ItemLens");
            } catch (ClassNotFoundException cnfe) {
                out(sender, toSender, "[dump] ItemLens class not found: " + cnfe.getMessage());
            }

            Object codeSource = itemClass.getProtectionDomain() != null && itemClass.getProtectionDomain().getCodeSource() != null
                    ? itemClass.getProtectionDomain().getCodeSource().getLocation()
                    : "unknown";
            out(sender, toSender, "[dump] Item.class location: " + codeSource);

            out(sender, toSender, "[dump] Fields of Item:");
            for (Field f : itemClass.getDeclaredFields()) {
                out(sender, toSender, "  field: " + f.getName() + " (" + f.getType().getName() + "), modifiers=" + java.lang.reflect.Modifier.toString(f.getModifiers()));
            }

            out(sender, toSender, "[dump] Methods of Item (filter 'max'):");
            for (Method m : itemClass.getDeclaredMethods()) {
                if (m.getName().toLowerCase().contains("max")) {
                    out(sender, toSender, "  method: " + m.toString());
                }
            }

            if (itemLensClass != null) {
                Object lensSource = itemLensClass.getProtectionDomain() != null && itemLensClass.getProtectionDomain().getCodeSource() != null
                        ? itemLensClass.getProtectionDomain().getCodeSource().getLocation()
                        : "unknown";
                out(sender, toSender, "[dump] ItemLens.class location: " + lensSource);
                out(sender, toSender, "[dump] Fields of ItemLens:");
                for (Field f : itemLensClass.getDeclaredFields()) {
                    out(sender, toSender, "  field: " + f.getName());
                }
                out(sender, toSender, "[dump] Methods of ItemLens (search 'max'):");
                for (Method m : itemLensClass.getDeclaredMethods()) {
                    if (m.getName().toLowerCase().contains("max")) out(sender, toSender, "  " + m);
                }
            }

            // 试探性读取 public field Item.maxDamage
            try {
                Field md = itemClass.getField("maxDamage");
                out(sender, toSender, "[dump] Found public field Item.maxDamage: " + md);
            } catch (NoSuchFieldException nsf) {
                out(sender, toSender, "[dump] Item.maxDamage NOT found (NoSuchFieldException)");
            }

            // 如果正在世界里，尝试访问玩家面前方块（可选）或典型的 TileEntityCoreStabilizer 插槽检测（演示）
            try {
                // 这里我们尝试打印所有在线玩家第一位视线附近 TileEntity（保守）
                if (sender != null && sender.getEntityWorld() != null) {
                    // find a stabilizer at sender pos->offset example (你也可以改成读命令参数坐标)
                    int x = sender.getPosition().getX();
                    int y = sender.getPosition().getY();
                    int z = sender.getPosition().getZ();
                    // 仅作为示例：检查手上物品/附近方块
                    ItemStack held = sender.getEntityWorld().getPlayerEntityByName(sender.getName()) != null
                            ? sender.getEntityWorld().getPlayerEntityByName(sender.getName()).getHeldItemMainhand()
                            : ItemStack.EMPTY;
                    out(sender, toSender, "[dump] " + (held.isEmpty() ? "no held item" : "held: " + held.getItem().getClass().getName()));
                }
            } catch (Throwable t) {
                out(sender, toSender, "[dump] probe failed: " + t.getClass().getName() + " - " + t.getMessage());
            }

            out(sender, toSender, "[dump] 完成。若需要更详细的反编译证据，请把上面的 class location 解压并查看 Item.class 的字节码/反编译结果。");

        } catch (Throwable t) {
            t.printStackTrace();
            if (toSender && sender != null)
                sender.sendMessage(new TextComponentString("[dump] err: " + t.getClass().getName() + " - " + t.getMessage()));
        }
    }

    private static void out(ICommandSender sender, boolean toSender, String line) {
        System.out.println(line);
        if (toSender && sender != null)
            sender.sendMessage(new TextComponentString(line));
    }
}
