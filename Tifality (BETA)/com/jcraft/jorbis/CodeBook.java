/*
 * Decompiled with CFR 0.152.
 */
package com.jcraft.jorbis;

import com.jcraft.jogg.Buffer;
import com.jcraft.jorbis.StaticCodeBook;
import com.jcraft.jorbis.Util;

class CodeBook {
    int dim;
    int entries;
    StaticCodeBook c = new StaticCodeBook();
    float[] valuelist;
    int[] codelist;
    DecodeAux decode_tree;
    private int[] t = new int[15];

    CodeBook() {
    }

    int encode(int a2, Buffer b2) {
        b2.write(this.codelist[a2], this.c.lengthlist[a2]);
        return this.c.lengthlist[a2];
    }

    int errorv(float[] a2) {
        int best = this.best(a2, 1);
        for (int k = 0; k < this.dim; ++k) {
            a2[k] = this.valuelist[best * this.dim + k];
        }
        return best;
    }

    int encodev(int best, float[] a2, Buffer b2) {
        for (int k = 0; k < this.dim; ++k) {
            a2[k] = this.valuelist[best * this.dim + k];
        }
        return this.encode(best, b2);
    }

    int encodevs(float[] a2, Buffer b2, int step, int addmul) {
        int best = this.besterror(a2, step, addmul);
        return this.encode(best, b2);
    }

    synchronized int decodevs_add(float[] a2, int offset, Buffer b2, int n) {
        int i;
        int step = n / this.dim;
        if (this.t.length < step) {
            this.t = new int[step];
        }
        for (i = 0; i < step; ++i) {
            int entry = this.decode(b2);
            if (entry == -1) {
                return -1;
            }
            this.t[i] = entry * this.dim;
        }
        i = 0;
        int o = 0;
        while (i < this.dim) {
            for (int j = 0; j < step; ++j) {
                int n2 = offset + o + j;
                a2[n2] = a2[n2] + this.valuelist[this.t[j] + i];
            }
            ++i;
            o += step;
        }
        return 0;
    }

    int decodev_add(float[] a2, int offset, Buffer b2, int n) {
        if (this.dim > 8) {
            int i = 0;
            while (i < n) {
                int entry = this.decode(b2);
                if (entry == -1) {
                    return -1;
                }
                int t = entry * this.dim;
                int j = 0;
                while (j < this.dim) {
                    int n2 = offset + i++;
                    a2[n2] = a2[n2] + this.valuelist[t + j++];
                }
            }
        } else {
            int i = 0;
            while (i < n) {
                int entry = this.decode(b2);
                if (entry == -1) {
                    return -1;
                }
                int t = entry * this.dim;
                int j = 0;
                switch (this.dim) {
                    case 8: {
                        int n3 = offset + i++;
                        a2[n3] = a2[n3] + this.valuelist[t + j++];
                    }
                    case 7: {
                        int n4 = offset + i++;
                        a2[n4] = a2[n4] + this.valuelist[t + j++];
                    }
                    case 6: {
                        int n5 = offset + i++;
                        a2[n5] = a2[n5] + this.valuelist[t + j++];
                    }
                    case 5: {
                        int n6 = offset + i++;
                        a2[n6] = a2[n6] + this.valuelist[t + j++];
                    }
                    case 4: {
                        int n7 = offset + i++;
                        a2[n7] = a2[n7] + this.valuelist[t + j++];
                    }
                    case 3: {
                        int n8 = offset + i++;
                        a2[n8] = a2[n8] + this.valuelist[t + j++];
                    }
                    case 2: {
                        int n9 = offset + i++;
                        a2[n9] = a2[n9] + this.valuelist[t + j++];
                    }
                    case 1: {
                        int n10 = offset + i++;
                        a2[n10] = a2[n10] + this.valuelist[t + j++];
                    }
                }
            }
        }
        return 0;
    }

    int decodev_set(float[] a2, int offset, Buffer b2, int n) {
        int i = 0;
        while (i < n) {
            int entry = this.decode(b2);
            if (entry == -1) {
                return -1;
            }
            int t = entry * this.dim;
            int j = 0;
            while (j < this.dim) {
                a2[offset + i++] = this.valuelist[t + j++];
            }
        }
        return 0;
    }

    int decodevv_add(float[][] a2, int offset, int ch, Buffer b2, int n) {
        int chptr = 0;
        int i = offset / ch;
        while (i < (offset + n) / ch) {
            int entry = this.decode(b2);
            if (entry == -1) {
                return -1;
            }
            int t = entry * this.dim;
            for (int j = 0; j < this.dim; ++j) {
                float[] fArray = a2[chptr++];
                int n2 = i++;
                fArray[n2] = fArray[n2] + this.valuelist[t + j];
                if (chptr != ch) continue;
                chptr = 0;
            }
        }
        return 0;
    }

    int decode(Buffer b2) {
        int ptr = 0;
        DecodeAux t = this.decode_tree;
        int lok = b2.look(t.tabn);
        if (lok >= 0) {
            ptr = t.tab[lok];
            b2.adv(t.tabl[lok]);
            if (ptr <= 0) {
                return -ptr;
            }
        }
        do {
            switch (b2.read1()) {
                case 0: {
                    ptr = t.ptr0[ptr];
                    break;
                }
                case 1: {
                    ptr = t.ptr1[ptr];
                    break;
                }
                default: {
                    return -1;
                }
            }
        } while (ptr > 0);
        return -ptr;
    }

    int decodevs(float[] a2, int index, Buffer b2, int step, int addmul) {
        int entry = this.decode(b2);
        if (entry == -1) {
            return -1;
        }
        switch (addmul) {
            case -1: {
                int i = 0;
                int o = 0;
                while (i < this.dim) {
                    a2[index + o] = this.valuelist[entry * this.dim + i];
                    ++i;
                    o += step;
                }
                break;
            }
            case 0: {
                int i = 0;
                int o = 0;
                while (i < this.dim) {
                    int n = index + o;
                    a2[n] = a2[n] + this.valuelist[entry * this.dim + i];
                    ++i;
                    o += step;
                }
                break;
            }
            case 1: {
                int i = 0;
                int o = 0;
                while (i < this.dim) {
                    int n = index + o;
                    a2[n] = a2[n] * this.valuelist[entry * this.dim + i];
                    ++i;
                    o += step;
                }
                break;
            }
        }
        return entry;
    }

    int best(float[] a2, int step) {
        int besti = -1;
        float best = 0.0f;
        int e = 0;
        for (int i = 0; i < this.entries; ++i) {
            if (this.c.lengthlist[i] > 0) {
                float _this = CodeBook.dist(this.dim, this.valuelist, e, a2, step);
                if (besti == -1 || _this < best) {
                    best = _this;
                    besti = i;
                }
            }
            e += this.dim;
        }
        return besti;
    }

    int besterror(float[] a2, int step, int addmul) {
        int best = this.best(a2, step);
        switch (addmul) {
            case 0: {
                int i = 0;
                int o = 0;
                while (i < this.dim) {
                    int n = o;
                    a2[n] = a2[n] - this.valuelist[best * this.dim + i];
                    ++i;
                    o += step;
                }
                break;
            }
            case 1: {
                int i = 0;
                int o = 0;
                while (i < this.dim) {
                    float val = this.valuelist[best * this.dim + i];
                    if (val == 0.0f) {
                        a2[o] = 0.0f;
                    } else {
                        int n = o;
                        a2[n] = a2[n] / val;
                    }
                    ++i;
                    o += step;
                }
                break;
            }
        }
        return best;
    }

    void clear() {
    }

    private static float dist(int el2, float[] ref, int index, float[] b2, int step) {
        float acc = 0.0f;
        for (int i = 0; i < el2; ++i) {
            float val = ref[index + i] - b2[i * step];
            acc += val * val;
        }
        return acc;
    }

    int init_decode(StaticCodeBook s) {
        this.c = s;
        this.entries = s.entries;
        this.dim = s.dim;
        this.valuelist = s.unquantize();
        this.decode_tree = this.make_decode_tree();
        if (this.decode_tree == null) {
            this.clear();
            return -1;
        }
        return 0;
    }

    static int[] make_words(int[] l, int n) {
        int i;
        int[] marker = new int[33];
        int[] r = new int[n];
        for (i = 0; i < n; ++i) {
            int length = l[i];
            if (length <= 0) continue;
            int entry = marker[length];
            if (length < 32 && entry >>> length != 0) {
                return null;
            }
            r[i] = entry;
            int j = length;
            while (j > 0) {
                if ((marker[j] & 1) != 0) {
                    if (j == 1) {
                        marker[1] = marker[1] + 1;
                        break;
                    }
                    marker[j] = marker[j - 1] << 1;
                    break;
                }
                int n2 = j--;
                marker[n2] = marker[n2] + 1;
            }
            for (j = length + 1; j < 33 && marker[j] >>> 1 == entry; ++j) {
                entry = marker[j];
                marker[j] = marker[j - 1] << 1;
            }
        }
        for (i = 0; i < n; ++i) {
            int temp = 0;
            for (int j = 0; j < l[i]; ++j) {
                temp <<= 1;
                temp |= r[i] >>> j & 1;
            }
            r[i] = temp;
        }
        return r;
    }

    DecodeAux make_decode_tree() {
        int top = 0;
        DecodeAux t = new DecodeAux();
        t.ptr0 = new int[this.entries * 2];
        int[] ptr0 = t.ptr0;
        t.ptr1 = new int[this.entries * 2];
        int[] ptr1 = t.ptr1;
        int[] codelist = CodeBook.make_words(this.c.lengthlist, this.c.entries);
        if (codelist == null) {
            return null;
        }
        t.aux = this.entries * 2;
        for (int i = 0; i < this.entries; ++i) {
            int j;
            if (this.c.lengthlist[i] <= 0) continue;
            int ptr = 0;
            for (j = 0; j < this.c.lengthlist[i] - 1; ++j) {
                int bit = codelist[i] >>> j & 1;
                if (bit == 0) {
                    if (ptr0[ptr] == 0) {
                        ptr0[ptr] = ++top;
                    }
                    ptr = ptr0[ptr];
                    continue;
                }
                if (ptr1[ptr] == 0) {
                    ptr1[ptr] = ++top;
                }
                ptr = ptr1[ptr];
            }
            if ((codelist[i] >>> j & 1) == 0) {
                ptr0[ptr] = -i;
                continue;
            }
            ptr1[ptr] = -i;
        }
        t.tabn = Util.ilog(this.entries) - 4;
        if (t.tabn < 5) {
            t.tabn = 5;
        }
        int n = 1 << t.tabn;
        t.tab = new int[n];
        t.tabl = new int[n];
        for (int i = 0; i < n; ++i) {
            int p = 0;
            int j = 0;
            for (j = 0; j < t.tabn && (p > 0 || j == 0); ++j) {
                p = (i & 1 << j) != 0 ? ptr1[p] : ptr0[p];
            }
            t.tab[i] = p;
            t.tabl[i] = j;
        }
        return t;
    }

    class DecodeAux {
        int[] tab;
        int[] tabl;
        int tabn;
        int[] ptr0;
        int[] ptr1;
        int aux;

        DecodeAux() {
        }
    }
}

