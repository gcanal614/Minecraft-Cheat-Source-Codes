/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 */
package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.storage.MapStorage;
import optifine.Reflector;
import optifine.ReflectorMethod;

public abstract class MapGenStructure
extends MapGenBase {
    private MapGenStructureData structureData;
    protected Map structureMap = Maps.newHashMap();
    private static final String __OBFID = "CL_00000505";
    private LongHashMap structureLongMap = new LongHashMap();

    public abstract String getStructureName();

    @Override
    protected final void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
        this.func_143027_a(worldIn);
        if (!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ))) {
            this.rand.nextInt();
            try {
                if (this.canSpawnStructureAtCoords(chunkX, chunkZ)) {
                    StructureStart structurestart = this.getStructureStart(chunkX, chunkZ);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ), structurestart);
                    this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ), structurestart);
                    this.func_143026_a(chunkX, chunkZ, structurestart);
                }
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.addCrashSectionCallable("Is feature chunk", new Callable(){
                    private static final String __OBFID = "CL_00000506";

                    public String call() throws Exception {
                        return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
                    }
                });
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", chunkX, chunkZ));
                crashreportcategory.addCrashSectionCallable("Chunk pos hash", new Callable(){
                    private static final String __OBFID = "CL_00000507";

                    public String call() throws Exception {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
                    }
                });
                crashreportcategory.addCrashSectionCallable("Structure type", new Callable(){
                    private static final String __OBFID = "CL_00000508";

                    public String call() throws Exception {
                        return MapGenStructure.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    public boolean generateStructure(World worldIn, Random randomIn, ChunkCoordIntPair chunkCoord) {
        this.func_143027_a(worldIn);
        int i = (chunkCoord.chunkXPos << 4) + 8;
        int j = (chunkCoord.chunkZPos << 4) + 8;
        boolean flag = false;
        for (Object structurestart0 : this.structureMap.values()) {
            StructureStart structurestart = (StructureStart)structurestart0;
            if (!structurestart.isSizeableStructure() || !structurestart.func_175788_a(chunkCoord) || !structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) continue;
            structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
            structurestart.func_175787_b(chunkCoord);
            flag = true;
            this.func_143026_a(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
        }
        return flag;
    }

    public boolean func_175795_b(BlockPos pos) {
        this.func_143027_a(this.worldObj);
        return this.func_175797_c(pos) != null;
    }

    protected StructureStart func_175797_c(BlockPos pos) {
        for (Object structurestart0 : this.structureMap.values()) {
            StructureStart structurestart = (StructureStart)structurestart0;
            if (!structurestart.isSizeableStructure() || !structurestart.getBoundingBox().isVecInside(pos)) continue;
            for (StructureComponent structurecomponent : structurestart.getComponents()) {
                if (!structurecomponent.getBoundingBox().isVecInside(pos)) continue;
                return structurestart;
            }
        }
        return null;
    }

    public boolean func_175796_a(World worldIn, BlockPos pos) {
        this.func_143027_a(worldIn);
        for (Object structurestart0 : this.structureMap.values()) {
            StructureStart structurestart = (StructureStart)structurestart0;
            if (!structurestart.isSizeableStructure() || !structurestart.getBoundingBox().isVecInside(pos)) continue;
            return true;
        }
        return false;
    }

    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos) {
        this.worldObj = worldIn;
        this.func_143027_a(worldIn);
        this.rand.setSeed(worldIn.getSeed());
        long i = this.rand.nextLong();
        long j = this.rand.nextLong();
        long k = (long)(pos.getX() >> 4) * i;
        long l = (long)(pos.getZ() >> 4) * j;
        this.rand.setSeed(k ^ l ^ worldIn.getSeed());
        this.recursiveGenerate(worldIn, pos.getX() >> 4, pos.getZ() >> 4, 0, 0, null);
        double d0 = Double.MAX_VALUE;
        BlockPos blockpos = null;
        for (Object structurestart0 : this.structureMap.values()) {
            StructureComponent structurecomponent;
            BlockPos blockpos1;
            double d1;
            StructureStart structurestart = (StructureStart)structurestart0;
            if (!structurestart.isSizeableStructure() || !((d1 = (blockpos1 = (structurecomponent = structurestart.getComponents().get(0)).getBoundingBoxCenter()).distanceSq(pos)) < d0)) continue;
            d0 = d1;
            blockpos = blockpos1;
        }
        if (blockpos != null) {
            return blockpos;
        }
        List list = this.getCoordList();
        if (list != null) {
            BlockPos blockpos3 = null;
            for (Object blockpos2 : list) {
                double d2 = ((BlockPos)blockpos2).distanceSq(pos);
                if (!(d2 < d0)) continue;
                d0 = d2;
                blockpos3 = (BlockPos)blockpos2;
            }
            return blockpos3;
        }
        return null;
    }

    protected List getCoordList() {
        return null;
    }

    private void func_143027_a(World worldIn) {
        if (this.structureData == null) {
            if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                MapStorage mapstorage = (MapStorage)Reflector.call((Object)worldIn, (ReflectorMethod)Reflector.ForgeWorld_getPerWorldStorage, (Object[])new Object[0]);
                this.structureData = (MapGenStructureData)mapstorage.loadData(MapGenStructureData.class, this.getStructureName());
            } else {
                this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, this.getStructureName());
            }
            if (this.structureData == null) {
                this.structureData = new MapGenStructureData(this.getStructureName());
                if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                    MapStorage mapstorage1 = (MapStorage)Reflector.call((Object)worldIn, (ReflectorMethod)Reflector.ForgeWorld_getPerWorldStorage, (Object[])new Object[0]);
                    mapstorage1.setData(this.getStructureName(), this.structureData);
                } else {
                    worldIn.setItemData(this.getStructureName(), this.structureData);
                }
            } else {
                NBTTagCompound nbttagcompound1 = this.structureData.getTagCompound();
                for (String s : nbttagcompound1.getKeySet()) {
                    NBTTagCompound nbttagcompound;
                    NBTBase nbtbase = nbttagcompound1.getTag(s);
                    if (nbtbase.getId() != 10 || !(nbttagcompound = (NBTTagCompound)nbtbase).hasKey("ChunkX") || !nbttagcompound.hasKey("ChunkZ")) continue;
                    int i = nbttagcompound.getInteger("ChunkX");
                    int j = nbttagcompound.getInteger("ChunkZ");
                    StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound, worldIn);
                    if (structurestart == null) continue;
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(i, j), structurestart);
                    this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(i, j), structurestart);
                }
            }
        }
    }

    private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart start) {
        this.structureData.writeInstance(start.writeStructureComponentsToNBT(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
        this.structureData.markDirty();
    }

    protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);

    protected abstract StructureStart getStructureStart(int var1, int var2);
}

