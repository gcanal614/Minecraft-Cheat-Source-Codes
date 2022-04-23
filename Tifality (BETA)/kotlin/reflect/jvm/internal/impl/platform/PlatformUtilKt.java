/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.platform;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.platform.TargetPlatform;
import org.jetbrains.annotations.NotNull;

public final class PlatformUtilKt {
    @NotNull
    public static final String getPresentableDescription(@NotNull TargetPlatform $this$presentableDescription) {
        Intrinsics.checkNotNullParameter($this$presentableDescription, "$this$presentableDescription");
        return CollectionsKt.joinToString$default($this$presentableDescription.getComponentPlatforms(), "/", null, null, 0, null, null, 62, null);
    }
}

