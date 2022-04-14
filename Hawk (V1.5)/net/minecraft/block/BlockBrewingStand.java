package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockBrewingStand extends BlockContainer {
   public static final PropertyBool[] BOTTLE_PROPS = new PropertyBool[]{PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2")};
   private final Random rand = new Random();
   private static final String __OBFID = "CL_00000207";

   public boolean isFullCube() {
      return false;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityBrewingStand();
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.brewing_stand;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{BOTTLE_PROPS[0], BOTTLE_PROPS[1], BOTTLE_PROPS[2]});
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         if ((Boolean)var1.getValue(BOTTLE_PROPS[var3])) {
            var2 |= 1 << var3;
         }
      }

      return var2;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if (var6 instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)var6).func_145937_a(var5.getDisplayName());
         }
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState();

      for(int var3 = 0; var3 < 3; ++var3) {
         var2 = var2.withProperty(BOTTLE_PROPS[var3], (var1 & 1 << var3) > 0);
      }

      return var2;
   }

   public BlockBrewingStand() {
      super(Material.iron);
      this.setDefaultState(this.blockState.getBaseState().withProperty(BOTTLE_PROPS[0], false).withProperty(BOTTLE_PROPS[1], false).withProperty(BOTTLE_PROPS[2], false));
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         TileEntity var9 = var1.getTileEntity(var2);
         if (var9 instanceof TileEntityBrewingStand) {
            var4.displayGUIChest((TileEntityBrewingStand)var9);
         }

         return true;
      }
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstoneFromInventory(var1.getTileEntity(var2));
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBoundsForItemRender();
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.brewing_stand;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof TileEntityBrewingStand) {
         InventoryHelper.dropInventoryItems(var1, var2, (TileEntityBrewingStand)var4);
      }

      super.breakBlock(var1, var2, var3);
   }

   public int getRenderType() {
      return 3;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = (double)((float)var2.getX() + 0.4F + var4.nextFloat() * 0.2F);
      double var7 = (double)((float)var2.getY() + 0.7F + var4.nextFloat() * 0.3F);
      double var9 = (double)((float)var2.getZ() + 0.4F + var4.nextFloat() * 0.2F);
      var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var5, var7, var9, 0.0D, 0.0D, 0.0D);
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }
}
