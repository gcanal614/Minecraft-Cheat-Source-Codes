/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public enum PrimitiveType {
    BOOLEAN("Boolean"),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT("Float"),
    LONG("Long"),
    DOUBLE("Double");

    public static final Set<PrimitiveType> NUMBER_TYPES;
    private final Name typeName;
    private final Name arrayTypeName;
    private FqName typeFqName = null;
    private FqName arrayTypeFqName = null;

    private PrimitiveType(String typeName) {
        this.typeName = Name.identifier(typeName);
        this.arrayTypeName = Name.identifier(typeName + "Array");
    }

    @NotNull
    public Name getTypeName() {
        Name name = this.typeName;
        if (name == null) {
            PrimitiveType.$$$reportNull$$$0(0);
        }
        return name;
    }

    @NotNull
    public FqName getTypeFqName() {
        if (this.typeFqName != null) {
            FqName fqName2 = this.typeFqName;
            if (fqName2 == null) {
                PrimitiveType.$$$reportNull$$$0(1);
            }
            return fqName2;
        }
        FqName fqName3 = this.typeFqName = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(this.typeName);
        if (fqName3 == null) {
            PrimitiveType.$$$reportNull$$$0(2);
        }
        return fqName3;
    }

    @NotNull
    public Name getArrayTypeName() {
        Name name = this.arrayTypeName;
        if (name == null) {
            PrimitiveType.$$$reportNull$$$0(3);
        }
        return name;
    }

    @NotNull
    public FqName getArrayTypeFqName() {
        if (this.arrayTypeFqName != null) {
            FqName fqName2 = this.arrayTypeFqName;
            if (fqName2 == null) {
                PrimitiveType.$$$reportNull$$$0(4);
            }
            return fqName2;
        }
        FqName fqName3 = this.arrayTypeFqName = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(this.arrayTypeName);
        if (fqName3 == null) {
            PrimitiveType.$$$reportNull$$$0(5);
        }
        return fqName3;
    }

    static {
        NUMBER_TYPES = Collections.unmodifiableSet(EnumSet.of(CHAR, new PrimitiveType[]{BYTE, SHORT, INT, FLOAT, LONG, DOUBLE}));
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[2];
        objectArray2[0] = "kotlin/reflect/jvm/internal/impl/builtins/PrimitiveType";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeName";
                break;
            }
            case 1: 
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeFqName";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getArrayTypeName";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getArrayTypeFqName";
                break;
            }
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objectArray));
    }
}

