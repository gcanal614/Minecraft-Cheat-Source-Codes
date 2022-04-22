package stellar.skid.events.events;

import net.minecraft.entity.Entity;

public class LivingUpdateEvent implements Event {

    private final Entity entity;

    public LivingUpdateEvent(Entity entity) {
        this.entity = entity;

    }

    public Entity getEntity() {
        return this.entity;
    }

}