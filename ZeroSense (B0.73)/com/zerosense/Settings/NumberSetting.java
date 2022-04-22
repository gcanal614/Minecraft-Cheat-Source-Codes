package com.zerosense.Settings;

public class NumberSetting extends Setting {

    double min, max, inc, val;

    @Override
    public Object get() {
        return val;
    }

    public NumberSetting(String name, double min, double max, double inc, double defaultValue) {
        super(name);
        this.val = defaultValue;
        this.inc = inc;
        this.max = max;
        this.min = min;
    }

    public void increment(boolean negative) {
        val = Math.min(max, Math.max(min, val + inc * (negative ? -1 : 1)));
    }

    public NumberSetting(String name, double min, double max, double inc, double defaultValue, Setting parent, Object required) {
        super(name);
        this.val = defaultValue;
        this.inc = inc;
        this.max = max;
        this.min = min;
        this.parent = parent;
        this.required = required;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getValue() {
        return val;
    }

    public void setValue(double value) {
        double prec = 1/inc;
        this.val = Math.round(Math.min(max, Math.max(min, value)) * prec) / prec;
    }
}