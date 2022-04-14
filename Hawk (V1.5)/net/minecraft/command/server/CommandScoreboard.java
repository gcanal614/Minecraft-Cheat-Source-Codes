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
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
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

public class CommandScoreboard extends CommandBase {
   private static final String __OBFID = "CL_00000896";

   public String getCommandName() {
      return "scoreboard";
   }

   protected void setPlayer(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3 - 1];
      int var5 = var3;
      String var6 = func_175758_e(var1, var2[var3++]);
      ScoreObjective var7 = this.func_147189_a(var2[var3++], true);
      int var8 = var4.equalsIgnoreCase("set") ? parseInt(var2[var3++]) : parseInt(var2[var3++], 0);
      if (var2.length > var3) {
         Entity var9 = func_175768_b(var1, var2[var5]);

         try {
            NBTTagCompound var10 = JsonToNBT.func_180713_a(func_180529_a(var2, var3));
            NBTTagCompound var11 = new NBTTagCompound();
            var9.writeToNBT(var11);
            if (!CommandTestForBlock.func_175775_a(var10, var11, true)) {
               throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[]{var6});
            }
         } catch (NBTException var12) {
            throw new CommandException("commands.scoreboard.players.set.tagError", new Object[]{var12.getMessage()});
         }
      }

      Scoreboard var13 = this.getScoreboard();
      Score var14 = var13.getValueFromObjective(var6, var7);
      if (var4.equalsIgnoreCase("set")) {
         var14.setScorePoints(var8);
      } else if (var4.equalsIgnoreCase("add")) {
         var14.increseScore(var8);
      } else {
         var14.decreaseScore(var8);
      }

      notifyOperators(var1, this, "commands.scoreboard.players.set.success", new Object[]{var7.getName(), var6, var14.getScorePoints()});
   }

   protected void addTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3++];
      Scoreboard var5 = this.getScoreboard();
      if (var5.getTeam(var4) != null) {
         throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[]{var4});
      } else if (var4.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[]{var4, 16});
      } else if (var4.length() == 0) {
         throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
      } else {
         if (var2.length > var3) {
            String var6 = getChatComponentFromNthArg(var1, var2, var3).getUnformattedText();
            if (var6.length() > 32) {
               throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[]{var6, 32});
            }

            if (var6.length() > 0) {
               var5.createTeam(var4).setTeamName(var6);
            } else {
               var5.createTeam(var4);
            }
         } else {
            var5.createTeam(var4);
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.add.success", new Object[]{var4});
      }
   }

   protected void func_175779_n(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = getPlayerName(var1, var2[var3++]);
      ScoreObjective var6 = this.func_147189_a(var2[var3], false);
      if (var6.getCriteria() != IScoreObjectiveCriteria.field_178791_c) {
         throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[]{var6.getName()});
      } else {
         Score var7 = var4.getValueFromObjective(var5, var6);
         var7.func_178815_a(false);
         notifyOperators(var1, this, "commands.scoreboard.players.enable.success", new Object[]{var6.getName(), var5});
      }
   }

   protected void emptyTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      ScorePlayerTeam var5 = this.func_147183_a(var2[var3]);
      if (var5 != null) {
         ArrayList var6 = Lists.newArrayList(var5.getMembershipCollection());
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var6.size());
         if (var6.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[]{var5.getRegisteredName()});
         }

         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            var4.removePlayerFromTeam(var8, var5);
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.empty.success", new Object[]{var6.size(), var5.getRegisteredName()});
      }

   }

   protected void setObjectiveDisplay(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = var2[var3++];
      int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
      ScoreObjective var7 = null;
      if (var2.length == 4) {
         var7 = this.func_147189_a(var2[var3], false);
      }

      if (var6 < 0) {
         throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[]{var5});
      } else {
         var4.setObjectiveInDisplaySlot(var6, var7);
         if (var7 != null) {
            notifyOperators(var1, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[]{Scoreboard.getObjectiveDisplaySlot(var6), var7.getName()});
         } else {
            notifyOperators(var1, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[]{Scoreboard.getObjectiveDisplaySlot(var6)});
         }

      }
   }

   private boolean func_175780_b(ICommandSender var1, String[] var2) throws CommandException {
      int var3 = -1;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         if (this.isUsernameIndex(var2, var4) && "*".equals(var2[var4])) {
            if (var3 >= 0) {
               throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
            }

            var3 = var4;
         }
      }

      if (var3 < 0) {
         return false;
      } else {
         ArrayList var12 = Lists.newArrayList(this.getScoreboard().getObjectiveNames());
         String var5 = var2[var3];
         ArrayList var6 = Lists.newArrayList();
         Iterator var7 = var12.iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            var2[var3] = var8;

            try {
               this.processCommand(var1, var2);
               var6.add(var8);
            } catch (CommandException var11) {
               ChatComponentTranslation var10 = new ChatComponentTranslation(var11.getMessage(), var11.getErrorOjbects());
               var10.getChatStyle().setColor(EnumChatFormatting.RED);
               var1.addChatMessage(var10);
            }
         }

         var2[var3] = var5;
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var6.size());
         if (var6.size() == 0) {
            throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
         } else {
            return true;
         }
      }
   }

   protected void setTeamOption(ICommandSender var1, String[] var2, int var3) throws CommandException {
      ScorePlayerTeam var4 = this.func_147183_a(var2[var3++]);
      if (var4 != null) {
         String var5 = var2[var3++].toLowerCase();
         if (!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles") && !var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
         }

         if (var2.length == 4) {
            if (var5.equalsIgnoreCase("color")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))});
            }

            if (!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
               if (!var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
               }

               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team.EnumVisible.func_178825_a())});
            }

            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList("true", "false"))});
         }

         String var6 = var2[var3];
         if (var5.equalsIgnoreCase("color")) {
            EnumChatFormatting var8 = EnumChatFormatting.getValueByName(var6);
            if (var8 == null || var8.isFancyStyling()) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))});
            }

            var4.func_178774_a(var8);
            var4.setNamePrefix(var8.toString());
            var4.setNameSuffix(EnumChatFormatting.RESET.toString());
         } else if (var5.equalsIgnoreCase("friendlyfire")) {
            if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList("true", "false"))});
            }

            var4.setAllowFriendlyFire(var6.equalsIgnoreCase("true"));
         } else if (var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
            if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList("true", "false"))});
            }

            var4.setSeeFriendlyInvisiblesEnabled(var6.equalsIgnoreCase("true"));
         } else {
            Team.EnumVisible var7;
            if (var5.equalsIgnoreCase("nametagVisibility")) {
               var7 = Team.EnumVisible.func_178824_a(var6);
               if (var7 == null) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team.EnumVisible.func_178825_a())});
               }

               var4.func_178772_a(var7);
            } else if (var5.equalsIgnoreCase("deathMessageVisibility")) {
               var7 = Team.EnumVisible.func_178824_a(var6);
               if (var7 == null) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team.EnumVisible.func_178825_a())});
               }

               var4.func_178773_b(var7);
            }
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.option.success", new Object[]{var5, var4.getRegisteredName(), var6});
      }

   }

   protected void listTeams(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      if (var2.length > var3) {
         ScorePlayerTeam var5 = this.func_147183_a(var2[var3]);
         if (var5 == null) {
            return;
         }

         Collection var6 = var5.getMembershipCollection();
         var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var6.size());
         if (var6.size() <= 0) {
            throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[]{var5.getRegisteredName()});
         }

         ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[]{var6.size(), var5.getRegisteredName()});
         var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var7);
         var1.addChatMessage(new ChatComponentText(joinNiceString(var6.toArray())));
      } else {
         Collection var9 = var4.getTeams();
         var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var9.size());
         if (var9.size() <= 0) {
            throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
         }

         ChatComponentTranslation var10 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[]{var9.size()});
         var10.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var10);
         Iterator var11 = var9.iterator();

         while(var11.hasNext()) {
            ScorePlayerTeam var8 = (ScorePlayerTeam)var11.next();
            var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[]{var8.getRegisteredName(), var8.func_96669_c(), var8.getMembershipCollection().size()}));
         }
      }

   }

   protected void resetPlayers(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = func_175758_e(var1, var2[var3++]);
      if (var2.length > var3) {
         ScoreObjective var6 = this.func_147189_a(var2[var3++], false);
         var4.func_178822_d(var5, var6);
         notifyOperators(var1, this, "commands.scoreboard.players.resetscore.success", new Object[]{var6.getName(), var5});
      } else {
         var4.func_178822_d(var5, (ScoreObjective)null);
         notifyOperators(var1, this, "commands.scoreboard.players.reset.success", new Object[]{var5});
      }

   }

   protected List func_147184_a(boolean var1) {
      Collection var2 = this.getScoreboard().getScoreObjectives();
      ArrayList var3 = Lists.newArrayList();
      Iterator var4 = var2.iterator();

      while(true) {
         ScoreObjective var5;
         do {
            if (!var4.hasNext()) {
               return var3;
            }

            var5 = (ScoreObjective)var4.next();
         } while(var1 && var5.getCriteria().isReadOnly());

         var3.add(var5.getName());
      }
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   protected List func_175782_e() {
      Collection var1 = this.getScoreboard().getScoreObjectives();
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ScoreObjective var4 = (ScoreObjective)var3.next();
         if (var4.getCriteria() == IScoreObjectiveCriteria.field_178791_c) {
            var2.add(var4.getName());
         }
      }

      return var2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return getListOfStringsMatchingLastWord(var2, new String[]{"objectives", "players", "teams"});
      } else {
         if (var2[0].equalsIgnoreCase("objectives")) {
            if (var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"list", "add", "remove", "setdisplay"});
            }

            if (var2[1].equalsIgnoreCase("add")) {
               if (var2.length == 4) {
                  Set var4 = IScoreObjectiveCriteria.INSTANCES.keySet();
                  return func_175762_a(var2, var4);
               }
            } else if (var2[1].equalsIgnoreCase("remove")) {
               if (var2.length == 3) {
                  return func_175762_a(var2, this.func_147184_a(false));
               }
            } else if (var2[1].equalsIgnoreCase("setdisplay")) {
               if (var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, Scoreboard.func_178821_h());
               }

               if (var2.length == 4) {
                  return func_175762_a(var2, this.func_147184_a(false));
               }
            }
         } else if (var2[0].equalsIgnoreCase("players")) {
            if (var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"set", "add", "remove", "reset", "list", "enable", "test", "operation"});
            }

            if (!var2[1].equalsIgnoreCase("set") && !var2[1].equalsIgnoreCase("add") && !var2[1].equalsIgnoreCase("remove") && !var2[1].equalsIgnoreCase("reset")) {
               if (var2[1].equalsIgnoreCase("enable")) {
                  if (var2.length == 3) {
                     return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
                  }

                  if (var2.length == 4) {
                     return func_175762_a(var2, this.func_175782_e());
                  }
               } else if (!var2[1].equalsIgnoreCase("list") && !var2[1].equalsIgnoreCase("test")) {
                  if (var2[1].equalsIgnoreCase("operation")) {
                     if (var2.length == 3) {
                        return func_175762_a(var2, this.getScoreboard().getObjectiveNames());
                     }

                     if (var2.length == 4) {
                        return func_175762_a(var2, this.func_147184_a(true));
                     }

                     if (var2.length == 5) {
                        return getListOfStringsMatchingLastWord(var2, new String[]{"+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><"});
                     }

                     if (var2.length == 6) {
                        return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
                     }

                     if (var2.length == 7) {
                        return func_175762_a(var2, this.func_147184_a(false));
                     }
                  }
               } else {
                  if (var2.length == 3) {
                     return func_175762_a(var2, this.getScoreboard().getObjectiveNames());
                  }

                  if (var2.length == 4 && var2[1].equalsIgnoreCase("test")) {
                     return func_175762_a(var2, this.func_147184_a(false));
                  }
               }
            } else {
               if (var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }

               if (var2.length == 4) {
                  return func_175762_a(var2, this.func_147184_a(true));
               }
            }
         } else if (var2[0].equalsIgnoreCase("teams")) {
            if (var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"add", "remove", "join", "leave", "empty", "list", "option"});
            }

            if (var2[1].equalsIgnoreCase("join")) {
               if (var2.length == 3) {
                  return func_175762_a(var2, this.getScoreboard().getTeamNames());
               }

               if (var2.length >= 4) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }
            } else {
               if (var2[1].equalsIgnoreCase("leave")) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }

               if (!var2[1].equalsIgnoreCase("empty") && !var2[1].equalsIgnoreCase("list") && !var2[1].equalsIgnoreCase("remove")) {
                  if (var2[1].equalsIgnoreCase("option")) {
                     if (var2.length == 3) {
                        return func_175762_a(var2, this.getScoreboard().getTeamNames());
                     }

                     if (var2.length == 4) {
                        return getListOfStringsMatchingLastWord(var2, new String[]{"color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility"});
                     }

                     if (var2.length == 5) {
                        if (var2[3].equalsIgnoreCase("color")) {
                           return func_175762_a(var2, EnumChatFormatting.getValidValues(true, false));
                        }

                        if (var2[3].equalsIgnoreCase("nametagVisibility") || var2[3].equalsIgnoreCase("deathMessageVisibility")) {
                           return getListOfStringsMatchingLastWord(var2, Team.EnumVisible.func_178825_a());
                        }

                        if (var2[3].equalsIgnoreCase("friendlyfire") || var2[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                           return getListOfStringsMatchingLastWord(var2, new String[]{"true", "false"});
                        }
                     }
                  }
               } else if (var2.length == 3) {
                  return func_175762_a(var2, this.getScoreboard().getTeamNames());
               }
            }
         }

         return null;
      }
   }

   protected ScoreObjective func_147189_a(String var1, boolean var2) throws CommandException {
      Scoreboard var3 = this.getScoreboard();
      ScoreObjective var4 = var3.getObjective(var1);
      if (var4 == null) {
         throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[]{var1});
      } else if (var2 && var4.getCriteria().isReadOnly()) {
         throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[]{var1});
      } else {
         return var4;
      }
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return !var1[0].equalsIgnoreCase("players") ? (var1[0].equalsIgnoreCase("teams") ? var2 == 2 : false) : (var1.length > 1 && var1[1].equalsIgnoreCase("operation") ? var2 == 2 || var2 == 5 : var2 == 2);
   }

   protected void listObjectives(ICommandSender var1) throws CommandException {
      Scoreboard var2 = this.getScoreboard();
      Collection var3 = var2.getScoreObjectives();
      if (var3.size() <= 0) {
         throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
      } else {
         ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[]{var3.size()});
         var4.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var4);
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            ScoreObjective var6 = (ScoreObjective)var5.next();
            var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[]{var6.getName(), var6.getDisplayName(), var6.getCriteria().getName()}));
         }

      }
   }

   protected void joinTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = var2[var3++];
      HashSet var6 = Sets.newHashSet();
      HashSet var7 = Sets.newHashSet();
      String var8;
      if (var1 instanceof EntityPlayer && var3 == var2.length) {
         var8 = getCommandSenderAsPlayer(var1).getName();
         if (var4.func_151392_a(var8, var5)) {
            var6.add(var8);
         } else {
            var7.add(var8);
         }
      } else {
         label61:
         while(true) {
            while(true) {
               if (var3 >= var2.length) {
                  break label61;
               }

               var8 = var2[var3++];
               if (var8.startsWith("@")) {
                  List var13 = func_175763_c(var1, var8);
                  Iterator var10 = var13.iterator();

                  while(var10.hasNext()) {
                     Entity var11 = (Entity)var10.next();
                     String var12 = func_175758_e(var1, var11.getUniqueID().toString());
                     if (var4.func_151392_a(var12, var5)) {
                        var6.add(var12);
                     } else {
                        var7.add(var12);
                     }
                  }
               } else {
                  String var9 = func_175758_e(var1, var8);
                  if (var4.func_151392_a(var9, var5)) {
                     var6.add(var9);
                  } else {
                     var7.add(var9);
                  }
               }
            }
         }
      }

      if (!var6.isEmpty()) {
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var6.size());
         notifyOperators(var1, this, "commands.scoreboard.teams.join.success", new Object[]{var6.size(), var5, joinNiceString(var6.toArray(new String[0]))});
      }

      if (!var7.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.join.failure", new Object[]{var7.size(), var5, joinNiceString(var7.toArray(new String[0]))});
      }
   }

   protected void leaveTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      HashSet var5 = Sets.newHashSet();
      HashSet var6 = Sets.newHashSet();
      String var7;
      if (var1 instanceof EntityPlayer && var3 == var2.length) {
         var7 = getCommandSenderAsPlayer(var1).getName();
         if (var4.removePlayerFromTeams(var7)) {
            var5.add(var7);
         } else {
            var6.add(var7);
         }
      } else {
         label60:
         while(true) {
            while(true) {
               if (var3 >= var2.length) {
                  break label60;
               }

               var7 = var2[var3++];
               if (var7.startsWith("@")) {
                  List var12 = func_175763_c(var1, var7);
                  Iterator var9 = var12.iterator();

                  while(var9.hasNext()) {
                     Entity var10 = (Entity)var9.next();
                     String var11 = func_175758_e(var1, var10.getUniqueID().toString());
                     if (var4.removePlayerFromTeams(var11)) {
                        var5.add(var11);
                     } else {
                        var6.add(var11);
                     }
                  }
               } else {
                  String var8 = func_175758_e(var1, var7);
                  if (var4.removePlayerFromTeams(var8)) {
                     var5.add(var8);
                  } else {
                     var6.add(var8);
                  }
               }
            }
         }
      }

      if (!var5.isEmpty()) {
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var5.size());
         notifyOperators(var1, this, "commands.scoreboard.teams.leave.success", new Object[]{var5.size(), joinNiceString(var5.toArray(new String[0]))});
      }

      if (!var6.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[]{var6.size(), joinNiceString(var6.toArray(new String[0]))});
      }
   }

   protected Scoreboard getScoreboard() {
      return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
   }

   protected void removeTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      ScorePlayerTeam var5 = this.func_147183_a(var2[var3]);
      if (var5 != null) {
         var4.removeTeam(var5);
         notifyOperators(var1, this, "commands.scoreboard.teams.remove.success", new Object[]{var5.getRegisteredName()});
      }

   }

   protected void func_175781_o(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = func_175758_e(var1, var2[var3++]);
      ScoreObjective var6 = this.func_147189_a(var2[var3++], false);
      if (!var4.func_178819_b(var5, var6)) {
         throw new CommandException("commands.scoreboard.players.test.notFound", new Object[]{var6.getName(), var5});
      } else {
         int var7 = var2[var3].equals("*") ? Integer.MIN_VALUE : parseInt(var2[var3]);
         ++var3;
         int var8 = var3 < var2.length && !var2[var3].equals("*") ? parseInt(var2[var3], var7) : Integer.MAX_VALUE;
         Score var9 = var4.getValueFromObjective(var5, var6);
         if (var9.getScorePoints() >= var7 && var9.getScorePoints() <= var8) {
            notifyOperators(var1, this, "commands.scoreboard.players.test.success", new Object[]{var9.getScorePoints(), var7, var8});
         } else {
            throw new CommandException("commands.scoreboard.players.test.failed", new Object[]{var9.getScorePoints(), var7, var8});
         }
      }
   }

   protected void func_175778_p(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = func_175758_e(var1, var2[var3++]);
      ScoreObjective var6 = this.func_147189_a(var2[var3++], true);
      String var7 = var2[var3++];
      String var8 = func_175758_e(var1, var2[var3++]);
      ScoreObjective var9 = this.func_147189_a(var2[var3], false);
      Score var10 = var4.getValueFromObjective(var5, var6);
      if (!var4.func_178819_b(var8, var9)) {
         throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[]{var9.getName(), var8});
      } else {
         Score var11 = var4.getValueFromObjective(var8, var9);
         if (var7.equals("+=")) {
            var10.setScorePoints(var10.getScorePoints() + var11.getScorePoints());
         } else if (var7.equals("-=")) {
            var10.setScorePoints(var10.getScorePoints() - var11.getScorePoints());
         } else if (var7.equals("*=")) {
            var10.setScorePoints(var10.getScorePoints() * var11.getScorePoints());
         } else if (var7.equals("/=")) {
            if (var11.getScorePoints() != 0) {
               var10.setScorePoints(var10.getScorePoints() / var11.getScorePoints());
            }
         } else if (var7.equals("%=")) {
            if (var11.getScorePoints() != 0) {
               var10.setScorePoints(var10.getScorePoints() % var11.getScorePoints());
            }
         } else if (var7.equals("=")) {
            var10.setScorePoints(var11.getScorePoints());
         } else if (var7.equals("<")) {
            var10.setScorePoints(Math.min(var10.getScorePoints(), var11.getScorePoints()));
         } else if (var7.equals(">")) {
            var10.setScorePoints(Math.max(var10.getScorePoints(), var11.getScorePoints()));
         } else {
            if (!var7.equals("><")) {
               throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[]{var7});
            }

            int var12 = var10.getScorePoints();
            var10.setScorePoints(var11.getScorePoints());
            var11.setScorePoints(var12);
         }

         notifyOperators(var1, this, "commands.scoreboard.players.operation.success", new Object[0]);
      }
   }

   protected void addObjective(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3++];
      String var5 = var2[var3++];
      Scoreboard var6 = this.getScoreboard();
      IScoreObjectiveCriteria var7 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(var5);
      if (var7 == null) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[]{var5});
      } else if (var6.getObjective(var4) != null) {
         throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[]{var4});
      } else if (var4.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[]{var4, 16});
      } else if (var4.length() == 0) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
      } else {
         if (var2.length > var3) {
            String var8 = getChatComponentFromNthArg(var1, var2, var3).getUnformattedText();
            if (var8.length() > 32) {
               throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[]{var8, 32});
            }

            if (var8.length() > 0) {
               var6.addScoreObjective(var4, var7).setDisplayName(var8);
            } else {
               var6.addScoreObjective(var4, var7);
            }
         } else {
            var6.addScoreObjective(var4, var7);
         }

         notifyOperators(var1, this, "commands.scoreboard.objectives.add.success", new Object[]{var4});
      }
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.scoreboard.usage";
   }

   protected void removeObjective(ICommandSender var1, String var2) throws CommandException {
      Scoreboard var3 = this.getScoreboard();
      ScoreObjective var4 = this.func_147189_a(var2, false);
      var3.func_96519_k(var4);
      notifyOperators(var1, this, "commands.scoreboard.objectives.remove.success", new Object[]{var2});
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (!this.func_175780_b(var1, var2)) {
         if (var2.length < 1) {
            throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
         }

         if (var2[0].equalsIgnoreCase("objectives")) {
            if (var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
            }

            if (var2[1].equalsIgnoreCase("list")) {
               this.listObjectives(var1);
            } else if (var2[1].equalsIgnoreCase("add")) {
               if (var2.length < 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
               }

               this.addObjective(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("remove")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
               }

               this.removeObjective(var1, var2[2]);
            } else {
               if (!var2[1].equalsIgnoreCase("setdisplay")) {
                  throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
               }

               if (var2.length != 3 && var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
               }

               this.setObjectiveDisplay(var1, var2, 2);
            }
         } else if (var2[0].equalsIgnoreCase("players")) {
            if (var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
            }

            if (var2[1].equalsIgnoreCase("list")) {
               if (var2.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
               }

               this.listPlayers(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("add")) {
               if (var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("remove")) {
               if (var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("set")) {
               if (var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("reset")) {
               if (var2.length != 3 && var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
               }

               this.resetPlayers(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("enable")) {
               if (var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
               }

               this.func_175779_n(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("test")) {
               if (var2.length != 5 && var2.length != 6) {
                  throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
               }

               this.func_175781_o(var1, var2, 2);
            } else {
               if (!var2[1].equalsIgnoreCase("operation")) {
                  throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
               }

               if (var2.length != 7) {
                  throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
               }

               this.func_175778_p(var1, var2, 2);
            }
         } else if (var2[0].equalsIgnoreCase("teams")) {
            if (var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
            }

            if (var2[1].equalsIgnoreCase("list")) {
               if (var2.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
               }

               this.listTeams(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("add")) {
               if (var2.length < 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
               }

               this.addTeam(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("remove")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
               }

               this.removeTeam(var1, var2, 2);
            } else if (var2[1].equalsIgnoreCase("empty")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
               }

               this.emptyTeam(var1, var2, 2);
            } else if (!var2[1].equalsIgnoreCase("join")) {
               if (var2[1].equalsIgnoreCase("leave")) {
                  if (var2.length < 3 && !(var1 instanceof EntityPlayer)) {
                     throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                  }

                  this.leaveTeam(var1, var2, 2);
               } else {
                  if (!var2[1].equalsIgnoreCase("option")) {
                     throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                  }

                  if (var2.length != 4 && var2.length != 5) {
                     throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                  }

                  this.setTeamOption(var1, var2, 2);
               }
            } else {
               if (var2.length < 4 && (var2.length != 3 || !(var1 instanceof EntityPlayer))) {
                  throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
               }

               this.joinTeam(var1, var2, 2);
            }
         }
      }

   }

   protected ScorePlayerTeam func_147183_a(String var1) throws CommandException {
      Scoreboard var2 = this.getScoreboard();
      ScorePlayerTeam var3 = var2.getTeam(var1);
      if (var3 == null) {
         throw new CommandException("commands.scoreboard.teamNotFound", new Object[]{var1});
      } else {
         return var3;
      }
   }

   protected void listPlayers(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      if (var2.length > var3) {
         String var5 = func_175758_e(var1, var2[var3]);
         Map var6 = var4.func_96510_d(var5);
         var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var6.size());
         if (var6.size() <= 0) {
            throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[]{var5});
         }

         ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[]{var6.size(), var5});
         var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var7);
         Iterator var8 = var6.values().iterator();

         while(var8.hasNext()) {
            Score var9 = (Score)var8.next();
            var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[]{var9.getScorePoints(), var9.getObjective().getDisplayName(), var9.getObjective().getName()}));
         }
      } else {
         Collection var10 = var4.getObjectiveNames();
         var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var10.size());
         if (var10.size() <= 0) {
            throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
         }

         ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[]{var10.size()});
         var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var11);
         var1.addChatMessage(new ChatComponentText(joinNiceString(var10.toArray())));
      }

   }
}
