/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.packet;

import club.tifality.manager.event.CancellableEvent;
import net.minecraft.network.Packet;

public final class PacketSendEvent
extends CancellableEvent {
    private Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}

