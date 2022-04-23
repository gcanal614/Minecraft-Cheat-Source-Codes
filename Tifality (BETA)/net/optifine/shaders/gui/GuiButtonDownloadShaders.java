/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonDownloadShaders
extends GuiButton {
    public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
        super(buttonID, xPos, yPos, 22, 20, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            super.drawButton(mc, mouseX, mouseY);
            ResourceLocation resourcelocation = new ResourceLocation("optifine/textures/icons.png");
            mc.getTextureManager().bindTexture(resourcelocation);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GuiButtonDownloadShaders.drawTexturedModalRect(this.xPosition + 3, this.yPosition + 2, 0, 0, 16, 16);
        }
    }
}

