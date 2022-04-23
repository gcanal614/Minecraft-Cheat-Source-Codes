/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class UtfEncodingKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final byte[] stringsToBytes(@NotNull String[] strings) {
        int n;
        int n2;
        Intrinsics.checkNotNullParameter(strings, "strings");
        String[] $this$sumBy$iv = strings;
        boolean $i$f$sumBy = false;
        int sum$iv = 0;
        String[] stringArray = $this$sumBy$iv;
        int n3 = stringArray.length;
        for (n2 = 0; n2 < n3; ++n2) {
            void it;
            String element$iv;
            String string = element$iv = stringArray[n2];
            int n4 = sum$iv;
            boolean bl = false;
            int n5 = it.length();
            sum$iv = n4 + n5;
        }
        int resultLength = sum$iv;
        byte[] result2 = new byte[resultLength];
        int i = 0;
        String[] stringArray2 = strings;
        n2 = stringArray2.length;
        block1: for (n = 0; n < n2; ++n) {
            int element$iv = 0;
            String s = stringArray2[n];
            int n6 = s.length() - 1;
            if (element$iv > n6) continue;
            while (true) {
                void si;
                result2[i++] = (byte)s.charAt((int)si);
                if (si == n6) continue block1;
                ++si;
            }
        }
        boolean bl = i == result2.length;
        n = 0;
        if (_Assertions.ENABLED && !bl) {
            boolean bl2 = false;
            String string = "Should have reached the end";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return result2;
    }
}

