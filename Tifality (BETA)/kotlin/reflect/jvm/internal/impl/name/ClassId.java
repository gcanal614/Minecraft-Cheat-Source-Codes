/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.name;

import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClassId {
    private final FqName packageFqName;
    private final FqName relativeClassName;
    private final boolean local;

    @NotNull
    public static ClassId topLevel(@NotNull FqName topLevelFqName) {
        if (topLevelFqName == null) {
            ClassId.$$$reportNull$$$0(0);
        }
        return new ClassId(topLevelFqName.parent(), topLevelFqName.shortName());
    }

    public ClassId(@NotNull FqName packageFqName, @NotNull FqName relativeClassName, boolean local) {
        if (packageFqName == null) {
            ClassId.$$$reportNull$$$0(1);
        }
        if (relativeClassName == null) {
            ClassId.$$$reportNull$$$0(2);
        }
        this.packageFqName = packageFqName;
        assert (!relativeClassName.isRoot()) : "Class name must not be root: " + packageFqName + (local ? " (local)" : "");
        this.relativeClassName = relativeClassName;
        this.local = local;
    }

    public ClassId(@NotNull FqName packageFqName, @NotNull Name topLevelName) {
        if (packageFqName == null) {
            ClassId.$$$reportNull$$$0(3);
        }
        if (topLevelName == null) {
            ClassId.$$$reportNull$$$0(4);
        }
        this(packageFqName, FqName.topLevel(topLevelName), false);
    }

    @NotNull
    public FqName getPackageFqName() {
        FqName fqName2 = this.packageFqName;
        if (fqName2 == null) {
            ClassId.$$$reportNull$$$0(5);
        }
        return fqName2;
    }

    @NotNull
    public FqName getRelativeClassName() {
        FqName fqName2 = this.relativeClassName;
        if (fqName2 == null) {
            ClassId.$$$reportNull$$$0(6);
        }
        return fqName2;
    }

    @NotNull
    public Name getShortClassName() {
        Name name = this.relativeClassName.shortName();
        if (name == null) {
            ClassId.$$$reportNull$$$0(7);
        }
        return name;
    }

    public boolean isLocal() {
        return this.local;
    }

    @NotNull
    public ClassId createNestedClassId(@NotNull Name name) {
        if (name == null) {
            ClassId.$$$reportNull$$$0(8);
        }
        return new ClassId(this.getPackageFqName(), this.relativeClassName.child(name), this.local);
    }

    @Nullable
    public ClassId getOuterClassId() {
        FqName parent = this.relativeClassName.parent();
        return parent.isRoot() ? null : new ClassId(this.getPackageFqName(), parent, this.local);
    }

    public boolean isNestedClass() {
        return !this.relativeClassName.parent().isRoot();
    }

    @NotNull
    public FqName asSingleFqName() {
        if (this.packageFqName.isRoot()) {
            FqName fqName2 = this.relativeClassName;
            if (fqName2 == null) {
                ClassId.$$$reportNull$$$0(9);
            }
            return fqName2;
        }
        return new FqName(this.packageFqName.asString() + "." + this.relativeClassName.asString());
    }

    @NotNull
    public static ClassId fromString(@NotNull String string) {
        if (string == null) {
            ClassId.$$$reportNull$$$0(11);
        }
        return ClassId.fromString(string, false);
    }

    @NotNull
    public static ClassId fromString(@NotNull String string, boolean isLocal) {
        if (string == null) {
            ClassId.$$$reportNull$$$0(12);
        }
        String packageName = StringsKt.substringBeforeLast(string, '/', "").replace('/', '.');
        String className = StringsKt.substringAfterLast(string, '/', string);
        return new ClassId(new FqName(packageName), new FqName(className), isLocal);
    }

    @NotNull
    public String asString() {
        if (this.packageFqName.isRoot()) {
            String string = this.relativeClassName.asString();
            if (string == null) {
                ClassId.$$$reportNull$$$0(13);
            }
            return string;
        }
        String string = this.packageFqName.asString().replace('.', '/') + "/" + this.relativeClassName.asString();
        if (string == null) {
            ClassId.$$$reportNull$$$0(14);
        }
        return string;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ClassId id = (ClassId)o;
        return this.packageFqName.equals(id.packageFqName) && this.relativeClassName.equals(id.relativeClassName) && this.local == id.local;
    }

    public int hashCode() {
        int result2 = this.packageFqName.hashCode();
        result2 = 31 * result2 + this.relativeClassName.hashCode();
        result2 = 31 * result2 + Boolean.valueOf(this.local).hashCode();
        return result2;
    }

    public String toString() {
        return this.packageFqName.isRoot() ? "/" + this.asString() : this.asString();
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
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 13: 
            case 14: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 13: 
            case 14: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "topLevelFqName";
                break;
            }
            case 1: 
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "packageFqName";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "relativeClassName";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "topLevelName";
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 13: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/name/ClassId";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "segment";
                break;
            }
            case 11: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "string";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/name/ClassId";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getPackageFqName";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getRelativeClassName";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getShortClassName";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "asSingleFqName";
                break;
            }
            case 13: 
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "asString";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "topLevel";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 13: 
            case 14: {
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "createNestedClassId";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "startsWith";
                break;
            }
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "fromString";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 13: 
            case 14: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

