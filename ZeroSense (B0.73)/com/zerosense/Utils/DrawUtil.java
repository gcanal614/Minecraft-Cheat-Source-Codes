package com.zerosense.Utils;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class DrawUtil extends GuiScreen {
  public static DrawUtil instance = new DrawUtil();

  public static void setColor(int paramInt) {
    float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    GL11.glColor4f(f2, f3, f4, f1);
  }

  public static void drawBorderedRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt1, int paramInt2) {
    drawRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramInt1);
    drawRoundedRect((paramFloat1 + 0.5F), (paramFloat2 + 0.5F), (paramFloat3 - 0.5F), (paramFloat4 - 0.5F), paramFloat5, paramInt2);
  }

  public static void drawBorderedRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt1, int paramInt2) {
    drawRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramInt1);
    drawRoundedRect((paramFloat1 + paramFloat6), (paramFloat2 + paramFloat6), (paramFloat3 - paramFloat6), (paramFloat4 - paramFloat6), paramFloat5, paramInt2);
  }

  public static void drawRoundedRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt) {
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramDouble1 *= 2.0D;
    paramDouble2 *= 2.0D;
    paramDouble3 *= 2.0D;
    paramDouble4 *= 2.0D;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    setColor(paramInt);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D);
    for (b = 90; b <= '`'; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D);
    b = 0;
    if ((0x63 & 0xFFFFFF9C) < 0)
      return;
    while (b <= 90) {
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5);
      b += 3;
    }
    for (b = 90; b <= '3'; b += 3)
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
  }
}
