/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public final class DynamicTypesKt {
    public static final boolean isDynamic(@NotNull KotlinType $this$isDynamic) {
        Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
        return $this$isDynamic.unwrap() instanceof DynamicType;
    }
}

