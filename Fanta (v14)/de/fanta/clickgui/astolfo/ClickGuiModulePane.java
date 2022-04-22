/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.astolfo;

import de.fanta.Client;
import de.fanta.clickgui.astolfo.ClickGuiPane;
import de.fanta.clickgui.astolfo.ClickGuiScreen;
import de.fanta.clickgui.astolfo.objects.GuiBooleanChanger;
import de.fanta.clickgui.astolfo.objects.GuiChanger;
import de.fanta.clickgui.astolfo.objects.GuiColorChanger;
import de.fanta.clickgui.astolfo.objects.GuiNumberChanger;
import de.fanta.clickgui.astolfo.objects.GuiStringSelector;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.utils.ColorUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class ClickGuiModulePane
extends Gui {
    public int x;
    public int y;
    public int width = 125;
    public int height = 20;
    public int baseHeight = 20;
    private boolean extended;
    private Module module;
    private ClickGuiScreen screen;
    private ClickGuiPane paneOn;
    private Color BACKGROUND_COLOR = Color.decode("#1A1A1A");
    private Color BACKGROUND_COLOR2 = Color.decode("#252525");
    public List<GuiChanger> changers = new ArrayList<GuiChanger>();
    private boolean init = false;

    public ClickGuiModulePane(Module module, int x, int y, ClickGuiScreen screen, ClickGuiPane paneOn) {
        this.x = x;
        this.y = y;
        this.module = module;
        this.screen = screen;
    }

    public void draw(float mouseX, float mouseY) {
        block21: {
            if (this.init) break block21;
            int yAdd = 0;
            for (Setting setting : this.module.getSettings()) {
                GuiChanger curr = null;
                switch (setting.getSetting().getClass().getSimpleName()) {
                    case "CheckBox": {
                        curr = new GuiBooleanChanger(this.x + this.width + 1, this.y + this.changersAdd(), setting, this.module.getType());
                        this.changers.add(curr);
                        break;
                    }
                    case "Slider": {
                        curr = new GuiNumberChanger(this.x, this.y + this.changersAdd() + 18, setting, this.module.getType());
                        this.changers.add(curr);
                        break;
                    }
                    case "DropdownBox": {
                        curr = new GuiStringSelector(this.x + this.width + 1, this.y + this.changersAdd(), setting, this.module.getType());
                        this.changers.add(curr);
                        break;
                    }
                    case "ColorValue": {
                        curr = new GuiColorChanger(this.x + this.width + 1, this.y + this.changersAdd(), setting, this.module.getType());
                        this.changers.add(curr);
                        break;
                    }
                }
                if (curr == null) continue;
                yAdd += curr.height;
            }
            this.init = true;
        }
        this.height = !this.extended ? this.baseHeight : this.baseHeight + this.changersAdd();
        Color COLOR_ENABLED = new Color(this.module.getType().getColor());
        Color COLOR_DISABLED = this.BACKGROUND_COLOR2;
        this.update();
        ClickGuiModulePane.drawRect(this.x, this.y, this.x + this.width, this.y + this.baseHeight, this.isHovered(mouseX, mouseY) ? this.BACKGROUND_COLOR.getRGB() : this.BACKGROUND_COLOR.getRGB());
        ClickGuiModulePane.drawRect((float)this.x + 2.25f, (float)this.y - 2.5f, (float)(this.x + this.width) - 1.75f, (float)(this.y + this.baseHeight) - 2.5f, this.extended ? COLOR_DISABLED.getRGB() : (!this.module.isState() ? (this.isHovered(mouseX, mouseY) ? ColorUtils.getMulitpliedColor(COLOR_DISABLED, 1.5) : COLOR_DISABLED.getRGB()) : (this.isHovered(mouseX, mouseY) ? ColorUtils.getMulitpliedColor(COLOR_ENABLED, 0.75) : COLOR_ENABLED.getRGB())));
        int stringWidth = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.module.getName()) + 5;
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.module.getName(), this.x + this.width - stringWidth, this.y, this.extended ? (this.module.isState() ? COLOR_ENABLED.getRGB() : Color.white.getRGB()) : Color.white.getRGB());
        if (this.extended) {
            for (GuiChanger changer : this.changers) {
                changer.extended = true;
                changer.draw(mouseX, mouseY);
            }
        } else {
            for (GuiChanger changer : this.changers) {
                changer.extended = false;
            }
        }
    }

    public void update() {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHovered(mouseX, mouseY)) {
            if (mouseButton == 1 && this.module.getSettings().size() > 0) {
                boolean bl = this.extended = !this.extended;
            }
            if (mouseButton == 0) {
                this.module.toggle();
            }
        }
        for (GuiChanger changer : this.changers) {
            changer.mouseClicked(mouseX, mouseY, mouseButton);
        }
        return false;
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (Mouse.isButtonDown((int)0)) {
            int yAdd = this.y;
            GuiChanger prev = null;
            for (GuiChanger changer : this.changers) {
                changer.x = this.x;
                changer.y = yAdd + 18;
                prev = changer;
                yAdd += prev.height;
            }
        }
    }

    public void mouseReleased() {
    }

    public void onGuiClosed() {
        for (GuiChanger changer : this.changers) {
            if (changer.value == null) continue;
            changer.setting.getSetting().setValue(changer.value);
        }
    }

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= (float)this.x && mouseX <= (float)(this.x + this.width) && (double)mouseY >= (double)this.y - 2.5 && (double)mouseY <= (double)(this.y + this.baseHeight) - 2.5;
    }

    public int getHeight() {
        return this.height;
    }

    public int changersAdd() {
        int add = 0;
        try {
            for (GuiChanger changer : this.changers) {
                add += changer.height;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return add;
    }
}

