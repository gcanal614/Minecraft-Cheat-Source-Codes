package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderOverlay extends Event {

    private final ScaledResolution resolution;

    public EventRenderOverlay() {
        resolution = new ScaledResolution(Minecraft.getMinecraft());
    }

    public ScaledResolution getResolution() {
        return resolution;
    }
}
