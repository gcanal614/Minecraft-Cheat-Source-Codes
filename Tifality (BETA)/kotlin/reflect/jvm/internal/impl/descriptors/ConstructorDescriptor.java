/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConstructorDescriptor
extends FunctionDescriptor {
    @Override
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters();

    @Override
    @NotNull
    public KotlinType getReturnType();

    @Override
    @NotNull
    public ClassifierDescriptorWithTypeParameters getContainingDeclaration();

    @NotNull
    public ClassDescriptor getConstructedClass();

    @Override
    @Nullable
    public ConstructorDescriptor substitute(@NotNull TypeSubstitutor var1);

    public boolean isPrimary();
}

