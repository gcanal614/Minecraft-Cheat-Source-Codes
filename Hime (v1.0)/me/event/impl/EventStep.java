package me.event.impl;

import me.event.Event;
import net.minecraft.entity.Entity;

public class EventStep extends Event {

    public float stepHeight;
    public Entity entity;

    public EventStep(float stepHeight, Entity entity) {
        this.stepHeight = stepHeight;
        this.entity = entity;
        pre = true;
    }
    
    public EventStep() {
        pre = false;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }


}
