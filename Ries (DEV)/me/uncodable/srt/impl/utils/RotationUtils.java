/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.RandomUtils
 *  store.intent.intentguard.annotation.Native
 */
package me.uncodable.srt.impl.utils;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.RandomUtils;
import store.intent.intentguard.annotation.Native;

@Native
public class RotationUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final Random RANDOM = new Random();

    public static float[] doBasicRotations(Entity entity) {
        if (entity != null) {
            double diffY;
            double diffX = entity.posX - RotationUtils.MC.thePlayer.posX;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            } else {
                diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            }
            double diffZ = entity.posZ - RotationUtils.MC.thePlayer.posZ;
            double dist = Math.hypot(diffX, diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
            float pitch = MathHelper.clamp_float((float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI)), -90.0f, 90.0f);
            return new float[]{RotationUtils.MC.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.MC.thePlayer.rotationYaw), RotationUtils.MC.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.MC.thePlayer.rotationPitch)};
        }
        return null;
    }

    public static float[] doBypassIRotations(Entity entity) {
        if (entity != null) {
            double diffY;
            double diffX = entity.posX - RotationUtils.MC.thePlayer.posX;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            } else {
                diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            }
            double diffZ = entity.posZ - RotationUtils.MC.thePlayer.posZ;
            double dist = Math.hypot(diffX, diffZ);
            float sensitivity = RotationUtils.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float gcd = sensitivity * sensitivity * sensitivity * 1.2f;
            float yawRand = RANDOM.nextBoolean() ? -RandomUtils.nextFloat((float)0.0f, (float)3.0f) : RandomUtils.nextFloat((float)0.0f, (float)3.0f);
            float pitchRand = RANDOM.nextBoolean() ? -RandomUtils.nextFloat((float)0.0f, (float)3.0f) : RandomUtils.nextFloat((float)0.0f, (float)3.0f);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f + yawRand;
            float pitch = MathHelper.clamp_float((float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + pitchRand + MathHelper.clamp_float(RotationUtils.MC.thePlayer.getDistanceToEntity(entity) * 1.25f, 0.0f, 6.0f), -90.0f, 90.0f);
            if (RotationUtils.MC.thePlayer.ticksExisted % 2 == 0) {
                pitch = MathHelper.clamp_float(pitch + (RANDOM.nextBoolean() ? RandomUtils.nextFloat((float)2.0f, (float)8.0f) : -RandomUtils.nextFloat((float)2.0f, (float)8.0f)), -90.0f, 90.0f);
            }
            pitch -= pitch % gcd;
            yaw -= yaw % gcd;
            return new float[]{RotationUtils.MC.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.MC.thePlayer.rotationYaw), RotationUtils.MC.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.MC.thePlayer.rotationPitch)};
        }
        return null;
    }

    public static float[] doBypassIIRotations(Entity entity) {
        if (entity != null) {
            double diffY;
            double diffX = entity.posX - RotationUtils.MC.thePlayer.posX;
            double diffZ = entity.posZ - RotationUtils.MC.thePlayer.posZ;
            double dist = Math.hypot(diffX, diffZ);
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            } else {
                diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.MC.thePlayer.posY + (double)RotationUtils.MC.thePlayer.getEyeHeight());
            }
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
            float pitch = MathHelper.clamp_float((float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI)), -90.0f, 90.0f);
            return new float[]{RotationUtils.MC.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.MC.thePlayer.rotationYaw), RotationUtils.MC.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.MC.thePlayer.rotationPitch)};
        }
        return null;
    }
}

