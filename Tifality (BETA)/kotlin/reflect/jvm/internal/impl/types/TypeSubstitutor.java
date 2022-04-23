/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.FilteredAnnotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.CustomTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.DisjointKeysUnionTypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeCapabilitiesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeSubstitutor {
    public static final TypeSubstitutor EMPTY = TypeSubstitutor.create(TypeSubstitution.EMPTY);
    @NotNull
    private final TypeSubstitution substitution;

    @NotNull
    public static TypeSubstitutor create(@NotNull TypeSubstitution substitution) {
        if (substitution == null) {
            TypeSubstitutor.$$$reportNull$$$0(0);
        }
        return new TypeSubstitutor(substitution);
    }

    @NotNull
    public static TypeSubstitutor createChainedSubstitutor(@NotNull TypeSubstitution first, @NotNull TypeSubstitution second) {
        if (first == null) {
            TypeSubstitutor.$$$reportNull$$$0(1);
        }
        if (second == null) {
            TypeSubstitutor.$$$reportNull$$$0(2);
        }
        return TypeSubstitutor.create(DisjointKeysUnionTypeSubstitution.create(first, second));
    }

    @NotNull
    public static TypeSubstitutor create(@NotNull KotlinType context) {
        if (context == null) {
            TypeSubstitutor.$$$reportNull$$$0(4);
        }
        return TypeSubstitutor.create(TypeConstructorSubstitution.create(context.getConstructor(), context.getArguments()));
    }

    protected TypeSubstitutor(@NotNull TypeSubstitution substitution) {
        if (substitution == null) {
            TypeSubstitutor.$$$reportNull$$$0(5);
        }
        this.substitution = substitution;
    }

    public boolean isEmpty() {
        return this.substitution.isEmpty();
    }

    @NotNull
    public TypeSubstitution getSubstitution() {
        TypeSubstitution typeSubstitution = this.substitution;
        if (typeSubstitution == null) {
            TypeSubstitutor.$$$reportNull$$$0(6);
        }
        return typeSubstitution;
    }

    @NotNull
    public KotlinType safeSubstitute(@NotNull KotlinType type2, @NotNull Variance howThisTypeIsUsed) {
        KotlinType kotlinType;
        if (type2 == null) {
            TypeSubstitutor.$$$reportNull$$$0(7);
        }
        if (howThisTypeIsUsed == null) {
            TypeSubstitutor.$$$reportNull$$$0(8);
        }
        if (this.isEmpty()) {
            KotlinType kotlinType2 = type2;
            if (kotlinType2 == null) {
                TypeSubstitutor.$$$reportNull$$$0(9);
            }
            return kotlinType2;
        }
        try {
            kotlinType = this.unsafeSubstitute(new TypeProjectionImpl(howThisTypeIsUsed, type2), null, 0).getType();
        }
        catch (SubstitutionException e) {
            SimpleType simpleType2 = ErrorUtils.createErrorType(e.getMessage());
            if (simpleType2 == null) {
                TypeSubstitutor.$$$reportNull$$$0(11);
            }
            return simpleType2;
        }
        if (kotlinType == null) {
            TypeSubstitutor.$$$reportNull$$$0(10);
        }
        return kotlinType;
    }

    @Nullable
    public KotlinType substitute(@NotNull KotlinType type2, @NotNull Variance howThisTypeIsUsed) {
        TypeProjection projection;
        if (type2 == null) {
            TypeSubstitutor.$$$reportNull$$$0(12);
        }
        if (howThisTypeIsUsed == null) {
            TypeSubstitutor.$$$reportNull$$$0(13);
        }
        return (projection = this.substitute(new TypeProjectionImpl(howThisTypeIsUsed, this.getSubstitution().prepareTopLevelType(type2, howThisTypeIsUsed)))) == null ? null : projection.getType();
    }

    @Nullable
    public TypeProjection substitute(@NotNull TypeProjection typeProjection) {
        if (typeProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(14);
        }
        TypeProjection substitutedTypeProjection = this.substituteWithoutApproximation(typeProjection);
        if (!this.substitution.approximateCapturedTypes() && !this.substitution.approximateContravariantCapturedTypes()) {
            return substitutedTypeProjection;
        }
        return CapturedTypeApproximationKt.approximateCapturedTypesIfNecessary(substitutedTypeProjection, this.substitution.approximateContravariantCapturedTypes());
    }

    @Nullable
    public TypeProjection substituteWithoutApproximation(@NotNull TypeProjection typeProjection) {
        if (typeProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(15);
        }
        if (this.isEmpty()) {
            return typeProjection;
        }
        try {
            return this.unsafeSubstitute(typeProjection, null, 0);
        }
        catch (SubstitutionException e) {
            return null;
        }
    }

    @NotNull
    private TypeProjection unsafeSubstitute(@NotNull TypeProjection originalProjection, @Nullable TypeParameterDescriptor typeParameter, int recursionDepth) throws SubstitutionException {
        if (originalProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(16);
        }
        TypeSubstitutor.assertRecursionDepth(recursionDepth, originalProjection, this.substitution);
        if (originalProjection.isStarProjection()) {
            TypeProjection typeProjection = originalProjection;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(17);
            }
            return typeProjection;
        }
        KotlinType type2 = originalProjection.getType();
        if (type2 instanceof TypeWithEnhancement) {
            UnwrappedType origin = ((TypeWithEnhancement)((Object)type2)).getOrigin();
            KotlinType enhancement2 = ((TypeWithEnhancement)((Object)type2)).getEnhancement();
            TypeProjection substitution = this.unsafeSubstitute(new TypeProjectionImpl(originalProjection.getProjectionKind(), origin), typeParameter, recursionDepth + 1);
            KotlinType substitutedEnhancement = this.substitute(enhancement2, originalProjection.getProjectionKind());
            UnwrappedType resultingType = TypeWithEnhancementKt.wrapEnhancement(substitution.getType().unwrap(), substitutedEnhancement);
            return new TypeProjectionImpl(substitution.getProjectionKind(), resultingType);
        }
        if (DynamicTypesKt.isDynamic(type2) || type2.unwrap() instanceof RawType) {
            TypeProjection typeProjection = originalProjection;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(18);
            }
            return typeProjection;
        }
        TypeProjection substituted = this.substitution.get(type2);
        TypeProjection replacement = substituted != null ? TypeSubstitutor.projectedTypeForConflictedTypeWithUnsafeVariance(type2, substituted, typeParameter, originalProjection) : null;
        Variance originalProjectionKind = originalProjection.getProjectionKind();
        if (replacement == null && FlexibleTypesKt.isFlexible(type2) && !TypeCapabilitiesKt.isCustomTypeVariable(type2)) {
            FlexibleType flexibleType = FlexibleTypesKt.asFlexibleType(type2);
            TypeProjection substitutedLower = this.unsafeSubstitute(new TypeProjectionImpl(originalProjectionKind, flexibleType.getLowerBound()), typeParameter, recursionDepth + 1);
            TypeProjection substitutedUpper = this.unsafeSubstitute(new TypeProjectionImpl(originalProjectionKind, flexibleType.getUpperBound()), typeParameter, recursionDepth + 1);
            Variance substitutedProjectionKind = substitutedLower.getProjectionKind();
            assert (substitutedProjectionKind == substitutedUpper.getProjectionKind() && originalProjectionKind == Variance.INVARIANT || originalProjectionKind == substitutedProjectionKind) : "Unexpected substituted projection kind: " + (Object)((Object)substitutedProjectionKind) + "; original: " + (Object)((Object)originalProjectionKind);
            if (substitutedLower.getType() == flexibleType.getLowerBound() && substitutedUpper.getType() == flexibleType.getUpperBound()) {
                TypeProjection typeProjection = originalProjection;
                if (typeProjection == null) {
                    TypeSubstitutor.$$$reportNull$$$0(19);
                }
                return typeProjection;
            }
            UnwrappedType substitutedFlexibleType = KotlinTypeFactory.flexibleType(TypeSubstitutionKt.asSimpleType(substitutedLower.getType()), TypeSubstitutionKt.asSimpleType(substitutedUpper.getType()));
            return new TypeProjectionImpl(substitutedProjectionKind, substitutedFlexibleType);
        }
        if (KotlinBuiltIns.isNothing(type2) || KotlinTypeKt.isError(type2)) {
            TypeProjection typeProjection = originalProjection;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(20);
            }
            return typeProjection;
        }
        if (replacement != null) {
            VarianceConflictType varianceConflict = TypeSubstitutor.conflictType(originalProjectionKind, replacement.getProjectionKind());
            boolean allowVarianceConflict = CapturedTypeConstructorKt.isCaptured(type2);
            if (!allowVarianceConflict) {
                switch (varianceConflict) {
                    case OUT_IN_IN_POSITION: {
                        throw new SubstitutionException("Out-projection in in-position");
                    }
                    case IN_IN_OUT_POSITION: {
                        return new TypeProjectionImpl(Variance.OUT_VARIANCE, type2.getConstructor().getBuiltIns().getNullableAnyType());
                    }
                }
            }
            CustomTypeVariable typeVariable = TypeCapabilitiesKt.getCustomTypeVariable(type2);
            if (replacement.isStarProjection()) {
                TypeProjection typeProjection = replacement;
                if (typeProjection == null) {
                    TypeSubstitutor.$$$reportNull$$$0(21);
                }
                return typeProjection;
            }
            KotlinType substitutedType = typeVariable != null ? typeVariable.substitutionResult(replacement.getType()) : TypeUtils.makeNullableIfNeeded(replacement.getType(), type2.isMarkedNullable());
            if (!type2.getAnnotations().isEmpty()) {
                Annotations typeAnnotations = TypeSubstitutor.filterOutUnsafeVariance(this.substitution.filterAnnotations(type2.getAnnotations()));
                substitutedType = TypeUtilsKt.replaceAnnotations(substitutedType, new CompositeAnnotations(substitutedType.getAnnotations(), typeAnnotations));
            }
            Variance resultingProjectionKind = varianceConflict == VarianceConflictType.NO_CONFLICT ? TypeSubstitutor.combine(originalProjectionKind, replacement.getProjectionKind()) : originalProjectionKind;
            return new TypeProjectionImpl(resultingProjectionKind, substitutedType);
        }
        TypeProjection typeProjection = this.substituteCompoundType(originalProjection, recursionDepth);
        if (typeProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(22);
        }
        return typeProjection;
    }

    @NotNull
    private static TypeProjection projectedTypeForConflictedTypeWithUnsafeVariance(@NotNull KotlinType originalType, @NotNull TypeProjection substituted, @Nullable TypeParameterDescriptor typeParameter, @NotNull TypeProjection originalProjection) {
        if (originalType == null) {
            TypeSubstitutor.$$$reportNull$$$0(23);
        }
        if (substituted == null) {
            TypeSubstitutor.$$$reportNull$$$0(24);
        }
        if (originalProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(25);
        }
        if (!originalType.getAnnotations().hasAnnotation(KotlinBuiltIns.FQ_NAMES.unsafeVariance)) {
            TypeProjection typeProjection = substituted;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(26);
            }
            return typeProjection;
        }
        TypeConstructor constructor = substituted.getType().getConstructor();
        if (!(constructor instanceof NewCapturedTypeConstructor)) {
            TypeProjection typeProjection = substituted;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(27);
            }
            return typeProjection;
        }
        NewCapturedTypeConstructor capturedType = (NewCapturedTypeConstructor)constructor;
        TypeProjection capturedTypeProjection = capturedType.getProjection();
        Variance varianceOfCapturedType = capturedTypeProjection.getProjectionKind();
        VarianceConflictType conflictWithTopLevelType = TypeSubstitutor.conflictType(originalProjection.getProjectionKind(), varianceOfCapturedType);
        if (conflictWithTopLevelType == VarianceConflictType.OUT_IN_IN_POSITION) {
            return new TypeProjectionImpl(capturedTypeProjection.getType());
        }
        if (typeParameter == null) {
            TypeProjection typeProjection = substituted;
            if (typeProjection == null) {
                TypeSubstitutor.$$$reportNull$$$0(28);
            }
            return typeProjection;
        }
        VarianceConflictType conflictTypeWithTypeParameter = TypeSubstitutor.conflictType(typeParameter.getVariance(), varianceOfCapturedType);
        if (conflictTypeWithTypeParameter == VarianceConflictType.OUT_IN_IN_POSITION) {
            return new TypeProjectionImpl(capturedTypeProjection.getType());
        }
        TypeProjection typeProjection = substituted;
        if (typeProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(29);
        }
        return typeProjection;
    }

    @NotNull
    private static Annotations filterOutUnsafeVariance(@NotNull Annotations annotations2) {
        if (annotations2 == null) {
            TypeSubstitutor.$$$reportNull$$$0(30);
        }
        if (!annotations2.hasAnnotation(KotlinBuiltIns.FQ_NAMES.unsafeVariance)) {
            Annotations annotations3 = annotations2;
            if (annotations3 == null) {
                TypeSubstitutor.$$$reportNull$$$0(31);
            }
            return annotations3;
        }
        return new FilteredAnnotations(annotations2, (Function1<? super FqName, Boolean>)new Function1<FqName, Boolean>(){

            @Override
            public Boolean invoke(@NotNull FqName name) {
                if (name == null) {
                    1.$$$reportNull$$$0(0);
                }
                return !name.equals(KotlinBuiltIns.FQ_NAMES.unsafeVariance);
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "name", "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor$1", "invoke"));
            }
        });
    }

    private TypeProjection substituteCompoundType(TypeProjection originalProjection, int recursionDepth) throws SubstitutionException {
        List<TypeProjection> substitutedArguments;
        KotlinType substitutedType;
        KotlinType type2 = originalProjection.getType();
        Variance projectionKind = originalProjection.getProjectionKind();
        if (type2.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor) {
            return originalProjection;
        }
        KotlinType substitutedAbbreviation = null;
        SimpleType abbreviation = SpecialTypesKt.getAbbreviation(type2);
        if (abbreviation != null) {
            substitutedAbbreviation = this.substitute(abbreviation, Variance.INVARIANT);
        }
        if ((substitutedType = TypeSubstitutionKt.replace(type2, substitutedArguments = this.substituteTypeArguments(type2.getConstructor().getParameters(), type2.getArguments(), recursionDepth), this.substitution.filterAnnotations(type2.getAnnotations()))) instanceof SimpleType && substitutedAbbreviation instanceof SimpleType) {
            substitutedType = SpecialTypesKt.withAbbreviation((SimpleType)substitutedType, (SimpleType)substitutedAbbreviation);
        }
        return new TypeProjectionImpl(projectionKind, substitutedType);
    }

    private List<TypeProjection> substituteTypeArguments(List<TypeParameterDescriptor> typeParameters2, List<TypeProjection> typeArguments, int recursionDepth) throws SubstitutionException {
        ArrayList<TypeProjection> substitutedArguments = new ArrayList<TypeProjection>(typeParameters2.size());
        boolean wereChanges = false;
        for (int i = 0; i < typeParameters2.size(); ++i) {
            TypeParameterDescriptor typeParameter = typeParameters2.get(i);
            TypeProjection typeArgument = typeArguments.get(i);
            TypeProjection substitutedTypeArgument = this.unsafeSubstitute(typeArgument, typeParameter, recursionDepth + 1);
            switch (TypeSubstitutor.conflictType(typeParameter.getVariance(), substitutedTypeArgument.getProjectionKind())) {
                case NO_CONFLICT: {
                    if (typeParameter.getVariance() == Variance.INVARIANT || substitutedTypeArgument.isStarProjection()) break;
                    substitutedTypeArgument = new TypeProjectionImpl(Variance.INVARIANT, substitutedTypeArgument.getType());
                    break;
                }
                case OUT_IN_IN_POSITION: 
                case IN_IN_OUT_POSITION: {
                    substitutedTypeArgument = TypeUtils.makeStarProjection(typeParameter);
                }
            }
            if (substitutedTypeArgument != typeArgument) {
                wereChanges = true;
            }
            substitutedArguments.add(substitutedTypeArgument);
        }
        if (!wereChanges) {
            return typeArguments;
        }
        return substitutedArguments;
    }

    @NotNull
    public static Variance combine(@NotNull Variance typeParameterVariance, @NotNull TypeProjection typeProjection) {
        if (typeParameterVariance == null) {
            TypeSubstitutor.$$$reportNull$$$0(32);
        }
        if (typeProjection == null) {
            TypeSubstitutor.$$$reportNull$$$0(33);
        }
        if (typeProjection.isStarProjection()) {
            Variance variance = Variance.OUT_VARIANCE;
            if (variance == null) {
                TypeSubstitutor.$$$reportNull$$$0(34);
            }
            return variance;
        }
        return TypeSubstitutor.combine(typeParameterVariance, typeProjection.getProjectionKind());
    }

    @NotNull
    public static Variance combine(@NotNull Variance typeParameterVariance, @NotNull Variance projectionKind) {
        if (typeParameterVariance == null) {
            TypeSubstitutor.$$$reportNull$$$0(35);
        }
        if (projectionKind == null) {
            TypeSubstitutor.$$$reportNull$$$0(36);
        }
        if (typeParameterVariance == Variance.INVARIANT) {
            Variance variance = projectionKind;
            if (variance == null) {
                TypeSubstitutor.$$$reportNull$$$0(37);
            }
            return variance;
        }
        if (projectionKind == Variance.INVARIANT) {
            Variance variance = typeParameterVariance;
            if (variance == null) {
                TypeSubstitutor.$$$reportNull$$$0(38);
            }
            return variance;
        }
        if (typeParameterVariance == projectionKind) {
            Variance variance = projectionKind;
            if (variance == null) {
                TypeSubstitutor.$$$reportNull$$$0(39);
            }
            return variance;
        }
        throw new AssertionError((Object)("Variance conflict: type parameter variance '" + (Object)((Object)typeParameterVariance) + "' and " + "projection kind '" + (Object)((Object)projectionKind) + "' cannot be combined"));
    }

    private static VarianceConflictType conflictType(Variance position, Variance argument) {
        if (position == Variance.IN_VARIANCE && argument == Variance.OUT_VARIANCE) {
            return VarianceConflictType.OUT_IN_IN_POSITION;
        }
        if (position == Variance.OUT_VARIANCE && argument == Variance.IN_VARIANCE) {
            return VarianceConflictType.IN_IN_OUT_POSITION;
        }
        return VarianceConflictType.NO_CONFLICT;
    }

    private static void assertRecursionDepth(int recursionDepth, TypeProjection projection, TypeSubstitution substitution) {
        if (recursionDepth > 100) {
            throw new IllegalStateException("Recursion too deep. Most likely infinite loop while substituting " + TypeSubstitutor.safeToString(projection) + "; substitution: " + TypeSubstitutor.safeToString(substitution));
        }
    }

    private static String safeToString(Object o) {
        try {
            return o.toString();
        }
        catch (Throwable e) {
            if (ExceptionUtilsKt.isProcessCanceledException(e)) {
                throw (RuntimeException)e;
            }
            return "[Exception while computing toString(): " + e + "]";
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 31: 
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 31: 
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitution";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "first";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "second";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutionContext";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "context";
                break;
            }
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 31: 
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor";
                break;
            }
            case 7: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 8: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "howThisTypeIsUsed";
                break;
            }
            case 14: 
            case 15: 
            case 33: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeProjection";
                break;
            }
            case 16: 
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "originalProjection";
                break;
            }
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "originalType";
                break;
            }
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substituted";
                break;
            }
            case 30: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 32: 
            case 35: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameterVariance";
                break;
            }
            case 36: {
                objectArray2 = objectArray3;
                objectArray3[0] = "projectionKind";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getSubstitution";
                break;
            }
            case 9: 
            case 10: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "safeSubstitute";
                break;
            }
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: {
                objectArray = objectArray2;
                objectArray2[1] = "unsafeSubstitute";
                break;
            }
            case 26: 
            case 27: 
            case 28: 
            case 29: {
                objectArray = objectArray2;
                objectArray2[1] = "projectedTypeForConflictedTypeWithUnsafeVariance";
                break;
            }
            case 31: {
                objectArray = objectArray2;
                objectArray2[1] = "filterOutUnsafeVariance";
                break;
            }
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                objectArray = objectArray2;
                objectArray2[1] = "combine";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 1: 
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "createChainedSubstitutor";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 31: 
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                break;
            }
            case 7: 
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "safeSubstitute";
                break;
            }
            case 12: 
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "substituteWithoutApproximation";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "unsafeSubstitute";
                break;
            }
            case 23: 
            case 24: 
            case 25: {
                objectArray = objectArray;
                objectArray[2] = "projectedTypeForConflictedTypeWithUnsafeVariance";
                break;
            }
            case 30: {
                objectArray = objectArray;
                objectArray[2] = "filterOutUnsafeVariance";
                break;
            }
            case 32: 
            case 33: 
            case 35: 
            case 36: {
                objectArray = objectArray;
                objectArray[2] = "combine";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 31: 
            case 34: 
            case 37: 
            case 38: 
            case 39: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private static enum VarianceConflictType {
        NO_CONFLICT,
        IN_IN_OUT_POSITION,
        OUT_IN_IN_POSITION;

    }

    private static final class SubstitutionException
    extends Exception {
        public SubstitutionException(String message) {
            super(message);
        }
    }
}

