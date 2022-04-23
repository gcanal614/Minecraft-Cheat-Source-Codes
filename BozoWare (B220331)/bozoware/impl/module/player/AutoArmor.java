// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import java.util.List;
import bozoware.base.util.misc.TimerUtil;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Auto Armor", moduleCategory = ModuleCategory.PLAYER)
public class AutoArmor extends Module
{
    public TimerUtil timer;
    private int[] chestplate;
    private int[] leggings;
    private List[] allArmors;
    private int[] boots;
    private int[] helmet;
    private boolean dropping;
    private double delay;
    private int[] bestArmorSlot;
    private boolean best;
    private boolean equipping;
    double a;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Long> Speed;
    private final BooleanProperty openinv;
    
    public AutoArmor() {
        this.timer = new TimerUtil();
        this.allArmors = new List[4];
        this.Speed = new ValueProperty<Long>("Speed", 100L, 0L, 200L, this);
        this.openinv = new BooleanProperty("OpenInv", true, this);
        this.onUpdatePositionEvent = (e -> {
            if (!this.openinv.getPropertyValue() || AutoArmor.mc.currentScreen instanceof GuiInventory) {
                if (AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) {
                    if (this.timer.hasReached((long)this.delay)) {
                        this.getBestArmor();
                    }
                    if (!this.dropping) {
                        if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached((long)this.delay)) {
                            this.getBestArmor();
                        }
                    }
                    else if (this.timer.hasReached((long)this.delay)) {
                        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, AutoArmor.mc.thePlayer);
                        this.timer.reset();
                        this.dropping = false;
                    }
                }
            }
        });
    }
    
    private boolean checkDelay() {
        return !this.timer.hasReached(this.Speed.getPropertyValue());
    }
    
    public void drop(final int slot) {
        if (this.timer.hasReached((long)this.delay * 50L) && !this.dropping) {
            AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, AutoArmor.mc.thePlayer);
            this.dropping = true;
            this.timer.reset();
        }
    }
    
    public void getBestArmor() {
        for (int type = 1; type < 5; ++type) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                final ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (isBestArmor(is, type)) {
                    continue;
                }
                this.drop(4 + type);
            }
            for (int i = 9; i < 45; ++i) {
                if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack is2 = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (isBestArmor(is2, type) && getProtection(is2) > 0.0f) {
                        this.shiftClick(i);
                        this.timer.reset();
                        if (this.delay > 0.0) {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public void shiftClick(final int slot) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, AutoArmor.mc.thePlayer);
    }
    
    public static boolean isBestArmor(final ItemStack stack, final int type) {
        final float prot = getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        }
        else if (type == 2) {
            strType = "chestplate";
        }
        else if (type == 3) {
            strType = "leggings";
        }
        else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static float getProtection(final ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            prot += (float)(armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0);
        }
        return prot;
    }
}
