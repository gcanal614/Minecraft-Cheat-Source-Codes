package com.thunderware.events.listeners;

import com.thunderware.events.Event;

public class EventSlowDown extends Event<EventSlowDown> {
	double forwardMultiplier;
	double strafeMultiplier;
	
	public EventSlowDown(double fMulti, double sMulti) {
		this.forwardMultiplier = fMulti;
		this.strafeMultiplier = sMulti;
	}

	public double getForwardMultiplier() {
		return forwardMultiplier;
	}

	public void setForwardMultiplier(double forwardMultiplier) {
		this.forwardMultiplier = forwardMultiplier;
	}

	public double getStrafeMultiplier() {
		return strafeMultiplier;
	}

	public void setStrafeMultiplier(double strafeMultiplier) {
		this.strafeMultiplier = strafeMultiplier;
	}
}
