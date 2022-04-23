/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.OperatorChecks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaMethodDescriptor
extends SimpleFunctionDescriptorImpl
implements JavaCallableMemberDescriptor {
    public static final CallableDescriptor.UserDataKey<ValueParameterDescriptor> ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER = new CallableDescriptor.UserDataKey<ValueParameterDescriptor>(){};
    private ParameterNamesStatus parameterNamesStatus;

    protected JavaMethodDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @Nullable SimpleFunctionDescriptor original, @NotNull Annotations annotations2, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(1);
        }
        if (name == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(2);
        }
        if (kind == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(3);
        }
        if (source == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(4);
        }
        super(containingDeclaration, original, annotations2, name, kind, source);
        this.parameterNamesStatus = null;
    }

    @NotNull
    public static JavaMethodDescriptor createJavaMethod(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(5);
        }
        if (annotations2 == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(6);
        }
        if (name == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(7);
        }
        if (source == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(8);
        }
        return new JavaMethodDescriptor(containingDeclaration, null, annotations2, name, CallableMemberDescriptor.Kind.DECLARATION, source);
    }

    @Override
    @NotNull
    public SimpleFunctionDescriptorImpl initialize(@Nullable ReceiverParameterDescriptor extensionReceiverParameter, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @Nullable KotlinType unsubstitutedReturnType, @Nullable Modality modality, @NotNull Visibility visibility, @Nullable Map<? extends CallableDescriptor.UserDataKey<?>, ?> userData) {
        if (typeParameters2 == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(9);
        }
        if (unsubstitutedValueParameters == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(10);
        }
        if (visibility == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(11);
        }
        SimpleFunctionDescriptorImpl descriptor2 = super.initialize(extensionReceiverParameter, dispatchReceiverParameter, typeParameters2, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userData);
        this.setOperator(OperatorChecks.INSTANCE.check(descriptor2).isSuccess());
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = descriptor2;
        if (simpleFunctionDescriptorImpl == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(12);
        }
        return simpleFunctionDescriptorImpl;
    }

    @Override
    public boolean hasStableParameterNames() {
        assert (this.parameterNamesStatus != null) : "Parameter names status was not set: " + this;
        return this.parameterNamesStatus.isStable;
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        assert (this.parameterNamesStatus != null) : "Parameter names status was not set: " + this;
        return this.parameterNamesStatus.isSynthesized;
    }

    public void setParameterNamesStatus(boolean hasStableParameterNames, boolean hasSynthesizedParameterNames) {
        this.parameterNamesStatus = ParameterNamesStatus.get(hasStableParameterNames, hasSynthesizedParameterNames);
    }

    @Override
    @NotNull
    protected JavaMethodDescriptor createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (newOwner == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(13);
        }
        if (kind == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(14);
        }
        if (annotations2 == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(15);
        }
        if (source == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(16);
        }
        JavaMethodDescriptor result2 = new JavaMethodDescriptor(newOwner, (SimpleFunctionDescriptor)original, annotations2, newName != null ? newName : this.getName(), kind, source);
        result2.setParameterNamesStatus(this.hasStableParameterNames(), this.hasSynthesizedParameterNames());
        JavaMethodDescriptor javaMethodDescriptor = result2;
        if (javaMethodDescriptor == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(17);
        }
        return javaMethodDescriptor;
    }

    @Override
    @NotNull
    public JavaMethodDescriptor enhance(@Nullable KotlinType enhancedReceiverType, @NotNull List<ValueParameterData> enhancedValueParametersData, @NotNull KotlinType enhancedReturnType, @Nullable Pair<CallableDescriptor.UserDataKey<?>, ?> additionalUserData) {
        if (enhancedValueParametersData == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(18);
        }
        if (enhancedReturnType == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(19);
        }
        List<ValueParameterDescriptor> enhancedValueParameters = UtilKt.copyValueParameters(enhancedValueParametersData, this.getValueParameters(), this);
        ReceiverParameterDescriptor enhancedReceiver = enhancedReceiverType == null ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(this, enhancedReceiverType, Annotations.Companion.getEMPTY());
        JavaMethodDescriptor enhancedMethod = (JavaMethodDescriptor)this.newCopyBuilder().setValueParameters(enhancedValueParameters).setReturnType(enhancedReturnType).setExtensionReceiverParameter(enhancedReceiver).setDropOriginalInContainingParts().setPreserveSourceElement().build();
        assert (enhancedMethod != null) : "null after substitution while enhancing " + this.toString();
        if (additionalUserData != null) {
            enhancedMethod.putInUserDataMap(additionalUserData.getFirst(), additionalUserData.getSecond());
        }
        JavaMethodDescriptor javaMethodDescriptor = enhancedMethod;
        if (javaMethodDescriptor == null) {
            JavaMethodDescriptor.$$$reportNull$$$0(20);
        }
        return javaMethodDescriptor;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 12: 
            case 17: 
            case 20: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 12: 
            case 17: 
            case 20: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 1: 
            case 6: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 4: 
            case 8: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedValueParameters";
                break;
            }
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 12: 
            case 17: 
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaMethodDescriptor";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "enhancedValueParametersData";
                break;
            }
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "enhancedReturnType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaMethodDescriptor";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "initialize";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "createSubstitutedCopy";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "enhance";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "createJavaMethod";
                break;
            }
            case 9: 
            case 10: 
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "initialize";
                break;
            }
            case 12: 
            case 17: 
            case 20: {
                break;
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "createSubstitutedCopy";
                break;
            }
            case 18: 
            case 19: {
                objectArray = objectArray;
                objectArray[2] = "enhance";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 12: 
            case 17: 
            case 20: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private static enum ParameterNamesStatus {
        NON_STABLE_DECLARED(false, false),
        STABLE_DECLARED(true, false),
        NON_STABLE_SYNTHESIZED(false, true),
        STABLE_SYNTHESIZED(true, true);

        public final boolean isStable;
        public final boolean isSynthesized;

        private ParameterNamesStatus(boolean isStable, boolean isSynthesized) {
            this.isStable = isStable;
            this.isSynthesized = isSynthesized;
        }

        @NotNull
        public static ParameterNamesStatus get(boolean stable, boolean synthesized) {
            ParameterNamesStatus parameterNamesStatus = stable ? (synthesized ? STABLE_SYNTHESIZED : STABLE_DECLARED) : (synthesized ? NON_STABLE_SYNTHESIZED : NON_STABLE_DECLARED);
            if (parameterNamesStatus == null) {
                ParameterNamesStatus.$$$reportNull$$$0(0);
            }
            return parameterNamesStatus;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaMethodDescriptor$ParameterNamesStatus", "get"));
        }
    }
}

