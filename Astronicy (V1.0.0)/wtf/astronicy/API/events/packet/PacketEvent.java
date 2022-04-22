package wtf.astronicy.API.events.packet;

import net.minecraft.network.Packet;
import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;

public class PacketEvent extends Cancellable implements Event {
    Packet packet;
    boolean sending;

    public PacketEvent(Packet packet, boolean sending) {
        this.packet = packet;
        this.sending = sending;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public boolean isSending() {
        return this.sending;
    }
}
