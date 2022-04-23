/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.Event;
import net.minecraft.entity.Entity;

public final class DamageEntityEvent
implements Event {
    private final Entity entity;
    private final double damage;

    public DamageEntityEvent(Entity entity, double damage) {
        this.entity = entity;
        this.damage = damage;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getDamage() {
        return this.damage;
    }
}

