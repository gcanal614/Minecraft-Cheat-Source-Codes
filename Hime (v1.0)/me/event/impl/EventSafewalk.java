package me.event.impl;

import me.event.Event;

public class EventSafewalk extends Event {
	
	private boolean isSafe;
	
	public boolean isSafe() {
		return isSafe;
	}
	
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}

}
