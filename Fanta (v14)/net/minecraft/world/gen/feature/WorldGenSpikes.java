/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSpikes
extends WorldGenerator {
    private Block baseBlockRequired;

    public WorldGenSpikes(Block p_i45464_1_) {
        this.baseBlockRequired = p_i45464_1_;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.isAirBlock(position) && worldIn.getBlockState(position.down()).getBlock() == this.baseBlockRequired) {
            int i = rand.nextInt(32) + 6;
            int j = rand.nextInt(4) + 1;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            int k = position.getX() - j;
            while (k <= position.getX() + j) {
                int l = position.getZ() - j;
                while (l <= position.getZ() + j) {
                    int j1;
                    int i1 = k - position.getX();
                    if (i1 * i1 + (j1 = l - position.getZ()) * j1 <= j * j + 1 && worldIn.getBlockState(blockpos$mutableblockpos.func_181079_c(k, position.getY() - 1, l)).getBlock() != this.baseBlockRequired) {
                        return false;
                    }
                    ++l;
                }
                ++k;
            }
            int l1 = position.getY();
            while (l1 < position.getY() + i && l1 < 256) {
                int i2 = position.getX() - j;
                while (i2 <= position.getX() + j) {
                    int j2 = position.getZ() - j;
                    while (j2 <= position.getZ() + j) {
                        int k1;
                        int k2 = i2 - position.getX();
                        if (k2 * k2 + (k1 = j2 - position.getZ()) * k1 <= j * j + 1) {
                            worldIn.setBlockState(new BlockPos(i2, l1, j2), Blocks.obsidian.getDefaultState(), 2);
                        }
                        ++j2;
                    }
                    ++i2;
                }
                ++l1;
            }
            EntityEnderCrystal entity = new EntityEnderCrystal(worldIn);
            entity.setLocationAndAngles((float)position.getX() + 0.5f, position.getY() + i, (float)position.getZ() + 0.5f, rand.nextFloat() * 360.0f, 0.0f);
            worldIn.spawnEntityInWorld(entity);
            worldIn.setBlockState(position.up(i), Blocks.bedrock.getDefaultState(), 2);
            return true;
        }
        return false;
    }
}

