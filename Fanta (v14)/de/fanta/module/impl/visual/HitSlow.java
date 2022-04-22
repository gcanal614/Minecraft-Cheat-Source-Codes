/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import java.awt.Color;

public class HitSlow
extends Module {
    public static double hitSlowdown;

    public HitSlow() {
        super("HitSlow", 0, Module.Type.Visual, new Color(108, 2, 139));
        this.settings.add(new Setting("SlowDown", new Slider(0.0, 1000.0, 1.0, 50.0)));
    }

    @Override
    public void onEvent(Event event) {
        hitSlowdown = ((Slider)this.getSetting((String)"SlowDown").getSetting()).curValue;
    }
}

