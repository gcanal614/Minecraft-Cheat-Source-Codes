/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResolutionAnchorProvider {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProvider$Companion.$$INSTANCE;

    @Nullable
    public ModuleDescriptor getResolutionAnchor(@NotNull ModuleDescriptor var1);

    public static final class Companion {
        @NotNull
        private static final ResolutionAnchorProvider Default;
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            Default = new ResolutionAnchorProvider(){

                @Nullable
                public ModuleDescriptor getResolutionAnchor(@NotNull ModuleDescriptor moduleDescriptor) {
                    Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
                    return null;
                }
            };
        }
    }
}

