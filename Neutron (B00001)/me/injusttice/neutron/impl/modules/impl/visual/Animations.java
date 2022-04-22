package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;

public class Animations extends Module {

	public ModeSet mode = new ModeSet("Mode", "1.7", "1.7", "Old", "Spin", "Spinny", "Wingy", "Sigma", "Plain", "Slide", "Exhibition");
	public static DoubleSet spinSpeed = new DoubleSet("Spin", 2, 0, 20, 1.0);
	public static DoubleSet swingSpeed = new DoubleSet("Speed", 6, 0, 15, 1.0);

    public Animations() {
        super("Animations", 0, Category.VISUAL);
        addSettings(mode, spinSpeed, swingSpeed);
    }
}
