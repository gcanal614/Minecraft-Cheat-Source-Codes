// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import java.util.Random;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.item.ItemHoe;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.properties.IProperty;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import bozoware.base.BozoWare;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.material.Material;
import bozoware.base.util.Wrapper;
import bozoware.impl.module.visual.HUD;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Skyblock Macros", moduleCategory = ModuleCategory.WORLD)
public class royaltyisabozo extends Module
{
    private BooleanProperty nuke;
    private BooleanProperty placer;
    private BooleanProperty autoMove;
    public ValueProperty<Integer> bpsValue;
    private ValueProperty<Integer> radiusValue;
    boolean isThreadRunning;
    private final ArrayList<BlockPos> blocks;
    private final ArrayList<BlockPos> brokenBlocks;
    public static String prevDirection;
    public static String[] directions;
    public static String direction;
    BlockPos front;
    BlockPos left;
    BlockPos right;
    BlockPos behind;
    public Block currentBlock;
    TimerUtil timer;
    static int bruh;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    
    public royaltyisabozo() {
        this.nuke = new BooleanProperty("Nuker", true, this);
        this.placer = new BooleanProperty("Placer", false, this);
        this.autoMove = new BooleanProperty("Auto Move", true, this);
        this.bpsValue = new ValueProperty<Integer>("Ticks", 8, 1, 40, this);
        this.radiusValue = new ValueProperty<Integer>("Nuker Radius", 8, 1, 20, this);
        this.blocks = new ArrayList<BlockPos>();
        this.brokenBlocks = new ArrayList<BlockPos>();
        this.timer = new TimerUtil();
        this.onModuleEnabled = (() -> {
            if (!this.isThreadRunning && this.autoMove.getPropertyValue()) {
                this.runThread();
                this.isThreadRunning = true;
            }
            royaltyisabozo.bruh = (int)System.currentTimeMillis();
            return;
        });
        this.onModuleDisabled = (() -> {
            royaltyisabozo.mc.gameSettings.keyBindForward.pressed = false;
            this.isThreadRunning = false;
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (!this.isThreadRunning && this.autoMove.getPropertyValue()) {
                this.runThread();
                this.isThreadRunning = true;
            }
            if (this.autoMove.getPropertyValue()) {
                royaltyisabozo.directions = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
                royaltyisabozo.direction = royaltyisabozo.directions[HUD.wrapAngleToDirection(royaltyisabozo.mc.thePlayer.rotationYaw, royaltyisabozo.directions.length)];
                royaltyisabozo.directions = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
                royaltyisabozo.prevDirection = royaltyisabozo.directions[HUD.wrapAngleToDirection(royaltyisabozo.mc.thePlayer.rotationYaw - 180.0f, royaltyisabozo.directions.length)];
                if (royaltyisabozo.direction == "N") {
                    if (royaltyisabozo.mc.thePlayer.rotationYaw % 90.0f != 0.0f) {
                        royaltyisabozo.mc.thePlayer.rotationYaw = 180.0f;
                    }
                    this.front = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ - 0.75);
                    this.behind = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ + 1.0);
                    this.left = new BlockPos(royaltyisabozo.mc.thePlayer.posX - 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.right = new BlockPos(royaltyisabozo.mc.thePlayer.posX + 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                }
                if (royaltyisabozo.direction == "S") {
                    if (royaltyisabozo.mc.thePlayer.rotationYaw % 90.0f != 0.0f) {
                        royaltyisabozo.mc.thePlayer.rotationYaw = 0.0f;
                    }
                    this.front = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ + 0.75);
                    this.behind = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ - 1.0);
                    this.left = new BlockPos(royaltyisabozo.mc.thePlayer.posX + 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.right = new BlockPos(royaltyisabozo.mc.thePlayer.posX - 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                }
                if (royaltyisabozo.direction == "W") {
                    if (royaltyisabozo.mc.thePlayer.rotationYaw % 90.0f != 0.0f) {
                        royaltyisabozo.mc.thePlayer.rotationYaw = 90.0f;
                    }
                    this.front = new BlockPos(royaltyisabozo.mc.thePlayer.posX - 0.75, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.behind = new BlockPos(royaltyisabozo.mc.thePlayer.posX + 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.left = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ + 1.0);
                    this.right = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ - 1.0);
                }
                if (royaltyisabozo.direction == "E") {
                    if (royaltyisabozo.mc.thePlayer.rotationYaw % 90.0f != 0.0f) {
                        royaltyisabozo.mc.thePlayer.rotationYaw = -90.0f;
                    }
                    this.front = new BlockPos(royaltyisabozo.mc.thePlayer.posX + 0.75, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.behind = new BlockPos(royaltyisabozo.mc.thePlayer.posX - 1.0, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
                    this.left = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ - 1.0);
                    this.right = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ + 1.0);
                }
                if (Wrapper.getBlock(this.front).getMaterial() == Material.air) {
                    royaltyisabozo.mc.gameSettings.keyBindForward.pressed = true;
                    this.timer.reset();
                }
                else {
                    royaltyisabozo.mc.gameSettings.keyBindForward.pressed = true;
                    if (royaltyisabozo.prevDirection == "S" && royaltyisabozo.direction == "N") {
                        if (Wrapper.getBlock(this.left).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 90.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = -90.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() != Material.air && Wrapper.getBlock(this.left).getMaterial() != Material.air && Wrapper.getBlock(this.behind).getMaterial() == Material.air && Wrapper.getBlock(this.front).isFullBlock()) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 0.0f;
                        }
                    }
                    if (royaltyisabozo.direction == "W") {
                        if (Wrapper.getBlock(this.left).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 0.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 180.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() != Material.air && Wrapper.getBlock(this.left).getMaterial() != Material.air && Wrapper.getBlock(this.behind).getMaterial() == Material.air && Wrapper.getBlock(this.front).isFullBlock()) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = -90.0f;
                        }
                    }
                    if (royaltyisabozo.direction == "E") {
                        if (Wrapper.getBlock(this.left).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 180.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 0.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() != Material.air && Wrapper.getBlock(this.left).getMaterial() != Material.air && Wrapper.getBlock(this.behind).getMaterial() == Material.air && Wrapper.getBlock(this.front).isFullBlock()) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 90.0f;
                        }
                    }
                    if (royaltyisabozo.direction == "S") {
                        if (Wrapper.getBlock(this.left).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = -90.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() == Material.air) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 90.0f;
                        }
                        if (Wrapper.getBlock(this.right).getMaterial() != Material.air && Wrapper.getBlock(this.left).getMaterial() != Material.air && Wrapper.getBlock(this.behind).getMaterial() == Material.air && Wrapper.getBlock(this.front).isFullBlock()) {
                            royaltyisabozo.mc.thePlayer.rotationYaw = 180.0f;
                        }
                    }
                }
            }
            return;
        });
        final int diff;
        this.onRender2DEvent = (e -> {
            diff = (int)System.currentTimeMillis() - royaltyisabozo.bruh;
            royaltyisabozo.mc.fontRendererObj.drawStringWithShadow("Session Time: " + diff / 3600000 % 24 + "h " + diff / 60000 % 60 + "m " + diff / 1000 % 60 + "s", 8.0f, 40.0f, -1);
            royaltyisabozo.mc.fontRendererObj.drawStringWithShadow("Estimated Profit: " + this.getAverageWartPrice() + " coins", 8.0f, 50.0f, -1);
        });
    }
    
    private EnumFacing getFacingDirection(final BlockPos pos) {
        EnumFacing direction = null;
        if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.UP;
        }
        else if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.DOWN;
        }
        else if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.EAST;
        }
        else if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.WEST;
        }
        else if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.SOUTH;
        }
        else if (!Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.NORTH;
        }
        return direction;
    }
    
    public void eraseBlock(final BlockPos pos, final EnumFacing facing) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
        royaltyisabozo.mc.thePlayer.swingItem();
    }
    
    public void placeBlock(final BlockPos pos, final EnumFacing facing) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(pos, 0, this.getNetherwartStack(), 0.0f, 0.0f, 0.0f));
    }
    
    private ItemStack getNetherwartStack() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = royaltyisabozo.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && ((ItemBlock)itemStack.getItem()).getBlock() instanceof BlockNetherWart) {
                return itemStack;
            }
        }
        return null;
    }
    
    private int getAverageWartPrice() {
        int wart = 0;
        int enchantedWart = 0;
        int mutantWart = 0;
        for (int i = 44; i >= 9; --i) {
            final Slot slot = royaltyisabozo.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot.getHasStack()) {
                final ItemStack item = slot.getStack();
                if (item.getItem() != null) {
                    if (item.getItem() == Item.getItemById(372)) {
                        if (item.isItemEnchanted()) {
                            enchantedWart += item.stackSize;
                        }
                        else {
                            wart += item.stackSize;
                        }
                    }
                    if (item.getDisplayName().contains("Mutant Nether Wart")) {
                        mutantWart += item.stackSize;
                    }
                }
            }
        }
        return (int)(wart * 3.6) + enchantedWart * 316 + mutantWart * 50000;
    }
    
    public static royaltyisabozo getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(royaltyisabozo.class);
    }
    
    private int getBlockAge(final IBlockState blockState) {
        for (final Map.Entry<IProperty, Comparable> entry : blockState.getProperties().entrySet()) {
            if (entry.getKey().getName().equals("age")) {
                return (int)entry.getValue();
            }
        }
        return -1;
    }
    
    public void runThread() {
        new Thread() {
            final TimerUtil breakTimer = new TimerUtil();
            
            @Override
            public void run() {
                while (this.isAlive()) {
                    final double pauseTime = 1.0 / royaltyisabozo.this.bpsValue.getPropertyValue() * 1000.0;
                    royaltyisabozo.this.updateBlocks();
                    if (this.breakTimer.hasReached((long)pauseTime) && royaltyisabozo.this.blocks.size() >= 1 && royaltyisabozo.mc.thePlayer.getHeldItem() != null && royaltyisabozo.mc.thePlayer.getHeldItem().getItem() instanceof ItemHoe) {
                        royaltyisabozo.this.breakBlock();
                        this.breakTimer.reset();
                    }
                }
            }
        }.start();
    }
    
    public void breakBlock() {
        final BlockPos blockPos = this.getAndRemove();
        if (blockPos == null) {
            return;
        }
        final IBlockState blockState = royaltyisabozo.mc.theWorld.getBlockState(blockPos);
        royaltyisabozo.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
        this.brokenBlocks.add(blockPos);
        Wrapper.sendPacketDirect(new C0APacketAnimation());
    }
    
    private BlockPos getAndRemove() {
        try {
            return this.blocks.remove(this.getNextIndex());
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    private int getNextIndex() {
        if (this.blocks.size() > 0) {
            return new Random().nextInt(this.blocks.size());
        }
        return 0;
    }
    
    private BlockPos getPos() {
        try {
            return this.blocks.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public void updateBlocks() {
        this.blocks.clear();
        final BlockPos playerPos = new BlockPos(royaltyisabozo.mc.thePlayer.posX, royaltyisabozo.mc.thePlayer.posY, royaltyisabozo.mc.thePlayer.posZ);
        final BlockPos blockPos2 = playerPos.add(-4, -3, -4);
        final BlockPos blockPos3 = playerPos.add(4, 3, 4);
        BlockPos.getAllInBox(blockPos2, blockPos3).forEach(blockPos -> {
            if (royaltyisabozo.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockNetherWart && !this.brokenBlocks.contains(blockPos) && this.getBlockAge(royaltyisabozo.mc.theWorld.getBlockState(blockPos)) == this.getMaxBlockAge(royaltyisabozo.mc.theWorld.getBlockState(blockPos)) && royaltyisabozo.mc.thePlayer.getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ()) <= 5.8) {
                this.blocks.add(blockPos);
            }
        });
    }
    
    private int getMaxBlockAge(final IBlockState blockState) {
        for (final Map.Entry<IProperty, Comparable> entry : blockState.getProperties().entrySet()) {
            if (entry.getKey().getName().equals("age")) {
                final int maxValue = Integer.MAX_VALUE;
                final ArrayList<Integer> values = new ArrayList<Integer>();
                entry.getKey().getAllowedValues().forEach(value -> values.add(Integer.parseInt(value.toString())));
                return values.get(values.size() - 1);
            }
        }
        return Integer.MAX_VALUE;
    }
    
    static {
        royaltyisabozo.bruh = (int)System.currentTimeMillis();
    }
}
