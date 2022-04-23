/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClassifierType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaTypeParameter
extends ReflectJavaElement
implements ReflectJavaAnnotationOwner,
JavaTypeParameter {
    @NotNull
    private final TypeVariable<?> typeVariable;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ReflectJavaClassifierType> getUpperBounds() {
        void $this$mapTo$iv$iv;
        Type[] typeArray = this.typeVariable.getBounds();
        Intrinsics.checkNotNullExpressionValue(typeArray, "typeVariable.bounds");
        Type[] $this$map$iv = typeArray;
        boolean $i$f$map = false;
        Type[] typeArray2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var7_6 = $this$mapTo$iv$iv;
        int n = ((void)var7_6).length;
        for (int i = 0; i < n; ++i) {
            void p1;
            void item$iv$iv;
            void var11_10 = item$iv$iv = var7_6[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ReflectJavaClassifierType reflectJavaClassifierType = new ReflectJavaClassifierType((Type)p1);
            collection.add(reflectJavaClassifierType);
        }
        List bounds = (List)destination$iv$iv;
        ReflectJavaClassifierType reflectJavaClassifierType = (ReflectJavaClassifierType)CollectionsKt.singleOrNull(bounds);
        if (Intrinsics.areEqual(reflectJavaClassifierType != null ? reflectJavaClassifierType.getReflectType() : null, Object.class)) {
            return CollectionsKt.emptyList();
        }
        return bounds;
    }

    @Override
    @Nullable
    public AnnotatedElement getElement() {
        TypeVariable<?> typeVariable = this.typeVariable;
        if (!(typeVariable instanceof AnnotatedElement)) {
            typeVariable = null;
        }
        return typeVariable;
    }

    @Override
    @NotNull
    public Name getName() {
        Name name = Name.identifier(this.typeVariable.getName());
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(typeVariable.name)");
        return name;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaTypeParameter && Intrinsics.areEqual(this.typeVariable, ((ReflectJavaTypeParameter)other).typeVariable);
    }

    public int hashCode() {
        return this.typeVariable.hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.typeVariable;
    }

    public ReflectJavaTypeParameter(@NotNull TypeVariable<?> typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        this.typeVariable = typeVariable;
    }

    @NotNull
    public List<ReflectJavaAnnotation> getAnnotations() {
        return ReflectJavaAnnotationOwner.DefaultImpls.getAnnotations(this);
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return ReflectJavaAnnotationOwner.DefaultImpls.isDeprecatedInJavaDoc(this);
    }

    @Override
    @Nullable
    public ReflectJavaAnnotation findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return ReflectJavaAnnotationOwner.DefaultImpls.findAnnotation(this, fqName2);
    }
}

