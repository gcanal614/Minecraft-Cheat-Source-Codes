/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.world;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.module.Module;
import java.awt.Color;

public class Vclip
extends Module {
    public Vclip() {
        super("Vclip", 0, Module.Type.World, Color.orange);
    }

    @Override
    public void onEvent(Event event) {
        if (Vclip.mc.gameSettings.keyBindSneak.isPressed() && !Client.INSTANCE.moduleManager.getModule("Flight").isState()) {
            Vclip.mc.thePlayer.setPosition(Vclip.mc.thePlayer.posX, Vclip.mc.thePlayer.posY - 3.0, Vclip.mc.thePlayer.posZ);
            if (!(Vclip.mc.theWorld != null && Vclip.mc.thePlayer != null || !Vclip.mc.thePlayer.onGround || Vclip.mc.thePlayer.isOnLadder() || Vclip.mc.thePlayer.isInWater())) {
                Vclip.mc.thePlayer.motionY -= 6.0;
            }
        }
    }
}

