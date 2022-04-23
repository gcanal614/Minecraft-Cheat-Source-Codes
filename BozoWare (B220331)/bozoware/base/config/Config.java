// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.config;

import bozoware.base.module.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import bozoware.base.BozoWare;
import java.io.File;

public class Config
{
    private final File file;
    
    public Config(final String configName) {
        this.file = new File(BozoWare.getInstance().getConfigManager().getConfigDirectory() + "/" + configName + ".bozo");
        if (!this.file.exists()) {
            try {
                if (this.file.createNewFile()) {
                    System.out.println("Config Created!");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    public JsonObject saveJson() {
        final JsonObject object = new JsonObject();
        final JsonObject modules = new JsonObject();
        BozoWare.getInstance().getModuleManager().getModules().forEach(module -> modules.add(module.getModuleName(), (JsonElement)module.saveJson()));
        object.add("modules", (JsonElement)modules);
        return object;
    }
    
    public void loadJson(final JsonObject jsonObject) {
        if (jsonObject.has("modules")) {
            final JsonObject modulesJson = jsonObject.getAsJsonObject("modules");
            final JsonObject jsonObject2;
            BozoWare.getInstance().getModuleManager().getModules().forEach(module -> {
                if (jsonObject2.has(module.getModuleName())) {
                    module.loadJson(jsonObject2.getAsJsonObject(module.getModuleName()));
                }
            });
        }
    }
}
