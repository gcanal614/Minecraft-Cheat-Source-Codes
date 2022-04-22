/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;

public class EventMouse
extends Event<EventMouse> {
    private int buttonID;
    private boolean mouseDown;

    public EventMouse(int buttonID, boolean mouseDown) {
        this.buttonID = buttonID;
    }

    public int getButtonID() {
        return this.buttonID;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }

    public boolean isMouseDown() {
        return this.mouseDown;
    }

    public void setMouseDown(boolean mouseDown) {
        this.mouseDown = mouseDown;
    }

    public boolean isMotionEvent() {
        return this.buttonID == -1;
    }
}

