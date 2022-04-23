/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClassConstructorDescriptorImpl
extends FunctionDescriptorImpl
implements ClassConstructorDescriptor {
    protected final boolean isPrimary;
    private static final Name NAME = Name.special("<init>");

    protected ClassConstructorDescriptorImpl(@NotNull ClassDescriptor containingDeclaration, @Nullable ConstructorDescriptor original, @NotNull Annotations annotations2, boolean isPrimary, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (kind == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (source == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(3);
        }
        super(containingDeclaration, original, annotations2, NAME, kind, source);
        this.isPrimary = isPrimary;
    }

    @NotNull
    public static ClassConstructorDescriptorImpl create(@NotNull ClassDescriptor containingDeclaration, @NotNull Annotations annotations2, boolean isPrimary, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(4);
        }
        if (annotations2 == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(5);
        }
        if (source == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(6);
        }
        return new ClassConstructorDescriptorImpl(containingDeclaration, null, annotations2, isPrimary, CallableMemberDescriptor.Kind.DECLARATION, source);
    }

    public ClassConstructorDescriptorImpl initialize(@NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @NotNull Visibility visibility, @NotNull List<TypeParameterDescriptor> typeParameterDescriptors) {
        if (unsubstitutedValueParameters == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(10);
        }
        if (visibility == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(11);
        }
        if (typeParameterDescriptors == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(12);
        }
        super.initialize(null, this.calculateDispatchReceiverParameter(), typeParameterDescriptors, unsubstitutedValueParameters, null, Modality.FINAL, visibility);
        return this;
    }

    public ClassConstructorDescriptorImpl initialize(@NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @NotNull Visibility visibility) {
        if (unsubstitutedValueParameters == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(13);
        }
        if (visibility == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(14);
        }
        this.initialize(unsubstitutedValueParameters, visibility, this.getContainingDeclaration().getDeclaredTypeParameters());
        return this;
    }

    @Nullable
    public ReceiverParameterDescriptor calculateDispatchReceiverParameter() {
        DeclarationDescriptor classContainer;
        ClassDescriptor classDescriptor = this.getContainingDeclaration();
        if (classDescriptor.isInner() && (classContainer = classDescriptor.getContainingDeclaration()) instanceof ClassDescriptor) {
            return ((ClassDescriptor)classContainer).getThisAsReceiverParameter();
        }
        return null;
    }

    @Override
    @NotNull
    public ClassDescriptor getContainingDeclaration() {
        ClassDescriptor classDescriptor = (ClassDescriptor)super.getContainingDeclaration();
        if (classDescriptor == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(15);
        }
        return classDescriptor;
    }

    @Override
    @NotNull
    public ClassDescriptor getConstructedClass() {
        ClassDescriptor classDescriptor = this.getContainingDeclaration();
        if (classDescriptor == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(16);
        }
        return classDescriptor;
    }

    @Override
    @NotNull
    public ClassConstructorDescriptor getOriginal() {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor)super.getOriginal();
        if (classConstructorDescriptor == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(17);
        }
        return classConstructorDescriptor;
    }

    @Override
    @Nullable
    public ClassConstructorDescriptor substitute(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(18);
        }
        return (ClassConstructorDescriptor)super.substitute(originalSubstitutor);
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitConstructorDescriptor(this, data2);
    }

    @Override
    public boolean isPrimary() {
        return this.isPrimary;
    }

    @Override
    @NotNull
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        Set set = Collections.emptySet();
        if (set == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(19);
        }
        return set;
    }

    @Override
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(20);
        }
        assert (overriddenDescriptors.isEmpty()) : "Constructors cannot override anything";
    }

    @Override
    @NotNull
    protected ClassConstructorDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (newOwner == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(21);
        }
        if (kind == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(22);
        }
        if (annotations2 == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(23);
        }
        if (source == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(24);
        }
        if (kind != CallableMemberDescriptor.Kind.DECLARATION && kind != CallableMemberDescriptor.Kind.SYNTHESIZED) {
            throw new IllegalStateException("Attempt at creating a constructor that is not a declaration: \ncopy from: " + this + "\n" + "newOwner: " + newOwner + "\n" + "kind: " + (Object)((Object)kind));
        }
        assert (newName == null) : "Attempt to rename constructor: " + this;
        return new ClassConstructorDescriptorImpl((ClassDescriptor)newOwner, this, annotations2, this.isPrimary, CallableMemberDescriptor.Kind.DECLARATION, source);
    }

    @Override
    @NotNull
    public ClassConstructorDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor)super.copy(newOwner, modality, visibility, kind, copyOverrides);
        if (classConstructorDescriptor == null) {
            ClassConstructorDescriptorImpl.$$$reportNull$$$0(25);
        }
        return classConstructorDescriptor;
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
            case 15: 
            case 16: 
            case 17: 
            case 19: 
            case 25: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 15: 
            case 16: 
            case 17: 
            case 19: 
            case 25: {
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
            case 5: 
            case 8: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: 
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 3: 
            case 6: 
            case 9: 
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 10: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedValueParameters";
                break;
            }
            case 11: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameterDescriptors";
                break;
            }
            case 15: 
            case 16: 
            case 17: 
            case 19: 
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassConstructorDescriptorImpl";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "originalSubstitutor";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "overriddenDescriptors";
                break;
            }
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassConstructorDescriptorImpl";
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingDeclaration";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getConstructedClass";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 25: {
                objectArray = objectArray2;
                objectArray2[1] = "copy";
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
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "createSynthesized";
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "initialize";
                break;
            }
            case 15: 
            case 16: 
            case 17: 
            case 19: 
            case 25: {
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "setOverriddenDescriptors";
                break;
            }
            case 21: 
            case 22: 
            case 23: 
            case 24: {
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
            case 15: 
            case 16: 
            case 17: 
            case 19: 
            case 25: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

