package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.other.AutoHypixel;
import me.injusttice.neutron.utils.MathUtils;
import me.injusttice.neutron.utils.player.Timer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Stealer extends Module {

    public Timer timer = new Timer();
    public Timer closedtimer = new Timer();
    public DoubleSet stealerSe = new DoubleSet("Delay", 40, 0, 500, 1);

    public Stealer() {
        super("Stealer", 0, Category.PLAYER);
        addSettings(stealerSe);
    }

    @EventTarget
    public void onEvent(EventNigger e) {
        if(mc.thePlayer == null) {
            toggled = false;
        }

        if((mc.thePlayer.openContainer != null) && ((mc.thePlayer.openContainer instanceof ContainerChest))){
            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
            for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if((chest.getLowerChestInventory().getStackInSlot(i) != null) && (timer.hasReached((long) stealerSe.getValue()))) {
                    mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                    timer.reset();
                }
            }
            GuiChest chest1 = (GuiChest) mc.currentScreen;
            if (!hasItems(chest1) || isInventoryFull()) {
                if (closedtimer.hasReached((long) MathUtils.getRandomInRange(75, 150)))
                    mc.thePlayer.closeScreen();
            }
        }
    }

    private boolean hasItems(GuiChest chest) {
        int items = 0;
        int rows = chest.getInventoryRows() * 9;
        for (int i = 0; i < rows; i++) {
            Slot slot = chest.inventorySlots.getSlot(i);
            if (slot.getHasStack()) items++;
        }
        return items > 0;
    }

    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }

    public static Stealer instance(){
        return ModuleManager.getModule(Stealer.class);
    }

}
