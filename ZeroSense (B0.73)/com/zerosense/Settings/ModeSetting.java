package com.zerosense.Settings;

public class ModeSetting extends Setting {
   public int index = 0;
    String[] modes;

    @Override
    public Object get() {
        return getMode();
    }

    public ModeSetting(String name, String... modes) {
        super(name);
        this.modes = modes;
    }


    public ModeSetting(String name, Setting parent, Object required, String... modes) {
        super(name);
        this.modes = modes;
        this.parent = parent;
        this.required = required;
    }

    public String getMode() {
        return modes[index];
    }

    public int getIndex() {
        return index;
    }

    public String[] getModes() {
        return modes;
    }

    public void cycle(boolean invert) {
        index += invert ? -1 : 1;

        if (index < 0) {
            index = modes.length-1;
        }
        if (index >= modes.length) {
            index = 0;
        }
    }

    public boolean is(String mode) {
        return getMode().equalsIgnoreCase(mode);
    }
}