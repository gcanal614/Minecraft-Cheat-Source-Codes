package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.impl.*;
import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.movement.RotationUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.TimerHelper;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Scaffold extends Module {

    private ModeSet modeSet = new ModeSet("Mode", "Watchdog",  "Normal", "Packet Sneak", "Slow", "Watchdog", "Watchdog2", "AAC", "Ray" );
    private DoubleSet timerSpeed = new DoubleSet("Timer Speed", 1.0D, 0.5D, 5.0D, 0.05D);
    private ModeSet placeModeSet = new ModeSet("Place Mode", "Verus",  "Verus", "Post", "Pre", "Mixed", "Double" );
    private ModeSet vecModeSet = new ModeSet("VecHit Mode", "Verus",  "Verus", "Watchdog", "Morgan", "Legit" );
    private ModeSet rotationsSet = new ModeSet("Rotations", "Dynamic",  "Watchdog", "Dynamic", "VecHit", "NullPitch", "Morgan", "Disabled", "Redesky", "Verus", "AAC" );
    public BooleanSet sneakSet = new BooleanSet("Sneak", false);
    public BooleanSet sprintSet = new BooleanSet("Sprint", true);
    private DoubleSet expandValSet = new DoubleSet("Expand", 0.0D, 0.0D, 4.0D, 0.1D);
    private ModeSet swingModeSet = new ModeSet("Swing", "Client",  "Client", "Server", "None", "Spam" );
    public ModuleCategory spoofBlockCategory = new ModuleCategory("Spoof...");
    public ModeSet spoofBlockMode = new ModeSet("Mode", "Server",  "Client", "Server", "Silent" );
    public BooleanSet spoofClientSwapBack = new BooleanSet("Client Swap-Back", true);
    public BooleanSet spoofSetverSwitch = new BooleanSet("Server Switch (Beta)", false);
    public ModuleCategory keepYCategory = new ModuleCategory("KeepY...");
    public BooleanSet keepYEnabled = new BooleanSet("Enabled", false);
    public ModuleCategory towerCategory = new ModuleCategory("Tower...");
    public ModeSet towerMode = new ModeSet("Mode", "Disabled",  "Packet", "Watchdog", "Morgan", "Constant", "Disabled", "Verus", "Universocraft" );
    public DoubleSet towerTimer = new DoubleSet("Timer Speed", 1.0D, 0.3D, 5.0D, 0.05D);
    public DoubleSet towerPacketTicks = new DoubleSet("Packet Ticks", 2.0D, 1.0D, 5.0D, 1.0D);
    public DoubleSet towerConstantMotion = new DoubleSet("Constant Motion", 0.42D, 0.42D, 0.82D, 0.01D);
    public ModuleCategory watchdogCategory = new ModuleCategory("Watchdog...");
    public BooleanSet watchdogRandomVec = new BooleanSet("Random Vec", true);
    public DoubleSet watchdogVecY = new DoubleSet("Vec Y", 0.98D, 0.0D, 1.0D, 0.01D);
    public DoubleSet watchdogYLevel = new DoubleSet("Y-Level", 12.0D, 0.0D, 20.0D, 0.01D);
    public DoubleSet watchdogSideVal = new DoubleSet("Side Value", 0.3D, 0.0D, 0.5D, 0.01D);
    public BooleanSet watchdogKeepRots = new BooleanSet("Keep", false);

    public final Setting[] settingArray = new Setting[] {
            modeSet, timerSpeed, placeModeSet, vecModeSet, rotationsSet, keepYCategory, towerCategory, spoofBlockCategory, watchdogCategory, sneakSet,
            sprintSet, expandValSet, swingModeSet };

    private BlockPos cPos;
    private EnumFacing cFacing;
    float[] renderRotations;
    private boolean rotated;
    private boolean shouldRotate;
    boolean watchdogJumped;
    int watchdogState;
    float[] onplacerotupdate;
    boolean changed;
    int slotWithBlock;
    int itemBeforeSwap;
    boolean firstBlockPlaced;
    TimerHelper timer;

    public Scaffold() {
        super("Scaffold", 0, Category.MOVEMENT);
        rotated = false;
        shouldRotate = false;
        watchdogState = 0;
        changed = false;
        slotWithBlock = 0;
        itemBeforeSwap = 0;
        firstBlockPlaced = false;
        timer = new TimerHelper();
        addSettings(settingArray);
        watchdogCategory.addCatSettings(watchdogYLevel, watchdogSideVal, watchdogKeepRots, watchdogRandomVec, watchdogVecY );
        towerCategory.addCatSettings(towerMode, towerTimer, towerPacketTicks, towerConstantMotion );
        keepYCategory.addCatSettings(keepYEnabled);
        spoofBlockCategory.addCatSettings(spoofBlockMode, spoofClientSwapBack, spoofSetverSwitch );
    }

    @EventTarget
    public void onWalk(EventSafewalk e) {
        e.setSafewalk(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        changed = false;
        shouldRotate = false;
        firstBlockPlaced = false;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        switch (spoofBlockMode.getMode()) {
            case "Client":
                if (spoofClientSwapBack.isEnabled())
                    mc.thePlayer.inventory.currentItem = itemBeforeSwap;
                break;
            case "Server":
                if (mc.thePlayer.inventory.currentItem != itemBeforeSwap || (localPlayer.getCurrentEquippedItem() != null && localPlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock)) {
                    mc.thePlayer.inventory.currentItem = itemBeforeSwap;
                    break;
                }
                PacketUtil.sendPacket(new C09PacketHeldItemChange(itemBeforeSwap));
                break;
        }
        resetSneaking();
        if (modeSet.is("Packet Sneak") &&
                rotated)
            PacketUtil.sendPacketSilent(new C0BPacketEntityAction((Entity)localPlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        firstBlockPlaced = false;
        mc.timer.setTimerSpeed(1.0F);
    }

    @EventTarget
    public void onTick(EventTick e) {
        mc.timer.setTimerSpeed((float)timerSpeed.getValue());
        if (sprintSet.isEnabled() && MovementUtils.isMoving())
            localPlayer.setSprinting(true);
        else
            localPlayer.setSprinting(false);
        switch (spoofBlockMode.getMode()) {
            case "Server":
                if (localPlayer.getCurrentEquippedItem() == null || !(localPlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))
                    for (int a = 0; a < 9; a++) {
                        if (localPlayer.inventory.getStackInSlot(a) != null) {
                            boolean isSafeToSpoof = (localPlayer.inventory.getStackInSlot(a).getItem() instanceof net.minecraft.item.ItemBlock && !changed);
                            if (isSafeToSpoof) {
                                itemBeforeSwap = mc.thePlayer.inventory.currentItem;
                                slotWithBlock = a;
                                PacketUtil.sendPacketSilent(new C09PacketHeldItemChange(slotWithBlock));
                                changed = true;
                                break;
                            }
                        }
                    }
                break;
            case "Client":
                if (localPlayer.getCurrentEquippedItem() == null || !(localPlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))
                    for (int a = 0; a < 9; a++) {
                        if (localPlayer.inventory.getStackInSlot(a) != null) {
                            boolean isSafeToSpoof = (localPlayer.inventory.getStackInSlot(a).getItem() instanceof net.minecraft.item.ItemBlock && !changed);
                            if (isSafeToSpoof) {
                                itemBeforeSwap = mc.thePlayer.inventory.currentItem;
                                slotWithBlock = a;
                                localPlayer.inventory.currentItem = slotWithBlock;
                                changed = true;
                                break;
                            }
                        }
                    }
                break;
        }
        if (localPlayer.inventory.getStackInSlot(slotWithBlock) == null &&
                spoofSetverSwitch.isEnabled())
            changed = false;
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        if (keepYEnabled.isEnabled()) {
            mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
            mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
        }
        checkModule(Speed.class);
    }

    @EventTarget
    public void onPre(EventMotion e) {
        setDisplayName("Scaffold");
        if (modeSet.is("Packet Sneak") &&
                rotated && cPos != null && cFacing != null)
            PacketUtil.sendPacketSilent(new C0BPacketEntityAction((Entity)localPlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        if (sneakSet.isEnabled())
            setSneaking(rotated);
        boolean shouldTower = (GameSettings.isKeyDown(mc.gameSettings.keyBindJump) && !MovementUtils.isMoving());
        if (towerMode.is("Verus"))
            shouldTower = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
        if (shouldTower && !towerMode.is("Disabled")) {
            mc.gameSettings.keyBindJump.pressed = false;
            mc.timer.setTimerSpeed((float)towerTimer.getValue());
            switch (towerMode.getMode()) {
                case "Packet":
                    localPlayer.motionZ = 0.0D;
                    localPlayer.motionZ = 0.0D;
                    if (MovementUtils.getOnRealGround((EntityLivingBase)localPlayer, 0.01D) && localPlayer.ticksExisted % towerPacketTicks.getValue() == 0.0D) {
                        localPlayer.motionY = 0.0D;
                        localPlayer.isAirBorne = true;
                        mc.thePlayer.triggerAchievement(StatList.jumpStat);
                        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(localPlayer.posX, localPlayer.posY + 0.41999998688698D, localPlayer.posZ, false));
                        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(localPlayer.posX, localPlayer.posY + 0.7531999805212D, localPlayer.posZ, false));
                        MovementUtils.setY(localPlayer.posY + 1.0D);
                    }
                    break;
                case "Constant":
                    localPlayer.motionZ = 0.0D;
                    localPlayer.motionZ = 0.0D;
                    localPlayer.motionY = towerConstantMotion.getValue();
                    break;
                case "Watchdog":
                    localPlayer.motionZ = 0.0D;
                    localPlayer.motionZ = 0.0D;
                    if (watchdogJumped)
                        switch (watchdogState) {
                            case 0:
                                localPlayer.isAirBorne = true;
                                mc.thePlayer.triggerAchievement(StatList.jumpStat);
                                localPlayer.motionY = 0.41999998688698D;
                                watchdogState++;
                                break;
                            case 1:
                                localPlayer.motionY = 0.33319999363422D;
                                watchdogState++;
                                break;
                            case 2:
                                localPlayer.motionY = 0.24813599859094704D;
                                watchdogState++;
                                break;
                        }
                    if ((!watchdogJumped || watchdogState > 2) && MovementUtils.getOnRealGround((EntityLivingBase)localPlayer, 1.0D)) {
                        watchdogState = 0;
                        watchdogJumped = true;
                    }
                    break;
                case "Verus":
                    if (MovementUtils.getOnRealGround((EntityLivingBase)localPlayer, 0.01D) && localPlayer.onGround && localPlayer.isCollidedVertically) {
                        watchdogState = 0;
                        watchdogJumped = true;
                    }
                    if (watchdogJumped) {
                        MovementUtils.setSpeed1(MovementUtils.getSpeed());
                        switch (watchdogState) {
                            case 0:
                                localPlayer.isAirBorne = true;
                                mc.thePlayer.triggerAchievement(StatList.jumpStat);
                                localPlayer.motionY = 0.41999998688697815D;
                                watchdogState++;
                                break;
                            case 1:
                                watchdogState++;
                                break;
                            case 2:
                                watchdogState++;
                                break;
                            case 3:
                                e.setOnGround(true);
                                localPlayer.motionY = 0.0D;
                                watchdogState++;
                                break;
                            case 4:
                                watchdogState++;
                                break;
                        }
                        watchdogJumped = false;
                        break;
                    }
                    watchdogJumped = true;
                    break;
            }
        } else {
            watchdogState = -1;
        }
        switch (modeSet.getMode()) {
            case "Watchdog":
                localPlayer.motionZ *= 0.70D;
                localPlayer.motionX *= 0.70D;
                if (localPlayer.isPotionActive(Potion.moveSpeed)) {
                    localPlayer.motionZ *= 0.60D;
                    localPlayer.motionX *= 0.60D;
                }
                break;
            case "Watchdog2":
                if (localPlayer.onGround) {
                    if (localPlayer.isSprinting()) {
                        localPlayer.motionZ *= 0.91D;
                        localPlayer.motionX *= 0.91D;
                    }
                    if (localPlayer.isPotionActive(Potion.moveSpeed)) {
                        localPlayer.motionZ *= 0.51D;
                        localPlayer.motionX *= 0.51D;
                    }
                }
                break;
            case "Slow":
                if (localPlayer.onGround) {
                    localPlayer.motionZ *= 0.86D;
                    localPlayer.motionX *= 0.86D;
                }
                break;
            case "AAC":
                if (localPlayer.onGround) {
                    if (localPlayer.isSprinting()) {
                        localPlayer.motionZ *= 0.90D;
                        localPlayer.motionX *= 0.90D;
                    }
                    if (localPlayer.isPotionActive(Potion.moveSpeed)) {
                        localPlayer.motionZ *= 0.55D;
                        localPlayer.motionX *= 0.55D;
                    }
                }
                break;
        }
        rotated = false;
        if (cPos != null && cFacing != null) {
            float[] redeRots, vecRots, r3, rots = getHypixelRotations(cPos, cFacing);
            float rotationYaw = mc.thePlayer.rotationYaw;
            if (mc.thePlayer.moveForward < 0.0F)
                rotationYaw += 180.0F;
            float forward = 1.0F;
            if (mc.thePlayer.moveForward < 0.0F) {
                forward = -0.5F;
            } else if (mc.thePlayer.moveForward > 0.0F) {
                forward = 0.5F;
            }
            if (mc.thePlayer.moveStrafing > 0.0F)
                rotationYaw -= 90.0F * forward;
            if (mc.thePlayer.moveStrafing < 0.0F)
                rotationYaw += 90.0F * forward;
            switch (rotationsSet.getMode()) {
                case "AAC":
                    if (firstBlockPlaced) {
                        float[] r2 = getRotationsToVec(getVec(cPos, cFacing));
                        if (shouldRotate) {
                            onplacerotupdate = r2;
                        } else if (watchdogKeepRots.isEnabled()) {
                            onplacerotupdate = r2;
                        }
                        e.setYaw(onplacerotupdate[0]);
                        e.setPitch((float)Math.min(onplacerotupdate[1] + watchdogYLevel.getValue(), 90.0D));
                        break;
                    }
                    e.setYaw(rotationYaw + 180.0F);
                    e.setPitch(84.0F);
                    break;
                case "Watchdog":
                    e.setYaw(rotationYaw - 244.0F);
                    e.setPitch(75.10F);
                    break;
                case "Dynamic":
                    e.setYaw(rots[0]);
                    e.setPitch(localPlayer.onGround ? 80.31F : rots[1]);
                    break;
                case "NullPitch":
                    e.setYaw(rots[0]);
                    e.setPitch(Float.MAX_VALUE);
                    break;
                case "Redesky":
                    redeRots = getHypixelRotations(cPos, cFacing);
                    e.setYaw(redeRots[0]);
                    e.setPitch(75.93F);
                    break;
                case "VecHit":
                    vecRots = getRotationsToVec(getVec(cPos, cFacing));
                    e.setYaw(vecRots[0]);
                    e.setPitch(Math.min(vecRots[1], 90.0F));
                    break;
                case "Morgan":
                    r3 = getRotationsToVec(getVec(cPos, cFacing));
                    if (shouldRotate) {
                        e.setYaw(r3[0]);
                        e.setPitch(Math.min(r3[1], 90.0F));
                    }
                    break;
                case "Verus":
                    if (shouldRotate)
                        e.setPitch(90.0F);
                    break;
            }
            renderRotations = new float[] { e.getYaw(), e.getPitch() };
        }
        shouldRotate = false;
        switch (placeModeSet.getMode()) {
            case "Verus":
            case "Pre":
            case "Double":
                place();
                break;
            case "Mixed":
                if (localPlayer.ticksExisted % 2 == 0)
                    place();
                break;
        }
        mc.thePlayer.rotationPitchHead = renderRotations[1];
        mc.thePlayer.renderYawOffset = renderRotations[0];
        mc.thePlayer.rotationYawHead = renderRotations[0];
        mc.thePlayer.prevRenderYawOffset = renderRotations[0];
    }

    @EventTarget
    public void onPost(EventPostMotion e) {
        switch (placeModeSet.getMode()) {
            case "Post":
            case "Double":
                place();
                break;
            case "Mixed":
                if (localPlayer.ticksExisted % 2 != 0)
                    place();
                break;
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        mc.fontRendererObj.drawStringWithShadow(getBlockCount() + " blocks", (e.getWidth() / 2.0F - mc.fontRendererObj.getStringWidth(getBlockCount() + " blocks") - 15.0F), (e.getHeight() / 2.0F) - 3.5D, -1);
        mc.thePlayer.rotationPitchHead = renderRotations[1];
        mc.thePlayer.renderYawOffset = renderRotations[0];
        mc.thePlayer.rotationYawHead = renderRotations[0];
        mc.thePlayer.prevRenderYawOffset = renderRotations[0];
    }

    public void place() {
        double dir = MovementUtils.getDirection();
        double posy = mc.thePlayer.posY - 1.0D;
        double expandX = localPlayer.motionX * expandValSet.getValue() * 10.0D, expandZ = localPlayer.motionZ * expandValSet.getValue() * 10.0D;
        BlockPos pos = new BlockPos(mc.thePlayer.posX + expandX, posy, mc.thePlayer.posZ + expandZ);
        rotated = true;
        shouldRotate = true;
        setPosAndFace(pos);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir) {
            if (cPos != null && cFacing != null) {
                if (hasBlockOnHotbar()) {
                    int a;
                    if (modeSet.is("Packet Sneak"))
                        PacketUtil.sendPacketSilent(new C0BPacketEntityAction((Entity) localPlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                    firstBlockPlaced = true;
                    switch (swingModeSet.getMode()) {
                        case "Client":
                            localPlayer.swingItem();
                            break;
                        case "Server":
                            PacketUtil.sendPacketSilent(new C0APacketAnimation());
                            break;
                        case "Spam":
                            for (a = 0; a < 5; a++)
                                PacketUtil.sendPacketSilent(new C0APacketAnimation());
                            break;
                    }
                    if (spoofBlockMode.is("Silent"))
                        for (int i = 0; i < 9; i++) {
                            if (localPlayer.inventory.getStackInSlot(i) != null)
                                if (localPlayer.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemBlock) {
                                    PacketUtil.sendPacket(new C09PacketHeldItemChange(slotWithBlock));
                                    break;
                                }
                        }
                    Vec3 vec = getVec(cPos, cFacing);
                    if (modeSet.is("Ray")) ;
                    placeBlock(mc.thePlayer.inventory.getStackInSlot(slotWithBlock), cPos, cFacing, vec);
                    if (spoofBlockMode.is("Silent"))
                        PacketUtil.sendPacket(new C09PacketHeldItemChange(localPlayer.inventory.currentItem));
                }
            }
        }
    }

    public float[] getRotationsToVec(Vec3 vec) {
        double x = vec.xCoord, y = vec.yCoord, z = vec.zCoord;
        return RotationUtils.getRotsByPos(x, y, z);
    }

    public void resetSneaking() {
        mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSneak);
    }

    public void setSneaking(boolean sneaking) {
        mc.gameSettings.keyBindSneak.pressed = sneaking;
    }

    public float[] getHypixelRotations(BlockPos pos, EnumFacing facing) {
        float yaw = getYaw(pos, facing);
        float[] rots2 = getDirectionToBlock(pos.getX(), pos.getY(), pos.getZ(), facing);
        return new float[] { (float)(yaw + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D)), Math.min(90.0F, rots2[1]) };
    }

    public boolean hasBlockOnHotbar() {
        for (int a = 0; a < 9; a++) {
            if (mc.thePlayer.inventory.getStackInSlot(a) != null)
                if (mc.thePlayer.inventory.getStackInSlot(a).getItem() instanceof net.minecraft.item.ItemBlock)
                    return true;
        }
        return false;
    }

    public Vec3 getVec(BlockPos pos, EnumFacing facing) {
        double dynamicVerusVec, extraVerusRandomization;
        ThreadLocalRandom randomThread = ThreadLocalRandom.current();
        Vec3 vecToModify = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        switch (vecModeSet.getMode()) {
            case "Legit":
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    vecToModify.zCoord = localPlayer.posZ;
                    vecToModify.yCoord += 0.7D;
                }
                if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                    vecToModify.xCoord = localPlayer.posX;
                    vecToModify.yCoord += 0.7D;
                }
                if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                    vecToModify.xCoord += 0.5D;
                    vecToModify.zCoord += 0.5D;
                }
                if (facing == EnumFacing.UP)
                    vecToModify.yCoord++;
                switch (facing) {
                    case SOUTH:
                        vecToModify.zCoord++;
                        break;
                    case NORTH:
                        vecToModify.zCoord += 0.0D;
                        break;
                    case EAST:
                        vecToModify.xCoord++;
                        break;
                    case WEST:
                        vecToModify.xCoord += 0.0D;
                        break;
                }
                break;
            case "Watchdog":
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    vecToModify.zCoord = localPlayer.posZ;
                    vecToModify.yCoord += 0.98D;
                }
                if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                    vecToModify.xCoord = localPlayer.posX;
                    vecToModify.yCoord += 0.98D;
                }
                if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                    vecToModify.xCoord += 0.5D;
                    vecToModify.zCoord += 0.5D;
                }
                if (facing == EnumFacing.UP)
                    vecToModify.yCoord++;
                switch (facing) {
                    case SOUTH:
                        vecToModify.zCoord++;
                        break;
                    case NORTH:
                        vecToModify.zCoord += 0.0D;
                        break;
                    case EAST:
                        vecToModify.xCoord++;
                        break;
                    case WEST:
                        vecToModify.xCoord += 0.0D;
                        break;
                }
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    boolean limit = (localPlayer.posZ > pos.getZ() + 0.5D);
                    vecToModify.zCoord += limit ? -0.312D : 0.312D;
                }
                if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                    boolean limit = (localPlayer.posX > pos.getX() + 0.5D);
                    vecToModify.xCoord += limit ? -0.312D : 0.312D;
                }
                break;
            case "Morgan":
                vecToModify.xCoord -= 0.01009971204872145D;
                vecToModify.zCoord -= 0.010002380947912738D;
                break;
            case "Verus":
                dynamicVerusVec = ThreadLocalRandom.current().nextDouble(0.826D, 0.827D);
                extraVerusRandomization = ThreadLocalRandom.current().nextDouble(1.0E-4D, 4.0E-4D);
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    vecToModify.zCoord += ThreadLocalRandom.current().nextDouble(0.499D, 0.502D);
                    vecToModify.yCoord += dynamicVerusVec;
                } else if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                    vecToModify.xCoord += ThreadLocalRandom.current().nextDouble(0.499D, 0.501D);
                    vecToModify.yCoord += dynamicVerusVec;
                } else if (facing == EnumFacing.UP) {
                    vecToModify.xCoord += 0.5D;
                    vecToModify.zCoord += 0.5D;
                    vecToModify.yCoord++;
                } else if (facing == EnumFacing.DOWN) {
                    vecToModify.xCoord += 0.5D;
                    vecToModify.zCoord += 0.5D;
                }
                vecToModify.xCoord += extraVerusRandomization;
                vecToModify.yCoord += extraVerusRandomization;
                vecToModify.zCoord += extraVerusRandomization;
                break;
        }
        return vecToModify;
    }

    public float getYaw(BlockPos block, EnumFacing face) {
        Vec3 vecToModify = new Vec3(block.getX(), block.getY(), block.getZ());
        switch (face) {
            case EAST:
            case WEST:
                vecToModify.zCoord += 0.5D;
                break;
            case SOUTH:
            case NORTH:
                vecToModify.xCoord += 0.5D;
                break;
            case UP:
            case DOWN:
                vecToModify.xCoord += 0.5D;
                vecToModify.zCoord += 0.5D;
                break;
        }
        double x = vecToModify.xCoord - mc.thePlayer.posX;
        double z = vecToModify.zCoord - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        if (yaw < 0.0F)
            yaw -= 360.0F;
        return yaw;
    }

    public void placeBlock(ItemStack stack, BlockPos pos, EnumFacing facing, Vec3 vecHit) {
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, pos, facing, vecHit);
    }

    private void updateRenderRotations(EventMotion e) {
        mc.thePlayer.rotationPitchHead = e.getPitch();
        mc.thePlayer.renderYawOffset = e.getYaw();
        mc.thePlayer.rotationYawHead = e.getYaw();
        mc.thePlayer.prevRenderYawOffset = e.getYaw();
    }

    public int getBlockCount() {
        int blockCount = 0;
        for (int a = 0; a < 45; a++) {
            if (mc.thePlayer.inventoryContainer.getSlot(a).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(a).getStack();
                if (is.getItem() instanceof net.minecraft.item.ItemBlock)
                    blockCount += is.stackSize;
            }
        }
        return blockCount;
    }

    public float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg((World)mc.theWorld);
        var4.posX = var0 + 0.5D;
        var4.posY = var1 + 0.5D;
        var4.posZ = var2 + 0.5D;
        var4.posX += var3.getDirectionVec().getX() * 0.25D;
        var4.posY += var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity((Entity)var4);
    }

    public static float[] getDirectionToEntity(Entity var0) {
        return new float[] { getYaw(var0) + localPlayer.rotationYaw, getPitch(var0) + localPlayer.rotationPitch };
    }

    public static float getYaw(Entity var0) {
        double var5, var1 = var0.posX - localPlayer.posX;
        double var3 = var0.posZ - localPlayer.posZ;
        double degrees = Math.toDegrees(Math.atan(var3 / var1));
        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + degrees;
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + degrees;
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }
        return MathHelper.wrapAngleTo180_float(-(localPlayer.rotationYaw - (float)var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - localPlayer.posX;
        double var3 = var0.posZ - localPlayer.posZ;
        double var5 = var0.posY - 1.6D + var0.getEyeHeight() - localPlayer.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(localPlayer.rotationPitch - (float)var9);
    }

    private void setPosAndFace(BlockPos var1) {
        if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            if (keepYEnabled.isEnabled()) {
                boolean towering = (GameSettings.isKeyDown(mc.gameSettings.keyBindJump) && !MovementUtils.isMoving());
                boolean speedToggled = NeutronMain.instance.moduleManager.getModuleByName("Speed").isToggled();
                if (towering || !speedToggled) {
                    cPos = var1.add(0, -1, 0);
                    cFacing = EnumFacing.UP;
                }
            } else {
                cPos = var1.add(0, -1, 0);
                cFacing = EnumFacing.UP;
            }
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, 0, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(1, 0, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, 0, -1);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, 0, 1);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, 0, -1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, 0, 1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, 0, -1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, 0, 1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, 0, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(2, 0, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, 0, -2);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, 0, 2);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, 0, -2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, 0, 2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, 0, -2);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, 0, 2);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -1, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -1, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, -1);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, 1);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -1, -1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -1, 1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -1, -1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -1, 1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -1, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -1, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, -2);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, 2);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -1, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -1, -2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -1, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -1, 2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -1, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -1, -2);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -1, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -1, 2);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-3, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-3, -1, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(3, -1, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(3, -1, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, -3)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, -3);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, -1, 3)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -1, 3);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-3, -1, -3)).getBlock() != Blocks.air) {
            cPos = var1.add(-3, -1, -3);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-3, -1, 3)).getBlock() != Blocks.air) {
            cPos = var1.add(-3, -1, 3);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(3, -1, -3)).getBlock() != Blocks.air) {
            cPos = var1.add(3, -1, -3);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(3, -1, 3)).getBlock() != Blocks.air) {
            cPos = var1.add(3, -1, 3);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -2, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -2, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -2, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -2, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, -2, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -2, -1);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, -2, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -2, 1);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -2, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -2, -1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-1, -2, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(-1, -2, 1);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -2, -1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -2, -1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(1, -2, 1)).getBlock() != Blocks.air) {
            cPos = var1.add(1, -2, 1);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -2, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -2, 0);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -2, 0)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -2, 0);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, -2, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -2, -2);
            cFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, -2, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(0, -2, 2);
            cFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -2, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -2, -2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(-2, -2, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(-2, -2, 2);
            cFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -2, -2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -2, -2);
            cFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(2, -2, 2)).getBlock() != Blocks.air) {
            cPos = var1.add(2, -2, 2);
            cFacing = EnumFacing.WEST;
        } else {
            cPos = null;
            cFacing = null;
        }
    }
}
