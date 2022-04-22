/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.module.Module;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import org.lwjgl.opengl.GL11;

public class ModuleButton {
    private float x;
    private float y;
    public boolean isOpened;
    public Module module;
    private CategoryPanel panel;
    private int ani = 0;

    public ModuleButton(Module module, CategoryPanel panel, float x, float y) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.panel = panel;
    }

    public void drawButton(float mouseX, float mouseY) {
        int colorText;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        float moduleNameLength = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.module.name);
        boolean hovering = mouseX >= this.x + xOff + this.panel.lengthModule + 5.0f && mouseY >= this.y + yOff && mouseX <= this.x + xOff + this.panel.lengthModule + 15.0f && mouseY <= this.y + yOff + 10.0f;
        boolean hoveringText = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + moduleNameLength && mouseY <= this.y + yOff + 10.0f;
        int color = hovering ? Colors.getColor(88, 88, 88, 255) : Colors.getColor(66, 66, 66, 255);
        GL11.glPushMatrix();
        if (this.module.state) {
            this.ani += 5;
            color = Colors.getColor(200, 50, 0, 255 + this.ani);
        } else {
            color = Colors.getColor(41, 128, 185, 5 + this.ani);
            this.ani -= 5;
        }
        if (this.ani >= 255) {
            this.ani = 255;
        }
        if (this.ani < 0) {
            this.ani = 0;
            color = hovering ? Colors.getColor(88, 88, 88, 255) : Colors.getColor(66, 66, 66, 255);
        }
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)100.0f);
        GL11.glPopMatrix();
        RenderUtil.rectangle(this.x + xOff + this.panel.lengthModule + 5.0f, this.y + yOff, this.x + xOff + this.panel.lengthModule + 15.0f, this.y + yOff + 10.0f, color);
        int n = colorText = hoveringText ? Colors.getColor(189, 195, 199, 255) : Colors.getColor(127, 140, 141, 255);
        if (this.isOpened) {
            colorText = Colors.getColor(255, 255, 255, 255);
        }
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.module.name, (int)(this.x + xOff), (int)(this.y + yOff) - 2, colorText);
    }

    public void buttonClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hoveringText;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        float moduleNameLength = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.module.name);
        boolean hovering = mouseX >= this.x + xOff + this.panel.lengthModule + 5.0f && mouseY >= this.y + yOff && mouseX <= this.x + xOff + this.panel.lengthModule + 15.0f && mouseY <= this.y + yOff + 10.0f;
        boolean bl = hoveringText = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + moduleNameLength && mouseY <= this.y + yOff + 10.0f;
        if (hovering) {
            this.module.setState();
        }
        if (hoveringText && !this.module.settings.isEmpty()) {
            this.isOpened = true;
            this.panel.moduleButtons.forEach(moduleButton -> {
                if (moduleButton != this && moduleButton.isOpened) {
                    moduleButton.isOpened = !this.isOpened;
                }
            });
        }
    }
}

