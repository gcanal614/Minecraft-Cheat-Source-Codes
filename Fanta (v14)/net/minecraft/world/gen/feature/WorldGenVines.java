/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVines
extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (position.getY() < 128) {
            if (worldIn.isAirBlock(position)) {
                EnumFacing[] enumFacingArray = EnumFacing.Plane.HORIZONTAL.facings();
                int n = enumFacingArray.length;
                int n2 = 0;
                while (n2 < n) {
                    EnumFacing enumfacing = enumFacingArray[n2];
                    if (Blocks.vine.canPlaceBlockOnSide(worldIn, position, enumfacing)) {
                        IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty(BlockVine.NORTH, enumfacing == EnumFacing.NORTH).withProperty(BlockVine.EAST, enumfacing == EnumFacing.EAST).withProperty(BlockVine.SOUTH, enumfacing == EnumFacing.SOUTH).withProperty(BlockVine.WEST, enumfacing == EnumFacing.WEST);
                        worldIn.setBlockState(position, iblockstate, 2);
                        break;
                    }
                    ++n2;
                }
            } else {
                position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            }
            position = position.up();
        }
        return true;
    }
}

