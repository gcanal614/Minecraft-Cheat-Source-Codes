package me.injusttice.neutron.utils.render;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glColor4ub;

public class DrawUtil extends GuiScreen {

  public static DrawUtil instance = new DrawUtil();

  public static void glColour(final int color) {
    glColor4ub((byte) (color >> 16 & 0xFF),
            (byte) (color >> 8 & 0xFF),
            (byte) (color & 0xFF),
            (byte) (color >> 24 & 0xFF));
  }

  public static void drawRoundedRect(double x, double y, double x1, double y1, double radius, int color) {
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    x *= 2.0D;
    y *= 2.0D;
    x1 *= 2.0D;
    y1 *= 2.0D;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    setColor(color);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    int i;
    for (i = 0; i <= 90; i += 3)
      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
    for (i = 90; i <= 180; i += 3)
      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
    for (i = 0; i <= 90; i += 3)
      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
    for (i = 90; i <= 180; i += 3)
      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
  }

  public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float radius, float borderSize, int borderC, int insideC) {
    drawRoundedRect(x, y, x1, y1, radius, borderC);
    drawRoundedRect((x + borderSize), (y + borderSize), (x1 - borderSize), (y1 - borderSize), radius, insideC);
  }

  public static void setColor(int color) {
    float a = (color >> 24 & 0xFF) / 255.0F;
    float r = (color >> 16 & 0xFF) / 255.0F;
    float g = (color >> 8 & 0xFF) / 255.0F;
    float b = (color & 0xFF) / 255.0F;
    GL11.glColor4f(r, g, b, a);
  }
}
