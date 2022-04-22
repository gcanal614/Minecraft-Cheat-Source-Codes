package non.asset.event.impl.player;

import non.asset.event.cancelable.CancelableEvent;

public class SlowdownEvent extends CancelableEvent {

	private Type type;
	
	public SlowdownEvent(final Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public enum Type {
		Item, Sprinting, SoulSand, Water
	}
}
