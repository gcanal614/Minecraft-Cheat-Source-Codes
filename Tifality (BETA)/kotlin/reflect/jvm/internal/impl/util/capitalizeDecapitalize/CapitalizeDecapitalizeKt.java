/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class CapitalizeDecapitalizeKt {
    @NotNull
    public static final String decapitalizeSmartForCompiler(@NotNull String $this$decapitalizeSmartForCompiler, boolean asciiOnly) {
        Object v0;
        block4: {
            Intrinsics.checkNotNullParameter($this$decapitalizeSmartForCompiler, "$this$decapitalizeSmartForCompiler");
            CharSequence charSequence = $this$decapitalizeSmartForCompiler;
            boolean bl = false;
            if (charSequence.length() == 0 || !CapitalizeDecapitalizeKt.isUpperCaseCharAt($this$decapitalizeSmartForCompiler, 0, asciiOnly)) {
                return $this$decapitalizeSmartForCompiler;
            }
            if ($this$decapitalizeSmartForCompiler.length() == 1 || !CapitalizeDecapitalizeKt.isUpperCaseCharAt($this$decapitalizeSmartForCompiler, 1, asciiOnly)) {
                return asciiOnly ? CapitalizeDecapitalizeKt.decapitalizeAsciiOnly($this$decapitalizeSmartForCompiler) : StringsKt.decapitalize($this$decapitalizeSmartForCompiler);
            }
            Iterable $this$firstOrNull$iv = StringsKt.getIndices($this$decapitalizeSmartForCompiler);
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                int it = ((Number)element$iv).intValue();
                boolean bl2 = false;
                if (!(!CapitalizeDecapitalizeKt.isUpperCaseCharAt($this$decapitalizeSmartForCompiler, it, asciiOnly))) continue;
                v0 = element$iv;
                break block4;
            }
            v0 = null;
        }
        Integer n = v0;
        if (n == null) {
            return CapitalizeDecapitalizeKt.toLowerCase($this$decapitalizeSmartForCompiler, asciiOnly);
        }
        int secondWordStart = n - 1;
        StringBuilder stringBuilder = new StringBuilder();
        String string = $this$decapitalizeSmartForCompiler;
        int n2 = 0;
        boolean bl = false;
        String string2 = string.substring(n2, secondWordStart);
        Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        StringBuilder stringBuilder2 = stringBuilder.append(CapitalizeDecapitalizeKt.toLowerCase(string2, asciiOnly));
        string = $this$decapitalizeSmartForCompiler;
        n2 = 0;
        String string3 = string.substring(secondWordStart);
        Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
        return stringBuilder2.append(string3).toString();
    }

    private static final boolean isUpperCaseCharAt(String $this$isUpperCaseCharAt, int index, boolean asciiOnly) {
        boolean bl;
        char c = $this$isUpperCaseCharAt.charAt(index);
        if (asciiOnly) {
            char c2 = c;
            bl = 'A' <= c2 && 'Z' >= c2;
        } else {
            char c3 = c;
            boolean bl2 = false;
            bl = Character.isUpperCase(c3);
        }
        return bl;
    }

    private static final String toLowerCase(String string, boolean asciiOnly) {
        String string2;
        if (asciiOnly) {
            string2 = CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(string);
        } else {
            String string3 = string;
            boolean bl = false;
            String string4 = string3;
            if (string4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string5 = string4.toLowerCase();
            string2 = string5;
            Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).toLowerCase()");
        }
        return string2;
    }

    @NotNull
    public static final String capitalizeAsciiOnly(@NotNull String $this$capitalizeAsciiOnly) {
        String string;
        Intrinsics.checkNotNullParameter($this$capitalizeAsciiOnly, "$this$capitalizeAsciiOnly");
        CharSequence charSequence = $this$capitalizeAsciiOnly;
        char c = '\u0000';
        if (charSequence.length() == 0) {
            return $this$capitalizeAsciiOnly;
        }
        char c2 = $this$capitalizeAsciiOnly.charAt(0);
        c = c2;
        if ('a' <= c && 'z' >= c) {
            c = c2;
            boolean bl = false;
            c = Character.toUpperCase(c);
            String string2 = $this$capitalizeAsciiOnly;
            int n = 1;
            boolean bl2 = false;
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            string2 = string3;
            n = 0;
            string = String.valueOf(c) + string2;
        } else {
            string = $this$capitalizeAsciiOnly;
        }
        return string;
    }

    @NotNull
    public static final String decapitalizeAsciiOnly(@NotNull String $this$decapitalizeAsciiOnly) {
        String string;
        Intrinsics.checkNotNullParameter($this$decapitalizeAsciiOnly, "$this$decapitalizeAsciiOnly");
        CharSequence charSequence = $this$decapitalizeAsciiOnly;
        char c = '\u0000';
        if (charSequence.length() == 0) {
            return $this$decapitalizeAsciiOnly;
        }
        char c2 = $this$decapitalizeAsciiOnly.charAt(0);
        c = c2;
        if ('A' <= c && 'Z' >= c) {
            c = c2;
            boolean bl = false;
            c = Character.toLowerCase(c);
            String string2 = $this$decapitalizeAsciiOnly;
            int n = 1;
            boolean bl2 = false;
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            string2 = string3;
            n = 0;
            string = String.valueOf(c) + string2;
        } else {
            string = $this$decapitalizeAsciiOnly;
        }
        return string;
    }

    @NotNull
    public static final String toLowerCaseAsciiOnly(@NotNull String $this$toLowerCaseAsciiOnly) {
        Intrinsics.checkNotNullParameter($this$toLowerCaseAsciiOnly, "$this$toLowerCaseAsciiOnly");
        StringBuilder builder = new StringBuilder($this$toLowerCaseAsciiOnly.length());
        String string = $this$toLowerCaseAsciiOnly;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c;
            char c2 = string.charAt(i);
            char c3 = c2;
            if ('A' <= c3 && 'Z' >= c3) {
                c3 = c2;
                boolean bl = false;
                c = Character.toLowerCase(c3);
            } else {
                c = c2;
            }
            builder.append(c);
        }
        String string2 = builder.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "builder.toString()");
        return string2;
    }
}

