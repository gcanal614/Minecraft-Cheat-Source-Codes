// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import java.util.Iterator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.client.gui.inventory.GuiChest;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.misc.TimerUtil;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Chest Stealer", moduleCategory = ModuleCategory.PLAYER)
public class ChestStealer extends Module
{
    public TimerUtil timer;
    double a;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Double> Speed;
    private final BooleanProperty autoClose;
    private final BooleanProperty titleCheck;
    
    public ChestStealer() {
        this.timer = new TimerUtil();
        this.Speed = new ValueProperty<Double>("Speed", 100.0, 50.0, 200.0, this);
        this.autoClose = new BooleanProperty("Auto Close", true, this);
        this.titleCheck = new BooleanProperty("Title Check", true, this);
        this.onModuleDisabled = (() -> {});
        this.onModuleEnabled = (() -> this.a = this.Speed.getPropertyValue());
        GuiChest chest1;
        ContainerChest chest2;
        int i;
        this.onUpdatePositionEvent = (e -> {
            if (!(!(ChestStealer.mc.currentScreen instanceof GuiChest))) {
                if (ChestStealer.mc.thePlayer != null && ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
                    chest1 = (GuiChest)ChestStealer.mc.currentScreen;
                    for (chest2 = (ContainerChest)ChestStealer.mc.thePlayer.openContainer, i = 0; i < chest2.getLowerChestInventory().getSizeInventory(); ++i) {
                        if (this.titleCheck.getPropertyValue() && ((chest2.getLowerChestInventory().getStackInSlot(i) != null && chest1.lowerChestInventory.getDisplayName().getUnformattedText().contains("Chest")) || chest1.lowerChestInventory.getDisplayName().getUnformattedText().contains("Il y a 3 \u00e9toiles dans ce coffre")) && this.timer.hasReached((long)this.a) && !isTrash(chest2.getLowerChestInventory().getStackInSlot(i))) {
                            ChestStealer.mc.playerController.windowClick(chest2.windowId, i, 0, 1, ChestStealer.mc.thePlayer);
                            this.timer.reset();
                        }
                    }
                    if (this.autoClose.getPropertyValue() && (this.isChestEmpty(chest1) || this.isInventoryFull())) {
                        ChestStealer.mc.thePlayer.closeScreen();
                    }
                }
            }
        });
    }
    
    private boolean isInventoryFull() {
        for (int i = 9; i < 45; ++i) {
            if (!ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && !ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
            if (stack != null && !isTrash(stack)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isTrash(final ItemStack item) {
        return item.getItem().getUnlocalizedName().contains("tnt") || item.getDisplayName().contains("frog") || item.getItem().getUnlocalizedName().contains("stick") || item.getItem().getUnlocalizedName().contains("string") || item.getItem().getUnlocalizedName().contains("flint") || item.getItem().getUnlocalizedName().contains("feather") || item.getItem().getUnlocalizedName().contains("bucket") || item.getItem().getUnlocalizedName().contains("snow") || item.getItem().getUnlocalizedName().contains("enchant") || item.getItem().getUnlocalizedName().contains("exp") || item.getItem().getUnlocalizedName().contains("shears") || item.getItem().getUnlocalizedName().contains("arrow") || item.getItem().getUnlocalizedName().contains("anvil") || item.getItem().getUnlocalizedName().contains("torch") || item.getItem().getUnlocalizedName().contains("seeds") || item.getItem().getUnlocalizedName().contains("leather") || item.getItem().getUnlocalizedName().contains("boat") || item.getItem().getUnlocalizedName().contains("fishing") || item.getItem().getUnlocalizedName().contains("wheat") || item.getItem().getUnlocalizedName().contains("flower") || item.getItem().getUnlocalizedName().contains("record") || item.getItem().getUnlocalizedName().contains("note") || item.getItem().getUnlocalizedName().contains("sugar") || item.getItem().getUnlocalizedName().contains("wire") || item.getItem().getUnlocalizedName().contains("trip") || item.getItem().getUnlocalizedName().contains("slime") || item.getItem().getUnlocalizedName().contains("web") || item.getItem() instanceof ItemGlassBottle || item.getItem().getUnlocalizedName().contains("piston") || (item.getItem().getUnlocalizedName().contains("potion") && isBadPotion(item)) || item.getItem() instanceof ItemEgg || (item.getItem().getUnlocalizedName().contains("bow") && !item.getDisplayName().contains("Kit")) || item.getItem().getUnlocalizedName().contains("Raw");
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
}
