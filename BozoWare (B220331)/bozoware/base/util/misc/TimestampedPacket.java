// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.misc;

import net.minecraft.network.Packet;

public class TimestampedPacket
{
    public final Packet packet;
    private long timestamp;
    
    public TimestampedPacket(final Packet packet, final long timestamp) {
        this.packet = packet;
        this.timestamp = timestamp;
    }
}
