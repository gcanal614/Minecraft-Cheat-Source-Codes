/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 */
package net.minecraft.world.gen.feature;

import com.google.common.base.Predicates;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDesertWells
extends WorldGenerator {
    private static final BlockStateHelper field_175913_a = BlockStateHelper.forBlock(Blocks.sand).where(BlockSand.VARIANT, Predicates.equalTo((Object)BlockSand.EnumType.SAND));
    private final IBlockState field_175911_b = Blocks.stone_slab.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
    private final IBlockState field_175912_c = Blocks.sandstone.getDefaultState();
    private final IBlockState field_175910_d = Blocks.flowing_water.getDefaultState();

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (worldIn.isAirBlock(position) && position.getY() > 2) {
            position = position.down();
        }
        if (!field_175913_a.apply(worldIn.getBlockState(position))) {
            return false;
        }
        int i = -2;
        while (i <= 2) {
            int j = -2;
            while (j <= 2) {
                if (worldIn.isAirBlock(position.add(i, -1, j)) && worldIn.isAirBlock(position.add(i, -2, j))) {
                    return false;
                }
                ++j;
            }
            ++i;
        }
        int l = -1;
        while (l <= 0) {
            int l1 = -2;
            while (l1 <= 2) {
                int k = -2;
                while (k <= 2) {
                    worldIn.setBlockState(position.add(l1, l, k), this.field_175912_c, 2);
                    ++k;
                }
                ++l1;
            }
            ++l;
        }
        worldIn.setBlockState(position, this.field_175910_d, 2);
        for (Object enumfacing : EnumFacing.Plane.HORIZONTAL) {
            worldIn.setBlockState(position.offset((EnumFacing)enumfacing), this.field_175910_d, 2);
        }
        int i1 = -2;
        while (i1 <= 2) {
            int i2 = -2;
            while (i2 <= 2) {
                if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                    worldIn.setBlockState(position.add(i1, 1, i2), this.field_175912_c, 2);
                }
                ++i2;
            }
            ++i1;
        }
        worldIn.setBlockState(position.add(2, 1, 0), this.field_175911_b, 2);
        worldIn.setBlockState(position.add(-2, 1, 0), this.field_175911_b, 2);
        worldIn.setBlockState(position.add(0, 1, 2), this.field_175911_b, 2);
        worldIn.setBlockState(position.add(0, 1, -2), this.field_175911_b, 2);
        int j1 = -1;
        while (j1 <= 1) {
            int j2 = -1;
            while (j2 <= 1) {
                if (j1 == 0 && j2 == 0) {
                    worldIn.setBlockState(position.add(j1, 4, j2), this.field_175912_c, 2);
                } else {
                    worldIn.setBlockState(position.add(j1, 4, j2), this.field_175911_b, 2);
                }
                ++j2;
            }
            ++j1;
        }
        int k1 = 1;
        while (k1 <= 3) {
            worldIn.setBlockState(position.add(-1, k1, -1), this.field_175912_c, 2);
            worldIn.setBlockState(position.add(-1, k1, 1), this.field_175912_c, 2);
            worldIn.setBlockState(position.add(1, k1, -1), this.field_175912_c, 2);
            worldIn.setBlockState(position.add(1, k1, 1), this.field_175912_c, 2);
            ++k1;
        }
        return true;
    }
}

