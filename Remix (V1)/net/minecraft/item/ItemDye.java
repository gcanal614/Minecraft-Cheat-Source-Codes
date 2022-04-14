package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.util.*;

public class ItemDye extends Item
{
    public static final int[] dyeColors;
    
    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    public static boolean func_179234_a(final ItemStack p_179234_0_, final World worldIn, final BlockPos p_179234_2_) {
        final IBlockState var3 = worldIn.getBlockState(p_179234_2_);
        if (var3.getBlock() instanceof IGrowable) {
            final IGrowable var4 = (IGrowable)var3.getBlock();
            if (var4.isStillGrowing(worldIn, p_179234_2_, var3, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (var4.canUseBonemeal(worldIn, worldIn.rand, p_179234_2_, var3)) {
                        var4.grow(worldIn, worldIn.rand, p_179234_2_, var3);
                    }
                    --p_179234_0_.stackSize;
                }
                return true;
            }
        }
        return false;
    }
    
    public static void func_180617_a(final World worldIn, final BlockPos p_180617_1_, int p_180617_2_) {
        if (p_180617_2_ == 0) {
            p_180617_2_ = 15;
        }
        final Block var3 = worldIn.getBlockState(p_180617_1_).getBlock();
        if (var3.getMaterial() != Material.air) {
            var3.setBlockBoundsBasedOnState(worldIn, p_180617_1_);
            for (int var4 = 0; var4 < p_180617_2_; ++var4) {
                final double var5 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var6 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var7 = ItemDye.itemRand.nextGaussian() * 0.02;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, p_180617_1_.getX() + ItemDye.itemRand.nextFloat(), p_180617_1_.getY() + ItemDye.itemRand.nextFloat() * var3.getBlockBoundsMaxY(), p_180617_1_.getZ() + ItemDye.itemRand.nextFloat(), var5, var6, var7, new int[0]);
            }
        }
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        final int var2 = stack.getMetadata();
        return super.getUnlocalizedName() + "." + EnumDyeColor.func_176766_a(var2).func_176762_d();
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
            return false;
        }
        final EnumDyeColor var9 = EnumDyeColor.func_176766_a(stack.getMetadata());
        if (var9 == EnumDyeColor.WHITE) {
            if (func_179234_a(stack, worldIn, pos)) {
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, pos, 0);
                }
                return true;
            }
        }
        else if (var9 == EnumDyeColor.BROWN) {
            final IBlockState var10 = worldIn.getBlockState(pos);
            final Block var11 = var10.getBlock();
            if (var11 == Blocks.log && var10.getValue(BlockPlanks.VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE) {
                if (side == EnumFacing.DOWN) {
                    return false;
                }
                if (side == EnumFacing.UP) {
                    return false;
                }
                pos = pos.offset(side);
                if (worldIn.isAirBlock(pos)) {
                    final IBlockState var12 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, playerIn);
                    worldIn.setBlockState(pos, var12, 2);
                    if (!playerIn.capabilities.isCreativeMode) {
                        --stack.stackSize;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (target instanceof EntitySheep) {
            final EntitySheep var4 = (EntitySheep)target;
            final EnumDyeColor var5 = EnumDyeColor.func_176766_a(stack.getMetadata());
            if (!var4.getSheared() && var4.func_175509_cj() != var5) {
                var4.func_175512_b(var5);
                --stack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
        for (int var4 = 0; var4 < 16; ++var4) {
            subItems.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    static {
        dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
}
