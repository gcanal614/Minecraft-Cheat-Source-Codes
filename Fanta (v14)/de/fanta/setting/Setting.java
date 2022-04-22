/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.setting;

import de.fanta.setting.settings.BaseSetting;

public class Setting {
    private String name;
    private BaseSetting setting;

    public Setting(String name, BaseSetting setting) {
        this.name = name;
        this.setting = setting;
    }

    public String getName() {
        return this.name;
    }

    public BaseSetting getSetting() {
        return this.setting;
    }
}

