// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "No Rotate", moduleCategory = ModuleCategory.PLAYER)
public class NoRotate extends Module
{
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    
    public NoRotate() {
        S08PacketPlayerPosLook packet;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                packet = (S08PacketPlayerPosLook)e.getPacket();
                packet.setYaw(NoRotate.mc.thePlayer.rotationYaw);
                packet.setPitch(NoRotate.mc.thePlayer.rotationPitch);
            }
        });
    }
}
