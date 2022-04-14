/*
 * Decompiled with CFR 0.152.
 */
package cafe.corrosion.config.dynamic;

import cafe.corrosion.util.player.PlayerUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.concurrent.atomic.AtomicReference;
import me.vaziak.cube.web.WebRequest;

public class DynamicConfigLoader {
    private static final String CONFIG_URL = "https://corrosion.lol/configs/%s";
    private static final Gson GSON = new Gson();

    public static JsonArray loadToMemory(String name) {
        WebRequest webRequest = new WebRequest(String.format(CONFIG_URL, name)).setMethod("GET");
        AtomicReference responseObject = new AtomicReference();
        webRequest.invoke(response -> {
            int responseCode = response.getResponseCode();
            byte[] data = response.getResponseData();
            if (responseCode != 200) {
                PlayerUtil.sendMessage("An error occurred while attempting to load " + name + " dynamically!");
                return;
            }
            responseObject.set(GSON.fromJson(new String(data), JsonArray.class));
            PlayerUtil.sendMessage("Successfully loaded " + name + "! Type -config web (name) again to load the config.");
        });
        return (JsonArray)responseObject.get();
    }
}

