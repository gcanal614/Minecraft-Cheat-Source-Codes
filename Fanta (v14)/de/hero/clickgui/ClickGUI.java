/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package de.hero.clickgui;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.ConfigBox;
import de.fanta.clickgui.defaultgui.components.FontBox;
import de.fanta.module.Module;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.Translate;
import de.hero.clickgui.Panel;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.elements.menu.ElementSlider;
import de.hero.clickgui.util.ColorUtil;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUI
extends GuiScreen {
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    private ModuleButton mb = null;
    private ConfigBox configBox;
    private FontBox fontBox;
    Translate translate;

    public ClickGUI() {
        FontUtil.setupFontUtils();
        panels = new ArrayList();
        double pwidth = 80.0;
        double pheight = 15.0;
        double px = 10.0;
        double py = 10.0;
        double pyplus = pheight + 10.0;
        this.configBox = new ConfigBox(100.0f, 100.0f);
        Module.Type[] typeArray = Module.Type.values();
        int n = typeArray.length;
        int n2 = 0;
        while (n2 < n) {
            final Module.Type c = typeArray[n2];
            String title = String.valueOf(Character.toUpperCase(c.name().toLowerCase().charAt(0))) + c.name().toLowerCase().substring(1);
            panels.add(new Panel(title, px, py, pwidth, pheight, false, this){

                @Override
                public void setup() {
                    for (Module m : Client.INSTANCE.moduleManager.modules) {
                        if (!m.getType().equals((Object)c)) continue;
                        this.Elements.add(new ModuleButton(m, this));
                    }
                }
            });
            py += pyplus;
            ++n2;
        }
        rpanels = new ArrayList();
        for (Panel p : panels) {
            rpanels.add(p);
        }
        Collections.reverse(rpanels);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        this.translate.interpolate(GuiContainer.width, GuiContainer.height, 4.0);
        double xmod = (float)GuiContainer.width / 2.0f - this.translate.getX() / 2.0f;
        double ymod = (float)GuiContainer.height / 2.0f - this.translate.getY() / 2.0f;
        GlStateManager.translate(xmod, ymod, 0.0);
        GlStateManager.scale(this.translate.getX() / (float)GuiContainer.width, this.translate.getY() / (float)GuiContainer.height, 1.0f);
        this.configBox.drawConfigBox(mouseX, mouseY);
        for (Panel p : panels) {
            p.drawScreen(mouseX, mouseY, partialTicks);
        }
        ScaledResolution s = new ScaledResolution(this.mc);
        GL11.glPushMatrix();
        GL11.glTranslated((double)s.getScaledWidth(), (double)s.getScaledHeight(), (double)0.0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        FontUtil.drawStringWithShadow("byHeroCode", -Minecraft.getMinecraft().fontRendererObj.getStringWidth("byHeroCode"), -Minecraft.getMinecraft().fontRendererObj.getFontHeight(), -15599509);
        GL11.glPopMatrix();
        this.mb = null;
        block1: for (Panel p : panels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton e : p.Elements) {
                if (!e.listening) continue;
                this.mb = e;
                break block1;
            }
        }
        for (Panel panel : panels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended || b.menuelements == null || b.menuelements.isEmpty()) continue;
                double off = 0.0;
                Color temp = ColorUtil.getClickGUIColor().darker();
                int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
                double height = 0.0;
                for (Element e : b.menuelements) {
                    e.offset = off;
                    e.update();
                    height += e.height;
                    e.drawScreen(mouseX, mouseY, partialTicks);
                    off += e.height;
                }
                b.additionalHeight = height;
            }
        }
        if (this.mb != null) {
            ClickGUI.drawRect(0.0f, 0.0f, width, height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(s.getScaledWidth() / 2), (float)(s.getScaledHeight() / 2), (float)0.0f);
            GL11.glScalef((float)4.0f, (float)4.0f, (float)0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0.0, -10.0, -1);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " + this.mb.mod.getName() + (this.mb.mod.getKeyBind() > -1 ? " (" + Keyboard.getKeyName((int)this.mb.mod.getKeyBind()) + ")" : ""), 0.0, 0.0, -1);
            GL11.glScalef((float)0.25f, (float)0.25f, (float)0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("by HeroCode", 0.0, 20.0, -1);
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.configBox.configBoxClicked(mouseX, mouseY, mouseButton);
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    if (!e.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                    return;
                }
            }
        }
        for (Panel p : rpanels) {
            if (!p.mouseClicked(mouseX, mouseY, mouseButton)) continue;
            return;
        }
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.configBox.configBoxReleased(mouseX, mouseY, state);
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    e.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
        for (Panel p : rpanels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel p : rpanels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton e : p.Elements) {
                try {
                    if (!e.keyTyped(typedChar, keyCode)) continue;
                    return;
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        this.translate = new Translate(0.0f, 0.0f);
        if (Client.INSTANCE.moduleManager.getModule("GuiBlur").isState() && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    if (!(e instanceof ElementSlider)) continue;
                    ((ElementSlider)e).dragging = false;
                }
            }
        }
    }

    public void closeAllSettings() {
        for (Panel p : rpanels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton e : p.Elements) {
                e.extended = false;
            }
        }
    }

    public void renderVignetteScaledResolution(ScaledResolution scaledRes) {
        Color color = Color.cyan;
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
        RenderUtil.color(RenderUtil.injectAlpha(color.getRGB(), MathHelper.clamp_int(60, 0, 255)).getRGB());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(771, 770);
        GlStateManager.tryBlendFuncSeparate(771, 770, 1, 0);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/misc/vignette.png"));
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0, scaledRes.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
}

