/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemCommonSuperTypesContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeSystemInferenceExtensionContext
extends TypeSystemCommonSuperTypesContext,
TypeSystemContext {

    public static final class DefaultImpls {
        @NotNull
        public static TypeConstructorMarker typeConstructor(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$typeConstructor) {
            Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
            return TypeSystemContext.DefaultImpls.typeConstructor($this, $this$typeConstructor);
        }

        @Nullable
        public static TypeArgumentMarker getArgumentOrNull(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull SimpleTypeMarker $this$getArgumentOrNull, int index) {
            Intrinsics.checkNotNullParameter($this$getArgumentOrNull, "$this$getArgumentOrNull");
            return TypeSystemContext.DefaultImpls.getArgumentOrNull($this, $this$getArgumentOrNull, index);
        }

        @NotNull
        public static SimpleTypeMarker lowerBoundIfFlexible(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$lowerBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
            return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible($this, $this$lowerBoundIfFlexible);
        }

        @NotNull
        public static SimpleTypeMarker upperBoundIfFlexible(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$upperBoundIfFlexible) {
            Intrinsics.checkNotNullParameter($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
            return TypeSystemContext.DefaultImpls.upperBoundIfFlexible($this, $this$upperBoundIfFlexible);
        }

        public static boolean isDynamic(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$isDynamic) {
            Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
            return TypeSystemContext.DefaultImpls.isDynamic($this, $this$isDynamic);
        }

        public static boolean isDefinitelyNotNullType(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$isDefinitelyNotNullType) {
            Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
            return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType($this, $this$isDefinitelyNotNullType);
        }

        public static boolean hasFlexibleNullability(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$hasFlexibleNullability) {
            Intrinsics.checkNotNullParameter($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
            return TypeSystemContext.DefaultImpls.hasFlexibleNullability($this, $this$hasFlexibleNullability);
        }

        public static boolean isNothing(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull KotlinTypeMarker $this$isNothing) {
            Intrinsics.checkNotNullParameter($this$isNothing, "$this$isNothing");
            return TypeSystemContext.DefaultImpls.isNothing($this, $this$isNothing);
        }

        public static boolean isClassType(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull SimpleTypeMarker $this$isClassType) {
            Intrinsics.checkNotNullParameter($this$isClassType, "$this$isClassType");
            return TypeSystemContext.DefaultImpls.isClassType($this, $this$isClassType);
        }

        @Nullable
        public static List<SimpleTypeMarker> fastCorrespondingSupertypes(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull SimpleTypeMarker $this$fastCorrespondingSupertypes, @NotNull TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes($this, $this$fastCorrespondingSupertypes, constructor);
        }

        public static boolean isIntegerLiteralType(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull SimpleTypeMarker $this$isIntegerLiteralType) {
            Intrinsics.checkNotNullParameter($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
            return TypeSystemContext.DefaultImpls.isIntegerLiteralType($this, $this$isIntegerLiteralType);
        }

        @NotNull
        public static TypeArgumentMarker get(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull TypeArgumentListMarker $this$get, int index) {
            Intrinsics.checkNotNullParameter($this$get, "$this$get");
            return TypeSystemContext.DefaultImpls.get($this, $this$get, index);
        }

        public static int size(@NotNull TypeSystemInferenceExtensionContext $this, @NotNull TypeArgumentListMarker $this$size) {
            Intrinsics.checkNotNullParameter($this$size, "$this$size");
            return TypeSystemContext.DefaultImpls.size($this, $this$size);
        }
    }
}

