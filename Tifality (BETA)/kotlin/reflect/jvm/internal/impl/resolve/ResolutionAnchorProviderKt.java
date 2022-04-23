/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProvider;
import org.jetbrains.annotations.NotNull;

public final class ResolutionAnchorProviderKt {
    @NotNull
    private static final ModuleDescriptor.Capability<ResolutionAnchorProvider> RESOLUTION_ANCHOR_PROVIDER_CAPABILITY = new ModuleDescriptor.Capability("ResolutionAnchorProvider");

    @NotNull
    public static final ModuleDescriptor.Capability<ResolutionAnchorProvider> getRESOLUTION_ANCHOR_PROVIDER_CAPABILITY() {
        return RESOLUTION_ANCHOR_PROVIDER_CAPABILITY;
    }
}

