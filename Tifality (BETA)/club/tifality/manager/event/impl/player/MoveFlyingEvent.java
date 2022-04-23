/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.Event;

public final class MoveFlyingEvent
implements Event {
    private float yaw;

    public MoveFlyingEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}

