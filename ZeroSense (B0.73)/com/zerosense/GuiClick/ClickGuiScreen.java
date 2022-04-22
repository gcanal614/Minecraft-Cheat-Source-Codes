package com.zerosense.GuiClick;

import com.zerosense.ZeroSense;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;
import com.zerosense.mods.impl.RENDER.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {
    public static List<ClickCategory> categories = new ArrayList<>();
    public static boolean loaded = false;
    public static ClickCategory clickedCategory;

    public static int Astolfo(int var2, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + (long) (var2 * 109)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        Gui.drawRect(sr.getScaledHeight(), 0,0,Astolfo(10000,0.4f,0.3f) , new Color(2, 2, 2, 10).getRGB());

        for (int i = categories.size() - 1; i >= 0; i--) {
            ClickCategory category = categories.get(i);
            category.draw(mouseX, mouseY);
        }
        if (clickedCategory != null)
            clickedCategory.onMove(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (int i = 0; i < categories.size(); i++) {
            ClickCategory category = categories.get(i);
            category.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            clickedCategory = null;
        }
    }

    @Override
    public void initGui() {
        int i = 0;
        if (!loaded) {
            for (Module.Category value : Module.Category.values()) {
                categories.add(new ClickCategory(i, value));
                i++;
            }
            loaded = true;
        }
    }

    @Override
    public void onGuiClosed() {
        ModuleManager.getModuleByName("ClickGui").toggle();
        ClickGui.categories = categories;
    }
}
