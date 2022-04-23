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
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.Java8ParameterNamesLoader;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaModifierListOwner;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaValueParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ReflectJavaMember
extends ReflectJavaElement
implements ReflectJavaAnnotationOwner,
ReflectJavaModifierListOwner,
JavaMember {
    @NotNull
    public abstract Member getMember();

    @Override
    @NotNull
    public AnnotatedElement getElement() {
        Member member = this.getMember();
        if (member == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.reflect.AnnotatedElement");
        }
        return (AnnotatedElement)((Object)member);
    }

    @Override
    public int getModifiers() {
        return this.getMember().getModifiers();
    }

    @Override
    @NotNull
    public Name getName() {
        Object object;
        block3: {
            block2: {
                object = this.getMember().getName();
                if (object == null) break block2;
                String string = object;
                boolean bl = false;
                boolean bl2 = false;
                String it = string;
                boolean bl3 = false;
                object = Name.identifier(it);
                if (object != null) break block3;
            }
            Name name = SpecialNames.NO_NAME_PROVIDED;
            object = name;
            Intrinsics.checkNotNullExpressionValue(name, "SpecialNames.NO_NAME_PROVIDED");
        }
        return object;
    }

    @Override
    @NotNull
    public ReflectJavaClass getContainingClass() {
        Class<?> clazz = this.getMember().getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(clazz, "member.declaringClass");
        return new ReflectJavaClass(clazz);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    protected final List<JavaValueParameter> getValueParameters(@NotNull Type[] parameterTypes, @NotNull Annotation[][] parameterAnnotations, boolean isVararg) {
        List<String> names;
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(parameterAnnotations, "parameterAnnotations");
        ArrayList<ReflectJavaValueParameter> result2 = new ArrayList<ReflectJavaValueParameter>(parameterTypes.length);
        List<String> list = names = Java8ParameterNamesLoader.INSTANCE.loadParameterNames(this.getMember());
        int shift = list != null ? list.size() - parameterTypes.length : 0;
        int n = 0;
        int n2 = parameterTypes.length;
        while (n < n2) {
            String string;
            void i;
            ReflectJavaType type2 = ReflectJavaType.Factory.create(parameterTypes[i]);
            if (names != null) {
                List<String> list2;
                boolean bl = false;
                boolean bl2 = false;
                List<String> $this$run = list2;
                boolean bl3 = false;
                string = CollectionsKt.getOrNull($this$run, (int)(i + shift));
                if (string == null) {
                    String string2 = "No parameter with index " + (int)i + '+' + shift + " (name=" + this.getName() + " type=" + type2 + ") in " + $this$run + "@ReflectJavaMember";
                    boolean bl4 = false;
                    throw (Throwable)new IllegalStateException(string2.toString());
                }
            } else {
                string = null;
            }
            String name = string;
            boolean isParamVararg = isVararg && i == ArraysKt.getLastIndex(parameterTypes);
            result2.add(new ReflectJavaValueParameter(type2, parameterAnnotations[i], name, isParamVararg));
            ++i;
        }
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaMember && Intrinsics.areEqual(this.getMember(), ((ReflectJavaMember)other).getMember());
    }

    public int hashCode() {
        return this.getMember().hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.getMember();
    }

    @NotNull
    public List<ReflectJavaAnnotation> getAnnotations() {
        return ReflectJavaAnnotationOwner.DefaultImpls.getAnnotations(this);
    }

    @Override
    @Nullable
    public ReflectJavaAnnotation findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return ReflectJavaAnnotationOwner.DefaultImpls.findAnnotation(this, fqName2);
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return ReflectJavaAnnotationOwner.DefaultImpls.isDeprecatedInJavaDoc(this);
    }

    @Override
    public boolean isAbstract() {
        return ReflectJavaModifierListOwner.DefaultImpls.isAbstract(this);
    }

    @Override
    public boolean isStatic() {
        return ReflectJavaModifierListOwner.DefaultImpls.isStatic(this);
    }

    @Override
    public boolean isFinal() {
        return ReflectJavaModifierListOwner.DefaultImpls.isFinal(this);
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        return ReflectJavaModifierListOwner.DefaultImpls.getVisibility(this);
    }
}

