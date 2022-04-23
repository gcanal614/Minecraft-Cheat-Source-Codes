/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jetbrains.annotations.NotNull;

public class SmartList<E>
extends AbstractList<E>
implements RandomAccess {
    private int mySize;
    private Object myElem;

    @Override
    public E get(int index) {
        if (index < 0 || index >= this.mySize) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.mySize);
        }
        if (this.mySize == 1) {
            return (E)this.myElem;
        }
        return (E)((Object[])this.myElem)[index];
    }

    @Override
    public boolean add(E e) {
        if (this.mySize == 0) {
            this.myElem = e;
        } else if (this.mySize == 1) {
            Object[] array = new Object[]{this.myElem, e};
            this.myElem = array;
        } else {
            Object[] array = (Object[])this.myElem;
            int oldCapacity = array.length;
            if (this.mySize >= oldCapacity) {
                int newCapacity = oldCapacity * 3 / 2 + 1;
                int minCapacity = this.mySize + 1;
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity;
                }
                Object[] oldArray = array;
                array = new Object[newCapacity];
                this.myElem = array;
                System.arraycopy(oldArray, 0, array, 0, oldCapacity);
            }
            array[this.mySize] = e;
        }
        ++this.mySize;
        ++this.modCount;
        return true;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > this.mySize) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.mySize);
        }
        if (this.mySize == 0) {
            this.myElem = e;
        } else if (this.mySize == 1 && index == 0) {
            Object[] array = new Object[]{e, this.myElem};
            this.myElem = array;
        } else {
            Object[] array = new Object[this.mySize + 1];
            if (this.mySize == 1) {
                array[0] = this.myElem;
            } else {
                Object[] oldArray = (Object[])this.myElem;
                System.arraycopy(oldArray, 0, array, 0, index);
                System.arraycopy(oldArray, index, array, index + 1, this.mySize - index);
            }
            array[index] = e;
            this.myElem = array;
        }
        ++this.mySize;
        ++this.modCount;
    }

    @Override
    public int size() {
        return this.mySize;
    }

    @Override
    public void clear() {
        this.myElem = null;
        this.mySize = 0;
        ++this.modCount;
    }

    @Override
    public E set(int index, E element) {
        Object oldValue;
        if (index < 0 || index >= this.mySize) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.mySize);
        }
        if (this.mySize == 1) {
            oldValue = this.myElem;
            this.myElem = element;
        } else {
            Object[] array = (Object[])this.myElem;
            oldValue = array[index];
            array[index] = element;
        }
        return (E)oldValue;
    }

    @Override
    public E remove(int index) {
        Object oldValue;
        if (index < 0 || index >= this.mySize) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.mySize);
        }
        if (this.mySize == 1) {
            oldValue = this.myElem;
            this.myElem = null;
        } else {
            Object[] array = (Object[])this.myElem;
            oldValue = array[index];
            if (this.mySize == 2) {
                this.myElem = array[1 - index];
            } else {
                int numMoved = this.mySize - index - 1;
                if (numMoved > 0) {
                    System.arraycopy(array, index + 1, array, index, numMoved);
                }
                array[this.mySize - 1] = null;
            }
        }
        --this.mySize;
        ++this.modCount;
        return (E)oldValue;
    }

    @Override
    @NotNull
    public Iterator<E> iterator() {
        if (this.mySize == 0) {
            EmptyIterator emptyIterator = EmptyIterator.getInstance();
            if (emptyIterator == null) {
                SmartList.$$$reportNull$$$0(2);
            }
            return emptyIterator;
        }
        if (this.mySize == 1) {
            return new SingletonIterator();
        }
        Iterator iterator2 = super.iterator();
        if (iterator2 == null) {
            SmartList.$$$reportNull$$$0(3);
        }
        return iterator2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    @NotNull
    public <T> T[] toArray(@NotNull T[] a2) {
        int aLength;
        block10: {
            if (a2 == null) {
                SmartList.$$$reportNull$$$0(4);
            }
            aLength = a2.length;
            if (this.mySize == 1) {
                if (aLength != 0) {
                    a2[0] = this.myElem;
                    break block10;
                } else {
                    Object[] r = (Object[])Array.newInstance(a2.getClass().getComponentType(), 1);
                    r[0] = this.myElem;
                    if (r == null) {
                        SmartList.$$$reportNull$$$0(5);
                    }
                    return r;
                }
            }
            if (aLength < this.mySize) {
                T[] TArray = Arrays.copyOf((Object[])this.myElem, this.mySize, a2.getClass());
                if (TArray == null) {
                    SmartList.$$$reportNull$$$0(6);
                }
                return TArray;
            }
            if (this.mySize != 0) {
                System.arraycopy(this.myElem, 0, a2, 0, this.mySize);
            }
        }
        if (aLength > this.mySize) {
            a2[this.mySize] = null;
        }
        if (a2 == null) {
            SmartList.$$$reportNull$$$0(7);
        }
        return a2;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 7: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 7: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "elements";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/utils/SmartList";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "a";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/utils/SmartList";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "iterator";
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "toArray";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 7: {
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "toArray";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 7: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private class SingletonIterator
    extends SingletonIteratorBase<E> {
        private final int myInitialModCount;

        public SingletonIterator() {
            this.myInitialModCount = SmartList.this.modCount;
        }

        @Override
        protected E getElement() {
            return SmartList.this.myElem;
        }

        @Override
        protected void checkCoModification() {
            if (SmartList.this.modCount != this.myInitialModCount) {
                throw new ConcurrentModificationException("ModCount: " + SmartList.this.modCount + "; expected: " + this.myInitialModCount);
            }
        }

        @Override
        public void remove() {
            this.checkCoModification();
            SmartList.this.clear();
        }
    }

    private static abstract class SingletonIteratorBase<T>
    implements Iterator<T> {
        private boolean myVisited;

        private SingletonIteratorBase() {
        }

        @Override
        public final boolean hasNext() {
            return !this.myVisited;
        }

        @Override
        public final T next() {
            if (this.myVisited) {
                throw new NoSuchElementException();
            }
            this.myVisited = true;
            this.checkCoModification();
            return this.getElement();
        }

        protected abstract void checkCoModification();

        protected abstract T getElement();
    }

    private static class EmptyIterator<T>
    implements Iterator<T> {
        private static final EmptyIterator INSTANCE = new EmptyIterator();

        private EmptyIterator() {
        }

        public static <T> EmptyIterator<T> getInstance() {
            return INSTANCE;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
    }
}

