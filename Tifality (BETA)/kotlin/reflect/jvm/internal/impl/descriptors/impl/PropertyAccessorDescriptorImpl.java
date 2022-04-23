/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PropertyAccessorDescriptorImpl
extends DeclarationDescriptorNonRootImpl
implements PropertyAccessorDescriptor {
    private boolean isDefault;
    private final boolean isExternal;
    private final Modality modality;
    private final PropertyDescriptor correspondingProperty;
    private final boolean isInline;
    private final CallableMemberDescriptor.Kind kind;
    private Visibility visibility;
    @Nullable
    private FunctionDescriptor initialSignatureDescriptor;

    public PropertyAccessorDescriptorImpl(@NotNull Modality modality, @NotNull Visibility visibility, @NotNull PropertyDescriptor correspondingProperty, @NotNull Annotations annotations2, @NotNull Name name, boolean isDefault, boolean isExternal, boolean isInline, CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (modality == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (visibility == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (correspondingProperty == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (annotations2 == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (name == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(4);
        }
        if (source == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(5);
        }
        super(correspondingProperty.getContainingDeclaration(), annotations2, name, source);
        this.initialSignatureDescriptor = null;
        this.modality = modality;
        this.visibility = visibility;
        this.correspondingProperty = correspondingProperty;
        this.isDefault = isDefault;
        this.isExternal = isExternal;
        this.isInline = isInline;
        this.kind = kind;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }

    @Override
    @NotNull
    public CallableMemberDescriptor.Kind getKind() {
        CallableMemberDescriptor.Kind kind = this.kind;
        if (kind == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(6);
        }
        return kind;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isInfix() {
        return false;
    }

    @Override
    public boolean isExternal() {
        return this.isExternal;
    }

    @Override
    public boolean isInline() {
        return this.isInline;
    }

    @Override
    public boolean isTailrec() {
        return false;
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

    @Override
    public boolean isExpect() {
        return false;
    }

    @Override
    public boolean isActual() {
        return false;
    }

    @Override
    @NotNull
    public FunctionDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(7);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(8);
        }
        return list;
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override
    @NotNull
    public Modality getModality() {
        Modality modality = this.modality;
        if (modality == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(9);
        }
        return modality;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = this.visibility;
        if (visibility == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(10);
        }
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    @NotNull
    public PropertyDescriptor getCorrespondingProperty() {
        PropertyDescriptor propertyDescriptor = this.correspondingProperty;
        if (propertyDescriptor == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(12);
        }
        return propertyDescriptor;
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.getCorrespondingProperty().getExtensionReceiverParameter();
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return this.getCorrespondingProperty().getDispatchReceiverParameter();
    }

    @Override
    @NotNull
    public FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> newCopyBuilder() {
        throw new UnsupportedOperationException("Accessors must be copied by the corresponding property");
    }

    @Override
    @NotNull
    public PropertyAccessorDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new UnsupportedOperationException("Accessors must be copied by the corresponding property");
    }

    @NotNull
    protected Collection<PropertyAccessorDescriptor> getOverriddenDescriptors(boolean isGetter) {
        ArrayList<PropertyAccessorDescriptor> result2 = new ArrayList<PropertyAccessorDescriptor>(0);
        for (PropertyDescriptor propertyDescriptor : this.getCorrespondingProperty().getOverriddenDescriptors()) {
            PropertyAccessorDescriptor accessorDescriptor = isGetter ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
            if (accessorDescriptor == null) continue;
            result2.add(accessorDescriptor);
        }
        ArrayList<PropertyAccessorDescriptor> arrayList = result2;
        if (arrayList == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(13);
        }
        return arrayList;
    }

    @Override
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            PropertyAccessorDescriptorImpl.$$$reportNull$$$0(14);
        }
        assert (overriddenDescriptors.isEmpty()) : "Overridden accessors should be empty";
    }

    @Override
    @NotNull
    public abstract PropertyAccessorDescriptor getOriginal();

    @Override
    @Nullable
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return this.initialSignatureDescriptor;
    }

    public void setInitialSignatureDescriptor(@Nullable FunctionDescriptor initialSignatureDescriptor) {
        this.initialSignatureDescriptor = initialSignatureDescriptor;
    }

    @Override
    public boolean isHiddenToOvercomeSignatureClash() {
        return false;
    }

    @Override
    public boolean isHiddenForResolutionEverywhereBesideSupercalls() {
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
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "modality";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "correspondingProperty";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyAccessorDescriptorImpl";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutor";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "overriddenDescriptors";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyAccessorDescriptorImpl";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getKind";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeParameters";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "getModality";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getCorrespondingVariable";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getCorrespondingProperty";
                break;
            }
            case 13: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "setOverriddenDescriptors";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

