package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PlayerConfigurationParser {
   public static final String ITEM_ACTIVE = "active";
   public static final String ITEM_TYPE = "type";
   private String player = null;
   public static final String CONFIG_ITEMS = "items";

   public PlayerConfiguration parsePlayerConfiguration(JsonElement var1) {
      if (var1 == null) {
         throw new JsonParseException(String.valueOf((new StringBuilder("JSON object is null, player: ")).append(this.player)));
      } else {
         JsonObject var2 = (JsonObject)var1;
         PlayerConfiguration var3 = new PlayerConfiguration();
         JsonArray var4 = (JsonArray)var2.get("items");
         if (var4 != null) {
            for(int var5 = 0; var5 < var4.size(); ++var5) {
               JsonObject var6 = (JsonObject)var4.get(var5);
               boolean var7 = Json.getBoolean(var6, "active", true);
               if (var7) {
                  String var8 = Json.getString(var6, "type");
                  if (var8 == null) {
                     Config.warn(String.valueOf((new StringBuilder("Item type is null, player: ")).append(this.player)));
                  } else {
                     String var9 = Json.getString(var6, "model");
                     if (var9 == null) {
                        var9 = String.valueOf((new StringBuilder("items/")).append(var8).append("/model.cfg"));
                     }

                     PlayerItemModel var10 = this.downloadModel(var9);
                     if (var10 != null) {
                        if (!var10.isUsePlayerTexture()) {
                           String var11 = Json.getString(var6, "texture");
                           if (var11 == null) {
                              var11 = String.valueOf((new StringBuilder("items/")).append(var8).append("/users/").append(this.player).append(".png"));
                           }

                           BufferedImage var12 = this.downloadTextureImage(var11);
                           if (var12 == null) {
                              continue;
                           }

                           var10.setTextureImage(var12);
                           ResourceLocation var13 = new ResourceLocation("optifine.net", var11);
                           var10.setTextureLocation(var13);
                        }

                        var3.addPlayerItemModel(var10);
                     }
                  }
               }
            }
         }

         return var3;
      }
   }

   public PlayerConfigurationParser(String var1) {
      this.player = var1;
   }

   private PlayerItemModel downloadModel(String var1) {
      String var2 = String.valueOf((new StringBuilder("http://s.optifine.net/")).append(var1));

      try {
         byte[] var3 = HttpPipeline.get(var2, Minecraft.getMinecraft().getProxy());
         String var4 = new String(var3, "ASCII");
         JsonParser var5 = new JsonParser();
         JsonObject var6 = (JsonObject)var5.parse(var4);
         new PlayerItemParser();
         PlayerItemModel var8 = PlayerItemParser.parseItemModel(var6);
         return var8;
      } catch (Exception var9) {
         Config.warn(String.valueOf((new StringBuilder("Error loading item model ")).append(var1).append(": ").append(var9.getClass().getName()).append(": ").append(var9.getMessage())));
         return null;
      }
   }

   private BufferedImage downloadTextureImage(String var1) {
      String var2 = String.valueOf((new StringBuilder("http://s.optifine.net/")).append(var1));

      try {
         byte[] var3 = HttpPipeline.get(var2, Minecraft.getMinecraft().getProxy());
         BufferedImage var4 = ImageIO.read(new ByteArrayInputStream(var3));
         return var4;
      } catch (IOException var5) {
         Config.warn(String.valueOf((new StringBuilder("Error loading item texture ")).append(var1).append(": ").append(var5.getClass().getName()).append(": ").append(var5.getMessage())));
         return null;
      }
   }
}
