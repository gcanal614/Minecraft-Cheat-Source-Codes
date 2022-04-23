/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClassConstructorDescriptor
extends ConstructorDescriptor {
    @Override
    @NotNull
    public ClassConstructorDescriptor getOriginal();

    @Override
    @Nullable
    public ClassConstructorDescriptor substitute(@NotNull TypeSubstitutor var1);
}

