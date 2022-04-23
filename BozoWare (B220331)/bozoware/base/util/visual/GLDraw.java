// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import org.lwjgl.opengl.GL11;

public class GLDraw
{
    public static void glFilledQuad(final double x, final double y, final double width, final double height, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        RenderUtil.setColor(color);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void glFilledEllipse(final double x, final double y, final float radius, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2832);
        GL11.glPointSize(radius);
        GL11.glBegin(0);
        RenderUtil.setColor(color);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glEnable(3553);
    }
}
