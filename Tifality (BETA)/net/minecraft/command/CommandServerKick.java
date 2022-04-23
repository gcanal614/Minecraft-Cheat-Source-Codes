/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandServerKick
extends CommandBase {
    @Override
    public String getCommandName() {
        return "kick";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.kick.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length > 0 && args2[0].length() > 1) {
            EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args2[0]);
            String s = "Kicked by an operator.";
            boolean flag = false;
            if (entityplayermp == null) {
                throw new PlayerNotFoundException();
            }
            if (args2.length >= 2) {
                s = CommandServerKick.getChatComponentFromNthArg(sender, args2, 1).getUnformattedText();
                flag = true;
            }
            entityplayermp.playerNetServerHandler.kickPlayerFromServer(s);
            if (flag) {
                CommandServerKick.notifyOperators(sender, (ICommand)this, "commands.kick.success.reason", entityplayermp.getCommandSenderName(), s);
            } else {
                CommandServerKick.notifyOperators(sender, (ICommand)this, "commands.kick.success", entityplayermp.getCommandSenderName());
            }
        } else {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length >= 1 ? CommandServerKick.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

