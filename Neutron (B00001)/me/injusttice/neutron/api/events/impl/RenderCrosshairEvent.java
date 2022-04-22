package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class RenderCrosshairEvent extends Event {

    private final ScaledResolution sr;

    public RenderCrosshairEvent(ScaledResolution sr) {
        this.sr = sr;
    }

    public ScaledResolution getScaledRes() {
        return this.sr;
    }
}
