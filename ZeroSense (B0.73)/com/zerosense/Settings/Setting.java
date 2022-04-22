package com.zerosense.Settings;

public class Setting {
    public boolean focused;
    public String name;
    Setting parent = null;
    Object required;

    public Setting() {
    }

    public boolean canShow() {
        if (parent instanceof NumberSetting) {
            return ((NumberSetting) parent).getValue() == (double) required;
        }
        return isStatic() || parent.get() == required;
    }

    public boolean isStatic() {
        return parent == null;
    }

    public Object get() {
        return null;
    }

    public String getName() {
        return name;
    }

    public Setting(String name) {
        this.name = name;
    }
}