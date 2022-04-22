/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Crosshair
extends Module {
    private final Map<String, Float> cachedStringWidth = new HashMap<String, Float>();

    public Crosshair() {
        super("Crosshair", 0, Module.Type.Visual, Color.pink);
        this.settings.add(new Setting("Alpha", new Slider(0.0, 255.0, 1.0, 50.0)));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender2D && event.isPre()) {
            ScaledResolution sr = new ScaledResolution(mc);
            Color red = new Color(0, 255, 0, (int)((Slider)this.getSetting((String)"Alpha").getSetting()).curValue);
            Gui.drawRect(sr.getScaledWidth() / 2 - 5, sr.getScaledHeight() / 2, sr.getScaledWidth() / 2 - 1, (float)(sr.getScaledHeight() / 2) + 1.15f, red.getRGB());
            Gui.drawRect(sr.getScaledWidth() / 2 + 3, sr.getScaledHeight() / 2, sr.getScaledWidth() / 2 + 7, (float)(sr.getScaledHeight() / 2) + 1.15f, red.getRGB());
            Gui.drawRect((float)(sr.getScaledWidth() / 2) + 0.5f, sr.getScaledHeight() / 2 + 2, (float)(sr.getScaledWidth() / 2) + 1.5f, sr.getScaledHeight() / 2 + 6, red.getRGB());
            Gui.drawRect((float)(sr.getScaledWidth() / 2) + 0.5f, sr.getScaledHeight() / 2 - 1, (float)(sr.getScaledWidth() / 2) + 1.5f, sr.getScaledHeight() / 2 - 5, red.getRGB());
        }
    }
}

