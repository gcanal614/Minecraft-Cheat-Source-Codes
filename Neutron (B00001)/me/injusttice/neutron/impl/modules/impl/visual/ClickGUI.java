package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.ui.clickguis.BetaClickGui;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.VISUAL);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new BetaClickGui());
        toggle();
    }
}
