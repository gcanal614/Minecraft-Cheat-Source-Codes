/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.Wrapper;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(label="NoRotate", category=ModuleCategory.PLAYER)
public final class NoRotate
extends Module {
    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)e.getPacket();
            e.setPacket(new S08PacketPlayerPosLook(packet.getX(), packet.getY(), packet.getZ(), Wrapper.getPlayer().currentEvent.getYaw(), Wrapper.getPlayer().currentEvent.getPitch(), packet.func_179834_f()));
        }
    }
}

