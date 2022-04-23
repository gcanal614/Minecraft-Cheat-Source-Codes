// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.player;

import bozoware.base.event.CancellableEvent;

public class PlayerMoveEvent extends CancellableEvent
{
    private double motionX;
    private double motionY;
    private double motionZ;
    
    public PlayerMoveEvent(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
}
