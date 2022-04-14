package optifine;

import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.*;
import javax.imageio.*;
import java.io.*;
import com.google.gson.*;

public class PlayerConfigurationParser
{
    public static final String CONFIG_ITEMS = "items";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_ACTIVE = "active";
    private String player;
    
    public PlayerConfigurationParser(final String player) {
        this.player = null;
        this.player = player;
    }
    
    public PlayerConfiguration parsePlayerConfiguration(final JsonElement je) {
        if (je == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        final JsonObject jo = (JsonObject)je;
        final PlayerConfiguration pc = new PlayerConfiguration();
        final JsonArray items = (JsonArray)jo.get("items");
        if (items != null) {
            for (int i = 0; i < items.size(); ++i) {
                final JsonObject item = (JsonObject)items.get(i);
                final boolean active = Json.getBoolean(item, "active", true);
                if (active) {
                    final String type = Json.getString(item, "type");
                    if (type == null) {
                        Config.warn("Item type is null, player: " + this.player);
                    }
                    else {
                        String modelPath = Json.getString(item, "model");
                        if (modelPath == null) {
                            modelPath = "items/" + type + "/model.cfg";
                        }
                        final PlayerItemModel model = this.downloadModel(modelPath);
                        if (model != null) {
                            if (!model.isUsePlayerTexture()) {
                                String texturePath = Json.getString(item, "texture");
                                if (texturePath == null) {
                                    texturePath = "items/" + type + "/users/" + this.player + ".png";
                                }
                                final BufferedImage image = this.downloadTextureImage(texturePath);
                                if (image == null) {
                                    continue;
                                }
                                model.setTextureImage(image);
                                final ResourceLocation loc = new ResourceLocation("optifine.net", texturePath);
                                model.setTextureLocation(loc);
                            }
                            pc.addPlayerItemModel(model);
                        }
                    }
                }
            }
        }
        return pc;
    }
    
    private BufferedImage downloadTextureImage(final String texturePath) {
        final String textureUrl = "http://s.optifine.net/" + texturePath;
        try {
            final byte[] e = HttpPipeline.get(textureUrl, Minecraft.getMinecraft().getProxy());
            final BufferedImage image = ImageIO.read(new ByteArrayInputStream(e));
            return image;
        }
        catch (IOException var5) {
            Config.warn("Error loading item texture " + texturePath + ": " + var5.getClass().getName() + ": " + var5.getMessage());
            return null;
        }
    }
    
    private PlayerItemModel downloadModel(final String modelPath) {
        final String modelUrl = "http://s.optifine.net/" + modelPath;
        try {
            final byte[] e = HttpPipeline.get(modelUrl, Minecraft.getMinecraft().getProxy());
            final String jsonStr = new String(e, "ASCII");
            final JsonParser jp = new JsonParser();
            final JsonObject jo = (JsonObject)jp.parse(jsonStr);
            final PlayerItemParser pip = new PlayerItemParser();
            final PlayerItemModel pim = PlayerItemParser.parseItemModel(jo);
            return pim;
        }
        catch (Exception var9) {
            Config.warn("Error loading item model " + modelPath + ": " + var9.getClass().getName() + ": " + var9.getMessage());
            return null;
        }
    }
}
