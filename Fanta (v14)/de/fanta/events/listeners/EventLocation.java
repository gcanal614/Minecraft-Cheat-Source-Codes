/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;

public class EventLocation
extends Event<EventLocation> {
    public float yaw;
    public float pitch;

    public EventLocation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}

