/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.FieldSet;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.WireFormat;

public abstract class GeneratedMessageLite
extends AbstractMessageLite
implements Serializable {
    protected GeneratedMessageLite() {
    }

    protected GeneratedMessageLite(Builder builder) {
    }

    @Override
    public Parser<? extends MessageLite> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    protected boolean parseUnknownField(CodedInputStream input, CodedOutputStream unknownFieldsCodedOutput, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
        return input.skipField(tag, unknownFieldsCodedOutput);
    }

    protected void makeExtensionsImmutable() {
    }

    private static <MessageType extends MessageLite> boolean parseUnknownField(FieldSet<ExtensionDescriptor> extensions, MessageType defaultInstance, CodedInputStream input, CodedOutputStream unknownFieldsCodedOutput, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
        int wireType = WireFormat.getTagWireType(tag);
        int fieldNumber = WireFormat.getTagFieldNumber(tag);
        GeneratedExtension<MessageType, ?> extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber);
        boolean unknown = false;
        boolean packed = false;
        if (extension == null) {
            unknown = true;
        } else if (wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), false)) {
            packed = false;
        } else if (extension.descriptor.isRepeated && extension.descriptor.type.isPackable() && wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), true)) {
            packed = true;
        } else {
            unknown = true;
        }
        if (unknown) {
            return input.skipField(tag, unknownFieldsCodedOutput);
        }
        if (packed) {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (extension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                while (input.getBytesUntilLimit() > 0) {
                    int rawValue = input.readEnum();
                    Object value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
                    if (value == null) {
                        return true;
                    }
                    extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value));
                }
            } else {
                while (input.getBytesUntilLimit() > 0) {
                    Object value = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                    extensions.addRepeatedField(extension.descriptor, value);
                }
            }
            input.popLimit(limit);
        } else {
            Object value;
            switch (extension.descriptor.getLiteJavaType()) {
                case MESSAGE: {
                    MessageLite existingValue;
                    MessageLite.Builder subBuilder = null;
                    if (!extension.descriptor.isRepeated() && (existingValue = (MessageLite)extensions.getField(extension.descriptor)) != null) {
                        subBuilder = existingValue.toBuilder();
                    }
                    if (subBuilder == null) {
                        subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
                    }
                    if (extension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                        input.readGroup(extension.getNumber(), subBuilder, extensionRegistry);
                    } else {
                        input.readMessage(subBuilder, extensionRegistry);
                    }
                    value = subBuilder.build();
                    break;
                }
                case ENUM: {
                    int rawValue = input.readEnum();
                    value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
                    if (value != null) break;
                    unknownFieldsCodedOutput.writeRawVarint32(tag);
                    unknownFieldsCodedOutput.writeUInt32NoTag(rawValue);
                    return true;
                }
                default: {
                    value = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                }
            }
            if (extension.descriptor.isRepeated()) {
                extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value));
            } else {
                extensions.setField(extension.descriptor, extension.singularToFieldSetType(value));
            }
        }
        return true;
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type2, Class singularType) {
        return new GeneratedExtension<ContainingType, Type>(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type2, false, false), singularType);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type2, boolean isPacked, Class singularType) {
        List emptyList = Collections.emptyList();
        return new GeneratedExtension(containingTypeDefaultInstance, emptyList, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type2, true, isPacked), singularType);
    }

    static Method getMethodOrDie(Class clazz, String name, Class ... params) {
        try {
            return clazz.getMethod(name, params);
        }
        catch (NoSuchMethodException e) {
            String string = String.valueOf(String.valueOf(clazz.getName()));
            String string2 = String.valueOf(String.valueOf(name));
            throw new RuntimeException(new StringBuilder(45 + string.length() + string2.length()).append("Generated message class \"").append(string).append("\" missing method \"").append(string2).append("\".").toString(), e);
        }
    }

    static Object invokeOrDie(Method method, Object object, Object ... params) {
        try {
            return method.invoke(object, params);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            if (cause instanceof Error) {
                throw (Error)cause;
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final MessageLite messageDefaultInstance;
        final ExtensionDescriptor descriptor;
        final Class singularType;
        final Method enumValueOf;

        GeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, ExtensionDescriptor descriptor2, Class singularType) {
            if (containingTypeDefaultInstance == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (descriptor2.getLiteType() == WireFormat.FieldType.MESSAGE && messageDefaultInstance == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.containingTypeDefaultInstance = containingTypeDefaultInstance;
            this.defaultValue = defaultValue;
            this.messageDefaultInstance = messageDefaultInstance;
            this.descriptor = descriptor2;
            this.singularType = singularType;
            this.enumValueOf = Internal.EnumLite.class.isAssignableFrom(singularType) ? GeneratedMessageLite.getMethodOrDie(singularType, "valueOf", Integer.TYPE) : null;
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        public int getNumber() {
            return this.descriptor.getNumber();
        }

        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        Object fromFieldSetType(Object value) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                    ArrayList<Object> result2 = new ArrayList<Object>();
                    for (Object element : (List)value) {
                        result2.add(this.singularFromFieldSetType(element));
                    }
                    return result2;
                }
                return value;
            }
            return this.singularFromFieldSetType(value);
        }

        Object singularFromFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return GeneratedMessageLite.invokeOrDie(this.enumValueOf, null, (Integer)value);
            }
            return value;
        }

        Object singularToFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return ((Internal.EnumLite)value).getNumber();
            }
            return value;
        }
    }

    static final class ExtensionDescriptor
    implements FieldSet.FieldDescriptorLite<ExtensionDescriptor> {
        final Internal.EnumLiteMap<?> enumTypeMap;
        final int number;
        final WireFormat.FieldType type;
        final boolean isRepeated;
        final boolean isPacked;

        ExtensionDescriptor(Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type2, boolean isRepeated, boolean isPacked) {
            this.enumTypeMap = enumTypeMap;
            this.number = number;
            this.type = type2;
            this.isRepeated = isRepeated;
            this.isPacked = isPacked;
        }

        @Override
        public int getNumber() {
            return this.number;
        }

        @Override
        public WireFormat.FieldType getLiteType() {
            return this.type;
        }

        @Override
        public WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        @Override
        public boolean isRepeated() {
            return this.isRepeated;
        }

        @Override
        public boolean isPacked() {
            return this.isPacked;
        }

        public Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        @Override
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder to, MessageLite from) {
            return ((Builder)to).mergeFrom((GeneratedMessageLite)from);
        }

        @Override
        public int compareTo(ExtensionDescriptor other) {
            return this.number - other.number;
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
    extends Builder<MessageType, BuilderType>
    implements ExtendableMessageOrBuilder<MessageType> {
        private FieldSet<ExtensionDescriptor> extensions = FieldSet.emptySet();
        private boolean extensionsIsMutable;

        protected ExtendableBuilder() {
        }

        private void ensureExtensionsIsMutable() {
            if (!this.extensionsIsMutable) {
                this.extensions = this.extensions.clone();
                this.extensionsIsMutable = true;
            }
        }

        private FieldSet<ExtensionDescriptor> buildExtensions() {
            this.extensions.makeImmutable();
            this.extensionsIsMutable = false;
            return this.extensions;
        }

        @Override
        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        protected final void mergeExtensionFields(MessageType other) {
            this.ensureExtensionsIsMutable();
            this.extensions.mergeFrom(((ExtendableMessage)other).extensions);
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType>>
    extends GeneratedMessageLite
    implements ExtendableMessageOrBuilder<MessageType> {
        private final FieldSet<ExtensionDescriptor> extensions;

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        protected ExtendableMessage(ExtendableBuilder<MessageType, ?> builder) {
            this.extensions = ((ExtendableBuilder)builder).buildExtensions();
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != this.getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> extension) {
            this.verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.descriptor);
        }

        public final <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> extension) {
            this.verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.descriptor);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, Type> extension) {
            this.verifyExtensionContainingType(extension);
            Object value = this.extensions.getField(extension.descriptor);
            if (value == null) {
                return extension.defaultValue;
            }
            return (Type)extension.fromFieldSetType(value);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> extension, int index) {
            this.verifyExtensionContainingType(extension);
            return (Type)extension.singularFromFieldSetType(this.extensions.getRepeatedField(extension.descriptor, index));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        @Override
        protected boolean parseUnknownField(CodedInputStream input, CodedOutputStream unknownFieldsCodedOutput, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return GeneratedMessageLite.parseUnknownField(this.extensions, this.getDefaultInstanceForType(), input, unknownFieldsCodedOutput, extensionRegistry, tag);
        }

        @Override
        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
        }

        protected ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private Map.Entry<ExtensionDescriptor, Object> next;
            private final boolean messageSetWireFormat;

            private ExtensionWriter(boolean messageSetWireFormat) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (this.next != null && this.next.getKey().getNumber() < end) {
                    ExtensionDescriptor extension = this.next.getKey();
                    if (this.messageSetWireFormat && extension.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !extension.isRepeated()) {
                        output.writeMessageSetExtension(extension.getNumber(), (MessageLite)this.next.getValue());
                    } else {
                        FieldSet.writeField(extension, this.next.getValue(), output);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                        continue;
                    }
                    this.next = null;
                }
            }
        }
    }

    public static interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage>
    extends MessageLiteOrBuilder {
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite, BuilderType extends Builder>
    extends AbstractMessageLite.Builder<BuilderType> {
        private ByteString unknownFields = ByteString.EMPTY;

        protected Builder() {
        }

        @Override
        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        public abstract BuilderType mergeFrom(MessageType var1);

        public abstract MessageType getDefaultInstanceForType();

        public final ByteString getUnknownFields() {
            return this.unknownFields;
        }

        public final BuilderType setUnknownFields(ByteString unknownFields) {
            this.unknownFields = unknownFields;
            return (BuilderType)this;
        }
    }
}

