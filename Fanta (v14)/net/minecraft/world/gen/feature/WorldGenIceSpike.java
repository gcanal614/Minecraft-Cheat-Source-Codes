/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIceSpike
extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (worldIn.isAirBlock(position) && position.getY() > 2) {
            position = position.down();
        }
        if (worldIn.getBlockState(position).getBlock() != Blocks.snow) {
            return false;
        }
        position = position.up(rand.nextInt(4));
        int i = rand.nextInt(4) + 7;
        int j = i / 4 + rand.nextInt(2);
        if (j > 1 && rand.nextInt(60) == 0) {
            position = position.up(10 + rand.nextInt(30));
        }
        int k = 0;
        while (k < i) {
            float f = (1.0f - (float)k / (float)i) * (float)j;
            int l = MathHelper.ceiling_float_int(f);
            int i1 = -l;
            while (i1 <= l) {
                float f1 = (float)MathHelper.abs_int(i1) - 0.25f;
                int j1 = -l;
                while (j1 <= l) {
                    float f2 = (float)MathHelper.abs_int(j1) - 0.25f;
                    if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || rand.nextFloat() <= 0.75f)) {
                        Block block = worldIn.getBlockState(position.add(i1, k, j1)).getBlock();
                        if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
                            this.setBlockAndNotifyAdequately(worldIn, position.add(i1, k, j1), Blocks.packed_ice.getDefaultState());
                        }
                        if (k != 0 && l > 1 && ((block = worldIn.getBlockState(position.add(i1, -k, j1)).getBlock()).getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice)) {
                            this.setBlockAndNotifyAdequately(worldIn, position.add(i1, -k, j1), Blocks.packed_ice.getDefaultState());
                        }
                    }
                    ++j1;
                }
                ++i1;
            }
            ++k;
        }
        int k1 = j - 1;
        if (k1 < 0) {
            k1 = 0;
        } else if (k1 > 1) {
            k1 = 1;
        }
        int l1 = -k1;
        while (l1 <= k1) {
            int i2 = -k1;
            while (i2 <= k1) {
                BlockPos blockpos = position.add(l1, -1, i2);
                int j2 = 50;
                if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                    j2 = rand.nextInt(5);
                }
                while (blockpos.getY() > 50) {
                    Block block1 = worldIn.getBlockState(blockpos).getBlock();
                    if (block1.getMaterial() != Material.air && block1 != Blocks.dirt && block1 != Blocks.snow && block1 != Blocks.ice && block1 != Blocks.packed_ice) break;
                    this.setBlockAndNotifyAdequately(worldIn, blockpos, Blocks.packed_ice.getDefaultState());
                    blockpos = blockpos.down();
                    if (--j2 > 0) continue;
                    blockpos = blockpos.down(rand.nextInt(5) + 1);
                    j2 = rand.nextInt(5);
                }
                ++i2;
            }
            ++l1;
        }
        return true;
    }
}

