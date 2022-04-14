package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRecord extends Item {
   private static final String __OBFID = "CL_00000057";
   public final String recordName;
   private static final Map field_150928_b = Maps.newHashMap();

   public EnumRarity getRarity(ItemStack var1) {
      return EnumRarity.RARE;
   }

   public static ItemRecord getRecord(String var0) {
      return (ItemRecord)field_150928_b.get(var0);
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.getBlockState(var4);
      if (var9.getBlock() == Blocks.jukebox && !(Boolean)var9.getValue(BlockJukebox.HAS_RECORD)) {
         if (var3.isRemote) {
            return true;
         } else {
            ((BlockJukebox)Blocks.jukebox).insertRecord(var3, var4, var9, var1);
            var3.playAuxSFXAtEntity((EntityPlayer)null, 1005, var4, Item.getIdFromItem(this));
            --var1.stackSize;
            return true;
         }
      } else {
         return false;
      }
   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      var3.add(this.getRecordNameLocal());
   }

   public String getRecordNameLocal() {
      return StatCollector.translateToLocal(String.valueOf((new StringBuilder("item.record.")).append(this.recordName).append(".desc")));
   }

   protected ItemRecord(String var1) {
      this.recordName = var1;
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabMisc);
      field_150928_b.put(String.valueOf((new StringBuilder("records.")).append(var1)), this);
   }
}
