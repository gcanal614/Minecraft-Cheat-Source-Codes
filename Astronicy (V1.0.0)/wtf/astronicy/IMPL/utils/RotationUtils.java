package wtf.astronicy.IMPL.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();


    public static float[] getFacingRotations2(final int paramInt1, final double d, final int paramInt3) {
        final EntitySnowball localEntityPig = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        localEntityPig.posX = paramInt1 + 0.5;
        localEntityPig.posY = d + 0.5;
        localEntityPig.posZ = paramInt3 + 0.5;
        return getRotationsNeeded(localEntityPig);
    }

    public static float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        Minecraft mc = Minecraft.getMinecraft();
        final double xSize = entity.posX - mc.thePlayer.posX;
        final double ySize = entity.posY + entity.getEyeHeight() / 2 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double zSize = entity.posZ - mc.thePlayer.posZ;
        final double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
        final float yaw = (float) (Math.atan2(zSize, xSize) * 180 / Math.PI) - 90;
        final float pitch = (float) (-(Math.atan2(ySize, theta) * 180 / Math.PI));
        return new float[]{(mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) % 360, (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) % 360.0f};
    }

    public static float clampRotation() {
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }


    public static float[] getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + (double)player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = (double)MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0D / 3.141592653589793D));
        return new float[]{yaw, pitch};
    }

    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f2 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f3 = -MathHelper.cos(-pitch * 0.017453292F);
        float f4 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double)(f2 * f3), (double)f4, (double)(f * f3));
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

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        return PlayerUtils.isOnHypixel() && mc.thePlayer.isMoving() ? getRotations(entity.posX + MathUtils.randomNumber(0.03D, -0.03D), entity.posY + (double)entity.getEyeHeight() - 0.4D + MathUtils.randomNumber(0.07D, -0.07D), entity.posZ + MathUtils.randomNumber(0.03D, -0.03D)) : getRotations(entity.posX, entity.posY + (double)entity.getEyeHeight() - 0.4D, entity.posZ);
    }
    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double y = ent.posY + ent.getEyeHeight() / 2.0f;
        double z = ent.posZ;
        return RotationUtils.getRotationFromPosition(x, y, z);
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[] { yaw, pitch };
    }
}