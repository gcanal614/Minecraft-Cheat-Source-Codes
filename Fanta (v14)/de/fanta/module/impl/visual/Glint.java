/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.ColorValue;
import java.awt.Color;

public class Glint
extends Module {
    public Glint() {
        super("Glint", 0, Module.Type.Visual, new Color(198, 29, 0));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
    }
}

