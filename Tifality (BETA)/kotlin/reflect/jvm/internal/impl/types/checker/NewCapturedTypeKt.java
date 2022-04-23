/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NewCapturedTypeKt {
    @Nullable
    public static final SimpleType captureFromArguments(@NotNull SimpleType type2, @NotNull CaptureStatus status) {
        SimpleType simpleType2;
        Intrinsics.checkNotNullParameter(type2, "type");
        Intrinsics.checkNotNullParameter((Object)status, "status");
        List<TypeProjection> list = NewCapturedTypeKt.captureArguments(type2, status);
        if (list != null) {
            List<TypeProjection> list2 = list;
            boolean bl = false;
            boolean bl2 = false;
            List<TypeProjection> it = list2;
            boolean bl3 = false;
            simpleType2 = NewCapturedTypeKt.replaceArguments(type2, it);
        } else {
            simpleType2 = null;
        }
        return simpleType2;
    }

    private static final SimpleType replaceArguments(UnwrappedType $this$replaceArguments, List<? extends TypeProjection> arguments2) {
        return KotlinTypeFactory.simpleType$default($this$replaceArguments.getAnnotations(), $this$replaceArguments.getConstructor(), arguments2, $this$replaceArguments.isMarkedNullable(), null, 16, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final List<TypeProjection> captureArguments(UnwrappedType type2, CaptureStatus status) {
        UnwrappedType unwrappedType;
        KotlinType lowerType;
        Collection collection;
        Object object;
        void $this$mapTo$iv$iv;
        Object element$iv2;
        boolean bl;
        List<TypeProjection> arguments2;
        block13: {
            if (type2.getArguments().size() != type2.getConstructor().getParameters().size()) {
                return null;
            }
            arguments2 = type2.getArguments();
            Iterable $this$all$iv = arguments2;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv2 : $this$all$iv) {
                    TypeProjection it = (TypeProjection)element$iv2;
                    boolean bl2 = false;
                    if (it.getProjectionKind() == Variance.INVARIANT) continue;
                    bl = false;
                    break block13;
                }
                bl = true;
            }
        }
        if (bl) {
            return null;
        }
        Iterable iterable = arguments2;
        List<TypeParameterDescriptor> list = type2.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "type.constructor.parameters");
        Iterable $this$map$iv = CollectionsKt.zip(iterable, (Iterable)list);
        int $i$f$map = 0;
        element$iv2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            TypeProjection typeProjection;
            void projection;
            void $dstr$projection$parameter;
            object = (Pair)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl3 = false;
            TypeProjection typeProjection2 = (TypeProjection)$dstr$projection$parameter.component1();
            TypeParameterDescriptor parameter = (TypeParameterDescriptor)$dstr$projection$parameter.component2();
            if (projection.getProjectionKind() == Variance.INVARIANT) {
                typeProjection = projection;
            } else {
                lowerType = !projection.isStarProjection() && projection.getProjectionKind() == Variance.IN_VARIANCE ? projection.getType().unwrap() : null;
                TypeParameterDescriptor typeParameterDescriptor = parameter;
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "parameter");
                typeProjection = TypeUtilsKt.asTypeProjection(new NewCapturedType(status, (UnwrappedType)lowerType, (TypeProjection)projection, typeParameterDescriptor));
            }
            unwrappedType = typeProjection;
            collection.add(unwrappedType);
        }
        List capturedArguments = (List)destination$iv$iv;
        TypeSubstitutor substitutor = TypeConstructorSubstitution.Companion.create(type2.getConstructor(), capturedArguments).buildSubstitutor();
        $i$f$map = 0;
        int n = ((Collection)arguments2).size();
        while ($i$f$map < n) {
            void index;
            TypeProjection oldProjection = arguments2.get((int)index);
            TypeProjection newProjection = (TypeProjection)capturedArguments.get((int)index);
            if (oldProjection.getProjectionKind() != Variance.INVARIANT) {
                Collection $this$mapTo$iv;
                Object item$iv$iv;
                TypeParameterDescriptor typeParameterDescriptor = type2.getConstructor().getParameters().get((int)index);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "type.constructor.parameters[index]");
                List<KotlinType> list2 = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(list2, "type.constructor.parameters[index].upperBounds");
                item$iv$iv = list2;
                boolean $dstr$projection$parameter = false;
                Collection destination$iv = new ArrayList();
                boolean $i$f$mapTo2 = false;
                for (Object item$iv : $this$mapTo$iv) {
                    void it;
                    lowerType = (KotlinType)item$iv;
                    collection = destination$iv;
                    boolean bl4 = false;
                    unwrappedType = NewKotlinTypeChecker.Companion.getDefault().transformToNewType(substitutor.safeSubstitute((KotlinType)it, Variance.INVARIANT).unwrap());
                    collection.add(unwrappedType);
                }
                List capturedTypeSupertypes = (List)destination$iv;
                if (!oldProjection.isStarProjection() && oldProjection.getProjectionKind() == Variance.OUT_VARIANCE) {
                    $this$mapTo$iv = capturedTypeSupertypes;
                    object = NewKotlinTypeChecker.Companion.getDefault().transformToNewType(oldProjection.getType().unwrap());
                    boolean bl5 = false;
                    $this$mapTo$iv.add(object);
                }
                KotlinType kotlinType = newProjection.getType();
                if (kotlinType == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
                }
                NewCapturedType capturedType = (NewCapturedType)kotlinType;
                capturedType.getConstructor().initializeSupertypes(capturedTypeSupertypes);
            }
            ++index;
        }
        return capturedArguments;
    }
}

