package me.injusttice.neutron.utils.movement;

import me.injusttice.neutron.api.events.impl.EventMotionUpdate;
import me.injusttice.neutron.api.events.impl.EventMove;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.MoveEvent;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.combat.KillAura;
import me.injusttice.neutron.impl.modules.impl.combat.TargetStrafe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class MovementUtils {

    private static List<Double> frictionValues;

    public static void multMotionBy(double motion) {
        mc.thePlayer.motionZ *= motion;
        mc.thePlayer.motionX *= motion;
    }

    public static void setMoveSpeed(double moveSpeed) {
        Minecraft mc = Minecraft.getMinecraft();
        MovementInput movementInput = mc.thePlayer.movementInput();
        double moveForward = movementInput.getForward();
        double moveStrafe = movementInput.getStrafe();
        double yaw = mc.thePlayer.rotationYaw;
        double modifier = moveForward == 0.0F ? 90.0F : moveForward < 0.0F ? -45.0F : 45.0F;
        boolean moving = moveForward != 0 || moveStrafe != 0;

        yaw += moveForward < 0.0F ? 180.0F : 0.0F;

        if (moveStrafe < 0.0F) {
            yaw += modifier;
        } else if (moveStrafe > 0.0F) {
            yaw -= modifier;
        }

        if (moving) {
                setX(-(MathHelper.sin((float) Math.toRadians(yaw)) * moveSpeed));
                setZ(MathHelper.cos((float) Math.toRadians(yaw)) * moveSpeed);
        } else {
            setX(0);
            setZ(0);
        }
    }

    public static boolean isOnGround() {
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

    public static double getJumpHeight() {
        double baseJumpHeight = 0.41999998688697815;
        if (isInLiquid()) {
            return 0.13500000163912773;
        }
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return 0.41999998688697815 + (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f;
        }
        return 0.41999998688697815;
    }

    public static double getJumpHeight(double baseJumpHeight) {
        if (isInLiquid()) {
            return 0.13499999955296516;
        }
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            return baseJumpHeight + (MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f;
        }
        return baseJumpHeight;
    }

    public static double getFriction(double moveSpeed) {
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        double lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        return calculateFriction(moveSpeed, lastDist, getBaseMoveSpeed());
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeedRef) {
        frictionValues.clear();
        frictionValues.add(lastDist - lastDist / 159.99);
        frictionValues.add(lastDist - (moveSpeed - lastDist) / 33.3);
        double materialFriction = mc.thePlayer.isInWater() ? 0.89 : (mc.thePlayer.isInLava() ? 0.535 : 0.98);
        frictionValues.add(lastDist - baseMoveSpeedRef * (1.0 - materialFriction));
        return Collections.min((Collection<? extends Double>)frictionValues);
    }

    public static double getBaseSpeedHypixelAppliedLow() {
        double baseSpeed = 0.2873;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 0.3);
        }
        return baseSpeed;
    }

    public static int getJumpBoostModifier() {
        PotionEffect effect = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump.id);
        if (effect != null) {
            return effect.getAmplifier() + 1;
        }
        return 0;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (amplifier + 1) * 0.1f;
        }
        return baseJumpHeight;
    }

    public  static Minecraft mc = Minecraft.getMinecraft();

    public static void setX(double x){
        setPos(x, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    public static void setZ(double z){
        setPos(mc.thePlayer.posX, 0, mc.thePlayer.posZ);
    }

    public static void setY(double y) {
        mc.thePlayer.setPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ);
    }

    public static double getBlocksPerSecond() {
        if (mc.thePlayer == null || mc.thePlayer.ticksExisted < 1) {
            return 0;
        }

        return mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ) * (mc.timer.ticksPerSecond * mc.timer.timerSpeed);
    }

    public static void actualSetSpeed(double moveSpeed) {
        setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public static boolean isInLiquid(Entity e) {
        for (int x = MathHelper.floor_double(e.boundingBox.minY); x < MathHelper.floor_double(e.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(e.boundingBox.minZ); z < MathHelper.floor_double(e.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)e.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    public static boolean isInLiquid() {
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }

    public static double getYaw(boolean strafing) {
        float rotationYaw = strafing ? mc.thePlayer.rotationYawHead : mc.thePlayer.rotationYaw;
        float forward = 1F;

        double moveForward = mc.thePlayer.movementInput.moveForward;
        double moveStrafing = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;

        if (moveForward < 0) {
            rotationYaw += 180F;
        }

        if (moveForward < 0) {
            forward = -0.5F;
        } else if(moveForward > 0) {
            forward = 0.5F;
        }

        if (moveStrafing > 0) {
            rotationYaw -= 90F * forward;
        } else if(moveStrafing < 0) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static void forward(double length) {
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        mc.thePlayer.setPosition(mc.thePlayer.posX + (-Math.sin(yaw) * length), mc.thePlayer.posY, mc.thePlayer.posZ + (Math.cos(yaw) * length));
    }

    public static boolean isOnGround(double height, EntityPlayer player) {
        if (!mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void setNigga(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double offsetX = Math.cos(Math.toRadians(yaw + 90.0f));
        double offsetZ = Math.sin(Math.toRadians(yaw + 90.0f));
        mc.thePlayer.motionX = forward * moveSpeed * offsetX + strafe * moveSpeed * offsetZ;
        mc.thePlayer.motionZ = forward * moveSpeed * offsetZ - strafe * moveSpeed * offsetX;
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.actualSetSpeedX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.actualSetSpeedZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static float getMovementDirection() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final float forward = player.moveForward;
        final float strafe = player.moveStrafing;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
            }
            else if (strafe < 0.0f) {
                direction -= 45.0f;
            }
        }
        else if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
            }
            else if (strafe < 0.0f) {
                direction += 45.0f;
            }
        }
        else if (strafe > 0.0f) {
            direction -= 90.0f;
        }
        else if (strafe < 0.0f) {
            direction += 90.0f;
        }
        direction += player.rotationYaw;
        return MathHelper.wrapAngleTo180_float(direction);
    }

    public static void bypassOffSet(EventMotion event) {
        if (isMoving()) {
            List<Double> BypassOffset = Arrays.asList(0.125, 0.25, 0.375, 0.625, 0.75, 0.015625, 0.5, 0.0625, 0.875, 0.1875);
            double d3 = event.getY() % 1.0;
            BypassOffset.sort(Comparator.comparingDouble(PreY -> Math.abs(PreY - d3)));
            double acc = event.getY() - d3 + BypassOffset.get(0);
            if (Math.abs(BypassOffset.get(0) - d3) < 0.005) {
                event.setY(acc);
                event.setOnGround(true);
            }
            else {
                List<Double> BypassOffset2 = Arrays.asList(0.715, 0.945, 0.09, 0.155, 0.14, 0.045, 0.63, 0.31);
                double d3_ = event.getY() % 1.0;
                BypassOffset2.sort(Comparator.comparingDouble(PreY -> Math.abs(PreY - d3_)));
                acc = event.getY() - d3_ + BypassOffset2.get(0);
                if (Math.abs(BypassOffset2.get(0) - d3_) < 0.005) {
                    event.setY(acc);
                }
            }
        }
    }

    public static void setSpeed(EventMotion moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
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

        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.setX(mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setSpeed(EventMotionUpdate eventMotionUpdate, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
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

        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed(EventMotion moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public static double getBaseSpeedHypixelApplied() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1.0);
        }
        return baseSpeed;
    }


    public static void strafe(double speed) {
        float a = mc.thePlayer.rotationYaw * 0.017453292F;
        float l = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * 1.5f;
        float r = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * 1.5f;
        float rf = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * 0.19f;
        float lf = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * -0.19f;
        float lb = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * 0.76f;
        float rb = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * -0.76f;
        if (mc.gameSettings.keyBindForward.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (MathHelper.sin(lf) * speed);
                mc.thePlayer.motionZ += (MathHelper.cos(lf) * speed);
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (MathHelper.sin(rf) * speed);
                mc.thePlayer.motionZ += (MathHelper.cos(rf) * speed);
            } else {
                mc.thePlayer.motionX -= (MathHelper.sin(a) * speed);
                mc.thePlayer.motionZ += (MathHelper.cos(a) * speed);
            }
        } else if (mc.gameSettings.keyBindBack.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (MathHelper.sin(lb) * speed);
                mc.thePlayer.motionZ += (MathHelper.cos(lb) * speed);
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (MathHelper.sin(rb) * speed);
                mc.thePlayer.motionZ += (MathHelper.cos(rb) * speed);
            } else {
                mc.thePlayer.motionX += (MathHelper.sin(a) * speed);
                mc.thePlayer.motionZ -= (MathHelper.cos(a) * speed);
            }
        } else if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (MathHelper.sin(l) * speed);
            mc.thePlayer.motionZ -= (MathHelper.cos(l) * speed);
        } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (MathHelper.sin(r) * speed);
            mc.thePlayer.motionZ -= (MathHelper.cos(r) * speed);
        }

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

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty())
            return true;
        return false;
    }

    public static double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if (mc.thePlayer.moveForward < 0F)
            forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F)
            forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if (mc.thePlayer.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static void strafe(float speed) {
        if(!isMoving())
            return;

        double yaw = getDirection();
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static double getSpeed() {
        return (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static float getSpeed(EntityLivingBase e) {
        return (float) Math.sqrt((e.posX - e.prevPosX) * (e.posX - e.prevPosX) + (e.posZ - e.prevPosZ) * (e.posZ - e.prevPosZ));
    }

    public static void setPos(double x, double y, double z) {
        mc.thePlayer.setPosition(x, y,  z);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1 + .2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        return baseSpeed;
    }

    public static double getBaseSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static void setSpeed(EventMove e, float speed) {
        if (!isMoving())
            return;
        e.setX(-Math.sin(getDirection()) * speed);
        e.setZ(Math.cos(getDirection()) * speed);
    }

    public static void setSpeed(double speed, float forward, float strafing, float yaw) {
        boolean reversed = forward < 0.0F;
        float strafingYaw = 90.0F * (forward > 0.0F ? 0.5F : (reversed ? -0.5F : 1.0F));
        if (reversed) {
            yaw += 180.0F;
        }

        if (strafing > 0.0F) {
            yaw -= strafingYaw;
        } else if (strafing < 0.0F) {
            yaw += strafingYaw;
        }

        double x = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        double z = Math.cos(Math.toRadians((double)yaw));
        mc.thePlayer.motionX = x * speed;
        mc.thePlayer.motionZ = z * speed;
    }

    public static void setSpeed1(double speed) {
        EntityPlayerSP player = mc.thePlayer;
        if (isMoving()){
            TargetStrafe targetStrafe = ModuleManager.getModule(TargetStrafe.class);
            KillAura killAura = ModuleManager.getModule(KillAura.class);
            if (targetStrafe.isEnabled() && (!targetStrafe.holdspace.isEnabled() || Keyboard.isKeyDown(57))) {
                Entity target = KillAura.target;
                if (killAura.isEnabled() && target != null) {
                    float dist = mc.thePlayer.getDistanceToEntity(target);
                    double radius = (Double)targetStrafe.radius.getValue();
                    setSpeed(speed, (double)dist <= radius + 1.0E-4D ? 0.0F : 1.0F, (double)dist <= radius + 1.0D ? (float)targetStrafe.direction : 0.0F, RotationUtils.getYawToEntity(target));
                    return;
                }
            }
            setSpeed(speed, player.moveForward, player.moveStrafing, player.rotationYaw);
        }
    }

    public static boolean isDistFromGround(double dist) {
        return Minecraft.getMinecraft().theWorld.checkBlockCollision(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().addCoord(0.0, -dist, 0.0));
    }

    public static boolean getOnRealGround(EntityLivingBase entity, double y) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, entity.getEntityBoundingBox().offset(0.0D, -y, 0.0D)).isEmpty();
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

    public static boolean isBlockUnder(final Minecraft mc) {
        return mc.theWorld.checkBlockCollision(mc.thePlayer.getEntityBoundingBox().addCoord(0.0, -1.0, 0.0));
    }

    public static boolean isOverVoid(final Minecraft mc) {
        final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox();
        final double height = bb.maxY - bb.minY;

        double offset = height;

        AxisAlignedBB bbPos;

        while (!mc.theWorld.checkBlockCollision((bbPos = bb.addCoord(0, -offset, 0)))) {
            if (bbPos.minY <= 0.0) return true;

            offset += height;
        }

        return false;
    }

    public static float getMovementDirection(final float forward,
                                             final float strafing,
                                             float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return yaw;

        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        return yaw;
    }

    public static boolean isColliding(AxisAlignedBB box) {
        return mc.theWorld.checkBlockCollision(box);
    }


    public static double getGroundLevel() {
        for (int i = (int) Math.round(mc.thePlayer.posY); i > 0; --i) {
            AxisAlignedBB box = mc.thePlayer.boundingBox.addCoord(0.0, 0.0, 0.0);
            box.minY = i - 1;
            box.maxY = i;
            if (!isColliding(box) || !(box.minY <= mc.thePlayer.posY)) {
                continue;
            }
            return i;
        }
        return 0.0;
    }

    static {
        frictionValues = new ArrayList<Double>();
    }
}