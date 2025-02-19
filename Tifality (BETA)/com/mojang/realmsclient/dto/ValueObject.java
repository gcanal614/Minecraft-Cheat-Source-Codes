/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ValueObject {
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Field f2 : this.getClass().getFields()) {
            if (ValueObject.isStatic(f2)) continue;
            try {
                sb.append(f2.getName()).append("=").append(f2.get(this)).append(" ");
            }
            catch (IllegalAccessException ignore) {
                // empty catch block
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    private static boolean isStatic(Field f2) {
        return Modifier.isStatic(f2.getModifiers());
    }
}

