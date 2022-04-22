package non.asset.module.impl.player;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.game.TickEvent;
import non.asset.event.impl.player.BoundingBoxEvent;
import non.asset.event.impl.player.MotionEvent;
import non.asset.event.impl.player.PushEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.event.impl.render.InsideBlockRenderEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

public class Phase extends Module {
    private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.VANILLA);
    private int moveUnder;
    private NumberValue<Double> distance = new NumberValue<>("Distance", 2.0, 0.0, 10.0, 0.1);
    private NumberValue<Double> vanillaspeed = new NumberValue<>("Vanilla Speed", 0.5, 0.0, 3.0, 0.1);
    private NumberValue<Double> verticalspeed = new NumberValue<>("Vertical Speed", 0.5, 0.0, 3.0, 0.1);
    public static boolean phasing;
    private int delay;
    private int state;
    private boolean ncpSetup;
    private TimerUtil timer = new TimerUtil();

    public Phase() {
        super("Phase", Category.PLAYER);
        setDescription("Phase blocks");
    }

    public enum Mode {
    	VANILLA, HYPIXEL, NCP, AAC, MUSHMC
    }
    
    @Handler
    public void onTick(TickEvent event) {
        if (getMc().thePlayer == null) return;
        
        if (mode.getValue() == Mode.MUSHMC) {
            getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY + 5, getMc().thePlayer.posZ, true));
            getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY + 0.004, getMc().thePlayer.posZ, false));
        	toggle();
        }
        
        
        if (mode.getValue() == Mode.VANILLA) {
            if (getMc().thePlayer != null && moveUnder == 1) {
                getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY - 2.0, getMc().thePlayer.posZ, false));
                getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, true));
                moveUnder = 0;
            }
            if (getMc().thePlayer != null && moveUnder == 1488) {
                double mx = -Math.sin(Math.toRadians(getMc().thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(getMc().thePlayer.rotationYaw));
                double x = getMc().thePlayer.movementInput.moveForward * mx + getMc().thePlayer.movementInput.moveStrafe * mz;
                double z = getMc().thePlayer.movementInput.moveForward * mz - getMc().thePlayer.movementInput.moveStrafe * mx;
                getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX + x, getMc().thePlayer.posY, getMc().thePlayer.posZ + z, false));
                getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Float.NEGATIVE_INFINITY, getMc().thePlayer.posY, Float.NEGATIVE_INFINITY, true));
                moveUnder = 0;
            }
        }
        if (mode.getValue() == Mode.NCP) {
            if (getMc().thePlayer.isCollidedHorizontally && getMc().gameSettings.keyBindSprint.isKeyDown()) {
                getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY - 0.05, getMc().thePlayer.posZ, true));
                getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY, getMc().thePlayer.posZ, true));
                getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY - 0.05, getMc().thePlayer.posZ, true));
            }
        }
    }

    @Handler
    public void onBB(BoundingBoxEvent event) {
        setSuffix(StringUtils.capitalize(mode.getValue().name().toLowerCase()));
        
        if (mode.getValue() == Mode.HYPIXEL && isInsideBlock()) {
            event.setBoundingBox(null);
        }
        
        if (mode.getValue() == Mode.VANILLA) {
            if (getMc().thePlayer.isCollidedHorizontally && !isInsideBlock()) {
                double mx = -Math.sin(Math.toRadians(getMc().thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(getMc().thePlayer.rotationYaw));
                double x = getMc().thePlayer.movementInput.moveForward * mx + getMc().thePlayer.movementInput.moveStrafe * mz;
                double z = getMc().thePlayer.movementInput.moveForward * mz - getMc().thePlayer.movementInput.moveStrafe * mx;
                event.setBoundingBox(null);
                getMc().thePlayer.setPosition(getMc().thePlayer.posX + x,getMc().thePlayer.posY,getMc().thePlayer.posZ + z);
                moveUnder = 69;
            }
            if (isInsideBlock()) event.setBoundingBox(null);
        }
        if (mode.getValue() == Mode.NCP) {
            if (isInBlock(getMc().thePlayer, 0.0f) && !getMc().gameSettings.keyBindSprint.isKeyDown() && event.getBlockPos().getY() > getMc().thePlayer.getEntityBoundingBox().minY - 0.4 && event.getBlockPos().getY() < getMc().thePlayer.getEntityBoundingBox().maxY + 1.0) {
                getMc().thePlayer.jumpMovementFactor = 0;
                event.setBoundingBox(null);
            }
            if (isInBlock(getMc().thePlayer, 0.0f) && getMc().gameSettings.keyBindSprint.isKeyDown()) {
                event.setBoundingBox(null);
            }
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (!event.isSending() && event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            if (packet.getChatComponent().getUnformattedText().contains("You cannot go past the border.")) {
                event.setCanceled(true);
            }
        }

    	if(mode.getValue() == Mode.AAC) {
    		if (!event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
                if (getMc().thePlayer != null && getMc().theWorld != null && getMc().thePlayer.rotationYaw != -180 && getMc().thePlayer.rotationPitch != 0) {
                    packet.yaw = getMc().thePlayer.rotationYaw;
                    packet.pitch = getMc().thePlayer.rotationPitch;
                }
            }
    	}
        
        
        if (mode.getValue() == Mode.VANILLA && !event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 2) {
            moveUnder = 1;
        }
        if (mode.getValue() == Mode.VANILLA && !event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 69) {
            moveUnder = 1488;
        }
        if (mode.getValue() == Mode.NCP && event.isSending() && event.getPacket() instanceof C03PacketPlayer && !getMc().thePlayer.isMoving() && getMc().thePlayer.posY == getMc().thePlayer.lastTickPosY) {
            event.setCanceled(true);
        }
    }

    @Handler
    public void onMove(MotionEvent event) {
        if (mode.getValue() == Mode.HYPIXEL) {
            if (isInsideBlock()) {
                if (getMc().gameSettings.keyBindJump.isKeyDown()) event.setY(getMc().thePlayer.motionY += 0.09f);
                else if (getMc().gameSettings.keyBindSneak.isKeyDown()) event.setY(getMc().thePlayer.motionY -= 0.00);
                else event.setY(getMc().thePlayer.motionY = 0.0f);
                setMoveSpeed(event, 0.3);
            }
        }
        if (mode.getValue() == Mode.VANILLA) {
            if (isInsideBlock()) {
                if (getMc().gameSettings.keyBindJump.isKeyDown()) {
                    event.setY(getMc().thePlayer.motionY = verticalspeed.getValue());
                } else if (getMc().gameSettings.keyBindSneak.isKeyDown()) {
                    event.setY(getMc().thePlayer.motionY = -verticalspeed.getValue());
                } else {
                    event.setY(getMc().thePlayer.motionY = 0.0);
                }
                setMoveSpeed(event, vanillaspeed.getValue());
            }
        }
    }

    @Handler
    public void onPush(PushEvent event) {
        event.setCanceled(true);
    }

    @Handler
    public void onInside(InsideBlockRenderEvent event) {
        event.setCanceled(true);
    }

    private void setMoveSpeed(MotionEvent event, double speed) {
        double forward = getMc().thePlayer.movementInput.moveForward;
        double strafe = getMc().thePlayer.movementInput.moveStrafe;
        float yaw = getMc().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
            event.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	if(mode.getValue() == Mode.AAC) {
        	if(mc.thePlayer.moveForward > 0) {
				mc.thePlayer.setSprinting(true);
				mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive((int) 10220 * 100));
				}
        	
				event.setPitch(-90);
				event.setYaw(mc.getSystemTime() % 1000);
				mc.thePlayer.onGround =false;
				mc.thePlayer.sendQueue.addToSendQueue
				(new C06PacketPlayerPosLook(
						mc.thePlayer.posX*= mc.thePlayer.cameraPitch = 1,
						mc.thePlayer.posY -0.1F,
						mc.thePlayer.posZ*= mc.thePlayer.cameraPitch = 1,
						mc.thePlayer.rotationYaw,
						mc.thePlayer.rotationPitch,
						false));
				mc.thePlayer.motionX *= 0;
				mc.thePlayer.motionZ *= 0;
			}
    	
    	
        if (mode.getValue() == Mode.HYPIXEL) {
            if (!event.isPre()) {
                double multiplier = 0.3;
                double mx = -Math.sin(Math.toRadians(getMc().thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(getMc().thePlayer.rotationYaw));
                double x = getMc().thePlayer.movementInput.moveForward * multiplier * mx + getMc().thePlayer.movementInput.moveStrafe * multiplier * mz;
                double z = getMc().thePlayer.movementInput.moveForward * multiplier * mz - getMc().thePlayer.movementInput.moveStrafe * multiplier * mx;
                if (getMc().thePlayer.isCollidedHorizontally && !getMc().thePlayer.isOnLadder()) {
                    getMc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX + x, getMc().thePlayer.posY, getMc().thePlayer.posZ + z, false));
                    for (int i = 1; i < 10; ++i) {
                       // getMc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, 8.988465674311579E307, getMc().thePlayer.posZ, false));
                        getMc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, 8.988465674311579, getMc().thePlayer.posZ, false));
                    }
                    getMc().thePlayer.setPosition(getMc().thePlayer.posX + x, getMc().thePlayer.posY, getMc().thePlayer.posZ + z);
                }
        	
            }
        }
        if (mode.getValue() == Mode.VANILLA && getMc().gameSettings.keyBindSneak.isPressed() && !isInsideBlock()) {
            getMc().thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY - 2.0, getMc().thePlayer.posZ, true));
            moveUnder = 2;
        }
        if (mode.getValue() == Mode.NCP) {
            final String lowerCase= getMc().getRenderViewEntity().getHorizontalFacing().name().toLowerCase();
            if (getMc().thePlayer.isCollidedHorizontally && getMc().thePlayer.moveForward != 0.0f) {
                ++delay;
                switch (lowerCase) {
                    case "east": {
                        getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX + 9.999999747378752E-6, getMc().thePlayer.posY, getMc().thePlayer.posZ, false));
                        break;
                    }
                    case "west": {
                        getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX - 9.999999747378752E-6, getMc().thePlayer.posY, getMc().thePlayer.posZ, false));
                        break;
                    }
                    case "north": {
                        getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY, getMc().thePlayer.posZ - 9.999999747378752E-6, false));
                        break;
                    }
                    case "south": {
                        getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY, getMc().thePlayer.posZ + 9.999999747378752E-6, false));
                        break;
                    }
                    default:
                        break;
                }
                if (delay >= 1) {
                    switch (lowerCase) {
                        case "east": {
                            getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX + 2.0, getMc().thePlayer.posY, getMc().thePlayer.posZ, false));
                            break;
                        }
                        case "west": {
                            getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX - 2.0, getMc().thePlayer.posY, getMc().thePlayer.posZ, false));
                            break;
                        }
                        case "north": {
                            getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY, getMc().thePlayer.posZ - 2.0, false));
                            break;
                        }
                        case "south": {
                            getMc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY, getMc().thePlayer.posZ + 2.0, false));
                            break;
                        }
                        default:
                            break;
                    }
                    delay = 0;
                }
            }
        }
    }

    private boolean isInBlock(Entity e, float offset) {
        for (int x = MathHelper.floor_double(e.getEntityBoundingBox().minX); x < MathHelper.floor_double(e.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(e.getEntityBoundingBox().minY); y < MathHelper.floor_double(e.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(e.getEntityBoundingBox().minZ); z < MathHelper.floor_double(e.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = getMc().theWorld.getBlockState(new BlockPos(x, y + offset, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
                        final AxisAlignedBB boundingBox = block.getCollisionBoundingBox(getMc().theWorld, new BlockPos(x, y + offset, z), getMc().theWorld.getBlockState(new BlockPos(x, y + offset, z)));
                        if (boundingBox != null && e.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = getMc().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(getMc().theWorld, new BlockPos(x, y, z), getMc().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null) && (getMc().thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void onEnable() {
        phasing = false;
    }

    @Override
    public void onDisable() {
        delay = 0;
        ncpSetup = false;
        timer.reset();
    }
}
