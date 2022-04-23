/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ImplicitClassReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;

public class LazyClassReceiverParameterDescriptor
extends AbstractReceiverParameterDescriptor {
    private final ClassDescriptor descriptor;
    private final ImplicitClassReceiver receiverValue;

    public LazyClassReceiverParameterDescriptor(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            LazyClassReceiverParameterDescriptor.$$$reportNull$$$0(0);
        }
        super(Annotations.Companion.getEMPTY());
        this.descriptor = descriptor2;
        this.receiverValue = new ImplicitClassReceiver(descriptor2, null);
    }

    @Override
    @NotNull
    public ReceiverValue getValue() {
        ImplicitClassReceiver implicitClassReceiver = this.receiverValue;
        if (implicitClassReceiver == null) {
            LazyClassReceiverParameterDescriptor.$$$reportNull$$$0(1);
        }
        return implicitClassReceiver;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        ClassDescriptor classDescriptor = this.descriptor;
        if (classDescriptor == null) {
            LazyClassReceiverParameterDescriptor.$$$reportNull$$$0(2);
        }
        return classDescriptor;
    }

    @Override
    public String toString() {
        return "class " + this.descriptor.getName() + "::this";
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
            case 1: 
            case 2: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 1: 
            case 2: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "descriptor";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazyClassReceiverParameterDescriptor";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazyClassReceiverParameterDescriptor";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "getValue";
                break;
            }
            case 2: {
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
            case 1: 
            case 2: {
                break;
            }
            case 3: {
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
            case 1: 
            case 2: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

