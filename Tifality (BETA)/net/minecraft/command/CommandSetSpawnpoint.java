/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetSpawnpoint
extends CommandBase {
    @Override
    public String getCommandName() {
        return "spawnpoint";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.spawnpoint.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        BlockPos blockpos;
        if (args2.length > 1 && args2.length < 4) {
            throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
        }
        EntityPlayerMP entityplayermp = args2.length > 0 ? CommandSetSpawnpoint.getPlayer(sender, args2[0]) : CommandSetSpawnpoint.getCommandSenderAsPlayer(sender);
        BlockPos blockPos = blockpos = args2.length > 3 ? CommandSetSpawnpoint.parseBlockPos(sender, args2, 1, true) : entityplayermp.getPosition();
        if (entityplayermp.worldObj != null) {
            entityplayermp.setSpawnPoint(blockpos, true);
            CommandSetSpawnpoint.notifyOperators(sender, (ICommand)this, "commands.spawnpoint.success", entityplayermp.getCommandSenderName(), blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandSetSpawnpoint.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : (args2.length > 1 && args2.length <= 4 ? CommandSetSpawnpoint.func_175771_a(args2, 1, pos) : null);
    }

    @Override
    public boolean isUsernameIndex(String[] args2, int index) {
        return index == 0;
    }
}

