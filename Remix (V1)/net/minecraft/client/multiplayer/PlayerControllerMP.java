package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.network.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.passive.*;

public class PlayerControllerMP
{
    private final Minecraft mc;
    private final NetHandlerPlayClient netClientHandler;
    public float curBlockDamageMP;
    public int blockHitDelay;
    public WorldSettings.GameType currentGameType;
    private BlockPos field_178895_c;
    private ItemStack currentItemHittingBlock;
    private float stepSoundTickCounter;
    private boolean isHittingBlock;
    private int currentPlayerItem;
    
    public PlayerControllerMP(final Minecraft mcIn, final NetHandlerPlayClient p_i45062_2_) {
        this.field_178895_c = new BlockPos(-1, -1, -1);
        this.currentGameType = WorldSettings.GameType.SURVIVAL;
        this.mc = mcIn;
        this.netClientHandler = p_i45062_2_;
    }
    
    public static void func_178891_a(final Minecraft mcIn, final PlayerControllerMP p_178891_1_, final BlockPos p_178891_2_, final EnumFacing p_178891_3_) {
        if (!mcIn.theWorld.func_175719_a(mcIn.thePlayer, p_178891_2_, p_178891_3_)) {
            p_178891_1_.func_178888_a(p_178891_2_, p_178891_3_);
        }
    }
    
    public void setPlayerCapabilities(final EntityPlayer p_78748_1_) {
        this.currentGameType.configurePlayerCapabilities(p_78748_1_.capabilities);
    }
    
    public boolean enableEverythingIsScrewedUpMode() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public void setGameType(final WorldSettings.GameType p_78746_1_) {
        (this.currentGameType = p_78746_1_).configurePlayerCapabilities(this.mc.thePlayer.capabilities);
    }
    
    public void flipPlayer(final EntityPlayer playerIn) {
        playerIn.rotationYaw = -180.0f;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean func_178888_a(final BlockPos p_178888_1_, final EnumFacing p_178888_2_) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!this.mc.thePlayer.func_175142_cm()) {
                final Block var3 = this.mc.theWorld.getBlockState(p_178888_1_).getBlock();
                final ItemStack var4 = this.mc.thePlayer.getCurrentEquippedItem();
                if (var4 == null) {
                    return false;
                }
                if (!var4.canDestroy(var3)) {
                    return false;
                }
            }
        }
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        final WorldClient var5 = this.mc.theWorld;
        final IBlockState var6 = var5.getBlockState(p_178888_1_);
        final Block var7 = var6.getBlock();
        if (var7.getMaterial() == Material.air) {
            return false;
        }
        var5.playAuxSFX(2001, p_178888_1_, Block.getStateId(var6));
        final boolean var8 = var5.setBlockToAir(p_178888_1_);
        if (var8) {
            var7.onBlockDestroyedByPlayer(var5, p_178888_1_, var6);
        }
        this.field_178895_c = new BlockPos(this.field_178895_c.getX(), -1, this.field_178895_c.getZ());
        if (!this.currentGameType.isCreative()) {
            final ItemStack var9 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var9 != null) {
                var9.onBlockDestroyed(var5, var7, p_178888_1_, this.mc.thePlayer);
                if (var9.stackSize == 0) {
                    this.mc.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return var8;
    }
    
    public boolean func_180511_b(final BlockPos p_180511_1_, final EnumFacing p_180511_2_) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!this.mc.thePlayer.func_175142_cm()) {
                final Block var3 = this.mc.theWorld.getBlockState(p_180511_1_).getBlock();
                final ItemStack var4 = this.mc.thePlayer.getCurrentEquippedItem();
                if (var4 == null) {
                    return false;
                }
                if (!var4.canDestroy(var3)) {
                    return false;
                }
            }
        }
        if (!this.mc.theWorld.getWorldBorder().contains(p_180511_1_)) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180511_1_, p_180511_2_));
            func_178891_a(this.mc, this, p_180511_1_, p_180511_2_);
            this.blockHitDelay = 5;
        }
        else if (!this.isHittingBlock || !this.func_178893_a(p_180511_1_)) {
            if (this.isHittingBlock) {
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, p_180511_2_));
            }
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180511_1_, p_180511_2_));
            final Block var3 = this.mc.theWorld.getBlockState(p_180511_1_).getBlock();
            final boolean var5 = var3.getMaterial() != Material.air;
            if (var5 && this.curBlockDamageMP == 0.0f) {
                var3.onBlockClicked(this.mc.theWorld, p_180511_1_, this.mc.thePlayer);
            }
            if (var5 && var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, p_180511_1_) >= 1.0f) {
                this.func_178888_a(p_180511_1_, p_180511_2_);
            }
            else {
                this.isHittingBlock = true;
                this.field_178895_c = p_180511_1_;
                this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0f) - 1);
            }
        }
        return true;
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, EnumFacing.DOWN));
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0f;
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, -1);
        }
    }
    
    public boolean func_180512_c(final BlockPos p_180512_1_, final EnumFacing p_180512_2_) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
            return true;
        }
        if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(p_180512_1_)) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
            func_178891_a(this.mc, this, p_180512_1_, p_180512_2_);
            return true;
        }
        if (!this.func_178893_a(p_180512_1_)) {
            return this.func_180511_b(p_180512_1_, p_180512_2_);
        }
        final Block var3 = this.mc.theWorld.getBlockState(p_180512_1_).getBlock();
        if (var3.getMaterial() == Material.air) {
            return this.isHittingBlock = false;
        }
        this.curBlockDamageMP += var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, p_180512_1_);
        if (this.stepSoundTickCounter % 4.0f == 0.0f) {
            this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var3.stepSound.getStepSound()), (var3.stepSound.getVolume() + 1.0f) / 8.0f, var3.stepSound.getFrequency() * 0.5f, p_180512_1_.getX() + 0.5f, p_180512_1_.getY() + 0.5f, p_180512_1_.getZ() + 0.5f));
        }
        ++this.stepSoundTickCounter;
        if (this.curBlockDamageMP >= 1.0f) {
            this.isHittingBlock = false;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
            this.func_178888_a(p_180512_1_, p_180512_2_);
            this.curBlockDamageMP = 0.0f;
            this.stepSoundTickCounter = 0.0f;
            this.blockHitDelay = 5;
        }
        this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0f) - 1);
        return true;
    }
    
    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
        }
        else {
            this.netClientHandler.getNetworkManager().checkDisconnected();
        }
    }
    
    private boolean func_178893_a(final BlockPos p_178893_1_) {
        final ItemStack var2 = this.mc.thePlayer.getHeldItem();
        boolean var3 = this.currentItemHittingBlock == null && var2 == null;
        if (this.currentItemHittingBlock != null && var2 != null) {
            var3 = (var2.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(var2, this.currentItemHittingBlock) && (var2.isItemStackDamageable() || var2.getMetadata() == this.currentItemHittingBlock.getMetadata()));
        }
        return p_178893_1_.equals(this.field_178895_c) && var3;
    }
    
    public void syncCurrentPlayItem() {
        final int var1 = this.mc.thePlayer.inventory.currentItem;
        if (var1 != this.currentPlayerItem) {
            this.currentPlayerItem = var1;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
        }
    }
    
    public boolean func_178890_a(final EntityPlayerSP p_178890_1_, final WorldClient p_178890_2_, final ItemStack p_178890_3_, final BlockPos p_178890_4_, final EnumFacing p_178890_5_, final Vec3 p_178890_6_) {
        this.syncCurrentPlayItem();
        final float var7 = (float)(p_178890_6_.xCoord - p_178890_4_.getX());
        final float var8 = (float)(p_178890_6_.yCoord - p_178890_4_.getY());
        final float var9 = (float)(p_178890_6_.zCoord - p_178890_4_.getZ());
        boolean var10 = false;
        if (!this.mc.theWorld.getWorldBorder().contains(p_178890_4_)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState var11 = p_178890_2_.getBlockState(p_178890_4_);
            if ((!p_178890_1_.isSneaking() || p_178890_1_.getHeldItem() == null) && var11.getBlock().onBlockActivated(p_178890_2_, p_178890_4_, var11, p_178890_1_, p_178890_5_, var7, var8, var9)) {
                var10 = true;
            }
            if (!var10 && p_178890_3_ != null && p_178890_3_.getItem() instanceof ItemBlock) {
                final ItemBlock var12 = (ItemBlock)p_178890_3_.getItem();
                if (!var12.canPlaceBlockOnSide(p_178890_2_, p_178890_4_, p_178890_5_, p_178890_1_, p_178890_3_)) {
                    return false;
                }
            }
        }
        final BlockPlaceEvent event = new BlockPlaceEvent(p_178890_3_, p_178890_4_, p_178890_5_, p_178890_6_);
        Base.INSTANCE.getEventManager().emit(event);
        if (event.isCancelled()) {
            return false;
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(p_178890_4_, p_178890_5_.getIndex(), p_178890_1_.inventory.getCurrentItem(), var7, var8, var9));
        if (var10 || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return true;
        }
        if (p_178890_3_ == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int var13 = p_178890_3_.getMetadata();
            final int var14 = p_178890_3_.stackSize;
            final boolean var15 = p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
            p_178890_3_.setItemDamage(var13);
            p_178890_3_.stackSize = var14;
            return var15;
        }
        return p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
    }
    
    public boolean sendUseItem(final EntityPlayer playerIn, final World worldIn, final ItemStack itemStackIn) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
        final int var4 = itemStackIn.stackSize;
        final ItemStack var5 = itemStackIn.useItemRightClick(worldIn, playerIn);
        if (var5 == itemStackIn && (var5 == null || var5.stackSize == var4)) {
            return false;
        }
        playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = var5;
        if (var5.stackSize == 0) {
            playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
        }
        return true;
    }
    
    public boolean sendFakeUseItem(final EntityPlayer playerIn, final World worldIn, final ItemStack itemStackIn) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.syncCurrentPlayItem();
        final int var4 = itemStackIn.stackSize - 1;
        final ItemStack var5 = itemStackIn.useItemRightClick(worldIn, playerIn);
        if (var5 == itemStackIn && (var5 == null || var5.stackSize == var4)) {
            return false;
        }
        playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = var5;
        if (var5.stackSize == 0) {
            playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
        }
        return true;
    }
    
    public EntityPlayerSP func_178892_a(final World worldIn, final StatFileWriter p_178892_2_) {
        return new EntityPlayerSP(this.mc, worldIn, this.netClientHandler, p_178892_2_);
    }
    
    public void attackEntity(final EntityPlayer playerIn, final Entity targetEntity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            playerIn.attackTargetEntityWithCurrentItem(targetEntity);
        }
    }
    
    public boolean interactWithEntitySendPacket(final EntityPlayer playerIn, final Entity targetEntity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity);
    }
    
    public boolean func_178894_a(final EntityPlayer p_178894_1_, final Entity p_178894_2_, final MovingObjectPosition p_178894_3_) {
        this.syncCurrentPlayItem();
        final Vec3 var4 = new Vec3(p_178894_3_.hitVec.xCoord - p_178894_2_.posX, p_178894_3_.hitVec.yCoord - p_178894_2_.posY, p_178894_3_.hitVec.zCoord - p_178894_2_.posZ);
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(p_178894_2_, var4));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && p_178894_2_.func_174825_a(p_178894_1_, var4);
    }
    
    public ItemStack windowClick(final int windowId, final int slotId, final int mouseButtonClicked, final int mode, final EntityPlayer playerIn) {
        final short short1 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
        final ItemStack itemstack = playerIn.openContainer.slotClick(slotId, mouseButtonClicked, mode, playerIn);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(windowId, slotId, mouseButtonClicked, mode, itemstack, short1));
        return itemstack;
    }
    
    public void sendEnchantPacket(final int p_78756_1_, final int p_78756_2_) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
    }
    
    public void sendSlotPacket(final ItemStack itemStackIn, final int slotId) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(slotId, itemStackIn));
        }
    }
    
    public void sendPacketDropItem(final ItemStack itemStackIn) {
        if (this.currentGameType.isCreative() && itemStackIn != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, itemStackIn));
        }
    }
    
    public void onStoppedUsingItem(final EntityPlayer playerIn) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        playerIn.stopUsingItem();
    }
    
    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
    
    public boolean isRidingHorse() {
        return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
    }
    
    public boolean isSpectatorMode() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public WorldSettings.GameType func_178889_l() {
        return this.currentGameType;
    }
    
    public boolean onPlayerRightClick(final EntityPlayer player, final WorldClient worldIn, final ItemStack heldStack, final BlockPos hitPos, final EnumFacing side, final Vec3 hitVec) {
        this.syncCurrentPlayItem();
        final float f = (float)(hitVec.xCoord - hitPos.getX());
        final float f2 = (float)(hitVec.yCoord - hitPos.getY());
        final float f3 = (float)(hitVec.zCoord - hitPos.getZ());
        boolean flag = false;
        if (!this.mc.theWorld.getWorldBorder().contains(hitPos)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState iblockstate = worldIn.getBlockState(hitPos);
            if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, player, side, f, f2, f3)) {
                flag = true;
            }
            if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
                final ItemBlock itemblock = (ItemBlock)heldStack.getItem();
                if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack)) {
                    return false;
                }
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), player.inventory.getCurrentItem(), f, f2, f3));
        if (flag || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return true;
        }
        if (heldStack == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int i = heldStack.getMetadata();
            final int j = heldStack.stackSize;
            final boolean flag2 = heldStack.onItemUse(player, worldIn, hitPos, side, f, f2, f3);
            heldStack.setItemDamage(i);
            heldStack.stackSize = j;
            return flag2;
        }
        return heldStack.onItemUse(player, worldIn, hitPos, side, f, f2, f3);
    }
    
    public boolean MoonplexBypass(final EntityPlayer player, final WorldClient worldIn, final ItemStack heldStack, final BlockPos hitPos, final EnumFacing side, final Vec3 hitVec) {
        this.syncCurrentPlayItem();
        final float f = (float)(hitVec.xCoord - hitPos.getX());
        final float f2 = (float)(hitVec.yCoord - hitPos.getY());
        final float f3 = (float)(hitVec.zCoord - hitPos.getZ());
        boolean flag = false;
        if (!this.mc.theWorld.getWorldBorder().contains(hitPos)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState iblockstate = worldIn.getBlockState(hitPos);
            if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, player, side, f, f2, f3)) {
                flag = true;
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), player.inventory.getCurrentItem(), f, f2, f3));
        return !flag && this.currentGameType != WorldSettings.GameType.SPECTATOR && false;
    }
    
    public boolean sendUseItemNCP(final EntityPlayer playerIn, final World worldIn, final ItemStack itemStackIn) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
        this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
        return true;
    }
}
