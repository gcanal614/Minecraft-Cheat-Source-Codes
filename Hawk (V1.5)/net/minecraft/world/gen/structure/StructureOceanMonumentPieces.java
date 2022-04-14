package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StructureOceanMonumentPieces {
   private static final String __OBFID = "CL_00001994";

   public static void func_175970_a() {
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.MonumentBuilding.class, "OMB");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.MonumentCoreRoom.class, "OMCR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleXRoom.class, "OMDXR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleXYRoom.class, "OMDXYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleYRoom.class, "OMDYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleYZRoom.class, "OMDYZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleZRoom.class, "OMDZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.EntryRoom.class, "OMEntry");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.Penthouse.class, "OMPenthouse");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.SimpleRoom.class, "OMSimple");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.SimpleTopRoom.class, "OMSimpleT");
   }

   public static class Penthouse extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001977";

      public Penthouse(EnumFacing var1, StructureBoundingBox var2) {
         super(var1, var2);
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         this.func_175804_a(var1, var3, 2, -1, 2, 11, -1, 11, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, -1, 0, 1, -1, 11, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 12, -1, 0, 13, -1, 11, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 2, -1, 0, 11, -1, 1, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 2, -1, 12, 11, -1, 13, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 0, 0, 0, 0, 0, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 13, 0, 0, 13, 0, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 0, 0, 12, 0, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 0, 13, 12, 0, 13, field_175826_b, field_175826_b, false);

         for(int var4 = 2; var4 <= 11; var4 += 3) {
            this.func_175811_a(var1, field_175825_e, 0, 0, var4, var3);
            this.func_175811_a(var1, field_175825_e, 13, 0, var4, var3);
            this.func_175811_a(var1, field_175825_e, var4, 0, 0, var3);
         }

         this.func_175804_a(var1, var3, 2, 0, 3, 4, 0, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 0, 3, 11, 0, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 4, 0, 9, 9, 0, 11, field_175826_b, field_175826_b, false);
         this.func_175811_a(var1, field_175826_b, 5, 0, 8, var3);
         this.func_175811_a(var1, field_175826_b, 8, 0, 8, var3);
         this.func_175811_a(var1, field_175826_b, 10, 0, 10, var3);
         this.func_175811_a(var1, field_175826_b, 3, 0, 10, var3);
         this.func_175804_a(var1, var3, 3, 0, 3, 3, 0, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 10, 0, 3, 10, 0, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 6, 0, 10, 7, 0, 10, field_175827_c, field_175827_c, false);
         byte var7 = 3;

         for(int var5 = 0; var5 < 2; ++var5) {
            for(int var6 = 2; var6 <= 8; var6 += 3) {
               this.func_175804_a(var1, var3, var7, 0, var6, var7, 2, var6, field_175826_b, field_175826_b, false);
            }

            var7 = 10;
         }

         this.func_175804_a(var1, var3, 5, 0, 10, 5, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 8, 0, 10, 8, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, -1, 7, 7, -1, 8, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 6, -1, 3, 7, -1, 4, field_175822_f, field_175822_f, false);
         this.func_175817_a(var1, var3, 6, 1, 6);
         return true;
      }

      public Penthouse() {
      }
   }

   public static class DoubleXYRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001982";

      public DoubleXYRoom() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         StructureOceanMonumentPieces.RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var5 = this.field_175830_k;
         StructureOceanMonumentPieces.RoomDefinition var6 = var5.field_175965_b[EnumFacing.UP.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var7 = var4.field_175965_b[EnumFacing.UP.getIndex()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var6.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 8, 1, 7, 8, 6, field_175828_a);
         }

         if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 8, 8, 1, 14, 8, 6, field_175828_a);
         }

         for(int var8 = 1; var8 <= 7; ++var8) {
            IBlockState var9 = field_175826_b;
            if (var8 == 2 || var8 == 6) {
               var9 = field_175828_a;
            }

            this.func_175804_a(var1, var3, 0, var8, 0, 0, var8, 7, var9, var9, false);
            this.func_175804_a(var1, var3, 15, var8, 0, 15, var8, 7, var9, var9, false);
            this.func_175804_a(var1, var3, 1, var8, 0, 15, var8, 0, var9, var9, false);
            this.func_175804_a(var1, var3, 1, var8, 7, 14, var8, 7, var9, var9, false);
         }

         this.func_175804_a(var1, var3, 2, 1, 3, 2, 7, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 1, 2, 4, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 1, 5, 4, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 13, 1, 3, 13, 7, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 11, 1, 2, 12, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 11, 1, 5, 12, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 3, 5, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 1, 3, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 7, 2, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 5, 2, 5, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 5, 2, 10, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 5, 5, 5, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 5, 5, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175811_a(var1, field_175826_b, 6, 6, 2, var3);
         this.func_175811_a(var1, field_175826_b, 9, 6, 2, var3);
         this.func_175811_a(var1, field_175826_b, 6, 6, 5, var3);
         this.func_175811_a(var1, field_175826_b, 9, 6, 5, var3);
         this.func_175804_a(var1, var3, 5, 4, 3, 6, 4, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 4, 3, 10, 4, 4, field_175826_b, field_175826_b, false);
         this.func_175811_a(var1, field_175825_e, 5, 4, 2, var3);
         this.func_175811_a(var1, field_175825_e, 5, 4, 5, var3);
         this.func_175811_a(var1, field_175825_e, 10, 4, 2, var3);
         this.func_175811_a(var1, field_175825_e, 10, 4, 5, var3);
         if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 1, 0, 12, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 1, 7, 12, 2, 7, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 15, 1, 3, 15, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var6.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 5, 0, 4, 6, 0, field_175822_f, field_175822_f, false);
         }

         if (var6.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 5, 7, 4, 6, 7, field_175822_f, field_175822_f, false);
         }

         if (var6.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 5, 3, 0, 6, 4, field_175822_f, field_175822_f, false);
         }

         if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 5, 0, 12, 6, 0, field_175822_f, field_175822_f, false);
         }

         if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 5, 7, 12, 6, 7, field_175822_f, field_175822_f, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 15, 5, 3, 15, 6, 4, field_175822_f, field_175822_f, false);
         }

         return true;
      }

      public DoubleXYRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 2, 2, 1);
      }
   }

   static class YZDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001989";

      private YZDoubleRoomFitHelper() {
      }

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleYZRoom(var1, var2, var3);
      }

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         if (var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d && var1.field_175966_c[EnumFacing.UP.getIndex()] && !var1.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
            StructureOceanMonumentPieces.RoomDefinition var2 = var1.field_175965_b[EnumFacing.NORTH.getIndex()];
            return var2.field_175966_c[EnumFacing.UP.getIndex()] && !var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
         } else {
            return false;
         }
      }

      YZDoubleRoomFitHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }
   }

   static class FitSimpleRoomTopHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001986";

      FitSimpleRoomTopHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         return !var1.field_175966_c[EnumFacing.WEST.getIndex()] && !var1.field_175966_c[EnumFacing.EAST.getIndex()] && !var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175966_c[EnumFacing.SOUTH.getIndex()] && !var1.field_175966_c[EnumFacing.UP.getIndex()];
      }

      private FitSimpleRoomTopHelper() {
      }

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         return new StructureOceanMonumentPieces.SimpleTopRoom(var1, var2, var3);
      }
   }

   public abstract static class Piece extends StructureComponent {
      protected StructureOceanMonumentPieces.RoomDefinition field_175830_k;
      protected static final int field_175831_h;
      protected static final IBlockState field_175822_f;
      protected static final IBlockState field_175825_e;
      protected static final int field_175823_g;
      protected static final int field_175829_j;
      private static final String __OBFID = "CL_00001976";
      protected static final IBlockState field_175827_c;
      protected static final IBlockState field_175826_b;
      protected static final IBlockState field_175824_d;
      protected static final int field_175832_i;
      protected static final IBlockState field_175828_a;

      protected Piece(int var1, EnumFacing var2, StructureOceanMonumentPieces.RoomDefinition var3, int var4, int var5, int var6) {
         super(var1);
         this.coordBaseMode = var2;
         this.field_175830_k = var3;
         int var7 = var3.field_175967_a;
         int var8 = var7 % 5;
         int var9 = var7 / 5 % 5;
         int var10 = var7 / 25;
         if (var2 != EnumFacing.NORTH && var2 != EnumFacing.SOUTH) {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, var6 * 8 - 1, var5 * 4 - 1, var4 * 8 - 1);
         } else {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, var4 * 8 - 1, var5 * 4 - 1, var6 * 8 - 1);
         }

         switch(var2) {
         case NORTH:
            this.boundingBox.offset(var8 * 8, var10 * 4, -(var9 + var6) * 8 + 1);
            break;
         case SOUTH:
            this.boundingBox.offset(var8 * 8, var10 * 4, var9 * 8);
            break;
         case WEST:
            this.boundingBox.offset(-(var9 + var6) * 8 + 1, var10 * 4, var8 * 8);
            break;
         default:
            this.boundingBox.offset(var9 * 8, var10 * 4, var8 * 8);
         }

      }

      public Piece(int var1) {
         super(var1);
      }

      protected static final int func_175820_a(int var0, int var1, int var2) {
         return var1 * 25 + var2 * 5 + var0;
      }

      protected void func_175821_a(World var1, StructureBoundingBox var2, int var3, int var4, boolean var5) {
         if (var5) {
            this.func_175804_a(var1, var2, var3, 0, var4, var3 + 2, 0, var4 + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var2, var3 + 5, 0, var4, var3 + 8 - 1, 0, var4 + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var2, var3 + 3, 0, var4, var3 + 4, 0, var4 + 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var2, var3 + 3, 0, var4 + 5, var3 + 4, 0, var4 + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var2, var3 + 3, 0, var4 + 2, var3 + 4, 0, var4 + 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var2, var3 + 3, 0, var4 + 5, var3 + 4, 0, var4 + 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var2, var3 + 2, 0, var4 + 3, var3 + 2, 0, var4 + 4, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var2, var3 + 5, 0, var4 + 3, var3 + 5, 0, var4 + 4, field_175826_b, field_175826_b, false);
         } else {
            this.func_175804_a(var1, var2, var3, 0, var4, var3 + 8 - 1, 0, var4 + 8 - 1, field_175828_a, field_175828_a, false);
         }

      }

      protected void writeStructureToNBT(NBTTagCompound var1) {
      }

      public Piece() {
         super(0);
      }

      protected void func_175819_a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9) {
         for(int var10 = var4; var10 <= var7; ++var10) {
            for(int var11 = var3; var11 <= var6; ++var11) {
               for(int var12 = var5; var12 <= var8; ++var12) {
                  if (this.func_175807_a(var1, var11, var10, var12, var2) == field_175822_f) {
                     this.func_175811_a(var1, var9, var11, var10, var12, var2);
                  }
               }
            }
         }

      }

      protected boolean func_175818_a(StructureBoundingBox var1, int var2, int var3, int var4, int var5) {
         int var6 = this.getXWithOffset(var2, var3);
         int var7 = this.getZWithOffset(var2, var3);
         int var8 = this.getXWithOffset(var4, var5);
         int var9 = this.getZWithOffset(var4, var5);
         return var1.intersectsWith(Math.min(var6, var8), Math.min(var7, var9), Math.max(var6, var8), Math.max(var7, var9));
      }

      public Piece(EnumFacing var1, StructureBoundingBox var2) {
         super(1);
         this.coordBaseMode = var1;
         this.boundingBox = var2;
      }

      protected void readStructureFromNBT(NBTTagCompound var1) {
      }

      protected boolean func_175817_a(World var1, StructureBoundingBox var2, int var3, int var4, int var5) {
         int var6 = this.getXWithOffset(var3, var5);
         int var7 = this.getYWithOffset(var4);
         int var8 = this.getZWithOffset(var3, var5);
         if (var2.func_175898_b(new BlockPos(var6, var7, var8))) {
            EntityGuardian var9 = new EntityGuardian(var1);
            var9.func_175467_a(true);
            var9.heal(var9.getMaxHealth());
            var9.setLocationAndAngles((double)var6 + 0.5D, (double)var7, (double)var8 + 0.5D, 0.0F, 0.0F);
            var9.func_180482_a(var1.getDifficultyForLocation(new BlockPos(var9)), (IEntityLivingData)null);
            var1.spawnEntityInWorld(var9);
            return true;
         } else {
            return false;
         }
      }

      static {
         field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGHMETA);
         field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKSMETA);
         field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARKMETA);
         field_175824_d = field_175826_b;
         field_175825_e = Blocks.sea_lantern.getDefaultState();
         field_175822_f = Blocks.water.getDefaultState();
         field_175823_g = func_175820_a(2, 0, 0);
         field_175831_h = func_175820_a(2, 2, 0);
         field_175832_i = func_175820_a(0, 1, 0);
         field_175829_j = func_175820_a(4, 1, 0);
      }
   }

   static class RoomDefinition {
      boolean field_175964_e;
      int field_175962_f;
      StructureOceanMonumentPieces.RoomDefinition[] field_175965_b = new StructureOceanMonumentPieces.RoomDefinition[6];
      boolean field_175963_d;
      private static final String __OBFID = "CL_00001972";
      int field_175967_a;
      boolean[] field_175966_c = new boolean[6];

      public void func_175957_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2) {
         this.field_175965_b[var1.getIndex()] = var2;
         var2.field_175965_b[var1.getOpposite().getIndex()] = this;
      }

      public void func_175958_a() {
         for(int var1 = 0; var1 < 6; ++var1) {
            this.field_175966_c[var1] = this.field_175965_b[var1] != null;
         }

      }

      public boolean func_175961_b() {
         return this.field_175967_a >= 75;
      }

      public boolean func_175959_a(int var1) {
         if (this.field_175964_e) {
            return true;
         } else {
            this.field_175962_f = var1;

            for(int var2 = 0; var2 < 6; ++var2) {
               if (this.field_175965_b[var2] != null && this.field_175966_c[var2] && this.field_175965_b[var2].field_175962_f != var1 && this.field_175965_b[var2].func_175959_a(var1)) {
                  return true;
               }
            }

            return false;
         }
      }

      public RoomDefinition(int var1) {
         this.field_175967_a = var1;
      }

      public int func_175960_c() {
         int var1 = 0;

         for(int var2 = 0; var2 < 6; ++var2) {
            if (this.field_175966_c[var2]) {
               ++var1;
            }
         }

         return var1;
      }
   }

   public static class SimpleRoom extends StructureOceanMonumentPieces.Piece {
      private int field_175833_o;
      private static final String __OBFID = "CL_00001975";

      public SimpleRoom() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         boolean var4 = this.field_175833_o != 0 && var2.nextBoolean() && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()] && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()] && this.field_175830_k.func_175960_c() > 1;
         if (this.field_175833_o == 0) {
            this.func_175804_a(var1, var3, 0, 1, 0, 2, 1, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 3, 0, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 2, 0, 0, 2, 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 1, 2, 0, 2, 2, 0, field_175828_a, field_175828_a, false);
            this.func_175811_a(var1, field_175825_e, 1, 2, 1, var3);
            this.func_175804_a(var1, var3, 5, 1, 0, 7, 1, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 5, 3, 0, 7, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 2, 0, 7, 2, 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 5, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
            this.func_175811_a(var1, field_175825_e, 6, 2, 1, var3);
            this.func_175804_a(var1, var3, 0, 1, 5, 2, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 3, 5, 2, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 2, 5, 0, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 1, 2, 7, 2, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175811_a(var1, field_175825_e, 1, 2, 6, var3);
            this.func_175804_a(var1, var3, 5, 1, 5, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 5, 3, 5, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 2, 5, 7, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 5, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175811_a(var1, field_175825_e, 6, 2, 6, var3);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.func_175804_a(var1, var3, 3, 3, 0, 4, 3, 0, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 3, 3, 0, 4, 3, 1, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 3, 2, 0, 4, 2, 0, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 3, 1, 0, 4, 1, 1, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.func_175804_a(var1, var3, 3, 3, 7, 4, 3, 7, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 3, 3, 6, 4, 3, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 3, 2, 7, 4, 2, 7, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 3, 1, 6, 4, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.func_175804_a(var1, var3, 0, 3, 3, 0, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 0, 3, 3, 1, 3, 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 0, 2, 3, 0, 2, 4, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 0, 1, 3, 1, 1, 4, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.func_175804_a(var1, var3, 7, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 6, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 7, 2, 3, 7, 2, 4, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 6, 1, 3, 7, 1, 4, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 1) {
            this.func_175804_a(var1, var3, 2, 1, 2, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 2, 1, 5, 2, 3, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 5, 1, 5, 5, 3, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 5, 1, 2, 5, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175811_a(var1, field_175825_e, 2, 2, 2, var3);
            this.func_175811_a(var1, field_175825_e, 2, 2, 5, var3);
            this.func_175811_a(var1, field_175825_e, 5, 2, 5, var3);
            this.func_175811_a(var1, field_175825_e, 5, 2, 2, var3);
            this.func_175804_a(var1, var3, 0, 1, 0, 1, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 1, 1, 0, 3, 1, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 1, 7, 1, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 1, 6, 0, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 1, 6, 7, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 1, 1, 7, 3, 1, field_175826_b, field_175826_b, false);
            this.func_175811_a(var1, field_175828_a, 1, 2, 0, var3);
            this.func_175811_a(var1, field_175828_a, 0, 2, 1, var3);
            this.func_175811_a(var1, field_175828_a, 1, 2, 7, var3);
            this.func_175811_a(var1, field_175828_a, 0, 2, 6, var3);
            this.func_175811_a(var1, field_175828_a, 6, 2, 7, var3);
            this.func_175811_a(var1, field_175828_a, 7, 2, 6, var3);
            this.func_175811_a(var1, field_175828_a, 6, 2, 0, var3);
            this.func_175811_a(var1, field_175828_a, 7, 2, 1, var3);
            if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.func_175804_a(var1, var3, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 1, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.func_175804_a(var1, var3, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 1, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.func_175804_a(var1, var3, 0, 3, 1, 0, 3, 6, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 0, 2, 1, 0, 2, 6, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 0, 1, 1, 0, 1, 6, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.func_175804_a(var1, var3, 7, 3, 1, 7, 3, 6, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 7, 2, 1, 7, 2, 6, field_175828_a, field_175828_a, false);
               this.func_175804_a(var1, var3, 7, 1, 1, 7, 1, 6, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 2) {
            this.func_175804_a(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175822_f, field_175822_f, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175822_f, field_175822_f, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.func_175804_a(var1, var3, 7, 1, 3, 7, 2, 4, field_175822_f, field_175822_f, false);
            }
         }

         if (var4) {
            this.func_175804_a(var1, var3, 3, 1, 3, 4, 1, 4, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 3, 2, 3, 4, 2, 4, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 3, 3, 3, 4, 3, 4, field_175826_b, field_175826_b, false);
         }

         return true;
      }

      public SimpleRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 1, 1, 1);
         this.field_175833_o = var3.nextInt(3);
      }
   }

   static class FitSimpleRoomHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001987";

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         return new StructureOceanMonumentPieces.SimpleRoom(var1, var2, var3);
      }

      FitSimpleRoomHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      private FitSimpleRoomHelper() {
      }

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         return true;
      }
   }

   public static class DoubleYRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001981";

      public DoubleYRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 1, 2, 1);
      }

      public DoubleYRoom() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         StructureOceanMonumentPieces.RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
         if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 8, 1, 6, 8, 6, field_175828_a);
         }

         this.func_175804_a(var1, var3, 0, 4, 0, 0, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 4, 0, 7, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 4, 0, 6, 4, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 4, 7, 6, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 4, 1, 2, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 4, 2, 1, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 4, 1, 5, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 4, 2, 6, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 4, 5, 2, 4, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 4, 5, 1, 4, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 4, 5, 5, 4, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 4, 5, 6, 4, 5, field_175826_b, field_175826_b, false);
         StructureOceanMonumentPieces.RoomDefinition var5 = this.field_175830_k;

         for(int var6 = 1; var6 <= 5; var6 += 4) {
            byte var7 = 0;
            if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.func_175804_a(var1, var3, 2, var6, var7, 2, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 5, var6, var7, 5, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 3, var6 + 2, var7, 4, var6 + 2, var7, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 0, var6, var7, 7, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 0, var6 + 1, var7, 7, var6 + 1, var7, field_175828_a, field_175828_a, false);
            }

            var7 = 7;
            if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.func_175804_a(var1, var3, 2, var6, var7, 2, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 5, var6, var7, 5, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 3, var6 + 2, var7, 4, var6 + 2, var7, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, 0, var6, var7, 7, var6 + 2, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 0, var6 + 1, var7, 7, var6 + 1, var7, field_175828_a, field_175828_a, false);
            }

            byte var8 = 0;
            if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.func_175804_a(var1, var3, var8, var6, 2, var8, var6 + 2, 2, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6, 5, var8, var6 + 2, 5, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6 + 2, 3, var8, var6 + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, var8, var6, 0, var8, var6 + 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6 + 1, 0, var8, var6 + 1, 7, field_175828_a, field_175828_a, false);
            }

            var8 = 7;
            if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.func_175804_a(var1, var3, var8, var6, 2, var8, var6 + 2, 2, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6, 5, var8, var6 + 2, 5, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6 + 2, 3, var8, var6 + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(var1, var3, var8, var6, 0, var8, var6 + 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, var6 + 1, 0, var8, var6 + 1, 7, field_175828_a, field_175828_a, false);
            }

            var5 = var4;
         }

         return true;
      }
   }

   public static class EntryRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001978";

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         this.func_175804_a(var1, var3, 0, 3, 0, 2, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 2, 0, 1, 2, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 2, 0, 7, 2, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 0, 2, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175822_f, field_175822_f, false);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 3, 1, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 6, 1, 3, 7, 2, 4, field_175822_f, field_175822_f, false);
         }

         return true;
      }

      public EntryRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2) {
         super(1, var1, var2, 1, 1, 1);
      }

      public EntryRoom() {
      }
   }

   static class YDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001990";

      YDoubleRoomFitHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         return var1.field_175966_c[EnumFacing.UP.getIndex()] && !var1.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
      }

      private YDoubleRoomFitHelper() {
      }

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleYRoom(var1, var2, var3);
      }
   }

   static class XYDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001991";

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         var2.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         var2.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleXYRoom(var1, var2, var3);
      }

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         if (var1.field_175966_c[EnumFacing.EAST.getIndex()] && !var1.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d && var1.field_175966_c[EnumFacing.UP.getIndex()] && !var1.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
            StructureOceanMonumentPieces.RoomDefinition var2 = var1.field_175965_b[EnumFacing.EAST.getIndex()];
            return var2.field_175966_c[EnumFacing.UP.getIndex()] && !var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
         } else {
            return false;
         }
      }

      XYDoubleRoomFitHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      private XYDoubleRoomFitHelper() {
      }
   }

   public static class DoubleXRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001983";

      public DoubleXRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 2, 1, 1);
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         StructureOceanMonumentPieces.RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var5 = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var5.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 4, 1, 7, 4, 6, field_175828_a);
         }

         if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 8, 4, 1, 14, 4, 6, field_175828_a);
         }

         this.func_175804_a(var1, var3, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 15, 3, 0, 15, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 7, 14, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 2, 0, 0, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 15, 2, 0, 15, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 1, 2, 0, 15, 2, 0, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 1, 2, 7, 14, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 0, 10, 1, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 2, 0, 9, 2, 3, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 5, 3, 0, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175811_a(var1, field_175825_e, 6, 2, 3, var3);
         this.func_175811_a(var1, field_175825_e, 9, 2, 3, var3);
         if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 1, 0, 12, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 11, 1, 7, 12, 2, 7, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 15, 1, 3, 15, 2, 4, field_175822_f, field_175822_f, false);
         }

         return true;
      }

      public DoubleXRoom() {
      }
   }

   public static class MonumentBuilding extends StructureOceanMonumentPieces.Piece {
      private StructureOceanMonumentPieces.RoomDefinition field_175844_p;
      private List field_175843_q = Lists.newArrayList();
      private StructureOceanMonumentPieces.RoomDefinition field_175845_o;
      private static final String __OBFID = "CL_00001985";

      private void func_175842_f(World var1, Random var2, StructureBoundingBox var3) {
         int var4;
         if (this.func_175818_a(var3, 7, 21, 13, 50)) {
            this.func_175804_a(var1, var3, 7, 0, 21, 13, 0, 50, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 7, 1, 21, 13, 10, 50, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 11, 8, 21, 13, 8, 53, field_175828_a, field_175828_a, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, var4 + 7, var4 + 5, 21, var4 + 7, var4 + 5, 54, field_175826_b, field_175826_b, false);
            }

            for(var4 = 21; var4 <= 45; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 12, 9, var4, var3);
            }
         }

         if (this.func_175818_a(var3, 44, 21, 50, 54)) {
            this.func_175804_a(var1, var3, 44, 0, 21, 50, 0, 50, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 44, 1, 21, 50, 10, 50, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 44, 8, 21, 46, 8, 53, field_175828_a, field_175828_a, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 50 - var4, var4 + 5, 21, 50 - var4, var4 + 5, 54, field_175826_b, field_175826_b, false);
            }

            for(var4 = 21; var4 <= 45; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 45, 9, var4, var3);
            }
         }

         if (this.func_175818_a(var3, 8, 44, 49, 54)) {
            this.func_175804_a(var1, var3, 14, 0, 44, 43, 0, 50, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 14, 1, 44, 43, 10, 50, field_175822_f, field_175822_f, false);

            for(var4 = 12; var4 <= 45; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, var4, 9, 45, var3);
               this.func_175811_a(var1, field_175824_d, var4, 9, 52, var3);
               if (var4 == 12 || var4 == 18 || var4 == 24 || var4 == 33 || var4 == 39 || var4 == 45) {
                  this.func_175811_a(var1, field_175824_d, var4, 9, 47, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 9, 50, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 10, 45, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 10, 46, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 10, 51, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 10, 52, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 11, 47, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 11, 50, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 12, 48, var3);
                  this.func_175811_a(var1, field_175824_d, var4, 12, 49, var3);
               }
            }

            for(var4 = 0; var4 < 3; ++var4) {
               this.func_175804_a(var1, var3, 8 + var4, 5 + var4, 54, 49 - var4, 5 + var4, 54, field_175828_a, field_175828_a, false);
            }

            this.func_175804_a(var1, var3, 11, 8, 54, 46, 8, 54, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 14, 8, 44, 43, 8, 53, field_175828_a, field_175828_a, false);
         }

      }

      private void func_175841_d(World var1, Random var2, StructureBoundingBox var3) {
         if (this.func_175818_a(var3, 21, 21, 36, 36)) {
            this.func_175804_a(var1, var3, 21, 0, 22, 36, 0, 36, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 21, 1, 22, 36, 23, 36, field_175822_f, field_175822_f, false);

            for(int var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 21 + var4, 13 + var4, 21 + var4, 36 - var4, 13 + var4, 21 + var4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 21 + var4, 13 + var4, 36 - var4, 36 - var4, 13 + var4, 36 - var4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 21 + var4, 13 + var4, 22 + var4, 21 + var4, 13 + var4, 35 - var4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 36 - var4, 13 + var4, 22 + var4, 36 - var4, 13 + var4, 35 - var4, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(var1, var3, 25, 16, 25, 32, 16, 32, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 25, 17, 25, 25, 19, 25, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 32, 17, 25, 32, 19, 25, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 25, 17, 32, 25, 19, 32, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 32, 17, 32, 32, 19, 32, field_175826_b, field_175826_b, false);
            this.func_175811_a(var1, field_175826_b, 26, 20, 26, var3);
            this.func_175811_a(var1, field_175826_b, 27, 21, 27, var3);
            this.func_175811_a(var1, field_175825_e, 27, 20, 27, var3);
            this.func_175811_a(var1, field_175826_b, 26, 20, 31, var3);
            this.func_175811_a(var1, field_175826_b, 27, 21, 30, var3);
            this.func_175811_a(var1, field_175825_e, 27, 20, 30, var3);
            this.func_175811_a(var1, field_175826_b, 31, 20, 31, var3);
            this.func_175811_a(var1, field_175826_b, 30, 21, 30, var3);
            this.func_175811_a(var1, field_175825_e, 30, 20, 30, var3);
            this.func_175811_a(var1, field_175826_b, 31, 20, 26, var3);
            this.func_175811_a(var1, field_175826_b, 30, 21, 27, var3);
            this.func_175811_a(var1, field_175825_e, 30, 20, 27, var3);
            this.func_175804_a(var1, var3, 28, 21, 27, 29, 21, 27, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 27, 21, 28, 27, 21, 29, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 28, 21, 30, 29, 21, 30, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 30, 21, 28, 30, 21, 29, field_175828_a, field_175828_a, false);
         }

      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         this.func_175840_a(false, 0, var1, var2, var3);
         this.func_175840_a(true, 33, var1, var2, var3);
         this.func_175839_b(var1, var2, var3);
         this.func_175837_c(var1, var2, var3);
         this.func_175841_d(var1, var2, var3);
         this.func_175835_e(var1, var2, var3);
         this.func_175842_f(var1, var2, var3);
         this.func_175838_g(var1, var2, var3);

         int var4;
         label82:
         for(var4 = 0; var4 < 7; ++var4) {
            int var5 = 0;

            while(true) {
               while(true) {
                  if (var5 >= 7) {
                     continue label82;
                  }

                  if (var5 == 0 && var4 == 3) {
                     var5 = 6;
                  }

                  int var6 = var4 * 9;
                  int var7 = var5 * 9;

                  for(int var8 = 0; var8 < 4; ++var8) {
                     for(int var9 = 0; var9 < 4; ++var9) {
                        this.func_175811_a(var1, field_175826_b, var6 + var8, 0, var7 + var9, var3);
                        this.func_175808_b(var1, field_175826_b, var6 + var8, -1, var7 + var9, var3);
                     }
                  }

                  if (var4 != 0 && var4 != 6) {
                     var5 += 6;
                  } else {
                     ++var5;
                  }
               }
            }
         }

         for(var4 = 0; var4 < 5; ++var4) {
            this.func_175804_a(var1, var3, -1 - var4, 0 + var4 * 2, -1 - var4, -1 - var4, 23, 58 + var4, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 58 + var4, 0 + var4 * 2, -1 - var4, 58 + var4, 23, 58 + var4, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 0 - var4, 0 + var4 * 2, -1 - var4, 57 + var4, 23, -1 - var4, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 0 - var4, 0 + var4 * 2, 58 + var4, 57 + var4, 23, 58 + var4, field_175822_f, field_175822_f, false);
         }

         Iterator var11 = this.field_175843_q.iterator();

         while(var11.hasNext()) {
            StructureOceanMonumentPieces.Piece var10 = (StructureOceanMonumentPieces.Piece)var11.next();
            if (var10.getBoundingBox().intersectsWith(var3)) {
               var10.addComponentParts(var1, var2, var3);
            }
         }

         return true;
      }

      private void func_175839_b(World var1, Random var2, StructureBoundingBox var3) {
         if (this.func_175818_a(var3, 22, 5, 35, 17)) {
            this.func_175804_a(var1, var3, 25, 0, 0, 32, 8, 20, field_175822_f, field_175822_f, false);

            for(int var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 24, 2, 5 + var4 * 4, 24, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 22, 4, 5 + var4 * 4, 23, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
               this.func_175811_a(var1, field_175826_b, 25, 5, 5 + var4 * 4, var3);
               this.func_175811_a(var1, field_175826_b, 26, 6, 5 + var4 * 4, var3);
               this.func_175811_a(var1, field_175825_e, 26, 5, 5 + var4 * 4, var3);
               this.func_175804_a(var1, var3, 33, 2, 5 + var4 * 4, 33, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, 34, 4, 5 + var4 * 4, 35, 4, 5 + var4 * 4, field_175826_b, field_175826_b, false);
               this.func_175811_a(var1, field_175826_b, 32, 5, 5 + var4 * 4, var3);
               this.func_175811_a(var1, field_175826_b, 31, 6, 5 + var4 * 4, var3);
               this.func_175811_a(var1, field_175825_e, 31, 5, 5 + var4 * 4, var3);
               this.func_175804_a(var1, var3, 27, 6, 5 + var4 * 4, 30, 6, 5 + var4 * 4, field_175828_a, field_175828_a, false);
            }
         }

      }

      private void func_175837_c(World var1, Random var2, StructureBoundingBox var3) {
         if (this.func_175818_a(var3, 15, 20, 42, 21)) {
            this.func_175804_a(var1, var3, 15, 0, 21, 42, 0, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 26, 1, 21, 31, 3, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 21, 12, 21, 36, 12, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 17, 11, 21, 40, 11, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 16, 10, 21, 41, 10, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 15, 7, 21, 42, 9, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 16, 6, 21, 41, 6, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 17, 5, 21, 40, 5, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 21, 4, 21, 36, 4, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 22, 3, 21, 26, 3, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 31, 3, 21, 35, 3, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 23, 2, 21, 25, 2, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 32, 2, 21, 34, 2, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 28, 4, 20, 29, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175811_a(var1, field_175826_b, 27, 3, 21, var3);
            this.func_175811_a(var1, field_175826_b, 30, 3, 21, var3);
            this.func_175811_a(var1, field_175826_b, 26, 2, 21, var3);
            this.func_175811_a(var1, field_175826_b, 31, 2, 21, var3);
            this.func_175811_a(var1, field_175826_b, 25, 1, 21, var3);
            this.func_175811_a(var1, field_175826_b, 32, 1, 21, var3);

            int var4;
            for(var4 = 0; var4 < 7; ++var4) {
               this.func_175811_a(var1, field_175827_c, 28 - var4, 6 + var4, 21, var3);
               this.func_175811_a(var1, field_175827_c, 29 + var4, 6 + var4, 21, var3);
            }

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175811_a(var1, field_175827_c, 28 - var4, 9 + var4, 21, var3);
               this.func_175811_a(var1, field_175827_c, 29 + var4, 9 + var4, 21, var3);
            }

            this.func_175811_a(var1, field_175827_c, 28, 12, 21, var3);
            this.func_175811_a(var1, field_175827_c, 29, 12, 21, var3);

            for(var4 = 0; var4 < 3; ++var4) {
               this.func_175811_a(var1, field_175827_c, 22 - var4 * 2, 8, 21, var3);
               this.func_175811_a(var1, field_175827_c, 22 - var4 * 2, 9, 21, var3);
               this.func_175811_a(var1, field_175827_c, 35 + var4 * 2, 8, 21, var3);
               this.func_175811_a(var1, field_175827_c, 35 + var4 * 2, 9, 21, var3);
            }

            this.func_175804_a(var1, var3, 15, 13, 21, 42, 15, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 15, 1, 21, 15, 6, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 16, 1, 21, 16, 5, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 17, 1, 21, 20, 4, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 21, 1, 21, 21, 3, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 22, 1, 21, 22, 2, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 23, 1, 21, 24, 1, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 42, 1, 21, 42, 6, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 41, 1, 21, 41, 5, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 37, 1, 21, 40, 4, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 36, 1, 21, 36, 3, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 35, 1, 21, 35, 2, 21, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 33, 1, 21, 34, 1, 21, field_175822_f, field_175822_f, false);
         }

      }

      private void func_175838_g(World var1, Random var2, StructureBoundingBox var3) {
         int var4;
         if (this.func_175818_a(var3, 14, 21, 20, 43)) {
            this.func_175804_a(var1, var3, 14, 0, 21, 20, 0, 43, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 14, 1, 22, 20, 14, 43, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 18, 12, 22, 20, 12, 39, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 18, 12, 21, 20, 12, 21, field_175826_b, field_175826_b, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, var4 + 14, var4 + 9, 21, var4 + 14, var4 + 9, 43 - var4, field_175826_b, field_175826_b, false);
            }

            for(var4 = 23; var4 <= 39; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 19, 13, var4, var3);
            }
         }

         if (this.func_175818_a(var3, 37, 21, 43, 43)) {
            this.func_175804_a(var1, var3, 37, 0, 21, 43, 0, 43, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 37, 1, 22, 43, 14, 43, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 37, 12, 22, 39, 12, 39, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 37, 12, 21, 39, 12, 21, field_175826_b, field_175826_b, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 43 - var4, var4 + 9, 21, 43 - var4, var4 + 9, 43 - var4, field_175826_b, field_175826_b, false);
            }

            for(var4 = 23; var4 <= 39; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 38, 13, var4, var3);
            }
         }

         if (this.func_175818_a(var3, 15, 37, 42, 43)) {
            this.func_175804_a(var1, var3, 21, 0, 37, 36, 0, 43, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 21, 1, 37, 36, 14, 43, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 21, 12, 37, 36, 12, 39, field_175828_a, field_175828_a, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 15 + var4, var4 + 9, 43 - var4, 42 - var4, var4 + 9, 43 - var4, field_175826_b, field_175826_b, false);
            }

            for(var4 = 21; var4 <= 36; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, var4, 13, 38, var3);
            }
         }

      }

      public MonumentBuilding(Random var1, int var2, int var3, EnumFacing var4) {
         super(0);
         this.coordBaseMode = var4;
         switch(this.coordBaseMode) {
         case NORTH:
         case SOUTH:
            this.boundingBox = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
            break;
         default:
            this.boundingBox = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
         }

         List var5 = this.func_175836_a(var1);
         this.field_175845_o.field_175963_d = true;
         this.field_175843_q.add(new StructureOceanMonumentPieces.EntryRoom(this.coordBaseMode, this.field_175845_o));
         this.field_175843_q.add(new StructureOceanMonumentPieces.MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, var1));
         ArrayList var6 = Lists.newArrayList();
         var6.add(new StructureOceanMonumentPieces.XYDoubleRoomFitHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.YZDoubleRoomFitHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.ZDoubleRoomFitHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.XDoubleRoomFitHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.YDoubleRoomFitHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.FitSimpleRoomTopHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         var6.add(new StructureOceanMonumentPieces.FitSimpleRoomHelper((StructureOceanMonumentPieces.SwitchEnumFacing)null));
         Iterator var7 = var5.iterator();

         while(true) {
            while(true) {
               StructureOceanMonumentPieces.RoomDefinition var8;
               do {
                  do {
                     if (!var7.hasNext()) {
                        int var16 = this.boundingBox.minY;
                        int var17 = this.getXWithOffset(9, 22);
                        int var18 = this.getZWithOffset(9, 22);
                        Iterator var11 = this.field_175843_q.iterator();

                        while(var11.hasNext()) {
                           StructureOceanMonumentPieces.Piece var12 = (StructureOceanMonumentPieces.Piece)var11.next();
                           var12.getBoundingBox().offset(var17, var16, var18);
                        }

                        StructureBoundingBox var19 = StructureBoundingBox.func_175899_a(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
                        StructureBoundingBox var13 = StructureBoundingBox.func_175899_a(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
                        StructureBoundingBox var14 = StructureBoundingBox.func_175899_a(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
                        int var15 = var1.nextInt();
                        this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, var19, var15++));
                        this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, var13, var15++));
                        this.field_175843_q.add(new StructureOceanMonumentPieces.Penthouse(this.coordBaseMode, var14));
                        return;
                     }

                     var8 = (StructureOceanMonumentPieces.RoomDefinition)var7.next();
                  } while(var8.field_175963_d);
               } while(var8.func_175961_b());

               Iterator var9 = var6.iterator();

               while(var9.hasNext()) {
                  StructureOceanMonumentPieces.MonumentRoomFitHelper var10 = (StructureOceanMonumentPieces.MonumentRoomFitHelper)var9.next();
                  if (var10.func_175969_a(var8)) {
                     this.field_175843_q.add(var10.func_175968_a(this.coordBaseMode, var8, var1));
                     break;
                  }
               }
            }
         }
      }

      private List func_175836_a(Random var1) {
         StructureOceanMonumentPieces.RoomDefinition[] var2 = new StructureOceanMonumentPieces.RoomDefinition[75];

         int var3;
         int var4;
         byte var5;
         int var6;
         for(var3 = 0; var3 < 5; ++var3) {
            for(var4 = 0; var4 < 4; ++var4) {
               var5 = 0;
               var6 = func_175820_a(var3, var5, var4);
               var2[var6] = new StructureOceanMonumentPieces.RoomDefinition(var6);
            }
         }

         for(var3 = 0; var3 < 5; ++var3) {
            for(var4 = 0; var4 < 4; ++var4) {
               var5 = 1;
               var6 = func_175820_a(var3, var5, var4);
               var2[var6] = new StructureOceanMonumentPieces.RoomDefinition(var6);
            }
         }

         for(var3 = 1; var3 < 4; ++var3) {
            for(var4 = 0; var4 < 2; ++var4) {
               var5 = 2;
               var6 = func_175820_a(var3, var5, var4);
               var2[var6] = new StructureOceanMonumentPieces.RoomDefinition(var6);
            }
         }

         this.field_175845_o = var2[field_175823_g];

         int var7;
         int var8;
         int var9;
         int var10;
         int var11;
         for(var3 = 0; var3 < 5; ++var3) {
            for(var4 = 0; var4 < 5; ++var4) {
               for(int var12 = 0; var12 < 3; ++var12) {
                  var6 = func_175820_a(var3, var12, var4);
                  if (var2[var6] != null) {
                     EnumFacing[] var13 = EnumFacing.values();
                     var7 = var13.length;

                     for(var8 = 0; var8 < var7; ++var8) {
                        EnumFacing var14 = var13[var8];
                        var9 = var3 + var14.getFrontOffsetX();
                        var10 = var12 + var14.getFrontOffsetY();
                        var11 = var4 + var14.getFrontOffsetZ();
                        if (var9 >= 0 && var9 < 5 && var11 >= 0 && var11 < 5 && var10 >= 0 && var10 < 3) {
                           int var15 = func_175820_a(var9, var10, var11);
                           if (var2[var15] != null) {
                              if (var11 != var4) {
                                 var2[var6].func_175957_a(var14.getOpposite(), var2[var15]);
                              } else {
                                 var2[var6].func_175957_a(var14, var2[var15]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         StructureOceanMonumentPieces.RoomDefinition var21;
         var2[field_175831_h].func_175957_a(EnumFacing.UP, var21 = new StructureOceanMonumentPieces.RoomDefinition(1003));
         StructureOceanMonumentPieces.RoomDefinition var22;
         var2[field_175832_i].func_175957_a(EnumFacing.SOUTH, var22 = new StructureOceanMonumentPieces.RoomDefinition(1001));
         StructureOceanMonumentPieces.RoomDefinition var23;
         var2[field_175829_j].func_175957_a(EnumFacing.SOUTH, var23 = new StructureOceanMonumentPieces.RoomDefinition(1002));
         var21.field_175963_d = true;
         var22.field_175963_d = true;
         var23.field_175963_d = true;
         this.field_175845_o.field_175964_e = true;
         this.field_175844_p = var2[func_175820_a(var1.nextInt(4), 0, 2)];
         this.field_175844_p.field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         ArrayList var24 = Lists.newArrayList();
         StructureOceanMonumentPieces.RoomDefinition[] var16 = var2;
         var7 = var2.length;

         for(var8 = 0; var8 < var7; ++var8) {
            StructureOceanMonumentPieces.RoomDefinition var17 = var16[var8];
            if (var17 != null) {
               var17.func_175958_a();
               var24.add(var17);
            }
         }

         var21.func_175958_a();
         Collections.shuffle(var24, var1);
         int var25 = 1;
         Iterator var18 = var24.iterator();

         label130:
         while(var18.hasNext()) {
            StructureOceanMonumentPieces.RoomDefinition var19 = (StructureOceanMonumentPieces.RoomDefinition)var18.next();
            int var20 = 0;
            var9 = 0;

            while(true) {
               while(true) {
                  do {
                     if (var20 >= 2 || var9 >= 5) {
                        continue label130;
                     }

                     ++var9;
                     var10 = var1.nextInt(6);
                  } while(!var19.field_175966_c[var10]);

                  var11 = EnumFacing.getFront(var10).getOpposite().getIndex();
                  var19.field_175966_c[var10] = false;
                  var19.field_175965_b[var10].field_175966_c[var11] = false;
                  if (var19.func_175959_a(var25++) && var19.field_175965_b[var10].func_175959_a(var25++)) {
                     ++var20;
                  } else {
                     var19.field_175966_c[var10] = true;
                     var19.field_175965_b[var10].field_175966_c[var11] = true;
                  }
               }
            }
         }

         var24.add(var21);
         var24.add(var22);
         var24.add(var23);
         return var24;
      }

      private void func_175835_e(World var1, Random var2, StructureBoundingBox var3) {
         int var4;
         if (this.func_175818_a(var3, 0, 21, 6, 58)) {
            this.func_175804_a(var1, var3, 0, 0, 21, 6, 0, 57, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 0, 1, 21, 6, 7, 57, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 4, 4, 21, 6, 4, 53, field_175828_a, field_175828_a, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, field_175826_b, field_175826_b, false);
            }

            for(var4 = 23; var4 < 53; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 5, 5, var4, var3);
            }

            this.func_175811_a(var1, field_175824_d, 5, 5, 52, var3);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(var1, var3, 4, 1, 52, 6, 3, 52, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 5, 1, 51, 5, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(var3, 51, 21, 58, 58)) {
            this.func_175804_a(var1, var3, 51, 0, 21, 57, 0, 57, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 51, 1, 21, 57, 7, 57, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 51, 4, 21, 53, 4, 53, field_175828_a, field_175828_a, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 57 - var4, var4 + 1, 21, 57 - var4, var4 + 1, 57 - var4, field_175826_b, field_175826_b, false);
            }

            for(var4 = 23; var4 < 53; var4 += 3) {
               this.func_175811_a(var1, field_175824_d, 52, 5, var4, var3);
            }

            this.func_175811_a(var1, field_175824_d, 52, 5, 52, var3);
            this.func_175804_a(var1, var3, 51, 1, 52, 53, 3, 52, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 52, 1, 51, 52, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(var3, 0, 51, 57, 57)) {
            this.func_175804_a(var1, var3, 7, 0, 51, 50, 0, 57, field_175828_a, field_175828_a, false);
            this.func_175804_a(var1, var3, 7, 1, 51, 50, 10, 57, field_175822_f, field_175822_f, false);

            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, var4 + 1, var4 + 1, 57 - var4, 56 - var4, var4 + 1, 57 - var4, field_175826_b, field_175826_b, false);
            }
         }

      }

      private void func_175840_a(boolean var1, int var2, World var3, Random var4, StructureBoundingBox var5) {
         boolean var6 = true;
         if (this.func_175818_a(var5, var2, 0, var2 + 23, 20)) {
            this.func_175804_a(var3, var5, var2, 0, 0, var2 + 24, 0, 20, field_175828_a, field_175828_a, false);
            this.func_175804_a(var3, var5, var2, 1, 0, var2 + 24, 10, 20, field_175822_f, field_175822_f, false);

            int var7;
            for(var7 = 0; var7 < 4; ++var7) {
               this.func_175804_a(var3, var5, var2 + var7, var7 + 1, var7, var2 + var7, var7 + 1, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(var3, var5, var2 + var7 + 7, var7 + 5, var7 + 7, var2 + var7 + 7, var7 + 5, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(var3, var5, var2 + 17 - var7, var7 + 5, var7 + 7, var2 + 17 - var7, var7 + 5, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(var3, var5, var2 + 24 - var7, var7 + 1, var7, var2 + 24 - var7, var7 + 1, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(var3, var5, var2 + var7 + 1, var7 + 1, var7, var2 + 23 - var7, var7 + 1, var7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var3, var5, var2 + var7 + 8, var7 + 5, var7 + 7, var2 + 16 - var7, var7 + 5, var7 + 7, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(var3, var5, var2 + 4, 4, 4, var2 + 6, 4, 20, field_175828_a, field_175828_a, false);
            this.func_175804_a(var3, var5, var2 + 7, 4, 4, var2 + 17, 4, 6, field_175828_a, field_175828_a, false);
            this.func_175804_a(var3, var5, var2 + 18, 4, 4, var2 + 20, 4, 20, field_175828_a, field_175828_a, false);
            this.func_175804_a(var3, var5, var2 + 11, 8, 11, var2 + 13, 8, 20, field_175828_a, field_175828_a, false);
            this.func_175811_a(var3, field_175824_d, var2 + 12, 9, 12, var5);
            this.func_175811_a(var3, field_175824_d, var2 + 12, 9, 15, var5);
            this.func_175811_a(var3, field_175824_d, var2 + 12, 9, 18, var5);
            var7 = var1 ? var2 + 19 : var2 + 5;
            int var8 = var1 ? var2 + 5 : var2 + 19;

            int var9;
            for(var9 = 20; var9 >= 5; var9 -= 3) {
               this.func_175811_a(var3, field_175824_d, var7, 5, var9, var5);
            }

            for(var9 = 19; var9 >= 7; var9 -= 3) {
               this.func_175811_a(var3, field_175824_d, var8, 5, var9, var5);
            }

            for(var9 = 0; var9 < 4; ++var9) {
               int var10 = var1 ? var2 + (24 - (17 - var9 * 3)) : var2 + 17 - var9 * 3;
               this.func_175811_a(var3, field_175824_d, var10, 5, 5, var5);
            }

            this.func_175811_a(var3, field_175824_d, var8, 5, 5, var5);
            this.func_175804_a(var3, var5, var2 + 11, 1, 12, var2 + 13, 7, 12, field_175828_a, field_175828_a, false);
            this.func_175804_a(var3, var5, var2 + 12, 1, 11, var2 + 12, 7, 13, field_175828_a, field_175828_a, false);
         }

      }

      public MonumentBuilding() {
      }
   }

   public static class WingRoom extends StructureOceanMonumentPieces.Piece {
      private int field_175834_o;
      private static final String __OBFID = "CL_00001973";

      public WingRoom() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.field_175834_o == 0) {
            int var4;
            for(var4 = 0; var4 < 4; ++var4) {
               this.func_175804_a(var1, var3, 10 - var4, 3 - var4, 20 - var4, 12 + var4, 3 - var4, 20, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(var1, var3, 7, 0, 6, 15, 0, 16, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 0, 6, 6, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 16, 0, 6, 16, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 1, 7, 7, 1, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 15, 1, 7, 15, 1, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 7, 1, 6, 9, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 13, 1, 6, 15, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 8, 1, 7, 9, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 13, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 9, 0, 5, 13, 0, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 10, 0, 7, 12, 0, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 8, 0, 10, 8, 0, 12, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 14, 0, 10, 14, 0, 12, field_175827_c, field_175827_c, false);

            for(var4 = 18; var4 >= 7; var4 -= 3) {
               this.func_175811_a(var1, field_175825_e, 6, 3, var4, var3);
               this.func_175811_a(var1, field_175825_e, 16, 3, var4, var3);
            }

            this.func_175811_a(var1, field_175825_e, 10, 0, 10, var3);
            this.func_175811_a(var1, field_175825_e, 12, 0, 10, var3);
            this.func_175811_a(var1, field_175825_e, 10, 0, 12, var3);
            this.func_175811_a(var1, field_175825_e, 12, 0, 12, var3);
            this.func_175811_a(var1, field_175825_e, 8, 3, 6, var3);
            this.func_175811_a(var1, field_175825_e, 14, 3, 6, var3);
            this.func_175811_a(var1, field_175826_b, 4, 2, 4, var3);
            this.func_175811_a(var1, field_175825_e, 4, 1, 4, var3);
            this.func_175811_a(var1, field_175826_b, 4, 0, 4, var3);
            this.func_175811_a(var1, field_175826_b, 18, 2, 4, var3);
            this.func_175811_a(var1, field_175825_e, 18, 1, 4, var3);
            this.func_175811_a(var1, field_175826_b, 18, 0, 4, var3);
            this.func_175811_a(var1, field_175826_b, 4, 2, 18, var3);
            this.func_175811_a(var1, field_175825_e, 4, 1, 18, var3);
            this.func_175811_a(var1, field_175826_b, 4, 0, 18, var3);
            this.func_175811_a(var1, field_175826_b, 18, 2, 18, var3);
            this.func_175811_a(var1, field_175825_e, 18, 1, 18, var3);
            this.func_175811_a(var1, field_175826_b, 18, 0, 18, var3);
            this.func_175811_a(var1, field_175826_b, 9, 7, 20, var3);
            this.func_175811_a(var1, field_175826_b, 13, 7, 20, var3);
            this.func_175804_a(var1, var3, 6, 0, 21, 7, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 15, 0, 21, 16, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175817_a(var1, var3, 11, 2, 16);
         } else if (this.field_175834_o == 1) {
            this.func_175804_a(var1, var3, 9, 3, 18, 13, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 9, 0, 18, 9, 2, 18, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 13, 0, 18, 13, 2, 18, field_175826_b, field_175826_b, false);
            byte var8 = 9;
            byte var5 = 20;
            byte var6 = 5;

            int var7;
            for(var7 = 0; var7 < 2; ++var7) {
               this.func_175811_a(var1, field_175826_b, var8, var6 + 1, var5, var3);
               this.func_175811_a(var1, field_175825_e, var8, var6, var5, var3);
               this.func_175811_a(var1, field_175826_b, var8, var6 - 1, var5, var3);
               var8 = 13;
            }

            this.func_175804_a(var1, var3, 7, 3, 7, 15, 3, 14, field_175826_b, field_175826_b, false);
            var8 = 10;

            for(var7 = 0; var7 < 2; ++var7) {
               this.func_175804_a(var1, var3, var8, 0, 10, var8, 6, 10, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, 0, 12, var8, 6, 12, field_175826_b, field_175826_b, false);
               this.func_175811_a(var1, field_175825_e, var8, 0, 10, var3);
               this.func_175811_a(var1, field_175825_e, var8, 0, 12, var3);
               this.func_175811_a(var1, field_175825_e, var8, 4, 10, var3);
               this.func_175811_a(var1, field_175825_e, var8, 4, 12, var3);
               var8 = 12;
            }

            var8 = 8;

            for(var7 = 0; var7 < 2; ++var7) {
               this.func_175804_a(var1, var3, var8, 0, 7, var8, 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(var1, var3, var8, 0, 14, var8, 2, 14, field_175826_b, field_175826_b, false);
               var8 = 14;
            }

            this.func_175804_a(var1, var3, 8, 3, 8, 8, 3, 13, field_175827_c, field_175827_c, false);
            this.func_175804_a(var1, var3, 14, 3, 8, 14, 3, 13, field_175827_c, field_175827_c, false);
            this.func_175817_a(var1, var3, 11, 5, 13);
         }

         return true;
      }

      public WingRoom(EnumFacing var1, StructureBoundingBox var2, int var3) {
         super(var1, var2);
         this.field_175834_o = var3 & 1;
      }
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00001993";
      static final int[] field_175971_a = new int[EnumFacing.values().length];

      static {
         try {
            field_175971_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_175971_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_175971_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   static class ZDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001988";

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         return var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d;
      }

      private ZDoubleRoomFitHelper() {
      }

      ZDoubleRoomFitHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         StructureOceanMonumentPieces.RoomDefinition var4 = var2;
         if (!var2.field_175966_c[EnumFacing.NORTH.getIndex()] || var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
            var4 = var2.field_175965_b[EnumFacing.SOUTH.getIndex()];
         }

         var4.field_175963_d = true;
         var4.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleZRoom(var1, var4, var3);
      }
   }

   public static class MonumentCoreRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001984";

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         this.func_175819_a(var1, var3, 1, 8, 0, 14, 8, 14, field_175828_a);
         byte var4 = 7;
         IBlockState var5 = field_175826_b;
         this.func_175804_a(var1, var3, 0, var4, 0, 0, var4, 15, var5, var5, false);
         this.func_175804_a(var1, var3, 15, var4, 0, 15, var4, 15, var5, var5, false);
         this.func_175804_a(var1, var3, 1, var4, 0, 15, var4, 0, var5, var5, false);
         this.func_175804_a(var1, var3, 1, var4, 15, 14, var4, 15, var5, var5, false);

         int var6;
         int var7;
         for(var6 = 1; var6 <= 6; ++var6) {
            var5 = field_175826_b;
            if (var6 == 2 || var6 == 6) {
               var5 = field_175828_a;
            }

            for(var7 = 0; var7 <= 15; var7 += 15) {
               this.func_175804_a(var1, var3, var7, var6, 0, var7, var6, 1, var5, var5, false);
               this.func_175804_a(var1, var3, var7, var6, 6, var7, var6, 9, var5, var5, false);
               this.func_175804_a(var1, var3, var7, var6, 14, var7, var6, 15, var5, var5, false);
            }

            this.func_175804_a(var1, var3, 1, var6, 0, 1, var6, 0, var5, var5, false);
            this.func_175804_a(var1, var3, 6, var6, 0, 9, var6, 0, var5, var5, false);
            this.func_175804_a(var1, var3, 14, var6, 0, 14, var6, 0, var5, var5, false);
            this.func_175804_a(var1, var3, 1, var6, 15, 14, var6, 15, var5, var5, false);
         }

         this.func_175804_a(var1, var3, 6, 3, 6, 9, 6, 9, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 7, 4, 7, 8, 5, 8, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), false);

         for(var6 = 3; var6 <= 6; var6 += 3) {
            for(var7 = 6; var7 <= 9; var7 += 3) {
               this.func_175811_a(var1, field_175825_e, var7, var6, 6, var3);
               this.func_175811_a(var1, field_175825_e, var7, var6, 9, var3);
            }
         }

         this.func_175804_a(var1, var3, 5, 1, 6, 5, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 9, 5, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 1, 6, 10, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 1, 9, 10, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 1, 5, 6, 2, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 1, 5, 9, 2, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 1, 10, 6, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 1, 10, 9, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 2, 5, 5, 6, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 2, 10, 5, 6, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 2, 5, 10, 6, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 2, 10, 10, 6, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 7, 1, 5, 7, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 7, 1, 10, 7, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 7, 9, 5, 7, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 10, 7, 9, 10, 7, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 7, 5, 6, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 7, 10, 6, 7, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 7, 5, 14, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 9, 7, 10, 14, 7, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 1, 2, 2, 1, 3, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 1, 2, 3, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 13, 1, 2, 13, 1, 3, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 12, 1, 2, 12, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 1, 12, 2, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 1, 13, 3, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 13, 1, 12, 13, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 12, 1, 13, 12, 1, 13, field_175826_b, field_175826_b, false);
         return true;
      }

      public MonumentCoreRoom() {
      }

      public MonumentCoreRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 2, 2, 2);
      }
   }

   static class XDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private static final String __OBFID = "CL_00001992";

      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1) {
         return var1.field_175966_c[EnumFacing.EAST.getIndex()] && !var1.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d;
      }

      private XDoubleRoomFitHelper() {
      }

      XDoubleRoomFitHelper(StructureOceanMonumentPieces.SwitchEnumFacing var1) {
         this();
      }

      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         var2.field_175963_d = true;
         var2.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleXRoom(var1, var2, var3);
      }
   }

   public static class DoubleYZRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001980";

      public DoubleYZRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 1, 2, 2);
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         StructureOceanMonumentPieces.RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var5 = this.field_175830_k;
         StructureOceanMonumentPieces.RoomDefinition var6 = var4.field_175965_b[EnumFacing.UP.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var7 = var5.field_175965_b[EnumFacing.UP.getIndex()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 0, 8, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 8, 1, 6, 8, 7, field_175828_a);
         }

         if (var6.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 8, 8, 6, 8, 14, field_175828_a);
         }

         int var8;
         IBlockState var9;
         for(var8 = 1; var8 <= 7; ++var8) {
            var9 = field_175826_b;
            if (var8 == 2 || var8 == 6) {
               var9 = field_175828_a;
            }

            this.func_175804_a(var1, var3, 0, var8, 0, 0, var8, 15, var9, var9, false);
            this.func_175804_a(var1, var3, 7, var8, 0, 7, var8, 15, var9, var9, false);
            this.func_175804_a(var1, var3, 1, var8, 0, 6, var8, 0, var9, var9, false);
            this.func_175804_a(var1, var3, 1, var8, 15, 6, var8, 15, var9, var9, false);
         }

         for(var8 = 1; var8 <= 7; ++var8) {
            var9 = field_175827_c;
            if (var8 == 2 || var8 == 6) {
               var9 = field_175825_e;
            }

            this.func_175804_a(var1, var3, 3, var8, 7, 4, var8, 8, var9, var9, false);
         }

         if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 1, 3, 7, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 15, 4, 2, 15, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 11, 0, 2, 12, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 1, 11, 7, 2, 12, field_175822_f, field_175822_f, false);
         }

         if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 5, 0, 4, 6, 0, field_175822_f, field_175822_f, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 5, 3, 7, 6, 4, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 5, 4, 2, 6, 4, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 2, 6, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 5, 6, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (var7.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 5, 3, 0, 6, 4, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 1, 4, 2, 2, 4, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 2, 1, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 5, 1, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (var6.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 5, 15, 4, 6, 15, field_175822_f, field_175822_f, false);
         }

         if (var6.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 5, 11, 0, 6, 12, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 1, 4, 10, 2, 4, 13, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 10, 1, 3, 10, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 1, 1, 13, 1, 3, 13, field_175826_b, field_175826_b, false);
         }

         if (var6.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 5, 11, 7, 6, 12, field_175822_f, field_175822_f, false);
            this.func_175804_a(var1, var3, 5, 4, 10, 6, 4, 13, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 10, 6, 3, 10, field_175826_b, field_175826_b, false);
            this.func_175804_a(var1, var3, 6, 1, 13, 6, 3, 13, field_175826_b, field_175826_b, false);
         }

         return true;
      }

      public DoubleYZRoom() {
      }
   }

   public static class DoubleZRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001979";

      public DoubleZRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 1, 1, 2);
      }

      public DoubleZRoom() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         StructureOceanMonumentPieces.RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var5 = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 0, 8, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var5.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 4, 1, 6, 4, 7, field_175828_a);
         }

         if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 4, 8, 6, 4, 14, field_175828_a);
         }

         this.func_175804_a(var1, var3, 0, 3, 0, 0, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 3, 0, 7, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 15, 6, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 2, 0, 0, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 7, 2, 0, 7, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 1, 2, 0, 7, 2, 0, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 1, 2, 15, 6, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(var1, var3, 0, 1, 0, 0, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 1, 0, 7, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 0, 7, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 15, 6, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 1, 1, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 1, 1, 6, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 1, 1, 3, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 3, 1, 6, 3, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 13, 1, 1, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 1, 13, 6, 1, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 13, 1, 3, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 6, 3, 13, 6, 3, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 1, 6, 2, 3, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 6, 5, 3, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 1, 9, 2, 3, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 1, 9, 5, 3, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 2, 6, 4, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 3, 2, 9, 4, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 2, 2, 7, 2, 2, 8, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 5, 2, 7, 5, 2, 8, field_175826_b, field_175826_b, false);
         this.func_175811_a(var1, field_175825_e, 2, 2, 5, var3);
         this.func_175811_a(var1, field_175825_e, 5, 2, 5, var3);
         this.func_175811_a(var1, field_175825_e, 2, 2, 10, var3);
         this.func_175811_a(var1, field_175825_e, 5, 2, 10, var3);
         this.func_175811_a(var1, field_175826_b, 2, 3, 5, var3);
         this.func_175811_a(var1, field_175826_b, 5, 3, 5, var3);
         this.func_175811_a(var1, field_175826_b, 2, 3, 10, var3);
         this.func_175811_a(var1, field_175826_b, 5, 3, 10, var3);
         if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 1, 3, 7, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 15, 4, 2, 15, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(var1, var3, 0, 1, 11, 0, 2, 12, field_175822_f, field_175822_f, false);
         }

         if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(var1, var3, 7, 1, 11, 7, 2, 12, field_175822_f, field_175822_f, false);
         }

         return true;
      }
   }

   interface MonumentRoomFitHelper {
      boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition var1);

      StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3);
   }

   public static class SimpleTopRoom extends StructureOceanMonumentPieces.Piece {
      private static final String __OBFID = "CL_00001974";

      public SimpleTopRoom() {
      }

      public SimpleTopRoom(EnumFacing var1, StructureOceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, var1, var2, 1, 1, 1);
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(var1, var3, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(var1, var3, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         for(int var4 = 1; var4 <= 6; ++var4) {
            for(int var5 = 1; var5 <= 6; ++var5) {
               if (var2.nextInt(3) != 0) {
                  int var6 = 2 + (var2.nextInt(4) == 0 ? 0 : 1);
                  this.func_175804_a(var1, var3, var4, var6, var5, var4, 3, var5, Blocks.sponge.getStateFromMeta(1), Blocks.sponge.getStateFromMeta(1), false);
               }
            }
         }

         this.func_175804_a(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(var1, var3, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
         this.func_175804_a(var1, var3, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(var1, var3, 3, 1, 0, 4, 2, 0, field_175822_f, field_175822_f, false);
         }

         return true;
      }
   }
}
