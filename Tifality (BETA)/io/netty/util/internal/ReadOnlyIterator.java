/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal;

import java.util.Iterator;

public final class ReadOnlyIterator<T>
implements Iterator<T> {
    private final Iterator<? extends T> iterator;

    public ReadOnlyIterator(Iterator<? extends T> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("iterator");
        }
        this.iterator = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        return this.iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("read-only");
    }
}

