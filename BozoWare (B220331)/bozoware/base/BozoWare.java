// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;
import viamcp.ViaMCP;
import bozoware.visual.screens.dropdown.GuiDropDown;
import bozoware.visual.screens.ot.GuiOTUI;
import bozoware.visual.screens.alt.AltManager;
import bozoware.base.config.ConfigManager;
import bozoware.base.file.FileManager;
import bozoware.base.command.CommandManager;
import bozoware.base.property.PropertyManager;
import bozoware.visual.font.FontManager;
import bozoware.base.event.Event;
import bozoware.base.event.EventManager;
import bozoware.base.module.ModuleManager;
import bozoware.base.util.visual.SkiddedBlurUtil;

public class BozoWare
{
    public final String CLIENT_NAME = "BozoWare";
    public final String CLIENT_VERSION = "220331";
    public static String BozoUserName;
    private static final BozoWare INSTANCE;
    public static final SkiddedBlurUtil blurrer;
    private ModuleManager moduleManager;
    private EventManager<Event> eventManager;
    private FontManager fontManager;
    private PropertyManager propertyManager;
    private CommandManager commandManager;
    private FileManager fileManager;
    private ConfigManager configManager;
    private AltManager altManager;
    private final GuiOTUI guiOTUIScreen;
    public final Runnable onClientStart;
    public final Runnable onClientExit;
    
    public BozoWare() {
        this.guiOTUIScreen = new GuiOTUI();
        this.onClientStart = (() -> {
            System.out.println("Starting Client! Hello Bozo!");
            System.out.printf("%s Version %s%n", "BozoWare", "220331");
            this.eventManager = new EventManager<Event>();
            this.propertyManager = new PropertyManager();
            this.moduleManager = new ModuleManager();
            this.fontManager = new FontManager();
            this.commandManager = new CommandManager();
            this.fileManager = new FileManager();
            this.configManager = new ConfigManager();
            this.guiOTUIScreen.setupGui();
            (this.altManager = new AltManager()).onStart();
            GuiDropDown.onStartTask.run();
            try {
                ViaMCP.getInstance().start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return;
        });
        this.onClientExit = (() -> {
            System.out.println("Closing Client! GoodBye Bozo!");
            System.out.printf("%s Version %s%n", "BozoWare", "220331");
            this.altManager.onExit();
        });
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public EventManager<Event> getEventManager() {
        return this.eventManager;
    }
    
    public void chat(final String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§f[§c§4BozoWare§f]§r " + msg));
    }
    
    public FontManager getFontManager() {
        return this.fontManager;
    }
    
    public PropertyManager getPropertyManager() {
        return this.propertyManager;
    }
    
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public GuiOTUI getGuiOTUIScreen() {
        return this.guiOTUIScreen;
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    public FileManager getFileManager() {
        return this.fileManager;
    }
    
    public static BozoWare getInstance() {
        return BozoWare.INSTANCE;
    }
    
    public AltManager getAltManager() {
        return this.altManager;
    }
    
    static {
        INSTANCE = new BozoWare();
        blurrer = new SkiddedBlurUtil();
    }
}
