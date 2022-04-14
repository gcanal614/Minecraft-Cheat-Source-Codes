package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnderChest extends BlockContainer {
   public static final PropertyDirection field_176437_a;
   private static final String __OBFID = "CL_00000238";

   protected boolean canSilkHarvest() {
      return true;
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(field_176437_a)).getIndex();
   }

   static {
      field_176437_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }

   protected BlockEnderChest() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176437_a, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      InventoryEnderChest var9 = var4.getInventoryEnderChest();
      TileEntity var10 = var1.getTileEntity(var2);
      if (var9 != null && var10 instanceof TileEntityEnderChest) {
         if (var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube()) {
            return true;
         } else if (var1.isRemote) {
            return true;
         } else {
            var9.setChestTileEntity((TileEntityEnderChest)var10);
            var4.displayGUIChest(var9);
            return true;
         }
      } else {
         return true;
      }
   }

   public boolean isFullCube() {
      return false;
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if (var2.getAxis() == EnumFacing.Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(field_176437_a, var2);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.obsidian);
   }

   public int getRenderType() {
      return 2;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(field_176437_a, var8.func_174811_aO().getOpposite());
   }

   public int quantityDropped(Random var1) {
      return 8;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      for(int var5 = 0; var5 < 3; ++var5) {
         int var6 = var4.nextInt(2) * 2 - 1;
         int var7 = var4.nextInt(2) * 2 - 1;
         double var8 = (double)var2.getX() + 0.5D + 0.25D * (double)var6;
         double var10 = (double)((float)var2.getY() + var4.nextFloat());
         double var12 = (double)var2.getZ() + 0.5D + 0.25D * (double)var7;
         double var14 = (double)(var4.nextFloat() * (float)var6);
         double var16 = ((double)var4.nextFloat() - 0.5D) * 0.125D;
         double var18 = (double)(var4.nextFloat() * (float)var7);
         var1.spawnParticle(EnumParticleTypes.PORTAL, var8, var10, var12, var14, var16, var18);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176437_a});
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(field_176437_a, var4.func_174811_aO().getOpposite()), 2);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityEnderChest();
   }
}
