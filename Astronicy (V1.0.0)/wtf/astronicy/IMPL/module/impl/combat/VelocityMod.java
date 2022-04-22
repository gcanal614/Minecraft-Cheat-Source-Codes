package wtf.astronicy.IMPL.module.impl.combat;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.packet.ReceivePacketEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModName("Velocity")
@Category(ModuleCategory.COMBAT)
public final class VelocityMod extends Module {
   @Listener(ReceivePacketEvent.class)
   public final void onReceivePacket(ReceivePacketEvent event) {
      Packet packet = event.getPacket();
      if (packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion) {
         event.setCancelled();
      }

   }
}
