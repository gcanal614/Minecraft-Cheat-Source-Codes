/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;

public class EventNoRotate
extends Event<EventNoRotate> {
    public float yaw;
    public float pitch;

    public EventNoRotate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}

