/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkull
extends BlockContainer {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool NODROP = PropertyBool.create("nodrop");
    private static final Predicate<BlockWorldState> IS_WITHER_SKELETON = new Predicate<BlockWorldState>(){

        @Override
        public boolean apply(BlockWorldState p_apply_1_) {
            return p_apply_1_.getBlockState() != null && p_apply_1_.getBlockState().getBlock() == Blocks.skull && p_apply_1_.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)p_apply_1_.getTileEntity()).getSkullType() == 1;
        }
    };
    private BlockPattern witherBasePattern;
    private BlockPattern witherPattern;

    protected BlockSkull() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(NODROP, false));
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("tile.skull.skeleton.name");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_setBlockBoundsBasedOnState_1_, BlockPos p_setBlockBoundsBasedOnState_2_) {
        switch (p_setBlockBoundsBasedOnState_1_.getBlockState(p_setBlockBoundsBasedOnState_2_).getValue(FACING)) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case NORTH: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case SOUTH: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case WEST: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case EAST: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World p_getCollisionBoundingBox_1_, BlockPos p_getCollisionBoundingBox_2_, IBlockState p_getCollisionBoundingBox_3_) {
        this.setBlockBoundsBasedOnState(p_getCollisionBoundingBox_1_, p_getCollisionBoundingBox_2_);
        return super.getCollisionBoundingBox(p_getCollisionBoundingBox_1_, p_getCollisionBoundingBox_2_, p_getCollisionBoundingBox_3_);
    }

    @Override
    public IBlockState onBlockPlaced(World p_onBlockPlaced_1_, BlockPos p_onBlockPlaced_2_, EnumFacing p_onBlockPlaced_3_, float p_onBlockPlaced_4_, float p_onBlockPlaced_5_, float p_onBlockPlaced_6_, int p_onBlockPlaced_7_, EntityLivingBase p_onBlockPlaced_8_) {
        return this.getDefaultState().withProperty(FACING, p_onBlockPlaced_8_.getHorizontalFacing()).withProperty(NODROP, false);
    }

    @Override
    public TileEntity createNewTileEntity(World p_createNewTileEntity_1_, int p_createNewTileEntity_2_) {
        return new TileEntitySkull();
    }

    @Override
    public Item getItem(World p_getItem_1_, BlockPos p_getItem_2_) {
        return Items.skull;
    }

    @Override
    public int getDamageValue(World p_getDamageValue_1_, BlockPos p_getDamageValue_2_) {
        TileEntity tileentity = p_getDamageValue_1_.getTileEntity(p_getDamageValue_2_);
        return tileentity instanceof TileEntitySkull ? ((TileEntitySkull)tileentity).getSkullType() : super.getDamageValue(p_getDamageValue_1_, p_getDamageValue_2_);
    }

    @Override
    public void onBlockHarvested(World p_onBlockHarvested_1_, BlockPos p_onBlockHarvested_2_, IBlockState p_onBlockHarvested_3_, EntityPlayer p_onBlockHarvested_4_) {
        if (p_onBlockHarvested_4_.capabilities.isCreativeMode) {
            p_onBlockHarvested_3_ = p_onBlockHarvested_3_.withProperty(NODROP, true);
            p_onBlockHarvested_1_.setBlockState(p_onBlockHarvested_2_, p_onBlockHarvested_3_, 4);
        }
        this.dropBlockAsItem(p_onBlockHarvested_1_, p_onBlockHarvested_2_, p_onBlockHarvested_3_, 0);
        super.onBlockHarvested(p_onBlockHarvested_1_, p_onBlockHarvested_2_, p_onBlockHarvested_3_, p_onBlockHarvested_4_);
    }

    @Override
    public void breakBlock(World p_breakBlock_1_, BlockPos p_breakBlock_2_, IBlockState p_breakBlock_3_) {
        super.breakBlock(p_breakBlock_1_, p_breakBlock_2_, p_breakBlock_3_);
    }

    public List<ItemStack> getDrops(IBlockAccess p_getDrops_1_, BlockPos p_getDrops_2_, IBlockState p_getDrops_3_, int p_getDrops_4_) {
        TileEntity tileentity;
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (!p_getDrops_3_.getValue(NODROP).booleanValue() && (tileentity = p_getDrops_1_.getTileEntity(p_getDrops_2_)) instanceof TileEntitySkull) {
            TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
            ItemStack itemstack = new ItemStack(Items.skull, 1, tileentityskull.getSkullType());
            if (tileentityskull.getSkullType() == 3 && tileentityskull.getPlayerProfile() != null) {
                itemstack.setTagCompound(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
                itemstack.getTagCompound().setTag("SkullOwner", nbttagcompound);
            }
            ret.add(itemstack);
        }
        return ret;
    }

    @Override
    public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_) {
        return Items.skull;
    }

    public boolean canDispenserPlace(World p_canDispenserPlace_1_, BlockPos p_canDispenserPlace_2_, ItemStack p_canDispenserPlace_3_) {
        return p_canDispenserPlace_3_.getMetadata() == 1 && p_canDispenserPlace_2_.getY() >= 2 && p_canDispenserPlace_1_.getDifficulty() != EnumDifficulty.PEACEFUL && !p_canDispenserPlace_1_.isRemote ? this.getWitherBasePattern().match(p_canDispenserPlace_1_, p_canDispenserPlace_2_) != null : false;
    }

    public void checkWitherSpawn(World p_checkWitherSpawn_1_, BlockPos p_checkWitherSpawn_2_, TileEntitySkull p_checkWitherSpawn_3_) {
        BlockPattern blockpattern;
        BlockPattern.PatternHelper blockpattern$patternhelper;
        if (p_checkWitherSpawn_3_.getSkullType() == 1 && p_checkWitherSpawn_2_.getY() >= 2 && p_checkWitherSpawn_1_.getDifficulty() != EnumDifficulty.PEACEFUL && !p_checkWitherSpawn_1_.isRemote && (blockpattern$patternhelper = (blockpattern = this.getWitherPattern()).match(p_checkWitherSpawn_1_, p_checkWitherSpawn_2_)) != null) {
            int i1;
            int j;
            for (j = 0; j < 3; ++j) {
                BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(j, 0, 0);
                p_checkWitherSpawn_1_.setBlockState(blockworldstate.getPos(), blockworldstate.getBlockState().withProperty(NODROP, true), 2);
            }
            for (j = 0; j < blockpattern.getPalmLength(); ++j) {
                for (int k = 0; k < blockpattern.getThumbLength(); ++k) {
                    BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(j, k, 0);
                    p_checkWitherSpawn_1_.setBlockState(blockworldstate1.getPos(), Blocks.air.getDefaultState(), 2);
                }
            }
            BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 0, 0).getPos();
            EntityWither entitywither = new EntityWither(p_checkWitherSpawn_1_);
            BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
            entitywither.setLocationAndAngles((double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.55, (double)blockpos1.getZ() + 0.5, blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0f : 90.0f, 0.0f);
            entitywither.renderYawOffset = blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0f : 90.0f;
            entitywither.func_82206_m();
            for (EntityPlayer entityplayer : p_checkWitherSpawn_1_.getEntitiesWithinAABB(EntityPlayer.class, entitywither.getEntityBoundingBox().expand(50.0, 50.0, 50.0))) {
                entityplayer.triggerAchievement(AchievementList.spawnWither);
            }
            p_checkWitherSpawn_1_.spawnEntityInWorld(entitywither);
            for (i1 = 0; i1 < 120; ++i1) {
                p_checkWitherSpawn_1_.spawnParticle(EnumParticleTypes.SNOWBALL, (double)blockpos.getX() + p_checkWitherSpawn_1_.rand.nextDouble(), (double)(blockpos.getY() - 2) + p_checkWitherSpawn_1_.rand.nextDouble() * 3.9, (double)blockpos.getZ() + p_checkWitherSpawn_1_.rand.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
            for (i1 = 0; i1 < blockpattern.getPalmLength(); ++i1) {
                for (int j1 = 0; j1 < blockpattern.getThumbLength(); ++j1) {
                    BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(i1, j1, 0);
                    p_checkWitherSpawn_1_.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.air);
                }
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(p_getStateFromMeta_1_ & 7)).withProperty(NODROP, (p_getStateFromMeta_1_ & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        int i = 0;
        i |= p_getMetaFromState_1_.getValue(FACING).getIndex();
        if (p_getMetaFromState_1_.getValue(NODROP).booleanValue()) {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING, NODROP);
    }

    protected BlockPattern getWitherBasePattern() {
        if (this.witherBasePattern == null) {
            this.witherBasePattern = FactoryBlockPattern.start().aisle("   ", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherBasePattern;
    }

    protected BlockPattern getWitherPattern() {
        if (this.witherPattern == null) {
            this.witherPattern = FactoryBlockPattern.start().aisle("^^^", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', IS_WITHER_SKELETON).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherPattern;
    }
}

