package de.tired.event.events;

import de.tired.event.Event;

public class Render3DEvent2 extends Event {

    public float partialTicks;

    public Render3DEvent2(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
