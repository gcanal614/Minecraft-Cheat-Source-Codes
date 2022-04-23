/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.ApproximationBounds;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$WhenMappings;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.TypeArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CapturedTypeApproximationKt {
    private static final TypeProjection toTypeProjection(TypeArgument $this$toTypeProjection) {
        boolean bl = $this$toTypeProjection.isConsistent();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            DescriptorRenderer descriptorRenderer2 = DescriptorRenderer.Companion.withOptions(toTypeProjection.1.descriptorRenderer.1.INSTANCE);
            String string = "Only consistent enhanced type projection can be converted to type projection, but " + '[' + descriptorRenderer2.render($this$toTypeProjection.getTypeParameter()) + ": <" + descriptorRenderer2.renderType($this$toTypeProjection.getInProjection()) + ", " + descriptorRenderer2.renderType($this$toTypeProjection.getOutProjection()) + ">]" + " was found";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        Function1<Variance, Variance> $fun$removeProjectionIfRedundant$2 = new Function1<Variance, Variance>($this$toTypeProjection){
            final /* synthetic */ TypeArgument $this_toTypeProjection;

            @NotNull
            public final Variance invoke(@NotNull Variance variance) {
                Intrinsics.checkNotNullParameter((Object)((Object)variance), "variance");
                return variance == this.$this_toTypeProjection.getTypeParameter().getVariance() ? Variance.INVARIANT : variance;
            }
            {
                this.$this_toTypeProjection = typeArgument;
                super(1);
            }
        };
        return Intrinsics.areEqual($this$toTypeProjection.getInProjection(), $this$toTypeProjection.getOutProjection()) ? new TypeProjectionImpl($this$toTypeProjection.getInProjection()) : (KotlinBuiltIns.isNothing($this$toTypeProjection.getInProjection()) && $this$toTypeProjection.getTypeParameter().getVariance() != Variance.IN_VARIANCE ? new TypeProjectionImpl($fun$removeProjectionIfRedundant$2.invoke(Variance.OUT_VARIANCE), $this$toTypeProjection.getOutProjection()) : (KotlinBuiltIns.isNullableAny($this$toTypeProjection.getOutProjection()) ? new TypeProjectionImpl($fun$removeProjectionIfRedundant$2.invoke(Variance.IN_VARIANCE), $this$toTypeProjection.getInProjection()) : new TypeProjectionImpl($fun$removeProjectionIfRedundant$2.invoke(Variance.OUT_VARIANCE), $this$toTypeProjection.getOutProjection())));
    }

    private static final TypeArgument toTypeArgument(TypeProjection $this$toTypeArgument, TypeParameterDescriptor typeParameter) {
        TypeArgument typeArgument;
        switch (CapturedTypeApproximationKt$WhenMappings.$EnumSwitchMapping$0[TypeSubstitutor.combine(typeParameter.getVariance(), $this$toTypeArgument).ordinal()]) {
            case 1: {
                KotlinType kotlinType = $this$toTypeArgument.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "type");
                KotlinType kotlinType2 = $this$toTypeArgument.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType2, "type");
                typeArgument = new TypeArgument(typeParameter, kotlinType, kotlinType2);
                break;
            }
            case 2: {
                KotlinType kotlinType = $this$toTypeArgument.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "type");
                SimpleType simpleType2 = DescriptorUtilsKt.getBuiltIns(typeParameter).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(simpleType2, "typeParameter.builtIns.nullableAnyType");
                typeArgument = new TypeArgument(typeParameter, kotlinType, simpleType2);
                break;
            }
            case 3: {
                SimpleType simpleType3 = DescriptorUtilsKt.getBuiltIns(typeParameter).getNothingType();
                Intrinsics.checkNotNullExpressionValue(simpleType3, "typeParameter.builtIns.nothingType");
                KotlinType kotlinType = simpleType3;
                KotlinType kotlinType3 = $this$toTypeArgument.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType3, "type");
                typeArgument = new TypeArgument(typeParameter, kotlinType, kotlinType3);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return typeArgument;
    }

    @Nullable
    public static final TypeProjection approximateCapturedTypesIfNecessary(@Nullable TypeProjection typeProjection, boolean approximateContravariant) {
        if (typeProjection == null) {
            return null;
        }
        if (typeProjection.isStarProjection()) {
            return typeProjection;
        }
        KotlinType kotlinType = typeProjection.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "typeProjection.type");
        KotlinType type2 = kotlinType;
        if (!TypeUtils.contains(type2, approximateCapturedTypesIfNecessary.1.INSTANCE)) {
            return typeProjection;
        }
        Variance variance = typeProjection.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue((Object)variance, "typeProjection.projectionKind");
        Variance howThisTypeIsUsed = variance;
        if (howThisTypeIsUsed == Variance.OUT_VARIANCE) {
            ApproximationBounds<KotlinType> approximation = CapturedTypeApproximationKt.approximateCapturedTypes(type2);
            return new TypeProjectionImpl(howThisTypeIsUsed, approximation.getUpper());
        }
        if (approximateContravariant) {
            KotlinType approximation = CapturedTypeApproximationKt.approximateCapturedTypes(type2).getLower();
            return new TypeProjectionImpl(howThisTypeIsUsed, approximation);
        }
        return CapturedTypeApproximationKt.substituteCapturedTypesWithProjections(typeProjection);
    }

    private static final TypeProjection substituteCapturedTypesWithProjections(TypeProjection typeProjection) {
        TypeSubstitutor typeSubstitutor2 = TypeSubstitutor.create(new TypeConstructorSubstitution(){

            @Nullable
            public TypeProjection get(@NotNull TypeConstructor key) {
                Intrinsics.checkNotNullParameter(key, "key");
                TypeConstructor typeConstructor2 = key;
                if (!(typeConstructor2 instanceof CapturedTypeConstructor)) {
                    typeConstructor2 = null;
                }
                CapturedTypeConstructor capturedTypeConstructor = (CapturedTypeConstructor)typeConstructor2;
                if (capturedTypeConstructor == null) {
                    return null;
                }
                CapturedTypeConstructor capturedTypeConstructor2 = capturedTypeConstructor;
                if (capturedTypeConstructor2.getProjection().isStarProjection()) {
                    return new TypeProjectionImpl(Variance.OUT_VARIANCE, capturedTypeConstructor2.getProjection().getType());
                }
                return capturedTypeConstructor2.getProjection();
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutor2, "TypeSubstitutor.create(o\u2026ojection\n        }\n    })");
        TypeSubstitutor typeSubstitutor3 = typeSubstitutor2;
        return typeSubstitutor3.substituteWithoutApproximation(typeProjection);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final ApproximationBounds<KotlinType> approximateCapturedTypes(@NotNull KotlinType type2) {
        KotlinType kotlinType;
        boolean lowerBoundIsTrivial;
        ArrayList<TypeArgument> upperBoundArguments;
        ArrayList<Object> lowerBoundArguments;
        block15: {
            boolean bl;
            Intrinsics.checkNotNullParameter(type2, "type");
            if (FlexibleTypesKt.isFlexible(type2)) {
                ApproximationBounds<KotlinType> boundsForFlexibleLower = CapturedTypeApproximationKt.approximateCapturedTypes(FlexibleTypesKt.lowerIfFlexible(type2));
                ApproximationBounds<KotlinType> boundsForFlexibleUpper = CapturedTypeApproximationKt.approximateCapturedTypes(FlexibleTypesKt.upperIfFlexible(type2));
                return new ApproximationBounds<KotlinType>(TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(boundsForFlexibleLower.getLower()), FlexibleTypesKt.upperIfFlexible(boundsForFlexibleUpper.getLower())), type2), TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(boundsForFlexibleLower.getUpper()), FlexibleTypesKt.upperIfFlexible(boundsForFlexibleUpper.getUpper())), type2));
            }
            TypeConstructor typeConstructor2 = type2.getConstructor();
            if (CapturedTypeConstructorKt.isCaptured(type2)) {
                ApproximationBounds<KotlinType> approximationBounds;
                TypeConstructor typeConstructor3 = typeConstructor2;
                if (typeConstructor3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor");
                }
                TypeProjection typeProjection = ((CapturedTypeConstructor)typeConstructor3).getProjection();
                Function1<KotlinType, KotlinType> $fun$makeNullableIfNeeded$1 = new Function1<KotlinType, KotlinType>(type2){
                    final /* synthetic */ KotlinType $type;

                    @NotNull
                    public final KotlinType invoke(@NotNull KotlinType $this$makeNullableIfNeeded) {
                        Intrinsics.checkNotNullParameter($this$makeNullableIfNeeded, "$this$makeNullableIfNeeded");
                        KotlinType kotlinType = TypeUtils.makeNullableIfNeeded($this$makeNullableIfNeeded, this.$type.isMarkedNullable());
                        Intrinsics.checkNotNullExpressionValue(kotlinType, "TypeUtils.makeNullableIf\u2026s, type.isMarkedNullable)");
                        return kotlinType;
                    }
                    {
                        this.$type = kotlinType;
                        super(1);
                    }
                };
                KotlinType kotlinType2 = typeProjection.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType2, "typeProjection.type");
                KotlinType bound = $fun$makeNullableIfNeeded$1.invoke(kotlinType2);
                switch (CapturedTypeApproximationKt$WhenMappings.$EnumSwitchMapping$1[typeProjection.getProjectionKind().ordinal()]) {
                    case 1: {
                        SimpleType simpleType2 = TypeUtilsKt.getBuiltIns(type2).getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(simpleType2, "type.builtIns.nullableAnyType");
                        approximationBounds = new ApproximationBounds<KotlinType>(bound, simpleType2);
                        break;
                    }
                    case 2: {
                        SimpleType simpleType3 = TypeUtilsKt.getBuiltIns(type2).getNothingType();
                        Intrinsics.checkNotNullExpressionValue(simpleType3, "type.builtIns.nothingType");
                        approximationBounds = new ApproximationBounds<KotlinType>($fun$makeNullableIfNeeded$1.invoke((KotlinType)simpleType3), bound);
                        break;
                    }
                    default: {
                        throw (Throwable)((Object)new AssertionError((Object)("Only nontrivial projections should have been captured, not: " + typeProjection)));
                    }
                }
                return approximationBounds;
            }
            if (type2.getArguments().isEmpty() || type2.getArguments().size() != typeConstructor2.getParameters().size()) {
                return new ApproximationBounds<KotlinType>(type2, type2);
            }
            lowerBoundArguments = new ArrayList<Object>();
            upperBoundArguments = new ArrayList<TypeArgument>();
            Iterable iterable = type2.getArguments();
            List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
            Intrinsics.checkNotNullExpressionValue(list, "typeConstructor.parameters");
            for (Pair bound : CollectionsKt.zip(iterable, (Iterable)list)) {
                void lower;
                void typeProjection;
                TypeParameterDescriptor typeParameter;
                TypeProjection typeProjection2 = (TypeProjection)bound.component1();
                TypeParameterDescriptor typeParameterDescriptor = typeParameter = (TypeParameterDescriptor)bound.component2();
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "typeParameter");
                TypeArgument typeArgument = CapturedTypeApproximationKt.toTypeArgument((TypeProjection)typeProjection, typeParameterDescriptor);
                if (typeProjection.isStarProjection()) {
                    lowerBoundArguments.add(typeArgument);
                    upperBoundArguments.add(typeArgument);
                    continue;
                }
                ApproximationBounds<TypeArgument> approximationBounds = CapturedTypeApproximationKt.approximateProjection(typeArgument);
                TypeArgument typeArgument2 = approximationBounds.component1();
                TypeArgument upper = approximationBounds.component2();
                lowerBoundArguments.add(lower);
                upperBoundArguments.add(upper);
            }
            Iterable $this$any$iv = lowerBoundArguments;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    TypeArgument it = (TypeArgument)element$iv;
                    boolean bl2 = false;
                    if (!(!it.isConsistent())) continue;
                    bl = true;
                    break block15;
                }
                bl = lowerBoundIsTrivial = false;
            }
        }
        if (lowerBoundIsTrivial) {
            SimpleType simpleType4 = TypeUtilsKt.getBuiltIns(type2).getNothingType();
            Intrinsics.checkNotNullExpressionValue(simpleType4, "type.builtIns.nothingType");
            kotlinType = simpleType4;
        } else {
            kotlinType = CapturedTypeApproximationKt.replaceTypeArguments(type2, (List<TypeArgument>)lowerBoundArguments);
        }
        return new ApproximationBounds<KotlinType>(kotlinType, CapturedTypeApproximationKt.replaceTypeArguments(type2, (List<TypeArgument>)upperBoundArguments));
    }

    /*
     * WARNING - void declaration
     */
    private static final KotlinType replaceTypeArguments(KotlinType $this$replaceTypeArguments, List<TypeArgument> newTypeArguments) {
        Collection<TypeProjection> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        boolean bl = $this$replaceTypeArguments.getArguments().size() == newTypeArguments.size();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-CapturedTypeApproximationKt$replaceTypeArguments$22 = false;
            String $i$a$-assert-CapturedTypeApproximationKt$replaceTypeArguments$22 = "Incorrect type arguments " + newTypeArguments;
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-CapturedTypeApproximationKt$replaceTypeArguments$22));
        }
        Iterable iterable = newTypeArguments;
        KotlinType kotlinType = $this$replaceTypeArguments;
        boolean $i$f$map = false;
        void $i$a$-assert-CapturedTypeApproximationKt$replaceTypeArguments$22 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            TypeArgument typeArgument = (TypeArgument)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl3 = false;
            TypeProjection typeProjection = CapturedTypeApproximationKt.toTypeProjection((TypeArgument)it);
            collection.add(typeProjection);
        }
        collection = (List)destination$iv$iv;
        return TypeSubstitutionKt.replace$default(kotlinType, (List)collection, null, 2, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final ApproximationBounds<TypeArgument> approximateProjection(TypeArgument typeArgument) {
        void inLower;
        void outLower;
        Object object = CapturedTypeApproximationKt.approximateCapturedTypes(typeArgument.getInProjection());
        KotlinType kotlinType = ((ApproximationBounds)object).component1();
        KotlinType inUpper = ((ApproximationBounds)object).component2();
        ApproximationBounds<KotlinType> approximationBounds = CapturedTypeApproximationKt.approximateCapturedTypes(typeArgument.getOutProjection());
        object = approximationBounds.component1();
        KotlinType outUpper = approximationBounds.component2();
        return new ApproximationBounds<TypeArgument>(new TypeArgument(typeArgument.getTypeParameter(), inUpper, (KotlinType)outLower), new TypeArgument(typeArgument.getTypeParameter(), (KotlinType)inLower, outUpper));
    }
}

