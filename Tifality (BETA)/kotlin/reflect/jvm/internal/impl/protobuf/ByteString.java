/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.RopeByteString;

public abstract class ByteString
implements Iterable<Byte> {
    public static final ByteString EMPTY = new LiteralByteString(new byte[0]);

    ByteString() {
    }

    public abstract ByteIterator iterator();

    public abstract int size();

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public static ByteString copyFrom(byte[] bytes, int offset, int size) {
        byte[] copy2 = new byte[size];
        System.arraycopy(bytes, offset, copy2, 0, size);
        return new LiteralByteString(copy2);
    }

    public static ByteString copyFrom(byte[] bytes) {
        return ByteString.copyFrom(bytes, 0, bytes.length);
    }

    public static ByteString copyFromUtf8(String text) {
        try {
            return new LiteralByteString(text.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public ByteString concat(ByteString other) {
        int otherSize;
        int thisSize = this.size();
        if ((long)thisSize + (long)(otherSize = other.size()) >= Integer.MAX_VALUE) {
            int n = thisSize;
            int n2 = otherSize;
            throw new IllegalArgumentException(new StringBuilder(53).append("ByteString would be too long: ").append(n).append("+").append(n2).toString());
        }
        return RopeByteString.concatenate(this, other);
    }

    public static ByteString copyFrom(Iterable<ByteString> byteStrings) {
        ArrayList<ByteString> collection;
        if (!(byteStrings instanceof Collection)) {
            collection = new ArrayList<ByteString>();
            for (ByteString byteString : byteStrings) {
                collection.add(byteString);
            }
        } else {
            collection = (ArrayList<ByteString>)byteStrings;
        }
        ByteString result2 = collection.isEmpty() ? EMPTY : ByteString.balancedConcat(collection.iterator(), collection.size());
        return result2;
    }

    private static ByteString balancedConcat(Iterator<ByteString> iterator2, int length) {
        ByteString result2;
        assert (length >= 1);
        if (length == 1) {
            result2 = iterator2.next();
        } else {
            int halfLength = length >>> 1;
            ByteString left = ByteString.balancedConcat(iterator2, halfLength);
            ByteString right = ByteString.balancedConcat(iterator2, length - halfLength);
            result2 = left.concat(right);
        }
        return result2;
    }

    public void copyTo(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        if (sourceOffset < 0) {
            int n = sourceOffset;
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(n).toString());
        }
        if (targetOffset < 0) {
            int n = targetOffset;
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Target offset < 0: ").append(n).toString());
        }
        if (numberToCopy < 0) {
            int n = numberToCopy;
            throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(n).toString());
        }
        if (sourceOffset + numberToCopy > this.size()) {
            int n = sourceOffset + numberToCopy;
            throw new IndexOutOfBoundsException(new StringBuilder(34).append("Source end offset < 0: ").append(n).toString());
        }
        if (targetOffset + numberToCopy > target.length) {
            int n = targetOffset + numberToCopy;
            throw new IndexOutOfBoundsException(new StringBuilder(34).append("Target end offset < 0: ").append(n).toString());
        }
        if (numberToCopy > 0) {
            this.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        }
    }

    protected abstract void copyToInternal(byte[] var1, int var2, int var3, int var4);

    public byte[] toByteArray() {
        int size = this.size();
        if (size == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        }
        byte[] result2 = new byte[size];
        this.copyToInternal(result2, 0, 0, size);
        return result2;
    }

    void writeTo(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (sourceOffset < 0) {
            int n = sourceOffset;
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(n).toString());
        }
        if (numberToWrite < 0) {
            int n = numberToWrite;
            throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(n).toString());
        }
        if (sourceOffset + numberToWrite > this.size()) {
            int n = sourceOffset + numberToWrite;
            throw new IndexOutOfBoundsException(new StringBuilder(39).append("Source end offset exceeded: ").append(n).toString());
        }
        if (numberToWrite > 0) {
            this.writeToInternal(out, sourceOffset, numberToWrite);
        }
    }

    abstract void writeToInternal(OutputStream var1, int var2, int var3) throws IOException;

    public abstract String toString(String var1) throws UnsupportedEncodingException;

    public String toStringUtf8() {
        try {
            return this.toString("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public abstract boolean isValidUtf8();

    protected abstract int partialIsValidUtf8(int var1, int var2, int var3);

    public abstract CodedInputStream newCodedInput();

    public static Output newOutput() {
        return new Output(128);
    }

    protected abstract int getTreeDepth();

    protected abstract boolean isBalanced();

    protected abstract int peekCachedHashCode();

    protected abstract int partialHash(int var1, int var2, int var3);

    public String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
    }

    public static final class Output
    extends OutputStream {
        private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
        private final int initialCapacity;
        private final ArrayList<ByteString> flushedBuffers;
        private int flushedBuffersTotalBytes;
        private byte[] buffer;
        private int bufferPos;

        Output(int initialCapacity) {
            if (initialCapacity < 0) {
                throw new IllegalArgumentException("Buffer size < 0");
            }
            this.initialCapacity = initialCapacity;
            this.flushedBuffers = new ArrayList();
            this.buffer = new byte[initialCapacity];
        }

        @Override
        public synchronized void write(int b2) {
            if (this.bufferPos == this.buffer.length) {
                this.flushFullBuffer(1);
            }
            this.buffer[this.bufferPos++] = (byte)b2;
        }

        @Override
        public synchronized void write(byte[] b2, int offset, int length) {
            if (length <= this.buffer.length - this.bufferPos) {
                System.arraycopy(b2, offset, this.buffer, this.bufferPos, length);
                this.bufferPos += length;
            } else {
                int copySize = this.buffer.length - this.bufferPos;
                System.arraycopy(b2, offset, this.buffer, this.bufferPos, copySize);
                this.flushFullBuffer(length -= copySize);
                System.arraycopy(b2, offset += copySize, this.buffer, 0, length);
                this.bufferPos = length;
            }
        }

        public synchronized ByteString toByteString() {
            this.flushLastBuffer();
            return ByteString.copyFrom(this.flushedBuffers);
        }

        private byte[] copyArray(byte[] buffer, int length) {
            byte[] result2 = new byte[length];
            System.arraycopy(buffer, 0, result2, 0, Math.min(buffer.length, length));
            return result2;
        }

        public synchronized int size() {
            return this.flushedBuffersTotalBytes + this.bufferPos;
        }

        public String toString() {
            return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
        }

        private void flushFullBuffer(int minSize) {
            this.flushedBuffers.add(new LiteralByteString(this.buffer));
            this.flushedBuffersTotalBytes += this.buffer.length;
            int newSize = Math.max(this.initialCapacity, Math.max(minSize, this.flushedBuffersTotalBytes >>> 1));
            this.buffer = new byte[newSize];
            this.bufferPos = 0;
        }

        private void flushLastBuffer() {
            if (this.bufferPos < this.buffer.length) {
                if (this.bufferPos > 0) {
                    byte[] bufferCopy = this.copyArray(this.buffer, this.bufferPos);
                    this.flushedBuffers.add(new LiteralByteString(bufferCopy));
                }
            } else {
                this.flushedBuffers.add(new LiteralByteString(this.buffer));
                this.buffer = EMPTY_BYTE_ARRAY;
            }
            this.flushedBuffersTotalBytes += this.bufferPos;
            this.bufferPos = 0;
        }
    }

    public static interface ByteIterator
    extends Iterator<Byte> {
        public byte nextByte();
    }
}

