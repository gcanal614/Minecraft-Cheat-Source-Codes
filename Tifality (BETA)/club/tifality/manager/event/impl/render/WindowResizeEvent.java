/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.render;

import club.tifality.manager.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class WindowResizeEvent
implements Event {
    private final ScaledResolution scaledResolution;

    public WindowResizeEvent(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }
}

