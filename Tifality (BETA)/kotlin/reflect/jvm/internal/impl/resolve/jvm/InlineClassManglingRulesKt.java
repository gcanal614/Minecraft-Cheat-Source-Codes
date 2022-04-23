/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class InlineClassManglingRulesKt {
    public static final boolean shouldHideConstructorDueToInlineClassTypeValueParameters(@NotNull CallableMemberDescriptor descriptor2) {
        boolean bl;
        block8: {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            CallableMemberDescriptor callableMemberDescriptor = descriptor2;
            if (!(callableMemberDescriptor instanceof ClassConstructorDescriptor)) {
                callableMemberDescriptor = null;
            }
            ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor)callableMemberDescriptor;
            if (classConstructorDescriptor == null) {
                return false;
            }
            ClassConstructorDescriptor constructorDescriptor = classConstructorDescriptor;
            if (Visibilities.isPrivate(constructorDescriptor.getVisibility())) {
                return false;
            }
            ClassDescriptor classDescriptor = constructorDescriptor.getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(classDescriptor, "constructorDescriptor.constructedClass");
            if (classDescriptor.isInline()) {
                return false;
            }
            if (DescriptorUtils.isSealedClass(constructorDescriptor.getConstructedClass())) {
                return false;
            }
            List<ValueParameterDescriptor> list = constructorDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "constructorDescriptor.valueParameters");
            Iterable $this$any$iv = list;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    ValueParameterDescriptor it = (ValueParameterDescriptor)element$iv;
                    boolean bl2 = false;
                    ValueParameterDescriptor valueParameterDescriptor = it;
                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "it");
                    KotlinType kotlinType = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
                    if (!InlineClassManglingRulesKt.requiresFunctionNameManglingInParameterTypes(kotlinType)) continue;
                    bl = true;
                    break block8;
                }
                bl = false;
            }
        }
        return bl;
    }

    public static final boolean isInlineClassThatRequiresMangling(@NotNull DeclarationDescriptor $this$isInlineClassThatRequiresMangling) {
        Intrinsics.checkNotNullParameter($this$isInlineClassThatRequiresMangling, "$this$isInlineClassThatRequiresMangling");
        return InlineClassesUtilsKt.isInlineClass($this$isInlineClassThatRequiresMangling) && !InlineClassManglingRulesKt.isDontMangleClass((ClassDescriptor)$this$isInlineClassThatRequiresMangling);
    }

    public static final boolean isInlineClassThatRequiresMangling(@NotNull KotlinType $this$isInlineClassThatRequiresMangling) {
        Intrinsics.checkNotNullParameter($this$isInlineClassThatRequiresMangling, "$this$isInlineClassThatRequiresMangling");
        ClassifierDescriptor classifierDescriptor = $this$isInlineClassThatRequiresMangling.getConstructor().getDeclarationDescriptor();
        return classifierDescriptor != null && InlineClassManglingRulesKt.isInlineClassThatRequiresMangling(classifierDescriptor);
    }

    private static final boolean requiresFunctionNameManglingInParameterTypes(KotlinType $this$requiresFunctionNameManglingInParameterTypes) {
        return InlineClassManglingRulesKt.isInlineClassThatRequiresMangling($this$requiresFunctionNameManglingInParameterTypes) || InlineClassManglingRulesKt.isTypeParameterWithUpperBoundThatRequiresMangling($this$requiresFunctionNameManglingInParameterTypes);
    }

    private static final boolean isDontMangleClass(ClassDescriptor classDescriptor) {
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), DescriptorUtils.RESULT_FQ_NAME);
    }

    private static final boolean isTypeParameterWithUpperBoundThatRequiresMangling(KotlinType $this$isTypeParameterWithUpperBoundThatRequiresMangling) {
        ClassifierDescriptor classifierDescriptor = $this$isTypeParameterWithUpperBoundThatRequiresMangling.getConstructor().getDeclarationDescriptor();
        if (!(classifierDescriptor instanceof TypeParameterDescriptor)) {
            classifierDescriptor = null;
        }
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)classifierDescriptor;
        if (typeParameterDescriptor == null) {
            return false;
        }
        TypeParameterDescriptor descriptor2 = typeParameterDescriptor;
        return InlineClassManglingRulesKt.requiresFunctionNameManglingInParameterTypes(TypeUtilsKt.getRepresentativeUpperBound(descriptor2));
    }
}

