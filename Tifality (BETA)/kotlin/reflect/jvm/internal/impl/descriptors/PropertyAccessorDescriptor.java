/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableAccessorDescriptor;
import org.jetbrains.annotations.NotNull;

public interface PropertyAccessorDescriptor
extends VariableAccessorDescriptor {
    public boolean isDefault();

    @NotNull
    public PropertyDescriptor getCorrespondingProperty();
}

