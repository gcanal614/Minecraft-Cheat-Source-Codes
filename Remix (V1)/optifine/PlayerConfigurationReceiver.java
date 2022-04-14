package optifine;

import com.google.gson.*;

public class PlayerConfigurationReceiver implements IFileDownloadListener
{
    private String player;
    
    public PlayerConfigurationReceiver(final String player) {
        this.player = null;
        this.player = player;
    }
    
    @Override
    public void fileDownloadFinished(final String url, final byte[] bytes, final Throwable exception) {
        if (bytes != null) {
            try {
                final String e = new String(bytes, "ASCII");
                final JsonParser jp = new JsonParser();
                final JsonElement je = jp.parse(e);
                final PlayerConfigurationParser pcp = new PlayerConfigurationParser(this.player);
                final PlayerConfiguration pc = pcp.parsePlayerConfiguration(je);
                if (pc != null) {
                    pc.setInitialized(true);
                    PlayerConfigurations.setPlayerConfiguration(this.player, pc);
                }
            }
            catch (Exception var9) {
                Config.dbg("Error parsing configuration: " + url + ", " + var9.getClass().getName() + ": " + var9.getMessage());
            }
        }
    }
}
