package non.asset.module.impl.movement;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.SafewalkEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.event.impl.render.Render2DEvent;
import non.asset.module.Module;
import non.asset.module.impl.SpeedModifier;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

public class Scaffold extends Module {
    private List<Block> invalid;
    private TimerUtil timerMotion = new TimerUtil();
    private BlockData blockData;
    private BooleanValue tower = new BooleanValue("Tower", false);
	private EnumValue<Modes> mode = new EnumValue<>("Rotation Mode", Modes.TICK);
	private NumberValue<Float> timerchanger = new NumberValue<>("Timer", 1f, 0.1f, 2.0f, 0.1f);
    private BooleanValue speedmod = new BooleanValue("Speed Modifier", false);
	private NumberValue<Float> speedmodifier = new NumberValue<>("Modifier Value", 0.2f, 0.1f, 1.0f, 0.1f, speedmod, "true");
    private BooleanValue Switch = new BooleanValue("Item Spoof", true);
    private BooleanValue sprint = new BooleanValue("Alow Sprint", true);
    private BooleanValue noswing = new BooleanValue("No Swing", false);
    private BooleanValue keepy = new BooleanValue("KeepY", false);
    private int NoigaY;
    private float yaw;
    private float pitch;
    private float[] serverAngles = new float[2];
    private float[] prevRotations = new float[2];
    
    public BlockPos blockPlaced;
    
    public Scaffold() {
        super("Scaffold", Category.MOVEMENT);
        setDescription("Auto places blocks under you");
        invalid = Arrays.asList(Blocks.anvil, Blocks.wooden_pressure_plate,Blocks.stone_slab,Blocks.wooden_slab,Blocks.stone_slab2, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.sapling,
                Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.chest, Blocks.anvil, Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.gravel);
    }
    public enum Modes{
    	TICK, REDESKY, SNAP, NONE
    }
    public enum Tower {
    	NCP, SPARTAN
    }
    @Override
    public void onEnable() {
        if (getMc().theWorld != null) {
            timerMotion.reset();
            NoigaY = MathHelper.floor_double(getMc().thePlayer.posY);

            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
            
        }
    }
    @Override
    public void onDisable() {
    	mc.timer.timerSpeed = 1;
    	blockPlaced = null;
    }
    @Handler
    public void onPacket(PacketEvent event) {
    }
    @Handler
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
        	if(!sprint.isEnabled()) {
        		mc.thePlayer.setSprinting(false);
        	}
            if (keepy.isEnabled()) {
                if ((!getMc().thePlayer.isMoving() && getMc().gameSettings.keyBindJump.isKeyDown()) || (getMc().thePlayer.isCollidedVertically || getMc().thePlayer.onGround)) {
                    NoigaY = MathHelper.floor_double(getMc().thePlayer.posY);
                }
            } else {
                NoigaY = MathHelper.floor_double(getMc().thePlayer.posY);
            }
            
            blockData = null;
            BlockPos blockBelow = new BlockPos(getMc().thePlayer.posX, NoigaY - 1, getMc().thePlayer.posZ);
            if (Math.abs(getMc().thePlayer.motionX) > 0 && Math.abs(getMc().thePlayer.motionZ) > 0) {
                blockBelow = new BlockPos(getMc().thePlayer.posX, NoigaY - 1.0, getMc().thePlayer.posZ);
            }
            if (getMc().theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
                blockData = getBlockData2(blockBelow);
                if (blockData != null) {
                	blockPlaced = new BlockPos(getBlockData2(blockBelow).position.getX(), getBlockData2(blockBelow).position.getY(), getBlockData2(blockBelow).position.getZ());
                    if(!noswing.isEnabled()) {
                    	getMc().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());	
                    }
                	if(mode.getValue() == Modes.TICK) {
                		pitch = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[1];
                    	yaw = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[0];
                	}
                	if(mode.getValue() == Modes.SNAP) {
                		pitch = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[1];
                    	yaw = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[0];
                    	if(pitch > 90) {
                            event.setYaw(yaw);
                            event.setPitch(pitch);
                        }
                        if(pitch < 90) {
                            event.setYaw(yaw);
                        	event.setPitch(pitch + 10);
                        }
                	} 
                	if(mode.getValue() == Modes.REDESKY) {
            			pitch = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[1];
                		yaw = aimAtLocation(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ())[0];
            		}
                }
            }
    		if(mode.getValue() == Modes.REDESKY) {
            	float[] rot = getRotations(blockBelow.getPosition(), getClosestEnum(blockBelow.getPosition()));
                event.setYaw(rot[0]);
                event.setPitch(86);
        	}
    		if(mode.getValue() == Modes.TICK) {
                if(pitch < 80) {
                    event.setYaw(yaw);
                	event.setPitch(pitch + 12);
                }
        	}
    		if(speedmod.isEnabled()) {
    			if(mc.thePlayer.onGround) {
    				SpeedModifier.setSpeed(speedmodifier.getValue());
    			}
    		}
    		mc.timer.timerSpeed = timerchanger.getValue();
        } else {
            if (blockData != null) {
                if (getBlockCount() <= 0 || (!Switch.isEnabled() && getMc().thePlayer.getCurrentEquippedItem() != null && !(getMc().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock))) {
                    return;
                }
                final int heldItem = getMc().thePlayer.inventory.currentItem;
                boolean hasBlock = false;
                if (Switch.isEnabled()) {
                    for (int i = 0; i < 9; ++i) {
                        if (getMc().thePlayer.inventory.getStackInSlot(i) != null && getMc().thePlayer.inventory.getStackInSlot(i).stackSize != 0 && getMc().thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !invalid.contains(((ItemBlock) getMc().thePlayer.inventory.getStackInSlot(i).getItem()).getBlock())) {
                            getMc().thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(getMc().thePlayer.inventory.currentItem = i));
                            hasBlock = true;
                            break;
                        }
                    }
                    if (!hasBlock) {
                        for (int i = 0; i < 45; ++i) {
                            if (getMc().thePlayer.inventory.getStackInSlot(i) != null && getMc().thePlayer.inventory.getStackInSlot(i).stackSize != 0 && getMc().thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !invalid.contains(((ItemBlock) getMc().thePlayer.inventory.getStackInSlot(i).getItem()).getBlock())) {
                                getMc().playerController.windowClick(getMc().thePlayer.inventoryContainer.windowId, i, 8, 2, getMc().thePlayer);
                                break;
                            }
                        }
                    }
                }
                if (tower.isEnabled()) {
					if (getMc().gameSettings.keyBindJump.isKeyDown() && !getMc().thePlayer.isMoving() && !getMc().thePlayer.isPotionActive(Potion.jump)) {
                        getMc().thePlayer.motionY = 0.42F;
                        getMc().thePlayer.motionX = Minecraft.getMinecraft().thePlayer.motionZ = 0;
                        if (timerMotion.sleep(1500)) {
                            getMc().thePlayer.motionY = -0.28f;
                            timerMotion.reset();
                        }
                	}
                }        
                BlockPos blockBelow = new BlockPos(getMc().thePlayer.posX, NoigaY - 1, getMc().thePlayer.posZ);
                getMc().playerController.onPlayerRightClick(getMc().thePlayer, getMc().theWorld, getMc().thePlayer.getHeldItem(), getBlockData2(blockBelow).position, getBlockData2(blockBelow).face, new Vec3(getBlockData2(blockBelow).position.getX(), getBlockData2(blockBelow).position.getY(), getBlockData2(blockBelow).position.getZ()));
                
                if (Switch.isEnabled()) {
                    getMc().thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(getMc().thePlayer.inventory.currentItem = heldItem));
                }
            }
        }
    }
	private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!getMc().thePlayer.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack is = getMc().thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || invalid.contains(((ItemBlock) item).getBlock())) continue;
            blockCount += is.stackSize;
        }
        return blockCount;
    }

    @Handler
    public void onRender2D(Render2DEvent event) {
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        ScaledResolution sr = new ScaledResolution(getMc());
        hud.fontValue.getValue().drawStringWithShadow(Integer.toString(getBlockCount()), sr.getScaledWidth() / 2 + 1 - hud.fontValue.getValue().getStringWidth(Integer.toString(getBlockCount())) / 2, sr.getScaledHeight() / 2 + 24, getBlockColor(getBlockCount()));
    }

    @Handler
    public void onSafewalk(SafewalkEvent event) {
        if (getMc().thePlayer != null) {
        	event.setCanceled(!keepy.isEnabled() && getMc().thePlayer.onGround);
        }
    }
    
    private float[] smoothAngle(float[] dst, float[] src, float i) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        smoothedAngle = MathUtils.constrainAngle(smoothedAngle);
        smoothedAngle[0] = (src[0] - smoothedAngle[0] / i > 0 ? i : 1);
        smoothedAngle[1] = (src[1] - smoothedAngle[1] / i > 0 ? i : 1);
        return smoothedAngle;
    }

    private float getDistance(float[] original) {
        final float yaw = MathHelper.wrapAngleTo180_float(serverAngles[0]) - MathHelper.wrapAngleTo180_float(original[0]);
        final float pitch = MathHelper.wrapAngleTo180_float(serverAngles[1]) - MathHelper.wrapAngleTo180_float(original[1]);
        return (float) Math.sqrt(yaw * yaw + pitch * pitch);
    }

    public BlockData getBlockData2(BlockPos pos) {
        if (!invalid.contains(getMc().theWorld.getBlockState((pos.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.add(-1, 0, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.add(1, 0, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.add(0, 0, 1);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.add(0, 0, -1);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos19 = pos.add(-2, 0, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos1.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos2.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos3.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos4.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.add(0, -1, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos5.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos5.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos5.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos5.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos5.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.add(1, 0, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos6.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos6.add(-1, 0, 0))).getBlock())) {
            return new BlockData(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos6.add(1, 0, 0))).getBlock())) {
            return new BlockData(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos6.add(0, 0, 1))).getBlock())) {
            return new BlockData(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState((pos6.add(0, 0, -1))).getBlock())) {
            return new BlockData(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.add(-1, 0, 0);
        if (!invalid.contains(getMc().theWorld.getBlockState((pos7.add(0, -1, 0))).getBlock())) {
            return new BlockData(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos7.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos7.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos7.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos7.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.add(0, 0, 1);
        if (!invalid.contains(getMc().theWorld.getBlockState(pos8.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos8.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos8.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos8.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos8.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.add(0, 0, -1);
        if (!invalid.contains(getMc().theWorld.getBlockState(pos9.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos9.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos9.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos9.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalid.contains(getMc().theWorld.getBlockState(pos9.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    private int getBlockColor(int count) {
        float f = count;
        float f1 = 64;
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }

    private float[] aimAtLocation(double positionX, double positionY, double positionZ) {
        double x = positionX - getMc().thePlayer.posX;
        double y = positionY - getMc().thePlayer.posY;
        double z = positionZ - getMc().thePlayer.posZ;
        double distance = MathHelper.sqrt_double(x * x + z * z);
        return new float[]{(float) (Math.atan2(z, x) * 180 / 3) - 90.0f, (float) (-(Math.atan2(y, distance) * 180 / 3)), (float) (-(Math.atan2(y, distance) * 180 / 3))};
    }
    public class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    
    public boolean ticks(int ticks) {
    	return (mc.thePlayer.ticksExisted % ticks == 0);
    }
    
    public boolean reach(TimerUtil a, long time) {
    	return a.reach(time);
    }
    
    public boolean ticksMore(boolean more, int irc) {
    	
    	if(!more) {
        	return mc.thePlayer.ticksExisted <= irc;
    	}
    	
    	return mc.thePlayer.ticksExisted > irc;
    }
    
    public static float[] getRotations(BlockPos block, EnumFacing face){
		
		Minecraft mc = Minecraft.getMinecraft();
		
		double x = block.getX() + 0.5 - mc.thePlayer.posX + (double)face.getFrontOffsetX()/2;
		double z = block.getZ() + 0.5 - mc.thePlayer.posZ + (double)face.getFrontOffsetZ()/2;
		double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() -(block.getY() + 0.5);
		double d3 = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
		if(yaw < 0.0F){
			yaw += 360f;
		}
		return  new float[]{yaw, pitch};
	}

    public static EnumFacing getClosestEnum(BlockPos pos){
     	EnumFacing closestEnum = EnumFacing.UP;
    	float rotations = MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[0]);
    	if(rotations >= 45 && rotations <= 135){
    		closestEnum = EnumFacing.EAST;
    	}else if((rotations >= 135 && rotations <= 180) ||
    			(rotations <= -135 && rotations >= -180)){
    		closestEnum = EnumFacing.SOUTH;
    	}else if(rotations <= -45 && rotations >= -135){
    		closestEnum = EnumFacing.WEST;
    	}else if((rotations >= -45 && rotations <= 0) ||
    			(rotations <= 45 && rotations >= 0)){
    		closestEnum = EnumFacing.NORTH;
    	}
    	if (MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) > 75 || 
    			MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) < -75){
    		closestEnum = EnumFacing.UP;
    	}
    	return closestEnum;
	}
    public static EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.UP;
        } else if (!mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.DOWN;
        } else if (!mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.EAST;
        } else if (!mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.WEST;
        } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.SOUTH;
        } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube() && !(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.NORTH;
        }
        MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
        if (rayResult != null && rayResult.getBlockPos() == pos) {
            return rayResult.sideHit;
        }
        return direction;
    }

    public List<Block> getInvalid() {
		return invalid;
	}
	public void setInvalid(List<Block> invalid) {
		this.invalid = invalid;
	}
	public TimerUtil getTimerMotion() {
		return timerMotion;
	}
	public void setTimerMotion(TimerUtil timerMotion) {
		this.timerMotion = timerMotion;
	}
	public BlockData getBlockData() {
		return blockData;
	}
	public void setBlockData(BlockData blockData) {
		this.blockData = blockData;
	}
	public BooleanValue getTower() {
		return tower;
	}
	public void setTower(BooleanValue tower) {
		this.tower = tower;
	}
	public EnumValue<Modes> getMode() {
		return mode;
	}
	public void setMode(EnumValue<Modes> mode) {
		this.mode = mode;
	}
	public NumberValue<Float> getTimerchanger() {
		return timerchanger;
	}
	public void setTimerchanger(NumberValue<Float> timerchanger) {
		this.timerchanger = timerchanger;
	}
	public BooleanValue getSpeedmod() {
		return speedmod;
	}
	public void setSpeedmod(BooleanValue speedmod) {
		this.speedmod = speedmod;
	}
	public NumberValue<Float> getSpeedmodifier() {
		return speedmodifier;
	}
	public void setSpeedmodifier(NumberValue<Float> speedmodifier) {
		this.speedmodifier = speedmodifier;
	}
	public BooleanValue getSwitch() {
		return Switch;
	}
	public void setSwitch(BooleanValue switch1) {
		Switch = switch1;
	}
	public BooleanValue getSprint() {
		return sprint;
	}
	public void setSprint(BooleanValue sprint) {
		this.sprint = sprint;
	}
	public BooleanValue getNoswing() {
		return noswing;
	}
	public void setNoswing(BooleanValue noswing) {
		this.noswing = noswing;
	}
	public BooleanValue getKeepy() {
		return keepy;
	}
	public void setKeepy(BooleanValue keepy) {
		this.keepy = keepy;
	}
	public int getNoigaY() {
		return NoigaY;
	}
	public void setNoigaY(int noigaY) {
		NoigaY = noigaY;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float[] getServerAngles() {
		return serverAngles;
	}
	public void setServerAngles(float[] serverAngles) {
		this.serverAngles = serverAngles;
	}
	public float[] getPrevRotations() {
		return prevRotations;
	}
	public void setPrevRotations(float[] prevRotations) {
		this.prevRotations = prevRotations;
	}
}