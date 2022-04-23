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
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyAccessorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertyGetterDescriptorImpl
extends PropertyAccessorDescriptorImpl
implements PropertyGetterDescriptor {
    private KotlinType returnType;
    @NotNull
    private final PropertyGetterDescriptor original;

    public PropertyGetterDescriptorImpl(@NotNull PropertyDescriptor correspondingProperty, @NotNull Annotations annotations2, @NotNull Modality modality, @NotNull Visibility visibility, boolean isDefault, boolean isExternal, boolean isInline, @NotNull CallableMemberDescriptor.Kind kind, @Nullable PropertyGetterDescriptor original, @NotNull SourceElement source) {
        if (correspondingProperty == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (modality == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (visibility == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (kind == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(4);
        }
        if (source == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(5);
        }
        super(modality, visibility, correspondingProperty, annotations2, Name.special("<get-" + correspondingProperty.getName() + ">"), isDefault, isExternal, isInline, kind, source);
        this.original = original != null ? original : this;
    }

    public void initialize(KotlinType returnType) {
        this.returnType = returnType == null ? this.getCorrespondingProperty().getType() : returnType;
    }

    @NotNull
    public Collection<? extends PropertyGetterDescriptor> getOverriddenDescriptors() {
        Collection<PropertyAccessorDescriptor> collection = super.getOverriddenDescriptors(true);
        if (collection == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(6);
        }
        return collection;
    }

    @Override
    @NotNull
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(7);
        }
        return list;
    }

    @Override
    public KotlinType getReturnType() {
        return this.returnType;
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitPropertyGetterDescriptor(this, data2);
    }

    @Override
    @NotNull
    public PropertyGetterDescriptor getOriginal() {
        PropertyGetterDescriptor propertyGetterDescriptor = this.original;
        if (propertyGetterDescriptor == null) {
            PropertyGetterDescriptorImpl.$$$reportNull$$$0(8);
        }
        return propertyGetterDescriptor;
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
                objectArray3[0] = "correspondingProperty";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "modality";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 6: 
            case 7: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyGetterDescriptorImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyGetterDescriptorImpl";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getValueParameters";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
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

