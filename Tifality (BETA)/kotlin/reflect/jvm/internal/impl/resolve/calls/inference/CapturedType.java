/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SubtypingRepresentatives;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class CapturedType
extends SimpleType
implements SubtypingRepresentatives,
CapturedTypeMarker {
    @NotNull
    private final TypeProjection typeProjection;
    @NotNull
    private final CapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;
    @NotNull
    private final Annotations annotations;

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        boolean bl = false;
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        MemberScope memberScope2 = ErrorUtils.createErrorScope("No member resolution should be done on captured type, it used only during constraint system resolution", true);
        Intrinsics.checkNotNullExpressionValue(memberScope2, "ErrorUtils.createErrorSc\u2026solution\", true\n        )");
        return memberScope2;
    }

    @Override
    @NotNull
    public KotlinType getSubTypeRepresentative() {
        SimpleType simpleType2 = TypeUtilsKt.getBuiltIns(this).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.nullableAnyType");
        return this.representative(Variance.OUT_VARIANCE, simpleType2);
    }

    @Override
    @NotNull
    public KotlinType getSuperTypeRepresentative() {
        SimpleType simpleType2 = TypeUtilsKt.getBuiltIns(this).getNothingType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.nothingType");
        return this.representative(Variance.IN_VARIANCE, simpleType2);
    }

    private final KotlinType representative(Variance variance, KotlinType kotlinType) {
        KotlinType kotlinType2 = this.typeProjection.getProjectionKind() == variance ? this.typeProjection.getType() : kotlinType;
        Intrinsics.checkNotNullExpressionValue(kotlinType2, "if (typeProjection.proje\u2026jection.type else default");
        return kotlinType2;
    }

    @Override
    public boolean sameTypeConstructor(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return this.getConstructor() == type2.getConstructor();
    }

    @Override
    @NotNull
    public String toString() {
        return "Captured(" + this.typeProjection + ')' + (this.isMarkedNullable() ? "?" : "");
    }

    @Override
    @NotNull
    public CapturedType makeNullableAsSpecified(boolean newNullability) {
        if (newNullability == this.isMarkedNullable()) {
            return this;
        }
        return new CapturedType(this.typeProjection, this.getConstructor(), newNullability, this.getAnnotations());
    }

    @Override
    @NotNull
    public CapturedType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new CapturedType(this.typeProjection, this.getConstructor(), this.isMarkedNullable(), newAnnotations);
    }

    @Override
    @NotNull
    public CapturedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjection = this.typeProjection.refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjection, "typeProjection.refine(kotlinTypeRefiner)");
        return new CapturedType(typeProjection, this.getConstructor(), this.isMarkedNullable(), this.getAnnotations());
    }

    @Override
    @NotNull
    public CapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    public CapturedType(@NotNull TypeProjection typeProjection, @NotNull CapturedTypeConstructor constructor, boolean isMarkedNullable, @NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        this.typeProjection = typeProjection;
        this.constructor = constructor;
        this.isMarkedNullable = isMarkedNullable;
        this.annotations = annotations2;
    }

    public /* synthetic */ CapturedType(TypeProjection typeProjection, CapturedTypeConstructor capturedTypeConstructor, boolean bl, Annotations annotations2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            capturedTypeConstructor = new CapturedTypeConstructorImpl(typeProjection);
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            annotations2 = Annotations.Companion.getEMPTY();
        }
        this(typeProjection, capturedTypeConstructor, bl, annotations2);
    }
}

