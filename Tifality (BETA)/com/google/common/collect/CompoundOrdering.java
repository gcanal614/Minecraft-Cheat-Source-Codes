/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;

@GwtCompatible(serializable=true)
final class CompoundOrdering<T>
extends Ordering<T>
implements Serializable {
    final ImmutableList<Comparator<? super T>> comparators;
    private static final long serialVersionUID = 0L;

    CompoundOrdering(Comparator<? super T> primary, Comparator<? super T> secondary) {
        this.comparators = ImmutableList.of(primary, secondary);
    }

    CompoundOrdering(Iterable<? extends Comparator<? super T>> comparators) {
        this.comparators = ImmutableList.copyOf(comparators);
    }

    @Override
    public int compare(T left, T right) {
        int size = this.comparators.size();
        for (int i = 0; i < size; ++i) {
            int result2 = ((Comparator)this.comparators.get(i)).compare(left, right);
            if (result2 == 0) continue;
            return result2;
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof CompoundOrdering) {
            CompoundOrdering that = (CompoundOrdering)object;
            return this.comparators.equals(that.comparators);
        }
        return false;
    }

    public int hashCode() {
        return this.comparators.hashCode();
    }

    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}

