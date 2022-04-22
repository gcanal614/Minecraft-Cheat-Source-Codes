package me.injusttice.neutron.impl.modules.impl.player;

import java.util.concurrent.ThreadLocalRandom;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventCollide;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;

public class Phase extends Module {

    public ModeSet mode = new ModeSet("Mode", "Watchdog", "BlocksMC VClip", "Instant", "Watchdog", "Custom", "Redesky", "Clip");
    public DoubleSet watchdogPackets = new DoubleSet("Watchdog Packets", 10.0D, 1.0D, 20.0D, 1.0D);
    public ModuleCategory subCategory = new ModuleCategory("Custom...");
    public DoubleSet offset = new DoubleSet("Offset", 0.204D, 0.1D, 0.5D, 0.005D);
    public BooleanSet motionY = new BooleanSet("MotionY", false);
    public BooleanSet packet = new BooleanSet("Packet", true);
    public BooleanSet packetGround = new BooleanSet("Packet Ground", false);
    public DoubleSet packetOffset = new DoubleSet("Packet Offset", 4.0D, 0.0D, 7.0D, 0.1D);
    public DoubleSet packetYOffset = new DoubleSet("Packet Y Offset", 4.0E-4D, 0.0D, 0.1D, 1.0E-4D);

    public Phase() {
        super("Phase", 0, Category.PLAYER);
        addSettings(mode, watchdogPackets, subCategory);
        subCategory.addCatSettings(offset, motionY, packetOffset, packetGround, packetOffset, packetYOffset);
    }

    @EventTarget
    public void onCollide(EventCollide e) {
        if (mode.getMode().equalsIgnoreCase("Clip"))
            e.setBoundingBox(null);
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();
            switch (mode.getMode()) {
                case "BlocksMC VClip":
                    if(message.contains(mc.thePlayer.getName() + " wants to fight")) {
                        mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY - 5, mc.thePlayer.posZ);
                    }
                    break;
            }
        }
    }

    @EventTarget
    public void onUpdate(EventMotion e) {
        double posX, posZ, posXdoggo, posZdoggo, editX, editZ, x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
        double direction = MovementUtils.getDirection();
        switch (mode.getMode()) {
            case "Custom":
                posX = -Math.sin(direction) * offset.getValue();
                posZ = Math.cos(direction) * offset.getValue();
                mc.thePlayer.onGround = true;
                mc.thePlayer.isCollidedVertically = true;
                mc.thePlayer.isCollided = true;
                if (motionY.enabled)
                    mc.thePlayer.motionY = 0.0D;
                if (MovementUtils.isMoving()) {
                    mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(posX, 0.0D, posZ));
                    mc.thePlayer.resetPositionToBB();
                }
                if (packet.enabled)
                    PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x + posX * packetOffset.getValue(), y + packetYOffset.getValue(), z + posZ * packetOffset.getValue(), packetGround.enabled));
                break;
            case "Watchdog":
                posXdoggo = -Math.sin(direction) * 0.2D;
                posZdoggo = Math.cos(direction) * 0.2D;
                if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && MovementUtils.isMoving()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + posXdoggo, mc.thePlayer.posY, mc.thePlayer.posZ + posZdoggo, true));
                    for (int i = 1; i < watchdogPackets.getValue(); i++)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, ThreadLocalRandom.current().nextDouble(161.0D, 200.0D), mc.thePlayer.posZ, true));
                    mc.thePlayer.setPosition(mc.thePlayer.posX + posXdoggo, mc.thePlayer.posY, mc.thePlayer.posZ + posZdoggo);
                }
                break;
            case "Instant":
                mc.thePlayer.onGround = true;
                editX = -Math.sin(direction) * 0.6973181D;
                editZ = Math.cos(direction) * 0.6973181D;
                if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && MovementUtils.isMoving()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + editX * 2.0D, mc.thePlayer.posY + 4.0E-4D, mc.thePlayer.posZ + editZ * 2.0D, false));
                    mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(editX, 0.0D, editZ));
                    mc.thePlayer.resetPositionToBB();
                }
                break;
            case "Redesky":
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-7D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                setToggled(false);
                break;
        }
    }
}
