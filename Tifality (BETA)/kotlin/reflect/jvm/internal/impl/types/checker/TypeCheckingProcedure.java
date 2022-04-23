/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeCapabilitiesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerProcedureCallbacksImpl;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks;
import kotlin.reflect.jvm.internal.impl.types.checker.UtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeCheckingProcedure {
    private final TypeCheckingProcedureCallbacks constraints;

    @Nullable
    public static KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (subtype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(0);
        }
        if (supertype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(1);
        }
        return TypeCheckingProcedure.findCorrespondingSupertype(subtype, supertype, new TypeCheckerProcedureCallbacksImpl());
    }

    @Nullable
    public static KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype, @NotNull TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        if (subtype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(2);
        }
        if (supertype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(3);
        }
        if (typeCheckingProcedureCallbacks == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(4);
        }
        return UtilsKt.findCorrespondingSupertype(subtype, supertype, typeCheckingProcedureCallbacks);
    }

    @NotNull
    private static KotlinType getOutType(@NotNull TypeParameterDescriptor parameter, @NotNull TypeProjection argument) {
        if (parameter == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(5);
        }
        if (argument == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(6);
        }
        boolean isInProjected = argument.getProjectionKind() == Variance.IN_VARIANCE || parameter.getVariance() == Variance.IN_VARIANCE;
        KotlinType kotlinType = isInProjected ? DescriptorUtilsKt.getBuiltIns(parameter).getNullableAnyType() : argument.getType();
        if (kotlinType == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(7);
        }
        return kotlinType;
    }

    @NotNull
    private static KotlinType getInType(@NotNull TypeParameterDescriptor parameter, @NotNull TypeProjection argument) {
        if (parameter == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(8);
        }
        if (argument == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(9);
        }
        boolean isOutProjected = argument.getProjectionKind() == Variance.OUT_VARIANCE || parameter.getVariance() == Variance.OUT_VARIANCE;
        KotlinType kotlinType = isOutProjected ? DescriptorUtilsKt.getBuiltIns(parameter).getNothingType() : argument.getType();
        if (kotlinType == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(10);
        }
        return kotlinType;
    }

    public TypeCheckingProcedure(TypeCheckingProcedureCallbacks constraints) {
        this.constraints = constraints;
    }

    public boolean equalTypes(@NotNull KotlinType type1, @NotNull KotlinType type2) {
        TypeConstructor constructor2;
        if (type1 == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(11);
        }
        if (type2 == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(12);
        }
        if (type1 == type2) {
            return true;
        }
        if (FlexibleTypesKt.isFlexible(type1)) {
            if (FlexibleTypesKt.isFlexible(type2)) {
                return !KotlinTypeKt.isError(type1) && !KotlinTypeKt.isError(type2) && this.isSubtypeOf(type1, type2) && this.isSubtypeOf(type2, type1);
            }
            return this.heterogeneousEquivalence(type2, type1);
        }
        if (FlexibleTypesKt.isFlexible(type2)) {
            return this.heterogeneousEquivalence(type1, type2);
        }
        if (type1.isMarkedNullable() != type2.isMarkedNullable()) {
            return false;
        }
        if (type1.isMarkedNullable()) {
            return this.constraints.assertEqualTypes(TypeUtils.makeNotNullable(type1), TypeUtils.makeNotNullable(type2), this);
        }
        TypeConstructor constructor1 = type1.getConstructor();
        if (!this.constraints.assertEqualTypeConstructors(constructor1, constructor2 = type2.getConstructor())) {
            return false;
        }
        List<TypeProjection> type1Arguments = type1.getArguments();
        List<TypeProjection> type2Arguments = type2.getArguments();
        if (type1Arguments.size() != type2Arguments.size()) {
            return false;
        }
        for (int i = 0; i < type1Arguments.size(); ++i) {
            TypeProjection typeProjection1 = type1Arguments.get(i);
            TypeProjection typeProjection2 = type2Arguments.get(i);
            if (typeProjection1.isStarProjection() && typeProjection2.isStarProjection()) continue;
            TypeParameterDescriptor typeParameter1 = constructor1.getParameters().get(i);
            TypeParameterDescriptor typeParameter2 = constructor2.getParameters().get(i);
            if (this.capture(typeProjection1, typeProjection2, typeParameter1)) continue;
            if (TypeCheckingProcedure.getEffectiveProjectionKind(typeParameter1, typeProjection1) != TypeCheckingProcedure.getEffectiveProjectionKind(typeParameter2, typeProjection2)) {
                return false;
            }
            if (this.constraints.assertEqualTypes(typeProjection1.getType(), typeProjection2.getType(), this)) continue;
            return false;
        }
        return true;
    }

    protected boolean heterogeneousEquivalence(KotlinType inflexibleType, KotlinType flexibleType) {
        assert (!FlexibleTypesKt.isFlexible(inflexibleType)) : "Only inflexible types are allowed here: " + inflexibleType;
        return this.isSubtypeOf(FlexibleTypesKt.asFlexibleType(flexibleType).getLowerBound(), inflexibleType) && this.isSubtypeOf(inflexibleType, FlexibleTypesKt.asFlexibleType(flexibleType).getUpperBound());
    }

    public static EnrichedProjectionKind getEffectiveProjectionKind(@NotNull TypeParameterDescriptor typeParameter, @NotNull TypeProjection typeArgument) {
        if (typeParameter == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(13);
        }
        if (typeArgument == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(14);
        }
        Variance a2 = typeParameter.getVariance();
        Variance b2 = typeArgument.getProjectionKind();
        if (b2 == Variance.INVARIANT) {
            Variance t = a2;
            a2 = b2;
            b2 = t;
        }
        if (a2 == Variance.IN_VARIANCE && b2 == Variance.OUT_VARIANCE) {
            return EnrichedProjectionKind.STAR;
        }
        if (a2 == Variance.OUT_VARIANCE && b2 == Variance.IN_VARIANCE) {
            return EnrichedProjectionKind.STAR;
        }
        return EnrichedProjectionKind.fromVariance(b2);
    }

    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (subtype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(15);
        }
        if (supertype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(16);
        }
        if (TypeCapabilitiesKt.sameTypeConstructors(subtype, supertype)) {
            return !subtype.isMarkedNullable() || supertype.isMarkedNullable();
        }
        KotlinType subtypeRepresentative = TypeCapabilitiesKt.getSubtypeRepresentative(subtype);
        KotlinType supertypeRepresentative = TypeCapabilitiesKt.getSupertypeRepresentative(supertype);
        if (subtypeRepresentative != subtype || supertypeRepresentative != supertype) {
            return this.isSubtypeOf(subtypeRepresentative, supertypeRepresentative);
        }
        return this.isSubtypeOfForRepresentatives(subtype, supertype);
    }

    private boolean isSubtypeOfForRepresentatives(KotlinType subtype, KotlinType supertype) {
        if (KotlinTypeKt.isError(subtype) || KotlinTypeKt.isError(supertype)) {
            return true;
        }
        if (!supertype.isMarkedNullable() && subtype.isMarkedNullable()) {
            return false;
        }
        if (KotlinBuiltIns.isNothingOrNullableNothing(subtype)) {
            return true;
        }
        KotlinType closestSupertype = TypeCheckingProcedure.findCorrespondingSupertype(subtype, supertype, this.constraints);
        if (closestSupertype == null) {
            return this.constraints.noCorrespondingSupertype(subtype, supertype);
        }
        if (!supertype.isMarkedNullable() && closestSupertype.isMarkedNullable()) {
            return false;
        }
        return this.checkSubtypeForTheSameConstructor(closestSupertype, supertype);
    }

    private boolean checkSubtypeForTheSameConstructor(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (subtype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(17);
        }
        if (supertype == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(18);
        }
        TypeConstructor constructor = subtype.getConstructor();
        List<TypeProjection> subArguments = subtype.getArguments();
        List<TypeProjection> superArguments = supertype.getArguments();
        if (subArguments.size() != superArguments.size()) {
            return false;
        }
        List<TypeParameterDescriptor> parameters2 = constructor.getParameters();
        for (int i = 0; i < parameters2.size(); ++i) {
            boolean argumentIsErrorType;
            TypeParameterDescriptor parameter = parameters2.get(i);
            TypeProjection superArgument = superArguments.get(i);
            TypeProjection subArgument = subArguments.get(i);
            if (superArgument.isStarProjection() || this.capture(subArgument, superArgument, parameter)) continue;
            boolean bl = argumentIsErrorType = KotlinTypeKt.isError(subArgument.getType()) || KotlinTypeKt.isError(superArgument.getType());
            if (!argumentIsErrorType && parameter.getVariance() == Variance.INVARIANT && subArgument.getProjectionKind() == Variance.INVARIANT && superArgument.getProjectionKind() == Variance.INVARIANT) {
                if (this.constraints.assertEqualTypes(subArgument.getType(), superArgument.getType(), this)) continue;
                return false;
            }
            KotlinType superOut = TypeCheckingProcedure.getOutType(parameter, superArgument);
            KotlinType subOut = TypeCheckingProcedure.getOutType(parameter, subArgument);
            if (!this.constraints.assertSubtype(subOut, superOut, this)) {
                return false;
            }
            KotlinType superIn = TypeCheckingProcedure.getInType(parameter, superArgument);
            KotlinType subIn = TypeCheckingProcedure.getInType(parameter, subArgument);
            if (superArgument.getProjectionKind() != Variance.OUT_VARIANCE) {
                if (this.constraints.assertSubtype(superIn, subIn, this)) continue;
                return false;
            }
            assert (KotlinBuiltIns.isNothing(superIn)) : "In component must be Nothing for out-projection";
        }
        return true;
    }

    private boolean capture(@NotNull TypeProjection subtypeArgumentProjection, @NotNull TypeProjection supertypeArgumentProjection, @NotNull TypeParameterDescriptor parameter) {
        if (subtypeArgumentProjection == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(19);
        }
        if (supertypeArgumentProjection == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(20);
        }
        if (parameter == null) {
            TypeCheckingProcedure.$$$reportNull$$$0(21);
        }
        if (parameter.getVariance() != Variance.INVARIANT) {
            return false;
        }
        if (subtypeArgumentProjection.getProjectionKind() != Variance.INVARIANT && supertypeArgumentProjection.getProjectionKind() == Variance.INVARIANT) {
            return this.constraints.capture(supertypeArgumentProjection.getType(), subtypeArgumentProjection);
        }
        return false;
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
            case 7: 
            case 10: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 7: 
            case 10: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subtype";
                break;
            }
            case 1: 
            case 3: 
            case 16: 
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertype";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeCheckingProcedureCallbacks";
                break;
            }
            case 5: 
            case 8: 
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameter";
                break;
            }
            case 6: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "argument";
                break;
            }
            case 7: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure";
                break;
            }
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type1";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type2";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameter";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeArgument";
                break;
            }
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subtypeArgumentProjection";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypeArgumentProjection";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getOutType";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getInType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "findCorrespondingSupertype";
                break;
            }
            case 5: 
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "getOutType";
                break;
            }
            case 7: 
            case 10: {
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "getInType";
                break;
            }
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "equalTypes";
                break;
            }
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "getEffectiveProjectionKind";
                break;
            }
            case 15: 
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "isSubtypeOf";
                break;
            }
            case 17: 
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "checkSubtypeForTheSameConstructor";
                break;
            }
            case 19: 
            case 20: 
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "capture";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 7: 
            case 10: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public static enum EnrichedProjectionKind {
        IN,
        OUT,
        INV,
        STAR;


        @NotNull
        public static EnrichedProjectionKind fromVariance(@NotNull Variance variance) {
            if (variance == null) {
                EnrichedProjectionKind.$$$reportNull$$$0(0);
            }
            switch (variance) {
                case INVARIANT: {
                    EnrichedProjectionKind enrichedProjectionKind = INV;
                    if (enrichedProjectionKind == null) {
                        EnrichedProjectionKind.$$$reportNull$$$0(1);
                    }
                    return enrichedProjectionKind;
                }
                case IN_VARIANCE: {
                    EnrichedProjectionKind enrichedProjectionKind = IN;
                    if (enrichedProjectionKind == null) {
                        EnrichedProjectionKind.$$$reportNull$$$0(2);
                    }
                    return enrichedProjectionKind;
                }
                case OUT_VARIANCE: {
                    EnrichedProjectionKind enrichedProjectionKind = OUT;
                    if (enrichedProjectionKind == null) {
                        EnrichedProjectionKind.$$$reportNull$$$0(3);
                    }
                    return enrichedProjectionKind;
                }
            }
            throw new IllegalStateException("Unknown variance");
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
                case 1: 
                case 2: 
                case 3: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "variance";
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure$EnrichedProjectionKind";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure$EnrichedProjectionKind";
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "fromVariance";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "fromVariance";
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

