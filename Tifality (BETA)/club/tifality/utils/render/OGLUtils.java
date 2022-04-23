/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.render;

import club.tifality.utils.render.LockedResolution;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;

public final class OGLUtils {
    private static final FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer(4);
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
    private static final float[] BUFFER = new float[3];

    private OGLUtils() {
    }

    public static void startBlending() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void endBlending() {
        GL11.glDisable(3042);
    }

    public static void startScissorBox(ScaledResolution sr, int x, int y, int width, int height) {
        int sf = sr.getScaleFactor();
        GL11.glEnable(3089);
        GL11.glScissor(x * sf, (sr.getScaledHeight() - (y + height)) * sf, width * sf, height * sf);
    }

    public static void startScissorBox(LockedResolution lr, int x, int y, int width, int height) {
        int sf = 2;
        GL11.glEnable(3089);
        GL11.glScissor(x * sf, (lr.getHeight() - (y + height)) * sf, width * sf, height * sf);
    }

    public static void endScissorBox() {
        GL11.glDisable(3089);
    }

    public static void enableBlending() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void disableTexture2D() {
        GL11.glDisable(3553);
    }

    public static void enableTexture2D() {
        GL11.glEnable(3553);
    }

    public static void enableDepth() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
    }

    public static void disableDepth() {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
    }

    public static void preDraw(int color, int mode) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OGLUtils.color(color);
        GL11.glBegin(mode);
    }

    public static void postDraw() {
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public static void color(int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }

    public static void disableBlending() {
        GL11.glDisable(3042);
    }
}

