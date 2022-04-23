/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal;

class Util {
    public static Object getEnumConstantByName(Class<? extends Enum<?>> enumClass, String name) {
        return Enum.valueOf(enumClass, name);
    }
}

