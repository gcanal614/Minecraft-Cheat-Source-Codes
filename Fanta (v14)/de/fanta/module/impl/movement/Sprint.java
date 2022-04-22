/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.settings.CheckBox;
import java.awt.Color;

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", 0, Module.Type.Movement, new Color(108, 2, 139));
    }

    @Override
    public void onEvent(Event event) {
        if (Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
            Sprint.mc.gameSettings.keyBindSprint.pressed = false;
        } else if (Sprint.mc.gameSettings.keyBindForward.pressed || Client.INSTANCE.moduleManager.getModule("Killaura").isState() && !((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Killaura").getSetting((String)"LegitRange").getSetting()).state) {
            Sprint.mc.gameSettings.keyBindSprint.pressed = true;
        }
    }
}

