package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class FlatLayerInfo {
   private IBlockState field_175901_b;
   private int layerCount;
   private final int field_175902_a;
   private int layerMinimumY;
   private static final String __OBFID = "CL_00000441";

   private int getFillBlockMeta() {
      return this.field_175901_b.getBlock().getMetaFromState(this.field_175901_b);
   }

   public IBlockState func_175900_c() {
      return this.field_175901_b;
   }

   public FlatLayerInfo(int var1, int var2, Block var3, int var4) {
      this(var1, var2, var3);
      this.field_175901_b = var3.getStateFromMeta(var4);
   }

   public void setMinY(int var1) {
      this.layerMinimumY = var1;
   }

   private Block func_151536_b() {
      return this.field_175901_b.getBlock();
   }

   public int getMinY() {
      return this.layerMinimumY;
   }

   public FlatLayerInfo(int var1, int var2, Block var3) {
      this.layerCount = 1;
      this.field_175902_a = var1;
      this.layerCount = var2;
      this.field_175901_b = var3.getDefaultState();
   }

   public FlatLayerInfo(int var1, Block var2) {
      this(3, var1, var2);
   }

   public String toString() {
      String var1;
      if (this.field_175902_a >= 3) {
         ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(this.func_151536_b());
         var1 = var2 == null ? "null" : var2.toString();
         if (this.layerCount > 1) {
            var1 = String.valueOf((new StringBuilder(String.valueOf(this.layerCount))).append("*").append(var1));
         }
      } else {
         var1 = Integer.toString(Block.getIdFromBlock(this.func_151536_b()));
         if (this.layerCount > 1) {
            var1 = String.valueOf((new StringBuilder(String.valueOf(this.layerCount))).append("x").append(var1));
         }
      }

      int var3 = this.getFillBlockMeta();
      if (var3 > 0) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(":").append(var3));
      }

      return var1;
   }

   public int getLayerCount() {
      return this.layerCount;
   }
}
