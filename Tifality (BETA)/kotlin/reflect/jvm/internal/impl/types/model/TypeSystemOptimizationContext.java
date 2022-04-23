/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.model;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import org.jetbrains.annotations.NotNull;

public interface TypeSystemOptimizationContext {
    public boolean identicalArguments(@NotNull SimpleTypeMarker var1, @NotNull SimpleTypeMarker var2);

    public static final class DefaultImpls {
        public static boolean identicalArguments(@NotNull TypeSystemOptimizationContext $this, @NotNull SimpleTypeMarker a2, @NotNull SimpleTypeMarker b2) {
            Intrinsics.checkNotNullParameter(a2, "a");
            Intrinsics.checkNotNullParameter(b2, "b");
            return false;
        }
    }
}

