package stellar.skid;

import stellar.skid.commands.NovoCommandHandler;
import stellar.skid.gui.screen.click.DiscordGUI;
import stellar.skid.gui.screen.dropdown.DropdownGUI;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.PlayerManager;
import stellar.skid.utils.fonts.api.FontManager;
import stellar.skid.utils.fonts.impl.SimpleFontManager;
import stellar.skid.utils.notifications.NotificationManager;
import stellar.skid.utils.tasks.TaskManager;
import com.thealtening.api.response.Account;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
//import net.skidunion.irc.IRCClient;
//import net.skidunion.irc.entities.UserEntity;
//import net.skidunion.security.Protection;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public final class StellarWare {

    /* fields */
    private final boolean BETA = false;
    private final boolean HOTFIX = false;

    private static StellarWare INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger();

    private final Minecraft mc = Minecraft.getInstance();
    private final Path dataFolder;

    public String version = "@VERSION@";

    /* managers */ // todo public fields
    private String token = null;
    private final NovoCommandHandler novoCommandHandler = new NovoCommandHandler(this);
    private ModuleManager moduleManager;
    public TaskManager taskManager;
    public PlayerManager playerManager;
    private final AsynchronousDataRetriever dataRetriever = new AsynchronousDataRetriever(null);
    public NotificationManager notificationManager;
    public FontManager fontManager = SimpleFontManager.create();

    /* gui */
    public DiscordGUI discordGUI;
    public DropdownGUI dropDownGUI;

//    public IRCClient irc;
//    private Protection protection;

    /* misc */
    private ServerData lastConnectedServer;

    /* constructors */
    public StellarWare() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Cannot instantiate " + getClass().getCanonicalName() + " twice");
        }
    }

    /* methods */
    public void onStart() {
    	Initializer.getInstance().onProtection(token);
    }

    public void onLoaded() {
    }

    public void onDisable() {
        try {
            getModuleManager().getConfigManager().save("default");
        } catch (IOException e) {
            getLogger().warn("An I/O error occurred while " + e.getMessage() + "!", e);
        } catch (ObjectMappingException e) {
            getLogger().warn("An I/O error occurred while serializing config!", e);
        }
    }

    {
        this.dataFolder = Paths.get(mc.mcDataDir.getAbsolutePath()).resolve("StellarWare");
    }

//    public IRCClient getIRC() {
//        return irc;
//    }

    public NovoCommandHandler getNovoCommandHandler() {
        return novoCommandHandler;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public AsynchronousDataRetriever getDataRetriever() {
        return dataRetriever;
    }

    public DiscordGUI getDiscordGUI() {
        return discordGUI;
    }

    public DropdownGUI getDropDownGUI() {
        return dropDownGUI;
    }
    public String getVersion() {
        return "1.0.0";
    }

    public Path getDataFolder() {
        return dataFolder/*Paths.get(this.mc.mcDataDir.getAbsolutePath() + "\\StellarWare")*/;
    }

    public String getPathString() {
        return mc.mcDataDir.getAbsolutePath() + "\\StellarWare\\";
    }

    public CompletableFuture<Account> generateAlteningAlt() {
        return dataRetriever.getAccountDataAsync();
    }

    public ServerData getLastConnectedServer() {
        return lastConnectedServer;
    }

    public void setLastConnectedServer(@NotNull ServerData lastConnectedServer) {
        this.lastConnectedServer = lastConnectedServer;
    }

    public void init() {

    }

    public String Xor(Object obj, String key) {
        StringBuilder sb = new StringBuilder();
        char[] keyChars = key.toCharArray();

        int i = 0;

        for (char c : obj.toString().toCharArray()) {
            sb.append((char) (c ^ keyChars[i % keyChars.length]));
            i++;
        }

        return sb.toString();
    }

    public boolean isAnythingNull() {
        return moduleManager == null || playerManager == null || notificationManager == null;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public Minecraft getMinecraft() {
        return mc;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static StellarWare getInstance() {
        try {
            if (INSTANCE == null) INSTANCE = new StellarWare();
            return INSTANCE;
        } catch (Throwable t) {
            LOGGER.warn(t);
            throw t;
        }
    }

    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

//    public Protection getProtection() {
//        return protection;
//    }

//    public void setProtection(Protection protection) {
//        this.protection = protection;
//    }
    //endregion

//    public UserEntity getIRCUser(String name) {
//        return irc.getUserManager().findByNickname(name);
//    }

    public int viaVersion() {
        return 404;
    }
}
