/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class InvalidModuleException
extends IllegalStateException {
    public InvalidModuleException(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        super(message);
    }
}

