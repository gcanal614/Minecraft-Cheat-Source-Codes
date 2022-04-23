/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.NoSuchElementException;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString;

class BoundedByteString
extends LiteralByteString {
    private final int bytesOffset;
    private final int bytesLength;

    BoundedByteString(byte[] bytes, int offset, int length) {
        super(bytes);
        if (offset < 0) {
            int n = offset;
            throw new IllegalArgumentException(new StringBuilder(29).append("Offset too small: ").append(n).toString());
        }
        if (length < 0) {
            int n = offset;
            throw new IllegalArgumentException(new StringBuilder(29).append("Length too small: ").append(n).toString());
        }
        if ((long)offset + (long)length > (long)bytes.length) {
            int n = offset;
            int n2 = length;
            throw new IllegalArgumentException(new StringBuilder(48).append("Offset+Length too large: ").append(n).append("+").append(n2).toString());
        }
        this.bytesOffset = offset;
        this.bytesLength = length;
    }

    @Override
    public byte byteAt(int index) {
        if (index < 0) {
            int n = index;
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(28).append("Index too small: ").append(n).toString());
        }
        if (index >= this.size()) {
            int n = index;
            int n2 = this.size();
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(41).append("Index too large: ").append(n).append(", ").append(n2).toString());
        }
        return this.bytes[this.bytesOffset + index];
    }

    @Override
    public int size() {
        return this.bytesLength;
    }

    @Override
    protected int getOffsetIntoBytes() {
        return this.bytesOffset;
    }

    @Override
    protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        System.arraycopy(this.bytes, this.getOffsetIntoBytes() + sourceOffset, target, targetOffset, numberToCopy);
    }

    @Override
    public ByteString.ByteIterator iterator() {
        return new BoundedByteIterator();
    }

    private class BoundedByteIterator
    implements ByteString.ByteIterator {
        private int position;
        private final int limit;

        private BoundedByteIterator() {
            this.position = BoundedByteString.this.getOffsetIntoBytes();
            this.limit = this.position + BoundedByteString.this.size();
        }

        @Override
        public boolean hasNext() {
            return this.position < this.limit;
        }

        @Override
        public Byte next() {
            return this.nextByte();
        }

        @Override
        public byte nextByte() {
            if (this.position >= this.limit) {
                throw new NoSuchElementException();
            }
            return BoundedByteString.this.bytes[this.position++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

