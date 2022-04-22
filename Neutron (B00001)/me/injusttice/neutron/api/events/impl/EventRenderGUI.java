package me.injusttice.neutron.api.events.impl;

import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGUI extends EventNigger<EventRenderGUI> {
    public ScaledResolution sr;

    public EventRenderGUI(ScaledResolution sr) {
        this.sr = sr;
    }
}
