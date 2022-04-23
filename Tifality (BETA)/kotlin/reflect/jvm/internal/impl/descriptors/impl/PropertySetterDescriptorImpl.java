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
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyAccessorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertySetterDescriptorImpl
extends PropertyAccessorDescriptorImpl
implements PropertySetterDescriptor {
    private ValueParameterDescriptor parameter;
    @NotNull
    private final PropertySetterDescriptor original;

    public PropertySetterDescriptorImpl(@NotNull PropertyDescriptor correspondingProperty, @NotNull Annotations annotations2, @NotNull Modality modality, @NotNull Visibility visibility, boolean isDefault, boolean isExternal, boolean isInline, @NotNull CallableMemberDescriptor.Kind kind, @Nullable PropertySetterDescriptor original, @NotNull SourceElement source) {
        if (correspondingProperty == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (modality == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (visibility == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (kind == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(4);
        }
        if (source == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(5);
        }
        super(modality, visibility, correspondingProperty, annotations2, Name.special("<set-" + correspondingProperty.getName() + ">"), isDefault, isExternal, isInline, kind, source);
        this.original = original != null ? original : this;
    }

    public void initialize(@NotNull ValueParameterDescriptor parameter) {
        if (parameter == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(6);
        }
        assert (this.parameter == null);
        this.parameter = parameter;
    }

    public static ValueParameterDescriptorImpl createSetterParameter(@NotNull PropertySetterDescriptor setterDescriptor, @NotNull KotlinType type2, @NotNull Annotations annotations2) {
        if (setterDescriptor == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(7);
        }
        if (type2 == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(8);
        }
        if (annotations2 == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(9);
        }
        return new ValueParameterDescriptorImpl(setterDescriptor, null, 0, annotations2, Name.special("<set-?>"), type2, false, false, false, null, SourceElement.NO_SOURCE);
    }

    @NotNull
    public Collection<? extends PropertySetterDescriptor> getOverriddenDescriptors() {
        Collection<PropertyAccessorDescriptor> collection = super.getOverriddenDescriptors(false);
        if (collection == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(10);
        }
        return collection;
    }

    @Override
    @NotNull
    public List<ValueParameterDescriptor> getValueParameters() {
        if (this.parameter == null) {
            throw new IllegalStateException();
        }
        List<ValueParameterDescriptor> list = Collections.singletonList(this.parameter);
        if (list == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(11);
        }
        return list;
    }

    @Override
    @NotNull
    public KotlinType getReturnType() {
        SimpleType simpleType2 = DescriptorUtilsKt.getBuiltIns(this).getUnitType();
        if (simpleType2 == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(12);
        }
        return simpleType2;
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitPropertySetterDescriptor(this, data2);
    }

    @Override
    @NotNull
    public PropertySetterDescriptor getOriginal() {
        PropertySetterDescriptor propertySetterDescriptor = this.original;
        if (propertySetterDescriptor == null) {
            PropertySetterDescriptorImpl.$$$reportNull$$$0(13);
        }
        return propertySetterDescriptor;
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
                objectArray3[0] = "correspondingProperty";
                break;
            }
            case 1: 
            case 9: {
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
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameter";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "setterDescriptor";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertySetterDescriptorImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertySetterDescriptorImpl";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getValueParameters";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getReturnType";
                break;
            }
            case 13: {
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
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "initialize";
                break;
            }
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "createSetterParameter";
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
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

