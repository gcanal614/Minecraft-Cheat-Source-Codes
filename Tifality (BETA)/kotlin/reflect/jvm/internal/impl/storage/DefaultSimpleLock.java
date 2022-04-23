/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.storage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.storage.SimpleLock;
import org.jetbrains.annotations.NotNull;

public class DefaultSimpleLock
implements SimpleLock {
    @NotNull
    private final Lock lock;

    @Override
    public void lock() {
        this.lock.lock();
    }

    @Override
    public void unlock() {
        this.lock.unlock();
    }

    @NotNull
    protected final Lock getLock() {
        return this.lock;
    }

    public DefaultSimpleLock(@NotNull Lock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
    }

    public /* synthetic */ DefaultSimpleLock(Lock lock, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            lock = new ReentrantLock();
        }
        this(lock);
    }

    public DefaultSimpleLock() {
        this(null, 1, null);
    }
}

