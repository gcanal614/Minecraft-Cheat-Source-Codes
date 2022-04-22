package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
    
    public ModeSet mode = new ModeSet("Mode", "Packet", "Packet", "Cancel");

    public Velocity() {
        super("Velocity", 0, Category.COMBAT);
        addSettings(mode);
    }

    @EventTarget
    public void onGet(EventReceivePacket e) {
        setDisplayName("Velocity §7" + mode.getMode());
        switch (mode.getMode()) {
            case "Packet":
                if (e.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity)e.getPacket();
                    e.setCancelled((s12.getEntityID() == mc.thePlayer.getEntityId()));
                }
                if (e.getPacket() instanceof S27PacketExplosion)
                    e.setCancelled(true);
                break;
            case "Cancel":
                if(e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion)
                    e.setCancelled(true);
                break;
        }
    }
}
