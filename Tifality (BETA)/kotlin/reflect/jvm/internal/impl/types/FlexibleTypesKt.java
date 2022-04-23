/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

public final class FlexibleTypesKt {
    public static final boolean isFlexible(@NotNull KotlinType $this$isFlexible) {
        Intrinsics.checkNotNullParameter($this$isFlexible, "$this$isFlexible");
        return $this$isFlexible.unwrap() instanceof FlexibleType;
    }

    @NotNull
    public static final FlexibleType asFlexibleType(@NotNull KotlinType $this$asFlexibleType) {
        Intrinsics.checkNotNullParameter($this$asFlexibleType, "$this$asFlexibleType");
        UnwrappedType unwrappedType = $this$asFlexibleType.unwrap();
        if (unwrappedType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
        }
        return (FlexibleType)unwrappedType;
    }

    @NotNull
    public static final SimpleType lowerIfFlexible(@NotNull KotlinType $this$lowerIfFlexible) {
        SimpleType simpleType2;
        Intrinsics.checkNotNullParameter($this$lowerIfFlexible, "$this$lowerIfFlexible");
        UnwrappedType unwrappedType = $this$lowerIfFlexible.unwrap();
        boolean bl = false;
        boolean bl2 = false;
        UnwrappedType $this$with = unwrappedType;
        boolean bl3 = false;
        UnwrappedType unwrappedType2 = $this$with;
        if (unwrappedType2 instanceof FlexibleType) {
            simpleType2 = ((FlexibleType)$this$with).getLowerBound();
        } else if (unwrappedType2 instanceof SimpleType) {
            simpleType2 = (SimpleType)$this$with;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return simpleType2;
    }

    @NotNull
    public static final SimpleType upperIfFlexible(@NotNull KotlinType $this$upperIfFlexible) {
        SimpleType simpleType2;
        Intrinsics.checkNotNullParameter($this$upperIfFlexible, "$this$upperIfFlexible");
        UnwrappedType unwrappedType = $this$upperIfFlexible.unwrap();
        boolean bl = false;
        boolean bl2 = false;
        UnwrappedType $this$with = unwrappedType;
        boolean bl3 = false;
        UnwrappedType unwrappedType2 = $this$with;
        if (unwrappedType2 instanceof FlexibleType) {
            simpleType2 = ((FlexibleType)$this$with).getUpperBound();
        } else if (unwrappedType2 instanceof SimpleType) {
            simpleType2 = (SimpleType)$this$with;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return simpleType2;
    }
}

