












































































package com._test_ddddddd_.dfcfix.util;

public final class _y_g_y_l_n_n_m_s_q_j_d_s_l_s_r_b_d_ddddddddd_{//.java//LensCompat {

    private static java.lang.reflect.Field field_maxDamage = null;
    private static boolean fieldTried = false;

    private _y_g_y_l_n_n_m_s_q_j_d_s_l_s_r_b_d_ddddddddd_(){}//LensCompat() {}

    public static int getMaxDamageSafe(net.minecraft.item.Item item, net.minecraft.item.ItemStack stack) {
        if (item == null || stack == null) return 0;

        if (!fieldTried) {
            fieldTried = true;
            try {
                try {
                    field_maxDamage = item.getClass().getField("maxDamage"); // public on subclass
                } catch (NoSuchFieldException e) {
                    try {
                        field_maxDamage = net.minecraft.item.Item.class.getField("maxDamage"); // fallback
                    } catch (NoSuchFieldException e2) {
                        field_maxDamage = null;
                    }
                }
                if (field_maxDamage != null) field_maxDamage.setAccessible(true);
            } catch (Throwable t) {
                t.printStackTrace();
                field_maxDamage = null;
            }
        }

        if (field_maxDamage != null) {
            try {
                Object val = field_maxDamage.get(item);
                if (val instanceof Number) return ((Number) val).intValue();
            } catch (Throwable t) {
                t.printStackTrace();
                field_maxDamage = null;
            }
        }

        // 运行时检查打印（不会引用未定义的 lens）
        try {
            System.out.println("[CHECK util] stack.getMaxDamage() = " + stack.getMaxDamage());
        } catch (Throwable ignored) {}

        // fallback to methods (various signatures)
        try {
            try {
                java.lang.reflect.Method m1 = item.getClass().getMethod("getMaxDamage", net.minecraft.item.ItemStack.class);
                Object r = m1.invoke(item, stack);
                if (r instanceof Number) return ((Number) r).intValue();
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m2 = item.getClass().getMethod("getMaxDamage");
                Object r = m2.invoke(item);
                if (r instanceof Number) return ((Number) r).intValue();
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m3 = net.minecraft.item.Item.class.getMethod("getMaxDamage", net.minecraft.item.ItemStack.class);
                Object r = m3.invoke(item, stack);
                if (r instanceof Number) return ((Number) r).intValue();
            } catch (NoSuchMethodException ignored) {}

            try {
                java.lang.reflect.Method m4 = net.minecraft.item.Item.class.getMethod("getMaxDamage");
                Object r = m4.invoke(item);
                if (r instanceof Number) return ((Number) r).intValue();
            } catch (NoSuchMethodException ignored) {}

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return 0;
    }
}
