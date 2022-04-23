/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotatedImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import org.jetbrains.annotations.NotNull;

public final class FieldDescriptorImpl
extends AnnotatedImpl
implements FieldDescriptor {
    @NotNull
    private final PropertyDescriptor correspondingProperty;

    public FieldDescriptorImpl(@NotNull Annotations annotations2, @NotNull PropertyDescriptor correspondingProperty) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(correspondingProperty, "correspondingProperty");
        super(annotations2);
        this.correspondingProperty = correspondingProperty;
    }
}

