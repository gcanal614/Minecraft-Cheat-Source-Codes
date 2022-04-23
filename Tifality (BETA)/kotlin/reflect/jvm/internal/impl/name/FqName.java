/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.name;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public final class FqName {
    public static final FqName ROOT = new FqName("");
    @NotNull
    private final FqNameUnsafe fqName;
    private transient FqName parent;

    public FqName(@NotNull String fqName2) {
        if (fqName2 == null) {
            FqName.$$$reportNull$$$0(1);
        }
        this.fqName = new FqNameUnsafe(fqName2, this);
    }

    public FqName(@NotNull FqNameUnsafe fqName2) {
        if (fqName2 == null) {
            FqName.$$$reportNull$$$0(2);
        }
        this.fqName = fqName2;
    }

    private FqName(@NotNull FqNameUnsafe fqName2, FqName parent) {
        if (fqName2 == null) {
            FqName.$$$reportNull$$$0(3);
        }
        this.fqName = fqName2;
        this.parent = parent;
    }

    @NotNull
    public String asString() {
        String string = this.fqName.asString();
        if (string == null) {
            FqName.$$$reportNull$$$0(4);
        }
        return string;
    }

    @NotNull
    public FqNameUnsafe toUnsafe() {
        FqNameUnsafe fqNameUnsafe = this.fqName;
        if (fqNameUnsafe == null) {
            FqName.$$$reportNull$$$0(5);
        }
        return fqNameUnsafe;
    }

    public boolean isRoot() {
        return this.fqName.isRoot();
    }

    @NotNull
    public FqName parent() {
        if (this.parent != null) {
            FqName fqName2 = this.parent;
            if (fqName2 == null) {
                FqName.$$$reportNull$$$0(6);
            }
            return fqName2;
        }
        if (this.isRoot()) {
            throw new IllegalStateException("root");
        }
        FqName fqName3 = this.parent = new FqName(this.fqName.parent());
        if (fqName3 == null) {
            FqName.$$$reportNull$$$0(7);
        }
        return fqName3;
    }

    @NotNull
    public FqName child(@NotNull Name name) {
        if (name == null) {
            FqName.$$$reportNull$$$0(8);
        }
        return new FqName(this.fqName.child(name), this);
    }

    @NotNull
    public Name shortName() {
        Name name = this.fqName.shortName();
        if (name == null) {
            FqName.$$$reportNull$$$0(9);
        }
        return name;
    }

    @NotNull
    public Name shortNameOrSpecial() {
        Name name = this.fqName.shortNameOrSpecial();
        if (name == null) {
            FqName.$$$reportNull$$$0(10);
        }
        return name;
    }

    @NotNull
    public List<Name> pathSegments() {
        List<Name> list = this.fqName.pathSegments();
        if (list == null) {
            FqName.$$$reportNull$$$0(11);
        }
        return list;
    }

    public boolean startsWith(@NotNull Name segment) {
        if (segment == null) {
            FqName.$$$reportNull$$$0(12);
        }
        return this.fqName.startsWith(segment);
    }

    @NotNull
    public static FqName topLevel(@NotNull Name shortName) {
        if (shortName == null) {
            FqName.$$$reportNull$$$0(13);
        }
        return new FqName(FqNameUnsafe.topLevel(shortName));
    }

    public String toString() {
        return this.fqName.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FqName)) {
            return false;
        }
        FqName otherFqName = (FqName)o;
        return this.fqName.equals(otherFqName.fqName);
    }

    public int hashCode() {
        return this.fqName.hashCode();
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
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "names";
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fqName";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/name/FqName";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "segment";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "shortName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/name/FqName";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "asString";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "toUnsafe";
                break;
            }
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "parent";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "shortName";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "shortNameOrSpecial";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "pathSegments";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "fromSegments";
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "child";
                break;
            }
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "startsWith";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "topLevel";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

