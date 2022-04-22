/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.setting.settings;

import de.fanta.setting.settings.BaseSetting;

public class CheckBox
implements BaseSetting {
    public boolean state;

    public CheckBox(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public Object getValue() {
        return this.state;
    }

    @Override
    public Object getMinValue() {
        return false;
    }

    @Override
    public Object getMaxValue() {
        return true;
    }

    @Override
    public void setValue(Object value) {
        this.state = (Boolean)value;
    }

    @Override
    public void setMinValue(Object value) {
    }

    @Override
    public void setMaxValue(Object value) {
    }
}

