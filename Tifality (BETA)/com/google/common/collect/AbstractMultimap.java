/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultimap<K, V>
implements Multimap<K, V> {
    private transient Collection<Map.Entry<K, V>> entries;
    private transient Set<K> keySet;
    private transient Multiset<K> keys;
    private transient Collection<V> values;
    private transient Map<K, Collection<V>> asMap;

    AbstractMultimap() {
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        for (Collection<V> collection : this.asMap().values()) {
            if (!collection.contains(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        Collection<V> collection = this.asMap().get(key);
        return collection != null && collection.contains(value);
    }

    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        Collection<V> collection = this.asMap().get(key);
        return collection != null && collection.remove(value);
    }

    @Override
    public boolean put(@Nullable K key, @Nullable V value) {
        return this.get(key).add(value);
    }

    @Override
    public boolean putAll(@Nullable K key, Iterable<? extends V> values2) {
        Preconditions.checkNotNull(values2);
        if (values2 instanceof Collection) {
            Collection valueCollection = (Collection)values2;
            return !valueCollection.isEmpty() && this.get(key).addAll(valueCollection);
        }
        Iterator<V> valueItr = values2.iterator();
        return valueItr.hasNext() && Iterators.addAll(this.get(key), valueItr);
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (Map.Entry<K, V> entry : multimap.entries()) {
            changed |= this.put(entry.getKey(), entry.getValue());
        }
        return changed;
    }

    @Override
    public Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values2) {
        Preconditions.checkNotNull(values2);
        Collection result2 = this.removeAll(key);
        this.putAll(key, values2);
        return result2;
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> result2 = this.entries;
        return result2 == null ? (this.entries = this.createEntries()) : result2;
    }

    Collection<Map.Entry<K, V>> createEntries() {
        if (this instanceof SetMultimap) {
            return new EntrySet();
        }
        return new Entries();
    }

    abstract Iterator<Map.Entry<K, V>> entryIterator();

    @Override
    public Set<K> keySet() {
        Set<K> result2 = this.keySet;
        return result2 == null ? (this.keySet = this.createKeySet()) : result2;
    }

    Set<K> createKeySet() {
        return new Maps.KeySet<K, Collection<V>>(this.asMap());
    }

    @Override
    public Multiset<K> keys() {
        Multiset<K> result2 = this.keys;
        return result2 == null ? (this.keys = this.createKeys()) : result2;
    }

    Multiset<K> createKeys() {
        return new Multimaps.Keys(this);
    }

    @Override
    public Collection<V> values() {
        Collection<V> result2 = this.values;
        return result2 == null ? (this.values = this.createValues()) : result2;
    }

    Collection<V> createValues() {
        return new Values();
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entries().iterator());
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<Collection<V>>> result2 = this.asMap;
        return result2 == null ? (this.asMap = this.createAsMap()) : result2;
    }

    abstract Map<K, Collection<V>> createAsMap();

    @Override
    public boolean equals(@Nullable Object object) {
        return Multimaps.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return this.asMap().hashCode();
    }

    public String toString() {
        return this.asMap().toString();
    }

    class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public Iterator<V> iterator() {
            return AbstractMultimap.this.valueIterator();
        }

        @Override
        public int size() {
            return AbstractMultimap.this.size();
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return AbstractMultimap.this.containsValue(o);
        }

        @Override
        public void clear() {
            AbstractMultimap.this.clear();
        }
    }

    private class EntrySet
    extends Entries
    implements Set<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return Sets.equalsImpl(this, obj);
        }
    }

    private class Entries
    extends Multimaps.Entries<K, V> {
        private Entries() {
        }

        @Override
        Multimap<K, V> multimap() {
            return AbstractMultimap.this;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.entryIterator();
        }
    }
}

