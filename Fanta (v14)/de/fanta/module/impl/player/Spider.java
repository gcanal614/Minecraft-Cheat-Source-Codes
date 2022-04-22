/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.TimeUtil;
import java.awt.Color;

public class Spider
extends Module {
    TimeUtil time = new TimeUtil();

    public Spider() {
        super("Spider", 0, Module.Type.Player, Color.red);
        this.settings.add(new Setting("SpiderMode", new DropdownBox("Intave", new String[]{"Intave", "AntiAC"})));
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onEvent(Event event) {
        block15: {
            if (!(event instanceof EventTick)) break block15;
            double yaw = Math.toRadians(Spider.mc.thePlayer.rotationYaw);
            switch (((DropdownBox)this.getSetting((String)"SpiderMode").getSetting()).curOption) {
                case "Intave": {
                    if (!Spider.mc.thePlayer.isCollidedHorizontally) break;
                    if (Spider.mc.thePlayer.onGround) {
                        Spider.mc.thePlayer.jump();
                    } else if (Spider.mc.thePlayer.motionY < 0.03) {
                        Spider.mc.thePlayer.motionY += 0.08;
                    }
                    if (!(Spider.mc.thePlayer.motionY > 0.03)) break;
                    Spider.mc.thePlayer.setPosition(Spider.mc.thePlayer.posX, Spider.mc.thePlayer.posY - 0.001, Spider.mc.thePlayer.posZ);
                    Spider.mc.thePlayer.onGround = true;
                    if (!(Spider.mc.thePlayer.moveForward > 0.0f)) break;
                    Spider.mc.thePlayer.motionX = -Math.sin(yaw) * 0.04;
                    Spider.mc.thePlayer.motionZ = Math.cos(yaw) * 0.04;
                    break;
                }
                case "AntiAC": {
                    if (Spider.mc.thePlayer.onGround) {
                        boolean cfr_ignored_0 = event instanceof EventTick;
                    }
                    Spider.mc.timer.timerSpeed = 1.0f;
                    Spider.mc.thePlayer.stepHeight = 0.1f;
                    Spider.mc.timer.timerSpeed = !Spider.mc.thePlayer.isCollidedHorizontally ? 1.0f : 1.0f;
                    if (Spider.mc.thePlayer.isCollidedHorizontally && Spider.mc.thePlayer.onGround) {
                        Spider.mc.thePlayer.jump();
                        break;
                    }
                    if (!Spider.mc.thePlayer.isCollidedHorizontally || Spider.mc.thePlayer.onGround || Spider.mc.thePlayer.ticksExisted % 2 != 0) break;
                    Spider.mc.thePlayer.onGround = true;
                    Spider.mc.thePlayer.motionY = 0.42f;
                }
            }
        }
    }
}

