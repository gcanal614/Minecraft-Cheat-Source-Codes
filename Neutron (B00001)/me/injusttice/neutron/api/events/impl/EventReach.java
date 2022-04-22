package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventReach extends Event {
    public float reachDistance = -1.0F;

    public float getReachDistance() {
        return this.reachDistance;
    }

    public void setReachDistance(float reachDistance) {
        this.reachDistance = reachDistance;
    }
}
