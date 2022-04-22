package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.client.Minecraft;

public class EventMove extends Event {

    public double x;
    public double y;
    public double z;

    private final Minecraft getMc = Minecraft.getMinecraft();

    public EventMove(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
        this.getMc.thePlayer.motionX = x;
    }

    public void setY(double y) {
        this.y = y;
        this.getMc.thePlayer.motionY = y;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
        this.getMc.thePlayer.motionZ = z;
    }
}
