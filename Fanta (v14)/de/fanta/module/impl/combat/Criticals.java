/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.combat;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.Killaura;
import java.awt.Color;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals
extends Module {
    public Criticals() {
        super("Criticals", 0, Module.Type.Combat, new Color(108, 2, 139));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick && Killaura.hasTarget() && Criticals.mc.thePlayer.ticksExisted % 4 == 0 && Criticals.mc.thePlayer.onGround && Client.INSTANCE.moduleManager.getModule("Killaura").isState() && !Client.INSTANCE.moduleManager.getModule("Flight").isState()) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + (double)0.05f, Criticals.mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, true));
        }
    }
}

