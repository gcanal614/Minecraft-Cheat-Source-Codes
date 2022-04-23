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
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractNullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractStrictEqualityTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker$WhenMappings;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AbstractTypeChecker {
    @JvmField
    public static boolean RUN_SLOW_ASSERTIONS;
    public static final AbstractTypeChecker INSTANCE;

    public final boolean isSubtypeOf(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker subType, @NotNull KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        if (subType == superType) {
            return true;
        }
        boolean bl = false;
        boolean bl2 = false;
        AbstractTypeCheckerContext $this$with = context;
        boolean bl3 = false;
        return INSTANCE.completeIsSubTypeOf($this$with, $this$with.prepareType($this$with.refineType(subType)), $this$with.prepareType($this$with.refineType(superType)), isFromNullabilityConstraint);
    }

    public static /* synthetic */ boolean isSubtypeOf$default(AbstractTypeChecker abstractTypeChecker, AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean bl, int n, Object object) {
        if ((n & 8) != 0) {
            bl = false;
        }
        return abstractTypeChecker.isSubtypeOf(abstractTypeCheckerContext, kotlinTypeMarker, kotlinTypeMarker2, bl);
    }

    public final boolean equalTypes(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker a2, @NotNull KotlinTypeMarker b2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        boolean bl = false;
        boolean bl2 = false;
        AbstractTypeCheckerContext $this$with = context;
        boolean bl3 = false;
        if (a2 == b2) {
            return true;
        }
        if (INSTANCE.isCommonDenotableType($this$with, a2) && INSTANCE.isCommonDenotableType($this$with, b2)) {
            KotlinTypeMarker refinedA = $this$with.refineType(a2);
            KotlinTypeMarker refinedB = $this$with.refineType(b2);
            SimpleTypeMarker simpleA = $this$with.lowerBoundIfFlexible(refinedA);
            if (!$this$with.areEqualTypeConstructors($this$with.typeConstructor(refinedA), $this$with.typeConstructor(refinedB))) {
                return false;
            }
            if ($this$with.argumentsCount(simpleA) == 0) {
                if ($this$with.hasFlexibleNullability(refinedA) || $this$with.hasFlexibleNullability(refinedB)) {
                    return true;
                }
                return $this$with.isMarkedNullable(simpleA) == $this$with.isMarkedNullable($this$with.lowerBoundIfFlexible(refinedB));
            }
        }
        return AbstractTypeChecker.isSubtypeOf$default(INSTANCE, context, a2, b2, false, 8, null) && AbstractTypeChecker.isSubtypeOf$default(INSTANCE, context, b2, a2, false, 8, null);
    }

    private final boolean completeIsSubTypeOf(AbstractTypeCheckerContext $this$completeIsSubTypeOf, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        Boolean bl = this.checkSubtypeForSpecialCases($this$completeIsSubTypeOf, $this$completeIsSubTypeOf.lowerBoundIfFlexible(subType), $this$completeIsSubTypeOf.upperBoundIfFlexible(superType));
        if (bl != null) {
            Boolean bl2 = bl;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean it = bl2;
            boolean bl5 = false;
            $this$completeIsSubTypeOf.addSubtypeConstraint(subType, superType, isFromNullabilityConstraint);
            return it;
        }
        Boolean bl6 = $this$completeIsSubTypeOf.addSubtypeConstraint(subType, superType, isFromNullabilityConstraint);
        if (bl6 != null) {
            Boolean bl7 = bl6;
            boolean bl8 = false;
            boolean bl9 = false;
            boolean it = bl7;
            boolean bl10 = false;
            return it;
        }
        return this.isSubtypeOfForSingleClassifierType($this$completeIsSubTypeOf, $this$completeIsSubTypeOf.lowerBoundIfFlexible(subType), $this$completeIsSubTypeOf.upperBoundIfFlexible(superType));
    }

    private final Boolean checkSubtypeForIntegerLiteralType(AbstractTypeCheckerContext $this$checkSubtypeForIntegerLiteralType, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        if (!$this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(subType) && !$this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(superType)) {
            return null;
        }
        Function3<SimpleTypeMarker, SimpleTypeMarker, Boolean, Boolean> $fun$typeInIntegerLiteralType$1 = new Function3<SimpleTypeMarker, SimpleTypeMarker, Boolean, Boolean>($this$checkSubtypeForIntegerLiteralType){
            final /* synthetic */ AbstractTypeCheckerContext $this_checkSubtypeForIntegerLiteralType;

            public final boolean invoke(@NotNull SimpleTypeMarker integerLiteralType, @NotNull SimpleTypeMarker type2, boolean checkSupertypes) {
                boolean bl;
                block3: {
                    Intrinsics.checkNotNullParameter(integerLiteralType, "integerLiteralType");
                    Intrinsics.checkNotNullParameter(type2, "type");
                    Iterable $this$any$iv = this.$this_checkSubtypeForIntegerLiteralType.possibleIntegerTypes(integerLiteralType);
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            KotlinTypeMarker possibleType = (KotlinTypeMarker)element$iv;
                            boolean bl2 = false;
                            if (!(Intrinsics.areEqual(this.$this_checkSubtypeForIntegerLiteralType.typeConstructor(possibleType), this.$this_checkSubtypeForIntegerLiteralType.typeConstructor(type2)) || checkSupertypes && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, this.$this_checkSubtypeForIntegerLiteralType, type2, possibleType, false, 8, null))) continue;
                            bl = true;
                            break block3;
                        }
                        bl = false;
                    }
                }
                return bl;
            }
            {
                this.$this_checkSubtypeForIntegerLiteralType = abstractTypeCheckerContext;
                super(3);
            }
        };
        if ($this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(subType) && $this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(superType)) {
            return true;
        }
        if ($this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(subType) ? $fun$typeInIntegerLiteralType$1.invoke(subType, superType, false) : $this$checkSubtypeForIntegerLiteralType.isIntegerLiteralType(superType) && $fun$typeInIntegerLiteralType$1.invoke(superType, subType, true)) {
            return true;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    private final boolean hasNothingSupertype(AbstractTypeCheckerContext $this$hasNothingSupertype, SimpleTypeMarker type) {
        block8: {
            typeConstructor = $this$hasNothingSupertype.typeConstructor(type);
            if ($this$hasNothingSupertype.isClassTypeConstructor(typeConstructor)) {
                return $this$hasNothingSupertype.isNothingConstructor(typeConstructor);
            }
            this_$iv = $this$hasNothingSupertype;
            $i$f$anySupertype = false;
            it = type;
            $i$a$-anySupertype-AbstractTypeChecker$hasNothingSupertype$1 = false;
            if ($this$hasNothingSupertype.isNothingConstructor($this$hasNothingSupertype.typeConstructor(it))) {
                v0 = true;
            } else {
                this_$iv.initialize();
                v1 = this_$iv.getSupertypesDeque();
                Intrinsics.checkNotNull(v1);
                deque$iv = v1;
                v2 = this_$iv.getSupertypesSet();
                Intrinsics.checkNotNull(v2);
                visitedSupertypes$iv = v2;
                deque$iv.push(type);
                block0: while (true) {
                    var10_10 = deque$iv;
                    var11_11 = false;
                    if (!(var10_10.isEmpty() == false)) break;
                    if (visitedSupertypes$iv.size() > 1000) {
                        var10_10 = "Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null);
                        var11_11 = false;
                        throw (Throwable)new IllegalStateException(var10_10.toString());
                    }
                    v3 = current$iv = deque$iv.pop();
                    Intrinsics.checkNotNullExpressionValue(v3, "current");
                    if (!visitedSupertypes$iv.add(v3)) continue;
                    it = current$iv;
                    $i$a$-anySupertype-AbstractTypeChecker$hasNothingSupertype$2 = false;
                    var12_13 = $this$hasNothingSupertype.isClassType(it) != false ? (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    var13_14 = false;
                    var14_16 = false;
                    it$iv = var12_13;
                    $i$a$-takeIf-AbstractTypeCheckerContext$anySupertype$policy$1$iv = false;
                    if (((Intrinsics.areEqual(it$iv, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true) != false ? var12_13 : null) == null) {
                        continue;
                    }
                    policy$iv = policy$iv;
                    var13_15 = this_$iv.supertypes(this_$iv.typeConstructor(current$iv)).iterator();
                    while (true) {
                        if (var13_15.hasNext()) ** break;
                        continue block0;
                        supertype$iv = var13_15.next();
                        it = newType$iv = policy$iv.transformType(this_$iv, supertype$iv);
                        $i$a$-anySupertype-AbstractTypeChecker$hasNothingSupertype$1 = false;
                        if ($this$hasNothingSupertype.isNothingConstructor($this$hasNothingSupertype.typeConstructor(it))) {
                            this_$iv.clear();
                            v0 = true;
                            break block8;
                        }
                        deque$iv.add(newType$iv);
                    }
                    break;
                }
                this_$iv.clear();
                v0 = false;
            }
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean isSubtypeOfForSingleClassifierType(AbstractTypeCheckerContext $this$isSubtypeOfForSingleClassifierType, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        boolean bl;
        block21: {
            boolean bl2;
            if (RUN_SLOW_ASSERTIONS) {
                boolean bl3 = $this$isSubtypeOfForSingleClassifierType.isSingleClassifierType(subType) || $this$isSubtypeOfForSingleClassifierType.isIntersection($this$isSubtypeOfForSingleClassifierType.typeConstructor(subType)) || $this$isSubtypeOfForSingleClassifierType.isAllowedTypeVariable(subType);
                bl2 = false;
                if (_Assertions.ENABLED && !bl3) {
                    boolean $i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$32 = false;
                    String $i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$32 = "Not singleClassifierType and not intersection subType: " + subType;
                    throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$32));
                }
                bl3 = $this$isSubtypeOfForSingleClassifierType.isSingleClassifierType(superType) || $this$isSubtypeOfForSingleClassifierType.isAllowedTypeVariable(superType);
                bl2 = false;
                if (_Assertions.ENABLED && !bl3) {
                    boolean $i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$42 = false;
                    String $i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$42 = "Not singleClassifierType superType: " + superType;
                    throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$42));
                }
            }
            if (!AbstractNullabilityChecker.INSTANCE.isPossibleSubtype($this$isSubtypeOfForSingleClassifierType, subType, superType)) {
                return false;
            }
            Boolean bl4 = this.checkSubtypeForIntegerLiteralType($this$isSubtypeOfForSingleClassifierType, $this$isSubtypeOfForSingleClassifierType.lowerBoundIfFlexible(subType), $this$isSubtypeOfForSingleClassifierType.upperBoundIfFlexible(superType));
            if (bl4 != null) {
                Boolean bl5 = bl4;
                bl2 = false;
                boolean $i$a$-assert-AbstractTypeChecker$isSubtypeOfForSingleClassifierType$42 = false;
                boolean it = bl5;
                boolean bl6 = false;
                AbstractTypeCheckerContext.addSubtypeConstraint$default($this$isSubtypeOfForSingleClassifierType, subType, superType, false, 4, null);
                return it;
            }
            TypeConstructorMarker superConstructor = $this$isSubtypeOfForSingleClassifierType.typeConstructor(superType);
            if ($this$isSubtypeOfForSingleClassifierType.isEqualTypeConstructors($this$isSubtypeOfForSingleClassifierType.typeConstructor(subType), superConstructor) && $this$isSubtypeOfForSingleClassifierType.parametersCount(superConstructor) == 0) {
                return true;
            }
            if ($this$isSubtypeOfForSingleClassifierType.isAnyConstructor($this$isSubtypeOfForSingleClassifierType.typeConstructor(superType))) {
                return true;
            }
            List<SimpleTypeMarker> supertypesWithSameConstructor = this.findCorrespondingSupertypes($this$isSubtypeOfForSingleClassifierType, subType, superConstructor);
            switch (supertypesWithSameConstructor.size()) {
                case 0: {
                    return this.hasNothingSupertype($this$isSubtypeOfForSingleClassifierType, subType);
                }
                case 1: {
                    return this.isSubtypeForSameConstructor($this$isSubtypeOfForSingleClassifierType, $this$isSubtypeOfForSingleClassifierType.asArgumentList(CollectionsKt.first(supertypesWithSameConstructor)), superType);
                }
            }
            ArgumentList newArguments = new ArgumentList($this$isSubtypeOfForSingleClassifierType.parametersCount(superConstructor));
            boolean anyNonOutParameter = false;
            int bl6 = 0;
            int n = $this$isSubtypeOfForSingleClassifierType.parametersCount(superConstructor);
            while (bl6 < n) {
                void index;
                block22: {
                    void $this$mapTo$iv$iv;
                    boolean bl7 = anyNonOutParameter = anyNonOutParameter || $this$isSubtypeOfForSingleClassifierType.getVariance($this$isSubtypeOfForSingleClassifierType.getParameter(superConstructor, (int)index)) != TypeVariance.OUT;
                    if (anyNonOutParameter) break block22;
                    Iterable $this$map$iv = supertypesWithSameConstructor;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        Object object;
                        Collection collection;
                        block24: {
                            boolean bl8;
                            Object object2;
                            void it;
                            block23: {
                                SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker)item$iv$iv;
                                collection = destination$iv$iv;
                                boolean bl9 = false;
                                object = $this$isSubtypeOfForSingleClassifierType.getArgumentOrNull((SimpleTypeMarker)it, (int)index);
                                if (object == null) break block23;
                                object2 = object;
                                bl8 = false;
                                boolean bl10 = false;
                                TypeArgumentMarker it2 = object2;
                                boolean bl11 = false;
                                object = $this$isSubtypeOfForSingleClassifierType.getVariance(it2) == TypeVariance.INV ? object2 : null;
                                if (object != null && (object = $this$isSubtypeOfForSingleClassifierType.getType((TypeArgumentMarker)object)) != null) break block24;
                            }
                            object2 = "Incorrect type: " + it + ", subType: " + subType + ", superType: " + superType;
                            bl8 = false;
                            throw (Throwable)new IllegalStateException(object2.toString());
                        }
                        Object object3 = object;
                        collection.add(object3);
                    }
                    List allProjections = (List)destination$iv$iv;
                    TypeArgumentMarker intersection = $this$isSubtypeOfForSingleClassifierType.asTypeArgument($this$isSubtypeOfForSingleClassifierType.intersectTypes(allProjections));
                    newArguments.add(intersection);
                }
                ++index;
            }
            if (!anyNonOutParameter && this.isSubtypeForSameConstructor($this$isSubtypeOfForSingleClassifierType, newArguments, superType)) {
                return true;
            }
            Iterable $this$any$iv = supertypesWithSameConstructor;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    SimpleTypeMarker it = (SimpleTypeMarker)element$iv;
                    boolean bl12 = false;
                    if (!INSTANCE.isSubtypeForSameConstructor($this$isSubtypeOfForSingleClassifierType, $this$isSubtypeOfForSingleClassifierType.asArgumentList(it), superType)) continue;
                    bl = true;
                    break block21;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public final boolean isSubtypeForSameConstructor(@NotNull AbstractTypeCheckerContext $this$isSubtypeForSameConstructor, @NotNull TypeArgumentListMarker capturedSubArguments, @NotNull SimpleTypeMarker superType) {
        Intrinsics.checkNotNullParameter($this$isSubtypeForSameConstructor, "$this$isSubtypeForSameConstructor");
        Intrinsics.checkNotNullParameter(capturedSubArguments, "capturedSubArguments");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeConstructorMarker superTypeConstructor = $this$isSubtypeForSameConstructor.typeConstructor(superType);
        int n = 0;
        int n2 = $this$isSubtypeForSameConstructor.parametersCount(superTypeConstructor);
        while (n < n2) {
            void index;
            TypeArgumentMarker superProjection = $this$isSubtypeForSameConstructor.getArgument(superType, (int)index);
            if (!$this$isSubtypeForSameConstructor.isStarProjection(superProjection)) {
                boolean bl;
                TypeVariance variance;
                KotlinTypeMarker superArgumentType = $this$isSubtypeForSameConstructor.getType(superProjection);
                TypeArgumentMarker typeArgumentMarker = $this$isSubtypeForSameConstructor.get(capturedSubArguments, (int)index);
                boolean bl2 = false;
                boolean bl3 = false;
                TypeArgumentMarker it = typeArgumentMarker;
                int n32 = 0;
                int n4 = $this$isSubtypeForSameConstructor.getVariance(it) == TypeVariance.INV ? 1 : 0;
                boolean bl4 = false;
                if (_Assertions.ENABLED && n4 == 0) {
                    boolean $i$a$-assert-AbstractTypeChecker$isSubtypeForSameConstructor$subArgumentType$1$22 = false;
                    String $i$a$-assert-AbstractTypeChecker$isSubtypeForSameConstructor$subArgumentType$1$22 = "Incorrect sub argument: " + it;
                    throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-AbstractTypeChecker$isSubtypeForSameConstructor$subArgumentType$1$22));
                }
                KotlinTypeMarker subArgumentType = $this$isSubtypeForSameConstructor.getType(it);
                if (this.effectiveVariance($this$isSubtypeForSameConstructor.getVariance($this$isSubtypeForSameConstructor.getParameter(superTypeConstructor, (int)index)), $this$isSubtypeForSameConstructor.getVariance(superProjection)) == null) {
                    return $this$isSubtypeForSameConstructor.isErrorTypeEqualsToAnything();
                }
                AbstractTypeCheckerContext this_$iv = $this$isSubtypeForSameConstructor;
                boolean $i$f$runWithArgumentsSettings$type_system = false;
                if (AbstractTypeCheckerContext.access$getArgumentsDepth$p(this_$iv) > 100) {
                    String n32 = "Arguments depth is too high. Some related argument: " + subArgumentType;
                    n4 = 0;
                    throw (Throwable)new IllegalStateException(n32.toString());
                }
                AbstractTypeCheckerContext abstractTypeCheckerContext = this_$iv;
                n32 = AbstractTypeCheckerContext.access$getArgumentsDepth$p(abstractTypeCheckerContext);
                AbstractTypeCheckerContext.access$setArgumentsDepth$p(abstractTypeCheckerContext, n32 + 1);
                AbstractTypeCheckerContext $this$runWithArgumentsSettings = this_$iv;
                boolean bl5 = false;
                switch (AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$0[variance.ordinal()]) {
                    case 1: {
                        bl = INSTANCE.equalTypes($this$runWithArgumentsSettings, subArgumentType, superArgumentType);
                        break;
                    }
                    case 2: {
                        bl = AbstractTypeChecker.isSubtypeOf$default(INSTANCE, $this$runWithArgumentsSettings, subArgumentType, superArgumentType, false, 8, null);
                        break;
                    }
                    case 3: {
                        bl = AbstractTypeChecker.isSubtypeOf$default(INSTANCE, $this$runWithArgumentsSettings, superArgumentType, subArgumentType, false, 8, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                boolean result$iv = bl;
                AbstractTypeCheckerContext abstractTypeCheckerContext2 = this_$iv;
                n4 = AbstractTypeCheckerContext.access$getArgumentsDepth$p(abstractTypeCheckerContext2);
                AbstractTypeCheckerContext.access$setArgumentsDepth$p(abstractTypeCheckerContext2, n4 + -1);
                boolean correctArgument = result$iv;
                if (!correctArgument) {
                    return false;
                }
            }
            ++index;
        }
        return true;
    }

    private final boolean isCommonDenotableType(AbstractTypeCheckerContext $this$isCommonDenotableType, KotlinTypeMarker type2) {
        return $this$isCommonDenotableType.isDenotable($this$isCommonDenotableType.typeConstructor(type2)) && !$this$isCommonDenotableType.isDynamic(type2) && !$this$isCommonDenotableType.isDefinitelyNotNullType(type2) && Intrinsics.areEqual($this$isCommonDenotableType.typeConstructor($this$isCommonDenotableType.lowerBoundIfFlexible(type2)), $this$isCommonDenotableType.typeConstructor($this$isCommonDenotableType.upperBoundIfFlexible(type2)));
    }

    @Nullable
    public final TypeVariance effectiveVariance(@NotNull TypeVariance declared, @NotNull TypeVariance useSite) {
        Intrinsics.checkNotNullParameter((Object)declared, "declared");
        Intrinsics.checkNotNullParameter((Object)useSite, "useSite");
        if (declared == TypeVariance.INV) {
            return useSite;
        }
        if (useSite == TypeVariance.INV) {
            return declared;
        }
        if (declared == useSite) {
            return declared;
        }
        return null;
    }

    private final Boolean checkSubtypeForSpecialCases(AbstractTypeCheckerContext $this$checkSubtypeForSpecialCases, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        TypeConstructorMarker superTypeConstructor;
        KotlinTypeMarker lowerType;
        CapturedTypeMarker superTypeCaptured;
        if ($this$checkSubtypeForSpecialCases.isError(subType) || $this$checkSubtypeForSpecialCases.isError(superType)) {
            if ($this$checkSubtypeForSpecialCases.isErrorTypeEqualsToAnything()) {
                return true;
            }
            if ($this$checkSubtypeForSpecialCases.isMarkedNullable(subType) && !$this$checkSubtypeForSpecialCases.isMarkedNullable(superType)) {
                return false;
            }
            return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes($this$checkSubtypeForSpecialCases, $this$checkSubtypeForSpecialCases.withNullability(subType, false), $this$checkSubtypeForSpecialCases.withNullability(superType, false));
        }
        if ($this$checkSubtypeForSpecialCases.isStubType(subType) || $this$checkSubtypeForSpecialCases.isStubType(superType)) {
            return $this$checkSubtypeForSpecialCases.isStubTypeEqualsToAnything();
        }
        CapturedTypeMarker capturedTypeMarker = superTypeCaptured = $this$checkSubtypeForSpecialCases.asCapturedType(superType);
        KotlinTypeMarker kotlinTypeMarker = lowerType = capturedTypeMarker != null ? $this$checkSubtypeForSpecialCases.lowerType(capturedTypeMarker) : null;
        if (superTypeCaptured != null && lowerType != null) {
            switch (AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$1[$this$checkSubtypeForSpecialCases.getLowerCapturedTypePolicy(subType, superTypeCaptured).ordinal()]) {
                case 1: {
                    return AbstractTypeChecker.isSubtypeOf$default(this, $this$checkSubtypeForSpecialCases, subType, lowerType, false, 8, null);
                }
                case 2: {
                    if (!AbstractTypeChecker.isSubtypeOf$default(this, $this$checkSubtypeForSpecialCases, subType, lowerType, false, 8, null)) break;
                    return true;
                }
                case 3: {
                    break;
                }
            }
        }
        if ($this$checkSubtypeForSpecialCases.isIntersection(superTypeConstructor = $this$checkSubtypeForSpecialCases.typeConstructor(superType))) {
            boolean bl;
            block15: {
                boolean bl2 = !$this$checkSubtypeForSpecialCases.isMarkedNullable(superType);
                boolean bl3 = false;
                if (_Assertions.ENABLED && !bl2) {
                    boolean bl4 = false;
                    String string = "Intersection type should not be marked nullable!: " + superType;
                    throw (Throwable)((Object)new AssertionError((Object)string));
                }
                Iterable $this$all$iv = $this$checkSubtypeForSpecialCases.supertypes(superTypeConstructor);
                boolean $i$f$all = false;
                if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$all$iv) {
                        KotlinTypeMarker it = (KotlinTypeMarker)element$iv;
                        boolean bl5 = false;
                        if (AbstractTypeChecker.isSubtypeOf$default(INSTANCE, $this$checkSubtypeForSpecialCases, subType, it, false, 8, null)) continue;
                        bl = false;
                        break block15;
                    }
                    bl = true;
                }
            }
            return bl;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    private final List<SimpleTypeMarker> collectAllSupertypesWithGivenTypeConstructor(AbstractTypeCheckerContext $this$collectAllSupertypesWithGivenTypeConstructor, SimpleTypeMarker subType, TypeConstructorMarker superConstructor) {
        block15: {
            v0 = $this$collectAllSupertypesWithGivenTypeConstructor.fastCorrespondingSupertypes(subType, superConstructor);
            if (v0 != null) {
                var4_4 = v0;
                var5_6 = false;
                var6_8 = false;
                it = var4_4;
                $i$a$-let-AbstractTypeChecker$collectAllSupertypesWithGivenTypeConstructor$1 = false;
                return it;
            }
            if (!$this$collectAllSupertypesWithGivenTypeConstructor.isClassTypeConstructor(superConstructor) && $this$collectAllSupertypesWithGivenTypeConstructor.isClassType(subType)) {
                return CollectionsKt.emptyList();
            }
            if ($this$collectAllSupertypesWithGivenTypeConstructor.isCommonFinalClassConstructor(superConstructor)) {
                if ($this$collectAllSupertypesWithGivenTypeConstructor.areEqualTypeConstructors($this$collectAllSupertypesWithGivenTypeConstructor.typeConstructor(subType), superConstructor)) {
                    v1 = $this$collectAllSupertypesWithGivenTypeConstructor.captureFromArguments(subType, CaptureStatus.FOR_SUBTYPING);
                    if (v1 == null) {
                        v1 = subType;
                    }
                    v2 = CollectionsKt.listOf(v1);
                } else {
                    v2 = CollectionsKt.emptyList();
                }
                return v2;
            }
            result = new SmartList<E>();
            this_$iv = $this$collectAllSupertypesWithGivenTypeConstructor;
            $i$f$anySupertype = false;
            it = subType;
            $i$a$-anySupertype-AbstractTypeChecker$collectAllSupertypesWithGivenTypeConstructor$2 = false;
            if (!false) {
                this_$iv.initialize();
                v3 = this_$iv.getSupertypesDeque();
                Intrinsics.checkNotNull(v3);
                deque$iv = v3;
                v4 = this_$iv.getSupertypesSet();
                Intrinsics.checkNotNull(v4);
                visitedSupertypes$iv = v4;
                deque$iv.push(subType);
                block0: while (true) {
                    var11_16 = deque$iv;
                    var12_17 = false;
                    if (!(var11_16.isEmpty() == false)) break;
                    if (visitedSupertypes$iv.size() > 1000) {
                        var11_16 = "Too many supertypes for type: " + subType + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null);
                        var12_17 = false;
                        throw (Throwable)new IllegalStateException(var11_16.toString());
                    }
                    v5 = current$iv = deque$iv.pop();
                    Intrinsics.checkNotNullExpressionValue(v5, "current");
                    if (!visitedSupertypes$iv.add(v5)) continue;
                    it = current$iv;
                    $i$a$-anySupertype-AbstractTypeChecker$collectAllSupertypesWithGivenTypeConstructor$3 = false;
                    v6 = $this$collectAllSupertypesWithGivenTypeConstructor.captureFromArguments(it, CaptureStatus.FOR_SUBTYPING);
                    if (v6 == null) {
                        v6 = it;
                    }
                    current = v6;
                    if ($this$collectAllSupertypesWithGivenTypeConstructor.areEqualTypeConstructors($this$collectAllSupertypesWithGivenTypeConstructor.typeConstructor(current), superConstructor)) {
                        result.add(current);
                        v7 = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                    } else {
                        v7 = $this$collectAllSupertypesWithGivenTypeConstructor.argumentsCount(current) == 0 ? (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE : $this$collectAllSupertypesWithGivenTypeConstructor.substitutionSupertypePolicy(current);
                    }
                    var14_20 = v7;
                    var15_21 = false;
                    var16_23 = false;
                    it$iv = var14_20;
                    $i$a$-takeIf-AbstractTypeCheckerContext$anySupertype$policy$1$iv = false;
                    if (((Intrinsics.areEqual(it$iv, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true) != false ? var14_20 : null) == null) {
                        continue;
                    }
                    policy$iv = policy$iv;
                    var15_22 = this_$iv.supertypes(this_$iv.typeConstructor(current$iv)).iterator();
                    while (true) {
                        if (var15_22.hasNext()) ** break;
                        continue block0;
                        supertype$iv = var15_22.next();
                        it = newType$iv = policy$iv.transformType(this_$iv, supertype$iv);
                        $i$a$-anySupertype-AbstractTypeChecker$collectAllSupertypesWithGivenTypeConstructor$2 = false;
                        if (false) {
                            this_$iv.clear();
                            break block15;
                        }
                        deque$iv.add(newType$iv);
                    }
                    break;
                }
                this_$iv.clear();
            }
        }
        return result;
    }

    private final List<SimpleTypeMarker> collectAndFilter(AbstractTypeCheckerContext $this$collectAndFilter, SimpleTypeMarker classType, TypeConstructorMarker constructor) {
        return this.selectOnlyPureKotlinSupertypes($this$collectAndFilter, this.collectAllSupertypesWithGivenTypeConstructor($this$collectAndFilter, classType, constructor));
    }

    /*
     * WARNING - void declaration
     */
    private final List<SimpleTypeMarker> selectOnlyPureKotlinSupertypes(AbstractTypeCheckerContext $this$selectOnlyPureKotlinSupertypes, List<? extends SimpleTypeMarker> supertypes2) {
        void $this$filterTo$iv$iv;
        if (supertypes2.size() < 2) {
            return supertypes2;
        }
        Iterable $this$filter$iv = supertypes2;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            boolean bl;
            block3: {
                void $this$all$iv;
                SimpleTypeMarker it = (SimpleTypeMarker)element$iv$iv;
                boolean bl2 = false;
                TypeArgumentListMarker typeArgumentListMarker = $this$selectOnlyPureKotlinSupertypes.asArgumentList(it);
                TypeSystemContext context$iv = $this$selectOnlyPureKotlinSupertypes;
                boolean $i$f$all = false;
                boolean bl3 = false;
                boolean bl4 = false;
                TypeSystemContext $this$with$iv = context$iv;
                boolean bl5 = false;
                int n = $this$with$iv.size((TypeArgumentListMarker)$this$all$iv);
                boolean bl6 = false;
                int n2 = 0;
                int n3 = n;
                for (n2 = 0; n2 < n3; ++n2) {
                    int index$iv = n2;
                    boolean bl7 = false;
                    TypeArgumentMarker it2 = $this$with$iv.get((TypeArgumentListMarker)$this$all$iv, index$iv);
                    boolean bl8 = false;
                    if ($this$selectOnlyPureKotlinSupertypes.asFlexibleType($this$selectOnlyPureKotlinSupertypes.getType(it2)) == null) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
            if (!bl) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List<SimpleTypeMarker> allPureSupertypes = (List<SimpleTypeMarker>)destination$iv$iv;
        Collection collection = allPureSupertypes;
        boolean bl = false;
        return !collection.isEmpty() ? allPureSupertypes : supertypes2;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final List<SimpleTypeMarker> findCorrespondingSupertypes(@NotNull AbstractTypeCheckerContext $this$findCorrespondingSupertypes, @NotNull SimpleTypeMarker subType, @NotNull TypeConstructorMarker superConstructor) {
        block11: {
            Intrinsics.checkNotNullParameter($this$findCorrespondingSupertypes, "$this$findCorrespondingSupertypes");
            Intrinsics.checkNotNullParameter(subType, "subType");
            Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
            if ($this$findCorrespondingSupertypes.isClassType(subType)) {
                return this.collectAndFilter($this$findCorrespondingSupertypes, subType, superConstructor);
            }
            if (!$this$findCorrespondingSupertypes.isClassTypeConstructor(superConstructor) && !$this$findCorrespondingSupertypes.isIntegerLiteralTypeConstructor(superConstructor)) {
                return this.collectAllSupertypesWithGivenTypeConstructor($this$findCorrespondingSupertypes, subType, superConstructor);
            }
            classTypeSupertypes = new SmartList<Object>();
            this_$iv = $this$findCorrespondingSupertypes;
            $i$f$anySupertype = false;
            it = subType;
            $i$a$-anySupertype-AbstractTypeChecker$findCorrespondingSupertypes$1 = false;
            if (!false) {
                this_$iv.initialize();
                v0 = this_$iv.getSupertypesDeque();
                Intrinsics.checkNotNull(v0);
                deque$iv = v0;
                v1 = this_$iv.getSupertypesSet();
                Intrinsics.checkNotNull(v1);
                visitedSupertypes$iv = v1;
                deque$iv.push(subType);
                block0: while (true) {
                    var11_13 = deque$iv;
                    var12_14 = false;
                    if (!(var11_13.isEmpty() == false)) break;
                    if (visitedSupertypes$iv.size() > 1000) {
                        var11_13 = "Too many supertypes for type: " + subType + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null);
                        var12_14 = false;
                        throw (Throwable)new IllegalStateException(var11_13.toString());
                    }
                    v2 = current$iv = deque$iv.pop();
                    Intrinsics.checkNotNullExpressionValue(v2, "current");
                    if (!visitedSupertypes$iv.add(v2)) continue;
                    it = current$iv;
                    $i$a$-anySupertype-AbstractTypeChecker$findCorrespondingSupertypes$2 = false;
                    if ($this$findCorrespondingSupertypes.isClassType((SimpleTypeMarker)it)) {
                        classTypeSupertypes.add(it);
                        v3 = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                    } else {
                        v3 = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    }
                    var13_17 = v3;
                    var14_20 = false;
                    var15_22 = false;
                    it$iv = var13_17;
                    $i$a$-takeIf-AbstractTypeCheckerContext$anySupertype$policy$1$iv = false;
                    if (((Intrinsics.areEqual(it$iv, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true) != false ? var13_17 : null) == null) {
                        continue;
                    }
                    policy$iv = policy$iv;
                    var14_21 = this_$iv.supertypes(this_$iv.typeConstructor(current$iv)).iterator();
                    while (true) {
                        if (var14_21.hasNext()) ** break;
                        continue block0;
                        supertype$iv = var14_21.next();
                        newType$iv = policy$iv.transformType(this_$iv, supertype$iv);
                        it = newType$iv;
                        $i$a$-anySupertype-AbstractTypeChecker$findCorrespondingSupertypes$1 = false;
                        if (false) {
                            this_$iv.clear();
                            break block11;
                        }
                        deque$iv.add(newType$iv);
                    }
                    break;
                }
                this_$iv.clear();
            }
        }
        $this$flatMap$iv = classTypeSupertypes;
        $i$f$flatMap = false;
        it = $this$flatMap$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$flatMapTo = false;
        for (T element$iv$iv : $this$flatMapTo$iv$iv) {
            it = (SimpleTypeMarker)element$iv$iv;
            $i$a$-flatMap-AbstractTypeChecker$findCorrespondingSupertypes$3 = false;
            v4 = it;
            Intrinsics.checkNotNullExpressionValue(v4, "it");
            list$iv$iv = AbstractTypeChecker.INSTANCE.collectAndFilter($this$findCorrespondingSupertypes, v4, superConstructor);
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    private AbstractTypeChecker() {
    }

    static {
        AbstractTypeChecker abstractTypeChecker;
        INSTANCE = abstractTypeChecker = new AbstractTypeChecker();
    }
}

