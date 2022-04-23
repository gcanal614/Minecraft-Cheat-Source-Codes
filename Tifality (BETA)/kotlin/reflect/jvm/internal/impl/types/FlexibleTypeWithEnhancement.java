/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class FlexibleTypeWithEnhancement
extends FlexibleType
implements TypeWithEnhancement {
    @NotNull
    private final FlexibleType origin;
    @NotNull
    private final KotlinType enhancement;

    @Override
    @NotNull
    public UnwrappedType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return TypeWithEnhancementKt.wrapEnhancement(this.getOrigin().replaceAnnotations(newAnnotations), this.getEnhancement());
    }

    @Override
    @NotNull
    public UnwrappedType makeNullableAsSpecified(boolean newNullability) {
        return TypeWithEnhancementKt.wrapEnhancement(this.getOrigin().makeNullableAsSpecified(newNullability), this.getEnhancement().unwrap().makeNullableAsSpecified(newNullability));
    }

    @Override
    @NotNull
    public String render(@NotNull DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        if (options.getEnhancedTypes()) {
            return renderer.renderType(this.getEnhancement());
        }
        return this.getOrigin().render(renderer, options);
    }

    @Override
    @NotNull
    public SimpleType getDelegate() {
        return this.getOrigin().getDelegate();
    }

    @Override
    @NotNull
    public FlexibleTypeWithEnhancement refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getOrigin());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
        }
        return new FlexibleTypeWithEnhancement((FlexibleType)kotlinType, kotlinTypeRefiner.refineType(this.getEnhancement()));
    }

    @Override
    @NotNull
    public FlexibleType getOrigin() {
        return this.origin;
    }

    @Override
    @NotNull
    public KotlinType getEnhancement() {
        return this.enhancement;
    }

    public FlexibleTypeWithEnhancement(@NotNull FlexibleType origin, @NotNull KotlinType enhancement2) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(enhancement2, "enhancement");
        super(origin.getLowerBound(), origin.getUpperBound());
        this.origin = origin;
        this.enhancement = enhancement2;
    }
}

