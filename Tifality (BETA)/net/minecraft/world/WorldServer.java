/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer
extends World
implements IThreadListener {
    private static final Logger logger = LogManager.getLogger();
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private final Set<NextTickListEntry> pendingTickListEntriesHashSet = Sets.newHashSet();
    private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet();
    private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
    public ChunkProviderServer theChunkProviderServer;
    public boolean disableLevelSaving;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final SpawnerAnimals mobSpawner = new SpawnerAnimals();
    protected final VillageSiege villageSiege = new VillageSiege(this);
    private ServerBlockEventList[] field_147490_S = new ServerBlockEventList[]{new ServerBlockEventList(), new ServerBlockEventList()};
    private int blockEventCacheIndex;
    private static final List<WeightedRandomChestContent> bonusChestContent = Lists.newArrayList(new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10));
    private List<NextTickListEntry> pendingTickListEntriesThisTick = Lists.newArrayList();

    public WorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId) {
        super(saveHandlerIn, info, WorldProvider.getProviderForDimension(dimensionId), false);
        this.mcServer = server;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.worldTeleporter = new Teleporter(this);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(server.getMaxWorldSize());
    }

    @Override
    public World init() {
        this.mapStorage = new MapStorage(this.saveHandler);
        String s = VillageCollection.fileNameForProvider(this.provider);
        VillageCollection villagecollection = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, s);
        if (villagecollection == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(s, this.villageCollectionObj);
        } else {
            this.villageCollectionObj = villagecollection;
            this.villageCollectionObj.setWorldsForAll(this);
        }
        this.worldScoreboard = new ServerScoreboard(this.mcServer);
        ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (scoreboardsavedata == null) {
            scoreboardsavedata = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", scoreboardsavedata);
        }
        scoreboardsavedata.setScoreboard(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardsavedata);
        this.getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
        this.getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
        this.getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
        this.getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
        this.getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
        if (this.worldInfo.getBorderLerpTime() > 0L) {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
        } else {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize());
        }
        return this;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
        }
        this.provider.getWorldChunkManager().cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                long i = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(i - i % 24000L);
            }
            this.wakeAllPlayers();
        }
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.mobSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
        }
        this.chunkProvider.unloadQueuedChunks();
        int j = this.calculateSkylightSubtracted(1.0f);
        if (j != this.getSkylightSubtracted()) {
            this.setSkylightSubtracted(j);
        }
        this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        }
        this.tickUpdates(false);
        this.updateBlocks();
        this.thePlayerManager.updatePlayerInstances();
        this.villageCollectionObj.tick();
        this.villageSiege.tick();
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.sendQueuedBlockEvents();
    }

    public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(EnumCreatureType creatureType, BlockPos pos) {
        List<BiomeGenBase.SpawnListEntry> list = this.getChunkProvider().getPossibleCreatures(creatureType, pos);
        return list != null && !list.isEmpty() ? WeightedRandom.getRandomItem(this.rand, list) : null;
    }

    public boolean canCreatureTypeSpawnHere(EnumCreatureType creatureType, BiomeGenBase.SpawnListEntry spawnListEntry, BlockPos pos) {
        List<BiomeGenBase.SpawnListEntry> list = this.getChunkProvider().getPossibleCreatures(creatureType, pos);
        return list != null && !list.isEmpty() ? list.contains(spawnListEntry) : false;
    }

    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = false;
        if (!this.playerEntities.isEmpty()) {
            int i = 0;
            int j = 0;
            for (EntityPlayer entityplayer : this.playerEntities) {
                if (entityplayer.isSpectator()) {
                    ++i;
                    continue;
                }
                if (!entityplayer.isPlayerSleeping()) continue;
                ++j;
            }
            this.allPlayersSleeping = j > 0 && j >= this.playerEntities.size() - i;
        }
    }

    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (EntityPlayer entityplayer : this.playerEntities) {
            if (!entityplayer.isPlayerSleeping()) continue;
            entityplayer.wakeUpPlayer(false, false, true);
        }
        this.resetRainAndThunder();
    }

    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }

    public boolean areAllPlayersAsleep() {
        if (this.allPlayersSleeping && !this.isRemote) {
            for (EntityPlayer entityplayer : this.playerEntities) {
                if (!entityplayer.isSpectator() && entityplayer.isPlayerFullyAsleep()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void setInitialSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(this.func_181545_F() + 1);
        }
        int i = this.worldInfo.getSpawnX();
        int j = this.worldInfo.getSpawnZ();
        int k = 0;
        while (this.getGroundAboveSeaLevel(new BlockPos(i, 0, j)).getMaterial() == Material.air) {
            i += this.rand.nextInt(8) - this.rand.nextInt(8);
            j += this.rand.nextInt(8) - this.rand.nextInt(8);
            if (++k != 10000) continue;
        }
        this.worldInfo.setSpawnX(i);
        this.worldInfo.setSpawnZ(j);
    }

    @Override
    protected void updateBlocks() {
        super.updateBlocks();
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            for (ChunkCoordIntPair chunkcoordintpair1 : this.activeChunkSet) {
                this.getChunkFromChunkCoords(chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos).func_150804_b(false);
            }
        } else {
            int i = 0;
            int j = 0;
            for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
                int l2;
                int k = chunkcoordintpair.chunkXPos * 16;
                int l = chunkcoordintpair.chunkZPos * 16;
                Chunk chunk = this.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
                this.playMoodSoundAndCheckLight(k, l, chunk);
                chunk.func_150804_b(false);
                if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    int i1 = this.updateLCG >> 2;
                    BlockPos blockpos = this.adjustPosToNearbyEntity(new BlockPos(k + (i1 & 0xF), 0, l + (i1 >> 8 & 0xF)));
                    if (this.canLightningStrike(blockpos)) {
                        this.addWeatherEffect(new EntityLightningBolt(this, blockpos.getX(), blockpos.getY(), blockpos.getZ()));
                    }
                }
                if (this.rand.nextInt(16) == 0) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    int k2 = this.updateLCG >> 2;
                    BlockPos blockpos2 = this.getPrecipitationHeight(new BlockPos(k + (k2 & 0xF), 0, l + (k2 >> 8 & 0xF)));
                    BlockPos blockpos1 = blockpos2.down();
                    if (this.canBlockFreezeNoWater(blockpos1)) {
                        this.setBlockState(blockpos1, Blocks.ice.getDefaultState());
                    }
                    if (this.isRaining() && this.canSnowAt(blockpos2, true)) {
                        this.setBlockState(blockpos2, Blocks.snow_layer.getDefaultState());
                    }
                    if (this.isRaining() && this.getBiomeGenForCoords(blockpos1).canSpawnLightningBolt()) {
                        this.getBlockState(blockpos1).getBlock().fillWithRain(this, blockpos1);
                    }
                }
                if ((l2 = this.getGameRules().getInt("randomTickSpeed")) <= 0) continue;
                for (ExtendedBlockStorage extendedblockstorage : chunk.getBlockStorageArray()) {
                    if (extendedblockstorage == null || !extendedblockstorage.getNeedsRandomTick()) continue;
                    for (int j1 = 0; j1 < l2; ++j1) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        int k1 = this.updateLCG >> 2;
                        int l1 = k1 & 0xF;
                        int i2 = k1 >> 8 & 0xF;
                        int j2 = k1 >> 16 & 0xF;
                        ++j;
                        IBlockState iblockstate = extendedblockstorage.get(l1, j2, i2);
                        Block block = iblockstate.getBlock();
                        if (!block.getTickRandomly()) continue;
                        ++i;
                        block.randomTick(this, new BlockPos(l1 + k, j2 + extendedblockstorage.getYLocation(), i2 + l), iblockstate, this.rand);
                    }
                }
            }
        }
    }

    protected BlockPos adjustPosToNearbyEntity(BlockPos pos) {
        BlockPos blockpos = this.getPrecipitationHeight(pos);
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), this.getHeight(), blockpos.getZ())).expand(3.0, 3.0, 3.0);
        List<EntityLivingBase> list = this.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new Predicate<EntityLivingBase>(){

            @Override
            public boolean apply(EntityLivingBase p_apply_1_) {
                return p_apply_1_ != null && p_apply_1_.isEntityAlive() && WorldServer.this.canSeeSky(p_apply_1_.getPosition());
            }
        });
        return !list.isEmpty() ? list.get(this.rand.nextInt(list.size())).getPosition() : blockpos;
    }

    @Override
    public boolean isBlockTickPending(BlockPos pos, Block blockType) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockType);
        return this.pendingTickListEntriesThisTick.contains(nextticklistentry);
    }

    @Override
    public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {
        this.updateBlockTick(pos, blockIn, delay, 0);
    }

    @Override
    public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
        int i = 0;
        if (this.scheduledUpdatesAreImmediate && blockIn.getMaterial() != Material.air) {
            if (blockIn.requiresUpdates()) {
                IBlockState iblockstate;
                i = 8;
                if (this.isAreaLoaded(nextticklistentry.position.add(-i, -i, -i), nextticklistentry.position.add(i, i, i)) && (iblockstate = this.getBlockState(nextticklistentry.position)).getBlock().getMaterial() != Material.air && iblockstate.getBlock() == nextticklistentry.getBlock()) {
                    iblockstate.getBlock().updateTick(this, nextticklistentry.position, iblockstate, this.rand);
                }
                return;
            }
            delay = 1;
        }
        if (this.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
            if (blockIn.getMaterial() != Material.air) {
                nextticklistentry.setScheduledTime((long)delay + this.worldInfo.getWorldTotalTime());
                nextticklistentry.setPriority(priority);
            }
            if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
                this.pendingTickListEntriesHashSet.add(nextticklistentry);
                this.pendingTickListEntriesTreeSet.add(nextticklistentry);
            }
        }
    }

    @Override
    public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
        nextticklistentry.setPriority(priority);
        if (blockIn.getMaterial() != Material.air) {
            nextticklistentry.setScheduledTime((long)delay + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
            this.pendingTickListEntriesHashSet.add(nextticklistentry);
            this.pendingTickListEntriesTreeSet.add(nextticklistentry);
        }
    }

    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        } else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }

    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }

    @Override
    public boolean tickUpdates(boolean p_72955_1_) {
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }
        int i = this.pendingTickListEntriesTreeSet.size();
        if (i != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (i > 1000) {
            i = 1000;
        }
        for (int j = 0; j < i; ++j) {
            NextTickListEntry nextticklistentry = this.pendingTickListEntriesTreeSet.first();
            if (!p_72955_1_ && nextticklistentry.scheduledTime > this.worldInfo.getWorldTotalTime()) break;
            this.pendingTickListEntriesTreeSet.remove(nextticklistentry);
            this.pendingTickListEntriesHashSet.remove(nextticklistentry);
            this.pendingTickListEntriesThisTick.add(nextticklistentry);
        }
        Iterator<NextTickListEntry> iterator2 = this.pendingTickListEntriesThisTick.iterator();
        while (iterator2.hasNext()) {
            NextTickListEntry nextticklistentry1 = iterator2.next();
            iterator2.remove();
            int k = 0;
            if (this.isAreaLoaded(nextticklistentry1.position.add(-k, -k, -k), nextticklistentry1.position.add(k, k, k))) {
                IBlockState iblockstate = this.getBlockState(nextticklistentry1.position);
                if (iblockstate.getBlock().getMaterial() == Material.air || !Block.isEqualTo(iblockstate.getBlock(), nextticklistentry1.getBlock())) continue;
                try {
                    iblockstate.getBlock().updateTick(this, nextticklistentry1.position, iblockstate, this.rand);
                    continue;
                }
                catch (Throwable throwable) {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while ticking a block");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being ticked");
                    CrashReportCategory.addBlockInfo(crashreportcategory, nextticklistentry1.position, iblockstate);
                    throw new ReportedException(crashreport);
                }
            }
            this.scheduleUpdate(nextticklistentry1.position, nextticklistentry1.getBlock(), 0);
        }
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }

    @Override
    public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
        ChunkCoordIntPair chunkcoordintpair = chunkIn.getChunkCoordIntPair();
        int i = (chunkcoordintpair.chunkXPos << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkcoordintpair.chunkZPos << 4) - 2;
        int l = k + 16 + 2;
        return this.func_175712_a(new StructureBoundingBox(i, 0, k, j, 256, l), p_72920_2_);
    }

    @Override
    public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
        ArrayList<NextTickListEntry> list = null;
        for (int i = 0; i < 2; ++i) {
            Iterator<NextTickListEntry> iterator2 = i == 0 ? this.pendingTickListEntriesTreeSet.iterator() : this.pendingTickListEntriesThisTick.iterator();
            while (iterator2.hasNext()) {
                NextTickListEntry nextticklistentry = iterator2.next();
                BlockPos blockpos = nextticklistentry.position;
                if (blockpos.getX() < structureBB.minX || blockpos.getX() >= structureBB.maxX || blockpos.getZ() < structureBB.minZ || blockpos.getZ() >= structureBB.maxZ) continue;
                if (p_175712_2_) {
                    this.pendingTickListEntriesHashSet.remove(nextticklistentry);
                    iterator2.remove();
                }
                if (list == null) {
                    list = Lists.newArrayList();
                }
                list.add(nextticklistentry);
            }
        }
        return list;
    }

    @Override
    public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
        if (!this.canSpawnAnimals() && (entityIn instanceof EntityAnimal || entityIn instanceof EntityWaterMob)) {
            entityIn.setDead();
        }
        if (!this.canSpawnNPCs() && entityIn instanceof INpc) {
            entityIn.setDead();
        }
        super.updateEntityWithOptionalForce(entityIn, forceUpdate);
    }

    private boolean canSpawnNPCs() {
        return this.mcServer.getCanSpawnNPCs();
    }

    private boolean canSpawnAnimals() {
        return this.mcServer.getCanSpawnAnimals();
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        IChunkLoader ichunkloader = this.saveHandler.getChunkLoader(this.provider);
        this.theChunkProviderServer = new ChunkProviderServer(this, ichunkloader, this.provider.createChunkGenerator());
        return this.theChunkProviderServer;
    }

    public List<TileEntity> getTileEntitiesIn(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        ArrayList<TileEntity> list = Lists.newArrayList();
        for (int i = 0; i < this.loadedTileEntityList.size(); ++i) {
            TileEntity tileentity = (TileEntity)this.loadedTileEntityList.get(i);
            BlockPos blockpos = tileentity.getPos();
            if (blockpos.getX() < minX || blockpos.getY() < minY || blockpos.getZ() < minZ || blockpos.getX() >= maxX || blockpos.getY() >= maxY || blockpos.getZ() >= maxZ) continue;
            list.add(tileentity);
        }
        return list;
    }

    @Override
    public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
        return !this.mcServer.isBlockProtected(this, pos, player) && this.getWorldBorder().contains(pos);
    }

    @Override
    public void initialize(WorldSettings settings2) {
        if (!this.worldInfo.isInitialized()) {
            try {
                this.createSpawnPosition(settings2);
                if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
                    this.setDebugWorldSettings();
                }
                super.initialize(settings2);
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception initializing level");
                try {
                    this.addWorldInfoToCrashReport(crashreport);
                }
                catch (Throwable throwable2) {
                    // empty catch block
                }
                throw new ReportedException(crashreport);
            }
            this.worldInfo.setServerInitialized(true);
        }
    }

    private void setDebugWorldSettings() {
        this.worldInfo.setMapFeaturesEnabled(false);
        this.worldInfo.setAllowCommands(true);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThundering(false);
        this.worldInfo.setCleanWeatherTime(1000000000);
        this.worldInfo.setWorldTime(6000L);
        this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
        this.worldInfo.setHardcore(false);
        this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldInfo.setDifficultyLocked(true);
        this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
    }

    private void createSpawnPosition(WorldSettings p_73052_1_) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
        } else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
        } else {
            this.findingSpawnPoint = true;
            WorldChunkManager worldchunkmanager = this.provider.getWorldChunkManager();
            List<BiomeGenBase> list = worldchunkmanager.getBiomesToSpawnIn();
            Random random = new Random(this.getSeed());
            BlockPos blockpos = worldchunkmanager.findBiomePosition(0, 0, 256, list, random);
            int i = 0;
            int j = this.provider.getAverageGroundLevel();
            int k = 0;
            if (blockpos != null) {
                i = blockpos.getX();
                k = blockpos.getZ();
            } else {
                logger.warn("Unable to find spawn biome");
            }
            int l = 0;
            while (!this.provider.canCoordinateBeSpawn(i, k)) {
                i += random.nextInt(64) - random.nextInt(64);
                k += random.nextInt(64) - random.nextInt(64);
                if (++l != 1000) continue;
            }
            this.worldInfo.setSpawn(new BlockPos(i, j, k));
            this.findingSpawnPoint = false;
            if (p_73052_1_.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }

    protected void createBonusChest() {
        int k;
        int j;
        BlockPos blockpos;
        WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest(bonusChestContent, 10);
        for (int i = 0; i < 10 && !worldgeneratorbonuschest.generate(this, this.rand, blockpos = this.getTopSolidOrLiquidBlock(new BlockPos(j = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6), 0, k = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6))).up()); ++i) {
        }
    }

    public BlockPos getSpawnCoordinate() {
        return this.provider.getSpawnCoordinate();
    }

    public void saveAllChunks(boolean p_73044_1_, IProgressUpdate progressCallback) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (progressCallback != null) {
                progressCallback.displaySavingString("Saving level");
            }
            this.saveLevel();
            if (progressCallback != null) {
                progressCallback.displayLoadingString("Saving chunks");
            }
            this.chunkProvider.saveChunks(p_73044_1_, progressCallback);
            for (Chunk chunk : Lists.newArrayList(this.theChunkProviderServer.func_152380_a())) {
                if (chunk == null || this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition)) continue;
                this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
            }
        }
    }

    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }

    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.worldInfo.setBorderSize(this.getWorldBorder().getDiameter());
        this.worldInfo.getBorderCenterX(this.getWorldBorder().getCenterX());
        this.worldInfo.getBorderCenterZ(this.getWorldBorder().getCenterZ());
        this.worldInfo.setBorderSafeZone(this.getWorldBorder().getDamageBuffer());
        this.worldInfo.setBorderDamagePerBlock(this.getWorldBorder().getDamageAmount());
        this.worldInfo.setBorderWarningDistance(this.getWorldBorder().getWarningDistance());
        this.worldInfo.setBorderWarningTime(this.getWorldBorder().getWarningTime());
        this.worldInfo.setBorderLerpTarget(this.getWorldBorder().getTargetSize());
        this.worldInfo.setBorderLerpTime(this.getWorldBorder().getTimeUntilTarget());
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }

    @Override
    protected void onEntityAdded(Entity entityIn) {
        super.onEntityAdded(entityIn);
        this.entitiesById.addKey(entityIn.getEntityId(), entityIn);
        this.entitiesByUuid.put(entityIn.getUniqueID(), entityIn);
        Entity[] aentity = entityIn.getParts();
        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.addKey(aentity[i].getEntityId(), aentity[i]);
            }
        }
    }

    @Override
    protected void onEntityRemoved(Entity entityIn) {
        super.onEntityRemoved(entityIn);
        this.entitiesById.removeObject(entityIn.getEntityId());
        this.entitiesByUuid.remove(entityIn.getUniqueID());
        Entity[] aentity = entityIn.getParts();
        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.removeObject(aentity[i].getEntityId());
            }
        }
    }

    @Override
    public boolean addWeatherEffect(Entity entityIn) {
        if (super.addWeatherEffect(entityIn)) {
            this.mcServer.getConfigurationManager().sendToAllNear(entityIn.posX, entityIn.posY, entityIn.posZ, 512.0, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(entityIn));
            return true;
        }
        return false;
    }

    @Override
    public void setEntityState(Entity entityIn, byte state) {
        this.getEntityTracker().func_151248_b(entityIn, new S19PacketEntityStatus(entityIn, state));
    }

    @Override
    public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
        Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
        explosion.doExplosionA();
        explosion.doExplosionB(false);
        if (!isSmoking) {
            explosion.func_180342_d();
        }
        for (EntityPlayer entityplayer : this.playerEntities) {
            if (!(entityplayer.getDistanceSq(x, y, z) < 4096.0)) continue;
            ((EntityPlayerMP)entityplayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(x, y, z, strength, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityplayer)));
        }
        return explosion;
    }

    @Override
    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
        BlockEventData blockeventdata = new BlockEventData(pos, blockIn, eventID, eventParam);
        for (BlockEventData blockeventdata1 : this.field_147490_S[this.blockEventCacheIndex]) {
            if (!blockeventdata1.equals(blockeventdata)) continue;
            return;
        }
        this.field_147490_S[this.blockEventCacheIndex].add(blockeventdata);
    }

    private void sendQueuedBlockEvents() {
        while (!this.field_147490_S[this.blockEventCacheIndex].isEmpty()) {
            int i = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 1;
            for (BlockEventData blockeventdata : this.field_147490_S[i]) {
                if (!this.fireBlockEvent(blockeventdata)) continue;
                this.mcServer.getConfigurationManager().sendToAllNear(blockeventdata.getPosition().getX(), blockeventdata.getPosition().getY(), blockeventdata.getPosition().getZ(), 64.0, this.provider.getDimensionId(), new S24PacketBlockAction(blockeventdata.getPosition(), blockeventdata.getBlock(), blockeventdata.getEventID(), blockeventdata.getEventParameter()));
            }
            this.field_147490_S[i].clear();
        }
    }

    private boolean fireBlockEvent(BlockEventData event) {
        IBlockState iblockstate = this.getBlockState(event.getPosition());
        return iblockstate.getBlock() == event.getBlock() ? iblockstate.getBlock().onBlockEventReceived(this, event.getPosition(), iblockstate, event.getEventID(), event.getEventParameter()) : false;
    }

    public void flush() {
        this.saveHandler.flush();
    }

    @Override
    protected void updateWeather() {
        boolean flag = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
        }
        if (flag != this.isRaining()) {
            if (flag) {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0f));
            } else {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }

    @Override
    protected int getRenderDistanceChunks() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }

    public MinecraftServer getMinecraftServer() {
        return this.mcServer;
    }

    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }

    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }

    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }

    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, int numberOfParticles, double p_175739_9_, double p_175739_11_, double p_175739_13_, double p_175739_15_, int ... p_175739_17_) {
        this.spawnParticle(particleType, false, xCoord, yCoord, zCoord, numberOfParticles, p_175739_9_, p_175739_11_, p_175739_13_, p_175739_15_, p_175739_17_);
    }

    public void spawnParticle(EnumParticleTypes particleType, boolean longDistance, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int ... p_180505_18_) {
        S2APacketParticles packet = new S2APacketParticles(particleType, longDistance, (float)xCoord, (float)yCoord, (float)zCoord, (float)xOffset, (float)yOffset, (float)zOffset, (float)particleSpeed, numberOfParticles, p_180505_18_);
        for (int i = 0; i < this.playerEntities.size(); ++i) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)this.playerEntities.get(i);
            BlockPos blockpos = entityplayermp.getPosition();
            double d0 = blockpos.distanceSq(xCoord, yCoord, zCoord);
            if (!(d0 <= 256.0) && (!longDistance || !(d0 <= 65536.0))) continue;
            entityplayermp.playerNetServerHandler.sendPacket(packet);
        }
    }

    public Entity getEntityFromUuid(UUID uuid) {
        return this.entitiesByUuid.get(uuid);
    }

    @Override
    public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
        return this.mcServer.addScheduledTask(runnableToSchedule);
    }

    @Override
    public boolean isCallingFromMinecraftThread() {
        return this.mcServer.isCallingFromMinecraftThread();
    }

    static class ServerBlockEventList
    extends ArrayList<BlockEventData> {
        private ServerBlockEventList() {
        }
    }
}

