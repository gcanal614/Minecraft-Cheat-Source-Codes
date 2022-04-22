/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.setting.Setting;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.TimeUtil;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Mouse;

public class TextField {
    public float x;
    public float y;
    private CategoryPanel panel;
    private float dragX;
    private Setting setting;
    private boolean focused;
    private char key = '\u0000';
    private List<String> list = new ArrayList<String>();
    TimeUtil time = new TimeUtil();

    public TextField(Setting setting, CategoryPanel panel, float x, float y) {
        this.setting = setting;
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    public void drawTextField(float mouseX, float mouseY) {
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean hovering = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 9.0f && mouseY <= this.y + yOff + 9.0f;
        int color = hovering ? Colors.getColor(150, 150, 150, 255) : Colors.getColor(110, 110, 110, 255);
        int fcolor = hovering ? Colors.getColor(150, 150, 150, 255) : Colors.getColor(20, 30, 20, 255);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.setting.getName(), (int)(this.x + xOff + 12.0f), (int)(this.y + yOff - 2.0f), -1);
        if (hovering && Mouse.isButtonDown((int)1)) {
            this.focused = true;
        } else if (!hovering && Mouse.isButtonDown((int)1)) {
            this.focused = false;
        }
        RenderUtil.rectangle(this.x + xOff, this.y + yOff, this.x + xOff + 9.0f, this.y + yOff + 9.0f, this.focused ? fcolor : color);
        if (this.focused) {
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("", (int)(this.x + xOff + 12.0f), (int)(this.y + yOff + 9.0f), -1);
        }
    }

    public void textFieldClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean bl = hovering = mouseX >= this.x + xOff && mouseY >= this.y + yOff && mouseX <= this.x + xOff + 9.0f && mouseY <= this.y + yOff + 9.0f;
        if (hovering) {
            ((de.fanta.setting.settings.TextField)((Object)this.setting.getSetting())).focused = !((de.fanta.setting.settings.TextField)((Object)this.setting.getSetting())).focused;
        }
    }
}

