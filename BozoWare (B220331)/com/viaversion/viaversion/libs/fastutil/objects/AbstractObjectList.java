// 
// Decompiled by Procyon v0.5.36
// 

package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.ListIterator;
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Collection;
import com.viaversion.viaversion.libs.fastutil.Stack;

public abstract class AbstractObjectList<K> extends AbstractObjectCollection<K> implements ObjectList<K>, Stack<K>
{
    protected AbstractObjectList() {
    }
    
    protected void ensureIndex(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
        }
    }
    
    protected void ensureRestrictedIndex(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }
    
    @Override
    public void add(final int index, final K k) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean add(final K k) {
        this.add(this.size(), k);
        return true;
    }
    
    @Override
    public K remove(final int i) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public K set(final int index, final K k) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(int index, final Collection<? extends K> c) {
        this.ensureIndex(index);
        final Iterator<? extends K> i = c.iterator();
        final boolean retVal = i.hasNext();
        while (i.hasNext()) {
            this.add(index++, i.next());
        }
        return retVal;
    }
    
    @Override
    public boolean addAll(final Collection<? extends K> c) {
        return this.addAll(this.size(), c);
    }
    
    @Override
    public ObjectListIterator<K> iterator() {
        return this.listIterator();
    }
    
    @Override
    public ObjectListIterator<K> listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public ObjectListIterator<K> listIterator(final int index) {
        this.ensureIndex(index);
        return new ObjectListIterator<K>() {
            int pos = index;
            int last = -1;
            
            @Override
            public boolean hasNext() {
                return this.pos < AbstractObjectList.this.size();
            }
            
            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }
            
            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final AbstractObjectList this$0 = AbstractObjectList.this;
                final int last = this.pos++;
                this.last = last;
                return this$0.get(last);
            }
            
            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final AbstractObjectList this$0 = AbstractObjectList.this;
                final int n = this.pos - 1;
                this.pos = n;
                this.last = n;
                return this$0.get(n);
            }
            
            @Override
            public int nextIndex() {
                return this.pos;
            }
            
            @Override
            public int previousIndex() {
                return this.pos - 1;
            }
            
            @Override
            public void add(final K k) {
                AbstractObjectList.this.add(this.pos++, k);
                this.last = -1;
            }
            
            @Override
            public void set(final K k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractObjectList.this.set(this.last, k);
            }
            
            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractObjectList.this.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }
    
    @Override
    public boolean contains(final Object k) {
        return this.indexOf(k) >= 0;
    }
    
    @Override
    public int indexOf(final Object k) {
        final ObjectListIterator<K> i = this.listIterator();
        while (i.hasNext()) {
            final K e = i.next();
            if (Objects.equals(k, e)) {
                return i.previousIndex();
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object k) {
        final ObjectListIterator<K> i = this.listIterator(this.size());
        while (i.hasPrevious()) {
            final K e = i.previous();
            if (Objects.equals(k, e)) {
                return i.nextIndex();
            }
        }
        return -1;
    }
    
    @Override
    public void size(final int size) {
        int i = this.size();
        if (size > i) {
            while (i++ < size) {
                this.add(null);
            }
        }
        else {
            while (i-- != size) {
                this.remove(i);
            }
        }
    }
    
    @Override
    public ObjectList<K> subList(final int from, final int to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ObjectSubList<K>(this, from, to);
    }
    
    @Override
    public void removeElements(final int from, final int to) {
        this.ensureIndex(to);
        final ObjectListIterator<K> i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.next();
            i.remove();
        }
    }
    
    @Override
    public void addElements(int index, final K[] a, int offset, int length) {
        this.ensureIndex(index);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        while (length-- != 0) {
            this.add(index++, a[offset++]);
        }
    }
    
    @Override
    public void addElements(final int index, final K[] a) {
        this.addElements(index, a, 0, a.length);
    }
    
    @Override
    public void getElements(final int from, final Object[] a, int offset, int length) {
        final ObjectListIterator<K> i = this.listIterator(from);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
        }
        while (length-- != 0) {
            a[offset++] = i.next();
        }
    }
    
    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }
    
    private boolean valEquals(final Object a, final Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
    
    @Override
    public int hashCode() {
        final ObjectIterator<K> i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
            final K k = i.next();
            h = 31 * h + ((k == null) ? 0 : k.hashCode());
        }
        return h;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final List<?> l = (List<?>)o;
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        final ListIterator<?> i1 = this.listIterator();
        final ListIterator<?> i2 = l.listIterator();
        while (s-- != 0) {
            if (!this.valEquals(i1.next(), i2.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int compareTo(final List<? extends K> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof ObjectList) {
            final ObjectListIterator<K> i1 = this.listIterator();
            final ObjectListIterator<K> i2 = ((ObjectList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                final K e1 = i1.next();
                final K e2 = i2.next();
                final int r;
                if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
                    return r;
                }
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        final ListIterator<? extends K> i3 = (ListIterator<? extends K>)this.listIterator();
        final ListIterator<? extends K> i4 = l.listIterator();
        while (i3.hasNext() && i4.hasNext()) {
            final int r;
            if ((r = ((Comparable)i3.next()).compareTo(i4.next())) != 0) {
                return r;
            }
        }
        return i4.hasNext() ? -1 : (i3.hasNext() ? 1 : 0);
    }
    
    @Override
    public void push(final K o) {
        this.add(o);
    }
    
    @Override
    public K pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(this.size() - 1);
    }
    
    @Override
    public K top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.get(this.size() - 1);
    }
    
    @Override
    public K peek(final int i) {
        return this.get(this.size() - 1 - i);
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        final ObjectIterator<K> i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (n-- != 0) {
            if (first) {
                first = false;
            }
            else {
                s.append(", ");
            }
            final K k = i.next();
            if (this == k) {
                s.append("(this list)");
            }
            else {
                s.append(String.valueOf(k));
            }
        }
        s.append("]");
        return s.toString();
    }
    
    public static class ObjectSubList<K> extends AbstractObjectList<K> implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList<K> l;
        protected final int from;
        protected int to;
        
        public ObjectSubList(final ObjectList<K> l, final int from, final int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }
        
        private boolean assertRange() {
            assert this.from <= this.l.size();
            assert this.to <= this.l.size();
            assert this.to >= this.from;
            return true;
        }
        
        @Override
        public boolean add(final K k) {
            this.l.add(this.to, k);
            ++this.to;
            assert this.assertRange();
            return true;
        }
        
        @Override
        public void add(final int index, final K k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
            assert this.assertRange();
        }
        
        @Override
        public boolean addAll(final int index, final Collection<? extends K> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, (Collection<?>)c);
        }
        
        @Override
        public K get(final int index) {
            this.ensureRestrictedIndex(index);
            return this.l.get(this.from + index);
        }
        
        @Override
        public K remove(final int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.remove(this.from + index);
        }
        
        @Override
        public K set(final int index, final K k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }
        
        @Override
        public int size() {
            return this.to - this.from;
        }
        
        @Override
        public void getElements(final int from, final Object[] a, final int offset, final int length) {
            this.ensureIndex(from);
            if (from + length > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }
        
        @Override
        public void removeElements(final int from, final int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
            assert this.assertRange();
        }
        
        @Override
        public void addElements(final int index, final K[] a, final int offset, final int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
            assert this.assertRange();
        }
        
        @Override
        public ObjectListIterator<K> listIterator(final int index) {
            this.ensureIndex(index);
            return new ObjectListIterator<K>() {
                int pos = index;
                int last = -1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < ObjectSubList.this.size();
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.pos > 0;
                }
                
                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final ObjectList<K> l = ObjectSubList.this.l;
                    final int from = ObjectSubList.this.from;
                    final int last = this.pos++;
                    this.last = last;
                    return (K)l.get(from + last);
                }
                
                @Override
                public K previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    final ObjectList<K> l = ObjectSubList.this.l;
                    final int from = ObjectSubList.this.from;
                    final int n = this.pos - 1;
                    this.pos = n;
                    this.last = n;
                    return (K)l.get(from + n);
                }
                
                @Override
                public int nextIndex() {
                    return this.pos;
                }
                
                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }
                
                @Override
                public void add(final K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.add(this.pos++, k);
                    this.last = -1;
                    assert ObjectSubList.this.assertRange();
                }
                
                @Override
                public void set(final K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.set(this.last, k);
                }
                
                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.remove(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    assert ObjectSubList.this.assertRange();
                }
            };
        }
        
        @Override
        public ObjectList<K> subList(final int from, final int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ObjectSubList((ObjectList<Object>)this, from, to);
        }
    }
}
