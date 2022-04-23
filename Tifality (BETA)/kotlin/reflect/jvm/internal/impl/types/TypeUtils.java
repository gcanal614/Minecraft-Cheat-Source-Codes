/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeUtils {
    public static final SimpleType DONT_CARE = ErrorUtils.createErrorTypeWithCustomDebugName("DONT_CARE");
    public static final SimpleType CANT_INFER_FUNCTION_PARAM_TYPE = ErrorUtils.createErrorType("Cannot be inferred");
    @NotNull
    public static final SimpleType NO_EXPECTED_TYPE = new SpecialType("NO_EXPECTED_TYPE");
    public static final SimpleType UNIT_EXPECTED_TYPE = new SpecialType("UNIT_EXPECTED_TYPE");

    public static boolean noExpectedType(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(0);
        }
        return type2 == NO_EXPECTED_TYPE || type2 == UNIT_EXPECTED_TYPE;
    }

    public static boolean isDontCarePlaceholder(@Nullable KotlinType type2) {
        return type2 != null && type2.getConstructor() == DONT_CARE.getConstructor();
    }

    @NotNull
    public static KotlinType makeNullable(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(1);
        }
        return TypeUtils.makeNullableAsSpecified(type2, true);
    }

    @NotNull
    public static KotlinType makeNotNullable(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(2);
        }
        return TypeUtils.makeNullableAsSpecified(type2, false);
    }

    @NotNull
    public static KotlinType makeNullableAsSpecified(@NotNull KotlinType type2, boolean nullable) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(3);
        }
        UnwrappedType unwrappedType = type2.unwrap().makeNullableAsSpecified(nullable);
        if (unwrappedType == null) {
            TypeUtils.$$$reportNull$$$0(4);
        }
        return unwrappedType;
    }

    @NotNull
    public static SimpleType makeNullableIfNeeded(@NotNull SimpleType type2, boolean nullable) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(5);
        }
        if (nullable) {
            SimpleType simpleType2 = type2.makeNullableAsSpecified(true);
            if (simpleType2 == null) {
                TypeUtils.$$$reportNull$$$0(6);
            }
            return simpleType2;
        }
        SimpleType simpleType3 = type2;
        if (simpleType3 == null) {
            TypeUtils.$$$reportNull$$$0(7);
        }
        return simpleType3;
    }

    @NotNull
    public static KotlinType makeNullableIfNeeded(@NotNull KotlinType type2, boolean nullable) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(8);
        }
        if (nullable) {
            return TypeUtils.makeNullable(type2);
        }
        KotlinType kotlinType = type2;
        if (kotlinType == null) {
            TypeUtils.$$$reportNull$$$0(9);
        }
        return kotlinType;
    }

    @NotNull
    public static SimpleType makeUnsubstitutedType(ClassifierDescriptor classifierDescriptor, MemberScope unsubstitutedMemberScope, Function1<KotlinTypeRefiner, SimpleType> refinedTypeFactory) {
        if (ErrorUtils.isError(classifierDescriptor)) {
            SimpleType simpleType2 = ErrorUtils.createErrorType("Unsubstituted type for " + classifierDescriptor);
            if (simpleType2 == null) {
                TypeUtils.$$$reportNull$$$0(11);
            }
            return simpleType2;
        }
        TypeConstructor typeConstructor2 = classifierDescriptor.getTypeConstructor();
        return TypeUtils.makeUnsubstitutedType(typeConstructor2, unsubstitutedMemberScope, refinedTypeFactory);
    }

    @NotNull
    public static SimpleType makeUnsubstitutedType(@NotNull TypeConstructor typeConstructor2, @NotNull MemberScope unsubstitutedMemberScope, @NotNull Function1<KotlinTypeRefiner, SimpleType> refinedTypeFactory) {
        if (typeConstructor2 == null) {
            TypeUtils.$$$reportNull$$$0(12);
        }
        if (unsubstitutedMemberScope == null) {
            TypeUtils.$$$reportNull$$$0(13);
        }
        if (refinedTypeFactory == null) {
            TypeUtils.$$$reportNull$$$0(14);
        }
        List<TypeProjection> arguments2 = TypeUtils.getDefaultTypeProjections(typeConstructor2.getParameters());
        SimpleType simpleType2 = KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), typeConstructor2, arguments2, false, unsubstitutedMemberScope, refinedTypeFactory);
        if (simpleType2 == null) {
            TypeUtils.$$$reportNull$$$0(15);
        }
        return simpleType2;
    }

    @NotNull
    public static List<TypeProjection> getDefaultTypeProjections(@NotNull List<TypeParameterDescriptor> parameters2) {
        if (parameters2 == null) {
            TypeUtils.$$$reportNull$$$0(16);
        }
        ArrayList<TypeProjectionImpl> result2 = new ArrayList<TypeProjectionImpl>(parameters2.size());
        for (TypeParameterDescriptor parameterDescriptor : parameters2) {
            result2.add(new TypeProjectionImpl(parameterDescriptor.getDefaultType()));
        }
        List<TypeProjection> list = CollectionsKt.toList(result2);
        if (list == null) {
            TypeUtils.$$$reportNull$$$0(17);
        }
        return list;
    }

    @NotNull
    public static List<KotlinType> getImmediateSupertypes(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(18);
        }
        TypeSubstitutor substitutor = TypeSubstitutor.create(type2);
        Collection<KotlinType> originalSupertypes = type2.getConstructor().getSupertypes();
        ArrayList<KotlinType> result2 = new ArrayList<KotlinType>(originalSupertypes.size());
        for (KotlinType supertype : originalSupertypes) {
            KotlinType substitutedType = TypeUtils.createSubstitutedSupertype(type2, supertype, substitutor);
            if (substitutedType == null) continue;
            result2.add(substitutedType);
        }
        ArrayList<KotlinType> arrayList = result2;
        if (arrayList == null) {
            TypeUtils.$$$reportNull$$$0(19);
        }
        return arrayList;
    }

    @Nullable
    public static KotlinType createSubstitutedSupertype(@NotNull KotlinType subType, @NotNull KotlinType superType, @NotNull TypeSubstitutor substitutor) {
        KotlinType substitutedType;
        if (subType == null) {
            TypeUtils.$$$reportNull$$$0(20);
        }
        if (superType == null) {
            TypeUtils.$$$reportNull$$$0(21);
        }
        if (substitutor == null) {
            TypeUtils.$$$reportNull$$$0(22);
        }
        if ((substitutedType = substitutor.substitute(superType, Variance.INVARIANT)) != null) {
            return TypeUtils.makeNullableIfNeeded(substitutedType, subType.isMarkedNullable());
        }
        return null;
    }

    public static boolean isNullableType(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(27);
        }
        if (type2.isMarkedNullable()) {
            return true;
        }
        if (FlexibleTypesKt.isFlexible(type2) && TypeUtils.isNullableType(FlexibleTypesKt.asFlexibleType(type2).getUpperBound())) {
            return true;
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(type2)) {
            return false;
        }
        if (TypeUtils.isTypeParameter(type2)) {
            return TypeUtils.hasNullableSuperType(type2);
        }
        TypeConstructor constructor = type2.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            for (KotlinType supertype : constructor.getSupertypes()) {
                if (!TypeUtils.isNullableType(supertype)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean acceptsNullable(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(28);
        }
        if (type2.isMarkedNullable()) {
            return true;
        }
        return FlexibleTypesKt.isFlexible(type2) && TypeUtils.acceptsNullable(FlexibleTypesKt.asFlexibleType(type2).getUpperBound());
    }

    public static boolean hasNullableSuperType(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(29);
        }
        if (type2.getConstructor().getDeclarationDescriptor() instanceof ClassDescriptor) {
            return false;
        }
        for (KotlinType supertype : TypeUtils.getImmediateSupertypes(type2)) {
            if (!TypeUtils.isNullableType(supertype)) continue;
            return true;
        }
        return false;
    }

    @Nullable
    public static ClassDescriptor getClassDescriptor(@NotNull KotlinType type2) {
        ClassifierDescriptor declarationDescriptor;
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(30);
        }
        if ((declarationDescriptor = type2.getConstructor().getDeclarationDescriptor()) instanceof ClassDescriptor) {
            return (ClassDescriptor)declarationDescriptor;
        }
        return null;
    }

    public static boolean contains(@Nullable KotlinType type2, @NotNull Function1<UnwrappedType, Boolean> isSpecialType) {
        if (isSpecialType == null) {
            TypeUtils.$$$reportNull$$$0(43);
        }
        return TypeUtils.contains(type2, isSpecialType, null);
    }

    private static boolean contains(@Nullable KotlinType type2, @NotNull Function1<UnwrappedType, Boolean> isSpecialType, SmartSet<KotlinType> visited) {
        FlexibleType flexibleType;
        if (isSpecialType == null) {
            TypeUtils.$$$reportNull$$$0(44);
        }
        if (type2 == null) {
            return false;
        }
        UnwrappedType unwrappedType = type2.unwrap();
        if (TypeUtils.noExpectedType(type2)) {
            return isSpecialType.invoke(unwrappedType);
        }
        if (visited != null && visited.contains(type2)) {
            return false;
        }
        if (isSpecialType.invoke(unwrappedType).booleanValue()) {
            return true;
        }
        if (visited == null) {
            visited = SmartSet.create();
        }
        visited.add(type2);
        FlexibleType flexibleType2 = flexibleType = unwrappedType instanceof FlexibleType ? (FlexibleType)unwrappedType : null;
        if (flexibleType != null && (TypeUtils.contains(flexibleType.getLowerBound(), isSpecialType, visited) || TypeUtils.contains(flexibleType.getUpperBound(), isSpecialType, visited))) {
            return true;
        }
        if (unwrappedType instanceof DefinitelyNotNullType && TypeUtils.contains(((DefinitelyNotNullType)unwrappedType).getOriginal(), isSpecialType, visited)) {
            return true;
        }
        TypeConstructor typeConstructor2 = type2.getConstructor();
        if (typeConstructor2 instanceof IntersectionTypeConstructor) {
            IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor)typeConstructor2;
            for (KotlinType supertype : intersectionTypeConstructor.getSupertypes()) {
                if (!TypeUtils.contains(supertype, isSpecialType, visited)) continue;
                return true;
            }
            return false;
        }
        for (TypeProjection projection : type2.getArguments()) {
            if (projection.isStarProjection() || !TypeUtils.contains(projection.getType(), isSpecialType, visited)) continue;
            return true;
        }
        return false;
    }

    @NotNull
    public static TypeProjection makeStarProjection(@NotNull TypeParameterDescriptor parameterDescriptor) {
        if (parameterDescriptor == null) {
            TypeUtils.$$$reportNull$$$0(45);
        }
        return new StarProjectionImpl(parameterDescriptor);
    }

    public static boolean isTypeParameter(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(59);
        }
        return TypeUtils.getTypeParameterDescriptorOrNull(type2) != null || type2.getConstructor() instanceof NewTypeVariableConstructor;
    }

    @Nullable
    public static TypeParameterDescriptor getTypeParameterDescriptorOrNull(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeUtils.$$$reportNull$$$0(62);
        }
        if (type2.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor) {
            return (TypeParameterDescriptor)type2.getConstructor().getDeclarationDescriptor();
        }
        return null;
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
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
            case 15: 
            case 17: 
            case 19: 
            case 26: 
            case 35: 
            case 47: 
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
            case 15: 
            case 17: 
            case 19: 
            case 26: 
            case 35: 
            case 47: 
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
            case 15: 
            case 17: 
            case 19: 
            case 26: 
            case 35: 
            case 47: 
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeConstructor";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedMemberScope";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "refinedTypeFactory";
                break;
            }
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameters";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subType";
                break;
            }
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "superType";
                break;
            }
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutor";
                break;
            }
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "result";
                break;
            }
            case 31: 
            case 33: {
                objectArray2 = objectArray3;
                objectArray3[0] = "clazz";
                break;
            }
            case 32: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeArguments";
                break;
            }
            case 34: {
                objectArray2 = objectArray3;
                objectArray3[0] = "projections";
                break;
            }
            case 36: {
                objectArray2 = objectArray3;
                objectArray3[0] = "a";
                break;
            }
            case 37: {
                objectArray2 = objectArray3;
                objectArray3[0] = "b";
                break;
            }
            case 39: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 41: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameterConstructors";
                break;
            }
            case 42: {
                objectArray2 = objectArray3;
                objectArray3[0] = "specialType";
                break;
            }
            case 43: 
            case 44: {
                objectArray2 = objectArray3;
                objectArray3[0] = "isSpecialType";
                break;
            }
            case 45: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameterDescriptor";
                break;
            }
            case 46: 
            case 50: {
                objectArray2 = objectArray3;
                objectArray3[0] = "numberValueTypeConstructor";
                break;
            }
            case 48: 
            case 49: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypes";
                break;
            }
            case 51: 
            case 54: {
                objectArray2 = objectArray3;
                objectArray3[0] = "expectedType";
                break;
            }
            case 53: {
                objectArray2 = objectArray3;
                objectArray3[0] = "literalTypeConstructor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "makeNullableAsSpecified";
                break;
            }
            case 6: 
            case 7: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "makeNullableIfNeeded";
                break;
            }
            case 11: 
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "makeUnsubstitutedType";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultTypeProjections";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getImmediateSupertypes";
                break;
            }
            case 26: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllSupertypes";
                break;
            }
            case 35: {
                objectArray = objectArray2;
                objectArray2[1] = "substituteProjectionsForParameters";
                break;
            }
            case 47: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultPrimitiveNumberType";
                break;
            }
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                objectArray = objectArray2;
                objectArray2[1] = "getPrimitiveNumberType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "noExpectedType";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "makeNullable";
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "makeNotNullable";
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "makeNullableAsSpecified";
                break;
            }
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
            case 15: 
            case 17: 
            case 19: 
            case 26: 
            case 35: 
            case 47: 
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                break;
            }
            case 5: 
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "makeNullableIfNeeded";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "canHaveSubtypes";
                break;
            }
            case 12: 
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "makeUnsubstitutedType";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "getDefaultTypeProjections";
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "getImmediateSupertypes";
                break;
            }
            case 20: 
            case 21: 
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "createSubstitutedSupertype";
                break;
            }
            case 23: 
            case 24: {
                objectArray = objectArray;
                objectArray[2] = "collectAllSupertypes";
                break;
            }
            case 25: {
                objectArray = objectArray;
                objectArray[2] = "getAllSupertypes";
                break;
            }
            case 27: {
                objectArray = objectArray;
                objectArray[2] = "isNullableType";
                break;
            }
            case 28: {
                objectArray = objectArray;
                objectArray[2] = "acceptsNullable";
                break;
            }
            case 29: {
                objectArray = objectArray;
                objectArray[2] = "hasNullableSuperType";
                break;
            }
            case 30: {
                objectArray = objectArray;
                objectArray[2] = "getClassDescriptor";
                break;
            }
            case 31: 
            case 32: {
                objectArray = objectArray;
                objectArray[2] = "substituteParameters";
                break;
            }
            case 33: 
            case 34: {
                objectArray = objectArray;
                objectArray[2] = "substituteProjectionsForParameters";
                break;
            }
            case 36: 
            case 37: {
                objectArray = objectArray;
                objectArray[2] = "equalTypes";
                break;
            }
            case 38: 
            case 39: {
                objectArray = objectArray;
                objectArray[2] = "dependsOnTypeParameters";
                break;
            }
            case 40: 
            case 41: {
                objectArray = objectArray;
                objectArray[2] = "dependsOnTypeConstructors";
                break;
            }
            case 42: 
            case 43: 
            case 44: {
                objectArray = objectArray;
                objectArray[2] = "contains";
                break;
            }
            case 45: {
                objectArray = objectArray;
                objectArray[2] = "makeStarProjection";
                break;
            }
            case 46: 
            case 48: {
                objectArray = objectArray;
                objectArray[2] = "getDefaultPrimitiveNumberType";
                break;
            }
            case 49: {
                objectArray = objectArray;
                objectArray[2] = "findByFqName";
                break;
            }
            case 50: 
            case 51: 
            case 53: 
            case 54: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveNumberType";
                break;
            }
            case 59: {
                objectArray = objectArray;
                objectArray[2] = "isTypeParameter";
                break;
            }
            case 60: {
                objectArray = objectArray;
                objectArray[2] = "isReifiedTypeParameter";
                break;
            }
            case 61: {
                objectArray = objectArray;
                objectArray[2] = "isNonReifiedTypeParameter";
                break;
            }
            case 62: {
                objectArray = objectArray;
                objectArray[2] = "getTypeParameterDescriptorOrNull";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
            case 15: 
            case 17: 
            case 19: 
            case 26: 
            case 35: 
            case 47: 
            case 52: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public static class SpecialType
    extends DelegatingSimpleType {
        private final String name;

        public SpecialType(String name) {
            this.name = name;
        }

        @Override
        @NotNull
        protected SimpleType getDelegate() {
            throw new IllegalStateException(this.name);
        }

        @Override
        @NotNull
        public SimpleType replaceAnnotations(@NotNull Annotations newAnnotations) {
            if (newAnnotations == null) {
                SpecialType.$$$reportNull$$$0(0);
            }
            throw new IllegalStateException(this.name);
        }

        @Override
        @NotNull
        public SimpleType makeNullableAsSpecified(boolean newNullability) {
            throw new IllegalStateException(this.name);
        }

        @Override
        @NotNull
        public String toString() {
            String string = this.name;
            if (string == null) {
                SpecialType.$$$reportNull$$$0(1);
            }
            return string;
        }

        @Override
        @NotNull
        public DelegatingSimpleType replaceDelegate(@NotNull SimpleType delegate) {
            if (delegate == null) {
                SpecialType.$$$reportNull$$$0(2);
            }
            throw new IllegalStateException(this.name);
        }

        @Override
        @NotNull
        public SpecialType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            if (kotlinTypeRefiner == null) {
                SpecialType.$$$reportNull$$$0(3);
            }
            SpecialType specialType = this;
            if (specialType == null) {
                SpecialType.$$$reportNull$$$0(4);
            }
            return specialType;
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
                case 4: {
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
                case 4: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newAnnotations";
                    break;
                }
                case 1: 
                case 4: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils$SpecialType";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "delegate";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlinTypeRefiner";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils$SpecialType";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "toString";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "refine";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "replaceAnnotations";
                    break;
                }
                case 1: 
                case 4: {
                    break;
                }
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "replaceDelegate";
                    break;
                }
                case 3: {
                    objectArray = objectArray;
                    objectArray[2] = "refine";
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
                case 4: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

