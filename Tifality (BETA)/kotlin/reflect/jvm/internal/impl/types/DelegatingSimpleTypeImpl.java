/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.AnnotatedSimpleType;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public abstract class DelegatingSimpleTypeImpl
extends DelegatingSimpleType {
    @NotNull
    private final SimpleType delegate;

    @Override
    @NotNull
    public DelegatingSimpleTypeImpl replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return newAnnotations != this.getAnnotations() ? (DelegatingSimpleTypeImpl)new AnnotatedSimpleType(this, newAnnotations) : this;
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        if (newNullability == this.isMarkedNullable()) {
            return this;
        }
        return this.getDelegate().makeNullableAsSpecified(newNullability).replaceAnnotations(this.getAnnotations());
    }

    @Override
    @NotNull
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    public DelegatingSimpleTypeImpl(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }
}

