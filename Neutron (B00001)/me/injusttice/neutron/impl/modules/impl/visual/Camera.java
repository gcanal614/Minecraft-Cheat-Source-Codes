package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;

public class Camera extends Module {

    public static DoubleSet x = new DoubleSet("X", 1.0D, -1.0D, 2.0D, 0.1);
    public static DoubleSet y = new DoubleSet("Y", 0.0D, -1.0D, 1.0D, 0.1);
    public static DoubleSet z = new DoubleSet("Z", 0.0D, -1.0D, 1.0D, 0.1);
    public static DoubleSet zoom = new DoubleSet("Zoom", 0.0D, -2.0D, 2.0D, 0.1);
    public static DoubleSet scale = new DoubleSet("Scale", 0.90D, 0.0D, 4.0D, 0.01);

    public Camera() {
        super("Camera", 0, Category.VISUAL);
        addSettings(x, y, z, zoom, scale);
    }
}
