/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.UninitializedMessageException;

public abstract class AbstractParser<MessageType extends MessageLite>
implements Parser<MessageType> {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

    private UninitializedMessageException newUninitializedMessageException(MessageType message) {
        if (message instanceof AbstractMessageLite) {
            return ((AbstractMessageLite)message).newUninitializedMessageException();
        }
        return new UninitializedMessageException((MessageLite)message);
    }

    private MessageType checkMessageInitialized(MessageType message) throws InvalidProtocolBufferException {
        if (message != null && !message.isInitialized()) {
            throw this.newUninitializedMessageException(message).asInvalidProtocolBufferException().setUnfinishedMessage((MessageLite)message);
        }
        return message;
    }

    public MessageType parsePartialFrom(ByteString data2, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        CodedInputStream input = data2.newCodedInput();
        MessageLite message = (MessageLite)this.parsePartialFrom(input, extensionRegistry);
        try {
            input.checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        }
        return (MessageType)message;
    }

    @Override
    public MessageType parseFrom(ByteString data2, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return this.checkMessageInitialized(this.parsePartialFrom(data2, extensionRegistry));
    }

    public MessageType parsePartialFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        CodedInputStream codedInput = CodedInputStream.newInstance(input);
        MessageLite message = (MessageLite)this.parsePartialFrom(codedInput, extensionRegistry);
        try {
            codedInput.checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        }
        return (MessageType)message;
    }

    @Override
    public MessageType parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return this.checkMessageInitialized(this.parsePartialFrom(input, extensionRegistry));
    }

    public MessageType parsePartialDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        int size;
        try {
            int firstByte = input.read();
            if (firstByte == -1) {
                return null;
            }
            size = CodedInputStream.readRawVarint32(firstByte, input);
        }
        catch (IOException e) {
            throw new InvalidProtocolBufferException(e.getMessage());
        }
        AbstractMessageLite.Builder.LimitedInputStream limitedInput = new AbstractMessageLite.Builder.LimitedInputStream(input, size);
        return this.parsePartialFrom(limitedInput, extensionRegistry);
    }

    @Override
    public MessageType parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return this.checkMessageInitialized(this.parsePartialDelimitedFrom(input, extensionRegistry));
    }
}

