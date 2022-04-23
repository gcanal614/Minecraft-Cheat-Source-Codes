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
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMember;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaMethod
extends ReflectJavaMember
implements JavaMethod {
    @NotNull
    private final Method member;

    @Override
    @NotNull
    public List<JavaValueParameter> getValueParameters() {
        Type[] typeArray = this.getMember().getGenericParameterTypes();
        Intrinsics.checkNotNullExpressionValue(typeArray, "member.genericParameterTypes");
        Annotation[][] annotationArray = this.getMember().getParameterAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotationArray, "member.parameterAnnotations");
        return this.getValueParameters(typeArray, annotationArray, this.getMember().isVarArgs());
    }

    @Override
    @NotNull
    public ReflectJavaType getReturnType() {
        Type type2 = this.getMember().getGenericReturnType();
        Intrinsics.checkNotNullExpressionValue(type2, "member.genericReturnType");
        return ReflectJavaType.Factory.create(type2);
    }

    @Override
    @Nullable
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        ReflectJavaAnnotationArgument reflectJavaAnnotationArgument;
        Object object = this.getMember().getDefaultValue();
        if (object != null) {
            Object object2 = object;
            boolean bl = false;
            boolean bl2 = false;
            Object it = object2;
            boolean bl3 = false;
            reflectJavaAnnotationArgument = ReflectJavaAnnotationArgument.Factory.create(it, null);
        } else {
            reflectJavaAnnotationArgument = null;
        }
        return reflectJavaAnnotationArgument;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ReflectJavaTypeParameter> getTypeParameters() {
        void $this$mapTo$iv$iv;
        TypeVariable<Method>[] typeVariableArray = this.getMember().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeVariableArray, "member.typeParameters");
        TypeVariable<Method>[] $this$map$iv = typeVariableArray;
        boolean $i$f$map = false;
        TypeVariable<Method>[] typeVariableArray2 = $this$map$iv;
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
    public Method getMember() {
        return this.member;
    }

    public ReflectJavaMethod(@NotNull Method member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }

    @Override
    public boolean getHasAnnotationParameterDefaultValue() {
        return JavaMethod.DefaultImpls.getHasAnnotationParameterDefaultValue(this);
    }
}

