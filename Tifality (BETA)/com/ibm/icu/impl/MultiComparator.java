/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.impl;

import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MultiComparator<T>
implements Comparator<T> {
    private Comparator<T>[] comparators;

    public MultiComparator(Comparator<T> ... comparators) {
        this.comparators = comparators;
    }

    @Override
    public int compare(T arg0, T arg1) {
        for (int i = 0; i < this.comparators.length; ++i) {
            int result2 = this.comparators[i].compare(arg0, arg1);
            if (result2 == 0) continue;
            if (result2 > 0) {
                return i + 1;
            }
            return -(i + 1);
        }
        return 0;
    }
}

