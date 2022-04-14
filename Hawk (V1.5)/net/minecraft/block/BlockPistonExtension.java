package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends Block {
   public static final PropertyDirection field_176326_a = PropertyDirection.create("facing");
   public static final PropertyEnum field_176325_b = PropertyEnum.create("type", BlockPistonExtension.EnumPistonType.class);
   private static final String __OBFID = "CL_00000367";
   public static final PropertyBool field_176327_M = PropertyBool.create("short");

   private void func_176323_e(IBlockState var1) {
      float var2 = 0.25F;
      float var3 = 0.375F;
      float var4 = 0.625F;
      float var5 = 0.25F;
      float var6 = 0.75F;
      switch((EnumFacing)var1.getValue(field_176326_a)) {
      case DOWN:
         this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
         break;
      case UP:
         this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
         break;
      case NORTH:
         this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
         break;
      case SOUTH:
         this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
         break;
      case WEST:
         this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
         break;
      case EAST:
         this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(field_176326_a);
      BlockPos var6 = var2.offset(var5.getOpposite());
      IBlockState var7 = var1.getBlockState(var6);
      if (var7.getBlock() != Blocks.piston && var7.getBlock() != Blocks.sticky_piston) {
         var1.setBlockToAir(var2);
      } else {
         var7.getBlock().onNeighborBlockChange(var1, var6, var7, var4);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      EnumFacing var4 = ((EnumFacing)var3.getValue(field_176326_a)).getOpposite();
      var2 = var2.offset(var4);
      IBlockState var5 = var1.getBlockState(var2);
      if ((var5.getBlock() == Blocks.piston || var5.getBlock() == Blocks.sticky_piston) && (Boolean)var5.getValue(BlockPistonBase.EXTENDED)) {
         var5.getBlock().dropBlockAsItem(var1, var2, var5, 0);
         var1.setBlockToAir(var2);
      }

   }

   public BlockPistonExtension() {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176326_a, EnumFacing.NORTH).withProperty(field_176325_b, BlockPistonExtension.EnumPistonType.DEFAULT).withProperty(field_176327_M, false));
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
   }

   public static EnumFacing func_176322_b(int var0) {
      int var1 = var0 & 7;
      return var1 > 5 ? null : EnumFacing.getFront(var1);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return false;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.func_176324_d(var1.getBlockState(var2));
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }

   public Item getItem(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getValue(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.func_176324_d(var3);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.func_176323_e(var3);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.capabilities.isCreativeMode) {
         EnumFacing var5 = (EnumFacing)var3.getValue(field_176326_a);
         if (var5 != null) {
            BlockPos var6 = var2.offset(var5.getOpposite());
            Block var7 = var1.getBlockState(var6).getBlock();
            if (var7 == Blocks.piston || var7 == Blocks.sticky_piston) {
               var1.setBlockToAir(var6);
            }
         }
      }

      super.onBlockHarvested(var1, var2, var3, var4);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176326_a, func_176322_b(var1)).withProperty(field_176325_b, (var1 & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176326_a, field_176325_b, field_176327_M});
   }

   public void func_176324_d(IBlockState var1) {
      float var2 = 0.25F;
      EnumFacing var3 = (EnumFacing)var1.getValue(field_176326_a);
      if (var3 != null) {
         switch(var3) {
         case DOWN:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
            break;
         case UP:
            this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case NORTH:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
            break;
         case SOUTH:
            this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
            break;
         case WEST:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
            break;
         case EAST:
            this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         }
      }

   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176326_a)).getIndex();
      if (var1.getValue(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY) {
         var3 |= 8;
      }

      return var3;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public static enum EnumPistonType implements IStringSerializable {
      private static final BlockPistonExtension.EnumPistonType[] $VALUES = new BlockPistonExtension.EnumPistonType[]{DEFAULT, STICKY};
      STICKY("STICKY", 1, "sticky");

      private static final String __OBFID = "CL_00002035";
      private final String field_176714_c;
      private static final BlockPistonExtension.EnumPistonType[] ENUM$VALUES = new BlockPistonExtension.EnumPistonType[]{DEFAULT, STICKY};
      DEFAULT("DEFAULT", 0, "normal");

      private EnumPistonType(String var3, int var4, String var5) {
         this.field_176714_c = var5;
      }

      public String getName() {
         return this.field_176714_c;
      }

      public String toString() {
         return this.field_176714_c;
      }
   }

   static final class SwitchEnumFacing {
      static final int[] field_177247_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002036";

      static {
         try {
            field_177247_a[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_177247_a[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177247_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177247_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177247_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177247_a[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
