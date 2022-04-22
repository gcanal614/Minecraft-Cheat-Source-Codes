package stellar.skid.modules.visual;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.lwjgl.input.Keyboard;

import stellar.skid.StellarWare;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.utils.notifications.NotificationType;

public class ConfigManager extends AbstractModule {
	
    public ConfigManager(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "ConfigManager", "Config Manager", Keyboard.KEY_RSHIFT, EnumModuleType.VISUALS, "");
    }
    
    public void onEnable() {
    	StellarWare.getInstance().notificationManager.pop("Registered!", NotificationType.SUCCESS);
    }

}
