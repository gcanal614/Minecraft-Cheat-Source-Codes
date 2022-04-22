package stellar.skid.events.events;

import net.minecraft.network.Packet;
import stellar.skid.events.events.callables.CancellableEvent;

public class EventPacketSend extends CancellableEvent {
    private Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
