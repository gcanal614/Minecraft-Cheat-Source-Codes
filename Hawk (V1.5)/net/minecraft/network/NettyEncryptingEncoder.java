package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingEncoder extends MessageToByteEncoder {
   private static final String __OBFID = "CL_00001239";
   private final NettyEncryptionTranslator encryptionCodec;

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws ShortBufferException {
      this.encryptionCodec.cipher(var2, var3);
   }

   public NettyEncryptingEncoder(Cipher var1) {
      this.encryptionCodec = new NettyEncryptionTranslator(var1);
   }

   protected void encode(ChannelHandlerContext var1, Object var2, ByteBuf var3) throws ShortBufferException {
      this.encode(var1, (ByteBuf)var2, var3);
   }
}
