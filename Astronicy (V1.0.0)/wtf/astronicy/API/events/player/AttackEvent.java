package wtf.astronicy.API.events.player;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;
import net.minecraft.entity.Entity;

public final class AttackEvent extends Cancellable implements Event {
   private final Entity entity;

   public AttackEvent(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
