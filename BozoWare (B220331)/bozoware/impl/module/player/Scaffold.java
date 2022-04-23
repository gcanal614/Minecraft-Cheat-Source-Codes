// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import java.util.Random;
import net.minecraft.util.Vec3;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.RandomUtils;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.block.material.Material;
import bozoware.base.util.Wrapper;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.util.MathHelper;
import bozoware.base.util.player.MovementUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import bozoware.base.BozoWare;
import net.minecraft.block.Block;
import java.util.List;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Scaffol2d", moduleCategory = ModuleCategory.PLAYER)
public class Scaffold extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    private final EnumProperty<RotModes> ROTATION_MODE;
    private final BooleanProperty towerBool;
    private final EnumProperty<TowerModes> towerMode;
    private final BooleanProperty switchBool;
    public final BooleanProperty swBool;
    private final BooleanProperty noSwing;
    private final BooleanProperty slowedBool;
    private final BooleanProperty noSprintBool;
    public final BooleanProperty timerBool;
    private final ValueProperty<Double> timerMin;
    private final ValueProperty<Double> timerMax;
    EnumFacing facing;
    boolean placing;
    BlockPos blockBefore;
    BlockPos blockUnder;
    BlockData data;
    public TimerUtil timer;
    private final List<Block> invalid;
    private int slot;
    private int lastSlot;
    private int blockCount;
    private int blocksPlaced;
    private static float nextYaw;
    private static float nextPitch;
    
    public static Scaffold getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Scaffold.class);
    }
    
    public Scaffold() {
        this.ROTATION_MODE = new EnumProperty<RotModes>("Rotation Mode", RotModes.LookDir, this);
        this.towerBool = new BooleanProperty("Tower", false, this);
        this.towerMode = new EnumProperty<TowerModes>("Tower Mode", TowerModes.NCP, this);
        this.switchBool = new BooleanProperty("Switch To Block", true, this);
        this.swBool = new BooleanProperty("SafeWalk", true, this);
        this.noSwing = new BooleanProperty("NoSwing", false, this);
        this.slowedBool = new BooleanProperty("Slow Movement", true, this);
        this.noSprintBool = new BooleanProperty("No Sprint", true, this);
        this.timerBool = new BooleanProperty("Timer", false, this);
        this.timerMin = new ValueProperty<Double>("Timer Min", 0.75, 0.1, 5.0, this);
        this.timerMax = new ValueProperty<Double>("Timer Max", 1.45, 0.1, 5.0, this);
        this.timer = new TimerUtil();
        this.invalid = Arrays.asList(Blocks.anvil, Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.chest, Blocks.anvil, Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.gravel);
        this.setModuleBind(50);
        this.timerMax.setHidden(true);
        this.timerMin.setHidden(true);
        this.timerBool.onValueChange = (() -> {
            this.timerMax.setHidden(!this.timerBool.getPropertyValue());
            this.timerMin.setHidden(!this.timerBool.getPropertyValue());
            return;
        });
        this.onModuleDisabled = (() -> {
            Scaffold.mc.timer.timerSpeed = 1.0f;
            if (this.switchBool.getPropertyValue()) {
                Scaffold.mc.thePlayer.inventory.currentItem = this.lastSlot;
            }
            return;
        });
        this.onModuleEnabled = (() -> {
            this.blocksPlaced = 0;
            if (this.switchBool.getPropertyValue()) {
                this.lastSlot = Scaffold.mc.thePlayer.inventory.currentItem;
            }
            return;
        });
        double timerMinClamped;
        double bruh;
        double x;
        double z;
        double y;
        BlockPos block;
        final EnumFacing[] array;
        int length;
        int i = 0;
        EnumFacing facing;
        BlockData data;
        float[] ROTATIONS;
        float[] ROTATIONS2;
        float yaw;
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        EntityPlayerSP thePlayer4;
        EntityPlayerSP thePlayer5;
        final double n;
        this.onUpdatePositionEvent = (e -> {
            this.blockCount = this.getBlockCount();
            if (e.isPre()) {
                if (this.switchBool.getPropertyValue()) {
                    if (this.getBlockSlot() != -1) {
                        Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.getBlockSlot()));
                        Scaffold.mc.thePlayer.inventory.currentItem = this.getBlockSlot();
                    }
                    else {
                        BozoWare.getInstance().chat("No blocks found in hotbar.");
                        this.toggleModule();
                    }
                }
                if (this.slowedBool.getPropertyValue()) {
                    MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() / 3.0);
                }
                if (this.noSprintBool.getPropertyValue()) {
                    Scaffold.mc.thePlayer.setSprinting(false);
                }
                if (this.timerBool.getPropertyValue()) {
                    timerMinClamped = MathHelper.clamp_double(this.timerMin.getPropertyValue(), 0.1, this.timerMax.getPropertyValue() - 0.1);
                    bruh = ThreadLocalRandom.current().nextDouble(timerMinClamped, (double)this.timerMax.getPropertyValue());
                    Scaffold.mc.timer.timerSpeed = (float)bruh;
                }
                x = Scaffold.mc.thePlayer.posX;
                z = Scaffold.mc.thePlayer.posZ;
                y = Scaffold.mc.thePlayer.posY;
                this.setModuleSuffix(String.valueOf(this.blocksPlaced));
                block = new BlockPos(x, y, z);
                if (Wrapper.getBlock(block).getMaterial() == Material.air) {
                    EnumFacing.values();
                    for (length = array.length; i < length; ++i) {
                        facing = array[i];
                        this.placing = true;
                        this.facing = facing;
                        data = this.getBlockData(block);
                        this.data = data;
                    }
                }
                switch (this.ROTATION_MODE.getPropertyValue()) {
                    case Basic: {
                        e.setPitch(75.0f);
                        Scaffold.mc.thePlayer.rotationPitchHead = 75.0f;
                        if (this.data != null) {
                            ROTATIONS = getRotations(this.data.position, this.data.face);
                            e.setYaw(ROTATIONS[0]);
                            Scaffold.mc.thePlayer.rotationYawHead = ROTATIONS[0];
                            Scaffold.mc.thePlayer.renderYawOffset = ROTATIONS[0];
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        if (this.data != null) {
                            ROTATIONS2 = Wrapper.getFacePos(Wrapper.getVec3(this.data.position));
                            e.setPitch(ROTATIONS2[1]);
                            Scaffold.mc.thePlayer.rotationPitchHead = ROTATIONS2[1];
                            e.setYaw(ROTATIONS2[0]);
                            Scaffold.mc.thePlayer.rotationYawHead = ROTATIONS2[0];
                            Scaffold.mc.thePlayer.renderYawOffset = ROTATIONS2[0];
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case LookDir: {
                        e.setPitch(75.0f);
                        Scaffold.mc.thePlayer.rotationPitchHead = 75.0f;
                        yaw = MovementUtil.getDirectionStrafeFix(Scaffold.mc.thePlayer.moveForward, Scaffold.mc.thePlayer.moveStrafing, Scaffold.mc.thePlayer.rotationYaw + 180.0f);
                        Scaffold.mc.thePlayer.renderYawOffset = yaw;
                        e.setYaw(Scaffold.mc.thePlayer.rotationYawHead = yaw);
                        break;
                    }
                }
            }
            else if (this.data != null && Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.getCurrentEquippedItem(), this.data.position, this.data.face, this.getVec3(this.data.position, this.data.face))) {
                ++this.blocksPlaced;
                if (this.blocksPlaced % 5 == 0) {
                    Scaffold.nextPitch = nextPitch();
                }
                if (this.noSwing.getPropertyValue()) {
                    Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
                else {
                    Scaffold.mc.thePlayer.swingItem();
                }
                if (this.towerBool.getPropertyValue() && Keyboard.isKeyDown(57)) {
                    switch (this.towerMode.getPropertyValue()) {
                        case NCP: {
                            Scaffold.mc.thePlayer.motionY = 0.41999998688697815;
                            break;
                        }
                        case Watchdog: {
                            if (!Scaffold.mc.thePlayer.isMoving() && Scaffold.mc.thePlayer.onGround) {
                                thePlayer = Scaffold.mc.thePlayer;
                                thePlayer.motionZ *= 0.0;
                                thePlayer2 = Scaffold.mc.thePlayer;
                                thePlayer2.motionX *= 0.0;
                                thePlayer3 = Scaffold.mc.thePlayer;
                                thePlayer3.motionY += 0.449;
                                break;
                            }
                            else if (Scaffold.mc.thePlayer.onGround && MovementUtil.isOnGround(0.325)) {
                                Scaffold.mc.thePlayer.motionY = 0.41999998688697815;
                                break;
                            }
                            else if (Scaffold.mc.thePlayer.motionY < 0.17 && Scaffold.mc.thePlayer.motionY > 0.16) {
                                Scaffold.mc.thePlayer.motionY = -0.18001240491867065;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case Watchdog2: {
                            if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && !Scaffold.mc.thePlayer.isMoving() && !Scaffold.mc.thePlayer.isPotionActive(Potion.jump)) {
                                Scaffold.mc.thePlayer.jump();
                                thePlayer4 = Scaffold.mc.thePlayer;
                                thePlayer5 = Scaffold.mc.thePlayer;
                                thePlayer5.motionZ = n;
                                thePlayer4.motionX = n;
                                Scaffold.mc.thePlayer.motionY = -0.2800000011920929;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            return;
        });
        final MinecraftFontRenderer LFR;
        final MinecraftFontRenderer LFR2;
        final ScaledResolution SR;
        this.onRender2DEvent = (e -> {
            LFR = BozoWare.getInstance().getFontManager().largeFontRenderer;
            LFR2 = BozoWare.getInstance().getFontManager().largeFontRenderer2;
            SR = e.getScaledResolution();
            LFR.drawStringWithShadow("Blocks " + this.blockCount, SR.getScaledWidth() / 2.0f - 20.0f, SR.getScaledHeight() / 2.0f + 25.0f, -1);
        });
    }
    
    private static float nextPitch() {
        return 73.0f + RandomUtils.nextFloat(0.0f, 2.0f);
    }
    
    public static float[] getRotations(final BlockPos block, final EnumFacing face) {
        final double x = block.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX + face.getFrontOffsetX() / 2.0;
        final double z = block.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ + face.getFrontOffsetZ() / 2.0;
        final double d1 = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (block.getY() + 0.5);
        final double d2 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(Math.atan2(d1, d2) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[] { yaw, Scaffold.nextPitch };
    }
    
    private BlockData getBlockData(final BlockPos pos) {
        if (this.isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos2 = pos.add(0, -1, 0).add(1, 0, 0);
        final BlockPos pos3 = pos.add(0, -1, 0).add(0, 0, 1);
        final BlockPos pos4 = pos.add(0, -1, 0).add(-1, 0, 0);
        final BlockPos pos5 = pos.add(0, -1, 0).add(0, 0, -1);
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private boolean isPosSolid(final BlockPos pos) {
        final Block block = Scaffold.mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock && this.isValidBlock((ItemBlock)stack.getItem())) {
                blockCount += stack.stackSize;
            }
        }
        return blockCount;
    }
    
    public Vec3 getVec3(final BlockPos pos, final EnumFacing face) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        x += face.getFrontOffsetX() / 2.0;
        z += face.getFrontOffsetZ() / 2.0;
        y += face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += getRandomInRange(0.25, -0.25);
            z += getRandomInRange(0.25, -0.25);
        }
        else {
            y += getRandomInRange(0.25, -0.25);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += getRandomInRange(0.25, -0.25);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += getRandomInRange(0.25, -0.25);
        }
        return new Vec3(x, y, z);
    }
    
    public static double getRandomInRange(final double min, final double max) {
        final Random random = new Random();
        final double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;
        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }
    
    private boolean isValidBlock(final ItemBlock Block) {
        return !this.invalid.contains(Block.getBlock());
    }
    
    private int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && this.isValidBlock((ItemBlock)itemStack.getItem())) {
                return i - 36;
            }
        }
        return -1;
    }
    
    private class BlockData
    {
        public BlockPos position;
        public EnumFacing face;
        
        private BlockData(final BlockPos position, final EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
    
    private enum TowerModes
    {
        NCP, 
        Watchdog, 
        Watchdog2;
    }
    
    private enum RotModes
    {
        Watchdog, 
        LookDir, 
        Basic, 
        Behind;
    }
}
