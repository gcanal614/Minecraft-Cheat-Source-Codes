/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.setting.Setting;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import org.lwjgl.opengl.GL11;

public class CheckBox {
    public float x;
    public float y;
    private CategoryPanel panel;
    private Setting setting;
    int ani = 0;

    public CheckBox(Setting setting, CategoryPanel panel, float x, float y) {
        this.setting = setting;
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    public void drawCheckBox(float mouseX, float mouseY) {
        int color;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean hovering = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 9.0f && mouseY <= this.y + yOff + 9.0f;
        GL11.glPushMatrix();
        int n = color = hovering ? Colors.getColor(150, 150, 150, 255) : Colors.getColor(110, 110, 110, 255);
        if (((de.fanta.setting.settings.CheckBox)this.setting.getSetting()).state) {
            this.ani += 5;
            color = Colors.getColor(200, 50, 0, 255 + this.ani);
        } else {
            color = Colors.getColor(200, 50, 0, 255 + this.ani);
            this.ani -= 5;
        }
        if (this.ani >= 255) {
            this.ani = 255;
        }
        if (this.ani < 0) {
            this.ani = 0;
            color = hovering ? Colors.getColor(150, 150, 150, 255) : Colors.getColor(110, 110, 110, 255);
        }
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glPopMatrix();
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.setting.getName(), (int)(this.x + xOff + 12.0f), (int)(this.y + yOff - 2.0f), -1);
        RenderUtil.rectangle(this.x + xOff, this.y + yOff, this.x + xOff + 9.0f, this.y + yOff + 9.0f, color);
    }

    public void checkBoxClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean bl = hovering = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 9.0f && mouseY <= this.y + yOff + 9.0f;
        if (hovering) {
            ((de.fanta.setting.settings.CheckBox)this.setting.getSetting()).state = !((de.fanta.setting.settings.CheckBox)this.setting.getSetting()).state;
        }
    }
}

