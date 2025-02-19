/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;

public class BiomeGenForest
extends BiomeGenBase {
    private int field_150632_aF;
    protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
    protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
    protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);

    public BiomeGenForest(int p_i45377_1_, int p_i45377_2_) {
        super(p_i45377_1_);
        this.field_150632_aF = p_i45377_2_;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }
        this.setFillerBlockMetadata(5159473);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }
        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }

    @Override
    protected BiomeGenBase func_150557_a(int p_150557_1_, boolean p_150557_2_) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = p_150557_1_;
            if (p_150557_2_) {
                this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
            }
            return this;
        }
        return super.func_150557_a(p_150557_1_, p_150557_2_);
    }

    @Override
    public WorldGenAbstractTree genBigTreeChance(Random rand) {
        return this.field_150632_aF == 3 && rand.nextInt(3) > 0 ? field_150631_aE : (this.field_150632_aF != 2 && rand.nextInt(5) != 0 ? this.worldGeneratorTrees : field_150630_aD);
    }

    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
        if (this.field_150632_aF == 1) {
            double d0 = MathHelper.clamp_double((1.0 + GRASS_COLOR_NOISE.func_151601_a((double)pos.getX() / 48.0, (double)pos.getZ() / 48.0)) / 2.0, 0.0, 0.9999);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
        }
        return super.pickRandomFlower(rand, pos);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        if (this.field_150632_aF == 3) {
            int i = 0;
            while (i < 4) {
                int j = 0;
                while (j < 4) {
                    int k = i * 4 + 1 + 8 + rand.nextInt(3);
                    int l = j * 4 + 1 + 8 + rand.nextInt(3);
                    BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
                    if (rand.nextInt(20) == 0) {
                        WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
                        worldgenbigmushroom.generate(worldIn, rand, blockpos);
                    } else {
                        WorldGenAbstractTree worldgenabstracttree = this.genBigTreeChance(rand);
                        worldgenabstracttree.func_175904_e();
                        if (worldgenabstracttree.generate(worldIn, rand, blockpos)) {
                            worldgenabstracttree.func_180711_a(worldIn, rand, blockpos);
                        }
                    }
                    ++j;
                }
                ++i;
            }
        }
        int j1 = rand.nextInt(5) - 3;
        if (this.field_150632_aF == 1) {
            j1 += 2;
        }
        int k1 = 0;
        while (k1 < j1) {
            int l1 = rand.nextInt(3);
            if (l1 == 0) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            } else if (l1 == 1) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            } else if (l1 == 2) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }
            int i2 = 0;
            while (i2 < 5) {
                int j2 = rand.nextInt(16) + 8;
                int k2 = rand.nextInt(16) + 8;
                int i1 = rand.nextInt(worldIn.getHeight(pos.add(j2, 0, k2)).getY() + 32);
                if (DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, new BlockPos(pos.getX() + j2, i1, pos.getZ() + k2))) break;
                ++i2;
            }
            ++k1;
        }
        super.decorate(worldIn, rand, pos);
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        int i = super.getGrassColorAtPos(pos);
        return this.field_150632_aF == 3 ? (i & 0xFEFEFE) + 2634762 >> 1 : i;
    }

    @Override
    protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            BiomeGenForest biomegenforest = new BiomeGenForest(p_180277_1_, 1);
            biomegenforest.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2f));
            biomegenforest.setBiomeName("Flower Forest");
            biomegenforest.func_150557_a(6976549, true);
            biomegenforest.setFillerBlockMetadata(8233509);
            return biomegenforest;
        }
        return this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID ? new BiomeGenMutated(p_180277_1_, this){

            @Override
            public void decorate(World worldIn, Random rand, BlockPos pos) {
                this.baseBiome.decorate(worldIn, rand, pos);
            }
        } : new BiomeGenMutated(p_180277_1_, this){

            @Override
            public WorldGenAbstractTree genBigTreeChance(Random rand) {
                return rand.nextBoolean() ? field_150629_aC : field_150630_aD;
            }
        };
    }
}

