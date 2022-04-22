package wtf.astronicy.API.events.player;

import wtf.astronicy.API.events.Event;

public final class MoveEvent implements Event {
   public double x;
   public double y;
   public double z;

   public MoveEvent(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double getX() {
      return x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return z;
   }

   public void setZ(double z) {
      this.z = z;
   }
}
