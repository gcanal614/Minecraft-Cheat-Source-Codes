/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.hero.clickgui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public class McOutlineHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void start() {
        McOutlineHelper.mc.renderGlobal.getEntityOutlineFramebuffer().bindFramebuffer(false);
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
    }

    public static void stop() {
        McOutlineHelper.mc.renderGlobal.getEntityOutlineFramebuffer().unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(false);
    }

    public static void render() {
        McOutlineHelper.stop();
        ScaledResolution sr = ScaledResolution.INSTANCE;
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        Framebuffer fb = McOutlineHelper.mc.renderGlobal.getEntityOutlineShader().getFramebufferRaw("final");
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        McOutlineHelper.mc.renderGlobal.getEntityOutlineFramebuffer().bindFramebufferTexture();
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)sr.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)sr.getScaledWidth(), (double)sr.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)sr.getScaledWidth(), (double)0.0);
        GL11.glEnd();
        GlStateManager.disableBlend();
        McOutlineHelper.mc.renderGlobal.getEntityOutlineFramebuffer().framebufferClear();
        McOutlineHelper.stop();
    }
}

