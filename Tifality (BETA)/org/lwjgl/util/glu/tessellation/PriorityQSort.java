/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.glu.tessellation;

import org.lwjgl.util.glu.tessellation.PriorityQ;
import org.lwjgl.util.glu.tessellation.PriorityQHeap;

class PriorityQSort
extends PriorityQ {
    PriorityQHeap heap;
    Object[] keys;
    int[] order;
    int size;
    int max;
    boolean initialized;
    PriorityQ.Leq leq;

    PriorityQSort(PriorityQ.Leq leq) {
        this.heap = new PriorityQHeap(leq);
        this.keys = new Object[32];
        this.size = 0;
        this.max = 32;
        this.initialized = false;
        this.leq = leq;
    }

    void pqDeletePriorityQ() {
        if (this.heap != null) {
            this.heap.pqDeletePriorityQ();
        }
        this.order = null;
        this.keys = null;
    }

    private static boolean LT(PriorityQ.Leq leq, Object x, Object y) {
        return !PriorityQHeap.LEQ(leq, y, x);
    }

    private static boolean GT(PriorityQ.Leq leq, Object x, Object y) {
        return !PriorityQHeap.LEQ(leq, x, y);
    }

    private static void Swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    boolean pqInit() {
        int i;
        Stack[] stack = new Stack[50];
        for (int k = 0; k < stack.length; ++k) {
            stack[k] = new Stack();
        }
        int top = 0;
        int seed = 2016473283;
        this.order = new int[this.size + 1];
        int p = 0;
        int r = this.size - 1;
        int piv = 0;
        for (i = p; i <= r; ++i) {
            this.order[i] = piv++;
        }
        stack[top].p = p;
        stack[top].r = r;
        ++top;
        while (--top >= 0) {
            int j;
            p = stack[top].p;
            r = stack[top].r;
            while (r > p + 10) {
                seed = Math.abs(seed * 1539415821 + 1);
                i = p + seed % (r - p + 1);
                piv = this.order[i];
                this.order[i] = this.order[p];
                this.order[p] = piv;
                i = p - 1;
                j = r + 1;
                while (true) {
                    if (PriorityQSort.GT(this.leq, this.keys[this.order[++i]], this.keys[piv])) {
                        continue;
                    }
                    while (PriorityQSort.LT(this.leq, this.keys[this.order[--j]], this.keys[piv])) {
                    }
                    PriorityQSort.Swap(this.order, i, j);
                    if (i >= j) break;
                }
                PriorityQSort.Swap(this.order, i, j);
                if (i - p < r - j) {
                    stack[top].p = j + 1;
                    stack[top].r = r;
                    ++top;
                    r = i - 1;
                    continue;
                }
                stack[top].p = p;
                stack[top].r = i - 1;
                ++top;
                p = j + 1;
            }
            for (i = p + 1; i <= r; ++i) {
                piv = this.order[i];
                for (j = i; j > p && PriorityQSort.LT(this.leq, this.keys[this.order[j - 1]], this.keys[piv]); --j) {
                    this.order[j] = this.order[j - 1];
                }
                this.order[j] = piv;
            }
        }
        this.max = this.size;
        this.initialized = true;
        this.heap.pqInit();
        return true;
    }

    int pqInsert(Object keyNew) {
        if (this.initialized) {
            return this.heap.pqInsert(keyNew);
        }
        int curr = this.size++;
        if (this.size >= this.max) {
            Object[] saveKey = this.keys;
            this.max <<= 1;
            Object[] pqKeys = new Object[this.max];
            System.arraycopy(this.keys, 0, pqKeys, 0, this.keys.length);
            this.keys = pqKeys;
            if (this.keys == null) {
                this.keys = saveKey;
                return Integer.MAX_VALUE;
            }
        }
        assert (curr != Integer.MAX_VALUE);
        this.keys[curr] = keyNew;
        return -(curr + 1);
    }

    Object pqExtractMin() {
        Object heapMin;
        if (this.size == 0) {
            return this.heap.pqExtractMin();
        }
        Object sortMin = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty() && PriorityQSort.LEQ(this.leq, heapMin = this.heap.pqMinimum(), sortMin)) {
            return this.heap.pqExtractMin();
        }
        do {
            --this.size;
        } while (this.size > 0 && this.keys[this.order[this.size - 1]] == null);
        return sortMin;
    }

    Object pqMinimum() {
        Object heapMin;
        if (this.size == 0) {
            return this.heap.pqMinimum();
        }
        Object sortMin = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty() && PriorityQHeap.LEQ(this.leq, heapMin = this.heap.pqMinimum(), sortMin)) {
            return heapMin;
        }
        return sortMin;
    }

    boolean pqIsEmpty() {
        return this.size == 0 && this.heap.pqIsEmpty();
    }

    void pqDelete(int curr) {
        if (curr >= 0) {
            this.heap.pqDelete(curr);
            return;
        }
        curr = -(curr + 1);
        assert (curr < this.max && this.keys[curr] != null);
        this.keys[curr] = null;
        while (this.size > 0 && this.keys[this.order[this.size - 1]] == null) {
            --this.size;
        }
    }

    private static class Stack {
        int p;
        int r;

        private Stack() {
        }
    }
}

