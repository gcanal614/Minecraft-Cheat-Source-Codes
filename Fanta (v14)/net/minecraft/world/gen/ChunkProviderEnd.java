/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class ChunkProviderEnd
implements IChunkProvider {
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    public ChunkProviderEnd(World worldIn, long p_i2007_2_) {
        this.endWorld = worldIn;
        this.endRNG = new Random(p_i2007_2_);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }

    public void func_180520_a(int p_180520_1_, int p_180520_2_, ChunkPrimer p_180520_3_) {
        int i = 2;
        int j = i + 1;
        int k = 33;
        int l = i + 1;
        this.densities = this.initializeNoiseField(this.densities, p_180520_1_ * i, 0, p_180520_2_ * i, j, k, l);
        int i1 = 0;
        while (i1 < i) {
            int j1 = 0;
            while (j1 < i) {
                int k1 = 0;
                while (k1 < 32) {
                    double d0 = 0.25;
                    double d1 = this.densities[((i1 + 0) * l + j1 + 0) * k + k1 + 0];
                    double d2 = this.densities[((i1 + 0) * l + j1 + 1) * k + k1 + 0];
                    double d3 = this.densities[((i1 + 1) * l + j1 + 0) * k + k1 + 0];
                    double d4 = this.densities[((i1 + 1) * l + j1 + 1) * k + k1 + 0];
                    double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * k + k1 + 1] - d1) * d0;
                    double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
                    double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * k + k1 + 1] - d3) * d0;
                    double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;
                    int l1 = 0;
                    while (l1 < 4) {
                        double d9 = 0.125;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        int i2 = 0;
                        while (i2 < 8) {
                            double d14 = 0.125;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            int j2 = 0;
                            while (j2 < 8) {
                                IBlockState iblockstate = null;
                                if (d15 > 0.0) {
                                    iblockstate = Blocks.end_stone.getDefaultState();
                                }
                                int k2 = i2 + i1 * 8;
                                int l2 = l1 + k1 * 4;
                                int i3 = j2 + j1 * 8;
                                p_180520_3_.setBlockState(k2, l2, i3, iblockstate);
                                d15 += d16;
                                ++j2;
                            }
                            d10 += d12;
                            d11 += d13;
                            ++i2;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                        ++l1;
                    }
                    ++k1;
                }
                ++j1;
            }
            ++i1;
        }
    }

    public void func_180519_a(ChunkPrimer p_180519_1_) {
        int i = 0;
        while (i < 16) {
            int j = 0;
            while (j < 16) {
                int k = 1;
                int l = -1;
                IBlockState iblockstate = Blocks.end_stone.getDefaultState();
                IBlockState iblockstate1 = Blocks.end_stone.getDefaultState();
                int i1 = 127;
                while (i1 >= 0) {
                    IBlockState iblockstate2 = p_180519_1_.getBlockState(i, i1, j);
                    if (iblockstate2.getBlock().getMaterial() == Material.air) {
                        l = -1;
                    } else if (iblockstate2.getBlock() == Blocks.stone) {
                        if (l == -1) {
                            if (k <= 0) {
                                iblockstate = Blocks.air.getDefaultState();
                                iblockstate1 = Blocks.end_stone.getDefaultState();
                            }
                            l = k;
                            if (i1 >= 0) {
                                p_180519_1_.setBlockState(i, i1, j, iblockstate);
                            } else {
                                p_180519_1_.setBlockState(i, i1, j, iblockstate1);
                            }
                        } else if (l > 0) {
                            --l;
                            p_180519_1_.setBlockState(i, i1, j, iblockstate1);
                        }
                    }
                    --i1;
                }
                ++j;
            }
            ++i;
        }
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        this.endRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.func_180520_a(x, z, chunkprimer);
        this.func_180519_a(chunkprimer);
        Chunk chunk = new Chunk(this.endWorld, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();
        int i = 0;
        while (i < abyte.length) {
            abyte[i] = (byte)this.biomesForGeneration[i].biomeID;
            ++i;
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_) {
        if (p_73187_1_ == null) {
            p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
        }
        double d0 = 684.412;
        double d1 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0, 200.0, 0.5);
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, (d0 *= 2.0) / 80.0, d1 / 160.0, d0 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
        int i = 0;
        int j = 0;
        while (j < p_73187_5_) {
            int k = 0;
            while (k < p_73187_7_) {
                float f = (float)(j + p_73187_2_) / 1.0f;
                float f1 = (float)(k + p_73187_4_) / 1.0f;
                float f2 = 100.0f - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0f;
                if (f2 > 80.0f) {
                    f2 = 80.0f;
                }
                if (f2 < -100.0f) {
                    f2 = -100.0f;
                }
                int l = 0;
                while (l < p_73187_6_) {
                    double d2 = 0.0;
                    double d3 = this.noiseData2[i] / 512.0;
                    double d4 = this.noiseData3[i] / 512.0;
                    double d5 = (this.noiseData1[i] / 10.0 + 1.0) / 2.0;
                    d2 = d5 < 0.0 ? d3 : (d5 > 1.0 ? d4 : d3 + (d4 - d3) * d5);
                    d2 -= 8.0;
                    d2 += (double)f2;
                    int i1 = 2;
                    if (l > p_73187_6_ / 2 - i1) {
                        double d6 = (float)(l - (p_73187_6_ / 2 - i1)) / 64.0f;
                        d6 = MathHelper.clamp_double(d6, 0.0, 1.0);
                        d2 = d2 * (1.0 - d6) + -3000.0 * d6;
                    }
                    if (l < (i1 = 8)) {
                        double d7 = (float)(i1 - l) / ((float)i1 - 1.0f);
                        d2 = d2 * (1.0 - d7) + -30.0 * d7;
                    }
                    p_73187_1_[i] = d2;
                    ++i;
                    ++l;
                }
                ++k;
            }
            ++j;
        }
        return p_73187_1_;
    }

    @Override
    public boolean chunkExists(int x, int z) {
        return true;
    }

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        BlockFalling.fallInstantly = true;
        BlockPos blockpos = new BlockPos(p_73153_2_ * 16, 0, p_73153_3_ * 16);
        this.endWorld.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, blockpos);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_) {
        return false;
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate progressCallback) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "RandomLevelSource";
    }

    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.endWorld.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
    }

    @Override
    public Chunk provideChunk(BlockPos blockPosIn) {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }
}

