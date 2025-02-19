/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.Via
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.platform.providers.Provider
 *  com.viaversion.viaversion.api.type.Type
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelPipeline
 *  io.netty.handler.codec.DecoderException
 *  io.netty.handler.codec.MessageToByteEncoder
 *  io.netty.handler.codec.MessageToMessageDecoder
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.netty.EmptyChannelHandler;
import de.gerrygames.viarewind.netty.ForwardMessageToByteEncoder;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionSendStorage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionHandlerProvider
implements Provider {
    public void handleSetCompression(UserConnection user, int threshold) {
        ChannelPipeline pipeline = user.getChannel().pipeline();
        if (user.isClientSide()) {
            pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(threshold));
            pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(threshold));
        } else {
            CompressionSendStorage storage = (CompressionSendStorage)user.get(CompressionSendStorage.class);
            storage.setRemoveCompression(true);
        }
    }

    public void handleTransform(UserConnection user) {
        CompressionSendStorage storage = (CompressionSendStorage)user.get(CompressionSendStorage.class);
        if (storage.isRemoveCompression()) {
            ChannelPipeline pipeline = user.getChannel().pipeline();
            String compressor = null;
            String decompressor = null;
            if (pipeline.get("compress") != null) {
                compressor = "compress";
                decompressor = "decompress";
            } else if (pipeline.get("compression-encoder") != null) {
                compressor = "compression-encoder";
                decompressor = "compression-decoder";
            }
            if (compressor == null) {
                throw new IllegalStateException("Couldn't remove compression for 1.7!");
            }
            pipeline.replace(decompressor, decompressor, (ChannelHandler)new EmptyChannelHandler());
            pipeline.replace(compressor, compressor, (ChannelHandler)new ForwardMessageToByteEncoder());
            storage.setRemoveCompression(false);
        }
    }

    protected ChannelHandler getEncoder(int threshold) {
        return new Compressor(threshold);
    }

    protected ChannelHandler getDecoder(int threshold) {
        return new Decompressor(threshold);
    }

    private static class Compressor
    extends MessageToByteEncoder<ByteBuf> {
        private final Deflater deflater;
        private final int threshold;

        public Compressor(int var1) {
            this.threshold = var1;
            this.deflater = new Deflater();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
            int frameLength = in.readableBytes();
            if (frameLength < this.threshold) {
                out.writeByte(0);
                out.writeBytes(in);
                return;
            }
            Type.VAR_INT.writePrimitive(out, frameLength);
            ByteBuf temp = in;
            if (!in.hasArray()) {
                temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            } else {
                in.retain();
            }
            ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer();
            try {
                this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    output.ensureWritable(4096);
                    output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output.arrayOffset() + output.writerIndex(), output.writableBytes()));
                }
                out.writeBytes(output);
            }
            finally {
                output.release();
                temp.release();
                this.deflater.reset();
            }
        }
    }

    private static class Decompressor
    extends MessageToMessageDecoder<ByteBuf> {
        private final Inflater inflater;
        private final int threshold;

        public Decompressor(int var1) {
            this.threshold = var1;
            this.inflater = new Inflater();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (!in.isReadable()) {
                return;
            }
            int outLength = Type.VAR_INT.readPrimitive(in);
            if (outLength == 0) {
                out.add(in.readBytes(in.readableBytes()));
                return;
            }
            if (outLength < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold);
            }
            if (outLength > 0x200000) {
                throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of " + 0x200000);
            }
            ByteBuf temp = in;
            if (!in.hasArray()) {
                temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            } else {
                in.retain();
            }
            ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer(outLength, outLength);
            try {
                this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                output.writerIndex(output.writerIndex() + this.inflater.inflate(output.array(), output.arrayOffset(), outLength));
                out.add(output.retain());
            }
            finally {
                output.release();
                temp.release();
                this.inflater.reset();
            }
        }
    }
}

