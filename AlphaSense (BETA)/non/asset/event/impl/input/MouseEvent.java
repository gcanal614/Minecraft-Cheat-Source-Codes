package non.asset.event.impl.input;

import non.asset.event.Event;

public class MouseEvent extends Event {
	private int button;

	public MouseEvent(final int button) {
		this.button = button;
	}

	public int getButton() {
		return this.button;
	}

	public void setButton(final int button) {
		this.button = button;
	}
}
