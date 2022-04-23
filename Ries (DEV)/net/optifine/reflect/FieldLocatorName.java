/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.minecraft.src.Config;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.ReflectorClass;

public class FieldLocatorName
implements IFieldLocator {
    private final ReflectorClass reflectorClass;
    private final String targetFieldName;

    public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
        this.reflectorClass = reflectorClass;
        this.targetFieldName = targetFieldName;
    }

    @Override
    public Field getField() {
        Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        try {
            Field field = this.getDeclaredField(oclass, this.targetFieldName);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException var3) {
            Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
            return null;
        }
        catch (Throwable securityexception) {
            securityexception.printStackTrace();
            return null;
        }
    }

    private Field getDeclaredField(Class cls, String name) throws NoSuchFieldException {
        Field[] afield;
        for (Field field : afield = cls.getDeclaredFields()) {
            if (!field.getName().equals(name)) continue;
            return field;
        }
        if (cls == Object.class) {
            throw new NoSuchFieldException(name);
        }
        return this.getDeclaredField(cls.getSuperclass(), name);
    }
}

