/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;

public class ReflectorRaw {
    public static Field getField(Class cls, Class fieldType) {
        try {
            Field[] afield;
            for (Field field : afield = cls.getDeclaredFields()) {
                if (field.getType() != fieldType) continue;
                field.setAccessible(true);
                return field;
            }
            return null;
        }
        catch (Exception var5) {
            return null;
        }
    }

    public static Field[] getFields(Class cls, Class fieldType) {
        try {
            Field[] afield = cls.getDeclaredFields();
            return ReflectorRaw.getFields(afield, fieldType);
        }
        catch (Exception var3) {
            return null;
        }
    }

    public static Field[] getFields(Field[] fields, Class fieldType) {
        try {
            ArrayList<Field> list = new ArrayList<Field>();
            for (Field field : fields) {
                if (field.getType() != fieldType) continue;
                field.setAccessible(true);
                list.add(field);
            }
            return list.toArray(new Field[0]);
        }
        catch (Exception var5) {
            return null;
        }
    }

    public static Field[] getFieldsAfter(Class cls, Field field, Class fieldType) {
        try {
            Field[] afield = cls.getDeclaredFields();
            List<Field> list = Arrays.asList(afield);
            int i = list.indexOf(field);
            if (i < 0) {
                return new Field[0];
            }
            List<Field> list1 = list.subList(i + 1, list.size());
            Field[] afield1 = list1.toArray(new Field[0]);
            return ReflectorRaw.getFields(afield1, fieldType);
        }
        catch (Exception var8) {
            return null;
        }
    }

    public static Field getField(Class cls, Class fieldType, int index) {
        Field[] afield = ReflectorRaw.getFields(cls, fieldType);
        return index >= 0 && index < Objects.requireNonNull(afield).length ? afield[index] : null;
    }

    public static Object getFieldValue(Object obj, Class cls, Class fieldType, int index) {
        ReflectorField reflectorfield = ReflectorRaw.getReflectorField(cls, fieldType, index);
        return reflectorfield == null ? null : (!reflectorfield.exists() ? null : Reflector.getFieldValue(obj, reflectorfield));
    }

    public static ReflectorField getReflectorField(Class cls, Class fieldType) {
        Field field = ReflectorRaw.getField(cls, fieldType);
        if (field == null) {
            return null;
        }
        ReflectorClass reflectorclass = new ReflectorClass(cls);
        return new ReflectorField(reflectorclass, field.getName());
    }

    public static ReflectorField getReflectorField(Class cls, Class fieldType, int index) {
        Field field = ReflectorRaw.getField(cls, fieldType, index);
        if (field == null) {
            return null;
        }
        ReflectorClass reflectorclass = new ReflectorClass(cls);
        return new ReflectorField(reflectorclass, field.getName());
    }
}

