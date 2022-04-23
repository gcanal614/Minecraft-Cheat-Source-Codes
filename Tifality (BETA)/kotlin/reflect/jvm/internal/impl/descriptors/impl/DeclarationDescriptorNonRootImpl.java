/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public abstract class DeclarationDescriptorNonRootImpl
extends DeclarationDescriptorImpl
implements DeclarationDescriptorNonRoot {
    @NotNull
    private final DeclarationDescriptor containingDeclaration;
    @NotNull
    private final SourceElement source;

    protected DeclarationDescriptorNonRootImpl(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(1);
        }
        if (name == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(2);
        }
        if (source == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(3);
        }
        super(annotations2, name);
        this.containingDeclaration = containingDeclaration;
        this.source = source;
    }

    @Override
    @NotNull
    public DeclarationDescriptorWithSource getOriginal() {
        DeclarationDescriptorWithSource declarationDescriptorWithSource = (DeclarationDescriptorWithSource)super.getOriginal();
        if (declarationDescriptorWithSource == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(4);
        }
        return declarationDescriptorWithSource;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = this.containingDeclaration;
        if (declarationDescriptor == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(5);
        }
        return declarationDescriptor;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = this.source;
        if (sourceElement == null) {
            DeclarationDescriptorNonRootImpl.$$$reportNull$$$0(6);
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
            case 5: 
            case 6: {
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
            case 5: 
            case 6: {
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
                objectArray3[0] = "annotations";
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
            case 5: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorNonRootImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorNonRootImpl";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingDeclaration";
                break;
            }
            case 6: {
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
            case 5: 
            case 6: {
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
            case 5: 
            case 6: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

