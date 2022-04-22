package me.injusttice.neutron.utils.world;

import me.injusttice.neutron.utils.player.Rotation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

public class SetBlockAndFacingUtils {
    
    static Minecraft mc;
    
    public static float getYaw(Entity var0) {
        double var = var0.posX - SetBlockAndFacingUtils.mc.thePlayer.posX;
        double var2 = var0.posZ - SetBlockAndFacingUtils.mc.thePlayer.posZ;
        double var3;
        if (var2 < 0.0 && var < 0.0) {
            var3 = 90.0 + Math.toDegrees(Math.atan(var2 / var));
        }
        else if (var2 < 0.0 && var > 0.0) {
            var3 = -90.0 + Math.toDegrees(Math.atan(var2 / var));
        }
        else {
            var3 = Math.toDegrees(-Math.atan(var / var2));
        }
        return MathHelper.wrapAngleTo180_float(-(SetBlockAndFacingUtils.mc.thePlayer.rotationYaw - (float)var3));
    }
    
    public static float getPitch(Entity var0) {
        double var = var0.posX - SetBlockAndFacingUtils.mc.thePlayer.posX;
        double var2 = var0.posZ - SetBlockAndFacingUtils.mc.thePlayer.posZ;
        double var3 = var0.posY - 1.6 + var0.getEyeHeight() - SetBlockAndFacingUtils.mc.thePlayer.posY;
        double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
        double var5 = -Math.toDegrees(Math.atan(var3 / var4));
        return -MathHelper.wrapAngleTo180_float(SetBlockAndFacingUtils.mc.thePlayer.rotationPitch - (float)var5);
    }
    
    static {
        SetBlockAndFacingUtils.mc = Minecraft.getMinecraft();
    }
    
    public static class BlockUtil
    {
        private static Minecraft mc;
        static float last;
        
        public static Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation, float turnSpeed) {
            float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
            float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
            return new Rotation(currentRotation.getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
        }
        
        private static float getAngleDifference(float a, float b) {
            return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
        }
        
        public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
            EntityEgg var4 = new EntityEgg(BlockUtil.mc.theWorld);
            var4.posX = var0 + 0.5;
            var4.posY = var1 + 0.5;
            var4.posZ = var2 + 0.5;
            EntityEgg entityEgg = var4;
            entityEgg.posX += var3.getDirectionVec().getX() * 0.25;
            EntityEgg entityEgg2 = var4;
            entityEgg2.posY += var3.getDirectionVec().getY() * 0.25;
            EntityEgg entityEgg3 = var4;
            entityEgg3.posZ += var3.getDirectionVec().getZ() * 0.25;
            return getDirectionToEntity(var4);
        }
        
        private static float[] getDirectionToEntity(Entity var0) {
            return new float[] { getYaw(var0) + BlockUtil.mc.thePlayer.rotationYaw, getPitch(var0) + BlockUtil.mc.thePlayer.rotationPitch };
        }
        
        public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
            double d1 = pos.getX() - paramEntityPlayer.posX;
            double d2 = pos.getY() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
            double d3 = pos.getZ() - paramEntityPlayer.posZ;
            double d4 = Math.sqrt(d1 * d1 + d3 * d3);
            float f1 = (float)(Math.atan2(d3, d1) * 180.0 / 3.141592653589793) - 90.0f;
            float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            return new float[] { f1, f2 };
        }
        
        public static float getYaw(Entity var0) {
            double var = var0.posX - BlockUtil.mc.thePlayer.posX;
            double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
            double var3;
            if (var2 < 0.0 && var < 0.0) {
                var3 = 90.0 + Math.toDegrees(Math.atan(var2 / var));
            }
            else if (var2 < 0.0 && var > 0.0) {
                var3 = -90.0 + Math.toDegrees(Math.atan(var2 / var));
            }
            else {
                var3 = Math.toDegrees(-Math.atan(var / var2));
            }
            return MathHelper.wrapAngleTo180_float(-(BlockUtil.mc.thePlayer.rotationYaw - (float)var3));
        }
        
        public static float getPitch(Entity var0) {
            double var = var0.posX - BlockUtil.mc.thePlayer.posX;
            double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
            double var3 = var0.posY - 1.6 + var0.getEyeHeight() - BlockUtil.mc.thePlayer.posY;
            double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
            double var5 = -Math.toDegrees(Math.atan(var3 / var4));
            return -MathHelper.wrapAngleTo180_float(BlockUtil.mc.thePlayer.rotationPitch - (float)var5);
        }
        
        static {
            BlockUtil.mc = Minecraft.getMinecraft();
            BlockUtil.last = 0.0f;
        }
    }
}
