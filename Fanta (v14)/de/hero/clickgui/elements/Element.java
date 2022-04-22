/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements;

import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.hero.clickgui.ClickGUI;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.FontUtil;

public class Element {
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public double offset;
    public double x;
    public double y;
    public double width;
    public double height;
    public String setstrg;
    public boolean comboextended;

    public void setup() {
        this.clickgui = this.parent.parent.clickgui;
        this.height = 15.0;
    }

    public void update() {
        this.x = this.parent.x;
        this.y = this.parent.y + this.parent.height + this.offset + 1.0;
        this.width = this.parent.width;
        String sname = this.set.getName();
        if (this.set.getSetting() instanceof CheckBox) {
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            double textx = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg);
            if (textx < this.x + 13.0) {
                this.width += this.x + 13.0 - textx + 1.0;
            }
        } else if (this.set.getSetting() instanceof DropdownBox) {
            this.height = this.comboextended ? ((DropdownBox)this.set.getSetting()).getOptions().length * (FontUtil.getFontHeight() + 2) + 15 : 15;
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            int longest = FontUtil.getStringWidth(this.setstrg);
            String[] stringArray = ((DropdownBox)this.set.getSetting()).getOptions();
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                String s = stringArray[n2];
                int temp = FontUtil.getStringWidth(s);
                if (temp > longest) {
                    longest = temp;
                }
                ++n2;
            }
            double textx = this.x + this.width - (double)longest;
            if (textx < this.x) {
                this.width += this.x - textx + 1.0;
            }
        } else if (this.set.getSetting() instanceof Slider) {
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            String displayval = "" + (double)Math.round(Double.valueOf(((Slider)this.set.getSetting()).curValue) * 100.0) / 100.0;
            String displaymax = "" + (double)Math.round((Double)((Slider)this.set.getSetting()).getMaxValue() * 100.0) / 100.0;
            double textx = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg) - (double)FontUtil.getStringWidth(displaymax) - 4.0;
            if (textx < this.x) {
                this.width += this.x - textx + 1.0;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return this.isHovered(mouseX, mouseY);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

