package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureOceanMonument extends MapGenStructure {
   private static final List field_175803_h;
   private int field_175800_f;
   private static final String __OBFID = "CL_00001996";
   public static final List field_175802_d;
   private int field_175801_g;

   public StructureOceanMonument() {
      this.field_175800_f = 32;
      this.field_175801_g = 5;
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      return new StructureOceanMonument.StartMonument(this.worldObj, this.rand, var1, var2);
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      if (var1 < 0) {
         var1 -= this.field_175800_f - 1;
      }

      if (var2 < 0) {
         var2 -= this.field_175800_f - 1;
      }

      int var5 = var1 / this.field_175800_f;
      int var6 = var2 / this.field_175800_f;
      Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387313);
      var5 *= this.field_175800_f;
      var6 *= this.field_175800_f;
      var5 += (var7.nextInt(this.field_175800_f - this.field_175801_g) + var7.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      var6 += (var7.nextInt(this.field_175800_f - this.field_175801_g) + var7.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      if (var3 == var5 && var4 == var6) {
         if (this.worldObj.getWorldChunkManager().func_180300_a(new BlockPos(var3 * 16 + 8, 64, var4 * 16 + 8), (BiomeGenBase)null) != BiomeGenBase.deepOcean) {
            return false;
         }

         boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 29, field_175802_d);
         if (var8) {
            return true;
         }
      }

      return false;
   }

   public List func_175799_b() {
      return field_175803_h;
   }

   static {
      field_175802_d = Arrays.asList(BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver);
      field_175803_h = Lists.newArrayList();
      field_175803_h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
   }

   public StructureOceanMonument(Map var1) {
      this();
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if (((String)var3.getKey()).equals("spacing")) {
            this.field_175800_f = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_175800_f, 1);
         } else if (((String)var3.getKey()).equals("separation")) {
            this.field_175801_g = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_175801_g, 1);
         }
      }

   }

   public String getStructureName() {
      return "Monument";
   }

   public static class StartMonument extends StructureStart {
      private static final String __OBFID = "CL_00001995";
      private Set field_175791_c = Sets.newHashSet();
      private boolean field_175790_d;

      public void generateStructure(World var1, Random var2, StructureBoundingBox var3) {
         if (!this.field_175790_d) {
            this.components.clear();
            this.func_175789_b(var1, var2, this.func_143019_e(), this.func_143018_f());
         }

         super.generateStructure(var1, var2, var3);
      }

      public StartMonument(World var1, Random var2, int var3, int var4) {
         super(var3, var4);
         this.func_175789_b(var1, var2, var3, var4);
      }

      public void func_143017_b(NBTTagCompound var1) {
         super.func_143017_b(var1);
         if (var1.hasKey("Processed", 9)) {
            NBTTagList var2 = var1.getTagList("Processed", 10);

            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               NBTTagCompound var4 = var2.getCompoundTagAt(var3);
               this.field_175791_c.add(new ChunkCoordIntPair(var4.getInteger("X"), var4.getInteger("Z")));
            }
         }

      }

      public void func_175787_b(ChunkCoordIntPair var1) {
         super.func_175787_b(var1);
         this.field_175791_c.add(var1);
      }

      private void func_175789_b(World var1, Random var2, int var3, int var4) {
         var2.setSeed(var1.getSeed());
         long var5 = var2.nextLong();
         long var7 = var2.nextLong();
         long var9 = (long)var3 * var5;
         long var11 = (long)var4 * var7;
         var2.setSeed(var9 ^ var11 ^ var1.getSeed());
         int var13 = var3 * 16 + 8 - 29;
         int var14 = var4 * 16 + 8 - 29;
         EnumFacing var15 = EnumFacing.Plane.HORIZONTAL.random(var2);
         this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(var2, var13, var14, var15));
         this.updateBoundingBox();
         this.field_175790_d = true;
      }

      public void func_143022_a(NBTTagCompound var1) {
         super.func_143022_a(var1);
         NBTTagList var2 = new NBTTagList();
         Iterator var3 = this.field_175791_c.iterator();

         while(var3.hasNext()) {
            ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setInteger("X", var4.chunkXPos);
            var5.setInteger("Z", var4.chunkZPos);
            var2.appendTag(var5);
         }

         var1.setTag("Processed", var2);
      }

      public StartMonument() {
      }

      public boolean func_175788_a(ChunkCoordIntPair var1) {
         return this.field_175791_c.contains(var1) ? false : super.func_175788_a(var1);
      }
   }
}
