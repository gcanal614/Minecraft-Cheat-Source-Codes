/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactoryImpl;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfigurationImpl;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MethodSignatureMappingKt {
    @NotNull
    public static final String computeJvmDescriptor(@NotNull FunctionDescriptor $this$computeJvmDescriptor, boolean withReturnType, boolean withName) {
        Intrinsics.checkNotNullParameter($this$computeJvmDescriptor, "$this$computeJvmDescriptor");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        if (withName) {
            String string;
            if ($this$computeJvmDescriptor instanceof ConstructorDescriptor) {
                string = "<init>";
            } else {
                String string2 = $this$computeJvmDescriptor.getName().asString();
                string = string2;
                Intrinsics.checkNotNullExpressionValue(string2, "name.asString()");
            }
            $this$buildString.append(string);
        }
        $this$buildString.append("(");
        ReceiverParameterDescriptor receiverParameterDescriptor = $this$computeJvmDescriptor.getExtensionReceiverParameter();
        if (receiverParameterDescriptor != null) {
            ReceiverParameterDescriptor receiverParameterDescriptor2 = receiverParameterDescriptor;
            boolean bl6 = false;
            boolean bl7 = false;
            ReceiverParameterDescriptor it = receiverParameterDescriptor2;
            boolean bl8 = false;
            ReceiverParameterDescriptor receiverParameterDescriptor3 = it;
            Intrinsics.checkNotNullExpressionValue(receiverParameterDescriptor3, "it");
            KotlinType kotlinType = receiverParameterDescriptor3.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
            MethodSignatureMappingKt.appendErasedType($this$buildString, kotlinType);
        }
        Iterator<ValueParameterDescriptor> iterator2 = $this$computeJvmDescriptor.getValueParameters().iterator();
        while (iterator2.hasNext()) {
            ValueParameterDescriptor parameter;
            ValueParameterDescriptor valueParameterDescriptor = parameter = iterator2.next();
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "parameter");
            KotlinType kotlinType = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "parameter.type");
            MethodSignatureMappingKt.appendErasedType($this$buildString, kotlinType);
        }
        $this$buildString.append(")");
        if (withReturnType) {
            if (TypeSignatureMappingKt.hasVoidReturnType($this$computeJvmDescriptor)) {
                $this$buildString.append("V");
            } else {
                KotlinType kotlinType = $this$computeJvmDescriptor.getReturnType();
                Intrinsics.checkNotNull(kotlinType);
                Intrinsics.checkNotNullExpressionValue(kotlinType, "returnType!!");
                MethodSignatureMappingKt.appendErasedType($this$buildString, kotlinType);
            }
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public static /* synthetic */ String computeJvmDescriptor$default(FunctionDescriptor functionDescriptor, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 1) != 0) {
            bl = true;
        }
        if ((n & 2) != 0) {
            bl2 = true;
        }
        return MethodSignatureMappingKt.computeJvmDescriptor(functionDescriptor, bl, bl2);
    }

    public static final boolean forceSingleValueParameterBoxing(@NotNull CallableDescriptor f2) {
        Intrinsics.checkNotNullParameter(f2, "f");
        if (!(f2 instanceof FunctionDescriptor)) {
            return false;
        }
        if (((FunctionDescriptor)f2).getValueParameters().size() != 1 || SpecialBuiltinMembers.isFromJavaOrBuiltins((CallableMemberDescriptor)f2) || Intrinsics.areEqual(((FunctionDescriptor)f2).getName().asString(), "remove") ^ true) {
            return false;
        }
        FunctionDescriptor functionDescriptor = ((FunctionDescriptor)f2).getOriginal();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "f.original");
        List<ValueParameterDescriptor> list = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "f.original.valueParameters");
        ValueParameterDescriptor valueParameterDescriptor = CollectionsKt.single(list);
        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "f.original.valueParameters.single()");
        KotlinType kotlinType = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "f.original.valueParameters.single().type");
        JvmType jvmType = MethodSignatureMappingKt.mapToJvmType(kotlinType);
        if (!(jvmType instanceof JvmType.Primitive)) {
            jvmType = null;
        }
        JvmType.Primitive primitive = (JvmType.Primitive)jvmType;
        if ((primitive != null ? primitive.getJvmPrimitiveType() : null) != JvmPrimitiveType.INT) {
            return false;
        }
        FunctionDescriptor functionDescriptor2 = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor)f2);
        if (functionDescriptor2 == null) {
            return false;
        }
        FunctionDescriptor overridden = functionDescriptor2;
        FunctionDescriptor functionDescriptor3 = overridden.getOriginal();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor3, "overridden.original");
        List<ValueParameterDescriptor> list2 = functionDescriptor3.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list2, "overridden.original.valueParameters");
        ValueParameterDescriptor valueParameterDescriptor2 = CollectionsKt.single(list2);
        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor2, "overridden.original.valueParameters.single()");
        KotlinType kotlinType2 = valueParameterDescriptor2.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType2, "overridden.original.valueParameters.single().type");
        JvmType overriddenParameterType = MethodSignatureMappingKt.mapToJvmType(kotlinType2);
        DeclarationDescriptor declarationDescriptor = overridden.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "overridden.containingDeclaration");
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor), KotlinBuiltIns.FQ_NAMES.mutableCollection.toUnsafe()) && overriddenParameterType instanceof JvmType.Object && Intrinsics.areEqual(((JvmType.Object)overriddenParameterType).getInternalName(), "java/lang/Object");
    }

    @Nullable
    public static final String computeJvmSignature(@NotNull CallableDescriptor $this$computeJvmSignature) {
        Intrinsics.checkNotNullParameter($this$computeJvmSignature, "$this$computeJvmSignature");
        boolean $i$f$signatures = false;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        boolean bl = false;
        boolean bl2 = false;
        SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
        boolean bl3 = false;
        if (DescriptorUtils.isLocal($this$computeJvmSignature)) {
            return null;
        }
        DeclarationDescriptor declarationDescriptor = $this$computeJvmSignature.getContainingDeclaration();
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            declarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
        if (classDescriptor == null) {
            return null;
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        Name name = classDescriptor2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "classDescriptor.name");
        if (name.isSpecial()) {
            return null;
        }
        CallableDescriptor callableDescriptor = $this$computeJvmSignature.getOriginal();
        if (!(callableDescriptor instanceof SimpleFunctionDescriptor)) {
            callableDescriptor = null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)callableDescriptor;
        if (simpleFunctionDescriptor == null) {
            return null;
        }
        return $this$signatures.signature(classDescriptor2, MethodSignatureMappingKt.computeJvmDescriptor$default(simpleFunctionDescriptor, false, false, 3, null));
    }

    @NotNull
    public static final String getInternalName(@NotNull ClassDescriptor $this$internalName) {
        Intrinsics.checkNotNullParameter($this$internalName, "$this$internalName");
        FqNameUnsafe fqNameUnsafe = DescriptorUtilsKt.getFqNameSafe($this$internalName).toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "fqNameSafe.toUnsafe()");
        ClassId classId = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(fqNameUnsafe);
        if (classId != null) {
            ClassId classId2 = classId;
            boolean bl = false;
            boolean bl2 = false;
            ClassId it = classId2;
            boolean bl3 = false;
            JvmClassName jvmClassName = JvmClassName.byClassId(it);
            Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byClassId(it)");
            String string = jvmClassName.getInternalName();
            Intrinsics.checkNotNullExpressionValue(string, "JvmClassName.byClassId(it).internalName");
            return string;
        }
        return TypeSignatureMappingKt.computeInternalName$default($this$internalName, null, 2, null);
    }

    private static final void appendErasedType(StringBuilder $this$appendErasedType, KotlinType type2) {
        $this$appendErasedType.append(MethodSignatureMappingKt.mapToJvmType(type2));
    }

    @NotNull
    public static final JvmType mapToJvmType(@NotNull KotlinType $this$mapToJvmType) {
        Intrinsics.checkNotNullParameter($this$mapToJvmType, "$this$mapToJvmType");
        return (JvmType)TypeSignatureMappingKt.mapType$default($this$mapToJvmType, JvmTypeFactoryImpl.INSTANCE, TypeMappingMode.DEFAULT, TypeMappingConfigurationImpl.INSTANCE, null, null, 32, null);
    }
}

