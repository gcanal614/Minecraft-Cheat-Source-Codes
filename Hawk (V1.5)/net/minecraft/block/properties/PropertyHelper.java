package net.minecraft.block.properties;

import com.google.common.base.Objects;

public abstract class PropertyHelper implements IProperty {
   private final String name;
   private final Class valueClass;
   private static final String __OBFID = "CL_00002018";

   public Class getValueClass() {
      return this.valueClass;
   }

   public String getName() {
      return this.name;
   }

   protected PropertyHelper(String var1, Class var2) {
      this.valueClass = var2;
      this.name = var1;
   }

   public int hashCode() {
      return 31 * this.valueClass.hashCode() + this.name.hashCode();
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", this.getAllowedValues()).toString();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         PropertyHelper var2 = (PropertyHelper)var1;
         return this.valueClass.equals(var2.valueClass) && this.name.equals(var2.name);
      } else {
         return false;
      }
   }
}
