package com.zerosense.GuiClick;

import com.zerosense.Utils.Component;
import com.zerosense.mods.Module;

import com.zerosense.mods.ModuleManager;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickCategory extends Component {
    Module.Category category;
    int dx = 0,dy = 0;
    List<ClickModule> modules = new ArrayList<>();
    boolean showModules = false, pressed = false;

    public ClickCategory(int index, Module.Category category) {
        super(200, 100, 120, 30);
        this.category = category;
        y += height*2*index;

        int i = 0;
        for (Module module : ModuleManager.getModulesByCategory(category)) {
            modules.add(new ClickModule(i,module,this));
            i++;
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 1)
            showModules = !showModules;
        if (mouseButton == 0) {
            ClickGuiScreen.categories.remove(this);
            ClickGuiScreen.categories.add(0,this);
            if (ClickGuiScreen.clickedCategory == null)
                ClickGuiScreen.clickedCategory = this;
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseOn(mouseX, mouseY)) {
            onClick(mouseX, mouseY, mouseButton);
        }

        for (ClickModule module : modules) {
            module.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    public void onMove(int mouseX, int mouseY) {
        if (isPressed(mouseX, mouseY, 0)) {
            if (!pressed) {
                dx = mouseX - x;
                dy = mouseY - y;
                pressed = true;
            }
            setPosition(mouseX - dx, mouseY - dy);
        } else if (pressed) {
            pressed = false;
        }
    }

    public static int Astolfo(int var2, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + (long) (var2 * 109)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (showModules) {
            for (ClickModule module : modules) {
                module.draw(mouseX, mouseY);
            }
        }

        Gui.drawRect(getLeft(),getBottom(),getRight(),getTop(), Astolfo(10000,0.5f,0.3f) );

        mc.fontRendererObj.drawString(category.name(), x-mc.fontRendererObj.getStringWidth(category.name())/2,y-mc.fontRendererObj.FONT_HEIGHT/2,0xFFFFFFFF);
    }
}
