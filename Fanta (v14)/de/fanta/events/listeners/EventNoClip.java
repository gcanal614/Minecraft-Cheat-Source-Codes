/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;
import de.fanta.events.EventType;

public class EventNoClip
extends Event<EventNoClip> {
    public boolean noClip;
    public EventType type;

    public EventNoClip(boolean noClip) {
        this.noClip = noClip;
    }

    @Override
    public void setType(EventType type) {
        this.type = type;
    }
}

