/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.config;

import club.tifality.manager.config.Alts;
import club.tifality.manager.config.Config;
import club.tifality.utils.handler.Manager;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.io.FilenameUtils;

public final class ConfigManager
extends Manager<Config> {
    public static final File CONFIGS_DIR = new File("LitelyWare", "config");
    public static final String EXTENTION = ".tifality";
    public static ArrayList Files = new ArrayList();

    public ConfigManager() {
        super(ConfigManager.loadConfigs());
        CONFIGS_DIR.mkdirs();
        Files.add(new Alts("alts", false, true));
    }

    public static ArrayList<Config> loadConfigs() {
        ArrayList<Config> loadedConfigs = new ArrayList<Config>();
        File[] files = CONFIGS_DIR.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!FilenameUtils.getExtension(file.getName()).equals("tifality")) continue;
                loadedConfigs.add(new Config(FilenameUtils.removeExtension(file.getName())));
            }
        }
        return loadedConfigs;
    }

    public boolean loadConfig(String configName) {
        if (configName == null) {
            return false;
        }
        Config config = this.findConfig(configName);
        if (config == null) {
            return false;
        }
        try {
            FileReader reader = new FileReader(config.getFile());
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject)parser.parse(reader);
            config.load(object);
            return true;
        }
        catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean saveConfig(String configName) {
        if (configName == null) {
            return false;
        }
        Config config = this.findConfig(configName);
        if (config == null) {
            Config newConfig = config = new Config(configName);
            this.getElements().add(newConfig);
        }
        String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson(config.save());
        config.save();
        try {
            FileWriter writer = new FileWriter(config.getFile());
            writer.write(contentPrettyPrint);
            writer.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public Config findConfig(String configName) {
        if (configName == null) {
            return null;
        }
        for (Config config : this.getElements()) {
            if (!config.getName().equalsIgnoreCase(configName)) continue;
            return config;
        }
        if (new File(CONFIGS_DIR, configName + EXTENTION).exists()) {
            return new Config(configName);
        }
        return null;
    }

    public boolean deleteConfig(String configName) {
        if (configName == null) {
            return false;
        }
        Config config = this.findConfig(configName);
        if (config != null) {
            File f = config.getFile();
            this.getElements().remove(config);
            return f.exists() && f.delete();
        }
        return false;
    }

    public void loadFiles() {
        for (CustomFile f : Files) {
            try {
                if (!f.loadOnStart()) continue;
                f.loadFile();
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    public void saveFiles() {
        for (CustomFile f : Files) {
            try {
                f.saveFile();
                System.out.println("SaveFiles");
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    public CustomFile getFile(Class clazz) {
        CustomFile file;
        Iterator var2 = Files.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while ((file = (CustomFile)var2.next()).getClass() != clazz);
        return file;
    }

    public static abstract class CustomFile {
        private final File file;
        private final String name;
        private boolean load;

        public CustomFile(String name, boolean Module2, boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            this.file = new File("LitelyWare", name + ".txt");
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                }
                catch (Exception var5) {
                    var5.printStackTrace();
                }
            }
        }

        public final File getFile() {
            return this.file;
        }

        private boolean loadOnStart() {
            return this.load;
        }

        public final String getName() {
            return this.name;
        }

        public abstract void loadFile() throws IOException;

        public abstract void saveFile() throws IOException;
    }
}

