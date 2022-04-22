package me.injusttice.neutron.api.settings.impl;

import me.injusttice.neutron.api.settings.Setting;

public class BooleanSet extends Setting {
  public boolean enabled;

  public BooleanSet(String name, boolean enabled) {
    this.name = name;
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void toggle() {
    setEnabled(!this.enabled);
  }
}
