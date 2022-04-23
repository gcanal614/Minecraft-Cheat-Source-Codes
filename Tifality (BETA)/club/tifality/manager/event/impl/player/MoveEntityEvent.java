/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.CancellableEvent;

public final class MoveEntityEvent
extends CancellableEvent {
    private double x;
    private double y;
    private double z;
    public boolean isSafeWalk = false;

    public MoveEntityEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isSafeWalk() {
        return this.isSafeWalk;
    }
}

