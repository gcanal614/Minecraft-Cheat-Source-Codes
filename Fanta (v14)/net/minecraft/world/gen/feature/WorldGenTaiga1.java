/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTaiga1
extends WorldGenAbstractTree {
    private static final IBlockState field_181636_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
    private static final IBlockState field_181637_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false);

    public WorldGenTaiga1() {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = rand.nextInt(5) + 7;
        int j = i - rand.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + rand.nextInt(k + 1);
        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
            int i1 = position.getY();
            while (i1 <= position.getY() + 1 + i && flag) {
                int j1 = 1;
                j1 = i1 - position.getY() < j ? 0 : l;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
                int k1 = position.getX() - j1;
                while (k1 <= position.getX() + j1 && flag) {
                    int l1 = position.getZ() - j1;
                    while (l1 <= position.getZ() + j1 && flag) {
                        if (i1 >= 0 && i1 < 256) {
                            if (!this.func_150523_a(worldIn.getBlockState(blockpos$mutableblockpos.func_181079_c(k1, i1, l1)).getBlock())) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                        ++l1;
                    }
                    ++k1;
                }
                ++i1;
            }
            if (!flag) {
                return false;
            }
            Block block = worldIn.getBlockState(position.down()).getBlock();
            if ((block == Blocks.grass || block == Blocks.dirt) && position.getY() < 256 - i - 1) {
                this.func_175921_a(worldIn, position.down());
                int k2 = 0;
                int l2 = position.getY() + i;
                while (l2 >= position.getY() + j) {
                    int j3 = position.getX() - k2;
                    while (j3 <= position.getX() + k2) {
                        int k3 = j3 - position.getX();
                        int i2 = position.getZ() - k2;
                        while (i2 <= position.getZ() + k2) {
                            BlockPos blockpos;
                            int j2 = i2 - position.getZ();
                            if (!(Math.abs(k3) == k2 && Math.abs(j2) == k2 && k2 > 0 || worldIn.getBlockState(blockpos = new BlockPos(j3, l2, i2)).getBlock().isFullBlock())) {
                                this.setBlockAndNotifyAdequately(worldIn, blockpos, field_181637_b);
                            }
                            ++i2;
                        }
                        ++j3;
                    }
                    if (k2 >= 1 && l2 == position.getY() + j + 1) {
                        --k2;
                    } else if (k2 < l) {
                        ++k2;
                    }
                    --l2;
                }
                int i3 = 0;
                while (i3 < i - 1) {
                    Block block1 = worldIn.getBlockState(position.up(i3)).getBlock();
                    if (block1.getMaterial() == Material.air || block1.getMaterial() == Material.leaves) {
                        this.setBlockAndNotifyAdequately(worldIn, position.up(i3), field_181636_a);
                    }
                    ++i3;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

