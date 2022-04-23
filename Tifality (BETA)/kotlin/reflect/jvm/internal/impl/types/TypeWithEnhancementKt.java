/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleTypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeWithEnhancementKt {
    @Nullable
    public static final KotlinType getEnhancement(@NotNull KotlinType $this$getEnhancement) {
        Intrinsics.checkNotNullParameter($this$getEnhancement, "$this$getEnhancement");
        KotlinType kotlinType = $this$getEnhancement;
        return kotlinType instanceof TypeWithEnhancement ? ((TypeWithEnhancement)((Object)$this$getEnhancement)).getEnhancement() : null;
    }

    @NotNull
    public static final KotlinType unwrapEnhancement(@NotNull KotlinType $this$unwrapEnhancement) {
        Intrinsics.checkNotNullParameter($this$unwrapEnhancement, "$this$unwrapEnhancement");
        KotlinType kotlinType = TypeWithEnhancementKt.getEnhancement($this$unwrapEnhancement);
        if (kotlinType == null) {
            kotlinType = $this$unwrapEnhancement;
        }
        return kotlinType;
    }

    @NotNull
    public static final UnwrappedType inheritEnhancement(@NotNull UnwrappedType $this$inheritEnhancement, @NotNull KotlinType origin) {
        Intrinsics.checkNotNullParameter($this$inheritEnhancement, "$this$inheritEnhancement");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return TypeWithEnhancementKt.wrapEnhancement($this$inheritEnhancement, TypeWithEnhancementKt.getEnhancement(origin));
    }

    @NotNull
    public static final UnwrappedType wrapEnhancement(@NotNull UnwrappedType $this$wrapEnhancement, @Nullable KotlinType enhancement2) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter($this$wrapEnhancement, "$this$wrapEnhancement");
        if (enhancement2 == null) {
            return $this$wrapEnhancement;
        }
        UnwrappedType unwrappedType2 = $this$wrapEnhancement;
        if (unwrappedType2 instanceof SimpleType) {
            unwrappedType = new SimpleTypeWithEnhancement((SimpleType)$this$wrapEnhancement, enhancement2);
        } else if (unwrappedType2 instanceof FlexibleType) {
            unwrappedType = new FlexibleTypeWithEnhancement((FlexibleType)$this$wrapEnhancement, enhancement2);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return unwrappedType;
    }
}

