package com.zerosense.Utils;


import com.zerosense.Events.Event;

public class EventRender3D extends Event<EventRender3D> {
  private static float partialTicks;
  public float renderPartialTicks;
  
  public EventRender3D(float partialTicks, float renderPartialTicks) {
    this.renderPartialTicks = renderPartialTicks;
    this.partialTicks = partialTicks;
  }
  
  public static float getPartialTicks() {
    return partialTicks;
  }
  
  public void setPartialTicks(float partialTicks) {
    this.partialTicks = partialTicks;
  }
}
