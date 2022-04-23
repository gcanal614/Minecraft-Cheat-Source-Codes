/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.pcollections;

import kotlin.reflect.jvm.internal.pcollections.ConsPStack;
import kotlin.reflect.jvm.internal.pcollections.IntTreePMap;
import kotlin.reflect.jvm.internal.pcollections.MapEntry;
import org.jetbrains.annotations.NotNull;

public final class HashPMap<K, V> {
    private static final HashPMap<Object, Object> EMPTY = new HashPMap(IntTreePMap.empty(), 0);
    private final IntTreePMap<ConsPStack<MapEntry<K, V>>> intMap;
    private final int size;

    @NotNull
    public static <K, V> HashPMap<K, V> empty() {
        HashPMap<Object, Object> hashPMap = EMPTY;
        if (hashPMap == null) {
            HashPMap.$$$reportNull$$$0(0);
        }
        return hashPMap;
    }

    private HashPMap(IntTreePMap<ConsPStack<MapEntry<K, V>>> intMap, int size) {
        this.intMap = intMap;
        this.size = size;
    }

    public int size() {
        return this.size;
    }

    public boolean containsKey(Object key) {
        return HashPMap.keyIndexIn(this.getEntries(key.hashCode()), key) != -1;
    }

    public V get(Object key) {
        ConsPStack<MapEntry<K, V>> entries = this.getEntries(key.hashCode());
        while (entries != null && entries.size() > 0) {
            MapEntry entry = (MapEntry)entries.first;
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entries = entries.rest;
        }
        return null;
    }

    @NotNull
    public HashPMap<K, V> plus(K key, V value) {
        ConsPStack<MapEntry<K, V>> entries = this.getEntries(key.hashCode());
        int size0 = entries.size();
        int i = HashPMap.keyIndexIn(entries, key);
        if (i != -1) {
            entries = entries.minus(i);
        }
        entries = entries.plus(new MapEntry<K, V>(key, value));
        return new HashPMap<K, V>(this.intMap.plus(key.hashCode(), entries), this.size - size0 + entries.size());
    }

    @NotNull
    public HashPMap<K, V> minus(Object key) {
        ConsPStack<MapEntry<K, V>> entries = this.getEntries(key.hashCode());
        int i = HashPMap.keyIndexIn(entries, key);
        if (i == -1) {
            HashPMap hashPMap = this;
            if (hashPMap == null) {
                HashPMap.$$$reportNull$$$0(1);
            }
            return hashPMap;
        }
        if ((entries = entries.minus(i)).size() == 0) {
            return new HashPMap<K, V>(this.intMap.minus(key.hashCode()), this.size - 1);
        }
        return new HashPMap<K, V>(this.intMap.plus(key.hashCode(), entries), this.size - 1);
    }

    private ConsPStack<MapEntry<K, V>> getEntries(int hash) {
        ConsPStack<MapEntry<K, V>> entries = this.intMap.get(hash);
        if (entries == null) {
            return ConsPStack.empty();
        }
        return entries;
    }

    private static <K, V> int keyIndexIn(ConsPStack<MapEntry<K, V>> entries, Object key) {
        int i = 0;
        while (entries != null && entries.size() > 0) {
            MapEntry entry = (MapEntry)entries.first;
            if (entry.key.equals(key)) {
                return i;
            }
            entries = entries.rest;
            ++i;
        }
        return -1;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[2];
        objectArray2[0] = "kotlin/reflect/jvm/internal/pcollections/HashPMap";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "empty";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "minus";
                break;
            }
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objectArray));
    }
}

