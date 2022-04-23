/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtoBufUtilKt {
    @Nullable
    public static final <M extends GeneratedMessageLite.ExtendableMessage<M>, T> T getExtensionOrNull(@NotNull GeneratedMessageLite.ExtendableMessage<M> $this$getExtensionOrNull, @NotNull GeneratedMessageLite.GeneratedExtension<M, T> extension) {
        Intrinsics.checkNotNullParameter($this$getExtensionOrNull, "$this$getExtensionOrNull");
        Intrinsics.checkNotNullParameter(extension, "extension");
        return $this$getExtensionOrNull.hasExtension(extension) ? (T)$this$getExtensionOrNull.getExtension(extension) : null;
    }

    @Nullable
    public static final <M extends GeneratedMessageLite.ExtendableMessage<M>, T> T getExtensionOrNull(@NotNull GeneratedMessageLite.ExtendableMessage<M> $this$getExtensionOrNull, @NotNull GeneratedMessageLite.GeneratedExtension<M, List<T>> extension, int index) {
        Intrinsics.checkNotNullParameter($this$getExtensionOrNull, "$this$getExtensionOrNull");
        Intrinsics.checkNotNullParameter(extension, "extension");
        return index < $this$getExtensionOrNull.getExtensionCount(extension) ? (T)$this$getExtensionOrNull.getExtension(extension, index) : null;
    }
}

