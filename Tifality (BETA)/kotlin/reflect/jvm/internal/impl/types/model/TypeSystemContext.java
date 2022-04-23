/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
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
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeSystemContext
extends TypeSystemOptimizationContext {
    @Nullable
    public SimpleTypeMarker asSimpleType(@NotNull KotlinTypeMarker var1);

    @Nullable
    public FlexibleTypeMarker asFlexibleType(@NotNull KotlinTypeMarker var1);

    public boolean isError(@NotNull KotlinTypeMarker var1);

    @Nullable
    public DynamicTypeMarker asDynamicType(@NotNull FlexibleTypeMarker var1);

    @NotNull
    public SimpleTypeMarker upperBound(@NotNull FlexibleTypeMarker var1);

    @NotNull
    public SimpleTypeMarker lowerBound(@NotNull FlexibleTypeMarker var1);

    @Nullable
    public CapturedTypeMarker asCapturedType(@NotNull SimpleTypeMarker var1);

    @Nullable
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(@NotNull SimpleTypeMarker var1);

    public boolean isMarkedNullable(@NotNull SimpleTypeMarker var1);

    @NotNull
    public SimpleTypeMarker withNullability(@NotNull SimpleTypeMarker var1, boolean var2);

    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull SimpleTypeMarker var1);

    public boolean isProjectionNotNull(@NotNull CapturedTypeMarker var1);

    public int argumentsCount(@NotNull KotlinTypeMarker var1);

    @NotNull
    public TypeArgumentMarker getArgument(@NotNull KotlinTypeMarker var1, int var2);

    public boolean isStubType(@NotNull SimpleTypeMarker var1);

    @NotNull
    public TypeArgumentMarker asTypeArgument(@NotNull KotlinTypeMarker var1);

    @Nullable
    public KotlinTypeMarker lowerType(@NotNull CapturedTypeMarker var1);

    public boolean isStarProjection(@NotNull TypeArgumentMarker var1);

    @NotNull
    public TypeVariance getVariance(@NotNull TypeArgumentMarker var1);

    @NotNull
    public KotlinTypeMarker getType(@NotNull TypeArgumentMarker var1);

    public int parametersCount(@NotNull TypeConstructorMarker var1);

    @NotNull
    public TypeParameterMarker getParameter(@NotNull TypeConstructorMarker var1, int var2);

    @NotNull
    public Collection<KotlinTypeMarker> supertypes(@NotNull TypeConstructorMarker var1);

    public boolean isIntersection(@NotNull TypeConstructorMarker var1);

    public boolean isClassTypeConstructor(@NotNull TypeConstructorMarker var1);

    public boolean isIntegerLiteralTypeConstructor(@NotNull TypeConstructorMarker var1);

    @NotNull
    public TypeVariance getVariance(@NotNull TypeParameterMarker var1);

    public boolean isEqualTypeConstructors(@NotNull TypeConstructorMarker var1, @NotNull TypeConstructorMarker var2);

    public boolean isDenotable(@NotNull TypeConstructorMarker var1);

    @NotNull
    public SimpleTypeMarker lowerBoundIfFlexible(@NotNull KotlinTypeMarker var1);

    @NotNull
    public SimpleTypeMarker upperBoundIfFlexible(@NotNull KotlinTypeMarker var1);

    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull KotlinTypeMarker var1);

    public boolean isNullableType(@NotNull KotlinTypeMarker var1);

    @NotNull
    public Collection<KotlinTypeMarker> possibleIntegerTypes(@NotNull SimpleTypeMarker var1);

    public boolean isCommonFinalClassConstructor(@NotNull TypeConstructorMarker var1);

    @Nullable
    public SimpleTypeMarker captureFromArguments(@NotNull SimpleTypeMarker var1, @NotNull CaptureStatus var2);

    @NotNull
    public TypeArgumentListMarker asArgumentList(@NotNull SimpleTypeMarker var1);

    @NotNull
    public TypeArgumentMarker get(@NotNull TypeArgumentListMarker var1, int var2);

    public int size(@NotNull TypeArgumentListMarker var1);

    public boolean isAnyConstructor(@NotNull TypeConstructorMarker var1);

    public boolean isNothingConstructor(@NotNull TypeConstructorMarker var1);

    public boolean isSingleClassifierType(@NotNull SimpleTypeMarker var1);

    @NotNull
    public KotlinTypeMarker intersectTypes(@NotNull List<? extends KotlinTypeMarker> var1);

    public boolean isPrimitiveType(@NotNull SimpleTypeMarker var1);

    public static final class DefaultImpls {
        @Nullable
        public static TypeArgumentMarker getArgumentOrNull(@NotNull TypeSystemContext $this, @NotNull SimpleTypeMarker $this$getArgumentOrNull, int index) {
            Intrinsics.checkNotNullParameter($this$getArgumentOrNull, "$this$getArgumentOrNull");
            int n = index;
            if (0 <= n && $this.argumentsCount($this$getArgumentOrNull) > n) {
                return $this.getArgument($this$getArgumentOrNull, index);
            }
            return null;
        }

        @NotNull
        public static SimpleTypeMarker lowerBoundIfFlexible(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$lowerBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
            KotlinTypeMarker kotlinTypeMarker = $this.asFlexibleType($this$lowerBoundIfFlexible);
            if (kotlinTypeMarker == null || (kotlinTypeMarker = $this.lowerBound((FlexibleTypeMarker)kotlinTypeMarker)) == null) {
                SimpleTypeMarker simpleTypeMarker = $this.asSimpleType($this$lowerBoundIfFlexible);
                kotlinTypeMarker = simpleTypeMarker;
                Intrinsics.checkNotNull(simpleTypeMarker);
            }
            return kotlinTypeMarker;
        }

        @NotNull
        public static SimpleTypeMarker upperBoundIfFlexible(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$upperBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
            KotlinTypeMarker kotlinTypeMarker = $this.asFlexibleType($this$upperBoundIfFlexible);
            if (kotlinTypeMarker == null || (kotlinTypeMarker = $this.upperBound((FlexibleTypeMarker)kotlinTypeMarker)) == null) {
                SimpleTypeMarker simpleTypeMarker = $this.asSimpleType($this$upperBoundIfFlexible);
                kotlinTypeMarker = simpleTypeMarker;
                Intrinsics.checkNotNull(simpleTypeMarker);
            }
            return kotlinTypeMarker;
        }

        public static boolean isDynamic(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$isDynamic) {
            Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
            FlexibleTypeMarker flexibleTypeMarker = $this.asFlexibleType($this$isDynamic);
            return (flexibleTypeMarker != null ? $this.asDynamicType(flexibleTypeMarker) : null) != null;
        }

        public static boolean isDefinitelyNotNullType(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$isDefinitelyNotNullType) {
            Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
            SimpleTypeMarker simpleTypeMarker = $this.asSimpleType($this$isDefinitelyNotNullType);
            return (simpleTypeMarker != null ? $this.asDefinitelyNotNullType(simpleTypeMarker) : null) != null;
        }

        public static boolean hasFlexibleNullability(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$hasFlexibleNullability) {
            Intrinsics.checkNotNullParameter($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
            return $this.isMarkedNullable($this.lowerBoundIfFlexible($this$hasFlexibleNullability)) != $this.isMarkedNullable($this.upperBoundIfFlexible($this$hasFlexibleNullability));
        }

        @NotNull
        public static TypeConstructorMarker typeConstructor(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$typeConstructor) {
            Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
            SimpleTypeMarker simpleTypeMarker = $this.asSimpleType($this$typeConstructor);
            if (simpleTypeMarker == null) {
                simpleTypeMarker = $this.lowerBoundIfFlexible($this$typeConstructor);
            }
            return $this.typeConstructor(simpleTypeMarker);
        }

        public static boolean isNothing(@NotNull TypeSystemContext $this, @NotNull KotlinTypeMarker $this$isNothing) {
            Intrinsics.checkNotNullParameter($this$isNothing, "$this$isNothing");
            return $this.isNothingConstructor($this.typeConstructor($this$isNothing)) && !$this.isNullableType($this$isNothing);
        }

        public static boolean isClassType(@NotNull TypeSystemContext $this, @NotNull SimpleTypeMarker $this$isClassType) {
            Intrinsics.checkNotNullParameter($this$isClassType, "$this$isClassType");
            return $this.isClassTypeConstructor($this.typeConstructor($this$isClassType));
        }

        @Nullable
        public static List<SimpleTypeMarker> fastCorrespondingSupertypes(@NotNull TypeSystemContext $this, @NotNull SimpleTypeMarker $this$fastCorrespondingSupertypes, @NotNull TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return null;
        }

        public static boolean isIntegerLiteralType(@NotNull TypeSystemContext $this, @NotNull SimpleTypeMarker $this$isIntegerLiteralType) {
            Intrinsics.checkNotNullParameter($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
            return $this.isIntegerLiteralTypeConstructor($this.typeConstructor($this$isIntegerLiteralType));
        }

        @NotNull
        public static TypeArgumentMarker get(@NotNull TypeSystemContext $this, @NotNull TypeArgumentListMarker $this$get, int index) {
            TypeArgumentMarker typeArgumentMarker;
            Intrinsics.checkNotNullParameter($this$get, "$this$get");
            TypeArgumentListMarker typeArgumentListMarker = $this$get;
            if (typeArgumentListMarker instanceof SimpleTypeMarker) {
                typeArgumentMarker = $this.getArgument((KotlinTypeMarker)((Object)$this$get), index);
            } else if (typeArgumentListMarker instanceof ArgumentList) {
                Object e = ((ArgumentList)$this$get).get(index);
                Intrinsics.checkNotNullExpressionValue(e, "get(index)");
                typeArgumentMarker = (TypeArgumentMarker)e;
            } else {
                String string = "unknown type argument list type: " + $this$get + ", " + Reflection.getOrCreateKotlinClass($this$get.getClass());
                boolean bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            return typeArgumentMarker;
        }

        public static int size(@NotNull TypeSystemContext $this, @NotNull TypeArgumentListMarker $this$size) {
            int n;
            Intrinsics.checkNotNullParameter($this$size, "$this$size");
            TypeArgumentListMarker typeArgumentListMarker = $this$size;
            if (typeArgumentListMarker instanceof SimpleTypeMarker) {
                n = $this.argumentsCount((KotlinTypeMarker)((Object)$this$size));
            } else if (typeArgumentListMarker instanceof ArgumentList) {
                n = ((ArgumentList)$this$size).size();
            } else {
                String string = "unknown type argument list type: " + $this$size + ", " + Reflection.getOrCreateKotlinClass($this$size.getClass());
                boolean bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            return n;
        }

        public static boolean identicalArguments(@NotNull TypeSystemContext $this, @NotNull SimpleTypeMarker a2, @NotNull SimpleTypeMarker b2) {
            Intrinsics.checkNotNullParameter(a2, "a");
            Intrinsics.checkNotNullParameter(b2, "b");
            return TypeSystemOptimizationContext.DefaultImpls.identicalArguments($this, a2, b2);
        }
    }
}

