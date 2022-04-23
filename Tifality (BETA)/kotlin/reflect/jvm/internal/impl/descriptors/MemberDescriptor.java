/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import org.jetbrains.annotations.NotNull;

public interface MemberDescriptor
extends DeclarationDescriptorNonRoot,
DeclarationDescriptorWithVisibility {
    @NotNull
    public Modality getModality();

    @Override
    @NotNull
    public Visibility getVisibility();

    public boolean isExpect();

    public boolean isActual();

    public boolean isExternal();
}

