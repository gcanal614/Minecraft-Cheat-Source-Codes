package com.zerosense.Utils;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public class JColor extends Color {
  private static final long serialVersionUID = 1L;
  
  public JColor(int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt1, paramInt2, paramInt3);
  }
  
  public JColor(JColor paramJColor, int paramInt) {
    super(paramJColor.getRed(), paramJColor.getGreen(), paramJColor.getBlue(), paramInt);
  }
  
  public float getBrightness() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[2];
  }
  
  public float getHue() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[0];
  }
  
  public void glColor() {
    GlStateManager.color(getRed() / 255.0F, getGreen() / 255.0F, getBlue() / 255.0F, getAlpha() / 255.0F);
  }
  
  public static JColor fromHSB(float paramFloat1, float paramFloat2, float paramFloat3) {
    return new JColor(Color.getHSBColor(paramFloat1, paramFloat2, paramFloat3));
  }
  
  public JColor(int paramInt) {
    super(paramInt);
  }
  
  public float getSaturation() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[1];
  }
  
  public JColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public JColor(int paramInt, boolean paramBoolean) {
    super(paramInt, paramBoolean);
  }
  
  public JColor(Color paramColor) {
    super(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), paramColor.getAlpha());
  }
}
