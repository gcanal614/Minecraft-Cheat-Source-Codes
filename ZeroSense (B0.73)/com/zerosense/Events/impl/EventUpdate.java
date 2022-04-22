package com.zerosense.Events.impl;


import com.zerosense.Events.Event;

public class EventUpdate extends Event<EventUpdate> {
    private float yaw, pitch;

    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }
    public  void setPitch(float pitch) {
        pitch = pitch;
    }

    public enum State{
        PRE,
        POST
    }
}
