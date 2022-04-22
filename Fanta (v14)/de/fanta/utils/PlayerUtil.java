/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtil {
    static Minecraft mc = Minecraft.getMinecraft();

    public static void setSpeed(double speed) {
        PlayerUtil.mc.thePlayer.motionX = -(Math.sin(PlayerUtil.getDirection()) * speed);
        PlayerUtil.mc.thePlayer.motionZ = Math.cos(PlayerUtil.getDirection()) * speed;
    }

    public static float getDirection() {
        float RotationYaw = PlayerUtil.mc.thePlayer.rotationYaw;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            RotationYaw += 20.0f;
        }
        float MoveForward = 0.5f;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            MoveForward = -0.5f;
        } else if (PlayerUtil.mc.thePlayer.moveForward > 0.0f) {
            MoveForward = 0.0f;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing > 0.0f) {
            RotationYaw -= 1.0f * MoveForward;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing < 0.0f) {
            RotationYaw += 1.0f * MoveForward;
        }
        return RotationYaw *= 0.017f;
    }

    public static boolean isInTablist(Entity entity) {
        for (NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {
            if (!playerInfo.getGameProfile().getName().equalsIgnoreCase(entity.getName()) && playerInfo.getGameProfile().getName().contains(entity.getName())) continue;
            return true;
        }
        return false;
    }

    public static double getDistanceToBlock(BlockPos pos) {
        double f = PlayerUtil.mc.thePlayer.posX - (double)pos.getX();
        double f1 = PlayerUtil.mc.thePlayer.posY - (double)pos.getY();
        double f2 = PlayerUtil.mc.thePlayer.posZ - (double)pos.getZ();
        return MathHelper.sqrt_double(f * f + f1 * f1 + f2 * f2);
    }

    public static void verusdmg() {
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + 3.001, PlayerUtil.mc.thePlayer.posZ, false));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ, false));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ, true));
    }
}

