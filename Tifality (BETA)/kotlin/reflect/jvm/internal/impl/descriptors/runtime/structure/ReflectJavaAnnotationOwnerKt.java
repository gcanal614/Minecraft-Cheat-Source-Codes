/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaAnnotationOwnerKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<ReflectJavaAnnotation> getAnnotations(@NotNull Annotation[] $this$getAnnotations) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$getAnnotations, "$this$getAnnotations");
        Annotation[] $this$map$iv = $this$getAnnotations;
        boolean $i$f$map = false;
        Annotation[] annotationArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var6_6 = $this$mapTo$iv$iv;
        int n = ((void)var6_6).length;
        for (int i = 0; i < n; ++i) {
            void p1;
            void item$iv$iv;
            void var10_10 = item$iv$iv = var6_6[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ReflectJavaAnnotation reflectJavaAnnotation = new ReflectJavaAnnotation((Annotation)p1);
            collection.add(reflectJavaAnnotation);
        }
        return (List)destination$iv$iv;
    }

    @Nullable
    public static final ReflectJavaAnnotation findAnnotation(@NotNull Annotation[] $this$findAnnotation, @NotNull FqName fqName2) {
        ReflectJavaAnnotation reflectJavaAnnotation;
        Annotation annotation;
        block3: {
            Intrinsics.checkNotNullParameter($this$findAnnotation, "$this$findAnnotation");
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            Annotation[] $this$firstOrNull$iv = $this$findAnnotation;
            boolean $i$f$firstOrNull = false;
            Annotation[] annotationArray = $this$firstOrNull$iv;
            int n = annotationArray.length;
            for (int i = 0; i < n; ++i) {
                Annotation element$iv;
                Annotation it = element$iv = annotationArray[i];
                boolean bl = false;
                if (!Intrinsics.areEqual(ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(it))).asSingleFqName(), fqName2)) continue;
                annotation = element$iv;
                break block3;
            }
            annotation = null;
        }
        if (annotation != null) {
            Annotation annotation2 = annotation;
            boolean bl = false;
            boolean bl2 = false;
            Annotation p1 = annotation2;
            boolean bl3 = false;
            reflectJavaAnnotation = new ReflectJavaAnnotation(p1);
        } else {
            reflectJavaAnnotation = null;
        }
        return reflectJavaAnnotation;
    }
}

