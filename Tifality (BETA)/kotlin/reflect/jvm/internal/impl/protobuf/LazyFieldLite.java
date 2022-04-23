/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;

public class LazyFieldLite {
    private ByteString bytes;
    private ExtensionRegistryLite extensionRegistry;
    private volatile boolean isDirty;
    protected volatile MessageLite value;

    public MessageLite getValue(MessageLite defaultInstance) {
        this.ensureInitialized(defaultInstance);
        return this.value;
    }

    public MessageLite setValue(MessageLite value) {
        MessageLite originalValue = this.value;
        this.value = value;
        this.bytes = null;
        this.isDirty = true;
        return originalValue;
    }

    public int getSerializedSize() {
        if (this.isDirty) {
            return this.value.getSerializedSize();
        }
        return this.bytes.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void ensureInitialized(MessageLite defaultInstance) {
        if (this.value != null) {
            return;
        }
        LazyFieldLite lazyFieldLite = this;
        synchronized (lazyFieldLite) {
            if (this.value != null) {
                return;
            }
            try {
                this.value = this.bytes != null ? defaultInstance.getParserForType().parseFrom(this.bytes, this.extensionRegistry) : defaultInstance;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

