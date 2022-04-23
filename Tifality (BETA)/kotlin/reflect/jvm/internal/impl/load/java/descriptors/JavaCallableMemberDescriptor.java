/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.Pair;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaCallableMemberDescriptor
extends CallableMemberDescriptor {
    @NotNull
    public JavaCallableMemberDescriptor enhance(@Nullable KotlinType var1, @NotNull List<ValueParameterData> var2, @NotNull KotlinType var3, @Nullable Pair<CallableDescriptor.UserDataKey<?>, ?> var4);
}

