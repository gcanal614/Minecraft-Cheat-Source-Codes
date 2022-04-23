/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  store.intent.intentguard.annotation.Exclude
 *  store.intent.intentguard.annotation.Strategy
 */
package me.uncodable.srt.impl.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.FileUtils;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

@Exclude(value={Strategy.NAME_REMAPPING, Strategy.STRING_ENCRYPTION, Strategy.FLOW_OBFUSCATION, Strategy.NUMBER_OBFUSCATION, Strategy.REFERENCE_OBFUSCATION, Strategy.PARAMETER_OBFUSCATION})
public class ConfigUtils {
    public static boolean loadConfiguration(String inConfig, boolean loadBinds) {
        Set entrySet;
        FileReader reader;
        File config = new File(FileUtils.SRT_CONFIG_DIR, inConfig.concat(".json"));
        JsonParser parser = new JsonParser();
        try {
            reader = new FileReader(config);
        }
        catch (FileNotFoundException e) {
            Ries.INSTANCE.msg(String.format("An I/O error occurred while attempting to load the selected configuration. Failed to locate the configuration file \"%s.\"", config.getName()));
            e.printStackTrace();
            return false;
        }
        JsonElement element = parser.parse((Reader)reader);
        try {
            entrySet = element.getAsJsonObject().entrySet();
        }
        catch (IllegalStateException e) {
            Ries.INSTANCE.msg(String.format("An I/O error occurred while attempting to load the selected configuration. The \"%s\" configuration file appears to be malformed.", config.getName()));
            e.printStackTrace();
            return false;
        }
        for (Map.Entry entry : entrySet) {
            for (Module module : Ries.INSTANCE.getModuleManager().getModules()) {
                if (!module.getInfo().internalName().equalsIgnoreCase((String)entry.getKey())) continue;
                JsonObject jsonModule = ((JsonElement)entry.getValue()).getAsJsonObject();
                if (jsonModule.get("toggled").getAsBoolean() != module.isEnabled()) {
                    module.toggle();
                }
                if (loadBinds) {
                    module.setPrimaryKey(jsonModule.get("primary_bind").getAsInt());
                }
                if (!jsonModule.has("settings")) continue;
                JsonObject jsonSetting = jsonModule.getAsJsonObject("settings");
                block11: for (Setting setting : Ries.INSTANCE.getSettingManager().getAllSettings(module)) {
                    String lowercase = setting.getInternalName().toLowerCase();
                    if (!jsonSetting.has(lowercase)) continue;
                    switch (setting.getSettingType()) {
                        case CHECKBOX: {
                            setting.setTicked(jsonSetting.get(lowercase).getAsBoolean());
                            continue block11;
                        }
                        case SLIDER: {
                            setting.setCurrentValue(jsonSetting.get(lowercase).getAsNumber().doubleValue());
                            continue block11;
                        }
                        case COMBO_BOX: {
                            setting.setCurrentCombo(jsonSetting.get(lowercase).getAsString());
                            continue block11;
                        }
                    }
                    Ries.INSTANCE.msg(String.format("Unknown setting type parsed: %s", setting.getSettingType().name()));
                }
            }
        }
        Ries.INSTANCE.msg(String.format("Loaded configuration file \"%s.\"", config.getName()));
        return true;
    }

    public static boolean saveConfiguration(String outConfig) {
        PrintWriter writer;
        File config = new File(FileUtils.SRT_CONFIG_DIR, outConfig.concat(".json"));
        JsonObject jsonModules = new JsonObject();
        try {
            writer = new PrintWriter(config);
        }
        catch (FileNotFoundException e) {
            Ries.INSTANCE.msg("An I/O error occurred while attempting to save the current configuration.");
            e.printStackTrace();
            return false;
        }
        for (Module module : Ries.INSTANCE.getModuleManager().getModules()) {
            ArrayList<Setting> settings = Ries.INSTANCE.getSettingManager().getAllSettings(module);
            JsonObject jsonModule = new JsonObject();
            jsonModule.addProperty("toggled", Boolean.valueOf(module.isEnabled()));
            jsonModule.addProperty("primary_bind", (Number)module.getPrimaryKey());
            if (!settings.isEmpty()) {
                JsonObject jsonSetting = new JsonObject();
                block8: for (Setting setting : settings) {
                    String lowercase = setting.getInternalName().toLowerCase();
                    switch (setting.getSettingType()) {
                        case CHECKBOX: {
                            jsonSetting.addProperty(lowercase, Boolean.valueOf(setting.isTicked()));
                            continue block8;
                        }
                        case SLIDER: {
                            jsonSetting.addProperty(lowercase, (Number)setting.getCurrentValue());
                            continue block8;
                        }
                        case COMBO_BOX: {
                            jsonSetting.addProperty(lowercase, setting.getCurrentCombo());
                            continue block8;
                        }
                    }
                    Ries.INSTANCE.msg(String.format("Unknown setting type parsed: %s", setting.getSettingType().name()));
                }
                jsonModule.add("settings", (JsonElement)jsonSetting);
            }
            jsonModules.add(module.getInfo().internalName().toLowerCase(), (JsonElement)jsonModule);
        }
        writer.println(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonModules));
        writer.close();
        Ries.INSTANCE.msg(String.format("Saved configuration file \"%s.\"", config.getName()));
        return true;
    }
}

