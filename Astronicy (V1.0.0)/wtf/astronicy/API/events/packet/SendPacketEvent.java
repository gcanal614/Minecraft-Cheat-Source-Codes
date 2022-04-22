package wtf.astronicy.API.events.packet;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;
import net.minecraft.network.Packet;

public final class SendPacketEvent extends Cancellable implements Event {
   private final Packet packet;

   public SendPacketEvent(Packet packet) {
      this.packet = packet;
   }

   public Packet getPacket() {
      return this.packet;
   }
}
