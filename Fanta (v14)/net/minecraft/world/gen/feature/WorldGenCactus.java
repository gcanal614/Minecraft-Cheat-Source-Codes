/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCactus
extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = 0;
        while (i < 10) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos)) {
                int j = 1 + rand.nextInt(rand.nextInt(3) + 1);
                int k = 0;
                while (k < j) {
                    if (Blocks.cactus.canBlockStay(worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos.up(k), Blocks.cactus.getDefaultState(), 2);
                    }
                    ++k;
                }
            }
            ++i;
        }
        return true;
    }
}

