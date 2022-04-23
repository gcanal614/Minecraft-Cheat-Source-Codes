/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import kotlin.reflect.jvm.internal.impl.protobuf.BoundedByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.Utf8;
import kotlin.reflect.jvm.internal.impl.protobuf.WireFormat;

public final class CodedInputStream {
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int bufferPos;
    private final InputStream input;
    private int lastTag;
    private boolean enableAliasing = false;
    private int totalBytesRetired;
    private int currentLimit = Integer.MAX_VALUE;
    private int recursionDepth;
    private int recursionLimit = 64;
    private int sizeLimit = 0x4000000;
    private RefillCallback refillCallback = null;

    public static CodedInputStream newInstance(InputStream input) {
        return new CodedInputStream(input);
    }

    static CodedInputStream newInstance(LiteralByteString byteString) {
        CodedInputStream result2 = new CodedInputStream(byteString);
        try {
            result2.pushLimit(byteString.size());
        }
        catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
        return result2;
    }

    public int readTag() throws IOException {
        if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = this.readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        return this.lastTag;
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                long value = this.readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            }
            case 1: {
                long value = this.readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value);
                return true;
            }
            case 2: {
                ByteString value = this.readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value);
                return true;
            }
            case 3: {
                output.writeRawVarint32(tag);
                this.skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                this.checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            }
            case 4: {
                return false;
            }
            case 5: {
                int value = this.readRawLittleEndian32();
                output.writeRawVarint32(tag);
                output.writeFixed32NoTag(value);
                return true;
            }
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        while ((tag = this.readTag()) != 0 && this.skipField(tag, output)) {
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return this.readRawVarint64();
    }

    public long readInt64() throws IOException {
        return this.readRawVarint64();
    }

    public int readInt32() throws IOException {
        return this.readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return this.readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return this.readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return this.readRawVarint64() != 0L;
    }

    public String readString() throws IOException {
        int size = this.readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            String result2 = new String(this.buffer, this.bufferPos, size, "UTF-8");
            this.bufferPos += size;
            return result2;
        }
        if (size == 0) {
            return "";
        }
        return new String(this.readRawBytesSlowPath(size), "UTF-8");
    }

    public String readStringRequireUtf8() throws IOException {
        byte[] bytes;
        int pos;
        int size = this.readRawVarint32();
        if (size <= this.bufferSize - (pos = this.bufferPos) && size > 0) {
            bytes = this.buffer;
            this.bufferPos = pos + size;
        } else {
            if (size == 0) {
                return "";
            }
            bytes = this.readRawBytesSlowPath(size);
            pos = 0;
        }
        if (!Utf8.isValidUtf8(bytes, pos, pos + size)) {
            throw InvalidProtocolBufferException.invalidUtf8();
        }
        return new String(bytes, pos, size, "UTF-8");
    }

    public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        ++this.recursionDepth;
        builder.mergeFrom(this, extensionRegistry);
        this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        --this.recursionDepth;
    }

    public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = this.readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = this.pushLimit(length);
        ++this.recursionDepth;
        builder.mergeFrom(this, extensionRegistry);
        this.checkLastTagWas(0);
        --this.recursionDepth;
        this.popLimit(oldLimit);
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = this.readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = this.pushLimit(length);
        ++this.recursionDepth;
        MessageLite result2 = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
        this.checkLastTagWas(0);
        --this.recursionDepth;
        this.popLimit(oldLimit);
        return (T)result2;
    }

    public ByteString readBytes() throws IOException {
        int size = this.readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            ByteString result2 = this.bufferIsImmutable && this.enableAliasing ? new BoundedByteString(this.buffer, this.bufferPos, size) : ByteString.copyFrom(this.buffer, this.bufferPos, size);
            this.bufferPos += size;
            return result2;
        }
        if (size == 0) {
            return ByteString.EMPTY;
        }
        return new LiteralByteString(this.readRawBytesSlowPath(size));
    }

    public int readUInt32() throws IOException {
        return this.readRawVarint32();
    }

    public int readEnum() throws IOException {
        return this.readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return this.readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return this.readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return CodedInputStream.decodeZigZag32(this.readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return CodedInputStream.decodeZigZag64(this.readRawVarint64());
    }

    public int readRawVarint32() throws IOException {
        block4: {
            int x;
            int pos;
            block6: {
                byte[] buffer;
                block8: {
                    block7: {
                        block5: {
                            pos = this.bufferPos;
                            if (this.bufferSize == pos) break block4;
                            buffer = this.buffer;
                            if ((x = buffer[pos++]) >= 0) {
                                this.bufferPos = pos;
                                return x;
                            }
                            if (this.bufferSize - pos < 9) break block4;
                            if ((long)(x ^= buffer[pos++] << 7) >= 0L) break block5;
                            x = (int)((long)x ^ 0xFFFFFFFFFFFFFF80L);
                            break block6;
                        }
                        if ((long)(x ^= buffer[pos++] << 14) < 0L) break block7;
                        x = (int)((long)x ^ 0x3F80L);
                        break block6;
                    }
                    if ((long)(x ^= buffer[pos++] << 21) >= 0L) break block8;
                    x = (int)((long)x ^ 0xFFFFFFFFFFE03F80L);
                    break block6;
                }
                byte y = buffer[pos++];
                x ^= y << 28;
                x = (int)((long)x ^ 0xFE03F80L);
                if (y < 0 && buffer[pos++] < 0 && buffer[pos++] < 0 && buffer[pos++] < 0 && buffer[pos++] < 0 && buffer[pos++] < 0) break block4;
            }
            this.bufferPos = pos;
            return x;
        }
        return (int)this.readRawVarint64SlowPath();
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        int b2;
        int offset;
        if ((firstByte & 0x80) == 0) {
            return firstByte;
        }
        int result2 = firstByte & 0x7F;
        for (offset = 7; offset < 32; offset += 7) {
            b2 = input.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result2 |= (b2 & 0x7F) << offset;
            if ((b2 & 0x80) != 0) continue;
            return result2;
        }
        while (offset < 64) {
            b2 = input.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((b2 & 0x80) == 0) {
                return result2;
            }
            offset += 7;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public long readRawVarint64() throws IOException {
        block4: {
            long x;
            int pos;
            block6: {
                byte[] buffer;
                block12: {
                    block11: {
                        block10: {
                            block9: {
                                block8: {
                                    block7: {
                                        block5: {
                                            byte y;
                                            pos = this.bufferPos;
                                            if (this.bufferSize == pos) break block4;
                                            buffer = this.buffer;
                                            if ((y = buffer[pos++]) >= 0) {
                                                this.bufferPos = pos;
                                                return y;
                                            }
                                            if (this.bufferSize - pos < 9) break block4;
                                            if ((x = (long)(y ^ buffer[pos++] << 7)) >= 0L) break block5;
                                            x ^= 0xFFFFFFFFFFFFFF80L;
                                            break block6;
                                        }
                                        if ((x ^= (long)(buffer[pos++] << 14)) < 0L) break block7;
                                        x ^= 0x3F80L;
                                        break block6;
                                    }
                                    if ((x ^= (long)(buffer[pos++] << 21)) >= 0L) break block8;
                                    x ^= 0xFFFFFFFFFFE03F80L;
                                    break block6;
                                }
                                if ((x ^= (long)buffer[pos++] << 28) < 0L) break block9;
                                x ^= 0xFE03F80L;
                                break block6;
                            }
                            if ((x ^= (long)buffer[pos++] << 35) >= 0L) break block10;
                            x ^= 0xFFFFFFF80FE03F80L;
                            break block6;
                        }
                        if ((x ^= (long)buffer[pos++] << 42) < 0L) break block11;
                        x ^= 0x3F80FE03F80L;
                        break block6;
                    }
                    if ((x ^= (long)buffer[pos++] << 49) >= 0L) break block12;
                    x ^= 0xFFFE03F80FE03F80L;
                    break block6;
                }
                x ^= (long)buffer[pos++] << 56;
                if ((x ^= 0xFE03F80FE03F80L) < 0L && (long)buffer[pos++] < 0L) break block4;
            }
            this.bufferPos = pos;
            return x;
        }
        return this.readRawVarint64SlowPath();
    }

    long readRawVarint64SlowPath() throws IOException {
        long result2 = 0L;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b2 = this.readRawByte();
            result2 |= (long)(b2 & 0x7F) << shift;
            if ((b2 & 0x80) != 0) continue;
            return result2;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 4) {
            this.refillBuffer(4);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 4;
        return buffer[pos] & 0xFF | (buffer[pos + 1] & 0xFF) << 8 | (buffer[pos + 2] & 0xFF) << 16 | (buffer[pos + 3] & 0xFF) << 24;
    }

    public long readRawLittleEndian64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 8) {
            this.refillBuffer(8);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 8;
        return (long)buffer[pos] & 0xFFL | ((long)buffer[pos + 1] & 0xFFL) << 8 | ((long)buffer[pos + 2] & 0xFFL) << 16 | ((long)buffer[pos + 3] & 0xFFL) << 24 | ((long)buffer[pos + 4] & 0xFFL) << 32 | ((long)buffer[pos + 5] & 0xFFL) << 40 | ((long)buffer[pos + 6] & 0xFFL) << 48 | ((long)buffer[pos + 7] & 0xFFL) << 56;
    }

    public static int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    public static long decodeZigZag64(long n) {
        return n >>> 1 ^ -(n & 1L);
    }

    private CodedInputStream(InputStream input) {
        this.buffer = new byte[4096];
        this.bufferSize = 0;
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(LiteralByteString byteString) {
        this.buffer = byteString.bytes;
        this.bufferPos = byteString.getOffsetIntoBytes();
        this.bufferSize = this.bufferPos + byteString.size();
        this.totalBytesRetired = -this.bufferPos;
        this.input = null;
        this.bufferIsImmutable = true;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        int oldLimit = this.currentLimit;
        if ((byteLimit += this.totalBytesRetired + this.bufferPos) > oldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        this.currentLimit = byteLimit;
        this.recomputeBufferSizeAfterLimit();
        return oldLimit;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.totalBytesRetired + this.bufferSize;
        if (bufferEnd > this.currentLimit) {
            this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        this.recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        int currentAbsolutePosition = this.totalBytesRetired + this.bufferPos;
        return this.currentLimit - currentAbsolutePosition;
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !this.tryRefillBuffer(1);
    }

    private void ensureAvailable(int n) throws IOException {
        if (this.bufferSize - this.bufferPos < n) {
            this.refillBuffer(n);
        }
    }

    private void refillBuffer(int n) throws IOException {
        if (!this.tryRefillBuffer(n)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        if (this.bufferPos + n <= this.bufferSize) {
            int n2 = n;
            throw new IllegalStateException(new StringBuilder(77).append("refillBuffer() called when ").append(n2).append(" bytes were already available in buffer").toString());
        }
        if (this.totalBytesRetired + this.bufferPos + n > this.currentLimit) {
            return false;
        }
        if (this.refillCallback != null) {
            this.refillCallback.onRefill();
        }
        if (this.input != null) {
            int bytesRead;
            int pos = this.bufferPos;
            if (pos > 0) {
                if (this.bufferSize > pos) {
                    System.arraycopy(this.buffer, pos, this.buffer, 0, this.bufferSize - pos);
                }
                this.totalBytesRetired += pos;
                this.bufferSize -= pos;
                this.bufferPos = 0;
            }
            if ((bytesRead = this.input.read(this.buffer, this.bufferSize, this.buffer.length - this.bufferSize)) == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                int n3 = bytesRead;
                throw new IllegalStateException(new StringBuilder(102).append("InputStream#read(byte[]) returned invalid result: ").append(n3).append("\nThe InputStream implementation is buggy.").toString());
            }
            if (bytesRead > 0) {
                this.bufferSize += bytesRead;
                if (this.totalBytesRetired + n - this.sizeLimit > 0) {
                    throw InvalidProtocolBufferException.sizeLimitExceeded();
                }
                this.recomputeBufferSizeAfterLimit();
                return this.bufferSize >= n ? true : this.tryRefillBuffer(n);
            }
        }
        return false;
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            this.refillBuffer(1);
        }
        return this.buffer[this.bufferPos++];
    }

    private byte[] readRawBytesSlowPath(int size) throws IOException {
        int pos;
        byte[] chunk;
        if (size <= 0) {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (this.totalBytesRetired + this.bufferPos + size > this.currentLimit) {
            this.skipRawBytes(this.currentLimit - this.totalBytesRetired - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (size < 4096) {
            byte[] bytes = new byte[size];
            int pos2 = this.bufferSize - this.bufferPos;
            System.arraycopy(this.buffer, this.bufferPos, bytes, 0, pos2);
            this.bufferPos = this.bufferSize;
            this.ensureAvailable(size - pos2);
            System.arraycopy(this.buffer, 0, bytes, pos2, size - pos2);
            this.bufferPos = size - pos2;
            return bytes;
        }
        int originalBufferPos = this.bufferPos;
        int originalBufferSize = this.bufferSize;
        this.totalBytesRetired += this.bufferSize;
        this.bufferPos = 0;
        this.bufferSize = 0;
        ArrayList<byte[]> chunks = new ArrayList<byte[]>();
        for (int sizeLeft = size - (originalBufferSize - originalBufferPos); sizeLeft > 0; sizeLeft -= chunk.length) {
            int n;
            chunk = new byte[Math.min(sizeLeft, 4096)];
            for (pos = 0; pos < chunk.length; pos += n) {
                int n2 = n = this.input == null ? -1 : this.input.read(chunk, pos, chunk.length - pos);
                if (n == -1) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                this.totalBytesRetired += n;
            }
            chunks.add(chunk);
        }
        byte[] bytes = new byte[size];
        pos = originalBufferSize - originalBufferPos;
        System.arraycopy(this.buffer, originalBufferPos, bytes, 0, pos);
        for (byte[] chunk2 : chunks) {
            System.arraycopy(chunk2, 0, bytes, pos, chunk2.length);
            pos += chunk2.length;
        }
        return bytes;
    }

    public void skipRawBytes(int size) throws IOException {
        if (size <= this.bufferSize - this.bufferPos && size >= 0) {
            this.bufferPos += size;
        } else {
            this.skipRawBytesSlowPath(size);
        }
    }

    private void skipRawBytesSlowPath(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (this.totalBytesRetired + this.bufferPos + size > this.currentLimit) {
            this.skipRawBytes(this.currentLimit - this.totalBytesRetired - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int pos = this.bufferSize - this.bufferPos;
        this.bufferPos = this.bufferSize;
        this.refillBuffer(1);
        while (size - pos > this.bufferSize) {
            pos += this.bufferSize;
            this.bufferPos = this.bufferSize;
            this.refillBuffer(1);
        }
        this.bufferPos = size - pos;
    }

    private static interface RefillCallback {
        public void onRefill();
    }
}

