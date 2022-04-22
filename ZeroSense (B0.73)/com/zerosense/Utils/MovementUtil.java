package com.zerosense.Utils;

import com.zerosense.Events.impl.EventMove;
import com.zerosense.Events.impl.EventUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

public class MovementUtil {
    private static Minecraft mc;
    boolean onground;
    public static void setOnGround(boolean onground) {
        onground = onground;
    }
    public static void setSpeed(final EventMove e, double speed) {
        final EntityPlayerSP player = mc.thePlayer;
        if (isMoving()) {
            setSpeed(e, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
        }
    }

    public static void setSpeed(final EventMove e, double speed, float forward, float strafing, float yaw) {
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = Math.cos(Math.toRadians(yaw + 90.0f));
        double z = Math.cos(Math.toRadians(yaw));

        e.setX(x * speed);
        e.setZ(z * speed);
    }
    public static double getSpeed() {
        double defaultSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            defaultSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return defaultSpeed;
    }
    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, (double)mc.thePlayer.movementInput.moveStrafe, (double)mc.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
        moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }


    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    public static void teleportUp(final float d) {
        MovementUtil.mc.thePlayer.setPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + d, MovementUtil.mc.thePlayer.posZ);
    }

    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static void teleportForward(final float d) {
        final double playerYaw = Math.toRadians(MovementUtil.mc.thePlayer.rotationYaw);
        MovementUtil.mc.thePlayer.setPosition(MovementUtil.mc.thePlayer.posX + d * -Math.sin(playerYaw), MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ + d * Math.cos(playerYaw));
    }

    public static void teleportForwardPacket(final float d) {
        final double playerYaw = Math.toRadians(MovementUtil.mc.thePlayer.rotationYaw);
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX + d * -Math.sin(playerYaw), MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ + d * Math.cos(playerYaw), true));
    }

    public static void teleportUpPacket(final float d) {
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + d, MovementUtil.mc.thePlayer.posZ, true));
    }

    public static float getSpeed(int i) {
        return (float)Math.sqrt(MovementUtil.mc.thePlayer.motionX * MovementUtil.mc.thePlayer.motionX + MovementUtil.mc.thePlayer.motionZ * MovementUtil.mc.thePlayer.motionZ);
    }

    public static boolean isMoving() {
        return MovementUtil.mc.thePlayer != null && (MovementUtil.mc.thePlayer.movementInput.moveForward != 0.0f || MovementUtil.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }

    public static void strafe(final Double speed) {
        if (!isMoving()) {
            return;
        }
        final float yaw = (float)getDirection();
        final EntityPlayerSP thePlayer = MovementUtil.mc.thePlayer;
        thePlayer.motionX = -Math.sin(yaw) * speed;
        thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static double getDirection() {
        final EntityPlayerSP thePlayer = MovementUtil.mc.thePlayer;
        Float rotationYaw = thePlayer.rotationYaw;
        if (thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        Float forward = 1.0f;
        if (thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    static {
        MovementUtil.mc = Minecraft.getMinecraft();
    }
    public static void actualSetSpeed(double moveSpeed) {
        setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {

        double fforward = forward;
        double sstrafe = strafe;
        float yyaw = yaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;


    }


}

