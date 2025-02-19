package non.asset.module.impl.other;

import java.awt.Color;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.module.Module;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", Category.OTHER);
        setRenderLabel("NoRotate");
        setDescription("Alow you own your head");
    }
    
    @Handler
    public void handle(PacketEvent event) {
        if (!event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            if (getMc().thePlayer != null && getMc().theWorld != null && getMc().thePlayer.rotationYaw != -180 && getMc().thePlayer.rotationPitch != 0) {
                packet.yaw = getMc().thePlayer.rotationYaw;
                packet.pitch = getMc().thePlayer.rotationPitch;
            }
        }
    }
}
