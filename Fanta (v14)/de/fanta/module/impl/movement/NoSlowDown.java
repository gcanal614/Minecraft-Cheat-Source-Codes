/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import java.awt.Color;

public class NoSlowDown
extends Module {
    public NoSlowDown() {
        super("NoSlowDown", 0, Module.Type.Movement, Color.YELLOW);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick) {
            if (NoSlowDown.mc.thePlayer.isBlocking() && NoSlowDown.mc.thePlayer.motionX == 0.0) {
                double cfr_ignored_0 = NoSlowDown.mc.thePlayer.motionZ;
            }
            if (NoSlowDown.mc.thePlayer.isBlocking() && NoSlowDown.mc.thePlayer.motionX == 0.0) {
                double cfr_ignored_1 = NoSlowDown.mc.thePlayer.motionZ;
            }
        }
    }
}

