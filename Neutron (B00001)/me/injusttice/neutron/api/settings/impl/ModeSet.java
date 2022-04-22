package me.injusttice.neutron.api.settings.impl;

import me.injusttice.neutron.api.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModeSet extends Setting {
  private String selected;

  public int index;

  public List<String> modes = new ArrayList<>();

  public ModeSet(String name, String defaultMode, String... modeOptions) {
    this.name = name;
    this.modes = Arrays.asList(modeOptions);
    this.index = this.modes.indexOf(defaultMode);
    this.selected = this.modes.get(this.index);
  }

  public String getMode() {
    return this.modes.get(this.index);
  }

  public boolean is(String mode) {
    return (this.index == this.modes.indexOf(mode));
  }

  public void setSelected(String selected) {
    this.selected = selected;
    this.index = this.modes.indexOf(selected);
    onChange();
  }

  public void positiveCycle() {
    if (this.index < this.modes.size() - 1) {
      this.index++;
    } else {
      this.index = 0;
    }
    onChange();
  }

  public void negativeCycle() {
    if (this.index <= 0) {
      this.index = this.modes.size() - 1;
    } else {
      this.index--;
    }
    onChange();
  }

  public void setIndex(int index) {
    this.index = index;
    onChange();
  }

  public void onChange() {}
}
