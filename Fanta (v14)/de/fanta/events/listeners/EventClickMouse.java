/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;

public class EventClickMouse
extends Event<EventClickMouse> {
    private int slot;

    public EventClickMouse(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}

