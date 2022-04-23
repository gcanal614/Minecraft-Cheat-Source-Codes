// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.player;

import java.text.DecimalFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.Entity;
import bozoware.impl.event.player.PlayerMoveEvent;
import net.minecraft.util.MathHelper;
import net.minecraft.potion.Potion;
import net.minecraft.client.Minecraft;

public class MovementUtil
{
    private static final Minecraft mc;
    private static double speed;
    private static double currentDistance;
    private static double lastDistance;
    public static boolean prevOnGround;
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (MovementUtil.mc.thePlayer != null && Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static double getBaseJumpMotion() {
        double baseJumpMotion = 0.41999998688697815;
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            baseJumpMotion += (MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        return baseJumpMotion;
    }
    
    public static double getBaseMoveSpeed(final double d) {
        double baseSpeed = d;
        if (MovementUtil.mc.thePlayer != null && Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static void hClip(final double offset) {
        MovementUtil.mc.thePlayer.setPosition(MovementUtil.mc.thePlayer.posX + -MathHelper.sin(getDirection()) * offset, MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ + MathHelper.cos(getDirection()) * offset);
    }
    
    public static void setSpeed(final PlayerMoveEvent e, final double moveSpeed) {
        setSpeed(e, moveSpeed, MovementUtil.mc.thePlayer.rotationYaw, MovementUtil.mc.thePlayer.moveStrafing, MovementUtil.mc.thePlayer.movementInput.moveForward);
    }
    
    public static void setSpeed(final PlayerMoveEvent moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? -45 : 45);
            }
            else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            }
            else if (pseudoForward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setMotionX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setMotionZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    public static boolean isOnGround(final double height) {
        return !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, MovementUtil.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static void TPSpeed(final double moveSpeed) {
        final double x = MovementUtil.mc.thePlayer.posX;
        final double y = MovementUtil.mc.thePlayer.posY;
        final double z = MovementUtil.mc.thePlayer.posZ;
        if (MovementUtil.mc.thePlayer.isMoving()) {
            if (MovementUtil.mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) {
                MovementUtil.mc.thePlayer.setPositionAndUpdate(x + moveSpeed, y, z);
            }
            if (MovementUtil.mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                MovementUtil.mc.thePlayer.setPositionAndUpdate(x - moveSpeed, y, z);
            }
            if (MovementUtil.mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) {
                MovementUtil.mc.thePlayer.setPositionAndUpdate(x, y, z - moveSpeed);
            }
            if (MovementUtil.mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) {
                MovementUtil.mc.thePlayer.setPositionAndUpdate(x, y, z + moveSpeed);
            }
        }
    }
    
    public static boolean isMoving() {
        return MovementUtil.mc.thePlayer.moveForward != 0.0f || MovementUtil.mc.thePlayer.moveStrafing != 0.0f;
    }
    
    public static double getMoveSpeed() {
        return Math.sqrt(MovementUtil.mc.thePlayer.motionX * MovementUtil.mc.thePlayer.motionX + MovementUtil.mc.thePlayer.motionZ * MovementUtil.mc.thePlayer.motionZ);
    }
    
    public static double lastDist() {
        if (MovementUtil.mc.thePlayer != null) {
            final double xDist = MovementUtil.mc.thePlayer.posX - MovementUtil.mc.thePlayer.prevPosX;
            final double zDist = MovementUtil.mc.thePlayer.posZ - MovementUtil.mc.thePlayer.prevPosZ;
            return Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return 0.0;
    }
    
    public static void Dynamic(final double Speed, final float jumpHeight, final double airFriction, final double distanceDiff, final boolean depresscito, final boolean timerBoost, final double timerSpeedGround, final double timerSpeedAir) {
        if (MovementUtil.mc.thePlayer.onGround && MovementUtil.mc.thePlayer.isMoving()) {
            if (timerBoost) {
                MovementUtil.mc.timer.timerSpeed = (float)timerSpeedGround;
            }
            MovementUtil.mc.thePlayer.motionY = jumpHeight;
            MovementUtil.speed = Math.max(getBaseMoveSpeed() * Speed, MovementUtil.speed * Speed);
            MovementUtil.prevOnGround = true;
        }
        if (MovementUtil.mc.thePlayer.isAirBorne) {
            if (timerBoost) {
                MovementUtil.mc.timer.timerSpeed = (float)timerSpeedAir;
            }
            if (MovementUtil.prevOnGround) {
                MovementUtil.speed -= distanceDiff * (MovementUtil.speed - getBaseMoveSpeed());
                MovementUtil.prevOnGround = false;
            }
            else if (!depresscito) {
                MovementUtil.speed -= MovementUtil.speed / airFriction;
            }
            else {
                MovementUtil.speed -= MovementUtil.speed / airFriction - 1.0E-9;
            }
            setMoveSpeed(MovementUtil.speed);
        }
    }
    
    public static void setMoveSpeed(final double moveSpeed) {
        float forward = MovementUtil.mc.thePlayer.movementInput.moveForward;
        float strafe = MovementUtil.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MovementUtil.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtil.mc.thePlayer.motionX = 0.0;
            MovementUtil.mc.thePlayer.motionZ = 0.0;
        }
        final int d = 45;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? (-d) : d);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? d : (-d));
            }
            strafe = 0.0f;
            if (forward > 0.0) {
                forward = 1.0f;
            }
            else if (forward < 0.0) {
                forward = -1.0f;
            }
        }
        final double xDist = forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
        final double zDist = forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
        MovementUtil.mc.thePlayer.motionX = xDist;
        MovementUtil.mc.thePlayer.motionZ = zDist;
    }
    
    public static void setSpeed(final double speed) {
        if (MovementUtil.mc.thePlayer.isMoving()) {
            Minecraft.getMinecraft().thePlayer.motionX = -MathHelper.sin(getDirection()) * speed;
            Minecraft.getMinecraft().thePlayer.motionZ = MathHelper.cos(getDirection()) * speed;
        }
        else {
            Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
        }
    }
    
    public static String getBPS() {
        final double xDist = MovementUtil.mc.thePlayer.posX - MovementUtil.mc.thePlayer.lastTickPosX;
        final double zDist = MovementUtil.mc.thePlayer.posZ - MovementUtil.mc.thePlayer.lastTickPosZ;
        final double lastDist = StrictMath.sqrt(xDist * xDist + zDist * zDist);
        final double speed1 = lastDist * 20.0 * MovementUtil.mc.timer.timerSpeed;
        final DecimalFormat df = new DecimalFormat("0.00");
        return df.format(speed1);
    }
    
    public static float getDirection() {
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        final float forward = Minecraft.getMinecraft().thePlayer.moveForward;
        final float strafe = Minecraft.getMinecraft().thePlayer.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += ((forward == 0.0f) ? 90 : ((forward < 0.0f) ? -45 : 45));
        }
        if (strafe > 0.0f) {
            yaw -= ((forward == 0.0f) ? 90 : ((forward < 0.0f) ? -45 : 45));
        }
        return yaw * 0.017453292f;
    }
    
    public static float getDirectionStrafeFix(final float forward, final float strafing, float yaw) {
        if (forward == 0.0 && strafing == 0.0) {
            return yaw;
        }
        final boolean reversed = forward < 0.0;
        final double strafingYaw = 90.0 * ((forward > 0.0) ? 0.5 : (reversed ? -0.5 : 1.0));
        if (reversed) {
            yaw += 180.0;
        }
        if (strafing > 0.0f) {
            yaw -= (float)strafingYaw;
        }
        else if (strafing < 0.0) {
            yaw += (float)strafingYaw;
        }
        return yaw;
    }
    
    public static long getBaseMoveSpeedLong() {
        double baseSpeed = 32.0;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return (long)(baseSpeed / 32.0);
    }
    
    public static float getBaseMoveSpeedFloat() {
        double baseSpeed = 0.6;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return (float)baseSpeed;
    }
    
    public static double getBaseSpeed() {
        return Math.sqrt(MovementUtil.mc.thePlayer.motionX * MovementUtil.mc.thePlayer.motionX + MovementUtil.mc.thePlayer.motionZ * MovementUtil.mc.thePlayer.motionZ);
    }
    
    public static void setMoveSpeed(final long val) {
        final float f = val * 0.1f;
        setSpeed(f);
    }
    
    public static boolean setBaseMoveSpeed() {
        final float f = getBaseMoveSpeedFloat();
        setMoveSpeed(f / 2.45);
        return true;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
