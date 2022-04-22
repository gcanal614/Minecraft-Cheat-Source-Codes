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

public class GuiStringSelector
extends GuiChanger<String> {
    private String[] values;
    private long lastMS = System.currentTimeMillis();

    public GuiStringSelector(int x, int y, Setting setting, Module.Type cat) {
        super(x, y, setting, cat);
        this.height = 20;
        this.width = 119;
        this.lastMS = System.currentTimeMillis();
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.value == null) {
            this.value = (String)this.setting.getSetting().getValue();
        }
        if (this.values == null) {
            this.values = (String[])this.setting.getSetting().getMaxValue();
        }
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.setting.getName(), this.x + 6, this.y + 6, Color.white.getRGB());
        int valueWidth = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth((String)this.value);
        Client.INSTANCE.unicodeBasicFontRenderer.drawString((String)this.value, this.x + this.width - valueWidth, this.y + 6, Color.white.getRGB());
        if (Mouse.isButtonDown((int)0) && System.currentTimeMillis() - this.lastMS >= 200L && mouseX >= (float)(this.x + 3) && mouseX <= (float)(this.x + 3 + this.width) && mouseY >= (float)this.y && mouseY <= (float)(this.y + 20)) {
            int index = 0;
            int i = 0;
            while (i < this.values.length) {
                if (this.values[i].equals(this.value)) {
                    index = i;
                }
                ++i;
            }
            if (index + 1 < this.values.length) {
                this.value = this.values[index + 1];
                this.lastMS = System.currentTimeMillis();
            } else {
                this.value = this.values[0];
                this.lastMS = System.currentTimeMillis();
            }
        }
        if (!Mouse.isButtonDown((int)0)) {
            this.lastMS = System.currentTimeMillis() - 250L;
        }
        this.setting.getSetting().setValue(this.value);
        super.draw(mouseX, mouseY);
    }
}

