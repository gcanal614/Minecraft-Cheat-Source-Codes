package com.zerosense.Events;

public class MotionEventUpdate extends Event<EventMotionUpdate> {
    private float yaw;
    private float pitch;

    private State state;

    public MotionEventUpdate(float yaw, float pitch, State state) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.state = state;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        pitch = pitch;
    }

    public State getState() {
        return state;
    }

    public enum State {
        PRE, POST;
    }
}
