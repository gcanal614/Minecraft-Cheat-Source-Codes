package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {

    public Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = null;
        setPacket(packet);
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}