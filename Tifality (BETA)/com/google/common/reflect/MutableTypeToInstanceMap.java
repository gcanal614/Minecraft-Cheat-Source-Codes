/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToInstanceMap;
import com.google.common.reflect.TypeToken;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class MutableTypeToInstanceMap<B>
extends ForwardingMap<TypeToken<? extends B>, B>
implements TypeToInstanceMap<B> {
    private final Map<TypeToken<? extends B>, B> backingMap = Maps.newHashMap();

    @Override
    @Nullable
    public <T extends B> T getInstance(Class<T> type2) {
        return this.trustedGet(TypeToken.of(type2));
    }

    @Override
    @Nullable
    public <T extends B> T putInstance(Class<T> type2, @Nullable T value) {
        return this.trustedPut(TypeToken.of(type2), value);
    }

    @Override
    @Nullable
    public <T extends B> T getInstance(TypeToken<T> type2) {
        return this.trustedGet(type2.rejectTypeVariables());
    }

    @Override
    @Nullable
    public <T extends B> T putInstance(TypeToken<T> type2, @Nullable T value) {
        return this.trustedPut(type2.rejectTypeVariables(), value);
    }

    @Override
    public B put(TypeToken<? extends B> key, B value) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override
    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map2) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override
    public Set<Map.Entry<TypeToken<? extends B>, B>> entrySet() {
        return UnmodifiableEntry.transformEntries(super.entrySet());
    }

    @Override
    protected Map<TypeToken<? extends B>, B> delegate() {
        return this.backingMap;
    }

    @Nullable
    private <T extends B> T trustedPut(TypeToken<T> type2, @Nullable T value) {
        return (T)this.backingMap.put(type2, value);
    }

    @Nullable
    private <T extends B> T trustedGet(TypeToken<T> type2) {
        return (T)this.backingMap.get(type2);
    }

    private static final class UnmodifiableEntry<K, V>
    extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;

        static <K, V> Set<Map.Entry<K, V>> transformEntries(final Set<Map.Entry<K, V>> entries) {
            return new ForwardingSet<Map.Entry<K, V>>(){

                @Override
                protected Set<Map.Entry<K, V>> delegate() {
                    return entries;
                }

                @Override
                public Iterator<Map.Entry<K, V>> iterator() {
                    return UnmodifiableEntry.transformEntries(super.iterator());
                }

                @Override
                public Object[] toArray() {
                    return this.standardToArray();
                }

                @Override
                public <T> T[] toArray(T[] array) {
                    return this.standardToArray(array);
                }
            };
        }

        private static <K, V> Iterator<Map.Entry<K, V>> transformEntries(Iterator<Map.Entry<K, V>> entries) {
            return Iterators.transform(entries, new Function<Map.Entry<K, V>, Map.Entry<K, V>>(){

                @Override
                public Map.Entry<K, V> apply(Map.Entry<K, V> entry) {
                    return new UnmodifiableEntry(entry);
                }
            });
        }

        private UnmodifiableEntry(Map.Entry<K, V> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }

        @Override
        protected Map.Entry<K, V> delegate() {
            return this.delegate;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }
}

