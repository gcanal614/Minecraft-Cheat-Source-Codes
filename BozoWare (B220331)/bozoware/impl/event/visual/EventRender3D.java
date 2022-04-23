// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.visual;

import bozoware.base.event.CancellableEvent;

public class EventRender3D extends CancellableEvent
{
    public float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
