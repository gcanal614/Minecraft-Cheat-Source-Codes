/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.render;

import club.tifality.utils.Wrapper;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public final class SkeetButton
extends GuiButton {
    public SkeetButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, 80, 15, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(0, 0, 0).getRGB());
            Gui.drawRect((float)this.xPosition + 0.5f, (float)this.yPosition + 0.5f, (float)(this.xPosition + this.width) - 0.5f, (float)(this.yPosition + this.height) - 0.5f, this.hovered ? new Color(80, 80, 80).getRGB() : new Color(50, 50, 50).getRGB());
            RenderingUtils.drawGradientRect((float)this.xPosition + 1.0f, (float)this.yPosition + 1.0f, (float)(this.xPosition + this.width) - 1.0f, (float)(this.yPosition + this.height) - 1.0f, false, this.hovered ? RenderingUtils.darker(new Color(34, 34, 34).getRGB(), 1.1f) : new Color(34, 34, 34).getRGB(), this.hovered ? RenderingUtils.darker(new Color(26, 26, 26).getRGB(), 1.1f) : new Color(26, 26, 26).getRGB());
            mc.getTextureManager().bindTexture(buttonTextures);
            this.mouseDragged(mc, mouseX, mouseY);
            Wrapper.getCSGOFontRenderer().drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2 + 3, -1);
            GlStateManager.resetColor();
        }
    }
}

