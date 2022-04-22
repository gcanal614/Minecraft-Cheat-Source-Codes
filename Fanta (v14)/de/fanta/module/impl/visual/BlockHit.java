/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import java.awt.Color;

public class BlockHit
extends Module {
    public static BlockHit INSTANCE;

    public BlockHit() {
        super("BlockHit", 0, Module.Type.Visual, Color.magenta);
        this.settings.add(new Setting("AnimationMode", new DropdownBox("Fanta", new String[]{"Fanta", "Drop", "Astolfo", "Centaurus", "Exhibition", "Flux", "Sigma4", "Violence", "LB"})));
    }

    @Override
    public void onEvent(Event event) {
    }
}

