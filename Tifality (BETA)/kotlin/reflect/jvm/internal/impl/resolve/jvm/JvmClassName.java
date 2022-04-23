/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

public class JvmClassName {
    private final String internalName;
    private FqName fqName;

    @NotNull
    public static JvmClassName byInternalName(@NotNull String internalName) {
        if (internalName == null) {
            JvmClassName.$$$reportNull$$$0(0);
        }
        return new JvmClassName(internalName);
    }

    @NotNull
    public static JvmClassName byClassId(@NotNull ClassId classId) {
        if (classId == null) {
            JvmClassName.$$$reportNull$$$0(1);
        }
        FqName packageFqName = classId.getPackageFqName();
        String relativeClassName = classId.getRelativeClassName().asString().replace('.', '$');
        return packageFqName.isRoot() ? new JvmClassName(relativeClassName) : new JvmClassName(packageFqName.asString().replace('.', '/') + "/" + relativeClassName);
    }

    @NotNull
    public static JvmClassName byFqNameWithoutInnerClasses(@NotNull FqName fqName2) {
        if (fqName2 == null) {
            JvmClassName.$$$reportNull$$$0(2);
        }
        JvmClassName r = new JvmClassName(fqName2.asString().replace('.', '/'));
        r.fqName = fqName2;
        JvmClassName jvmClassName = r;
        if (jvmClassName == null) {
            JvmClassName.$$$reportNull$$$0(3);
        }
        return jvmClassName;
    }

    private JvmClassName(@NotNull String internalName) {
        if (internalName == null) {
            JvmClassName.$$$reportNull$$$0(5);
        }
        this.internalName = internalName;
    }

    @NotNull
    public FqName getFqNameForTopLevelClassMaybeWithDollars() {
        return new FqName(this.internalName.replace('/', '.'));
    }

    @NotNull
    public FqName getPackageFqName() {
        int lastSlash = this.internalName.lastIndexOf("/");
        if (lastSlash == -1) {
            FqName fqName2 = FqName.ROOT;
            if (fqName2 == null) {
                JvmClassName.$$$reportNull$$$0(7);
            }
            return fqName2;
        }
        return new FqName(this.internalName.substring(0, lastSlash).replace('/', '.'));
    }

    @NotNull
    public String getInternalName() {
        String string = this.internalName;
        if (string == null) {
            JvmClassName.$$$reportNull$$$0(8);
        }
        return string;
    }

    public String toString() {
        return this.internalName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.internalName.equals(((JvmClassName)o).internalName);
    }

    public int hashCode() {
        return this.internalName.hashCode();
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
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "internalName";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classId";
                break;
            }
            case 2: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fqName";
                break;
            }
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmClassName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmClassName";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "byFqNameWithoutInnerClasses";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getFqNameForClassNameWithoutDollars";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getPackageFqName";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getInternalName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "byInternalName";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "byClassId";
                break;
            }
            case 2: 
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "byFqNameWithoutInnerClasses";
                break;
            }
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                break;
            }
            case 5: {
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
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

