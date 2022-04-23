/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.movement;

import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.module.impl.movement.NoSlowdown;
import club.tifality.module.impl.movement.TargetStrafe;
import club.tifality.utils.Wrapper;
import club.tifality.utils.server.ServerUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public final class MovementUtils {
    public static final double SPRINTING_MOD = 1.3;
    public static final double SNEAK_MOD = 0.3;
    public static final double VANILLA_JUMP_HEIGHT = (double)0.42f;
    public static final double WALK_SPEED = 0.221;
    private static final List<Double> frictionValues = new ArrayList<Double>();
    private static final double MIN_DIF = 0.01;
    public static final double MAX_DIST = 2.14;
    public static final double BUNNY_DIV_FRICTION = 159.99;
    private static final double SWIM_MOD = 0.5203619909502263;
    private static final double[] DEPTH_STRIDER_VALUES = new double[]{1.0, 1.4304347826086956, 1.734782608695652, 1.9217391304347824};
    private static final double AIR_FRICTION = 0.98;
    private static final double WATER_FRICTION = 0.89;
    private static final double LAVA_FRICTION = 0.535;
    private static final Minecraft mc = Minecraft.getMinecraft();

    private MovementUtils() {
    }

    public static void forward(double length) {
        double yaw = Math.toRadians(MovementUtils.mc.thePlayer.rotationYaw);
        MovementUtils.mc.thePlayer.setPosition(MovementUtils.mc.thePlayer.posX + -Math.sin(yaw) * length, MovementUtils.mc.thePlayer.posY, MovementUtils.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }

    public static boolean isBlockUnder() {
        if (MovementUtils.mc.thePlayer.posY < 0.0) {
            return false;
        }
        for (int off = 0; off < (int)MovementUtils.mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = MovementUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -off, 0.0);
            if (MovementUtils.mc.theWorld.getCollidingBoundingBoxes(MovementUtils.mc.thePlayer, bb).isEmpty()) continue;
            return true;
        }
        return false;
    }

    public static int getJumpBoostModifier() {
        PotionEffect effect = Wrapper.getPlayer().getActivePotionEffect(Potion.jump.id);
        if (effect != null) {
            return effect.getAmplifier() + 1;
        }
        return 0;
    }

    private static boolean isMovingEnoughForSprint() {
        MovementInput movementInput = Wrapper.getPlayer().movementInput;
        return movementInput.moveForward > 0.8f || movementInput.moveForward < -0.8f || movementInput.moveStrafe > 0.8f || movementInput.moveStrafe < -0.8f;
    }

    public static float getMovementDirection() {
        float forward = Wrapper.getPlayer().movementInput.moveForward;
        float strafe = Wrapper.getPlayer().movementInput.moveStrafe;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
            } else if (strafe < 0.0f) {
                direction -= 45.0f;
            }
        } else if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
            } else if (strafe < 0.0f) {
                direction += 45.0f;
            }
        } else if (strafe > 0.0f) {
            direction -= 90.0f;
        } else if (strafe < 0.0f) {
            direction += 90.0f;
        }
        return MathHelper.wrapAngleTo180_float(direction += Wrapper.getPlayer().rotationYaw);
    }

    public static boolean isBlockAbove() {
        for (double height = 0.0; height <= 1.0; height += 0.5) {
            List<AxisAlignedBB> collidingList = Wrapper.getWorld().getCollidingBoundingBoxes(Wrapper.getPlayer(), Wrapper.getPlayer().getEntityBoundingBox().offset(0.0, height, 0.0));
            if (collidingList.isEmpty()) continue;
            return true;
        }
        return false;
    }

    public static boolean fallDistDamage() {
        if (!ServerUtils.isOnHypixel() || !MovementUtils.isOnGround() || MovementUtils.isBlockAbove()) {
            return false;
        }
        EntityPlayerSP player = Wrapper.getPlayer();
        double randomOffset = Math.random() * (double)3.0E-4f;
        double jumpHeight = 0.0525 - randomOffset;
        int packets = (int)((double)MovementUtils.getMinFallDist() / (jumpHeight - randomOffset) + 1.0);
        for (int i = 0; i < packets; ++i) {
            Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + jumpHeight, player.posZ, false));
            Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + randomOffset, player.posZ, false));
        }
        Wrapper.sendPacketDirect(new C03PacketPlayer(true));
        return true;
    }

    public static boolean isOverVoid() {
        for (double posY = Wrapper.getPlayer().posY; posY > 0.0; posY -= 1.0) {
            if (Wrapper.getWorld().getBlockState(new BlockPos(Wrapper.getPlayer().posX, posY, Wrapper.getPlayer().posZ)).getBlock() instanceof BlockAir) continue;
            return false;
        }
        return true;
    }

    public static double getJumpHeight(double baseJumpHeight) {
        if (MovementUtils.isInLiquid()) {
            return 0.13499999955296516;
        }
        if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
            return baseJumpHeight + (double)(((float)Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f);
        }
        return baseJumpHeight;
    }

    public static double getJumpHeight() {
        double baseJumpHeight = 0.42f;
        if (MovementUtils.isInLiquid()) {
            return 0.13500000163912773;
        }
        if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
            return baseJumpHeight + (double)(((float)Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f);
        }
        return baseJumpHeight;
    }

    public static float getMinFallDist() {
        float minDist = 3.0f;
        if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
            minDist += (float)Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f;
        }
        return minDist;
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeedRef) {
        frictionValues.clear();
        frictionValues.add(lastDist - lastDist / 159.99);
        frictionValues.add(lastDist - (moveSpeed - lastDist) / 33.3);
        double materialFriction = Wrapper.getPlayer().isInWater() ? 0.89 : (Wrapper.getPlayer().isInLava() ? 0.535 : 0.98);
        frictionValues.add(lastDist - baseMoveSpeedRef * (1.0 - materialFriction));
        return Collections.min(frictionValues);
    }

    public static double getBlockHeight() {
        return Wrapper.getPlayer().posY - (double)((int)Wrapper.getPlayer().posY);
    }

    public static boolean canSprint(boolean omni) {
        return (omni ? MovementUtils.isMovingEnoughForSprint() : Wrapper.getPlayer().movementInput.moveForward >= 0.8f) && !Wrapper.getPlayer().isCollidedHorizontally && Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6 && !Wrapper.getPlayer().isSneaking() && (!Wrapper.getPlayer().isUsingItem() || NoSlowdown.isNoSlowdownEnabled());
    }

    public static double getBaseMoveSpeed(double basespeed) {
        double baseSpeed = basespeed;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static double getBPS() {
        if (MovementUtils.mc.thePlayer == null || MovementUtils.mc.thePlayer.ticksExisted < 1) {
            return 0.0;
        }
        return MovementUtils.getDistance(MovementUtils.mc.thePlayer.lastTickPosX, MovementUtils.mc.thePlayer.lastTickPosZ) * (double)(20.0f * MovementUtils.mc.getTimer().timerSpeed);
    }

    public static double getDistance(double x, double z) {
        double xSpeed = MovementUtils.mc.thePlayer.posX - x;
        double zSpeed = MovementUtils.mc.thePlayer.posZ - z;
        return MathHelper.sqrt_double(xSpeed * xSpeed + zSpeed * zSpeed);
    }

    public static void setSpeed(MoveEntityEvent e, double speed) {
        EntityLivingBase target;
        EntityPlayerSP player = Wrapper.getPlayer();
        TargetStrafe targetStrafe = TargetStrafe.getInstance();
        KillAura killAura = KillAura.getInstance();
        if (targetStrafe.isEnabled() && (targetStrafe.holdSpaceProperty.getValue().booleanValue() || MovementUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f || MovementUtils.mc.thePlayer.movementInput.moveForward != 0.0f) && (!targetStrafe.holdSpaceProperty.getValue().booleanValue() || MovementUtils.mc.gameSettings.keyBindJump.isKeyDown()) && (target = killAura.getTarget()) != null && targetStrafe.shouldStrafe()) {
            if (targetStrafe.shouldAdaptSpeed()) {
                speed = Math.min(speed, targetStrafe.getAdaptedSpeed());
            }
            targetStrafe.setSpeed(e, speed);
            return;
        }
        MovementUtils.setSpeed(e, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }

    public static void setSpeed(double speed) {
        MovementUtils.mc.thePlayer.motionX = -Math.sin(MovementUtils.getDirection()) * speed;
        MovementUtils.mc.thePlayer.motionZ = Math.cos(MovementUtils.getDirection()) * speed;
    }

    public static void setSpeed(MoveEntityEvent e, double speed, float forward, float strafing, float yaw) {
        boolean reversed;
        boolean bl = reversed = forward < 0.0f;
        float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : (reversed ? -0.5f : 1.0f));
        if (reversed) {
            yaw += 180.0f;
        }
        if (strafing > 0.0f) {
            yaw -= strafingYaw;
        } else if (strafing < 0.0f) {
            yaw += strafingYaw;
        }
        double x = Math.cos(Math.toRadians(yaw + 90.0f));
        double z = Math.cos(Math.toRadians(yaw));
        e.setX(x * speed);
        e.setZ(z * speed);
    }

    public static boolean isOnGround() {
        return Wrapper.getPlayer().onGround && Wrapper.getPlayer().isCollidedVertically;
    }

    public static boolean isOnGround(double height) {
        return !MovementUtils.mc.theWorld.getCollidingBoundingBoxes(MovementUtils.mc.thePlayer, MovementUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static void strafe() {
        MovementUtils.strafe(MovementUtils.getSpeed());
    }

    public static void strafe(float speed) {
        if (!MovementUtils.isMove()) {
            return;
        }
        double yaw = MovementUtils.getDirection();
        MovementUtils.mc.thePlayer.motionX = -Math.sin(yaw) * (double)speed;
        MovementUtils.mc.thePlayer.motionZ = Math.cos(yaw) * (double)speed;
    }

    public static double getDirection() {
        float rotationYaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (MovementUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MovementUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (MovementUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MovementUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MovementUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static double getJumpBoostModifier(double baseJumpHeight, boolean potionJumpHeight) {
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump) && potionJumpHeight) {
            int amplifier = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        }
        return baseJumpHeight;
    }

    public static int getSpeedEffect() {
        return MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    public static float getSpeed() {
        return (float)Math.sqrt(MovementUtils.mc.thePlayer.motionX * MovementUtils.mc.thePlayer.motionX + MovementUtils.mc.thePlayer.motionZ * MovementUtils.mc.thePlayer.motionZ);
    }

    public static boolean isMoving() {
        return Wrapper.getPlayer().movementInput.moveForward != 0.0f || Wrapper.getPlayer().movementInput.moveStrafe != 0.0f;
    }

    public static boolean isMove() {
        return MovementUtils.mc.thePlayer != null && (MovementUtils.mc.thePlayer.movementInput.moveForward != 0.0f || MovementUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }

    public static void setMotion(double speed) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtils.mc.thePlayer.motionX = 0.0;
            MovementUtils.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            MovementUtils.mc.thePlayer.motionX = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
            MovementUtils.mc.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
        }
    }

    public static void setMotion(MoveEntityEvent e, double speed) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        double yaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            e.setX(0.0);
            e.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (double)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (double)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0));
            double sin = Math.sin(Math.toRadians(yaw + 90.0));
            e.setX(forward * speed * cos + strafe * speed * sin);
            e.setZ(forward * speed * sin - strafe * speed * cos);
        }
    }

    public static void setMotio(MoveEntityEvent e, double speed) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (MovementUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        if (MovementUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw = (float)((double)rotationYaw - 90.0 * forward);
        }
        if (MovementUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw = (float)((double)rotationYaw + 90.0 * forward);
        }
        double yaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtils.mc.thePlayer.motionX = 0.0;
            MovementUtils.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (double)(forward > 0.0 ? -44 : 44);
                } else if (strafe < 0.0) {
                    yaw += (double)(forward > 0.0 ? 44 : -44);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            e.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0)));
            e.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0)));
        }
    }

    public static boolean isInLiquid() {
        if (MovementUtils.mc.thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        int y = (int)MovementUtils.mc.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(MovementUtils.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(MovementUtils.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(MovementUtils.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(MovementUtils.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = MovementUtils.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == null || block.getMaterial() == Material.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                inLiquid = true;
            }
        }
        return inLiquid;
    }

    public static boolean isOnIce() {
        EntityPlayerSP player = MovementUtils.mc.thePlayer;
        Block blockUnder = MovementUtils.mc.theWorld.getBlockState(new BlockPos(player.posX, player.posY - 1.0, player.posZ)).getBlock();
        return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
    }

    public static void fakeJump() {
        MovementUtils.mc.thePlayer.isAirBorne = true;
        MovementUtils.mc.thePlayer.triggerAchievement(StatList.jumpStat);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static void bypassOffSet(UpdatePositionEvent event) {
        if (MovementUtils.isMoving()) {
            List<Double> BypassOffset = Arrays.asList(0.125, 0.25, 0.375, 0.625, 0.75, 0.015625, 0.5, 0.0625, 0.875, 0.1875);
            double d3 = event.getPosY() % 1.0;
            BypassOffset.sort(Comparator.comparingDouble(PreY -> Math.abs(PreY - d3)));
            double acc = event.getPosY() - d3 + BypassOffset.get(0);
            if (Math.abs(BypassOffset.get(0) - d3) < 0.005) {
                event.setPosY(acc);
                event.setOnGround(true);
            } else {
                List<Double> BypassOffset2 = Arrays.asList(0.715, 0.945, 0.09, 0.155, 0.14, 0.045, 0.63, 0.31);
                double d3_ = event.getPosY() % 1.0;
                BypassOffset2.sort(Comparator.comparingDouble(PreY -> Math.abs(PreY - d3_)));
                acc = event.getPosY() - d3_ + BypassOffset2.get(0);
                if (Math.abs(BypassOffset2.get(0) - d3_) < 0.005) {
                    event.setPosY(acc);
                }
            }
        }
    }

    public static void setMotion(MoveEntityEvent event, double speed, double motion) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        double yaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (double)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (double)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0));
            double sin = Math.sin(Math.toRadians(yaw + 90.0));
            event.setX((forward * speed * cos + strafe * speed * sin) * motion);
            event.setZ((forward * speed * sin - strafe * speed * cos) * motion);
        }
    }
}

