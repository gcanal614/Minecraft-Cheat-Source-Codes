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
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ReceiverParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.TransientReceiver;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractReceiverParameterDescriptor
extends DeclarationDescriptorImpl
implements ReceiverParameterDescriptor {
    private static final Name RECEIVER_PARAMETER_NAME = Name.special("<this>");

    public AbstractReceiverParameterDescriptor(@NotNull Annotations annotations2) {
        if (annotations2 == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(0);
        }
        super(annotations2, RECEIVER_PARAMETER_NAME);
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(1);
        }
        if (substitutor.isEmpty()) {
            return this;
        }
        KotlinType substitutedType = this.getContainingDeclaration() instanceof ClassDescriptor ? substitutor.substitute(this.getType(), Variance.OUT_VARIANCE) : substitutor.substitute(this.getType(), Variance.INVARIANT);
        if (substitutedType == null) {
            return null;
        }
        if (substitutedType == this.getType()) {
            return this;
        }
        return new ReceiverParameterDescriptorImpl(this.getContainingDeclaration(), new TransientReceiver(substitutedType), this.getAnnotations());
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitReceiverParameterDescriptor(this, data2);
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return null;
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return null;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(2);
        }
        return list;
    }

    @Override
    @Nullable
    public KotlinType getReturnType() {
        return this.getType();
    }

    @Override
    @NotNull
    public KotlinType getType() {
        KotlinType kotlinType = this.getValue().getType();
        if (kotlinType == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(3);
        }
        return kotlinType;
    }

    @Override
    @NotNull
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(4);
        }
        return list;
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override
    @NotNull
    public Collection<? extends CallableDescriptor> getOverriddenDescriptors() {
        Set set = Collections.emptySet();
        if (set == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(5);
        }
        return set;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Visibilities.LOCAL;
        if (visibility == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(6);
        }
        return visibility;
    }

    @Override
    @NotNull
    public ParameterDescriptor getOriginal() {
        AbstractReceiverParameterDescriptor abstractReceiverParameterDescriptor = this;
        if (abstractReceiverParameterDescriptor == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(7);
        }
        return abstractReceiverParameterDescriptor;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        if (sourceElement == null) {
            AbstractReceiverParameterDescriptor.$$$reportNull$$$0(8);
        }
        return sourceElement;
    }

    @Override
    @Nullable
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
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
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
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
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
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
                objectArray3[0] = "substitutor";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractReceiverParameterDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractReceiverParameterDescriptor";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeParameters";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getType";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getValueParameters";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 8: {
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
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
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
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

