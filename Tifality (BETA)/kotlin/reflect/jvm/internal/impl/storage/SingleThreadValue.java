/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.storage;

class SingleThreadValue<T> {
    private final T value;
    private final Thread thread;

    SingleThreadValue(T value) {
        this.value = value;
        this.thread = Thread.currentThread();
    }

    public boolean hasValue() {
        return this.thread == Thread.currentThread();
    }

    public T getValue() {
        if (!this.hasValue()) {
            throw new IllegalStateException("No value in this thread (hasValue should be checked before)");
        }
        return this.value;
    }
}

