package non.asset.event.impl.render;

import non.asset.event.Event;

public class Render3DEvent extends Event {

	private float partialTicks;

	public Render3DEvent(final float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
}
