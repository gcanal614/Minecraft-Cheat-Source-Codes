/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;

public class UnmodifiableLazyStringList
extends AbstractList<String>
implements RandomAccess,
LazyStringList {
    private final LazyStringList list;

    public UnmodifiableLazyStringList(LazyStringList list) {
        this.list = list;
    }

    @Override
    public String get(int index) {
        return (String)this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public ByteString getByteString(int index) {
        return this.list.getByteString(index);
    }

    @Override
    public void add(ByteString element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<String> listIterator(final int index) {
        return new ListIterator<String>(){
            ListIterator<String> iter;
            {
                this.iter = UnmodifiableLazyStringList.this.list.listIterator(index);
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public String next() {
                return this.iter.next();
            }

            @Override
            public boolean hasPrevious() {
                return this.iter.hasPrevious();
            }

            @Override
            public String previous() {
                return this.iter.previous();
            }

            @Override
            public int nextIndex() {
                return this.iter.nextIndex();
            }

            @Override
            public int previousIndex() {
                return this.iter.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(String o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(String o) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            Iterator<String> iter;
            {
                this.iter = UnmodifiableLazyStringList.this.list.iterator();
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public String next() {
                return this.iter.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    @Override
    public LazyStringList getUnmodifiableView() {
        return this;
    }
}

