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
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpecialTypesKt {
    @Nullable
    public static final AbbreviatedType getAbbreviatedType(@NotNull KotlinType $this$getAbbreviatedType) {
        Intrinsics.checkNotNullParameter($this$getAbbreviatedType, "$this$getAbbreviatedType");
        UnwrappedType unwrappedType = $this$getAbbreviatedType.unwrap();
        if (!(unwrappedType instanceof AbbreviatedType)) {
            unwrappedType = null;
        }
        return (AbbreviatedType)unwrappedType;
    }

    @Nullable
    public static final SimpleType getAbbreviation(@NotNull KotlinType $this$getAbbreviation) {
        Intrinsics.checkNotNullParameter($this$getAbbreviation, "$this$getAbbreviation");
        AbbreviatedType abbreviatedType = SpecialTypesKt.getAbbreviatedType($this$getAbbreviation);
        return abbreviatedType != null ? abbreviatedType.getAbbreviation() : null;
    }

    @NotNull
    public static final SimpleType withAbbreviation(@NotNull SimpleType $this$withAbbreviation, @NotNull SimpleType abbreviatedType) {
        Intrinsics.checkNotNullParameter($this$withAbbreviation, "$this$withAbbreviation");
        Intrinsics.checkNotNullParameter(abbreviatedType, "abbreviatedType");
        if (KotlinTypeKt.isError($this$withAbbreviation)) {
            return $this$withAbbreviation;
        }
        return new AbbreviatedType($this$withAbbreviation, abbreviatedType);
    }

    public static final boolean isDefinitelyNotNullType(@NotNull KotlinType $this$isDefinitelyNotNullType) {
        Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return $this$isDefinitelyNotNullType.unwrap() instanceof DefinitelyNotNullType;
    }

    @NotNull
    public static final SimpleType makeSimpleTypeDefinitelyNotNullOrNotNull(@NotNull SimpleType $this$makeSimpleTypeDefinitelyNotNullOrNotNull) {
        Intrinsics.checkNotNullParameter($this$makeSimpleTypeDefinitelyNotNullOrNotNull, "$this$makeSimpleTypeDefinitelyNotNullOrNotNull");
        DefinitelyNotNullType definitelyNotNullType = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors($this$makeSimpleTypeDefinitelyNotNullOrNotNull);
        SimpleType simpleType2 = definitelyNotNullType != null ? (SimpleType)definitelyNotNullType : SpecialTypesKt.makeIntersectionTypeDefinitelyNotNullOrNotNull($this$makeSimpleTypeDefinitelyNotNullOrNotNull);
        if (simpleType2 == null) {
            simpleType2 = $this$makeSimpleTypeDefinitelyNotNullOrNotNull.makeNullableAsSpecified(false);
        }
        return simpleType2;
    }

    @NotNull
    public static final NewCapturedType withNotNullProjection(@NotNull NewCapturedType $this$withNotNullProjection) {
        Intrinsics.checkNotNullParameter($this$withNotNullProjection, "$this$withNotNullProjection");
        return new NewCapturedType($this$withNotNullProjection.getCaptureStatus(), $this$withNotNullProjection.getConstructor(), $this$withNotNullProjection.getLowerType(), $this$withNotNullProjection.getAnnotations(), $this$withNotNullProjection.isMarkedNullable(), true);
    }

    @NotNull
    public static final UnwrappedType makeDefinitelyNotNullOrNotNull(@NotNull UnwrappedType $this$makeDefinitelyNotNullOrNotNull) {
        Intrinsics.checkNotNullParameter($this$makeDefinitelyNotNullOrNotNull, "$this$makeDefinitelyNotNullOrNotNull");
        DefinitelyNotNullType definitelyNotNullType = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors($this$makeDefinitelyNotNullOrNotNull);
        SimpleType simpleType2 = definitelyNotNullType != null ? (SimpleType)definitelyNotNullType : SpecialTypesKt.makeIntersectionTypeDefinitelyNotNullOrNotNull($this$makeDefinitelyNotNullOrNotNull);
        return simpleType2 != null ? (UnwrappedType)simpleType2 : $this$makeDefinitelyNotNullOrNotNull.makeNullableAsSpecified(false);
    }

    private static final SimpleType makeIntersectionTypeDefinitelyNotNullOrNotNull(KotlinType $this$makeIntersectionTypeDefinitelyNotNullOrNotNull) {
        TypeConstructor typeConstructor2 = $this$makeIntersectionTypeDefinitelyNotNullOrNotNull.getConstructor();
        if (!(typeConstructor2 instanceof IntersectionTypeConstructor)) {
            typeConstructor2 = null;
        }
        IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor)typeConstructor2;
        if (intersectionTypeConstructor == null) {
            return null;
        }
        IntersectionTypeConstructor typeConstructor3 = intersectionTypeConstructor;
        IntersectionTypeConstructor intersectionTypeConstructor2 = SpecialTypesKt.makeDefinitelyNotNullOrNotNull(typeConstructor3);
        if (intersectionTypeConstructor2 == null) {
            return null;
        }
        IntersectionTypeConstructor definitelyNotNullConstructor = intersectionTypeConstructor2;
        return definitelyNotNullConstructor.createType();
    }

    /*
     * WARNING - void declaration
     */
    private static final IntersectionTypeConstructor makeDefinitelyNotNullOrNotNull(IntersectionTypeConstructor $this$makeDefinitelyNotNullOrNotNull) {
        IntersectionTypeConstructor intersectionTypeConstructor;
        void $this$mapTo$iv$iv$iv;
        IntersectionTypeConstructor $this$transformComponents$iv = $this$makeDefinitelyNotNullOrNotNull;
        boolean $i$f$transformComponents = false;
        boolean changed$iv = false;
        Iterable $this$map$iv$iv = $this$transformComponents$iv.getSupertypes();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv$iv : $this$mapTo$iv$iv$iv) {
            KotlinType kotlinType;
            void it$iv;
            KotlinType kotlinType2 = (KotlinType)item$iv$iv$iv;
            Collection collection = destination$iv$iv$iv;
            boolean bl = false;
            void it = it$iv;
            boolean bl2 = false;
            if (TypeUtils.isNullableType((KotlinType)it)) {
                changed$iv = true;
                it = it$iv;
                boolean bl3 = false;
                kotlinType = SpecialTypesKt.makeDefinitelyNotNullOrNotNull(it.unwrap());
            } else {
                kotlinType = it$iv;
            }
            void var14_19 = kotlinType;
            collection.add(var14_19);
        }
        List newSupertypes$iv = (List)destination$iv$iv$iv;
        if (!changed$iv) {
            intersectionTypeConstructor = null;
        } else {
            KotlinType kotlinType;
            KotlinType kotlinType3 = $this$transformComponents$iv.getAlternativeType();
            if (kotlinType3 != null) {
                KotlinType kotlinType4 = kotlinType3;
                boolean bl = false;
                boolean bl4 = false;
                KotlinType alternative$iv = kotlinType4;
                boolean bl5 = false;
                KotlinType it = alternative$iv;
                boolean bl6 = false;
                if (TypeUtils.isNullableType(it)) {
                    it = alternative$iv;
                    boolean bl7 = false;
                    kotlinType = SpecialTypesKt.makeDefinitelyNotNullOrNotNull(it.unwrap());
                } else {
                    kotlinType = alternative$iv;
                }
            } else {
                kotlinType = null;
            }
            KotlinType updatedAlternative$iv = kotlinType;
            intersectionTypeConstructor = new IntersectionTypeConstructor(newSupertypes$iv).setAlternative(updatedAlternative$iv);
        }
        return intersectionTypeConstructor;
    }
}

