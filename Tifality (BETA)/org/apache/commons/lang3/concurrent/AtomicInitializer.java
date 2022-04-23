/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;

public abstract class AtomicInitializer<T>
implements ConcurrentInitializer<T> {
    private final AtomicReference<T> reference = new AtomicReference();

    @Override
    public T get() throws ConcurrentException {
        T result2 = this.reference.get();
        if (result2 == null && !this.reference.compareAndSet(null, result2 = this.initialize())) {
            result2 = this.reference.get();
        }
        return result2;
    }

    protected abstract T initialize() throws ConcurrentException;
}

