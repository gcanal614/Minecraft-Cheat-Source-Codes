package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public class LockCode {
   public static final LockCode EMPTY_CODE = new LockCode("");
   private final String lock;
   private static final String __OBFID = "CL_00002260";

   public void toNBT(NBTTagCompound var1) {
      var1.setString("Lock", this.lock);
   }

   public static LockCode fromNBT(NBTTagCompound var0) {
      if (var0.hasKey("Lock", 8)) {
         String var1 = var0.getString("Lock");
         return new LockCode(var1);
      } else {
         return EMPTY_CODE;
      }
   }

   public String getLock() {
      return this.lock;
   }

   public LockCode(String var1) {
      this.lock = var1;
   }

   public boolean isEmpty() {
      return this.lock == null || this.lock.isEmpty();
   }
}
