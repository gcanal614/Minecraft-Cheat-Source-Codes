/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.Panel;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.module.Module;
import de.fanta.utils.Colors;

public class CategoryButton {
    public Panel panel;
    public Module.Type type;
    public boolean isActive;
    public CategoryPanel catePanel;
    private float x;
    private float y;

    public CategoryButton(Module.Type type, Panel panel, float x, float y) {
        this.type = type;
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.catePanel = new CategoryPanel(this, x + 50.0f, 53.0f);
    }

    public void drawCateButton(float mouseX, float mouseY) {
        int color;
        boolean hovering = mouseX >= this.x + this.panel.dragX && mouseY >= this.y + this.panel.dragY && mouseX <= this.x + this.panel.dragX + (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.type.name()) && mouseY <= this.y + this.panel.dragY + 10.0f;
        int n = color = hovering ? Colors.getColor(200, 50, 0, 255) : Colors.getColor(127, 140, 141, 255);
        if (this.isActive) {
            color = Colors.getColor(200, 50, 0, 255);
        }
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.type.name(), this.x + this.panel.dragX - 3.0f, this.y + this.panel.dragY, color);
        if (this.isActive) {
            this.catePanel.drawCategory(mouseX, mouseY);
        }
    }

    public void cateButtonClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        boolean bl = hovering = mouseX >= this.x + this.panel.dragX && mouseY >= this.y + this.panel.dragY && mouseX <= this.x + this.panel.dragX + (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.type.name()) && mouseY <= this.y + this.panel.dragY + 10.0f;
        if (hovering) {
            this.isActive = true;
            for (CategoryButton button : this.panel.buttons) {
                if (button == this || !button.isActive) continue;
                button.isActive = false;
            }
        }
        if (this.isActive) {
            this.catePanel.categoryClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void cateButtonReleased(float mouseX, float mouseY, int state) {
        if (this.isActive) {
            this.catePanel.categoryReleased(mouseX, mouseY, state);
        }
    }
}

