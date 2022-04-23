/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ValueParameterDescriptor
extends ParameterDescriptor,
VariableDescriptor {
    @Override
    @NotNull
    public CallableDescriptor getContainingDeclaration();

    public int getIndex();

    public boolean declaresDefaultValue();

    @Nullable
    public KotlinType getVarargElementType();

    @Override
    @NotNull
    public ValueParameterDescriptor getOriginal();

    @NotNull
    public ValueParameterDescriptor copy(@NotNull CallableDescriptor var1, @NotNull Name var2, int var3);

    @NotNull
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors();

    public boolean isCrossinline();

    public boolean isNoinline();

    public static final class DefaultImpls {
        public static boolean isLateInit(@NotNull ValueParameterDescriptor $this) {
            return false;
        }
    }
}

