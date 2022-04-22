package me.event.impl;

import me.event.Event;

public class EventUpdate extends Event {
	private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    float lastyaw;
    float lastpitch;
    private boolean ground;

    public EventUpdate(double x, double y, double z, float yaw, float pitch, float lastyaw, float lastpitch, boolean ground) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastyaw = lastyaw;
        this.lastpitch = lastpitch;
        this.ground = ground;
        pre = true;
    }

    public EventUpdate() {
        pre = false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    
    public float getLastYaw() {
        return this.lastyaw;
    }
    
    public void setLastYaw(float lastyaw) {
        this.lastyaw = lastyaw;
    }
      
    public float getLastpitch() {
        return this.lastpitch;
    }
      
    public void setLastpitch(float lastpitch) {
        this.lastpitch = lastpitch;
    }

    public boolean getGroundState() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }
}