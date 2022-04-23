// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.player;

import bozoware.base.util.Wrapper;
import bozoware.base.event.CancellableEvent;

public class UpdatePositionEvent extends CancellableEvent
{
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean isPre;
    
    public UpdatePositionEvent(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        this.isPre = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    public void setPre(final boolean pre) {
        this.isPre = pre;
    }
    
    public boolean isPre() {
        return this.isPre;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
        Wrapper.getPlayer().renderYawOffset = yaw;
        Wrapper.getPlayer().rotationYawHead = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
}
