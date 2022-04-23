/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationOwnerKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ReflectJavaAnnotationOwner
extends JavaAnnotationOwner {
    @Nullable
    public AnnotatedElement getElement();

    public static final class DefaultImpls {
        @NotNull
        public static List<ReflectJavaAnnotation> getAnnotations(@NotNull ReflectJavaAnnotationOwner $this) {
            Object object = $this.getElement();
            if (object == null || (object = object.getDeclaredAnnotations()) == null || (object = ReflectJavaAnnotationOwnerKt.getAnnotations(object)) == null) {
                object = CollectionsKt.emptyList();
            }
            return object;
        }

        @Nullable
        public static ReflectJavaAnnotation findAnnotation(@NotNull ReflectJavaAnnotationOwner $this, @NotNull FqName fqName2) {
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            Annotation[] annotationArray = $this.getElement();
            return annotationArray != null && (annotationArray = annotationArray.getDeclaredAnnotations()) != null ? ReflectJavaAnnotationOwnerKt.findAnnotation(annotationArray, fqName2) : null;
        }

        public static boolean isDeprecatedInJavaDoc(@NotNull ReflectJavaAnnotationOwner $this) {
            return false;
        }
    }
}

