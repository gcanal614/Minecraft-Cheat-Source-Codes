/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.astolfo.objects;

import de.fanta.module.Module;
import de.fanta.setting.Setting;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public abstract class GuiChanger<T>
extends Gui {
    public int x;
    public int y;
    public int width = 125;
    public int height = 35;
    public Setting setting;
    public boolean extended = false;
    public T value;
    public Color HOVER_COLOR = Color.blue;
    public Color BACKGROUND_COLOR = new Color(25, 25, 25);
    public Module.Type cat;

    public GuiChanger(int x, int y, Setting setting, Module.Type cat) {
        this.x = x;
        this.y = y;
        this.setting = setting;
        this.height = 17;
        this.cat = cat;
    }

    public void draw(float mouseX, float mouseY) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseDragged(int mouseX, int mouseY, int mouseButton) {
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}

