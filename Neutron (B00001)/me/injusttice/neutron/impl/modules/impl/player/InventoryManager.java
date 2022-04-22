package me.injusttice.neutron.impl.modules.impl.player;

import java.util.concurrent.ThreadLocalRandom;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.other.AutoHypixel;
import me.injusttice.neutron.utils.player.TimerHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class InventoryManager extends Module {
    public DoubleSet delaySet = new DoubleSet("Delay", 200.0D, 20.0D, 1000.0D, 5.0D, " ms");

    public DoubleSet randomizationSet = new DoubleSet("Randomization", 20.0D, 0.0D, 400.0D, 5.0D, " ms");

    public BooleanSet cleanBad = new BooleanSet("Clean", true);

    public ModuleCategory limitsCategory = new ModuleCategory("Limits...");

    public DoubleSet blocksLimit = new DoubleSet("Blocks", 64.0D, 0.0D, 320.0D, 16.0D);

    public ModuleCategory slotsCategory = new ModuleCategory("Slots...");

    public DoubleSet swordSlot = new DoubleSet("Sword Slot", 1.0D, 1.0D, 9.0D, 1.0D);

    public DoubleSet axeSlot = new DoubleSet("Axe Slot", 2.0D, 1.0D, 9.0D, 1.0D);

    public DoubleSet pickaxeSlot = new DoubleSet("Pickaxe Slot", 3.0D, 1.0D, 9.0D, 1.0D);

    public DoubleSet gappleSlot = new DoubleSet("Gapple Slot", 4.0D, 1.0D, 9.0D, 1.0D);

    public DoubleSet blockSlot = new DoubleSet("Block Slot", 8.0D, 1.0D, 9.0D, 1.0D);

    public DoubleSet potionSlot = new DoubleSet("Potion Slot", 7.0D, 1.0D, 9.0D, 1.0D);

    public ModuleCategory checksCategory = new ModuleCategory("Checks...");

    public BooleanSet onlyInventory = new BooleanSet("Only Inventory", false);

    public BooleanSet editCheck = new BooleanSet("Edit Check", true);

    public BooleanSet noInventoryCheck = new BooleanSet("No ChestGui", true);

    public Setting[] settingList = new Setting[] { delaySet, randomizationSet, cleanBad, limitsCategory, slotsCategory, checksCategory };

    public void addSettingsToCats() {
        this.limitsCategory.addCatSettings(blocksLimit );
        this.slotsCategory.addCatSettings(swordSlot, axeSlot, pickaxeSlot, gappleSlot, blockSlot, potionSlot);
        this.checksCategory.addCatSettings(onlyInventory, editCheck, noInventoryCheck);
    }

    private TimerHelper delayTimer = new TimerHelper();

    public InventoryManager() {
        super("InventoryManager", 0, Category.PLAYER);
        addSettings(this.settingList);
        addSettingsToCats();
    }

    @EventTarget
    public void onPacket(EventSendPacket e) {
        if (e.getPacket() instanceof net.minecraft.network.play.client.C0DPacketCloseWindow || e.getPacket() instanceof net.minecraft.network.play.client.C07PacketPlayerDigging || e.getPacket() instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement)
            this.delayTimer.resetWithOffset(150L);
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        this.setDisplayName("Inventory Manager");
        int gapple = getGapple();
        int potion = getPotion();
        int bestSword = getBestSword();
        int bestPick = getBestPickaxe();
        int bestAxe = getBestAxe();
        int bestShovel = getBestShovel();
        int blocks = getBlocks();
        if (this.onlyInventory.isEnabled() &&
                !(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer))
            return;
        if (this.editCheck.isEnabled() &&
                !localPlayer.isAllowEdit())
            return;
        if (this.noInventoryCheck.isEnabled() &&
                this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiChest)
            return;
        long finalDelay = (long)Math.max(0.0D, this.delaySet.getValue() + ThreadLocalRandom.current().nextDouble(0.0D, this.randomizationSet.getValue() + 1.0D));
        if (this.delayTimer.timeElapsed(finalDelay)) {
            for (int k = 0; k < Math.min(localPlayer.inventory.mainInventory.length, 879123742); k++) {
                ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
                if (is != null && !(is.getItem() instanceof net.minecraft.item.ItemArmor)) {
                    int gappleSlot = (int)(this.gappleSlot.getValue() - 1.0D);
                    boolean gappleBoolean = true;
                    if (localPlayer.inventory.getStackInSlot(gappleSlot) != null)
                        gappleBoolean = (localPlayer.inventory.getStackInSlot(gappleSlot).getItem() != Items.golden_apple);
                    if (gapple != -1 && gapple != gappleSlot && gappleBoolean)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[gapple]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, gappleSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    int potionSlot = (int)(this.potionSlot.getValue() - 1.0D);
                    boolean potionBoolean = true;
                    if (localPlayer.inventory.getStackInSlot(potionSlot) != null)
                        potionBoolean = !(localPlayer.inventory.getStackInSlot(potionSlot).getItem() instanceof net.minecraft.item.ItemPotion);
                    if (potion != -1 && potion != potionSlot && potionBoolean)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[potion]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, potionSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    int blockSlot = (int)(this.blockSlot.getValue() - 1.0D);
                    boolean blockBoolean = true;
                    if (localPlayer.inventory.getStackInSlot(blockSlot) != null)
                        blockBoolean = !(localPlayer.inventory.getStackInSlot(blockSlot).getItem() instanceof net.minecraft.item.ItemBlock);
                    if (blocks != -1 && blocks != blockSlot && blockBoolean)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[blocks]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, blockSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    int swordSlot = (int)(this.swordSlot.getValue() - 1.0D);
                    if (bestSword != -1 && bestSword != swordSlot)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[bestSword]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, swordSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    int axeSlot = (int)(this.axeSlot.getValue() - 1.0D);
                    if (bestAxe != -1 && bestAxe != axeSlot)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[bestAxe]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, axeSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    int pickaxeSlot = (int)(this.pickaxeSlot.getValue() - 1.0D);
                    if (bestPick != -1 && bestPick != pickaxeSlot)
                        for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); ) {
                            Slot s = this.mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                            if (!s.getHasStack() || s.getStack() != this.mc.thePlayer.inventory.mainInventory[bestPick]) {
                                i++;
                                continue;
                            }
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, s.slotNumber, pickaxeSlot, 2, (EntityPlayer)this.mc.thePlayer);
                            this.delayTimer.reset();
                            return;
                        }
                    if (this.cleanBad.isEnabled() && isBad(is.getItem())) {
                        drop(k, is);
                        this.delayTimer.reset();
                        return;
                    }
                    boolean clean = this.cleanBad.isEnabled();
                    if (clean) {
                        if (is.getItem() instanceof ItemSword &&
                                bestSword != -1 && bestSword != k) {
                            drop(k, is);
                            this.delayTimer.reset();
                            return;
                        }
                        if (is.getItem() instanceof ItemPickaxe &&
                                bestPick != -1 && bestPick != k) {
                            drop(k, is);
                            this.delayTimer.reset();
                            return;
                        }
                        if (is.getItem() instanceof ItemAxe &&
                                bestAxe != -1 && bestAxe != k) {
                            drop(k, is);
                            this.delayTimer.reset();
                            return;
                        }
                        if (isShovel(is.getItem()) &&
                                bestShovel != -1 && bestShovel != k) {
                            drop(k, is);
                            this.delayTimer.reset();
                            return;
                        }
                    }
                    this.delayTimer.reset();
                }
            }
            this.delayTimer.reset();
        }
    }

    private int getGapple() {
        int gapple = -1;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() == Items.golden_apple)
                gapple = k;
        }
        return gapple;
    }

    private int getPotion() {
        int potion = -1;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof net.minecraft.item.ItemPotion)
                potion = k;
        }
        return potion;
    }

    private int getBlocks() {
        int bestBlocks = -1;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof net.minecraft.item.ItemBlock)
                bestBlocks = k;
        }
        return bestBlocks;
    }

    private int getBestSword() {
        int bestSword = -1;
        float bestDamage = 1.0F;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemSword) {
                ItemSword itemSword = (ItemSword)is.getItem();
                float damage = itemSword.getDamageVsEntity();
                damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestSword = k;
                }
            }
        }
        return bestSword;
    }

    private int getBestPickaxe() {
        int bestPick = -1;
        float bestDamage = 1.0F;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemPickaxe) {
                ItemPickaxe itemSword = (ItemPickaxe)is.getItem();
                float damage = itemSword.getStrVsBlock(is, Block.getBlockById(4));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }

    private int getBestAxe() {
        int bestPick = -1;
        float bestDamage = 1.0F;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemAxe) {
                ItemAxe itemSword = (ItemAxe)is.getItem();
                float damage = itemSword.getStrVsBlock(is, Block.getBlockById(17));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }

    private int getBestShovel() {
        int bestPick = -1;
        float bestDamage = 1.0F;
        for (int k = 0; k < this.mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = this.mc.thePlayer.inventory.mainInventory[k];
            if (is != null && isShovel(is.getItem())) {
                ItemTool itemSword = (ItemTool)is.getItem();
                float damage = itemSword.getStrVsBlock(is, Block.getBlockById(3));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }

    private boolean isShovel(Item is) {
        return (Item.getItemById(256) == is || Item.getItemById(269) == is || Item.getItemById(273) == is || Item.getItemById(277) == is || Item.getItemById(284) == is);
    }

    public void drop(int index, ItemStack item) {
        boolean hotbar = false;
        for (int k = 0; k < 9; k++) {
            ItemStack itemK = this.mc.thePlayer.inventory.getStackInSlot(k);
            if (itemK != null && itemK == item)
                hotbar = true;
        }
        if (hotbar) {
            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, index + 36, 1, 4, (EntityPlayer)this.mc.thePlayer);
        } else {
            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, index, 1, 4, (EntityPlayer)this.mc.thePlayer);
        }
    }

    public void shiftClick(int slot) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)this.mc.thePlayer);
    }

    public void click(int slot) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, (EntityPlayer)this.mc.thePlayer);
    }

    public void swap(int slot, int hotbarSlot) {
        if (hotbarSlot == slot - 36)
            return;
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot, 2, (EntityPlayer)this.mc.thePlayer);
    }

    private boolean isBad(Item i) {
        return (i
                .getUnlocalizedName().contains("tnt") || i
                .getUnlocalizedName().contains("stick") || i
                .getUnlocalizedName().contains("string") || i
                .getUnlocalizedName().contains("flint") || i
                .getUnlocalizedName().contains("bucket") || i
                .getUnlocalizedName().contains("feather") || i
                .getUnlocalizedName().contains("snow") || i
                .getUnlocalizedName().contains("piston") || i instanceof net.minecraft.item.ItemGlassBottle || i

                .getUnlocalizedName().contains("web") || i
                .getUnlocalizedName().contains("slime") || i
                .getUnlocalizedName().contains("trip") || i
                .getUnlocalizedName().contains("wire") || i
                .getUnlocalizedName().contains("sugar") || i
                .getUnlocalizedName().contains("note") || i
                .getUnlocalizedName().contains("record") || i
                .getUnlocalizedName().contains("flower") || i
                .getUnlocalizedName().contains("wheat") || i
                .getUnlocalizedName().contains("fishing") || i
                .getUnlocalizedName().contains("boat") || i
                .getUnlocalizedName().contains("leather") || i
                .getUnlocalizedName().contains("seeds") || i
                .getUnlocalizedName().contains("skull") || i
                .getUnlocalizedName().contains("torch") || i
                .getUnlocalizedName().contains("anvil") || i
                .getUnlocalizedName().contains("enchant") || i
                .getUnlocalizedName().contains("exp") || i
                .getUnlocalizedName().contains("shears"));
    }

    public static InventoryManager instance(){
        return ModuleManager.getModule(InventoryManager.class);
    }

}
