// Decompiled with: CFR 0.152
// Class Version: 8
package bozoware.impl.module.player;

import bozoware.base.BozoWare;
import bozoware.base.event.EventConsumer;
import bozoware.base.event.EventListener;
import bozoware.base.module.Module;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.util.Wrapper;
import bozoware.base.util.misc.MathUtil;
import bozoware.base.util.misc.TimerUtil;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.util.visual.BloomUtil;
import bozoware.base.util.visual.RenderUtil;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.impl.module.visual.HUD;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.visual.font.MinecraftFontRenderer;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ModuleData(moduleName="Scaffold", moduleCategory=ModuleCategory.PLAYER)
public class BlockFly
        extends Module {
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    private final EnumProperty<Modes> Mode = new EnumProperty<Modes>("Mode", Modes.NCP, this);
    private final ValueProperty<Integer> expandAmount = new ValueProperty<Integer>("Expand Amount", 3, 0, 12, this);
    private final EnumProperty<RotModes> RotMode = new EnumProperty<RotModes>("Rotation Mode", RotModes.Watchdog, this);
    private BooleanProperty keepY = new BooleanProperty("Keep-Y", true, (Module)this);
    private BooleanProperty autoJump = new BooleanProperty("Auto Jump", true, (Module)this);
    private BooleanProperty hideJumps = new BooleanProperty("Hide Jumps", true, (Module)this);
    private BooleanProperty customSpeedBool = new BooleanProperty("Custom Speed", false, (Module)this);
    private ValueProperty<Double> customSpeed = new ValueProperty<Double>("Custom Speed Value", 0.3, 0.1, 0.5, this);
    private final BooleanProperty RPBool = new BooleanProperty("Random Pitch", false, (Module)this);
    private final BooleanProperty towerBool = new BooleanProperty("Tower", false, (Module)this);
    private final EnumProperty<TowerModes> towerMode = new EnumProperty<TowerModes>("Tower Mode", TowerModes.NCP, this);
    private final BooleanProperty switchBool = new BooleanProperty("Switch To Block", true, (Module)this);
    public final BooleanProperty downWardsBool = new BooleanProperty("Downwards", true, (Module)this);
    public final BooleanProperty swBool = new BooleanProperty("SafeWalk", true, (Module)this);
    private final BooleanProperty noSwing = new BooleanProperty("NoSwing", true, (Module)this);
    private final BooleanProperty CD0 = new BooleanProperty("ClickDelay0", true, (Module)this);
    private final BooleanProperty normalizeVec = new BooleanProperty("Normalize Vec", true, (Module)this);
    private final BooleanProperty slowedBool = new BooleanProperty("Slow Movement", true, (Module)this);
    private final BooleanProperty noSprintBool = new BooleanProperty("No Sprint", true, (Module)this);
    public final BooleanProperty timerBool = new BooleanProperty("Timer", true, (Module)this);
    private final ValueProperty<Double> timerMin = new ValueProperty<Double>("Timer Min", 0.75, 0.1, 5.0, this);
    private final ValueProperty<Double> timerMax = new ValueProperty<Double>("Timer Max", 1.45, 0.1, 5.0, this);
    private double xPosi;
    private double xPosition;
    static BlockPos blockBef;
    static BlockPos NCP;
    static BlockPos blockUnder;
    EnumFacing facing = null;
    boolean placing = false;
    private static double Y;
    BlockData blockdata;
    BlockData data;
    private static double yOnEnable;
    private final Vector3d vec = new Vector3d();
    public TimerUtil timer = new TimerUtil();
    private final List<Block> invalid;
    double YPos;
    private int slot;
    private int lastSlot;
    private int blockCount;
    ScaledResolution sr;

    public static BlockFly getInstance() {
        return (BlockFly)BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class);
    }

    public BlockFly() {
        this.sr = new ScaledResolution(mc, BlockFly.mc.displayWidth, BlockFly.mc.displayHeight);
        this.customSpeed.setHidden(true);
        this.hideJumps.setHidden(false);
        this.autoJump.onValueChange = () -> {
            if (this.autoJump.getPropertyValue().booleanValue()) {
                this.hideJumps.setHidden(false);
            } else {
                this.hideJumps.setHidden(true);
            }
        };
        this.customSpeedBool.onValueChange = () -> {
            if (this.customSpeedBool.getPropertyValue().booleanValue()) {
                this.customSpeed.setHidden(false);
            } else {
                this.customSpeed.setHidden(true);
            }
        };
        this.invalid = Arrays.asList(Blocks.beacon, Blocks.nether_wart, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.flower_pot, Blocks.red_flower, Blocks.yellow_flower, Blocks.waterlily, Blocks.double_plant);
        this.setModuleBind(50);
        this.timerBool.onValueChange = () -> {
            this.timerMax.setHidden(this.timerBool.getPropertyValue() == false);
            this.timerMin.setHidden(this.timerBool.getPropertyValue() == false);
        };
        this.onModuleDisabled = () -> {
            BlockFly.mc.gameSettings.keyBindJump.pressed = false;
            BlockFly.mc.timer.timerSpeed = 1.0f;
            if (this.switchBool.getPropertyValue().booleanValue()) {
                BlockFly.mc.thePlayer.inventory.currentItem = this.lastSlot;
            } else {
                Wrapper.sendPacketDirect(new C09PacketHeldItemChange(this.lastSlot));
            }
            blockUnder = null;
            blockBef = null;
        };
        this.onModuleEnabled = () -> {
            this.xPosi = this.sr.getScaledWidth() / 2;
            this.xPosition = this.sr.getScaledWidth() / 2;
            yOnEnable = BlockFly.mc.thePlayer.posY;
            Y = MathHelper.floor_double(BlockFly.mc.thePlayer.posY);
            if (this.switchBool.getPropertyValue().booleanValue() && BlockFly.mc.thePlayer.inventory.getCurrentItem() != null) {
                this.lastSlot = BlockFly.mc.thePlayer.inventory.currentItem;
            }
            blockUnder = null;
            blockBef = null;
        };
        this.onUpdatePositionEvent = e -> {
            if (this.getBlockSlot() != -1 || this.blockCount != 0) {
                // empty if block
            }
            if (BlockFly.mc.thePlayer.onGround) {
                yOnEnable = BlockFly.mc.thePlayer.posY;
            }
            if (this.hideJumps.getPropertyValue().booleanValue() && this.autoJump.getPropertyValue().booleanValue() && !(yOnEnable > BlockFly.mc.thePlayer.posY) && !Keyboard.isKeyDown((int)BlockFly.mc.gameSettings.keyBindJump.getKeyCode())) {
                BlockFly.mc.thePlayer.posY -= BlockFly.mc.thePlayer.posY - BlockFly.mc.thePlayer.lastTickPosY;
                BlockFly.mc.thePlayer.lastTickPosY -= BlockFly.mc.thePlayer.posY - BlockFly.mc.thePlayer.lastTickPosY;
            }
            if (this.autoJump.getPropertyValue().booleanValue() && BlockFly.mc.thePlayer.isMoving() && this.getBlockSlot() != -1) {
                BlockFly.mc.gameSettings.keyBindJump.pressed = true;
            }
            if (this.autoJump.getPropertyValue().booleanValue() && !BlockFly.mc.thePlayer.isMoving() && !Keyboard.isKeyDown((int)BlockFly.mc.gameSettings.keyBindJump.getKeyCode())) {
                BlockFly.mc.gameSettings.keyBindJump.pressed = false;
            }
            if (this.customSpeedBool.getPropertyValue().booleanValue() && this.getBlockSlot() != -1) {
                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * (1.0 + (Double)this.customSpeed.getPropertyValue()) / 1.5);
            }
            this.blockCount = this.getBlockCount();
            if (e.isPre()) {
                if (this.switchBool.getPropertyValue().booleanValue()) {
                    if (this.getBlockSlot() != -1) {
                        BlockFly.mc.thePlayer.inventory.currentItem = this.getBlockSlot();
                    } else {
                        return;
                    }
                }
                if (this.slowedBool.getPropertyValue().booleanValue()) {
                    MovementUtil.setMoveSpeed(0.1);
                }
                if (this.noSprintBool.getPropertyValue().booleanValue()) {
                    BlockFly.mc.thePlayer.setSprinting(false);
                } else if (Wrapper.getPlayer().isMovingForward() && (Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6 || Wrapper.getPlayer().capabilities.isCreativeMode) && !BlockFly.mc.thePlayer.isCollidedHorizontally) {
                    BlockFly.mc.thePlayer.setSprinting(true);
                }
                if (this.timerBool.getPropertyValue().booleanValue()) {
                    if (Keyboard.isKeyDown((int)BlockFly.mc.gameSettings.keyBindJump.getKeyCode())) {
                        BlockFly.mc.timer.timerSpeed = 1.0f;
                    }
                    if (!Keyboard.isKeyDown((int)BlockFly.mc.gameSettings.keyBindJump.getKeyCode())) {
                        double timerMinClamped = MathHelper.clamp_double((Double)this.timerMin.getPropertyValue(), 0.1, (Double)this.timerMax.getPropertyValue() - 0.1);
                        double bruh = ThreadLocalRandom.current().nextDouble(timerMinClamped, ((Double)this.timerMax.getPropertyValue()).doubleValue());
                        BlockFly.mc.timer.timerSpeed = (float)bruh;
                    }
                }
                BlockFly.mc.rightClickDelayTimer = this.CD0.getPropertyValue() != false ? 0 : 6;
                double x = BlockFly.mc.thePlayer.posX;
                double z = BlockFly.mc.thePlayer.posZ;
                double y = BlockFly.mc.thePlayer.posY;
                blockUnder = null;
                blockBef = null;
                if (this.keepY.getPropertyValue().booleanValue()) {
                    if (!BlockFly.mc.thePlayer.isMoving() && BlockFly.mc.gameSettings.keyBindJump.isKeyDown() || BlockFly.mc.thePlayer.isCollidedVertically || BlockFly.mc.thePlayer.onGround) {
                        Y = MathHelper.floor_double(BlockFly.mc.thePlayer.posY);
                    }
                } else {
                    Y = MathHelper.floor_double(BlockFly.mc.thePlayer.posY);
                }
                if (Keyboard.isKeyDown((int)BlockFly.mc.gameSettings.keyBindSneak.getKeyCode()) && !BlockFly.mc.thePlayer.isCollidedHorizontally && this.downWardsBool.getPropertyValue().booleanValue()) {
                    BlockFly.mc.thePlayer.setSneaking(false);
                    BlockFly.mc.gameSettings.keyBindSneak.pressed = false;
                    Y = MathHelper.floor_double(BlockFly.mc.thePlayer.posY) - 1;
                }
                block0 : switch ((Modes)((Object)((Object)this.Mode.getPropertyValue()))) {
                    case NCP: {
                        NCP = new BlockPos(BlockFly.mc.thePlayer.posX, Y - 1.0, BlockFly.mc.thePlayer.posZ);
                        if (Wrapper.getBlock(NCP).getMaterial() != Material.air) break;
                        blockUnder = new BlockPos(BlockFly.mc.thePlayer.posX, Y - 1.0, BlockFly.mc.thePlayer.posZ);
                        for (EnumFacing facing : EnumFacing.values()) {
                            BlockPos offset = blockUnder.offset(facing);
                            if (Wrapper.getBlock(offset).getMaterial() == Material.air) continue;
                            this.facing = facing;
                            blockBef = offset;
                            break block0;
                        }
                        break;
                    }
                    case Expand: {
                        double n2 = (double)((Integer)this.expandAmount.getPropertyValue()).intValue() + 1.0E-4;
                        block18: for (double n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2) + 1.0)) {
                            BlockPos Expand = new BlockPos(BlockFly.mc.thePlayer.posX - (double)MathHelper.sin(MathUtil.clampRotation()) * n3, BlockFly.mc.thePlayer.posY - 1.0, BlockFly.mc.thePlayer.posZ + (double)MathHelper.cos(MathUtil.clampRotation()) * n3);
                            if (Wrapper.getBlock(Expand).getMaterial() != Material.air) continue;
                            blockUnder = new BlockPos(BlockFly.mc.thePlayer.posX + BlockFly.mc.thePlayer.motionX, Y - 1.0, BlockFly.mc.thePlayer.posZ + BlockFly.mc.thePlayer.motionZ);
                            for (EnumFacing facing : EnumFacing.values()) {
                                BlockPos offset = Expand.offset(facing);
                                if (Wrapper.getBlock(offset).getMaterial() == Material.air) continue;
                                this.facing = facing;
                                blockBef = offset;
                                continue block18;
                            }
                        }
                        break;
                    }
                    case ReallyBad: {
                        float yaw = BlockFly.mc.thePlayer.rotationYaw;
                        double dist = 0.0;
                        BlockPos underPos = new BlockPos(x + -Math.sin(Math.toRadians(yaw)) * dist, this.YPos, z + Math.cos(Math.toRadians(yaw)) * dist);
                        this.data = this.getBlockData(underPos);
                        BlockData data = this.getBlockData(underPos);
                        if (this.towerBool.getPropertyValue().booleanValue() && Keyboard.isKeyDown((int)57) && !BlockFly.mc.thePlayer.isPotionActive(Potion.jump) && !BlockFly.mc.thePlayer.isMoving()) {
                            this.YPos = y - 1.0;
                            BlockFly.mc.thePlayer.motionY = 0.42f;
                            break;
                        }
                        this.YPos = y;
                    }
                }
                switch ((RotModes)((Object)((Object)this.RotMode.getPropertyValue()))) {
                    case Basic: {
                        float[] rots;
                        if (blockBef != null) {
                            rots = Wrapper.getFacePos(Wrapper.getVec3(blockBef));
                            float yaw = BlockFly.mc.thePlayer.rotationYaw;
                            double dist = 0.0;
                            BlockPos underPos = new BlockPos(x + -Math.sin(Math.toRadians(yaw)) * dist, y, z + Math.cos(Math.toRadians(yaw)) * dist);
                            this.data = this.getBlockData(underPos);
                            break;
                        }
                        e.setYaw(BlockFly.mc.thePlayer.prevRotationYaw + 180.0f);
                        BlockFly.mc.thePlayer.rotationYawHead = BlockFly.mc.thePlayer.prevRotationYaw + 180.0f;
                        BlockFly.mc.thePlayer.renderYawOffset = BlockFly.mc.thePlayer.prevRotationYaw + 180.0f;
                        if (!this.RPBool.getPropertyValue().booleanValue()) {
                            e.setPitch(75.0f);
                            BlockFly.mc.thePlayer.rotationPitchHead = 75.0f;
                            break;
                        }
                        float rpitch = ThreadLocalRandom.current().nextInt(75, 90);
                        e.setPitch(rpitch);
                        BlockFly.mc.thePlayer.rotationPitchHead = rpitch;
                        break;
                    }
                    case Watchdog: {
                        float[] rots;
                        if (!this.RPBool.getPropertyValue().booleanValue()) {
                            e.setPitch(75.0f);
                            BlockFly.mc.thePlayer.rotationPitchHead = 75.0f;
                        } else {
                            float rpitch = ThreadLocalRandom.current().nextInt(75, 90);
                            e.setPitch(rpitch);
                            BlockFly.mc.thePlayer.rotationPitchHead = rpitch;
                        }
                        if (blockBef != null) {
                            rots = Wrapper.getFacePos(Wrapper.getVec3(blockBef));
                            e.setPitch(rots[1]);
                            BlockFly.mc.thePlayer.rotationPitchHead = rots[1];
                            e.setYaw(rots[0]);
                            BlockFly.mc.thePlayer.rotationYawHead = rots[0];
                            BlockFly.mc.thePlayer.renderYawOffset = rots[0];
                            break;
                        }
                        e.setYaw(BlockFly.mc.thePlayer.rotationYaw + 180.0f);
                        BlockFly.mc.thePlayer.rotationYawHead = BlockFly.mc.thePlayer.rotationYaw + 180.0f;
                        BlockFly.mc.thePlayer.renderYawOffset = BlockFly.mc.thePlayer.rotationYaw + 180.0f;
                        break;
                    }
                    case LookDir: {
                        float yaw;
                        e.setPitch(75.0f);
                        BlockFly.mc.thePlayer.rotationPitchHead = 75.0f;
                        BlockFly.mc.thePlayer.renderYawOffset = yaw = MovementUtil.getDirectionStrafeFix(BlockFly.mc.thePlayer.moveForward, BlockFly.mc.thePlayer.moveStrafing, BlockFly.mc.thePlayer.rotationYaw + 180.0f);
                        BlockFly.mc.thePlayer.rotationYawHead = yaw;
                        e.setYaw((float)((double)yaw + Math.random()));
                        break;
                    }
                    case Behind: {
                        if (!this.RPBool.getPropertyValue().booleanValue()) {
                            e.setPitch(75.0f);
                            BlockFly.mc.thePlayer.rotationPitchHead = 75.0f;
                        } else {
                            float rpitch = ThreadLocalRandom.current().nextInt(75, 90);
                            e.setPitch(rpitch);
                            BlockFly.mc.thePlayer.rotationPitchHead = rpitch;
                        }
                        e.setYaw(BlockFly.mc.thePlayer.rotationYaw + 180.0f);
                        BlockFly.mc.thePlayer.rotationYawHead = BlockFly.mc.thePlayer.rotationYaw + 180.0f;
                        BlockFly.mc.thePlayer.renderYawOffset = BlockFly.mc.thePlayer.rotationYaw + 180.0f;
                    }
                }
            }
            if (!e.isPre()) {
                this.placing = false;
                if (blockUnder == null) {
                    return;
                }
                if (blockBef == null) {
                    return;
                }
                this.placing = true;
                BlockPos pos2 = new BlockPos(blockBef.getX(), blockBef.getY(), blockBef.getZ());
                MovingObjectPosition pos = BlockFly.mc.theWorld.rayTraceBlocks(Wrapper.getVec3(blockUnder).addVector(0.5, 0.5, 0.5), Wrapper.getVec3(blockBef).addVector(0.5, 0.5, 0.5));
                if (pos == null) {
                    return;
                }
                Vec3 hitVec = Wrapper.getVec3(blockUnder);
                if ((((Modes)((Object)((Object)this.Mode.getPropertyValue()))).equals((Object)Modes.NCP) || ((Modes)((Object)((Object)this.Mode.getPropertyValue()))).equals((Object)Modes.Expand)) && blockBef != null && (this.normalizeVec.getPropertyValue() != false ? BlockFly.mc.playerController.onPlayerRightClick(BlockFly.mc.thePlayer, BlockFly.mc.theWorld, BlockFly.mc.thePlayer.getCurrentEquippedItem(), pos2, pos.sideHit, hitVec.normalize()) : BlockFly.mc.playerController.onPlayerRightClick(BlockFly.mc.thePlayer, BlockFly.mc.theWorld, BlockFly.mc.thePlayer.getCurrentEquippedItem(), pos2, pos.sideHit, hitVec))) {
                    if (this.noSwing.getPropertyValue().booleanValue()) {
                        BlockFly.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    } else {
                        BlockFly.mc.thePlayer.swingItem();
                    }
                    if (BlockFly.mc.thePlayer.isMoving() && BlockFly.mc.timer.timerSpeed == 0.5f && !this.timerBool.getPropertyValue().booleanValue()) {
                        BlockFly.mc.timer.timerSpeed = 1.0f;
                    }
                    if (this.towerBool.getPropertyValue().booleanValue() && Keyboard.isKeyDown((int)57) && !BlockFly.mc.thePlayer.isPotionActive(Potion.jump)) {
                        switch ((TowerModes)((Object)((Object)this.towerMode.getPropertyValue()))) {
                            case NCP: {
                                if (!e.isPre && this.getBlockSlot() != -1 && BlockFly.mc.gameSettings.keyBindJump.isKeyDown() && !BlockFly.mc.thePlayer.isMoving()) {
                                    BlockFly.mc.thePlayer.motionZ = 0.0;
                                    BlockFly.mc.thePlayer.motionX = 0.0;
                                    if (BlockFly.mc.thePlayer.onGround) {
                                        BlockFly.mc.thePlayer.jump();
                                        if (this.timer.hasReached(1500L)) {
                                            BlockFly.mc.thePlayer.motionY = -0.28;
                                            this.timer.reset();
                                        }
                                    }
                                    BlockFly.mc.thePlayer.motionY = 0.41955;
                                }
                                BlockFly.mc.timer.timerSpeed = 0.5f;
                                break;
                            }
                            case Watchdog: {
                                if (!BlockFly.mc.thePlayer.isMoving() && BlockFly.mc.thePlayer.onGround) {
                                    BlockFly.mc.thePlayer.motionZ *= 0.0;
                                    BlockFly.mc.thePlayer.motionX *= 0.0;
                                    BlockFly.mc.thePlayer.motionY += 0.449;
                                    break;
                                }
                                if (BlockFly.mc.thePlayer.onGround && MovementUtil.isOnGround(0.325)) {
                                    BlockFly.mc.thePlayer.motionY = 0.42f;
                                    break;
                                }
                                if (!(BlockFly.mc.thePlayer.motionY < 0.17) || !(BlockFly.mc.thePlayer.motionY > 0.16)) break;
                                BlockFly.mc.thePlayer.motionY = -0.1800124f;
                                break;
                            }
                            case Watchdog2: {
                                if (!BlockFly.mc.gameSettings.keyBindJump.isKeyDown() || BlockFly.mc.thePlayer.isMoving() || BlockFly.mc.thePlayer.isPotionActive(Potion.jump)) break;
                                BlockFly.mc.thePlayer.motionZ = 0.0;
                                BlockFly.mc.thePlayer.motionX = 0.0;
                                BlockFly.mc.thePlayer.motionY = -0.28f;
                                break;
                            }
                            case Slow: {
                                BlockFly.mc.thePlayer.motionY += -1.0;
                                BlockFly.mc.thePlayer.motionY += 0.42;
                            }
                        }
                    }
                }
            }
        };
        this.onRender2DEvent = e -> {
            MinecraftFontRenderer LFR = BozoWare.getInstance().getFontManager().largeFontRenderer;
            MinecraftFontRenderer LFR2 = BozoWare.getInstance().getFontManager().largeFontRenderer2;
            ScaledResolution SR = e.getScaledResolution();
            this.xPosi = SR.getScaledWidth() / 2 - BlockFly.mc.fontRendererObj.getStringWidth(this.blockCount + " blocks") / 2;
            this.xPosition = RenderUtil.animate(this.xPosi, this.xPosition, 0.05);
            if (this.blockCount != 1) {
                BlockFly.mc.fontRendererObj.drawStringWithShadow("" + this.blockCount + " blocks", (float)this.xPosition, (float)SR.getScaledHeight() / 2.0f + 5.0f, HUD.getInstance().bozoColor);
                BloomUtil.bloom(() -> BlockFly.mc.fontRendererObj.drawStringWithShadow("" + this.blockCount, (float)this.xPosition, (float)((double)((float)SR.getScaledHeight() / 2.0f) + 4.75), HUD.getInstance().bozoColor));
            } else {
                BlockFly.mc.fontRendererObj.drawStringWithShadow("" + this.blockCount + " block", (float)this.xPosition, (float)SR.getScaledHeight() / 2.0f + 5.0f, HUD.getInstance().bozoColor);
                BloomUtil.bloom(() -> BlockFly.mc.fontRendererObj.drawStringWithShadow("" + this.blockCount, (float)this.xPosition, (float)((double)((float)SR.getScaledHeight() / 2.0f) + 4.75), HUD.getInstance().bozoColor));
            }
        };
    }

    private BlockData getBlockData(BlockPos pos) {
        if (this.isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos.add(0, -1, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        BlockPos pos2 = pos.add(0, -1, 0).add(1, 0, 0);
        BlockPos pos3 = pos.add(0, -1, 0).add(0, 0, 1);
        BlockPos pos4 = pos.add(0, -1, 0).add(-1, 0, 0);
        BlockPos pos5 = pos.add(0, -1, 0).add(0, 0, -1);
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        if (this.isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP, null);
        }
        if (this.isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST, null);
        }
        if (this.isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST, null);
        }
        if (this.isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH, null);
        }
        if (this.isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH, null);
        }
        return null;
    }

    private boolean isPosSolid(BlockPos pos) {
        Block block = BlockFly.mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }

    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 9; i < 45; ++i) {
            ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !this.isValidBlock((ItemBlock)stack.getItem())) continue;
            blockCount += stack.stackSize;
        }
        return blockCount;
    }

    public Vec3 getVec3(BlockPos pos, EnumFacing face) {
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.5;
        double z = (double)pos.getZ() + 0.5;
        x += (double)face.getFrontOffsetX() / 2.0;
        z += (double)face.getFrontOffsetZ() / 2.0;
        y += (double)face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += BlockFly.getRandomInRange(0.25, -0.25);
            z += BlockFly.getRandomInRange(0.25, -0.25);
        } else {
            y += BlockFly.getRandomInRange(0.25, -0.25);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += BlockFly.getRandomInRange(0.25, -0.25);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += BlockFly.getRandomInRange(0.25, -0.25);
        }
        return new Vec3(x, y, z);
    }

    public static double getRandomInRange(double min, double max) {
        double shifted;
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        if ((shifted = scaled + min) > max) {
            shifted = max;
        }
        return shifted;
    }

    private boolean isValidBlock(ItemBlock Block2) {
        Block block = Block2.getBlock();
        if (Block2.getBlock() instanceof BlockFlower) {
            return false;
        }
        if (Block2.getBlock() instanceof BlockDoublePlant) {
            return false;
        }
        return !this.invalid.contains(block);
    }

    private int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            ItemStack itemStack = BlockFly.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || !this.isValidBlock((ItemBlock)itemStack.getItem())) continue;
            return i - 36;
        }
        return -1;
    }

    private void getBlocksFromInventory() {
        if (BlockFly.mc.currentScreen instanceof GuiChest) {
            return;
        }
        for (int index = 9; index < 36; ++index) {
            ItemStack stack = BlockFly.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null || !this.isValidBlock((ItemBlock)stack.getItem())) continue;
            BlockFly.mc.playerController.windowClick(0, index, 6, 2, BlockFly.mc.thePlayer);
            break;
        }
    }

    private static enum RotModes {
        Watchdog,
        LookDir,
        Basic,
        Behind;

    }

    private static enum TowerModes {
        NCP,
        Vanilla,
        Slow,
        Watchdog,
        Watchdog2;

    }

    private static enum Modes {
        NCP,
        Expand,
        ReallyBad;

    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face, BlockData blockData) {
            this.position = position;
            this.face = face;
        }
    }
}
