/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KProperty;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.jvm.KTypesJvm;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KTypeImpl$WhenMappings;
import kotlin.reflect.jvm.internal.KTypeParameterImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0013\u0010#\u001a\u00020\u001b2\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020'H\u0016J\u0015\u0010(\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u001bH\u0000\u00a2\u0006\u0002\b*J\b\u0010+\u001a\u00020,H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000f\u0010\fR\u001d\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0011\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0017X\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!\u00a8\u0006-"}, d2={"Lkotlin/reflect/jvm/internal/KTypeImpl;", "Lkotlin/jvm/internal/KTypeBase;", "type", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "computeJavaType", "Lkotlin/Function0;", "Ljava/lang/reflect/Type;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lkotlin/jvm/functions/Function0;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "arguments", "Lkotlin/reflect/KTypeProjection;", "getArguments", "arguments$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "classifier", "Lkotlin/reflect/KClassifier;", "getClassifier", "()Lkotlin/reflect/KClassifier;", "classifier$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "getComputeJavaType$annotations", "()V", "isMarkedNullable", "", "()Z", "javaType", "getJavaType", "()Ljava/lang/reflect/Type;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "convert", "equals", "other", "", "hashCode", "", "makeNullableAsSpecified", "nullable", "makeNullableAsSpecified$kotlin_reflection", "toString", "", "kotlin-reflection"})
public final class KTypeImpl
implements KTypeBase {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final ReflectProperties.LazySoftVal<Type> computeJavaType;
    @Nullable
    private final ReflectProperties.LazySoftVal classifier$delegate;
    @NotNull
    private final ReflectProperties.LazySoftVal arguments$delegate;
    @NotNull
    private final KotlinType type;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KTypeImpl.class), "classifier", "getClassifier()Lkotlin/reflect/KClassifier;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KTypeImpl.class), "arguments", "getArguments()Ljava/util/List;"))};
    }

    @Override
    @Nullable
    public Type getJavaType() {
        ReflectProperties.LazySoftVal<Type> lazySoftVal = this.computeJavaType;
        return lazySoftVal != null ? lazySoftVal.invoke() : null;
    }

    @Override
    @Nullable
    public KClassifier getClassifier() {
        return (KClassifier)this.classifier$delegate.getValue(this, $$delegatedProperties[0]);
    }

    private final KClassifier convert(KotlinType type2) {
        ClassifierDescriptor descriptor2 = type2.getConstructor().getDeclarationDescriptor();
        if (descriptor2 instanceof ClassDescriptor) {
            Class<?> clazz = UtilKt.toJavaClass((ClassDescriptor)descriptor2);
            if (clazz == null) {
                return null;
            }
            Class<?> jClass = clazz;
            if (jClass.isArray()) {
                Object object = CollectionsKt.singleOrNull(type2.getArguments());
                if (object == null || (object = object.getType()) == null) {
                    return new KClassImpl(jClass);
                }
                Intrinsics.checkNotNullExpressionValue(object, "type.arguments.singleOrN\u2026return KClassImpl(jClass)");
                Object argument = object;
                KClassifier kClassifier = this.convert((KotlinType)argument);
                if (kClassifier == null) {
                    throw (Throwable)new KotlinReflectionInternalError("Cannot determine classifier for array element type: " + this);
                }
                KClassifier elementClassifier = kClassifier;
                return new KClassImpl(ReflectClassUtilKt.createArrayType(JvmClassMappingKt.getJavaClass(KTypesJvm.getJvmErasure(elementClassifier))));
            }
            if (!TypeUtils.isNullableType(type2)) {
                Class<?> clazz2 = ReflectClassUtilKt.getPrimitiveByWrapper(jClass);
                if (clazz2 == null) {
                    clazz2 = jClass;
                }
                return new KClassImpl(clazz2);
            }
            return new KClassImpl(jClass);
        }
        if (descriptor2 instanceof TypeParameterDescriptor) {
            return new KTypeParameterImpl(null, (TypeParameterDescriptor)descriptor2);
        }
        if (descriptor2 instanceof TypeAliasDescriptor) {
            String string = "Type alias classifiers are not yet supported";
            boolean bl = false;
            throw (Throwable)new NotImplementedError("An operation is not implemented: " + string);
        }
        return null;
    }

    @Override
    @NotNull
    public List<KTypeProjection> getArguments() {
        return (List)this.arguments$delegate.getValue(this, $$delegatedProperties[1]);
    }

    @Override
    public boolean isMarkedNullable() {
        return this.type.isMarkedNullable();
    }

    @Override
    @NotNull
    public List<Annotation> getAnnotations() {
        return UtilKt.computeAnnotations(this.type);
    }

    @NotNull
    public final KTypeImpl makeNullableAsSpecified$kotlin_reflection(boolean nullable) {
        if (!FlexibleTypesKt.isFlexible(this.type) && this.isMarkedNullable() == nullable) {
            return this;
        }
        KotlinType kotlinType = TypeUtils.makeNullableAsSpecified(this.type, nullable);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "TypeUtils.makeNullableAsSpecified(type, nullable)");
        return new KTypeImpl(kotlinType, (Function0<? extends Type>)this.computeJavaType);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof KTypeImpl && Intrinsics.areEqual(this.type, ((KTypeImpl)other).type);
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    @NotNull
    public String toString() {
        return ReflectionObjectRenderer.INSTANCE.renderType(this.type);
    }

    @NotNull
    public final KotlinType getType() {
        return this.type;
    }

    /*
     * WARNING - void declaration
     */
    public KTypeImpl(@NotNull KotlinType type2, @Nullable Function0<? extends Type> computeJavaType) {
        ReflectProperties.LazySoftVal lazySoftVal;
        Intrinsics.checkNotNullParameter(type2, "type");
        this.type = type2;
        KTypeImpl kTypeImpl = this;
        Function0<? extends Type> function0 = computeJavaType;
        if (!(function0 instanceof ReflectProperties.LazySoftVal)) {
            function0 = null;
        }
        if ((lazySoftVal = (ReflectProperties.LazySoftVal)function0) == null) {
            Function0<? extends Type> function02 = computeJavaType;
            if (function02 != null) {
                void p1;
                Function0<? extends Type> function03 = function02;
                boolean bl = false;
                boolean bl2 = false;
                Function0<? extends Type> function04 = function03;
                KTypeImpl kTypeImpl2 = kTypeImpl;
                boolean bl3 = false;
                ReflectProperties.LazySoftVal lazySoftVal2 = ReflectProperties.lazySoft(p1);
                kTypeImpl = kTypeImpl2;
                lazySoftVal = lazySoftVal2;
            } else {
                lazySoftVal = null;
            }
        }
        kTypeImpl.computeJavaType = lazySoftVal;
        this.classifier$delegate = ReflectProperties.lazySoft((Function0)new Function0<KClassifier>(this){
            final /* synthetic */ KTypeImpl this$0;

            @Nullable
            public final KClassifier invoke() {
                return KTypeImpl.access$convert(this.this$0, this.this$0.getType());
            }
            {
                this.this$0 = kTypeImpl;
                super(0);
            }
        });
        this.arguments$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KTypeProjection>>(this, computeJavaType){
            final /* synthetic */ KTypeImpl this$0;
            final /* synthetic */ Function0 $computeJavaType;

            /*
             * WARNING - void declaration
             */
            public final List<KTypeProjection> invoke() {
                void $this$mapIndexedTo$iv$iv;
                List<TypeProjection> typeArguments = this.this$0.getType().getArguments();
                if (typeArguments.isEmpty()) {
                    return CollectionsKt.emptyList();
                }
                KProperty kProperty = null;
                Lazy<T> parameterizedTypeArguments2 = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<List<? extends Type>>(this){
                    final /* synthetic */ arguments.2 this$0;

                    @NotNull
                    public final List<Type> invoke() {
                        Type type2 = this.this$0.this$0.getJavaType();
                        Intrinsics.checkNotNull(type2);
                        return ReflectClassUtilKt.getParameterizedTypeArguments(type2);
                    }
                    {
                        this.this$0 = var1_1;
                        super(0);
                    }
                });
                Iterable $this$mapIndexed$iv = typeArguments;
                boolean $i$f$mapIndexed = false;
                Iterable iterable = $this$mapIndexed$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv = 0;
                for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
                    KTypeProjection kTypeProjection;
                    void typeProjection;
                    int n = index$iv$iv++;
                    boolean bl = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    TypeProjection typeProjection2 = (TypeProjection)item$iv$iv;
                    int n2 = n;
                    Collection collection = destination$iv$iv;
                    boolean bl2 = false;
                    if (typeProjection.isStarProjection()) {
                        kTypeProjection = KTypeProjection.Companion.getSTAR();
                    } else {
                        void i;
                        KotlinType kotlinType = typeProjection.getType();
                        Intrinsics.checkNotNullExpressionValue(kotlinType, "typeProjection.type");
                        KTypeImpl type2 = new KTypeImpl(kotlinType, this.$computeJavaType == null ? null : (Function0)new Function0<Type>((int)i, this, parameterizedTypeArguments2, kProperty){
                            final /* synthetic */ int $i;
                            final /* synthetic */ arguments.2 this$0;
                            final /* synthetic */ Lazy $parameterizedTypeArguments$inlined;
                            final /* synthetic */ KProperty $parameterizedTypeArguments$metadata$inlined;
                            {
                                this.$i = n;
                                this.this$0 = var2_2;
                                this.$parameterizedTypeArguments$inlined = lazy;
                                this.$parameterizedTypeArguments$metadata$inlined = kProperty;
                                super(0);
                            }

                            @NotNull
                            public final Type invoke() {
                                Type type2;
                                Type javaType = this.this$0.this$0.getJavaType();
                                if (javaType instanceof Class) {
                                    Class clazz;
                                    if (((Class)javaType).isArray()) {
                                        Class<?> clazz2 = ((Class)javaType).getComponentType();
                                        clazz = clazz2;
                                        Intrinsics.checkNotNullExpressionValue(clazz2, "javaType.componentType");
                                    } else {
                                        clazz = Object.class;
                                    }
                                    type2 = (Type)((Object)clazz);
                                } else if (javaType instanceof GenericArrayType) {
                                    if (this.$i != 0) {
                                        throw (Throwable)new KotlinReflectionInternalError("Array type has been queried for a non-0th argument: " + this.this$0.this$0);
                                    }
                                    type2 = ((GenericArrayType)javaType).getGenericComponentType();
                                } else if (javaType instanceof ParameterizedType) {
                                    Lazy lazy = this.$parameterizedTypeArguments$inlined;
                                    Object var3_3 = null;
                                    KProperty kProperty = this.$parameterizedTypeArguments$metadata$inlined;
                                    boolean bl = false;
                                    Type argument = (Type)((List)lazy.getValue()).get(this.$i);
                                    if (!(argument instanceof WildcardType)) {
                                        type2 = argument;
                                    } else {
                                        Type[] typeArray = ((WildcardType)argument).getLowerBounds();
                                        Intrinsics.checkNotNullExpressionValue(typeArray, "argument.lowerBounds");
                                        type2 = ArraysKt.firstOrNull(typeArray);
                                        if (type2 == null) {
                                            Type[] typeArray2 = ((WildcardType)argument).getUpperBounds();
                                            Intrinsics.checkNotNullExpressionValue(typeArray2, "argument.upperBounds");
                                            type2 = ArraysKt.first(typeArray2);
                                        }
                                    }
                                } else {
                                    throw (Throwable)new KotlinReflectionInternalError("Non-generic type has been queried for arguments: " + this.this$0.this$0);
                                }
                                Intrinsics.checkNotNullExpressionValue(type2, "when (val javaType = jav\u2026s\")\n                    }");
                                return type2;
                            }
                        });
                        switch (KTypeImpl$WhenMappings.$EnumSwitchMapping$0[typeProjection.getProjectionKind().ordinal()]) {
                            case 1: {
                                kTypeProjection = KTypeProjection.Companion.invariant(type2);
                                break;
                            }
                            case 2: {
                                kTypeProjection = KTypeProjection.Companion.contravariant(type2);
                                break;
                            }
                            case 3: {
                                kTypeProjection = KTypeProjection.Companion.covariant(type2);
                                break;
                            }
                            default: {
                                throw new NoWhenBranchMatchedException();
                            }
                        }
                    }
                    KTypeProjection kTypeProjection2 = kTypeProjection;
                    collection.add(kTypeProjection2);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = kTypeImpl;
                this.$computeJavaType = function0;
                super(0);
            }
        });
    }

    public /* synthetic */ KTypeImpl(KotlinType kotlinType, Function0 function0, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            function0 = null;
        }
        this(kotlinType, function0);
    }

    public static final /* synthetic */ KClassifier access$convert(KTypeImpl $this, KotlinType type2) {
        return $this.convert(type2);
    }
}

