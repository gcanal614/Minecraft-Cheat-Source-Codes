/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class StarProjectionForAbsentTypeParameter
extends TypeProjectionBase {
    private final KotlinType nullableAnyType;

    @Override
    public boolean isStarProjection() {
        return true;
    }

    @Override
    @NotNull
    public Variance getProjectionKind() {
        return Variance.OUT_VARIANCE;
    }

    @Override
    @NotNull
    public KotlinType getType() {
        return this.nullableAnyType;
    }

    @Override
    @NotNull
    public TypeProjection refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public StarProjectionForAbsentTypeParameter(@NotNull KotlinBuiltIns kotlinBuiltIns) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
        SimpleType simpleType2 = kotlinBuiltIns.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "kotlinBuiltIns.nullableAnyType");
        this.nullableAnyType = simpleType2;
    }
}

