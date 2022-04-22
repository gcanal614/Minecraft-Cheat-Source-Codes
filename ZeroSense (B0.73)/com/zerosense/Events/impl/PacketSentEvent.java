package com.zerosense.Events.impl;

import com.zerosense.Events.Event;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class PacketSentEvent extends Event
{
    private static Packet packet;
    private boolean canceled;
    
    public PacketSentEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public static Packet getPacket() {
        return packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
}
