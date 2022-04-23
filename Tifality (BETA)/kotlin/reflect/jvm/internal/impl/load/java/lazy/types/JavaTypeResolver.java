/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeFlexibility;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver$computeArguments$;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaWildcardType;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaTypeResolver {
    private final LazyJavaResolverContext c;
    private final TypeParameterResolver typeParameterResolver;

    @NotNull
    public final KotlinType transformJavaType(@Nullable JavaType javaType, @NotNull JavaTypeAttributes attr) {
        Object object;
        block5: {
            JavaType javaType2;
            block8: {
                block9: {
                    block7: {
                        block6: {
                            block4: {
                                Intrinsics.checkNotNullParameter(attr, "attr");
                                javaType2 = javaType;
                                if (!(javaType2 instanceof JavaPrimitiveType)) break block4;
                                PrimitiveType primitiveType = ((JavaPrimitiveType)javaType).getType();
                                SimpleType simpleType2 = primitiveType != null ? this.c.getModule().getBuiltIns().getPrimitiveKotlinType(primitiveType) : this.c.getModule().getBuiltIns().getUnitType();
                                Intrinsics.checkNotNullExpressionValue(simpleType2, "if (primitiveType != nul\u2026.module.builtIns.unitType");
                                object = simpleType2;
                                break block5;
                            }
                            if (!(javaType2 instanceof JavaClassifierType)) break block6;
                            object = this.transformJavaClassifierType((JavaClassifierType)javaType, attr);
                            break block5;
                        }
                        if (!(javaType2 instanceof JavaArrayType)) break block7;
                        object = JavaTypeResolver.transformArrayType$default(this, (JavaArrayType)javaType, attr, false, 4, null);
                        break block5;
                    }
                    if (!(javaType2 instanceof JavaWildcardType)) break block8;
                    object = ((JavaWildcardType)javaType).getBound();
                    if (object == null) break block9;
                    Object object2 = object;
                    boolean bl = false;
                    boolean bl2 = false;
                    Object it = object2;
                    boolean bl3 = false;
                    object = this.transformJavaType((JavaType)it, attr);
                    if (object != null) break block5;
                }
                SimpleType simpleType3 = this.c.getModule().getBuiltIns().getDefaultBound();
                Intrinsics.checkNotNullExpressionValue(simpleType3, "c.module.builtIns.defaultBound");
                object = simpleType3;
                break block5;
            }
            if (javaType2 == null) {
                SimpleType simpleType4 = this.c.getModule().getBuiltIns().getDefaultBound();
                Intrinsics.checkNotNullExpressionValue(simpleType4, "c.module.builtIns.defaultBound");
                object = simpleType4;
            } else {
                throw (Throwable)new UnsupportedOperationException("Unsupported type: " + javaType);
            }
        }
        return object;
    }

    @NotNull
    public final KotlinType transformArrayType(@NotNull JavaArrayType arrayType, @NotNull JavaTypeAttributes attr, boolean isVararg) {
        PrimitiveType primitiveType;
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        Intrinsics.checkNotNullParameter(attr, "attr");
        JavaType javaComponentType = arrayType.getComponentType();
        JavaType javaType = javaComponentType;
        if (!(javaType instanceof JavaPrimitiveType)) {
            javaType = null;
        }
        JavaPrimitiveType javaPrimitiveType = (JavaPrimitiveType)javaType;
        PrimitiveType primitiveType2 = primitiveType = javaPrimitiveType != null ? javaPrimitiveType.getType() : null;
        if (primitiveType != null) {
            SimpleType simpleType2 = this.c.getModule().getBuiltIns().getPrimitiveArrayKotlinType(primitiveType);
            Intrinsics.checkNotNullExpressionValue(simpleType2, "c.module.builtIns.getPri\u2026KotlinType(primitiveType)");
            SimpleType jetType = simpleType2;
            return attr.isForAnnotationParameter() ? (UnwrappedType)jetType : KotlinTypeFactory.flexibleType(jetType, jetType.makeNullableAsSpecified(true));
        }
        KotlinType componentType = this.transformJavaType(javaComponentType, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, attr.isForAnnotationParameter(), null, 2, null));
        if (attr.isForAnnotationParameter()) {
            Variance projectionKind = isVararg ? Variance.OUT_VARIANCE : Variance.INVARIANT;
            SimpleType simpleType3 = this.c.getModule().getBuiltIns().getArrayType(projectionKind, componentType);
            Intrinsics.checkNotNullExpressionValue(simpleType3, "c.module.builtIns.getArr\u2026ctionKind, componentType)");
            return simpleType3;
        }
        SimpleType simpleType4 = this.c.getModule().getBuiltIns().getArrayType(Variance.INVARIANT, componentType);
        Intrinsics.checkNotNullExpressionValue(simpleType4, "c.module.builtIns.getArr\u2026INVARIANT, componentType)");
        return KotlinTypeFactory.flexibleType(simpleType4, this.c.getModule().getBuiltIns().getArrayType(Variance.OUT_VARIANCE, componentType).makeNullableAsSpecified(true));
    }

    public static /* synthetic */ KotlinType transformArrayType$default(JavaTypeResolver javaTypeResolver, JavaArrayType javaArrayType, JavaTypeAttributes javaTypeAttributes, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return javaTypeResolver.transformArrayType(javaArrayType, javaTypeAttributes, bl);
    }

    private final KotlinType transformJavaClassifierType(JavaClassifierType javaType, JavaTypeAttributes attr) {
        Function0<SimpleType> $fun$errorType$1 = new Function0<SimpleType>(javaType){
            final /* synthetic */ JavaClassifierType $javaType;

            @NotNull
            public final SimpleType invoke() {
                SimpleType simpleType2 = ErrorUtils.createErrorType("Unresolved java class " + this.$javaType.getPresentableText());
                Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026vaType.presentableText}\")");
                return simpleType2;
            }
            {
                this.$javaType = javaClassifierType;
                super(0);
            }
        };
        boolean useFlexible = !attr.isForAnnotationParameter() && attr.getHowThisTypeIsUsed() != TypeUsage.SUPERTYPE;
        boolean isRaw = javaType.isRaw();
        if (!isRaw && !useFlexible) {
            SimpleType simpleType2 = this.computeSimpleJavaClassifierType(javaType, attr, null);
            return simpleType2 != null ? (KotlinType)simpleType2 : (KotlinType)$fun$errorType$1.invoke();
        }
        SimpleType simpleType3 = this.computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND), null);
        if (simpleType3 == null) {
            return (KotlinType)$fun$errorType$1.invoke();
        }
        SimpleType lower = simpleType3;
        SimpleType simpleType4 = this.computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND), lower);
        if (simpleType4 == null) {
            return (KotlinType)$fun$errorType$1.invoke();
        }
        SimpleType upper = simpleType4;
        return isRaw ? (UnwrappedType)new RawTypeImpl(lower, upper) : KotlinTypeFactory.flexibleType(lower, upper);
    }

    private final SimpleType computeSimpleJavaClassifierType(JavaClassifierType javaType, JavaTypeAttributes attr, SimpleType lowerResult) {
        Object object = lowerResult;
        if (object == null || (object = object.getAnnotations()) == null) {
            object = new LazyJavaAnnotations(this.c, javaType);
        }
        Object annotations2 = object;
        TypeConstructor typeConstructor2 = this.computeTypeConstructor(javaType, attr);
        if (typeConstructor2 == null) {
            return null;
        }
        TypeConstructor constructor = typeConstructor2;
        boolean isNullable = this.isNullable(attr);
        SimpleType simpleType2 = lowerResult;
        if (Intrinsics.areEqual(simpleType2 != null ? simpleType2.getConstructor() : null, constructor) && !javaType.isRaw() && isNullable) {
            return lowerResult.makeNullableAsSpecified(true);
        }
        List<TypeProjection> arguments2 = this.computeArguments(javaType, attr, constructor);
        return KotlinTypeFactory.simpleType$default((Annotations)annotations2, constructor, arguments2, isNullable, null, 16, null);
    }

    private final TypeConstructor computeTypeConstructor(JavaClassifierType javaType, JavaTypeAttributes attr) {
        Object object;
        JavaClassifier classifier2;
        JavaClassifier javaClassifier = javaType.getClassifier();
        if (javaClassifier == null) {
            return this.createNotFoundClass(javaType);
        }
        JavaClassifier javaClassifier2 = classifier2 = javaClassifier;
        if (javaClassifier2 instanceof JavaClass) {
            ClassDescriptor classData;
            FqName $this$sure$iv = ((JavaClass)classifier2).getFqName();
            boolean $i$f$sure = false;
            FqName fqName2 = $this$sure$iv;
            if (fqName2 == null) {
                String string;
                boolean bl = false;
                String string2 = string = "Class type should have a FQ name: " + classifier2;
                throw (Throwable)((Object)new AssertionError((Object)string2));
            }
            FqName fqName3 = fqName2;
            ClassDescriptor classDescriptor = this.mapKotlinClass(javaType, attr, fqName3);
            if (classDescriptor == null) {
                classDescriptor = this.c.getComponents().getModuleClassResolver().resolveClass((JavaClass)classifier2);
            }
            if ((object = (classData = classDescriptor)) == null || (object = object.getTypeConstructor()) == null) {
                object = this.createNotFoundClass(javaType);
            }
        } else if (javaClassifier2 instanceof JavaTypeParameter) {
            TypeParameterDescriptor typeParameterDescriptor = this.typeParameterResolver.resolveTypeParameter((JavaTypeParameter)classifier2);
            object = typeParameterDescriptor != null ? typeParameterDescriptor.getTypeConstructor() : null;
        } else {
            throw (Throwable)new IllegalStateException("Unknown classifier kind: " + classifier2);
        }
        return object;
    }

    private final TypeConstructor createNotFoundClass(JavaClassifierType javaType) {
        ClassId classId = ClassId.topLevel(new FqName(javaType.getClassifierQualifiedName()));
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(FqName(\u2026classifierQualifiedName))");
        ClassId classId2 = classId;
        TypeConstructor typeConstructor2 = this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses().getClass(classId2, CollectionsKt.listOf(0)).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "c.components.deserialize\u2026istOf(0)).typeConstructor");
        return typeConstructor2;
    }

    private final ClassDescriptor mapKotlinClass(JavaClassifierType javaType, JavaTypeAttributes attr, FqName fqName2) {
        if (attr.isForAnnotationParameter() && Intrinsics.areEqual(fqName2, JavaTypeResolverKt.access$getJAVA_LANG_CLASS_FQ_NAME$p())) {
            return this.c.getComponents().getReflectionTypes().getKClass();
        }
        JavaToKotlinClassMap javaToKotlin = JavaToKotlinClassMap.INSTANCE;
        ClassDescriptor classDescriptor = JavaToKotlinClassMap.mapJavaToKotlin$default(javaToKotlin, fqName2, this.c.getModule().getBuiltIns(), null, 4, null);
        if (classDescriptor == null) {
            return null;
        }
        ClassDescriptor kotlinDescriptor = classDescriptor;
        if (javaToKotlin.isReadOnly(kotlinDescriptor) && (attr.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE || this.argumentsMakeSenseOnlyForMutableContainer(javaType, kotlinDescriptor))) {
            return javaToKotlin.convertReadOnlyToMutable(kotlinDescriptor);
        }
        return kotlinDescriptor;
    }

    private final boolean argumentsMakeSenseOnlyForMutableContainer(JavaClassifierType $this$argumentsMakeSenseOnlyForMutableContainer, ClassDescriptor readOnlyContainer) {
        argumentsMakeSenseOnlyForMutableContainer.1 $fun$isSuperWildcard$1 = argumentsMakeSenseOnlyForMutableContainer.1.INSTANCE;
        if (!$fun$isSuperWildcard$1.invoke(CollectionsKt.lastOrNull($this$argumentsMakeSenseOnlyForMutableContainer.getTypeArguments()))) {
            return false;
        }
        TypeConstructor typeConstructor2 = JavaToKotlinClassMap.INSTANCE.convertReadOnlyToMutable(readOnlyContainer).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "JavaToKotlinClassMap.con\u2026         .typeConstructor");
        List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "JavaToKotlinClassMap.con\u2026ypeConstructor.parameters");
        Object object = CollectionsKt.lastOrNull(list);
        if (object == null || (object = object.getVariance()) == null) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue(object, "JavaToKotlinClassMap.con\u2026.variance ?: return false");
        Object mutableLastParameterVariance = object;
        return mutableLastParameterVariance != Variance.OUT_VARIANCE;
    }

    /*
     * Unable to fully structure code
     */
    private final List<TypeProjection> computeArguments(JavaClassifierType javaType, JavaTypeAttributes attr, TypeConstructor constructor) {
        isRaw = javaType.isRaw();
        if (isRaw) ** GOTO lbl-1000
        if (javaType.getTypeArguments().isEmpty()) {
            v0 = constructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(v0, "constructor.parameters");
            var6_5 = v0;
            var7_6 = false;
            ** if (!(var6_5.isEmpty() == false)) goto lbl-1000
        }
        ** GOTO lbl-1000
lbl-1000:
        // 2 sources

        {
            v1 = true;
            ** GOTO lbl13
        }
lbl-1000:
        // 2 sources

        {
            v1 = false;
        }
lbl13:
        // 2 sources

        eraseTypeParameters = v1;
        v2 = constructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(v2, "constructor.parameters");
        typeParameters = v2;
        if (eraseTypeParameters) {
            $this$map$iv = typeParameters;
            $i$f$map = false;
            var9_14 = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            $i$f$mapTo = false;
            for (T item$iv$iv : $this$mapTo$iv$iv) {
                var14_29 = (TypeParameterDescriptor)item$iv$iv;
                var21_38 = destination$iv$iv;
                $i$a$-map-JavaTypeResolver$computeArguments$1 = false;
                erasedUpperBound = new LazyWrappedType(this.c.getStorageManager(), (Function0<? extends KotlinType>)new Function0<KotlinType>((TypeParameterDescriptor)parameter, this, attr, constructor, isRaw){
                    final /* synthetic */ TypeParameterDescriptor $parameter;
                    final /* synthetic */ JavaTypeResolver this$0;
                    final /* synthetic */ JavaTypeAttributes $attr$inlined;
                    final /* synthetic */ TypeConstructor $constructor$inlined;
                    final /* synthetic */ boolean $isRaw$inlined;
                    {
                        this.$parameter = typeParameterDescriptor;
                        this.this$0 = javaTypeResolver;
                        this.$attr$inlined = javaTypeAttributes;
                        this.$constructor$inlined = typeConstructor2;
                        this.$isRaw$inlined = bl;
                        super(0);
                    }

                    @NotNull
                    public final KotlinType invoke() {
                        TypeParameterDescriptor typeParameterDescriptor = this.$parameter;
                        Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "parameter");
                        return JavaTypeResolverKt.getErasedUpperBound(typeParameterDescriptor, this.$attr$inlined.getUpperBoundOfTypeParameter(), (Function0<? extends KotlinType>)new Function0<KotlinType>(this){
                            final /* synthetic */ computeArguments$$inlined$map$lambda$1 this$0;
                            {
                                this.this$0 = var1_1;
                                super(0);
                            }

                            @NotNull
                            public final KotlinType invoke() {
                                ClassifierDescriptor classifierDescriptor = this.this$0.$constructor$inlined.getDeclarationDescriptor();
                                Intrinsics.checkNotNull(classifierDescriptor);
                                Intrinsics.checkNotNullExpressionValue(classifierDescriptor, "constructor.declarationDescriptor!!");
                                SimpleType simpleType2 = classifierDescriptor.getDefaultType();
                                Intrinsics.checkNotNullExpressionValue(simpleType2, "constructor.declarationDescriptor!!.defaultType");
                                return TypeUtilsKt.replaceArgumentsWithStarProjections(simpleType2);
                            }
                        });
                    }
                });
                v3 = parameter;
                Intrinsics.checkNotNullExpressionValue(v3, "parameter");
                var22_41 = RawSubstitution.INSTANCE.computeProjection((TypeParameterDescriptor)v3, isRaw ? attr : attr.withFlexibility(JavaTypeFlexibility.INFLEXIBLE), erasedUpperBound);
                var21_38.add(var22_41);
            }
            return CollectionsKt.toList((List)destination$iv$iv);
        }
        if (typeParameters.size() != javaType.getTypeArguments().size()) {
            $this$map$iv = typeParameters;
            $i$f$map = false;
            $this$mapTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            $i$f$mapTo = false;
            for (T item$iv$iv : $this$mapTo$iv$iv) {
                parameter = (TypeParameterDescriptor)item$iv$iv;
                var21_39 = destination$iv$iv;
                $i$a$-map-JavaTypeResolver$computeArguments$2 = false;
                v4 = p;
                Intrinsics.checkNotNullExpressionValue(v4, "p");
                var22_42 = new TypeProjectionImpl(ErrorUtils.createErrorType(v4.getName().asString()));
                var21_39.add(var22_42);
            }
            return CollectionsKt.toList((List)destination$iv$iv);
        }
        $this$map$iv = CollectionsKt.withIndex((Iterable)javaType.getTypeArguments());
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        var12_25 = $this$mapTo$iv$iv.iterator();
        while (var12_25.hasNext()) {
            p = item$iv$iv = var12_25.next();
            var21_40 = destination$iv$iv;
            $i$a$-map-JavaTypeResolver$computeArguments$3 = false;
            erasedUpperBound = indexedArgument;
            var17_44 = erasedUpperBound.component1();
            javaTypeArgument = (JavaType)erasedUpperBound.component2();
            erasedUpperBound = i < typeParameters.size();
            var19_46 = false;
            if (_Assertions.ENABLED && !erasedUpperBound) {
                $i$a$-assert-JavaTypeResolver$computeArguments$3$1 = false;
                var20_48 = "Argument index should be less then type parameters count, but " + (int)i + " > " + typeParameters.size();
                throw (Throwable)new AssertionError((Object)var20_48);
            }
            parameter = typeParameters.get((int)i);
            v5 = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null);
            v6 = parameter;
            Intrinsics.checkNotNullExpressionValue(v6, "parameter");
            var22_43 = this.transformToTypeProjection(javaTypeArgument, v5, v6);
            var21_40.add(var22_43);
        }
        return CollectionsKt.toList((List)destination$iv$iv);
    }

    private final TypeProjection transformToTypeProjection(JavaType javaType, JavaTypeAttributes attr, TypeParameterDescriptor typeParameter) {
        TypeProjection typeProjection;
        JavaType javaType2 = javaType;
        if (javaType2 instanceof JavaWildcardType) {
            Variance projectionKind;
            JavaType bound = ((JavaWildcardType)javaType).getBound();
            Variance variance = projectionKind = ((JavaWildcardType)javaType).isExtends() ? Variance.OUT_VARIANCE : Variance.IN_VARIANCE;
            typeProjection = bound == null || this.isConflictingArgumentFor(projectionKind, typeParameter) ? JavaTypeResolverKt.makeStarProjection(typeParameter, attr) : TypeUtilsKt.createProjection(this.transformJavaType(bound, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null)), projectionKind, typeParameter);
        } else {
            typeProjection = new TypeProjectionImpl(Variance.INVARIANT, this.transformJavaType(javaType, attr));
        }
        return typeProjection;
    }

    private final boolean isConflictingArgumentFor(Variance $this$isConflictingArgumentFor, TypeParameterDescriptor typeParameter) {
        if (typeParameter.getVariance() == Variance.INVARIANT) {
            return false;
        }
        return $this$isConflictingArgumentFor != typeParameter.getVariance();
    }

    private final boolean isNullable(JavaTypeAttributes $this$isNullable) {
        if ($this$isNullable.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND) {
            return false;
        }
        return !$this$isNullable.isForAnnotationParameter() && $this$isNullable.getHowThisTypeIsUsed() != TypeUsage.SUPERTYPE;
    }

    public JavaTypeResolver(@NotNull LazyJavaResolverContext c, @NotNull TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        this.c = c;
        this.typeParameterResolver = typeParameterResolver;
    }
}

