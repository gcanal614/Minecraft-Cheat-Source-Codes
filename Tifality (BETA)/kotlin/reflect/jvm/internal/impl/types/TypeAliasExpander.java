/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansion;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansionReportStrategy;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class TypeAliasExpander {
    private final TypeAliasExpansionReportStrategy reportStrategy;
    private final boolean shouldCheckBounds;
    @NotNull
    private static final TypeAliasExpander NON_REPORTING;
    public static final Companion Companion;

    @NotNull
    public final SimpleType expand(@NotNull TypeAliasExpansion typeAliasExpansion, @NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(typeAliasExpansion, "typeAliasExpansion");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        return this.expandRecursively(typeAliasExpansion, annotations2, false, 0, true);
    }

    private final SimpleType expandRecursively(TypeAliasExpansion typeAliasExpansion, Annotations annotations2, boolean isNullable, int recursionDepth, boolean withAbbreviatedType) {
        TypeProjectionImpl underlyingProjection = new TypeProjectionImpl(Variance.INVARIANT, typeAliasExpansion.getDescriptor().getUnderlyingType());
        TypeProjection expandedProjection = this.expandTypeProjection(underlyingProjection, typeAliasExpansion, null, recursionDepth);
        KotlinType kotlinType = expandedProjection.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "expandedProjection.type");
        SimpleType expandedType = TypeSubstitutionKt.asSimpleType(kotlinType);
        if (KotlinTypeKt.isError(expandedType)) {
            return expandedType;
        }
        boolean bl = expandedProjection.getProjectionKind() == Variance.INVARIANT;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Type alias expansion: result for " + typeAliasExpansion.getDescriptor() + " is " + (Object)((Object)expandedProjection.getProjectionKind()) + ", should be invariant";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.checkRepeatedAnnotations(expandedType.getAnnotations(), annotations2);
        SimpleType simpleType2 = this.combineAnnotations(expandedType, annotations2);
        boolean bl4 = false;
        boolean bl5 = false;
        SimpleType it = simpleType2;
        boolean bl6 = false;
        SimpleType simpleType3 = TypeUtils.makeNullableIfNeeded(it, isNullable);
        Intrinsics.checkNotNullExpressionValue(simpleType3, "expandedType.combineAnno\u2026fNeeded(it, isNullable) }");
        SimpleType expandedTypeWithExtraAnnotations = simpleType3;
        return withAbbreviatedType ? SpecialTypesKt.withAbbreviation(expandedTypeWithExtraAnnotations, this.createAbbreviation(typeAliasExpansion, annotations2, isNullable)) : expandedTypeWithExtraAnnotations;
    }

    private final SimpleType createAbbreviation(TypeAliasExpansion $this$createAbbreviation, Annotations annotations2, boolean isNullable) {
        TypeConstructor typeConstructor2 = $this$createAbbreviation.getDescriptor().getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations2, typeConstructor2, $this$createAbbreviation.getArguments(), isNullable, MemberScope.Empty.INSTANCE);
    }

    private final TypeProjection expandTypeProjection(TypeProjection underlyingProjection, TypeAliasExpansion typeAliasExpansion, TypeParameterDescriptor typeParameterDescriptor, int recursionDepth) {
        Variance variance;
        Object object;
        Variance substitutionVariance;
        Variance variance2;
        TypeAliasExpander.Companion.assertRecursionDepth(recursionDepth, typeAliasExpansion.getDescriptor());
        if (underlyingProjection.isStarProjection()) {
            TypeParameterDescriptor typeParameterDescriptor2 = typeParameterDescriptor;
            Intrinsics.checkNotNull(typeParameterDescriptor2);
            TypeProjection typeProjection = TypeUtils.makeStarProjection(typeParameterDescriptor2);
            Intrinsics.checkNotNullExpressionValue(typeProjection, "TypeUtils.makeStarProjec\u2026ypeParameterDescriptor!!)");
            return typeProjection;
        }
        KotlinType kotlinType = underlyingProjection.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "underlyingProjection.type");
        KotlinType underlyingType = kotlinType;
        TypeProjection typeProjection = typeAliasExpansion.getReplacement(underlyingType.getConstructor());
        if (typeProjection == null) {
            return this.expandNonArgumentTypeProjection(underlyingProjection, typeAliasExpansion, recursionDepth);
        }
        TypeProjection argument = typeProjection;
        if (argument.isStarProjection()) {
            TypeParameterDescriptor typeParameterDescriptor3 = typeParameterDescriptor;
            Intrinsics.checkNotNull(typeParameterDescriptor3);
            TypeProjection typeProjection2 = TypeUtils.makeStarProjection(typeParameterDescriptor3);
            Intrinsics.checkNotNullExpressionValue(typeProjection2, "TypeUtils.makeStarProjec\u2026ypeParameterDescriptor!!)");
            return typeProjection2;
        }
        UnwrappedType argumentType = argument.getType().unwrap();
        TypeAliasExpander typeAliasExpander = this;
        boolean bl = false;
        boolean bl2 = false;
        TypeAliasExpander $this$run = typeAliasExpander;
        boolean bl3 = false;
        Variance variance3 = argument.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue((Object)variance3, "argument.projectionKind");
        Variance argumentVariance = variance3;
        Variance variance4 = underlyingProjection.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue((Object)variance4, "underlyingProjection.projectionKind");
        Variance underlyingVariance = variance4;
        if (underlyingVariance == argumentVariance) {
            variance2 = argumentVariance;
        } else if (underlyingVariance == Variance.INVARIANT) {
            variance2 = argumentVariance;
        } else if (argumentVariance == Variance.INVARIANT) {
            variance2 = underlyingVariance;
        } else {
            $this$run.reportStrategy.conflictingProjection(typeAliasExpansion.getDescriptor(), typeParameterDescriptor, argumentType);
            variance2 = substitutionVariance = argumentVariance;
        }
        if ((object = typeParameterDescriptor) == null || (object = object.getVariance()) == null) {
            object = Variance.INVARIANT;
        }
        Intrinsics.checkNotNullExpressionValue(object, "typeParameterDescriptor?\u2026nce ?: Variance.INVARIANT");
        Object parameterVariance = object;
        if (parameterVariance == substitutionVariance) {
            variance = substitutionVariance;
        } else if (parameterVariance == Variance.INVARIANT) {
            variance = substitutionVariance;
        } else if (substitutionVariance == Variance.INVARIANT) {
            variance = Variance.INVARIANT;
        } else {
            $this$run.reportStrategy.conflictingProjection(typeAliasExpansion.getDescriptor(), typeParameterDescriptor, argumentType);
            variance = substitutionVariance;
        }
        Variance resultingVariance = variance;
        this.checkRepeatedAnnotations(underlyingType.getAnnotations(), argumentType.getAnnotations());
        UnwrappedType substitutedType = argumentType instanceof DynamicType ? (UnwrappedType)this.combineAnnotations((DynamicType)argumentType, underlyingType.getAnnotations()) : (UnwrappedType)this.combineNullabilityAndAnnotations(TypeSubstitutionKt.asSimpleType(argumentType), underlyingType);
        return new TypeProjectionImpl(resultingVariance, substitutedType);
    }

    private final DynamicType combineAnnotations(DynamicType $this$combineAnnotations, Annotations newAnnotations) {
        return $this$combineAnnotations.replaceAnnotations(this.createCombinedAnnotations($this$combineAnnotations, newAnnotations));
    }

    private final SimpleType combineAnnotations(SimpleType $this$combineAnnotations, Annotations newAnnotations) {
        return KotlinTypeKt.isError($this$combineAnnotations) ? $this$combineAnnotations : TypeSubstitutionKt.replace$default($this$combineAnnotations, null, this.createCombinedAnnotations($this$combineAnnotations, newAnnotations), 1, null);
    }

    private final Annotations createCombinedAnnotations(KotlinType $this$createCombinedAnnotations, Annotations newAnnotations) {
        if (KotlinTypeKt.isError($this$createCombinedAnnotations)) {
            return $this$createCombinedAnnotations.getAnnotations();
        }
        return AnnotationsKt.composeAnnotations(newAnnotations, $this$createCombinedAnnotations.getAnnotations());
    }

    /*
     * WARNING - void declaration
     */
    private final void checkRepeatedAnnotations(Annotations existingAnnotations, Annotations newAnnotations) {
        void $this$mapTo$iv;
        Iterable iterable = existingAnnotations;
        boolean bl = false;
        Collection destination$iv = new HashSet();
        boolean $i$f$mapTo = false;
        for (Object item$iv : $this$mapTo$iv) {
            void it;
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor)item$iv;
            Collection collection = destination$iv;
            boolean bl2 = false;
            FqName fqName2 = it.getFqName();
            collection.add(fqName2);
        }
        HashSet existingAnnotationFqNames = (HashSet)destination$iv;
        for (AnnotationDescriptor annotation : newAnnotations) {
            if (!existingAnnotationFqNames.contains(annotation.getFqName())) continue;
            this.reportStrategy.repeatedAnnotation(annotation);
        }
    }

    private final SimpleType combineNullability(SimpleType $this$combineNullability, KotlinType fromType) {
        SimpleType simpleType2 = TypeUtils.makeNullableIfNeeded($this$combineNullability, fromType.isMarkedNullable());
        Intrinsics.checkNotNullExpressionValue(simpleType2, "TypeUtils.makeNullableIf\u2026romType.isMarkedNullable)");
        return simpleType2;
    }

    private final SimpleType combineNullabilityAndAnnotations(SimpleType $this$combineNullabilityAndAnnotations, KotlinType fromType) {
        return this.combineAnnotations(this.combineNullability($this$combineNullabilityAndAnnotations, fromType), fromType.getAnnotations());
    }

    /*
     * WARNING - void declaration
     */
    private final TypeProjection expandNonArgumentTypeProjection(TypeProjection originalProjection, TypeAliasExpansion typeAliasExpansion, int recursionDepth) {
        TypeProjection typeProjection;
        UnwrappedType originalType = originalProjection.getType().unwrap();
        if (DynamicTypesKt.isDynamic(originalType)) {
            return originalProjection;
        }
        SimpleType type2 = TypeSubstitutionKt.asSimpleType(originalType);
        if (KotlinTypeKt.isError(type2) || !TypeUtilsKt.requiresTypeAliasExpansion(type2)) {
            return originalProjection;
        }
        TypeConstructor typeConstructor2 = type2.getConstructor();
        ClassifierDescriptor typeDescriptor = typeConstructor2.getDeclarationDescriptor();
        boolean bl = typeConstructor2.getParameters().size() == type2.getArguments().size();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-TypeAliasExpander$expandNonArgumentTypeProjection$22 = false;
            String $i$a$-assert-TypeAliasExpander$expandNonArgumentTypeProjection$22 = "Unexpected malformed type: " + type2;
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-TypeAliasExpander$expandNonArgumentTypeProjection$22));
        }
        ClassifierDescriptor classifierDescriptor = typeDescriptor;
        if (classifierDescriptor instanceof TypeParameterDescriptor) {
            typeProjection = originalProjection;
        } else if (classifierDescriptor instanceof TypeAliasDescriptor) {
            void $this$mapIndexedTo$iv$iv;
            if (typeAliasExpansion.isRecursion((TypeAliasDescriptor)typeDescriptor)) {
                this.reportStrategy.recursiveTypeAlias((TypeAliasDescriptor)typeDescriptor);
                return new TypeProjectionImpl(Variance.INVARIANT, ErrorUtils.createErrorType("Recursive type alias: " + ((TypeAliasDescriptor)typeDescriptor).getName()));
            }
            Iterable $this$mapIndexed$iv = type2.getArguments();
            boolean $i$f$mapIndexed = false;
            Iterable iterable = $this$mapIndexed$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
            boolean $i$f$mapIndexedTo = false;
            int index$iv$iv = 0;
            for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
                void i;
                void typeAliasArgument;
                int n = index$iv$iv++;
                boolean bl3 = false;
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                TypeProjection typeProjection2 = (TypeProjection)item$iv$iv;
                int n2 = n;
                Collection collection = destination$iv$iv;
                boolean bl4 = false;
                TypeProjection typeProjection3 = this.expandTypeProjection((TypeProjection)typeAliasArgument, typeAliasExpansion, typeConstructor2.getParameters().get((int)i), recursionDepth + 1);
                collection.add(typeProjection3);
            }
            List expandedArguments = (List)destination$iv$iv;
            TypeAliasExpansion nestedExpansion = TypeAliasExpansion.Companion.create(typeAliasExpansion, (TypeAliasDescriptor)typeDescriptor, expandedArguments);
            SimpleType nestedExpandedType = this.expandRecursively(nestedExpansion, type2.getAnnotations(), type2.isMarkedNullable(), recursionDepth + 1, false);
            SimpleType substitutedType = this.substituteArguments(type2, typeAliasExpansion, recursionDepth);
            SimpleType typeWithAbbreviation = DynamicTypesKt.isDynamic(nestedExpandedType) ? nestedExpandedType : SpecialTypesKt.withAbbreviation(nestedExpandedType, substitutedType);
            typeProjection = new TypeProjectionImpl(originalProjection.getProjectionKind(), typeWithAbbreviation);
        } else {
            SimpleType substitutedType = this.substituteArguments(type2, typeAliasExpansion, recursionDepth);
            this.checkTypeArgumentsSubstitution(type2, substitutedType);
            typeProjection = new TypeProjectionImpl(originalProjection.getProjectionKind(), substitutedType);
        }
        return typeProjection;
    }

    /*
     * WARNING - void declaration
     */
    private final SimpleType substituteArguments(SimpleType $this$substituteArguments, TypeAliasExpansion typeAliasExpansion, int recursionDepth) {
        void $this$mapIndexedTo$iv$iv;
        TypeConstructor typeConstructor2 = $this$substituteArguments.getConstructor();
        Iterable $this$mapIndexed$iv = $this$substituteArguments.getArguments();
        boolean $i$f$mapIndexed = false;
        Iterable iterable = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void i;
            void originalArgument;
            int n = index$iv$iv++;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection)item$iv$iv;
            int n2 = n;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            TypeProjection projection = this.expandTypeProjection((TypeProjection)originalArgument, typeAliasExpansion, typeConstructor2.getParameters().get((int)i), recursionDepth + 1);
            TypeProjection typeProjection2 = projection.isStarProjection() ? projection : (TypeProjection)new TypeProjectionImpl(projection.getProjectionKind(), TypeUtils.makeNullableIfNeeded(projection.getType(), originalArgument.getType().isMarkedNullable()));
            collection.add(typeProjection2);
        }
        List substitutedArguments = (List)destination$iv$iv;
        return TypeSubstitutionKt.replace$default($this$substituteArguments, substitutedArguments, null, 2, null);
    }

    /*
     * WARNING - void declaration
     */
    private final void checkTypeArgumentsSubstitution(KotlinType unsubstitutedType, KotlinType substitutedType) {
        TypeSubstitutor typeSubstitutor2 = TypeSubstitutor.create(substitutedType);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutor2, "TypeSubstitutor.create(substitutedType)");
        TypeSubstitutor typeSubstitutor3 = typeSubstitutor2;
        Iterable $this$forEachIndexed$iv = substitutedType.getArguments();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void substitutedArgument;
            int n = index$iv++;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection)item$iv;
            int i = n;
            boolean bl2 = false;
            if (substitutedArgument.isStarProjection()) continue;
            KotlinType kotlinType = substitutedArgument.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "substitutedArgument.type");
            if (TypeUtilsKt.containsTypeAliasParameters(kotlinType)) continue;
            TypeProjection unsubstitutedArgument = unsubstitutedType.getArguments().get(i);
            TypeParameterDescriptor typeParameter = unsubstitutedType.getConstructor().getParameters().get(i);
            if (!this.shouldCheckBounds) continue;
            KotlinType kotlinType2 = unsubstitutedArgument.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType2, "unsubstitutedArgument.type");
            KotlinType kotlinType3 = substitutedArgument.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType3, "substitutedArgument.type");
            TypeParameterDescriptor typeParameterDescriptor = typeParameter;
            Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "typeParameter");
            Companion.checkBoundsInTypeAlias(this.reportStrategy, kotlinType2, kotlinType3, typeParameterDescriptor, typeSubstitutor3);
        }
    }

    public TypeAliasExpander(@NotNull TypeAliasExpansionReportStrategy reportStrategy, boolean shouldCheckBounds) {
        Intrinsics.checkNotNullParameter(reportStrategy, "reportStrategy");
        this.reportStrategy = reportStrategy;
        this.shouldCheckBounds = shouldCheckBounds;
    }

    static {
        Companion = new Companion(null);
        NON_REPORTING = new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false);
    }

    public static final class Companion {
        public final void checkBoundsInTypeAlias(@NotNull TypeAliasExpansionReportStrategy reportStrategy, @NotNull KotlinType unsubstitutedArgument, @NotNull KotlinType typeArgument, @NotNull TypeParameterDescriptor typeParameterDescriptor, @NotNull TypeSubstitutor substitutor) {
            Intrinsics.checkNotNullParameter(reportStrategy, "reportStrategy");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
            Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            for (KotlinType bound : typeParameterDescriptor.getUpperBounds()) {
                KotlinType substitutedBound;
                Intrinsics.checkNotNullExpressionValue(substitutor.safeSubstitute(bound, Variance.INVARIANT), "substitutor.safeSubstitu\u2026ound, Variance.INVARIANT)");
                if (KotlinTypeChecker.DEFAULT.isSubtypeOf(typeArgument, substitutedBound)) continue;
                reportStrategy.boundsViolationInSubstitution(substitutedBound, unsubstitutedArgument, typeArgument, typeParameterDescriptor);
            }
        }

        private final void assertRecursionDepth(int recursionDepth, TypeAliasDescriptor typeAliasDescriptor) {
            if (recursionDepth > 100) {
                throw (Throwable)((Object)new AssertionError((Object)("Too deep recursion while expanding type alias " + typeAliasDescriptor.getName())));
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

