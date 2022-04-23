/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InlineClassesUtilsKt {
    @Nullable
    public static final ValueParameterDescriptor underlyingRepresentation(@NotNull ClassDescriptor $this$underlyingRepresentation) {
        Intrinsics.checkNotNullParameter($this$underlyingRepresentation, "$this$underlyingRepresentation");
        if (!$this$underlyingRepresentation.isInline()) {
            return null;
        }
        Object object = $this$underlyingRepresentation.getUnsubstitutedPrimaryConstructor();
        return object != null && (object = object.getValueParameters()) != null ? (ValueParameterDescriptor)CollectionsKt.singleOrNull(object) : null;
    }

    public static final boolean isInlineClass(@NotNull DeclarationDescriptor $this$isInlineClass) {
        Intrinsics.checkNotNullParameter($this$isInlineClass, "$this$isInlineClass");
        return $this$isInlineClass instanceof ClassDescriptor && ((ClassDescriptor)$this$isInlineClass).isInline();
    }

    @Nullable
    public static final ValueParameterDescriptor unsubstitutedUnderlyingParameter(@NotNull KotlinType $this$unsubstitutedUnderlyingParameter) {
        Intrinsics.checkNotNullParameter($this$unsubstitutedUnderlyingParameter, "$this$unsubstitutedUnderlyingParameter");
        ClassifierDescriptor $this$safeAs$iv = $this$unsubstitutedUnderlyingParameter.getConstructor().getDeclarationDescriptor();
        boolean $i$f$safeAs = false;
        ClassifierDescriptor classifierDescriptor = $this$safeAs$iv;
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor;
        return classDescriptor != null ? InlineClassesUtilsKt.underlyingRepresentation(classDescriptor) : null;
    }

    public static final boolean isInlineClassType(@NotNull KotlinType $this$isInlineClassType) {
        Intrinsics.checkNotNullParameter($this$isInlineClassType, "$this$isInlineClassType");
        ClassifierDescriptor classifierDescriptor = $this$isInlineClassType.getConstructor().getDeclarationDescriptor();
        return classifierDescriptor != null ? InlineClassesUtilsKt.isInlineClass(classifierDescriptor) : false;
    }

    @Nullable
    public static final KotlinType substitutedUnderlyingType(@NotNull KotlinType $this$substitutedUnderlyingType) {
        Intrinsics.checkNotNullParameter($this$substitutedUnderlyingType, "$this$substitutedUnderlyingType");
        ValueParameterDescriptor valueParameterDescriptor = InlineClassesUtilsKt.unsubstitutedUnderlyingParameter($this$substitutedUnderlyingType);
        if (valueParameterDescriptor == null) {
            return null;
        }
        ValueParameterDescriptor parameter = valueParameterDescriptor;
        MemberScope memberScope2 = $this$substitutedUnderlyingType.getMemberScope();
        Name name = parameter.getName();
        Intrinsics.checkNotNullExpressionValue(name, "parameter.name");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor)CollectionsKt.singleOrNull((Iterable)memberScope2.getContributedVariables(name, NoLookupLocation.FOR_ALREADY_TRACKED));
        return propertyDescriptor != null ? propertyDescriptor.getType() : null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean isGetterOfUnderlyingPropertyOfInlineClass(@NotNull CallableDescriptor $this$isGetterOfUnderlyingPropertyOfInlineClass) {
        Intrinsics.checkNotNullParameter($this$isGetterOfUnderlyingPropertyOfInlineClass, "$this$isGetterOfUnderlyingPropertyOfInlineClass");
        if (!($this$isGetterOfUnderlyingPropertyOfInlineClass instanceof PropertyGetterDescriptor)) return false;
        PropertyDescriptor propertyDescriptor = ((PropertyGetterDescriptor)$this$isGetterOfUnderlyingPropertyOfInlineClass).getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "correspondingProperty");
        if (!InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(propertyDescriptor)) return false;
        return true;
    }

    public static final boolean isUnderlyingPropertyOfInlineClass(@NotNull VariableDescriptor $this$isUnderlyingPropertyOfInlineClass) {
        Intrinsics.checkNotNullParameter($this$isUnderlyingPropertyOfInlineClass, "$this$isUnderlyingPropertyOfInlineClass");
        DeclarationDescriptor declarationDescriptor = $this$isUnderlyingPropertyOfInlineClass.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "this.containingDeclaration");
        DeclarationDescriptor containingDeclaration = declarationDescriptor;
        if (!InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
            return false;
        }
        DeclarationDescriptor declarationDescriptor2 = containingDeclaration;
        if (declarationDescriptor2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ValueParameterDescriptor valueParameterDescriptor = InlineClassesUtilsKt.underlyingRepresentation((ClassDescriptor)declarationDescriptor2);
        return Intrinsics.areEqual(valueParameterDescriptor != null ? valueParameterDescriptor.getName() : null, $this$isUnderlyingPropertyOfInlineClass.getName());
    }
}

