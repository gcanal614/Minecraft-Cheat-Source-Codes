package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C00PacketKeepAlive implements Packet {
   private static final String __OBFID = "CL_00001359";
   private int key;

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayServer)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.key = var1.readVarIntFromBuffer();
   }

   public C00PacketKeepAlive() {
   }

   public C00PacketKeepAlive(int var1) {
      this.key = var1;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processKeepAlive(this);
   }

   public int getKey() {
      return this.key;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.key);
   }
}
