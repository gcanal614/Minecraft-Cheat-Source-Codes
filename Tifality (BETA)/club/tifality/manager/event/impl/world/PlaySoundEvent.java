/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.world;

import club.tifality.manager.event.CancellableEvent;

public final class PlaySoundEvent
extends CancellableEvent {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final double dist;
    private final String soundName;

    public PlaySoundEvent(double posX, double posY, double posZ, double dist, String soundName) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dist = dist;
        this.soundName = soundName;
    }

    public double getDist() {
        return this.dist;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public String getSoundName() {
        return this.soundName;
    }
}

