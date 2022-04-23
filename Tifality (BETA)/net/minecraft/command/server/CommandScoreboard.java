/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandScoreboard
extends CommandBase {
    @Override
    public String getCommandName() {
        return "scoreboard";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender p_getCommandUsage_1_) {
        return "commands.scoreboard.usage";
    }

    @Override
    public void processCommand(ICommandSender p_processCommand_1_, String[] p_processCommand_2_) throws CommandException {
        if (!this.func_175780_b(p_processCommand_1_, p_processCommand_2_)) {
            if (p_processCommand_2_.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }
            if (p_processCommand_2_[0].equalsIgnoreCase("objectives")) {
                if (p_processCommand_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (p_processCommand_2_[1].equalsIgnoreCase("list")) {
                    this.listObjectives(p_processCommand_1_);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("add")) {
                    if (p_processCommand_2_.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.addObjective(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("remove")) {
                    if (p_processCommand_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.removeObjective(p_processCommand_1_, p_processCommand_2_[2]);
                } else {
                    if (!p_processCommand_2_[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (p_processCommand_2_.length != 3 && p_processCommand_2_.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.setObjectiveDisplay(p_processCommand_1_, p_processCommand_2_, 2);
                }
            } else if (p_processCommand_2_[0].equalsIgnoreCase("players")) {
                if (p_processCommand_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (p_processCommand_2_[1].equalsIgnoreCase("list")) {
                    if (p_processCommand_2_.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.listPlayers(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("add")) {
                    if (p_processCommand_2_.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.setPlayer(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("remove")) {
                    if (p_processCommand_2_.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.setPlayer(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("set")) {
                    if (p_processCommand_2_.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.setPlayer(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("reset")) {
                    if (p_processCommand_2_.length != 3 && p_processCommand_2_.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.resetPlayers(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("enable")) {
                    if (p_processCommand_2_.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                    }
                    this.func_175779_n(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("test")) {
                    if (p_processCommand_2_.length != 5 && p_processCommand_2_.length != 6) {
                        throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                    }
                    this.func_175781_o(p_processCommand_1_, p_processCommand_2_, 2);
                } else {
                    if (!p_processCommand_2_[1].equalsIgnoreCase("operation")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (p_processCommand_2_.length != 7) {
                        throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                    }
                    this.func_175778_p(p_processCommand_1_, p_processCommand_2_, 2);
                }
            } else {
                if (!p_processCommand_2_[0].equalsIgnoreCase("teams")) {
                    throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
                }
                if (p_processCommand_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (p_processCommand_2_[1].equalsIgnoreCase("list")) {
                    if (p_processCommand_2_.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.listTeams(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("add")) {
                    if (p_processCommand_2_.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.addTeam(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("remove")) {
                    if (p_processCommand_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.removeTeam(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (p_processCommand_2_[1].equalsIgnoreCase("empty")) {
                    if (p_processCommand_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.emptyTeam(p_processCommand_1_, p_processCommand_2_, 2);
                } else if (!p_processCommand_2_[1].equalsIgnoreCase("join")) {
                    if (p_processCommand_2_[1].equalsIgnoreCase("leave")) {
                        if (p_processCommand_2_.length < 3 && !(p_processCommand_1_ instanceof EntityPlayer)) {
                            throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                        }
                        this.leaveTeam(p_processCommand_1_, p_processCommand_2_, 2);
                    } else {
                        if (!p_processCommand_2_[1].equalsIgnoreCase("option")) {
                            throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                        }
                        if (p_processCommand_2_.length != 4 && p_processCommand_2_.length != 5) {
                            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                        }
                        this.setTeamOption(p_processCommand_1_, p_processCommand_2_, 2);
                    }
                } else {
                    if (!(p_processCommand_2_.length >= 4 || p_processCommand_2_.length == 3 && p_processCommand_1_ instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.joinTeam(p_processCommand_1_, p_processCommand_2_, 2);
                }
            }
        }
    }

    private boolean func_175780_b(ICommandSender p_175780_1_, String[] p_175780_2_) throws CommandException {
        int lvt_3_1_ = -1;
        for (int lvt_4_1_ = 0; lvt_4_1_ < p_175780_2_.length; ++lvt_4_1_) {
            if (!this.isUsernameIndex(p_175780_2_, lvt_4_1_) || !"*".equals(p_175780_2_[lvt_4_1_])) continue;
            if (lvt_3_1_ >= 0) {
                throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
            }
            lvt_3_1_ = lvt_4_1_;
        }
        if (lvt_3_1_ < 0) {
            return false;
        }
        ArrayList<String> lvt_4_2_ = Lists.newArrayList(this.getScoreboard().getObjectiveNames());
        String lvt_5_1_ = p_175780_2_[lvt_3_1_];
        ArrayList<String> lvt_6_1_ = Lists.newArrayList();
        Iterator lvt_7_1_ = lvt_4_2_.iterator();
        while (lvt_7_1_.hasNext()) {
            String lvt_8_1_;
            p_175780_2_[lvt_3_1_] = lvt_8_1_ = (String)lvt_7_1_.next();
            try {
                this.processCommand(p_175780_1_, p_175780_2_);
                lvt_6_1_.add(lvt_8_1_);
            }
            catch (CommandException var11) {
                ChatComponentTranslation lvt_10_1_ = new ChatComponentTranslation(var11.getMessage(), var11.getErrorObjects());
                lvt_10_1_.getChatStyle().setColor(EnumChatFormatting.RED);
                p_175780_1_.addChatMessage(lvt_10_1_);
            }
        }
        p_175780_2_[lvt_3_1_] = lvt_5_1_;
        p_175780_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, lvt_6_1_.size());
        if (lvt_6_1_.size() == 0) {
            throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
        }
        return true;
    }

    protected Scoreboard getScoreboard() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }

    protected ScoreObjective getObjective(String p_getObjective_1_, boolean p_getObjective_2_) throws CommandException {
        Scoreboard lvt_3_1_ = this.getScoreboard();
        ScoreObjective lvt_4_1_ = lvt_3_1_.getObjective(p_getObjective_1_);
        if (lvt_4_1_ == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", p_getObjective_1_);
        }
        if (p_getObjective_2_ && lvt_4_1_.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", p_getObjective_1_);
        }
        return lvt_4_1_;
    }

    protected ScorePlayerTeam getTeam(String p_getTeam_1_) throws CommandException {
        Scoreboard lvt_2_1_ = this.getScoreboard();
        ScorePlayerTeam lvt_3_1_ = lvt_2_1_.getTeam(p_getTeam_1_);
        if (lvt_3_1_ == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", p_getTeam_1_);
        }
        return lvt_3_1_;
    }

    protected void addObjective(ICommandSender p_addObjective_1_, String[] p_addObjective_2_, int p_addObjective_3_) throws CommandException {
        String lvt_4_1_ = p_addObjective_2_[p_addObjective_3_++];
        String lvt_5_1_ = p_addObjective_2_[p_addObjective_3_++];
        Scoreboard lvt_6_1_ = this.getScoreboard();
        IScoreObjectiveCriteria lvt_7_1_ = IScoreObjectiveCriteria.INSTANCES.get(lvt_5_1_);
        if (lvt_7_1_ == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", lvt_5_1_);
        }
        if (lvt_6_1_.getObjective(lvt_4_1_) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", lvt_4_1_);
        }
        if (lvt_4_1_.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", lvt_4_1_, 16);
        }
        if (lvt_4_1_.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (p_addObjective_2_.length > p_addObjective_3_) {
            String lvt_8_1_ = CommandScoreboard.getChatComponentFromNthArg(p_addObjective_1_, p_addObjective_2_, p_addObjective_3_).getUnformattedText();
            if (lvt_8_1_.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", lvt_8_1_, 32);
            }
            if (lvt_8_1_.length() > 0) {
                lvt_6_1_.addScoreObjective(lvt_4_1_, lvt_7_1_).setDisplayName(lvt_8_1_);
            } else {
                lvt_6_1_.addScoreObjective(lvt_4_1_, lvt_7_1_);
            }
        } else {
            lvt_6_1_.addScoreObjective(lvt_4_1_, lvt_7_1_);
        }
        CommandScoreboard.notifyOperators(p_addObjective_1_, (ICommand)this, "commands.scoreboard.objectives.add.success", lvt_4_1_);
    }

    protected void addTeam(ICommandSender p_addTeam_1_, String[] p_addTeam_2_, int p_addTeam_3_) throws CommandException {
        String lvt_4_1_ = p_addTeam_2_[p_addTeam_3_++];
        Scoreboard lvt_5_1_ = this.getScoreboard();
        if (lvt_5_1_.getTeam(lvt_4_1_) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", lvt_4_1_);
        }
        if (lvt_4_1_.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", lvt_4_1_, 16);
        }
        if (lvt_4_1_.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (p_addTeam_2_.length > p_addTeam_3_) {
            String lvt_6_1_ = CommandScoreboard.getChatComponentFromNthArg(p_addTeam_1_, p_addTeam_2_, p_addTeam_3_).getUnformattedText();
            if (lvt_6_1_.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", lvt_6_1_, 32);
            }
            if (lvt_6_1_.length() > 0) {
                lvt_5_1_.createTeam(lvt_4_1_).setTeamName(lvt_6_1_);
            } else {
                lvt_5_1_.createTeam(lvt_4_1_);
            }
        } else {
            lvt_5_1_.createTeam(lvt_4_1_);
        }
        CommandScoreboard.notifyOperators(p_addTeam_1_, (ICommand)this, "commands.scoreboard.teams.add.success", lvt_4_1_);
    }

    protected void setTeamOption(ICommandSender p_setTeamOption_1_, String[] p_setTeamOption_2_, int p_setTeamOption_3_) throws CommandException {
        ScorePlayerTeam lvt_4_1_;
        if ((lvt_4_1_ = this.getTeam(p_setTeamOption_2_[p_setTeamOption_3_++])) != null) {
            String lvt_5_1_;
            if (!((lvt_5_1_ = p_setTeamOption_2_[p_setTeamOption_3_++].toLowerCase()).equalsIgnoreCase("color") || lvt_5_1_.equalsIgnoreCase("friendlyfire") || lvt_5_1_.equalsIgnoreCase("seeFriendlyInvisibles") || lvt_5_1_.equalsIgnoreCase("nametagVisibility") || lvt_5_1_.equalsIgnoreCase("deathMessageVisibility"))) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (p_setTeamOption_2_.length == 4) {
                if (lvt_5_1_.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)));
                }
                if (!lvt_5_1_.equalsIgnoreCase("friendlyfire") && !lvt_5_1_.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!lvt_5_1_.equalsIgnoreCase("nametagVisibility") && !lvt_5_1_.equalsIgnoreCase("deathMessageVisibility")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceString(Team.EnumVisible.func_178825_a()));
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
            }
            String lvt_6_1_ = p_setTeamOption_2_[p_setTeamOption_3_];
            if (lvt_5_1_.equalsIgnoreCase("color")) {
                EnumChatFormatting lvt_7_1_ = EnumChatFormatting.getValueByName(lvt_6_1_);
                if (lvt_7_1_ == null || lvt_7_1_.isFancyStyling()) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)));
                }
                lvt_4_1_.setChatFormat(lvt_7_1_);
                lvt_4_1_.setNamePrefix(lvt_7_1_.toString());
                lvt_4_1_.setNameSuffix(EnumChatFormatting.RESET.toString());
            } else if (lvt_5_1_.equalsIgnoreCase("friendlyfire")) {
                if (!lvt_6_1_.equalsIgnoreCase("true") && !lvt_6_1_.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
                }
                lvt_4_1_.setAllowFriendlyFire(lvt_6_1_.equalsIgnoreCase("true"));
            } else if (lvt_5_1_.equalsIgnoreCase("seeFriendlyInvisibles")) {
                if (!lvt_6_1_.equalsIgnoreCase("true") && !lvt_6_1_.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
                }
                lvt_4_1_.setSeeFriendlyInvisiblesEnabled(lvt_6_1_.equalsIgnoreCase("true"));
            } else if (lvt_5_1_.equalsIgnoreCase("nametagVisibility")) {
                Team.EnumVisible lvt_7_3_ = Team.EnumVisible.func_178824_a(lvt_6_1_);
                if (lvt_7_3_ == null) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceString(Team.EnumVisible.func_178825_a()));
                }
                lvt_4_1_.setNameTagVisibility(lvt_7_3_);
            } else if (lvt_5_1_.equalsIgnoreCase("deathMessageVisibility")) {
                Team.EnumVisible lvt_7_3_ = Team.EnumVisible.func_178824_a(lvt_6_1_);
                if (lvt_7_3_ == null) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", lvt_5_1_, CommandScoreboard.joinNiceString(Team.EnumVisible.func_178825_a()));
                }
                lvt_4_1_.setDeathMessageVisibility(lvt_7_3_);
            }
            CommandScoreboard.notifyOperators(p_setTeamOption_1_, (ICommand)this, "commands.scoreboard.teams.option.success", lvt_5_1_, lvt_4_1_.getRegisteredName(), lvt_6_1_);
        }
    }

    protected void removeTeam(ICommandSender p_removeTeam_1_, String[] p_removeTeam_2_, int p_removeTeam_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        ScorePlayerTeam lvt_5_1_ = this.getTeam(p_removeTeam_2_[p_removeTeam_3_]);
        if (lvt_5_1_ != null) {
            lvt_4_1_.removeTeam(lvt_5_1_);
            CommandScoreboard.notifyOperators(p_removeTeam_1_, (ICommand)this, "commands.scoreboard.teams.remove.success", lvt_5_1_.getRegisteredName());
        }
    }

    protected void listTeams(ICommandSender p_listTeams_1_, String[] p_listTeams_2_, int p_listTeams_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        if (p_listTeams_2_.length > p_listTeams_3_) {
            ScorePlayerTeam lvt_5_1_ = this.getTeam(p_listTeams_2_[p_listTeams_3_]);
            if (lvt_5_1_ == null) {
                return;
            }
            Collection<String> lvt_6_1_ = lvt_5_1_.getMembershipCollection();
            p_listTeams_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, lvt_6_1_.size());
            if (lvt_6_1_.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", lvt_5_1_.getRegisteredName());
            }
            ChatComponentTranslation lvt_7_1_ = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", lvt_6_1_.size(), lvt_5_1_.getRegisteredName());
            lvt_7_1_.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_listTeams_1_.addChatMessage(lvt_7_1_);
            p_listTeams_1_.addChatMessage(new ChatComponentText(CommandScoreboard.joinNiceString(lvt_6_1_.toArray())));
        } else {
            Collection<ScorePlayerTeam> lvt_5_2_ = lvt_4_1_.getTeams();
            p_listTeams_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, lvt_5_2_.size());
            if (lvt_5_2_.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            ChatComponentTranslation lvt_6_2_ = new ChatComponentTranslation("commands.scoreboard.teams.list.count", lvt_5_2_.size());
            lvt_6_2_.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_listTeams_1_.addChatMessage(lvt_6_2_);
            for (ScorePlayerTeam lvt_8_1_ : lvt_5_2_) {
                p_listTeams_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", lvt_8_1_.getRegisteredName(), lvt_8_1_.getTeamName(), lvt_8_1_.getMembershipCollection().size()));
            }
        }
    }

    protected void joinTeam(ICommandSender p_joinTeam_1_, String[] p_joinTeam_2_, int p_joinTeam_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        String lvt_5_1_ = p_joinTeam_2_[p_joinTeam_3_++];
        HashSet<String> lvt_6_1_ = Sets.newHashSet();
        HashSet<String> lvt_7_1_ = Sets.newHashSet();
        if (p_joinTeam_1_ instanceof EntityPlayer && p_joinTeam_3_ == p_joinTeam_2_.length) {
            String lvt_8_2_ = CommandScoreboard.getCommandSenderAsPlayer(p_joinTeam_1_).getName();
            if (lvt_4_1_.addPlayerToTeam(lvt_8_2_, lvt_5_1_)) {
                lvt_6_1_.add(lvt_8_2_);
            } else {
                lvt_7_1_.add(lvt_8_2_);
            }
        } else {
            while (p_joinTeam_3_ < p_joinTeam_2_.length) {
                String lvt_8_2_;
                if ((lvt_8_2_ = p_joinTeam_2_[p_joinTeam_3_++]).startsWith("@")) {
                    List<Entity> lvt_9_1_ = CommandScoreboard.func_175763_c(p_joinTeam_1_, lvt_8_2_);
                    for (Entity lvt_11_1_ : lvt_9_1_) {
                        String lvt_12_1_ = CommandScoreboard.getEntityName(p_joinTeam_1_, lvt_11_1_.getUniqueID().toString());
                        if (lvt_4_1_.addPlayerToTeam(lvt_12_1_, lvt_5_1_)) {
                            lvt_6_1_.add(lvt_12_1_);
                            continue;
                        }
                        lvt_7_1_.add(lvt_12_1_);
                    }
                    continue;
                }
                String lvt_9_2_ = CommandScoreboard.getEntityName(p_joinTeam_1_, lvt_8_2_);
                if (lvt_4_1_.addPlayerToTeam(lvt_9_2_, lvt_5_1_)) {
                    lvt_6_1_.add(lvt_9_2_);
                    continue;
                }
                lvt_7_1_.add(lvt_9_2_);
            }
        }
        if (!lvt_6_1_.isEmpty()) {
            p_joinTeam_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, lvt_6_1_.size());
            CommandScoreboard.notifyOperators(p_joinTeam_1_, (ICommand)this, "commands.scoreboard.teams.join.success", lvt_6_1_.size(), lvt_5_1_, CommandScoreboard.joinNiceString(lvt_6_1_.toArray(new String[lvt_6_1_.size()])));
        }
        if (!lvt_7_1_.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", lvt_7_1_.size(), lvt_5_1_, CommandScoreboard.joinNiceString(lvt_7_1_.toArray(new String[lvt_7_1_.size()])));
        }
    }

    protected void leaveTeam(ICommandSender p_leaveTeam_1_, String[] p_leaveTeam_2_, int p_leaveTeam_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        HashSet<String> lvt_5_1_ = Sets.newHashSet();
        HashSet<String> lvt_6_1_ = Sets.newHashSet();
        if (p_leaveTeam_1_ instanceof EntityPlayer && p_leaveTeam_3_ == p_leaveTeam_2_.length) {
            String lvt_7_2_ = CommandScoreboard.getCommandSenderAsPlayer(p_leaveTeam_1_).getName();
            if (lvt_4_1_.removePlayerFromTeams(lvt_7_2_)) {
                lvt_5_1_.add(lvt_7_2_);
            } else {
                lvt_6_1_.add(lvt_7_2_);
            }
        } else {
            while (p_leaveTeam_3_ < p_leaveTeam_2_.length) {
                String lvt_7_2_;
                if ((lvt_7_2_ = p_leaveTeam_2_[p_leaveTeam_3_++]).startsWith("@")) {
                    List<Entity> lvt_8_1_ = CommandScoreboard.func_175763_c(p_leaveTeam_1_, lvt_7_2_);
                    for (Entity lvt_10_1_ : lvt_8_1_) {
                        String lvt_11_1_ = CommandScoreboard.getEntityName(p_leaveTeam_1_, lvt_10_1_.getUniqueID().toString());
                        if (lvt_4_1_.removePlayerFromTeams(lvt_11_1_)) {
                            lvt_5_1_.add(lvt_11_1_);
                            continue;
                        }
                        lvt_6_1_.add(lvt_11_1_);
                    }
                    continue;
                }
                String lvt_8_2_ = CommandScoreboard.getEntityName(p_leaveTeam_1_, lvt_7_2_);
                if (lvt_4_1_.removePlayerFromTeams(lvt_8_2_)) {
                    lvt_5_1_.add(lvt_8_2_);
                    continue;
                }
                lvt_6_1_.add(lvt_8_2_);
            }
        }
        if (!lvt_5_1_.isEmpty()) {
            p_leaveTeam_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, lvt_5_1_.size());
            CommandScoreboard.notifyOperators(p_leaveTeam_1_, (ICommand)this, "commands.scoreboard.teams.leave.success", lvt_5_1_.size(), CommandScoreboard.joinNiceString(lvt_5_1_.toArray(new String[lvt_5_1_.size()])));
        }
        if (!lvt_6_1_.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", lvt_6_1_.size(), CommandScoreboard.joinNiceString(lvt_6_1_.toArray(new String[lvt_6_1_.size()])));
        }
    }

    protected void emptyTeam(ICommandSender p_emptyTeam_1_, String[] p_emptyTeam_2_, int p_emptyTeam_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        ScorePlayerTeam lvt_5_1_ = this.getTeam(p_emptyTeam_2_[p_emptyTeam_3_]);
        if (lvt_5_1_ != null) {
            ArrayList<String> lvt_6_1_ = Lists.newArrayList(lvt_5_1_.getMembershipCollection());
            p_emptyTeam_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, lvt_6_1_.size());
            if (lvt_6_1_.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", lvt_5_1_.getRegisteredName());
            }
            for (String lvt_8_1_ : lvt_6_1_) {
                lvt_4_1_.removePlayerFromTeam(lvt_8_1_, lvt_5_1_);
            }
            CommandScoreboard.notifyOperators(p_emptyTeam_1_, (ICommand)this, "commands.scoreboard.teams.empty.success", lvt_6_1_.size(), lvt_5_1_.getRegisteredName());
        }
    }

    protected void removeObjective(ICommandSender p_removeObjective_1_, String p_removeObjective_2_) throws CommandException {
        Scoreboard lvt_3_1_ = this.getScoreboard();
        ScoreObjective lvt_4_1_ = this.getObjective(p_removeObjective_2_, false);
        lvt_3_1_.removeObjective(lvt_4_1_);
        CommandScoreboard.notifyOperators(p_removeObjective_1_, (ICommand)this, "commands.scoreboard.objectives.remove.success", p_removeObjective_2_);
    }

    protected void listObjectives(ICommandSender p_listObjectives_1_) throws CommandException {
        Scoreboard lvt_2_1_ = this.getScoreboard();
        Collection<ScoreObjective> lvt_3_1_ = lvt_2_1_.getScoreObjectives();
        if (lvt_3_1_.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        ChatComponentTranslation lvt_4_1_ = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", lvt_3_1_.size());
        lvt_4_1_.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        p_listObjectives_1_.addChatMessage(lvt_4_1_);
        for (ScoreObjective lvt_6_1_ : lvt_3_1_) {
            p_listObjectives_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", lvt_6_1_.getName(), lvt_6_1_.getDisplayName(), lvt_6_1_.getCriteria().getName()));
        }
    }

    protected void setObjectiveDisplay(ICommandSender p_setObjectiveDisplay_1_, String[] p_setObjectiveDisplay_2_, int p_setObjectiveDisplay_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        String lvt_5_1_ = p_setObjectiveDisplay_2_[p_setObjectiveDisplay_3_++];
        int lvt_6_1_ = Scoreboard.getObjectiveDisplaySlotNumber(lvt_5_1_);
        ScoreObjective lvt_7_1_ = null;
        if (p_setObjectiveDisplay_2_.length == 4) {
            lvt_7_1_ = this.getObjective(p_setObjectiveDisplay_2_[p_setObjectiveDisplay_3_], false);
        }
        if (lvt_6_1_ < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", lvt_5_1_);
        }
        lvt_4_1_.setObjectiveInDisplaySlot(lvt_6_1_, lvt_7_1_);
        if (lvt_7_1_ != null) {
            CommandScoreboard.notifyOperators(p_setObjectiveDisplay_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(lvt_6_1_), lvt_7_1_.getName());
        } else {
            CommandScoreboard.notifyOperators(p_setObjectiveDisplay_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(lvt_6_1_));
        }
    }

    protected void listPlayers(ICommandSender p_listPlayers_1_, String[] p_listPlayers_2_, int p_listPlayers_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        if (p_listPlayers_2_.length > p_listPlayers_3_) {
            String lvt_5_1_ = CommandScoreboard.getEntityName(p_listPlayers_1_, p_listPlayers_2_[p_listPlayers_3_]);
            Map<ScoreObjective, Score> lvt_6_1_ = lvt_4_1_.getObjectivesForEntity(lvt_5_1_);
            p_listPlayers_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, lvt_6_1_.size());
            if (lvt_6_1_.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", lvt_5_1_);
            }
            ChatComponentTranslation lvt_7_1_ = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", lvt_6_1_.size(), lvt_5_1_);
            lvt_7_1_.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_listPlayers_1_.addChatMessage(lvt_7_1_);
            for (Score lvt_9_1_ : lvt_6_1_.values()) {
                p_listPlayers_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", lvt_9_1_.getScorePoints(), lvt_9_1_.getObjective().getDisplayName(), lvt_9_1_.getObjective().getName()));
            }
        } else {
            Collection<String> lvt_5_2_ = lvt_4_1_.getObjectiveNames();
            p_listPlayers_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, lvt_5_2_.size());
            if (lvt_5_2_.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            ChatComponentTranslation lvt_6_2_ = new ChatComponentTranslation("commands.scoreboard.players.list.count", lvt_5_2_.size());
            lvt_6_2_.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_listPlayers_1_.addChatMessage(lvt_6_2_);
            p_listPlayers_1_.addChatMessage(new ChatComponentText(CommandScoreboard.joinNiceString(lvt_5_2_.toArray())));
        }
    }

    protected void setPlayer(ICommandSender p_setPlayer_1_, String[] p_setPlayer_2_, int p_setPlayer_3_) throws CommandException {
        int lvt_8_1_;
        String lvt_6_1_;
        String lvt_4_1_ = p_setPlayer_2_[p_setPlayer_3_ - 1];
        int lvt_5_1_ = p_setPlayer_3_;
        if ((lvt_6_1_ = CommandScoreboard.getEntityName(p_setPlayer_1_, p_setPlayer_2_[p_setPlayer_3_++])).length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", lvt_6_1_, 40);
        }
        ScoreObjective lvt_7_1_ = this.getObjective(p_setPlayer_2_[p_setPlayer_3_++], true);
        int n = lvt_8_1_ = lvt_4_1_.equalsIgnoreCase("set") ? CommandScoreboard.parseInt(p_setPlayer_2_[p_setPlayer_3_++]) : CommandScoreboard.parseInt(p_setPlayer_2_[p_setPlayer_3_++], 0);
        if (p_setPlayer_2_.length > p_setPlayer_3_) {
            Entity lvt_9_1_ = CommandScoreboard.getEntity(p_setPlayer_1_, p_setPlayer_2_[lvt_5_1_]);
            try {
                NBTTagCompound lvt_10_1_ = JsonToNBT.getTagFromJson(CommandScoreboard.buildString(p_setPlayer_2_, p_setPlayer_3_));
                NBTTagCompound lvt_11_1_ = new NBTTagCompound();
                lvt_9_1_.writeToNBT(lvt_11_1_);
                if (!NBTUtil.func_181123_a(lvt_10_1_, lvt_11_1_, true)) {
                    throw new CommandException("commands.scoreboard.players.set.tagMismatch", lvt_6_1_);
                }
            }
            catch (NBTException var12) {
                throw new CommandException("commands.scoreboard.players.set.tagError", var12.getMessage());
            }
        }
        Scoreboard lvt_9_2_ = this.getScoreboard();
        Score lvt_10_3_ = lvt_9_2_.getValueFromObjective(lvt_6_1_, lvt_7_1_);
        if (lvt_4_1_.equalsIgnoreCase("set")) {
            lvt_10_3_.setScorePoints(lvt_8_1_);
        } else if (lvt_4_1_.equalsIgnoreCase("add")) {
            lvt_10_3_.increseScore(lvt_8_1_);
        } else {
            lvt_10_3_.decreaseScore(lvt_8_1_);
        }
        CommandScoreboard.notifyOperators(p_setPlayer_1_, (ICommand)this, "commands.scoreboard.players.set.success", lvt_7_1_.getName(), lvt_6_1_, lvt_10_3_.getScorePoints());
    }

    protected void resetPlayers(ICommandSender p_resetPlayers_1_, String[] p_resetPlayers_2_, int p_resetPlayers_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        String lvt_5_1_ = CommandScoreboard.getEntityName(p_resetPlayers_1_, p_resetPlayers_2_[p_resetPlayers_3_++]);
        if (p_resetPlayers_2_.length > p_resetPlayers_3_) {
            ScoreObjective lvt_6_1_ = this.getObjective(p_resetPlayers_2_[p_resetPlayers_3_++], false);
            lvt_4_1_.removeObjectiveFromEntity(lvt_5_1_, lvt_6_1_);
            CommandScoreboard.notifyOperators(p_resetPlayers_1_, (ICommand)this, "commands.scoreboard.players.resetscore.success", lvt_6_1_.getName(), lvt_5_1_);
        } else {
            lvt_4_1_.removeObjectiveFromEntity(lvt_5_1_, null);
            CommandScoreboard.notifyOperators(p_resetPlayers_1_, (ICommand)this, "commands.scoreboard.players.reset.success", lvt_5_1_);
        }
    }

    protected void func_175779_n(ICommandSender p_175779_1_, String[] p_175779_2_, int p_175779_3_) throws CommandException {
        String lvt_5_1_;
        Scoreboard lvt_4_1_ = this.getScoreboard();
        if ((lvt_5_1_ = CommandScoreboard.getPlayerName(p_175779_1_, p_175779_2_[p_175779_3_++])).length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", lvt_5_1_, 40);
        }
        ScoreObjective lvt_6_1_ = this.getObjective(p_175779_2_[p_175779_3_], false);
        if (lvt_6_1_.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", lvt_6_1_.getName());
        }
        Score lvt_7_1_ = lvt_4_1_.getValueFromObjective(lvt_5_1_, lvt_6_1_);
        lvt_7_1_.setLocked(false);
        CommandScoreboard.notifyOperators(p_175779_1_, (ICommand)this, "commands.scoreboard.players.enable.success", lvt_6_1_.getName(), lvt_5_1_);
    }

    protected void func_175781_o(ICommandSender p_175781_1_, String[] p_175781_2_, int p_175781_3_) throws CommandException {
        ScoreObjective lvt_6_1_;
        String lvt_5_1_;
        Scoreboard lvt_4_1_ = this.getScoreboard();
        if ((lvt_5_1_ = CommandScoreboard.getEntityName(p_175781_1_, p_175781_2_[p_175781_3_++])).length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", lvt_5_1_, 40);
        }
        if (!lvt_4_1_.entityHasObjective(lvt_5_1_, lvt_6_1_ = this.getObjective(p_175781_2_[p_175781_3_++], false))) {
            throw new CommandException("commands.scoreboard.players.test.notFound", lvt_6_1_.getName(), lvt_5_1_);
        }
        int lvt_7_1_ = p_175781_2_[p_175781_3_].equals("*") ? Integer.MIN_VALUE : CommandScoreboard.parseInt(p_175781_2_[p_175781_3_]);
        int lvt_8_1_ = ++p_175781_3_ < p_175781_2_.length && !p_175781_2_[p_175781_3_].equals("*") ? CommandScoreboard.parseInt(p_175781_2_[p_175781_3_], lvt_7_1_) : Integer.MAX_VALUE;
        Score lvt_9_1_ = lvt_4_1_.getValueFromObjective(lvt_5_1_, lvt_6_1_);
        if (lvt_9_1_.getScorePoints() < lvt_7_1_ || lvt_9_1_.getScorePoints() > lvt_8_1_) {
            throw new CommandException("commands.scoreboard.players.test.failed", lvt_9_1_.getScorePoints(), lvt_7_1_, lvt_8_1_);
        }
        CommandScoreboard.notifyOperators(p_175781_1_, (ICommand)this, "commands.scoreboard.players.test.success", lvt_9_1_.getScorePoints(), lvt_7_1_, lvt_8_1_);
    }

    protected void func_175778_p(ICommandSender p_175778_1_, String[] p_175778_2_, int p_175778_3_) throws CommandException {
        Scoreboard lvt_4_1_ = this.getScoreboard();
        String lvt_5_1_ = CommandScoreboard.getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
        ScoreObjective lvt_6_1_ = this.getObjective(p_175778_2_[p_175778_3_++], true);
        String lvt_7_1_ = p_175778_2_[p_175778_3_++];
        String lvt_8_1_ = CommandScoreboard.getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
        ScoreObjective lvt_9_1_ = this.getObjective(p_175778_2_[p_175778_3_], false);
        if (lvt_5_1_.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", lvt_5_1_, 40);
        }
        if (lvt_8_1_.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", lvt_8_1_, 40);
        }
        Score lvt_10_1_ = lvt_4_1_.getValueFromObjective(lvt_5_1_, lvt_6_1_);
        if (!lvt_4_1_.entityHasObjective(lvt_8_1_, lvt_9_1_)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", lvt_9_1_.getName(), lvt_8_1_);
        }
        Score lvt_11_1_ = lvt_4_1_.getValueFromObjective(lvt_8_1_, lvt_9_1_);
        if (lvt_7_1_.equals("+=")) {
            lvt_10_1_.setScorePoints(lvt_10_1_.getScorePoints() + lvt_11_1_.getScorePoints());
        } else if (lvt_7_1_.equals("-=")) {
            lvt_10_1_.setScorePoints(lvt_10_1_.getScorePoints() - lvt_11_1_.getScorePoints());
        } else if (lvt_7_1_.equals("*=")) {
            lvt_10_1_.setScorePoints(lvt_10_1_.getScorePoints() * lvt_11_1_.getScorePoints());
        } else if (lvt_7_1_.equals("/=")) {
            if (lvt_11_1_.getScorePoints() != 0) {
                lvt_10_1_.setScorePoints(lvt_10_1_.getScorePoints() / lvt_11_1_.getScorePoints());
            }
        } else if (lvt_7_1_.equals("%=")) {
            if (lvt_11_1_.getScorePoints() != 0) {
                lvt_10_1_.setScorePoints(lvt_10_1_.getScorePoints() % lvt_11_1_.getScorePoints());
            }
        } else if (lvt_7_1_.equals("=")) {
            lvt_10_1_.setScorePoints(lvt_11_1_.getScorePoints());
        } else if (lvt_7_1_.equals("<")) {
            lvt_10_1_.setScorePoints(Math.min(lvt_10_1_.getScorePoints(), lvt_11_1_.getScorePoints()));
        } else if (lvt_7_1_.equals(">")) {
            lvt_10_1_.setScorePoints(Math.max(lvt_10_1_.getScorePoints(), lvt_11_1_.getScorePoints()));
        } else {
            if (!lvt_7_1_.equals("><")) {
                throw new CommandException("commands.scoreboard.players.operation.invalidOperation", lvt_7_1_);
            }
            int lvt_12_1_ = lvt_10_1_.getScorePoints();
            lvt_10_1_.setScorePoints(lvt_11_1_.getScorePoints());
            lvt_11_1_.setScorePoints(lvt_12_1_);
        }
        CommandScoreboard.notifyOperators(p_175778_1_, (ICommand)this, "commands.scoreboard.players.operation.success", new Object[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender p_addTabCompletionOptions_1_, String[] p_addTabCompletionOptions_2_, BlockPos p_addTabCompletionOptions_3_) {
        if (p_addTabCompletionOptions_2_.length == 1) {
            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "objectives", "players", "teams");
        }
        if (p_addTabCompletionOptions_2_[0].equalsIgnoreCase("objectives")) {
            if (p_addTabCompletionOptions_2_.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "list", "add", "remove", "setdisplay");
            }
            if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("add")) {
                if (p_addTabCompletionOptions_2_.length == 4) {
                    Set<String> lvt_4_1_ = IScoreObjectiveCriteria.INSTANCES.keySet();
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, lvt_4_1_);
                }
            } else if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("remove")) {
                if (p_addTabCompletionOptions_2_.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(false));
                }
            } else if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("setdisplay")) {
                if (p_addTabCompletionOptions_2_.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, Scoreboard.getDisplaySlotStrings());
                }
                if (p_addTabCompletionOptions_2_.length == 4) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(false));
                }
            }
        } else if (p_addTabCompletionOptions_2_[0].equalsIgnoreCase("players")) {
            if (p_addTabCompletionOptions_2_.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "set", "add", "remove", "reset", "list", "enable", "test", "operation");
            }
            if (!(p_addTabCompletionOptions_2_[1].equalsIgnoreCase("set") || p_addTabCompletionOptions_2_[1].equalsIgnoreCase("add") || p_addTabCompletionOptions_2_[1].equalsIgnoreCase("remove") || p_addTabCompletionOptions_2_[1].equalsIgnoreCase("reset"))) {
                if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("enable")) {
                    if (p_addTabCompletionOptions_2_.length == 3) {
                        return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, MinecraftServer.getServer().getAllUsernames());
                    }
                    if (p_addTabCompletionOptions_2_.length == 4) {
                        return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_175782_e());
                    }
                } else if (!p_addTabCompletionOptions_2_[1].equalsIgnoreCase("list") && !p_addTabCompletionOptions_2_[1].equalsIgnoreCase("test")) {
                    if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("operation")) {
                        if (p_addTabCompletionOptions_2_.length == 3) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.getScoreboard().getObjectiveNames());
                        }
                        if (p_addTabCompletionOptions_2_.length == 4) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(true));
                        }
                        if (p_addTabCompletionOptions_2_.length == 5) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");
                        }
                        if (p_addTabCompletionOptions_2_.length == 6) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, MinecraftServer.getServer().getAllUsernames());
                        }
                        if (p_addTabCompletionOptions_2_.length == 7) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(false));
                        }
                    }
                } else {
                    if (p_addTabCompletionOptions_2_.length == 3) {
                        return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.getScoreboard().getObjectiveNames());
                    }
                    if (p_addTabCompletionOptions_2_.length == 4 && p_addTabCompletionOptions_2_[1].equalsIgnoreCase("test")) {
                        return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(false));
                    }
                }
            } else {
                if (p_addTabCompletionOptions_2_.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, MinecraftServer.getServer().getAllUsernames());
                }
                if (p_addTabCompletionOptions_2_.length == 4) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.func_147184_a(true));
                }
            }
        } else if (p_addTabCompletionOptions_2_[0].equalsIgnoreCase("teams")) {
            if (p_addTabCompletionOptions_2_.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("join")) {
                if (p_addTabCompletionOptions_2_.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.getScoreboard().getTeamNames());
                }
                if (p_addTabCompletionOptions_2_.length >= 4) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, MinecraftServer.getServer().getAllUsernames());
                }
            } else {
                if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("leave")) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, MinecraftServer.getServer().getAllUsernames());
                }
                if (!(p_addTabCompletionOptions_2_[1].equalsIgnoreCase("empty") || p_addTabCompletionOptions_2_[1].equalsIgnoreCase("list") || p_addTabCompletionOptions_2_[1].equalsIgnoreCase("remove"))) {
                    if (p_addTabCompletionOptions_2_[1].equalsIgnoreCase("option")) {
                        if (p_addTabCompletionOptions_2_.length == 3) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.getScoreboard().getTeamNames());
                        }
                        if (p_addTabCompletionOptions_2_.length == 4) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility");
                        }
                        if (p_addTabCompletionOptions_2_.length == 5) {
                            if (p_addTabCompletionOptions_2_[3].equalsIgnoreCase("color")) {
                                return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, EnumChatFormatting.getValidValues(true, false));
                            }
                            if (!p_addTabCompletionOptions_2_[3].equalsIgnoreCase("nametagVisibility") && !p_addTabCompletionOptions_2_[3].equalsIgnoreCase("deathMessageVisibility")) {
                                if (!p_addTabCompletionOptions_2_[3].equalsIgnoreCase("friendlyfire") && !p_addTabCompletionOptions_2_[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                    return null;
                                }
                                return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, "true", "false");
                            }
                            return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, Team.EnumVisible.func_178825_a());
                        }
                    }
                } else if (p_addTabCompletionOptions_2_.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(p_addTabCompletionOptions_2_, this.getScoreboard().getTeamNames());
                }
            }
        }
        return null;
    }

    protected List<String> func_147184_a(boolean p_147184_1_) {
        Collection<ScoreObjective> lvt_2_1_ = this.getScoreboard().getScoreObjectives();
        ArrayList<String> lvt_3_1_ = Lists.newArrayList();
        Iterator<ScoreObjective> lvt_4_1_ = lvt_2_1_.iterator();
        while (lvt_4_1_.hasNext()) {
            ScoreObjective lvt_5_1_ = lvt_4_1_.next();
            if (p_147184_1_ && lvt_5_1_.getCriteria().isReadOnly()) continue;
            lvt_3_1_.add(lvt_5_1_.getName());
        }
        return lvt_3_1_;
    }

    protected List<String> func_175782_e() {
        Collection<ScoreObjective> lvt_1_1_ = this.getScoreboard().getScoreObjectives();
        ArrayList<String> lvt_2_1_ = Lists.newArrayList();
        for (ScoreObjective lvt_4_1_ : lvt_1_1_) {
            if (lvt_4_1_.getCriteria() != IScoreObjectiveCriteria.TRIGGER) continue;
            lvt_2_1_.add(lvt_4_1_.getName());
        }
        return lvt_2_1_;
    }

    @Override
    public boolean isUsernameIndex(String[] p_isUsernameIndex_1_, int p_isUsernameIndex_2_) {
        if (!p_isUsernameIndex_1_[0].equalsIgnoreCase("players")) {
            if (p_isUsernameIndex_1_[0].equalsIgnoreCase("teams")) {
                return p_isUsernameIndex_2_ == 2;
            }
            return false;
        }
        if (p_isUsernameIndex_1_.length > 1 && p_isUsernameIndex_1_[1].equalsIgnoreCase("operation")) {
            return p_isUsernameIndex_2_ == 2 || p_isUsernameIndex_2_ == 5;
        }
        return p_isUsernameIndex_2_ == 2;
    }
}

