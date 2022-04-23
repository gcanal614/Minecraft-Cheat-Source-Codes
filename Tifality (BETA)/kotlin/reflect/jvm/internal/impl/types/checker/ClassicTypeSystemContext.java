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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbstractStubType;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContextKt;
import kotlin.reflect.jvm.internal.impl.types.checker.IntersectionTypeKt;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeKt;
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
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClassicTypeSystemContext
extends TypeSystemCommonBackendContext,
TypeSystemInferenceExtensionContext {
    @Override
    @Nullable
    public SimpleTypeMarker asSimpleType(@NotNull KotlinTypeMarker var1);

    @Override
    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull SimpleTypeMarker var1);

    public static final class DefaultImpls {
        public static boolean isDenotable(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isDenotable) {
            Intrinsics.checkNotNullParameter($this$isDenotable, "$this$isDenotable");
            boolean bl = $this$isDenotable instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isDenotable;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((TypeConstructor)$this$isDenotable).isDenotable();
        }

        public static boolean isIntegerLiteralTypeConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isIntegerLiteralTypeConstructor) {
            Intrinsics.checkNotNullParameter($this$isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
            boolean bl = $this$isIntegerLiteralTypeConstructor instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isIntegerLiteralTypeConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return $this$isIntegerLiteralTypeConstructor instanceof IntegerLiteralTypeConstructor;
        }

        @NotNull
        public static Collection<KotlinTypeMarker> possibleIntegerTypes(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$possibleIntegerTypes) {
            Intrinsics.checkNotNullParameter($this$possibleIntegerTypes, "$this$possibleIntegerTypes");
            TypeConstructorMarker typeConstructor2 = $this.typeConstructor($this$possibleIntegerTypes);
            boolean bl = typeConstructor2 instanceof IntegerLiteralTypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$possibleIntegerTypes;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((IntegerLiteralTypeConstructor)typeConstructor2).getPossibleTypes();
        }

        @NotNull
        public static SimpleTypeMarker withNullability(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$withNullability, boolean nullable) {
            Intrinsics.checkNotNullParameter($this$withNullability, "$this$withNullability");
            boolean bl = $this$withNullability instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$withNullability;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((SimpleType)$this$withNullability).makeNullableAsSpecified(nullable);
        }

        public static boolean isError(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isError) {
            Intrinsics.checkNotNullParameter($this$isError, "$this$isError");
            boolean bl = $this$isError instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$isError;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return KotlinTypeKt.isError((KotlinType)$this$isError);
        }

        public static boolean isStubType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isStubType) {
            Intrinsics.checkNotNullParameter($this$isStubType, "$this$isStubType");
            boolean bl = $this$isStubType instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$isStubType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return $this$isStubType instanceof AbstractStubType;
        }

        @Nullable
        public static KotlinTypeMarker lowerType(@NotNull ClassicTypeSystemContext $this, @NotNull CapturedTypeMarker $this$lowerType) {
            Intrinsics.checkNotNullParameter($this$lowerType, "$this$lowerType");
            boolean bl = $this$lowerType instanceof NewCapturedType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                CapturedTypeMarker $this$errorMessage$iv = $this$lowerType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((NewCapturedType)$this$lowerType).getLowerType();
        }

        public static boolean isIntersection(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isIntersection) {
            Intrinsics.checkNotNullParameter($this$isIntersection, "$this$isIntersection");
            boolean bl = $this$isIntersection instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isIntersection;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return $this$isIntersection instanceof IntersectionTypeConstructor;
        }

        public static boolean identicalArguments(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker a2, @NotNull SimpleTypeMarker b2) {
            Intrinsics.checkNotNullParameter(a2, "a");
            Intrinsics.checkNotNullParameter(b2, "b");
            boolean bl = a2 instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = a2;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            bl = b2 instanceof SimpleType;
            bl2 = false;
            bl3 = false;
            if (!bl) {
                boolean bl5 = false;
                SimpleTypeMarker $this$errorMessage$iv = b2;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((SimpleType)a2).getArguments() == ((SimpleType)b2).getArguments();
        }

        @Nullable
        public static SimpleTypeMarker asSimpleType(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$asSimpleType) {
            Intrinsics.checkNotNullParameter($this$asSimpleType, "$this$asSimpleType");
            boolean bl = $this$asSimpleType instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$asSimpleType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            UnwrappedType unwrappedType = ((KotlinType)$this$asSimpleType).unwrap();
            if (!(unwrappedType instanceof SimpleType)) {
                unwrappedType = null;
            }
            return (SimpleType)unwrappedType;
        }

        @Nullable
        public static FlexibleTypeMarker asFlexibleType(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$asFlexibleType) {
            Intrinsics.checkNotNullParameter($this$asFlexibleType, "$this$asFlexibleType");
            boolean bl = $this$asFlexibleType instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$asFlexibleType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            UnwrappedType unwrappedType = ((KotlinType)$this$asFlexibleType).unwrap();
            if (!(unwrappedType instanceof FlexibleType)) {
                unwrappedType = null;
            }
            return (FlexibleType)unwrappedType;
        }

        @Nullable
        public static DynamicTypeMarker asDynamicType(@NotNull ClassicTypeSystemContext $this, @NotNull FlexibleTypeMarker $this$asDynamicType) {
            Intrinsics.checkNotNullParameter($this$asDynamicType, "$this$asDynamicType");
            boolean bl = $this$asDynamicType instanceof FlexibleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                FlexibleTypeMarker $this$errorMessage$iv = $this$asDynamicType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            FlexibleTypeMarker flexibleTypeMarker = $this$asDynamicType;
            if (!(flexibleTypeMarker instanceof DynamicType)) {
                flexibleTypeMarker = null;
            }
            return (DynamicType)flexibleTypeMarker;
        }

        @NotNull
        public static SimpleTypeMarker upperBound(@NotNull ClassicTypeSystemContext $this, @NotNull FlexibleTypeMarker $this$upperBound) {
            Intrinsics.checkNotNullParameter($this$upperBound, "$this$upperBound");
            boolean bl = $this$upperBound instanceof FlexibleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                FlexibleTypeMarker $this$errorMessage$iv = $this$upperBound;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((FlexibleType)$this$upperBound).getUpperBound();
        }

        @NotNull
        public static SimpleTypeMarker lowerBound(@NotNull ClassicTypeSystemContext $this, @NotNull FlexibleTypeMarker $this$lowerBound) {
            Intrinsics.checkNotNullParameter($this$lowerBound, "$this$lowerBound");
            boolean bl = $this$lowerBound instanceof FlexibleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                FlexibleTypeMarker $this$errorMessage$iv = $this$lowerBound;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((FlexibleType)$this$lowerBound).getLowerBound();
        }

        @Nullable
        public static CapturedTypeMarker asCapturedType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$asCapturedType) {
            Intrinsics.checkNotNullParameter($this$asCapturedType, "$this$asCapturedType");
            boolean bl = $this$asCapturedType instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$asCapturedType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            SimpleTypeMarker simpleTypeMarker = $this$asCapturedType;
            if (!(simpleTypeMarker instanceof NewCapturedType)) {
                simpleTypeMarker = null;
            }
            return (NewCapturedType)simpleTypeMarker;
        }

        @Nullable
        public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$asDefinitelyNotNullType) {
            Intrinsics.checkNotNullParameter($this$asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
            boolean bl = $this$asDefinitelyNotNullType instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$asDefinitelyNotNullType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            SimpleTypeMarker simpleTypeMarker = $this$asDefinitelyNotNullType;
            if (!(simpleTypeMarker instanceof DefinitelyNotNullType)) {
                simpleTypeMarker = null;
            }
            return (DefinitelyNotNullType)simpleTypeMarker;
        }

        public static boolean isMarkedNullable(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isMarkedNullable) {
            Intrinsics.checkNotNullParameter($this$isMarkedNullable, "$this$isMarkedNullable");
            boolean bl = $this$isMarkedNullable instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$isMarkedNullable;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((SimpleType)$this$isMarkedNullable).isMarkedNullable();
        }

        @NotNull
        public static TypeConstructorMarker typeConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$typeConstructor) {
            Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
            boolean bl = $this$typeConstructor instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$typeConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((SimpleType)$this$typeConstructor).getConstructor();
        }

        public static int argumentsCount(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$argumentsCount) {
            Intrinsics.checkNotNullParameter($this$argumentsCount, "$this$argumentsCount");
            boolean bl = $this$argumentsCount instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$argumentsCount;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((KotlinType)$this$argumentsCount).getArguments().size();
        }

        @NotNull
        public static TypeArgumentMarker getArgument(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$getArgument, int index) {
            Intrinsics.checkNotNullParameter($this$getArgument, "$this$getArgument");
            boolean bl = $this$getArgument instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$getArgument;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((KotlinType)$this$getArgument).getArguments().get(index);
        }

        public static boolean isStarProjection(@NotNull ClassicTypeSystemContext $this, @NotNull TypeArgumentMarker $this$isStarProjection) {
            Intrinsics.checkNotNullParameter($this$isStarProjection, "$this$isStarProjection");
            boolean bl = $this$isStarProjection instanceof TypeProjection;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeArgumentMarker $this$errorMessage$iv = $this$isStarProjection;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((TypeProjection)$this$isStarProjection).isStarProjection();
        }

        @NotNull
        public static TypeVariance getVariance(@NotNull ClassicTypeSystemContext $this, @NotNull TypeArgumentMarker $this$getVariance) {
            Intrinsics.checkNotNullParameter($this$getVariance, "$this$getVariance");
            boolean bl = $this$getVariance instanceof TypeProjection;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeArgumentMarker $this$errorMessage$iv = $this$getVariance;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            Variance variance = ((TypeProjection)$this$getVariance).getProjectionKind();
            Intrinsics.checkNotNullExpressionValue((Object)variance, "this.projectionKind");
            return ClassicTypeSystemContextKt.convertVariance(variance);
        }

        @NotNull
        public static KotlinTypeMarker getType(@NotNull ClassicTypeSystemContext $this, @NotNull TypeArgumentMarker $this$getType) {
            Intrinsics.checkNotNullParameter($this$getType, "$this$getType");
            boolean bl = $this$getType instanceof TypeProjection;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeArgumentMarker $this$errorMessage$iv = $this$getType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((TypeProjection)$this$getType).getType().unwrap();
        }

        public static int parametersCount(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$parametersCount) {
            Intrinsics.checkNotNullParameter($this$parametersCount, "$this$parametersCount");
            boolean bl = $this$parametersCount instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$parametersCount;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((TypeConstructor)$this$parametersCount).getParameters().size();
        }

        @NotNull
        public static TypeParameterMarker getParameter(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$getParameter, int index) {
            Intrinsics.checkNotNullParameter($this$getParameter, "$this$getParameter");
            boolean bl = $this$getParameter instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$getParameter;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            TypeParameterDescriptor typeParameterDescriptor = ((TypeConstructor)$this$getParameter).getParameters().get(index);
            Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "this.parameters[index]");
            return typeParameterDescriptor;
        }

        @NotNull
        public static Collection<KotlinTypeMarker> supertypes(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$supertypes) {
            Intrinsics.checkNotNullParameter($this$supertypes, "$this$supertypes");
            boolean bl = $this$supertypes instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$supertypes;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            Collection<KotlinTypeMarker> collection = ((TypeConstructor)$this$supertypes).getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collection, "this.supertypes");
            return collection;
        }

        @NotNull
        public static TypeVariance getVariance(@NotNull ClassicTypeSystemContext $this, @NotNull TypeParameterMarker $this$getVariance) {
            Intrinsics.checkNotNullParameter($this$getVariance, "$this$getVariance");
            boolean bl = $this$getVariance instanceof TypeParameterDescriptor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeParameterMarker $this$errorMessage$iv = $this$getVariance;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            Variance variance = ((TypeParameterDescriptor)$this$getVariance).getVariance();
            Intrinsics.checkNotNullExpressionValue((Object)variance, "this.variance");
            return ClassicTypeSystemContextKt.convertVariance(variance);
        }

        public static boolean isEqualTypeConstructors(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker c1, @NotNull TypeConstructorMarker c2) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            boolean bl = c1 instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = c1;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            bl = c2 instanceof TypeConstructor;
            bl2 = false;
            bl3 = false;
            if (!bl) {
                boolean bl5 = false;
                TypeConstructorMarker $this$errorMessage$iv = c2;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return Intrinsics.areEqual(c1, c2);
        }

        public static boolean isClassTypeConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isClassTypeConstructor) {
            Intrinsics.checkNotNullParameter($this$isClassTypeConstructor, "$this$isClassTypeConstructor");
            boolean bl = $this$isClassTypeConstructor instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isClassTypeConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((TypeConstructor)$this$isClassTypeConstructor).getDeclarationDescriptor() instanceof ClassDescriptor;
        }

        public static boolean isCommonFinalClassConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isCommonFinalClassConstructor) {
            Intrinsics.checkNotNullParameter($this$isCommonFinalClassConstructor, "$this$isCommonFinalClassConstructor");
            boolean bl = $this$isCommonFinalClassConstructor instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isCommonFinalClassConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$isCommonFinalClassConstructor).getDeclarationDescriptor();
            if (!(classifierDescriptor instanceof ClassDescriptor)) {
                classifierDescriptor = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor;
            if (classDescriptor == null) {
                return false;
            }
            ClassDescriptor classDescriptor2 = classDescriptor;
            return ModalityKt.isFinalClass(classDescriptor2) && classDescriptor2.getKind() != ClassKind.ENUM_ENTRY && classDescriptor2.getKind() != ClassKind.ANNOTATION_CLASS;
        }

        @NotNull
        public static TypeArgumentListMarker asArgumentList(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$asArgumentList) {
            Intrinsics.checkNotNullParameter($this$asArgumentList, "$this$asArgumentList");
            boolean bl = $this$asArgumentList instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$asArgumentList;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return (TypeArgumentListMarker)((Object)$this$asArgumentList);
        }

        @Nullable
        public static SimpleTypeMarker captureFromArguments(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker type2, @NotNull CaptureStatus status) {
            Intrinsics.checkNotNullParameter(type2, "type");
            Intrinsics.checkNotNullParameter((Object)status, "status");
            boolean bl = type2 instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = type2;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return NewCapturedTypeKt.captureFromArguments((SimpleType)type2, status);
        }

        public static boolean isAnyConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isAnyConstructor) {
            Intrinsics.checkNotNullParameter($this$isAnyConstructor, "$this$isAnyConstructor");
            boolean bl = $this$isAnyConstructor instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isAnyConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor)$this$isAnyConstructor, KotlinBuiltIns.FQ_NAMES.any);
        }

        public static boolean isNothingConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isNothingConstructor) {
            Intrinsics.checkNotNullParameter($this$isNothingConstructor, "$this$isNothingConstructor");
            boolean bl = $this$isNothingConstructor instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isNothingConstructor;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor)$this$isNothingConstructor, KotlinBuiltIns.FQ_NAMES.nothing);
        }

        @NotNull
        public static TypeArgumentMarker asTypeArgument(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$asTypeArgument) {
            Intrinsics.checkNotNullParameter($this$asTypeArgument, "$this$asTypeArgument");
            boolean bl = $this$asTypeArgument instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$asTypeArgument;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return TypeUtilsKt.asTypeProjection((KotlinType)$this$asTypeArgument);
        }

        public static boolean isSingleClassifierType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isSingleClassifierType) {
            Intrinsics.checkNotNullParameter($this$isSingleClassifierType, "$this$isSingleClassifierType");
            boolean bl = $this$isSingleClassifierType instanceof SimpleType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$isSingleClassifierType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return !KotlinTypeKt.isError((KotlinType)((Object)$this$isSingleClassifierType)) && !(((SimpleType)$this$isSingleClassifierType).getConstructor().getDeclarationDescriptor() instanceof TypeAliasDescriptor) && (((SimpleType)$this$isSingleClassifierType).getConstructor().getDeclarationDescriptor() != null || $this$isSingleClassifierType instanceof CapturedType || $this$isSingleClassifierType instanceof NewCapturedType || $this$isSingleClassifierType instanceof DefinitelyNotNullType || ((SimpleType)$this$isSingleClassifierType).getConstructor() instanceof IntegerLiteralTypeConstructor);
        }

        @NotNull
        public static KotlinTypeMarker intersectTypes(@NotNull ClassicTypeSystemContext $this, @NotNull List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return IntersectionTypeKt.intersectTypes(types);
        }

        @NotNull
        public static AbstractTypeCheckerContext newBaseTypeCheckerContext(@NotNull ClassicTypeSystemContext $this, boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
            return new ClassicTypeCheckerContext(errorTypesEqualToAnything, stubTypesEqualToAnything, false, null, 12, null);
        }

        public static boolean isProjectionNotNull(@NotNull ClassicTypeSystemContext $this, @NotNull CapturedTypeMarker $this$isProjectionNotNull) {
            Intrinsics.checkNotNullParameter($this$isProjectionNotNull, "$this$isProjectionNotNull");
            boolean bl = $this$isProjectionNotNull instanceof NewCapturedType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                CapturedTypeMarker $this$errorMessage$iv = $this$isProjectionNotNull;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((NewCapturedType)$this$isProjectionNotNull).isProjectionNotNull();
        }

        public static boolean isNullableType(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isNullableType) {
            Intrinsics.checkNotNullParameter($this$isNullableType, "$this$isNullableType");
            boolean bl = $this$isNullableType instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$isNullableType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return TypeUtils.isNullableType((KotlinType)$this$isNullableType);
        }

        public static boolean isPrimitiveType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isPrimitiveType) {
            Intrinsics.checkNotNullParameter($this$isPrimitiveType, "$this$isPrimitiveType");
            boolean bl = $this$isPrimitiveType instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                SimpleTypeMarker $this$errorMessage$iv = $this$isPrimitiveType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return KotlinBuiltIns.isPrimitiveType((KotlinType)((Object)$this$isPrimitiveType));
        }

        public static boolean hasAnnotation(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$hasAnnotation, @NotNull FqName fqName2) {
            Intrinsics.checkNotNullParameter($this$hasAnnotation, "$this$hasAnnotation");
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            boolean bl = $this$hasAnnotation instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$hasAnnotation;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return ((KotlinType)$this$hasAnnotation).getAnnotations().hasAnnotation(fqName2);
        }

        @Nullable
        public static TypeParameterMarker getTypeParameterClassifier(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$getTypeParameterClassifier) {
            Intrinsics.checkNotNullParameter($this$getTypeParameterClassifier, "$this$getTypeParameterClassifier");
            boolean bl = $this$getTypeParameterClassifier instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$getTypeParameterClassifier;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$getTypeParameterClassifier).getDeclarationDescriptor();
            if (!(classifierDescriptor instanceof TypeParameterDescriptor)) {
                classifierDescriptor = null;
            }
            return (TypeParameterDescriptor)classifierDescriptor;
        }

        public static boolean isInlineClass(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isInlineClass) {
            Intrinsics.checkNotNullParameter($this$isInlineClass, "$this$isInlineClass");
            boolean bl = $this$isInlineClass instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isInlineClass;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$isInlineClass).getDeclarationDescriptor();
            if (!(classifierDescriptor instanceof ClassDescriptor)) {
                classifierDescriptor = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor;
            return classDescriptor != null && classDescriptor.isInline();
        }

        @NotNull
        public static KotlinTypeMarker getRepresentativeUpperBound(@NotNull ClassicTypeSystemContext $this, @NotNull TypeParameterMarker $this$getRepresentativeUpperBound) {
            Intrinsics.checkNotNullParameter($this$getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
            boolean bl = $this$getRepresentativeUpperBound instanceof TypeParameterDescriptor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeParameterMarker $this$errorMessage$iv = $this$getRepresentativeUpperBound;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor)$this$getRepresentativeUpperBound);
        }

        @Nullable
        public static KotlinTypeMarker getSubstitutedUnderlyingType(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$getSubstitutedUnderlyingType) {
            Intrinsics.checkNotNullParameter($this$getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
            boolean bl = $this$getSubstitutedUnderlyingType instanceof KotlinType;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                KotlinTypeMarker $this$errorMessage$iv = $this$getSubstitutedUnderlyingType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            return InlineClassesUtilsKt.substitutedUnderlyingType((KotlinType)$this$getSubstitutedUnderlyingType);
        }

        @Nullable
        public static PrimitiveType getPrimitiveType(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$getPrimitiveType) {
            Intrinsics.checkNotNullParameter($this$getPrimitiveType, "$this$getPrimitiveType");
            boolean bl = $this$getPrimitiveType instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$getPrimitiveType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$getPrimitiveType).getDeclarationDescriptor();
            if (classifierDescriptor == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            return KotlinBuiltIns.getPrimitiveType((ClassDescriptor)classifierDescriptor);
        }

        @Nullable
        public static PrimitiveType getPrimitiveArrayType(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$getPrimitiveArrayType) {
            Intrinsics.checkNotNullParameter($this$getPrimitiveArrayType, "$this$getPrimitiveArrayType");
            boolean bl = $this$getPrimitiveArrayType instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$getPrimitiveArrayType;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$getPrimitiveArrayType).getDeclarationDescriptor();
            if (classifierDescriptor == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            return KotlinBuiltIns.getPrimitiveArrayType((ClassDescriptor)classifierDescriptor);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static boolean isUnderKotlinPackage(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$isUnderKotlinPackage) {
            Intrinsics.checkNotNullParameter($this$isUnderKotlinPackage, "$this$isUnderKotlinPackage");
            boolean bl = $this$isUnderKotlinPackage instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$isUnderKotlinPackage;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$isUnderKotlinPackage).getDeclarationDescriptor();
            if (classifierDescriptor == null) return false;
            ClassifierDescriptor classifierDescriptor2 = classifierDescriptor;
            bl2 = false;
            bl3 = false;
            DeclarationDescriptor p1 = classifierDescriptor2;
            boolean bl5 = false;
            if (!KotlinBuiltIns.isUnderKotlinPackage(p1)) return false;
            return true;
        }

        @NotNull
        public static FqNameUnsafe getClassFqNameUnsafe(@NotNull ClassicTypeSystemContext $this, @NotNull TypeConstructorMarker $this$getClassFqNameUnsafe) {
            Intrinsics.checkNotNullParameter($this$getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
            boolean bl = $this$getClassFqNameUnsafe instanceof TypeConstructor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                TypeConstructorMarker $this$errorMessage$iv = $this$getClassFqNameUnsafe;
                boolean $i$f$errorMessage = false;
                String string = "ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + Reflection.getOrCreateKotlinClass($this$errorMessage$iv.getClass());
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            ClassifierDescriptor classifierDescriptor = ((TypeConstructor)$this$getClassFqNameUnsafe).getDeclarationDescriptor();
            if (classifierDescriptor == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            return DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor)classifierDescriptor);
        }

        public static boolean isMarkedNullable(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isMarkedNullable) {
            Intrinsics.checkNotNullParameter($this$isMarkedNullable, "$this$isMarkedNullable");
            return TypeSystemCommonBackendContext.DefaultImpls.isMarkedNullable($this, $this$isMarkedNullable);
        }

        @NotNull
        public static TypeConstructorMarker typeConstructor(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$typeConstructor) {
            Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
            return TypeSystemInferenceExtensionContext.DefaultImpls.typeConstructor($this, $this$typeConstructor);
        }

        @Nullable
        public static List<SimpleTypeMarker> fastCorrespondingSupertypes(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$fastCorrespondingSupertypes, @NotNull TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return TypeSystemInferenceExtensionContext.DefaultImpls.fastCorrespondingSupertypes($this, $this$fastCorrespondingSupertypes, constructor);
        }

        @NotNull
        public static TypeArgumentMarker get(@NotNull ClassicTypeSystemContext $this, @NotNull TypeArgumentListMarker $this$get, int index) {
            Intrinsics.checkNotNullParameter($this$get, "$this$get");
            return TypeSystemInferenceExtensionContext.DefaultImpls.get($this, $this$get, index);
        }

        @Nullable
        public static TypeArgumentMarker getArgumentOrNull(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$getArgumentOrNull, int index) {
            Intrinsics.checkNotNullParameter($this$getArgumentOrNull, "$this$getArgumentOrNull");
            return TypeSystemInferenceExtensionContext.DefaultImpls.getArgumentOrNull($this, $this$getArgumentOrNull, index);
        }

        public static boolean hasFlexibleNullability(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$hasFlexibleNullability) {
            Intrinsics.checkNotNullParameter($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
            return TypeSystemInferenceExtensionContext.DefaultImpls.hasFlexibleNullability($this, $this$hasFlexibleNullability);
        }

        public static boolean isClassType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isClassType) {
            Intrinsics.checkNotNullParameter($this$isClassType, "$this$isClassType");
            return TypeSystemInferenceExtensionContext.DefaultImpls.isClassType($this, $this$isClassType);
        }

        public static boolean isDefinitelyNotNullType(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isDefinitelyNotNullType) {
            Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
            return TypeSystemInferenceExtensionContext.DefaultImpls.isDefinitelyNotNullType($this, $this$isDefinitelyNotNullType);
        }

        public static boolean isDynamic(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isDynamic) {
            Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
            return TypeSystemInferenceExtensionContext.DefaultImpls.isDynamic($this, $this$isDynamic);
        }

        public static boolean isIntegerLiteralType(@NotNull ClassicTypeSystemContext $this, @NotNull SimpleTypeMarker $this$isIntegerLiteralType) {
            Intrinsics.checkNotNullParameter($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
            return TypeSystemInferenceExtensionContext.DefaultImpls.isIntegerLiteralType($this, $this$isIntegerLiteralType);
        }

        public static boolean isNothing(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$isNothing) {
            Intrinsics.checkNotNullParameter($this$isNothing, "$this$isNothing");
            return TypeSystemInferenceExtensionContext.DefaultImpls.isNothing($this, $this$isNothing);
        }

        @NotNull
        public static SimpleTypeMarker lowerBoundIfFlexible(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$lowerBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
            return TypeSystemInferenceExtensionContext.DefaultImpls.lowerBoundIfFlexible($this, $this$lowerBoundIfFlexible);
        }

        public static int size(@NotNull ClassicTypeSystemContext $this, @NotNull TypeArgumentListMarker $this$size) {
            Intrinsics.checkNotNullParameter($this$size, "$this$size");
            return TypeSystemInferenceExtensionContext.DefaultImpls.size($this, $this$size);
        }

        @NotNull
        public static SimpleTypeMarker upperBoundIfFlexible(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$upperBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
            return TypeSystemInferenceExtensionContext.DefaultImpls.upperBoundIfFlexible($this, $this$upperBoundIfFlexible);
        }

        @NotNull
        public static KotlinTypeMarker makeNullable(@NotNull ClassicTypeSystemContext $this, @NotNull KotlinTypeMarker $this$makeNullable) {
            Intrinsics.checkNotNullParameter($this$makeNullable, "$this$makeNullable");
            return TypeSystemCommonBackendContext.DefaultImpls.makeNullable($this, $this$makeNullable);
        }
    }
}

