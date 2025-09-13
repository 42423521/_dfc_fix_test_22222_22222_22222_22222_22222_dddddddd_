package com._test_ddddddd_.dfcfix.util;

public final class fjdkls_dddddddddddd_grd_fdjkl_fwk_kal_kah_kill_all_human_ddddddd_{//.java//LensCompat {
    private static java.lang.reflect.Field field_maxDamage = null;
    private static boolean fieldTried = false;

 //   System.out.println("[CHECK] lens.maxDamage = " + lens.maxDamage);
 //   System.out.println("[CHECK] stack.getMaxDamage() = " + stack.getMaxDamage());
  //  System.out.println("[CHECK] ItemLens.getLensDamage(stack) = " + com.hbm.items.machine.ItemLens.getLensDamage(stack));


    public static int getMaxDamageSafe(net.minecraft.item.Item item, net.minecraft.item.ItemStack stack) {
        if (!fieldTried) {
            fieldTried = true;
            try {
                // 先尝试子类/实例类的 public 字段
                field_maxDamage = item.getClass().getField("maxDamage");
            } catch (NoSuchFieldException e) {
                try {
                    // 再尝试父类 Item
                    field_maxDamage = net.minecraft.item.Item.class.getField("maxDamage");
                } catch (NoSuchFieldException e2) {
                    field_maxDamage = null;
                }
            }
            if (field_maxDamage != null) field_maxDamage.setAccessible(true);
        }

        if (field_maxDamage != null) {
            try {
                Object val = field_maxDamage.get(item);
                if (val instanceof Integer) return (Integer) val;
            } catch (Throwable t) {
                t.printStackTrace();
                field_maxDamage = null; // 以后不再尝试字段
            }
        }


    //    System.out.println("[CHECK] lens.maxDamage = " + lens.maxDamage);


        // 运行时检查打印（不会引用未定义的 lens）
        try {
            System.out.println("[CHECK util] stack.getMaxDamage() = " + stack.getMaxDamage());
        } catch (Throwable ignored) {}


        System.out.println("[CHECK] stack.getMaxDamage() = " + stack.getMaxDamage());
        System.out.println("[CHECK] ItemLens.getLensDamage(stack) = " + com.hbm.items.machine.ItemLens.getLensDamage(stack));


        // fallback 到方法调用（常见签名）
        try {
            try {
                java.lang.reflect.Method m1 = item.getClass().getMethod("getMaxDamage", net.minecraft.item.ItemStack.class);
                Object r = m1.invoke(item, stack);
                if (r instanceof Integer) return (Integer) r;
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m2 = item.getClass().getMethod("getMaxDamage");
                Object r = m2.invoke(item);
                if (r instanceof Integer) return (Integer) r;
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m3 = net.minecraft.item.Item.class.getMethod("getMaxDamage", net.minecraft.item.ItemStack.class);
                Object r = m3.invoke(item, stack);
                if (r instanceof Integer) return (Integer) r;
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m4 = net.minecraft.item.Item.class.getMethod("getMaxDamage");
                Object r = m4.invoke(item);
                if (r instanceof Integer) return (Integer) r;
            } catch (NoSuchMethodException ignored) {}

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return 0;
    }
}
