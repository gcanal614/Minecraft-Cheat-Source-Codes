package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EntityRenderEvent extends Event {
    private final float partialTicks;

    public EntityRenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
