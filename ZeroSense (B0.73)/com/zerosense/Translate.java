package com.zerosense;

import com.zerosense.Utils.AnimationUtils;

public final class Translate {
   private static double x;
   private static double y;

   public Translate(float x, float y) {
      this.x = (double)x;
      this.y = (double)y;
   }

   public static final void interpolate(double targetX, double targetY, double smoothing) {
      x = AnimationUtils.animate(targetX, x, smoothing);
      y = AnimationUtils.animate(targetY, y, smoothing);
   }

   public static double getX() {
      return x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public static double getY() {
      return y;
   }

   public void setY(double y) {
      this.y = y;
   }
}
