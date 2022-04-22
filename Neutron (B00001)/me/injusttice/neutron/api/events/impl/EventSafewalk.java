package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventSafewalk extends Event {
	private boolean safewalk;

	public boolean isSafewalk() {
		return this.safewalk;
	}

	public void setSafewalk(boolean safewalk) {
		this.safewalk = safewalk;
	}
}
