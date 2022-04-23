/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.entity;

import club.tifality.manager.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class EntitySwingEvent
implements Event {
    private final int entityId;

    public EntitySwingEvent(EntityLivingBase entity) {
        this.entityId = entity.getEntityId();
    }

    public int getEntityId() {
        return this.entityId;
    }
}

