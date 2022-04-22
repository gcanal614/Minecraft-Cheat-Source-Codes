package me.injusttice.neutron.utils.render;

import java.awt.Rectangle;
import org.lwjgl.opengl.GL11;

public class R2DUtils {

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawRect(Rectangle rectangle, int color) {
        R2DUtils.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, int color) {
        R2DUtils.enableGL2D();
        R2DUtils.glColor(color);
        R2DUtils.drawRect(x2, y2, x1, y1);
        R2DUtils.disableGL2D();
    }

    public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        R2DUtils.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        R2DUtils.glColor(topColor);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        R2DUtils.glColor(bottomColor);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        R2DUtils.disableGL2D();
    }

    public static void drawGradientRect(double x2, double y2, double x22, double y22, int col1, int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        R2DUtils.glColor(col1);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        R2DUtils.glColor(col2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, float r2, float g2, float b2, float a2) {
        R2DUtils.enableGL2D();
        GL11.glColor4f(r2, g2, b2, a2);
        R2DUtils.drawRect(x2, y2, x1, y1);
        R2DUtils.disableGL2D();
    }

    public static void drawRect(float x2, float y2, float x1, float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
    }
}

