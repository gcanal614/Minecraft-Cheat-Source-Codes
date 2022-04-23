/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultValue;
import org.jetbrains.annotations.NotNull;

public final class Constant
extends JavaDefaultValue {
    @NotNull
    private final Object value;

    public Constant(@NotNull Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(null);
        this.value = value;
    }
}

