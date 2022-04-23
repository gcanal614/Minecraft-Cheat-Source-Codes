/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.render;

import club.tifality.manager.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class Render3DEvent
implements Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public Render3DEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

