package optifine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;
import java.util.*;

public class PlayerConfigurations
{
    private static Map mapConfigurations;
    
    public static void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer player, final float scale, final float partialTicks) {
        final PlayerConfiguration cfg = getPlayerConfiguration(player);
        if (cfg != null) {
            cfg.renderPlayerItems(modelBiped, player, scale, partialTicks);
        }
    }
    
    public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer player) {
        final String name = player.getNameClear();
        if (name == null) {
            return null;
        }
        PlayerConfiguration pc = getMapConfigurations().get(name);
        if (pc == null) {
            pc = new PlayerConfiguration();
            getMapConfigurations().put(name, pc);
            final PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
            final String url = "http://s.optifine.net/users/" + name + ".cfg";
            final FileDownloadThread fdt = new FileDownloadThread(url, pcl);
            fdt.start();
        }
        return pc;
    }
    
    public static synchronized void setPlayerConfiguration(final String player, final PlayerConfiguration pc) {
        getMapConfigurations().put(player, pc);
    }
    
    private static Map getMapConfigurations() {
        if (PlayerConfigurations.mapConfigurations == null) {
            PlayerConfigurations.mapConfigurations = new HashMap();
        }
        return PlayerConfigurations.mapConfigurations;
    }
    
    static {
        PlayerConfigurations.mapConfigurations = null;
    }
}
