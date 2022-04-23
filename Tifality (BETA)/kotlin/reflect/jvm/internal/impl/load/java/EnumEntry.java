/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultValue;
import org.jetbrains.annotations.NotNull;

public final class EnumEntry
extends JavaDefaultValue {
    @NotNull
    private final ClassDescriptor descriptor;

    public EnumEntry(@NotNull ClassDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        super(null);
        this.descriptor = descriptor2;
    }
}

