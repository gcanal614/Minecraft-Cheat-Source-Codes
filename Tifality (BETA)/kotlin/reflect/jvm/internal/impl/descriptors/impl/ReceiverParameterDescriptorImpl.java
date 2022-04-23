/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;

public class ReceiverParameterDescriptorImpl
extends AbstractReceiverParameterDescriptor {
    private final DeclarationDescriptor containingDeclaration;
    private final ReceiverValue value;

    public ReceiverParameterDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @NotNull ReceiverValue value, @NotNull Annotations annotations2) {
        if (containingDeclaration == null) {
            ReceiverParameterDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (value == null) {
            ReceiverParameterDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (annotations2 == null) {
            ReceiverParameterDescriptorImpl.$$$reportNull$$$0(2);
        }
        super(annotations2);
        this.containingDeclaration = containingDeclaration;
        this.value = value;
    }

    @Override
    @NotNull
    public ReceiverValue getValue() {
        ReceiverValue receiverValue = this.value;
        if (receiverValue == null) {
            ReceiverParameterDescriptorImpl.$$$reportNull$$$0(3);
        }
        return receiverValue;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = this.containingDeclaration;
        if (declarationDescriptor == null) {
            ReceiverParameterDescriptorImpl.$$$reportNull$$$0(4);
        }
        return declarationDescriptor;
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
            case 3: 
            case 4: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 3: 
            case 4: {
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
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "value";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 3: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ReceiverParameterDescriptorImpl";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ReceiverParameterDescriptorImpl";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getValue";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingDeclaration";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 3: 
            case 4: {
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "copy";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 3: 
            case 4: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

