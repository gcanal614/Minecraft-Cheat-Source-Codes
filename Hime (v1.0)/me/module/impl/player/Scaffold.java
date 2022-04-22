package me.module.impl.player;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import me.Hime;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.event.impl.Event3D;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventSafewalk;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.event.impl.MoveEvent;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.module.impl.combat.Killaura;
import me.settings.Setting;
import me.util.ColorUtil;
import me.util.MovementUtils;
import me.util.RayCastUtil;
import me.util.RenderUtil;
import me.util.Rotation;
import me.util.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;


public class Scaffold extends Module {

    private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
    private List<Block> badBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.gravel);
    private BlockData blockData;
    private Setting safewalk;
    private Setting blockFly;
    private Setting tower;
    private Setting sorting;
    private int stage;
    private Setting keeprots;
    private Setting towermove;
    private Setting swing;
    private Setting text;
    private Setting keepY;
    private Setting auraCheck;
    private Setting expand;
    public String mode;
    private Setting boost;
    private String boostMode;
    private String textMode;
    private String rotsMode;
    public Setting expandamount;
    public Setting slotChange;
    public Setting edgecheck;

    float yaw = 0;
    float pitch = 0;

    public Setting delay;
    public Setting turn;
    public Setting keepSprint;
    public static boolean isPlaceTick = false;
    public static boolean stopWalk = false;
    public String scaffoldMode;
    private double startY;
    public TimeUtil towerTimer = new TimeUtil();

    private int count;
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private TimeUtil timer = new TimeUtil();
    public Setting eagle;
    public Setting raycast;
    public Setting sprintBypass;
    private int slot;
    private EventUpdate eventUpdate = null;

    private float[] rotations = new float[2];

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_X, Category.PLAYER);


        this.addModes("TowerMode", "Hypixel", "Hypixel2", "Cubecraft", "Packet");
        this.mode = this.getModes("TowerMode");

        this.addModes("Scaffold Mode", "Hypixel", "Cubecraft", "AAC");
        this.scaffoldMode = this.getModes("Scaffold Mode");

        this.addModes("Scaffold Rotations Mode", "NCP", "Normal", "Hypixel", "Hypixel2", "Random");
        this.rotsMode = this.getModes("Scaffold Rotations Mode");

        this.addModes("Boost Mode", "Hypixel", "Hypixel2", "Mineplex");
        this.boostMode = this.getModes("Boost Mode");

        this.addModes("Text Mode", "Normal", "Bottom", "Sigma");
        this.textMode = this.getModes("Text Mode");
        Hime.instance.settingsManager.rSetting(keeprots = new Setting("KeepRots", this, true));
        Hime.instance.settingsManager.rSetting(safewalk = new Setting("Safewalk", this, true));
        Hime.instance.settingsManager.rSetting(blockFly = new Setting("Downwards", this, true));
        Hime.instance.settingsManager.rSetting(sorting = new Setting("Sorting", this, false));

        Hime.instance.settingsManager.rSetting(delay = new Setting("Place Delay", this, 0, 0, 1000, true));
        Hime.instance.settingsManager.rSetting(turn = new Setting("Hypixel Rotation Turn Amount", this, 5, 0, 30, true));
        Hime.instance.settingsManager.rSetting(tower = new Setting("Tower", this, true));
        Hime.instance.settingsManager.rSetting(towermove = new Setting("TowerMove", this, true));
        Hime.instance.settingsManager.rSetting(swing = new Setting("Swing", this, false));
        Hime.instance.settingsManager.rSetting(keepY = new Setting("KeepY", this, false));
        Hime.instance.settingsManager.rSetting(auraCheck = new Setting("auraCheck", this, false));

        Hime.instance.settingsManager.rSetting(eagle = new Setting("Eagle", this, false));



        Hime.instance.settingsManager.rSetting(keepSprint = new Setting("KeepSprint", this, true));
        Hime.instance.settingsManager.rSetting(slotChange = new Setting("Change Slot Hypixel", this, false));

        Hime.instance.settingsManager.rSetting(sprintBypass = new Setting("Hypixel Sprint Bypass", this, true));
        Hime.instance.settingsManager.rSetting(edgecheck = new Setting("Edge Check", this, false));

        Hime.instance.settingsManager.rSetting(boost = new Setting("Boost", this, false));

        Hime.instance.settingsManager.rSetting(raycast = new Setting("RayCast", this, false));
        Hime.instance.settingsManager.rSetting(text = new Setting("Text", this, true));
        Hime.instance.settingsManager.rSetting(expand = new Setting("Expand", this, false));
        Hime.instance.settingsManager.rSetting(expandamount = new Setting("Expand Amount", this, 1, 0, 15, true));



    }

    @Handler
    public void onPre(EventUpdate event) {
        this.mode = this.getModes("TowerMode");
        this.scaffoldMode = this.getModes("Scaffold Mode");
        this.rotsMode = this.getModes("Scaffold Rotations Mode");
        this.boostMode = this.getModes("Boost Mode");
        this.textMode = this.getModes("Text Mode");

        if(event.isPre()) {
            if (this.auraCheck.getValBoolean()){
                Hime.instance.moduleManager.getModule(Killaura.class).setToggled(false);
            }

            if(scaffoldMode.equalsIgnoreCase("Hypixel") || scaffoldMode.equalsIgnoreCase("Cubecraft")) {
                int slot = this.getSlot();
                this.stopWalk = (getBlockCount() == 0 || slot == -1) && safewalk.getValBoolean();
                this.isPlaceTick = keeprots.getValBoolean() ? blockData != null && slot != -1 : blockData != null && slot != -1 && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(0, -1, 0)).getBlock() == Blocks.air;
                if (slot == -1) {
                    moveBlocksToHotbar();

                    return;
                }
                if (!keepSprint.getValBoolean()) {
                    mc.thePlayer.setSprinting(false);
                    mc.gameSettings.keyBindSprint.pressed = false;
                    mc.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }

                if(this.scaffoldMode.equalsIgnoreCase("Hypixel")) {
                    if (keepSprint.getValBoolean()) {
                        if (this.sprintBypass.getValBoolean()) {
                            // mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        }
                    }
                }
                this.blockData = this.getBlockData();
                if (this.blockData == null) {
                    return;
                }

                // tower and towermove
                if (mc.gameSettings.keyBindJump.isKeyDown() && tower.getValBoolean() && (this.towermove.getValBoolean() || !mc.thePlayer.isMoving()) && !mc.thePlayer.isPotionActive(Potion.jump)) {
                    switch(mode) {
                        case "Hypixel2":
                            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                                if(mc.gameSettings.keyBindJump.pressed) {
                                    // if (!mc.thePlayer.isMoving()) {
                                    mc.timer.timerSpeed = 2F;
                                    if (!MovementUtils.isOnGround(0.79) || mc.thePlayer.onGround) {
                                        mc.thePlayer.motionY = 0.41985;
                                    }
                                    if(mc.thePlayer.ticksExisted % 75 == 0) {
                                        // mc.thePlayer.motionY = -1L;
                                        mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 75 == 0 ? -0.019429035780923 : 0.52f;
                                        towerTimer.reset();
                                    }
                                    //  }
                                    break;
                                }
                            }
                            break;
                        case "Hypixel":{
                            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                                if(mc.gameSettings.keyBindJump.pressed) {
                                    // if (!mc.thePlayer.isMoving()) {
                                    if (!MovementUtils.isOnGround(0.79) || mc.thePlayer.onGround) {
                                        mc.thePlayer.motionY = 0.41985;
                                    }
                                    if(mc.thePlayer.ticksExisted % 75 == 0) {
                                        // mc.thePlayer.motionY = -1L;
                                        mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 75 == 0 ? -0.019429035780923 : 0.52f;
                                        towerTimer.reset();
                                    }
                                    //}
                                    break;
                                }
                            }
                        }
                        //mc.thePlayer.jump and motionx and z = 0
                        case "Packet":
                            if (mc.thePlayer.ticksExisted % 2 == 0) {
                                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688698, mc.thePlayer.posZ, false));
                                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212, mc.thePlayer.posZ, false));
                            }
                            break;
                        case "Cubecraft":
                            count++;
                            mc.thePlayer.motionX = 0;
                            mc.thePlayer.motionZ = 0;
                            mc.thePlayer.jumpMovementFactor = 0;
                            if (MovementUtils.isOnGround(2))
                                if (count == 1) {
                                    mc.thePlayer.motionY = 0.41;
                                } else {

                                    mc.thePlayer.motionY = 0.47;
                                    count = 0;
                                }
                            break;
                    }
                } else {
                    towerTimer.reset();
                    if(!this.boost.getValBoolean()) {
                        mc.timer.timerSpeed = 1.0f;
                    }
                }
                if (this.isPlaceTick) {

                    Rotation targetRotation = new Rotation(me.util.setBlockAndFacing.BlockUtil.getDirectionToBlock(blockData.getPosition().getX(), blockData.getPosition().getY(), blockData.getPosition().getZ(), blockData.getFacing())[0], 81f);
                    Rotation limitedRotation = me.util.setBlockAndFacing.BlockUtil.limitAngleChange(new Rotation(yaw, event.getPitch()), targetRotation, this.rotsMode.equalsIgnoreCase("Hypixel") ? (float) ThreadLocalRandom.current().nextDouble(20, 30) : this.turn.getValInt());
                    yaw = limitedRotation.getYaw();
                    pitch = limitedRotation.getPitch();


                    float yaw = event.getYaw();
                    float pitch = event.getPitch();
                    boolean random = mc.thePlayer.isMoving();
                    // float speed = (float) ThreadLocalRandom.current().nextDouble(2, 3);



                    float targetYaw = 0;

                    if (this.blockData.getFacing().getName().equalsIgnoreCase("west")) {
                        targetYaw = -90;
                    }
                    if (this.blockData.getFacing().getName().equalsIgnoreCase("north")) {
                        targetYaw = 0;
                    }
                    if (this.blockData.getFacing().getName().equalsIgnoreCase("east")) {
                        targetYaw = 90;
                    }
                    if (this.blockData.getFacing().getName().equalsIgnoreCase("south")) {
                        targetYaw = 180;
                    }
                    float speed = (float) ThreadLocalRandom.current().nextDouble(1.5, 2.5);
                    float yawDifference = event.getLastYaw() - targetYaw;
                    if(yawDifference < 91) {
                        yaw = event.getLastYaw() - (yawDifference / speed);
                    }else {
                        yaw = targetYaw;
                    }
                    float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
                    float gcd = f * f * f * 1.2F;

                    yaw -= yaw % gcd;

                    if(this.rotsMode.equalsIgnoreCase("NCP") || this.rotsMode.equalsIgnoreCase("Normal")) {
                        event.setPitch(85);
                        event.setYaw(yaw);
                    }else if(this.rotsMode.equalsIgnoreCase("Hypixel")){
                        //   event.setPitch(81);
                        //  event.setYaw(yaw);
                        event.setYaw(this.yaw);
                        event.setPitch(81f); //79.44f maybe?
                        //   mc.thePlayer.rotationYaw = this.yaw;
                        //       mc.thePlayer.rotationPitch = 81f;
                    }else if(this.rotsMode.equalsIgnoreCase("Hypixel2")){
                        //   event.setPitch(85);
                        // event.setYaw(85);
                        event.setYaw(this.yaw); //no random + less turn
                        event.setPitch(81f);
                    }else if(this.rotsMode.equalsIgnoreCase("Random")){
                        event.setYaw(randInt(0, 90));
                        event.setPitch(randInt(81, 88));
                    }



                    if(this.boost.getValBoolean()) {
                        if(this.boostMode.equalsIgnoreCase("Mineplex")) {
                            if (stage > 10)
                                stage = 0;
                            stage++;
                            if (mc.thePlayer.isMoving()) {
                                if (stage > 4) {
                                    mc.thePlayer.setSpeed((float) ThreadLocalRandom.current().nextDouble(0.14, 0.24));
                                    // MovementUtil2.instance.strafe((float) ThreadLocalRandom.current().nextDouble(1, 1.2));
                                } else {
                                    mc.thePlayer.setSpeed(0);
                                    // MovementUtil2.instance.strafe(0);
                                }
                            } else {
                                stage = 0;
                            }
                        }
                    }
                    //  mc.thePlayer.rotationPitchHead = 85;
                    if(this.rotsMode.equalsIgnoreCase("NCP") || this.rotsMode.equalsIgnoreCase("Normal")) {
                        mc.thePlayer.setRotationYawHead(yaw);
                        mc.thePlayer.renderYawOffset = yaw;
                        mc.thePlayer.rotationPitchHead = 85;
                    }else if(this.rotsMode.equalsIgnoreCase("Hypixel")){
                        mc.thePlayer.setRotationYawHead(this.yaw);
                        mc.thePlayer.renderYawOffset = this.yaw;
                        mc.thePlayer.rotationPitchHead = 81;
                    }else if(this.rotsMode.equalsIgnoreCase("Hypixel2")){
                        mc.thePlayer.setRotationYawHead(this.yaw);
                        mc.thePlayer.renderYawOffset = this.yaw;
                        mc.thePlayer.rotationPitchHead = 81;
                    }else if(this.rotsMode.equalsIgnoreCase("Random")){
                        event.setYaw(randInt(0, 90));
                        mc.thePlayer.rotationYawHead =  randInt(0, 90);
                        mc.thePlayer.rotationPitchHead = randInt(81, 88);
                        event.setPitch(randInt(81, 88));
                    }

                }
            }else {
                if (eagle.getValBoolean()) {
                    if (rotated) {
                        setSneaking(true);
                    } else {
                        setSneaking(false);
                    }
                }



                rotated = false;
                currentPos = null;
                currentFacing = null;
                BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                    setBlockAndFacing(pos);

                    if (currentPos != null) {
                        float facing[] = me.util.setBlockAndFacing.BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);

                        float yaw = facing[0];
                        float pitch = Math.min(90, facing[1] + 9);

                        rotations[0] = yaw;
                        rotations[1] = pitch;

                        this.rotated = !(this.raycast.getValBoolean() && !rayTrace(yaw, pitch));
                        if(this.rotsMode.equalsIgnoreCase("NCP") || this.rotsMode.equalsIgnoreCase("Normal")) {
                            event.setYaw(yaw);
                            event.setPitch(pitch);
                        }else if(this.rotsMode.equalsIgnoreCase("Hypixel")) {
                            event.setYaw(this.yaw);
                            event.setPitch(81f);
                        }else if(this.rotsMode.equalsIgnoreCase("Hypixel2")){
                            event.setYaw(this.yaw);
                            event.setPitch(81f);
                        }else if(this.rotsMode.equalsIgnoreCase("Random")){
                            event.setYaw(randInt(0, 90));
                            event.setPitch(randInt(81, 88));
                        }

                    }
                } else {
                    if (keeprots.getValBoolean()) {
                        if(this.rotsMode.equalsIgnoreCase("NCP") || this.rotsMode.equalsIgnoreCase("Normal")) {
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                        }else if(this.rotsMode.equalsIgnoreCase("Hypixel")) {
                            event.setYaw(this.yaw);
                            event.setPitch(81f);
                        }else if(this.rotsMode.equalsIgnoreCase("Hypixel2")){
                            event.setYaw(this.yaw);
                            event.setPitch(81f);
                        }else if(this.rotsMode.equalsIgnoreCase("Random")){
                            event.setYaw(randInt(0, 90));
                            event.setPitch(randInt(81, 88));
                        }
                    }
                }
                if(this.rotsMode.equalsIgnoreCase("NCP") || this.rotsMode.equalsIgnoreCase("Normal")) {
                    mc.thePlayer.rotationYawHead = event.getYaw();
                    mc.thePlayer.rotationPitchHead = event.getPitch();
                    mc.thePlayer.renderYawOffset = event.getYaw();
                }else if(this.rotsMode.equalsIgnoreCase("Hypixel")){
                    mc.thePlayer.setRotationYawHead(this.yaw);
                    mc.thePlayer.renderYawOffset = this.yaw;
                    mc.thePlayer.rotationPitchHead = 81;
                }else if(this.rotsMode.equalsIgnoreCase("Hypixel2")){
                    mc.thePlayer.setRotationYawHead(this.yaw);
                    mc.thePlayer.renderYawOffset = this.yaw;
                    mc.thePlayer.rotationPitchHead = 81;
                }else if(this.rotsMode.equalsIgnoreCase("Random")){
                    mc.thePlayer.rotationYawHead =  randInt(0, 90);
                    mc.thePlayer.rotationPitchHead = randInt(81, 88);
                }
            }
        }
    }
    @Handler
    public final void onPacket(EventSendPacket e) {
        if(this.scaffoldMode.equalsIgnoreCase("Hypixel")) {
            if (keepSprint.getValBoolean()) {
                if (this.sprintBypass.getValBoolean()) {
                    if(e.getPacket() instanceof S08PacketPlayerPosLook) {
                    }
                    if (e.getPacket() instanceof C0BPacketEntityAction) {
                        e.cancel();
                        mc.getNetHandler().addToSendQueueSilent(e.getPacket());
                    }
                }
            }
        }
    }

    @Handler
    public void onMove(MoveEvent event) {
        if(this.boost.getValBoolean()) {
            if(this.boostMode.equalsIgnoreCase("Hypixel")) {
                //Credit Destiny
                //mc.timer.timerSpeed = 1.29F;
                mc.timer.timerSpeed = 1.4F;
                MovementUtils.setSpeed(event, 0.1554 + RandomUtils.nextFloat(0.0005372821F, 0.00168281F) + 0.1554);
            }
            if(this.boostMode.equalsIgnoreCase("Hypixel2")) {
                //mc.timer.timerSpeed = 1.29F;
                mc.timer.timerSpeed = 1.7F;
                //  MovementUtils.setSpeed(event, 0.1554 + RandomUtils.nextFloat(0.0005372821F, 0.00168281F) + 0.1554);
            }
        }
    }

    @Handler
    public void onPost(EventUpdate event) {
        this.eventUpdate = event;

        if(event.isPost()) {
            switch(scaffoldMode) {
                case "Hypixel":
                    if(this.slotChange.getValBoolean()) {
                        int slot = this.getSlot();
                        if (slot != -1 && this.blockData != null) {
                            final int currentSlot = mc.thePlayer.inventory.currentItem;
                            mc.thePlayer.inventory.currentItem = slot;
                            if (this.getPlaceBlock(this.blockData.getPosition(), this.blockData.getFacing())) {

                                //  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = currentSlot));

                            }
                            //    mc.thePlayer.inventory.currentItem = currentSlot;
                            //   Hime.addClientChatMessage(currentSlot + "a a " + Math.random());
                        }
                    }else {
                        int slot = this.getSlot();
                        if (slot != -1 && this.blockData != null) {
                            final int currentSlot = mc.thePlayer.inventory.currentItem;
                            mc.thePlayer.inventory.currentItem = slot;
                            if (this.getPlaceBlock(this.blockData.getPosition(), this.blockData.getFacing())) {
                                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
                            }
                            mc.thePlayer.inventory.currentItem = currentSlot;
                        }
                    }
                    break;
                case "AAC":
                    for(int i = 0; i < 9; i++){
                        if(mc.thePlayer.inventory.getStackInSlot(i) == null)
                            continue;
                        if(mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock){
                            mc.thePlayer.sendQueue.addToSendQueueSilent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i));
                        }
                    }
                    if (currentPos != null) {
                        if (timer.hasTimePassed((long) this.delay.getValDouble()) && rotated) {
                            if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), currentPos, currentFacing, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()))) {
                                    timer.reset();
                                    if(swing.getValBoolean()) {
                                        mc.thePlayer.swingItem();
                                    }else {
                                        mc.getNetHandler().addToSendQueueSilent(new C0APacketAnimation());
                                    }

                                    if (!eagle.getValBoolean()) {

                                    } else {
                                        mc.thePlayer.motionX *= 0.7;
                                        mc.thePlayer.motionZ *= 0.7;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "Cubecraft":
                    int slot2 = this.getSlot();
                    if (slot2 != -1 && this.blockData != null) {
                        final int currentSlot = mc.thePlayer.inventory.currentItem;
                        mc.thePlayer.inventory.currentItem = slot2;
                        if (this.getPlaceBlock(this.blockData.getPosition(), this.blockData.getFacing())) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
                            mc.thePlayer.setSprinting(false);
                            mc.thePlayer.setSpeed((float) 0.44);
                        }
                    }
                    break;

            }
        }
    }



    @Handler
    public void onSafe(EventSafewalk event) {
        boolean downwards = (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockFly.getValBoolean() && mc.thePlayer.onGround);
        if (mc.thePlayer.onGround && this.safewalk.getValBoolean() && !downwards) {
            event.setSafe(true);
        }
    }

    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }


    public  boolean isMoving2() {
        return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
    }


    @Override
    public void onEnable() {
        super.onEnable();
        stage = 0;
        startY = mc.thePlayer.posY;
        slot = mc.thePlayer.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0f;
        if(scaffoldMode.equalsIgnoreCase("Hypixel")) {
            if(this.slotChange.getValBoolean()) {
                mc.thePlayer.inventory.currentItem = slot;
            }
            if(eventUpdate != null) {
                // 	 System.out.println("aa");
                //  	Rotation targetRotation = new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
                //   Rotation limitedRotation = me.util.setBlockAndFacing.BlockUtil.limitAngleChange(new Rotation(yaw, eventUpdate.getPitch()), targetRotation, (float) ThreadLocalRandom.current().nextDouble(20, 30));
                // yaw = limitedRotation.getYaw();
                /// pitch = limitedRotation.getPitch();
                //  eventUpdate.setYaw(eventUpdate.getYaw());
                // eventUpdate.setPitch(90);
            }
        }
        // mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));

        // mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = prevSlot));
    }
    private boolean getPlaceBlock(final BlockPos pos, final EnumFacing facing) {
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        Vec3i data = this.blockData.getFacing().getDirectionVec();
        //     if(timer.hasTimePassed((long) delay.getValDouble()) ){
        if((edgecheck.getValBoolean() ? isOnEdgeWithOffset(0.15) : timer.hasTimePassed((long) delay.getValDouble()))) {
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, new Vec3(this.blockData.getPosition()).addVector(0.5, 0.5, 0.5).add(new Vec3(data.getX() * 0.5, data.getY() * 0.5, data.getZ() * 0.5)))) {
                if (this.swing.getValBoolean()) {
                    mc.thePlayer.swingItem();
                } else {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
                if(!edgecheck.getValBoolean()) {
                    timer.reset();
                }
                return true;
            }


        }
        return false;
    }

    private boolean rayTrace(float yaw, float pitch) {
        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 =  RayCastUtil.getVectorForRotation(pitch, yaw);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);


        MovingObjectPosition result = mc.theWorld.rayTraceBlocks(vec3, vec32, false);


        return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && currentPos.equals(result.getBlockPos());
    }

    public void setBlockAndFacing(BlockPos var1) {

        //if(!shouldDownwards()) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, -2);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, 2);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, -2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, -2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 0);
            currentFacing = EnumFacing.DOWN;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 1);
            currentFacing = EnumFacing.WEST;
        }
    }

    private void setSneaking(boolean b) {
        KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
        mc.gameSettings.keyBindSneak.pressed = b;
    }

    private BlockData getBlockData() {
        final EnumFacing[] invert = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
        double yValue = 0;
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockFly.getValBoolean() && mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
            yValue -= 1;
        }
        BlockPos expand = new BlockPos(mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0, yValue, 0);
        BlockPos playerpos = expand;

        if(mc.thePlayer.isMoving()){
            if(this.expand.getValBoolean()) {
                playerpos = expand.offset(mc.thePlayer.getHorizontalFacing(), this.expandamount.getValInt());
            }
        }
        boolean tower = !this.towermove.getValBoolean() && this.tower.getValBoolean() && !mc.thePlayer.isMoving();
        if (!this.blockFly.getValBoolean() && this.keepY.getValBoolean() && !tower) {
            if(this.expand.getValBoolean()) {
                playerpos = new BlockPos(new Vec3(mc.thePlayer.getPositionVector().xCoord, this.startY, mc.thePlayer.getPositionVector().zCoord)).offset(mc.thePlayer.getHorizontalFacing(), this.expandamount.getValInt());
                //  playerpos = expand2.offset(mc.thePlayer.getHorizontalFacing(), 3);
            }else {
                playerpos = new BlockPos(new Vec3(mc.thePlayer.getPositionVector().xCoord, this.startY, mc.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
            }
        } else {
            this.startY = mc.thePlayer.posY;
        }

        List<EnumFacing> facingVals = Arrays.asList(EnumFacing.values());
        for (int i = 0; i < facingVals.size(); ++i) {
            if (mc.theWorld.getBlockState(playerpos.offset(facingVals.get(i))).getBlock().getMaterial() != Material.air) {
                return new BlockData(playerpos.offset(facingVals.get(i)), invert[facingVals.get(i).ordinal()]);
            }
        }
        final BlockPos[] addons = {
                new BlockPos(-1, 0, 0),
                new BlockPos(1, 0, 0),
                new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        for (int length2 = addons.length, j = 0; j < length2; ++j) {
            final BlockPos offsetPos = playerpos.add(addons[j].getX(), 0, addons[j].getZ());
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                for (int k = 0; k < EnumFacing.values().length; ++k) {
                    if (mc.theWorld.getBlockState(offsetPos.offset(EnumFacing.values()[k])).getBlock().getMaterial() != Material.air) {
                        return new BlockData(offsetPos.offset(EnumFacing.values()[k]), invert[EnumFacing.values()[k].ordinal()]);
                    }
                }
            }
        }
        return null;
    }

    private int getSlot() {
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && (this.sorting.getValBoolean() ? (this.getBestStack() == itemStack) : itemStack.stackSize >= 1)) {
                return k;
            }
        }
        return -1;
    }

    public ItemStack getBestStack() {
        List<ItemStack> slots = new ArrayList<>();
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                slots.add(itemStack);
            }
        }
        slots.sort((o1, o2) -> (int)(o2.stackSize - o1.stackSize));
        if (slots.isEmpty())
            return null;
        return slots.get(0);
    }

    private boolean isValid(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBlock) {
            boolean isBad = false;

            ItemBlock block = (ItemBlock) itemStack.getItem();
            for (int i = 0; i < this.badBlocks.size(); i++) {
                if (block.getBlock().equals(this.badBlocks.get(i))) {
                    isBad = true;
                }
            }

            return !isBad;
        }
        return false;
    }

    public static int randInt (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private int getBlockCount() {
        int count = 0;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                count += itemStack.stackSize;
            }
        }
        return count;
    }

    private class BlockData {
        private BlockPos blockPos;
        private EnumFacing enumFacing;

        private BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }

        private EnumFacing getFacing() {
            return this.enumFacing;
        }

        private BlockPos getPosition() {
            return this.blockPos;
        }
    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (!isHotbarFull()) {
            for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
                if (k > 8 && !added) {
                    final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
                    if (itemStack != null && this.isValid(itemStack)) {
                        shiftClick(k);
                        added = true;
                    }
                }
            }
        }
    }

    @Handler
    public void onRender2D(EventRenderHUD event) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int blocks = getBlockCount();
        String text = (new StringBuilder(String.valueOf(blocks))).toString();
        if(this.text.getValBoolean()) {
            //	  ItemStack stack = mc.thePlayer.inventory.getStackInSlot(getSlot());
            //    GL11.glPushMatrix();
            //	  RenderHelper.enableGUIStandardItemLighting();
            //  mc.getRenderItem().renderItemAndEffectIntoGUI(stack, sr.getScaledWidth() / 2 - 36, sr.getScaledHeight() / 2 + 4);
            //  GL11.glPopMatrix();
            // this.ufr.drawCenteredString(String.valueOf(text) + " blocks", (sr.getScaledWidth() / 2) + 1.1F, (sr.getScaledHeight() / 2) + this.ufr.getHeight(text) + 1.1F, (new Color(0, 0, 0)).getRGB());
            switch(textMode) {
                case "Normal":
                    Hime.instance.rfrs.drawCenteredString("§" + this.getStackColorSuffix(blocks) + String.valueOf(text) + " §fBlocks", (sr.getScaledWidth() / 2) + 0.1F, (sr.getScaledHeight() / 2) + Hime.instance.rfrs.getHeight(text) + 0.1F, -1);
                    break;
                case "Bottom":
                    Hime.instance.rfrs.drawCenteredString("§" + this.getStackColorSuffix(blocks) + String.valueOf(text) + " §fBlocks", (sr.getScaledWidth() / 2) + 0.1F, (sr.getScaledHeight() / 1) - 69, -1);
                    break;
                case "Sigma":
                    RenderUtil.instance.draw2DImage(new ResourceLocation("client/scaffold1.png"), (sr.getScaledWidth() / 2) - 40.1F- (int) Hime.instance.rfrs.getWidth(text) / 2, (sr.getScaledHeight() / 1) - 99, 80 + (int) Hime.instance.rfrs.getWidth(text), 55, Color.WHITE);
                    RenderUtil.instance.draw2DImage(new ResourceLocation("client/scaffold2.png"), (sr.getScaledWidth() / 2) - 40.1F, (sr.getScaledHeight() / 1) - 116.5, 80, 80, Color.WHITE);
                    Hime.instance.rfrs.drawCenteredString(String.valueOf(text) + " §7Blocks", (sr.getScaledWidth() / 2) + 0.1F, (sr.getScaledHeight() / 1) - 79, -1);
                    break;
            }
        }
    }

    @Handler
    public void onRender3D(Event3D event) {
        if(this.text.getValBoolean()) {
            //this.drawCircle(mc.thePlayer, event.getPartialTicks(), 0.6D, 0, 1);
            //	this.drawCircle(mc.thePlayer, event.getPartialTicks(), 0.5D, 0, 1);
        }
    }

    private void drawCircle(Entity entity, float partialTicks, double rad, double height, double alpha) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0F);
        GL11.glBegin(3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (this.mc.getRenderManager()).viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (this.mc.getRenderManager()).viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (this.mc.getRenderManager()).viewerPosZ;
        final float hue = (float) (ColorUtil.getClickGUIColor());
        //                           colorSaturation  colorBrightness
        Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());

        float r = 0.003921569F * color.getRed();
        float g = 0.003921569F * color.getGreen();
        float b = 0.003921569F * color.getBlue();
        double pix2 = 6.283185307179586D;
        for (int i = 0; i <= 90; i++) {
            //GL11.glColor4d(r, g, b, 0.3);
            GL11.glColor4d(1, 1, 1, 0.3);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y + height, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null) {
                count++;
            }
        }
        return count == 8;
    }
    public static void shiftClick(int slot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
    }

    /*@Handler
    public void onMotion(EventMotion event) {
      if (this.safewalk.getValBoolean())
        event.setSafeWalk(true);
    }*/

    protected String getStackColorSuffix(int size) {

        if (size > 45)
            return "a";
        if (size > 30)
            return "e";
        if (size > 20)
            return "6";
        if (size < 10)
            return "4";
        return "c";

    }

    private boolean isOnEdgeWithOffset(double paramDouble) {
        double d1 = mc.thePlayer.posX;
        double d2 = mc.thePlayer.posY;
        double d3 = mc.thePlayer.posZ;
        BlockPos blockPos1 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 - paramDouble);
        BlockPos blockPos2 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 + paramDouble);
        BlockPos blockPos3 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 + paramDouble);
        BlockPos blockPos4 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 - paramDouble);
        return (mc.thePlayer.worldObj.getBlockState(blockPos1).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air);
    }



}