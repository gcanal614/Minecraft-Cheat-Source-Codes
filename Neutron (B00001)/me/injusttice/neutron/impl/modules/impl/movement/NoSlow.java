package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.events.impl.EventPostMotion;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.network.PacketUtil;

import net.minecraft.network.play.server.S30PacketWindowItems;
import org.apache.commons.lang3.*;

import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

public class NoSlow extends Module {

    public ModeSet mode = new ModeSet("Mode", "Vanilla", "Vanilla", "NCP", "Hypixel");

    int ticks = 0;

    public NoSlow() {
        super("NoSlow", 0, Category.MOVEMENT);
        addSettings(mode);
    }

    @EventTarget
    public void onPacketRec(EventReceivePacket e) {
        if (mode.getMode().contains("Hypixel") && e.getPacket() instanceof S30PacketWindowItems && (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking())) {
            ticks = mc.thePlayer.ticksExisted;
            e.setCancelled(true);
        }
    }

    @EventTarget
    public void onPost(EventPostMotion e) {
        if (mode.getMode().contains("Hypixel")) {
            if (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) {
                int process = Math.min(Math.round(((mc.thePlayer.ticksExisted - ticks) / 30f * 100)), 100);
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                        mc.thePlayer.inventory.getCurrentItem(), 0f, 0f, 0f));
            } else {
                ticks = mc.thePlayer.ticksExisted;
            }
        }
    }

    public void onEvent(EventNigger e) {
        this.setDisplayName("No Slow");
        switch (mode.getMode()) {
            case "NCP":
                if (mc.thePlayer.isBlocking()) {
                    if (e.isPre()) {
                        PacketUtil.sendPacketSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -6, -1), EnumFacing.DOWN));
                    } else {
                        mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), RandomUtils.nextInt(1, Integer.MAX_VALUE));
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                    }
                }
                break;
        }
    }
}