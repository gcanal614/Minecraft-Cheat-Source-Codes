package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
   protected boolean tileEntityInvalid;
   protected World worldObj;
   protected BlockPos pos;
   private int blockMetadata;
   private static Map classToNameMap = Maps.newHashMap();
   private static Map nameToClassMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00000340";
   private static final Logger logger = LogManager.getLogger();
   protected Block blockType;

   public void writeToNBT(NBTTagCompound var1) {
      String var2 = (String)classToNameMap.get(this.getClass());
      if (var2 == null) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append(this.getClass()).append(" is missing a mapping! This is a bug!")));
      } else {
         var1.setString("id", var2);
         var1.setInteger("x", this.pos.getX());
         var1.setInteger("y", this.pos.getY());
         var1.setInteger("z", this.pos.getZ());
      }
   }

   public void validate() {
      this.tileEntityInvalid = false;
   }

   public void setWorldObj(World var1) {
      this.worldObj = var1;
   }

   public void addInfoToCrashReport(CrashReportCategory var1) {
      var1.addCrashSectionCallable("Name", new Callable(this) {
         final TileEntity this$0;
         private static final String __OBFID = "CL_00000341";

         {
            this.this$0 = var1;
         }

         public String call() {
            return String.valueOf((new StringBuilder(String.valueOf((String)TileEntity.access$0().get(this.this$0.getClass())))).append(" // ").append(this.this$0.getClass().getCanonicalName()));
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      if (this.worldObj != null) {
         CrashReportCategory.addBlockInfo(var1, this.pos, this.getBlockType(), this.getBlockMetadata());
         var1.addCrashSectionCallable("Actual block type", new Callable(this) {
            final TileEntity this$0;
            private static final String __OBFID = "CL_00000343";

            public Object call() throws Exception {
               return this.call();
            }

            {
               this.this$0 = var1;
            }

            public String call() {
               int var1 = Block.getIdFromBlock(this.this$0.worldObj.getBlockState(this.this$0.pos).getBlock());

               try {
                  return String.format("ID #%d (%s // %s)", var1, Block.getBlockById(var1).getUnlocalizedName(), Block.getBlockById(var1).getClass().getCanonicalName());
               } catch (Throwable var3) {
                  return String.valueOf((new StringBuilder("ID #")).append(var1));
               }
            }
         });
         var1.addCrashSectionCallable("Actual block data value", new Callable(this) {
            private static final String __OBFID = "CL_00000344";
            final TileEntity this$0;

            public String call() {
               IBlockState var1 = this.this$0.worldObj.getBlockState(this.this$0.pos);
               int var2 = var1.getBlock().getMetaFromState(var1);
               if (var2 < 0) {
                  return String.valueOf((new StringBuilder("Unknown? (Got ")).append(var2).append(")"));
               } else {
                  String var3 = String.format("%4s", Integer.toBinaryString(var2)).replace(" ", "0");
                  return String.format("%1$d / 0x%1$X / 0b%2$s", var2, var3);
               }
            }

            {
               this.this$0 = var1;
            }

            public Object call() throws Exception {
               return this.call();
            }
         });
      }

   }

   public Packet getDescriptionPacket() {
      return null;
   }

   public TileEntity() {
      this.pos = BlockPos.ORIGIN;
      this.blockMetadata = -1;
   }

   public void markDirty() {
      if (this.worldObj != null) {
         IBlockState var1 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var1.getBlock().getMetaFromState(var1);
         this.worldObj.func_175646_b(this.pos, this);
         if (this.getBlockType() != Blocks.air) {
            this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
         }
      }

   }

   public Block getBlockType() {
      if (this.blockType == null) {
         this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
      }

      return this.blockType;
   }

   public int getBlockMetadata() {
      if (this.blockMetadata == -1) {
         IBlockState var1 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var1.getBlock().getMetaFromState(var1);
      }

      return this.blockMetadata;
   }

   static Map access$0() {
      return classToNameMap;
   }

   public World getWorld() {
      return this.worldObj;
   }

   public boolean isInvalid() {
      return this.tileEntityInvalid;
   }

   public boolean receiveClientEvent(int var1, int var2) {
      return false;
   }

   public void updateContainingBlockInfo() {
      this.blockType = null;
      this.blockMetadata = -1;
   }

   public void readFromNBT(NBTTagCompound var1) {
      this.pos = new BlockPos(var1.getInteger("x"), var1.getInteger("y"), var1.getInteger("z"));
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public static TileEntity createAndLoadEntity(NBTTagCompound var0) {
      TileEntity var1 = null;

      try {
         Class var2 = (Class)nameToClassMap.get(var0.getString("id"));
         if (var2 != null) {
            var1 = (TileEntity)var2.newInstance();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      if (var1 != null) {
         var1.readFromNBT(var0);
      } else {
         logger.warn(String.valueOf((new StringBuilder("Skipping BlockEntity with id ")).append(var0.getString("id"))));
      }

      return var1;
   }

   static {
      addMapping(TileEntityFurnace.class, "Furnace");
      addMapping(TileEntityChest.class, "Chest");
      addMapping(TileEntityEnderChest.class, "EnderChest");
      addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
      addMapping(TileEntityDispenser.class, "Trap");
      addMapping(TileEntityDropper.class, "Dropper");
      addMapping(TileEntitySign.class, "Sign");
      addMapping(TileEntityMobSpawner.class, "MobSpawner");
      addMapping(TileEntityNote.class, "Music");
      addMapping(TileEntityPiston.class, "Piston");
      addMapping(TileEntityBrewingStand.class, "Cauldron");
      addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
      addMapping(TileEntityEndPortal.class, "Airportal");
      addMapping(TileEntityCommandBlock.class, "Control");
      addMapping(TileEntityBeacon.class, "Beacon");
      addMapping(TileEntitySkull.class, "Skull");
      addMapping(TileEntityDaylightDetector.class, "DLDetector");
      addMapping(TileEntityHopper.class, "Hopper");
      addMapping(TileEntityComparator.class, "Comparator");
      addMapping(TileEntityFlowerPot.class, "FlowerPot");
      addMapping(TileEntityBanner.class, "Banner");
   }

   private static void addMapping(Class var0, String var1) {
      if (nameToClassMap.containsKey(var1)) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Duplicate id: ")).append(var1)));
      } else {
         nameToClassMap.put(var1, var0);
         classToNameMap.put(var0, var1);
      }
   }

   public boolean hasWorldObj() {
      return this.worldObj != null;
   }

   public void invalidate() {
      this.tileEntityInvalid = true;
   }

   public double getMaxRenderDistanceSquared() {
      return 4096.0D;
   }

   public void setPos(BlockPos var1) {
      this.pos = var1;
   }

   public double getDistanceSq(double var1, double var3, double var5) {
      double var7 = (double)this.pos.getX() + 0.5D - var1;
      double var9 = (double)this.pos.getY() + 0.5D - var3;
      double var11 = (double)this.pos.getZ() + 0.5D - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }
}
