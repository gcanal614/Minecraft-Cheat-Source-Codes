/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector;
import org.jetbrains.annotations.NotNull;

public final class IntersectionTypeKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final UnwrappedType intersectTypes(@NotNull List<? extends UnwrappedType> types) {
        void $this$mapTo$iv$iv;
        SimpleType simpleType2;
        Collection collection;
        void $this$mapTo$iv$iv2;
        Intrinsics.checkNotNullParameter(types, "types");
        switch (types.size()) {
            case 0: {
                String string = "Expected some types";
                boolean bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            case 1: {
                return CollectionsKt.single(types);
            }
        }
        boolean hasFlexibleTypes = false;
        boolean hasErrorType = false;
        Iterable $this$map$iv = types;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            SimpleType simpleType3;
            void it;
            UnwrappedType unwrappedType = (UnwrappedType)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            hasErrorType = hasErrorType || KotlinTypeKt.isError((KotlinType)it);
            void var13_19 = it;
            if (var13_19 instanceof SimpleType) {
                simpleType3 = (SimpleType)it;
            } else if (var13_19 instanceof FlexibleType) {
                if (DynamicTypesKt.isDynamic((KotlinType)it)) {
                    return it;
                }
                hasFlexibleTypes = true;
                simpleType3 = ((FlexibleType)it).getLowerBound();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            simpleType2 = simpleType3;
            collection.add(simpleType2);
        }
        List lowerBounds = (List)destination$iv$iv;
        if (hasErrorType) {
            SimpleType simpleType4 = ErrorUtils.createErrorType("Intersection of error types: " + types);
            Intrinsics.checkNotNullExpressionValue(simpleType4, "ErrorUtils.createErrorTy\u2026 of error types: $types\")");
            return simpleType4;
        }
        if (!hasFlexibleTypes) {
            return TypeIntersector.INSTANCE.intersectTypes$descriptors(lowerBounds);
        }
        Iterable $this$map$iv2 = types;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            UnwrappedType bl = (UnwrappedType)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl2 = false;
            simpleType2 = FlexibleTypesKt.upperIfFlexible((KotlinType)it);
            collection.add(simpleType2);
        }
        List upperBounds2 = (List)destination$iv$iv2;
        return KotlinTypeFactory.flexibleType(TypeIntersector.INSTANCE.intersectTypes$descriptors(lowerBounds), TypeIntersector.INSTANCE.intersectTypes$descriptors(upperBounds2));
    }
}

