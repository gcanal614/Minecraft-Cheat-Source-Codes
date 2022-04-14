package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandTrigger extends CommandBase
{
    @Override
    public String getCommandName() {
        return "trigger";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.trigger.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }
        EntityPlayerMP var3;
        if (sender instanceof EntityPlayerMP) {
            var3 = (EntityPlayerMP)sender;
        }
        else {
            final Entity var4 = sender.getCommandSenderEntity();
            if (!(var4 instanceof EntityPlayerMP)) {
                throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }
            var3 = (EntityPlayerMP)var4;
        }
        final Scoreboard var5 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        final ScoreObjective var6 = var5.getObjective(args[0]);
        if (var6 == null || var6.getCriteria() != IScoreObjectiveCriteria.field_178791_c) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
        }
        final int var7 = CommandBase.parseInt(args[2]);
        if (!var5.func_178819_b(var3.getName(), var6)) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
        }
        final Score var8 = var5.getValueFromObjective(var3.getName(), var6);
        if (var8.func_178816_g()) {
            throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
        }
        if ("set".equals(args[1])) {
            var8.setScorePoints(var7);
        }
        else {
            if (!"add".equals(args[1])) {
                throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
            }
            var8.increseScore(var7);
        }
        var8.func_178815_a(true);
        if (var3.theItemInWorldManager.isCreative()) {
            CommandBase.notifyOperators(sender, this, "commands.trigger.success", args[0], args[1], args[2]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final Scoreboard var4 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
            final ArrayList var5 = Lists.newArrayList();
            for (final ScoreObjective var7 : var4.getScoreObjectives()) {
                if (var7.getCriteria() == IScoreObjectiveCriteria.field_178791_c) {
                    var5.add(var7.getName());
                }
            }
            return CommandBase.getListOfStringsMatchingLastWord(args, (String[])var5.toArray(new String[var5.size()]));
        }
        return (args.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(args, "add", "set") : null;
    }
}
