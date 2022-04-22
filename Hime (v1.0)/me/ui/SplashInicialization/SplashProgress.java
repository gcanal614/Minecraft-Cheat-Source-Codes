package me.ui.SplashInicialization;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SplashProgress {

    private static final int MAX = 10;
    private static int PROGRESS = 0;
    private static String STRING = "";
    private static ResourceLocation splash;
    private static SplashUnicodeFontRenderer font;
    private static GlStateManager glsm = new GlStateManager();

    public static void update() {
        if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null){
            return;
        }
        drawSplash(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setProgress(int progress, String text) {
        PROGRESS = progress;
        STRING = text;
        update();
    }

    public static void drawSplash(TextureManager tm) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int sf = sr.getScaleFactor();

        Framebuffer fb = new Framebuffer(sr.getScaledWidth() * sf, sr.getScaledHeight() * sf, true);
        fb.bindFramebuffer(false);

        glsm.matrixMode(GL11.GL_PROJECTION);
        glsm.loadIdentity();
        glsm.ortho(0.0d, sr.getScaledWidth(), sr.getScaledHeight(), 0.0d, 1000.0d, 3000.0d);
        glsm.matrixMode(GL11.GL_MODELVIEW);
        glsm.loadIdentity();
        glsm.translate(0.0f,0.0f, -2000.0f);
        glsm.disableLighting();
        glsm.disableFog();
        glsm.disableDepth();
        glsm.enableTexture2D();

        if(splash == null){
            splash = new ResourceLocation("client/250472.jpg");
        }

        tm.bindTexture(splash);

        glsm.resetColor();
        glsm.color(1.0f, 1.0f, 1.0f, 1.0f);

        Gui.drawScaledCustomSizeModalRect(0,0,0,0, 1920, 1080, sr.getScaledWidth(), sr.getScaledHeight(), 1920, 1080);
        drawProgress();
        fb.unbindFramebuffer();
        fb.framebufferRender(sr.getScaledWidth() * sf, sr.getScaledHeight() * sf);

        glsm.enableAlpha();
        glsm.alphaFunc(516, 0.1f);

        Minecraft.getMinecraft().updateDisplay();
    }

    private static void drawProgress() {
        if(Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null){
            return;
        }

        if(font == null){
            font = SplashUnicodeFontRenderer.getFontOnPC("Comfortaa", 20);
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        double nProgress = PROGRESS;
        double calc = (nProgress / MAX) * sr.getScaledWidth();

        Gui.drawRect(0, sr.getScaledHeight() - 35, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,50).getRGB());

        //glsm.resetColor();
        //resetTextureState();

        font.drawString(STRING, 20, sr.getScaledHeight() - 25, -1);
        String step = PROGRESS + "/" + MAX;
        font.drawString(step, sr.getScaledHeight() - 20 - font.getStringWidth(step), sr.getScaledHeight() - 25, -1);

        glsm.resetColor();
        resetTextureState();

        Gui.drawRect(0, sr.getScaledHeight() - 2, calc, sr.getScaledHeight(), -1);
        Gui.drawRect(0, sr.getScaledHeight() -2, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,10).getRGB());
    }

    private static void resetTextureState() {
        glsm.textureState[glsm.activeTextureUnit].textureName = -1;
    }

}
