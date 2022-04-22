package com.zerosense.Events.impl;


import com.zerosense.Events.Event;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {

    public boolean isCancelled;
    private static Packet packet;

    public static Packet getPacket() {
        return packet;
    }
    
    public EventReceivePacket(final Packet packet) {
        this.packet = packet;
    }
    
    @Override
    public void setCancelled(final boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
