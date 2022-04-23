/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaAnnotation
extends ReflectJavaElement
implements JavaAnnotation {
    @NotNull
    private final Annotation annotation;

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<JavaAnnotationArgument> getArguments() {
        void $this$mapTo$iv$iv;
        Method[] methodArray = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)).getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(methodArray, "annotation.annotationClass.java.declaredMethods");
        Method[] $this$map$iv = methodArray;
        boolean $i$f$map = false;
        Method[] methodArray2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var6_6 = $this$mapTo$iv$iv;
        int n = ((void)var6_6).length;
        for (int i = 0; i < n; ++i) {
            void method;
            void item$iv$iv;
            void var10_10 = item$iv$iv = var6_6[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Object object = method.invoke(this.annotation, new Object[0]);
            Intrinsics.checkNotNullExpressionValue(object, "method.invoke(annotation)");
            void v2 = method;
            Intrinsics.checkNotNullExpressionValue(v2, "method");
            ReflectJavaAnnotationArgument reflectJavaAnnotationArgument = ReflectJavaAnnotationArgument.Factory.create(object, Name.identifier(v2.getName()));
            collection.add(reflectJavaAnnotationArgument);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public ClassId getClassId() {
        return ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)));
    }

    @Override
    @NotNull
    public ReflectJavaClass resolve() {
        return new ReflectJavaClass(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)));
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaAnnotation && Intrinsics.areEqual(this.annotation, ((ReflectJavaAnnotation)other).annotation);
    }

    public int hashCode() {
        return ((Object)this.annotation).hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.annotation;
    }

    @NotNull
    public final Annotation getAnnotation() {
        return this.annotation;
    }

    public ReflectJavaAnnotation(@NotNull Annotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        this.annotation = annotation;
    }

    @Override
    public boolean isIdeExternalAnnotation() {
        return JavaAnnotation.DefaultImpls.isIdeExternalAnnotation(this);
    }
}

