package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {
   private static final String __OBFID = "CL_00001080";

   private double func_110671_a(List var1, World var2, CommandSpreadPlayers.Position[] var3, boolean var4) {
      double var5 = 0.0D;
      int var7 = 0;
      HashMap var8 = Maps.newHashMap();

      for(int var9 = 0; var9 < var1.size(); ++var9) {
         Entity var10 = (Entity)var1.get(var9);
         CommandSpreadPlayers.Position var11;
         if (var4) {
            Team var12 = var10 instanceof EntityPlayer ? ((EntityPlayer)var10).getTeam() : null;
            if (!var8.containsKey(var12)) {
               var8.put(var12, var3[var7++]);
            }

            var11 = (CommandSpreadPlayers.Position)var8.get(var12);
         } else {
            var11 = var3[var7++];
         }

         var10.setPositionAndUpdate((double)((float)MathHelper.floor_double(var11.field_111101_a) + 0.5F), (double)var11.func_111092_a(var2), (double)MathHelper.floor_double(var11.field_111100_b) + 0.5D);
         double var17 = Double.MAX_VALUE;

         for(int var14 = 0; var14 < var3.length; ++var14) {
            if (var11 != var3[var14]) {
               double var15 = var11.func_111099_a(var3[var14]);
               var17 = Math.min(var15, var17);
            }
         }

         var5 += var17;
      }

      var5 /= (double)var1.size();
      return var5;
   }

   private int func_110667_a(List var1) {
      HashSet var2 = Sets.newHashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if (var4 instanceof EntityPlayer) {
            var2.add(((EntityPlayer)var4).getTeam());
         } else {
            var2.add((Object)null);
         }
      }

      return var2.size();
   }

   private CommandSpreadPlayers.Position[] func_110670_a(Random var1, int var2, double var3, double var5, double var7, double var9) {
      CommandSpreadPlayers.Position[] var11 = new CommandSpreadPlayers.Position[var2];

      for(int var12 = 0; var12 < var11.length; ++var12) {
         CommandSpreadPlayers.Position var13 = new CommandSpreadPlayers.Position();
         var13.func_111097_a(var1, var3, var5, var7, var9);
         var11[var12] = var13;
      }

      return var11;
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   private void func_110669_a(ICommandSender var1, List var2, CommandSpreadPlayers.Position var3, double var4, double var6, World var8, boolean var9) throws CommandException {
      Random var10 = new Random();
      double var11 = var3.field_111101_a - var6;
      double var13 = var3.field_111100_b - var6;
      double var15 = var3.field_111101_a + var6;
      double var17 = var3.field_111100_b + var6;
      CommandSpreadPlayers.Position[] var19 = this.func_110670_a(var10, var9 ? this.func_110667_a(var2) : var2.size(), var11, var13, var15, var17);
      int var20 = this.func_110668_a(var3, var4, var8, var10, var11, var13, var15, var17, var19, var9);
      double var21 = this.func_110671_a(var2, var8, var19, var9);
      notifyOperators(var1, this, String.valueOf((new StringBuilder("commands.spreadplayers.success.")).append(var9 ? "teams" : "players")), new Object[]{var19.length, var3.field_111101_a, var3.field_111100_b});
      if (var19.length > 1) {
         var1.addChatMessage(new ChatComponentTranslation(String.valueOf((new StringBuilder("commands.spreadplayers.info.")).append(var9 ? "teams" : "players")), new Object[]{String.format("%.2f", var21), var20}));
      }

   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.spreadplayers.usage";
   }

   public String getCommandName() {
      return "spreadplayers";
   }

   private int func_110668_a(CommandSpreadPlayers.Position var1, double var2, World var4, Random var5, double var6, double var8, double var10, double var12, CommandSpreadPlayers.Position[] var14, boolean var15) throws CommandException {
      boolean var16 = true;
      double var17 = 3.4028234663852886E38D;

      int var19;
      for(var19 = 0; var19 < 10000 && var16; ++var19) {
         var16 = false;
         var17 = 3.4028234663852886E38D;

         int var20;
         CommandSpreadPlayers.Position var21;
         for(int var22 = 0; var22 < var14.length; ++var22) {
            CommandSpreadPlayers.Position var23 = var14[var22];
            var20 = 0;
            var21 = new CommandSpreadPlayers.Position();

            for(int var24 = 0; var24 < var14.length; ++var24) {
               if (var22 != var24) {
                  CommandSpreadPlayers.Position var25 = var14[var24];
                  double var26 = var23.func_111099_a(var25);
                  var17 = Math.min(var26, var17);
                  if (var26 < var2) {
                     ++var20;
                     var21.field_111101_a += var25.field_111101_a - var23.field_111101_a;
                     var21.field_111100_b += var25.field_111100_b - var23.field_111100_b;
                  }
               }
            }

            if (var20 > 0) {
               var21.field_111101_a /= (double)var20;
               var21.field_111100_b /= (double)var20;
               double var30 = (double)var21.func_111096_b();
               if (var30 > 0.0D) {
                  var21.func_111095_a();
                  var23.func_111094_b(var21);
               } else {
                  var23.func_111097_a(var5, var6, var8, var10, var12);
               }

               var16 = true;
            }

            if (var23.func_111093_a(var6, var8, var10, var12)) {
               var16 = true;
            }
         }

         if (!var16) {
            CommandSpreadPlayers.Position[] var28 = var14;
            int var29 = var14.length;

            for(var20 = 0; var20 < var29; ++var20) {
               var21 = var28[var20];
               if (!var21.func_111098_b(var4)) {
                  var21.func_111097_a(var5, var6, var8, var10, var12);
                  var16 = true;
               }
            }
         }
      }

      if (var19 >= 10000) {
         throw new CommandException(String.valueOf((new StringBuilder("commands.spreadplayers.failure.")).append(var15 ? "teams" : "players")), new Object[]{var14.length, var1.field_111101_a, var1.field_111100_b, String.format("%.2f", var17)});
      } else {
         return var19;
      }
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
      } else {
         byte var3 = 0;
         BlockPos var4 = var1.getPosition();
         double var5 = (double)var4.getX();
         int var7 = var3 + 1;
         double var8 = func_175761_b(var5, var2[var3], true);
         double var10 = func_175761_b((double)var4.getZ(), var2[var7++], true);
         double var12 = parseDouble(var2[var7++], 0.0D);
         double var14 = parseDouble(var2[var7++], var12 + 1.0D);
         boolean var16 = parseBoolean(var2[var7++]);
         ArrayList var17 = Lists.newArrayList();

         while(var7 < var2.length) {
            String var18 = var2[var7++];
            if (PlayerSelector.hasArguments(var18)) {
               List var19 = PlayerSelector.func_179656_b(var1, var18, Entity.class);
               if (var19.size() == 0) {
                  throw new EntityNotFoundException();
               }

               var17.addAll(var19);
            } else {
               EntityPlayerMP var20 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(var18);
               if (var20 == null) {
                  throw new PlayerNotFoundException();
               }

               var17.add(var20);
            }
         }

         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var17.size());
         if (var17.isEmpty()) {
            throw new EntityNotFoundException();
         } else {
            var1.addChatMessage(new ChatComponentTranslation(String.valueOf((new StringBuilder("commands.spreadplayers.spreading.")).append(var16 ? "teams" : "players")), new Object[]{var17.size(), var14, var8, var10, var12}));
            this.func_110669_a(var1, var17, new CommandSpreadPlayers.Position(var8, var10), var12, var14, ((Entity)var17.get(0)).worldObj, var16);
         }
      }
   }

   static class Position {
      double field_111100_b;
      double field_111101_a;
      private static final String __OBFID = "CL_00001105";

      void func_111095_a() {
         double var1 = (double)this.func_111096_b();
         this.field_111101_a /= var1;
         this.field_111100_b /= var1;
      }

      public boolean func_111098_b(World var1) {
         BlockPos var2 = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);

         while(var2.getY() > 0) {
            var2 = var2.offsetDown();
            Material var3 = var1.getBlockState(var2).getBlock().getMaterial();
            if (var3 != Material.air) {
               if (!var3.isLiquid() && var3 != Material.fire) {
                  return true;
               }

               return false;
            }
         }

         return false;
      }

      public void func_111094_b(CommandSpreadPlayers.Position var1) {
         this.field_111101_a -= var1.field_111101_a;
         this.field_111100_b -= var1.field_111100_b;
      }

      public int func_111092_a(World var1) {
         BlockPos var2 = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);

         while(var2.getY() > 0) {
            var2 = var2.offsetDown();
            if (var1.getBlockState(var2).getBlock().getMaterial() != Material.air) {
               return var2.getY() + 1;
            }
         }

         return 257;
      }

      public boolean func_111093_a(double var1, double var3, double var5, double var7) {
         boolean var9 = false;
         if (this.field_111101_a < var1) {
            this.field_111101_a = var1;
            var9 = true;
         } else if (this.field_111101_a > var5) {
            this.field_111101_a = var5;
            var9 = true;
         }

         if (this.field_111100_b < var3) {
            this.field_111100_b = var3;
            var9 = true;
         } else if (this.field_111100_b > var7) {
            this.field_111100_b = var7;
            var9 = true;
         }

         return var9;
      }

      public void func_111097_a(Random var1, double var2, double var4, double var6, double var8) {
         this.field_111101_a = MathHelper.getRandomDoubleInRange(var1, var2, var6);
         this.field_111100_b = MathHelper.getRandomDoubleInRange(var1, var4, var8);
      }

      Position(double var1, double var3) {
         this.field_111101_a = var1;
         this.field_111100_b = var3;
      }

      Position() {
      }

      float func_111096_b() {
         return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
      }

      double func_111099_a(CommandSpreadPlayers.Position var1) {
         double var2 = this.field_111101_a - var1.field_111101_a;
         double var4 = this.field_111100_b - var1.field_111100_b;
         return Math.sqrt(var2 * var2 + var4 * var4);
      }
   }
}
