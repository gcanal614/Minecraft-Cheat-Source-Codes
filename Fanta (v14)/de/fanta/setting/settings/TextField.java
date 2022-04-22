/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.setting.settings;

public class TextField {
    public boolean focused;

    public TextField(boolean state) {
        this.focused = this.focused = false;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setState(boolean focused) {
        this.focused = focused;
    }
}

