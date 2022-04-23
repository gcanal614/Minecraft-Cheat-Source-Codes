/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.InputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;

public interface Parser<MessageType> {
    public MessageType parsePartialFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;
}

