/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

@Beta
public final class CountingInputStream
extends FilterInputStream {
    private long count;
    private long mark = -1L;

    public CountingInputStream(@Nullable InputStream in) {
        super(in);
    }

    public long getCount() {
        return this.count;
    }

    @Override
    public int read() throws IOException {
        int result2 = this.in.read();
        if (result2 != -1) {
            ++this.count;
        }
        return result2;
    }

    @Override
    public int read(byte[] b2, int off, int len) throws IOException {
        int result2 = this.in.read(b2, off, len);
        if (result2 != -1) {
            this.count += (long)result2;
        }
        return result2;
    }

    @Override
    public long skip(long n) throws IOException {
        long result2 = this.in.skip(n);
        this.count += result2;
        return result2;
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
        this.mark = this.count;
    }

    @Override
    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        if (this.mark == -1L) {
            throw new IOException("Mark not set");
        }
        this.in.reset();
        this.count = this.mark;
    }
}

