package non.asset.module.impl.movement;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.BoundingBoxEvent;
import non.asset.event.impl.player.MotionEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;

import org.apache.commons.lang3.StringUtils;

public class Jesus extends Module {
    private EnumValue<modeYouMom> YouMom = new EnumValue<>("Mode", modeYouMom.VANILLA);
    private boolean wasWater;
    private TimerUtil timer = new TimerUtil();
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        setDescription("Walking on water like a jew");
    }

    public enum modeYouMom {
        VANILLA, NCP
    }
    @Handler
    public void onUpdate(UpdateEvent event) {
        if (getMc().thePlayer != null) {
            setSuffix(StringUtils.capitalize(YouMom.getValue().name().toLowerCase()));
            switch (YouMom.getValue()) {
                
                case VANILLA:
                    if (event.isPre()) {
                        if (isOnLiquid()) {
                            event.setOnGround(false);
                        }
                        if (!event.isPre() || (getMc().thePlayer.isBurning() && isOnWater())) return;
                        if (isInLiquid() && !getMc().gameSettings.keyBindSneak.isKeyDown() && !getMc().gameSettings.keyBindJump.isKeyDown() && getMc().thePlayer.fallDistance < 3) {
                            getMc().thePlayer.motionY = 0.1;
                        }
                    }
                    break;
                case NCP:
                    if (!event.isPre() || (getMc().thePlayer.isBurning() && isOnWater())) return;
                    if (isInLiquid() && !getMc().gameSettings.keyBindSneak.isKeyDown() && !getMc().gameSettings.keyBindJump.isKeyDown() && getMc().thePlayer.fallDistance < 3) {
                        getMc().thePlayer.motionY = 0.1;
                    }
                    break;
            }
        }
    }

    @Handler
    public void onBoundingBox(BoundingBoxEvent event) {
        if (getMc().thePlayer != null) {
            Block block = getMc().theWorld.getBlockState(event.getBlockPos()).getBlock();
            switch (YouMom.getValue()) {
                
                case VANILLA:
                    if (getMc().theWorld == null || getMc().thePlayer.fallDistance > 3 || (getMc().thePlayer.isBurning() && isOnWater()))
                        return;
                    if (!(block instanceof BlockLiquid) || isInLiquid() || getMc().thePlayer.isSneaking())
                        return;
                    event.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1, 1).contract(0, 0, 0).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
                    break;
                
                case NCP:
                    if (getMc().theWorld == null || getMc().thePlayer.fallDistance > 3 || (getMc().thePlayer.isBurning() && isOnWater()))
                        return;
                    if (!(block instanceof BlockLiquid) || isInLiquid() || getMc().thePlayer.isSneaking())
                        return;
                    event.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1, 1).contract(0, 0.000000000002000111, 0).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
                    break;
            }
        }
    }

    @Handler
    public void onPacketSent(PacketEvent event) {
        if (getMc().thePlayer != null) {
            switch (YouMom.getValue()) {
                case NCP:
                    if (!(event.getPacket() instanceof C03PacketPlayer) || !event.isSending() || isInLiquid() || !isOnLiquid() || getMc().thePlayer.isSneaking() || getMc().thePlayer.fallDistance > 3 || (getMc().thePlayer.isBurning() && isOnWater()))
                        return;
                    C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
                    if (event.getPacket() instanceof C03PacketPlayer && !getMc().thePlayer.isMoving()) event.setCanceled(true);
                    if (getMc().thePlayer.isSprinting() && getMc().thePlayer.isInLava() && isOnLiquid())
                        getMc().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(getMc().thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    getMc().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(getMc().thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    packet.setY(packet.getPositionY() + (getMc().thePlayer.ticksExisted % 2 == 0 ? 0.000000000002000111 : 0));
                    packet.setOnGround(getMc().thePlayer.ticksExisted % 2 != 0);
                    break;
			default:
				break;
            }
        }
    }


    private boolean isOnLiquid() {
        final double y = getMc().thePlayer.posY - 0.015625;
        for (int x = MathHelper.floor_double(getMc().thePlayer.posX); x < MathHelper.ceiling_double_int(getMc().thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(getMc().thePlayer.posZ); z < MathHelper.ceiling_double_int(getMc().thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (getMc().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnWater() {
        final double y = getMc().thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(getMc().thePlayer.posX); x < MathHelper.ceiling_double_int(getMc().thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(getMc().thePlayer.posZ); z < MathHelper.ceiling_double_int(getMc().thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (getMc().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid && getMc().theWorld.getBlockState(pos).getBlock().getMaterial() == Material.water) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInLiquid() {
        final double y = getMc().thePlayer.posY + 0.01;
        for (int x = MathHelper.floor_double(getMc().thePlayer.posX); x < MathHelper.ceiling_double_int(getMc().thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(getMc().thePlayer.posZ); z < MathHelper.ceiling_double_int(getMc().thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int) y, z);
                if (getMc().theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    private double getSpeed() {
        return Math.sqrt(getMc().thePlayer.motionX * getMc().thePlayer.motionX + getMc().thePlayer.motionZ * getMc().thePlayer.motionZ);
    }

    private void setSpeed(final double speed) {
        getMc().thePlayer.motionX = -(Math.sin(getDirection()) * speed);
        getMc().thePlayer.motionZ = Math.cos(getDirection()) * speed;
    }
    private boolean isBlockUnder() {
        for (int i = (int) (getMc().thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ);
            if (getMc().theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
    private float getDirection() {
        float direction = getMc().thePlayer.rotationYaw;
        if (getMc().thePlayer.moveForward < 0.0f) {
            direction += 180.0f;
        }
        float forward;
        if (getMc().thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (getMc().thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        } else {
            forward = 1.0f;
        }
        if (getMc().thePlayer.moveStrafing > 0.0f) {
            direction -= 90.0f * forward;
        } else if (getMc().thePlayer.moveStrafing < 0.0f) {
            direction += 90.0f * forward;
        }
        direction *= 0.017453292f;
        return direction;
    }


    public enum Mode {
        SOLID, BOUNCE
    }

    @Override
    public void onEnable() {
        timer.reset();
    }

    @Override
    public void onDisable() {
        wasWater = false;
    }
}
