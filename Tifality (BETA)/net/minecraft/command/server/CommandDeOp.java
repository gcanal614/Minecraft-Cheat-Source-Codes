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

public class CommandDeOp
extends CommandBase {
    @Override
    public String getCommandName() {
        return "deop";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.deop.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        GameProfile gameprofile;
        MinecraftServer minecraftserver;
        if (args2.length == 1 && args2[0].length() > 0) {
            minecraftserver = MinecraftServer.getServer();
            gameprofile = minecraftserver.getConfigurationManager().getOppedPlayers().getGameProfileFromName(args2[0]);
            if (gameprofile == null) {
                throw new CommandException("commands.deop.failed", args2[0]);
            }
        } else {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
        minecraftserver.getConfigurationManager().removeOp(gameprofile);
        CommandDeOp.notifyOperators(sender, (ICommand)this, "commands.deop.success", args2[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandDeOp.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames()) : null;
    }
}

