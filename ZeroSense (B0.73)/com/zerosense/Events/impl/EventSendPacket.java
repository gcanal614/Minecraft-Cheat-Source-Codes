package com.zerosense.Events.impl;


import com.zerosense.Events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event<EventSendPacket> {
    public boolean cancelled;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static Packet packet;

    public static Packet getPacket() {
        return packet;
    }

    public void setCancelled(boolean paramBoolean) {
        this.cancelled = paramBoolean;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public EventSendPacket(Packet paramPacket) {
        packet = paramPacket;
    }

    public void setPacket(Packet paramPacket) {
        packet = paramPacket;
    }


    public static void noEventSendPacket(Packet paramPacket) {
        packet = null;

    }


}
