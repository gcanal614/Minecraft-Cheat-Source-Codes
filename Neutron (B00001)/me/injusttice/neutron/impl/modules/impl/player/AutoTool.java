package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class AutoTool extends Module {

    private int oldSlot = -1;
    private boolean wasBreaking = false;

    public AutoTool() {
        super("AutoTool", 0, Category.PLAYER);
    }

    @EventTarget
    public void onEvent(EventUpdate e) {
        this.setDisplayName("Auto Tool");
        if (mc.currentScreen == null && mc.thePlayer != null && mc.theWorld != null
                && mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null
                && mc.objectMouseOver.entityHit == null && Mouse.isButtonDown(0)) {
            float bestSpeed = 1.0F;
            int bestSlot = -1;
            Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            for (int k = 0; k < 9; k++) {
                ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
                if (item != null) {
                    float speed = item.getStrVsBlock(block);
                    if (speed > bestSpeed) {
                        bestSpeed = speed;
                        bestSlot = k;
                    }
                }
            }
            if (bestSlot != -1 && mc.thePlayer.inventory.currentItem != bestSlot) {
                mc.thePlayer.inventory.currentItem = bestSlot;
                wasBreaking = true;
            } else if (bestSlot == -1) {
                if (wasBreaking) {
                    mc.thePlayer.inventory.currentItem = oldSlot;
                    wasBreaking = false;
                }
                oldSlot = mc.thePlayer.inventory.currentItem;
            }
        } else if (mc.thePlayer != null && mc.theWorld != null) {
            if (wasBreaking) {
                mc.thePlayer.inventory.currentItem = oldSlot;
                wasBreaking = false;
            }
            oldSlot = mc.thePlayer.inventory.currentItem;
        }
    }
}

