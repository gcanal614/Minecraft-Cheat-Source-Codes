/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.annotations.NotNull;

public final class LazyJavaAnnotationsKt {
    @NotNull
    public static final Annotations resolveAnnotations(@NotNull LazyJavaResolverContext $this$resolveAnnotations, @NotNull JavaAnnotationOwner annotationsOwner) {
        Intrinsics.checkNotNullParameter($this$resolveAnnotations, "$this$resolveAnnotations");
        Intrinsics.checkNotNullParameter(annotationsOwner, "annotationsOwner");
        return new LazyJavaAnnotations($this$resolveAnnotations, annotationsOwner);
    }
}

