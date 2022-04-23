// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.visual;

import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.event.CancellableEvent;

public class Render2DEvent extends CancellableEvent
{
    private final ScaledResolution scaledResolution;
    
    public Render2DEvent(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
    
    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }
}
