/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClassifierType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaConstructor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaField;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMethod;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaModifierListOwner;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.LightClassOriginKind;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaClass
extends ReflectJavaElement
implements ReflectJavaAnnotationOwner,
ReflectJavaModifierListOwner,
JavaClass {
    private final Class<?> klass;

    @Override
    @NotNull
    public Class<?> getElement() {
        return this.klass;
    }

    @Override
    public int getModifiers() {
        return this.klass.getModifiers();
    }

    @NotNull
    public List<Name> getInnerClassNames() {
        Class<?>[] classArray = this.klass.getDeclaredClasses();
        Intrinsics.checkNotNullExpressionValue(classArray, "klass.declaredClasses");
        return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.filterNot(ArraysKt.asSequence(classArray), innerClassNames.1.INSTANCE), innerClassNames.2.INSTANCE));
    }

    @Override
    @NotNull
    public FqName getFqName() {
        FqName fqName2 = ReflectClassUtilKt.getClassId(this.klass).asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "klass.classId.asSingleFqName()");
        return fqName2;
    }

    @Override
    @Nullable
    public ReflectJavaClass getOuterClass() {
        ReflectJavaClass reflectJavaClass;
        Class<?> clazz = this.klass.getDeclaringClass();
        if (clazz != null) {
            Class<?> clazz2 = clazz;
            boolean bl = false;
            boolean bl2 = false;
            Class<?> p1 = clazz2;
            boolean bl3 = false;
            reflectJavaClass = new ReflectJavaClass(p1);
        } else {
            reflectJavaClass = null;
        }
        return reflectJavaClass;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<JavaClassifierType> getSupertypes() {
        void $this$mapTo$iv$iv;
        if (Intrinsics.areEqual(this.klass, Object.class)) {
            return CollectionsKt.emptyList();
        }
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        Type type2 = this.klass.getGenericSuperclass();
        if (type2 == null) {
            type2 = (Type)((Object)Object.class);
        }
        spreadBuilder.add(type2);
        Type[] typeArray = this.klass.getGenericInterfaces();
        Intrinsics.checkNotNullExpressionValue(typeArray, "klass.genericInterfaces");
        spreadBuilder.addSpread(typeArray);
        Iterable $this$map$iv = CollectionsKt.listOf((Type[])spreadBuilder.toArray(new Type[spreadBuilder.size()]));
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            Type type3 = (Type)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ReflectJavaClassifierType reflectJavaClassifierType = new ReflectJavaClassifierType((Type)p1);
            collection.add(reflectJavaClassifierType);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public List<ReflectJavaMethod> getMethods() {
        Method[] methodArray = this.klass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(methodArray, "klass.declaredMethods");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filter(ArraysKt.asSequence(methodArray), (Function1)new Function1<Method, Boolean>(this){
            final /* synthetic */ ReflectJavaClass this$0;

            public final boolean invoke(Method method) {
                Method method2 = method;
                Intrinsics.checkNotNullExpressionValue(method2, "method");
                return method2.isSynthetic() ? false : (this.this$0.isEnum() ? !ReflectJavaClass.access$isEnumValuesOrValueOf(this.this$0, method) : true);
            }
            {
                this.this$0 = reflectJavaClass;
                super(1);
            }
        }), methods.2.INSTANCE));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isEnumValuesOrValueOf(Method method) {
        block4: {
            String string = method.getName();
            if (string == null) return false;
            String string2 = string;
            switch (string2.hashCode()) {
                case 231605032: {
                    if (!string2.equals("valueOf")) return false;
                    break;
                }
                case -823812830: {
                    if (!string2.equals("values")) return false;
                    Class<?>[] classArray = method.getParameterTypes();
                    Intrinsics.checkNotNullExpressionValue(classArray, "method.parameterTypes");
                    Class<?>[] classArray2 = classArray;
                    boolean bl = false;
                    if (classArray2.length != 0) return false;
                    return true;
                }
            }
            break block4;
            return false;
        }
        boolean bl = Arrays.equals(method.getParameterTypes(), new Class[]{String.class});
        return bl;
    }

    @NotNull
    public List<ReflectJavaField> getFields() {
        Field[] fieldArray = this.klass.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "klass.declaredFields");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(ArraysKt.asSequence(fieldArray), fields.1.INSTANCE), fields.2.INSTANCE));
    }

    @NotNull
    public List<ReflectJavaConstructor> getConstructors() {
        Constructor<?>[] constructorArray = this.klass.getDeclaredConstructors();
        Intrinsics.checkNotNullExpressionValue(constructorArray, "klass.declaredConstructors");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(ArraysKt.asSequence(constructorArray), constructors.1.INSTANCE), constructors.2.INSTANCE));
    }

    @Override
    public boolean hasDefaultConstructor() {
        return false;
    }

    @Override
    @Nullable
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override
    @NotNull
    public Name getName() {
        Name name = Name.identifier(this.klass.getSimpleName());
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(klass.simpleName)");
        return name;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ReflectJavaTypeParameter> getTypeParameters() {
        void $this$mapTo$iv$iv;
        TypeVariable<Class<?>>[] $this$map$iv = this.klass.getTypeParameters();
        boolean $i$f$map = false;
        TypeVariable<Class<?>>[] typeVariableArray = $this$map$iv;
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
    public boolean isInterface() {
        return this.klass.isInterface();
    }

    @Override
    public boolean isAnnotationType() {
        return this.klass.isAnnotation();
    }

    @Override
    public boolean isEnum() {
        return this.klass.isEnum();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaClass && Intrinsics.areEqual(this.klass, ((ReflectJavaClass)other).klass);
    }

    public int hashCode() {
        return this.klass.hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.klass;
    }

    public ReflectJavaClass(@NotNull Class<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
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

    public static final /* synthetic */ boolean access$isEnumValuesOrValueOf(ReflectJavaClass $this, Method method) {
        return $this.isEnumValuesOrValueOf(method);
    }
}

