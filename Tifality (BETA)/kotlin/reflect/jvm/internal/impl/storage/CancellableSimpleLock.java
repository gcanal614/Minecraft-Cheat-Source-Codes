/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.storage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.storage.DefaultSimpleLock;
import org.jetbrains.annotations.NotNull;

public final class CancellableSimpleLock
extends DefaultSimpleLock {
    private final Runnable checkCancelled;
    private final Function1<InterruptedException, Unit> interruptedExceptionHandler;

    @Override
    public void lock() {
        try {
            while (!this.getLock().tryLock(50L, TimeUnit.MILLISECONDS)) {
                this.checkCancelled.run();
            }
        }
        catch (InterruptedException e) {
            this.interruptedExceptionHandler.invoke(e);
        }
    }

    public CancellableSimpleLock(@NotNull Lock lock, @NotNull Runnable checkCancelled, @NotNull Function1<? super InterruptedException, Unit> interruptedExceptionHandler) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        Intrinsics.checkNotNullParameter(checkCancelled, "checkCancelled");
        Intrinsics.checkNotNullParameter(interruptedExceptionHandler, "interruptedExceptionHandler");
        super(lock);
        this.checkCancelled = checkCancelled;
        this.interruptedExceptionHandler = interruptedExceptionHandler;
    }

    public CancellableSimpleLock(@NotNull Runnable checkCancelled, @NotNull Function1<? super InterruptedException, Unit> interruptedExceptionHandler) {
        Intrinsics.checkNotNullParameter(checkCancelled, "checkCancelled");
        Intrinsics.checkNotNullParameter(interruptedExceptionHandler, "interruptedExceptionHandler");
        Function1<? super InterruptedException, Unit> function1 = interruptedExceptionHandler;
        Lock lock = new ReentrantLock();
        Runnable runnable = checkCancelled;
        this(lock, runnable, function1);
    }
}

