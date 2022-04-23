/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import org.jetbrains.annotations.Nullable;

public final class Ref<T> {
    @Nullable
    private T value;

    @Nullable
    public final T getValue() {
        return this.value;
    }

    public Ref(@Nullable T value) {
        this.value = value;
    }
}

