package com.zerosense.GuiClick;

import com.zerosense.Settings.Setting;
import com.zerosense.Utils.Component;
import com.zerosense.mods.Module;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickModule extends Component {
    Module module;
    List<ClickSetting> settings = new ArrayList<>();
    ClickCategory parent;
    boolean showSettings = false;
    int index;

    public ClickModule(int index, Module module, ClickCategory parent) {
        super(200, 100, 100, 30);
        this.parent = parent;
        this.module = module;
        this.index = index;

        int i = 0;
        for (Setting setting : module.settings) {
            settings.add(new ClickSetting(i, setting, this));
            i++;
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            module.toggle();
        if (mouseButton == 1)
            showSettings = !showSettings;
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!parent.showModules)
            return;
        if (mouseOn(mouseX, mouseY))
            onClick(mouseX, mouseY, mouseButton);

        for (ClickSetting setting : settings) {
            setting.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        setPosition(parent.getX(), parent.getY() + parent.getHeight()/2+getHeight()/2 + getHeight() * index);

        if (showSettings) {
            int i = 0;
            for (ClickSetting setting : settings) {
                if (setting.setting.canShow()) {
                    setting.draw(i, mouseX, mouseY);
                    i++;
                }
            }
        }

        Gui.drawRect(getLeft(),getBottom(),getRight(),getTop(), module.toggled ? 0xAA515151 : 0xAA000000);

        mc.fontRendererObj.drawString(module.getDisplayName(), x-mc.fontRendererObj.getStringWidth(module.getDisplayName())/2,y-mc.fontRendererObj.FONT_HEIGHT/2, module.toggled ? new Color(22, 191, 10).getRGB() : 0xFFFFFFFF);
    }
}
