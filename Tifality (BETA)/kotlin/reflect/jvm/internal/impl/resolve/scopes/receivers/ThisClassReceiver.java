/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;

public interface ThisClassReceiver
extends ReceiverValue {
    @NotNull
    public ClassDescriptor getClassDescriptor();
}

