package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute field_180373_a;
   private final String unlocalizedName;
   private boolean shouldWatch;
   private final double defaultValue;
   private static final String __OBFID = "CL_00001565";

   public BaseAttribute setShouldWatch(boolean var1) {
      this.shouldWatch = var1;
      return this;
   }

   public double getDefaultValue() {
      return this.defaultValue;
   }

   public IAttribute func_180372_d() {
      return this.field_180373_a;
   }

   public String getAttributeUnlocalizedName() {
      return this.unlocalizedName;
   }

   public int hashCode() {
      return this.unlocalizedName.hashCode();
   }

   public boolean getShouldWatch() {
      return this.shouldWatch;
   }

   protected BaseAttribute(IAttribute var1, String var2, double var3) {
      this.field_180373_a = var1;
      this.unlocalizedName = var2;
      this.defaultValue = var3;
      if (var2 == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   public boolean equals(Object var1) {
      return var1 instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)var1).getAttributeUnlocalizedName());
   }
}
