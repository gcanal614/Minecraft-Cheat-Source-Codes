package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
   private static final String __OBFID = "CL_00000256";

   public BlockHay() {
      super(Material.grass);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176298_M, EnumFacing.Axis.Y));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      EnumFacing.Axis var3 = (EnumFacing.Axis)var1.getValue(field_176298_M);
      if (var3 == EnumFacing.Axis.X) {
         var2 |= 4;
      } else if (var3 == EnumFacing.Axis.Z) {
         var2 |= 8;
      }

      return var2;
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      return new ItemStack(Item.getItemFromBlock(this), 1, 0);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176298_M});
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing.Axis var2 = EnumFacing.Axis.Y;
      int var3 = var1 & 12;
      if (var3 == 4) {
         var2 = EnumFacing.Axis.X;
      } else if (var3 == 8) {
         var2 = EnumFacing.Axis.Z;
      }

      return this.getDefaultState().withProperty(field_176298_M, var2);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8).withProperty(field_176298_M, var3.getAxis());
   }
}
