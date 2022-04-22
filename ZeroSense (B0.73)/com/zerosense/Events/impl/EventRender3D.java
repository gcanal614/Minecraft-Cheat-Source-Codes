package com.zerosense.Events.impl;


import com.zerosense.Events.Event;

public class EventRender3D extends Event<EventRender3D> {
    private float partialTicks;

    public void setPartialTicks(float paramFloat) {
        this.partialTicks = paramFloat;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public EventRender3D(float paramFloat) {
        this.partialTicks = paramFloat;
    }
}
