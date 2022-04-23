/*
 * Decompiled with CFR 0.152.
 */
package club.tifality;

import club.tifality.discord.RichPresence;
import club.tifality.gui.console.SourceConsoleGUI;
import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.font.TrueTypeFontRenderer;
import club.tifality.gui.notification.NotificationManager;
import club.tifality.gui.screen.SplashProgress;
import club.tifality.gui.strawberry.ClickGui;
import club.tifality.manager.api.bus.Bus;
import club.tifality.manager.api.bus.BusImpl;
import club.tifality.manager.command.CommandManager;
import club.tifality.manager.config.ConfigManager;
import club.tifality.manager.event.Event;
import club.tifality.manager.friend.FriendManager;
import club.tifality.manager.homoBus.bus.impl.EventBus;
import club.tifality.manager.keybind.BindSystem;
import club.tifality.module.ModuleManager;
import club.tifality.utils.Wrapper;
import org.lwjgl.opengl.Display;

public final class Tifality {
    public static final Tifality INSTANCE = new Tifality();
    private Bus<Event> eventBuz;
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private NotificationManager notificationManager;
    private static String API;
    private CommandManager commandManager;
    private FriendManager friendManager;
    public static final String NAME = "Tifality";
    public static final String VERSION = "Beta Version";
    public static int startTime;
    private static SourceConsoleGUI sourceConsoleGUI;
    public static TrueTypeFontRenderer tahoma;

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public CommandManager getCommandHandler() {
        return this.commandManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public void onStarting() {
        Display.setTitle("Launching, Please wait...");
    }

    public void onStarted() {
        Display.setTitle("Minecraft 1.8.9");
    }

    public void onPostInit() {
        this.onStarting();
        SplashProgress.setProgress(1);
        try {
            RichPresence clientRichPresence = new RichPresence();
            clientRichPresence.setup();
        }
        catch (Throwable clientRichPresence) {
            // empty catch block
        }
        this.configManager = new ConfigManager();
        this.friendManager = new FriendManager();
        EventBus eventBus = new EventBus();
        this.eventBuz = new BusImpl<Event>();
        this.moduleManager = new ModuleManager();
        SkeetUI.init();
        sourceConsoleGUI = new SourceConsoleGUI();
        this.notificationManager = new NotificationManager();
        this.commandManager = new CommandManager();
        this.eventBuz.subscribe(new club.tifality.gui.click.ClickGui());
        this.eventBuz.subscribe(new ClickGui());
        eventBus.subscribe(new BindSystem(this.moduleManager.getModules()));
        this.eventBuz.subscribe(new BindSystem(this.moduleManager.getModules()));
        this.moduleManager.postInit();
        this.getConfigManager().loadConfig("Value");
        this.configManager.loadFiles();
        Wrapper.getFontRenderer().generateTextures();
        Wrapper.getNameTagFontRenderer().generateTextures();
        Wrapper.getCSGOFontRenderer().generateTextures();
        Wrapper.getTestFont().generateTextures();
        Wrapper.getVerdana10().generateTextures();
        Wrapper.getVerdana16().generateTextures();
        Wrapper.getTitleFont().generateTextures();
        Wrapper.getInfoFont().generateTextures();
        Wrapper.getBigFontRenderer().generateTextures();
        Wrapper.getEspFontRenderer().generateTextures();
        Wrapper.getEspBiggerFontRenderer().generateTextures();
        Wrapper.getTestFont1().generateTextures();
        this.onStarted();
    }

    public static SourceConsoleGUI getSourceConsoleGUI() {
        return sourceConsoleGUI;
    }

    public void onShutDown() {
        this.getConfigManager().saveConfig("Value");
        this.configManager.saveFiles();
    }

    public Bus<Event> getEventBus() {
        return this.eventBuz;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public static Tifality getInstance() {
        return INSTANCE;
    }

    public static String getAPI() {
        return API;
    }

    public static void setAPI(String API) {
    }

    static {
        startTime = (int)System.currentTimeMillis();
    }
}

