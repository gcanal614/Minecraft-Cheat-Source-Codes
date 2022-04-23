/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ExceptionUtilsKt {
    @NotNull
    public static final RuntimeException rethrow(@NotNull Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        throw e;
    }

    public static final boolean isProcessCanceledException(@NotNull Throwable $this$isProcessCanceledException) {
        Intrinsics.checkNotNullParameter($this$isProcessCanceledException, "$this$isProcessCanceledException");
        Class<?> klass = $this$isProcessCanceledException.getClass();
        while (!Intrinsics.areEqual(klass.getCanonicalName(), "com.intellij.openapi.progress.ProcessCanceledException")) {
            if (klass.getSuperclass() != null) continue;
            return false;
        }
        return true;
    }
}

