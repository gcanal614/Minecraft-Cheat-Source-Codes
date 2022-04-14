package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {
   private Block soilId;
   private static final String __OBFID = "CL_00000060";
   private Block field_150908_b;

   public ItemSeedFood(int var1, float var2, Block var3, Block var4) {
      super(var1, var2, false);
      this.field_150908_b = var3;
      this.soilId = var4;
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.UP) {
         return false;
      } else if (!var2.func_175151_a(var4.offset(var5), var5, var1)) {
         return false;
      } else if (var3.getBlockState(var4).getBlock() == this.soilId && var3.isAirBlock(var4.offsetUp())) {
         var3.setBlockState(var4.offsetUp(), this.field_150908_b.getDefaultState());
         --var1.stackSize;
         return true;
      } else {
         return false;
      }
   }
}
