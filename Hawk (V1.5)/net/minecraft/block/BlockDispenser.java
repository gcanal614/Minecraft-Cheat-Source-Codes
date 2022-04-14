package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer {
   public static final RegistryDefaulted dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
   private static final String __OBFID = "CL_00000229";
   protected Random rand = new Random();
   public static final PropertyDirection FACING = PropertyDirection.create("facing");

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote) {
         this.func_176439_d(var1, var2);
      }

   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityDispenser();
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(FACING, BlockPistonBase.func_180695_a(var1, var2, var4)), 2);
      if (var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if (var6 instanceof TileEntityDispenser) {
            ((TileEntityDispenser)var6).func_146018_a(var5.getDisplayName());
         }
      }

   }

   private void setDefaultDirection(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
         boolean var5 = var1.getBlockState(var2.offsetNorth()).getBlock().isFullBlock();
         boolean var6 = var1.getBlockState(var2.offsetSouth()).getBlock().isFullBlock();
         if (var4 == EnumFacing.NORTH && var5 && !var6) {
            var4 = EnumFacing.SOUTH;
         } else if (var4 == EnumFacing.SOUTH && var6 && !var5) {
            var4 = EnumFacing.NORTH;
         } else {
            boolean var7 = var1.getBlockState(var2.offsetWest()).getBlock().isFullBlock();
            boolean var8 = var1.getBlockState(var2.offsetEast()).getBlock().isFullBlock();
            if (var4 == EnumFacing.WEST && var7 && !var8) {
               var4 = EnumFacing.EAST;
            } else if (var4 == EnumFacing.EAST && var8 && !var7) {
               var4 = EnumFacing.WEST;
            }
         }

         var1.setBlockState(var2, var3.withProperty(FACING, var4).withProperty(TRIGGERED, false), 2);
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         TileEntity var9 = var1.getTileEntity(var2);
         if (var9 instanceof TileEntityDispenser) {
            var4.displayGUIChest((TileEntityDispenser)var9);
         }

         return true;
      }
   }

   public int getRenderType() {
      return 3;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstoneFromInventory(var1.getTileEntity(var2));
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      this.setDefaultDirection(var1, var2, var3);
   }

   public static EnumFacing getFacing(int var0) {
      return EnumFacing.getFront(var0 & 7);
   }

   protected BlockDispenser() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, false));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   protected void func_176439_d(World var1, BlockPos var2) {
      BlockSourceImpl var3 = new BlockSourceImpl(var1, var2);
      TileEntityDispenser var4 = (TileEntityDispenser)var3.getBlockTileEntity();
      if (var4 != null) {
         int var5 = var4.func_146017_i();
         if (var5 < 0) {
            var1.playAuxSFX(1001, var2, 0);
         } else {
            ItemStack var6 = var4.getStackInSlot(var5);
            IBehaviorDispenseItem var7 = this.func_149940_a(var6);
            if (var7 != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
               ItemStack var8 = var7.dispense(var3, var6);
               var4.setInventorySlotContents(var5, var8.stackSize == 0 ? null : var8);
            }
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof TileEntityDispenser) {
         InventoryHelper.dropInventoryItems(var1, var2, (TileEntityDispenser)var4);
         var1.updateComparatorOutputLevel(var2, this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, BlockPistonBase.func_180695_a(var1, var2, var8)).withProperty(TRIGGERED, false);
   }

   public int tickRate(World var1) {
      return 4;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var1.isBlockPowered(var2) || var1.isBlockPowered(var2.offsetUp());
      boolean var6 = (Boolean)var3.getValue(TRIGGERED);
      if (var5 && !var6) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
         var1.setBlockState(var2, var3.withProperty(TRIGGERED, true), 4);
      } else if (!var5 && var6) {
         var1.setBlockState(var2, var3.withProperty(TRIGGERED, false), 4);
      }

   }

   protected IBehaviorDispenseItem func_149940_a(ItemStack var1) {
      return (IBehaviorDispenseItem)dispenseBehaviorRegistry.getObject(var1 == null ? null : var1.getItem());
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, TRIGGERED});
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, getFacing(var1)).withProperty(TRIGGERED, (var1 & 8) > 0);
   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
   }

   public static IPosition getDispensePosition(IBlockSource var0) {
      EnumFacing var1 = getFacing(var0.getBlockMetadata());
      double var2 = var0.getX() + 0.7D * (double)var1.getFrontOffsetX();
      double var4 = var0.getY() + 0.7D * (double)var1.getFrontOffsetY();
      double var6 = var0.getZ() + 0.7D * (double)var1.getFrontOffsetZ();
      return new PositionImpl(var2, var4, var6);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if ((Boolean)var1.getValue(TRIGGERED)) {
         var3 |= 8;
      }

      return var3;
   }
}
