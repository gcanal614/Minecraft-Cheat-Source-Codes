/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StorageKt {
    @NotNull
    public static final <T> T getValue(@NotNull NotNullLazyValue<? extends T> $this$getValue, @Nullable Object _this, @NotNull KProperty<?> p) {
        Intrinsics.checkNotNullParameter($this$getValue, "$this$getValue");
        Intrinsics.checkNotNullParameter(p, "p");
        return (T)$this$getValue.invoke();
    }

    @Nullable
    public static final <T> T getValue(@NotNull NullableLazyValue<? extends T> $this$getValue, @Nullable Object _this, @NotNull KProperty<?> p) {
        Intrinsics.checkNotNullParameter($this$getValue, "$this$getValue");
        Intrinsics.checkNotNullParameter(p, "p");
        return (T)$this$getValue.invoke();
    }
}

