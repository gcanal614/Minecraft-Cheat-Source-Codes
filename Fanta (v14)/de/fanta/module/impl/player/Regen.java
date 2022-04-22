/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import java.awt.Color;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen
extends Module {
    public Regen() {
        super("Regen", 0, Module.Type.Player, Color.white);
        this.settings.add(new Setting("Modes", new DropdownBox("Intave", new String[]{"Intave"})));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick && Regen.mc.thePlayer.getHealth() < 20.0f && Regen.mc.thePlayer.getFoodStats().getFoodLevel() > 19) {
            int i = 0;
            while (i < 100) {
                Regen.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                ++i;
            }
        }
    }
}

