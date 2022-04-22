package com.zerosense.Events.impl;

import com.zerosense.Events.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGUI extends Event {
    private ScaledResolution resolution;

    public void fire(ScaledResolution resolution) {
        this.resolution = resolution;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }
}
