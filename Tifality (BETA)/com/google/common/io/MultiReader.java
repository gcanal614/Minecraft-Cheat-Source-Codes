/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.CharSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import javax.annotation.Nullable;

class MultiReader
extends Reader {
    private final Iterator<? extends CharSource> it;
    private Reader current;

    MultiReader(Iterator<? extends CharSource> readers) throws IOException {
        this.it = readers;
        this.advance();
    }

    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.current = this.it.next().openStream();
        }
    }

    @Override
    public int read(@Nullable char[] cbuf, int off, int len) throws IOException {
        if (this.current == null) {
            return -1;
        }
        int result2 = this.current.read(cbuf, off, len);
        if (result2 == -1) {
            this.advance();
            return this.read(cbuf, off, len);
        }
        return result2;
    }

    @Override
    public long skip(long n) throws IOException {
        Preconditions.checkArgument(n >= 0L, "n is negative");
        if (n > 0L) {
            while (this.current != null) {
                long result2 = this.current.skip(n);
                if (result2 > 0L) {
                    return result2;
                }
                this.advance();
            }
        }
        return 0L;
    }

    @Override
    public boolean ready() throws IOException {
        return this.current != null && this.current.ready();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        if (this.current != null) {
            try {
                this.current.close();
            }
            finally {
                this.current = null;
            }
        }
    }
}

