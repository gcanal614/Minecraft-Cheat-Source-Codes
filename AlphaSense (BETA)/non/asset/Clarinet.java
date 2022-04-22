package non.asset;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import non.asset.arraylist.ArrayListManager;
import non.asset.command.CommandManager;
import non.asset.config.ConfigManager;
import non.asset.event.bus.EventBus;
import non.asset.friend.FriendManager;
import non.asset.gui.account.system.AccountManager;
import non.asset.module.ModuleManager;
import non.asset.notification.NotificationManager;
import non.asset.utils.OFC.DCConnection.DiscordARP;
import non.asset.utils.oldthealtening.AltService;

public enum Clarinet {
    INSTANCE;
    private File dir;
    private EventBus eventBus = new EventBus();
    private FriendManager friendManager;
    private ConfigManager configManager;
    private ModuleManager moduleManager = new ModuleManager();
    private NotificationManager notificationManager = new NotificationManager();
    private ArrayListManager arraylistManager = new ArrayListManager();
    private CommandManager commandManager = new CommandManager();
    private AltService altService = new AltService();
    private AccountManager accountManager;
    private boolean autoRelogTheAltening = false;
    private boolean autoRelogNormal = false;
    
    public static String name = "AlphaSense X";
    public static String version = "";
    
    /**
     * BETA !!!1!
     * */
    public static boolean beta = false;
    
    public static boolean golingask = beta;
    
    public static boolean logPassed = false;

	public static DiscordARP discordRP = new DiscordARP();

	public static UpdateMotion hud = new UpdateMotion();
	
    public void startClient() {
    	
        dir = new File(Minecraft.getMinecraft().mcDataDir, "AlphaSense");
        
        moduleManager.setDir(new File(dir, "modules"));
        
        System.out.println("Initializing Modules...");
        moduleManager.initializeModules();

        System.out.println("Loading Modules...");
        moduleManager.loadModules();

        System.out.println("Loading FriendManager...");
        friendManager = new FriendManager(dir);

        configManager = new ConfigManager(new File(dir, "configs"));
        

        System.out.println("Loading ConfigManager...");
        configManager.load();

        System.out.println("Initializing ConfigManager...");
        commandManager.initialize();

        System.out.println("Starting  AccountManager...");
        accountManager = new AccountManager(dir);
    }

    public void endClient() {
        moduleManager.saveModules();
        friendManager.getFriendSaving().saveFile();
        accountManager.save();
    }

    public static DiscordARP getDiscordARP() {
		return discordRP;
	}
    
    public void switchToMojang() {
        try {
            altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to modank altservice");
        }
    }
    public void switchToTheAltening() {
        try {
            altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to altening altservice");
        }
    }

    public ArrayListManager getArraylistManager() {
        return arraylistManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public File getDir() {
        return dir;
    }
    
    public boolean setPassedLogin(boolean loginPassed) {
		return logPassed = loginPassed;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
    
    public static String getName() {
    	return name;
    }
    
    public static String getVersion() {
    	return version;
    }
    
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public void setAutoRelogNormal(boolean autoRelogNormal) {
        this.autoRelogNormal = autoRelogNormal;
    }


    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public AltService getAltService() {
        return altService;
    }

    public boolean isAutoRelogTheAltening() {
        return autoRelogTheAltening;
    }

    public void setAutoRelogTheAltening(boolean autoRelogTheAltening) {
        this.autoRelogTheAltening = autoRelogTheAltening;
    }

    public boolean isAutoRelogNormal() {
        return autoRelogNormal;
    }

}
