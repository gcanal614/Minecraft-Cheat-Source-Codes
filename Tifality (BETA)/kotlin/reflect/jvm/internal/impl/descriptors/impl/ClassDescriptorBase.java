/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public abstract class ClassDescriptorBase
extends AbstractClassDescriptor {
    private final DeclarationDescriptor containingDeclaration;
    private final SourceElement source;
    private final boolean isExternal;

    protected ClassDescriptorBase(@NotNull StorageManager storageManager, @NotNull DeclarationDescriptor containingDeclaration, @NotNull Name name, @NotNull SourceElement source, boolean isExternal) {
        if (storageManager == null) {
            ClassDescriptorBase.$$$reportNull$$$0(0);
        }
        if (containingDeclaration == null) {
            ClassDescriptorBase.$$$reportNull$$$0(1);
        }
        if (name == null) {
            ClassDescriptorBase.$$$reportNull$$$0(2);
        }
        if (source == null) {
            ClassDescriptorBase.$$$reportNull$$$0(3);
        }
        super(storageManager, name);
        this.containingDeclaration = containingDeclaration;
        this.source = source;
        this.isExternal = isExternal;
    }

    @Override
    public boolean isExternal() {
        return this.isExternal;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = this.containingDeclaration;
        if (declarationDescriptor == null) {
            ClassDescriptorBase.$$$reportNull$$$0(4);
        }
        return declarationDescriptor;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = this.source;
        if (sourceElement == null) {
            ClassDescriptorBase.$$$reportNull$$$0(5);
        }
        return sourceElement;
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
            case 4: 
            case 5: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 4: 
            case 5: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 4: 
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorBase";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorBase";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingDeclaration";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getSource";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 4: 
            case 5: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 5: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

