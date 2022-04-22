package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", 0, Category.PLAYER);
    }

    @EventTarget
    public void onEvent(EventUpdate event) {
        this.setDisplayName("No Rotate");
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            if(mc.thePlayer != null && mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                packet.yaw = mc.thePlayer.rotationYaw;
                packet.pitch = mc.thePlayer.rotationPitch;
            }
        }
    }
}
