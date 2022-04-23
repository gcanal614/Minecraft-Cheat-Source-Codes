// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.visual;

import bozoware.base.event.CancellableEvent;

public class EventRenderScoreboard extends CancellableEvent
{
    private final float width;
    private final float height;
    private float positionY;
    private float positionX;
    private boolean background;
    
    public EventRenderScoreboard(final float positionX, final float positionY, final float width, final float height, final int blurAmount) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.background = true;
    }
    
    public float getPositionX() {
        return this.positionX;
    }
    
    public void setPositionX(final float positionX) {
        this.positionX = positionX;
    }
    
    public float getPositionY() {
        return this.positionY;
    }
    
    public void setPositionY(final float positionY) {
        this.positionY = positionY;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public boolean getDrawBackground() {
        return this.background;
    }
    
    public void setBackground(final boolean background) {
        this.background = background;
    }
}
