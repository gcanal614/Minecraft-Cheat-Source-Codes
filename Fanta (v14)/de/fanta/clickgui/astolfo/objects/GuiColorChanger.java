/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.astolfo.objects;

import de.fanta.clickgui.astolfo.objects.GuiChanger;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.hero.clickgui.classes.GuiColorChooser;
import java.awt.Color;
import net.minecraft.client.Minecraft;

public class GuiColorChanger
extends GuiChanger<Integer> {
    public GuiColorChooser chooser;

    public GuiColorChanger(int x, int y, Setting setting, Module.Type cat) {
        super(x, y, setting, cat);
        this.chooser = new GuiColorChooser(x, y + 15, new Color((Integer)this.setting.getSetting().getValue()).getRGB());
        this.height = 15;
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        super.draw(mouseX, mouseY);
        GuiColorChanger.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.BACKGROUND_COLOR.getRGB());
        this.drawString(Minecraft.getMinecraft().fontRendererObj, this.setting.getName(), this.x + 5, this.y + 5, Color.white.getRGB());
        GuiColorChanger.drawRect(this.x + this.width - 25, this.y + 5, this.x + this.width - 5, this.y + 13, this.chooser.color);
        this.chooser.x = this.x;
        this.chooser.y = this.y + 15;
        this.height = 15 + this.chooser.getHeight();
        this.chooser.draw((int)mouseX, (int)mouseY);
        try {
            int i = this.chooser.color;
            this.setting.getSetting().setValue(i);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

