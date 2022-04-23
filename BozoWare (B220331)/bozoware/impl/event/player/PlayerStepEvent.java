// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.player;

import bozoware.base.event.Event;

public class PlayerStepEvent implements Event
{
    private float stepHeight;
    private double heightStepped;
    private boolean pre;
    
    public PlayerStepEvent(final float stepHeight) {
        this.stepHeight = stepHeight;
        this.pre = true;
    }
    
    public float getStepHeight() {
        return this.stepHeight;
    }
    
    public void setStepHeight(final float stepHeight) {
        this.stepHeight = stepHeight;
    }
    
    public double getHeightStepped() {
        return this.heightStepped;
    }
    
    public void setHeightStepped(final double heightStepped) {
        this.heightStepped = heightStepped;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
}
