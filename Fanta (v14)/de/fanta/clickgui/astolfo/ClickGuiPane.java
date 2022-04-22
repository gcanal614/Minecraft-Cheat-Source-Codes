/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.astolfo;

import com.google.gson.annotations.Expose;
import de.fanta.Client;
import de.fanta.clickgui.astolfo.ClickGuiModulePane;
import de.fanta.clickgui.astolfo.ClickGuiScreen;
import de.fanta.clickgui.astolfo.objects.GuiChanger;
import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import de.fanta.module.Module;
import de.fanta.utils.ColorUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class ClickGuiPane
extends Gui {
    @Expose
    public int x;
    @Expose
    public int y;
    private int width = 125;
    private int height = 20;
    @Expose
    public boolean extended;
    @Expose
    public Module.Type type;
    private ClickGuiScreen screen;
    public List<ClickGuiModulePane> modulePanes = new ArrayList<ClickGuiModulePane>();
    private Color BACKGROUND_COLOR = Color.decode("#1A1A1A");
    private Color BACKGROUND_COLOR2 = Color.decode("#252525");
    private GlyphPageFontRenderer fontRenderer = ClientFont.font(20, "AstolfoClickgui-IconFont", true);
    private char show = (char)103;
    private char hide = (char)105;

    public ClickGuiPane(Module.Type type, int x, int y, ClickGuiScreen screen) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.screen = screen;
        int y2 = y;
        for (Module module : Client.INSTANCE.moduleManager.modules) {
            if (module.getType() != this.type) continue;
            ClickGuiModulePane modPane = new ClickGuiModulePane(module, x, y2, screen, this);
            this.modulePanes.add(modPane);
            y2 += modPane.getHeight();
        }
    }

    public void draw(float mouseX, float mouseY) {
        this.update();
        int height2 = 0;
        for (ClickGuiModulePane pane : this.modulePanes) {
            height2 += pane.getHeight();
        }
        ClickGuiPane.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.BACKGROUND_COLOR.getRGB());
        if (this.extended) {
            ClickGuiPane.drawRect(this.x, this.y + this.height, this.x + this.width, this.y + this.height + height2, this.BACKGROUND_COLOR.getRGB());
        }
        int y2 = this.y + this.height;
        for (ClickGuiModulePane pane : this.modulePanes) {
            if (this.extended) {
                pane.draw(mouseX, mouseY);
            }
            pane.x = this.x;
            pane.y = y2;
            y2 += pane.getHeight();
            int yAdd = pane.y;
            GuiChanger prev = null;
            for (GuiChanger changer : pane.changers) {
                changer.x = pane.x;
                changer.y = yAdd + 18;
                prev = changer;
                yAdd += prev.height;
            }
        }
        if (!this.extended) {
            this.drawHollowRect((double)this.x - 0.7, (double)this.y - 0.5, (double)this.width + 1.3, (double)this.height + 0.5, 0.7, this.type.getColor());
        } else {
            this.drawHollowRect((double)this.x - 0.7, (double)this.y - 0.5, (double)this.width + 1.3, (double)(this.height + height2), 0.7, this.type.getColor());
        }
        Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.type.name(), this.x + 2, (float)this.y + 2.5f, Color.white.getRGB());
        this.fontRenderer.drawString("" + this.type.getIcon(), this.x + this.width - 15, (float)this.y + 3.5f, this.type.getColor());
        this.fontRenderer.drawString(this.extended ? "" + this.show : "" + this.hide, (float)(this.x + this.width) - 27.5f, (float)this.y + 3.5f, this.extended ? this.type.getColor() : ColorUtils.getMulitpliedColor(this.BACKGROUND_COLOR2.getRGB(), 1.3));
    }

    public void update() {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHovered(mouseX, mouseY) && mouseButton == 1) {
            boolean bl = this.extended = !this.extended;
        }
        if (this.extended) {
            for (ClickGuiModulePane pane : this.modulePanes) {
                pane.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        return false;
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (this.screen.selectedPane == this) {
            this.x = mouseX - this.width / 2;
            this.y = mouseY - this.height / 2;
        }
        if (this.screen.selectedPane != this) {
            this.screen.selectedPane.mouseDragged(mouseX, mouseY);
        }
        int y2 = this.y + this.height;
        for (ClickGuiModulePane pane : this.modulePanes) {
            pane.x = this.x;
            pane.y = y2;
            y2 += pane.getHeight();
            if (!Mouse.isButtonDown((int)0)) continue;
            int yAdd = pane.y;
            GuiChanger prev = null;
            for (GuiChanger changer : pane.changers) {
                changer.x = pane.x;
                changer.y = yAdd + 18;
                prev = changer;
                yAdd += prev.height;
            }
        }
    }

    public void mouseReleased() {
    }

    public void onGuiClosed() {
        for (ClickGuiModulePane pane : this.modulePanes) {
            pane.onGuiClosed();
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}

