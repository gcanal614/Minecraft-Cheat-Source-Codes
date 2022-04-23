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
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Substitutable;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CallableDescriptor
extends DeclarationDescriptorNonRoot,
DeclarationDescriptorWithVisibility,
Substitutable<CallableDescriptor> {
    @Nullable
    public ReceiverParameterDescriptor getExtensionReceiverParameter();

    @Nullable
    public ReceiverParameterDescriptor getDispatchReceiverParameter();

    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters();

    @Nullable
    public KotlinType getReturnType();

    @Override
    @NotNull
    public CallableDescriptor getOriginal();

    @NotNull
    public List<ValueParameterDescriptor> getValueParameters();

    public boolean hasSynthesizedParameterNames();

    @NotNull
    public Collection<? extends CallableDescriptor> getOverriddenDescriptors();

    @Nullable
    public <V> V getUserData(UserDataKey<V> var1);

    public static interface UserDataKey<V> {
    }
}

