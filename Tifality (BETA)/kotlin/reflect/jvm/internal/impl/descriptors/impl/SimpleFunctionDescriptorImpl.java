/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleFunctionDescriptorImpl
extends FunctionDescriptorImpl
implements SimpleFunctionDescriptor {
    protected SimpleFunctionDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @Nullable SimpleFunctionDescriptor original, @NotNull Annotations annotations2, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (name == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (kind == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (source == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(4);
        }
        super(containingDeclaration, original, annotations2, name, kind, source);
    }

    @NotNull
    public static SimpleFunctionDescriptorImpl create(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(5);
        }
        if (annotations2 == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(6);
        }
        if (name == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(7);
        }
        if (kind == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(8);
        }
        if (source == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(9);
        }
        return new SimpleFunctionDescriptorImpl(containingDeclaration, null, annotations2, name, kind, source);
    }

    @Override
    @NotNull
    public SimpleFunctionDescriptorImpl initialize(@Nullable ReceiverParameterDescriptor extensionReceiverParameter, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @Nullable KotlinType unsubstitutedReturnType, @Nullable Modality modality, @NotNull Visibility visibility) {
        if (typeParameters2 == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(10);
        }
        if (unsubstitutedValueParameters == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(11);
        }
        if (visibility == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(12);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = this.initialize(extensionReceiverParameter, dispatchReceiverParameter, typeParameters2, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, null);
        if (simpleFunctionDescriptorImpl == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(13);
        }
        return simpleFunctionDescriptorImpl;
    }

    @NotNull
    public SimpleFunctionDescriptorImpl initialize(@Nullable ReceiverParameterDescriptor extensionReceiverParameter, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @Nullable KotlinType unsubstitutedReturnType, @Nullable Modality modality, @NotNull Visibility visibility, @Nullable Map<? extends CallableDescriptor.UserDataKey<?>, ?> userData) {
        if (typeParameters2 == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(14);
        }
        if (unsubstitutedValueParameters == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(15);
        }
        if (visibility == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(16);
        }
        super.initialize(extensionReceiverParameter, dispatchReceiverParameter, typeParameters2, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility);
        if (userData != null && !userData.isEmpty()) {
            this.userDataMap = new LinkedHashMap(userData);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = this;
        if (simpleFunctionDescriptorImpl == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(17);
        }
        return simpleFunctionDescriptorImpl;
    }

    @Override
    @NotNull
    public SimpleFunctionDescriptor getOriginal() {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)super.getOriginal();
        if (simpleFunctionDescriptor == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(18);
        }
        return simpleFunctionDescriptor;
    }

    @Override
    @NotNull
    protected FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (newOwner == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(19);
        }
        if (kind == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(20);
        }
        if (annotations2 == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(21);
        }
        if (source == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(22);
        }
        return new SimpleFunctionDescriptorImpl(newOwner, (SimpleFunctionDescriptor)original, annotations2, newName != null ? newName : this.getName(), kind, source);
    }

    @Override
    @NotNull
    public SimpleFunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)super.copy(newOwner, modality, visibility, kind, copyOverrides);
        if (simpleFunctionDescriptor == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(23);
        }
        return simpleFunctionDescriptor;
    }

    @Override
    @NotNull
    public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder() {
        FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> copyBuilder = super.newCopyBuilder();
        if (copyBuilder == null) {
            SimpleFunctionDescriptorImpl.$$$reportNull$$$0(24);
        }
        return copyBuilder;
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
            case 13: 
            case 17: 
            case 18: 
            case 23: 
            case 24: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 13: 
            case 17: 
            case 18: 
            case 23: 
            case 24: {
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
            case 21: {
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
            case 8: 
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 4: 
            case 9: 
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 10: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 11: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedValueParameters";
                break;
            }
            case 12: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 13: 
            case 17: 
            case 18: 
            case 23: 
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/SimpleFunctionDescriptorImpl";
                break;
            }
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/SimpleFunctionDescriptorImpl";
                break;
            }
            case 13: 
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "initialize";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "copy";
                break;
            }
            case 24: {
                objectArray = objectArray2;
                objectArray2[1] = "newCopyBuilder";
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
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "initialize";
                break;
            }
            case 13: 
            case 17: 
            case 18: 
            case 23: 
            case 24: {
                break;
            }
            case 19: 
            case 20: 
            case 21: 
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "createSubstitutedCopy";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 13: 
            case 17: 
            case 18: 
            case 23: 
            case 24: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

