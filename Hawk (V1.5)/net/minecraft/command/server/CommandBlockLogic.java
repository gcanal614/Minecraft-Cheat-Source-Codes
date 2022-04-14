package net.minecraft.command.server;

import io.netty.buffer.ByteBuf;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public abstract class CommandBlockLogic implements ICommandSender {
   private final CommandResultStats field_175575_g = new CommandResultStats();
   private int successCount;
   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
   private static final String __OBFID = "CL_00000128";
   private IChatComponent lastOutput = null;
   private String commandStored = "";
   private String customName = "@";
   private boolean trackOutput = true;

   public void func_145750_b(IChatComponent var1) {
      this.lastOutput = var1;
   }

   public IChatComponent getLastOutput() {
      return this.lastOutput;
   }

   public CommandResultStats func_175572_n() {
      return this.field_175575_g;
   }

   public void readDataFromNBT(NBTTagCompound var1) {
      this.commandStored = var1.getString("Command");
      this.successCount = var1.getInteger("SuccessCount");
      if (var1.hasKey("CustomName", 8)) {
         this.customName = var1.getString("CustomName");
      }

      if (var1.hasKey("TrackOutput", 1)) {
         this.trackOutput = var1.getBoolean("TrackOutput");
      }

      if (var1.hasKey("LastOutput", 8) && this.trackOutput) {
         this.lastOutput = IChatComponent.Serializer.jsonToComponent(var1.getString("LastOutput"));
      }

      this.field_175575_g.func_179668_a(var1);
   }

   public void writeDataToNBT(NBTTagCompound var1) {
      var1.setString("Command", this.commandStored);
      var1.setInteger("SuccessCount", this.successCount);
      var1.setString("CustomName", this.customName);
      var1.setBoolean("TrackOutput", this.trackOutput);
      if (this.lastOutput != null && this.trackOutput) {
         var1.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
      }

      this.field_175575_g.func_179670_b(var1);
   }

   public String getCustomName() {
      return this.commandStored;
   }

   public void func_145754_b(String var1) {
      this.customName = var1;
   }

   public void trigger(World var1) {
      if (var1.isRemote) {
         this.successCount = 0;
      }

      MinecraftServer var2 = MinecraftServer.getServer();
      if (var2 != null && var2.func_175578_N() && var2.isCommandBlockEnabled()) {
         ICommandManager var3 = var2.getCommandManager();

         try {
            this.lastOutput = null;
            this.successCount = var3.executeCommand(this, this.commandStored);
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.makeCrashReport(var7, "Executing command block");
            CrashReportCategory var6 = var5.makeCategory("Command to be executed");
            var6.addCrashSectionCallable("Command", new Callable(this) {
               private static final String __OBFID = "CL_00002154";
               final CommandBlockLogic this$0;

               {
                  this.this$0 = var1;
               }

               public String func_180324_a() {
                  return this.this$0.getCustomName();
               }

               public Object call() {
                  return this.func_180324_a();
               }
            });
            var6.addCrashSectionCallable("Name", new Callable(this) {
               private static final String __OBFID = "CL_00002153";
               final CommandBlockLogic this$0;

               public String func_180326_a() {
                  return this.this$0.getName();
               }

               public Object call() {
                  return this.func_180326_a();
               }

               {
                  this.this$0 = var1;
               }
            });
            throw new ReportedException(var5);
         }
      } else {
         this.successCount = 0;
      }

   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return var1 <= 2;
   }

   public boolean sendCommandFeedback() {
      MinecraftServer var1 = MinecraftServer.getServer();
      return var1 == null || !var1.func_175578_N() || var1.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
   }

   public int getSuccessCount() {
      return this.successCount;
   }

   public boolean func_175574_a(EntityPlayer var1) {
      if (!var1.capabilities.isCreativeMode) {
         return false;
      } else {
         if (var1.getEntityWorld().isRemote) {
            var1.func_146095_a(this);
         }

         return true;
      }
   }

   public boolean func_175571_m() {
      return this.trackOutput;
   }

   public String getName() {
      return this.customName;
   }

   public abstract int func_145751_f();

   public void addChatMessage(IChatComponent var1) {
      if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
         this.lastOutput = (new ChatComponentText(String.valueOf((new StringBuilder("[")).append(timestampFormat.format(new Date())).append("] ")))).appendSibling(var1);
         this.func_145756_e();
      }

   }

   public void func_174794_a(CommandResultStats.Type var1, int var2) {
      this.field_175575_g.func_179672_a(this, var1, var2);
   }

   public void func_175573_a(boolean var1) {
      this.trackOutput = var1;
   }

   public abstract void func_145756_e();

   public abstract void func_145757_a(ByteBuf var1);

   public void setCommand(String var1) {
      this.commandStored = var1;
      this.successCount = 0;
   }

   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }
}
