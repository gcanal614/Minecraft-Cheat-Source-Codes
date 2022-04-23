/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NumberWithRadix {
    @NotNull
    private final String number;
    private final int radix;

    public NumberWithRadix(@NotNull String number, int radix) {
        Intrinsics.checkNotNullParameter(number, "number");
        this.number = number;
        this.radix = radix;
    }

    @NotNull
    public final String component1() {
        return this.number;
    }

    public final int component2() {
        return this.radix;
    }

    @NotNull
    public String toString() {
        return "NumberWithRadix(number=" + this.number + ", radix=" + this.radix + ")";
    }

    public int hashCode() {
        String string = this.number;
        return (string != null ? string.hashCode() : 0) * 31 + this.radix;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NumberWithRadix)) break block3;
                NumberWithRadix numberWithRadix = (NumberWithRadix)object;
                if (!Intrinsics.areEqual(this.number, numberWithRadix.number) || this.radix != numberWithRadix.radix) break block3;
            }
            return true;
        }
        return false;
    }
}

