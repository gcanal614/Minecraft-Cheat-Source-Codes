/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.astolfo.objects;

import de.fanta.Client;
import de.fanta.clickgui.astolfo.objects.GuiChanger;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class GuiNumberChanger
extends GuiChanger<Double> {
    private float xAdd = 0.0f;
    private boolean init = false;

    public GuiNumberChanger(int x, int y, Setting setting, Module.Type cat) {
        super(x, y, setting, cat);
        this.height = 20;
        this.width = 119;
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.value == null) {
            this.value = (Double)this.setting.getSetting().getValue();
        }
        if (!this.init) {
            this.value = this.calculateValue();
            float percentWidth = (float)((double)this.width / (Double)this.setting.getSetting().getMaxValue() * (Double)this.setting.getSetting().getValue());
            this.xAdd = (int)percentWidth;
            this.init = true;
        }
        GuiNumberChanger.drawRect(this.x + 3, this.y + 1, (float)(this.x + 3) + this.xAdd, this.y + 20, this.cat.getColor());
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.setting.getName(), this.x + 6, this.y + 4, Color.white.getRGB());
        int valueWidth = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(((Double)this.value).toString().replace(".", ","));
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(((Double)this.value).toString().replace(".", ","), this.x + this.width - valueWidth, this.y + 4, Color.white.getRGB());
        if (Mouse.isButtonDown((int)0) && mouseX >= (float)(this.x + 3) && mouseX <= (float)(this.x + 3 + this.width) && mouseY >= (float)this.y && mouseY <= (float)(this.y + 20)) {
            this.xAdd = mouseX - (float)this.x - 3.0f;
        }
        this.value = this.calculateValue();
        if (this.value != null) {
            this.setting.getSetting().setValue(this.value);
        }
    }

    public double calculateValue() {
        float percentWidth = 1.0f / (float)this.width * this.xAdd * 100.0f;
        double d = (float)((Double)this.setting.getSetting().getMaxValue() * (double)(percentWidth / 100.0f));
        return (double)Math.round(d * 100.0) / 100.0;
    }
}

