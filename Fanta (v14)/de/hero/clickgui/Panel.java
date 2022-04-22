/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui;

import de.hero.clickgui.ClickGUI;
import de.hero.clickgui.elements.ModuleButton;
import de.hero.clickgui.util.ColorUtil;
import de.hero.clickgui.util.FontUtil;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;

public class Panel {
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements = new ArrayList();
    public ClickGUI clickgui;

    public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
        this.title = ititle;
        this.x = ix;
        this.y = iy;
        this.width = iwidth;
        this.height = iheight;
        this.extended = iextended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = parent;
        this.setup();
    }

    public void setup() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + (double)mouseX;
            this.y = this.y2 + (double)mouseY;
        }
        Color temp = ColorUtil.getClickGUIColor().darker();
        double mcOutlineEnd = this.y + this.height;
        Gui.drawRect2(this.x, this.y, this.x + this.width, this.y + this.height, new Color(0, 255, 0, 255).getRGB());
        FontUtil.drawString(this.title, this.x + 2.0, this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
        FontUtil.drawString(this.extended ? "\u0245" : "V", this.x + this.width - 10.0, this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
        if (this.extended && !this.Elements.isEmpty()) {
            double startY = this.y + this.height;
            int epanelcolor = -14474461;
            for (ModuleButton et : this.Elements) {
                Gui.drawRect2(this.x, startY, this.x + this.width, startY + et.height + 1.0, new Color(38, 38, 38, 210).getRGB());
                et.x = this.x;
                et.y = startY;
                et.width = this.width;
                et.drawScreen(mouseX, mouseY, partialTicks);
                startY += et.height + (et.extended ? et.additionalHeight : 0.0) + 1.0;
            }
            mcOutlineEnd = startY;
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.x2 = this.x - (double)mouseX;
            this.y2 = this.y - (double)mouseY;
            this.dragging = true;
            return true;
        }
        if (mouseButton == 1 && this.isHovered(mouseX, mouseY)) {
            this.extended = !this.extended;
            return true;
        }
        if (this.extended) {
            for (ModuleButton et : this.Elements) {
                if (!et.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                return true;
            }
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

