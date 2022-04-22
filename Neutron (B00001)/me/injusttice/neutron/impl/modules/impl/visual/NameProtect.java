package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.StringSet;

public class NameProtect extends Module {

	public static StringSet nameprotect = new StringSet("Name", "User");

	public NameProtect() {
		super("NameProtect", 0, Category.OTHER);
		addSettings(nameprotect);
	}

	public static String getNameProtect() {
		return nameprotect.getText();
	}
}
