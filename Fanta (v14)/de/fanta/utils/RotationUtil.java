/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import de.fanta.utils.RenderUtil;
import de.fanta.utils.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtil {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static Rotation rotation;

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (mc.isGamePaused()) {
            return new float[]{0.0f, -90.0f};
        }
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - RotationUtil.mc.thePlayer.posX;
        double diffZ = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + (double)elb.getEyeHeight() - 0.4 - RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight();
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight();
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static Rotation mouseFix(float yaw, float pitch) {
        float curYaw = rotation == null ? RotationUtil.mc.thePlayer.rotationYaw : rotation.getYaw();
        float curPitch = rotation == null ? RotationUtil.mc.thePlayer.rotationPitch : rotation.getPitch();
        float f = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f * f * f * 1.2f;
        float nYaw = yaw - curYaw;
        nYaw -= nYaw % f2;
        yaw = curYaw + nYaw;
        float nPitch = pitch - curPitch;
        nPitch -= nPitch % f2;
        pitch = curPitch + nPitch;
        return new Rotation(yaw, MathHelper.clamp_int((int)pitch, -90, 90));
    }

    public static float getAngle(Entity entity) {
        double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, 1.0) - RenderUtil.interpolate(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.lastTickPosX, 1.0);
        double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, 1.0) - RenderUtil.interpolate(RotationUtil.mc.thePlayer.posZ, RotationUtil.mc.thePlayer.lastTickPosZ, 1.0);
        float yaw = (float)(-Math.toDegrees(Math.atan2(x, z)));
        return (float)((double)yaw - RenderUtil.interpolate(RotationUtil.mc.thePlayer.rotationYaw, RotationUtil.mc.thePlayer.prevRotationYaw, 1.0));
    }
}

