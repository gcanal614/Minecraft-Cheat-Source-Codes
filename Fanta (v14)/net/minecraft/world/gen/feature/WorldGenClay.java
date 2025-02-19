/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenClay
extends WorldGenerator {
    private Block field_150546_a = Blocks.clay;
    private int numberOfBlocks;

    public WorldGenClay(int p_i2011_1_) {
        this.numberOfBlocks = p_i2011_1_;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water) {
            return false;
        }
        int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
        int j = 1;
        int k = position.getX() - i;
        while (k <= position.getX() + i) {
            int l = position.getZ() - i;
            while (l <= position.getZ() + i) {
                int j1;
                int i1 = k - position.getX();
                if (i1 * i1 + (j1 = l - position.getZ()) * j1 <= i * i) {
                    int k1 = position.getY() - j;
                    while (k1 <= position.getY() + j) {
                        BlockPos blockpos = new BlockPos(k, k1, l);
                        Block block = worldIn.getBlockState(blockpos).getBlock();
                        if (block == Blocks.dirt || block == Blocks.clay) {
                            worldIn.setBlockState(blockpos, this.field_150546_a.getDefaultState(), 2);
                        }
                        ++k1;
                    }
                }
                ++l;
            }
            ++k;
        }
        return true;
    }
}

