package me.injusttice.neutron.api.settings;

import java.util.ArrayList;
import java.util.Arrays;

public class ModuleCategory extends Setting {
  public boolean expanded;

  public ArrayList<Setting> settingsOnCat = new ArrayList<>();

  public ModuleCategory(String displayName) {
    this.name = displayName;
  }

  public void addCatSettings(Setting... settings) {
    this.settingsOnCat.addAll(Arrays.asList(settings));
  }

  public boolean isExpanded() {
    return this.expanded;
  }

  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
  }
}
