/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Substitutable;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import org.jetbrains.annotations.NotNull;

public interface ClassifierDescriptorWithTypeParameters
extends ClassifierDescriptor,
DeclarationDescriptorWithVisibility,
MemberDescriptor,
Substitutable<ClassifierDescriptorWithTypeParameters> {
    public boolean isInner();

    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters();
}

