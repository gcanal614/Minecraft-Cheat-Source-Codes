/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class CountingInputStream
extends ProxyInputStream {
    private long count;

    public CountingInputStream(InputStream in) {
        super(in);
    }

    @Override
    public synchronized long skip(long length) throws IOException {
        long skip = super.skip(length);
        this.count += skip;
        return skip;
    }

    @Override
    protected synchronized void afterRead(int n) {
        if (n != -1) {
            this.count += (long)n;
        }
    }

    public int getCount() {
        long result2 = this.getByteCount();
        if (result2 > Integer.MAX_VALUE) {
            throw new ArithmeticException("The byte count " + result2 + " is too large to be converted to an int");
        }
        return (int)result2;
    }

    public int resetCount() {
        long result2 = this.resetByteCount();
        if (result2 > Integer.MAX_VALUE) {
            throw new ArithmeticException("The byte count " + result2 + " is too large to be converted to an int");
        }
        return (int)result2;
    }

    public synchronized long getByteCount() {
        return this.count;
    }

    public synchronized long resetByteCount() {
        long tmp = this.count;
        this.count = 0L;
        return tmp;
    }
}

