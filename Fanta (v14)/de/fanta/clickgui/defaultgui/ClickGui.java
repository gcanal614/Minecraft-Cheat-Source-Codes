/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui;

import de.fanta.clickgui.defaultgui.Panel;
import de.fanta.clickgui.defaultgui.components.ConfigBox;
import de.fanta.utils.Translate;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ClickGui
extends GuiScreen {
    public boolean activ;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Panel panels;
    private final ConfigBox configBox;
    Translate translate;

    public ClickGui(boolean activ, float x, float y) {
        this.panels = new Panel(x, y);
        this.configBox = new ConfigBox(x, y + 200.0f);
        this.activ = activ;
    }

    private void scissorBox(float x, float y, float width, float length) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scale.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)scaleFactor)), (int)((int)(((float)scale.getScaledHeight() - length) * (float)scaleFactor)), (int)((int)((width - x) * (float)scaleFactor)), (int)((int)((length - y) * (float)scaleFactor)));
    }

    public void drawMenu(float mouseX, float mouseY) {
        if (!this.activ) {
            return;
        }
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        this.panels.drawPanel(mouseX, mouseY);
        this.configBox.drawConfigBox(mouseX, mouseY);
        this.translate = new Translate(0.0f, 0.0f);
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            this.panels.panelClicked(mouseX, mouseY, mouseButton);
            this.configBox.configBoxClicked(mouseX, mouseY, mouseButton);
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.panels.panelReleased(mouseX, mouseY, state);
        this.configBox.configBoxReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        this.panels.panelHandleInput();
        super.handleMouseInput();
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
        this.activ = false;
        super.onGuiClosed();
    }
}

