/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import java.awt.Color;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class AntiVoid
extends Module {
    public AntiVoid() {
        super("AntiVoid", 0, Module.Type.Movement, Color.yellow);
        this.settings.add(new Setting("Modes", new DropdownBox("Intave", new String[]{"Watchdog", "Intave"})));
    }

    @Override
    public void onEnable() {
        AntiVoid.mc.thePlayer.respawnPlayer();
        AntiVoid.mc.thePlayer.isDead = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        AntiVoid.mc.thePlayer.respawnPlayer();
        AntiVoid.mc.thePlayer.isDead = false;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "Intave": {
                AntiVoid.mc.thePlayer.isDead = true;
                break;
            }
            case "Watchdog": {
                if (this.isBlockUnder() || !(AntiVoid.mc.thePlayer.fallDistance > 0.2f)) break;
                AntiVoid.mc.thePlayer.motionY = -1.0;
                Client.INSTANCE.moduleManager.getModule("Speed").setState(false);
            }
        }
    }

    public boolean isBlockUnder() {
        int i = (int)AntiVoid.mc.thePlayer.posY;
        while (i >= 0) {
            BlockPos position = new BlockPos(AntiVoid.mc.thePlayer.posX, (double)i, AntiVoid.mc.thePlayer.posZ);
            if (!(AntiVoid.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
            --i;
        }
        return false;
    }
}

