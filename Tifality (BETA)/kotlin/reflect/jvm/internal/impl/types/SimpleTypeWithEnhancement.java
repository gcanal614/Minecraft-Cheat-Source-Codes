/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class SimpleTypeWithEnhancement
extends DelegatingSimpleType
implements TypeWithEnhancement {
    @NotNull
    private final SimpleType delegate;
    @NotNull
    private final KotlinType enhancement;

    @Override
    @NotNull
    public UnwrappedType getOrigin() {
        return this.getDelegate();
    }

    @Override
    @NotNull
    public SimpleType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        UnwrappedType unwrappedType = TypeWithEnhancementKt.wrapEnhancement(this.getOrigin().replaceAnnotations(newAnnotations), this.getEnhancement());
        if (unwrappedType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return (SimpleType)unwrappedType;
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        UnwrappedType unwrappedType = TypeWithEnhancementKt.wrapEnhancement(this.getOrigin().makeNullableAsSpecified(newNullability), this.getEnhancement().unwrap().makeNullableAsSpecified(newNullability));
        if (unwrappedType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return (SimpleType)unwrappedType;
    }

    @Override
    @NotNull
    public SimpleTypeWithEnhancement replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new SimpleTypeWithEnhancement(delegate, this.getEnhancement());
    }

    @Override
    @NotNull
    public SimpleTypeWithEnhancement refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getDelegate());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new SimpleTypeWithEnhancement((SimpleType)kotlinType, kotlinTypeRefiner.refineType(this.getEnhancement()));
    }

    @Override
    @NotNull
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    @Override
    @NotNull
    public KotlinType getEnhancement() {
        return this.enhancement;
    }

    public SimpleTypeWithEnhancement(@NotNull SimpleType delegate, @NotNull KotlinType enhancement2) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(enhancement2, "enhancement");
        this.delegate = delegate;
        this.enhancement = enhancement2;
    }
}

