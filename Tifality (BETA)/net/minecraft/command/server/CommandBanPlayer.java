/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.BlockPos;

public class CommandBanPlayer
extends CommandBase {
    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.ban.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length >= 1 && args2[0].length() > 0) {
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args2[0]);
            if (gameprofile == null) {
                throw new CommandException("commands.ban.failed", args2[0]);
            }
            String s = null;
            if (args2.length >= 2) {
                s = CommandBanPlayer.getChatComponentFromNthArg(sender, args2, 1).getUnformattedText();
            }
            UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, (Date)null, sender.getCommandSenderName(), (Date)null, s);
            minecraftserver.getConfigurationManager().getBannedPlayers().addEntry(userlistbansentry);
            EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().getPlayerByUsername(args2[0]);
            if (entityplayermp != null) {
                entityplayermp.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
            }
        } else {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        CommandBanPlayer.notifyOperators(sender, (ICommand)this, "commands.ban.success", args2[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length >= 1 ? CommandBanPlayer.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

