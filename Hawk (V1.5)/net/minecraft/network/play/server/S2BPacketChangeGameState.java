package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState implements Packet {
   private int state;
   public static final String[] MESSAGE_NAMES = new String[]{"tile.bed.notValid"};
   private static final String __OBFID = "CL_00001301";
   private float field_149141_c;

   public S2BPacketChangeGameState(int var1, float var2) {
      this.state = var1;
      this.field_149141_c = var2;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.state);
      var1.writeFloat(this.field_149141_c);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public S2BPacketChangeGameState() {
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleChangeGameState(this);
   }

   public int func_149138_c() {
      return this.state;
   }

   public float func_149137_d() {
      return this.field_149141_c;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.state = var1.readUnsignedByte();
      this.field_149141_c = var1.readFloat();
   }
}
