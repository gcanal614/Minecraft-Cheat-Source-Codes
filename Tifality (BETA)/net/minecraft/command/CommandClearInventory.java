/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandClearInventory
extends CommandBase {
    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.clear.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        EntityPlayerMP entityplayermp = args2.length == 0 ? CommandClearInventory.getCommandSenderAsPlayer(sender) : CommandClearInventory.getPlayer(sender, args2[0]);
        Item item = args2.length >= 2 ? CommandClearInventory.getItemByText(sender, args2[1]) : null;
        int i = args2.length >= 3 ? CommandClearInventory.parseInt(args2[2], -1) : -1;
        int j = args2.length >= 4 ? CommandClearInventory.parseInt(args2[3], -1) : -1;
        NBTTagCompound nbttagcompound = null;
        if (args2.length >= 5) {
            try {
                nbttagcompound = JsonToNBT.getTagFromJson(CommandClearInventory.buildString(args2, 4));
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.clear.tagError", nbtexception.getMessage());
            }
        }
        if (args2.length >= 2 && item == null) {
            throw new CommandException("commands.clear.failure", entityplayermp.getCommandSenderName());
        }
        int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
        entityplayermp.inventoryContainer.detectAndSendChanges();
        if (!entityplayermp.capabilities.isCreativeMode) {
            entityplayermp.updateHeldItem();
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
        if (k == 0) {
            throw new CommandException("commands.clear.failure", entityplayermp.getCommandSenderName());
        }
        if (j == 0) {
            sender.addChatMessage(new ChatComponentTranslation("commands.clear.testing", entityplayermp.getCommandSenderName(), k));
        } else {
            CommandClearInventory.notifyOperators(sender, (ICommand)this, "commands.clear.success", entityplayermp.getCommandSenderName(), k);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandClearInventory.getListOfStringsMatchingLastWord(args2, this.func_147209_d()) : (args2.length == 2 ? CommandClearInventory.getListOfStringsMatchingLastWord(args2, Item.itemRegistry.getKeys()) : null);
    }

    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] args2, int index) {
        return index == 0;
    }
}

