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
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class AbbreviatedType
extends DelegatingSimpleType {
    @NotNull
    private final SimpleType delegate;
    @NotNull
    private final SimpleType abbreviation;

    @NotNull
    public final SimpleType getExpandedType() {
        return this.getDelegate();
    }

    @Override
    @NotNull
    public AbbreviatedType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new AbbreviatedType(this.getDelegate().replaceAnnotations(newAnnotations), this.abbreviation);
    }

    @Override
    @NotNull
    public AbbreviatedType makeNullableAsSpecified(boolean newNullability) {
        return new AbbreviatedType(this.getDelegate().makeNullableAsSpecified(newNullability), this.abbreviation.makeNullableAsSpecified(newNullability));
    }

    @Override
    @NotNull
    public AbbreviatedType replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new AbbreviatedType(delegate, this.abbreviation);
    }

    @Override
    @NotNull
    public AbbreviatedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getDelegate());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        KotlinType kotlinType2 = kotlinTypeRefiner.refineType(this.abbreviation);
        if (kotlinType2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new AbbreviatedType((SimpleType)kotlinType, (SimpleType)kotlinType2);
    }

    @Override
    @NotNull
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    @NotNull
    public final SimpleType getAbbreviation() {
        return this.abbreviation;
    }

    public AbbreviatedType(@NotNull SimpleType delegate, @NotNull SimpleType abbreviation) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(abbreviation, "abbreviation");
        this.delegate = delegate;
        this.abbreviation = abbreviation;
    }
}

