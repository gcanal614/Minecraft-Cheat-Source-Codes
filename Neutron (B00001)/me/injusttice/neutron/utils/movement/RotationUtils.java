package me.injusttice.neutron.utils.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotationFromPosition(double var0, double var2, double var4) {
        double var5 = var0 - Minecraft.getMinecraft().thePlayer.posX;
        double var6 = var4 - Minecraft.getMinecraft().thePlayer.posZ;
        double var7 = var2 - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        double var8 = MathHelper.sqrt_double(var5 * var5 + var6 * var6);
        float var9 = (float)(Math.atan2(var6, var5) * 180.0 / 3.141592653589793) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7, var8) * 180.0 / 3.141592653589793));
        return new float[] { var9, var10 };
    }

    public static float[] getRotsByPos(double posX, double posY, double posZ) {
        EntityPlayerSP player = mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - player.posY + player.getEyeHeight();
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
        return new float[] { yaw, pitch };
    }

    public static Vec3 getVectorToEntity(Entity e) {
        float[] rots = getRotsByPos(e.posX, e.posY, e.posZ);
        float yaw = rots[0];
        float pitch = rots[1];
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((f1 * f2), f3, (f * f2));
    }

    public static float getYawToEntity(Entity entity) {
        EntityPlayerSP player = mc.thePlayer;
        return getYawBetween(player.rotationYaw, player.posX, player.posZ, entity.posX, entity.posZ);
    }

    public static float getYawBetween(float yaw, double srcX, double srcZ, double destX, double destZ) {
        double xDist = destX - srcX;
        double zDist = destZ - srcZ;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0D / 3.141592653589793D) - 90.0F;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }

    public static float[] getRotationsWithDir(EntityLivingBase entityIn, float hSpeed, float vSpeed, float oldYaw, float oldPitch) {
        float yaw = updateRotation(oldYaw,
                getNeededRotations(entityIn)[0], hSpeed);
        float pitch = updateRotation(oldPitch,
                getNeededRotations(entityIn)[1], vSpeed);
        return new float[] { yaw, pitch };
    }

    public static float[] getRotations(EntityLivingBase entityIn, float speed, float oldYaw, float oldPitch) {
        float yaw = updateRotation(oldYaw,
                getNeededRotations(entityIn)[0], speed);
        float pitch = updateRotation(oldPitch,
                getNeededRotations(entityIn)[1], speed);
        return new float[] { yaw, pitch };
    }

    public static float[] getRotations(Entity entity) {
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + (elb.getEyeHeight() - 0.4) - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[] { yaw, pitch };
    }

    private static float updateRotation(float currentRotation, float intendedRotation, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment)
            f = increment;
        if (f < -increment)
            f = -increment;
        return currentRotation + f;
    }

    public static float[] getNeededRotations(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - mc.thePlayer.posX;
        double d1 = entityIn.posZ - mc.thePlayer.posZ;
        double d2 = entityIn.posY + entityIn.getEyeHeight() - (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight();
        double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.func_181159_b(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)-(MathHelper.func_181159_b(d2, d3) * 180.0D / Math.PI);
        return new float[] { f, f1 };
    }

    public static float[] getRotationsWithDir(double x, double y, double z, float hSpeed, float vSpeed, float oldYaw, float oldPitch) {
        float yaw = updateRotation2(oldYaw,
                getNeededRotations(x, y, z)[0], hSpeed);
        float pitch = updateRotation2(oldPitch,
                getNeededRotations(x, y, z)[1], vSpeed);
        return new float[] { yaw, pitch };
    }

    private static float updateRotation2(float currentRotation, float intendedRotation, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment)
            f = increment;
        if (f < -increment)
            f = -increment;
        return currentRotation + f;
    }

    public static float[] getNeededRotations(double x, double y, double z) {
        double d0 = x - mc.thePlayer.posX;
        double d1 = z - mc.thePlayer.posZ;
        double d2 = y - (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight();
        double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.func_181159_b(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)-(MathHelper.func_181159_b(d2, d3) * 180.0D / Math.PI);
        return new float[] { f, f1 };
    }

    public static void applySmoothing(final float[] lastRotations,
                                      final float smoothing,
                                      final float[] dstRotation) {
        if (smoothing > 0.0F) {
            final float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
            final float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);

            final float smoothingFactor = Math.max(1.0F, smoothing / 10.0F);

            dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
            dstRotation[1] = Math.max(Math.min(90.0F, lastRotations[1] + pitchChange / smoothingFactor), -90.0F);
        }
    }

}
