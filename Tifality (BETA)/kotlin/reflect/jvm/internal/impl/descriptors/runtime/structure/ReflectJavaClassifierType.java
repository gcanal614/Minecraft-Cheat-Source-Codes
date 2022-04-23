/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaClassifierType
extends ReflectJavaType
implements JavaClassifierType {
    @NotNull
    private final JavaClassifier classifier;
    @NotNull
    private final Type reflectType;

    @Override
    @NotNull
    public JavaClassifier getClassifier() {
        return this.classifier;
    }

    @Override
    @NotNull
    public String getClassifierQualifiedName() {
        throw (Throwable)new UnsupportedOperationException("Type not found: " + this.getReflectType());
    }

    @Override
    @NotNull
    public String getPresentableText() {
        return this.getReflectType().toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isRaw() {
        Type type2 = this.getReflectType();
        boolean bl = false;
        boolean bl2 = false;
        Type $this$with = type2;
        boolean bl3 = false;
        if (!($this$with instanceof Class)) return false;
        TypeVariable<Class<T>>[] typeVariableArray = ((Class)$this$with).getTypeParameters();
        boolean bl4 = false;
        TypeVariable<Class<T>>[] typeVariableArray2 = typeVariableArray;
        boolean bl5 = false;
        if (typeVariableArray2.length != 0) return true;
        return false;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<JavaType> getTypeArguments() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = ReflectClassUtilKt.getParameterizedTypeArguments(this.getReflectType());
        ReflectJavaType.Factory factory = ReflectJavaType.Factory;
        boolean $i$f$map = false;
        void var4_4 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            Type type2 = (Type)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ReflectJavaType reflectJavaType = factory.create((Type)p1);
            collection.add(reflectJavaType);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public Collection<JavaAnnotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override
    @Nullable
    public JavaAnnotation findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return null;
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return false;
    }

    @Override
    @NotNull
    public Type getReflectType() {
        return this.reflectType;
    }

    /*
     * WARNING - void declaration
     */
    public ReflectJavaClassifierType(@NotNull Type reflectType) {
        JavaClassifier javaClassifier;
        JavaClassifier javaClassifier2;
        void $this$run;
        Type type2;
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
        ReflectJavaClassifierType reflectJavaClassifierType = this;
        boolean bl = false;
        boolean bl2 = false;
        ReflectJavaClassifierType reflectJavaClassifierType2 = reflectJavaClassifierType;
        ReflectJavaClassifierType reflectJavaClassifierType3 = this;
        boolean bl3 = false;
        Type type3 = type2 = $this$run.getReflectType();
        if (type3 instanceof Class) {
            javaClassifier2 = new ReflectJavaClass((Class)type2);
        } else if (type3 instanceof TypeVariable) {
            javaClassifier2 = new ReflectJavaTypeParameter((TypeVariable)type2);
        } else if (type3 instanceof ParameterizedType) {
            Type type4 = ((ParameterizedType)type2).getRawType();
            if (type4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<*>");
            }
            javaClassifier2 = new ReflectJavaClass((Class)type4);
        } else {
            throw (Throwable)new IllegalStateException("Not a classifier type (" + type2.getClass() + "): " + type2);
        }
        JavaClassifier classifier2 = javaClassifier2;
        reflectJavaClassifierType3.classifier = javaClassifier = classifier2;
    }
}

