/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.Objects;

public class LongHashMap<V> {
    private transient Entry<V>[] hashArray = new Entry[4096];
    private transient int numHashElements;
    private int mask = this.hashArray.length - 1;
    private int capacity = 3072;
    private final float percentUseable = 0.75f;
    private volatile transient int modCount;

    private static int getHashedKey(long originalKey) {
        return (int)(originalKey ^ originalKey >>> 27);
    }

    private static int hash(int integer) {
        integer = integer ^ integer >>> 20 ^ integer >>> 12;
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }

    private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
        return p_76158_0_ & p_76158_1_;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    public V getValueByKey(long p_76164_1_) {
        int i = LongHashMap.getHashedKey(p_76164_1_);
        Entry<V> entry = this.hashArray[LongHashMap.getHashIndex(i, this.mask)];
        while (entry != null) {
            if (entry.key == p_76164_1_) {
                return entry.value;
            }
            entry = entry.nextEntry;
        }
        return null;
    }

    public boolean containsItem(long p_76161_1_) {
        return this.getEntry(p_76161_1_) != null;
    }

    final Entry<V> getEntry(long p_76160_1_) {
        int i = LongHashMap.getHashedKey(p_76160_1_);
        Entry<V> entry = this.hashArray[LongHashMap.getHashIndex(i, this.mask)];
        while (entry != null) {
            if (entry.key == p_76160_1_) {
                return entry;
            }
            entry = entry.nextEntry;
        }
        return null;
    }

    public void add(long p_76163_1_, V p_76163_3_) {
        int i = LongHashMap.getHashedKey(p_76163_1_);
        int j = LongHashMap.getHashIndex(i, this.mask);
        Entry<V> entry = this.hashArray[j];
        while (entry != null) {
            if (entry.key == p_76163_1_) {
                entry.value = p_76163_3_;
                return;
            }
            entry = entry.nextEntry;
        }
        ++this.modCount;
        this.createKey(i, p_76163_1_, p_76163_3_, j);
    }

    private void resizeTable(int p_76153_1_) {
        Entry<V>[] entry = this.hashArray;
        int i = entry.length;
        if (i == 0x40000000) {
            this.capacity = Integer.MAX_VALUE;
        } else {
            Entry[] entry1 = new Entry[p_76153_1_];
            this.copyHashTableTo(entry1);
            this.hashArray = entry1;
            this.mask = this.hashArray.length - 1;
            float f = p_76153_1_;
            this.getClass();
            this.capacity = (int)(f * 0.75f);
        }
    }

    private void copyHashTableTo(Entry<V>[] p_76154_1_) {
        Entry<V>[] entry = this.hashArray;
        int i = p_76154_1_.length;
        for (int j = 0; j < entry.length; ++j) {
            Entry entry2;
            Entry<V> entry1 = entry[j];
            if (entry1 == null) continue;
            entry[j] = null;
            do {
                entry2 = entry1.nextEntry;
                int k = LongHashMap.getHashIndex(entry1.hash, i - 1);
                entry1.nextEntry = p_76154_1_[k];
                p_76154_1_[k] = entry1;
                entry1 = entry2;
            } while (entry2 != null);
        }
    }

    public void remove(long p_76159_1_) {
        Entry<V> entry = this.removeKey(p_76159_1_);
    }

    final Entry<V> removeKey(long p_76152_1_) {
        Entry<V> entry;
        int i = LongHashMap.getHashedKey(p_76152_1_);
        int j = LongHashMap.getHashIndex(i, this.mask);
        Entry<V> entry1 = entry = this.hashArray[j];
        while (entry1 != null) {
            Entry entry2 = entry1.nextEntry;
            if (entry1.key == p_76152_1_) {
                ++this.modCount;
                --this.numHashElements;
                if (entry == entry1) {
                    this.hashArray[j] = entry2;
                } else {
                    entry.nextEntry = entry2;
                }
                return entry1;
            }
            entry = entry1;
            entry1 = entry2;
        }
        return null;
    }

    private void createKey(int p_76156_1_, long p_76156_2_, V p_76156_4_, int p_76156_5_) {
        Entry<V> entry = this.hashArray[p_76156_5_];
        this.hashArray[p_76156_5_] = new Entry<V>(p_76156_1_, p_76156_2_, p_76156_4_, entry);
        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }

    public double getKeyDistribution() {
        int i = 0;
        for (Entry<V> vEntry : this.hashArray) {
            if (vEntry == null) continue;
            ++i;
        }
        return (double)i / (double)this.numHashElements;
    }

    static class Entry<V> {
        final long key;
        V value;
        Entry<V> nextEntry;
        final int hash;

        Entry(int p_i1553_1_, long p_i1553_2_, V p_i1553_4_, Entry<V> p_i1553_5_) {
            this.value = p_i1553_4_;
            this.nextEntry = p_i1553_5_;
            this.key = p_i1553_2_;
            this.hash = p_i1553_1_;
        }

        public final long getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final boolean equals(Object p_equals_1_) {
            if (p_equals_1_ instanceof Entry) {
                Long object1;
                Entry entry = (Entry)p_equals_1_;
                Long object = this.getKey();
                if (Objects.equals(object, object1 = Long.valueOf(entry.getKey()))) {
                    V object2 = this.getValue();
                    V object3 = entry.getValue();
                    return Objects.equals(object2, object3);
                }
            }
            return false;
        }

        public final int hashCode() {
            return LongHashMap.getHashedKey(this.key);
        }

        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
}

