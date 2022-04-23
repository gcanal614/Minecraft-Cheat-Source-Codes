/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.name;

import org.jetbrains.annotations.NotNull;

public final class Name
implements Comparable<Name> {
    @NotNull
    private final String name;
    private final boolean special;

    private Name(@NotNull String name, boolean special) {
        if (name == null) {
            Name.$$$reportNull$$$0(0);
        }
        this.name = name;
        this.special = special;
    }

    @NotNull
    public String asString() {
        String string = this.name;
        if (string == null) {
            Name.$$$reportNull$$$0(1);
        }
        return string;
    }

    @NotNull
    public String getIdentifier() {
        if (this.special) {
            throw new IllegalStateException("not identifier: " + this);
        }
        String string = this.asString();
        if (string == null) {
            Name.$$$reportNull$$$0(2);
        }
        return string;
    }

    public boolean isSpecial() {
        return this.special;
    }

    @Override
    public int compareTo(Name that) {
        return this.name.compareTo(that.name);
    }

    @NotNull
    public static Name identifier(@NotNull String name) {
        if (name == null) {
            Name.$$$reportNull$$$0(3);
        }
        return new Name(name, false);
    }

    public static boolean isValidIdentifier(@NotNull String name) {
        if (name == null) {
            Name.$$$reportNull$$$0(4);
        }
        if (name.isEmpty() || name.startsWith("<")) {
            return false;
        }
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch != '.' && ch != '/' && ch != '\\') continue;
            return false;
        }
        return true;
    }

    @NotNull
    public static Name special(@NotNull String name) {
        if (name == null) {
            Name.$$$reportNull$$$0(5);
        }
        if (!name.startsWith("<")) {
            throw new IllegalArgumentException("special name must start with '<': " + name);
        }
        return new Name(name, true);
    }

    @NotNull
    public static Name guessByFirstCharacter(@NotNull String name) {
        if (name == null) {
            Name.$$$reportNull$$$0(6);
        }
        if (name.startsWith("<")) {
            return Name.special(name);
        }
        return Name.identifier(name);
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name)) {
            return false;
        }
        Name name1 = (Name)o;
        if (this.special != name1.special) {
            return false;
        }
        return this.name.equals(name1.name);
    }

    public int hashCode() {
        int result2 = this.name.hashCode();
        result2 = 31 * result2 + (this.special ? 1 : 0);
        return result2;
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
            case 1: 
            case 2: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 1: 
            case 2: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/name/Name";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/name/Name";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "asString";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getIdentifier";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: 
            case 2: {
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "identifier";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "isValidIdentifier";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "special";
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "guessByFirstCharacter";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 1: 
            case 2: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

