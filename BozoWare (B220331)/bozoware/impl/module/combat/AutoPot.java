// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import bozoware.base.util.Wrapper;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.item.ItemSkull;
import bozoware.base.util.misc.TimerUtil;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Auto Potion", moduleCategory = ModuleCategory.COMBAT)
public class AutoPot extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    TimerUtil timer;
    public static int counter;
    
    public AutoPot() {
        this.timer = new TimerUtil();
        final int pcount;
        int i;
        int j;
        boolean speed;
        boolean regen;
        int spoofSlot;
        int[] pots;
        int k;
        this.onUpdatePositionEvent = (event -> {
            pcount = this.getPotionCount();
            this.setModuleSuffix(String.valueOf(pcount));
            if (!(!AutoPot.mc.thePlayer.onGround)) {
                if (event.isPre()) {
                    if (AutoPot.mc.thePlayer.ticksExisted < 200) {
                        return;
                    }
                    else {
                        for (i = 0; i < 9; ++i) {
                            if (AutoPot.mc.thePlayer.inventory.getStackInSlot(i) != null) {
                                if (AutoPot.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemSkull && AutoPot.mc.thePlayer.getHealth() < 5.0f) {
                                    AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                                    AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.getCurrentEquippedItem()));
                                    AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(AutoPot.mc.thePlayer.inventory.currentItem));
                                }
                            }
                        }
                    }
                }
                for (j = 0; j < 9; ++j) {
                    if (AutoPot.mc.thePlayer.inventory.getStackInSlot(j) != null) {
                        if (AutoPot.mc.thePlayer.inventory.getStackInSlot(j).getItem() instanceof ItemSkull && AutoPot.mc.thePlayer.getHealth() < 5.0f) {
                            AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(j));
                            AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.getCurrentEquippedItem()));
                            AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(AutoPot.mc.thePlayer.inventory.currentItem));
                        }
                    }
                }
                speed = true;
                regen = true;
                spoofSlot = this.getBestSpoofSlot();
                pots = new int[] { 6, -1, -1 };
                pots[1] = 10;
                pots[2] = 1;
                for (k = 0; k < pots.length; ++k) {
                    if (pots[k] != -1) {
                        if (pots[k] == 6 || pots[k] == 10) {
                            if (this.timer.hasReached(900L) && !AutoPot.mc.thePlayer.isPotionActive(pots[k]) && AutoPot.mc.thePlayer.getHealth() < 15.0f) {
                                this.getBestPot(spoofSlot, pots[k]);
                            }
                        }
                        else if (this.timer.hasReached(1000L) && !AutoPot.mc.thePlayer.isPotionActive(pots[k])) {
                            this.getBestPot(spoofSlot, pots[k]);
                        }
                    }
                }
            }
        });
    }
    
    public void swap(final int slot1, final int hotbarSlot) {
        AutoPot.mc.playerController.windowClick(AutoPot.mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, AutoPot.mc.thePlayer);
    }
    
    float[] getRotations() {
        final double movedPosX = AutoPot.mc.thePlayer.posX + AutoPot.mc.thePlayer.motionX * 26.0;
        final double movedPosY = AutoPot.mc.thePlayer.boundingBox.minY - 3.6;
        final double movedPosZ = AutoPot.mc.thePlayer.posZ + AutoPot.mc.thePlayer.motionZ * 26.0;
        return getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
    }
    
    int getBestSpoofSlot() {
        int spoofSlot = 5;
        for (int i = 36; i < 45; ++i) {
            if (!AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                spoofSlot = i - 36;
                break;
            }
            if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemPotion) {
                spoofSlot = i - 36;
                break;
            }
        }
        return spoofSlot;
    }
    
    void getBestPot(final int hotbarSlot, final int potID) {
        for (int i = 9; i < 45; ++i) {
            if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (AutoPot.mc.currentScreen == null || AutoPot.mc.currentScreen instanceof GuiInventory)) {
                final ItemStack is = AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemPotion) {
                    final ItemPotion pot = (ItemPotion)is.getItem();
                    if (pot.getEffects(is).isEmpty()) {
                        return;
                    }
                    final PotionEffect effect = pot.getEffects(is).get(0);
                    final int potionID = effect.getPotionID();
                    if (potionID == potID && ItemPotion.isSplash(is.getItemDamage()) && this.isBestPot(pot, is)) {
                        if (36 + hotbarSlot != i) {
                            this.swap(i, hotbarSlot);
                        }
                        this.timer.reset();
                        final boolean canpot = true;
                        final int oldSlot = AutoPot.mc.thePlayer.inventory.currentItem;
                        AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(hotbarSlot));
                        AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.getRotations()[0], this.getRotations()[1], AutoPot.mc.thePlayer.onGround));
                        AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.inventory.getCurrentItem()));
                        AutoPot.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                        break;
                    }
                }
            }
        }
    }
    
    boolean isBestPot(final ItemPotion potion, final ItemStack stack) {
        if (potion.getEffects(stack) == null || potion.getEffects(stack).size() != 1) {
            return false;
        }
        final PotionEffect effect = potion.getEffects(stack).get(0);
        final int potionID = effect.getPotionID();
        final int amplifier = effect.getAmplifier();
        final int duration = effect.getDuration();
        for (int i = 9; i < 45; ++i) {
            if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemPotion) {
                    final ItemPotion pot = (ItemPotion)is.getItem();
                    if (pot.getEffects(is) != null) {
                        for (final Object o : pot.getEffects(is)) {
                            final PotionEffect effects = (PotionEffect)o;
                            final int id = effects.getPotionID();
                            final int ampl = effects.getAmplifier();
                            final int dur = effects.getDuration();
                            if (id == potionID && ItemPotion.isSplash(is.getItemDamage())) {
                                if (ampl > amplifier) {
                                    return false;
                                }
                                if (ampl == amplifier && dur > duration) {
                                    return false;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private int getPotionCount() {
        int potioncount = 0;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemPotion && !isBadPotion(stack) && ItemPotion.isSplash(stack.getItemDamage())) {
                potioncount += stack.stackSize;
            }
        }
        return potioncount;
    }
    
    public static boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
}
