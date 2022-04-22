package me.event.impl;

import me.event.Event;

public class EventRenderHUD extends Event {
	private float width, height, partialTicks;

    public EventRenderHUD(float width, float height, float partialTicks) {
        this.width = width;
        this.height = height;
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
		return partialTicks;
	}

	public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
