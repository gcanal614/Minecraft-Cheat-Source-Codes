/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinSpecialProperties;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PropertiesConventionUtilKt {
    @Nullable
    public static final Name propertyNameByGetMethodName(@NotNull Name methodName) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        Name name = PropertiesConventionUtilKt.propertyNameFromAccessorMethodName$default(methodName, "get", false, null, 12, null);
        if (name == null) {
            name = PropertiesConventionUtilKt.propertyNameFromAccessorMethodName$default(methodName, "is", false, null, 8, null);
        }
        return name;
    }

    @Nullable
    public static final Name propertyNameBySetMethodName(@NotNull Name methodName, boolean withIsPrefix) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return PropertiesConventionUtilKt.propertyNameFromAccessorMethodName$default(methodName, "set", false, withIsPrefix ? "is" : null, 4, null);
    }

    @NotNull
    public static final List<Name> propertyNamesBySetMethodName(@NotNull Name methodName) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return CollectionsKt.listOfNotNull(PropertiesConventionUtilKt.propertyNameBySetMethodName(methodName, false), PropertiesConventionUtilKt.propertyNameBySetMethodName(methodName, true));
    }

    private static final Name propertyNameFromAccessorMethodName(Name methodName, String prefix, boolean removePrefix, String addPrefix) {
        if (methodName.isSpecial()) {
            return null;
        }
        String string = methodName.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(string, "methodName.identifier");
        String identifier = string;
        if (!StringsKt.startsWith$default(identifier, prefix, false, 2, null)) {
            return null;
        }
        if (identifier.length() == prefix.length()) {
            return null;
        }
        char c = identifier.charAt(prefix.length());
        if ('a' <= c && 'z' >= c) {
            return null;
        }
        if (addPrefix != null) {
            c = '\u0000';
            boolean bl = false;
            if (_Assertions.ENABLED && !removePrefix) {
                boolean bl2 = false;
                String string2 = "Assertion failed";
                throw (Throwable)((Object)new AssertionError((Object)string2));
            }
            return Name.identifier(addPrefix + StringsKt.removePrefix(identifier, (CharSequence)prefix));
        }
        if (!removePrefix) {
            return methodName;
        }
        String name = CapitalizeDecapitalizeKt.decapitalizeSmartForCompiler(StringsKt.removePrefix(identifier, (CharSequence)prefix), true);
        if (!Name.isValidIdentifier(name)) {
            return null;
        }
        return Name.identifier(name);
    }

    static /* synthetic */ Name propertyNameFromAccessorMethodName$default(Name name, String string, boolean bl, String string2, int n, Object object) {
        if ((n & 4) != 0) {
            bl = true;
        }
        if ((n & 8) != 0) {
            string2 = null;
        }
        return PropertiesConventionUtilKt.propertyNameFromAccessorMethodName(name, string, bl, string2);
    }

    @NotNull
    public static final List<Name> getPropertyNamesCandidatesByAccessorName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String string = name.asString();
        Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
        String nameAsString = string;
        if (JvmAbi.isGetterName(nameAsString)) {
            return CollectionsKt.listOfNotNull(PropertiesConventionUtilKt.propertyNameByGetMethodName(name));
        }
        if (JvmAbi.isSetterName(nameAsString)) {
            return PropertiesConventionUtilKt.propertyNamesBySetMethodName(name);
        }
        return BuiltinSpecialProperties.INSTANCE.getPropertyNameCandidatesBySpecialGetterName(name);
    }
}

