package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSand extends WorldGenerator {
   private int radius;
   private static final String __OBFID = "CL_00000431";
   private Block field_150517_a;

   public boolean generate(World var1, Random var2, BlockPos var3) {
      if (var1.getBlockState(var3).getBlock().getMaterial() != Material.water) {
         return false;
      } else {
         int var4 = var2.nextInt(this.radius - 2) + 2;
         byte var5 = 2;

         for(int var6 = var3.getX() - var4; var6 <= var3.getX() + var4; ++var6) {
            for(int var7 = var3.getZ() - var4; var7 <= var3.getZ() + var4; ++var7) {
               int var8 = var6 - var3.getX();
               int var9 = var7 - var3.getZ();
               if (var8 * var8 + var9 * var9 <= var4 * var4) {
                  for(int var10 = var3.getY() - var5; var10 <= var3.getY() + var5; ++var10) {
                     BlockPos var11 = new BlockPos(var6, var10, var7);
                     Block var12 = var1.getBlockState(var11).getBlock();
                     if (var12 == Blocks.dirt || var12 == Blocks.grass) {
                        var1.setBlockState(var11, this.field_150517_a.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   public WorldGenSand(Block var1, int var2) {
      this.field_150517_a = var1;
      this.radius = var2;
   }
}
