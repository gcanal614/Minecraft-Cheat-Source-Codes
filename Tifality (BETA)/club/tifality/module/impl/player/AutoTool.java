/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.utils.Wrapper;
import club.tifality.utils.inventory.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(label="AutoTool", category=ModuleCategory.PLAYER)
public final class AutoTool
extends Module {
    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            MovingObjectPosition objectMouseOver = Wrapper.getMinecraft().objectMouseOver;
            if (objectMouseOver != null && Wrapper.getGameSettings().keyBindAttack.isKeyDown()) {
                if (objectMouseOver.entityHit != null) {
                    this.doSwordSwap();
                } else {
                    BlockPos blockPos = objectMouseOver.getBlockPos();
                    if (blockPos != null) {
                        Block block = Wrapper.getWorld().getBlockState(blockPos).getBlock();
                        float strongestToolStr = 1.0f;
                        int strongestToolSlot = -1;
                        for (int i = 36; i < 45; ++i) {
                            float strVsBlock;
                            ItemStack stack = Wrapper.getStackInSlot(i);
                            if (stack == null || !(stack.getItem() instanceof ItemTool) || !((strVsBlock = stack.getStrVsBlock(block)) > strongestToolStr)) continue;
                            strongestToolStr = strVsBlock;
                            strongestToolSlot = i;
                        }
                        if (strongestToolSlot != -1) {
                            Wrapper.getPlayer().inventory.currentItem = strongestToolSlot - 36;
                        }
                    }
                }
            } else if (KillAura.getInstance().getTarget() != null) {
                this.doSwordSwap();
            }
        }
    }

    private void doSwordSwap() {
        double damage = 1.0;
        int slot = -1;
        for (int i = 36; i < 45; ++i) {
            double damageVs;
            ItemStack stack = Wrapper.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemSword) || !((damageVs = InventoryUtils.getItemDamage(stack)) > damage)) continue;
            damage = damageVs;
            slot = i;
        }
        if (slot != -1) {
            Wrapper.getPlayer().inventory.currentItem = slot - 36;
        }
    }
}

