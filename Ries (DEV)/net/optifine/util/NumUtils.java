/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.util;

public class NumUtils {
    public static float limit(float val, float min, float max) {
        return val < min ? min : Math.min(val, max);
    }

    public static int mod(int x, int y) {
        int i = x % y;
        if (i < 0) {
            i += y;
        }
        return i;
    }
}

