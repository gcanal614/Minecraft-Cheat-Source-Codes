package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;
import optifine.BlockPosM;
import optifine.Config;
import optifine.DynamicLights;
import optifine.PlayerControllerOF;
import optifine.Reflector;

public class WorldClient extends World {
   private NetHandlerPlayClient sendQueue;
   private ChunkProviderClient clientChunkProvider;
   private final Set previousActiveChunkSet = Sets.newHashSet();
   private static final String __OBFID = "CL_00000882";
   private boolean playerUpdate = false;
   private final Set entityList = Sets.newHashSet();
   private final Minecraft mc = Minecraft.getMinecraft();
   private BlockPosM randomTickPosM = new BlockPosM(0, 0, 0, 3);
   private final Set entitySpawnQueue = Sets.newHashSet();

   protected void func_147456_g() {
      super.func_147456_g();
      this.previousActiveChunkSet.retainAll(this.activeChunkSet);
      if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
         this.previousActiveChunkSet.clear();
      }

      int var1 = 0;
      Iterator var2 = this.activeChunkSet.iterator();

      while(var2.hasNext()) {
         ChunkCoordIntPair var3 = (ChunkCoordIntPair)var2.next();
         if (!this.previousActiveChunkSet.contains(var3)) {
            int var4 = var3.chunkXPos * 16;
            int var5 = var3.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
            this.func_147467_a(var4, var5, var6);
            this.theProfiler.endSection();
            this.previousActiveChunkSet.add(var3);
            ++var1;
            if (var1 >= 10) {
               return;
            }
         }
      }

   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport var1) {
      CrashReportCategory var2 = super.addWorldInfoToCrashReport(var1);
      var2.addCrashSectionCallable("Forced entities", new Callable(this) {
         private static final String __OBFID = "CL_00000883";
         final WorldClient this$0;

         public String call() {
            return String.valueOf((new StringBuilder(String.valueOf(WorldClient.access$0(this.this$0).size()))).append(" total; ").append(WorldClient.access$0(this.this$0).toString()));
         }

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var2.addCrashSectionCallable("Retry entities", new Callable(this) {
         private static final String __OBFID = "CL_00000884";
         final WorldClient this$0;

         {
            this.this$0 = var1;
         }

         public String call() {
            return String.valueOf((new StringBuilder(String.valueOf(WorldClient.access$1(this.this$0).size()))).append(" total; ").append(WorldClient.access$1(this.this$0).toString()));
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var2.addCrashSectionCallable("Server brand", new Callable(this) {
         final WorldClient this$0;
         private static final String __OBFID = "CL_00000885";

         public String call() {
            return WorldClient.access$2(this.this$0).thePlayer.getClientBrand();
         }

         public Object call() throws Exception {
            return this.call();
         }

         {
            this.this$0 = var1;
         }
      });
      var2.addCrashSectionCallable("Server type", new Callable(this) {
         private static final String __OBFID = "CL_00000886";
         final WorldClient this$0;

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return WorldClient.access$2(this.this$0).getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
         }
      });
      return var2;
   }

   public WorldClient(NetHandlerPlayClient var1, WorldSettings var2, int var3, EnumDifficulty var4, Profiler var5) {
      super(new SaveHandlerMP(), new WorldInfo(var2, "MpServer"), WorldProvider.getProviderForDimension(var3), var5, true);
      this.sendQueue = var1;
      this.getWorldInfo().setDifficulty(var4);
      this.provider.registerWorld(this);
      this.setSpawnLocation(new BlockPos(8, 64, 8));
      this.chunkProvider = this.createChunkProvider();
      this.mapStorage = new SaveDataMemoryStorage();
      this.calculateInitialSkylight();
      this.calculateInitialWeather();
      Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, this);
      if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
         this.mc.playerController = new PlayerControllerOF(this.mc, var1);
      }

   }

   public void setWorldScoreboard(Scoreboard var1) {
      this.worldScoreboard = var1;
   }

   static Set access$1(WorldClient var0) {
      return var0.entitySpawnQueue;
   }

   public void doVoidFogParticles(int var1, int var2, int var3) {
      byte var4 = 16;
      Random var5 = new Random();
      ItemStack var6 = this.mc.thePlayer.getHeldItem();
      boolean var7 = this.mc.playerController.func_178889_l() == WorldSettings.GameType.CREATIVE && var6 != null && Block.getBlockFromItem(var6.getItem()) == Blocks.barrier;
      BlockPosM var8 = this.randomTickPosM;

      for(int var9 = 0; var9 < 1000; ++var9) {
         int var10 = var1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var11 = var2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var12 = var3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         var8.setXyz(var10, var11, var12);
         IBlockState var13 = this.getBlockState(var8);
         var13.getBlock().randomDisplayTick(this, var8, var13, var5);
         if (var7 && var13.getBlock() == Blocks.barrier) {
            this.spawnParticle(EnumParticleTypes.BARRIER, (double)((float)var10 + 0.5F), (double)((float)var11 + 0.5F), (double)((float)var12 + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
         }
      }

   }

   protected void updateWeather() {
   }

   protected IChunkProvider createChunkProvider() {
      this.clientChunkProvider = new ChunkProviderClient(this);
      return this.clientChunkProvider;
   }

   public void doPreChunk(int var1, int var2, boolean var3) {
      if (var3) {
         this.clientChunkProvider.loadChunk(var1, var2);
      } else {
         this.clientChunkProvider.unloadChunk(var1, var2);
      }

      if (!var3) {
         this.markBlockRangeForRenderUpdate(var1 * 16, 0, var2 * 16, var1 * 16 + 15, 256, var2 * 16 + 15);
      }

   }

   public Entity removeEntityFromWorld(int var1) {
      Entity var2 = (Entity)this.entitiesById.removeObject(var1);
      if (var2 != null) {
         this.entityList.remove(var2);
         this.removeEntity(var2);
      }

      return var2;
   }

   static Set access$0(WorldClient var0) {
      return var0.entityList;
   }

   public void sendQuittingDisconnectingPacket() {
      this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
   }

   public void removeAllEntities() {
      this.loadedEntityList.removeAll(this.unloadedEntityList);

      int var1;
      Entity var2;
      int var3;
      int var4;
      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         var2 = (Entity)this.unloadedEntityList.get(var1);
         var3 = var2.chunkCoordX;
         var4 = var2.chunkCoordZ;
         if (var2.addedToChunk && this.isChunkLoaded(var3, var4, true)) {
            this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
         }
      }

      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         this.onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
      }

      this.unloadedEntityList.clear();

      for(var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
         var2 = (Entity)this.loadedEntityList.get(var1);
         if (var2.ridingEntity != null) {
            if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
               continue;
            }

            var2.ridingEntity.riddenByEntity = null;
            var2.ridingEntity = null;
         }

         if (var2.isDead) {
            var3 = var2.chunkCoordX;
            var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.isChunkLoaded(var3, var4, true)) {
               this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
            }

            this.loadedEntityList.remove(var1--);
            this.onEntityRemoved(var2);
         }
      }

   }

   static Minecraft access$2(WorldClient var0) {
      return var0.mc;
   }

   public Entity getEntityByID(int var1) {
      return (Entity)(var1 == this.mc.thePlayer.getEntityId() ? this.mc.thePlayer : super.getEntityByID(var1));
   }

   public void setWorldTime(long var1) {
      if (var1 < 0L) {
         var1 = -var1;
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
      } else {
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
      }

      super.setWorldTime(var1);
   }

   public boolean spawnEntityInWorld(Entity var1) {
      boolean var2 = super.spawnEntityInWorld(var1);
      this.entityList.add(var1);
      if (!var2) {
         this.entitySpawnQueue.add(var1);
      } else if (var1 instanceof EntityMinecart) {
         this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)var1));
      }

      return var2;
   }

   public void playSound(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
      double var11 = this.mc.func_175606_aa().getDistanceSq(var1, var3, var5);
      PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(var7), var8, var9, (float)var1, (float)var3, (float)var5);
      if (var10 && var11 > 100.0D) {
         double var14 = Math.sqrt(var11) / 40.0D;
         this.mc.getSoundHandler().playDelayedSound(var13, (int)(var14 * 20.0D));
      } else {
         this.mc.getSoundHandler().playSound(var13);
      }

   }

   protected int getRenderDistanceChunks() {
      return this.mc.gameSettings.renderDistanceChunks;
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = super.getCombinedLight(var1, var2);
      if (Config.isDynamicLights()) {
         var3 = DynamicLights.getCombinedLight(var1, var3);
      }

      return var3;
   }

   public void invalidateBlockReceiveRegion(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public void makeFireworks(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
      this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, var1, var3, var5, var7, var9, var11, this.mc.effectRenderer, var13));
   }

   public void addEntityToWorld(int var1, Entity var2) {
      Entity var3 = this.getEntityByID(var1);
      if (var3 != null) {
         this.removeEntity(var3);
      }

      this.entityList.add(var2);
      var2.setEntityId(var1);
      if (!this.spawnEntityInWorld(var2)) {
         this.entitySpawnQueue.add(var2);
      }

      this.entitiesById.addKey(var1, var2);
   }

   public void removeEntity(Entity var1) {
      super.removeEntity(var1);
      this.entityList.remove(var1);
   }

   public boolean func_180503_b(BlockPos var1, IBlockState var2) {
      int var3 = var1.getX();
      int var4 = var1.getY();
      int var5 = var1.getZ();
      this.invalidateBlockReceiveRegion(var3, var4, var5, var3, var4, var5);
      return super.setBlockState(var1, var2, 3);
   }

   public boolean setBlockState(BlockPos var1, IBlockState var2, int var3) {
      this.playerUpdate = this.isPlayerActing();
      boolean var4 = super.setBlockState(var1, var2, var3);
      this.playerUpdate = false;
      return var4;
   }

   private boolean isPlayerActing() {
      if (this.mc.playerController instanceof PlayerControllerOF) {
         PlayerControllerOF var1 = (PlayerControllerOF)this.mc.playerController;
         return var1.isActing();
      } else {
         return false;
      }
   }

   public void func_175731_a(BlockPos var1, String var2, float var3, float var4, boolean var5) {
      this.playSound((double)var1.getX() + 0.5D, (double)var1.getY() + 0.5D, (double)var1.getZ() + 0.5D, var2, var3, var4, var5);
   }

   protected void onEntityAdded(Entity var1) {
      super.onEntityAdded(var1);
      if (this.entitySpawnQueue.contains(var1)) {
         this.entitySpawnQueue.remove(var1);
      }

   }

   protected void onEntityRemoved(Entity var1) {
      super.onEntityRemoved(var1);
      boolean var2 = false;
      if (this.entityList.contains(var1)) {
         if (var1.isEntityAlive()) {
            this.entitySpawnQueue.add(var1);
            var2 = true;
         } else {
            this.entityList.remove(var1);
         }
      }

   }

   public void tick() {
      super.tick();
      this.func_82738_a(this.getTotalWorldTime() + 1L);
      if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
         this.setWorldTime(this.getWorldTime() + 1L);
      }

      this.theProfiler.startSection("reEntryProcessing");

      for(int var1 = 0; var1 < 10 && !this.entitySpawnQueue.isEmpty(); ++var1) {
         Entity var2 = (Entity)this.entitySpawnQueue.iterator().next();
         this.entitySpawnQueue.remove(var2);
         if (!this.loadedEntityList.contains(var2)) {
            this.spawnEntityInWorld(var2);
         }
      }

      this.theProfiler.endStartSection("chunkCache");
      this.clientChunkProvider.unloadQueuedChunks();
      this.theProfiler.endStartSection("blocks");
      this.func_147456_g();
      this.theProfiler.endSection();
   }
}
