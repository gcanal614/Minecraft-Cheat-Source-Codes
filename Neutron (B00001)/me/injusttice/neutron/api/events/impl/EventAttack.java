package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.entity.Entity;

public class EventAttack extends Event {
  
  public Entity entity;
  
  public EventAttack(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return this.entity;
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }
}
