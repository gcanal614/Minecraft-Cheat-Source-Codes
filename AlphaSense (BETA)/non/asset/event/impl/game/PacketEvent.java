package non.asset.event.impl.game;

import net.minecraft.network.Packet;
import non.asset.event.cancelable.CancelableEvent;

public class PacketEvent extends CancelableEvent {
	private boolean sending;
	private Packet packet;
	public PacketEvent(Packet packet, boolean sending) {
		this.packet = packet;
		this.sending = sending;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public boolean isSending() {return sending; }
}
