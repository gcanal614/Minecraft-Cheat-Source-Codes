package com.zerosense.Settings;

public class KeybindSetting extends Setting {
    public int code;

    public KeybindSetting(int code) {
        this.name = "Keybind";
        this.code = code;
    }

    public int getKeyCode(String keyName) {
        return this.code;
    }

    public void setKeyCode(int code) {
        this.code = code;
    }
}
