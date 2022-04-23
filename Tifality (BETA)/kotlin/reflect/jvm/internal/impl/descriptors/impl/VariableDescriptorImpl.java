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
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.StubType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class VariableDescriptorImpl
extends DeclarationDescriptorNonRootImpl
implements VariableDescriptor {
    protected KotlinType outType;

    public VariableDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @Nullable KotlinType outType, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (name == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (source == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(3);
        }
        super(containingDeclaration, annotations2, name, source);
        this.outType = outType;
    }

    @Override
    @NotNull
    public KotlinType getType() {
        KotlinType kotlinType = this.outType;
        if (kotlinType == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(4);
        }
        return kotlinType;
    }

    public void setOutType(KotlinType outType) {
        assert (this.outType == null || this.outType instanceof StubType);
        this.outType = outType;
    }

    @Override
    @NotNull
    public VariableDescriptor getOriginal() {
        VariableDescriptor variableDescriptor = (VariableDescriptor)super.getOriginal();
        if (variableDescriptor == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(5);
        }
        return variableDescriptor;
    }

    @Override
    @NotNull
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(6);
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
            VariableDescriptorImpl.$$$reportNull$$$0(7);
        }
        return set;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(8);
        }
        return list;
    }

    @Override
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return null;
    }

    @Override
    public ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return null;
    }

    @Override
    @NotNull
    public KotlinType getReturnType() {
        KotlinType kotlinType = this.getType();
        if (kotlinType == null) {
            VariableDescriptorImpl.$$$reportNull$$$0(9);
        }
        return kotlinType;
    }

    @Override
    public boolean isConst() {
        return false;
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
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: {
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
            case 6: 
            case 7: 
            case 8: 
            case 9: {
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
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorImpl";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getType";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getValueParameters";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeParameters";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "getReturnType";
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
            case 6: 
            case 7: 
            case 8: 
            case 9: {
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
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

