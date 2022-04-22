package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventTick;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.utils.network.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class AutoArmor extends Module {
    
    public BooleanSet silentInventory = new BooleanSet("Silent Inventory", true);
    public static DoubleSet delayx = new DoubleSet("Delay", 5.0, 0.0, 20.0, 1.0);
    public BooleanSet inventoryOnly = new BooleanSet("Inventory Only", false);
    private boolean inventoryOpened = false;
    
    public int getDelay() {
        return (int)delayx.getValue();
    }

    private int[] chestplate = new int[] { 311, 307, 315, 303, 299 };
    private int[] leggings = new int[] { 312, 308, 316, 304, 300 };
    private int[] boots = new int[] { 313, 309, 317, 305, 301 };
    private int[] helmet = new int[] { 310, 306, 314, 302, 298 };
    private boolean best = true;
    int delay = 0;

    public AutoArmor() {
        super("AutoArmor", 0, Category.COMBAT);
        addSettings(silentInventory, delayx, inventoryOnly);
    }

    @EventTarget
    public void onTick(EventTick e) {
        setDisplayName("Auto Armor §7" + delayx.getValue());
        if (inventoryOnly.isEnabled() && mc.currentScreen == null)
            return;
        if (!(mc.currentScreen instanceof GuiChest)) {
            autoArmor();
            armorSort();
        }
        delay++;
    }

    public void autoArmor() {
        if (best)
            return;
        int item = -1;
        if (delay > getDelay()) {
            delay = 0;
            if (mc.thePlayer.inventory.armorInventory[0] == null) {
                int boots[], length = (boots = this.boots).length;
                int i = 0;
                while (i < length) {
                    int id = boots[i];
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (mc.thePlayer.inventory.armorInventory[1] == null) {
                int leggings[], length = (leggings = this.leggings).length;
                int i = 0;
                while (i < length) {
                    int id = leggings[i];
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (mc.thePlayer.inventory.armorInventory[2] == null) {
                int chestplate[], length = (chestplate = this.chestplate).length;
                int i = 0;
                while (i < length) {
                    int id = chestplate[i];
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (mc.thePlayer.inventory.armorInventory[3] == null) {
                int helmet[], length = (helmet = this.helmet).length;
                int i = 0;
                while (i < length) {
                    int id = helmet[i];
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (item != -1) {
                if (silentInventory.isEnabled())
                    openFakeInv();
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, item, 0, 1, (EntityPlayer)mc.thePlayer);
                if (silentInventory.isEnabled())
                    closeFakeInv();
                delay = 0;
            }
        }
    }

    public void armorSort() {
        if (!best)
            return;
        if (delay > getDelay() + 1) {
            delay = 0;
            boolean switch2 = false;
            int x = -1;
            if (mc.thePlayer.inventory.armorInventory[0] == null) {
                int array[], j = (array = boots).length;
                int i = 0;
                while (i < j) {
                    int id = array[i];
                    if (getItem(id) != -1) {
                        x = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (isBetterArmor(0, boots)) {
                x = 8;
                switch2 = true;
            }
            if (mc.thePlayer.inventory.armorInventory[3] == null) {
                int array[], j = (array = helmet).length;
                int i = 0;
                while (i < j) {
                    int id = array[i];
                    if (getItem(id) != -1) {
                        x = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (isBetterArmor(3, helmet)) {
                x = 5;
                switch2 = true;
            }
            if (mc.thePlayer.inventory.armorInventory[1] == null) {
                int array[], j = (array = leggings).length;
                int i = 0;
                while (i < j) {
                    int id = array[i];
                    if (getItem(id) != -1) {
                        x = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (isBetterArmor(1, leggings)) {
                x = 7;
                switch2 = true;
            }
            if (mc.thePlayer.inventory.armorInventory[2] == null) {
                int array[], j = (array = chestplate).length;
                int i = 0;
                while (i < j) {
                    int id = array[i];
                    if (getItem(id) != -1) {
                        x = getItem(id);
                        break;
                    }
                    i++;
                }
            }
            if (isBetterArmor(2, chestplate)) {
                x = 6;
                switch2 = true;
            }
            boolean b = false;
            ItemStack[] stackArray;
            int k = (stackArray = mc.thePlayer.inventory.mainInventory).length;
            int a = 0;
            while (a < k) {
                ItemStack stack = stackArray[a];
                if (stack == null) {
                    b = true;
                    break;
                }
                a++;
            }
            switch2 = (switch2 && !b);
            if (x != -1) {
                if (silentInventory.isEnabled())
                    openFakeInv();
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, x, 0, switch2 ? 4 : 1, mc.thePlayer);
                if (silentInventory.isEnabled())
                    closeFakeInv();
                delay = 0;
            }
        }
    }

    public void openFakeInv() {
        if (!inventoryOpened)
            PacketUtil.sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        inventoryOpened = true;
    }

    public void closeFakeInv() {
        if (inventoryOpened)
            PacketUtil.sendPacket(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
        inventoryOpened = false;
    }

    public static boolean isBetterArmor(int slot, int[] armorType) {
        if ((Minecraft.getMinecraft()).thePlayer.inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            int array[], j = (array = armorType).length;
            int i;
            for (i = 0; i < j; i++) {
                int armor = array[i];
                if (Item.getIdFromItem((Minecraft.getMinecraft()).thePlayer.inventory.armorInventory[slot].getItem()) == armor) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                currentIndex++;
            }
            j = (array = armorType).length;
            for (i = 0; i < j; i++) {
                int armor = array[i];
                if (getItem(armor) != -1) {
                    finalInvIndex = invIndex;
                    break;
                }
                invIndex++;
            }
            if (finalInvIndex > -1)
                return (finalInvIndex < finalCurrentIndex);
        }
        return false;
    }

    public static int getItem(int id) {
        for (int i = 9; i < 45; i++) {
            ItemStack item = (Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id)
                return i;
        }
        return -1;
    }

    public static double getProt(ItemStack stack) {
        return (stack.getItem() instanceof ItemArmor) ? (((ItemArmor)stack.getItem()).damageReduceAmount + ((100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4) * 0.0075D) : 0.0D;
    }
}
