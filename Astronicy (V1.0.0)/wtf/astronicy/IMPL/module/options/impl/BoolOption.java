package wtf.astronicy.IMPL.module.options.impl;

import java.util.function.Supplier;

import wtf.astronicy.IMPL.module.options.Option;

public final class BoolOption extends Option {
   public BoolOption(String label, Boolean defaultValue, Supplier supplier) {
      super(label, defaultValue, supplier);
   }

   public BoolOption(String label, Boolean defaultValue) {
      super(label, defaultValue, () -> {
         return true;
      });
   }

   public Boolean getValue() {
      return (Boolean)super.getValue() && this.check();
   }
}
