package me.injusttice.neutron.api.settings.impl;

import me.injusttice.neutron.api.settings.Setting;

public class DoubleSet extends Setting {
  public double value;

  public double min;

  public double max;

  public double inc;

  public String suffix;

  public DoubleSet(String name, double value, double min, double max, double inc) {
    this.name = name;
    this.value = value;
    this.min = min;
    this.max = max;
    this.inc = inc;
  }

  public DoubleSet(String name, double value, double min, double max, double inc, String suffix) {
    this.name = name;
    this.value = value;
    this.min = min;
    this.max = max;
    this.inc = inc;
    this.suffix = suffix;
  }

  public String getSuffix() {
    return (this.suffix == null) ? "" : this.suffix;
  }

  public void setSuffix(String s) {
    this.suffix = s;
  }

  public double getValue() {
    return this.value;
  }

  public void setValue(double value) {
    double prec = 1.0D / this.inc;
    this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * prec) / prec;
    onChange();
  }

  public double getMin() {
    return this.min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public double getMax() {
    return this.max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public double getInc() {
    return this.inc;
  }

  public void setInc(double inc) {
    this.inc = inc;
  }

  public void onChange() {}
}
