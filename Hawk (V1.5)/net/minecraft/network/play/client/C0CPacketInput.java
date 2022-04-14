package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0CPacketInput implements Packet {
   private static final String __OBFID = "CL_00001367";
   private boolean sneaking;
   private float strafeSpeed;
   private boolean jumping;
   private float forwardSpeed;

   public float getStrafeSpeed() {
      return this.strafeSpeed;
   }

   public float getForwardSpeed() {
      return this.forwardSpeed;
   }

   public boolean isJumping() {
      return this.jumping;
   }

   public C0CPacketInput(float var1, float var2, boolean var3, boolean var4) {
      this.strafeSpeed = var1;
      this.forwardSpeed = var2;
      this.jumping = var3;
      this.sneaking = var4;
   }

   public void func_180766_a(INetHandlerPlayServer var1) {
      var1.processInput(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.strafeSpeed = var1.readFloat();
      this.forwardSpeed = var1.readFloat();
      byte var2 = var1.readByte();
      this.jumping = (var2 & 1) > 0;
      this.sneaking = (var2 & 2) > 0;
   }

   public C0CPacketInput() {
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.strafeSpeed);
      var1.writeFloat(this.forwardSpeed);
      byte var2 = 0;
      if (this.jumping) {
         var2 = (byte)(var2 | 1);
      }

      if (this.sneaking) {
         var2 = (byte)(var2 | 2);
      }

      var1.writeByte(var2);
   }

   public void processPacket(INetHandler var1) {
      this.func_180766_a((INetHandlerPlayServer)var1);
   }
}
