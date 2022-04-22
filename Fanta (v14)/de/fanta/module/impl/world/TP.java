/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.world;

import de.fanta.command.impl.Spec;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class TP
extends Module {
    public static double BoostSpeed;

    public TP() {
        super("TP", 0, Module.Type.World, Color.WHITE);
        this.settings.add(new Setting("Command", new CheckBox(false)));
        this.settings.add(new Setting("Boost", new CheckBox(false)));
        this.settings.add(new Setting("MineplexFast", new CheckBox(false)));
    }

    @Override
    public void onEvent(Event event) {
        TP.mc.thePlayer.onGround = true;
        if (event instanceof EventUpdate) {
            double forward;
            String name;
            TP.mc.thePlayer.motionY = 0.0;
            if (((CheckBox)this.getSetting((String)"Command").getSetting()).state) {
                for (Entity entity : TP.mc.theWorld.loadedEntityList) {
                    name = Spec.name;
                    if (!entity.getName().equalsIgnoreCase(name)) continue;
                    forward = 2.01;
                    TP.mc.thePlayer.setPosition(entity.posX + -Math.sin(Math.toRadians(entity.rotationYaw)) * forward, entity.posY, entity.posZ + Math.cos(Math.toRadians(entity.rotationYaw)) * forward);
                }
            }
            for (Entity entity : TP.mc.theWorld.loadedEntityList) {
                name = Spec.name;
                if (!entity.getName().equalsIgnoreCase(name)) continue;
                if (((CheckBox)this.getSetting((String)"MineplexFast").getSetting()).state) {
                    forward = 3.27;
                    TP.mc.thePlayer.setPosition(entity.posX + -Math.sin(Math.toRadians(entity.rotationYaw)) * forward, entity.posY, entity.posZ + Math.cos(Math.toRadians(entity.rotationYaw)) * forward);
                    continue;
                }
                TP.mc.thePlayer.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
            }
        }
    }
}

