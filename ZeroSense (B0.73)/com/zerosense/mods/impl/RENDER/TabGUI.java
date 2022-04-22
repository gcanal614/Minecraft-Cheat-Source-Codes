package com.zerosense.mods.impl.RENDER;

import com.zerosense.Events.Event;
import com.zerosense.Events.EventKey;
import com.zerosense.Events.impl.EventRenderGUI;
import com.zerosense.Settings.*;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

public class TabGUI extends Module {

    public int currentTab;

    public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE, Category.RENDER);
        toggled = true;
    }

    public static int rainbow(int delay){
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }


    final int[] yDist = {2};
    final  int[] counter = {1};

    public void onEvent(Event e){
        if(e instanceof EventRenderGUI){
            int count = 0;
            int primaryColor = 0xff0090ff, secondaryColor = 0xff0070aa;
            FontRenderer fr = mc.fontRendererObj;

            Gui.drawRect(5, 30.5, 70, 30 + Category.values().length * 16 + 3, 0x90000000);
            Gui.drawRect(5, 32 + currentTab * 16, 7,32 + currentTab * 16 + 12 + 4F, rainbow(count * 1));

            for(Category c : Module.Category.values()){

                fr.drawStringWithShadow(c.name, 11, 33 + count*16, -1);
                count++;
            }

            if(expanded){
                Category category = Module.Category.values()[currentTab];
                List<Module> modules = ModuleManager.getModulesByCategory(category);

                if(modules.size() == 0)
                    return;

                Gui.drawRect(70, 30.5, 70 + 68, 30 + modules.size() * 16 + 1.5, 0x90000000);
                Gui.drawRect(70, 32 + category.moduleIndex * 16, 7 + 65,32 + category.moduleIndex * 16 + 12 + 2.5F, rainbow(count * 1));

                count = 0;
                for(Module m : modules){

                    fr.drawStringWithShadow(m.name, 73, 33 + count*16, -1);

                    if(count == category.moduleIndex && m.expanded){
                        int index = 0, maxLength = 0;
                        for(Setting setting : m.settings) {
                            if(setting instanceof BooleanSetting){
                                BooleanSetting bool = (BooleanSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.getName() + ": " + (bool.isToggled() ? "Enabled" : "Disabled"))){
                                    fr.getStringWidth(setting.getName() + ": " + (bool.isToggled() ? "Enabled" : "Disabled"));
                                }
                            }

                            if(setting instanceof NumberSetting){
                                NumberSetting number = (NumberSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.getName() + ": " + number.getValue())){
                                    fr.getStringWidth(setting.getName() + ": " + number.getValue());
                                }
                            }

                            if(setting instanceof ModeSetting){
                                ModeSetting mode = (ModeSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.getName() + ": " + mode.getMode())){
                                    fr.getStringWidth(setting.getName() + ": " + mode.getMode());
                                }
                            }

                            if(setting instanceof KeybindSetting){
                                KeybindSetting key = (KeybindSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.getName() + ": " + Keyboard.getKeyName(key.code))){
                                    fr.getStringWidth(setting.getName() + ": " + Keyboard.getKeyName(key.code));
                                }
                            }
                            index++;
                        }
                        Gui.drawRect(70 + 68, 30.5 + maxLength * 2, 70 + 68 + 68 + 6, 30 + m.settings.size() * 16 + 1.5 + maxLength * 3, 0x90000000);
                        Gui.drawRect(70 + 68, 30.5F + m.index * 16, 7 + 61 + 68 + 6 + 70 + maxLength,33 + m.index * 16 + 12 + 2.5F, m.settings.get(m.index).focused ? primaryColor : rainbow(count * 1));
                        index = 0;
                        for(Setting setting : m.settings) {
                            if(setting instanceof BooleanSetting){
                                BooleanSetting bool = (BooleanSetting) setting;
                                fr.drawStringWithShadow(setting.getName() + ": " + (bool.isToggled() ? "Enabled" : "Disabled"), 73 + 68, 35 + index * 16, -1);
                            }

                            if(setting instanceof NumberSetting){
                                NumberSetting number = (NumberSetting) setting;
                                fr.drawStringWithShadow(setting.getName() + ": " + number.getValue(), 73 + 68, 35 + index * 16, -1);
                            }

                            if(setting instanceof ModeSetting){
                                ModeSetting mode = (ModeSetting) setting;
                                fr.drawStringWithShadow(setting.getName() + ": " + mode.getMode(

                                ), 73 + 68, 35 + index * 16, -1);
                            }

                            if(setting instanceof KeybindSetting){
                                KeybindSetting key = (KeybindSetting) setting;
                                fr.drawStringWithShadow(setting.getName() + ": " + Keyboard.getKeyName(key.code), 73 + 68, 35 + index * 16, -1);
                            }
                            index++;
                        }
                    }
                    count++;
                }
            }
        }

        if (e instanceof com.zerosense.Events.impl.EventKey) {
            int code = ((com.zerosense.Events.impl.EventKey)e).code;

            Category category = Module.Category.values()[currentTab];
            List<Module> modules = ModuleManager.getModulesByCategory(category);

            if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded){
                Module module = modules.get(category.moduleIndex);

                if(!module.settings.isEmpty() && module.settings.get(module.index).focused && module.settings.get(module.index) instanceof KeybindSetting){
                    if(code != Keyboard.KEY_RETURN && code != Keyboard.KEY_UP && code != Keyboard.KEY_DOWN && code != Keyboard.KEY_LEFT && code != Keyboard.KEY_RIGHT && code != Keyboard.KEY_ESCAPE && code != Keyboard.KEY_SPACE){
                        KeybindSetting keyBind = (KeybindSetting)module.settings.get(module.index);

                        keyBind.code = code;
                        keyBind.focused = false;

                        return;
                    }
                }
            }

            if(code == Keyboard.KEY_UP){
                if(expanded){
                    if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                        Module module = modules.get(category.moduleIndex);
                        if(module.settings.get(module.index).focused){
                            Setting setting = module.settings.get(module.index);

                            if (setting instanceof NumberSetting) {
                                ((NumberSetting) setting).increment(true);
                            }

                        } else {
                            if (module.index <= 0) {
                                module.index = module.settings.size() - 1;
                            } else
                                module.index--;
                        }
                    }else {
                        if (category.moduleIndex <= 0) {
                            category.moduleIndex = modules.size() - 1;
                        } else
                            category.moduleIndex--;
                    }
                }else {
                    if(currentTab <= 0){
                        currentTab = Category.values().length - 1;
                    }else
                        currentTab--;
                }
            }

            if(code == Keyboard.KEY_DOWN){
                if(expanded){
                    if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded){
                        Module module = modules.get(category.moduleIndex);
                        if(module.settings.get(module.index).focused){
                            Setting setting = module.settings.get(module.index);

                            if (setting instanceof NumberSetting) {
                                ((NumberSetting) setting).increment(false);
                            }

                        }else {
                            if (module.index >= module.settings.size() - 1) {
                                module.index = 0;
                            } else
                                module.index++;
                        }
                    }else {
                        if (category.moduleIndex >= modules.size() - 1) {
                            category.moduleIndex = 0;
                        } else
                            category.moduleIndex++;
                    }
                }else{
                    if(currentTab >= Category.values().length - 1){
                        currentTab = 0;
                    }else
                        currentTab++;
                }
            }
            if(code == Keyboard.KEY_RETURN){
                if(expanded && modules.size() != 0){
                    Module module = modules.get(category.moduleIndex);
                    if(!module.expanded)
                        module.expanded = true;
                    else if(module.expanded){
                        module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
                    }
                }


                final int[] yDist = {2};
                final  int[] counter = {1};
            }


            if(code == Keyboard.KEY_RIGHT){
                if(expanded && modules.size() != 0){
                    Module module = modules.get(category.moduleIndex);
                    if(expanded && !modules.isEmpty() && module.expanded){
                        Setting setting = module.settings.get(module.index);

                        if(module.settings.get(module.index).focused) {
                            if (setting instanceof BooleanSetting) {
                                ((BooleanSetting) setting).isToggled();
                            }
                            if (setting instanceof ModeSetting) {
                                ((ModeSetting) setting).cycle(true);
                            }
                        }
                    }else {
                        if (!module.name.equals("TabGUI")) {
                            module.toggle();

                        }
                    }
                }else {
                    expanded = true;
                }
            }

            if(code == Keyboard.KEY_LEFT) {
                Module module = modules.get(category.moduleIndex);
                if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {

                    if(module.settings.get(module.index).focused){

                    }else {
                        modules.get(category.moduleIndex).expanded = false;
                    }
                } else {
                    expanded = false;
                }

            }
        }
    }
}
