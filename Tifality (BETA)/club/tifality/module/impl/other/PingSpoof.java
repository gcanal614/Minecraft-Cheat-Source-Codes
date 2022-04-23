/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.Wrapper;
import club.tifality.utils.server.ServerUtils;
import club.tifality.utils.timer.TimerUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

@ModuleInfo(label="PingSpoof", category=ModuleCategory.OTHER)
public final class PingSpoof
extends Module {
    private final List<Packet<?>> packets = new ArrayList();
    private final TimerUtil timer = new TimerUtil();

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (!ServerUtils.isOnHypixel()) {
                return;
            }
            if (Wrapper.getPlayer().ticksExisted < 5 && this.packets.size() > 0) {
                this.packets.clear();
                return;
            }
            if (this.timer.hasElapsed((long)(5000.0 + Math.random() * 4000.0))) {
                while (this.packets.size() > 0) {
                    Wrapper.sendPacketDirect(this.packets.remove(0));
                }
                this.timer.reset();
            }
        }
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent e) {
        if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
            C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction)e.getPacket();
            if (packet.getUid() < 0) {
                this.packets.add(packet);
                e.setCancelled();
            }
        } else if (e.getPacket() instanceof C00PacketKeepAlive) {
            this.packets.add(e.getPacket());
            e.setCancelled();
        }
    }
}

