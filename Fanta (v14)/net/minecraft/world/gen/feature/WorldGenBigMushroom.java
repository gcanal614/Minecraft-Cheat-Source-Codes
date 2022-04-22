/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBigMushroom
extends WorldGenerator {
    private Block mushroomType;

    public WorldGenBigMushroom(Block p_i46449_1_) {
        super(true);
        this.mushroomType = p_i46449_1_;
    }

    public WorldGenBigMushroom() {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        block38: {
            if (this.mushroomType == null) {
                this.mushroomType = rand.nextBoolean() ? Blocks.brown_mushroom_block : Blocks.red_mushroom_block;
            }
            int i = rand.nextInt(3) + 4;
            boolean flag = true;
            if (position.getY() < 1 || position.getY() + i + 1 >= 256) break block38;
            int j = position.getY();
            while (j <= position.getY() + 1 + i) {
                int k = 3;
                if (j <= position.getY() + 3) {
                    k = 0;
                }
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
                int l = position.getX() - k;
                while (l <= position.getX() + k && flag) {
                    int i1 = position.getZ() - k;
                    while (i1 <= position.getZ() + k && flag) {
                        if (j >= 0 && j < 256) {
                            Block block = worldIn.getBlockState(blockpos$mutableblockpos.func_181079_c(l, j, i1)).getBlock();
                            if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                        ++i1;
                    }
                    ++l;
                }
                ++j;
            }
            if (!flag) {
                return false;
            }
            Block block1 = worldIn.getBlockState(position.down()).getBlock();
            if (block1 != Blocks.dirt && block1 != Blocks.grass && block1 != Blocks.mycelium) {
                return false;
            }
            int k2 = position.getY() + i;
            if (this.mushroomType == Blocks.red_mushroom_block) {
                k2 = position.getY() + i - 3;
            }
            int l2 = k2;
            while (l2 <= position.getY() + i) {
                int j3 = 1;
                if (l2 < position.getY() + i) {
                    ++j3;
                }
                if (this.mushroomType == Blocks.brown_mushroom_block) {
                    j3 = 3;
                }
                int k3 = position.getX() - j3;
                int l3 = position.getX() + j3;
                int j1 = position.getZ() - j3;
                int k1 = position.getZ() + j3;
                int l1 = k3;
                while (l1 <= l3) {
                    int i2 = j1;
                    while (i2 <= k1) {
                        block40: {
                            BlockPos blockpos;
                            BlockHugeMushroom.EnumType blockhugemushroom$enumtype;
                            block39: {
                                int j2 = 5;
                                if (l1 == k3) {
                                    --j2;
                                } else if (l1 == l3) {
                                    ++j2;
                                }
                                if (i2 == j1) {
                                    j2 -= 3;
                                } else if (i2 == k1) {
                                    j2 += 3;
                                }
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(j2);
                                if (this.mushroomType != Blocks.brown_mushroom_block && l2 >= position.getY() + i) break block39;
                                if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) break block40;
                                if (l1 == position.getX() - (j3 - 1) && i2 == j1) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                                }
                                if (l1 == k3 && i2 == position.getZ() - (j3 - 1)) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                                }
                                if (l1 == position.getX() + (j3 - 1) && i2 == j1) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                                }
                                if (l1 == l3 && i2 == position.getZ() - (j3 - 1)) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                                }
                                if (l1 == position.getX() - (j3 - 1) && i2 == k1) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                }
                                if (l1 == k3 && i2 == position.getZ() + (j3 - 1)) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                }
                                if (l1 == position.getX() + (j3 - 1) && i2 == k1) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                }
                                if (l1 == l3 && i2 == position.getZ() + (j3 - 1)) {
                                    blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                }
                            }
                            if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY() + i) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
                            }
                            if (!(position.getY() < position.getY() + i - 1 && blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.ALL_INSIDE || worldIn.getBlockState(blockpos = new BlockPos(l1, l2, i2)).getBlock().isFullBlock())) {
                                this.setBlockAndNotifyAdequately(worldIn, blockpos, this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, blockhugemushroom$enumtype));
                            }
                        }
                        ++i2;
                    }
                    ++l1;
                }
                ++l2;
            }
            int i3 = 0;
            while (i3 < i) {
                Block block2 = worldIn.getBlockState(position.up(i3)).getBlock();
                if (!block2.isFullBlock()) {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(i3), this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM));
                }
                ++i3;
            }
            return true;
        }
        return false;
    }
}

