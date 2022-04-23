/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.name;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public final class FqNameUnsafe {
    private static final Name ROOT_NAME = Name.special("<root>");
    private static final Pattern SPLIT_BY_DOTS = Pattern.compile("\\.");
    private static final Function1<String, Name> STRING_TO_NAME = new Function1<String, Name>(){

        @Override
        public Name invoke(String name) {
            return Name.guessByFirstCharacter(name);
        }
    };
    @NotNull
    private final String fqName;
    private transient FqName safe;
    private transient FqNameUnsafe parent;
    private transient Name shortName;

    FqNameUnsafe(@NotNull String fqName2, @NotNull FqName safe) {
        if (fqName2 == null) {
            FqNameUnsafe.$$$reportNull$$$0(0);
        }
        if (safe == null) {
            FqNameUnsafe.$$$reportNull$$$0(1);
        }
        this.fqName = fqName2;
        this.safe = safe;
    }

    public FqNameUnsafe(@NotNull String fqName2) {
        if (fqName2 == null) {
            FqNameUnsafe.$$$reportNull$$$0(2);
        }
        this.fqName = fqName2;
    }

    private FqNameUnsafe(@NotNull String fqName2, FqNameUnsafe parent, Name shortName) {
        if (fqName2 == null) {
            FqNameUnsafe.$$$reportNull$$$0(3);
        }
        this.fqName = fqName2;
        this.parent = parent;
        this.shortName = shortName;
    }

    private void compute() {
        int lastDot = this.fqName.lastIndexOf(46);
        if (lastDot >= 0) {
            this.shortName = Name.guessByFirstCharacter(this.fqName.substring(lastDot + 1));
            this.parent = new FqNameUnsafe(this.fqName.substring(0, lastDot));
        } else {
            this.shortName = Name.guessByFirstCharacter(this.fqName);
            this.parent = FqName.ROOT.toUnsafe();
        }
    }

    @NotNull
    public String asString() {
        String string = this.fqName;
        if (string == null) {
            FqNameUnsafe.$$$reportNull$$$0(4);
        }
        return string;
    }

    public boolean isSafe() {
        return this.safe != null || this.asString().indexOf(60) < 0;
    }

    @NotNull
    public FqName toSafe() {
        if (this.safe != null) {
            FqName fqName2 = this.safe;
            if (fqName2 == null) {
                FqNameUnsafe.$$$reportNull$$$0(5);
            }
            return fqName2;
        }
        FqName fqName3 = this.safe = new FqName(this);
        if (fqName3 == null) {
            FqNameUnsafe.$$$reportNull$$$0(6);
        }
        return fqName3;
    }

    public boolean isRoot() {
        return this.fqName.isEmpty();
    }

    @NotNull
    public FqNameUnsafe parent() {
        if (this.parent != null) {
            FqNameUnsafe fqNameUnsafe = this.parent;
            if (fqNameUnsafe == null) {
                FqNameUnsafe.$$$reportNull$$$0(7);
            }
            return fqNameUnsafe;
        }
        if (this.isRoot()) {
            throw new IllegalStateException("root");
        }
        this.compute();
        FqNameUnsafe fqNameUnsafe = this.parent;
        if (fqNameUnsafe == null) {
            FqNameUnsafe.$$$reportNull$$$0(8);
        }
        return fqNameUnsafe;
    }

    @NotNull
    public FqNameUnsafe child(@NotNull Name name) {
        if (name == null) {
            FqNameUnsafe.$$$reportNull$$$0(9);
        }
        String childFqName = this.isRoot() ? name.asString() : this.fqName + "." + name.asString();
        return new FqNameUnsafe(childFqName, this, name);
    }

    @NotNull
    public Name shortName() {
        if (this.shortName != null) {
            Name name = this.shortName;
            if (name == null) {
                FqNameUnsafe.$$$reportNull$$$0(10);
            }
            return name;
        }
        if (this.isRoot()) {
            throw new IllegalStateException("root");
        }
        this.compute();
        Name name = this.shortName;
        if (name == null) {
            FqNameUnsafe.$$$reportNull$$$0(11);
        }
        return name;
    }

    @NotNull
    public Name shortNameOrSpecial() {
        if (this.isRoot()) {
            Name name = ROOT_NAME;
            if (name == null) {
                FqNameUnsafe.$$$reportNull$$$0(12);
            }
            return name;
        }
        Name name = this.shortName();
        if (name == null) {
            FqNameUnsafe.$$$reportNull$$$0(13);
        }
        return name;
    }

    @NotNull
    public List<Name> pathSegments() {
        List<Name> list = this.isRoot() ? Collections.emptyList() : ArraysKt.map(SPLIT_BY_DOTS.split(this.fqName), STRING_TO_NAME);
        if (list == null) {
            FqNameUnsafe.$$$reportNull$$$0(14);
        }
        return list;
    }

    public boolean startsWith(@NotNull Name segment) {
        if (segment == null) {
            FqNameUnsafe.$$$reportNull$$$0(15);
        }
        if (this.isRoot()) {
            return false;
        }
        int firstDot = this.fqName.indexOf(46);
        return this.fqName.regionMatches(0, segment.asString(), 0, firstDot == -1 ? this.fqName.length() : firstDot);
    }

    @NotNull
    public static FqNameUnsafe topLevel(@NotNull Name shortName) {
        if (shortName == null) {
            FqNameUnsafe.$$$reportNull$$$0(16);
        }
        return new FqNameUnsafe(shortName.asString(), FqName.ROOT.toUnsafe(), shortName);
    }

    @NotNull
    public String toString() {
        String string = this.isRoot() ? ROOT_NAME.asString() : this.fqName;
        if (string == null) {
            FqNameUnsafe.$$$reportNull$$$0(17);
        }
        return string;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FqNameUnsafe)) {
            return false;
        }
        FqNameUnsafe that = (FqNameUnsafe)o;
        return this.fqName.equals(that.fqName);
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
            case 8: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 17: {
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
            case 8: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 17: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fqName";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "safe";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/name/FqNameUnsafe";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "segment";
                break;
            }
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "shortName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/name/FqNameUnsafe";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "asString";
                break;
            }
            case 5: 
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "toSafe";
                break;
            }
            case 7: 
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "parent";
                break;
            }
            case 10: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "shortName";
                break;
            }
            case 12: 
            case 13: {
                objectArray = objectArray2;
                objectArray2[1] = "shortNameOrSpecial";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "pathSegments";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "toString";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 17: {
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "child";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "startsWith";
                break;
            }
            case 16: {
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
            case 8: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 17: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

