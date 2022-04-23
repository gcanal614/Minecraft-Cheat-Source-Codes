/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

import me.uncodable.srt.impl.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class MovementUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static void setMoveSpeed(double moveSpeed, boolean breakWhenStopped) {
        float forward = MovementUtils.MC.thePlayer.movementInput.moveForward;
        float strafe = MovementUtils.MC.thePlayer.movementInput.moveStrafe;
        float yaw = MovementUtils.MC.thePlayer.rotationYaw;
        if ((double)forward == 0.0 && (double)strafe == 0.0 && breakWhenStopped) {
            MovementUtils.MC.thePlayer.motionX = 0.0;
            MovementUtils.MC.thePlayer.motionZ = 0.0;
        }
        int d = 45;
        if ((double)forward != 0.0) {
            if ((double)strafe > 0.0) {
                yaw += (float)((double)forward > 0.0 ? -d : d);
            } else if ((double)strafe < 0.0) {
                yaw += (float)((double)forward > 0.0 ? d : -d);
            }
            strafe = 0.0f;
            if ((double)forward > 0.0) {
                forward = 1.0f;
            } else if ((double)forward < 0.0) {
                forward = -1.0f;
            }
        }
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        MovementUtils.MC.thePlayer.motionX = (double)forward * moveSpeed * cos + (double)strafe * moveSpeed * sin;
        MovementUtils.MC.thePlayer.motionZ = (double)forward * moveSpeed * sin - (double)strafe * moveSpeed * cos;
    }

    public static void setMoveSpeed(double moveSpeed) {
        MovementUtils.setMoveSpeed(moveSpeed, true);
    }

    public static void addFriction() {
        MovementUtils.addFriction(0.91);
    }

    public static void addFriction(double friction) {
        MovementUtils.MC.thePlayer.motionX *= friction;
        MovementUtils.MC.thePlayer.motionZ *= friction;
    }

    public static void setMoveSpeedTeleport(double moveSpeed) {
        double x = -Math.sin(Math.toRadians(MovementUtils.MC.thePlayer.rotationYaw)) * moveSpeed;
        double z = Math.cos(Math.toRadians(MovementUtils.MC.thePlayer.rotationYaw)) * moveSpeed;
        EntityUtils.teleportToPos(new BlockPos(MovementUtils.MC.thePlayer.posX + x, MovementUtils.MC.thePlayer.posY, MovementUtils.MC.thePlayer.posZ + z), false);
        MovementUtils.MC.thePlayer.setPosition(MovementUtils.MC.thePlayer.posX + x, MovementUtils.MC.thePlayer.posY, MovementUtils.MC.thePlayer.posZ + z);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MovementUtils.MC.thePlayer != null && MovementUtils.MC.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = MovementUtils.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static boolean isMoving() {
        return MovementUtils.MC.thePlayer.moveForward != 0.0f || MovementUtils.MC.thePlayer.moveStrafing != 0.0f;
    }

    public static boolean isMoving2() {
        return MovementUtils.MC.thePlayer.moveForward != 0.0f || MovementUtils.MC.thePlayer.moveStrafing != 0.0f || MovementUtils.MC.gameSettings.keyBindJump.isKeyDown();
    }

    public static void zeroMotion() {
        MovementUtils.MC.thePlayer.motionZ = 0.0;
        MovementUtils.MC.thePlayer.motionX = 0.0;
    }

    public static void zeroMotion2() {
        MovementUtils.MC.thePlayer.motionZ = 0.0;
        MovementUtils.MC.thePlayer.motionY = 0.0;
        MovementUtils.MC.thePlayer.motionX = 0.0;
    }
}

