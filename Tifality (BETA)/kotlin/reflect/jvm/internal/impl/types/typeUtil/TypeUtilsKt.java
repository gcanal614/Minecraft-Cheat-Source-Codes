/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeUtilsKt {
    @NotNull
    public static final KotlinBuiltIns getBuiltIns(@NotNull KotlinType $this$builtIns) {
        Intrinsics.checkNotNullParameter($this$builtIns, "$this$builtIns");
        KotlinBuiltIns kotlinBuiltIns = $this$builtIns.getConstructor().getBuiltIns();
        Intrinsics.checkNotNullExpressionValue(kotlinBuiltIns, "constructor.builtIns");
        return kotlinBuiltIns;
    }

    @NotNull
    public static final KotlinType makeNullable(@NotNull KotlinType $this$makeNullable) {
        Intrinsics.checkNotNullParameter($this$makeNullable, "$this$makeNullable");
        KotlinType kotlinType = TypeUtils.makeNullable($this$makeNullable);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "TypeUtils.makeNullable(this)");
        return kotlinType;
    }

    @NotNull
    public static final KotlinType makeNotNullable(@NotNull KotlinType $this$makeNotNullable) {
        Intrinsics.checkNotNullParameter($this$makeNotNullable, "$this$makeNotNullable");
        KotlinType kotlinType = TypeUtils.makeNotNullable($this$makeNotNullable);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "TypeUtils.makeNotNullable(this)");
        return kotlinType;
    }

    public static final boolean isTypeParameter(@NotNull KotlinType $this$isTypeParameter) {
        Intrinsics.checkNotNullParameter($this$isTypeParameter, "$this$isTypeParameter");
        return TypeUtils.isTypeParameter($this$isTypeParameter);
    }

    public static final boolean isSubtypeOf(@NotNull KotlinType $this$isSubtypeOf, @NotNull KotlinType superType) {
        Intrinsics.checkNotNullParameter($this$isSubtypeOf, "$this$isSubtypeOf");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return KotlinTypeChecker.DEFAULT.isSubtypeOf($this$isSubtypeOf, superType);
    }

    @NotNull
    public static final KotlinType replaceAnnotations(@NotNull KotlinType $this$replaceAnnotations, @NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter($this$replaceAnnotations, "$this$replaceAnnotations");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        if ($this$replaceAnnotations.getAnnotations().isEmpty() && newAnnotations.isEmpty()) {
            return $this$replaceAnnotations;
        }
        return $this$replaceAnnotations.unwrap().replaceAnnotations(newAnnotations);
    }

    @NotNull
    public static final TypeProjection createProjection(@NotNull KotlinType type2, @NotNull Variance projectionKind, @Nullable TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(type2, "type");
        Intrinsics.checkNotNullParameter((Object)projectionKind, "projectionKind");
        TypeParameterDescriptor typeParameterDescriptor2 = typeParameterDescriptor;
        return new TypeProjectionImpl((typeParameterDescriptor2 != null ? typeParameterDescriptor2.getVariance() : null) == projectionKind ? Variance.INVARIANT : projectionKind, type2);
    }

    @NotNull
    public static final TypeProjection asTypeProjection(@NotNull KotlinType $this$asTypeProjection) {
        Intrinsics.checkNotNullParameter($this$asTypeProjection, "$this$asTypeProjection");
        return new TypeProjectionImpl($this$asTypeProjection);
    }

    public static final boolean contains(@NotNull KotlinType $this$contains, @NotNull Function1<? super UnwrappedType, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return TypeUtils.contains($this$contains, predicate);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final KotlinType replaceArgumentsWithStarProjections(@NotNull KotlinType $this$replaceArgumentsWithStarProjections) {
        UnwrappedType unwrappedType;
        UnwrappedType unwrapped$iv;
        Intrinsics.checkNotNullParameter($this$replaceArgumentsWithStarProjections, "$this$replaceArgumentsWithStarProjections");
        KotlinType $this$replaceArgumentsWith$iv = $this$replaceArgumentsWithStarProjections;
        boolean $i$f$replaceArgumentsWith = false;
        UnwrappedType unwrappedType2 = unwrapped$iv = $this$replaceArgumentsWith$iv.unwrap();
        if (unwrappedType2 instanceof FlexibleType) {
            Annotated annotated;
            SimpleType simpleType2;
            List newArguments$iv$iv;
            StarProjectionImpl starProjectionImpl;
            Collection collection;
            Iterable $this$mapTo$iv$iv$iv$iv;
            boolean $i$f$mapTo;
            Collection destination$iv$iv$iv$iv;
            boolean $i$f$map;
            Iterable $this$map$iv$iv$iv;
            SimpleType simpleType3;
            SimpleType $this$replaceArgumentsWith$iv$iv = ((FlexibleType)unwrapped$iv).getLowerBound();
            boolean $i$f$replaceArgumentsWith2 = false;
            if ($this$replaceArgumentsWith$iv$iv.getConstructor().getParameters().isEmpty() || $this$replaceArgumentsWith$iv$iv.getConstructor().getDeclarationDescriptor() == null) {
                simpleType3 = $this$replaceArgumentsWith$iv$iv;
            } else {
                List<TypeParameterDescriptor> list = $this$replaceArgumentsWith$iv$iv.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(list, "constructor.parameters");
                $this$map$iv$iv$iv = list;
                $i$f$map = false;
                Iterable iterable = $this$map$iv$iv$iv;
                destination$iv$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv$iv, 10));
                $i$f$mapTo = false;
                for (Object item$iv$iv$iv$iv : $this$mapTo$iv$iv$iv$iv) {
                    void p1;
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv$iv$iv;
                    collection = destination$iv$iv$iv$iv;
                    boolean bl = false;
                    starProjectionImpl = new StarProjectionImpl((TypeParameterDescriptor)p1);
                    collection.add(starProjectionImpl);
                }
                newArguments$iv$iv = (List)destination$iv$iv$iv$iv;
                simpleType3 = TypeSubstitutionKt.replace$default($this$replaceArgumentsWith$iv$iv, newArguments$iv$iv, null, 2, null);
            }
            $this$replaceArgumentsWith$iv$iv = ((FlexibleType)unwrapped$iv).getUpperBound();
            SimpleType simpleType4 = simpleType3;
            $i$f$replaceArgumentsWith2 = false;
            if ($this$replaceArgumentsWith$iv$iv.getConstructor().getParameters().isEmpty() || $this$replaceArgumentsWith$iv$iv.getConstructor().getDeclarationDescriptor() == null) {
                simpleType2 = $this$replaceArgumentsWith$iv$iv;
            } else {
                List<TypeParameterDescriptor> list = $this$replaceArgumentsWith$iv$iv.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(list, "constructor.parameters");
                $this$map$iv$iv$iv = list;
                $i$f$map = false;
                $this$mapTo$iv$iv$iv$iv = $this$map$iv$iv$iv;
                destination$iv$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv$iv, 10));
                $i$f$mapTo = false;
                for (Object item$iv$iv$iv$iv : $this$mapTo$iv$iv$iv$iv) {
                    void p1;
                    annotated = (TypeParameterDescriptor)item$iv$iv$iv$iv;
                    collection = destination$iv$iv$iv$iv;
                    boolean bl = false;
                    starProjectionImpl = new StarProjectionImpl((TypeParameterDescriptor)p1);
                    collection.add(starProjectionImpl);
                }
                newArguments$iv$iv = (List)destination$iv$iv$iv$iv;
                simpleType2 = TypeSubstitutionKt.replace$default($this$replaceArgumentsWith$iv$iv, newArguments$iv$iv, null, 2, null);
            }
            annotated = simpleType2;
            unwrappedType = KotlinTypeFactory.flexibleType(simpleType4, annotated);
        } else if (unwrappedType2 instanceof SimpleType) {
            SimpleType simpleType5;
            SimpleType $this$replaceArgumentsWith$iv$iv = (SimpleType)unwrapped$iv;
            boolean $i$f$replaceArgumentsWith3 = false;
            if ($this$replaceArgumentsWith$iv$iv.getConstructor().getParameters().isEmpty() || $this$replaceArgumentsWith$iv$iv.getConstructor().getDeclarationDescriptor() == null) {
                simpleType5 = $this$replaceArgumentsWith$iv$iv;
            } else {
                List<TypeParameterDescriptor> list = $this$replaceArgumentsWith$iv$iv.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(list, "constructor.parameters");
                Iterable $this$map$iv$iv$iv = list;
                boolean $i$f$map = false;
                Iterable $this$mapTo$iv$iv$iv$iv = $this$map$iv$iv$iv;
                Collection destination$iv$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv$iv$iv : $this$mapTo$iv$iv$iv$iv) {
                    void p1;
                    TypeParameterDescriptor bl = (TypeParameterDescriptor)item$iv$iv$iv$iv;
                    Collection collection = destination$iv$iv$iv$iv;
                    boolean $i$a$-unknown-TypeUtilsKt$replaceArgumentsWithStarProjections$2 = false;
                    StarProjectionImpl starProjectionImpl = new StarProjectionImpl((TypeParameterDescriptor)p1);
                    collection.add(starProjectionImpl);
                }
                List newArguments$iv$iv = (List)destination$iv$iv$iv$iv;
                simpleType5 = TypeSubstitutionKt.replace$default($this$replaceArgumentsWith$iv$iv, newArguments$iv$iv, null, 2, null);
            }
            unwrappedType = simpleType5;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(unwrappedType, unwrapped$iv);
    }

    public static final boolean containsTypeAliasParameters(@NotNull KotlinType $this$containsTypeAliasParameters) {
        Intrinsics.checkNotNullParameter($this$containsTypeAliasParameters, "$this$containsTypeAliasParameters");
        return TypeUtilsKt.contains($this$containsTypeAliasParameters, containsTypeAliasParameters.1.INSTANCE);
    }

    public static final boolean isTypeAliasParameter(@NotNull ClassifierDescriptor $this$isTypeAliasParameter) {
        Intrinsics.checkNotNullParameter($this$isTypeAliasParameter, "$this$isTypeAliasParameter");
        return $this$isTypeAliasParameter instanceof TypeParameterDescriptor && ((TypeParameterDescriptor)$this$isTypeAliasParameter).getContainingDeclaration() instanceof TypeAliasDescriptor;
    }

    public static final boolean requiresTypeAliasExpansion(@NotNull KotlinType $this$requiresTypeAliasExpansion) {
        Intrinsics.checkNotNullParameter($this$requiresTypeAliasExpansion, "$this$requiresTypeAliasExpansion");
        return TypeUtilsKt.contains($this$requiresTypeAliasExpansion, requiresTypeAliasExpansion.1.INSTANCE);
    }

    public static final boolean canHaveUndefinedNullability(@NotNull UnwrappedType $this$canHaveUndefinedNullability) {
        Intrinsics.checkNotNullParameter($this$canHaveUndefinedNullability, "$this$canHaveUndefinedNullability");
        return $this$canHaveUndefinedNullability.getConstructor() instanceof NewTypeVariableConstructor || $this$canHaveUndefinedNullability.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor || $this$canHaveUndefinedNullability instanceof NewCapturedType;
    }

    @NotNull
    public static final KotlinType getRepresentativeUpperBound(@NotNull TypeParameterDescriptor $this$representativeUpperBound) {
        KotlinType kotlinType;
        Object v4;
        block4: {
            Intrinsics.checkNotNullParameter($this$representativeUpperBound, "$this$representativeUpperBound");
            List<KotlinType> list = $this$representativeUpperBound.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(list, "upperBounds");
            Collection collection = list;
            boolean bl = false;
            boolean bl2 = !collection.isEmpty();
            bl = false;
            if (_Assertions.ENABLED && !bl2) {
                boolean bl3 = false;
                String string = "Upper bounds should not be empty: " + $this$representativeUpperBound;
                throw (Throwable)((Object)new AssertionError((Object)string));
            }
            List<KotlinType> list2 = $this$representativeUpperBound.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(list2, "upperBounds");
            Iterable $this$firstOrNull$iv = list2;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                ClassDescriptor classDescriptor;
                KotlinType it = (KotlinType)element$iv;
                boolean bl4 = false;
                ClassifierDescriptor classifierDescriptor = it.getConstructor().getDeclarationDescriptor();
                if (!(classifierDescriptor instanceof ClassDescriptor)) {
                    classifierDescriptor = null;
                }
                boolean bl5 = (ClassDescriptor)classifierDescriptor == null ? false : classDescriptor.getKind() != ClassKind.INTERFACE && classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS;
                if (!bl5) continue;
                v4 = element$iv;
                break block4;
            }
            v4 = null;
        }
        if ((kotlinType = (KotlinType)v4) == null) {
            List<KotlinType> list = $this$representativeUpperBound.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(list, "upperBounds");
            KotlinType kotlinType2 = CollectionsKt.first(list);
            Intrinsics.checkNotNullExpressionValue(kotlinType2, "upperBounds.first()");
            kotlinType = kotlinType2;
        }
        return kotlinType;
    }
}

