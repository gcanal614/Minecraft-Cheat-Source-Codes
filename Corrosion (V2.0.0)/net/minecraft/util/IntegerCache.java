/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class IntegerCache {
    private static final Integer[] field_181757_a = new Integer[65535];

    public static Integer func_181756_a(int p_181756_0_) {
        return p_181756_0_ > 0 && p_181756_0_ < field_181757_a.length ? field_181757_a[p_181756_0_] : Integer.valueOf(p_181756_0_);
    }

    static {
        int j2 = field_181757_a.length;
        for (int i2 = 0; i2 < j2; ++i2) {
            IntegerCache.field_181757_a[i2] = i2;
        }
    }
}

