package net.minecraft.world.gen.feature;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class WorldGeneratorBonusChest extends WorldGenerator {
   private static final String __OBFID = "CL_00000403";
   private final int itemsToGenerateInBonusChest;
   private final List field_175909_a;

   public WorldGeneratorBonusChest(List var1, int var2) {
      this.field_175909_a = var1;
      this.itemsToGenerateInBonusChest = var2;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      Block var4;
      while(((var4 = var1.getBlockState(var3).getBlock()).getMaterial() == Material.air || var4.getMaterial() == Material.leaves) && var3.getY() > 1) {
         var3 = var3.offsetDown();
      }

      if (var3.getY() < 1) {
         return false;
      } else {
         var3 = var3.offsetUp();

         for(int var5 = 0; var5 < 4; ++var5) {
            BlockPos var6 = var3.add(var2.nextInt(4) - var2.nextInt(4), var2.nextInt(3) - var2.nextInt(3), var2.nextInt(4) - var2.nextInt(4));
            if (var1.isAirBlock(var6) && World.doesBlockHaveSolidTopSurface(var1, var6.offsetDown())) {
               var1.setBlockState(var6, Blocks.chest.getDefaultState(), 2);
               TileEntity var7 = var1.getTileEntity(var6);
               if (var7 instanceof TileEntityChest) {
                  WeightedRandomChestContent.generateChestContents(var2, this.field_175909_a, (TileEntityChest)var7, this.itemsToGenerateInBonusChest);
               }

               BlockPos var8 = var6.offsetEast();
               BlockPos var9 = var6.offsetWest();
               BlockPos var10 = var6.offsetNorth();
               BlockPos var11 = var6.offsetSouth();
               if (var1.isAirBlock(var9) && World.doesBlockHaveSolidTopSurface(var1, var9.offsetDown())) {
                  var1.setBlockState(var9, Blocks.torch.getDefaultState(), 2);
               }

               if (var1.isAirBlock(var8) && World.doesBlockHaveSolidTopSurface(var1, var8.offsetDown())) {
                  var1.setBlockState(var8, Blocks.torch.getDefaultState(), 2);
               }

               if (var1.isAirBlock(var10) && World.doesBlockHaveSolidTopSurface(var1, var10.offsetDown())) {
                  var1.setBlockState(var10, Blocks.torch.getDefaultState(), 2);
               }

               if (var1.isAirBlock(var11) && World.doesBlockHaveSolidTopSurface(var1, var11.offsetDown())) {
                  var1.setBlockState(var11, Blocks.torch.getDefaultState(), 2);
               }

               return true;
            }
         }

         return false;
      }
   }
}
