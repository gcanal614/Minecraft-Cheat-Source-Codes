/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api.utilities;

import java.lang.reflect.Field;

public class ReflectionUtility {
    private String className;
    private Class<?> clazz;

    public ReflectionUtility(String v1) {
        try {
            this.clazz = Class.forName(v1);
        }
        catch (ClassNotFoundException v2) {
            v2.printStackTrace();
        }
    }

    public void setStaticField(String a2, Object v1) throws NoSuchFieldException, IllegalAccessException {
        Field v2 = this.clazz.getDeclaredField(a2);
        v2.setAccessible(true);
        Field v3 = Field.class.getDeclaredField("modifiers");
        v3.setAccessible(true);
        v3.setInt(v2, v2.getModifiers() & 0xFFFFFFEF);
        v2.set(null, v1);
    }
}

