/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.EventCancellable;
import net.minecraft.network.Packet;

public class EventReceivedPacket
extends EventCancellable {
    public static EventReceivedPacket INSTANCE;
    private Packet packet;

    public EventReceivedPacket(Packet packet) {
        INSTANCE = this;
        this.packet = packet;
    }

    @Override
    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

