package com.zerosense.Settings;

public class BooleanSetting extends Setting {
    public boolean toggled;

    @Override
    public Object get() {
        return toggled;
    }

    public boolean isToggled() {
        return toggled && canShow();
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public BooleanSetting(String name, boolean toggled) {
        super(name);
        this.toggled = toggled;
    }
    public BooleanSetting(String name, boolean toggled, Setting parent, Object required) {
        super(name);
        this.parent = parent;
        this.required = required;
        this.toggled = toggled;
    }
    public BooleanSetting(String name, Setting parent, Object required) {
        super(name);
        this.parent = parent;
        this.required = required;
        this.toggled = false;
    }
    public BooleanSetting(String name) {
        super(name);
        this.toggled = false;
    }
}