package com.zerosense.Events.impl;

import com.zerosense.Events.Event;

public class RotationEvent extends Event {
  public static float yaw;
  
 public static float pitch;
  
  public RotationEvent(float paramFloat1, float paramFloat2) {
    this.yaw = paramFloat1;
    this.pitch = paramFloat2;
  }
  
  public float getYaw() {
    return this.yaw;
  }
  
  public void setYaw(float paramFloat) {
    yaw = paramFloat;
  }
  
  public float getPitch() {
    return this.pitch;
  }
  
  public void setPitch(float paramFloat) {
    pitch = paramFloat;
  }
}
