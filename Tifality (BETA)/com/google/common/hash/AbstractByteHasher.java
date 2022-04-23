/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hasher;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractByteHasher
extends AbstractHasher {
    private final ByteBuffer scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    AbstractByteHasher() {
    }

    protected abstract void update(byte var1);

    protected void update(byte[] b2) {
        this.update(b2, 0, b2.length);
    }

    protected void update(byte[] b2, int off, int len) {
        for (int i = off; i < off + len; ++i) {
            this.update(b2[i]);
        }
    }

    @Override
    public Hasher putByte(byte b2) {
        this.update(b2);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] bytes) {
        Preconditions.checkNotNull(bytes);
        this.update(bytes);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] bytes, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, bytes.length);
        this.update(bytes, off, len);
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Hasher update(int bytes) {
        try {
            this.update(this.scratch.array(), 0, bytes);
        }
        finally {
            this.scratch.clear();
        }
        return this;
    }

    @Override
    public Hasher putShort(short s) {
        this.scratch.putShort(s);
        return this.update(2);
    }

    @Override
    public Hasher putInt(int i) {
        this.scratch.putInt(i);
        return this.update(4);
    }

    @Override
    public Hasher putLong(long l) {
        this.scratch.putLong(l);
        return this.update(8);
    }

    @Override
    public Hasher putChar(char c) {
        this.scratch.putChar(c);
        return this.update(2);
    }

    @Override
    public <T> Hasher putObject(T instance, Funnel<? super T> funnel) {
        funnel.funnel(instance, this);
        return this;
    }
}

