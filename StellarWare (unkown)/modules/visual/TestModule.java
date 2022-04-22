package stellar.skid.modules.visual;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.lwjgl.input.Keyboard;

import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.gui.screen.test.TestScreen;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;

public class TestModule extends AbstractModule{
	
    public TestModule(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "Test Module", "Test Module", 0, EnumModuleType.VISUALS, "");
    }
    
    @Override
    public void onEnable() {
    	mc.displayGuiScreen(new TestScreen());
    }

}
