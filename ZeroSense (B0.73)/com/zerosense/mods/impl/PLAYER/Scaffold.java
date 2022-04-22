package com.zerosense.mods.impl.PLAYER; // why its not world XD?



import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventReceivePacket;
import com.zerosense.Events.impl.RotationEvent;
import com.zerosense.Settings.BooleanSetting;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Settings.NumberSetting;
import com.zerosense.Utils.*;
import com.zerosense.Utils.block.BlockUtils;
import com.zerosense.mods.Module;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Scaffold extends Module {

    public float lastYaw, lastPitch;
    private Timer boosterTimer = new Timer();

    int dorotations = 1;

    private BlockUtils2.BlockData blockData;

    boolean cooldown = false;
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private TimeHelper timer = new TimeHelper();
    private Timer timer2 = new Timer();

    double oldY;

    public static boolean isEnabled = false;

    public static float yaw;

    private int delay;

    int currentItem;

    public float pitch;

    int delayplace = 0;

    int rotating = 0;

    public long LastBuild;

    public static Minecraft mc = Minecraft.getMinecraft();

    int currentSlot;


    public NumberSetting value = new NumberSetting("Value", 0.0, 0.0, 300, 10);
    public BooleanSetting eagle = new BooleanSetting("Eagle", false);
    public BooleanSetting swing = new BooleanSetting("NoSwing", false);
    public BooleanSetting SafeWalk = new BooleanSetting("SafeWalk", true);
    public BooleanSetting tower = new BooleanSetting("Tower", true);
    public BooleanSetting safewalk2 = new BooleanSetting("Safewalk", true);
    public NumberSetting timerspeed = new NumberSetting("Timer", 1.0D, 4.0D, 0.2D, 1.0D);
    public BooleanSetting sneakOnPlace = new BooleanSetting("Sneak on place", false);
    public BooleanSetting noSprint = new BooleanSetting("No sprint", false);
    public BooleanSetting rotationsKeep = new BooleanSetting("Rotations Keep", false);

    public BooleanSetting timerBoost = new BooleanSetting("Timer Boost", true);
    public BooleanSetting stopMovingOnPlace = new BooleanSetting("Stop Moving on place", true);
    public ModeSetting mode = new ModeSetting("Mode", "v1", "v2", "v3","v4");

    public Scaffold() {
        super("BlockFly", Keyboard.KEY_C, Category.MOVEMENT);
        this.addSettings( eagle, swing, tower, safewalk2, timerBoost, timerspeed, sneakOnPlace, noSprint, stopMovingOnPlace, mode);
    }

    @Override
    public void onEnable() {
        if(mode.is("v2")){
            isEnabled = true;
            this.boosterTimer.reset();
            this.oldY = mc.thePlayer.posY;
        }
    }

    @Override
    public void onDisable() {
        if(mode.is("v2")){
            isEnabled = false;
            mc.gameSettings.keyBindSneak.pressed = false;
            mc.timer.timerSpeed = 1.0F;
            this.delayplace = 0;
        }
        if(mode.is("v1"))
            setSneaking(false);
    }

    public void onEvent(Event e){
      //  setDisplayname(String.valueOf((new StringBuilder("BlockFly§7 [")).append(this.mode)) + "]");// send me this setdisplay XD
        if(mode.is("v1")) {
            if (eagle.isToggled()) {
                if (rotated) {
                    setSneaking(true);
                } else {
                    setSneaking(false);
                }
                if(noSprint.isToggled()){
                    mc.thePlayer.setSprinting(false);
                }
            }
            if (e instanceof EventMotion) {
                if (e.isPre()) {
                    rotated = false;
                    currentPos = null;
                    currentFacing = null;

                    BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                    if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                        setBlockAndFacing(pos);

                        if (currentPos != null) {
                            float facing[] = BlockUtils.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);
                            if (tower.isToggled()) {
                                if (mc.gameSettings.keyBindJump.pressed)
                                    mc.thePlayer.motionY = 0.03000D;
                            }
                            float yaw = facing[0];
                            float pitch = Math.min(90, facing[1] + 9);

                            if (!mc.gameSettings.keyBindSneak.pressed) {
                                e.setCancelled(true);
                            }

                            rotated = true;
                            e.setYaw(yaw);
                            e.setPitch(pitch);
                        }
                    }
                }// idk is this scaffold still works
                if (e.isPost()) {
                    if (currentPos != null) {
                        if (timer.hasTimeReached((long) value.getValue())) {
                            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                                if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), currentPos, currentFacing, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()))) {
                                    timer.reset();
                                    if (tower.isToggled()) {
                                        if (mc.gameSettings.keyBindJump.pressed)
                                            mc.thePlayer.motionY = 0.4196D;
                                    }
                                    if (e instanceof EventReceivePacket) {
                                        if (!mc.gameSettings.keyBindSneak.pressed) {
                                            e.setCancelled(true);
                                        }
                                    }
                                    if (swing.isToggled()) {
                                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                                    } else
                                        mc.thePlayer.swingItem();
                                }
                            }
                        }
                    }
                }
            }
        }
        if(mode.is("v2")) {
            if (e instanceof EventMotion) {
                if (e.isPre()) {
                    EventMotion eventMotion1 = (EventMotion)e;
                    mc.thePlayer.rotationYawHead = ((EventMotion)e).getYaw();
                    mc.thePlayer.renderYawOffset = ((EventMotion)e).getYaw();
                }
                if (this.timerBoost.isToggled())
                    if (!this.boosterTimer.hasTimeElapsed(1500L, false)) {
                        mc.timer.timerSpeed = 1.75F;
                    } else {
                        mc.timer.timerSpeed = 1.0F;
                    }
                if(this.rotationsKeep.isToggled()){
                    //RotationEvent.setYaw(lastYaw);
                  //  RotationEvent.setPitch(lastPitch);
                  // mc.thePlayer.prevRotationYawHead = mc.thePlayer.rotationYawHead = RotationEvent.getYaw();
                }
                if (this.noSprint.isToggled())
                    mc.thePlayer.setSprinting(false);
                if (this.sneakOnPlace.isToggled() && this.timer.hasTimeElapsed(50L))
                    mc.gameSettings.keyBindSneak.pressed = false;
                if (!this.sneakOnPlace.isToggled() && mc.gameSettings.keyBindSneak.pressed)
                    mc.gameSettings.keyBindSneak.pressed = false;
                EventMotion eventMotion = (EventMotion)e;
                if (mc.thePlayer.getCurrentEquippedItem() == null)
                    return;
                if (mc.thePlayer.getCurrentEquippedItem().getItem() == Item.getItemById(0))
                    return;
                if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))
                    return;
                if (e.isPre()) {
                    mc.timer.timerSpeed = (float)this.timerspeed.getValue();
                    if (this.sneakOnPlace.isToggled())
                        mc.thePlayer.setSneaking(true);
                    if (mc.theWorld == null || mc.thePlayer == null)
                        return;
                    try {
                        this.blockData = null;
                        if (mc.thePlayer.getHeldItem() != null) {
                            updateHotbarHypixel();
                            BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                            this.blockData = getBlockData(blockPos);
                            float[] arrayOfFloat = RotationUtils.getRotationFromPosition(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ());
                            mc.thePlayer.rotationYawHead = eventMotion.getYaw();
                            mc.thePlayer.renderYawOffset = eventMotion.getYaw();
                            eventMotion.setYaw(arrayOfFloat[0]);
                            eventMotion.setPitch(85.0F);
                            if (mc.gameSettings.keyBindJump.isKeyDown()); // or pressed xD
                            if (this.blockData == null)
                                return;
                            if (mc.theWorld == null || mc.thePlayer == null)
                                return;
                            Random random = new Random();
                            if (this.timer.hasTimeElapsed((1 + random.nextInt(9))) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                                mc.thePlayer.swingItem();
                                if (this.stopMovingOnPlace.isToggled()) {
                                    mc.thePlayer.motionX = 0.0D;
                                    mc.thePlayer.motionZ = 0.0D;
                                    mc.thePlayer.moveForward = 0.0F;
                                }
                                eventMotion.setYaw(arrayOfFloat[0]);
                                eventMotion.setPitch(85.0F);
                                if (this.sneakOnPlace.isToggled())
                                    mc.gameSettings.keyBindSneak.pressed = true;
                                mc.thePlayer.rotationYawHead = eventMotion.getYaw();
                                mc.thePlayer.renderYawOffset = eventMotion.getYaw();
                            }
                        }
                    } catch (Exception exception) {}
                }
                if (e.isPost()) {
                    if (this.blockData == null)
                        return;
                    if (mc.theWorld == null || mc.thePlayer == null)
                        return;
                    Random random = new Random();
                    if (this.timer.hasTimeElapsed((1 + random.nextInt(9))) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                        if (this.sneakOnPlace.isToggled())
                            mc.gameSettings.keyBindSneak.pressed = true;
                        mc.thePlayer.swingItem();
                        this.LastBuild = System.currentTimeMillis();
                    }
                }
            }
        }
        if(this.mode.is("v3")) {
            if (e instanceof EventMotion) {

                EventMotion event = (EventMotion) e;

                mc.thePlayer.setSprinting(false);
                double x = mc.thePlayer.posX;
                double z = mc.thePlayer.posZ;

                if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock().getMaterial().isReplaceable()) {
                    x = mc.thePlayer.posX;
                    z = mc.thePlayer.posZ;
                }
                final BlockPos underPos = new BlockPos(x, mc.thePlayer.posY - 1, z);
                final BlockUtils2.BlockData data = getBlockData(underPos);

                if (getBlockSlot() == -1) {
                    return;
                }

                //Tower
                if (e.isPre() && getBlockSlot() != -1 && mc.gameSettings.keyBindJump.pressed == true && !MovementUtil.isMoving() && tower.isToggled()) {
                    MovementUtil.setMotion(0);
                    if (mc.thePlayer.onGround) {
                        if (MovementUtil.isOnGround(0.76) && !MovementUtil.isOnGround(0.75) && mc.thePlayer.motionY > 0.23 && mc.thePlayer.motionY < 0.25) {
                            mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
                        }
                        if (MovementUtil.isOnGround(1.0E-4)) {
                            mc.thePlayer.motionY = 0.41999998688697815;
                            if (timer2.hasTimeElapsed(1500, false)) {
                                mc.thePlayer.motionY = -0.28;
                                timer2.reset();
                            }
                        } else if (mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4 && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4) {
                            mc.thePlayer.motionY = 0.0;
                        }
                    } else if (mc.theWorld.getBlockState(underPos).getBlock().getMaterial().isReplaceable() && data != null) {
                        mc.thePlayer.motionY = 0.41955;
                    }
                }
                if (mc.theWorld.getBlockState(underPos).getBlock().getMaterial().isReplaceable() && data != null) {
                    if (e.isPre()) {

                        event.setYaw(mc.thePlayer.rotationYaw + RandomUtils.nextFloat(178, 179));
                        event.setPitch(70f);
                        RenderUtils.setCustomYaw(event.yaw);
                        RenderUtils.setCustomPitch(event.pitch);

                        if (data.face == EnumFacing.UP) {
                            mc.timer.timerSpeed = 1;
                            event.setPitch(90f);
                            RenderUtils.setCustomPitch(event.pitch);
                        }

                        if (!mc.gameSettings.keyBindJump.pressed && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                            mc.thePlayer.onGround = false;
                        }

                    } else if (getBlockSlot() != -1) {

                        if (!mc.gameSettings.keyBindJump.pressed && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                            mc.thePlayer.onGround = false;
                        }

                        mc.thePlayer.inventory.currentItem = getBlockSlot();

                        mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), data.position, data.face, getVec3(data.position, data.face));

                        if (swing.isToggled()) {
                            mc.thePlayer.swingItem();
                        } else {
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        }
                    }
                } else {
                    event.setYaw(mc.thePlayer.rotationYaw + RandomUtils.nextFloat(178, 179));
                    event.setPitch(70f);
                    RenderUtils.setCustomYaw(event.yaw);
                    RenderUtils.setCustomPitch(event.pitch);
                }
            }
        }

        if(mode.is("v4")){
                if (e instanceof EventMotion) {
                    if (e.isPre()) {
                        EventMotion eventMotion1 = (EventMotion)e;
                        mc.thePlayer.rotationYawHead = ((EventMotion)e).getYaw();
                        mc.thePlayer.renderYawOffset = ((EventMotion)e).getYaw();
                    }
                    if (this.timerBoost.isToggled())
                        if (!this.boosterTimer.hasTimeElapsed(1500L, false)) {
                            mc.timer.timerSpeed = 1.75F;
                        } else {
                            mc.timer.timerSpeed = 1.0F;
                        }
                    if (this.noSprint.isToggled())
                        mc.thePlayer.setSprinting(false);
                    if (this.sneakOnPlace.isToggled() && this.timer.hasTimeElapsed(50L))
                        mc.gameSettings.keyBindSneak.pressed = false;
                    if (!this.sneakOnPlace.isToggled() && mc.gameSettings.keyBindSneak.pressed)
                        mc.gameSettings.keyBindSneak.pressed = false;
                    EventMotion eventMotion = (EventMotion)e;
                    if (mc.thePlayer.getCurrentEquippedItem() == null)
                        return;
                    if (mc.thePlayer.getCurrentEquippedItem().getItem() == Item.getItemById(0))
                        return;
                    if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))
                        return;
                    if (e.isPre()) {
                        mc.timer.timerSpeed = (float)this.timerspeed.getValue();
                        if (this.sneakOnPlace.isToggled())
                            mc.thePlayer.setSneaking(true);
                        if (mc.theWorld == null || mc.thePlayer == null)
                            return;
                        try {
                            this.blockData = null;
                            if (mc.thePlayer.getHeldItem() != null) {
                                updateHotbarHypixel();
                                BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                                this.blockData = getBlockData(blockPos);
                                float[] arrayOfFloat = RotationUtils.getRotationFromPosition(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ());
                                mc.thePlayer.rotationYawHead = eventMotion.getYaw();
                                mc.thePlayer.renderYawOffset = eventMotion.getYaw();
                                eventMotion.setYaw(arrayOfFloat[0]);
                                eventMotion.setPitch(85.0F);
                                if (mc.gameSettings.keyBindJump.isKeyDown()); // or pressed xD
                                if (this.blockData == null)
                                    return;
                                if (mc.theWorld == null || mc.thePlayer == null)
                                    return;
                                Random random = new Random();
                                if (this.timer.hasTimeElapsed((1 + random.nextInt(9))) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                                    mc.thePlayer.swingItem();
                                    if (this.stopMovingOnPlace.isToggled()) {
                                        mc.thePlayer.motionX = 0.0D;
                                        mc.thePlayer.motionZ = 0.0D;
                                        mc.thePlayer.moveForward = 0.0F;
                                    }
                                    eventMotion.setYaw(arrayOfFloat[0]);
                                    eventMotion.setPitch(85.0F);
                                    if (this.sneakOnPlace.isToggled())
                                        mc.gameSettings.keyBindSneak.pressed = true;
                                    mc.thePlayer.rotationYawHead = eventMotion.getYaw();
                                    mc.thePlayer.renderYawOffset = eventMotion.getYaw();
                                }
                            }

                        } catch (Exception exception) {}
                    }
                    if (e.isPost()) {
                        if (this.blockData == null)
                            return;
                        if (mc.theWorld == null || mc.thePlayer == null)
                            return;
                        Random random = new Random();
                        if (this.timer.hasTimeElapsed((1 + random.nextInt(9))) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                            if (this.sneakOnPlace.isToggled())
                                mc.gameSettings.keyBindSneak.pressed = true;
                            mc.thePlayer.swingItem();
                            this.LastBuild = System.currentTimeMillis();
                        }
                    }
                }


        }

        //if(this.mode("aaa"))

    }

    private void setSneaking(boolean b) {
        KeyBinding sneak = mc.gameSettings.keyBindSneak;
        try {
            Field field = sneak.getClass().getDeclaredField("pressed");
            field.setAccessible(true);
            field.setBoolean(sneak, b);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    };

    private void setBlockAndFacing(BlockPos var1){
        if(mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air){
            this.currentPos = var1.add(0,-1,0);
            currentFacing = EnumFacing.UP;
        }else if(mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air){
            this.currentPos = var1.add(-1,0,0);
            currentFacing = EnumFacing.EAST;
        }else if(mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air){
            this.currentPos = var1.add(1,0,0);
            currentFacing = EnumFacing.WEST;
        }else if(mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(0,0,-1);
            currentFacing = EnumFacing.SOUTH;
        }else if(mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(0,0,1);
            currentFacing = EnumFacing.NORTH;
        }else{
            currentFacing = null;
            currentPos = null;
        }
    }

    //v2 class

    public void updateHotbarHypixel() {
        ItemStack itemStack = new ItemStack(Item.getItemById(261));
        try {
            byte b = 36;
            if ((0x2D & 0xFFFFFFD2) < 0)
                return;
            while (b < 45) {
                int i = b - 36;
                if (!Container.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(b), itemStack, true) && mc.thePlayer.inventoryContainer.getSlot(b).getStack().getItem() instanceof net.minecraft.item.ItemBlock && mc.thePlayer.inventoryContainer.getSlot(b).getStack() != null) {
                    if (mc.thePlayer.inventory.currentItem == i)
                        break;
                    mc.thePlayer.inventory.currentItem = i;
                    this.currentItem = i;
                    mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.playerController.updateController();
                    break;
                }
                b++;
            }
        } catch (Exception exception) {}
    }

    public BlockUtils2.BlockData getBlockData(BlockPos paramBlockPos) {
        if (mc.theWorld.getBlockState(paramBlockPos.add(0, 0, 1)).getBlock() != Blocks.air) {
            if ((0x11 & 0xFFFFFFEE) < (0x1B & 0xFFFFFFE4))
                return null;
        } else {

        }
        return (mc.theWorld.getBlockState(paramBlockPos.add(0, -1, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(paramBlockPos.add(0, -1, 0), EnumFacing.UP) : ((mc.theWorld.getBlockState(paramBlockPos.add(-1, 0, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(paramBlockPos.add(-1, 0, 0), EnumFacing.EAST) : ((mc.theWorld.getBlockState(paramBlockPos.add(1, 0, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(paramBlockPos.add(1, 0, 0), EnumFacing.WEST) : ((mc.theWorld.getBlockState(paramBlockPos.add(0, 0, -1)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(paramBlockPos.add(0, 0, -1), EnumFacing.SOUTH) : null)));
    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        private BlockData(final BlockPos position, final EnumFacing face, final BlockData blockData) {
            this.position = position;
            this.face = face;
        }
    }

    private static transient List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane,
            Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
            Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest,
            Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab,
            Blocks.wooden_slab, Blocks.stone_slab2, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.red_flower,
            Blocks.yellow_flower, Blocks.flower_pot);

    private int getBlockSlot() {
        int item = -1;
        int stacksize = 0;
        for (int i = 36; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && !invalidBlocks.contains(((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock()) && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize >= stacksize) {
                item = i - 36;
                stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
            }
        }
        return item;
    }
    public Vec3 getVec3(final BlockPos pos, final EnumFacing face) {
        double x = pos.getX() + 0.500;
        double y = pos.getY() + 0.500;
        double z = pos.getZ() + 0.500;
        x += face.getFrontOffsetX() / 2.0;
        z += face.getFrontOffsetZ() / 2.0;
        y += face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += (new Random().nextDouble() / 2) - 0.25;
            z += (new Random().nextDouble() / 2) - 0.25;
        } else {
            y += (new Random().nextDouble() / 2) - 0.25;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += (new Random().nextDouble() / 2) - 0.25;
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += (new Random().nextDouble() / 2) - 0.25;
        }
        return new Vec3(x, y, z);
    }

    private BlockData getBlockData2(final BlockPos pos) {
        if (isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(-1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(-1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(1, 0, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(1, 0, 0).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, 1).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, 1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, 1).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, -1, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(1, 0, 0))) {
            return new BlockData(pos.add(0, 0, -1).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, 0, -1).add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, -1, 0).add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, -1, 0).add(-1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, -1, 0).add(1, 0, 0))) {
            return new BlockData(pos.add(0, -1, 0).add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, -1, 0).add(0, 0, 1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos.add(0, -1, 0).add(0, 0, -1))) {
            return new BlockData(pos.add(0, -1, 0).add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        final BlockPos pos2 = pos.add(0, -1, 0).add(1, 0, 0);
        final BlockPos pos3 = pos.add(0, -1, 0).add(0, 0, 1);
        final BlockPos pos4 = pos.add(0, -1, 0).add(-1, 0, 0);
        final BlockPos pos5 = pos.add(0, -1, 0).add(0, 0, -1);
        if (isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        if (isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP, (BlockData) null);
        }
        if (isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST, (BlockData) null);
        }
        if (isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST, (BlockData) null);
        }
        if (isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH, (BlockData) null);
        }
        if (isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH, (BlockData) null);
        }
        return null;
    }

    public static boolean isPosSolid(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if ((block.getMaterial().isSolid() || !block.isTranslucent() || block instanceof BlockLadder || block instanceof BlockCarpet
                || block instanceof BlockSnow || block instanceof BlockSkull)
                && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer)) {
            return true;
        }
        return false;
    }
}
