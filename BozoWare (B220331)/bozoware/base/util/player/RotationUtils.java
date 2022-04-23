// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;

public final class RotationUtils
{
    private static final Minecraft mc;
    
    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = RotationUtils.mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getNeededRotations(final EntityLivingBase entityIn) {
        final double d0 = entityIn.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double d2 = entityIn.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double d3 = entityIn.posY + entityIn.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY + (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY - Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY));
        final double d4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.func_181159_b(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
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
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
