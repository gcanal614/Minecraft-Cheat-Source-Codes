/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.render;

import club.tifality.manager.event.Event;
import club.tifality.utils.render.LockedResolution;

public final class Render2DEvent
implements Event {
    private final LockedResolution resolution;
    private final float partialTicks;

    public Render2DEvent(LockedResolution resolution, float partialTicks) {
        this.resolution = resolution;
        this.partialTicks = partialTicks;
    }

    public LockedResolution getResolution() {
        return this.resolution;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

