/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithDifferentJvmName;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaIncompatibilityRulesOverridabilityCondition
implements ExternalOverridabilityCondition {
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (this.isIncompatibleInAccordanceWithBuiltInOverridabilityRules(superDescriptor, subDescriptor, subClassDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        if (Companion.doesJavaOverrideHaveIncompatibleValueParameterKinds(superDescriptor, subDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    private final boolean isIncompatibleInAccordanceWithBuiltInOverridabilityRules(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        boolean isOneOfDescriptorsHidden;
        if (!(superDescriptor instanceof CallableMemberDescriptor) || !(subDescriptor instanceof FunctionDescriptor) || KotlinBuiltIns.isBuiltIn(subDescriptor)) {
            return false;
        }
        Name name = ((FunctionDescriptor)subDescriptor).getName();
        Intrinsics.checkNotNullExpressionValue(name, "subDescriptor.name");
        if (!BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            Name name2 = ((FunctionDescriptor)subDescriptor).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "subDescriptor.name");
            if (!BuiltinMethodsWithDifferentJvmName.INSTANCE.getSameAsRenamedInJvmBuiltin(name2)) {
                return false;
            }
        }
        CallableMemberDescriptor overriddenBuiltin = SpecialBuiltinMembers.getOverriddenSpecialBuiltin((CallableMemberDescriptor)superDescriptor);
        boolean bl = ((FunctionDescriptor)subDescriptor).isHiddenToOvercomeSignatureClash();
        CallableDescriptor callableDescriptor = superDescriptor;
        if (!(callableDescriptor instanceof FunctionDescriptor)) {
            callableDescriptor = null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor)callableDescriptor;
        boolean bl2 = functionDescriptor == null || bl != functionDescriptor.isHiddenToOvercomeSignatureClash() ? true : (isOneOfDescriptorsHidden = false);
        if (isOneOfDescriptorsHidden && (overriddenBuiltin == null || !((FunctionDescriptor)subDescriptor).isHiddenToOvercomeSignatureClash())) {
            return true;
        }
        if (!(subClassDescriptor instanceof JavaClassDescriptor) || ((FunctionDescriptor)subDescriptor).getInitialSignatureDescriptor() != null) {
            return false;
        }
        if (overriddenBuiltin == null || SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(subClassDescriptor, overriddenBuiltin)) {
            return false;
        }
        if (overriddenBuiltin instanceof FunctionDescriptor && superDescriptor instanceof FunctionDescriptor && BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor)overriddenBuiltin) != null) {
            String string = MethodSignatureMappingKt.computeJvmDescriptor$default((FunctionDescriptor)subDescriptor, false, false, 2, null);
            FunctionDescriptor functionDescriptor2 = ((FunctionDescriptor)superDescriptor).getOriginal();
            Intrinsics.checkNotNullExpressionValue(functionDescriptor2, "superDescriptor.original");
            if (Intrinsics.areEqual(string, MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor2, false, false, 2, null))) {
                return false;
            }
        }
        return true;
    }

    @Override
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY;
    }

    public static final class Companion {
        /*
         * WARNING - void declaration
         */
        public final boolean doesJavaOverrideHaveIncompatibleValueParameterKinds(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor) {
            Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
            Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
            if (!(subDescriptor instanceof JavaMethodDescriptor) || !(superDescriptor instanceof FunctionDescriptor)) {
                return false;
            }
            boolean bl = ((JavaMethodDescriptor)subDescriptor).getValueParameters().size() == ((FunctionDescriptor)superDescriptor).getValueParameters().size();
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean $i$a$-assert-JavaIncompatibilityRulesOverridabilityCondition$Companion$doesJavaOverrideHaveIncompatibleValueParameterKinds$22 = false;
                String $i$a$-assert-JavaIncompatibilityRulesOverridabilityCondition$Companion$doesJavaOverrideHaveIncompatibleValueParameterKinds$22 = "External overridability condition with CONFLICTS_ONLY should not be run with different value parameters size";
                throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-JavaIncompatibilityRulesOverridabilityCondition$Companion$doesJavaOverrideHaveIncompatibleValueParameterKinds$22));
            }
            SimpleFunctionDescriptor simpleFunctionDescriptor = ((JavaMethodDescriptor)subDescriptor).getOriginal();
            Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptor, "subDescriptor.original");
            List<ValueParameterDescriptor> list = simpleFunctionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "subDescriptor.original.valueParameters");
            Iterable iterable = list;
            FunctionDescriptor functionDescriptor = ((FunctionDescriptor)superDescriptor).getOriginal();
            Intrinsics.checkNotNullExpressionValue(functionDescriptor, "superDescriptor.original");
            List<ValueParameterDescriptor> list2 = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list2, "superDescriptor.original.valueParameters");
            for (Pair pair : CollectionsKt.zip(iterable, (Iterable)list2)) {
                void subParameter;
                ValueParameterDescriptor $i$a$-assert-JavaIncompatibilityRulesOverridabilityCondition$Companion$doesJavaOverrideHaveIncompatibleValueParameterKinds$22 = (ValueParameterDescriptor)pair.component1();
                ValueParameterDescriptor superParameter = (ValueParameterDescriptor)pair.component2();
                FunctionDescriptor functionDescriptor2 = (FunctionDescriptor)subDescriptor;
                void v6 = subParameter;
                Intrinsics.checkNotNullExpressionValue(v6, "subParameter");
                boolean isSubPrimitive = this.mapValueParameterType(functionDescriptor2, (ValueParameterDescriptor)v6) instanceof JvmType.Primitive;
                FunctionDescriptor functionDescriptor3 = (FunctionDescriptor)superDescriptor;
                ValueParameterDescriptor valueParameterDescriptor = superParameter;
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "superParameter");
                boolean isSuperPrimitive = this.mapValueParameterType(functionDescriptor3, valueParameterDescriptor) instanceof JvmType.Primitive;
                if (isSubPrimitive == isSuperPrimitive) continue;
                return true;
            }
            return false;
        }

        private final JvmType mapValueParameterType(FunctionDescriptor f2, ValueParameterDescriptor valueParameterDescriptor) {
            JvmType jvmType;
            if (MethodSignatureMappingKt.forceSingleValueParameterBoxing(f2) || this.isPrimitiveCompareTo(f2)) {
                KotlinType kotlinType = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "valueParameterDescriptor.type");
                jvmType = MethodSignatureMappingKt.mapToJvmType(TypeUtilsKt.makeNullable(kotlinType));
            } else {
                KotlinType kotlinType = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "valueParameterDescriptor.type");
                jvmType = MethodSignatureMappingKt.mapToJvmType(kotlinType);
            }
            return jvmType;
        }

        private final boolean isPrimitiveCompareTo(FunctionDescriptor f2) {
            if (f2.getValueParameters().size() != 1) {
                return false;
            }
            DeclarationDescriptor declarationDescriptor = f2.getContainingDeclaration();
            if (!(declarationDescriptor instanceof ClassDescriptor)) {
                declarationDescriptor = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
            if (classDescriptor == null) {
                return false;
            }
            ClassDescriptor classDescriptor2 = classDescriptor;
            List<ValueParameterDescriptor> list = f2.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "f.valueParameters");
            ValueParameterDescriptor valueParameterDescriptor = CollectionsKt.single(list);
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "f.valueParameters.single()");
            ClassifierDescriptor classifierDescriptor = valueParameterDescriptor.getType().getConstructor().getDeclarationDescriptor();
            if (!(classifierDescriptor instanceof ClassDescriptor)) {
                classifierDescriptor = null;
            }
            ClassDescriptor classDescriptor3 = (ClassDescriptor)classifierDescriptor;
            if (classDescriptor3 == null) {
                return false;
            }
            ClassDescriptor parameterClass = classDescriptor3;
            return KotlinBuiltIns.isPrimitiveClass(classDescriptor2) && Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor2), DescriptorUtilsKt.getFqNameSafe(parameterClass));
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

