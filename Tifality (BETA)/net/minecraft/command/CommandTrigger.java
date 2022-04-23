/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTrigger
extends CommandBase {
    @Override
    public String getCommandName() {
        return "trigger";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.trigger.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        EntityPlayerMP entityplayermp;
        if (args2.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }
        if (sender instanceof EntityPlayerMP) {
            entityplayermp = (EntityPlayerMP)sender;
        } else {
            Entity entity = sender.getCommandSenderEntity();
            if (!(entity instanceof EntityPlayerMP)) {
                throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }
            entityplayermp = (EntityPlayerMP)entity;
        }
        Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        ScoreObjective scoreobjective = scoreboard.getObjective(args2[0]);
        if (scoreobjective != null && scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            int i = CommandTrigger.parseInt(args2[2]);
            if (!scoreboard.entityHasObjective(entityplayermp.getCommandSenderName(), scoreobjective)) {
                throw new CommandException("commands.trigger.invalidObjective", args2[0]);
            }
            Score score = scoreboard.getValueFromObjective(entityplayermp.getCommandSenderName(), scoreobjective);
            if (score.isLocked()) {
                throw new CommandException("commands.trigger.disabled", args2[0]);
            }
            if ("set".equals(args2[1])) {
                score.setScorePoints(i);
            } else {
                if (!"add".equals(args2[1])) {
                    throw new CommandException("commands.trigger.invalidMode", args2[1]);
                }
                score.increseScore(i);
            }
            score.setLocked(true);
            if (entityplayermp.theItemInWorldManager.isCreative()) {
                CommandTrigger.notifyOperators(sender, (ICommand)this, "commands.trigger.success", args2[0], args2[1], args2[2]);
            }
        } else {
            throw new CommandException("commands.trigger.invalidObjective", args2[0]);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        if (args2.length == 1) {
            Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
            ArrayList<String> list = Lists.newArrayList();
            for (ScoreObjective scoreobjective : scoreboard.getScoreObjectives()) {
                if (scoreobjective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) continue;
                list.add(scoreobjective.getName());
            }
            return CommandTrigger.getListOfStringsMatchingLastWord(args2, list.toArray(new String[list.size()]));
        }
        return args2.length == 2 ? CommandTrigger.getListOfStringsMatchingLastWord(args2, "add", "set") : null;
    }
}

