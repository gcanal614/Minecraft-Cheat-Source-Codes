/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandReplaceItem
extends CommandBase {
    private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();

    @Override
    public String getCommandName() {
        return "replaceitem";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.replaceitem.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        Item item;
        int i;
        boolean flag;
        if (args2.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }
        if (args2[0].equals("entity")) {
            flag = false;
        } else {
            if (!args2[0].equals("block")) {
                throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }
            flag = true;
        }
        if (flag) {
            if (args2.length < 6) {
                throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }
            i = 4;
        } else {
            if (args2.length < 4) {
                throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
            }
            i = 2;
        }
        int j = this.getSlotForShortcut(args2[i++]);
        try {
            item = CommandReplaceItem.getItemByText(sender, args2[i]);
        }
        catch (NumberInvalidException numberinvalidexception) {
            if (Block.getBlockFromName(args2[i]) != Blocks.air) {
                throw numberinvalidexception;
            }
            item = null;
        }
        int k = args2.length > ++i ? CommandReplaceItem.parseInt(args2[i++], 1, 64) : 1;
        int l = args2.length > i ? CommandReplaceItem.parseInt(args2[i++]) : 0;
        ItemStack itemstack = new ItemStack(item, k, l);
        if (args2.length > i) {
            String s = CommandReplaceItem.getChatComponentFromNthArg(sender, args2, i).getUnformattedText();
            try {
                itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.replaceitem.tagError", nbtexception.getMessage());
            }
        }
        if (itemstack.getItem() == null) {
            itemstack = null;
        }
        if (flag) {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            BlockPos blockpos = CommandReplaceItem.parseBlockPos(sender, args2, 1, false);
            World world = sender.getEntityWorld();
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity == null || !(tileentity instanceof IInventory)) {
                throw new CommandException("commands.replaceitem.noContainer", blockpos.getX(), blockpos.getY(), blockpos.getZ());
            }
            IInventory iinventory = (IInventory)((Object)tileentity);
            if (j >= 0 && j < iinventory.getSizeInventory()) {
                iinventory.setInventorySlotContents(j, itemstack);
            }
        } else {
            Entity entity = CommandReplaceItem.func_175768_b(sender, args2[1]);
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }
            if (!entity.replaceItemInInventory(j, itemstack)) {
                throw new CommandException("commands.replaceitem.failed", j, k, itemstack == null ? "Air" : itemstack.getChatComponent());
            }
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
        CommandReplaceItem.notifyOperators(sender, (ICommand)this, "commands.replaceitem.success", j, k, itemstack == null ? "Air" : itemstack.getChatComponent());
    }

    private int getSlotForShortcut(String shortcut) throws CommandException {
        if (!SHORTCUTS.containsKey(shortcut)) {
            throw new CommandException("commands.generic.parameter.invalid", shortcut);
        }
        return SHORTCUTS.get(shortcut);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandReplaceItem.getListOfStringsMatchingLastWord(args2, "entity", "block") : (args2.length == 2 && args2[0].equals("entity") ? CommandReplaceItem.getListOfStringsMatchingLastWord(args2, this.getUsernames()) : (args2.length >= 2 && args2.length <= 4 && args2[0].equals("block") ? CommandReplaceItem.func_175771_a(args2, 1, pos) : (!(args2.length == 3 && args2[0].equals("entity") || args2.length == 5 && args2[0].equals("block")) ? (!(args2.length == 4 && args2[0].equals("entity") || args2.length == 6 && args2[0].equals("block")) ? null : CommandReplaceItem.getListOfStringsMatchingLastWord(args2, Item.itemRegistry.getKeys())) : CommandReplaceItem.getListOfStringsMatchingLastWord(args2, SHORTCUTS.keySet()))));
    }

    protected String[] getUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] args2, int index) {
        return args2.length > 0 && args2[0].equals("entity") && index == 1;
    }

    static {
        for (int i = 0; i < 54; ++i) {
            SHORTCUTS.put("slot.container." + i, i);
        }
        for (int j = 0; j < 9; ++j) {
            SHORTCUTS.put("slot.hotbar." + j, j);
        }
        for (int k = 0; k < 27; ++k) {
            SHORTCUTS.put("slot.inventory." + k, 9 + k);
        }
        for (int l = 0; l < 27; ++l) {
            SHORTCUTS.put("slot.enderchest." + l, 200 + l);
        }
        for (int i1 = 0; i1 < 8; ++i1) {
            SHORTCUTS.put("slot.villager." + i1, 300 + i1);
        }
        for (int j1 = 0; j1 < 15; ++j1) {
            SHORTCUTS.put("slot.horse." + j1, 500 + j1);
        }
        SHORTCUTS.put("slot.weapon", 99);
        SHORTCUTS.put("slot.armor.head", 103);
        SHORTCUTS.put("slot.armor.chest", 102);
        SHORTCUTS.put("slot.armor.legs", 101);
        SHORTCUTS.put("slot.armor.feet", 100);
        SHORTCUTS.put("slot.horse.saddle", 400);
        SHORTCUTS.put("slot.horse.armor", 401);
        SHORTCUTS.put("slot.horse.chest", 499);
    }
}

