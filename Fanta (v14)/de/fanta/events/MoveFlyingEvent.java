/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events;

import de.fanta.events.Event;

public class MoveFlyingEvent
extends Event<MoveFlyingEvent> {
    public float yaw;

    public MoveFlyingEvent(float yaw) {
        this.yaw = yaw;
    }
}

