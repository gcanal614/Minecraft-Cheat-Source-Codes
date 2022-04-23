/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptorWithAccessors;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyDescriptor
extends CallableMemberDescriptor,
VariableDescriptorWithAccessors {
    @Nullable
    public PropertyGetterDescriptor getGetter();

    @Nullable
    public PropertySetterDescriptor getSetter();

    @NotNull
    public List<PropertyAccessorDescriptor> getAccessors();

    @Override
    @NotNull
    public PropertyDescriptor getOriginal();

    @NotNull
    public Collection<? extends PropertyDescriptor> getOverriddenDescriptors();

    @Nullable
    public FieldDescriptor getBackingField();

    @Nullable
    public FieldDescriptor getDelegateField();

    @Override
    public PropertyDescriptor substitute(@NotNull TypeSubstitutor var1);
}

