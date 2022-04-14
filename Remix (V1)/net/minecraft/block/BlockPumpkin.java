package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.state.pattern.*;
import com.google.common.base.*;

public class BlockPumpkin extends BlockDirectional
{
    private BlockPattern field_176394_a;
    private BlockPattern field_176393_b;
    private BlockPattern field_176395_M;
    private BlockPattern field_176396_O;
    
    protected BlockPumpkin() {
        super(Material.gourd);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPumpkin.AGE, EnumFacing.NORTH));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        this.createGolem(worldIn, pos);
    }
    
    public boolean func_176390_d(final World worldIn, final BlockPos p_176390_2_) {
        return this.func_176392_j().func_177681_a(worldIn, p_176390_2_) != null || this.func_176389_S().func_177681_a(worldIn, p_176390_2_) != null;
    }
    
    private void createGolem(final World worldIn, final BlockPos p_180673_2_) {
        BlockPattern.PatternHelper var3;
        if ((var3 = this.func_176391_l().func_177681_a(worldIn, p_180673_2_)) != null) {
            for (int var4 = 0; var4 < this.func_176391_l().func_177685_b(); ++var4) {
                final BlockWorldState var5 = var3.func_177670_a(0, var4, 0);
                worldIn.setBlockState(var5.getPos(), Blocks.air.getDefaultState(), 2);
            }
            final EntitySnowman var6 = new EntitySnowman(worldIn);
            final BlockPos var7 = var3.func_177670_a(0, 2, 0).getPos();
            var6.setLocationAndAngles(var7.getX() + 0.5, var7.getY() + 0.05, var7.getZ() + 0.5, 0.0f, 0.0f);
            worldIn.spawnEntityInWorld(var6);
            for (int var8 = 0; var8 < 120; ++var8) {
                worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, var7.getX() + worldIn.rand.nextDouble(), var7.getY() + worldIn.rand.nextDouble() * 2.5, var7.getZ() + worldIn.rand.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
            for (int var8 = 0; var8 < this.func_176391_l().func_177685_b(); ++var8) {
                final BlockWorldState var9 = var3.func_177670_a(0, var8, 0);
                worldIn.func_175722_b(var9.getPos(), Blocks.air);
            }
        }
        else if ((var3 = this.func_176388_T().func_177681_a(worldIn, p_180673_2_)) != null) {
            for (int var4 = 0; var4 < this.func_176388_T().func_177684_c(); ++var4) {
                for (int var10 = 0; var10 < this.func_176388_T().func_177685_b(); ++var10) {
                    worldIn.setBlockState(var3.func_177670_a(var4, var10, 0).getPos(), Blocks.air.getDefaultState(), 2);
                }
            }
            final BlockPos var11 = var3.func_177670_a(1, 2, 0).getPos();
            final EntityIronGolem var12 = new EntityIronGolem(worldIn);
            var12.setPlayerCreated(true);
            var12.setLocationAndAngles(var11.getX() + 0.5, var11.getY() + 0.05, var11.getZ() + 0.5, 0.0f, 0.0f);
            worldIn.spawnEntityInWorld(var12);
            for (int var8 = 0; var8 < 120; ++var8) {
                worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, var11.getX() + worldIn.rand.nextDouble(), var11.getY() + worldIn.rand.nextDouble() * 3.9, var11.getZ() + worldIn.rand.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
            for (int var8 = 0; var8 < this.func_176388_T().func_177684_c(); ++var8) {
                for (int var13 = 0; var13 < this.func_176388_T().func_177685_b(); ++var13) {
                    final BlockWorldState var14 = var3.func_177670_a(var8, var13, 0);
                    worldIn.func_175722_b(var14.getPos(), Blocks.air);
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return worldIn.getBlockState(pos).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown());
    }
    
    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.getDefaultState().withProperty(BlockPumpkin.AGE, placer.func_174811_aO().getOpposite());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockPumpkin.AGE, EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((EnumFacing)state.getValue(BlockPumpkin.AGE)).getHorizontalIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPumpkin.AGE });
    }
    
    protected BlockPattern func_176392_j() {
        if (this.field_176394_a == null) {
            this.field_176394_a = FactoryBlockPattern.start().aisle(" ", "#", "#").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.field_176394_a;
    }
    
    protected BlockPattern func_176391_l() {
        if (this.field_176393_b == null) {
            this.field_176393_b = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.field_176393_b;
    }
    
    protected BlockPattern func_176389_S() {
        if (this.field_176395_M == null) {
            this.field_176395_M = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176395_M;
    }
    
    protected BlockPattern func_176388_T() {
        if (this.field_176396_O == null) {
            this.field_176396_O = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176396_O;
    }
}
