/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.clickgui.defaultgui.components.DropdownButton;
import de.fanta.setting.Setting;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.util.ArrayList;

public class DropdownBox {
    public float x;
    public float y;
    public CategoryPanel panel;
    public Setting setting;
    private boolean isOpened;
    private int ani = 0;
    public ArrayList<DropdownButton> dropdownButtons;
    public float buttonY;

    public DropdownBox(Setting setting, CategoryPanel panel, float x, float y) {
        this.setting = setting;
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.dropdownButtons = new ArrayList();
        de.fanta.setting.settings.DropdownBox dropdownBox = (de.fanta.setting.settings.DropdownBox)setting.getSetting();
        float buttonY = 9.0f;
        String[] stringArray = dropdownBox.getOptions();
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String o = stringArray[n2];
            if (!o.equals(dropdownBox.curOption)) {
                this.dropdownButtons.add(new DropdownButton(o, this, x, y + buttonY));
                buttonY += 9.0f;
            }
            ++n2;
        }
        this.buttonY = buttonY - 9.0f;
    }

    public void drawDropdownBox(float mouseX, float mouseY) {
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean hovering = mouseX >= this.x + xOff + 5.0f && mouseY >= this.y + yOff + 1.0f && mouseX <= this.x + xOff + 105.0f && mouseY <= this.y + yOff + 9.0f;
        int color = hovering ? Colors.getColor(200, 50, 0, 255) : Colors.getColor(50, 50, 50, 255);
        RenderUtil.rectangle(this.x + xOff + 5.0f, this.y + yOff + 1.0f, this.x + xOff + 105.0f, this.y + yOff + 9.0f, Colors.getColor(0, 0, 0, 255));
        RenderUtil.rectangle(this.x + xOff + 6.0f, this.y + yOff + 2.0f, this.x + xOff + 104.0f, this.y + yOff + 8.0f, color);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringScaled(((de.fanta.setting.settings.DropdownBox)this.setting.getSetting()).curOption, (int)(this.x + xOff + 6.0f), (int)(this.y + yOff), Colors.getColor(200, 200, 200, 255), 0.8);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringScaled(this.setting.getName(), (int)(this.x + xOff + 110.0f), (int)(this.y + yOff - 2.0f), -1, 1.0);
        if (this.isOpened) {
            this.dropdownButtons.forEach(dropdownButton -> dropdownButton.drawDropdownButton(mouseX, mouseY));
        }
    }

    public void dropdownBoxClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean bl = hovering = mouseX >= this.x + xOff + 5.0f && mouseY >= this.y + yOff + 1.0f && mouseX <= this.x + xOff + 105.0f && mouseY <= this.y + yOff + 9.0f;
        if (hovering) {
            boolean bl2 = this.isOpened = !this.isOpened;
        }
        if (this.isOpened) {
            this.dropdownButtons.forEach(dropdownButton -> dropdownButton.dropdownButtonClicked(mouseX, mouseY, mouseButton));
        }
    }
}

