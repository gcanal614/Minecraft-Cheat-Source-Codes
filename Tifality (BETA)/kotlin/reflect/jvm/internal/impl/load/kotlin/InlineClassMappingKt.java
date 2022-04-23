/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.HashSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InlineClassMappingKt {
    @Nullable
    public static final KotlinTypeMarker computeExpandedTypeForInlineClass(@NotNull TypeSystemCommonBackendContext $this$computeExpandedTypeForInlineClass, @NotNull KotlinTypeMarker inlineClassType) {
        Intrinsics.checkNotNullParameter($this$computeExpandedTypeForInlineClass, "$this$computeExpandedTypeForInlineClass");
        Intrinsics.checkNotNullParameter(inlineClassType, "inlineClassType");
        boolean bl = false;
        return InlineClassMappingKt.computeExpandedTypeInner($this$computeExpandedTypeForInlineClass, inlineClassType, new HashSet<TypeConstructorMarker>());
    }

    private static final KotlinTypeMarker computeExpandedTypeInner(TypeSystemCommonBackendContext $this$computeExpandedTypeInner, KotlinTypeMarker kotlinType, HashSet<TypeConstructorMarker> visitedClassifiers) {
        KotlinTypeMarker kotlinTypeMarker;
        TypeConstructorMarker classifier2 = $this$computeExpandedTypeInner.typeConstructor(kotlinType);
        if (!visitedClassifiers.add(classifier2)) {
            return null;
        }
        TypeParameterMarker typeParameter = $this$computeExpandedTypeInner.getTypeParameterClassifier(classifier2);
        if (typeParameter != null) {
            KotlinTypeMarker kotlinTypeMarker2 = InlineClassMappingKt.computeExpandedTypeInner($this$computeExpandedTypeInner, $this$computeExpandedTypeInner.getRepresentativeUpperBound(typeParameter), visitedClassifiers);
            if (kotlinTypeMarker2 != null) {
                KotlinTypeMarker kotlinTypeMarker3 = kotlinTypeMarker2;
                boolean bl = false;
                boolean bl2 = false;
                KotlinTypeMarker expandedUpperBound = kotlinTypeMarker3;
                boolean bl3 = false;
                kotlinTypeMarker = $this$computeExpandedTypeInner.isNullableType(expandedUpperBound) || !$this$computeExpandedTypeInner.isMarkedNullable(kotlinType) ? expandedUpperBound : $this$computeExpandedTypeInner.makeNullable(expandedUpperBound);
            } else {
                kotlinTypeMarker = null;
            }
        } else if ($this$computeExpandedTypeInner.isInlineClass(classifier2)) {
            KotlinTypeMarker kotlinTypeMarker4 = $this$computeExpandedTypeInner.getSubstitutedUnderlyingType(kotlinType);
            if (kotlinTypeMarker4 == null) {
                return null;
            }
            KotlinTypeMarker underlyingType = kotlinTypeMarker4;
            KotlinTypeMarker kotlinTypeMarker5 = InlineClassMappingKt.computeExpandedTypeInner($this$computeExpandedTypeInner, underlyingType, visitedClassifiers);
            if (kotlinTypeMarker5 == null) {
                return null;
            }
            KotlinTypeMarker expandedUnderlyingType = kotlinTypeMarker5;
            kotlinTypeMarker = !$this$computeExpandedTypeInner.isNullableType(kotlinType) ? expandedUnderlyingType : ($this$computeExpandedTypeInner.isNullableType(expandedUnderlyingType) ? kotlinType : (expandedUnderlyingType instanceof SimpleTypeMarker && $this$computeExpandedTypeInner.isPrimitiveType((SimpleTypeMarker)expandedUnderlyingType) ? kotlinType : $this$computeExpandedTypeInner.makeNullable(expandedUnderlyingType)));
        } else {
            kotlinTypeMarker = kotlinType;
        }
        return kotlinTypeMarker;
    }
}

