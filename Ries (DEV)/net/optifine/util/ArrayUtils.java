/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.util;

public class ArrayUtils {
    public static boolean contains(Object[] arr, Object val) {
        if (arr != null) {
            for (Object object : arr) {
                if (object != val) continue;
                return true;
            }
        }
        return false;
    }
}

