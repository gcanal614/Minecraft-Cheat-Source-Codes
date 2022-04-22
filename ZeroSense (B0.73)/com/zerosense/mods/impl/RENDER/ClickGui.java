package com.zerosense.mods.impl.RENDER;

import com.zerosense.GuiClick.ClickCategory;
import com.zerosense.GuiClick.ClickGuiScreen;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ClickGui extends Module {
    public static List<ClickCategory> categories;
    public static boolean loaded = false;

    public ClickGui() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGuiScreen());
    }
}
