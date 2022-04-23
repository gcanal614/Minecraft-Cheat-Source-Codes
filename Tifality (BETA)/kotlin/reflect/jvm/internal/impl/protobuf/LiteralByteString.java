/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.RopeByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.Utf8;

class LiteralByteString
extends ByteString {
    protected final byte[] bytes;
    private int hash = 0;

    LiteralByteString(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte byteAt(int index) {
        return this.bytes[index];
    }

    @Override
    public int size() {
        return this.bytes.length;
    }

    @Override
    protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        System.arraycopy(this.bytes, sourceOffset, target, targetOffset, numberToCopy);
    }

    @Override
    void writeToInternal(OutputStream outputStream, int sourceOffset, int numberToWrite) throws IOException {
        outputStream.write(this.bytes, this.getOffsetIntoBytes() + sourceOffset, numberToWrite);
    }

    @Override
    public String toString(String charsetName) throws UnsupportedEncodingException {
        return new String(this.bytes, this.getOffsetIntoBytes(), this.size(), charsetName);
    }

    @Override
    public boolean isValidUtf8() {
        int offset = this.getOffsetIntoBytes();
        return Utf8.isValidUtf8(this.bytes, offset, offset + this.size());
    }

    @Override
    protected int partialIsValidUtf8(int state, int offset, int length) {
        int index = this.getOffsetIntoBytes() + offset;
        return Utf8.partialIsValidUtf8(state, this.bytes, index, index + length);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        if (this.size() != ((ByteString)other).size()) {
            return false;
        }
        if (this.size() == 0) {
            return true;
        }
        if (other instanceof LiteralByteString) {
            return this.equalsRange((LiteralByteString)other, 0, this.size());
        }
        if (other instanceof RopeByteString) {
            return other.equals(this);
        }
        String string = String.valueOf(String.valueOf(other.getClass()));
        throw new IllegalArgumentException(new StringBuilder(49 + string.length()).append("Has a new type of ByteString been created? Found ").append(string).toString());
    }

    boolean equalsRange(LiteralByteString other, int offset, int length) {
        if (length > other.size()) {
            int n = length;
            int n2 = this.size();
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(n).append(n2).toString());
        }
        if (offset + length > other.size()) {
            int n = offset;
            int n3 = length;
            int n4 = other.size();
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: ").append(n).append(", ").append(n3).append(", ").append(n4).toString());
        }
        byte[] thisBytes = this.bytes;
        byte[] otherBytes = other.bytes;
        int thisLimit = this.getOffsetIntoBytes() + length;
        int thisIndex = this.getOffsetIntoBytes();
        int otherIndex = other.getOffsetIntoBytes() + offset;
        while (thisIndex < thisLimit) {
            if (thisBytes[thisIndex] != otherBytes[otherIndex]) {
                return false;
            }
            ++thisIndex;
            ++otherIndex;
        }
        return true;
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0) {
            int size = this.size();
            h = this.partialHash(size, 0, size);
            if (h == 0) {
                h = 1;
            }
            this.hash = h;
        }
        return h;
    }

    @Override
    protected int peekCachedHashCode() {
        return this.hash;
    }

    @Override
    protected int partialHash(int h, int offset, int length) {
        return LiteralByteString.hashCode(h, this.bytes, this.getOffsetIntoBytes() + offset, length);
    }

    static int hashCode(int h, byte[] bytes, int offset, int length) {
        for (int i = offset; i < offset + length; ++i) {
            h = h * 31 + bytes[i];
        }
        return h;
    }

    @Override
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(this);
    }

    @Override
    public ByteString.ByteIterator iterator() {
        return new LiteralByteIterator();
    }

    @Override
    protected int getTreeDepth() {
        return 0;
    }

    @Override
    protected boolean isBalanced() {
        return true;
    }

    protected int getOffsetIntoBytes() {
        return 0;
    }

    private class LiteralByteIterator
    implements ByteString.ByteIterator {
        private int position = 0;
        private final int limit;

        private LiteralByteIterator() {
            this.limit = LiteralByteString.this.size();
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
            try {
                return LiteralByteString.this.bytes[this.position++];
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

