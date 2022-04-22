/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.combat;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.events.listeners.PlayerMoveEvent;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.module.impl.movement.Speed;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity
extends Module {
    TimeUtil time = new TimeUtil();
    TimeUtil time2 = new TimeUtil();
    private boolean flag;

    public Velocity() {
        super("Velocity", 0, Module.Type.Combat, Color.orange);
        this.settings.add(new Setting("Modes", new DropdownBox("GommeQSG", new String[]{"Null", "OldGomme", "GommeQSG", "GommeBW|SW", "GommeNull", "OldAAC", "Legit", "AAC 3.3.13", "Reverse++", "AntiGamingChair"})));
    }

    @Override
    public void onEnable() {
        this.flag = false;
    }

    @Override
    public void onDisable() {
        this.flag = false;
    }

    @Override
    public void onEvent(Event event) {
        double yaw = Math.toRadians(Velocity.mc.thePlayer.rotationYaw);
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "Null": {
                S12PacketEntityVelocity packet;
                if (!(event instanceof EventReceivedPacket)) break;
                Packet p = EventReceivedPacket.INSTANCE.getPacket();
                if (p instanceof S12PacketEntityVelocity && (packet = (S12PacketEntityVelocity)p).getEntityID() == Velocity.mc.thePlayer.getEntityId()) {
                    EventReceivedPacket.INSTANCE.setCancelled(true);
                }
                if (!(p instanceof S27PacketExplosion)) break;
                EventReceivedPacket.INSTANCE.setCancelled(true);
                break;
            }
            case "Legit": {
                if (Velocity.mc.thePlayer.hurtTime == 0 || !Velocity.mc.thePlayer.isCollidedHorizontally) break;
                Velocity.setSpeed(0.1);
                break;
            }
            case "OldGomme": {
                if (Velocity.mc.thePlayer.isInLava() || Client.INSTANCE.moduleManager.getModule("Longjump").isState()) break;
                if (Velocity.mc.thePlayer.hurtTime != 0) {
                    Velocity.setSpeed(0.2);
                    boolean cfr_ignored_0 = Velocity.mc.thePlayer.onGround;
                    break;
                }
                int cfr_ignored_1 = Velocity.mc.thePlayer.hurtTime;
                break;
            }
            case "AntiGamingChair": {
                if (Client.INSTANCE.moduleManager.getModule("Longjump").isState() || Velocity.mc.thePlayer.hurtTime == 0) break;
                Velocity.setSpeed(0.1);
                if (!Velocity.mc.thePlayer.onGround) break;
                Velocity.mc.thePlayer.motionY = 0.42f;
                Velocity.mc.thePlayer.motionX *= -1.0;
                Velocity.mc.thePlayer.motionZ *= -1.0;
                break;
            }
            case "AAC 3.3.13": {
                if (Velocity.mc.thePlayer.hurtTime == 0) break;
                Velocity.setSpeed(Speed.getSpeed());
                break;
            }
            case "Reverse++": {
                if (Velocity.mc.thePlayer.hurtTime == 0) break;
                Velocity.setSpeed(0.35);
                if (!Velocity.mc.thePlayer.onGround) break;
                Velocity.mc.thePlayer.motionY = 0.22f;
                break;
            }
            case "GommeQSG": {
                if (Velocity.mc.thePlayer.isPotionActive(1) || !Velocity.mc.thePlayer.onGround) break;
                Velocity.mc.gameSettings.keyBindSneak.pressed = false;
                if (Velocity.mc.thePlayer.ticksExisted % 25 != 0) break;
                Velocity.mc.thePlayer.motionX *= 0.56;
                Velocity.mc.thePlayer.motionZ *= 0.56;
                if (Velocity.mc.thePlayer.ticksExisted % 6 != 0) break;
                Velocity.mc.thePlayer.jump();
                break;
            }
            case "GommeBW|SW": {
                float xZ = (float)((double)((float)(-Math.sin(yaw))) * 3.1E-4);
                float zZ = (float)((double)((float)Math.cos(yaw)) * 3.1E-4);
                if (Velocity.mc.thePlayer.isPotionActive(1)) break;
                if (Velocity.mc.thePlayer.onGround) {
                    Velocity.mc.gameSettings.keyBindSneak.pressed = false;
                    if (Velocity.mc.thePlayer.ticksExisted % 6 != 0) break;
                    Velocity.mc.gameSettings.keyBindJump.isPressed();
                    break;
                }
                if (!this.time.hasReached(65L)) break;
                Velocity.mc.gameSettings.keyBindSneak.pressed = true;
                if (Velocity.mc.thePlayer.ticksExisted % 4 == 0) {
                    Velocity.mc.thePlayer.motionX = -Math.sin(yaw) * -0.05;
                    Velocity.mc.thePlayer.motionZ = Math.cos(yaw) * -0.05;
                }
                this.time.reset();
                break;
            }
            case "OldAAC": {
                EntityPlayerSP cfr_ignored_2 = Velocity.mc.thePlayer;
                if (EntityPlayerSP.movementInput.jump || Velocity.mc.thePlayer.hurtTime >= 4 || !((double)Velocity.mc.thePlayer.fallDistance < 1.5) || !this.time2.hasReached(Killaura.randomNumber(260, 170))) break;
                Velocity.mc.thePlayer.motionY -= 0.01;
                this.time2.reset();
            }
        }
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "OldGomme": {
                if (!(event instanceof PlayerMoveEvent) || Velocity.mc.thePlayer.hurtTime == 0 || !Velocity.mc.thePlayer.onGround) break;
                Velocity.setSpeed1(PlayerMoveEvent.INSTANCE, 0.0);
            }
        }
    }

    public static void setSpeed(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Velocity.mc.getMinecraft().thePlayer;
        double yaw = player.rotationYaw;
        boolean isMoving = player.moveForward != 0.0f || player.moveStrafing != 0.0f;
        boolean isMovingForward = player.moveForward > 0.0f;
        boolean isMovingBackward = player.moveForward < 0.0f;
        boolean isMovingRight = player.moveStrafing > 0.0f;
        boolean isMovingLeft = player.moveStrafing < 0.0f;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean bl = isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            } else if (isMovingForward) {
                yaw -= 45.0;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            } else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }

    public static void setSpeed1(PlayerMoveEvent moveEvent, double moveSpeed) {
        EntityPlayerSP cfr_ignored_0 = Velocity.mc.thePlayer;
        EntityPlayerSP cfr_ignored_1 = Velocity.mc.thePlayer;
        Velocity.setSpeed1(moveEvent, moveSpeed, Velocity.mc.thePlayer.rotationYaw, EntityPlayerSP.movementInput.moveStrafe, EntityPlayerSP.movementInput.moveForward);
    }

    public static void setSpeed1(PlayerMoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
}

