package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventStepHeight extends Event {

    float stepHeight;

    public EventStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public float getStepHeight() {
        return this.stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }
}
