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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class NewKotlinTypeCheckerImpl
implements NewKotlinTypeChecker {
    @NotNull
    private final OverridingUtil overridingUtil;
    @NotNull
    private final KotlinTypeRefiner kotlinTypeRefiner;

    @Override
    @NotNull
    public OverridingUtil getOverridingUtil() {
        return this.overridingUtil;
    }

    @Override
    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        return this.isSubtypeOf(new ClassicTypeCheckerContext(true, false, false, this.getKotlinTypeRefiner(), 6, null), subtype.unwrap(), supertype.unwrap());
    }

    @Override
    public boolean equalTypes(@NotNull KotlinType a2, @NotNull KotlinType b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return this.equalTypes(new ClassicTypeCheckerContext(false, false, false, this.getKotlinTypeRefiner(), 6, null), a2.unwrap(), b2.unwrap());
    }

    public final boolean equalTypes(@NotNull ClassicTypeCheckerContext $this$equalTypes, @NotNull UnwrappedType a2, @NotNull UnwrappedType b2) {
        Intrinsics.checkNotNullParameter($this$equalTypes, "$this$equalTypes");
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return AbstractTypeChecker.INSTANCE.equalTypes($this$equalTypes, a2, b2);
    }

    public final boolean isSubtypeOf(@NotNull ClassicTypeCheckerContext $this$isSubtypeOf, @NotNull UnwrappedType subType, @NotNull UnwrappedType superType) {
        Intrinsics.checkNotNullParameter($this$isSubtypeOf, "$this$isSubtypeOf");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, $this$isSubtypeOf, subType, superType, false, 8, null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final SimpleType transformToNewType(@NotNull SimpleType type2) {
        TypeConstructor constructor;
        Intrinsics.checkNotNullParameter(type2, "type");
        TypeConstructor typeConstructor2 = constructor = type2.getConstructor();
        if (typeConstructor2 instanceof CapturedTypeConstructorImpl) {
            UnwrappedType lowerType;
            Object object = ((CapturedTypeConstructorImpl)constructor).getProjection();
            boolean bl = false;
            boolean bl2 = false;
            TypeProjection it = object;
            boolean bl3 = false;
            Object object2 = it.getProjectionKind() == Variance.IN_VARIANCE ? object : null;
            UnwrappedType unwrappedType = object2 != null && (object2 = object2.getType()) != null ? ((KotlinType)object2).unwrap() : (lowerType = null);
            if (((CapturedTypeConstructorImpl)constructor).getNewTypeConstructor() == null) {
                Collection<UnwrappedType> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                object = ((CapturedTypeConstructorImpl)constructor).getSupertypes();
                TypeProjection typeProjection = ((CapturedTypeConstructorImpl)constructor).getProjection();
                CapturedTypeConstructorImpl capturedTypeConstructorImpl = (CapturedTypeConstructorImpl)constructor;
                boolean $i$f$map = false;
                void var7_11 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it2;
                    KotlinType kotlinType = (KotlinType)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl4 = false;
                    UnwrappedType unwrappedType2 = it2.unwrap();
                    collection.add(unwrappedType2);
                }
                collection = (List)destination$iv$iv;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int n = 4;
                NewCapturedTypeConstructor newCapturedTypeConstructor = null;
                List list = collection;
                TypeProjection typeProjection2 = typeProjection;
                capturedTypeConstructorImpl.setNewTypeConstructor(new NewCapturedTypeConstructor(typeProjection2, list, newCapturedTypeConstructor, n, defaultConstructorMarker));
            }
            NewCapturedTypeConstructor newCapturedTypeConstructor = ((CapturedTypeConstructorImpl)constructor).getNewTypeConstructor();
            Intrinsics.checkNotNull(newCapturedTypeConstructor);
            return new NewCapturedType(CaptureStatus.FOR_SUBTYPING, newCapturedTypeConstructor, lowerType, type2.getAnnotations(), type2.isMarkedNullable(), false, 32, null);
        }
        if (typeConstructor2 instanceof IntegerValueTypeConstructor) {
            Collection<KotlinType> collection;
            Iterable $this$map$iv22 = ((IntegerValueTypeConstructor)constructor).getSupertypes();
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv22;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv22, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                KotlinType kotlinType;
                KotlinType it2 = (KotlinType)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue(TypeUtils.makeNullableAsSpecified(it2, type2.isMarkedNullable()), "TypeUtils.makeNullableAs\u2026t, type.isMarkedNullable)");
                collection.add(kotlinType);
            }
            collection = (List)destination$iv$iv;
            Collection collection2 = collection;
            IntersectionTypeConstructor newConstructor = new IntersectionTypeConstructor(collection2);
            boolean $this$map$iv22 = false;
            return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type2.getAnnotations(), newConstructor, CollectionsKt.emptyList(), false, type2.getMemberScope());
        }
        if (typeConstructor2 instanceof IntersectionTypeConstructor && type2.isMarkedNullable()) {
            IntersectionTypeConstructor intersectionTypeConstructor;
            IntersectionTypeConstructor intersectionTypeConstructor2;
            void $this$mapTo$iv$iv$iv;
            IntersectionTypeConstructor $this$transformComponents$iv = (IntersectionTypeConstructor)constructor;
            boolean $i$f$transformComponents = false;
            boolean changed$iv = false;
            Iterable $this$map$iv$iv = $this$transformComponents$iv.getSupertypes();
            boolean $i$f$map = false;
            Iterable item$iv$iv = $this$map$iv$iv;
            Collection destination$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv$iv : $this$mapTo$iv$iv$iv) {
                KotlinType kotlinType;
                void it$iv;
                KotlinType kotlinType2 = (KotlinType)item$iv$iv$iv;
                Collection collection = destination$iv$iv$iv;
                boolean bl = false;
                void it = it$iv;
                if (true) {
                    changed$iv = true;
                    it = it$iv;
                    boolean bl5 = false;
                    kotlinType = TypeUtilsKt.makeNullable((KotlinType)it);
                } else {
                    kotlinType = it$iv;
                }
                void var19_57 = kotlinType;
                collection.add(var19_57);
            }
            List newSupertypes$iv = (List)destination$iv$iv$iv;
            if (!changed$iv) {
                intersectionTypeConstructor2 = null;
            } else {
                KotlinType kotlinType;
                KotlinType kotlinType3 = $this$transformComponents$iv.getAlternativeType();
                if (kotlinType3 != null) {
                    KotlinType kotlinType4 = kotlinType3;
                    boolean bl = false;
                    boolean bl6 = false;
                    KotlinType alternative$iv = kotlinType4;
                    boolean bl7 = false;
                    KotlinType it = alternative$iv;
                    if (true) {
                        it = alternative$iv;
                        boolean bl8 = false;
                        kotlinType = TypeUtilsKt.makeNullable(it);
                    } else {
                        kotlinType = alternative$iv;
                    }
                } else {
                    kotlinType = null;
                }
                KotlinType updatedAlternative$iv = kotlinType;
                intersectionTypeConstructor2 = intersectionTypeConstructor = new IntersectionTypeConstructor(newSupertypes$iv).setAlternative(updatedAlternative$iv);
            }
            if (intersectionTypeConstructor2 == null) {
                intersectionTypeConstructor = (IntersectionTypeConstructor)constructor;
            }
            IntersectionTypeConstructor newConstructor = intersectionTypeConstructor;
            return newConstructor.createType();
        }
        return type2;
    }

    @NotNull
    public UnwrappedType transformToNewType(@NotNull UnwrappedType type2) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter(type2, "type");
        UnwrappedType unwrappedType2 = type2;
        if (unwrappedType2 instanceof SimpleType) {
            unwrappedType = this.transformToNewType((SimpleType)type2);
        } else if (unwrappedType2 instanceof FlexibleType) {
            SimpleType newLower = this.transformToNewType(((FlexibleType)type2).getLowerBound());
            SimpleType newUpper = this.transformToNewType(((FlexibleType)type2).getUpperBound());
            unwrappedType = newLower != ((FlexibleType)type2).getLowerBound() || newUpper != ((FlexibleType)type2).getUpperBound() ? KotlinTypeFactory.flexibleType(newLower, newUpper) : type2;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(unwrappedType, type2);
    }

    @Override
    @NotNull
    public KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    public NewKotlinTypeCheckerImpl(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        OverridingUtil overridingUtil2 = OverridingUtil.createWithTypeRefiner(this.getKotlinTypeRefiner());
        Intrinsics.checkNotNullExpressionValue(overridingUtil2, "OverridingUtil.createWit\u2026efiner(kotlinTypeRefiner)");
        this.overridingUtil = overridingUtil2;
    }
}

