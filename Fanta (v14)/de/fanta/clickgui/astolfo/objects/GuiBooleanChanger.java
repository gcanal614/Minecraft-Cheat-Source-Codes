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
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class GuiBooleanChanger
extends GuiChanger<Boolean> {
    private TimeUtil clickWaiter;

    public GuiBooleanChanger(int x, int y, Setting setting, Module.Type cat) {
        super(x, y, setting, cat);
        this.height = 20;
        this.width = 119;
        this.clickWaiter = new TimeUtil();
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.value == null) {
            this.value = (Boolean)this.setting.getSetting().getValue();
        }
        if (((Boolean)this.value).booleanValue()) {
            GuiBooleanChanger.drawRect(this.x + 3, this.y, this.x + 3 + this.width, this.y + 20, this.cat.getColor());
        }
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.setting.getName(), this.x + 6, this.y + 6, Color.white.getRGB());
        if (Mouse.isButtonDown((int)0) && this.clickWaiter.hasReached(200L) && mouseX >= (float)(this.x + 3) && mouseX <= (float)(this.x + 3 + this.width) && mouseY >= (float)this.y && mouseY <= (float)(this.y + 20)) {
            this.value = (Boolean)this.value == false;
            this.clickWaiter.reset();
        }
        this.setting.getSetting().setValue(this.value);
        super.draw(mouseX, mouseY);
    }
}

