package optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockPosM extends BlockPos {
   private BlockPosM[] facings;
   private int my;
   private int mx;
   private int mz;
   private boolean needsUpdate;
   private int level;

   public int getX() {
      return this.mx;
   }

   public BlockPos offset(EnumFacing var1, int var2) {
      return var2 == 1 ? this.offset(var1) : super.offset(var1, var2);
   }

   public void setXyz(double var1, double var3, double var5) {
      this.setXyz(MathHelper.floor_double(var1), MathHelper.floor_double(var3), MathHelper.floor_double(var5));
   }

   public BlockPos offset(EnumFacing var1) {
      if (this.level <= 0) {
         return super.offset(var1, 1);
      } else {
         if (this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
         }

         if (this.needsUpdate) {
            this.update();
         }

         int var2 = var1.getIndex();
         BlockPosM var3 = this.facings[var2];
         if (var3 == null) {
            int var4 = this.mx + var1.getFrontOffsetX();
            int var5 = this.my + var1.getFrontOffsetY();
            int var6 = this.mz + var1.getFrontOffsetZ();
            var3 = new BlockPosM(var4, var5, var6, this.level - 1);
            this.facings[var2] = var3;
         }

         return var3;
      }
   }

   public BlockPos getImmutable() {
      return new BlockPos(this.getX(), this.getY(), this.getZ());
   }

   public int getY() {
      return this.my;
   }

   public BlockPosM(double var1, double var3, double var5) {
      this(MathHelper.floor_double(var1), MathHelper.floor_double(var3), MathHelper.floor_double(var5));
   }

   private void update() {
      for(int var1 = 0; var1 < 6; ++var1) {
         BlockPosM var2 = this.facings[var1];
         if (var2 != null) {
            EnumFacing var3 = EnumFacing.VALUES[var1];
            int var4 = this.mx + var3.getFrontOffsetX();
            int var5 = this.my + var3.getFrontOffsetY();
            int var6 = this.mz + var3.getFrontOffsetZ();
            var2.setXyz(var4, var5, var6);
         }
      }

      this.needsUpdate = false;
   }

   public BlockPosM(int var1, int var2, int var3, int var4) {
      super(0, 0, 0);
      this.mx = var1;
      this.my = var2;
      this.mz = var3;
      this.level = var4;
   }

   public BlockPosM(int var1, int var2, int var3) {
      this(var1, var2, var3, 0);
   }

   public void setXyz(int var1, int var2, int var3) {
      this.mx = var1;
      this.my = var2;
      this.mz = var3;
      this.needsUpdate = true;
   }

   public int getZ() {
      return this.mz;
   }

   public static Iterable getAllInBoxMutable(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new Iterable(var2, var3) {
         private final BlockPos val$posTo;
         private final BlockPos val$posFrom;

         {
            this.val$posFrom = var1;
            this.val$posTo = var2;
         }

         public Iterator iterator() {
            return new AbstractIterator(this, this.val$posFrom, this.val$posTo) {
               private final BlockPos val$posFrom;
               private final BlockPos val$posTo;
               private BlockPosM theBlockPosM;
               final <undefinedtype> this$1;

               protected Object computeNext() {
                  return this.computeNext0();
               }

               {
                  this.this$1 = var1;
                  this.val$posFrom = var2;
                  this.val$posTo = var3;
                  this.theBlockPosM = null;
               }

               protected BlockPosM computeNext0() {
                  if (this.theBlockPosM == null) {
                     this.theBlockPosM = new BlockPosM(this.val$posFrom.getX(), this.val$posFrom.getY(), this.val$posFrom.getZ(), 3);
                     return this.theBlockPosM;
                  } else if (this.theBlockPosM.equals(this.val$posTo)) {
                     return (BlockPosM)this.endOfData();
                  } else {
                     int var1 = this.theBlockPosM.getX();
                     int var2 = this.theBlockPosM.getY();
                     int var3 = this.theBlockPosM.getZ();
                     if (var1 < this.val$posTo.getX()) {
                        ++var1;
                     } else if (var2 < this.val$posTo.getY()) {
                        var1 = this.val$posFrom.getX();
                        ++var2;
                     } else if (var3 < this.val$posTo.getZ()) {
                        var1 = this.val$posFrom.getX();
                        var2 = this.val$posFrom.getY();
                        ++var3;
                     }

                     this.theBlockPosM.setXyz(var1, var2, var3);
                     return this.theBlockPosM;
                  }
               }
            };
         }
      };
   }
}
