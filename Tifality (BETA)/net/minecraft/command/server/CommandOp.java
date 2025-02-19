/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandOp
extends CommandBase {
    @Override
    public String getCommandName() {
        return "op";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.op.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        GameProfile gameprofile;
        MinecraftServer minecraftserver;
        if (args2.length == 1 && args2[0].length() > 0) {
            minecraftserver = MinecraftServer.getServer();
            gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args2[0]);
            if (gameprofile == null) {
                throw new CommandException("commands.op.failed", args2[0]);
            }
        } else {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        minecraftserver.getConfigurationManager().addOp(gameprofile);
        CommandOp.notifyOperators(sender, (ICommand)this, "commands.op.success", args2[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        if (args2.length == 1) {
            String s = args2[args2.length - 1];
            ArrayList<String> list = Lists.newArrayList();
            for (GameProfile gameprofile : MinecraftServer.getServer().getGameProfiles()) {
                if (MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile) || !CommandOp.doesStringStartWith(s, gameprofile.getName())) continue;
                list.add(gameprofile.getName());
            }
            return list;
        }
        return null;
    }
}

