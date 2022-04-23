/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.name;

import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpecialNames {
    public static final Name NO_NAME_PROVIDED = Name.special("<no name provided>");
    public static final Name ROOT_PACKAGE = Name.special("<root package>");
    public static final Name DEFAULT_NAME_FOR_COMPANION_OBJECT = Name.identifier("Companion");
    public static final Name SAFE_IDENTIFIER_FOR_NO_NAME = Name.identifier("no_name_in_PSI_3d19d79d_1ba9_4cd0_b7f5_b46aa3cd5d40");
    public static final Name ANONYMOUS_FUNCTION = Name.special("<anonymous>");

    @NotNull
    public static Name safeIdentifier(@Nullable Name name) {
        Name name2 = name != null && !name.isSpecial() ? name : SAFE_IDENTIFIER_FOR_NO_NAME;
        if (name2 == null) {
            SpecialNames.$$$reportNull$$$0(0);
        }
        return name2;
    }

    public static boolean isSafeIdentifier(@NotNull Name name) {
        if (name == null) {
            SpecialNames.$$$reportNull$$$0(1);
        }
        return !name.asString().isEmpty() && !name.isSpecial();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
            case 1: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 2;
                break;
            }
            case 1: {
                n2 = 3;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/name/SpecialNames";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "safeIdentifier";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/name/SpecialNames";
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "isSafeIdentifier";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
            case 1: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

