package de.tired.api.guis.clickgui.setting.impl;
import de.tired.api.guis.clickgui.setting.Setting;
import de.tired.module.Module;

import java.util.function.Supplier;

public class TextBoxSetting extends Setting {

	private String value;

	public TextBoxSetting(String name, Module parent, String defaultValue, Supplier<Boolean> dependency, DeviderSetting deviderSetting) {
		super(name, parent, dependency, deviderSetting);
		this.value = defaultValue;
	}

	public TextBoxSetting(String name, Module parent, String defaultValue, Supplier<Boolean> dependency) {
		this(name, parent, defaultValue, dependency, null);
	}

	public TextBoxSetting(String name, Module parent, String defaultValue) {
		this(name, parent, defaultValue, () -> true);
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}