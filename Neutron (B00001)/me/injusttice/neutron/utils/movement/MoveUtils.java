package me.injusttice.neutron.utils.movement;

import me.injusttice.neutron.api.events.impl.EventMove;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MoveUtils {
    static final Minecraft mc = Minecraft.getMinecraft();

    public static double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F)
            rotationYaw += 180.0F;
        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (mc.thePlayer.moveStrafing > 0.0F)
            rotationYaw -= 90.0F * forward;
        if (mc.thePlayer.moveStrafing < 0.0F)
            rotationYaw += 90.0F * forward;
        return Math.toRadians(rotationYaw);
    }

    public static float getSpeed() {
        return (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static void setSpeed(float speed) {
        if (!isMoving())
            return;
        mc.thePlayer.motionX = -Math.sin(getDirection()) * speed;
        mc.thePlayer.motionZ = Math.cos(getDirection()) * speed;
    }

    public static void setSpeed(EventMove e, float speed) {
        if (!isMoving())
            return;
        e.setX(-Math.sin(getDirection()) * speed);
        e.setZ(Math.cos(getDirection()) * speed);
    }

    public static void multMotionBy(double motion) {
        mc.thePlayer.motionZ *= motion;
        mc.thePlayer.motionX *= motion;
    }

    public static void jumpAndMotion(EventMove e, double y) {
        e.setY(mc.thePlayer.motionY = y);
        mc.thePlayer.triggerAchievement(StatList.jumpStat);
        if (mc.thePlayer.isSprinting()) {
            float f = mc.thePlayer.rotationYaw * 0.017453292F;
            e.setX(e.getX() - (MathHelper.sin(f) * 0.2F));
            e.setZ(e.getZ() + (MathHelper.cos(f) * 0.2F));
        }
        mc.thePlayer.isAirBorne = true;
    }

    public static boolean isMoving() {
        return (mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
    }

    public static boolean isOverVoid() {
        for (int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; ) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir) {
                i--;
                continue;
            }
            return false;
        }
        return true;
    }

    public static boolean getOnRealGround(EntityLivingBase entity, double y) {
        return !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, entity.getEntityBoundingBox().offset(0.0D, -y, 0.0D)).isEmpty();
    }
}
