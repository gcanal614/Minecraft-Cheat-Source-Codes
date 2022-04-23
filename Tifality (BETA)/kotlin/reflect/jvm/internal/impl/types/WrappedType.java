/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

public abstract class WrappedType
extends KotlinType {
    public boolean isComputed() {
        return true;
    }

    @NotNull
    protected abstract KotlinType getDelegate();

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

    @Override
    @NotNull
    public final UnwrappedType unwrap() {
        KotlinType result2 = this.getDelegate();
        while (result2 instanceof WrappedType) {
            result2 = ((WrappedType)result2).getDelegate();
        }
        KotlinType kotlinType = result2;
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
        }
        return (UnwrappedType)kotlinType;
    }

    @NotNull
    public String toString() {
        return this.isComputed() ? this.getDelegate().toString() : "<Not computed yet>";
    }

    public WrappedType() {
        super(null);
    }
}

