/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;

public class EventKey
extends Event<EventKey> {
    public int code;
    public PressType type;

    public EventKey(int code, PressType type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static enum PressType {
        PRESSED,
        HELT;

    }
}

