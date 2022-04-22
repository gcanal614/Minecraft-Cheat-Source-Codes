package com.zerosense.mods;

import com.zerosense.Events.Event;
import com.zerosense.Settings.KeybindSetting;
import com.zerosense.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Module {

    public String SettingModuleName;

    public Minecraft mc = Minecraft.getMinecraft();

    public boolean expanded;

    public boolean toggled;

    public String name;

    public int index;

    public Category category;

    public int enabledTicks;

    public KeybindSetting keyCode = new KeybindSetting(0);

    public List<Setting> settings = new ArrayList<>();

    public Module(String string, int Int, Category category){
        this.name = string;
        this.keyCode.code = Int;
        this.category = category;
        addSettings(new Setting[] {(Setting) this.keyCode});
        setDisplayname(string);
    }

    public void addSettings(Setting... settings){
        this.settings.addAll(Arrays.asList(settings));
    }

    public String getDisplayName(){
        return this.SettingModuleName;
    }

    public void setDisplayname(String paramString){
        this.SettingModuleName = paramString;
    }

    public boolean isEnabled(){
        return toggled;
    }

    public int getKey(){
        return keyCode.code;
    }

    public void onEvent(Event e){

    }

    public void setEnabled(boolean Boolean){
        this.toggled = Boolean;
    }

    public void toggle(){
        toggled = !toggled;
        if(toggled){
            this.enabledTicks = 0;
            onEnable();
        } else if(!toggled){
            onDisable();
        }
    }

    public void onEnable(){}
    public void onDisable(){}

    public enum Category{
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        RENDER("Render"),
        WORLD("World"),
        EXPLOIT("Exploit"),
        CONFIGS("Configs");

        public String name;
        public int moduleIndex;

        Category(String name){
            this.name = name;
        }
    }
}
