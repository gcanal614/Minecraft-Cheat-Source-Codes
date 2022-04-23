/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandEmote
extends CommandBase {
    @Override
    public String getCommandName() {
        return "me";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.me.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length <= 0) {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
        IChatComponent ichatcomponent = CommandEmote.getChatComponentFromNthArg(sender, args2, 0, !(sender instanceof EntityPlayer));
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.emote", sender.getDisplayName(), ichatcomponent));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return CommandEmote.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames());
    }
}

