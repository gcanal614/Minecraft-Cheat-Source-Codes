/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.elements.menu;

import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import de.hero.clickgui.elements.Element;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.ColorUtil;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ElementSlider
extends Element {
    public boolean dragging;

    public ElementSlider(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
        this.dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String displayval = "" + (double)Math.round(((Slider)this.set.getSetting()).curValue * 100.0) / 100.0;
        boolean hoveredORdragged = this.isSliderHovered(mouseX, mouseY) || this.dragging;
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
        int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
        double percentBar = Double.valueOf(((Slider)this.set.getSetting()).curValue - (Double)((Slider)this.set.getSetting()).getMinValue()) / ((Double)((Slider)this.set.getSetting()).getMaxValue() - (Double)((Slider)this.set.getSetting()).getMinValue());
        Gui.drawRect2(this.x, this.y, this.x + this.width, this.y + this.height, new Color(18, 18, 18, 180).getRGB());
        FontUtil.drawString(this.setstrg, this.x + 1.0, this.y + 2.0, -1);
        FontUtil.drawString(displayval, this.x + this.width - (double)FontUtil.getStringWidth(displayval) - 2.0, this.y + 2.0, -1);
        Gui.drawRect2(this.x, this.y + 12.0, this.x + this.width, this.y + 13.5, -15724528);
        Gui.drawRect2(this.x, this.y + 12.0, this.x + percentBar * this.width, this.y + 13.5, Color.green.getRGB());
        if (percentBar > 0.0 && percentBar < 1.0) {
            Gui.drawRect2(this.x + percentBar * this.width - 1.0, this.y + 12.0, this.x + Math.min(percentBar * this.width, this.width), this.y + 13.5, Color.black.getRGB());
        }
        if (this.dragging) {
            double val;
            double diff = (Double)((Slider)this.set.getSetting()).getMaxValue() - (Double)((Slider)this.set.getSetting()).getMinValue();
            ((Slider)this.set.getSetting()).curValue = val = (Double)((Slider)this.set.getSetting()).getMinValue() + MathHelper.clamp_double(((double)mouseX - this.x) / this.width, 0.0, 1.0) * diff;
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isSliderHovered(mouseX, mouseY)) {
            this.dragging = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y + 11.0 && (double)mouseY <= this.y + 14.0;
    }
}

