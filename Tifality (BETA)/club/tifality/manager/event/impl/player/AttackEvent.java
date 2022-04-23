/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.CancellableEvent;
import net.minecraft.entity.EntityLivingBase;

public final class AttackEvent
extends CancellableEvent {
    private final EntityLivingBase entity;

    public AttackEvent(EntityLivingBase entity) {
        this.entity = entity;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }
}

