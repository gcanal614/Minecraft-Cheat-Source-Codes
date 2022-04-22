/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.FontBox;
import de.fanta.gui.font.ClientFont;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import net.minecraft.client.Minecraft;

public class FontButton {
    private String font;
    private float x;
    private float y;
    private boolean selected;
    private FontBox box;

    public FontButton(FontBox box, String font, float x, float y) {
        this.x = x;
        this.y = y;
        this.box = box;
        this.font = font;
    }

    public void drawButton(float mouseX, float mouseY) {
        float xOff = this.box.dragX;
        float yOff = this.box.dragY + this.box.scroll;
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.font, this.x + xOff + 1.0f, this.y + yOff + 1.5f, this.selected ? Colors.getColor(242, 62, 22, 255) : -1);
        RenderUtil.rectangle(this.x + xOff + 171.0f, this.y + yOff + 1.5f, this.x + xOff + 196.0f, this.y + yOff + 13.0f, Colors.getColor(25, 25, 25, 255));
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Load", this.x + xOff + 171.5f, this.y + yOff + 1.5f, -1);
    }

    public void buttonClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        float xOff = this.box.dragX;
        float yOff = this.box.dragY + this.box.scroll;
        boolean bl = hovering = mouseX >= this.x + xOff + 170.0f && mouseY >= this.y + yOff + 1.5f && mouseX <= this.x + xOff + 195.0f && mouseY <= this.y + yOff + 13.0f;
        if (hovering) {
            this.selected = true;
            Minecraft.getMinecraft().fontRendererObj = ClientFont.font(17, this.font, false);
        }
    }

    public void buttonReleased(float mouseX, float mouseY, int state) {
        this.selected = false;
    }
}

