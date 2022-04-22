package me.injusttice.neutron.api.settings.impl;

import me.injusttice.neutron.api.settings.Setting;

public class StringSet extends Setting {
  public String text;

  public StringSet(String name, String defaultText) {
    this.name = name;
    this.text = defaultText;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
