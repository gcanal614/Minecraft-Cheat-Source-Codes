package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class Event3D extends Event {
    private float partialTicks;

    public Event3D(float partialTicks){
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks(){
        return partialTicks;
    }
}
