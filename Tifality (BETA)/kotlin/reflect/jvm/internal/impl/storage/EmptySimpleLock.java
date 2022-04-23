/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.reflect.jvm.internal.impl.storage.SimpleLock;

public final class EmptySimpleLock
implements SimpleLock {
    public static final EmptySimpleLock INSTANCE;

    @Override
    public void lock() {
    }

    @Override
    public void unlock() {
    }

    private EmptySimpleLock() {
    }

    static {
        EmptySimpleLock emptySimpleLock;
        INSTANCE = emptySimpleLock = new EmptySimpleLock();
    }
}

