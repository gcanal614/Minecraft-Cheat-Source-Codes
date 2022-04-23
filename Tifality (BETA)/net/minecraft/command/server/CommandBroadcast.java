/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandBroadcast
extends CommandBase {
    @Override
    public String getCommandName() {
        return "say";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.say.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length <= 0 || args2[0].length() <= 0) {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
        }
        IChatComponent ichatcomponent = CommandBroadcast.getChatComponentFromNthArg(sender, args2, 0, true);
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.announcement", sender.getDisplayName(), ichatcomponent));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length >= 1 ? CommandBroadcast.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

