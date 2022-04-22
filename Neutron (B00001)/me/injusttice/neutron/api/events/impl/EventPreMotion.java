package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventPreMotion extends Event {
  public float yaw;
  
  public float pitch;
  
  public boolean ground;
  
  public double x;
  
  public double y;
  
  public double z;
  
  public EventPreMotion(float yaw, float pitch, boolean ground, double x, double y, double z) {
    this.yaw = yaw;
    this.pitch = pitch;
    this.ground = ground;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public float getYaw() {
    return this.yaw;
  }
  
  public void setYaw(float yaw) {
    this.yaw = yaw;
  }
  
  public float getPitch() {
    return this.pitch;
  }
  
  public void setPitch(float pitch) {
    this.pitch = pitch;
  }
  
  public boolean isOnGround() {
    return this.ground;
  }
  
  public void setOnGround(boolean ground) {
    this.ground = ground;
  }
  
  public double getX() {
    return this.x;
  }
  
  public void setX(double x) {
    this.x = x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public void setY(double y) {
    this.y = y;
  }
  
  public double getZ() {
    return this.z;
  }
  
  public void setZ(double z) {
    this.z = z;
  }
}
