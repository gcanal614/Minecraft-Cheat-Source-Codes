package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;

public class VillageCollection extends WorldSavedData {
   private final List villagerPositionsList = Lists.newArrayList();
   private World worldObj;
   private final List villageList = Lists.newArrayList();
   private final List newDoors = Lists.newArrayList();
   private int tickCounter;
   private static final String __OBFID = "CL_00001635";

   public void func_176060_a(BlockPos var1) {
      if (this.villagerPositionsList.size() <= 64 && !this.func_176057_e(var1)) {
         this.villagerPositionsList.add(var1);
      }

   }

   public void writeToNBT(NBTTagCompound var1) {
      var1.setInteger("Tick", this.tickCounter);
      NBTTagList var2 = new NBTTagList();
      Iterator var3 = this.villageList.iterator();

      while(var3.hasNext()) {
         Village var4 = (Village)var3.next();
         NBTTagCompound var5 = new NBTTagCompound();
         var4.writeVillageDataToNBT(var5);
         var2.appendTag(var5);
      }

      var1.setTag("Villages", var2);
   }

   public void tick() {
      ++this.tickCounter;
      Iterator var1 = this.villageList.iterator();

      while(var1.hasNext()) {
         Village var2 = (Village)var1.next();
         var2.tick(this.tickCounter);
      }

      this.removeAnnihilatedVillages();
      this.dropOldestVillagerPosition();
      this.addNewDoorsToVillageOrCreateVillage();
      if (this.tickCounter % 400 == 0) {
         this.markDirty();
      }

   }

   private void func_176059_d(BlockPos var1) {
      EnumFacing var2 = BlockDoor.func_176517_h(this.worldObj, var1);
      EnumFacing var3 = var2.getOpposite();
      int var4 = this.func_176061_a(var1, var2, 5);
      int var5 = this.func_176061_a(var1, var3, var4 + 1);
      if (var4 != var5) {
         this.newDoors.add(new VillageDoorInfo(var1, var4 < var5 ? var2 : var3, this.tickCounter));
      }

   }

   public void readFromNBT(NBTTagCompound var1) {
      this.tickCounter = var1.getInteger("Tick");
      NBTTagList var2 = var1.getTagList("Villages", 10);

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         Village var5 = new Village();
         var5.readVillageDataFromNBT(var4);
         this.villageList.add(var5);
      }

   }

   public List getVillageList() {
      return this.villageList;
   }

   private VillageDoorInfo func_176055_c(BlockPos var1) {
      Iterator var2 = this.newDoors.iterator();

      while(var2.hasNext()) {
         VillageDoorInfo var3 = (VillageDoorInfo)var2.next();
         if (var3.func_179852_d().getX() == var1.getX() && var3.func_179852_d().getZ() == var1.getZ() && Math.abs(var3.func_179852_d().getY() - var1.getY()) <= 1) {
            return var3;
         }
      }

      var2 = this.villageList.iterator();

      while(var2.hasNext()) {
         Village var5 = (Village)var2.next();
         VillageDoorInfo var4 = var5.func_179864_e(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   public VillageCollection(World var1) {
      super(func_176062_a(var1.provider));
      this.worldObj = var1;
      this.markDirty();
   }

   private boolean func_176058_f(BlockPos var1) {
      Block var2 = this.worldObj.getBlockState(var1).getBlock();
      return var2 instanceof BlockDoor ? var2.getMaterial() == Material.wood : false;
   }

   private void addNewDoorsToVillageOrCreateVillage() {
      for(int var1 = 0; var1 < this.newDoors.size(); ++var1) {
         VillageDoorInfo var2 = (VillageDoorInfo)this.newDoors.get(var1);
         Village var3 = this.func_176056_a(var2.func_179852_d(), 32);
         if (var3 == null) {
            var3 = new Village(this.worldObj);
            this.villageList.add(var3);
            this.markDirty();
         }

         var3.addVillageDoorInfo(var2);
      }

      this.newDoors.clear();
   }

   private void func_180609_b(BlockPos var1) {
      byte var2 = 16;
      byte var3 = 4;
      byte var4 = 16;

      for(int var5 = -var2; var5 < var2; ++var5) {
         for(int var6 = -var3; var6 < var3; ++var6) {
            for(int var7 = -var4; var7 < var4; ++var7) {
               BlockPos var8 = var1.add(var5, var6, var7);
               if (this.func_176058_f(var8)) {
                  VillageDoorInfo var9 = this.func_176055_c(var8);
                  if (var9 == null) {
                     this.func_176059_d(var8);
                  } else {
                     var9.func_179849_a(this.tickCounter);
                  }
               }
            }
         }
      }

   }

   private void dropOldestVillagerPosition() {
      if (!this.villagerPositionsList.isEmpty()) {
         this.func_180609_b((BlockPos)this.villagerPositionsList.remove(0));
      }

   }

   public VillageCollection(String var1) {
      super(var1);
   }

   public Village func_176056_a(BlockPos var1, int var2) {
      Village var3 = null;
      double var4 = 3.4028234663852886E38D;
      Iterator var6 = this.villageList.iterator();

      while(var6.hasNext()) {
         Village var7 = (Village)var6.next();
         double var8 = var7.func_180608_a().distanceSq(var1);
         if (var8 < var4) {
            float var10 = (float)(var2 + var7.getVillageRadius());
            if (var8 <= (double)(var10 * var10)) {
               var3 = var7;
               var4 = var8;
            }
         }
      }

      return var3;
   }

   private void removeAnnihilatedVillages() {
      Iterator var1 = this.villageList.iterator();

      while(var1.hasNext()) {
         Village var2 = (Village)var1.next();
         if (var2.isAnnihilated()) {
            var1.remove();
            this.markDirty();
         }
      }

   }

   private int func_176061_a(BlockPos var1, EnumFacing var2, int var3) {
      int var4 = 0;

      for(int var5 = 1; var5 <= 5; ++var5) {
         if (this.worldObj.isAgainstSky(var1.offset(var2, var5))) {
            ++var4;
            if (var4 >= var3) {
               return var4;
            }
         }
      }

      return var4;
   }

   private boolean func_176057_e(BlockPos var1) {
      Iterator var2 = this.villagerPositionsList.iterator();

      while(var2.hasNext()) {
         BlockPos var3 = (BlockPos)var2.next();
         if (var3.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   public static String func_176062_a(WorldProvider var0) {
      return String.valueOf((new StringBuilder("villages")).append(var0.getInternalNameSuffix()));
   }

   public void func_82566_a(World var1) {
      this.worldObj = var1;
      Iterator var2 = this.villageList.iterator();

      while(var2.hasNext()) {
         Village var3 = (Village)var2.next();
         var3.func_82691_a(var1);
      }

   }
}
