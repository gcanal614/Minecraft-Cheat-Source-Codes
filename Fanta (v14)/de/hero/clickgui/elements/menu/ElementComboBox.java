/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements.menu;

import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class ElementComboBox
extends Element {
    public ElementComboBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect2(this.x, this.y, this.x + this.width, this.y + 15.0, new Color(18, 18, 18, 180).getRGB());
        Gui.drawRect2(this.x, this.y + 15.0, this.x + this.width, this.y + this.height, new Color(18, 18, 18, 200).getRGB());
        FontUtil.drawTotalCenteredString(this.setstrg, this.x + this.width / 2.0, this.y + 7.0, new Color(150, 150, 150).getRGB());
        if (this.comboextended) {
            double ay = this.y + 15.0;
            String[] stringArray = ((DropdownBox)this.set.getSetting()).getOptions();
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                String sld = stringArray[n2];
                String elementtitle = String.valueOf(sld.substring(0, 1).toUpperCase()) + sld.substring(1, sld.length());
                if (sld.equalsIgnoreCase(((DropdownBox)this.set.getSetting()).curOption)) {
                    Gui.drawRect2(this.x, ay, this.x + this.width, ay + (double)FontUtil.getFontHeight() + 2.0, Color.green.getRGB());
                }
                FontUtil.drawCenteredString(elementtitle, this.x + this.width / 2.0, ay, -1);
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY < ay + (double)FontUtil.getFontHeight() + 2.0) {
                    Gui.drawRect2(this.x + this.width - 1.2, ay, this.x + this.width, ay + (double)FontUtil.getFontHeight() + 2.0, new Color(20, 220, 50).getRGB());
                }
                ay += (double)(FontUtil.getFontHeight() + 2);
                ++n2;
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.isButtonHovered(mouseX, mouseY)) {
                this.comboextended = !this.comboextended;
                return true;
            }
            if (!this.comboextended) {
                return false;
            }
            double ay = this.y + 15.0;
            String[] stringArray = ((DropdownBox)this.set.getSetting()).getOptions();
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                String slcd = stringArray[n2];
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY <= ay + (double)FontUtil.getFontHeight() + 2.0) {
                    ((DropdownBox)this.set.getSetting()).curOption = slcd;
                    return true;
                }
                ay += (double)(FontUtil.getFontHeight() + 2);
                ++n2;
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + 15.0;
    }
}

