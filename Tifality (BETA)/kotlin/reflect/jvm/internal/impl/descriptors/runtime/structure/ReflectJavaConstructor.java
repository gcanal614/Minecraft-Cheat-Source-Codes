/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMember;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaConstructor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import org.jetbrains.annotations.NotNull;

public final class ReflectJavaConstructor
extends ReflectJavaMember
implements JavaConstructor {
    @NotNull
    private final Constructor<?> member;

    @Override
    @NotNull
    public List<JavaValueParameter> getValueParameters() {
        Annotation[][] annotationArray;
        Type[] typeArray;
        int n;
        Type[] types = ((Constructor)this.getMember()).getGenericParameterTypes();
        Intrinsics.checkNotNullExpressionValue(types, "types");
        Type[] typeArray2 = types;
        boolean bl = false;
        if (typeArray2.length == 0) {
            return CollectionsKt.emptyList();
        }
        Class klass = ((Constructor)this.getMember()).getDeclaringClass();
        if (klass.getDeclaringClass() != null && !Modifier.isStatic(klass.getModifiers())) {
            Type[] typeArray3 = types;
            int n2 = 1;
            int n3 = types.length;
            n = 0;
            typeArray = ArraysKt.copyOfRange(typeArray3, n2, n3);
        } else {
            typeArray = types;
        }
        Type[] realTypes = typeArray;
        Annotation[][] annotations2 = ((Constructor)this.getMember()).getParameterAnnotations();
        if (((Object[])annotations2).length < realTypes.length) {
            throw (Throwable)new IllegalStateException("Illegal generic signature: " + this.getMember());
        }
        if (((Object[])annotations2).length > realTypes.length) {
            Intrinsics.checkNotNullExpressionValue(annotations2, "annotations");
            Object[] objectArray = (Object[])annotations2;
            n = ((Object[])annotations2).length - realTypes.length;
            int n4 = ((Object[])annotations2).length;
            boolean bl2 = false;
            annotationArray = (Annotation[][])ArraysKt.copyOfRange(objectArray, n, n4);
        } else {
            annotationArray = annotations2;
        }
        Annotation[][] realAnnotations = annotationArray;
        Intrinsics.checkNotNullExpressionValue(realTypes, "realTypes");
        Intrinsics.checkNotNullExpressionValue(realAnnotations, "realAnnotations");
        return this.getValueParameters(realTypes, realAnnotations, ((Constructor)this.getMember()).isVarArgs());
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ReflectJavaTypeParameter> getTypeParameters() {
        void $this$mapTo$iv$iv;
        TypeVariable<Constructor<T>>[] $this$map$iv = ((Constructor)this.getMember()).getTypeParameters();
        boolean $i$f$map = false;
        TypeVariable<Constructor<T>>[] typeVariableArray = $this$map$iv;
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
            ReflectJavaTypeParameter reflectJavaTypeParameter = new ReflectJavaTypeParameter((TypeVariable<?>)p1);
            collection.add(reflectJavaTypeParameter);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public Constructor<?> getMember() {
        return this.member;
    }

    public ReflectJavaConstructor(@NotNull Constructor<?> member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }
}

