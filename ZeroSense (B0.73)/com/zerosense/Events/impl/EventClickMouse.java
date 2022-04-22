package com.zerosense.Events.impl;

import com.zerosense.Events.Event;

public class EventClickMouse extends Event {

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    private int mouseX;
    private int mouseY;

    public EventClickMouse(int mouseX, int mouseY, int mouseButton) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.mouseButton = mouseButton;
    }

    private int mouseButton;
}
