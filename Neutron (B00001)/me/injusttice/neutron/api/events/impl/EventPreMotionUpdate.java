package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.client.Minecraft;

public class EventPreMotionUpdate extends Event {
    private float yaw, pitch;
    private boolean ground;
    public double x, y, z;
    Event.State state;
    float lastYaw, lastPitch;
    private float lastTickYaw;
    private float lastTickPitch;

    public EventPreMotionUpdate(float yaw, float pitch, float lastYaw, float lastTickYaw, float lastTickPitch, float lastPitch, boolean ground, double x, double y, double z) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lastTickYaw = lastTickYaw;
        this.lastTickPitch = lastTickPitch;
        this.lastYaw = lastYaw;
        this.lastPitch = lastPitch;
    }

    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;

        Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;
    }

    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean onGround() {
        return ground;
    }
    public void setGround(boolean ground) {
        this.ground = ground;
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
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public void setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public void setLastPitch(float lastPitch) {
        this.lastPitch = lastPitch;
    }

    public boolean isOnGround() {
        return ground;
    }

    public Event.State getState() {
        return state;
    }

    public void setState(Event.State state) {
        this.state = state;
    }

    public float getLastTickYaw() {
        return lastTickYaw;
    }

    public float getLastTickPitch() {
        return lastTickPitch;
    }

}
