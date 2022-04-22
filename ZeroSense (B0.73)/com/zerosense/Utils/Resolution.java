package com.zerosense.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class Resolution {
  public static Resolution INSTANCE;
  
  public int width;
  
  public int height;
  
  public int scale;
  
  public Resolution(Minecraft paramMinecraft) {
    this.width = paramMinecraft.displayWidth;
    this.height = paramMinecraft.displayHeight;
    this.scale = 1;
    boolean bool = paramMinecraft.isUnicode();
    int i = paramMinecraft.gameSettings.guiScale;
    if (i == 0)
      i = 1000; 
    while (this.scale < i && this.width / (this.scale + 1) >= 320 && this.height / (this.scale + 1) >= 240)
      this.scale++; 
    if (bool && this.scale % 2 != 0 && this.scale != 1)
      this.scale--; 
    this.width /= this.scale;
    this.height = this.width / this.scale;
  }
  
  public void update(Minecraft paramMinecraft) {
    this.width = paramMinecraft.displayWidth;
    this.height = paramMinecraft.displayHeight;
    this.scale = 1;
    boolean bool = paramMinecraft.isUnicode();
    int i = paramMinecraft.gameSettings.guiScale;
    if (i == 0)
      i = 1000; 
    while (this.scale < i && this.width / (this.scale + 1) >= 320 && this.height / (this.scale + 1) >= 240)
      this.scale++; 
    if (bool && this.scale % 2 != 0 && this.scale != 1)
      this.scale--; 
    double d1 = this.width / this.scale;
    double d2 = this.height / this.scale;
    this.width = MathHelper.ceiling_double_int(d1);
    this.height = MathHelper.ceiling_double_int(d2);
  }
  
  public static Resolution getResolution() {
    if (INSTANCE == null)
      INSTANCE = new Resolution(Minecraft.getMinecraft()); 
    INSTANCE.update(Minecraft.getMinecraft());
    return INSTANCE;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public int getScale() {
    return this.scale;
  }
  
  public void setWidth(int paramInt) {
    this.width = paramInt;
  }
  
  public void setHeight(int paramInt) {
    this.height = paramInt;
  }
  
  public void setScale(int paramInt) {
    this.scale = paramInt;
  }
}
