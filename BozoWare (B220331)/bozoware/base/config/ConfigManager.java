// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.config;

import java.io.FileNotFoundException;
import java.io.Reader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.util.Objects;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;
import bozoware.base.BozoWare;

public class ConfigManager
{
    public ConfigManager() {
        BozoWare.getInstance().getFileManager().addSubDirectory("configs");
    }
    
    public String getConfigDirectory() {
        return BozoWare.getInstance().getFileManager().getClientDirectory() + "/configs";
    }
    
    public boolean saveConfig(final Config config) {
        final String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)config.saveJson());
        try {
            final FileWriter fileWriter = new FileWriter(config.getFile());
            fileWriter.write(contentPrettyPrint);
            fileWriter.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean loadConfig(String configName) {
        configName += ".bozo";
        for (final File configFile : Objects.requireNonNull(new File(this.getConfigDirectory()).listFiles())) {
            if (configFile.getName().equalsIgnoreCase(configName)) {
                try {
                    final FileReader reader = new FileReader(configFile);
                    final JsonParser jsonParser = new JsonParser();
                    final JsonObject object = (JsonObject)jsonParser.parse((Reader)reader);
                    new Config(configFile.getName()).loadJson(object);
                    return true;
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
}
