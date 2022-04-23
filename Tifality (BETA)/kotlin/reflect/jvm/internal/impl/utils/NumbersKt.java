/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.utils.NumberWithRadix;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class NumbersKt {
    @NotNull
    public static final NumberWithRadix extractRadix(@NotNull String value) {
        NumberWithRadix numberWithRadix;
        Intrinsics.checkNotNullParameter(value, "value");
        if (StringsKt.startsWith$default(value, "0x", false, 2, null) || StringsKt.startsWith$default(value, "0X", false, 2, null)) {
            String string = value;
            int n = 2;
            boolean bl = false;
            String string2 = string.substring(n);
            Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.String).substring(startIndex)");
            numberWithRadix = new NumberWithRadix(string2, 16);
        } else if (StringsKt.startsWith$default(value, "0b", false, 2, null) || StringsKt.startsWith$default(value, "0B", false, 2, null)) {
            String string = value;
            int n = 2;
            boolean bl = false;
            String string3 = string.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            numberWithRadix = new NumberWithRadix(string3, 2);
        } else {
            numberWithRadix = new NumberWithRadix(value, 10);
        }
        return numberWithRadix;
    }
}

