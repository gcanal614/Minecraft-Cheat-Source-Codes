/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class ReverseOrdering<T>
extends Ordering<T>
implements Serializable {
    final Ordering<? super T> forwardOrder;
    private static final long serialVersionUID = 0L;

    ReverseOrdering(Ordering<? super T> forwardOrder) {
        this.forwardOrder = Preconditions.checkNotNull(forwardOrder);
    }

    @Override
    public int compare(T a2, T b2) {
        return this.forwardOrder.compare(b2, a2);
    }

    @Override
    public <S extends T> Ordering<S> reverse() {
        return this.forwardOrder;
    }

    @Override
    public <E extends T> E min(E a2, E b2) {
        return this.forwardOrder.max(a2, b2);
    }

    @Override
    public <E extends T> E min(E a2, E b2, E c, E ... rest) {
        return this.forwardOrder.max(a2, b2, c, rest);
    }

    @Override
    public <E extends T> E min(Iterator<E> iterator2) {
        return this.forwardOrder.max(iterator2);
    }

    @Override
    public <E extends T> E min(Iterable<E> iterable) {
        return this.forwardOrder.max(iterable);
    }

    @Override
    public <E extends T> E max(E a2, E b2) {
        return this.forwardOrder.min(a2, b2);
    }

    @Override
    public <E extends T> E max(E a2, E b2, E c, E ... rest) {
        return this.forwardOrder.min(a2, b2, c, rest);
    }

    @Override
    public <E extends T> E max(Iterator<E> iterator2) {
        return this.forwardOrder.min(iterator2);
    }

    @Override
    public <E extends T> E max(Iterable<E> iterable) {
        return this.forwardOrder.min(iterable);
    }

    public int hashCode() {
        return -this.forwardOrder.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ReverseOrdering) {
            ReverseOrdering that = (ReverseOrdering)object;
            return this.forwardOrder.equals(that.forwardOrder);
        }
        return false;
    }

    public String toString() {
        return this.forwardOrder + ".reverse()";
    }
}

