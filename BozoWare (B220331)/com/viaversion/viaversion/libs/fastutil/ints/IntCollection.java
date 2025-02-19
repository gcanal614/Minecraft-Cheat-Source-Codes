// 
// Decompiled by Procyon v0.5.36
// 

package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Collection;

public interface IntCollection extends Collection<Integer>, IntIterable
{
    IntIterator iterator();
    
    boolean add(final int p0);
    
    boolean contains(final int p0);
    
    boolean rem(final int p0);
    
    @Deprecated
    default boolean add(final Integer key) {
        return this.add((int)key);
    }
    
    @Deprecated
    default boolean contains(final Object key) {
        return key != null && this.contains((int)key);
    }
    
    @Deprecated
    default boolean remove(final Object key) {
        return key != null && this.rem((int)key);
    }
    
    int[] toIntArray();
    
    @Deprecated
    int[] toIntArray(final int[] p0);
    
    int[] toArray(final int[] p0);
    
    boolean addAll(final IntCollection p0);
    
    boolean containsAll(final IntCollection p0);
    
    boolean removeAll(final IntCollection p0);
    
    @Deprecated
    default boolean removeIf(final Predicate<? super Integer> filter) {
        return this.removeIf(key -> filter.test(key));
    }
    
    default boolean removeIf(final IntPredicate filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final IntIterator each = this.iterator();
        while (each.hasNext()) {
            if (filter.test(each.nextInt())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }
    
    boolean retainAll(final IntCollection p0);
}
