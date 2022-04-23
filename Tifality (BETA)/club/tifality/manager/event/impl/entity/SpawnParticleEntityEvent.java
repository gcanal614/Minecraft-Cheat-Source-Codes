/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.entity;

import club.tifality.manager.event.CancellableEvent;

public final class SpawnParticleEntityEvent
extends CancellableEvent {
    private final int type;
    private int multiplier;

    public SpawnParticleEntityEvent(int type2, int multiplier) {
        this.type = type2;
        this.multiplier = multiplier;
    }

    public int getType() {
        return this.type;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}

