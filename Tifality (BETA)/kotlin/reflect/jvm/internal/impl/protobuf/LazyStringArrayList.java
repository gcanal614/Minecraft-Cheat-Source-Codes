/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;
import kotlin.reflect.jvm.internal.impl.protobuf.UnmodifiableLazyStringList;

public class LazyStringArrayList
extends AbstractList<String>
implements RandomAccess,
LazyStringList {
    public static final LazyStringList EMPTY = new LazyStringArrayList().getUnmodifiableView();
    private final List<Object> list;

    public LazyStringArrayList() {
        this.list = new ArrayList<Object>();
    }

    public LazyStringArrayList(LazyStringList from) {
        this.list = new ArrayList<Object>(from.size());
        this.addAll(from);
    }

    @Override
    public String get(int index) {
        Object o = this.list.get(index);
        if (o instanceof String) {
            return (String)o;
        }
        if (o instanceof ByteString) {
            ByteString bs = (ByteString)o;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.list.set(index, s);
            }
            return s;
        }
        byte[] ba = (byte[])o;
        String s = Internal.toStringUtf8(ba);
        if (Internal.isValidUtf8(ba)) {
            this.list.set(index, s);
        }
        return s;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public String set(int index, String s) {
        Object o = this.list.set(index, s);
        return LazyStringArrayList.asString(o);
    }

    @Override
    public void add(int index, String element) {
        this.list.add(index, element);
        ++this.modCount;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        Collection<? extends String> collection = c instanceof LazyStringList ? ((LazyStringList)c).getUnderlyingElements() : c;
        boolean ret = this.list.addAll(index, collection);
        ++this.modCount;
        return ret;
    }

    @Override
    public String remove(int index) {
        Object o = this.list.remove(index);
        ++this.modCount;
        return LazyStringArrayList.asString(o);
    }

    @Override
    public void clear() {
        this.list.clear();
        ++this.modCount;
    }

    @Override
    public void add(ByteString element) {
        this.list.add(element);
        ++this.modCount;
    }

    @Override
    public ByteString getByteString(int index) {
        Object o = this.list.get(index);
        ByteString b2 = LazyStringArrayList.asByteString(o);
        if (b2 != o) {
            this.list.set(index, b2);
        }
        return b2;
    }

    private static String asString(Object o) {
        if (o instanceof String) {
            return (String)o;
        }
        if (o instanceof ByteString) {
            return ((ByteString)o).toStringUtf8();
        }
        return Internal.toStringUtf8((byte[])o);
    }

    private static ByteString asByteString(Object o) {
        if (o instanceof ByteString) {
            return (ByteString)o;
        }
        if (o instanceof String) {
            return ByteString.copyFromUtf8((String)o);
        }
        return ByteString.copyFrom((byte[])o);
    }

    @Override
    public List<?> getUnderlyingElements() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public LazyStringList getUnmodifiableView() {
        return new UnmodifiableLazyStringList(this);
    }
}

