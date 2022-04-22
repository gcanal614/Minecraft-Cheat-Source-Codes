/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.gui.flux;

import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonFanta
extends GuiButton {
    GlyphPageFontRenderer fluxButton = ClientFont.font(15, "Vision", true);

    public GuiButtonFanta(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public GuiButtonFanta(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        int unhovered = Color.blue.getRGB();
        int hovered = Color.black.getRGB();
        int blockedunhovered = Color.decode("#7D7D7D").getRGB();
        int blockedhovered = Color.decode("#646464").getRGB();
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        RenderUtil.drawRoundedRect(this.xPosition, this.yPosition, this.width, this.height, 5, this.enabled ? (this.hovered ? hovered : unhovered) : (this.hovered ? blockedhovered : blockedunhovered));
        RenderUtil.glColor(Color.white.getRGB());
        this.fluxButton.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + this.height / 2 - this.fluxButton.getFontHeight() / 2, Color.white.getRGB(), false);
    }
}

