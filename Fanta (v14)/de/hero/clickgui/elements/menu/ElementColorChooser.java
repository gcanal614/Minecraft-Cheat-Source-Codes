/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements.menu;

import de.fanta.setting.Setting;
import de.fanta.setting.settings.ColorValue;
import de.hero.clickgui.classes.GuiColorChooser;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class ElementColorChooser
extends Element {
    private GuiColorChooser colorChooser;

    public ElementColorChooser(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
        this.colorChooser = new GuiColorChooser((int)this.x, (int)this.y + 10);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.colorChooser.x = this.x;
        this.colorChooser.y = this.y + 10.0;
        this.height = 10 + this.colorChooser.getHeight();
        this.colorChooser.draw(mouseX, mouseY);
        this.colorChooser.setWidth((int)this.width);
        ((ColorValue)this.set.getSetting()).color = this.colorChooser.color;
        Gui.drawRect2(this.x, this.y, this.x + this.width, this.y + 10.0, new Color(38, 38, 38, 150).getRGB());
        FontUtil.drawCenteredString(String.valueOf(this.set.getName()) + ":", this.x + this.width / 2.0, this.y + 2.0, Color.white.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + 15.0;
    }
}

