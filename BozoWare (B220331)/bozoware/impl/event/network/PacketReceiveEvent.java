// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.network;

import net.minecraft.network.Packet;
import bozoware.base.event.CancellableEvent;

public class PacketReceiveEvent extends CancellableEvent
{
    private Packet<?> packet;
    
    public PacketReceiveEvent(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}
