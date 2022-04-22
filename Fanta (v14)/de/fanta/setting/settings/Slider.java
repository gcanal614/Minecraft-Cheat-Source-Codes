/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.setting.settings;

import de.fanta.setting.settings.BaseSetting;

public class Slider
implements BaseSetting {
    public double curValue;
    private double minValue;
    private double maxValue;
    private double stepValue;

    public Slider(double minValue, double maxValue, double stepValue, double curValue) {
        this.curValue = curValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepValue = stepValue;
    }

    @Override
    public Object getMinValue() {
        return this.minValue;
    }

    @Override
    public Object getMaxValue() {
        return this.maxValue;
    }

    public double getStepValue() {
        return this.stepValue;
    }

    @Override
    public Object getValue() {
        return this.curValue;
    }

    @Override
    public void setValue(Object value) {
        this.curValue = (Double)value;
    }

    @Override
    public void setMinValue(Object value) {
        this.minValue = (Double)value;
    }

    @Override
    public void setMaxValue(Object value) {
        this.maxValue = (Double)value;
    }
}

