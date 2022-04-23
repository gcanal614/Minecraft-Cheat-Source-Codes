/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;

public interface PossiblyExternalAnnotationDescriptor
extends AnnotationDescriptor {
    public boolean isIdeExternalAnnotation();
}

