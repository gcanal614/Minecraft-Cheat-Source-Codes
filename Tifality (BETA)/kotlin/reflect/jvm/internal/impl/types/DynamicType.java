/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class DynamicType
extends FlexibleType
implements DynamicTypeMarker {
    @NotNull
    private final Annotations annotations;

    @Override
    @NotNull
    public SimpleType getDelegate() {
        return this.getUpperBound();
    }

    @Override
    @NotNull
    public DynamicType makeNullableAsSpecified(boolean newNullability) {
        return this;
    }

    @Override
    public boolean isMarkedNullable() {
        return false;
    }

    @Override
    @NotNull
    public DynamicType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new DynamicType(TypeUtilsKt.getBuiltIns(this.getDelegate()), newAnnotations);
    }

    @Override
    @NotNull
    public String render(@NotNull DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        return "dynamic";
    }

    @Override
    @NotNull
    public DynamicType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    public DynamicType(@NotNull KotlinBuiltIns builtIns, @NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        SimpleType simpleType2 = builtIns.getNothingType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.nothingType");
        SimpleType simpleType3 = builtIns.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(simpleType3, "builtIns.nullableAnyType");
        super(simpleType2, simpleType3);
        this.annotations = annotations2;
    }
}

