/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;

public interface MessageLite
extends MessageLiteOrBuilder {
    public void writeTo(CodedOutputStream var1) throws IOException;

    public int getSerializedSize();

    public Parser<? extends MessageLite> getParserForType();

    public Builder newBuilderForType();

    public Builder toBuilder();

    public static interface Builder
    extends Cloneable,
    MessageLiteOrBuilder {
        public MessageLite build();

        public Builder mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;
    }
}

