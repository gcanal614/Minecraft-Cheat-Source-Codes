/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.storage.CancellableSimpleLock;
import kotlin.reflect.jvm.internal.impl.storage.DefaultSimpleLock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SimpleLock {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.storage.SimpleLock$Companion.$$INSTANCE;

    public void lock();

    public void unlock();

    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final DefaultSimpleLock simpleLock(@Nullable Runnable checkCancelled, @Nullable Function1<? super InterruptedException, Unit> interruptedExceptionHandler) {
            return checkCancelled != null && interruptedExceptionHandler != null ? (DefaultSimpleLock)new CancellableSimpleLock(checkCancelled, interruptedExceptionHandler) : new DefaultSimpleLock(null, 1, null);
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

