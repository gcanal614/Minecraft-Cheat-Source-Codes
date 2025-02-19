/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
    private static final Logger pmLogger = LogManager.getLogger();
    private final WorldServer theWorldServer;
    private final List<EntityPlayerMP> players = Lists.newArrayList();
    private final LongHashMap playerInstances = new LongHashMap();
    private final List<PlayerInstance> playerInstancesToUpdate = Lists.newArrayList();
    private final List<PlayerInstance> playerInstanceList = Lists.newArrayList();
    private int playerViewRadius;
    private long previousTotalWorldTime;
    private final int[][] xzDirectionsConst;

    public PlayerManager(WorldServer serverWorld) {
        int[][] nArrayArray = new int[4][];
        int[] nArray = new int[2];
        nArray[0] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[2];
        nArray3[0] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[2];
        nArray4[1] = -1;
        nArrayArray[3] = nArray4;
        this.xzDirectionsConst = nArrayArray;
        this.theWorldServer = serverWorld;
        this.setPlayerViewRadius(serverWorld.getMinecraftServer().getConfigurationManager().getViewDistance());
    }

    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }

    public void updatePlayerInstances() {
        WorldProvider worldprovider;
        long i = this.theWorldServer.getTotalWorldTime();
        if (i - this.previousTotalWorldTime > 8000L) {
            this.previousTotalWorldTime = i;
            int j = 0;
            while (j < this.playerInstanceList.size()) {
                PlayerInstance playermanager$playerinstance = this.playerInstanceList.get(j);
                playermanager$playerinstance.onUpdate();
                playermanager$playerinstance.processChunk();
                ++j;
            }
        } else {
            int k = 0;
            while (k < this.playerInstancesToUpdate.size()) {
                PlayerInstance playermanager$playerinstance1 = this.playerInstancesToUpdate.get(k);
                playermanager$playerinstance1.onUpdate();
                ++k;
            }
        }
        this.playerInstancesToUpdate.clear();
        if (this.players.isEmpty() && !(worldprovider = this.theWorldServer.provider).canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
        }
    }

    public boolean hasPlayerInstance(int chunkX, int chunkZ) {
        long i = (long)chunkX + Integer.MAX_VALUE | (long)chunkZ + Integer.MAX_VALUE << 32;
        return this.playerInstances.getValueByKey(i) != null;
    }

    private PlayerInstance getPlayerInstance(int chunkX, int chunkZ, boolean createIfAbsent) {
        long i = (long)chunkX + Integer.MAX_VALUE | (long)chunkZ + Integer.MAX_VALUE << 32;
        PlayerInstance playermanager$playerinstance = (PlayerInstance)this.playerInstances.getValueByKey(i);
        if (playermanager$playerinstance == null && createIfAbsent) {
            playermanager$playerinstance = new PlayerInstance(chunkX, chunkZ);
            this.playerInstances.add(i, playermanager$playerinstance);
            this.playerInstanceList.add(playermanager$playerinstance);
        }
        return playermanager$playerinstance;
    }

    public void markBlockForUpdate(BlockPos pos) {
        int j;
        int i = pos.getX() >> 4;
        PlayerInstance playermanager$playerinstance = this.getPlayerInstance(i, j = pos.getZ() >> 4, false);
        if (playermanager$playerinstance != null) {
            playermanager$playerinstance.flagChunkForUpdate(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
        }
    }

    public void addPlayer(EntityPlayerMP player) {
        int i = (int)player.posX >> 4;
        int j = (int)player.posZ >> 4;
        player.managedPosX = player.posX;
        player.managedPosZ = player.posZ;
        int k = i - this.playerViewRadius;
        while (k <= i + this.playerViewRadius) {
            int l = j - this.playerViewRadius;
            while (l <= j + this.playerViewRadius) {
                this.getPlayerInstance(k, l, true).addPlayer(player);
                ++l;
            }
            ++k;
        }
        this.players.add(player);
        this.filterChunkLoadQueue(player);
    }

    public void filterChunkLoadQueue(EntityPlayerMP player) {
        ArrayList list = Lists.newArrayList(player.loadedChunks);
        int i = 0;
        int j = this.playerViewRadius;
        int k = (int)player.posX >> 4;
        int l = (int)player.posZ >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkcoordintpair = this.getPlayerInstance(k, l, true).chunkCoords;
        player.loadedChunks.clear();
        if (list.contains(chunkcoordintpair)) {
            player.loadedChunks.add(chunkcoordintpair);
        }
        int k1 = 1;
        while (k1 <= j * 2) {
            int l1 = 0;
            while (l1 < 2) {
                int[] aint = this.xzDirectionsConst[i++ % 4];
                int i2 = 0;
                while (i2 < k1) {
                    chunkcoordintpair = this.getPlayerInstance(k + (i1 += aint[0]), l + (j1 += aint[1]), true).chunkCoords;
                    if (list.contains(chunkcoordintpair)) {
                        player.loadedChunks.add(chunkcoordintpair);
                    }
                    ++i2;
                }
                ++l1;
            }
            ++k1;
        }
        i %= 4;
        int j2 = 0;
        while (j2 < j * 2) {
            chunkcoordintpair = this.getPlayerInstance(k + (i1 += this.xzDirectionsConst[i][0]), l + (j1 += this.xzDirectionsConst[i][1]), true).chunkCoords;
            if (list.contains(chunkcoordintpair)) {
                player.loadedChunks.add(chunkcoordintpair);
            }
            ++j2;
        }
    }

    public void removePlayer(EntityPlayerMP player) {
        int i = (int)player.managedPosX >> 4;
        int j = (int)player.managedPosZ >> 4;
        int k = i - this.playerViewRadius;
        while (k <= i + this.playerViewRadius) {
            int l = j - this.playerViewRadius;
            while (l <= j + this.playerViewRadius) {
                PlayerInstance playermanager$playerinstance = this.getPlayerInstance(k, l, false);
                if (playermanager$playerinstance != null) {
                    playermanager$playerinstance.removePlayer(player);
                }
                ++l;
            }
            ++k;
        }
        this.players.remove(player);
    }

    private boolean overlaps(int x1, int z1, int x2, int z2, int radius) {
        int i = x1 - x2;
        int j = z1 - z2;
        return i >= -radius && i <= radius ? j >= -radius && j <= radius : false;
    }

    public void updateMountedMovingPlayer(EntityPlayerMP player) {
        int i = (int)player.posX >> 4;
        int j = (int)player.posZ >> 4;
        double d0 = player.managedPosX - player.posX;
        double d1 = player.managedPosZ - player.posZ;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 >= 64.0) {
            int k = (int)player.managedPosX >> 4;
            int l = (int)player.managedPosZ >> 4;
            int i1 = this.playerViewRadius;
            int j1 = i - k;
            int k1 = j - l;
            if (j1 != 0 || k1 != 0) {
                int l1 = i - i1;
                while (l1 <= i + i1) {
                    int i2 = j - i1;
                    while (i2 <= j + i1) {
                        PlayerInstance playermanager$playerinstance;
                        if (!this.overlaps(l1, i2, k, l, i1)) {
                            this.getPlayerInstance(l1, i2, true).addPlayer(player);
                        }
                        if (!this.overlaps(l1 - j1, i2 - k1, i, j, i1) && (playermanager$playerinstance = this.getPlayerInstance(l1 - j1, i2 - k1, false)) != null) {
                            playermanager$playerinstance.removePlayer(player);
                        }
                        ++i2;
                    }
                    ++l1;
                }
                this.filterChunkLoadQueue(player);
                player.managedPosX = player.posX;
                player.managedPosZ = player.posZ;
            }
        }
    }

    public boolean isPlayerWatchingChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
        PlayerInstance playermanager$playerinstance = this.getPlayerInstance(chunkX, chunkZ, false);
        return playermanager$playerinstance != null && playermanager$playerinstance.playersWatchingChunk.contains(player) && !player.loadedChunks.contains(playermanager$playerinstance.chunkCoords);
    }

    public void setPlayerViewRadius(int radius) {
        if ((radius = MathHelper.clamp_int(radius, 3, 32)) != this.playerViewRadius) {
            int i = radius - this.playerViewRadius;
            for (EntityPlayerMP entityplayermp : Lists.newArrayList(this.players)) {
                int j = (int)entityplayermp.posX >> 4;
                int k = (int)entityplayermp.posZ >> 4;
                if (i > 0) {
                    int j1 = j - radius;
                    while (j1 <= j + radius) {
                        int k1 = k - radius;
                        while (k1 <= k + radius) {
                            PlayerInstance playermanager$playerinstance = this.getPlayerInstance(j1, k1, true);
                            if (!playermanager$playerinstance.playersWatchingChunk.contains(entityplayermp)) {
                                playermanager$playerinstance.addPlayer(entityplayermp);
                            }
                            ++k1;
                        }
                        ++j1;
                    }
                    continue;
                }
                int l = j - this.playerViewRadius;
                while (l <= j + this.playerViewRadius) {
                    int i1 = k - this.playerViewRadius;
                    while (i1 <= k + this.playerViewRadius) {
                        if (!this.overlaps(l, i1, j, k, radius)) {
                            this.getPlayerInstance(l, i1, true).removePlayer(entityplayermp);
                        }
                        ++i1;
                    }
                    ++l;
                }
            }
            this.playerViewRadius = radius;
        }
    }

    public static int getFurthestViewableBlock(int distance) {
        return distance * 16 - 16;
    }

    class PlayerInstance {
        private final List<EntityPlayerMP> playersWatchingChunk = Lists.newArrayList();
        private final ChunkCoordIntPair chunkCoords;
        private short[] locationOfBlockChange = new short[64];
        private int numBlocksToUpdate;
        private int flagsYAreasToUpdate;
        private long previousWorldTime;

        public PlayerInstance(int chunkX, int chunkZ) {
            this.chunkCoords = new ChunkCoordIntPair(chunkX, chunkZ);
            PlayerManager.this.getWorldServer().theChunkProviderServer.loadChunk(chunkX, chunkZ);
        }

        public void addPlayer(EntityPlayerMP player) {
            if (this.playersWatchingChunk.contains(player)) {
                pmLogger.debug("Failed to add player. {} already is in chunk {}, {}", new Object[]{player, this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos});
            } else {
                if (this.playersWatchingChunk.isEmpty()) {
                    this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
                }
                this.playersWatchingChunk.add(player);
                player.loadedChunks.add(this.chunkCoords);
            }
        }

        public void removePlayer(EntityPlayerMP player) {
            if (this.playersWatchingChunk.contains(player)) {
                Chunk chunk = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                if (chunk.isPopulated()) {
                    player.playerNetServerHandler.sendPacket(new S21PacketChunkData(chunk, true, 0));
                }
                this.playersWatchingChunk.remove(player);
                player.loadedChunks.remove(this.chunkCoords);
                if (this.playersWatchingChunk.isEmpty()) {
                    long i = (long)this.chunkCoords.chunkXPos + Integer.MAX_VALUE | (long)this.chunkCoords.chunkZPos + Integer.MAX_VALUE << 32;
                    this.increaseInhabitedTime(chunk);
                    PlayerManager.this.playerInstances.remove(i);
                    PlayerManager.this.playerInstanceList.remove(this);
                    if (this.numBlocksToUpdate > 0) {
                        PlayerManager.this.playerInstancesToUpdate.remove(this);
                    }
                    PlayerManager.this.getWorldServer().theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                }
            }
        }

        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
        }

        private void increaseInhabitedTime(Chunk theChunk) {
            theChunk.setInhabitedTime(theChunk.getInhabitedTime() + PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime);
            this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
        }

        public void flagChunkForUpdate(int x, int y, int z) {
            if (this.numBlocksToUpdate == 0) {
                PlayerManager.this.playerInstancesToUpdate.add(this);
            }
            this.flagsYAreasToUpdate |= 1 << (y >> 4);
            if (this.numBlocksToUpdate < 64) {
                short short1 = (short)(x << 12 | z << 8 | y);
                int i = 0;
                while (i < this.numBlocksToUpdate) {
                    if (this.locationOfBlockChange[i] == short1) {
                        return;
                    }
                    ++i;
                }
                this.locationOfBlockChange[this.numBlocksToUpdate++] = short1;
            }
        }

        public void sendToAllPlayersWatchingChunk(Packet thePacket) {
            int i = 0;
            while (i < this.playersWatchingChunk.size()) {
                EntityPlayerMP entityplayermp = this.playersWatchingChunk.get(i);
                if (!entityplayermp.loadedChunks.contains(this.chunkCoords)) {
                    entityplayermp.playerNetServerHandler.sendPacket(thePacket);
                }
                ++i;
            }
        }

        public void onUpdate() {
            if (this.numBlocksToUpdate != 0) {
                if (this.numBlocksToUpdate == 1) {
                    int i = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
                    int j = this.locationOfBlockChange[0] & 0xFF;
                    int k = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
                    BlockPos blockpos = new BlockPos(i, j, k);
                    this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.this.theWorldServer, blockpos));
                    if (PlayerManager.this.theWorldServer.getBlockState(blockpos).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos));
                    }
                } else if (this.numBlocksToUpdate == 64) {
                    int i1 = this.chunkCoords.chunkXPos * 16;
                    int k1 = this.chunkCoords.chunkZPos * 16;
                    this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), false, this.flagsYAreasToUpdate));
                    int i2 = 0;
                    while (i2 < 16) {
                        if ((this.flagsYAreasToUpdate & 1 << i2) != 0) {
                            int k2 = i2 << 4;
                            List<TileEntity> list = PlayerManager.this.theWorldServer.getTileEntitiesIn(i1, k2, k1, i1 + 16, k2 + 16, k1 + 16);
                            int l = 0;
                            while (l < list.size()) {
                                this.sendTileToAllPlayersWatchingChunk(list.get(l));
                                ++l;
                            }
                        }
                        ++i2;
                    }
                } else {
                    this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
                    int j1 = 0;
                    while (j1 < this.numBlocksToUpdate) {
                        int l1 = (this.locationOfBlockChange[j1] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
                        int j2 = this.locationOfBlockChange[j1] & 0xFF;
                        int l2 = (this.locationOfBlockChange[j1] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
                        BlockPos blockpos1 = new BlockPos(l1, j2, l2);
                        if (PlayerManager.this.theWorldServer.getBlockState(blockpos1).getBlock().hasTileEntity()) {
                            this.sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos1));
                        }
                        ++j1;
                    }
                }
                this.numBlocksToUpdate = 0;
                this.flagsYAreasToUpdate = 0;
            }
        }

        private void sendTileToAllPlayersWatchingChunk(TileEntity theTileEntity) {
            Packet packet;
            if (theTileEntity != null && (packet = theTileEntity.getDescriptionPacket()) != null) {
                this.sendToAllPlayersWatchingChunk(packet);
            }
        }
    }
}

