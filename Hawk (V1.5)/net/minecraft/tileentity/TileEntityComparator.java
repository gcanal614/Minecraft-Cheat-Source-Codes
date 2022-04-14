package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {
   private int outputSignal;
   private static final String __OBFID = "CL_00000349";

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.outputSignal = var1.getInteger("OutputSignal");
   }

   public void setOutputSignal(int var1) {
      this.outputSignal = var1;
   }

   public int getOutputSignal() {
      return this.outputSignal;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setInteger("OutputSignal", this.outputSignal);
   }
}
