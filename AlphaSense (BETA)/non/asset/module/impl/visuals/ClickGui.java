package non.asset.module.impl.visuals;

import org.lwjgl.input.Keyboard;

import non.asset.gui.materialui.MaterialUI;
import non.asset.module.Module;

public class ClickGui extends Module {
    
	private MaterialUI materialUI = null;
    public ClickGui() {
        super("ClickGUI", Category.VISUALS);
        setHidden(true);
        setKeybind(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        if (getMc().theWorld != null) {
            if (getMc().theWorld != null) {
            	if (materialUI == null) {
                    materialUI = new MaterialUI();
                    materialUI.initializedUI();
                }
                getMc().displayGuiScreen(materialUI);
            }
            toggle();
        }
    }
}
