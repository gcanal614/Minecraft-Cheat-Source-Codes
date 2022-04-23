/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

final class AnnotatedSimpleType
extends DelegatingSimpleTypeImpl {
    @NotNull
    private final Annotations annotations;

    @Override
    @NotNull
    public AnnotatedSimpleType replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new AnnotatedSimpleType(delegate, this.getAnnotations());
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    public AnnotatedSimpleType(@NotNull SimpleType delegate, @NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        super(delegate);
        this.annotations = annotations2;
    }
}

