/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.UninitializedMessageException;

public abstract class AbstractMessageLite
implements MessageLite {
    protected int memoizedHashCode = 0;

    public void writeDelimitedTo(OutputStream output) throws IOException {
        int serialized = this.getSerializedSize();
        int bufferSize = CodedOutputStream.computePreferredBufferSize(CodedOutputStream.computeRawVarint32Size(serialized) + serialized);
        CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
        codedOutput.writeRawVarint32(serialized);
        this.writeTo(codedOutput);
        codedOutput.flush();
    }

    UninitializedMessageException newUninitializedMessageException() {
        return new UninitializedMessageException(this);
    }

    public static abstract class Builder<BuilderType extends Builder>
    implements MessageLite.Builder {
        public abstract BuilderType clone();

        public abstract BuilderType mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;

        protected static UninitializedMessageException newUninitializedMessageException(MessageLite message) {
            return new UninitializedMessageException(message);
        }

        static final class LimitedInputStream
        extends FilterInputStream {
            private int limit;

            LimitedInputStream(InputStream in, int limit) {
                super(in);
                this.limit = limit;
            }

            @Override
            public int available() throws IOException {
                return Math.min(super.available(), this.limit);
            }

            @Override
            public int read() throws IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                int result2 = super.read();
                if (result2 >= 0) {
                    --this.limit;
                }
                return result2;
            }

            @Override
            public int read(byte[] b2, int off, int len) throws IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                int result2 = super.read(b2, off, len = Math.min(len, this.limit));
                if (result2 >= 0) {
                    this.limit -= result2;
                }
                return result2;
            }

            @Override
            public long skip(long n) throws IOException {
                long result2 = super.skip(Math.min(n, (long)this.limit));
                if (result2 >= 0L) {
                    this.limit = (int)((long)this.limit - result2);
                }
                return result2;
            }
        }
    }
}

