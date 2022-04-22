package me.injusttice.neutron.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class WorldUtils {
    public static BlockPos getForwardBlock(double length) {
        Minecraft mc = Minecraft.getMinecraft();
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        BlockPos fPos = new BlockPos(mc.thePlayer.posX + -Math.sin(yaw) * length, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(yaw) * length);
        return fPos;
    }

    public static BlockPos getForwardBlockFromMovement(double length) {
        Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.thePlayer.movementInput.moveForward;
        float strafe = mc.thePlayer.movementInput.moveStrafe;
        double yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((forward > 0.0F) ? -45 : 45);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((forward > 0.0F) ? 45 : -45);
                strafe = 0.0F;
            }
        if (forward == 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            }
        if (forward < 0.0F)
            yaw += 180.0D;
        yaw = Math.toRadians(yaw);
        BlockPos fPos = new BlockPos(mc.thePlayer.posX + -Math.sin(yaw) * length, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(yaw) * length);
        return fPos;
    }

    public static BlockPos getForwardBlockFromMovement(double length, double sidewaysOffset) {
        Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.thePlayer.movementInput.moveForward;
        float strafe = mc.thePlayer.movementInput.moveStrafe;
        double yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((forward > 0.0F) ? -45 : 45);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((forward > 0.0F) ? 45 : -45);
                strafe = 0.0F;
            }
        if (forward == 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            }
        if (forward < 0.0F)
            yaw += 180.0D;
        yaw = Math.toRadians(yaw);
        BlockPos fPos = new BlockPos(mc.thePlayer.posX + -Math.sin(yaw) * length, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(yaw) * length);
        forward = mc.thePlayer.movementInput.moveForward;
        strafe = mc.thePlayer.movementInput.moveStrafe;
        yaw = Math.abs(mc.thePlayer.rotationYaw);
        if (forward != 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((forward > 0.0F) ? -45 : 45);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((forward > 0.0F) ? 45 : -45);
                strafe = 0.0F;
            }
        if (forward == 0.0F)
            if (strafe >= 1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            } else if (strafe <= -1.0F) {
                yaw += ((strafe > 0.0F) ? -90 : 90);
                strafe = 0.0F;
            }
        if (forward < 0.0F)
            yaw += 180.0D;
        yaw += 6.0D;
        yaw -= yaw % 45.0D;
        yaw += 90.0D;
        yaw = Math.toRadians(yaw);
        fPos = new BlockPos((Vec3i)fPos.add(-Math.sin(yaw) * sidewaysOffset, 0.0D, Math.cos(yaw) * sidewaysOffset));
        return fPos;
    }

    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        return getDistance(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x2 - x1;
        double d1 = y2 - y1;
        double d2 = z2 - z1;
        return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }
}
