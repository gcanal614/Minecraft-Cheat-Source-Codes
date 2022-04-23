/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.api.annotations.Priority;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.SafeWalkEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.movement.Speed;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.inventory.InventoryUtils;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.timer.TimerUtil;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

@ModuleInfo(label="Scaffold", category=ModuleCategory.MOVEMENT)
public final class Scaffold
extends Module {
    private final Property<Boolean> swingProperty = new Property<Boolean>("Swing", false);
    private final Property<Boolean> safeWalkProperty = new Property<Boolean>("Safe Walk", false);
    public final Property<Boolean> keepYValue = new Property<Boolean>("Keep Y", false);
    public final Property<Boolean> sprintValue = new Property<Boolean>("Sprint", true);
    private final Property<Boolean> towerProperty = new Property<Boolean>("Tower", true);
    public final EnumProperty<TowerMode> towerMode = new EnumProperty<TowerMode>("Tower mode", TowerMode.Taco, this.towerProperty::get);
    private final DoubleProperty jumpMotionValue = new DoubleProperty("Jump Motion", 0.3681288957595825, () -> this.towerMode.get() == TowerMode.Jump, 0.3681288957595825, (double)0.79f, 5.0E-4);
    private final Property<Boolean> baseMoveSpeed = new Property<Boolean>("Base Move Speed", true);
    private final Property<Boolean> moveTowerProperty = new Property<Boolean>("Move Tower", true, this.towerProperty::get);
    private final DoubleProperty maxAngleChangeProperty = new DoubleProperty("Turn Speed", 45.0, 1.0, 180.0, 1.0);
    private final DoubleProperty timerValue = new DoubleProperty("Timer", 1.0, 0.9f, 1.5, 0.05f);
    private final DoubleProperty modifierSpeed = new DoubleProperty("Modifier Speed", 1.0, 0.8f, 1.5, 0.05f);
    private final DoubleProperty baseSpeedValue = new DoubleProperty("Move Speed", (double)0.22f, this.baseMoveSpeed::get, 0.0, (double)0.28f, 0.01);
    private final Property<Boolean> pickerValue = new Property<Boolean>("Picker", true);
    public final EnumProperty<ModeValue> counterModeValue = new EnumProperty<ModeValue>("Counter mode", ModeValue.NUMBER);
    private final Property<Boolean> noBob = new Property<Boolean>("No bob", false);
    private final DoubleProperty blockSlotProperty = new DoubleProperty("Block Slot", 9.0, 1.0, 9.0, 1.0);
    private int blockCount;
    private int originalHotBarSlot;
    private int bestBlockStack;
    private BlockData data;
    private float[] angles;
    private static List<Block> blacklistedBlocks;
    private final TimerUtil sigmaTimer = new TimerUtil();
    double oldY = 0.0;
    private int sigmaY = 0;
    private double jumpGround = 0.0;

    @Listener
    public void onSafeWalkEvent(SafeWalkEvent event) {
        event.setCancelled(this.safeWalkProperty.getValue());
    }

    public Scaffold() {
        blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.ender_chest, Blocks.enchanting_table, Blocks.stone_button, Blocks.wooden_button, Blocks.crafting_table, Blocks.beacon, Blocks.furnace, Blocks.chest, Blocks.trapped_chest, Blocks.iron_bars, Blocks.cactus, Blocks.ladder);
    }

    @Listener(value=Priority.HIGH)
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        Scaffold.mc.getTimer().timerSpeed = ((Double)this.timerValue.get()).floatValue();
        if (this.noBob.get().booleanValue()) {
            Scaffold.mc.thePlayer.distanceWalkedModified = 0.0f;
        }
        if (event.isPre()) {
            this.updateBlockCount();
            this.data = null;
            int n = this.bestBlockStack = this.pickerValue.get() != false ? Scaffold.findBestBlockStack() : InventoryUtils.findAutoBlockBlock();
            if (this.bestBlockStack != -1) {
                BlockPos blockUnder;
                BlockData data2;
                if (this.bestBlockStack < 36) {
                    int blockSlot;
                    boolean override = true;
                    for (blockSlot = 44; blockSlot >= 36; --blockSlot) {
                        ItemStack stack = Wrapper.getStackInSlot(blockSlot);
                        if (InventoryUtils.isValid(stack)) continue;
                        InventoryUtils.windowClick(this.bestBlockStack, blockSlot - 36, InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                        this.bestBlockStack = blockSlot;
                        override = false;
                        break;
                    }
                    if (override) {
                        blockSlot = ((Double)this.blockSlotProperty.getValue()).intValue() - 1;
                        InventoryUtils.windowClick(this.bestBlockStack, blockSlot, InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                        this.bestBlockStack = blockSlot + 36;
                    }
                }
                if ((data2 = this.getBlockData(blockUnder = this.getBlockUnder())) == null) {
                    data2 = this.getBlockData(blockUnder.offset(EnumFacing.DOWN));
                }
                if (data2 != null && this.bestBlockStack >= 36) {
                    if (Scaffold.validateReplaceable(data2) && data2.hitVec != null) {
                        this.angles = this.getRotations(event, data2.hitVec, ((Double)this.maxAngleChangeProperty.get()).floatValue());
                    } else {
                        data2 = null;
                    }
                }
                if (this.angles != null) {
                    if (this.towerProperty.getValue().booleanValue() && Wrapper.getGameSettings().keyBindJump.isKeyDown()) {
                        this.tower();
                    }
                    event.setYaw(this.angles[0]);
                    event.setPitch(this.angles[1]);
                }
                this.data = data2;
            }
        } else if (this.data != null && this.bestBlockStack != -1 && this.bestBlockStack >= 36) {
            int hotBarSlot = this.bestBlockStack - 36;
            if (Wrapper.getPlayer().inventory.currentItem != hotBarSlot) {
                Wrapper.getPlayer().inventory.currentItem = hotBarSlot;
            }
            assert (this.data.hitVec != null);
            if (Wrapper.getPlayerController().onPlayerRightClick(Wrapper.getPlayer(), Wrapper.getWorld(), Wrapper.getPlayer().getCurrentEquippedItem(), this.data.pos, this.data.face, this.data.hitVec)) {
                if (this.towerProperty.getValue().booleanValue() && Wrapper.getGameSettings().keyBindJump.isKeyDown() && MovementUtils.isMoving()) {
                    if (this.towerMode.get() == TowerMode.Taco) {
                        double n = event.getPosY() % 1.0;
                        double n2 = this.down(event.getPosY());
                        List<Double> list = Arrays.asList(0.41999998688698, 0.7531999805212);
                        if (n > 0.419 && n < 0.753) {
                            event.setPosY(n2 + list.get(0));
                        } else if (n > 0.753) {
                            event.setPosY(n2 + list.get(1));
                        } else {
                            event.setPosY(n2);
                            event.setOnGround(true);
                        }
                        if (!MovementUtils.isMove()) {
                            Scaffold.mc.thePlayer.motionZ = 0.0;
                            Scaffold.mc.thePlayer.motionX = 0.0;
                            event.setPosX(event.getPosX() + (Scaffold.mc.thePlayer.ticksExisted % 2 == 0 ? ThreadLocalRandom.current().nextDouble(0.06, 0.0625) : -ThreadLocalRandom.current().nextDouble(0.06, 0.0625)));
                            event.setPosZ(event.getPosZ() + (Scaffold.mc.thePlayer.ticksExisted % 2 != 0 ? ThreadLocalRandom.current().nextDouble(0.06, 0.0625) : -ThreadLocalRandom.current().nextDouble(0.06, 0.0625)));
                        }
                    }
                    event.setPitch(90.0f);
                }
                if (this.swingProperty.getValue().booleanValue()) {
                    Wrapper.getPlayer().swingItem();
                } else {
                    Wrapper.sendPacket(new C0APacketAnimation());
                }
                if (Scaffold.mc.thePlayer.onGround) {
                    Double modifier = (Double)this.modifierSpeed.get();
                    Scaffold.mc.thePlayer.motionX *= modifier.doubleValue();
                    Scaffold.mc.thePlayer.motionZ *= modifier.doubleValue();
                }
            }
        }
        if (this.keepYValue.get().booleanValue()) {
            if (!MovementUtils.isMove() && Scaffold.mc.gameSettings.keyBindJump.isKeyDown() || Scaffold.mc.thePlayer.isCollidedVertically || Scaffold.mc.thePlayer.onGround) {
                this.sigmaY = MathHelper.floor_double(Scaffold.mc.thePlayer.posY);
            }
        } else {
            this.sigmaY = MathHelper.floor_double(Scaffold.mc.thePlayer.posY);
        }
        if (this.keepYValue.get().booleanValue() && this.oldY >= Scaffold.mc.thePlayer.posY) {
            Scaffold.mc.thePlayer.jump();
        }
    }

    @Listener
    private void onMoveEntityEvent(MoveEntityEvent e) {
        if (this.baseMoveSpeed.get().booleanValue() && MovementUtils.isMoving() && !((Double)this.baseSpeedValue.get()).equals(0.0) && !Tifality.INSTANCE.getModuleManager().getModule(Speed.class).isEnabled()) {
            MovementUtils.setMotion(e, (Double)this.baseSpeedValue.get());
        }
    }

    private static int findBestBlockStack() {
        int bestSlot = -1;
        int blockCount = -1;
        for (int i = 44; i >= 9; --i) {
            ItemStack stack = Wrapper.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !InventoryUtils.isGoodBlockStack(stack) || stack.stackSize <= blockCount) continue;
            bestSlot = i;
            blockCount = stack.stackSize;
        }
        return bestSlot;
    }

    private BlockPos getBlockUnder() {
        return this.keepYValue.get() != false ? new BlockPos(Scaffold.mc.thePlayer.posX, (double)this.sigmaY - 1.0, Scaffold.mc.thePlayer.posZ) : (Scaffold.mc.thePlayer.posY == Scaffold.mc.thePlayer.posY + 0.5 ? new BlockPos(Scaffold.mc.thePlayer) : new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY, Scaffold.mc.thePlayer.posZ).down());
    }

    private float[] getRotations(UpdatePositionEvent ev, Vec3 hitVec, float aimSpeed) {
        EntityPlayerSP entity = Wrapper.getPlayer();
        double x = hitVec.xCoord - entity.posX;
        double y = hitVec.yCoord - (entity.posY + (double)entity.getEyeHeight());
        double z = hitVec.zCoord - entity.posZ;
        double fDist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = Scaffold.interpolateRotation(ev.getPrevYaw(), (float)(StrictMath.atan2(z, x) * 180.0 / Math.PI) - 90.0f, aimSpeed);
        float pitch = Scaffold.interpolateRotation(ev.getPrevPitch(), (float)(-(StrictMath.atan2(y, fDist) * 180.0 / Math.PI)), aimSpeed);
        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    private static float interpolateRotation(float prev, float now, float maxTurn) {
        float var4 = MathHelper.wrapAngleTo180_float(now - prev);
        if (var4 > maxTurn) {
            var4 = maxTurn;
        }
        if (var4 < -maxTurn) {
            var4 = -maxTurn;
        }
        return prev + var4;
    }

    private static boolean validateReplaceable(BlockData data2) {
        BlockPos pos = data2.pos.offset(data2.face);
        WorldClient world = Wrapper.getWorld();
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    private boolean isPosSolid(Block block) {
        return !blacklistedBlocks.contains(block) && (block.getMaterial().isSolid() || !block.isTranslucent() || block.isVisuallyOpaque() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }

    private BlockData getBlockData(BlockPos pos) {
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos add = pos.add(0, 0, 0);
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add2 = pos.add(1, 0, 0);
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add3 = pos.add(0, 0, -1);
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add4 = pos.add(0, 0, 1);
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(1, 1, 0)).getBlock())) {
            return new BlockData(add.add(1, 1, 0), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add.add(-1, 2, -1)).getBlock())) {
            return new BlockData(add.add(-1, 2, -1), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(-2, 1, 0)).getBlock())) {
            return new BlockData(add2.add(-2, 1, 0), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add2.add(0, 2, 1)).getBlock())) {
            return new BlockData(add2.add(0, 2, 1), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(0, 1, 2)).getBlock())) {
            return new BlockData(add3.add(0, 1, 2), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add3.add(1, 2, 0)).getBlock())) {
            return new BlockData(add3.add(1, 2, 0), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(0, 1, -2)).getBlock())) {
            return new BlockData(add4.add(0, 1, -2), EnumFacing.DOWN);
        }
        if (this.isPosSolid(Scaffold.mc.theWorld.getBlockState(add4.add(-1, 2, 0)).getBlock())) {
            return new BlockData(add4.add(-1, 2, 0), EnumFacing.DOWN);
        }
        return null;
    }

    @Override
    public void onEnable() {
        this.blockCount = 0;
        this.originalHotBarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.oldY = Scaffold.mc.thePlayer.posY;
        if (this.keepYValue.get().booleanValue()) {
            if (Scaffold.mc.thePlayer.onGround) {
                Scaffold.mc.thePlayer.jump();
            }
            this.sigmaTimer.reset();
        }
    }

    @Override
    public void onDisable() {
        this.angles = null;
        Wrapper.getPlayer().inventory.currentItem = this.originalHotBarSlot;
    }

    public boolean isRotating() {
        return this.angles != null;
    }

    private void updateBlockCount() {
        this.blockCount = 0;
        for (int i = 9; i < 45; ++i) {
            ItemStack stack = Wrapper.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !InventoryUtils.isGoodBlockStack(stack)) continue;
            this.blockCount += stack.stackSize;
        }
    }

    @Listener
    public void onRender2DEvent(Render2DEvent event) {
        int c = Colors.getColor(255, 0, 0, 150);
        if (this.blockCount >= 64 && 128 > this.blockCount) {
            c = Colors.getColor(255, 255, 0, 150);
        } else if (this.blockCount >= 128) {
            c = Colors.getColor(0, 255, 0, 150);
        }
        if (this.counterModeValue.get() == ModeValue.RECT) {
            LockedResolution resolution = event.getResolution();
            float x = (float)resolution.getWidth() / 2.0f;
            float y = (float)resolution.getHeight() / 2.0f + 15.0f;
            float percentage = Math.min(1.0f, (float)this.blockCount / 128.0f);
            float width = 80.0f;
            float half = width / 2.0f;
            int color = RenderingUtils.getColorFromPercentage(percentage);
            Gui.drawRect(x - half - 0.5f, y - 2.0f, x + half + 0.5f, y + 2.0f, 0x78000000);
            Gui.drawGradientRect(x - half, y - 1.5f, x - half + width * percentage, y + 1.5f, color, new Color(color).darker().getRGB());
        } else {
            ScaledResolution res = new ScaledResolution(mc);
            String info = "" + this.blockCount;
            GlStateManager.enableBlend();
            RenderingUtils.drawOutlinedString(info, (float)res.getScaledWidth() / 2.0f - (float)Scaffold.mc.fontRendererObj.getStringWidth(info) / 2.0f, (float)res.getScaledHeight() / 2.0f - 25.0f, c, new Color(0, 0, 0, 210).getRGB());
            GlStateManager.disableBlend();
        }
    }

    private void tower() {
        if (this.towerMode.get() == TowerMode.Jump) {
            this.fakeJump();
            Scaffold.mc.thePlayer.motionY = (Double)this.jumpMotionValue.get();
        }
        if (this.towerMode.get() == TowerMode.Motion && Scaffold.mc.thePlayer.ticksExisted % (this.moveTowerProperty.get() != false && MovementUtils.isMove() ? 1 : 8) == 0) {
            if (Scaffold.mc.thePlayer.onGround) {
                this.fakeJump();
                this.jumpGround = Scaffold.mc.thePlayer.posY;
                Scaffold.mc.thePlayer.motionY = 0.3681289;
            }
            if (Scaffold.mc.thePlayer.posY > this.jumpGround + 1.0) {
                this.fakeJump();
                Scaffold.mc.thePlayer.setPosition(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY, Scaffold.mc.thePlayer.posZ);
                Scaffold.mc.thePlayer.motionY = 0.3681289;
                this.jumpGround = Scaffold.mc.thePlayer.posY;
            }
            if (!MovementUtils.isMove()) {
                Scaffold.mc.thePlayer.motionX = 0.0;
                Scaffold.mc.thePlayer.motionZ = 0.0;
                Scaffold.mc.thePlayer.jumpMovementFactor = 0.0f;
            }
        }
        if (this.towerMode.get() == TowerMode.Taco) {
            if (!MovementUtils.isMoving()) {
                Scaffold.mc.thePlayer.motionX = 0.0;
                Scaffold.mc.thePlayer.motionZ = 0.0;
                if (Scaffold.mc.thePlayer.onGround) {
                    Scaffold.mc.thePlayer.setPosition((double)this.down(Scaffold.mc.thePlayer.posX) + 0.5, Scaffold.mc.thePlayer.posY, (double)this.down(Scaffold.mc.thePlayer.posZ) + 0.5);
                }
            }
            if (MovementUtils.isOnGround(0.76) && !MovementUtils.isOnGround(0.75) && Scaffold.mc.thePlayer.motionY > 0.23 && Scaffold.mc.thePlayer.motionY < 0.25) {
                Scaffold.mc.thePlayer.motionY = (double)Math.round(Scaffold.mc.thePlayer.posY) - Scaffold.mc.thePlayer.posY;
            }
            if (MovementUtils.isOnGround(1.0E-4)) {
                Scaffold.mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.41999998688698, true);
            } else if (Scaffold.mc.thePlayer.posY >= (double)Math.round(Scaffold.mc.thePlayer.posY) - 1.0E-4 && Scaffold.mc.thePlayer.posY <= (double)Math.round(Scaffold.mc.thePlayer.posY) + 1.0E-4 && !Scaffold.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Scaffold.mc.thePlayer.motionY = 0.0;
            }
        }
    }

    private void fakeJump() {
        Scaffold.mc.thePlayer.isAirBorne = true;
        Scaffold.mc.thePlayer.triggerAchievement(StatList.jumpStat);
    }

    private int down(double n) {
        int n2 = (int)n;
        try {
            if (n < (double)n2) {
                return n2 - 1;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return n2;
    }

    public static enum Rotation {
        New,
        Old,
        Dev;

    }

    public static enum TowerMode {
        Taco,
        Motion,
        Jump;

    }

    public static enum ModeValue {
        NUMBER,
        RECT;

    }

    private static class BlockData {
        private final BlockPos pos;
        private final EnumFacing face;
        private final Vec3 hitVec;

        public BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
            this.hitVec = this.getHitVec();
        }

        private Vec3 getHitVec() {
            Vec3i directionVec = this.face.getDirectionVec();
            double x = (double)directionVec.getX() * 0.5;
            double z = (double)directionVec.getZ() * 0.5;
            if (this.face.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) {
                x = -x;
                z = -z;
            }
            Vec3 hitVec = new Vec3(this.pos).addVector(x + z, (double)directionVec.getY() * 0.5, x + z);
            Vec3 src = Wrapper.getPlayer().getPositionEyes(1.0f);
            MovingObjectPosition obj = Wrapper.getWorld().rayTraceBlocks(src, hitVec, false, false, true);
            if (obj == null || obj.hitVec == null || obj.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                return null;
            }
            if (this.face != EnumFacing.DOWN && this.face != EnumFacing.UP) {
                obj.hitVec = obj.hitVec.addVector(0.0, -0.2, 0.0);
            }
            return obj.hitVec;
        }
    }
}

