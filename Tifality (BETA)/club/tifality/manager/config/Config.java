/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.config;

import club.tifality.Tifality;
import club.tifality.manager.config.ConfigManager;
import club.tifality.manager.config.Serializable;
import club.tifality.module.Module;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;

public final class Config
implements Serializable {
    private final String name;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.CONFIGS_DIR, name + ".tifality");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();
        for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
            modulesObject.add(module.getLabel(), module.save());
        }
        jsonObject.add("Modules", modulesObject);
        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");
            for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                if (!modulesObject.has(module.getLabel())) continue;
                module.load(modulesObject.getAsJsonObject(module.getLabel()));
            }
        }
    }
}

