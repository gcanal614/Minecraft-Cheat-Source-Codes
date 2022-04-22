/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastUse
extends Module {
    public static double Packets;

    public FastUse() {
        super("FastUse", 0, Module.Type.Player, Color.YELLOW);
        this.settings.add(new Setting("Packets", new Slider(1.0, 30.0, 0.1, 4.0)));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick && FastUse.mc.thePlayer.getCurrentEquippedItem() != null && FastUse.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood && FastUse.mc.thePlayer.isUsingItem()) {
            Packets = ((Slider)this.getSetting((String)"Packets").getSetting()).curValue;
            int i = 0;
            while ((double)i < Packets) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
                ++i;
            }
        }
    }
}

