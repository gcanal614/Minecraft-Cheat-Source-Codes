package wtf.astronicy.IMPL.module.options.impl;

import java.util.function.Supplier;

import wtf.astronicy.IMPL.module.options.Option;

public final class DoubleOption extends Option {
   private final double minValue;
   private final double maxValue;
   private final double increment;

   public DoubleOption(String label, double defaultValue, Supplier supplier, double minValue, double maxValue, double increment) {
      super(label, defaultValue, supplier);
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.increment = increment;
   }

   public DoubleOption(String label, double defaultValue, double minValue, double maxValue, double increment) {
      super(label, defaultValue, () -> {
         return true;
      });
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.increment = increment;
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public double getIncrement() {
      return this.increment;
   }
}
