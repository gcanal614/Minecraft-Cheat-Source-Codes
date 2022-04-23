/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public abstract class DelegatingSimpleType
extends SimpleType {
    @NotNull
    protected abstract SimpleType getDelegate();

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.getDelegate().getAnnotations();
    }

    @Override
    @NotNull
    public TypeConstructor getConstructor() {
        return this.getDelegate().getConstructor();
    }

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        return this.getDelegate().getArguments();
    }

    @Override
    public boolean isMarkedNullable() {
        return this.getDelegate().isMarkedNullable();
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        return this.getDelegate().getMemberScope();
    }

    @NotNull
    public abstract DelegatingSimpleType replaceDelegate(@NotNull SimpleType var1);

    @Override
    @NotNull
    public SimpleType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getDelegate());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return this.replaceDelegate((SimpleType)kotlinType);
    }
}

