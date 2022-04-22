/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import de.fanta.module.impl.world.Scaffold;
import de.fanta.utils.RandomUtil;
import de.fanta.utils.TimeUtil;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Rotations {
    public static float yaw;
    public static float pitch;
    public static boolean RotationInUse;
    static Minecraft mc;
    Random random = new Random();
    float ran = 0.0f;
    static TimeUtil time;
    public static float friction;
    public static float strafe;
    public static float forward;
    public static float f1;
    public static float f2;

    static {
        mc = Minecraft.getMinecraft();
        time = new TimeUtil();
        friction = 0.0f;
        strafe = 0.0f;
        forward = 0.0f;
        f1 = 0.0f;
        f2 = 0.0f;
    }

    public static int getRotation(double before, float after) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > (double)after) {
            if (before - (double)after > 180.0) {
                return 1;
            }
            return -1;
        }
        if ((double)after - before > 180.0) {
            return -1;
        }
        return 1;
    }

    public static boolean setYaw(float y, float speed) {
        Rotations.setRotation(yaw, pitch);
        if (speed >= 360.0f) {
            yaw = y;
            return true;
        }
        if (Rotations.isInRange(yaw, y, speed) || speed >= 360.0f) {
            yaw = y;
            return true;
        }
        yaw = Rotations.getRotation(yaw, y) < 0 ? (yaw -= speed) : (yaw += speed);
        return false;
    }

    public static boolean isInRange(double before, float after, float max) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > (double)after) {
            if (before - (double)after > 180.0 && 360.0 - before - (double)after <= (double)max) {
                return true;
            }
            return before - (double)after <= (double)max;
        }
        if ((double)after - before > 180.0 && (double)(360.0f - after) - before <= (double)max) {
            return true;
        }
        return (double)after - before <= (double)max;
    }

    public static boolean setPitch(float p, float speed) {
        if (p > 90.0f) {
            p = 90.0f;
        } else if (p < -90.0f) {
            p = -90.0f;
        }
        if (Math.abs(pitch - p) <= speed || speed >= 360.0f) {
            pitch = p;
            return false;
        }
        pitch = p < pitch ? (pitch -= speed) : (pitch += speed);
        return true;
    }

    public static void setRotation(float y, float p) {
        if (p > 90.0f) {
            p = 90.0f;
        } else if (p < -90.0f) {
            p = -90.0f;
        }
        yaw = y;
        pitch = p;
        RotationInUse = true;
    }

    public static float[] Intaveee(EntityPlayerSP player, EntityLivingBase target) {
        float RotationPitch2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 140.0, 180.0);
        float yaw2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 3.13, Math.PI);
        double posX = target.posX - player.posX;
        float RotationY2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 170.0, 180.0);
        float Rotationss = (float)MathHelper.getRandomDoubleInRange(new Random(), 87.0, 88.0);
        float Rotationsss = (float)MathHelper.getRandomDoubleInRange(new Random(), Rotationss, 90.0);
        float RotationY4 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.2, 0.3);
        float RotationY3 = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationY4, 0.1);
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getAge() + (double)player.getEyeHeight());
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / (double)yaw2) - Rotationsss;
        float pitch = (float)(-(Math.atan2(posY, var14) * (double)RotationPitch2 / Math.PI));
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float[] Intavee(EntityPlayerSP player, EntityLivingBase target) {
        float RotationPitch = (float)MathHelper.getRandomDoubleInRange(new Random(), 90.0, 92.0);
        float RotationYaw = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationPitch, 94.0);
        double posX = target.posX - player.posX;
        float RotationY2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 175.0, 180.0);
        float RotationY4 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.2, 0.3);
        float RotationY3 = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationY4, 0.1);
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getAge() + (double)player.getEyeHeight() + (double)RotationY3);
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * (double)RotationY2 / Math.PI) - RotationYaw;
        float pitch = (float)(-(Math.atan2(posY, var14) * (double)RotationY2 / Math.PI));
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float[] getKillAuraRotsTest(EntityPlayerSP player, EntityLivingBase target) {
        float RotationPitch = (float)MathHelper.getRandomDoubleInRange(new Random(), 170.0, 180.0);
        float RotationPitch2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 175.0, 180.0);
        float RotationYaw = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationPitch, 94.0);
        float yaw2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 3.13, Math.PI);
        double posX = target.posX - player.posX;
        float RotationY2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 178.0, 180.0);
        float RotationY4 = (float)MathHelper.getRandomDoubleInRange(new Random(), 1.0, 2.0);
        float RotationY3 = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationY4, 0.1);
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getAge() + (double)player.getEyeHeight());
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * 180.0 / Math.PI - (double)RotationY3));
        float f2 = Rotations.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float[] basicRotation(EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = Rotations.mc.thePlayer.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - Rotations.mc.thePlayer.posX;
        double y = ey - (Rotations.mc.thePlayer.posY + (double)Rotations.mc.thePlayer.getEyeHeight());
        double z = ez - Rotations.mc.thePlayer.posZ;
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yaw = Rotations.updateRotation(currentYaw, calcYaw, 180.0f);
        float pitch = Rotations.updateRotation(currentPitch, calcPitch, 180.0f);
        float f2 = Rotations.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRots(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yawSpeed = 40.0f;
        float pitchSpeed = 180.0f;
        float yaw = Rotations.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = Rotations.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        float f2 = Rotations.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (f > p_70663_3_) {
            f = p_70663_3_;
        }
        if (f < -p_70663_3_) {
            f = -p_70663_3_;
        }
        return p_70663_1_ + f;
    }

    public static float[] rotationrecode7(Scaffold.BlockData blockData) {
        double x = (double)Scaffold.BlockData.getPos().getX() + 0.5 - Rotations.mc.thePlayer.posX + (double)Scaffold.BlockData.getFacing().getFrontOffsetX() / 2.0;
        double z = (double)Scaffold.BlockData.getPos().getZ() + 0.5 - Rotations.mc.thePlayer.posZ + (double)Scaffold.BlockData.getFacing().getFrontOffsetZ() / 2.0;
        double y = (double)Scaffold.BlockData.getPos().getY() + 0.6;
        double ymax = Rotations.mc.thePlayer.posY + (double)Rotations.mc.thePlayer.getEyeHeight() - y;
        double allmax = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(ymax, allmax) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, MathHelper.clamp_float(pitch, 78.0f, 80.0f)};
    }

    public static float[] rotationrecode2(Scaffold.BlockData blockdata) {
        double x = (double)Scaffold.BlockData.getPos().getX() + 0.5 - Rotations.mc.thePlayer.posX + (double)Scaffold.BlockData.getFacing().getFrontOffsetX() / 2.0;
        double z = (double)Scaffold.BlockData.getPos().getZ() + 0.5 - Rotations.mc.thePlayer.posZ + (double)Scaffold.BlockData.getFacing().getFrontOffsetZ() / 2.0;
        double y = Scaffold.BlockData.getPos().getY();
        double ymax = Rotations.mc.thePlayer.posY + (double)Rotations.mc.thePlayer.getEyeHeight() - y;
        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitchAngle = (float)(-(MathHelper.func_181159_b(y, angle) * 180.0 / Math.PI));
        float yaw = Rotations.updateRotation(Rotations.yaw, yawAngle, 180.0f);
        float pitch = Rotations.updateRotation(Rotations.pitch, pitchAngle, 180.0f);
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, MathHelper.clamp_float(pitch, 81.0f, 82.0f)};
    }

    public static float[] testScaffoldRots(BlockPos pos) {
        double x = (double)pos.getX() + 0.5 - Rotations.mc.thePlayer.posX + (double)Rotations.mc.thePlayer.getHorizontalFacing().getOpposite().getFrontOffsetX() / 2.0;
        double y = (double)pos.getY() - (Rotations.mc.thePlayer.posY + (double)Rotations.mc.thePlayer.getEyeHeight()) + 0.6;
        double z = (double)pos.getZ() + 0.5 - Rotations.mc.thePlayer.posZ + (double)Rotations.mc.thePlayer.getHorizontalFacing().getOpposite().getFrontOffsetZ() / 2.0;
        double diffX = x - Rotations.mc.thePlayer.posX;
        double diffY = y - Rotations.mc.thePlayer.posY + (double)Rotations.mc.thePlayer.getEyeHeight();
        double diffZ = z - Rotations.mc.thePlayer.posZ;
        double allmax = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 85.0f;
        float pitch = (float)(-Math.atan2(y, allmax) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static float getPitch() {
        if (RotationInUse) {
            return pitch;
        }
        return Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

    public static float getYaw() {
        if (RotationInUse) {
            return yaw;
        }
        return Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public static float[] getRotationsForPoint(Vec3 point) {
        if (point == null) {
            return null;
        }
        double diffX = point.xCoord - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = point.yCoord - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight() - 0.6);
        double diffZ = point.zCoord - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 1.0 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.5 * Math.random());
    }

    public static Vec3 getCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }

    public static Vec3 getCenter2(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5 - 5.0, bb.minY + (bb.maxY - bb.minY) * 0.1, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }

    public static float getYawToPoint(Vec3 p) {
        float[] rotations = Rotations.getRotationsForPoint(p);
        return rotations[0];
    }

    public static float getPitchToPoint(Vec3 p) {
        float[] rotations = Rotations.getRotationsForPoint(p);
        return rotations[1];
    }

    public void onTick() {
        if (!RotationInUse) {
            yaw = Rotations.mc.thePlayer.rotationYaw;
            pitch = Rotations.mc.thePlayer.rotationPitch;
        }
    }
}

