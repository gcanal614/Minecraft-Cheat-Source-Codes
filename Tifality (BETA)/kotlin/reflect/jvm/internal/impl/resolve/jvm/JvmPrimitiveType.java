/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

public enum JvmPrimitiveType {
    BOOLEAN(PrimitiveType.BOOLEAN, "boolean", "Z", "java.lang.Boolean"),
    CHAR(PrimitiveType.CHAR, "char", "C", "java.lang.Character"),
    BYTE(PrimitiveType.BYTE, "byte", "B", "java.lang.Byte"),
    SHORT(PrimitiveType.SHORT, "short", "S", "java.lang.Short"),
    INT(PrimitiveType.INT, "int", "I", "java.lang.Integer"),
    FLOAT(PrimitiveType.FLOAT, "float", "F", "java.lang.Float"),
    LONG(PrimitiveType.LONG, "long", "J", "java.lang.Long"),
    DOUBLE(PrimitiveType.DOUBLE, "double", "D", "java.lang.Double");

    private static final Set<FqName> WRAPPERS_CLASS_NAMES;
    private static final Map<String, JvmPrimitiveType> TYPE_BY_NAME;
    private static final Map<PrimitiveType, JvmPrimitiveType> TYPE_BY_PRIMITIVE_TYPE;
    private static final Map<String, JvmPrimitiveType> TYPE_BY_DESC;
    private final PrimitiveType primitiveType;
    private final String name;
    private final String desc;
    private final FqName wrapperFqName;

    @NotNull
    public static JvmPrimitiveType get(@NotNull String name) {
        JvmPrimitiveType result2;
        if (name == null) {
            JvmPrimitiveType.$$$reportNull$$$0(1);
        }
        if ((result2 = TYPE_BY_NAME.get(name)) == null) {
            throw new AssertionError((Object)("Non-primitive type name passed: " + name));
        }
        JvmPrimitiveType jvmPrimitiveType = result2;
        if (jvmPrimitiveType == null) {
            JvmPrimitiveType.$$$reportNull$$$0(2);
        }
        return jvmPrimitiveType;
    }

    @NotNull
    public static JvmPrimitiveType get(@NotNull PrimitiveType type2) {
        if (type2 == null) {
            JvmPrimitiveType.$$$reportNull$$$0(3);
        }
        JvmPrimitiveType jvmPrimitiveType = TYPE_BY_PRIMITIVE_TYPE.get((Object)type2);
        if (jvmPrimitiveType == null) {
            JvmPrimitiveType.$$$reportNull$$$0(4);
        }
        return jvmPrimitiveType;
    }

    private JvmPrimitiveType(@NotNull PrimitiveType primitiveType, @NotNull String name, String desc, String wrapperClassName) {
        if (primitiveType == null) {
            JvmPrimitiveType.$$$reportNull$$$0(6);
        }
        if (name == null) {
            JvmPrimitiveType.$$$reportNull$$$0(7);
        }
        if (desc == null) {
            JvmPrimitiveType.$$$reportNull$$$0(8);
        }
        if (wrapperClassName == null) {
            JvmPrimitiveType.$$$reportNull$$$0(9);
        }
        this.primitiveType = primitiveType;
        this.name = name;
        this.desc = desc;
        this.wrapperFqName = new FqName(wrapperClassName);
    }

    @NotNull
    public PrimitiveType getPrimitiveType() {
        PrimitiveType primitiveType = this.primitiveType;
        if (primitiveType == null) {
            JvmPrimitiveType.$$$reportNull$$$0(10);
        }
        return primitiveType;
    }

    @NotNull
    public String getJavaKeywordName() {
        String string = this.name;
        if (string == null) {
            JvmPrimitiveType.$$$reportNull$$$0(11);
        }
        return string;
    }

    @NotNull
    public String getDesc() {
        String string = this.desc;
        if (string == null) {
            JvmPrimitiveType.$$$reportNull$$$0(12);
        }
        return string;
    }

    @NotNull
    public FqName getWrapperFqName() {
        FqName fqName2 = this.wrapperFqName;
        if (fqName2 == null) {
            JvmPrimitiveType.$$$reportNull$$$0(13);
        }
        return fqName2;
    }

    static {
        WRAPPERS_CLASS_NAMES = new HashSet<FqName>();
        TYPE_BY_NAME = new HashMap<String, JvmPrimitiveType>();
        TYPE_BY_PRIMITIVE_TYPE = new EnumMap<PrimitiveType, JvmPrimitiveType>(PrimitiveType.class);
        TYPE_BY_DESC = new HashMap<String, JvmPrimitiveType>();
        for (JvmPrimitiveType type2 : JvmPrimitiveType.values()) {
            WRAPPERS_CLASS_NAMES.add(type2.getWrapperFqName());
            TYPE_BY_NAME.put(type2.getJavaKeywordName(), type2);
            TYPE_BY_PRIMITIVE_TYPE.put(type2.getPrimitiveType(), type2);
            TYPE_BY_DESC.put(type2.getDesc(), type2);
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 2: 
            case 4: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 2: 
            case 4: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "className";
                break;
            }
            case 1: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 2: 
            case 4: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmPrimitiveType";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 5: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "desc";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "primitiveType";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "wrapperClassName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmPrimitiveType";
                break;
            }
            case 2: 
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "get";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getPrimitiveType";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getJavaKeywordName";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getDesc";
                break;
            }
            case 13: {
                objectArray = objectArray2;
                objectArray2[1] = "getWrapperFqName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "isWrapperClassName";
                break;
            }
            case 1: 
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "get";
                break;
            }
            case 2: 
            case 4: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "getByDesc";
                break;
            }
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 4: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

