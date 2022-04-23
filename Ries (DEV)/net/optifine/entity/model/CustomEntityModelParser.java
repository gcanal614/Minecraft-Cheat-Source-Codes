/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 */
package net.optifine.entity.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.ConnectedParser;
import net.optifine.entity.model.CustomEntityModel;
import net.optifine.entity.model.CustomEntityRenderer;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.player.PlayerItemParser;
import net.optifine.util.Json;

public class CustomEntityModelParser {
    public static CustomEntityRenderer parseEntityRender(JsonObject obj, String path) {
        ConnectedParser connectedparser = new ConnectedParser("CustomEntityModels");
        String s = connectedparser.parseName(path);
        String s1 = connectedparser.parseBasePath(path);
        String s2 = Json.getString(obj, "texture");
        int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
        float f = Json.getFloat(obj, "shadowSize", -1.0f);
        JsonArray jsonarray = (JsonArray)obj.get("models");
        CustomEntityModelParser.checkNull(jsonarray, "Missing models");
        HashMap map = new HashMap();
        ArrayList<CustomModelRenderer> list = new ArrayList<CustomModelRenderer>();
        for (int i = 0; i < jsonarray.size(); ++i) {
            JsonObject jsonobject = (JsonObject)jsonarray.get(i);
            CustomEntityModelParser.processBaseId(jsonobject, map);
            CustomEntityModelParser.processExternalModel(jsonobject, map, s1);
            CustomEntityModelParser.processId(jsonobject, map);
            CustomModelRenderer custommodelrenderer = CustomEntityModelParser.parseCustomModelRenderer(jsonobject, aint, s1);
            list.add(custommodelrenderer);
        }
        CustomModelRenderer[] acustommodelrenderer = list.toArray(new CustomModelRenderer[0]);
        ResourceLocation resourcelocation = null;
        if (s2 != null) {
            resourcelocation = CustomEntityModelParser.getResourceLocation(s1, s2, ".png");
        }
        return new CustomEntityRenderer(s, s1, resourcelocation, acustommodelrenderer, f);
    }

    private static void processBaseId(JsonObject elem, Map mapModelJsons) {
        String s = Json.getString(elem, "baseId");
        if (s != null) {
            JsonObject jsonobject = (JsonObject)mapModelJsons.get(s);
            if (jsonobject == null) {
                Config.warn("BaseID not found: " + s);
            } else {
                CustomEntityModelParser.copyJsonElements(jsonobject, elem);
            }
        }
    }

    private static void processExternalModel(JsonObject elem, Map mapModelJsons, String basePath) {
        String s = Json.getString(elem, "model");
        if (s != null) {
            ResourceLocation resourcelocation = CustomEntityModelParser.getResourceLocation(basePath, s, ".jpm");
            try {
                JsonObject jsonobject = CustomEntityModelParser.loadJson(resourcelocation);
                if (jsonobject == null) {
                    Config.warn("Model not found: " + resourcelocation);
                    return;
                }
                CustomEntityModelParser.copyJsonElements(jsonobject, elem);
            }
            catch (JsonParseException | IOException ioexception) {
                Config.error("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void copyJsonElements(JsonObject objFrom, JsonObject objTo) {
        for (Map.Entry entry : objFrom.entrySet()) {
            if (((String)entry.getKey()).equals("id") || objTo.has((String)entry.getKey())) continue;
            objTo.add((String)entry.getKey(), (JsonElement)entry.getValue());
        }
    }

    public static ResourceLocation getResourceLocation(String basePath, String path, String extension) {
        if (!path.endsWith(extension)) {
            path = path + extension;
        }
        if (!path.contains("/")) {
            path = basePath + "/" + path;
        } else if (path.startsWith("./")) {
            path = basePath + "/" + path.substring(2);
        } else if (path.startsWith("~/")) {
            path = "optifine/" + path.substring(2);
        }
        return new ResourceLocation(path);
    }

    private static void processId(JsonObject elem, Map mapModelJsons) {
        String s = Json.getString(elem, "id");
        if (s != null) {
            if (s.length() < 1) {
                Config.warn("Empty model ID: " + s);
            } else if (mapModelJsons.containsKey(s)) {
                Config.warn("Duplicate model ID: " + s);
            } else {
                mapModelJsons.put(s, elem);
            }
        }
    }

    public static CustomModelRenderer parseCustomModelRenderer(JsonObject elem, int[] textureSize, String basePath) {
        String s = Json.getString(elem, "part");
        CustomEntityModelParser.checkNull(s, "Model part not specified, missing \"replace\" or \"attachTo\".");
        boolean flag = Json.getBoolean(elem, "attach", false);
        CustomEntityModel modelbase = new CustomEntityModel();
        if (textureSize != null) {
            modelbase.textureWidth = textureSize[0];
            modelbase.textureHeight = textureSize[1];
        }
        ModelUpdater modelupdater = null;
        JsonArray jsonarray = (JsonArray)elem.get("animations");
        if (jsonarray != null) {
            ArrayList<ModelVariableUpdater> list = new ArrayList<ModelVariableUpdater>();
            for (int i = 0; i < jsonarray.size(); ++i) {
                JsonObject jsonobject = (JsonObject)jsonarray.get(i);
                for (Map.Entry entry : jsonobject.entrySet()) {
                    String s1 = (String)entry.getKey();
                    String s2 = ((JsonElement)entry.getValue()).getAsString();
                    ModelVariableUpdater modelvariableupdater = new ModelVariableUpdater(s1, s2);
                    list.add(modelvariableupdater);
                }
            }
            if (list.size() > 0) {
                ModelVariableUpdater[] amodelvariableupdater = list.toArray(new ModelVariableUpdater[0]);
                modelupdater = new ModelUpdater(amodelvariableupdater);
            }
        }
        ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, textureSize, basePath);
        return new CustomModelRenderer(s, flag, modelrenderer, modelupdater);
    }

    private static void checkNull(Object obj, String msg) {
        if (obj == null) {
            throw new JsonParseException(msg);
        }
    }

    public static JsonObject loadJson(ResourceLocation location) throws IOException, JsonParseException {
        InputStream inputstream = Config.getResourceStream(location);
        if (inputstream == null) {
            return null;
        }
        String s = Config.readInputStream(inputstream, "ASCII");
        inputstream.close();
        JsonParser jsonparser = new JsonParser();
        return (JsonObject)jsonparser.parse(s);
    }
}

