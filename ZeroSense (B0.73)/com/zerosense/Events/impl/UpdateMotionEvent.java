package com.zerosense.Events.impl;


import com.zerosense.Events.Event;

public class UpdateMotionEvent extends Event {
  final Type type;
  
  public static float yaw;
  
 public static float pitch;
  
  public UpdateMotionEvent(Type paramType, float paramFloat1, float paramFloat2) {
    this.yaw = paramFloat1;
    this.pitch = paramFloat2;
    this.type = paramType;
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
  
  public enum Type {
    PRE, POST;
  }
}
