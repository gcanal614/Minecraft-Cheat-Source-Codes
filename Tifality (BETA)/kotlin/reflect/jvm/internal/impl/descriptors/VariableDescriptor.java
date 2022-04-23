/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.ValueDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import org.jetbrains.annotations.Nullable;

public interface VariableDescriptor
extends ValueDescriptor {
    public boolean isVar();

    @Nullable
    public ConstantValue<?> getCompileTimeInitializer();

    public boolean isConst();

    public boolean isLateInit();
}

