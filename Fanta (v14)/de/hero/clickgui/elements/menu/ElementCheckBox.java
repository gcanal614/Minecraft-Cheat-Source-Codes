/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements.menu;

import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.ColorUtil;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class ElementCheckBox
extends Element {
    public ElementCheckBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        Gui.drawRect2(this.x, this.y, this.x + this.width, this.y + this.height, new Color(18, 18, 18, 180).getRGB());
        FontUtil.drawString(this.setstrg, this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg) - 5.0, this.y + (double)(FontUtil.getFontHeight() / 2) - 2.0, new Color(150, 150, 150).getRGB());
        Gui.drawRect2(this.x + 1.0, this.y + 2.0, this.x + 12.0, this.y + 13.0, ((CheckBox)this.set.getSetting()).state ? Color.green.getRGB() : new Color(60, 60, 60).getRGB());
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isCheckHovered(mouseX, mouseY)) {
            ((CheckBox)this.set.getSetting()).setState(!((CheckBox)this.set.getSetting()).state);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isCheckHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x + 1.0 && (double)mouseX <= this.x + 12.0 && (double)mouseY >= this.y + 2.0 && (double)mouseY <= this.y + 13.0;
    }
}

