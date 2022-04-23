/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$ValueOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$ArgumentOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$AnnotationOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ClassOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ConstructorOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ContractOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EffectOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EnumEntryOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ExpressionOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$FunctionOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageFragmentOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PropertyOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$QualifiedNameOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTableOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$StringTableOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$ArgumentOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeAliasOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeParameterOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeTableOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ValueParameterOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementOrBuilder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementTableOrBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractParser;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringArrayList;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;

public final class ProtoBuf {

    public static final class Expression
    extends GeneratedMessageLite
    implements ProtoBuf$ExpressionOrBuilder {
        private static final Expression defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Expression> PARSER;
        private int bitField0_;
        private int flags_;
        private int valueParameterReference_;
        private ConstantValue constantValue_;
        private Type isInstanceType_;
        private int isInstanceTypeId_;
        private List<Expression> andArgument_;
        private List<Expression> orArgument_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Expression(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Expression(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Expression getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Expression getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Expression(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block25: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block25;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block25;
                            done = true;
                            continue block25;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block25;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.valueParameterReference_ = input.readInt32();
                            continue block25;
                        }
                        case 24: {
                            int rawValue = input.readEnum();
                            ConstantValue value = ConstantValue.valueOf(rawValue);
                            if (value == null) {
                                unknownFieldsCodedOutput.writeRawVarint32(tag);
                                unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                continue block25;
                            }
                            this.bitField0_ |= 4;
                            this.constantValue_ = value;
                            continue block25;
                        }
                        case 34: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.isInstanceType_.toBuilder();
                            }
                            this.isInstanceType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.isInstanceType_);
                                this.isInstanceType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue block25;
                        }
                        case 40: {
                            this.bitField0_ |= 0x10;
                            this.isInstanceTypeId_ = input.readInt32();
                            continue block25;
                        }
                        case 50: {
                            if ((mutable_bitField0_ & 0x20) != 32) {
                                this.andArgument_ = new ArrayList<Expression>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.andArgument_.add(input.readMessage(PARSER, extensionRegistry));
                            continue block25;
                        }
                        case 58: 
                    }
                    if ((mutable_bitField0_ & 0x40) != 64) {
                        this.orArgument_ = new ArrayList<Expression>();
                        mutable_bitField0_ |= 0x40;
                    }
                    this.orArgument_.add(input.readMessage(PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
                }
                if ((mutable_bitField0_ & 0x40) == 64) {
                    this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Expression> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasValueParameterReference() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getValueParameterReference() {
            return this.valueParameterReference_;
        }

        public boolean hasConstantValue() {
            return (this.bitField0_ & 4) == 4;
        }

        public ConstantValue getConstantValue() {
            return this.constantValue_;
        }

        public boolean hasIsInstanceType() {
            return (this.bitField0_ & 8) == 8;
        }

        public Type getIsInstanceType() {
            return this.isInstanceType_;
        }

        public boolean hasIsInstanceTypeId() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public int getIsInstanceTypeId() {
            return this.isInstanceTypeId_;
        }

        public int getAndArgumentCount() {
            return this.andArgument_.size();
        }

        public Expression getAndArgument(int index) {
            return this.andArgument_.get(index);
        }

        public int getOrArgumentCount() {
            return this.orArgument_.size();
        }

        public Expression getOrArgument(int index) {
            return this.orArgument_.get(index);
        }

        private void initFields() {
            this.flags_ = 0;
            this.valueParameterReference_ = 0;
            this.constantValue_ = ConstantValue.TRUE;
            this.isInstanceType_ = Type.getDefaultInstance();
            this.isInstanceTypeId_ = 0;
            this.andArgument_ = Collections.emptyList();
            this.orArgument_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            int i;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (this.hasIsInstanceType() && !this.getIsInstanceType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getAndArgumentCount(); ++i) {
                if (this.getAndArgument(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getOrArgumentCount(); ++i) {
                if (this.getOrArgument(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.valueParameterReference_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.constantValue_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, this.isInstanceType_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeInt32(5, this.isInstanceTypeId_);
            }
            for (i = 0; i < this.andArgument_.size(); ++i) {
                output.writeMessage(6, this.andArgument_.get(i));
            }
            for (i = 0; i < this.orArgument_.size(); ++i) {
                output.writeMessage(7, this.orArgument_.get(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.valueParameterReference_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeEnumSize(3, this.constantValue_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(4, this.isInstanceType_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeInt32Size(5, this.isInstanceTypeId_);
            }
            for (i = 0; i < this.andArgument_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(6, this.andArgument_.get(i));
            }
            for (i = 0; i < this.orArgument_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(7, this.orArgument_.get(i));
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Expression.newBuilder();
        }

        public static Builder newBuilder(Expression prototype) {
            return Expression.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Expression.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Expression>(){

                @Override
                public Expression parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Expression(input, extensionRegistry);
                }
            };
            defaultInstance = new Expression(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Expression, Builder>
        implements ProtoBuf$ExpressionOrBuilder {
            private int bitField0_;
            private int flags_;
            private int valueParameterReference_;
            private ConstantValue constantValue_ = ConstantValue.TRUE;
            private Type isInstanceType_ = Type.getDefaultInstance();
            private int isInstanceTypeId_;
            private List<Expression> andArgument_ = Collections.emptyList();
            private List<Expression> orArgument_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Expression getDefaultInstanceForType() {
                return Expression.getDefaultInstance();
            }

            @Override
            public Expression build() {
                Expression result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Expression buildPartial() {
                Expression result2 = new Expression(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.valueParameterReference_ = this.valueParameterReference_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.constantValue_ = this.constantValue_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.isInstanceType_ = this.isInstanceType_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result2.isInstanceTypeId_ = this.isInstanceTypeId_;
                if ((this.bitField0_ & 0x20) == 32) {
                    this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result2.andArgument_ = this.andArgument_;
                if ((this.bitField0_ & 0x40) == 64) {
                    this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                result2.orArgument_ = this.orArgument_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Expression other) {
                if (other == Expression.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasValueParameterReference()) {
                    this.setValueParameterReference(other.getValueParameterReference());
                }
                if (other.hasConstantValue()) {
                    this.setConstantValue(other.getConstantValue());
                }
                if (other.hasIsInstanceType()) {
                    this.mergeIsInstanceType(other.getIsInstanceType());
                }
                if (other.hasIsInstanceTypeId()) {
                    this.setIsInstanceTypeId(other.getIsInstanceTypeId());
                }
                if (!other.andArgument_.isEmpty()) {
                    if (this.andArgument_.isEmpty()) {
                        this.andArgument_ = other.andArgument_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureAndArgumentIsMutable();
                        this.andArgument_.addAll(other.andArgument_);
                    }
                }
                if (!other.orArgument_.isEmpty()) {
                    if (this.orArgument_.isEmpty()) {
                        this.orArgument_ = other.orArgument_;
                        this.bitField0_ &= 0xFFFFFFBF;
                    } else {
                        this.ensureOrArgumentIsMutable();
                        this.orArgument_.addAll(other.orArgument_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                int i;
                if (this.hasIsInstanceType() && !this.getIsInstanceType().isInitialized()) {
                    return false;
                }
                for (i = 0; i < this.getAndArgumentCount(); ++i) {
                    if (this.getAndArgument(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getOrArgumentCount(); ++i) {
                    if (this.getOrArgument(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Expression parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Expression)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public Builder setValueParameterReference(int value) {
                this.bitField0_ |= 2;
                this.valueParameterReference_ = value;
                return this;
            }

            public Builder setConstantValue(ConstantValue value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.constantValue_ = value;
                return this;
            }

            public boolean hasIsInstanceType() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getIsInstanceType() {
                return this.isInstanceType_;
            }

            public Builder mergeIsInstanceType(Type value) {
                this.isInstanceType_ = (this.bitField0_ & 8) == 8 && this.isInstanceType_ != Type.getDefaultInstance() ? Type.newBuilder(this.isInstanceType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setIsInstanceTypeId(int value) {
                this.bitField0_ |= 0x10;
                this.isInstanceTypeId_ = value;
                return this;
            }

            private void ensureAndArgumentIsMutable() {
                if ((this.bitField0_ & 0x20) != 32) {
                    this.andArgument_ = new ArrayList<Expression>(this.andArgument_);
                    this.bitField0_ |= 0x20;
                }
            }

            public int getAndArgumentCount() {
                return this.andArgument_.size();
            }

            public Expression getAndArgument(int index) {
                return this.andArgument_.get(index);
            }

            private void ensureOrArgumentIsMutable() {
                if ((this.bitField0_ & 0x40) != 64) {
                    this.orArgument_ = new ArrayList<Expression>(this.orArgument_);
                    this.bitField0_ |= 0x40;
                }
            }

            public int getOrArgumentCount() {
                return this.orArgument_.size();
            }

            public Expression getOrArgument(int index) {
                return this.orArgument_.get(index);
            }
        }

        public static enum ConstantValue implements Internal.EnumLite
        {
            TRUE(0, 0),
            FALSE(1, 1),
            NULL(2, 2);

            private static Internal.EnumLiteMap<ConstantValue> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static ConstantValue valueOf(int value) {
                switch (value) {
                    case 0: {
                        return TRUE;
                    }
                    case 1: {
                        return FALSE;
                    }
                    case 2: {
                        return NULL;
                    }
                }
                return null;
            }

            private ConstantValue(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ConstantValue>(){

                    @Override
                    public ConstantValue findValueByNumber(int number) {
                        return ConstantValue.valueOf(number);
                    }
                };
            }
        }
    }

    public static final class Effect
    extends GeneratedMessageLite
    implements ProtoBuf$EffectOrBuilder {
        private static final Effect defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Effect> PARSER;
        private int bitField0_;
        private EffectType effectType_;
        private List<Expression> effectConstructorArgument_;
        private Expression conclusionOfConditionalEffect_;
        private InvocationKind kind_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Effect(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Effect(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Effect getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Effect getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Effect(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block22: while (!done) {
                    Enum value;
                    int rawValue;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block22;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block22;
                            done = true;
                            continue block22;
                        }
                        case 8: {
                            rawValue = input.readEnum();
                            value = EffectType.valueOf(rawValue);
                            if (value == null) {
                                unknownFieldsCodedOutput.writeRawVarint32(tag);
                                unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                continue block22;
                            }
                            this.bitField0_ |= 1;
                            this.effectType_ = value;
                            continue block22;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.effectConstructorArgument_ = new ArrayList<Expression>();
                                mutable_bitField0_ |= 2;
                            }
                            this.effectConstructorArgument_.add(input.readMessage(Expression.PARSER, extensionRegistry));
                            continue block22;
                        }
                        case 26: {
                            Expression.Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.conclusionOfConditionalEffect_.toBuilder();
                            }
                            this.conclusionOfConditionalEffect_ = input.readMessage(Expression.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.conclusionOfConditionalEffect_);
                                this.conclusionOfConditionalEffect_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            continue block22;
                        }
                        case 32: 
                    }
                    rawValue = input.readEnum();
                    value = InvocationKind.valueOf(rawValue);
                    if (value == null) {
                        unknownFieldsCodedOutput.writeRawVarint32(tag);
                        unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                        continue;
                    }
                    this.bitField0_ |= 4;
                    this.kind_ = value;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Effect> getParserForType() {
            return PARSER;
        }

        public boolean hasEffectType() {
            return (this.bitField0_ & 1) == 1;
        }

        public EffectType getEffectType() {
            return this.effectType_;
        }

        public int getEffectConstructorArgumentCount() {
            return this.effectConstructorArgument_.size();
        }

        public Expression getEffectConstructorArgument(int index) {
            return this.effectConstructorArgument_.get(index);
        }

        public boolean hasConclusionOfConditionalEffect() {
            return (this.bitField0_ & 2) == 2;
        }

        public Expression getConclusionOfConditionalEffect() {
            return this.conclusionOfConditionalEffect_;
        }

        public boolean hasKind() {
            return (this.bitField0_ & 4) == 4;
        }

        public InvocationKind getKind() {
            return this.kind_;
        }

        private void initFields() {
            this.effectType_ = EffectType.RETURNS_CONSTANT;
            this.effectConstructorArgument_ = Collections.emptyList();
            this.conclusionOfConditionalEffect_ = Expression.getDefaultInstance();
            this.kind_ = InvocationKind.AT_MOST_ONCE;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getEffectConstructorArgumentCount(); ++i) {
                if (this.getEffectConstructorArgument(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasConclusionOfConditionalEffect() && !this.getConclusionOfConditionalEffect().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.effectType_.getNumber());
            }
            for (int i = 0; i < this.effectConstructorArgument_.size(); ++i) {
                output.writeMessage(2, this.effectConstructorArgument_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(3, this.conclusionOfConditionalEffect_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(4, this.kind_.getNumber());
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeEnumSize(1, this.effectType_.getNumber());
            }
            for (int i = 0; i < this.effectConstructorArgument_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.effectConstructorArgument_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(3, this.conclusionOfConditionalEffect_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeEnumSize(4, this.kind_.getNumber());
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Effect.newBuilder();
        }

        public static Builder newBuilder(Effect prototype) {
            return Effect.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Effect.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Effect>(){

                @Override
                public Effect parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Effect(input, extensionRegistry);
                }
            };
            defaultInstance = new Effect(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Effect, Builder>
        implements ProtoBuf$EffectOrBuilder {
            private int bitField0_;
            private EffectType effectType_ = EffectType.RETURNS_CONSTANT;
            private List<Expression> effectConstructorArgument_ = Collections.emptyList();
            private Expression conclusionOfConditionalEffect_ = Expression.getDefaultInstance();
            private InvocationKind kind_ = InvocationKind.AT_MOST_ONCE;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Effect getDefaultInstanceForType() {
                return Effect.getDefaultInstance();
            }

            @Override
            public Effect build() {
                Effect result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Effect buildPartial() {
                Effect result2 = new Effect(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.effectType_ = this.effectType_;
                if ((this.bitField0_ & 2) == 2) {
                    this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result2.effectConstructorArgument_ = this.effectConstructorArgument_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result2.conclusionOfConditionalEffect_ = this.conclusionOfConditionalEffect_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result2.kind_ = this.kind_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Effect other) {
                if (other == Effect.getDefaultInstance()) {
                    return this;
                }
                if (other.hasEffectType()) {
                    this.setEffectType(other.getEffectType());
                }
                if (!other.effectConstructorArgument_.isEmpty()) {
                    if (this.effectConstructorArgument_.isEmpty()) {
                        this.effectConstructorArgument_ = other.effectConstructorArgument_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureEffectConstructorArgumentIsMutable();
                        this.effectConstructorArgument_.addAll(other.effectConstructorArgument_);
                    }
                }
                if (other.hasConclusionOfConditionalEffect()) {
                    this.mergeConclusionOfConditionalEffect(other.getConclusionOfConditionalEffect());
                }
                if (other.hasKind()) {
                    this.setKind(other.getKind());
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getEffectConstructorArgumentCount(); ++i) {
                    if (this.getEffectConstructorArgument(i).isInitialized()) continue;
                    return false;
                }
                return !this.hasConclusionOfConditionalEffect() || this.getConclusionOfConditionalEffect().isInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Effect parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Effect)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setEffectType(EffectType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.effectType_ = value;
                return this;
            }

            private void ensureEffectConstructorArgumentIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.effectConstructorArgument_ = new ArrayList<Expression>(this.effectConstructorArgument_);
                    this.bitField0_ |= 2;
                }
            }

            public int getEffectConstructorArgumentCount() {
                return this.effectConstructorArgument_.size();
            }

            public Expression getEffectConstructorArgument(int index) {
                return this.effectConstructorArgument_.get(index);
            }

            public boolean hasConclusionOfConditionalEffect() {
                return (this.bitField0_ & 4) == 4;
            }

            public Expression getConclusionOfConditionalEffect() {
                return this.conclusionOfConditionalEffect_;
            }

            public Builder mergeConclusionOfConditionalEffect(Expression value) {
                this.conclusionOfConditionalEffect_ = (this.bitField0_ & 4) == 4 && this.conclusionOfConditionalEffect_ != Expression.getDefaultInstance() ? Expression.newBuilder(this.conclusionOfConditionalEffect_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setKind(InvocationKind value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.kind_ = value;
                return this;
            }
        }

        public static enum InvocationKind implements Internal.EnumLite
        {
            AT_MOST_ONCE(0, 0),
            EXACTLY_ONCE(1, 1),
            AT_LEAST_ONCE(2, 2);

            private static Internal.EnumLiteMap<InvocationKind> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static InvocationKind valueOf(int value) {
                switch (value) {
                    case 0: {
                        return AT_MOST_ONCE;
                    }
                    case 1: {
                        return EXACTLY_ONCE;
                    }
                    case 2: {
                        return AT_LEAST_ONCE;
                    }
                }
                return null;
            }

            private InvocationKind(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<InvocationKind>(){

                    @Override
                    public InvocationKind findValueByNumber(int number) {
                        return InvocationKind.valueOf(number);
                    }
                };
            }
        }

        public static enum EffectType implements Internal.EnumLite
        {
            RETURNS_CONSTANT(0, 0),
            CALLS(1, 1),
            RETURNS_NOT_NULL(2, 2);

            private static Internal.EnumLiteMap<EffectType> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static EffectType valueOf(int value) {
                switch (value) {
                    case 0: {
                        return RETURNS_CONSTANT;
                    }
                    case 1: {
                        return CALLS;
                    }
                    case 2: {
                        return RETURNS_NOT_NULL;
                    }
                }
                return null;
            }

            private EffectType(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<EffectType>(){

                    @Override
                    public EffectType findValueByNumber(int number) {
                        return EffectType.valueOf(number);
                    }
                };
            }
        }
    }

    public static final class Contract
    extends GeneratedMessageLite
    implements ProtoBuf$ContractOrBuilder {
        private static final Contract defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Contract> PARSER;
        private List<Effect> effect_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Contract(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Contract(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Contract getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Contract getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Contract(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block19;
                            done = true;
                            continue block19;
                        }
                        case 10: 
                    }
                    if (!(mutable_bitField0_ & true)) {
                        this.effect_ = new ArrayList<Effect>();
                        mutable_bitField0_ |= true;
                    }
                    this.effect_.add(input.readMessage(Effect.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.effect_ = Collections.unmodifiableList(this.effect_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Contract> getParserForType() {
            return PARSER;
        }

        public int getEffectCount() {
            return this.effect_.size();
        }

        public Effect getEffect(int index) {
            return this.effect_.get(index);
        }

        private void initFields() {
            this.effect_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getEffectCount(); ++i) {
                if (this.getEffect(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.effect_.size(); ++i) {
                output.writeMessage(1, this.effect_.get(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.effect_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.effect_.get(i));
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Contract.newBuilder();
        }

        public static Builder newBuilder(Contract prototype) {
            return Contract.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Contract.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Contract>(){

                @Override
                public Contract parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Contract(input, extensionRegistry);
                }
            };
            defaultInstance = new Contract(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Contract, Builder>
        implements ProtoBuf$ContractOrBuilder {
            private int bitField0_;
            private List<Effect> effect_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Contract getDefaultInstanceForType() {
                return Contract.getDefaultInstance();
            }

            @Override
            public Contract build() {
                Contract result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Contract buildPartial() {
                Contract result2 = new Contract(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.effect_ = Collections.unmodifiableList(this.effect_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.effect_ = this.effect_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Contract other) {
                if (other == Contract.getDefaultInstance()) {
                    return this;
                }
                if (!other.effect_.isEmpty()) {
                    if (this.effect_.isEmpty()) {
                        this.effect_ = other.effect_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureEffectIsMutable();
                        this.effect_.addAll(other.effect_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getEffectCount(); ++i) {
                    if (this.getEffect(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Contract parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Contract)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureEffectIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.effect_ = new ArrayList<Effect>(this.effect_);
                    this.bitField0_ |= 1;
                }
            }

            public int getEffectCount() {
                return this.effect_.size();
            }

            public Effect getEffect(int index) {
                return this.effect_.get(index);
            }
        }
    }

    public static final class PackageFragment
    extends GeneratedMessageLite.ExtendableMessage<PackageFragment>
    implements ProtoBuf$PackageFragmentOrBuilder {
        private static final PackageFragment defaultInstance;
        private final ByteString unknownFields;
        public static Parser<PackageFragment> PARSER;
        private int bitField0_;
        private StringTable strings_;
        private QualifiedNameTable qualifiedNames_;
        private Package package_;
        private List<Class> class__;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private PackageFragment(GeneratedMessageLite.ExtendableBuilder<PackageFragment, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private PackageFragment(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static PackageFragment getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public PackageFragment getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private PackageFragment(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block22: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block22;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block22;
                            done = true;
                            continue block22;
                        }
                        case 10: {
                            GeneratedMessageLite.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.strings_.toBuilder();
                            }
                            this.strings_ = input.readMessage(StringTable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                ((StringTable.Builder)subBuilder).mergeFrom(this.strings_);
                                this.strings_ = ((StringTable.Builder)subBuilder).buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block22;
                        }
                        case 18: {
                            GeneratedMessageLite.Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.qualifiedNames_.toBuilder();
                            }
                            this.qualifiedNames_ = input.readMessage(QualifiedNameTable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                ((QualifiedNameTable.Builder)subBuilder).mergeFrom(this.qualifiedNames_);
                                this.qualifiedNames_ = ((QualifiedNameTable.Builder)subBuilder).buildPartial();
                            }
                            this.bitField0_ |= 2;
                            continue block22;
                        }
                        case 26: {
                            GeneratedMessageLite.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.package_.toBuilder();
                            }
                            this.package_ = input.readMessage(Package.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                ((Package.Builder)subBuilder).mergeFrom(this.package_);
                                this.package_ = ((Package.Builder)subBuilder).buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue block22;
                        }
                        case 34: 
                    }
                    if ((mutable_bitField0_ & 8) != 8) {
                        this.class__ = new ArrayList<Class>();
                        mutable_bitField0_ |= 8;
                    }
                    this.class__.add(input.readMessage(Class.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 8) == 8) {
                    this.class__ = Collections.unmodifiableList(this.class__);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<PackageFragment> getParserForType() {
            return PARSER;
        }

        public boolean hasStrings() {
            return (this.bitField0_ & 1) == 1;
        }

        public StringTable getStrings() {
            return this.strings_;
        }

        public boolean hasQualifiedNames() {
            return (this.bitField0_ & 2) == 2;
        }

        public QualifiedNameTable getQualifiedNames() {
            return this.qualifiedNames_;
        }

        public boolean hasPackage() {
            return (this.bitField0_ & 4) == 4;
        }

        public Package getPackage() {
            return this.package_;
        }

        public List<Class> getClass_List() {
            return this.class__;
        }

        public int getClass_Count() {
            return this.class__.size();
        }

        public Class getClass_(int index) {
            return this.class__.get(index);
        }

        private void initFields() {
            this.strings_ = StringTable.getDefaultInstance();
            this.qualifiedNames_ = QualifiedNameTable.getDefaultInstance();
            this.package_ = Package.getDefaultInstance();
            this.class__ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (this.hasQualifiedNames() && !this.getQualifiedNames().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasPackage() && !this.getPackage().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getClass_Count(); ++i) {
                if (this.getClass_(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, this.strings_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, this.qualifiedNames_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, this.package_);
            }
            for (int i = 0; i < this.class__.size(); ++i) {
                output.writeMessage(4, this.class__.get(i));
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeMessageSize(1, this.strings_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(2, this.qualifiedNames_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(3, this.package_);
            }
            for (int i = 0; i < this.class__.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, this.class__.get(i));
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static PackageFragment parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return PackageFragment.newBuilder();
        }

        public static Builder newBuilder(PackageFragment prototype) {
            return PackageFragment.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return PackageFragment.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<PackageFragment>(){

                @Override
                public PackageFragment parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PackageFragment(input, extensionRegistry);
                }
            };
            defaultInstance = new PackageFragment(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<PackageFragment, Builder>
        implements ProtoBuf$PackageFragmentOrBuilder {
            private int bitField0_;
            private StringTable strings_ = StringTable.getDefaultInstance();
            private QualifiedNameTable qualifiedNames_ = QualifiedNameTable.getDefaultInstance();
            private Package package_ = Package.getDefaultInstance();
            private List<Class> class__ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public PackageFragment getDefaultInstanceForType() {
                return PackageFragment.getDefaultInstance();
            }

            @Override
            public PackageFragment build() {
                PackageFragment result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public PackageFragment buildPartial() {
                PackageFragment result2 = new PackageFragment(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.strings_ = this.strings_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.qualifiedNames_ = this.qualifiedNames_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.package_ = this.package_;
                if ((this.bitField0_ & 8) == 8) {
                    this.class__ = Collections.unmodifiableList(this.class__);
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result2.class__ = this.class__;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(PackageFragment other) {
                if (other == PackageFragment.getDefaultInstance()) {
                    return this;
                }
                if (other.hasStrings()) {
                    this.mergeStrings(other.getStrings());
                }
                if (other.hasQualifiedNames()) {
                    this.mergeQualifiedNames(other.getQualifiedNames());
                }
                if (other.hasPackage()) {
                    this.mergePackage(other.getPackage());
                }
                if (!other.class__.isEmpty()) {
                    if (this.class__.isEmpty()) {
                        this.class__ = other.class__;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureClass_IsMutable();
                        this.class__.addAll(other.class__);
                    }
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (this.hasQualifiedNames() && !this.getQualifiedNames().isInitialized()) {
                    return false;
                }
                if (this.hasPackage() && !this.getPackage().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getClass_Count(); ++i) {
                    if (this.getClass_(i).isInitialized()) continue;
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PackageFragment parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PackageFragment)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder mergeStrings(StringTable value) {
                this.strings_ = (this.bitField0_ & 1) == 1 && this.strings_ != StringTable.getDefaultInstance() ? StringTable.newBuilder(this.strings_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 1;
                return this;
            }

            public boolean hasQualifiedNames() {
                return (this.bitField0_ & 2) == 2;
            }

            public QualifiedNameTable getQualifiedNames() {
                return this.qualifiedNames_;
            }

            public Builder mergeQualifiedNames(QualifiedNameTable value) {
                this.qualifiedNames_ = (this.bitField0_ & 2) == 2 && this.qualifiedNames_ != QualifiedNameTable.getDefaultInstance() ? QualifiedNameTable.newBuilder(this.qualifiedNames_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 2;
                return this;
            }

            public boolean hasPackage() {
                return (this.bitField0_ & 4) == 4;
            }

            public Package getPackage() {
                return this.package_;
            }

            public Builder mergePackage(Package value) {
                this.package_ = (this.bitField0_ & 4) == 4 && this.package_ != Package.getDefaultInstance() ? Package.newBuilder(this.package_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 4;
                return this;
            }

            private void ensureClass_IsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.class__ = new ArrayList<Class>(this.class__);
                    this.bitField0_ |= 8;
                }
            }

            public int getClass_Count() {
                return this.class__.size();
            }

            public Class getClass_(int index) {
                return this.class__.get(index);
            }
        }
    }

    public static final class VersionRequirementTable
    extends GeneratedMessageLite
    implements ProtoBuf$VersionRequirementTableOrBuilder {
        private static final VersionRequirementTable defaultInstance;
        private final ByteString unknownFields;
        public static Parser<VersionRequirementTable> PARSER;
        private List<VersionRequirement> requirement_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private VersionRequirementTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private VersionRequirementTable(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static VersionRequirementTable getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public VersionRequirementTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private VersionRequirementTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block19;
                            done = true;
                            continue block19;
                        }
                        case 10: 
                    }
                    if (!(mutable_bitField0_ & true)) {
                        this.requirement_ = new ArrayList<VersionRequirement>();
                        mutable_bitField0_ |= true;
                    }
                    this.requirement_.add(input.readMessage(VersionRequirement.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.requirement_ = Collections.unmodifiableList(this.requirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<VersionRequirementTable> getParserForType() {
            return PARSER;
        }

        public List<VersionRequirement> getRequirementList() {
            return this.requirement_;
        }

        public int getRequirementCount() {
            return this.requirement_.size();
        }

        private void initFields() {
            this.requirement_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.requirement_.size(); ++i) {
                output.writeMessage(1, this.requirement_.get(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.requirement_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.requirement_.get(i));
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return VersionRequirementTable.newBuilder();
        }

        public static Builder newBuilder(VersionRequirementTable prototype) {
            return VersionRequirementTable.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return VersionRequirementTable.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<VersionRequirementTable>(){

                @Override
                public VersionRequirementTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new VersionRequirementTable(input, extensionRegistry);
                }
            };
            defaultInstance = new VersionRequirementTable(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<VersionRequirementTable, Builder>
        implements ProtoBuf$VersionRequirementTableOrBuilder {
            private int bitField0_;
            private List<VersionRequirement> requirement_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public VersionRequirementTable getDefaultInstanceForType() {
                return VersionRequirementTable.getDefaultInstance();
            }

            @Override
            public VersionRequirementTable build() {
                VersionRequirementTable result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public VersionRequirementTable buildPartial() {
                VersionRequirementTable result2 = new VersionRequirementTable(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.requirement_ = Collections.unmodifiableList(this.requirement_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.requirement_ = this.requirement_;
                return result2;
            }

            @Override
            public Builder mergeFrom(VersionRequirementTable other) {
                if (other == VersionRequirementTable.getDefaultInstance()) {
                    return this;
                }
                if (!other.requirement_.isEmpty()) {
                    if (this.requirement_.isEmpty()) {
                        this.requirement_ = other.requirement_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureRequirementIsMutable();
                        this.requirement_.addAll(other.requirement_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                VersionRequirementTable parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (VersionRequirementTable)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureRequirementIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.requirement_ = new ArrayList<VersionRequirement>(this.requirement_);
                    this.bitField0_ |= 1;
                }
            }
        }
    }

    public static final class VersionRequirement
    extends GeneratedMessageLite
    implements ProtoBuf$VersionRequirementOrBuilder {
        private static final VersionRequirement defaultInstance;
        private final ByteString unknownFields;
        public static Parser<VersionRequirement> PARSER;
        private int bitField0_;
        private int version_;
        private int versionFull_;
        private Level level_;
        private int errorCode_;
        private int message_;
        private VersionKind versionKind_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private VersionRequirement(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private VersionRequirement(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static VersionRequirement getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public VersionRequirement getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private VersionRequirement(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block24: while (!done) {
                    Enum value;
                    int rawValue;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block24;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block24;
                            done = true;
                            continue block24;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.version_ = input.readInt32();
                            continue block24;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.versionFull_ = input.readInt32();
                            continue block24;
                        }
                        case 24: {
                            rawValue = input.readEnum();
                            value = Level.valueOf(rawValue);
                            if (value == null) {
                                unknownFieldsCodedOutput.writeRawVarint32(tag);
                                unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                continue block24;
                            }
                            this.bitField0_ |= 4;
                            this.level_ = value;
                            continue block24;
                        }
                        case 32: {
                            this.bitField0_ |= 8;
                            this.errorCode_ = input.readInt32();
                            continue block24;
                        }
                        case 40: {
                            this.bitField0_ |= 0x10;
                            this.message_ = input.readInt32();
                            continue block24;
                        }
                        case 48: 
                    }
                    rawValue = input.readEnum();
                    value = VersionKind.valueOf(rawValue);
                    if (value == null) {
                        unknownFieldsCodedOutput.writeRawVarint32(tag);
                        unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                        continue;
                    }
                    this.bitField0_ |= 0x20;
                    this.versionKind_ = value;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<VersionRequirement> getParserForType() {
            return PARSER;
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getVersion() {
            return this.version_;
        }

        public boolean hasVersionFull() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getVersionFull() {
            return this.versionFull_;
        }

        public boolean hasLevel() {
            return (this.bitField0_ & 4) == 4;
        }

        public Level getLevel() {
            return this.level_;
        }

        public boolean hasErrorCode() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getErrorCode() {
            return this.errorCode_;
        }

        public boolean hasMessage() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public int getMessage() {
            return this.message_;
        }

        public boolean hasVersionKind() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public VersionKind getVersionKind() {
            return this.versionKind_;
        }

        private void initFields() {
            this.version_ = 0;
            this.versionFull_ = 0;
            this.level_ = Level.ERROR;
            this.errorCode_ = 0;
            this.message_ = 0;
            this.versionKind_ = VersionKind.LANGUAGE_VERSION;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.versionFull_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.level_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeInt32(4, this.errorCode_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeInt32(5, this.message_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeEnum(6, this.versionKind_.getNumber());
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.versionFull_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeEnumSize(3, this.level_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeInt32Size(4, this.errorCode_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeInt32Size(5, this.message_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeEnumSize(6, this.versionKind_.getNumber());
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return VersionRequirement.newBuilder();
        }

        public static Builder newBuilder(VersionRequirement prototype) {
            return VersionRequirement.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return VersionRequirement.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<VersionRequirement>(){

                @Override
                public VersionRequirement parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new VersionRequirement(input, extensionRegistry);
                }
            };
            defaultInstance = new VersionRequirement(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<VersionRequirement, Builder>
        implements ProtoBuf$VersionRequirementOrBuilder {
            private int bitField0_;
            private int version_;
            private int versionFull_;
            private Level level_ = Level.ERROR;
            private int errorCode_;
            private int message_;
            private VersionKind versionKind_ = VersionKind.LANGUAGE_VERSION;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public VersionRequirement getDefaultInstanceForType() {
                return VersionRequirement.getDefaultInstance();
            }

            @Override
            public VersionRequirement build() {
                VersionRequirement result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public VersionRequirement buildPartial() {
                VersionRequirement result2 = new VersionRequirement(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.versionFull_ = this.versionFull_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.level_ = this.level_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.errorCode_ = this.errorCode_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result2.message_ = this.message_;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result2.versionKind_ = this.versionKind_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(VersionRequirement other) {
                if (other == VersionRequirement.getDefaultInstance()) {
                    return this;
                }
                if (other.hasVersion()) {
                    this.setVersion(other.getVersion());
                }
                if (other.hasVersionFull()) {
                    this.setVersionFull(other.getVersionFull());
                }
                if (other.hasLevel()) {
                    this.setLevel(other.getLevel());
                }
                if (other.hasErrorCode()) {
                    this.setErrorCode(other.getErrorCode());
                }
                if (other.hasMessage()) {
                    this.setMessage(other.getMessage());
                }
                if (other.hasVersionKind()) {
                    this.setVersionKind(other.getVersionKind());
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                VersionRequirement parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (VersionRequirement)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setVersion(int value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                return this;
            }

            public Builder setVersionFull(int value) {
                this.bitField0_ |= 2;
                this.versionFull_ = value;
                return this;
            }

            public Builder setLevel(Level value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.level_ = value;
                return this;
            }

            public Builder setErrorCode(int value) {
                this.bitField0_ |= 8;
                this.errorCode_ = value;
                return this;
            }

            public Builder setMessage(int value) {
                this.bitField0_ |= 0x10;
                this.message_ = value;
                return this;
            }

            public Builder setVersionKind(VersionKind value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x20;
                this.versionKind_ = value;
                return this;
            }
        }

        public static enum VersionKind implements Internal.EnumLite
        {
            LANGUAGE_VERSION(0, 0),
            COMPILER_VERSION(1, 1),
            API_VERSION(2, 2);

            private static Internal.EnumLiteMap<VersionKind> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static VersionKind valueOf(int value) {
                switch (value) {
                    case 0: {
                        return LANGUAGE_VERSION;
                    }
                    case 1: {
                        return COMPILER_VERSION;
                    }
                    case 2: {
                        return API_VERSION;
                    }
                }
                return null;
            }

            private VersionKind(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<VersionKind>(){

                    @Override
                    public VersionKind findValueByNumber(int number) {
                        return VersionKind.valueOf(number);
                    }
                };
            }
        }

        public static enum Level implements Internal.EnumLite
        {
            WARNING(0, 0),
            ERROR(1, 1),
            HIDDEN(2, 2);

            private static Internal.EnumLiteMap<Level> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static Level valueOf(int value) {
                switch (value) {
                    case 0: {
                        return WARNING;
                    }
                    case 1: {
                        return ERROR;
                    }
                    case 2: {
                        return HIDDEN;
                    }
                }
                return null;
            }

            private Level(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Level>(){

                    @Override
                    public Level findValueByNumber(int number) {
                        return Level.valueOf(number);
                    }
                };
            }
        }
    }

    public static final class EnumEntry
    extends GeneratedMessageLite.ExtendableMessage<EnumEntry>
    implements ProtoBuf$EnumEntryOrBuilder {
        private static final EnumEntry defaultInstance;
        private final ByteString unknownFields;
        public static Parser<EnumEntry> PARSER;
        private int bitField0_;
        private int name_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private EnumEntry(GeneratedMessageLite.ExtendableBuilder<EnumEntry, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumEntry(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static EnumEntry getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public EnumEntry getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private EnumEntry(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block19;
                            done = true;
                            continue block19;
                        }
                        case 8: 
                    }
                    this.bitField0_ |= 1;
                    this.name_ = input.readInt32();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<EnumEntry> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getName() {
            return this.name_;
        }

        private void initFields() {
            this.name_ = 0;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.name_);
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.name_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return EnumEntry.newBuilder();
        }

        public static Builder newBuilder(EnumEntry prototype) {
            return EnumEntry.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return EnumEntry.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<EnumEntry>(){

                @Override
                public EnumEntry parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new EnumEntry(input, extensionRegistry);
                }
            };
            defaultInstance = new EnumEntry(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<EnumEntry, Builder>
        implements ProtoBuf$EnumEntryOrBuilder {
            private int bitField0_;
            private int name_;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public EnumEntry getDefaultInstanceForType() {
                return EnumEntry.getDefaultInstance();
            }

            @Override
            public EnumEntry build() {
                EnumEntry result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public EnumEntry buildPartial() {
                EnumEntry result2 = new EnumEntry(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.name_ = this.name_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(EnumEntry other) {
                if (other == EnumEntry.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                EnumEntry parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (EnumEntry)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return this;
            }
        }
    }

    public static final class TypeAlias
    extends GeneratedMessageLite.ExtendableMessage<TypeAlias>
    implements ProtoBuf$TypeAliasOrBuilder {
        private static final TypeAlias defaultInstance;
        private final ByteString unknownFields;
        public static Parser<TypeAlias> PARSER;
        private int bitField0_;
        private int flags_;
        private int name_;
        private List<TypeParameter> typeParameter_;
        private Type underlyingType_;
        private int underlyingTypeId_;
        private Type expandedType_;
        private int expandedTypeId_;
        private List<Annotation> annotation_;
        private List<Integer> versionRequirement_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private TypeAlias(GeneratedMessageLite.ExtendableBuilder<TypeAlias, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private TypeAlias(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static TypeAlias getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public TypeAlias getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TypeAlias(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block28: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block28;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block28;
                            done = true;
                            continue block28;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block28;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.name_ = input.readInt32();
                            continue block28;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.typeParameter_ = new ArrayList<TypeParameter>();
                                mutable_bitField0_ |= 4;
                            }
                            this.typeParameter_.add(input.readMessage(TypeParameter.PARSER, extensionRegistry));
                            continue block28;
                        }
                        case 34: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.underlyingType_.toBuilder();
                            }
                            this.underlyingType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.underlyingType_);
                                this.underlyingType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue block28;
                        }
                        case 40: {
                            this.bitField0_ |= 8;
                            this.underlyingTypeId_ = input.readInt32();
                            continue block28;
                        }
                        case 50: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x10) == 16) {
                                subBuilder = this.expandedType_.toBuilder();
                            }
                            this.expandedType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.expandedType_);
                                this.expandedType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x10;
                            continue block28;
                        }
                        case 56: {
                            this.bitField0_ |= 0x20;
                            this.expandedTypeId_ = input.readInt32();
                            continue block28;
                        }
                        case 66: {
                            if ((mutable_bitField0_ & 0x80) != 128) {
                                this.annotation_ = new ArrayList<Annotation>();
                                mutable_bitField0_ |= 0x80;
                            }
                            this.annotation_.add(input.readMessage(Annotation.PARSER, extensionRegistry));
                            continue block28;
                        }
                        case 248: {
                            if ((mutable_bitField0_ & 0x100) != 256) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x100;
                            }
                            this.versionRequirement_.add(input.readInt32());
                            continue block28;
                        }
                        case 250: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 0x100) != 256 && input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 0x100;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 4) == 4) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 0x80) == 128) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                }
                if ((mutable_bitField0_ & 0x100) == 256) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<TypeAlias> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getName() {
            return this.name_;
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public TypeParameter getTypeParameter(int index) {
            return this.typeParameter_.get(index);
        }

        public boolean hasUnderlyingType() {
            return (this.bitField0_ & 4) == 4;
        }

        public Type getUnderlyingType() {
            return this.underlyingType_;
        }

        public boolean hasUnderlyingTypeId() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getUnderlyingTypeId() {
            return this.underlyingTypeId_;
        }

        public boolean hasExpandedType() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public Type getExpandedType() {
            return this.expandedType_;
        }

        public boolean hasExpandedTypeId() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public int getExpandedTypeId() {
            return this.expandedTypeId_;
        }

        public List<Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public Annotation getAnnotation(int index) {
            return this.annotation_.get(index);
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        private void initFields() {
            this.flags_ = 6;
            this.name_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.underlyingType_ = Type.getDefaultInstance();
            this.underlyingTypeId_ = 0;
            this.expandedType_ = Type.getDefaultInstance();
            this.expandedTypeId_ = 0;
            this.annotation_ = Collections.emptyList();
            this.versionRequirement_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            int i;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getTypeParameterCount(); ++i) {
                if (this.getTypeParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasUnderlyingType() && !this.getUnderlyingType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasExpandedType() && !this.getExpandedType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getAnnotationCount(); ++i) {
                if (this.getAnnotation(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.name_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                output.writeMessage(3, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(4, this.underlyingType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeInt32(5, this.underlyingTypeId_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeMessage(6, this.expandedType_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeInt32(7, this.expandedTypeId_);
            }
            for (i = 0; i < this.annotation_.size(); ++i) {
                output.writeMessage(8, this.annotation_.get(i));
            }
            for (i = 0; i < this.versionRequirement_.size(); ++i) {
                output.writeInt32(31, this.versionRequirement_.get(i));
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(3, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(4, this.underlyingType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeInt32Size(5, this.underlyingTypeId_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeMessageSize(6, this.expandedType_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeInt32Size(7, this.expandedTypeId_);
            }
            for (i = 0; i < this.annotation_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(8, this.annotation_.get(i));
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.versionRequirement_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i2));
            }
            size += dataSize;
            size += 2 * this.getVersionRequirementList().size();
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static TypeAlias parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return TypeAlias.newBuilder();
        }

        public static Builder newBuilder(TypeAlias prototype) {
            return TypeAlias.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return TypeAlias.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<TypeAlias>(){

                @Override
                public TypeAlias parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new TypeAlias(input, extensionRegistry);
                }
            };
            defaultInstance = new TypeAlias(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<TypeAlias, Builder>
        implements ProtoBuf$TypeAliasOrBuilder {
            private int bitField0_;
            private int flags_ = 6;
            private int name_;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type underlyingType_ = Type.getDefaultInstance();
            private int underlyingTypeId_;
            private Type expandedType_ = Type.getDefaultInstance();
            private int expandedTypeId_;
            private List<Annotation> annotation_ = Collections.emptyList();
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public TypeAlias getDefaultInstanceForType() {
                return TypeAlias.getDefaultInstance();
            }

            @Override
            public TypeAlias build() {
                TypeAlias result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public TypeAlias buildPartial() {
                TypeAlias result2 = new TypeAlias(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.name_ = this.name_;
                if ((this.bitField0_ & 4) == 4) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result2.typeParameter_ = this.typeParameter_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result2.underlyingType_ = this.underlyingType_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 8;
                }
                result2.underlyingTypeId_ = this.underlyingTypeId_;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x10;
                }
                result2.expandedType_ = this.expandedType_;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result2.expandedTypeId_ = this.expandedTypeId_;
                if ((this.bitField0_ & 0x80) == 128) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= 0xFFFFFF7F;
                }
                result2.annotation_ = this.annotation_;
                if ((this.bitField0_ & 0x100) == 256) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= 0xFFFFFEFF;
                }
                result2.versionRequirement_ = this.versionRequirement_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(TypeAlias other) {
                if (other == TypeAlias.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (!other.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = other.typeParameter_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(other.typeParameter_);
                    }
                }
                if (other.hasUnderlyingType()) {
                    this.mergeUnderlyingType(other.getUnderlyingType());
                }
                if (other.hasUnderlyingTypeId()) {
                    this.setUnderlyingTypeId(other.getUnderlyingTypeId());
                }
                if (other.hasExpandedType()) {
                    this.mergeExpandedType(other.getExpandedType());
                }
                if (other.hasExpandedTypeId()) {
                    this.setExpandedTypeId(other.getExpandedTypeId());
                }
                if (!other.annotation_.isEmpty()) {
                    if (this.annotation_.isEmpty()) {
                        this.annotation_ = other.annotation_;
                        this.bitField0_ &= 0xFFFFFF7F;
                    } else {
                        this.ensureAnnotationIsMutable();
                        this.annotation_.addAll(other.annotation_);
                    }
                }
                if (!other.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = other.versionRequirement_;
                        this.bitField0_ &= 0xFFFFFEFF;
                    } else {
                        this.ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(other.versionRequirement_);
                    }
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                int i;
                if (!this.hasName()) {
                    return false;
                }
                for (i = 0; i < this.getTypeParameterCount(); ++i) {
                    if (this.getTypeParameter(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasUnderlyingType() && !this.getUnderlyingType().isInitialized()) {
                    return false;
                }
                if (this.hasExpandedType() && !this.getExpandedType().isInitialized()) {
                    return false;
                }
                for (i = 0; i < this.getAnnotationCount(); ++i) {
                    if (this.getAnnotation(i).isInitialized()) continue;
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                TypeAlias parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (TypeAlias)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 2;
                this.name_ = value;
                return this;
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.typeParameter_ = new ArrayList<TypeParameter>(this.typeParameter_);
                    this.bitField0_ |= 4;
                }
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeParameter getTypeParameter(int index) {
                return this.typeParameter_.get(index);
            }

            public boolean hasUnderlyingType() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getUnderlyingType() {
                return this.underlyingType_;
            }

            public Builder mergeUnderlyingType(Type value) {
                this.underlyingType_ = (this.bitField0_ & 8) == 8 && this.underlyingType_ != Type.getDefaultInstance() ? Type.newBuilder(this.underlyingType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setUnderlyingTypeId(int value) {
                this.bitField0_ |= 0x10;
                this.underlyingTypeId_ = value;
                return this;
            }

            public boolean hasExpandedType() {
                return (this.bitField0_ & 0x20) == 32;
            }

            public Type getExpandedType() {
                return this.expandedType_;
            }

            public Builder mergeExpandedType(Type value) {
                this.expandedType_ = (this.bitField0_ & 0x20) == 32 && this.expandedType_ != Type.getDefaultInstance() ? Type.newBuilder(this.expandedType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x20;
                return this;
            }

            public Builder setExpandedTypeId(int value) {
                this.bitField0_ |= 0x40;
                this.expandedTypeId_ = value;
                return this;
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 0x80) != 128) {
                    this.annotation_ = new ArrayList<Annotation>(this.annotation_);
                    this.bitField0_ |= 0x80;
                }
            }

            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            public Annotation getAnnotation(int index) {
                return this.annotation_.get(index);
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 0x100) != 256) {
                    this.versionRequirement_ = new ArrayList<Integer>(this.versionRequirement_);
                    this.bitField0_ |= 0x100;
                }
            }
        }
    }

    public static final class ValueParameter
    extends GeneratedMessageLite.ExtendableMessage<ValueParameter>
    implements ProtoBuf$ValueParameterOrBuilder {
        private static final ValueParameter defaultInstance;
        private final ByteString unknownFields;
        public static Parser<ValueParameter> PARSER;
        private int bitField0_;
        private int flags_;
        private int name_;
        private Type type_;
        private int typeId_;
        private Type varargElementType_;
        private int varargElementTypeId_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private ValueParameter(GeneratedMessageLite.ExtendableBuilder<ValueParameter, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private ValueParameter(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static ValueParameter getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public ValueParameter getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private ValueParameter(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block24: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block24;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block24;
                            done = true;
                            continue block24;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block24;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.name_ = input.readInt32();
                            continue block24;
                        }
                        case 26: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.type_.toBuilder();
                            }
                            this.type_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.type_);
                                this.type_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue block24;
                        }
                        case 34: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x10) == 16) {
                                subBuilder = this.varargElementType_.toBuilder();
                            }
                            this.varargElementType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.varargElementType_);
                                this.varargElementType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x10;
                            continue block24;
                        }
                        case 40: {
                            this.bitField0_ |= 8;
                            this.typeId_ = input.readInt32();
                            continue block24;
                        }
                        case 48: 
                    }
                    this.bitField0_ |= 0x20;
                    this.varargElementTypeId_ = input.readInt32();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<ValueParameter> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 4) == 4;
        }

        public Type getType() {
            return this.type_;
        }

        public boolean hasTypeId() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getTypeId() {
            return this.typeId_;
        }

        public boolean hasVarargElementType() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public Type getVarargElementType() {
            return this.varargElementType_;
        }

        public boolean hasVarargElementTypeId() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public int getVarargElementTypeId() {
            return this.varargElementTypeId_;
        }

        private void initFields() {
            this.flags_ = 0;
            this.name_ = 0;
            this.type_ = Type.getDefaultInstance();
            this.typeId_ = 0;
            this.varargElementType_ = Type.getDefaultInstance();
            this.varargElementTypeId_ = 0;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasType() && !this.getType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasVarargElementType() && !this.getVarargElementType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, this.type_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeMessage(4, this.varargElementType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeInt32(5, this.typeId_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeInt32(6, this.varargElementTypeId_);
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(3, this.type_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeMessageSize(4, this.varargElementType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeInt32Size(5, this.typeId_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeInt32Size(6, this.varargElementTypeId_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return ValueParameter.newBuilder();
        }

        public static Builder newBuilder(ValueParameter prototype) {
            return ValueParameter.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return ValueParameter.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<ValueParameter>(){

                @Override
                public ValueParameter parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ValueParameter(input, extensionRegistry);
                }
            };
            defaultInstance = new ValueParameter(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<ValueParameter, Builder>
        implements ProtoBuf$ValueParameterOrBuilder {
            private int bitField0_;
            private int flags_;
            private int name_;
            private Type type_ = Type.getDefaultInstance();
            private int typeId_;
            private Type varargElementType_ = Type.getDefaultInstance();
            private int varargElementTypeId_;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public ValueParameter getDefaultInstanceForType() {
                return ValueParameter.getDefaultInstance();
            }

            @Override
            public ValueParameter build() {
                ValueParameter result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public ValueParameter buildPartial() {
                ValueParameter result2 = new ValueParameter(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.name_ = this.name_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.type_ = this.type_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.typeId_ = this.typeId_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result2.varargElementType_ = this.varargElementType_;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result2.varargElementTypeId_ = this.varargElementTypeId_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(ValueParameter other) {
                if (other == ValueParameter.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasType()) {
                    this.mergeType(other.getType());
                }
                if (other.hasTypeId()) {
                    this.setTypeId(other.getTypeId());
                }
                if (other.hasVarargElementType()) {
                    this.mergeVarargElementType(other.getVarargElementType());
                }
                if (other.hasVarargElementTypeId()) {
                    this.setVarargElementTypeId(other.getVarargElementTypeId());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasName()) {
                    return false;
                }
                if (this.hasType() && !this.getType().isInitialized()) {
                    return false;
                }
                if (this.hasVarargElementType() && !this.getVarargElementType().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ValueParameter parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ValueParameter)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 2;
                this.name_ = value;
                return this;
            }

            public boolean hasType() {
                return (this.bitField0_ & 4) == 4;
            }

            public Type getType() {
                return this.type_;
            }

            public Builder mergeType(Type value) {
                this.type_ = (this.bitField0_ & 4) == 4 && this.type_ != Type.getDefaultInstance() ? Type.newBuilder(this.type_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setTypeId(int value) {
                this.bitField0_ |= 8;
                this.typeId_ = value;
                return this;
            }

            public boolean hasVarargElementType() {
                return (this.bitField0_ & 0x10) == 16;
            }

            public Type getVarargElementType() {
                return this.varargElementType_;
            }

            public Builder mergeVarargElementType(Type value) {
                this.varargElementType_ = (this.bitField0_ & 0x10) == 16 && this.varargElementType_ != Type.getDefaultInstance() ? Type.newBuilder(this.varargElementType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x10;
                return this;
            }

            public Builder setVarargElementTypeId(int value) {
                this.bitField0_ |= 0x20;
                this.varargElementTypeId_ = value;
                return this;
            }
        }
    }

    public static final class Property
    extends GeneratedMessageLite.ExtendableMessage<Property>
    implements ProtoBuf$PropertyOrBuilder {
        private static final Property defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Property> PARSER;
        private int bitField0_;
        private int flags_;
        private int oldFlags_;
        private int name_;
        private Type returnType_;
        private int returnTypeId_;
        private List<TypeParameter> typeParameter_;
        private Type receiverType_;
        private int receiverTypeId_;
        private ValueParameter setterValueParameter_;
        private int getterFlags_;
        private int setterFlags_;
        private List<Integer> versionRequirement_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Property(GeneratedMessageLite.ExtendableBuilder<Property, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Property(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Property getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Property getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Property(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block31: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block31;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block31;
                            done = true;
                            continue block31;
                        }
                        case 8: {
                            this.bitField0_ |= 2;
                            this.oldFlags_ = input.readInt32();
                            continue block31;
                        }
                        case 16: {
                            this.bitField0_ |= 4;
                            this.name_ = input.readInt32();
                            continue block31;
                        }
                        case 26: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.returnType_.toBuilder();
                            }
                            this.returnType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.returnType_);
                                this.returnType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue block31;
                        }
                        case 34: {
                            if ((mutable_bitField0_ & 0x20) != 32) {
                                this.typeParameter_ = new ArrayList<TypeParameter>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.typeParameter_.add(input.readMessage(TypeParameter.PARSER, extensionRegistry));
                            continue block31;
                        }
                        case 42: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x20) == 32) {
                                subBuilder = this.receiverType_.toBuilder();
                            }
                            this.receiverType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.receiverType_);
                                this.receiverType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x20;
                            continue block31;
                        }
                        case 50: {
                            ValueParameter.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x80) == 128) {
                                subBuilder = this.setterValueParameter_.toBuilder();
                            }
                            this.setterValueParameter_ = input.readMessage(ValueParameter.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.setterValueParameter_);
                                this.setterValueParameter_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x80;
                            continue block31;
                        }
                        case 56: {
                            this.bitField0_ |= 0x100;
                            this.getterFlags_ = input.readInt32();
                            continue block31;
                        }
                        case 64: {
                            this.bitField0_ |= 0x200;
                            this.setterFlags_ = input.readInt32();
                            continue block31;
                        }
                        case 72: {
                            this.bitField0_ |= 0x10;
                            this.returnTypeId_ = input.readInt32();
                            continue block31;
                        }
                        case 80: {
                            this.bitField0_ |= 0x40;
                            this.receiverTypeId_ = input.readInt32();
                            continue block31;
                        }
                        case 88: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block31;
                        }
                        case 248: {
                            if ((mutable_bitField0_ & 0x800) != 2048) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x800;
                            }
                            this.versionRequirement_.add(input.readInt32());
                            continue block31;
                        }
                        case 250: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 0x800) != 2048 && input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 0x800;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 0x800) == 2048) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Property> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasOldFlags() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getOldFlags() {
            return this.oldFlags_;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasReturnType() {
            return (this.bitField0_ & 8) == 8;
        }

        public Type getReturnType() {
            return this.returnType_;
        }

        public boolean hasReturnTypeId() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public int getReturnTypeId() {
            return this.returnTypeId_;
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public TypeParameter getTypeParameter(int index) {
            return this.typeParameter_.get(index);
        }

        public boolean hasReceiverType() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public Type getReceiverType() {
            return this.receiverType_;
        }

        public boolean hasReceiverTypeId() {
            return (this.bitField0_ & 0x40) == 64;
        }

        public int getReceiverTypeId() {
            return this.receiverTypeId_;
        }

        public boolean hasSetterValueParameter() {
            return (this.bitField0_ & 0x80) == 128;
        }

        public ValueParameter getSetterValueParameter() {
            return this.setterValueParameter_;
        }

        public boolean hasGetterFlags() {
            return (this.bitField0_ & 0x100) == 256;
        }

        public int getGetterFlags() {
            return this.getterFlags_;
        }

        public boolean hasSetterFlags() {
            return (this.bitField0_ & 0x200) == 512;
        }

        public int getSetterFlags() {
            return this.setterFlags_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        private void initFields() {
            this.flags_ = 518;
            this.oldFlags_ = 2054;
            this.name_ = 0;
            this.returnType_ = Type.getDefaultInstance();
            this.returnTypeId_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.receiverType_ = Type.getDefaultInstance();
            this.receiverTypeId_ = 0;
            this.setterValueParameter_ = ValueParameter.getDefaultInstance();
            this.getterFlags_ = 0;
            this.setterFlags_ = 0;
            this.versionRequirement_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasReturnType() && !this.getReturnType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getTypeParameterCount(); ++i) {
                if (this.getTypeParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasReceiverType() && !this.getReceiverType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasSetterValueParameter() && !this.getSetterValueParameter().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(3, this.returnType_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                output.writeMessage(4, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeMessage(5, this.receiverType_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                output.writeMessage(6, this.setterValueParameter_);
            }
            if ((this.bitField0_ & 0x100) == 256) {
                output.writeInt32(7, this.getterFlags_);
            }
            if ((this.bitField0_ & 0x200) == 512) {
                output.writeInt32(8, this.setterFlags_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeInt32(9, this.returnTypeId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                output.writeInt32(10, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(11, this.flags_);
            }
            for (i = 0; i < this.versionRequirement_.size(); ++i) {
                output.writeInt32(31, this.versionRequirement_.get(i));
            }
            extensionWriter.writeUntil(19000, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(3, this.returnType_);
            }
            for (int i = 0; i < this.typeParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeMessageSize(5, this.receiverType_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                size += CodedOutputStream.computeMessageSize(6, this.setterValueParameter_);
            }
            if ((this.bitField0_ & 0x100) == 256) {
                size += CodedOutputStream.computeInt32Size(7, this.getterFlags_);
            }
            if ((this.bitField0_ & 0x200) == 512) {
                size += CodedOutputStream.computeInt32Size(8, this.setterFlags_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeInt32Size(9, this.returnTypeId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                size += CodedOutputStream.computeInt32Size(10, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(11, this.flags_);
            }
            int dataSize = 0;
            for (int i = 0; i < this.versionRequirement_.size(); ++i) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i));
            }
            size += dataSize;
            size += 2 * this.getVersionRequirementList().size();
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Property.newBuilder();
        }

        public static Builder newBuilder(Property prototype) {
            return Property.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Property.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Property>(){

                @Override
                public Property parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Property(input, extensionRegistry);
                }
            };
            defaultInstance = new Property(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Property, Builder>
        implements ProtoBuf$PropertyOrBuilder {
            private int bitField0_;
            private int flags_ = 518;
            private int oldFlags_ = 2054;
            private int name_;
            private Type returnType_ = Type.getDefaultInstance();
            private int returnTypeId_;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type receiverType_ = Type.getDefaultInstance();
            private int receiverTypeId_;
            private ValueParameter setterValueParameter_ = ValueParameter.getDefaultInstance();
            private int getterFlags_;
            private int setterFlags_;
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Property getDefaultInstanceForType() {
                return Property.getDefaultInstance();
            }

            @Override
            public Property build() {
                Property result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Property buildPartial() {
                Property result2 = new Property(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.oldFlags_ = this.oldFlags_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.name_ = this.name_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.returnType_ = this.returnType_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result2.returnTypeId_ = this.returnTypeId_;
                if ((this.bitField0_ & 0x20) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result2.typeParameter_ = this.typeParameter_;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result2.receiverType_ = this.receiverType_;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result2.receiverTypeId_ = this.receiverTypeId_;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result2.setterValueParameter_ = this.setterValueParameter_;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x100;
                }
                result2.getterFlags_ = this.getterFlags_;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x200;
                }
                result2.setterFlags_ = this.setterFlags_;
                if ((this.bitField0_ & 0x800) == 2048) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= 0xFFFFF7FF;
                }
                result2.versionRequirement_ = this.versionRequirement_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Property other) {
                if (other == Property.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasOldFlags()) {
                    this.setOldFlags(other.getOldFlags());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasReturnType()) {
                    this.mergeReturnType(other.getReturnType());
                }
                if (other.hasReturnTypeId()) {
                    this.setReturnTypeId(other.getReturnTypeId());
                }
                if (!other.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = other.typeParameter_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(other.typeParameter_);
                    }
                }
                if (other.hasReceiverType()) {
                    this.mergeReceiverType(other.getReceiverType());
                }
                if (other.hasReceiverTypeId()) {
                    this.setReceiverTypeId(other.getReceiverTypeId());
                }
                if (other.hasSetterValueParameter()) {
                    this.mergeSetterValueParameter(other.getSetterValueParameter());
                }
                if (other.hasGetterFlags()) {
                    this.setGetterFlags(other.getGetterFlags());
                }
                if (other.hasSetterFlags()) {
                    this.setSetterFlags(other.getSetterFlags());
                }
                if (!other.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = other.versionRequirement_;
                        this.bitField0_ &= 0xFFFFF7FF;
                    } else {
                        this.ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(other.versionRequirement_);
                    }
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasName()) {
                    return false;
                }
                if (this.hasReturnType() && !this.getReturnType().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getTypeParameterCount(); ++i) {
                    if (this.getTypeParameter(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasReceiverType() && !this.getReceiverType().isInitialized()) {
                    return false;
                }
                if (this.hasSetterValueParameter() && !this.getSetterValueParameter().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Property parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Property)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public Builder setOldFlags(int value) {
                this.bitField0_ |= 2;
                this.oldFlags_ = value;
                return this;
            }

            public boolean hasName() {
                return (this.bitField0_ & 4) == 4;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 4;
                this.name_ = value;
                return this;
            }

            public boolean hasReturnType() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getReturnType() {
                return this.returnType_;
            }

            public Builder mergeReturnType(Type value) {
                this.returnType_ = (this.bitField0_ & 8) == 8 && this.returnType_ != Type.getDefaultInstance() ? Type.newBuilder(this.returnType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setReturnTypeId(int value) {
                this.bitField0_ |= 0x10;
                this.returnTypeId_ = value;
                return this;
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 0x20) != 32) {
                    this.typeParameter_ = new ArrayList<TypeParameter>(this.typeParameter_);
                    this.bitField0_ |= 0x20;
                }
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeParameter getTypeParameter(int index) {
                return this.typeParameter_.get(index);
            }

            public boolean hasReceiverType() {
                return (this.bitField0_ & 0x40) == 64;
            }

            public Type getReceiverType() {
                return this.receiverType_;
            }

            public Builder mergeReceiverType(Type value) {
                this.receiverType_ = (this.bitField0_ & 0x40) == 64 && this.receiverType_ != Type.getDefaultInstance() ? Type.newBuilder(this.receiverType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x40;
                return this;
            }

            public Builder setReceiverTypeId(int value) {
                this.bitField0_ |= 0x80;
                this.receiverTypeId_ = value;
                return this;
            }

            public boolean hasSetterValueParameter() {
                return (this.bitField0_ & 0x100) == 256;
            }

            public ValueParameter getSetterValueParameter() {
                return this.setterValueParameter_;
            }

            public Builder mergeSetterValueParameter(ValueParameter value) {
                this.setterValueParameter_ = (this.bitField0_ & 0x100) == 256 && this.setterValueParameter_ != ValueParameter.getDefaultInstance() ? ValueParameter.newBuilder(this.setterValueParameter_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x100;
                return this;
            }

            public Builder setGetterFlags(int value) {
                this.bitField0_ |= 0x200;
                this.getterFlags_ = value;
                return this;
            }

            public Builder setSetterFlags(int value) {
                this.bitField0_ |= 0x400;
                this.setterFlags_ = value;
                return this;
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 0x800) != 2048) {
                    this.versionRequirement_ = new ArrayList<Integer>(this.versionRequirement_);
                    this.bitField0_ |= 0x800;
                }
            }
        }
    }

    public static final class Function
    extends GeneratedMessageLite.ExtendableMessage<Function>
    implements ProtoBuf$FunctionOrBuilder {
        private static final Function defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Function> PARSER;
        private int bitField0_;
        private int flags_;
        private int oldFlags_;
        private int name_;
        private Type returnType_;
        private int returnTypeId_;
        private List<TypeParameter> typeParameter_;
        private Type receiverType_;
        private int receiverTypeId_;
        private List<ValueParameter> valueParameter_;
        private TypeTable typeTable_;
        private List<Integer> versionRequirement_;
        private Contract contract_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Function(GeneratedMessageLite.ExtendableBuilder<Function, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Function(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Function getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Function getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Function(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block31: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block31;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block31;
                            done = true;
                            continue block31;
                        }
                        case 8: {
                            this.bitField0_ |= 2;
                            this.oldFlags_ = input.readInt32();
                            continue block31;
                        }
                        case 16: {
                            this.bitField0_ |= 4;
                            this.name_ = input.readInt32();
                            continue block31;
                        }
                        case 26: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.returnType_.toBuilder();
                            }
                            this.returnType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.returnType_);
                                this.returnType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue block31;
                        }
                        case 34: {
                            if ((mutable_bitField0_ & 0x20) != 32) {
                                this.typeParameter_ = new ArrayList<TypeParameter>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.typeParameter_.add(input.readMessage(TypeParameter.PARSER, extensionRegistry));
                            continue block31;
                        }
                        case 42: {
                            Type.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x20) == 32) {
                                subBuilder = this.receiverType_.toBuilder();
                            }
                            this.receiverType_ = input.readMessage(Type.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.receiverType_);
                                this.receiverType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x20;
                            continue block31;
                        }
                        case 50: {
                            if ((mutable_bitField0_ & 0x100) != 256) {
                                this.valueParameter_ = new ArrayList<ValueParameter>();
                                mutable_bitField0_ |= 0x100;
                            }
                            this.valueParameter_.add(input.readMessage(ValueParameter.PARSER, extensionRegistry));
                            continue block31;
                        }
                        case 56: {
                            this.bitField0_ |= 0x10;
                            this.returnTypeId_ = input.readInt32();
                            continue block31;
                        }
                        case 64: {
                            this.bitField0_ |= 0x40;
                            this.receiverTypeId_ = input.readInt32();
                            continue block31;
                        }
                        case 72: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block31;
                        }
                        case 242: {
                            TypeTable.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x80) == 128) {
                                subBuilder = this.typeTable_.toBuilder();
                            }
                            this.typeTable_ = input.readMessage(TypeTable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.typeTable_);
                                this.typeTable_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x80;
                            continue block31;
                        }
                        case 248: {
                            if ((mutable_bitField0_ & 0x400) != 1024) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x400;
                            }
                            this.versionRequirement_.add(input.readInt32());
                            continue block31;
                        }
                        case 250: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 0x400) != 1024 && input.getBytesUntilLimit() > 0) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x400;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.versionRequirement_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block31;
                        }
                        case 258: 
                    }
                    Contract.Builder subBuilder = null;
                    if ((this.bitField0_ & 0x100) == 256) {
                        subBuilder = this.contract_.toBuilder();
                    }
                    this.contract_ = input.readMessage(Contract.PARSER, extensionRegistry);
                    if (subBuilder != null) {
                        subBuilder.mergeFrom(this.contract_);
                        this.contract_ = subBuilder.buildPartial();
                    }
                    this.bitField0_ |= 0x100;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 0x100) == 256) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                }
                if ((mutable_bitField0_ & 0x400) == 1024) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Function> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasOldFlags() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getOldFlags() {
            return this.oldFlags_;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasReturnType() {
            return (this.bitField0_ & 8) == 8;
        }

        public Type getReturnType() {
            return this.returnType_;
        }

        public boolean hasReturnTypeId() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public int getReturnTypeId() {
            return this.returnTypeId_;
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public TypeParameter getTypeParameter(int index) {
            return this.typeParameter_.get(index);
        }

        public boolean hasReceiverType() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public Type getReceiverType() {
            return this.receiverType_;
        }

        public boolean hasReceiverTypeId() {
            return (this.bitField0_ & 0x40) == 64;
        }

        public int getReceiverTypeId() {
            return this.receiverTypeId_;
        }

        public List<ValueParameter> getValueParameterList() {
            return this.valueParameter_;
        }

        public int getValueParameterCount() {
            return this.valueParameter_.size();
        }

        public ValueParameter getValueParameter(int index) {
            return this.valueParameter_.get(index);
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 0x80) == 128;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasContract() {
            return (this.bitField0_ & 0x100) == 256;
        }

        public Contract getContract() {
            return this.contract_;
        }

        private void initFields() {
            this.flags_ = 6;
            this.oldFlags_ = 6;
            this.name_ = 0;
            this.returnType_ = Type.getDefaultInstance();
            this.returnTypeId_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.receiverType_ = Type.getDefaultInstance();
            this.receiverTypeId_ = 0;
            this.valueParameter_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirement_ = Collections.emptyList();
            this.contract_ = Contract.getDefaultInstance();
        }

        @Override
        public final boolean isInitialized() {
            int i;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasReturnType() && !this.getReturnType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getTypeParameterCount(); ++i) {
                if (this.getTypeParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasReceiverType() && !this.getReceiverType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getValueParameterCount(); ++i) {
                if (this.getValueParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasContract() && !this.getContract().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(3, this.returnType_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                output.writeMessage(4, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeMessage(5, this.receiverType_);
            }
            for (i = 0; i < this.valueParameter_.size(); ++i) {
                output.writeMessage(6, this.valueParameter_.get(i));
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeInt32(7, this.returnTypeId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                output.writeInt32(8, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(9, this.flags_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                output.writeMessage(30, this.typeTable_);
            }
            for (i = 0; i < this.versionRequirement_.size(); ++i) {
                output.writeInt32(31, this.versionRequirement_.get(i));
            }
            if ((this.bitField0_ & 0x100) == 256) {
                output.writeMessage(32, this.contract_);
            }
            extensionWriter.writeUntil(19000, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(3, this.returnType_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, this.typeParameter_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeMessageSize(5, this.receiverType_);
            }
            for (i = 0; i < this.valueParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(6, this.valueParameter_.get(i));
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeInt32Size(7, this.returnTypeId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                size += CodedOutputStream.computeInt32Size(8, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(9, this.flags_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                size += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.versionRequirement_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i2));
            }
            size += dataSize;
            size += 2 * this.getVersionRequirementList().size();
            if ((this.bitField0_ & 0x100) == 256) {
                size += CodedOutputStream.computeMessageSize(32, this.contract_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Function parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Function.newBuilder();
        }

        public static Builder newBuilder(Function prototype) {
            return Function.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Function.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Function>(){

                @Override
                public Function parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Function(input, extensionRegistry);
                }
            };
            defaultInstance = new Function(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Function, Builder>
        implements ProtoBuf$FunctionOrBuilder {
            private int bitField0_;
            private int flags_ = 6;
            private int oldFlags_ = 6;
            private int name_;
            private Type returnType_ = Type.getDefaultInstance();
            private int returnTypeId_;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type receiverType_ = Type.getDefaultInstance();
            private int receiverTypeId_;
            private List<ValueParameter> valueParameter_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private List<Integer> versionRequirement_ = Collections.emptyList();
            private Contract contract_ = Contract.getDefaultInstance();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Function getDefaultInstanceForType() {
                return Function.getDefaultInstance();
            }

            @Override
            public Function build() {
                Function result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Function buildPartial() {
                Function result2 = new Function(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.oldFlags_ = this.oldFlags_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.name_ = this.name_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.returnType_ = this.returnType_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result2.returnTypeId_ = this.returnTypeId_;
                if ((this.bitField0_ & 0x20) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result2.typeParameter_ = this.typeParameter_;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result2.receiverType_ = this.receiverType_;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result2.receiverTypeId_ = this.receiverTypeId_;
                if ((this.bitField0_ & 0x100) == 256) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    this.bitField0_ &= 0xFFFFFEFF;
                }
                result2.valueParameter_ = this.valueParameter_;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x80;
                }
                result2.typeTable_ = this.typeTable_;
                if ((this.bitField0_ & 0x400) == 1024) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= 0xFFFFFBFF;
                }
                result2.versionRequirement_ = this.versionRequirement_;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x100;
                }
                result2.contract_ = this.contract_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Function other) {
                if (other == Function.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasOldFlags()) {
                    this.setOldFlags(other.getOldFlags());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasReturnType()) {
                    this.mergeReturnType(other.getReturnType());
                }
                if (other.hasReturnTypeId()) {
                    this.setReturnTypeId(other.getReturnTypeId());
                }
                if (!other.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = other.typeParameter_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(other.typeParameter_);
                    }
                }
                if (other.hasReceiverType()) {
                    this.mergeReceiverType(other.getReceiverType());
                }
                if (other.hasReceiverTypeId()) {
                    this.setReceiverTypeId(other.getReceiverTypeId());
                }
                if (!other.valueParameter_.isEmpty()) {
                    if (this.valueParameter_.isEmpty()) {
                        this.valueParameter_ = other.valueParameter_;
                        this.bitField0_ &= 0xFFFFFEFF;
                    } else {
                        this.ensureValueParameterIsMutable();
                        this.valueParameter_.addAll(other.valueParameter_);
                    }
                }
                if (other.hasTypeTable()) {
                    this.mergeTypeTable(other.getTypeTable());
                }
                if (!other.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = other.versionRequirement_;
                        this.bitField0_ &= 0xFFFFFBFF;
                    } else {
                        this.ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(other.versionRequirement_);
                    }
                }
                if (other.hasContract()) {
                    this.mergeContract(other.getContract());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                int i;
                if (!this.hasName()) {
                    return false;
                }
                if (this.hasReturnType() && !this.getReturnType().isInitialized()) {
                    return false;
                }
                for (i = 0; i < this.getTypeParameterCount(); ++i) {
                    if (this.getTypeParameter(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasReceiverType() && !this.getReceiverType().isInitialized()) {
                    return false;
                }
                for (i = 0; i < this.getValueParameterCount(); ++i) {
                    if (this.getValueParameter(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                    return false;
                }
                if (this.hasContract() && !this.getContract().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Function parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Function)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public Builder setOldFlags(int value) {
                this.bitField0_ |= 2;
                this.oldFlags_ = value;
                return this;
            }

            public boolean hasName() {
                return (this.bitField0_ & 4) == 4;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 4;
                this.name_ = value;
                return this;
            }

            public boolean hasReturnType() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getReturnType() {
                return this.returnType_;
            }

            public Builder mergeReturnType(Type value) {
                this.returnType_ = (this.bitField0_ & 8) == 8 && this.returnType_ != Type.getDefaultInstance() ? Type.newBuilder(this.returnType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setReturnTypeId(int value) {
                this.bitField0_ |= 0x10;
                this.returnTypeId_ = value;
                return this;
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 0x20) != 32) {
                    this.typeParameter_ = new ArrayList<TypeParameter>(this.typeParameter_);
                    this.bitField0_ |= 0x20;
                }
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeParameter getTypeParameter(int index) {
                return this.typeParameter_.get(index);
            }

            public boolean hasReceiverType() {
                return (this.bitField0_ & 0x40) == 64;
            }

            public Type getReceiverType() {
                return this.receiverType_;
            }

            public Builder mergeReceiverType(Type value) {
                this.receiverType_ = (this.bitField0_ & 0x40) == 64 && this.receiverType_ != Type.getDefaultInstance() ? Type.newBuilder(this.receiverType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x40;
                return this;
            }

            public Builder setReceiverTypeId(int value) {
                this.bitField0_ |= 0x80;
                this.receiverTypeId_ = value;
                return this;
            }

            private void ensureValueParameterIsMutable() {
                if ((this.bitField0_ & 0x100) != 256) {
                    this.valueParameter_ = new ArrayList<ValueParameter>(this.valueParameter_);
                    this.bitField0_ |= 0x100;
                }
            }

            public int getValueParameterCount() {
                return this.valueParameter_.size();
            }

            public ValueParameter getValueParameter(int index) {
                return this.valueParameter_.get(index);
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 0x200) == 512;
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public Builder mergeTypeTable(TypeTable value) {
                this.typeTable_ = (this.bitField0_ & 0x200) == 512 && this.typeTable_ != TypeTable.getDefaultInstance() ? TypeTable.newBuilder(this.typeTable_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x200;
                return this;
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 0x400) != 1024) {
                    this.versionRequirement_ = new ArrayList<Integer>(this.versionRequirement_);
                    this.bitField0_ |= 0x400;
                }
            }

            public boolean hasContract() {
                return (this.bitField0_ & 0x800) == 2048;
            }

            public Contract getContract() {
                return this.contract_;
            }

            public Builder mergeContract(Contract value) {
                this.contract_ = (this.bitField0_ & 0x800) == 2048 && this.contract_ != Contract.getDefaultInstance() ? Contract.newBuilder(this.contract_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x800;
                return this;
            }
        }
    }

    public static final class Constructor
    extends GeneratedMessageLite.ExtendableMessage<Constructor>
    implements ProtoBuf$ConstructorOrBuilder {
        private static final Constructor defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Constructor> PARSER;
        private int bitField0_;
        private int flags_;
        private List<ValueParameter> valueParameter_;
        private List<Integer> versionRequirement_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Constructor(GeneratedMessageLite.ExtendableBuilder<Constructor, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Constructor(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Constructor getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Constructor getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Constructor(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block22: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block22;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block22;
                            done = true;
                            continue block22;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block22;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.valueParameter_ = new ArrayList<ValueParameter>();
                                mutable_bitField0_ |= 2;
                            }
                            this.valueParameter_.add(input.readMessage(ValueParameter.PARSER, extensionRegistry));
                            continue block22;
                        }
                        case 248: {
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 4;
                            }
                            this.versionRequirement_.add(input.readInt32());
                            continue block22;
                        }
                        case 250: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 4;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.versionRequirement_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Constructor> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public List<ValueParameter> getValueParameterList() {
            return this.valueParameter_;
        }

        public int getValueParameterCount() {
            return this.valueParameter_.size();
        }

        public ValueParameter getValueParameter(int index) {
            return this.valueParameter_.get(index);
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        private void initFields() {
            this.flags_ = 6;
            this.valueParameter_ = Collections.emptyList();
            this.versionRequirement_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getValueParameterCount(); ++i) {
                if (this.getValueParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.flags_);
            }
            for (i = 0; i < this.valueParameter_.size(); ++i) {
                output.writeMessage(2, this.valueParameter_.get(i));
            }
            for (i = 0; i < this.versionRequirement_.size(); ++i) {
                output.writeInt32(31, this.versionRequirement_.get(i));
            }
            extensionWriter.writeUntil(19000, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            for (int i = 0; i < this.valueParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.valueParameter_.get(i));
            }
            int dataSize = 0;
            for (int i = 0; i < this.versionRequirement_.size(); ++i) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i));
            }
            size += dataSize;
            size += 2 * this.getVersionRequirementList().size();
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Constructor.newBuilder();
        }

        public static Builder newBuilder(Constructor prototype) {
            return Constructor.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Constructor.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Constructor>(){

                @Override
                public Constructor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Constructor(input, extensionRegistry);
                }
            };
            defaultInstance = new Constructor(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Constructor, Builder>
        implements ProtoBuf$ConstructorOrBuilder {
            private int bitField0_;
            private int flags_ = 6;
            private List<ValueParameter> valueParameter_ = Collections.emptyList();
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Constructor getDefaultInstanceForType() {
                return Constructor.getDefaultInstance();
            }

            @Override
            public Constructor build() {
                Constructor result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Constructor buildPartial() {
                Constructor result2 = new Constructor(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((this.bitField0_ & 2) == 2) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result2.valueParameter_ = this.valueParameter_;
                if ((this.bitField0_ & 4) == 4) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result2.versionRequirement_ = this.versionRequirement_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Constructor other) {
                if (other == Constructor.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (!other.valueParameter_.isEmpty()) {
                    if (this.valueParameter_.isEmpty()) {
                        this.valueParameter_ = other.valueParameter_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureValueParameterIsMutable();
                        this.valueParameter_.addAll(other.valueParameter_);
                    }
                }
                if (!other.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = other.versionRequirement_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(other.versionRequirement_);
                    }
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getValueParameterCount(); ++i) {
                    if (this.getValueParameter(i).isInitialized()) continue;
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Constructor parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Constructor)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            private void ensureValueParameterIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.valueParameter_ = new ArrayList<ValueParameter>(this.valueParameter_);
                    this.bitField0_ |= 2;
                }
            }

            public int getValueParameterCount() {
                return this.valueParameter_.size();
            }

            public ValueParameter getValueParameter(int index) {
                return this.valueParameter_.get(index);
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.versionRequirement_ = new ArrayList<Integer>(this.versionRequirement_);
                    this.bitField0_ |= 4;
                }
            }
        }
    }

    public static final class TypeTable
    extends GeneratedMessageLite
    implements ProtoBuf$TypeTableOrBuilder {
        private static final TypeTable defaultInstance;
        private final ByteString unknownFields;
        public static Parser<TypeTable> PARSER;
        private int bitField0_;
        private List<Type> type_;
        private int firstNullable_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private TypeTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private TypeTable(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static TypeTable getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public TypeTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TypeTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block20: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block20;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block20;
                            done = true;
                            continue block20;
                        }
                        case 10: {
                            if (!(mutable_bitField0_ & true)) {
                                this.type_ = new ArrayList<Type>();
                                mutable_bitField0_ |= true;
                            }
                            this.type_.add(input.readMessage(Type.PARSER, extensionRegistry));
                            continue block20;
                        }
                        case 16: 
                    }
                    this.bitField0_ |= 1;
                    this.firstNullable_ = input.readInt32();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.type_ = Collections.unmodifiableList(this.type_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<TypeTable> getParserForType() {
            return PARSER;
        }

        public List<Type> getTypeList() {
            return this.type_;
        }

        public int getTypeCount() {
            return this.type_.size();
        }

        public Type getType(int index) {
            return this.type_.get(index);
        }

        public boolean hasFirstNullable() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFirstNullable() {
            return this.firstNullable_;
        }

        private void initFields() {
            this.type_ = Collections.emptyList();
            this.firstNullable_ = -1;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getTypeCount(); ++i) {
                if (this.getType(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.type_.size(); ++i) {
                output.writeMessage(1, this.type_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(2, this.firstNullable_);
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.type_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.type_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(2, this.firstNullable_);
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return TypeTable.newBuilder();
        }

        public static Builder newBuilder(TypeTable prototype) {
            return TypeTable.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return TypeTable.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<TypeTable>(){

                @Override
                public TypeTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new TypeTable(input, extensionRegistry);
                }
            };
            defaultInstance = new TypeTable(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<TypeTable, Builder>
        implements ProtoBuf$TypeTableOrBuilder {
            private int bitField0_;
            private List<Type> type_ = Collections.emptyList();
            private int firstNullable_ = -1;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public TypeTable getDefaultInstanceForType() {
                return TypeTable.getDefaultInstance();
            }

            @Override
            public TypeTable build() {
                TypeTable result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public TypeTable buildPartial() {
                TypeTable result2 = new TypeTable(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.type_ = Collections.unmodifiableList(this.type_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.type_ = this.type_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 1;
                }
                result2.firstNullable_ = this.firstNullable_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(TypeTable other) {
                if (other == TypeTable.getDefaultInstance()) {
                    return this;
                }
                if (!other.type_.isEmpty()) {
                    if (this.type_.isEmpty()) {
                        this.type_ = other.type_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureTypeIsMutable();
                        this.type_.addAll(other.type_);
                    }
                }
                if (other.hasFirstNullable()) {
                    this.setFirstNullable(other.getFirstNullable());
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getTypeCount(); ++i) {
                    if (this.getType(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                TypeTable parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (TypeTable)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureTypeIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.type_ = new ArrayList<Type>(this.type_);
                    this.bitField0_ |= 1;
                }
            }

            public int getTypeCount() {
                return this.type_.size();
            }

            public Type getType(int index) {
                return this.type_.get(index);
            }

            public Builder setFirstNullable(int value) {
                this.bitField0_ |= 2;
                this.firstNullable_ = value;
                return this;
            }
        }
    }

    public static final class Package
    extends GeneratedMessageLite.ExtendableMessage<Package>
    implements ProtoBuf$PackageOrBuilder {
        private static final Package defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Package> PARSER;
        private int bitField0_;
        private List<Function> function_;
        private List<Property> property_;
        private List<TypeAlias> typeAlias_;
        private TypeTable typeTable_;
        private VersionRequirementTable versionRequirementTable_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Package(GeneratedMessageLite.ExtendableBuilder<Package, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Package(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Package getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Package getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Package(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block23: while (!done) {
                    GeneratedMessageLite.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block23;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block23;
                            done = true;
                            continue block23;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.function_ = new ArrayList<Function>();
                                mutable_bitField0_ |= 1;
                            }
                            this.function_.add(input.readMessage(Function.PARSER, extensionRegistry));
                            continue block23;
                        }
                        case 34: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.property_ = new ArrayList<Property>();
                                mutable_bitField0_ |= 2;
                            }
                            this.property_.add(input.readMessage(Property.PARSER, extensionRegistry));
                            continue block23;
                        }
                        case 42: {
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.typeAlias_ = new ArrayList<TypeAlias>();
                                mutable_bitField0_ |= 4;
                            }
                            this.typeAlias_.add(input.readMessage(TypeAlias.PARSER, extensionRegistry));
                            continue block23;
                        }
                        case 242: {
                            subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.typeTable_.toBuilder();
                            }
                            this.typeTable_ = input.readMessage(TypeTable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                ((TypeTable.Builder)subBuilder).mergeFrom(this.typeTable_);
                                this.typeTable_ = ((TypeTable.Builder)subBuilder).buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block23;
                        }
                        case 258: 
                    }
                    subBuilder = null;
                    if ((this.bitField0_ & 2) == 2) {
                        subBuilder = this.versionRequirementTable_.toBuilder();
                    }
                    this.versionRequirementTable_ = input.readMessage(VersionRequirementTable.PARSER, extensionRegistry);
                    if (subBuilder != null) {
                        ((VersionRequirementTable.Builder)subBuilder).mergeFrom(this.versionRequirementTable_);
                        this.versionRequirementTable_ = ((VersionRequirementTable.Builder)subBuilder).buildPartial();
                    }
                    this.bitField0_ |= 2;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Package> getParserForType() {
            return PARSER;
        }

        public List<Function> getFunctionList() {
            return this.function_;
        }

        public int getFunctionCount() {
            return this.function_.size();
        }

        public Function getFunction(int index) {
            return this.function_.get(index);
        }

        public List<Property> getPropertyList() {
            return this.property_;
        }

        public int getPropertyCount() {
            return this.property_.size();
        }

        public Property getProperty(int index) {
            return this.property_.get(index);
        }

        public List<TypeAlias> getTypeAliasList() {
            return this.typeAlias_;
        }

        public int getTypeAliasCount() {
            return this.typeAlias_.size();
        }

        public TypeAlias getTypeAlias(int index) {
            return this.typeAlias_.get(index);
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 1) == 1;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public boolean hasVersionRequirementTable() {
            return (this.bitField0_ & 2) == 2;
        }

        public VersionRequirementTable getVersionRequirementTable() {
            return this.versionRequirementTable_;
        }

        private void initFields() {
            this.function_ = Collections.emptyList();
            this.property_ = Collections.emptyList();
            this.typeAlias_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();
        }

        @Override
        public final boolean isInitialized() {
            int i;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (i = 0; i < this.getFunctionCount(); ++i) {
                if (this.getFunction(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getPropertyCount(); ++i) {
                if (this.getProperty(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getTypeAliasCount(); ++i) {
                if (this.getTypeAlias(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            for (i = 0; i < this.function_.size(); ++i) {
                output.writeMessage(3, this.function_.get(i));
            }
            for (i = 0; i < this.property_.size(); ++i) {
                output.writeMessage(4, this.property_.get(i));
            }
            for (i = 0; i < this.typeAlias_.size(); ++i) {
                output.writeMessage(5, this.typeAlias_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(30, this.typeTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(32, this.versionRequirementTable_);
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (i = 0; i < this.function_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(3, this.function_.get(i));
            }
            for (i = 0; i < this.property_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, this.property_.get(i));
            }
            for (i = 0; i < this.typeAlias_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(5, this.typeAlias_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Package parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Package.newBuilder();
        }

        public static Builder newBuilder(Package prototype) {
            return Package.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Package.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Package>(){

                @Override
                public Package parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Package(input, extensionRegistry);
                }
            };
            defaultInstance = new Package(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Package, Builder>
        implements ProtoBuf$PackageOrBuilder {
            private int bitField0_;
            private List<Function> function_ = Collections.emptyList();
            private List<Property> property_ = Collections.emptyList();
            private List<TypeAlias> typeAlias_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private VersionRequirementTable versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Package getDefaultInstanceForType() {
                return Package.getDefaultInstance();
            }

            @Override
            public Package build() {
                Package result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Package buildPartial() {
                Package result2 = new Package(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.function_ = this.function_;
                if ((this.bitField0_ & 2) == 2) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result2.property_ = this.property_;
                if ((this.bitField0_ & 4) == 4) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result2.typeAlias_ = this.typeAlias_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 1;
                }
                result2.typeTable_ = this.typeTable_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 2;
                }
                result2.versionRequirementTable_ = this.versionRequirementTable_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Package other) {
                if (other == Package.getDefaultInstance()) {
                    return this;
                }
                if (!other.function_.isEmpty()) {
                    if (this.function_.isEmpty()) {
                        this.function_ = other.function_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureFunctionIsMutable();
                        this.function_.addAll(other.function_);
                    }
                }
                if (!other.property_.isEmpty()) {
                    if (this.property_.isEmpty()) {
                        this.property_ = other.property_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensurePropertyIsMutable();
                        this.property_.addAll(other.property_);
                    }
                }
                if (!other.typeAlias_.isEmpty()) {
                    if (this.typeAlias_.isEmpty()) {
                        this.typeAlias_ = other.typeAlias_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureTypeAliasIsMutable();
                        this.typeAlias_.addAll(other.typeAlias_);
                    }
                }
                if (other.hasTypeTable()) {
                    this.mergeTypeTable(other.getTypeTable());
                }
                if (other.hasVersionRequirementTable()) {
                    this.mergeVersionRequirementTable(other.getVersionRequirementTable());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                int i;
                for (i = 0; i < this.getFunctionCount(); ++i) {
                    if (this.getFunction(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getPropertyCount(); ++i) {
                    if (this.getProperty(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getTypeAliasCount(); ++i) {
                    if (this.getTypeAlias(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Package parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Package)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureFunctionIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.function_ = new ArrayList<Function>(this.function_);
                    this.bitField0_ |= 1;
                }
            }

            public int getFunctionCount() {
                return this.function_.size();
            }

            public Function getFunction(int index) {
                return this.function_.get(index);
            }

            private void ensurePropertyIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.property_ = new ArrayList<Property>(this.property_);
                    this.bitField0_ |= 2;
                }
            }

            public int getPropertyCount() {
                return this.property_.size();
            }

            public Property getProperty(int index) {
                return this.property_.get(index);
            }

            private void ensureTypeAliasIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.typeAlias_ = new ArrayList<TypeAlias>(this.typeAlias_);
                    this.bitField0_ |= 4;
                }
            }

            public int getTypeAliasCount() {
                return this.typeAlias_.size();
            }

            public TypeAlias getTypeAlias(int index) {
                return this.typeAlias_.get(index);
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 8) == 8;
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public Builder mergeTypeTable(TypeTable value) {
                this.typeTable_ = (this.bitField0_ & 8) == 8 && this.typeTable_ != TypeTable.getDefaultInstance() ? TypeTable.newBuilder(this.typeTable_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeVersionRequirementTable(VersionRequirementTable value) {
                this.versionRequirementTable_ = (this.bitField0_ & 0x10) == 16 && this.versionRequirementTable_ != VersionRequirementTable.getDefaultInstance() ? VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x10;
                return this;
            }
        }
    }

    public static final class Class
    extends GeneratedMessageLite.ExtendableMessage<Class>
    implements ProtoBuf$ClassOrBuilder {
        private static final Class defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Class> PARSER;
        private int bitField0_;
        private int flags_;
        private int fqName_;
        private int companionObjectName_;
        private List<TypeParameter> typeParameter_;
        private List<Type> supertype_;
        private List<Integer> supertypeId_;
        private int supertypeIdMemoizedSerializedSize = -1;
        private List<Integer> nestedClassName_;
        private int nestedClassNameMemoizedSerializedSize = -1;
        private List<Constructor> constructor_;
        private List<Function> function_;
        private List<Property> property_;
        private List<TypeAlias> typeAlias_;
        private List<EnumEntry> enumEntry_;
        private List<Integer> sealedSubclassFqName_;
        private int sealedSubclassFqNameMemoizedSerializedSize = -1;
        private TypeTable typeTable_;
        private List<Integer> versionRequirement_;
        private VersionRequirementTable versionRequirementTable_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Class(GeneratedMessageLite.ExtendableBuilder<Class, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Class(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Class getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Class getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Class(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block38: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block38;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block38;
                            done = true;
                            continue block38;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.flags_ = input.readInt32();
                            continue block38;
                        }
                        case 16: {
                            if ((mutable_bitField0_ & 0x20) != 32) {
                                this.supertypeId_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.supertypeId_.add(input.readInt32());
                            continue block38;
                        }
                        case 18: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 0x20) != 32 && input.getBytesUntilLimit() > 0) {
                                this.supertypeId_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x20;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.supertypeId_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block38;
                        }
                        case 24: {
                            this.bitField0_ |= 2;
                            this.fqName_ = input.readInt32();
                            continue block38;
                        }
                        case 32: {
                            this.bitField0_ |= 4;
                            this.companionObjectName_ = input.readInt32();
                            continue block38;
                        }
                        case 42: {
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.typeParameter_ = new ArrayList<TypeParameter>();
                                mutable_bitField0_ |= 8;
                            }
                            this.typeParameter_.add(input.readMessage(TypeParameter.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 50: {
                            if ((mutable_bitField0_ & 0x10) != 16) {
                                this.supertype_ = new ArrayList<Type>();
                                mutable_bitField0_ |= 0x10;
                            }
                            this.supertype_.add(input.readMessage(Type.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 56: {
                            if ((mutable_bitField0_ & 0x40) != 64) {
                                this.nestedClassName_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x40;
                            }
                            this.nestedClassName_.add(input.readInt32());
                            continue block38;
                        }
                        case 58: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 0x40) != 64 && input.getBytesUntilLimit() > 0) {
                                this.nestedClassName_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x40;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.nestedClassName_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block38;
                        }
                        case 66: {
                            if ((mutable_bitField0_ & 0x80) != 128) {
                                this.constructor_ = new ArrayList<Constructor>();
                                mutable_bitField0_ |= 0x80;
                            }
                            this.constructor_.add(input.readMessage(Constructor.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 74: {
                            if ((mutable_bitField0_ & 0x100) != 256) {
                                this.function_ = new ArrayList<Function>();
                                mutable_bitField0_ |= 0x100;
                            }
                            this.function_.add(input.readMessage(Function.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 82: {
                            if ((mutable_bitField0_ & 0x200) != 512) {
                                this.property_ = new ArrayList<Property>();
                                mutable_bitField0_ |= 0x200;
                            }
                            this.property_.add(input.readMessage(Property.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 90: {
                            if ((mutable_bitField0_ & 0x400) != 1024) {
                                this.typeAlias_ = new ArrayList<TypeAlias>();
                                mutable_bitField0_ |= 0x400;
                            }
                            this.typeAlias_.add(input.readMessage(TypeAlias.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 106: {
                            if ((mutable_bitField0_ & 0x800) != 2048) {
                                this.enumEntry_ = new ArrayList<EnumEntry>();
                                mutable_bitField0_ |= 0x800;
                            }
                            this.enumEntry_.add(input.readMessage(EnumEntry.PARSER, extensionRegistry));
                            continue block38;
                        }
                        case 128: {
                            if ((mutable_bitField0_ & 0x1000) != 4096) {
                                this.sealedSubclassFqName_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x1000;
                            }
                            this.sealedSubclassFqName_.add(input.readInt32());
                            continue block38;
                        }
                        case 130: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 0x1000) != 4096 && input.getBytesUntilLimit() > 0) {
                                this.sealedSubclassFqName_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x1000;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.sealedSubclassFqName_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block38;
                        }
                        case 242: {
                            TypeTable.Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.typeTable_.toBuilder();
                            }
                            this.typeTable_ = input.readMessage(TypeTable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.typeTable_);
                                this.typeTable_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue block38;
                        }
                        case 248: {
                            if ((mutable_bitField0_ & 0x4000) != 16384) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x4000;
                            }
                            this.versionRequirement_.add(input.readInt32());
                            continue block38;
                        }
                        case 250: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 0x4000) != 16384 && input.getBytesUntilLimit() > 0) {
                                this.versionRequirement_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x4000;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.versionRequirement_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block38;
                        }
                        case 258: 
                    }
                    VersionRequirementTable.Builder subBuilder = null;
                    if ((this.bitField0_ & 0x10) == 16) {
                        subBuilder = this.versionRequirementTable_.toBuilder();
                    }
                    this.versionRequirementTable_ = input.readMessage(VersionRequirementTable.PARSER, extensionRegistry);
                    if (subBuilder != null) {
                        subBuilder.mergeFrom(this.versionRequirementTable_);
                        this.versionRequirementTable_ = subBuilder.buildPartial();
                    }
                    this.bitField0_ |= 0x10;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                }
                if ((mutable_bitField0_ & 8) == 8) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 0x10) == 16) {
                    this.supertype_ = Collections.unmodifiableList(this.supertype_);
                }
                if ((mutable_bitField0_ & 0x40) == 64) {
                    this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                }
                if ((mutable_bitField0_ & 0x80) == 128) {
                    this.constructor_ = Collections.unmodifiableList(this.constructor_);
                }
                if ((mutable_bitField0_ & 0x100) == 256) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                }
                if ((mutable_bitField0_ & 0x200) == 512) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                }
                if ((mutable_bitField0_ & 0x400) == 1024) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                }
                if ((mutable_bitField0_ & 0x800) == 2048) {
                    this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                }
                if ((mutable_bitField0_ & 0x1000) == 4096) {
                    this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                }
                if ((mutable_bitField0_ & 0x4000) == 16384) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Class> getParserForType() {
            return PARSER;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getFlags() {
            return this.flags_;
        }

        public boolean hasFqName() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getFqName() {
            return this.fqName_;
        }

        public boolean hasCompanionObjectName() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getCompanionObjectName() {
            return this.companionObjectName_;
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public TypeParameter getTypeParameter(int index) {
            return this.typeParameter_.get(index);
        }

        public List<Type> getSupertypeList() {
            return this.supertype_;
        }

        public int getSupertypeCount() {
            return this.supertype_.size();
        }

        public Type getSupertype(int index) {
            return this.supertype_.get(index);
        }

        public List<Integer> getSupertypeIdList() {
            return this.supertypeId_;
        }

        public List<Integer> getNestedClassNameList() {
            return this.nestedClassName_;
        }

        public List<Constructor> getConstructorList() {
            return this.constructor_;
        }

        public int getConstructorCount() {
            return this.constructor_.size();
        }

        public Constructor getConstructor(int index) {
            return this.constructor_.get(index);
        }

        public List<Function> getFunctionList() {
            return this.function_;
        }

        public int getFunctionCount() {
            return this.function_.size();
        }

        public Function getFunction(int index) {
            return this.function_.get(index);
        }

        public List<Property> getPropertyList() {
            return this.property_;
        }

        public int getPropertyCount() {
            return this.property_.size();
        }

        public Property getProperty(int index) {
            return this.property_.get(index);
        }

        public List<TypeAlias> getTypeAliasList() {
            return this.typeAlias_;
        }

        public int getTypeAliasCount() {
            return this.typeAlias_.size();
        }

        public TypeAlias getTypeAlias(int index) {
            return this.typeAlias_.get(index);
        }

        public List<EnumEntry> getEnumEntryList() {
            return this.enumEntry_;
        }

        public int getEnumEntryCount() {
            return this.enumEntry_.size();
        }

        public EnumEntry getEnumEntry(int index) {
            return this.enumEntry_.get(index);
        }

        public List<Integer> getSealedSubclassFqNameList() {
            return this.sealedSubclassFqName_;
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 8) == 8;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasVersionRequirementTable() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public VersionRequirementTable getVersionRequirementTable() {
            return this.versionRequirementTable_;
        }

        private void initFields() {
            this.flags_ = 6;
            this.fqName_ = 0;
            this.companionObjectName_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.supertype_ = Collections.emptyList();
            this.supertypeId_ = Collections.emptyList();
            this.nestedClassName_ = Collections.emptyList();
            this.constructor_ = Collections.emptyList();
            this.function_ = Collections.emptyList();
            this.property_ = Collections.emptyList();
            this.typeAlias_ = Collections.emptyList();
            this.enumEntry_ = Collections.emptyList();
            this.sealedSubclassFqName_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirement_ = Collections.emptyList();
            this.versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();
        }

        @Override
        public final boolean isInitialized() {
            int i;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasFqName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getTypeParameterCount(); ++i) {
                if (this.getTypeParameter(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getSupertypeCount(); ++i) {
                if (this.getSupertype(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getConstructorCount(); ++i) {
                if (this.getConstructor(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getFunctionCount(); ++i) {
                if (this.getFunction(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getPropertyCount(); ++i) {
                if (this.getProperty(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getTypeAliasCount(); ++i) {
                if (this.getTypeAlias(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (i = 0; i < this.getEnumEntryCount(); ++i) {
                if (this.getEnumEntry(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.flags_);
            }
            if (this.getSupertypeIdList().size() > 0) {
                output.writeRawVarint32(18);
                output.writeRawVarint32(this.supertypeIdMemoizedSerializedSize);
            }
            for (i = 0; i < this.supertypeId_.size(); ++i) {
                output.writeInt32NoTag(this.supertypeId_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(3, this.fqName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeInt32(4, this.companionObjectName_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                output.writeMessage(5, this.typeParameter_.get(i));
            }
            for (i = 0; i < this.supertype_.size(); ++i) {
                output.writeMessage(6, this.supertype_.get(i));
            }
            if (this.getNestedClassNameList().size() > 0) {
                output.writeRawVarint32(58);
                output.writeRawVarint32(this.nestedClassNameMemoizedSerializedSize);
            }
            for (i = 0; i < this.nestedClassName_.size(); ++i) {
                output.writeInt32NoTag(this.nestedClassName_.get(i));
            }
            for (i = 0; i < this.constructor_.size(); ++i) {
                output.writeMessage(8, this.constructor_.get(i));
            }
            for (i = 0; i < this.function_.size(); ++i) {
                output.writeMessage(9, this.function_.get(i));
            }
            for (i = 0; i < this.property_.size(); ++i) {
                output.writeMessage(10, this.property_.get(i));
            }
            for (i = 0; i < this.typeAlias_.size(); ++i) {
                output.writeMessage(11, this.typeAlias_.get(i));
            }
            for (i = 0; i < this.enumEntry_.size(); ++i) {
                output.writeMessage(13, this.enumEntry_.get(i));
            }
            if (this.getSealedSubclassFqNameList().size() > 0) {
                output.writeRawVarint32(130);
                output.writeRawVarint32(this.sealedSubclassFqNameMemoizedSerializedSize);
            }
            for (i = 0; i < this.sealedSubclassFqName_.size(); ++i) {
                output.writeInt32NoTag(this.sealedSubclassFqName_.get(i));
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(30, this.typeTable_);
            }
            for (i = 0; i < this.versionRequirement_.size(); ++i) {
                output.writeInt32(31, this.versionRequirement_.get(i));
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeMessage(32, this.versionRequirementTable_);
            }
            extensionWriter.writeUntil(19000, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int i2;
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            int dataSize = 0;
            for (i2 = 0; i2 < this.supertypeId_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.supertypeId_.get(i2));
            }
            size += dataSize;
            if (!this.getSupertypeIdList().isEmpty()) {
                ++size;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.supertypeIdMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(3, this.fqName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeInt32Size(4, this.companionObjectName_);
            }
            for (i = 0; i < this.typeParameter_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(5, this.typeParameter_.get(i));
            }
            for (i = 0; i < this.supertype_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(6, this.supertype_.get(i));
            }
            dataSize = 0;
            for (i2 = 0; i2 < this.nestedClassName_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.nestedClassName_.get(i2));
            }
            size += dataSize;
            if (!this.getNestedClassNameList().isEmpty()) {
                ++size;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.nestedClassNameMemoizedSerializedSize = dataSize;
            for (i = 0; i < this.constructor_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(8, this.constructor_.get(i));
            }
            for (i = 0; i < this.function_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(9, this.function_.get(i));
            }
            for (i = 0; i < this.property_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(10, this.property_.get(i));
            }
            for (i = 0; i < this.typeAlias_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(11, this.typeAlias_.get(i));
            }
            for (i = 0; i < this.enumEntry_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(13, this.enumEntry_.get(i));
            }
            dataSize = 0;
            for (i2 = 0; i2 < this.sealedSubclassFqName_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.sealedSubclassFqName_.get(i2));
            }
            size += dataSize;
            if (!this.getSealedSubclassFqNameList().isEmpty()) {
                size += 2;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.sealedSubclassFqNameMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            dataSize = 0;
            for (i2 = 0; i2 < this.versionRequirement_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i2));
            }
            size += dataSize;
            size += 2 * this.getVersionRequirementList().size();
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Class parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Class.newBuilder();
        }

        public static Builder newBuilder(Class prototype) {
            return Class.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Class.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Class>(){

                @Override
                public Class parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Class(input, extensionRegistry);
                }
            };
            defaultInstance = new Class(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Class, Builder>
        implements ProtoBuf$ClassOrBuilder {
            private int bitField0_;
            private int flags_ = 6;
            private int fqName_;
            private int companionObjectName_;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private List<Type> supertype_ = Collections.emptyList();
            private List<Integer> supertypeId_ = Collections.emptyList();
            private List<Integer> nestedClassName_ = Collections.emptyList();
            private List<Constructor> constructor_ = Collections.emptyList();
            private List<Function> function_ = Collections.emptyList();
            private List<Property> property_ = Collections.emptyList();
            private List<TypeAlias> typeAlias_ = Collections.emptyList();
            private List<EnumEntry> enumEntry_ = Collections.emptyList();
            private List<Integer> sealedSubclassFqName_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private List<Integer> versionRequirement_ = Collections.emptyList();
            private VersionRequirementTable versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Class getDefaultInstanceForType() {
                return Class.getDefaultInstance();
            }

            @Override
            public Class build() {
                Class result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Class buildPartial() {
                Class result2 = new Class(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.flags_ = this.flags_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.fqName_ = this.fqName_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.companionObjectName_ = this.companionObjectName_;
                if ((this.bitField0_ & 8) == 8) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result2.typeParameter_ = this.typeParameter_;
                if ((this.bitField0_ & 0x10) == 16) {
                    this.supertype_ = Collections.unmodifiableList(this.supertype_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result2.supertype_ = this.supertype_;
                if ((this.bitField0_ & 0x20) == 32) {
                    this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result2.supertypeId_ = this.supertypeId_;
                if ((this.bitField0_ & 0x40) == 64) {
                    this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                result2.nestedClassName_ = this.nestedClassName_;
                if ((this.bitField0_ & 0x80) == 128) {
                    this.constructor_ = Collections.unmodifiableList(this.constructor_);
                    this.bitField0_ &= 0xFFFFFF7F;
                }
                result2.constructor_ = this.constructor_;
                if ((this.bitField0_ & 0x100) == 256) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                    this.bitField0_ &= 0xFFFFFEFF;
                }
                result2.function_ = this.function_;
                if ((this.bitField0_ & 0x200) == 512) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                    this.bitField0_ &= 0xFFFFFDFF;
                }
                result2.property_ = this.property_;
                if ((this.bitField0_ & 0x400) == 1024) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    this.bitField0_ &= 0xFFFFFBFF;
                }
                result2.typeAlias_ = this.typeAlias_;
                if ((this.bitField0_ & 0x800) == 2048) {
                    this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                    this.bitField0_ &= 0xFFFFF7FF;
                }
                result2.enumEntry_ = this.enumEntry_;
                if ((this.bitField0_ & 0x1000) == 4096) {
                    this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                    this.bitField0_ &= 0xFFFFEFFF;
                }
                result2.sealedSubclassFqName_ = this.sealedSubclassFqName_;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 8;
                }
                result2.typeTable_ = this.typeTable_;
                if ((this.bitField0_ & 0x4000) == 16384) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= 0xFFFFBFFF;
                }
                result2.versionRequirement_ = this.versionRequirement_;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x10;
                }
                result2.versionRequirementTable_ = this.versionRequirementTable_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Class other) {
                if (other == Class.getDefaultInstance()) {
                    return this;
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasFqName()) {
                    this.setFqName(other.getFqName());
                }
                if (other.hasCompanionObjectName()) {
                    this.setCompanionObjectName(other.getCompanionObjectName());
                }
                if (!other.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = other.typeParameter_;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(other.typeParameter_);
                    }
                }
                if (!other.supertype_.isEmpty()) {
                    if (this.supertype_.isEmpty()) {
                        this.supertype_ = other.supertype_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureSupertypeIsMutable();
                        this.supertype_.addAll(other.supertype_);
                    }
                }
                if (!other.supertypeId_.isEmpty()) {
                    if (this.supertypeId_.isEmpty()) {
                        this.supertypeId_ = other.supertypeId_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureSupertypeIdIsMutable();
                        this.supertypeId_.addAll(other.supertypeId_);
                    }
                }
                if (!other.nestedClassName_.isEmpty()) {
                    if (this.nestedClassName_.isEmpty()) {
                        this.nestedClassName_ = other.nestedClassName_;
                        this.bitField0_ &= 0xFFFFFFBF;
                    } else {
                        this.ensureNestedClassNameIsMutable();
                        this.nestedClassName_.addAll(other.nestedClassName_);
                    }
                }
                if (!other.constructor_.isEmpty()) {
                    if (this.constructor_.isEmpty()) {
                        this.constructor_ = other.constructor_;
                        this.bitField0_ &= 0xFFFFFF7F;
                    } else {
                        this.ensureConstructorIsMutable();
                        this.constructor_.addAll(other.constructor_);
                    }
                }
                if (!other.function_.isEmpty()) {
                    if (this.function_.isEmpty()) {
                        this.function_ = other.function_;
                        this.bitField0_ &= 0xFFFFFEFF;
                    } else {
                        this.ensureFunctionIsMutable();
                        this.function_.addAll(other.function_);
                    }
                }
                if (!other.property_.isEmpty()) {
                    if (this.property_.isEmpty()) {
                        this.property_ = other.property_;
                        this.bitField0_ &= 0xFFFFFDFF;
                    } else {
                        this.ensurePropertyIsMutable();
                        this.property_.addAll(other.property_);
                    }
                }
                if (!other.typeAlias_.isEmpty()) {
                    if (this.typeAlias_.isEmpty()) {
                        this.typeAlias_ = other.typeAlias_;
                        this.bitField0_ &= 0xFFFFFBFF;
                    } else {
                        this.ensureTypeAliasIsMutable();
                        this.typeAlias_.addAll(other.typeAlias_);
                    }
                }
                if (!other.enumEntry_.isEmpty()) {
                    if (this.enumEntry_.isEmpty()) {
                        this.enumEntry_ = other.enumEntry_;
                        this.bitField0_ &= 0xFFFFF7FF;
                    } else {
                        this.ensureEnumEntryIsMutable();
                        this.enumEntry_.addAll(other.enumEntry_);
                    }
                }
                if (!other.sealedSubclassFqName_.isEmpty()) {
                    if (this.sealedSubclassFqName_.isEmpty()) {
                        this.sealedSubclassFqName_ = other.sealedSubclassFqName_;
                        this.bitField0_ &= 0xFFFFEFFF;
                    } else {
                        this.ensureSealedSubclassFqNameIsMutable();
                        this.sealedSubclassFqName_.addAll(other.sealedSubclassFqName_);
                    }
                }
                if (other.hasTypeTable()) {
                    this.mergeTypeTable(other.getTypeTable());
                }
                if (!other.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = other.versionRequirement_;
                        this.bitField0_ &= 0xFFFFBFFF;
                    } else {
                        this.ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(other.versionRequirement_);
                    }
                }
                if (other.hasVersionRequirementTable()) {
                    this.mergeVersionRequirementTable(other.getVersionRequirementTable());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                int i;
                if (!this.hasFqName()) {
                    return false;
                }
                for (i = 0; i < this.getTypeParameterCount(); ++i) {
                    if (this.getTypeParameter(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getSupertypeCount(); ++i) {
                    if (this.getSupertype(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getConstructorCount(); ++i) {
                    if (this.getConstructor(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getFunctionCount(); ++i) {
                    if (this.getFunction(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getPropertyCount(); ++i) {
                    if (this.getProperty(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getTypeAliasCount(); ++i) {
                    if (this.getTypeAlias(i).isInitialized()) continue;
                    return false;
                }
                for (i = 0; i < this.getEnumEntryCount(); ++i) {
                    if (this.getEnumEntry(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasTypeTable() && !this.getTypeTable().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Class parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Class)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 1;
                this.flags_ = value;
                return this;
            }

            public boolean hasFqName() {
                return (this.bitField0_ & 2) == 2;
            }

            public Builder setFqName(int value) {
                this.bitField0_ |= 2;
                this.fqName_ = value;
                return this;
            }

            public Builder setCompanionObjectName(int value) {
                this.bitField0_ |= 4;
                this.companionObjectName_ = value;
                return this;
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.typeParameter_ = new ArrayList<TypeParameter>(this.typeParameter_);
                    this.bitField0_ |= 8;
                }
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeParameter getTypeParameter(int index) {
                return this.typeParameter_.get(index);
            }

            private void ensureSupertypeIsMutable() {
                if ((this.bitField0_ & 0x10) != 16) {
                    this.supertype_ = new ArrayList<Type>(this.supertype_);
                    this.bitField0_ |= 0x10;
                }
            }

            public int getSupertypeCount() {
                return this.supertype_.size();
            }

            public Type getSupertype(int index) {
                return this.supertype_.get(index);
            }

            private void ensureSupertypeIdIsMutable() {
                if ((this.bitField0_ & 0x20) != 32) {
                    this.supertypeId_ = new ArrayList<Integer>(this.supertypeId_);
                    this.bitField0_ |= 0x20;
                }
            }

            private void ensureNestedClassNameIsMutable() {
                if ((this.bitField0_ & 0x40) != 64) {
                    this.nestedClassName_ = new ArrayList<Integer>(this.nestedClassName_);
                    this.bitField0_ |= 0x40;
                }
            }

            private void ensureConstructorIsMutable() {
                if ((this.bitField0_ & 0x80) != 128) {
                    this.constructor_ = new ArrayList<Constructor>(this.constructor_);
                    this.bitField0_ |= 0x80;
                }
            }

            public int getConstructorCount() {
                return this.constructor_.size();
            }

            public Constructor getConstructor(int index) {
                return this.constructor_.get(index);
            }

            private void ensureFunctionIsMutable() {
                if ((this.bitField0_ & 0x100) != 256) {
                    this.function_ = new ArrayList<Function>(this.function_);
                    this.bitField0_ |= 0x100;
                }
            }

            public int getFunctionCount() {
                return this.function_.size();
            }

            public Function getFunction(int index) {
                return this.function_.get(index);
            }

            private void ensurePropertyIsMutable() {
                if ((this.bitField0_ & 0x200) != 512) {
                    this.property_ = new ArrayList<Property>(this.property_);
                    this.bitField0_ |= 0x200;
                }
            }

            public int getPropertyCount() {
                return this.property_.size();
            }

            public Property getProperty(int index) {
                return this.property_.get(index);
            }

            private void ensureTypeAliasIsMutable() {
                if ((this.bitField0_ & 0x400) != 1024) {
                    this.typeAlias_ = new ArrayList<TypeAlias>(this.typeAlias_);
                    this.bitField0_ |= 0x400;
                }
            }

            public int getTypeAliasCount() {
                return this.typeAlias_.size();
            }

            public TypeAlias getTypeAlias(int index) {
                return this.typeAlias_.get(index);
            }

            private void ensureEnumEntryIsMutable() {
                if ((this.bitField0_ & 0x800) != 2048) {
                    this.enumEntry_ = new ArrayList<EnumEntry>(this.enumEntry_);
                    this.bitField0_ |= 0x800;
                }
            }

            public int getEnumEntryCount() {
                return this.enumEntry_.size();
            }

            public EnumEntry getEnumEntry(int index) {
                return this.enumEntry_.get(index);
            }

            private void ensureSealedSubclassFqNameIsMutable() {
                if ((this.bitField0_ & 0x1000) != 4096) {
                    this.sealedSubclassFqName_ = new ArrayList<Integer>(this.sealedSubclassFqName_);
                    this.bitField0_ |= 0x1000;
                }
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 0x2000) == 8192;
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public Builder mergeTypeTable(TypeTable value) {
                this.typeTable_ = (this.bitField0_ & 0x2000) == 8192 && this.typeTable_ != TypeTable.getDefaultInstance() ? TypeTable.newBuilder(this.typeTable_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x2000;
                return this;
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 0x4000) != 16384) {
                    this.versionRequirement_ = new ArrayList<Integer>(this.versionRequirement_);
                    this.bitField0_ |= 0x4000;
                }
            }

            public Builder mergeVersionRequirementTable(VersionRequirementTable value) {
                this.versionRequirementTable_ = (this.bitField0_ & 0x8000) == 32768 && this.versionRequirementTable_ != VersionRequirementTable.getDefaultInstance() ? VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x8000;
                return this;
            }
        }

        public static enum Kind implements Internal.EnumLite
        {
            CLASS(0, 0),
            INTERFACE(1, 1),
            ENUM_CLASS(2, 2),
            ENUM_ENTRY(3, 3),
            ANNOTATION_CLASS(4, 4),
            OBJECT(5, 5),
            COMPANION_OBJECT(6, 6);

            private static Internal.EnumLiteMap<Kind> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static Kind valueOf(int value) {
                switch (value) {
                    case 0: {
                        return CLASS;
                    }
                    case 1: {
                        return INTERFACE;
                    }
                    case 2: {
                        return ENUM_CLASS;
                    }
                    case 3: {
                        return ENUM_ENTRY;
                    }
                    case 4: {
                        return ANNOTATION_CLASS;
                    }
                    case 5: {
                        return OBJECT;
                    }
                    case 6: {
                        return COMPANION_OBJECT;
                    }
                }
                return null;
            }

            private Kind(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Kind>(){

                    @Override
                    public Kind findValueByNumber(int number) {
                        return Kind.valueOf(number);
                    }
                };
            }
        }
    }

    public static final class TypeParameter
    extends GeneratedMessageLite.ExtendableMessage<TypeParameter>
    implements ProtoBuf$TypeParameterOrBuilder {
        private static final TypeParameter defaultInstance;
        private final ByteString unknownFields;
        public static Parser<TypeParameter> PARSER;
        private int bitField0_;
        private int id_;
        private int name_;
        private boolean reified_;
        private Variance variance_;
        private List<Type> upperBound_;
        private List<Integer> upperBoundId_;
        private int upperBoundIdMemoizedSerializedSize = -1;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private TypeParameter(GeneratedMessageLite.ExtendableBuilder<TypeParameter, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private TypeParameter(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static TypeParameter getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public TypeParameter getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TypeParameter(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block25: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block25;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block25;
                            done = true;
                            continue block25;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.id_ = input.readInt32();
                            continue block25;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.name_ = input.readInt32();
                            continue block25;
                        }
                        case 24: {
                            this.bitField0_ |= 4;
                            this.reified_ = input.readBool();
                            continue block25;
                        }
                        case 32: {
                            int rawValue = input.readEnum();
                            Variance value = Variance.valueOf(rawValue);
                            if (value == null) {
                                unknownFieldsCodedOutput.writeRawVarint32(tag);
                                unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                continue block25;
                            }
                            this.bitField0_ |= 8;
                            this.variance_ = value;
                            continue block25;
                        }
                        case 42: {
                            if ((mutable_bitField0_ & 0x10) != 16) {
                                this.upperBound_ = new ArrayList<Type>();
                                mutable_bitField0_ |= 0x10;
                            }
                            this.upperBound_.add(input.readMessage(Type.PARSER, extensionRegistry));
                            continue block25;
                        }
                        case 48: {
                            if ((mutable_bitField0_ & 0x20) != 32) {
                                this.upperBoundId_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.upperBoundId_.add(input.readInt32());
                            continue block25;
                        }
                        case 50: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 0x20) != 32 && input.getBytesUntilLimit() > 0) {
                        this.upperBoundId_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 0x20;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.upperBoundId_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 0x10) == 16) {
                    this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
                }
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<TypeParameter> getParserForType() {
            return PARSER;
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getId() {
            return this.id_;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasReified() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getReified() {
            return this.reified_;
        }

        public boolean hasVariance() {
            return (this.bitField0_ & 8) == 8;
        }

        public Variance getVariance() {
            return this.variance_;
        }

        public List<Type> getUpperBoundList() {
            return this.upperBound_;
        }

        public int getUpperBoundCount() {
            return this.upperBound_.size();
        }

        public Type getUpperBound(int index) {
            return this.upperBound_.get(index);
        }

        public List<Integer> getUpperBoundIdList() {
            return this.upperBoundId_;
        }

        private void initFields() {
            this.id_ = 0;
            this.name_ = 0;
            this.reified_ = false;
            this.variance_ = Variance.INV;
            this.upperBound_ = Collections.emptyList();
            this.upperBoundId_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getUpperBoundCount(); ++i) {
                if (this.getUpperBound(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.reified_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(4, this.variance_.getNumber());
            }
            for (i = 0; i < this.upperBound_.size(); ++i) {
                output.writeMessage(5, this.upperBound_.get(i));
            }
            if (this.getUpperBoundIdList().size() > 0) {
                output.writeRawVarint32(50);
                output.writeRawVarint32(this.upperBoundIdMemoizedSerializedSize);
            }
            for (i = 0; i < this.upperBoundId_.size(); ++i) {
                output.writeInt32NoTag(this.upperBoundId_.get(i));
            }
            extensionWriter.writeUntil(1000, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBoolSize(3, this.reified_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeEnumSize(4, this.variance_.getNumber());
            }
            for (int i = 0; i < this.upperBound_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(5, this.upperBound_.get(i));
            }
            int dataSize = 0;
            for (int i = 0; i < this.upperBoundId_.size(); ++i) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.upperBoundId_.get(i));
            }
            size += dataSize;
            if (!this.getUpperBoundIdList().isEmpty()) {
                ++size;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.upperBoundIdMemoizedSerializedSize = dataSize;
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return TypeParameter.newBuilder();
        }

        public static Builder newBuilder(TypeParameter prototype) {
            return TypeParameter.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return TypeParameter.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<TypeParameter>(){

                @Override
                public TypeParameter parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new TypeParameter(input, extensionRegistry);
                }
            };
            defaultInstance = new TypeParameter(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<TypeParameter, Builder>
        implements ProtoBuf$TypeParameterOrBuilder {
            private int bitField0_;
            private int id_;
            private int name_;
            private boolean reified_;
            private Variance variance_ = Variance.INV;
            private List<Type> upperBound_ = Collections.emptyList();
            private List<Integer> upperBoundId_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public TypeParameter getDefaultInstanceForType() {
                return TypeParameter.getDefaultInstance();
            }

            @Override
            public TypeParameter build() {
                TypeParameter result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public TypeParameter buildPartial() {
                TypeParameter result2 = new TypeParameter(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.id_ = this.id_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result2.name_ = this.name_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result2.reified_ = this.reified_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result2.variance_ = this.variance_;
                if ((this.bitField0_ & 0x10) == 16) {
                    this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result2.upperBound_ = this.upperBound_;
                if ((this.bitField0_ & 0x20) == 32) {
                    this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result2.upperBoundId_ = this.upperBoundId_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(TypeParameter other) {
                if (other == TypeParameter.getDefaultInstance()) {
                    return this;
                }
                if (other.hasId()) {
                    this.setId(other.getId());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasReified()) {
                    this.setReified(other.getReified());
                }
                if (other.hasVariance()) {
                    this.setVariance(other.getVariance());
                }
                if (!other.upperBound_.isEmpty()) {
                    if (this.upperBound_.isEmpty()) {
                        this.upperBound_ = other.upperBound_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureUpperBoundIsMutable();
                        this.upperBound_.addAll(other.upperBound_);
                    }
                }
                if (!other.upperBoundId_.isEmpty()) {
                    if (this.upperBoundId_.isEmpty()) {
                        this.upperBoundId_ = other.upperBoundId_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureUpperBoundIdIsMutable();
                        this.upperBoundId_.addAll(other.upperBoundId_);
                    }
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasId()) {
                    return false;
                }
                if (!this.hasName()) {
                    return false;
                }
                for (int i = 0; i < this.getUpperBoundCount(); ++i) {
                    if (this.getUpperBound(i).isInitialized()) continue;
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                TypeParameter parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (TypeParameter)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public Builder setId(int value) {
                this.bitField0_ |= 1;
                this.id_ = value;
                return this;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            public Builder setName(int value) {
                this.bitField0_ |= 2;
                this.name_ = value;
                return this;
            }

            public Builder setReified(boolean value) {
                this.bitField0_ |= 4;
                this.reified_ = value;
                return this;
            }

            public Builder setVariance(Variance value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.variance_ = value;
                return this;
            }

            private void ensureUpperBoundIsMutable() {
                if ((this.bitField0_ & 0x10) != 16) {
                    this.upperBound_ = new ArrayList<Type>(this.upperBound_);
                    this.bitField0_ |= 0x10;
                }
            }

            public int getUpperBoundCount() {
                return this.upperBound_.size();
            }

            public Type getUpperBound(int index) {
                return this.upperBound_.get(index);
            }

            private void ensureUpperBoundIdIsMutable() {
                if ((this.bitField0_ & 0x20) != 32) {
                    this.upperBoundId_ = new ArrayList<Integer>(this.upperBoundId_);
                    this.bitField0_ |= 0x20;
                }
            }
        }

        public static enum Variance implements Internal.EnumLite
        {
            IN(0, 0),
            OUT(1, 1),
            INV(2, 2);

            private static Internal.EnumLiteMap<Variance> internalValueMap;
            private final int value;

            @Override
            public final int getNumber() {
                return this.value;
            }

            public static Variance valueOf(int value) {
                switch (value) {
                    case 0: {
                        return IN;
                    }
                    case 1: {
                        return OUT;
                    }
                    case 2: {
                        return INV;
                    }
                }
                return null;
            }

            private Variance(int index, int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Variance>(){

                    @Override
                    public Variance findValueByNumber(int number) {
                        return Variance.valueOf(number);
                    }
                };
            }
        }
    }

    public static final class Type
    extends GeneratedMessageLite.ExtendableMessage<Type>
    implements ProtoBuf$TypeOrBuilder {
        private static final Type defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Type> PARSER;
        private int bitField0_;
        private List<Argument> argument_;
        private boolean nullable_;
        private int flexibleTypeCapabilitiesId_;
        private Type flexibleUpperBound_;
        private int flexibleUpperBoundId_;
        private int className_;
        private int typeParameter_;
        private int typeParameterName_;
        private int typeAliasName_;
        private Type outerType_;
        private int outerTypeId_;
        private Type abbreviatedType_;
        private int abbreviatedTypeId_;
        private int flags_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Type(GeneratedMessageLite.ExtendableBuilder<Type, ?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Type(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Type getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Type getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Type(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block32: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block32;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block32;
                            done = true;
                            continue block32;
                        }
                        case 8: {
                            this.bitField0_ |= 0x1000;
                            this.flags_ = input.readInt32();
                            continue block32;
                        }
                        case 18: {
                            if (!(mutable_bitField0_ & true)) {
                                this.argument_ = new ArrayList<Argument>();
                                mutable_bitField0_ |= true;
                            }
                            this.argument_.add(input.readMessage(Argument.PARSER, extensionRegistry));
                            continue block32;
                        }
                        case 24: {
                            this.bitField0_ |= 1;
                            this.nullable_ = input.readBool();
                            continue block32;
                        }
                        case 32: {
                            this.bitField0_ |= 2;
                            this.flexibleTypeCapabilitiesId_ = input.readInt32();
                            continue block32;
                        }
                        case 42: {
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.flexibleUpperBound_.toBuilder();
                            }
                            this.flexibleUpperBound_ = input.readMessage(PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.flexibleUpperBound_);
                                this.flexibleUpperBound_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue block32;
                        }
                        case 48: {
                            this.bitField0_ |= 0x10;
                            this.className_ = input.readInt32();
                            continue block32;
                        }
                        case 56: {
                            this.bitField0_ |= 0x20;
                            this.typeParameter_ = input.readInt32();
                            continue block32;
                        }
                        case 64: {
                            this.bitField0_ |= 8;
                            this.flexibleUpperBoundId_ = input.readInt32();
                            continue block32;
                        }
                        case 72: {
                            this.bitField0_ |= 0x40;
                            this.typeParameterName_ = input.readInt32();
                            continue block32;
                        }
                        case 82: {
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 0x100) == 256) {
                                subBuilder = this.outerType_.toBuilder();
                            }
                            this.outerType_ = input.readMessage(PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.outerType_);
                                this.outerType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x100;
                            continue block32;
                        }
                        case 88: {
                            this.bitField0_ |= 0x200;
                            this.outerTypeId_ = input.readInt32();
                            continue block32;
                        }
                        case 96: {
                            this.bitField0_ |= 0x80;
                            this.typeAliasName_ = input.readInt32();
                            continue block32;
                        }
                        case 106: {
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 0x400) == 1024) {
                                subBuilder = this.abbreviatedType_.toBuilder();
                            }
                            this.abbreviatedType_ = input.readMessage(PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.abbreviatedType_);
                                this.abbreviatedType_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x400;
                            continue block32;
                        }
                        case 112: 
                    }
                    this.bitField0_ |= 0x800;
                    this.abbreviatedTypeId_ = input.readInt32();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Type> getParserForType() {
            return PARSER;
        }

        public List<Argument> getArgumentList() {
            return this.argument_;
        }

        public int getArgumentCount() {
            return this.argument_.size();
        }

        public Argument getArgument(int index) {
            return this.argument_.get(index);
        }

        public boolean hasNullable() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getNullable() {
            return this.nullable_;
        }

        public boolean hasFlexibleTypeCapabilitiesId() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getFlexibleTypeCapabilitiesId() {
            return this.flexibleTypeCapabilitiesId_;
        }

        public boolean hasFlexibleUpperBound() {
            return (this.bitField0_ & 4) == 4;
        }

        public Type getFlexibleUpperBound() {
            return this.flexibleUpperBound_;
        }

        public boolean hasFlexibleUpperBoundId() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getFlexibleUpperBoundId() {
            return this.flexibleUpperBoundId_;
        }

        public boolean hasClassName() {
            return (this.bitField0_ & 0x10) == 16;
        }

        public int getClassName() {
            return this.className_;
        }

        public boolean hasTypeParameter() {
            return (this.bitField0_ & 0x20) == 32;
        }

        public int getTypeParameter() {
            return this.typeParameter_;
        }

        public boolean hasTypeParameterName() {
            return (this.bitField0_ & 0x40) == 64;
        }

        public int getTypeParameterName() {
            return this.typeParameterName_;
        }

        public boolean hasTypeAliasName() {
            return (this.bitField0_ & 0x80) == 128;
        }

        public int getTypeAliasName() {
            return this.typeAliasName_;
        }

        public boolean hasOuterType() {
            return (this.bitField0_ & 0x100) == 256;
        }

        public Type getOuterType() {
            return this.outerType_;
        }

        public boolean hasOuterTypeId() {
            return (this.bitField0_ & 0x200) == 512;
        }

        public int getOuterTypeId() {
            return this.outerTypeId_;
        }

        public boolean hasAbbreviatedType() {
            return (this.bitField0_ & 0x400) == 1024;
        }

        public Type getAbbreviatedType() {
            return this.abbreviatedType_;
        }

        public boolean hasAbbreviatedTypeId() {
            return (this.bitField0_ & 0x800) == 2048;
        }

        public int getAbbreviatedTypeId() {
            return this.abbreviatedTypeId_;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 0x1000) == 4096;
        }

        public int getFlags() {
            return this.flags_;
        }

        private void initFields() {
            this.argument_ = Collections.emptyList();
            this.nullable_ = false;
            this.flexibleTypeCapabilitiesId_ = 0;
            this.flexibleUpperBound_ = Type.getDefaultInstance();
            this.flexibleUpperBoundId_ = 0;
            this.className_ = 0;
            this.typeParameter_ = 0;
            this.typeParameterName_ = 0;
            this.typeAliasName_ = 0;
            this.outerType_ = Type.getDefaultInstance();
            this.outerTypeId_ = 0;
            this.abbreviatedType_ = Type.getDefaultInstance();
            this.abbreviatedTypeId_ = 0;
            this.flags_ = 0;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getArgumentCount(); ++i) {
                if (this.getArgument(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasFlexibleUpperBound() && !this.getFlexibleUpperBound().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasOuterType() && !this.getOuterType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasAbbreviatedType() && !this.getAbbreviatedType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            GeneratedMessageLite.ExtendableMessage.ExtensionWriter extensionWriter = this.newExtensionWriter();
            if ((this.bitField0_ & 0x1000) == 4096) {
                output.writeInt32(1, this.flags_);
            }
            for (int i = 0; i < this.argument_.size(); ++i) {
                output.writeMessage(2, this.argument_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(3, this.nullable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(4, this.flexibleTypeCapabilitiesId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(5, this.flexibleUpperBound_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                output.writeInt32(6, this.className_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                output.writeInt32(7, this.typeParameter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeInt32(8, this.flexibleUpperBoundId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                output.writeInt32(9, this.typeParameterName_);
            }
            if ((this.bitField0_ & 0x100) == 256) {
                output.writeMessage(10, this.outerType_);
            }
            if ((this.bitField0_ & 0x200) == 512) {
                output.writeInt32(11, this.outerTypeId_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                output.writeInt32(12, this.typeAliasName_);
            }
            if ((this.bitField0_ & 0x400) == 1024) {
                output.writeMessage(13, this.abbreviatedType_);
            }
            if ((this.bitField0_ & 0x800) == 2048) {
                output.writeInt32(14, this.abbreviatedTypeId_);
            }
            extensionWriter.writeUntil(200, output);
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 0x1000) == 4096) {
                size += CodedOutputStream.computeInt32Size(1, this.flags_);
            }
            for (int i = 0; i < this.argument_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.argument_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeBoolSize(3, this.nullable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(4, this.flexibleTypeCapabilitiesId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(5, this.flexibleUpperBound_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += CodedOutputStream.computeInt32Size(6, this.className_);
            }
            if ((this.bitField0_ & 0x20) == 32) {
                size += CodedOutputStream.computeInt32Size(7, this.typeParameter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeInt32Size(8, this.flexibleUpperBoundId_);
            }
            if ((this.bitField0_ & 0x40) == 64) {
                size += CodedOutputStream.computeInt32Size(9, this.typeParameterName_);
            }
            if ((this.bitField0_ & 0x100) == 256) {
                size += CodedOutputStream.computeMessageSize(10, this.outerType_);
            }
            if ((this.bitField0_ & 0x200) == 512) {
                size += CodedOutputStream.computeInt32Size(11, this.outerTypeId_);
            }
            if ((this.bitField0_ & 0x80) == 128) {
                size += CodedOutputStream.computeInt32Size(12, this.typeAliasName_);
            }
            if ((this.bitField0_ & 0x400) == 1024) {
                size += CodedOutputStream.computeMessageSize(13, this.abbreviatedType_);
            }
            if ((this.bitField0_ & 0x800) == 2048) {
                size += CodedOutputStream.computeInt32Size(14, this.abbreviatedTypeId_);
            }
            size += this.extensionsSerializedSize();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Type.newBuilder();
        }

        public static Builder newBuilder(Type prototype) {
            return Type.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Type.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Type>(){

                @Override
                public Type parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Type(input, extensionRegistry);
                }
            };
            defaultInstance = new Type(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.ExtendableBuilder<Type, Builder>
        implements ProtoBuf$TypeOrBuilder {
            private int bitField0_;
            private List<Argument> argument_ = Collections.emptyList();
            private boolean nullable_;
            private int flexibleTypeCapabilitiesId_;
            private Type flexibleUpperBound_ = Type.getDefaultInstance();
            private int flexibleUpperBoundId_;
            private int className_;
            private int typeParameter_;
            private int typeParameterName_;
            private int typeAliasName_;
            private Type outerType_ = Type.getDefaultInstance();
            private int outerTypeId_;
            private Type abbreviatedType_ = Type.getDefaultInstance();
            private int abbreviatedTypeId_;
            private int flags_;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Type getDefaultInstanceForType() {
                return Type.getDefaultInstance();
            }

            @Override
            public Type build() {
                Type result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Type buildPartial() {
                Type result2 = new Type(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.argument_ = this.argument_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 1;
                }
                result2.nullable_ = this.nullable_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result2.flexibleTypeCapabilitiesId_ = this.flexibleTypeCapabilitiesId_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result2.flexibleUpperBound_ = this.flexibleUpperBound_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 8;
                }
                result2.flexibleUpperBoundId_ = this.flexibleUpperBoundId_;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x10;
                }
                result2.className_ = this.className_;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result2.typeParameter_ = this.typeParameter_;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result2.typeParameterName_ = this.typeParameterName_;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result2.typeAliasName_ = this.typeAliasName_;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x100;
                }
                result2.outerType_ = this.outerType_;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x200;
                }
                result2.outerTypeId_ = this.outerTypeId_;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x400;
                }
                result2.abbreviatedType_ = this.abbreviatedType_;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x800;
                }
                result2.abbreviatedTypeId_ = this.abbreviatedTypeId_;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x1000;
                }
                result2.flags_ = this.flags_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Type other) {
                if (other == Type.getDefaultInstance()) {
                    return this;
                }
                if (!other.argument_.isEmpty()) {
                    if (this.argument_.isEmpty()) {
                        this.argument_ = other.argument_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureArgumentIsMutable();
                        this.argument_.addAll(other.argument_);
                    }
                }
                if (other.hasNullable()) {
                    this.setNullable(other.getNullable());
                }
                if (other.hasFlexibleTypeCapabilitiesId()) {
                    this.setFlexibleTypeCapabilitiesId(other.getFlexibleTypeCapabilitiesId());
                }
                if (other.hasFlexibleUpperBound()) {
                    this.mergeFlexibleUpperBound(other.getFlexibleUpperBound());
                }
                if (other.hasFlexibleUpperBoundId()) {
                    this.setFlexibleUpperBoundId(other.getFlexibleUpperBoundId());
                }
                if (other.hasClassName()) {
                    this.setClassName(other.getClassName());
                }
                if (other.hasTypeParameter()) {
                    this.setTypeParameter(other.getTypeParameter());
                }
                if (other.hasTypeParameterName()) {
                    this.setTypeParameterName(other.getTypeParameterName());
                }
                if (other.hasTypeAliasName()) {
                    this.setTypeAliasName(other.getTypeAliasName());
                }
                if (other.hasOuterType()) {
                    this.mergeOuterType(other.getOuterType());
                }
                if (other.hasOuterTypeId()) {
                    this.setOuterTypeId(other.getOuterTypeId());
                }
                if (other.hasAbbreviatedType()) {
                    this.mergeAbbreviatedType(other.getAbbreviatedType());
                }
                if (other.hasAbbreviatedTypeId()) {
                    this.setAbbreviatedTypeId(other.getAbbreviatedTypeId());
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                this.mergeExtensionFields(other);
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getArgumentCount(); ++i) {
                    if (this.getArgument(i).isInitialized()) continue;
                    return false;
                }
                if (this.hasFlexibleUpperBound() && !this.getFlexibleUpperBound().isInitialized()) {
                    return false;
                }
                if (this.hasOuterType() && !this.getOuterType().isInitialized()) {
                    return false;
                }
                if (this.hasAbbreviatedType() && !this.getAbbreviatedType().isInitialized()) {
                    return false;
                }
                return this.extensionsAreInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Type parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Type)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureArgumentIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.argument_ = new ArrayList<Argument>(this.argument_);
                    this.bitField0_ |= 1;
                }
            }

            public int getArgumentCount() {
                return this.argument_.size();
            }

            public Argument getArgument(int index) {
                return this.argument_.get(index);
            }

            public Builder setNullable(boolean value) {
                this.bitField0_ |= 2;
                this.nullable_ = value;
                return this;
            }

            public Builder setFlexibleTypeCapabilitiesId(int value) {
                this.bitField0_ |= 4;
                this.flexibleTypeCapabilitiesId_ = value;
                return this;
            }

            public boolean hasFlexibleUpperBound() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getFlexibleUpperBound() {
                return this.flexibleUpperBound_;
            }

            public Builder mergeFlexibleUpperBound(Type value) {
                this.flexibleUpperBound_ = (this.bitField0_ & 8) == 8 && this.flexibleUpperBound_ != Type.getDefaultInstance() ? Type.newBuilder(this.flexibleUpperBound_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setFlexibleUpperBoundId(int value) {
                this.bitField0_ |= 0x10;
                this.flexibleUpperBoundId_ = value;
                return this;
            }

            public Builder setClassName(int value) {
                this.bitField0_ |= 0x20;
                this.className_ = value;
                return this;
            }

            public Builder setTypeParameter(int value) {
                this.bitField0_ |= 0x40;
                this.typeParameter_ = value;
                return this;
            }

            public Builder setTypeParameterName(int value) {
                this.bitField0_ |= 0x80;
                this.typeParameterName_ = value;
                return this;
            }

            public Builder setTypeAliasName(int value) {
                this.bitField0_ |= 0x100;
                this.typeAliasName_ = value;
                return this;
            }

            public boolean hasOuterType() {
                return (this.bitField0_ & 0x200) == 512;
            }

            public Type getOuterType() {
                return this.outerType_;
            }

            public Builder mergeOuterType(Type value) {
                this.outerType_ = (this.bitField0_ & 0x200) == 512 && this.outerType_ != Type.getDefaultInstance() ? Type.newBuilder(this.outerType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x200;
                return this;
            }

            public Builder setOuterTypeId(int value) {
                this.bitField0_ |= 0x400;
                this.outerTypeId_ = value;
                return this;
            }

            public boolean hasAbbreviatedType() {
                return (this.bitField0_ & 0x800) == 2048;
            }

            public Type getAbbreviatedType() {
                return this.abbreviatedType_;
            }

            public Builder mergeAbbreviatedType(Type value) {
                this.abbreviatedType_ = (this.bitField0_ & 0x800) == 2048 && this.abbreviatedType_ != Type.getDefaultInstance() ? Type.newBuilder(this.abbreviatedType_).mergeFrom(value).buildPartial() : value;
                this.bitField0_ |= 0x800;
                return this;
            }

            public Builder setAbbreviatedTypeId(int value) {
                this.bitField0_ |= 0x1000;
                this.abbreviatedTypeId_ = value;
                return this;
            }

            public Builder setFlags(int value) {
                this.bitField0_ |= 0x2000;
                this.flags_ = value;
                return this;
            }
        }

        public static final class Argument
        extends GeneratedMessageLite
        implements ProtoBuf$Type$ArgumentOrBuilder {
            private static final Argument defaultInstance;
            private final ByteString unknownFields;
            public static Parser<Argument> PARSER;
            private int bitField0_;
            private Projection projection_;
            private Type type_;
            private int typeId_;
            private byte memoizedIsInitialized = (byte)-1;
            private int memoizedSerializedSize = -1;

            private Argument(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.unknownFields = builder.getUnknownFields();
            }

            private Argument(boolean noInit) {
                this.unknownFields = ByteString.EMPTY;
            }

            public static Argument getDefaultInstance() {
                return defaultInstance;
            }

            @Override
            public Argument getDefaultInstanceForType() {
                return defaultInstance;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Argument(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.initFields();
                boolean mutable_bitField0_ = false;
                ByteString.Output unknownFieldsOutput = ByteString.newOutput();
                CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
                try {
                    boolean done = false;
                    block21: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block21;
                            }
                            default: {
                                if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block21;
                                done = true;
                                continue block21;
                            }
                            case 8: {
                                int rawValue = input.readEnum();
                                Projection value = Projection.valueOf(rawValue);
                                if (value == null) {
                                    unknownFieldsCodedOutput.writeRawVarint32(tag);
                                    unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                    continue block21;
                                }
                                this.bitField0_ |= 1;
                                this.projection_ = value;
                                continue block21;
                            }
                            case 18: {
                                kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Builder subBuilder = null;
                                if ((this.bitField0_ & 2) == 2) {
                                    subBuilder = this.type_.toBuilder();
                                }
                                this.type_ = input.readMessage(PARSER, extensionRegistry);
                                if (subBuilder != null) {
                                    subBuilder.mergeFrom(this.type_);
                                    this.type_ = subBuilder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                                continue block21;
                            }
                            case 24: 
                        }
                        this.bitField0_ |= 4;
                        this.typeId_ = input.readInt32();
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                }
                finally {
                    try {
                        unknownFieldsCodedOutput.flush();
                    }
                    catch (IOException iOException) {
                    }
                    finally {
                        this.unknownFields = unknownFieldsOutput.toByteString();
                    }
                    this.makeExtensionsImmutable();
                }
            }

            public Parser<Argument> getParserForType() {
                return PARSER;
            }

            public boolean hasProjection() {
                return (this.bitField0_ & 1) == 1;
            }

            public Projection getProjection() {
                return this.projection_;
            }

            public boolean hasType() {
                return (this.bitField0_ & 2) == 2;
            }

            public Type getType() {
                return this.type_;
            }

            public boolean hasTypeId() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getTypeId() {
                return this.typeId_;
            }

            private void initFields() {
                this.projection_ = Projection.INV;
                this.type_ = Type.getDefaultInstance();
                this.typeId_ = 0;
            }

            @Override
            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (this.hasType() && !this.getType().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }

            @Override
            public void writeTo(CodedOutputStream output) throws IOException {
                this.getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.writeEnum(1, this.projection_.getNumber());
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeMessage(2, this.type_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.writeInt32(3, this.typeId_);
                }
                output.writeRawBytes(this.unknownFields);
            }

            @Override
            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size += CodedOutputStream.computeEnumSize(1, this.projection_.getNumber());
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeMessageSize(2, this.type_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size += CodedOutputStream.computeInt32Size(3, this.typeId_);
                }
                this.memoizedSerializedSize = size += this.unknownFields.size();
                return size;
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override
            public Builder newBuilderForType() {
                return Argument.newBuilder();
            }

            public static Builder newBuilder(Argument prototype) {
                return Argument.newBuilder().mergeFrom(prototype);
            }

            @Override
            public Builder toBuilder() {
                return Argument.newBuilder(this);
            }

            static {
                PARSER = new AbstractParser<Argument>(){

                    @Override
                    public Argument parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new Argument(input, extensionRegistry);
                    }
                };
                defaultInstance = new Argument(true);
                defaultInstance.initFields();
            }

            public static final class Builder
            extends GeneratedMessageLite.Builder<Argument, Builder>
            implements ProtoBuf$Type$ArgumentOrBuilder {
                private int bitField0_;
                private Projection projection_ = Projection.INV;
                private Type type_ = Type.getDefaultInstance();
                private int typeId_;

                private Builder() {
                    this.maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                }

                private static Builder create() {
                    return new Builder();
                }

                @Override
                public Builder clone() {
                    return Builder.create().mergeFrom(this.buildPartial());
                }

                @Override
                public Argument getDefaultInstanceForType() {
                    return Argument.getDefaultInstance();
                }

                @Override
                public Argument build() {
                    Argument result2 = this.buildPartial();
                    if (!result2.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result2);
                    }
                    return result2;
                }

                public Argument buildPartial() {
                    Argument result2 = new Argument(this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ |= 1;
                    }
                    result2.projection_ = this.projection_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result2.type_ = this.type_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    result2.typeId_ = this.typeId_;
                    result2.bitField0_ = to_bitField0_;
                    return result2;
                }

                @Override
                public Builder mergeFrom(Argument other) {
                    if (other == Argument.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasProjection()) {
                        this.setProjection(other.getProjection());
                    }
                    if (other.hasType()) {
                        this.mergeType(other.getType());
                    }
                    if (other.hasTypeId()) {
                        this.setTypeId(other.getTypeId());
                    }
                    this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                    return this;
                }

                @Override
                public final boolean isInitialized() {
                    return !this.hasType() || this.getType().isInitialized();
                }

                @Override
                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Argument parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (Argument)e.getUnfinishedMessage();
                        throw e;
                    }
                    finally {
                        if (parsedMessage != null) {
                            this.mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }

                public Builder setProjection(Projection value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.projection_ = value;
                    return this;
                }

                public boolean hasType() {
                    return (this.bitField0_ & 2) == 2;
                }

                public Type getType() {
                    return this.type_;
                }

                public Builder mergeType(Type value) {
                    this.type_ = (this.bitField0_ & 2) == 2 && this.type_ != Type.getDefaultInstance() ? Type.newBuilder(this.type_).mergeFrom(value).buildPartial() : value;
                    this.bitField0_ |= 2;
                    return this;
                }

                public Builder setTypeId(int value) {
                    this.bitField0_ |= 4;
                    this.typeId_ = value;
                    return this;
                }
            }

            public static enum Projection implements Internal.EnumLite
            {
                IN(0, 0),
                OUT(1, 1),
                INV(2, 2),
                STAR(3, 3);

                private static Internal.EnumLiteMap<Projection> internalValueMap;
                private final int value;

                @Override
                public final int getNumber() {
                    return this.value;
                }

                public static Projection valueOf(int value) {
                    switch (value) {
                        case 0: {
                            return IN;
                        }
                        case 1: {
                            return OUT;
                        }
                        case 2: {
                            return INV;
                        }
                        case 3: {
                            return STAR;
                        }
                    }
                    return null;
                }

                private Projection(int index, int value) {
                    this.value = value;
                }

                static {
                    internalValueMap = new Internal.EnumLiteMap<Projection>(){

                        @Override
                        public Projection findValueByNumber(int number) {
                            return Projection.valueOf(number);
                        }
                    };
                }
            }
        }
    }

    public static final class Annotation
    extends GeneratedMessageLite
    implements ProtoBuf$AnnotationOrBuilder {
        private static final Annotation defaultInstance;
        private final ByteString unknownFields;
        public static Parser<Annotation> PARSER;
        private int bitField0_;
        private int id_;
        private List<Argument> argument_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private Annotation(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Annotation(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static Annotation getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public Annotation getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Annotation(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            int mutable_bitField0_ = 0;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block20: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block20;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block20;
                            done = true;
                            continue block20;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.id_ = input.readInt32();
                            continue block20;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.argument_ = new ArrayList<Argument>();
                        mutable_bitField0_ |= 2;
                    }
                    this.argument_.add(input.readMessage(Argument.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<Annotation> getParserForType() {
            return PARSER;
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getId() {
            return this.id_;
        }

        public List<Argument> getArgumentList() {
            return this.argument_;
        }

        public int getArgumentCount() {
            return this.argument_.size();
        }

        public Argument getArgument(int index) {
            return this.argument_.get(index);
        }

        private void initFields() {
            this.id_ = 0;
            this.argument_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgumentCount(); ++i) {
                if (this.getArgument(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.id_);
            }
            for (int i = 0; i < this.argument_.size(); ++i) {
                output.writeMessage(2, this.argument_.get(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.id_);
            }
            for (int i = 0; i < this.argument_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.argument_.get(i));
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return Annotation.newBuilder();
        }

        public static Builder newBuilder(Annotation prototype) {
            return Annotation.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return Annotation.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<Annotation>(){

                @Override
                public Annotation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Annotation(input, extensionRegistry);
                }
            };
            defaultInstance = new Annotation(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Annotation, Builder>
        implements ProtoBuf$AnnotationOrBuilder {
            private int bitField0_;
            private int id_;
            private List<Argument> argument_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public Annotation getDefaultInstanceForType() {
                return Annotation.getDefaultInstance();
            }

            @Override
            public Annotation build() {
                Annotation result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public Annotation buildPartial() {
                Annotation result2 = new Annotation(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result2.id_ = this.id_;
                if ((this.bitField0_ & 2) == 2) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result2.argument_ = this.argument_;
                result2.bitField0_ = to_bitField0_;
                return result2;
            }

            @Override
            public Builder mergeFrom(Annotation other) {
                if (other == Annotation.getDefaultInstance()) {
                    return this;
                }
                if (other.hasId()) {
                    this.setId(other.getId());
                }
                if (!other.argument_.isEmpty()) {
                    if (this.argument_.isEmpty()) {
                        this.argument_ = other.argument_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureArgumentIsMutable();
                        this.argument_.addAll(other.argument_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasId()) {
                    return false;
                }
                for (int i = 0; i < this.getArgumentCount(); ++i) {
                    if (this.getArgument(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Annotation parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Annotation)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public Builder setId(int value) {
                this.bitField0_ |= 1;
                this.id_ = value;
                return this;
            }

            private void ensureArgumentIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.argument_ = new ArrayList<Argument>(this.argument_);
                    this.bitField0_ |= 2;
                }
            }

            public int getArgumentCount() {
                return this.argument_.size();
            }

            public Argument getArgument(int index) {
                return this.argument_.get(index);
            }
        }

        public static final class Argument
        extends GeneratedMessageLite
        implements ProtoBuf$Annotation$ArgumentOrBuilder {
            private static final Argument defaultInstance;
            private final ByteString unknownFields;
            public static Parser<Argument> PARSER;
            private int bitField0_;
            private int nameId_;
            private Value value_;
            private byte memoizedIsInitialized = (byte)-1;
            private int memoizedSerializedSize = -1;

            private Argument(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.unknownFields = builder.getUnknownFields();
            }

            private Argument(boolean noInit) {
                this.unknownFields = ByteString.EMPTY;
            }

            public static Argument getDefaultInstance() {
                return defaultInstance;
            }

            @Override
            public Argument getDefaultInstanceForType() {
                return defaultInstance;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Argument(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.initFields();
                boolean mutable_bitField0_ = false;
                ByteString.Output unknownFieldsOutput = ByteString.newOutput();
                CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
                try {
                    boolean done = false;
                    block20: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block20;
                            }
                            default: {
                                if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block20;
                                done = true;
                                continue block20;
                            }
                            case 8: {
                                this.bitField0_ |= 1;
                                this.nameId_ = input.readInt32();
                                continue block20;
                            }
                            case 18: 
                        }
                        Value.Builder subBuilder = null;
                        if ((this.bitField0_ & 2) == 2) {
                            subBuilder = this.value_.toBuilder();
                        }
                        this.value_ = input.readMessage(Value.PARSER, extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom(this.value_);
                            this.value_ = subBuilder.buildPartial();
                        }
                        this.bitField0_ |= 2;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                }
                finally {
                    try {
                        unknownFieldsCodedOutput.flush();
                    }
                    catch (IOException iOException) {
                    }
                    finally {
                        this.unknownFields = unknownFieldsOutput.toByteString();
                    }
                    this.makeExtensionsImmutable();
                }
            }

            public Parser<Argument> getParserForType() {
                return PARSER;
            }

            public boolean hasNameId() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getNameId() {
                return this.nameId_;
            }

            public boolean hasValue() {
                return (this.bitField0_ & 2) == 2;
            }

            public Value getValue() {
                return this.value_;
            }

            private void initFields() {
                this.nameId_ = 0;
                this.value_ = Value.getDefaultInstance();
            }

            @Override
            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!this.hasNameId()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (!this.hasValue()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (!this.getValue().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }

            @Override
            public void writeTo(CodedOutputStream output) throws IOException {
                this.getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.writeInt32(1, this.nameId_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeMessage(2, this.value_);
                }
                output.writeRawBytes(this.unknownFields);
            }

            @Override
            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size += CodedOutputStream.computeInt32Size(1, this.nameId_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeMessageSize(2, this.value_);
                }
                this.memoizedSerializedSize = size += this.unknownFields.size();
                return size;
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override
            public Builder newBuilderForType() {
                return Argument.newBuilder();
            }

            public static Builder newBuilder(Argument prototype) {
                return Argument.newBuilder().mergeFrom(prototype);
            }

            @Override
            public Builder toBuilder() {
                return Argument.newBuilder(this);
            }

            static {
                PARSER = new AbstractParser<Argument>(){

                    @Override
                    public Argument parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new Argument(input, extensionRegistry);
                    }
                };
                defaultInstance = new Argument(true);
                defaultInstance.initFields();
            }

            public static final class Builder
            extends GeneratedMessageLite.Builder<Argument, Builder>
            implements ProtoBuf$Annotation$ArgumentOrBuilder {
                private int bitField0_;
                private int nameId_;
                private Value value_ = Value.getDefaultInstance();

                private Builder() {
                    this.maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                }

                private static Builder create() {
                    return new Builder();
                }

                @Override
                public Builder clone() {
                    return Builder.create().mergeFrom(this.buildPartial());
                }

                @Override
                public Argument getDefaultInstanceForType() {
                    return Argument.getDefaultInstance();
                }

                @Override
                public Argument build() {
                    Argument result2 = this.buildPartial();
                    if (!result2.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result2);
                    }
                    return result2;
                }

                public Argument buildPartial() {
                    Argument result2 = new Argument(this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ |= 1;
                    }
                    result2.nameId_ = this.nameId_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result2.value_ = this.value_;
                    result2.bitField0_ = to_bitField0_;
                    return result2;
                }

                @Override
                public Builder mergeFrom(Argument other) {
                    if (other == Argument.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasNameId()) {
                        this.setNameId(other.getNameId());
                    }
                    if (other.hasValue()) {
                        this.mergeValue(other.getValue());
                    }
                    this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                    return this;
                }

                @Override
                public final boolean isInitialized() {
                    if (!this.hasNameId()) {
                        return false;
                    }
                    if (!this.hasValue()) {
                        return false;
                    }
                    return this.getValue().isInitialized();
                }

                @Override
                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Argument parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (Argument)e.getUnfinishedMessage();
                        throw e;
                    }
                    finally {
                        if (parsedMessage != null) {
                            this.mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }

                public boolean hasNameId() {
                    return (this.bitField0_ & 1) == 1;
                }

                public Builder setNameId(int value) {
                    this.bitField0_ |= 1;
                    this.nameId_ = value;
                    return this;
                }

                public boolean hasValue() {
                    return (this.bitField0_ & 2) == 2;
                }

                public Value getValue() {
                    return this.value_;
                }

                public Builder mergeValue(Value value) {
                    this.value_ = (this.bitField0_ & 2) == 2 && this.value_ != Value.getDefaultInstance() ? Value.newBuilder(this.value_).mergeFrom(value).buildPartial() : value;
                    this.bitField0_ |= 2;
                    return this;
                }
            }

            public static final class Value
            extends GeneratedMessageLite
            implements ProtoBuf$Annotation$Argument$ValueOrBuilder {
                private static final Value defaultInstance;
                private final ByteString unknownFields;
                public static Parser<Value> PARSER;
                private int bitField0_;
                private Type type_;
                private long intValue_;
                private float floatValue_;
                private double doubleValue_;
                private int stringValue_;
                private int classId_;
                private int enumValueId_;
                private Annotation annotation_;
                private List<Value> arrayElement_;
                private int arrayDimensionCount_;
                private int flags_;
                private byte memoizedIsInitialized = (byte)-1;
                private int memoizedSerializedSize = -1;

                private Value(GeneratedMessageLite.Builder builder) {
                    super(builder);
                    this.unknownFields = builder.getUnknownFields();
                }

                private Value(boolean noInit) {
                    this.unknownFields = ByteString.EMPTY;
                }

                public static Value getDefaultInstance() {
                    return defaultInstance;
                }

                @Override
                public Value getDefaultInstanceForType() {
                    return defaultInstance;
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                private Value(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    this.initFields();
                    int mutable_bitField0_ = 0;
                    ByteString.Output unknownFieldsOutput = ByteString.newOutput();
                    CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
                    try {
                        boolean done = false;
                        block29: while (!done) {
                            int tag = input.readTag();
                            switch (tag) {
                                case 0: {
                                    done = true;
                                    continue block29;
                                }
                                default: {
                                    if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block29;
                                    done = true;
                                    continue block29;
                                }
                                case 8: {
                                    int rawValue = input.readEnum();
                                    Type value = Type.valueOf(rawValue);
                                    if (value == null) {
                                        unknownFieldsCodedOutput.writeRawVarint32(tag);
                                        unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                        continue block29;
                                    }
                                    this.bitField0_ |= 1;
                                    this.type_ = value;
                                    continue block29;
                                }
                                case 16: {
                                    this.bitField0_ |= 2;
                                    this.intValue_ = input.readSInt64();
                                    continue block29;
                                }
                                case 29: {
                                    this.bitField0_ |= 4;
                                    this.floatValue_ = input.readFloat();
                                    continue block29;
                                }
                                case 33: {
                                    this.bitField0_ |= 8;
                                    this.doubleValue_ = input.readDouble();
                                    continue block29;
                                }
                                case 40: {
                                    this.bitField0_ |= 0x10;
                                    this.stringValue_ = input.readInt32();
                                    continue block29;
                                }
                                case 48: {
                                    this.bitField0_ |= 0x20;
                                    this.classId_ = input.readInt32();
                                    continue block29;
                                }
                                case 56: {
                                    this.bitField0_ |= 0x40;
                                    this.enumValueId_ = input.readInt32();
                                    continue block29;
                                }
                                case 66: {
                                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Builder subBuilder = null;
                                    if ((this.bitField0_ & 0x80) == 128) {
                                        subBuilder = this.annotation_.toBuilder();
                                    }
                                    this.annotation_ = input.readMessage(Annotation.PARSER, extensionRegistry);
                                    if (subBuilder != null) {
                                        subBuilder.mergeFrom(this.annotation_);
                                        this.annotation_ = subBuilder.buildPartial();
                                    }
                                    this.bitField0_ |= 0x80;
                                    continue block29;
                                }
                                case 74: {
                                    if ((mutable_bitField0_ & 0x100) != 256) {
                                        this.arrayElement_ = new ArrayList<Value>();
                                        mutable_bitField0_ |= 0x100;
                                    }
                                    this.arrayElement_.add(input.readMessage(PARSER, extensionRegistry));
                                    continue block29;
                                }
                                case 80: {
                                    this.bitField0_ |= 0x200;
                                    this.flags_ = input.readInt32();
                                    continue block29;
                                }
                                case 88: 
                            }
                            this.bitField0_ |= 0x100;
                            this.arrayDimensionCount_ = input.readInt32();
                        }
                    }
                    catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    }
                    catch (IOException e) {
                        throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                    }
                    finally {
                        if ((mutable_bitField0_ & 0x100) == 256) {
                            this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
                        }
                        try {
                            unknownFieldsCodedOutput.flush();
                        }
                        catch (IOException iOException) {
                        }
                        finally {
                            this.unknownFields = unknownFieldsOutput.toByteString();
                        }
                        this.makeExtensionsImmutable();
                    }
                }

                public Parser<Value> getParserForType() {
                    return PARSER;
                }

                public boolean hasType() {
                    return (this.bitField0_ & 1) == 1;
                }

                public Type getType() {
                    return this.type_;
                }

                public boolean hasIntValue() {
                    return (this.bitField0_ & 2) == 2;
                }

                public long getIntValue() {
                    return this.intValue_;
                }

                public boolean hasFloatValue() {
                    return (this.bitField0_ & 4) == 4;
                }

                public float getFloatValue() {
                    return this.floatValue_;
                }

                public boolean hasDoubleValue() {
                    return (this.bitField0_ & 8) == 8;
                }

                public double getDoubleValue() {
                    return this.doubleValue_;
                }

                public boolean hasStringValue() {
                    return (this.bitField0_ & 0x10) == 16;
                }

                public int getStringValue() {
                    return this.stringValue_;
                }

                public boolean hasClassId() {
                    return (this.bitField0_ & 0x20) == 32;
                }

                public int getClassId() {
                    return this.classId_;
                }

                public boolean hasEnumValueId() {
                    return (this.bitField0_ & 0x40) == 64;
                }

                public int getEnumValueId() {
                    return this.enumValueId_;
                }

                public boolean hasAnnotation() {
                    return (this.bitField0_ & 0x80) == 128;
                }

                public Annotation getAnnotation() {
                    return this.annotation_;
                }

                public List<Value> getArrayElementList() {
                    return this.arrayElement_;
                }

                public int getArrayElementCount() {
                    return this.arrayElement_.size();
                }

                public Value getArrayElement(int index) {
                    return this.arrayElement_.get(index);
                }

                public boolean hasArrayDimensionCount() {
                    return (this.bitField0_ & 0x100) == 256;
                }

                public int getArrayDimensionCount() {
                    return this.arrayDimensionCount_;
                }

                public boolean hasFlags() {
                    return (this.bitField0_ & 0x200) == 512;
                }

                public int getFlags() {
                    return this.flags_;
                }

                private void initFields() {
                    this.type_ = Type.BYTE;
                    this.intValue_ = 0L;
                    this.floatValue_ = 0.0f;
                    this.doubleValue_ = 0.0;
                    this.stringValue_ = 0;
                    this.classId_ = 0;
                    this.enumValueId_ = 0;
                    this.annotation_ = Annotation.getDefaultInstance();
                    this.arrayElement_ = Collections.emptyList();
                    this.arrayDimensionCount_ = 0;
                    this.flags_ = 0;
                }

                @Override
                public final boolean isInitialized() {
                    byte isInitialized = this.memoizedIsInitialized;
                    if (isInitialized == 1) {
                        return true;
                    }
                    if (isInitialized == 0) {
                        return false;
                    }
                    if (this.hasAnnotation() && !this.getAnnotation().isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                    }
                    for (int i = 0; i < this.getArrayElementCount(); ++i) {
                        if (this.getArrayElement(i).isInitialized()) continue;
                        this.memoizedIsInitialized = 0;
                        return false;
                    }
                    this.memoizedIsInitialized = 1;
                    return true;
                }

                @Override
                public void writeTo(CodedOutputStream output) throws IOException {
                    this.getSerializedSize();
                    if ((this.bitField0_ & 1) == 1) {
                        output.writeEnum(1, this.type_.getNumber());
                    }
                    if ((this.bitField0_ & 2) == 2) {
                        output.writeSInt64(2, this.intValue_);
                    }
                    if ((this.bitField0_ & 4) == 4) {
                        output.writeFloat(3, this.floatValue_);
                    }
                    if ((this.bitField0_ & 8) == 8) {
                        output.writeDouble(4, this.doubleValue_);
                    }
                    if ((this.bitField0_ & 0x10) == 16) {
                        output.writeInt32(5, this.stringValue_);
                    }
                    if ((this.bitField0_ & 0x20) == 32) {
                        output.writeInt32(6, this.classId_);
                    }
                    if ((this.bitField0_ & 0x40) == 64) {
                        output.writeInt32(7, this.enumValueId_);
                    }
                    if ((this.bitField0_ & 0x80) == 128) {
                        output.writeMessage(8, this.annotation_);
                    }
                    for (int i = 0; i < this.arrayElement_.size(); ++i) {
                        output.writeMessage(9, this.arrayElement_.get(i));
                    }
                    if ((this.bitField0_ & 0x200) == 512) {
                        output.writeInt32(10, this.flags_);
                    }
                    if ((this.bitField0_ & 0x100) == 256) {
                        output.writeInt32(11, this.arrayDimensionCount_);
                    }
                    output.writeRawBytes(this.unknownFields);
                }

                @Override
                public int getSerializedSize() {
                    int size = this.memoizedSerializedSize;
                    if (size != -1) {
                        return size;
                    }
                    size = 0;
                    if ((this.bitField0_ & 1) == 1) {
                        size += CodedOutputStream.computeEnumSize(1, this.type_.getNumber());
                    }
                    if ((this.bitField0_ & 2) == 2) {
                        size += CodedOutputStream.computeSInt64Size(2, this.intValue_);
                    }
                    if ((this.bitField0_ & 4) == 4) {
                        size += CodedOutputStream.computeFloatSize(3, this.floatValue_);
                    }
                    if ((this.bitField0_ & 8) == 8) {
                        size += CodedOutputStream.computeDoubleSize(4, this.doubleValue_);
                    }
                    if ((this.bitField0_ & 0x10) == 16) {
                        size += CodedOutputStream.computeInt32Size(5, this.stringValue_);
                    }
                    if ((this.bitField0_ & 0x20) == 32) {
                        size += CodedOutputStream.computeInt32Size(6, this.classId_);
                    }
                    if ((this.bitField0_ & 0x40) == 64) {
                        size += CodedOutputStream.computeInt32Size(7, this.enumValueId_);
                    }
                    if ((this.bitField0_ & 0x80) == 128) {
                        size += CodedOutputStream.computeMessageSize(8, this.annotation_);
                    }
                    for (int i = 0; i < this.arrayElement_.size(); ++i) {
                        size += CodedOutputStream.computeMessageSize(9, this.arrayElement_.get(i));
                    }
                    if ((this.bitField0_ & 0x200) == 512) {
                        size += CodedOutputStream.computeInt32Size(10, this.flags_);
                    }
                    if ((this.bitField0_ & 0x100) == 256) {
                        size += CodedOutputStream.computeInt32Size(11, this.arrayDimensionCount_);
                    }
                    this.memoizedSerializedSize = size += this.unknownFields.size();
                    return size;
                }

                public static Builder newBuilder() {
                    return Builder.create();
                }

                @Override
                public Builder newBuilderForType() {
                    return Value.newBuilder();
                }

                public static Builder newBuilder(Value prototype) {
                    return Value.newBuilder().mergeFrom(prototype);
                }

                @Override
                public Builder toBuilder() {
                    return Value.newBuilder(this);
                }

                static {
                    PARSER = new AbstractParser<Value>(){

                        @Override
                        public Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                            return new Value(input, extensionRegistry);
                        }
                    };
                    defaultInstance = new Value(true);
                    defaultInstance.initFields();
                }

                public static final class Builder
                extends GeneratedMessageLite.Builder<Value, Builder>
                implements ProtoBuf$Annotation$Argument$ValueOrBuilder {
                    private int bitField0_;
                    private Type type_ = Type.BYTE;
                    private long intValue_;
                    private float floatValue_;
                    private double doubleValue_;
                    private int stringValue_;
                    private int classId_;
                    private int enumValueId_;
                    private Annotation annotation_ = Annotation.getDefaultInstance();
                    private List<Value> arrayElement_ = Collections.emptyList();
                    private int arrayDimensionCount_;
                    private int flags_;

                    private Builder() {
                        this.maybeForceBuilderInitialization();
                    }

                    private void maybeForceBuilderInitialization() {
                    }

                    private static Builder create() {
                        return new Builder();
                    }

                    @Override
                    public Builder clone() {
                        return Builder.create().mergeFrom(this.buildPartial());
                    }

                    @Override
                    public Value getDefaultInstanceForType() {
                        return Value.getDefaultInstance();
                    }

                    @Override
                    public Value build() {
                        Value result2 = this.buildPartial();
                        if (!result2.isInitialized()) {
                            throw Builder.newUninitializedMessageException(result2);
                        }
                        return result2;
                    }

                    public Value buildPartial() {
                        Value result2 = new Value(this);
                        int from_bitField0_ = this.bitField0_;
                        int to_bitField0_ = 0;
                        if ((from_bitField0_ & 1) == 1) {
                            to_bitField0_ |= 1;
                        }
                        result2.type_ = this.type_;
                        if ((from_bitField0_ & 2) == 2) {
                            to_bitField0_ |= 2;
                        }
                        result2.intValue_ = this.intValue_;
                        if ((from_bitField0_ & 4) == 4) {
                            to_bitField0_ |= 4;
                        }
                        result2.floatValue_ = this.floatValue_;
                        if ((from_bitField0_ & 8) == 8) {
                            to_bitField0_ |= 8;
                        }
                        result2.doubleValue_ = this.doubleValue_;
                        if ((from_bitField0_ & 0x10) == 16) {
                            to_bitField0_ |= 0x10;
                        }
                        result2.stringValue_ = this.stringValue_;
                        if ((from_bitField0_ & 0x20) == 32) {
                            to_bitField0_ |= 0x20;
                        }
                        result2.classId_ = this.classId_;
                        if ((from_bitField0_ & 0x40) == 64) {
                            to_bitField0_ |= 0x40;
                        }
                        result2.enumValueId_ = this.enumValueId_;
                        if ((from_bitField0_ & 0x80) == 128) {
                            to_bitField0_ |= 0x80;
                        }
                        result2.annotation_ = this.annotation_;
                        if ((this.bitField0_ & 0x100) == 256) {
                            this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
                            this.bitField0_ &= 0xFFFFFEFF;
                        }
                        result2.arrayElement_ = this.arrayElement_;
                        if ((from_bitField0_ & 0x200) == 512) {
                            to_bitField0_ |= 0x100;
                        }
                        result2.arrayDimensionCount_ = this.arrayDimensionCount_;
                        if ((from_bitField0_ & 0x400) == 1024) {
                            to_bitField0_ |= 0x200;
                        }
                        result2.flags_ = this.flags_;
                        result2.bitField0_ = to_bitField0_;
                        return result2;
                    }

                    @Override
                    public Builder mergeFrom(Value other) {
                        if (other == Value.getDefaultInstance()) {
                            return this;
                        }
                        if (other.hasType()) {
                            this.setType(other.getType());
                        }
                        if (other.hasIntValue()) {
                            this.setIntValue(other.getIntValue());
                        }
                        if (other.hasFloatValue()) {
                            this.setFloatValue(other.getFloatValue());
                        }
                        if (other.hasDoubleValue()) {
                            this.setDoubleValue(other.getDoubleValue());
                        }
                        if (other.hasStringValue()) {
                            this.setStringValue(other.getStringValue());
                        }
                        if (other.hasClassId()) {
                            this.setClassId(other.getClassId());
                        }
                        if (other.hasEnumValueId()) {
                            this.setEnumValueId(other.getEnumValueId());
                        }
                        if (other.hasAnnotation()) {
                            this.mergeAnnotation(other.getAnnotation());
                        }
                        if (!other.arrayElement_.isEmpty()) {
                            if (this.arrayElement_.isEmpty()) {
                                this.arrayElement_ = other.arrayElement_;
                                this.bitField0_ &= 0xFFFFFEFF;
                            } else {
                                this.ensureArrayElementIsMutable();
                                this.arrayElement_.addAll(other.arrayElement_);
                            }
                        }
                        if (other.hasArrayDimensionCount()) {
                            this.setArrayDimensionCount(other.getArrayDimensionCount());
                        }
                        if (other.hasFlags()) {
                            this.setFlags(other.getFlags());
                        }
                        this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                        return this;
                    }

                    @Override
                    public final boolean isInitialized() {
                        if (this.hasAnnotation() && !this.getAnnotation().isInitialized()) {
                            return false;
                        }
                        for (int i = 0; i < this.getArrayElementCount(); ++i) {
                            if (this.getArrayElement(i).isInitialized()) continue;
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                        Value parsedMessage = null;
                        try {
                            parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                        }
                        catch (InvalidProtocolBufferException e) {
                            parsedMessage = (Value)e.getUnfinishedMessage();
                            throw e;
                        }
                        finally {
                            if (parsedMessage != null) {
                                this.mergeFrom(parsedMessage);
                            }
                        }
                        return this;
                    }

                    public Builder setType(Type value) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.bitField0_ |= 1;
                        this.type_ = value;
                        return this;
                    }

                    public Builder setIntValue(long value) {
                        this.bitField0_ |= 2;
                        this.intValue_ = value;
                        return this;
                    }

                    public Builder setFloatValue(float value) {
                        this.bitField0_ |= 4;
                        this.floatValue_ = value;
                        return this;
                    }

                    public Builder setDoubleValue(double value) {
                        this.bitField0_ |= 8;
                        this.doubleValue_ = value;
                        return this;
                    }

                    public Builder setStringValue(int value) {
                        this.bitField0_ |= 0x10;
                        this.stringValue_ = value;
                        return this;
                    }

                    public Builder setClassId(int value) {
                        this.bitField0_ |= 0x20;
                        this.classId_ = value;
                        return this;
                    }

                    public Builder setEnumValueId(int value) {
                        this.bitField0_ |= 0x40;
                        this.enumValueId_ = value;
                        return this;
                    }

                    public boolean hasAnnotation() {
                        return (this.bitField0_ & 0x80) == 128;
                    }

                    public Annotation getAnnotation() {
                        return this.annotation_;
                    }

                    public Builder mergeAnnotation(Annotation value) {
                        this.annotation_ = (this.bitField0_ & 0x80) == 128 && this.annotation_ != Annotation.getDefaultInstance() ? Annotation.newBuilder(this.annotation_).mergeFrom(value).buildPartial() : value;
                        this.bitField0_ |= 0x80;
                        return this;
                    }

                    private void ensureArrayElementIsMutable() {
                        if ((this.bitField0_ & 0x100) != 256) {
                            this.arrayElement_ = new ArrayList<Value>(this.arrayElement_);
                            this.bitField0_ |= 0x100;
                        }
                    }

                    public int getArrayElementCount() {
                        return this.arrayElement_.size();
                    }

                    public Value getArrayElement(int index) {
                        return this.arrayElement_.get(index);
                    }

                    public Builder setArrayDimensionCount(int value) {
                        this.bitField0_ |= 0x200;
                        this.arrayDimensionCount_ = value;
                        return this;
                    }

                    public Builder setFlags(int value) {
                        this.bitField0_ |= 0x400;
                        this.flags_ = value;
                        return this;
                    }
                }

                public static enum Type implements Internal.EnumLite
                {
                    BYTE(0, 0),
                    CHAR(1, 1),
                    SHORT(2, 2),
                    INT(3, 3),
                    LONG(4, 4),
                    FLOAT(5, 5),
                    DOUBLE(6, 6),
                    BOOLEAN(7, 7),
                    STRING(8, 8),
                    CLASS(9, 9),
                    ENUM(10, 10),
                    ANNOTATION(11, 11),
                    ARRAY(12, 12);

                    private static Internal.EnumLiteMap<Type> internalValueMap;
                    private final int value;

                    @Override
                    public final int getNumber() {
                        return this.value;
                    }

                    public static Type valueOf(int value) {
                        switch (value) {
                            case 0: {
                                return BYTE;
                            }
                            case 1: {
                                return CHAR;
                            }
                            case 2: {
                                return SHORT;
                            }
                            case 3: {
                                return INT;
                            }
                            case 4: {
                                return LONG;
                            }
                            case 5: {
                                return FLOAT;
                            }
                            case 6: {
                                return DOUBLE;
                            }
                            case 7: {
                                return BOOLEAN;
                            }
                            case 8: {
                                return STRING;
                            }
                            case 9: {
                                return CLASS;
                            }
                            case 10: {
                                return ENUM;
                            }
                            case 11: {
                                return ANNOTATION;
                            }
                            case 12: {
                                return ARRAY;
                            }
                        }
                        return null;
                    }

                    private Type(int index, int value) {
                        this.value = value;
                    }

                    static {
                        internalValueMap = new Internal.EnumLiteMap<Type>(){

                            @Override
                            public Type findValueByNumber(int number) {
                                return Type.valueOf(number);
                            }
                        };
                    }
                }
            }
        }
    }

    public static final class QualifiedNameTable
    extends GeneratedMessageLite
    implements ProtoBuf$QualifiedNameTableOrBuilder {
        private static final QualifiedNameTable defaultInstance;
        private final ByteString unknownFields;
        public static Parser<QualifiedNameTable> PARSER;
        private List<QualifiedName> qualifiedName_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private QualifiedNameTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private QualifiedNameTable(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static QualifiedNameTable getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public QualifiedNameTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private QualifiedNameTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block19;
                            done = true;
                            continue block19;
                        }
                        case 10: 
                    }
                    if (!(mutable_bitField0_ & true)) {
                        this.qualifiedName_ = new ArrayList<QualifiedName>();
                        mutable_bitField0_ |= true;
                    }
                    this.qualifiedName_.add(input.readMessage(QualifiedName.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<QualifiedNameTable> getParserForType() {
            return PARSER;
        }

        public int getQualifiedNameCount() {
            return this.qualifiedName_.size();
        }

        public QualifiedName getQualifiedName(int index) {
            return this.qualifiedName_.get(index);
        }

        private void initFields() {
            this.qualifiedName_ = Collections.emptyList();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getQualifiedNameCount(); ++i) {
                if (this.getQualifiedName(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.qualifiedName_.size(); ++i) {
                output.writeMessage(1, this.qualifiedName_.get(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.qualifiedName_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.qualifiedName_.get(i));
            }
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return QualifiedNameTable.newBuilder();
        }

        public static Builder newBuilder(QualifiedNameTable prototype) {
            return QualifiedNameTable.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return QualifiedNameTable.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<QualifiedNameTable>(){

                @Override
                public QualifiedNameTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new QualifiedNameTable(input, extensionRegistry);
                }
            };
            defaultInstance = new QualifiedNameTable(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<QualifiedNameTable, Builder>
        implements ProtoBuf$QualifiedNameTableOrBuilder {
            private int bitField0_;
            private List<QualifiedName> qualifiedName_ = Collections.emptyList();

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public QualifiedNameTable getDefaultInstanceForType() {
                return QualifiedNameTable.getDefaultInstance();
            }

            @Override
            public QualifiedNameTable build() {
                QualifiedNameTable result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public QualifiedNameTable buildPartial() {
                QualifiedNameTable result2 = new QualifiedNameTable(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.qualifiedName_ = this.qualifiedName_;
                return result2;
            }

            @Override
            public Builder mergeFrom(QualifiedNameTable other) {
                if (other == QualifiedNameTable.getDefaultInstance()) {
                    return this;
                }
                if (!other.qualifiedName_.isEmpty()) {
                    if (this.qualifiedName_.isEmpty()) {
                        this.qualifiedName_ = other.qualifiedName_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureQualifiedNameIsMutable();
                        this.qualifiedName_.addAll(other.qualifiedName_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getQualifiedNameCount(); ++i) {
                    if (this.getQualifiedName(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                QualifiedNameTable parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (QualifiedNameTable)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureQualifiedNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.qualifiedName_ = new ArrayList<QualifiedName>(this.qualifiedName_);
                    this.bitField0_ |= 1;
                }
            }

            public int getQualifiedNameCount() {
                return this.qualifiedName_.size();
            }

            public QualifiedName getQualifiedName(int index) {
                return this.qualifiedName_.get(index);
            }
        }

        public static final class QualifiedName
        extends GeneratedMessageLite
        implements ProtoBuf$QualifiedNameTable$QualifiedNameOrBuilder {
            private static final QualifiedName defaultInstance;
            private final ByteString unknownFields;
            public static Parser<QualifiedName> PARSER;
            private int bitField0_;
            private int parentQualifiedName_;
            private int shortName_;
            private Kind kind_;
            private byte memoizedIsInitialized = (byte)-1;
            private int memoizedSerializedSize = -1;

            private QualifiedName(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.unknownFields = builder.getUnknownFields();
            }

            private QualifiedName(boolean noInit) {
                this.unknownFields = ByteString.EMPTY;
            }

            public static QualifiedName getDefaultInstance() {
                return defaultInstance;
            }

            @Override
            public QualifiedName getDefaultInstanceForType() {
                return defaultInstance;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private QualifiedName(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.initFields();
                boolean mutable_bitField0_ = false;
                ByteString.Output unknownFieldsOutput = ByteString.newOutput();
                CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
                try {
                    boolean done = false;
                    block21: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block21;
                            }
                            default: {
                                if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block21;
                                done = true;
                                continue block21;
                            }
                            case 8: {
                                this.bitField0_ |= 1;
                                this.parentQualifiedName_ = input.readInt32();
                                continue block21;
                            }
                            case 16: {
                                this.bitField0_ |= 2;
                                this.shortName_ = input.readInt32();
                                continue block21;
                            }
                            case 24: 
                        }
                        int rawValue = input.readEnum();
                        Kind value = Kind.valueOf(rawValue);
                        if (value == null) {
                            unknownFieldsCodedOutput.writeRawVarint32(tag);
                            unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                            continue;
                        }
                        this.bitField0_ |= 4;
                        this.kind_ = value;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                }
                finally {
                    try {
                        unknownFieldsCodedOutput.flush();
                    }
                    catch (IOException iOException) {
                    }
                    finally {
                        this.unknownFields = unknownFieldsOutput.toByteString();
                    }
                    this.makeExtensionsImmutable();
                }
            }

            public Parser<QualifiedName> getParserForType() {
                return PARSER;
            }

            public boolean hasParentQualifiedName() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getParentQualifiedName() {
                return this.parentQualifiedName_;
            }

            public boolean hasShortName() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getShortName() {
                return this.shortName_;
            }

            public boolean hasKind() {
                return (this.bitField0_ & 4) == 4;
            }

            public Kind getKind() {
                return this.kind_;
            }

            private void initFields() {
                this.parentQualifiedName_ = -1;
                this.shortName_ = 0;
                this.kind_ = Kind.PACKAGE;
            }

            @Override
            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!this.hasShortName()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }

            @Override
            public void writeTo(CodedOutputStream output) throws IOException {
                this.getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.writeInt32(1, this.parentQualifiedName_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeInt32(2, this.shortName_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.writeEnum(3, this.kind_.getNumber());
                }
                output.writeRawBytes(this.unknownFields);
            }

            @Override
            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size += CodedOutputStream.computeInt32Size(1, this.parentQualifiedName_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeInt32Size(2, this.shortName_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size += CodedOutputStream.computeEnumSize(3, this.kind_.getNumber());
                }
                this.memoizedSerializedSize = size += this.unknownFields.size();
                return size;
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override
            public Builder newBuilderForType() {
                return QualifiedName.newBuilder();
            }

            public static Builder newBuilder(QualifiedName prototype) {
                return QualifiedName.newBuilder().mergeFrom(prototype);
            }

            @Override
            public Builder toBuilder() {
                return QualifiedName.newBuilder(this);
            }

            static {
                PARSER = new AbstractParser<QualifiedName>(){

                    @Override
                    public QualifiedName parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new QualifiedName(input, extensionRegistry);
                    }
                };
                defaultInstance = new QualifiedName(true);
                defaultInstance.initFields();
            }

            public static final class Builder
            extends GeneratedMessageLite.Builder<QualifiedName, Builder>
            implements ProtoBuf$QualifiedNameTable$QualifiedNameOrBuilder {
                private int bitField0_;
                private int parentQualifiedName_ = -1;
                private int shortName_;
                private Kind kind_ = Kind.PACKAGE;

                private Builder() {
                    this.maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                }

                private static Builder create() {
                    return new Builder();
                }

                @Override
                public Builder clone() {
                    return Builder.create().mergeFrom(this.buildPartial());
                }

                @Override
                public QualifiedName getDefaultInstanceForType() {
                    return QualifiedName.getDefaultInstance();
                }

                @Override
                public QualifiedName build() {
                    QualifiedName result2 = this.buildPartial();
                    if (!result2.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result2);
                    }
                    return result2;
                }

                public QualifiedName buildPartial() {
                    QualifiedName result2 = new QualifiedName(this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ |= 1;
                    }
                    result2.parentQualifiedName_ = this.parentQualifiedName_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result2.shortName_ = this.shortName_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    result2.kind_ = this.kind_;
                    result2.bitField0_ = to_bitField0_;
                    return result2;
                }

                @Override
                public Builder mergeFrom(QualifiedName other) {
                    if (other == QualifiedName.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasParentQualifiedName()) {
                        this.setParentQualifiedName(other.getParentQualifiedName());
                    }
                    if (other.hasShortName()) {
                        this.setShortName(other.getShortName());
                    }
                    if (other.hasKind()) {
                        this.setKind(other.getKind());
                    }
                    this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                    return this;
                }

                @Override
                public final boolean isInitialized() {
                    return this.hasShortName();
                }

                @Override
                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    QualifiedName parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (QualifiedName)e.getUnfinishedMessage();
                        throw e;
                    }
                    finally {
                        if (parsedMessage != null) {
                            this.mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }

                public Builder setParentQualifiedName(int value) {
                    this.bitField0_ |= 1;
                    this.parentQualifiedName_ = value;
                    return this;
                }

                public boolean hasShortName() {
                    return (this.bitField0_ & 2) == 2;
                }

                public Builder setShortName(int value) {
                    this.bitField0_ |= 2;
                    this.shortName_ = value;
                    return this;
                }

                public Builder setKind(Kind value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 4;
                    this.kind_ = value;
                    return this;
                }
            }

            public static enum Kind implements Internal.EnumLite
            {
                CLASS(0, 0),
                PACKAGE(1, 1),
                LOCAL(2, 2);

                private static Internal.EnumLiteMap<Kind> internalValueMap;
                private final int value;

                @Override
                public final int getNumber() {
                    return this.value;
                }

                public static Kind valueOf(int value) {
                    switch (value) {
                        case 0: {
                            return CLASS;
                        }
                        case 1: {
                            return PACKAGE;
                        }
                        case 2: {
                            return LOCAL;
                        }
                    }
                    return null;
                }

                private Kind(int index, int value) {
                    this.value = value;
                }

                static {
                    internalValueMap = new Internal.EnumLiteMap<Kind>(){

                        @Override
                        public Kind findValueByNumber(int number) {
                            return Kind.valueOf(number);
                        }
                    };
                }
            }
        }
    }

    public static final class StringTable
    extends GeneratedMessageLite
    implements ProtoBuf$StringTableOrBuilder {
        private static final StringTable defaultInstance;
        private final ByteString unknownFields;
        public static Parser<StringTable> PARSER;
        private LazyStringList string_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;

        private StringTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private StringTable(boolean noInit) {
            this.unknownFields = ByteString.EMPTY;
        }

        public static StringTable getDefaultInstance() {
            return defaultInstance;
        }

        @Override
        public StringTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private StringTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.initFields();
            boolean mutable_bitField0_ = false;
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            try {
                boolean done = false;
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) continue block19;
                            done = true;
                            continue block19;
                        }
                        case 10: 
                    }
                    ByteString bs = input.readBytes();
                    if (!(mutable_bitField0_ & true)) {
                        this.string_ = new LazyStringArrayList();
                        mutable_bitField0_ |= true;
                    }
                    this.string_.add(bs);
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.string_ = this.string_.getUnmodifiableView();
                }
                try {
                    unknownFieldsCodedOutput.flush();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.unknownFields = unknownFieldsOutput.toByteString();
                }
                this.makeExtensionsImmutable();
            }
        }

        public Parser<StringTable> getParserForType() {
            return PARSER;
        }

        public ProtocolStringList getStringList() {
            return this.string_;
        }

        public String getString(int index) {
            return (String)this.string_.get(index);
        }

        private void initFields() {
            this.string_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.string_.size(); ++i) {
                output.writeBytes(1, this.string_.getByteString(i));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            int dataSize = 0;
            for (int i = 0; i < this.string_.size(); ++i) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag(this.string_.getByteString(i));
            }
            size += dataSize;
            size += 1 * this.getStringList().size();
            this.memoizedSerializedSize = size += this.unknownFields.size();
            return size;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override
        public Builder newBuilderForType() {
            return StringTable.newBuilder();
        }

        public static Builder newBuilder(StringTable prototype) {
            return StringTable.newBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return StringTable.newBuilder(this);
        }

        static {
            PARSER = new AbstractParser<StringTable>(){

                @Override
                public StringTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new StringTable(input, extensionRegistry);
                }
            };
            defaultInstance = new StringTable(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<StringTable, Builder>
        implements ProtoBuf$StringTableOrBuilder {
            private int bitField0_;
            private LazyStringList string_ = LazyStringArrayList.EMPTY;

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            @Override
            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            @Override
            public StringTable getDefaultInstanceForType() {
                return StringTable.getDefaultInstance();
            }

            @Override
            public StringTable build() {
                StringTable result2 = this.buildPartial();
                if (!result2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result2);
                }
                return result2;
            }

            public StringTable buildPartial() {
                StringTable result2 = new StringTable(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.string_ = this.string_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result2.string_ = this.string_;
                return result2;
            }

            @Override
            public Builder mergeFrom(StringTable other) {
                if (other == StringTable.getDefaultInstance()) {
                    return this;
                }
                if (!other.string_.isEmpty()) {
                    if (this.string_.isEmpty()) {
                        this.string_ = other.string_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureStringIsMutable();
                        this.string_.addAll(other.string_);
                    }
                }
                this.setUnknownFields(this.getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                StringTable parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (StringTable)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureStringIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.string_ = new LazyStringArrayList(this.string_);
                    this.bitField0_ |= 1;
                }
            }
        }
    }

    public static enum MemberKind implements Internal.EnumLite
    {
        DECLARATION(0, 0),
        FAKE_OVERRIDE(1, 1),
        DELEGATION(2, 2),
        SYNTHESIZED(3, 3);

        private static Internal.EnumLiteMap<MemberKind> internalValueMap;
        private final int value;

        @Override
        public final int getNumber() {
            return this.value;
        }

        public static MemberKind valueOf(int value) {
            switch (value) {
                case 0: {
                    return DECLARATION;
                }
                case 1: {
                    return FAKE_OVERRIDE;
                }
                case 2: {
                    return DELEGATION;
                }
                case 3: {
                    return SYNTHESIZED;
                }
            }
            return null;
        }

        private MemberKind(int index, int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<MemberKind>(){

                @Override
                public MemberKind findValueByNumber(int number) {
                    return MemberKind.valueOf(number);
                }
            };
        }
    }

    public static enum Visibility implements Internal.EnumLite
    {
        INTERNAL(0, 0),
        PRIVATE(1, 1),
        PROTECTED(2, 2),
        PUBLIC(3, 3),
        PRIVATE_TO_THIS(4, 4),
        LOCAL(5, 5);

        private static Internal.EnumLiteMap<Visibility> internalValueMap;
        private final int value;

        @Override
        public final int getNumber() {
            return this.value;
        }

        public static Visibility valueOf(int value) {
            switch (value) {
                case 0: {
                    return INTERNAL;
                }
                case 1: {
                    return PRIVATE;
                }
                case 2: {
                    return PROTECTED;
                }
                case 3: {
                    return PUBLIC;
                }
                case 4: {
                    return PRIVATE_TO_THIS;
                }
                case 5: {
                    return LOCAL;
                }
            }
            return null;
        }

        private Visibility(int index, int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<Visibility>(){

                @Override
                public Visibility findValueByNumber(int number) {
                    return Visibility.valueOf(number);
                }
            };
        }
    }

    public static enum Modality implements Internal.EnumLite
    {
        FINAL(0, 0),
        OPEN(1, 1),
        ABSTRACT(2, 2),
        SEALED(3, 3);

        private static Internal.EnumLiteMap<Modality> internalValueMap;
        private final int value;

        @Override
        public final int getNumber() {
            return this.value;
        }

        public static Modality valueOf(int value) {
            switch (value) {
                case 0: {
                    return FINAL;
                }
                case 1: {
                    return OPEN;
                }
                case 2: {
                    return ABSTRACT;
                }
                case 3: {
                    return SEALED;
                }
            }
            return null;
        }

        private Modality(int index, int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<Modality>(){

                @Override
                public Modality findValueByNumber(int number) {
                    return Modality.valueOf(number);
                }
            };
        }
    }
}

