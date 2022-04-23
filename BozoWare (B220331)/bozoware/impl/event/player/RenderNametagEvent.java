// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.player;

import net.minecraft.entity.EntityLivingBase;
import bozoware.base.event.Event;
import bozoware.base.event.CancellableEvent;

public final class RenderNametagEvent extends CancellableEvent implements Event
{
    private final EntityLivingBase entity;
    private String renderedName;
    
    public RenderNametagEvent(final EntityLivingBase entity, final String renderedName) {
        this.entity = entity;
        this.renderedName = renderedName;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public String getRenderedName() {
        return this.renderedName;
    }
    
    public void setRenderedName(final String renderedName) {
        this.renderedName = renderedName;
    }
}
