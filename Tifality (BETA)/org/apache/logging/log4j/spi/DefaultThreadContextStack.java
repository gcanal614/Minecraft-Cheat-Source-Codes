/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.spi.MutableThreadContextStack;
import org.apache.logging.log4j.spi.ThreadContextStack;

public class DefaultThreadContextStack
implements ThreadContextStack {
    private static final long serialVersionUID = 5050501L;
    private static ThreadLocal<List<String>> stack = new ThreadLocal();
    private final boolean useStack;

    public DefaultThreadContextStack(boolean useStack) {
        this.useStack = useStack;
    }

    @Override
    public String pop() {
        if (!this.useStack) {
            return "";
        }
        List<String> list = stack.get();
        if (list == null || list.size() == 0) {
            throw new NoSuchElementException("The ThreadContext stack is empty");
        }
        ArrayList<String> copy2 = new ArrayList<String>(list);
        int last = copy2.size() - 1;
        String result2 = (String)copy2.remove(last);
        stack.set(Collections.unmodifiableList(copy2));
        return result2;
    }

    @Override
    public String peek() {
        List<String> list = stack.get();
        if (list == null || list.size() == 0) {
            return null;
        }
        int last = list.size() - 1;
        return list.get(last);
    }

    @Override
    public void push(String message) {
        if (!this.useStack) {
            return;
        }
        this.add(message);
    }

    @Override
    public int getDepth() {
        List<String> list = stack.get();
        return list == null ? 0 : list.size();
    }

    @Override
    public List<String> asList() {
        List<String> list = stack.get();
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public void trim(int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        List<String> list = stack.get();
        if (list == null) {
            return;
        }
        ArrayList<String> copy2 = new ArrayList<String>();
        int count = Math.min(depth, list.size());
        for (int i = 0; i < count; ++i) {
            copy2.add(list.get(i));
        }
        stack.set(copy2);
    }

    @Override
    public ThreadContextStack copy() {
        List<String> result2 = null;
        if (!this.useStack || (result2 = stack.get()) == null) {
            return new MutableThreadContextStack(new ArrayList<String>());
        }
        return new MutableThreadContextStack(result2);
    }

    @Override
    public void clear() {
        stack.remove();
    }

    @Override
    public int size() {
        List<String> result2 = stack.get();
        return result2 == null ? 0 : result2.size();
    }

    @Override
    public boolean isEmpty() {
        List<String> result2 = stack.get();
        return result2 == null || result2.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        List<String> result2 = stack.get();
        return result2 != null && result2.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        List<String> immutable = stack.get();
        if (immutable == null) {
            List empty = Collections.emptyList();
            return empty.iterator();
        }
        return immutable.iterator();
    }

    @Override
    public Object[] toArray() {
        List<String> result2 = stack.get();
        if (result2 == null) {
            return new String[0];
        }
        return result2.toArray(new Object[result2.size()]);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        List<String> result2 = stack.get();
        if (result2 == null) {
            if (ts.length > 0) {
                ts[0] = null;
            }
            return ts;
        }
        return result2.toArray(ts);
    }

    @Override
    public boolean add(String s) {
        if (!this.useStack) {
            return false;
        }
        List<String> list = stack.get();
        ArrayList<String> copy2 = list == null ? new ArrayList<String>() : new ArrayList<String>(list);
        copy2.add(s);
        stack.set(Collections.unmodifiableList(copy2));
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!this.useStack) {
            return false;
        }
        List<String> list = stack.get();
        if (list == null || list.size() == 0) {
            return false;
        }
        ArrayList<String> copy2 = new ArrayList<String>(list);
        boolean result2 = copy2.remove(o);
        stack.set(Collections.unmodifiableList(copy2));
        return result2;
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        if (objects.isEmpty()) {
            return true;
        }
        List<String> list = stack.get();
        return list != null && list.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends String> strings) {
        if (!this.useStack || strings.isEmpty()) {
            return false;
        }
        List<String> list = stack.get();
        ArrayList<? extends String> copy2 = list == null ? new ArrayList<String>() : new ArrayList<String>(list);
        copy2.addAll(strings);
        stack.set(Collections.unmodifiableList(copy2));
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        if (!this.useStack || objects.isEmpty()) {
            return false;
        }
        List<String> list = stack.get();
        if (list == null || list.isEmpty()) {
            return false;
        }
        ArrayList<String> copy2 = new ArrayList<String>(list);
        boolean result2 = copy2.removeAll(objects);
        stack.set(Collections.unmodifiableList(copy2));
        return result2;
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        if (!this.useStack || objects.isEmpty()) {
            return false;
        }
        List<String> list = stack.get();
        if (list == null || list.isEmpty()) {
            return false;
        }
        ArrayList<String> copy2 = new ArrayList<String>(list);
        boolean result2 = copy2.retainAll(objects);
        stack.set(Collections.unmodifiableList(copy2));
        return result2;
    }

    public String toString() {
        List<String> list = stack.get();
        return list == null ? "[]" : list.toString();
    }
}

