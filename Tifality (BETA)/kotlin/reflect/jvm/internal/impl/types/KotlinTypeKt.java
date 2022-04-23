/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

public final class KotlinTypeKt {
    public static final boolean isNullable(@NotNull KotlinType $this$isNullable) {
        Intrinsics.checkNotNullParameter($this$isNullable, "$this$isNullable");
        return TypeUtils.isNullableType($this$isNullable);
    }

    public static final boolean isError(@NotNull KotlinType $this$isError) {
        Intrinsics.checkNotNullParameter($this$isError, "$this$isError");
        UnwrappedType unwrappedType = $this$isError.unwrap();
        boolean bl = false;
        boolean bl2 = false;
        UnwrappedType unwrapped = unwrappedType;
        boolean bl3 = false;
        return unwrapped instanceof ErrorType || unwrapped instanceof FlexibleType && ((FlexibleType)unwrapped).getDelegate() instanceof ErrorType;
    }
}

