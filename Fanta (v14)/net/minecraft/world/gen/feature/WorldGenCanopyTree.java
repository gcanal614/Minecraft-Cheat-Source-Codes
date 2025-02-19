/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenCanopyTree
extends WorldGenAbstractTree {
    private static final IBlockState field_181640_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);
    private static final IBlockState field_181641_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK).withProperty(BlockLeaves.CHECK_DECAY, false);

    public WorldGenCanopyTree(boolean p_i45461_1_) {
        super(p_i45461_1_);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = rand.nextInt(3) + rand.nextInt(2) + 6;
        int j = position.getX();
        int k = position.getY();
        int l = position.getZ();
        if (k >= 1 && k + i + 1 < 256) {
            BlockPos blockpos = position.down();
            Block block = worldIn.getBlockState(blockpos).getBlock();
            if (block != Blocks.grass && block != Blocks.dirt) {
                return false;
            }
            if (!this.func_181638_a(worldIn, position, i)) {
                return false;
            }
            this.func_175921_a(worldIn, blockpos);
            this.func_175921_a(worldIn, blockpos.east());
            this.func_175921_a(worldIn, blockpos.south());
            this.func_175921_a(worldIn, blockpos.south().east());
            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
            int i1 = i - rand.nextInt(4);
            int j1 = 2 - rand.nextInt(3);
            int k1 = j;
            int l1 = l;
            int i2 = k + i - 1;
            int j2 = 0;
            while (j2 < i) {
                int k2;
                BlockPos blockpos1;
                Material material;
                if (j2 >= i1 && j1 > 0) {
                    k1 += enumfacing.getFrontOffsetX();
                    l1 += enumfacing.getFrontOffsetZ();
                    --j1;
                }
                if ((material = worldIn.getBlockState(blockpos1 = new BlockPos(k1, k2 = k + j2, l1)).getBlock().getMaterial()) == Material.air || material == Material.leaves) {
                    this.func_181639_b(worldIn, blockpos1);
                    this.func_181639_b(worldIn, blockpos1.east());
                    this.func_181639_b(worldIn, blockpos1.south());
                    this.func_181639_b(worldIn, blockpos1.east().south());
                }
                ++j2;
            }
            int i3 = -2;
            while (i3 <= 0) {
                int l3 = -2;
                while (l3 <= 0) {
                    int k4 = -1;
                    this.func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
                    this.func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
                    this.func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
                    this.func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
                    if (!(i3 <= -2 && l3 <= -1 || i3 == -1 && l3 == -2)) {
                        k4 = 1;
                        this.func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
                        this.func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
                        this.func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
                        this.func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
                    }
                    ++l3;
                }
                ++i3;
            }
            if (rand.nextBoolean()) {
                this.func_150526_a(worldIn, k1, i2 + 2, l1);
                this.func_150526_a(worldIn, k1 + 1, i2 + 2, l1);
                this.func_150526_a(worldIn, k1 + 1, i2 + 2, l1 + 1);
                this.func_150526_a(worldIn, k1, i2 + 2, l1 + 1);
            }
            int j3 = -3;
            while (j3 <= 4) {
                int i4 = -3;
                while (i4 <= 4) {
                    if (!(j3 == -3 && i4 == -3 || j3 == -3 && i4 == 4 || j3 == 4 && i4 == -3 || j3 == 4 && i4 == 4 || Math.abs(j3) >= 3 && Math.abs(i4) >= 3)) {
                        this.func_150526_a(worldIn, k1 + j3, i2, l1 + i4);
                    }
                    ++i4;
                }
                ++j3;
            }
            int k3 = -1;
            while (k3 <= 2) {
                int j4 = -1;
                while (j4 <= 2) {
                    if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && rand.nextInt(3) <= 0) {
                        int l4 = rand.nextInt(3) + 2;
                        int i5 = 0;
                        while (i5 < l4) {
                            this.func_181639_b(worldIn, new BlockPos(j + k3, i2 - i5 - 1, l + j4));
                            ++i5;
                        }
                        int j5 = -1;
                        while (j5 <= 1) {
                            int l2 = -1;
                            while (l2 <= 1) {
                                this.func_150526_a(worldIn, k1 + k3 + j5, i2, l1 + j4 + l2);
                                ++l2;
                            }
                            ++j5;
                        }
                        int k5 = -2;
                        while (k5 <= 2) {
                            int l5 = -2;
                            while (l5 <= 2) {
                                if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                                    this.func_150526_a(worldIn, k1 + k3 + k5, i2 - 1, l1 + j4 + l5);
                                }
                                ++l5;
                            }
                            ++k5;
                        }
                    }
                    ++j4;
                }
                ++k3;
            }
            return true;
        }
        return false;
    }

    private boolean func_181638_a(World p_181638_1_, BlockPos p_181638_2_, int p_181638_3_) {
        int i = p_181638_2_.getX();
        int j = p_181638_2_.getY();
        int k = p_181638_2_.getZ();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int l = 0;
        while (l <= p_181638_3_ + 1) {
            int i1 = 1;
            if (l == 0) {
                i1 = 0;
            }
            if (l >= p_181638_3_ - 1) {
                i1 = 2;
            }
            int j1 = -i1;
            while (j1 <= i1) {
                int k1 = -i1;
                while (k1 <= i1) {
                    if (!this.func_150523_a(p_181638_1_.getBlockState(blockpos$mutableblockpos.func_181079_c(i + j1, j + l, k + k1)).getBlock())) {
                        return false;
                    }
                    ++k1;
                }
                ++j1;
            }
            ++l;
        }
        return true;
    }

    private void func_181639_b(World p_181639_1_, BlockPos p_181639_2_) {
        if (this.func_150523_a(p_181639_1_.getBlockState(p_181639_2_).getBlock())) {
            this.setBlockAndNotifyAdequately(p_181639_1_, p_181639_2_, field_181640_a);
        }
    }

    private void func_150526_a(World worldIn, int p_150526_2_, int p_150526_3_, int p_150526_4_) {
        BlockPos blockpos = new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_);
        Block block = worldIn.getBlockState(blockpos).getBlock();
        if (block.getMaterial() == Material.air) {
            this.setBlockAndNotifyAdequately(worldIn, blockpos, field_181641_b);
        }
    }
}

