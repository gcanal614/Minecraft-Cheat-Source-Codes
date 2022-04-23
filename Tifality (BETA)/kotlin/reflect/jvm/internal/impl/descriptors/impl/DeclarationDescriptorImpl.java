/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotatedImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import org.jetbrains.annotations.NotNull;

public abstract class DeclarationDescriptorImpl
extends AnnotatedImpl
implements DeclarationDescriptor {
    @NotNull
    private final Name name;

    public DeclarationDescriptorImpl(@NotNull Annotations annotations2, @NotNull Name name) {
        if (annotations2 == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (name == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(1);
        }
        super(annotations2);
        this.name = name;
    }

    @Override
    @NotNull
    public Name getName() {
        Name name = this.name;
        if (name == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(2);
        }
        return name;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getOriginal() {
        DeclarationDescriptorImpl declarationDescriptorImpl = this;
        if (declarationDescriptorImpl == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(3);
        }
        return declarationDescriptorImpl;
    }

    public String toString() {
        return DeclarationDescriptorImpl.toString(this);
    }

    @NotNull
    public static String toString(@NotNull DeclarationDescriptor descriptor2) {
        String string;
        if (descriptor2 == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(4);
        }
        try {
            string = DescriptorRenderer.DEBUG_TEXT.render(descriptor2) + "[" + descriptor2.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(descriptor2)) + "]";
        }
        catch (Throwable e) {
            String string2 = descriptor2.getClass().getSimpleName() + " " + descriptor2.getName();
            if (string2 == null) {
                DeclarationDescriptorImpl.$$$reportNull$$$0(6);
            }
            return string2;
        }
        if (string == null) {
            DeclarationDescriptorImpl.$$$reportNull$$$0(5);
        }
        return string;
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
            case 2: 
            case 3: 
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
            case 2: 
            case 3: 
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
                objectArray3[0] = "annotations";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorImpl";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "descriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorImpl";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getName";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 5: 
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "toString";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: {
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "toString";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

