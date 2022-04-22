package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

    String[] modes = new String[] {"Packet", "Watchdog", "Safe Packet", "Verus", "Verus Damage", "Verus Float" };
    public ModeSet mode = new ModeSet("Mode", "Packet", modes);
    int watchdogSafeState;
    int verusState;

    public NoFall() {
        super("NoFall", 0, Category.PLAYER);
        watchdogSafeState = 0;
        verusState = 0;
        addSettings(mode);
    }

    @EventTarget
    public void onPre(EventMotion event) {
        setDisplayName("No Fall §7" + mode.getMode());
        if (mc.thePlayer.onGround) {
            watchdogSafeState = 0;
            verusState = 0;
        }
        switch (mode.getMode()) {
            case "Verus Float":
                if (localPlayer.fallDistance > 3.4D && !MovementUtils.isOverVoid()) {
                    event.setOnGround(true);
                    localPlayer.motionY = 0.0D;
                    localPlayer.fallDistance = 0.0F;
                }
                break;
            case "Verus":
                if (localPlayer.fallDistance > 2.9D && !MovementUtils.isOverVoid()) {
                    event.setOnGround(true);
                    localPlayer.fallDistance = 0.0F;
                }
                break;
            case "Verus Damage":
                if (localPlayer.fallDistance > 3.95D && !MovementUtils.isOverVoid()) {
                    event.setOnGround(true);
                    localPlayer.fallDistance = 0.0F;
                }
                break;
            case "Watchdog":
                if (!mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && mc.thePlayer.motionY < 0.0d && mc.thePlayer.fallDistance > 3.0f) {
                    mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
                }
                break;
            case "Safe Packet":
                if (MovementUtils.isOverVoid())
                    return;
                if (watchdogSafeState < 1) {
                    if (mc.thePlayer.fallDistance > 2.7D) {
                        PacketUtil.sendPacket(new C03PacketPlayer(true));
                        mc.thePlayer.fallDistance = 0.0F;
                        watchdogSafeState++;
                    }
                    break;
                }
                if (mc.thePlayer.fallDistance > 3.9D) {
                    PacketUtil.sendPacket(new C03PacketPlayer(true));
                    mc.thePlayer.fallDistance = 0.0F;
                }
                break;
            case "Packet":
                if (mc.thePlayer.fallDistance > 2.9D)
                    PacketUtil.sendPacket(new C03PacketPlayer(true));
                break;
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket e) {
            C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();

            if (!mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && mc.thePlayer.motionY < 0.0d && packet.isMoving() && mc.thePlayer.fallDistance > 2.0f) {
                e.setCancelled(true);
                mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.x, packet.y, packet.z, packet.onGround));
        }
    }

    public void onDisable() {
        super.onDisable();
    }

    public void onEnable() {
        super.onEnable();
    }
}
