/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.world;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventNoClip;
import de.fanta.module.Module;
import java.awt.Color;

public class Phase
extends Module {
    public Phase() {
        super("Phase", 0, Module.Type.World, Color.red);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventNoClip) {
            ((EventNoClip)event).noClip = true;
        }
        if (Phase.mc.gameSettings.keyBindSneak.pressed) {
            this.getPlayer().motionY = -0.1;
        } else if (Phase.mc.gameSettings.keyBindJump.pressed) {
            this.getPlayer().motionY = 0.1;
        }
    }
}

