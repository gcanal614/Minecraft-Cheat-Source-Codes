/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerContextKt;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClassicTypeCheckerContext
extends AbstractTypeCheckerContext
implements ClassicTypeSystemContext {
    private final boolean errorTypeEqualsToAnything;
    private final boolean stubTypeEqualsToAnything;
    private final boolean allowedTypeVariable;
    @NotNull
    private final KotlinTypeRefiner kotlinTypeRefiner;
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public KotlinTypeMarker prepareType(@NotNull KotlinTypeMarker type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        boolean bl = type2 instanceof KotlinType;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = ClassicTypeCheckerContextKt.access$errorMessage(type2);
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return NewKotlinTypeChecker.Companion.getDefault().transformToNewType(((KotlinType)type2).unwrap());
    }

    @Override
    @NotNull
    public KotlinTypeMarker refineType(@NotNull KotlinTypeMarker type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        boolean bl = type2 instanceof KotlinType;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = ClassicTypeCheckerContextKt.access$errorMessage(type2);
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return this.kotlinTypeRefiner.refineType((KotlinType)type2);
    }

    @Override
    public boolean isErrorTypeEqualsToAnything() {
        return this.errorTypeEqualsToAnything;
    }

    @Override
    public boolean isStubTypeEqualsToAnything() {
        return this.stubTypeEqualsToAnything;
    }

    @Override
    public boolean areEqualTypeConstructors(@NotNull TypeConstructorMarker a2, @NotNull TypeConstructorMarker b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        boolean bl = a2 instanceof TypeConstructor;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = ClassicTypeCheckerContextKt.access$errorMessage(a2);
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        bl = b2 instanceof TypeConstructor;
        bl2 = false;
        bl3 = false;
        if (!bl) {
            boolean bl5 = false;
            String string = ClassicTypeCheckerContextKt.access$errorMessage(b2);
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return this.areEqualTypeConstructors((TypeConstructor)a2, (TypeConstructor)b2);
    }

    public boolean areEqualTypeConstructors(@NotNull TypeConstructor a2, @NotNull TypeConstructor b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return a2 instanceof IntegerLiteralTypeConstructor ? ((IntegerLiteralTypeConstructor)a2).checkConstructor(b2) : (b2 instanceof IntegerLiteralTypeConstructor ? ((IntegerLiteralTypeConstructor)b2).checkConstructor(a2) : Intrinsics.areEqual(a2, b2));
    }

    @Override
    @NotNull
    public AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform substitutionSupertypePolicy(@NotNull SimpleTypeMarker type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return Companion.classicSubstitutionSupertypePolicy(this, type2);
    }

    @Override
    public boolean isAllowedTypeVariable(@NotNull KotlinTypeMarker $this$isAllowedTypeVariable) {
        Intrinsics.checkNotNullParameter($this$isAllowedTypeVariable, "$this$isAllowedTypeVariable");
        return $this$isAllowedTypeVariable instanceof UnwrappedType && this.allowedTypeVariable && ((UnwrappedType)$this$isAllowedTypeVariable).getConstructor() instanceof NewTypeVariableConstructor;
    }

    public ClassicTypeCheckerContext(boolean errorTypeEqualsToAnything, boolean stubTypeEqualsToAnything, boolean allowedTypeVariable, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.errorTypeEqualsToAnything = errorTypeEqualsToAnything;
        this.stubTypeEqualsToAnything = stubTypeEqualsToAnything;
        this.allowedTypeVariable = allowedTypeVariable;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    public /* synthetic */ ClassicTypeCheckerContext(boolean bl, boolean bl2, boolean bl3, KotlinTypeRefiner kotlinTypeRefiner, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl2 = true;
        }
        if ((n & 4) != 0) {
            bl3 = true;
        }
        if ((n & 8) != 0) {
            kotlinTypeRefiner = KotlinTypeRefiner.Default.INSTANCE;
        }
        this(bl, bl2, bl3, kotlinTypeRefiner);
    }

    @Override
    public boolean isDenotable(@NotNull TypeConstructorMarker $this$isDenotable) {
        Intrinsics.checkNotNullParameter($this$isDenotable, "$this$isDenotable");
        return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, $this$isDenotable);
    }

    @Override
    public boolean isIntegerLiteralTypeConstructor(@NotNull TypeConstructorMarker $this$isIntegerLiteralTypeConstructor) {
        Intrinsics.checkNotNullParameter($this$isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, $this$isIntegerLiteralTypeConstructor);
    }

    @Override
    @NotNull
    public Collection<KotlinTypeMarker> possibleIntegerTypes(@NotNull SimpleTypeMarker $this$possibleIntegerTypes) {
        Intrinsics.checkNotNullParameter($this$possibleIntegerTypes, "$this$possibleIntegerTypes");
        return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, $this$possibleIntegerTypes);
    }

    @Override
    @NotNull
    public SimpleTypeMarker withNullability(@NotNull SimpleTypeMarker $this$withNullability, boolean nullable) {
        Intrinsics.checkNotNullParameter($this$withNullability, "$this$withNullability");
        return ClassicTypeSystemContext.DefaultImpls.withNullability(this, $this$withNullability, nullable);
    }

    @Override
    public boolean isError(@NotNull KotlinTypeMarker $this$isError) {
        Intrinsics.checkNotNullParameter($this$isError, "$this$isError");
        return ClassicTypeSystemContext.DefaultImpls.isError(this, $this$isError);
    }

    @Override
    public boolean isStubType(@NotNull SimpleTypeMarker $this$isStubType) {
        Intrinsics.checkNotNullParameter($this$isStubType, "$this$isStubType");
        return ClassicTypeSystemContext.DefaultImpls.isStubType(this, $this$isStubType);
    }

    @Override
    @Nullable
    public KotlinTypeMarker lowerType(@NotNull CapturedTypeMarker $this$lowerType) {
        Intrinsics.checkNotNullParameter($this$lowerType, "$this$lowerType");
        return ClassicTypeSystemContext.DefaultImpls.lowerType(this, $this$lowerType);
    }

    @Override
    public boolean isIntersection(@NotNull TypeConstructorMarker $this$isIntersection) {
        Intrinsics.checkNotNullParameter($this$isIntersection, "$this$isIntersection");
        return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, $this$isIntersection);
    }

    @Override
    public boolean identicalArguments(@NotNull SimpleTypeMarker a2, @NotNull SimpleTypeMarker b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, a2, b2);
    }

    @Override
    @Nullable
    public SimpleTypeMarker asSimpleType(@NotNull KotlinTypeMarker $this$asSimpleType) {
        Intrinsics.checkNotNullParameter($this$asSimpleType, "$this$asSimpleType");
        return ClassicTypeSystemContext.DefaultImpls.asSimpleType(this, $this$asSimpleType);
    }

    @Override
    @Nullable
    public FlexibleTypeMarker asFlexibleType(@NotNull KotlinTypeMarker $this$asFlexibleType) {
        Intrinsics.checkNotNullParameter($this$asFlexibleType, "$this$asFlexibleType");
        return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, $this$asFlexibleType);
    }

    @Override
    @Nullable
    public DynamicTypeMarker asDynamicType(@NotNull FlexibleTypeMarker $this$asDynamicType) {
        Intrinsics.checkNotNullParameter($this$asDynamicType, "$this$asDynamicType");
        return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, $this$asDynamicType);
    }

    @Override
    @NotNull
    public SimpleTypeMarker upperBound(@NotNull FlexibleTypeMarker $this$upperBound) {
        Intrinsics.checkNotNullParameter($this$upperBound, "$this$upperBound");
        return ClassicTypeSystemContext.DefaultImpls.upperBound(this, $this$upperBound);
    }

    @Override
    @NotNull
    public SimpleTypeMarker lowerBound(@NotNull FlexibleTypeMarker $this$lowerBound) {
        Intrinsics.checkNotNullParameter($this$lowerBound, "$this$lowerBound");
        return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, $this$lowerBound);
    }

    @Override
    @Nullable
    public CapturedTypeMarker asCapturedType(@NotNull SimpleTypeMarker $this$asCapturedType) {
        Intrinsics.checkNotNullParameter($this$asCapturedType, "$this$asCapturedType");
        return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, $this$asCapturedType);
    }

    @Override
    @Nullable
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(@NotNull SimpleTypeMarker $this$asDefinitelyNotNullType) {
        Intrinsics.checkNotNullParameter($this$asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
        return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, $this$asDefinitelyNotNullType);
    }

    @Override
    public boolean isMarkedNullable(@NotNull SimpleTypeMarker $this$isMarkedNullable) {
        Intrinsics.checkNotNullParameter($this$isMarkedNullable, "$this$isMarkedNullable");
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable((ClassicTypeSystemContext)this, $this$isMarkedNullable);
    }

    @Override
    public boolean isMarkedNullable(@NotNull KotlinTypeMarker $this$isMarkedNullable) {
        Intrinsics.checkNotNullParameter($this$isMarkedNullable, "$this$isMarkedNullable");
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable((ClassicTypeSystemContext)this, $this$isMarkedNullable);
    }

    @Override
    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull SimpleTypeMarker $this$typeConstructor) {
        Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext)this, $this$typeConstructor);
    }

    @Override
    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull KotlinTypeMarker $this$typeConstructor) {
        Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext)this, $this$typeConstructor);
    }

    @Override
    public int argumentsCount(@NotNull KotlinTypeMarker $this$argumentsCount) {
        Intrinsics.checkNotNullParameter($this$argumentsCount, "$this$argumentsCount");
        return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, $this$argumentsCount);
    }

    @Override
    @NotNull
    public TypeArgumentMarker getArgument(@NotNull KotlinTypeMarker $this$getArgument, int index) {
        Intrinsics.checkNotNullParameter($this$getArgument, "$this$getArgument");
        return ClassicTypeSystemContext.DefaultImpls.getArgument(this, $this$getArgument, index);
    }

    @Override
    public boolean isStarProjection(@NotNull TypeArgumentMarker $this$isStarProjection) {
        Intrinsics.checkNotNullParameter($this$isStarProjection, "$this$isStarProjection");
        return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, $this$isStarProjection);
    }

    @Override
    @NotNull
    public TypeVariance getVariance(@NotNull TypeArgumentMarker $this$getVariance) {
        Intrinsics.checkNotNullParameter($this$getVariance, "$this$getVariance");
        return ClassicTypeSystemContext.DefaultImpls.getVariance((ClassicTypeSystemContext)this, $this$getVariance);
    }

    @Override
    @NotNull
    public TypeVariance getVariance(@NotNull TypeParameterMarker $this$getVariance) {
        Intrinsics.checkNotNullParameter($this$getVariance, "$this$getVariance");
        return ClassicTypeSystemContext.DefaultImpls.getVariance((ClassicTypeSystemContext)this, $this$getVariance);
    }

    @Override
    @NotNull
    public KotlinTypeMarker getType(@NotNull TypeArgumentMarker $this$getType) {
        Intrinsics.checkNotNullParameter($this$getType, "$this$getType");
        return ClassicTypeSystemContext.DefaultImpls.getType(this, $this$getType);
    }

    @Override
    public int parametersCount(@NotNull TypeConstructorMarker $this$parametersCount) {
        Intrinsics.checkNotNullParameter($this$parametersCount, "$this$parametersCount");
        return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, $this$parametersCount);
    }

    @Override
    @NotNull
    public TypeParameterMarker getParameter(@NotNull TypeConstructorMarker $this$getParameter, int index) {
        Intrinsics.checkNotNullParameter($this$getParameter, "$this$getParameter");
        return ClassicTypeSystemContext.DefaultImpls.getParameter(this, $this$getParameter, index);
    }

    @Override
    @NotNull
    public Collection<KotlinTypeMarker> supertypes(@NotNull TypeConstructorMarker $this$supertypes) {
        Intrinsics.checkNotNullParameter($this$supertypes, "$this$supertypes");
        return ClassicTypeSystemContext.DefaultImpls.supertypes(this, $this$supertypes);
    }

    @Override
    public boolean isEqualTypeConstructors(@NotNull TypeConstructorMarker c1, @NotNull TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return ClassicTypeSystemContext.DefaultImpls.isEqualTypeConstructors(this, c1, c2);
    }

    @Override
    public boolean isClassTypeConstructor(@NotNull TypeConstructorMarker $this$isClassTypeConstructor) {
        Intrinsics.checkNotNullParameter($this$isClassTypeConstructor, "$this$isClassTypeConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, $this$isClassTypeConstructor);
    }

    @Override
    public boolean isCommonFinalClassConstructor(@NotNull TypeConstructorMarker $this$isCommonFinalClassConstructor) {
        Intrinsics.checkNotNullParameter($this$isCommonFinalClassConstructor, "$this$isCommonFinalClassConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, $this$isCommonFinalClassConstructor);
    }

    @Override
    @NotNull
    public TypeArgumentListMarker asArgumentList(@NotNull SimpleTypeMarker $this$asArgumentList) {
        Intrinsics.checkNotNullParameter($this$asArgumentList, "$this$asArgumentList");
        return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, $this$asArgumentList);
    }

    @Override
    @Nullable
    public SimpleTypeMarker captureFromArguments(@NotNull SimpleTypeMarker type2, @NotNull CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type2, "type");
        Intrinsics.checkNotNullParameter((Object)status, "status");
        return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, type2, status);
    }

    @Override
    public boolean isAnyConstructor(@NotNull TypeConstructorMarker $this$isAnyConstructor) {
        Intrinsics.checkNotNullParameter($this$isAnyConstructor, "$this$isAnyConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, $this$isAnyConstructor);
    }

    @Override
    public boolean isNothingConstructor(@NotNull TypeConstructorMarker $this$isNothingConstructor) {
        Intrinsics.checkNotNullParameter($this$isNothingConstructor, "$this$isNothingConstructor");
        return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, $this$isNothingConstructor);
    }

    @Override
    @NotNull
    public TypeArgumentMarker asTypeArgument(@NotNull KotlinTypeMarker $this$asTypeArgument) {
        Intrinsics.checkNotNullParameter($this$asTypeArgument, "$this$asTypeArgument");
        return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, $this$asTypeArgument);
    }

    @Override
    public boolean isSingleClassifierType(@NotNull SimpleTypeMarker $this$isSingleClassifierType) {
        Intrinsics.checkNotNullParameter($this$isSingleClassifierType, "$this$isSingleClassifierType");
        return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, $this$isSingleClassifierType);
    }

    @Override
    @NotNull
    public KotlinTypeMarker intersectTypes(@NotNull List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, types);
    }

    @Override
    public boolean isProjectionNotNull(@NotNull CapturedTypeMarker $this$isProjectionNotNull) {
        Intrinsics.checkNotNullParameter($this$isProjectionNotNull, "$this$isProjectionNotNull");
        return ClassicTypeSystemContext.DefaultImpls.isProjectionNotNull(this, $this$isProjectionNotNull);
    }

    @Override
    public boolean isNullableType(@NotNull KotlinTypeMarker $this$isNullableType) {
        Intrinsics.checkNotNullParameter($this$isNullableType, "$this$isNullableType");
        return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, $this$isNullableType);
    }

    @Override
    public boolean isPrimitiveType(@NotNull SimpleTypeMarker $this$isPrimitiveType) {
        Intrinsics.checkNotNullParameter($this$isPrimitiveType, "$this$isPrimitiveType");
        return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, $this$isPrimitiveType);
    }

    @Override
    public boolean hasAnnotation(@NotNull KotlinTypeMarker $this$hasAnnotation, @NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter($this$hasAnnotation, "$this$hasAnnotation");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, $this$hasAnnotation, fqName2);
    }

    @Override
    @Nullable
    public TypeParameterMarker getTypeParameterClassifier(@NotNull TypeConstructorMarker $this$getTypeParameterClassifier) {
        Intrinsics.checkNotNullParameter($this$getTypeParameterClassifier, "$this$getTypeParameterClassifier");
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, $this$getTypeParameterClassifier);
    }

    @Override
    public boolean isInlineClass(@NotNull TypeConstructorMarker $this$isInlineClass) {
        Intrinsics.checkNotNullParameter($this$isInlineClass, "$this$isInlineClass");
        return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, $this$isInlineClass);
    }

    @Override
    @NotNull
    public KotlinTypeMarker getRepresentativeUpperBound(@NotNull TypeParameterMarker $this$getRepresentativeUpperBound) {
        Intrinsics.checkNotNullParameter($this$getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
        return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, $this$getRepresentativeUpperBound);
    }

    @Override
    @Nullable
    public KotlinTypeMarker getSubstitutedUnderlyingType(@NotNull KotlinTypeMarker $this$getSubstitutedUnderlyingType) {
        Intrinsics.checkNotNullParameter($this$getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
        return ClassicTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, $this$getSubstitutedUnderlyingType);
    }

    @Override
    @Nullable
    public PrimitiveType getPrimitiveType(@NotNull TypeConstructorMarker $this$getPrimitiveType) {
        Intrinsics.checkNotNullParameter($this$getPrimitiveType, "$this$getPrimitiveType");
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, $this$getPrimitiveType);
    }

    @Override
    @Nullable
    public PrimitiveType getPrimitiveArrayType(@NotNull TypeConstructorMarker $this$getPrimitiveArrayType) {
        Intrinsics.checkNotNullParameter($this$getPrimitiveArrayType, "$this$getPrimitiveArrayType");
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, $this$getPrimitiveArrayType);
    }

    @Override
    public boolean isUnderKotlinPackage(@NotNull TypeConstructorMarker $this$isUnderKotlinPackage) {
        Intrinsics.checkNotNullParameter($this$isUnderKotlinPackage, "$this$isUnderKotlinPackage");
        return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, $this$isUnderKotlinPackage);
    }

    @Override
    @NotNull
    public FqNameUnsafe getClassFqNameUnsafe(@NotNull TypeConstructorMarker $this$getClassFqNameUnsafe) {
        Intrinsics.checkNotNullParameter($this$getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
        return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, $this$getClassFqNameUnsafe);
    }

    @Override
    @Nullable
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(@NotNull SimpleTypeMarker $this$fastCorrespondingSupertypes, @NotNull TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return ClassicTypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, $this$fastCorrespondingSupertypes, constructor);
    }

    @Override
    @NotNull
    public TypeArgumentMarker get(@NotNull TypeArgumentListMarker $this$get, int index) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        return ClassicTypeSystemContext.DefaultImpls.get(this, $this$get, index);
    }

    @Override
    @Nullable
    public TypeArgumentMarker getArgumentOrNull(@NotNull SimpleTypeMarker $this$getArgumentOrNull, int index) {
        Intrinsics.checkNotNullParameter($this$getArgumentOrNull, "$this$getArgumentOrNull");
        return ClassicTypeSystemContext.DefaultImpls.getArgumentOrNull(this, $this$getArgumentOrNull, index);
    }

    @Override
    public boolean hasFlexibleNullability(@NotNull KotlinTypeMarker $this$hasFlexibleNullability) {
        Intrinsics.checkNotNullParameter($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
        return ClassicTypeSystemContext.DefaultImpls.hasFlexibleNullability(this, $this$hasFlexibleNullability);
    }

    @Override
    public boolean isClassType(@NotNull SimpleTypeMarker $this$isClassType) {
        Intrinsics.checkNotNullParameter($this$isClassType, "$this$isClassType");
        return ClassicTypeSystemContext.DefaultImpls.isClassType(this, $this$isClassType);
    }

    @Override
    public boolean isDefinitelyNotNullType(@NotNull KotlinTypeMarker $this$isDefinitelyNotNullType) {
        Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return ClassicTypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, $this$isDefinitelyNotNullType);
    }

    @Override
    public boolean isDynamic(@NotNull KotlinTypeMarker $this$isDynamic) {
        Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
        return ClassicTypeSystemContext.DefaultImpls.isDynamic(this, $this$isDynamic);
    }

    @Override
    public boolean isIntegerLiteralType(@NotNull SimpleTypeMarker $this$isIntegerLiteralType) {
        Intrinsics.checkNotNullParameter($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralType(this, $this$isIntegerLiteralType);
    }

    @Override
    public boolean isNothing(@NotNull KotlinTypeMarker $this$isNothing) {
        Intrinsics.checkNotNullParameter($this$isNothing, "$this$isNothing");
        return ClassicTypeSystemContext.DefaultImpls.isNothing(this, $this$isNothing);
    }

    @Override
    @NotNull
    public SimpleTypeMarker lowerBoundIfFlexible(@NotNull KotlinTypeMarker $this$lowerBoundIfFlexible) {
        Intrinsics.checkNotNullParameter($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return ClassicTypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, $this$lowerBoundIfFlexible);
    }

    @Override
    public int size(@NotNull TypeArgumentListMarker $this$size) {
        Intrinsics.checkNotNullParameter($this$size, "$this$size");
        return ClassicTypeSystemContext.DefaultImpls.size(this, $this$size);
    }

    @Override
    @NotNull
    public SimpleTypeMarker upperBoundIfFlexible(@NotNull KotlinTypeMarker $this$upperBoundIfFlexible) {
        Intrinsics.checkNotNullParameter($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return ClassicTypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, $this$upperBoundIfFlexible);
    }

    @Override
    @NotNull
    public KotlinTypeMarker makeNullable(@NotNull KotlinTypeMarker $this$makeNullable) {
        Intrinsics.checkNotNullParameter($this$makeNullable, "$this$makeNullable");
        return ClassicTypeSystemContext.DefaultImpls.makeNullable(this, $this$makeNullable);
    }

    public static final class Companion {
        @NotNull
        public final AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform classicSubstitutionSupertypePolicy(@NotNull ClassicTypeSystemContext $this$classicSubstitutionSupertypePolicy, @NotNull SimpleTypeMarker type2) {
            Intrinsics.checkNotNullParameter($this$classicSubstitutionSupertypePolicy, "$this$classicSubstitutionSupertypePolicy");
            Intrinsics.checkNotNullParameter(type2, "type");
            boolean bl = type2 instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                String string = ClassicTypeCheckerContextKt.access$errorMessage(type2);
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            TypeSubstitutor substitutor = TypeConstructorSubstitution.Companion.create((KotlinType)((Object)type2)).buildSubstitutor();
            return new AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform($this$classicSubstitutionSupertypePolicy, substitutor){
                final /* synthetic */ ClassicTypeSystemContext $this_classicSubstitutionSupertypePolicy;
                final /* synthetic */ TypeSubstitutor $substitutor;

                @NotNull
                public SimpleTypeMarker transformType(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker type2) {
                    Intrinsics.checkNotNullParameter(context, "context");
                    Intrinsics.checkNotNullParameter(type2, "type");
                    SimpleTypeMarker simpleTypeMarker = this.$this_classicSubstitutionSupertypePolicy.lowerBoundIfFlexible(type2);
                    if (simpleTypeMarker == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
                    }
                    KotlinType kotlinType = this.$substitutor.safeSubstitute((KotlinType)((Object)simpleTypeMarker), Variance.INVARIANT);
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "substitutor.safeSubstitu\u2026ANT\n                    )");
                    SimpleTypeMarker simpleTypeMarker2 = this.$this_classicSubstitutionSupertypePolicy.asSimpleType(kotlinType);
                    Intrinsics.checkNotNull(simpleTypeMarker2);
                    return simpleTypeMarker2;
                }
                {
                    this.$this_classicSubstitutionSupertypePolicy = $receiver;
                    this.$substitutor = $captured_local_variable$1;
                }
            };
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

