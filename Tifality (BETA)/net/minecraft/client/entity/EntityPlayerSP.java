/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.entity;

import club.tifality.Tifality;
import club.tifality.manager.event.impl.player.MotionEvent;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.SendMessageEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.manager.event.impl.player.UseItemEvent;
import club.tifality.module.impl.movement.NoSlowdown;
import club.tifality.module.impl.movement.Scaffold;
import club.tifality.module.impl.movement.Sprint;
import club.tifality.utils.Rotation;
import club.tifality.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP
extends AbstractClientPlayer {
    public final NetHandlerPlayClient sendQueue;
    public MovementInput movementInput;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    public float timeInPortal;
    public float prevTimeInPortal;
    public float lastReportedYaw;
    public float lastReportedPitch;
    public boolean serverSprintState;
    public UpdatePositionEvent currentEvent;
    protected Minecraft mc;
    protected int sprintToggleTimer;
    private double lastReportedPosX;
    private double lastReportedPosY;
    private double lastReportedPosZ;
    private boolean serverSneakState;
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String clientBrand;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    private final StatFileWriter statWriter;

    public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
        super(worldIn, netHandler.getGameProfile());
        this.sendQueue = netHandler;
        this.statWriter = statFile;
        this.mc = mcIn;
        this.dimension = 0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void heal(float healAmount) {
    }

    public StatFileWriter getStatFileWriter() {
        return this.statWriter;
    }

    @Override
    public void mountEntity(Entity entityIn) {
        super.mountEntity(entityIn);
        if (entityIn instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
    }

    @Override
    public void onUpdate() {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            super.onUpdate();
            this.onUpdatePlayer();
        }
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        MoveEntityEvent moveEntityEvent = new MoveEntityEvent(x, y, z);
        Tifality.getInstance().getEventBus().post(moveEntityEvent);
        if (moveEntityEvent.isCancelled()) {
            return;
        }
        super.moveEntity(moveEntityEvent.getX(), moveEntityEvent.getY(), moveEntityEvent.getZ());
    }

    public void onUpdateWalkingPlayer() {
        try {
            MotionEvent motionEvent = new MotionEvent();
            Tifality.getInstance().getEventBus().post(motionEvent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdatePlayer() {
        boolean riding = this.isRiding();
        if (!riding) {
            boolean flag1;
            boolean flag = this.isSprinting();
            if (flag != this.serverSprintState) {
                if (flag) {
                    this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
                } else {
                    this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                this.serverSprintState = flag;
            }
            if ((flag1 = this.isSneaking()) != this.serverSneakState) {
                if (flag1) {
                    this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
                } else {
                    this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                this.serverSneakState = flag1;
            }
        }
        if (this.isCurrentViewEntity()) {
            UpdatePositionEvent updatePositionEvent = new UpdatePositionEvent(this.lastReportedYaw, this.lastReportedPitch, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, this.rotationYaw, this.rotationPitch, this.onGround);
            Tifality.INSTANCE.getEventBus().post(updatePositionEvent);
            this.currentEvent = updatePositionEvent;
            double eventX = updatePositionEvent.getPosX();
            double eventY = updatePositionEvent.getPosY();
            double eventZ = updatePositionEvent.getPosZ();
            float eventYaw = updatePositionEvent.getYaw();
            float eventPitch = updatePositionEvent.getPitch();
            boolean eventOnGround = updatePositionEvent.isOnGround();
            double xDif = eventX - this.lastReportedPosX;
            double yDif = eventY - this.lastReportedPosY;
            double zDif = eventZ - this.lastReportedPosZ;
            float yawDif = eventYaw - this.lastReportedYaw;
            float pitchDif = eventPitch - this.lastReportedPitch;
            boolean updateXYZ = !(xDif * xDif + yDif * yDif + zDif * zDif <= 9.0E-4) || this.positionUpdateTicks >= 20;
            boolean updateYawPitch = (double)yawDif != 0.0 || (double)pitchDif != 0.0;
            boolean cancelled = updatePositionEvent.isCancelled();
            if (riding) {
                if (!cancelled) {
                    this.sendQueue.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(eventYaw, eventPitch, eventOnGround));
                    this.sendQueue.sendPacket(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
                }
            } else {
                if (!cancelled) {
                    if (updateXYZ && updateYawPitch) {
                        this.sendQueue.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(eventX, eventY, eventZ, eventYaw, eventPitch, eventOnGround));
                    } else if (updateXYZ) {
                        this.sendQueue.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(eventX, eventY, eventZ, eventOnGround));
                    } else if (updateYawPitch) {
                        this.sendQueue.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(eventYaw, eventPitch, eventOnGround));
                    } else {
                        this.sendQueue.sendPacket(new C03PacketPlayer(eventOnGround));
                    }
                }
                ++this.positionUpdateTicks;
                if (updateXYZ) {
                    this.lastReportedPosX = eventX;
                    this.lastReportedPosY = eventY;
                    this.lastReportedPosZ = eventZ;
                    this.positionUpdateTicks = 0;
                }
                if (updateYawPitch) {
                    this.lastReportedYaw = eventYaw;
                    this.lastReportedPitch = eventPitch;
                }
                updatePositionEvent.setPost();
                Tifality.INSTANCE.getEventBus().post(updatePositionEvent);
            }
        }
    }

    @Override
    public EntityItem dropOneItem(boolean dropAll) {
        C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
        this.sendQueue.sendPacket(new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    @Override
    protected void joinEntityItemWithWorld(EntityItem itemIn) {
    }

    public void sendChatMessage(String message) {
        SendMessageEvent event = new SendMessageEvent(message);
        Tifality.getInstance().getEventBus().post(event);
        if (event.isCancelled()) {
            return;
        }
        this.sendQueue.sendPacket(new C01PacketChatMessage(event.getMessage()));
    }

    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.sendPacket(new C0APacketAnimation());
    }

    @Override
    public void respawnPlayer() {
        this.sendQueue.sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (!this.isEntityInvulnerable(damageSrc)) {
            this.setHealth(this.getHealth() - damageAmount);
        }
    }

    @Override
    public void closeScreen() {
        this.sendQueue.sendPacket(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    public void setPlayerSPHealth(float health) {
        if (this.hasValidHealth) {
            float f = this.getHealth() - health;
            if (f <= 0.0f) {
                this.setHealth(health);
                if (f < 0.0f) {
                    this.hurtResistantTime = 10;
                }
            } else {
                this.lastDamage = f;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = 20;
                this.damageEntity(DamageSource.generic, f);
                this.maxHurtTime = 10;
                this.hurtTime = 10;
            }
        } else {
            this.setHealth(health);
            this.hasValidHealth = true;
        }
    }

    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.sendPacket(new C13PacketPlayerAbilities(this.capabilities));
    }

    @Override
    public boolean isUser() {
        return true;
    }

    protected void sendHorseJump() {
        this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0f)));
    }

    public void sendHorseInventory() {
        this.sendQueue.sendPacket(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public String getClientBrand() {
        return this.clientBrand;
    }

    public void setClientBrand(String brand) {
        this.clientBrand = brand;
    }

    @Override
    public void addChatComponentMessage(IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z) {
        if (this.noClip) {
            return false;
        }
        BlockPos blockpos = new BlockPos(x, y, z);
        double d0 = x - (double)blockpos.getX();
        double d1 = z - (double)blockpos.getZ();
        if (!this.isOpenBlockSpace(blockpos)) {
            int i = -1;
            double d2 = 9999.0;
            if (this.isOpenBlockSpace(blockpos.west()) && d0 < d2) {
                d2 = d0;
                i = 0;
            }
            if (this.isOpenBlockSpace(blockpos.east()) && 1.0 - d0 < d2) {
                d2 = 1.0 - d0;
                i = 1;
            }
            if (this.isOpenBlockSpace(blockpos.north()) && d1 < d2) {
                d2 = d1;
                i = 4;
            }
            if (this.isOpenBlockSpace(blockpos.south()) && 1.0 - d1 < d2) {
                d2 = 1.0 - d1;
                i = 5;
            }
            float f = 0.1f;
            if (i == 0) {
                this.motionX = -f;
            }
            if (i == 1) {
                this.motionX = f;
            }
            if (i == 4) {
                this.motionZ = -f;
            }
            if (i == 5) {
                this.motionZ = f;
            }
        }
        return false;
    }

    private boolean isOpenBlockSpace(BlockPos pos) {
        return !this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = sprinting ? 600 : 0;
    }

    public void setXPStats(float currentXP, int maxXP, int level) {
        this.experience = currentXP;
        this.experienceTotal = maxXP;
        this.experienceLevel = level;
    }

    @Override
    public void addChatMessage(IChatComponent component) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    @Override
    public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
        return permLevel <= 0;
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }

    @Override
    public void playSound(String name, float volume, float pitch) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
    }

    @Override
    public boolean isServerWorld() {
        return true;
    }

    public boolean isRidingHorse() {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    @Override
    public void openEditSign(TileEntitySign signTile) {
        this.mc.displayGuiScreen(new GuiEditSign(signTile));
    }

    @Override
    public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(cmdBlockLogic));
    }

    @Override
    public void displayGUIBook(ItemStack bookStack) {
        Item item = bookStack.getItem();
        if (item == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
        }
    }

    @Override
    public void displayGUIChest(IInventory chestInventory) {
        String s;
        String string = s = chestInventory instanceof IInteractionObject ? ((IInteractionObject)((Object)chestInventory)).getGuiID() : "minecraft:container";
        if ("minecraft:chest".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        } else if ("minecraft:hopper".equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
        } else if ("minecraft:furnace".equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
        } else if ("minecraft:brewing_stand".equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
        } else if ("minecraft:beacon".equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
        } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        } else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
        }
    }

    @Override
    public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, horseInventory, horse));
    }

    @Override
    public void displayGui(IInteractionObject guiOwner) {
        String s = guiOwner.getGuiID();
        if ("minecraft:crafting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        } else if ("minecraft:enchanting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
        } else if ("minecraft:anvil".equals(s)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }

    @Override
    public void displayVillagerTradeGui(IMerchant villager) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.worldObj));
    }

    @Override
    public void onCriticalHit(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }

    @Override
    public void onEnchantmentCritical(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

    @Override
    public boolean isSneaking() {
        boolean flag = this.movementInput != null && this.movementInput.sneak;
        return flag && !this.sleeping;
    }

    @Override
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5);
            this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5);
        }
    }

    protected boolean isCurrentViewEntity() {
        return this.mc.getRenderViewEntity() == this;
    }

    @Override
    public void onLivingUpdate() {
        boolean flag3;
        Sprint sprint;
        float f;
        boolean flag;
        block46: {
            block45: {
                if (this.sprintingTicksLeft > 0) {
                    --this.sprintingTicksLeft;
                    if (this.sprintingTicksLeft == 0) {
                        this.setSprinting(false);
                    }
                }
                if (this.sprintToggleTimer > 0) {
                    --this.sprintToggleTimer;
                }
                this.prevTimeInPortal = this.timeInPortal;
                if (this.inPortal) {
                    if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                        this.mc.displayGuiScreen(null);
                    }
                    if (this.timeInPortal == 0.0f) {
                        this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4f + 0.8f));
                    }
                    this.timeInPortal += 0.0125f;
                    if (this.timeInPortal >= 1.0f) {
                        this.timeInPortal = 1.0f;
                    }
                    this.inPortal = false;
                } else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
                    this.timeInPortal += 0.006666667f;
                    if (this.timeInPortal > 1.0f) {
                        this.timeInPortal = 1.0f;
                    }
                } else {
                    if (this.timeInPortal > 0.0f) {
                        this.timeInPortal -= 0.05f;
                    }
                    if (this.timeInPortal < 0.0f) {
                        this.timeInPortal = 0.0f;
                    }
                }
                if (this.timeUntilPortal > 0) {
                    --this.timeUntilPortal;
                }
                flag = this.movementInput.jump;
                boolean flag1 = this.movementInput.sneak;
                f = 0.8f;
                boolean flag2 = this.movementInput.moveForward >= f;
                this.movementInput.updatePlayerMoveState();
                if (this.isUsingItem() && !this.isRiding()) {
                    UseItemEvent event = new UseItemEvent();
                    Tifality.getInstance().getEventBus().post(event);
                    if (!event.isCancelled()) {
                        this.movementInput.moveStrafe *= 0.2f;
                        this.movementInput.moveForward *= 0.2f;
                        this.sprintToggleTimer = 0;
                    }
                }
                this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + (double)this.width * 0.35);
                this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - (double)this.width * 0.35);
                this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - (double)this.width * 0.35);
                this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + (double)this.width * 0.35);
                sprint = Tifality.INSTANCE.getModuleManager().getModule(Sprint.class);
                boolean bl = flag3 = sprint.foodValue.get() == false || (float)this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
                if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                    if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                        this.sprintToggleTimer = 7;
                    } else {
                        this.setSprinting(true);
                    }
                }
                Scaffold scaffold = Tifality.INSTANCE.getModuleManager().getModule(Scaffold.class);
                NoSlowdown noSlow = Tifality.INSTANCE.getModuleManager().getModule(NoSlowdown.class);
                if (!this.isSprinting() && this.movementInput.moveForward >= f && flag3 && (noSlow.isEnabled() || !this.isUsingItem()) && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                    this.setSprinting(true);
                }
                if (scaffold.isEnabled() && !scaffold.sprintValue.get().booleanValue()) break block45;
                if (!sprint.isEnabled() || !sprint.checkServerSide.get().booleanValue() || !this.onGround && sprint.checkServerSideGround.get().booleanValue() || sprint.allDirectionsValue.get().booleanValue() || RotationUtils.targetRotation == null) break block46;
                Rotation rotation = new Rotation(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
                if (!(RotationUtils.getRotationDifference(rotation) > 30.0)) break block46;
            }
            this.setSprinting(false);
        }
        if (this.isSprinting() && ((!sprint.isEnabled() || !sprint.allDirectionsValue.get().booleanValue()) && this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3)) {
            this.setSprinting(false);
        }
        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
            if (this.movementInput.jump) {
                this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
        }
        if (this.isRidingHorse()) {
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            } else if (flag) {
                ++this.horseJumpPowerCounter;
                this.horseJumpPower = this.horseJumpPowerCounter < 10 ? (float)this.horseJumpPowerCounter * 0.1f : 0.8f + 2.0f / (float)(this.horseJumpPowerCounter - 9) * 0.1f;
            }
        } else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
}

