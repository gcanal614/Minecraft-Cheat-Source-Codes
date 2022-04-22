/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.DropdownBox;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;

public class DropdownButton {
    public float x;
    public float y;
    private String option;
    private DropdownBox dropdownBox;

    public DropdownButton(String option, DropdownBox dropdownBox, float x, float y) {
        this.dropdownBox = dropdownBox;
        this.option = option;
        this.x = x;
        this.y = y;
    }

    public void drawDropdownButton(float mouseX, float mouseY) {
        float xOff = this.dropdownBox.panel.cateButton.panel.dragX;
        float yOff = this.dropdownBox.panel.cateButton.panel.dragY;
        boolean hovering = mouseX >= this.x + xOff + 6.0f && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 104.0f && mouseY <= this.y + yOff + 8.0f;
        int color = hovering ? Colors.getColor(200, 50, 0, 255) : Colors.getColor(50, 50, 50, 255);
        RenderUtil.rectangle(this.x + xOff + 6.0f, this.y + yOff, this.x + xOff + 104.0f, this.y + yOff + 8.0f, color);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringScaledShadow(this.option, (int)(this.x + xOff + 5.0f), (int)(this.y + yOff) - 1, Colors.getColor(0, 0, 0, 255), 0.8, yOff, yOff);
    }

    public void dropdownButtonClicked(float mouseX, float mouseY, float mouseButton) {
        boolean hovering;
        float xOff = this.dropdownBox.panel.cateButton.panel.dragX;
        float yOff = this.dropdownBox.panel.cateButton.panel.dragY;
        boolean bl = hovering = mouseX >= this.x + xOff + 5.0f && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 105.0f && mouseY <= this.y + yOff + 8.0f;
        if (hovering) {
            String lastCurOption = ((de.fanta.setting.settings.DropdownBox)this.dropdownBox.setting.getSetting()).curOption;
            ((de.fanta.setting.settings.DropdownBox)this.dropdownBox.setting.getSetting()).curOption = this.option;
            this.option = lastCurOption;
        }
    }
}

