/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenPlains
extends BiomeGenBase {
    protected boolean field_150628_aC;

    protected BiomeGenPlains(int p_i1986_1_) {
        super(p_i1986_1_);
        this.setTemperatureRainfall(0.8f, 0.4f);
        this.setHeight(height_LowPlains);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
    }

    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
        double d0 = GRASS_COLOR_NOISE.func_151601_a((double)pos.getX() / 200.0, (double)pos.getZ() / 200.0);
        if (d0 < -0.8) {
            int j = rand.nextInt(4);
            switch (j) {
                case 0: {
                    return BlockFlower.EnumFlowerType.ORANGE_TULIP;
                }
                case 1: {
                    return BlockFlower.EnumFlowerType.RED_TULIP;
                }
                case 2: {
                    return BlockFlower.EnumFlowerType.PINK_TULIP;
                }
            }
            return BlockFlower.EnumFlowerType.WHITE_TULIP;
        }
        if (rand.nextInt(3) > 0) {
            int i = rand.nextInt(3);
            return i == 0 ? BlockFlower.EnumFlowerType.POPPY : (i == 1 ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
        }
        return BlockFlower.EnumFlowerType.DANDELION;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        double d0 = GRASS_COLOR_NOISE.func_151601_a((double)(pos.getX() + 8) / 200.0, (double)(pos.getZ() + 8) / 200.0);
        if (d0 < -0.8) {
            this.theBiomeDecorator.flowersPerChunk = 15;
            this.theBiomeDecorator.grassPerChunk = 5;
        } else {
            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
            int i = 0;
            while (i < 7) {
                int j = rand.nextInt(16) + 8;
                int k = rand.nextInt(16) + 8;
                int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
                DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
                ++i;
            }
        }
        if (this.field_150628_aC) {
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            int i1 = 0;
            while (i1 < 10) {
                int j1 = rand.nextInt(16) + 8;
                int k1 = rand.nextInt(16) + 8;
                int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
                DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
                ++i1;
            }
        }
        super.decorate(worldIn, rand, pos);
    }

    @Override
    protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
        BiomeGenPlains biomegenplains = new BiomeGenPlains(p_180277_1_);
        biomegenplains.setBiomeName("Sunflower Plains");
        biomegenplains.field_150628_aC = true;
        biomegenplains.setColor(9286496);
        biomegenplains.field_150609_ah = 14273354;
        return biomegenplains;
    }
}

