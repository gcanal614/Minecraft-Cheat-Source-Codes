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
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime
extends CommandBase {
    @Override
    public String getCommandName() {
        return "time";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.time.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length > 1) {
            if (args2[0].equals("set")) {
                int l = args2[1].equals("day") ? 1000 : (args2[1].equals("night") ? 13000 : CommandTime.parseInt(args2[1], 0));
                this.setTime(sender, l);
                CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.set", l);
                return;
            }
            if (args2[0].equals("add")) {
                int k = CommandTime.parseInt(args2[1], 0);
                this.addTime(sender, k);
                CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.added", k);
                return;
            }
            if (args2[0].equals("query")) {
                if (args2[1].equals("daytime")) {
                    int j = (int)(sender.getEntityWorld().getWorldTime() % Integer.MAX_VALUE);
                    sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
                    CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.query", j);
                    return;
                }
                if (args2[1].equals("gametime")) {
                    int i = (int)(sender.getEntityWorld().getTotalWorldTime() % Integer.MAX_VALUE);
                    sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
                    CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.query", i);
                    return;
                }
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandTime.getListOfStringsMatchingLastWord(args2, "set", "add", "query") : (args2.length == 2 && args2[0].equals("set") ? CommandTime.getListOfStringsMatchingLastWord(args2, "day", "night") : (args2.length == 2 && args2[0].equals("query") ? CommandTime.getListOfStringsMatchingLastWord(args2, "daytime", "gametime") : null));
    }

    protected void setTime(ICommandSender p_71552_1_, int p_71552_2_) {
        for (int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i) {
            MinecraftServer.getServer().worldServers[i].setWorldTime(p_71552_2_);
        }
    }

    protected void addTime(ICommandSender p_71553_1_, int p_71553_2_) {
        for (int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i) {
            WorldServer worldserver = MinecraftServer.getServer().worldServers[i];
            worldserver.setWorldTime(worldserver.getWorldTime() + (long)p_71553_2_);
        }
    }
}

