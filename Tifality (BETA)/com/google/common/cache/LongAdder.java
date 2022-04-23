/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.LongAddable;
import com.google.common.cache.Striped64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@GwtCompatible(emulated=true)
final class LongAdder
extends Striped64
implements Serializable,
LongAddable {
    private static final long serialVersionUID = 7249069246863182397L;

    @Override
    final long fn(long v, long x) {
        return v + x;
    }

    @Override
    public void add(long x) {
        long b2;
        Striped64.Cell[] as = this.cells;
        if (this.cells != null || !this.casBase(b2 = this.base, b2 + x)) {
            long v;
            Striped64.Cell a2;
            int n;
            boolean uncontended = true;
            Striped64.HashCode hc = (Striped64.HashCode)threadHashCode.get();
            int h = hc.code;
            if (as == null || (n = as.length) < 1 || (a2 = as[n - 1 & h]) == null || !(uncontended = a2.cas(v = a2.value, v + x))) {
                this.retryUpdate(x, hc, uncontended);
            }
        }
    }

    @Override
    public void increment() {
        this.add(1L);
    }

    public void decrement() {
        this.add(-1L);
    }

    @Override
    public long sum() {
        long sum = this.base;
        Striped64.Cell[] as = this.cells;
        if (as != null) {
            for (Striped64.Cell a2 : as) {
                if (a2 == null) continue;
                sum += a2.value;
            }
        }
        return sum;
    }

    public void reset() {
        this.internalReset(0L);
    }

    public long sumThenReset() {
        long sum = this.base;
        Striped64.Cell[] as = this.cells;
        this.base = 0L;
        if (as != null) {
            for (Striped64.Cell a2 : as) {
                if (a2 == null) continue;
                sum += a2.value;
                a2.value = 0L;
            }
        }
        return sum;
    }

    public String toString() {
        return Long.toString(this.sum());
    }

    @Override
    public long longValue() {
        return this.sum();
    }

    @Override
    public int intValue() {
        return (int)this.sum();
    }

    @Override
    public float floatValue() {
        return this.sum();
    }

    @Override
    public double doubleValue() {
        return this.sum();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeLong(this.sum());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.busy = 0;
        this.cells = null;
        this.base = s.readLong();
    }
}

