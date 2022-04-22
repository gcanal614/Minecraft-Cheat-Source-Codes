package wtf.astronicy.IMPL.module.options.impl;

import java.awt.Color;
import java.util.function.Supplier;

import wtf.astronicy.IMPL.module.options.Option;

public class ColorOption extends Option {
   public ColorOption(String label, Color defaultValue, Supplier supplier) {
      super(label, defaultValue, supplier);
   }

   public ColorOption(String label, Color defaultValue) {
      super(label, defaultValue, () -> {
         return true;
      });
   }
}
