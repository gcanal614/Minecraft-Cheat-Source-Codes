/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CapturedTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeParameterUtilsKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<TypeParameterDescriptor> computeConstructorTypeParameters(@NotNull ClassifierDescriptorWithTypeParameters $this$computeConstructorTypeParameters) {
        void $this$mapTo$iv$iv;
        Sequence<DeclarationDescriptor> containingClassTypeConstructorParameters;
        Object object;
        DeclarationDescriptor declarationDescriptor;
        Object element$iv;
        boolean $i$f$firstIsInstanceOrNull;
        Sequence<DeclarationDescriptor> $this$firstIsInstanceOrNull$iv;
        List parametersFromContainingFunctions2;
        List<TypeParameterDescriptor> declaredParameters2;
        block5: {
            Intrinsics.checkNotNullParameter($this$computeConstructorTypeParameters, "$this$computeConstructorTypeParameters");
            List<TypeParameterDescriptor> list = $this$computeConstructorTypeParameters.getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list, "declaredTypeParameters");
            declaredParameters2 = list;
            if (!$this$computeConstructorTypeParameters.isInner() && !($this$computeConstructorTypeParameters.getContainingDeclaration() instanceof CallableDescriptor)) {
                return declaredParameters2;
            }
            parametersFromContainingFunctions2 = SequencesKt.toList(SequencesKt.flatMap(SequencesKt.filter(SequencesKt.takeWhile(DescriptorUtilsKt.getParents($this$computeConstructorTypeParameters), computeConstructorTypeParameters.parametersFromContainingFunctions.1.INSTANCE), computeConstructorTypeParameters.parametersFromContainingFunctions.2.INSTANCE), computeConstructorTypeParameters.parametersFromContainingFunctions.3.INSTANCE));
            $this$firstIsInstanceOrNull$iv = DescriptorUtilsKt.getParents($this$computeConstructorTypeParameters);
            $i$f$firstIsInstanceOrNull = false;
            Iterator<DeclarationDescriptor> iterator2 = $this$firstIsInstanceOrNull$iv.iterator();
            while (iterator2.hasNext()) {
                element$iv = iterator2.next();
                if (!(element$iv instanceof ClassDescriptor)) continue;
                declarationDescriptor = element$iv;
                break block5;
            }
            declarationDescriptor = null;
        }
        $this$firstIsInstanceOrNull$iv = (object = (ClassDescriptor)declarationDescriptor) != null && (object = object.getTypeConstructor()) != null ? object.getParameters() : null;
        $i$f$firstIsInstanceOrNull = false;
        Sequence<DeclarationDescriptor> sequence = $this$firstIsInstanceOrNull$iv;
        if (sequence == null) {
            sequence = containingClassTypeConstructorParameters = CollectionsKt.emptyList();
        }
        if (parametersFromContainingFunctions2.isEmpty() && containingClassTypeConstructorParameters.isEmpty()) {
            List<TypeParameterDescriptor> list = $this$computeConstructorTypeParameters.getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list, "declaredTypeParameters");
            return list;
        }
        Iterable $this$map$iv = CollectionsKt.plus((Collection)parametersFromContainingFunctions2, (Iterable)((Object)containingClassTypeConstructorParameters));
        boolean $i$f$map = false;
        element$iv = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v5 = it;
            Intrinsics.checkNotNullExpressionValue(v5, "it");
            CapturedTypeParameterDescriptor capturedTypeParameterDescriptor = TypeParameterUtilsKt.capturedCopyForInnerDeclaration((TypeParameterDescriptor)v5, $this$computeConstructorTypeParameters, declaredParameters2.size());
            collection.add(capturedTypeParameterDescriptor);
        }
        List additional = (List)destination$iv$iv;
        return CollectionsKt.plus((Collection)declaredParameters2, (Iterable)additional);
    }

    private static final CapturedTypeParameterDescriptor capturedCopyForInnerDeclaration(TypeParameterDescriptor $this$capturedCopyForInnerDeclaration, DeclarationDescriptor declarationDescriptor, int declaredTypeParametersCount) {
        return new CapturedTypeParameterDescriptor($this$capturedCopyForInnerDeclaration, declarationDescriptor, declaredTypeParametersCount);
    }

    @Nullable
    public static final PossiblyInnerType buildPossiblyInnerType(@NotNull KotlinType $this$buildPossiblyInnerType) {
        Intrinsics.checkNotNullParameter($this$buildPossiblyInnerType, "$this$buildPossiblyInnerType");
        ClassifierDescriptor classifierDescriptor = $this$buildPossiblyInnerType.getConstructor().getDeclarationDescriptor();
        if (!(classifierDescriptor instanceof ClassifierDescriptorWithTypeParameters)) {
            classifierDescriptor = null;
        }
        return TypeParameterUtilsKt.buildPossiblyInnerType($this$buildPossiblyInnerType, (ClassifierDescriptorWithTypeParameters)classifierDescriptor, 0);
    }

    private static final PossiblyInnerType buildPossiblyInnerType(KotlinType $this$buildPossiblyInnerType, ClassifierDescriptorWithTypeParameters classifierDescriptor, int index) {
        if (classifierDescriptor == null || ErrorUtils.isError(classifierDescriptor)) {
            return null;
        }
        int toIndex = classifierDescriptor.getDeclaredTypeParameters().size() + index;
        if (!classifierDescriptor.isInner()) {
            boolean bl = toIndex == $this$buildPossiblyInnerType.getArguments().size() || DescriptorUtils.isLocal(classifierDescriptor);
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean bl3 = false;
                String string = $this$buildPossiblyInnerType.getArguments().size() - toIndex + " trailing arguments were found in " + $this$buildPossiblyInnerType + " type";
                throw (Throwable)((Object)new AssertionError((Object)string));
            }
            return new PossiblyInnerType(classifierDescriptor, $this$buildPossiblyInnerType.getArguments().subList(index, $this$buildPossiblyInnerType.getArguments().size()), null);
        }
        List<TypeProjection> argumentsSubList = $this$buildPossiblyInnerType.getArguments().subList(index, toIndex);
        DeclarationDescriptor declarationDescriptor = classifierDescriptor.getContainingDeclaration();
        if (!(declarationDescriptor instanceof ClassifierDescriptorWithTypeParameters)) {
            declarationDescriptor = null;
        }
        return new PossiblyInnerType(classifierDescriptor, argumentsSubList, TypeParameterUtilsKt.buildPossiblyInnerType($this$buildPossiblyInnerType, (ClassifierDescriptorWithTypeParameters)declarationDescriptor, toIndex));
    }
}

