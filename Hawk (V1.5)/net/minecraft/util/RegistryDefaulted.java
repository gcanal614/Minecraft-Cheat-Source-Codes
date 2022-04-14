package net.minecraft.util;

public class RegistryDefaulted extends RegistrySimple {
   private static final String __OBFID = "CL_00001198";
   private final Object defaultObject;

   public RegistryDefaulted(Object var1) {
      this.defaultObject = var1;
   }

   public Object getObject(Object var1) {
      Object var2 = super.getObject(var1);
      return var2 == null ? this.defaultObject : var2;
   }
}
