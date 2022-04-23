/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonPlayer
extends CommandBase {
    @Override
    public String getCommandName() {
        return "pardon";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.unban.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        GameProfile gameprofile;
        MinecraftServer minecraftserver;
        if (args2.length == 1 && args2[0].length() > 0) {
            minecraftserver = MinecraftServer.getServer();
            gameprofile = minecraftserver.getConfigurationManager().getBannedPlayers().isUsernameBanned(args2[0]);
            if (gameprofile == null) {
                throw new CommandException("commands.unban.failed", args2[0]);
            }
        } else {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
        minecraftserver.getConfigurationManager().getBannedPlayers().removeEntry(gameprofile);
        CommandPardonPlayer.notifyOperators(sender, (ICommand)this, "commands.unban.success", args2[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandPardonPlayer.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()) : null;
    }
}

