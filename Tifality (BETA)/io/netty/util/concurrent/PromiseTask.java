/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

class PromiseTask<V>
extends DefaultPromise<V>
implements RunnableFuture<V> {
    protected final Callable<V> task;

    static <T> Callable<T> toCallable(Runnable runnable, T result2) {
        return new RunnableAdapter<T>(runnable, result2);
    }

    PromiseTask(EventExecutor executor, Runnable runnable, V result2) {
        this(executor, PromiseTask.toCallable(runnable, result2));
    }

    PromiseTask(EventExecutor executor, Callable<V> callable) {
        super(executor);
        this.task = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public void run() {
        try {
            if (this.setUncancellableInternal()) {
                V result2 = this.task.call();
                this.setSuccessInternal(result2);
            }
        }
        catch (Throwable e) {
            this.setFailureInternal(e);
        }
    }

    @Override
    public final Promise<V> setFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setFailureInternal(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    @Override
    public final boolean tryFailure(Throwable cause) {
        return false;
    }

    protected final boolean tryFailureInternal(Throwable cause) {
        return super.tryFailure(cause);
    }

    @Override
    public final Promise<V> setSuccess(V result2) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setSuccessInternal(V result2) {
        super.setSuccess(result2);
        return this;
    }

    @Override
    public final boolean trySuccess(V result2) {
        return false;
    }

    protected final boolean trySuccessInternal(V result2) {
        return super.trySuccess(result2);
    }

    @Override
    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    protected final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }

    @Override
    protected StringBuilder toStringBuilder() {
        StringBuilder buf = super.toStringBuilder();
        buf.setCharAt(buf.length() - 1, ',');
        buf.append(" task: ");
        buf.append(this.task);
        buf.append(')');
        return buf;
    }

    private static final class RunnableAdapter<T>
    implements Callable<T> {
        final Runnable task;
        final T result;

        RunnableAdapter(Runnable task, T result2) {
            this.task = task;
            this.result = result2;
        }

        @Override
        public T call() {
            this.task.run();
            return this.result;
        }

        public String toString() {
            return "Callable(task: " + this.task + ", result: " + this.result + ')';
        }
    }
}

