/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements;

import de.fanta.Client;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.hero.clickgui.Panel;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.menu.ElementCheckBox;
import de.hero.clickgui.elements.menu.ElementColorChooser;
import de.hero.clickgui.elements.menu.ElementComboBox;
import de.hero.clickgui.elements.menu.ElementSlider;
import de.hero.clickgui.util.ColorUtil;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class ModuleButton {
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public double x;
    public double y;
    public double width;
    public double height;
    public double additionalHeight;
    public boolean extended = false;
    public boolean listening = false;

    public ModuleButton(Module imod, Panel pl) {
        this.mod = imod;
        this.height = Minecraft.getMinecraft().fontRendererObj.getFontHeight() + 2;
        this.parent = pl;
        this.menuelements = new ArrayList();
        if (imod.settings.size() != 0) {
            for (Setting s : imod.getSettings()) {
                if (s.getSetting() instanceof CheckBox) {
                    this.menuelements.add(new ElementCheckBox(this, s));
                    continue;
                }
                if (s.getSetting() instanceof Slider) {
                    this.menuelements.add(new ElementSlider(this, s));
                    continue;
                }
                if (s.getSetting() instanceof DropdownBox) {
                    this.menuelements.add(new ElementComboBox(this, s));
                    continue;
                }
                if (!(s.getSetting() instanceof ColorValue)) continue;
                this.menuelements.add(new ElementColorChooser(this, s));
            }
            for (Element e : this.menuelements) {
                this.additionalHeight += e.height;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        int textcolor = new Color(150, 150, 150).getRGB();
        if (this.mod.isState()) {
            textcolor = Color.green.getRGB();
        }
        FontUtil.drawTotalCenteredString(this.mod.getName(), this.x + this.width / 2.0, this.y + 1.0 + this.height / 2.0 - 2.0, textcolor);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (mouseButton == 0) {
            this.mod.setState();
        } else if (mouseButton == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
                boolean b = !this.extended;
                Client.clickgui.closeAllSettings();
                this.extended = b;
            }
        } else if (mouseButton == 2) {
            this.listening = true;
        }
        return true;
    }

    public boolean keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.listening) {
            this.listening = false;
            return true;
        }
        return false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

