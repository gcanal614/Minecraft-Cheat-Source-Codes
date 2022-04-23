/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.name;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNamesUtilKt$WhenMappings;
import kotlin.reflect.jvm.internal.impl.name.State;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FqNamesUtilKt {
    public static final boolean isSubpackageOf(@NotNull FqName $this$isSubpackageOf, @NotNull FqName packageName) {
        boolean bl;
        Intrinsics.checkNotNullParameter($this$isSubpackageOf, "$this$isSubpackageOf");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (Intrinsics.areEqual($this$isSubpackageOf, packageName)) {
            bl = true;
        } else if (packageName.isRoot()) {
            bl = true;
        } else {
            String string = $this$isSubpackageOf.asString();
            Intrinsics.checkNotNullExpressionValue(string, "this.asString()");
            String string2 = packageName.asString();
            Intrinsics.checkNotNullExpressionValue(string2, "packageName.asString()");
            bl = FqNamesUtilKt.isSubpackageOf(string, string2);
        }
        return bl;
    }

    private static final boolean isSubpackageOf(String subpackageNameStr, String packageNameStr) {
        return StringsKt.startsWith$default(subpackageNameStr, packageNameStr, false, 2, null) && subpackageNameStr.charAt(packageNameStr.length()) == '.';
    }

    @NotNull
    public static final FqName tail(@NotNull FqName $this$tail, @NotNull FqName prefix) {
        FqName fqName2;
        Intrinsics.checkNotNullParameter($this$tail, "$this$tail");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!FqNamesUtilKt.isSubpackageOf($this$tail, prefix) || prefix.isRoot()) {
            fqName2 = $this$tail;
        } else if (Intrinsics.areEqual($this$tail, prefix)) {
            FqName fqName3 = FqName.ROOT;
            fqName2 = fqName3;
            Intrinsics.checkNotNullExpressionValue(fqName3, "FqName.ROOT");
        } else {
            String string = $this$tail.asString();
            Intrinsics.checkNotNullExpressionValue(string, "asString()");
            String string2 = string;
            int n = prefix.asString().length() + 1;
            boolean bl = false;
            String string3 = string2;
            if (string3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).substring(startIndex)");
            fqName2 = new FqName(string4);
        }
        return fqName2;
    }

    public static final boolean isValidJavaFqName(@Nullable String qualifiedName2) {
        if (qualifiedName2 == null) {
            return false;
        }
        State state = State.BEGINNING;
        String string = qualifiedName2;
        int n = string.length();
        block4: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (FqNamesUtilKt$WhenMappings.$EnumSwitchMapping$0[state.ordinal()]) {
                case 1: 
                case 2: {
                    if (!Character.isJavaIdentifierPart(c)) {
                        return false;
                    }
                    state = State.MIDDLE;
                    continue block4;
                }
                case 3: {
                    if (c == '.') {
                        state = State.AFTER_DOT;
                        continue block4;
                    }
                    if (Character.isJavaIdentifierPart(c)) continue block4;
                    return false;
                }
            }
        }
        return state != State.AFTER_DOT;
    }
}

