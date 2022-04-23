/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.minecraft.src.Config;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.ReflectorClass;

public class FieldLocatorType
implements IFieldLocator {
    private final ReflectorClass reflectorClass;
    private final Class targetFieldType;
    private final int targetFieldIndex;

    public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
        this.reflectorClass = reflectorClass;
        this.targetFieldType = targetFieldType;
        this.targetFieldIndex = targetFieldIndex;
    }

    @Override
    public Field getField() {
        Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        try {
            Field[] afield = oclass.getDeclaredFields();
            int i = 0;
            for (Field field : afield) {
                if (field.getType() != this.targetFieldType) continue;
                if (i == this.targetFieldIndex) {
                    field.setAccessible(true);
                    return field;
                }
                ++i;
            }
            Config.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
            return null;
        }
        catch (Throwable securityexception) {
            securityexception.printStackTrace();
            return null;
        }
    }
}

