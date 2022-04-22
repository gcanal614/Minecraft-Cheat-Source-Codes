/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench
extends Container {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private BlockPos pos;

    public ContainerWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
        this.worldObj = worldIn;
        this.pos = posIn;
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int i = 0;
        while (i < 3) {
            int j = 0;
            while (j < 3) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
                ++j;
            }
            ++i;
        }
        int k = 0;
        while (k < 3) {
            int i1 = 0;
            while (i1 < 9) {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
                ++i1;
            }
            ++k;
        }
        int l = 0;
        while (l < 9) {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
            ++l;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!this.worldObj.isRemote) {
            int i = 0;
            while (i < 9) {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
                if (itemstack != null) {
                    playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
                }
                ++i;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.worldObj.getBlockState(this.pos).getBlock() != Blocks.crafting_table ? false : playerIn.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 37 ? !this.mergeItemStack(itemstack1, 37, 46, false) : (index >= 37 && index < 46 ? !this.mergeItemStack(itemstack1, 10, 37, false) : !this.mergeItemStack(itemstack1, 10, 46, false))) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot p_94530_2_) {
        return p_94530_2_.inventory != this.craftResult && super.canMergeSlot(stack, p_94530_2_);
    }
}

