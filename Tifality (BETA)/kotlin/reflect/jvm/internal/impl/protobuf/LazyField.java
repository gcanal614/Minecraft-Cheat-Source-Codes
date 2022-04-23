/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.Iterator;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyFieldLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;

public class LazyField
extends LazyFieldLite {
    private final MessageLite defaultInstance;

    public MessageLite getValue() {
        return this.getValue(this.defaultInstance);
    }

    public int hashCode() {
        return this.getValue().hashCode();
    }

    public boolean equals(Object obj) {
        return this.getValue().equals(obj);
    }

    public String toString() {
        return this.getValue().toString();
    }

    static class LazyIterator<K>
    implements Iterator<Map.Entry<K, Object>> {
        private Iterator<Map.Entry<K, Object>> iterator;

        public LazyIterator(Iterator<Map.Entry<K, Object>> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Map.Entry<K, Object> next() {
            Map.Entry<K, Object> entry = this.iterator.next();
            if (entry.getValue() instanceof LazyField) {
                return new LazyEntry(entry);
            }
            return entry;
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

    static class LazyEntry<K>
    implements Map.Entry<K, Object> {
        private Map.Entry<K, LazyField> entry;

        private LazyEntry(Map.Entry<K, LazyField> entry) {
            this.entry = entry;
        }

        @Override
        public K getKey() {
            return this.entry.getKey();
        }

        @Override
        public Object getValue() {
            LazyField field = this.entry.getValue();
            if (field == null) {
                return null;
            }
            return field.getValue();
        }

        @Override
        public Object setValue(Object value) {
            if (!(value instanceof MessageLite)) {
                throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
            }
            return this.entry.getValue().setValue((MessageLite)value);
        }
    }
}

