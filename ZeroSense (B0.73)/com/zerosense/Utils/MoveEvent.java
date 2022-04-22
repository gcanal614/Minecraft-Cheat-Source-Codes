package com.zerosense.Utils;

import com.zerosense.Events.Event;

public class MoveEvent extends Event {
  public double x;
  public double y;
  public double z;
  public static float yaw;
  
  public MoveEvent(float paramFloat) {
    this.yaw = paramFloat;
  }
  
  public float getYaw() {
    return this.yaw;
  }
  
  public void setYaw(float paramFloat) {
    yaw = paramFloat;
  }
}
