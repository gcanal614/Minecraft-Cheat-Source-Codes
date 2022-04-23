/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.player.ModelPlayerItem;
import net.optifine.player.PlayerItemModel;
import net.optifine.player.PlayerItemRenderer;
import net.optifine.util.Json;

public class PlayerItemParser {
    public static PlayerItemModel parseItemModel(JsonObject obj) {
        String s = Json.getString(obj, "type");
        if (!Config.equals(s, "PlayerItem")) {
            throw new JsonParseException("Unknown model type: " + s);
        }
        int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
        PlayerItemParser.checkNull(aint, "Missing texture size");
        Dimension dimension = new Dimension(aint[0], aint[1]);
        boolean flag = Json.getBoolean(obj, "usePlayerTexture", false);
        JsonArray jsonarray = (JsonArray)obj.get("models");
        PlayerItemParser.checkNull(jsonarray, "Missing elements");
        HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();
        ArrayList<PlayerItemRenderer> list = new ArrayList<PlayerItemRenderer>();
        for (int i = 0; i < jsonarray.size(); ++i) {
            PlayerItemRenderer playeritemrenderer;
            String s2;
            JsonObject jsonobject = (JsonObject)jsonarray.get(i);
            String s1 = Json.getString(jsonobject, "baseId");
            if (s1 != null) {
                JsonObject jsonobject1 = (JsonObject)map.get(s1);
                if (jsonobject1 == null) {
                    Config.warn("BaseID not found: " + s1);
                    continue;
                }
                for (Map.Entry entry : jsonobject1.entrySet()) {
                    if (jsonobject.has((String)entry.getKey())) continue;
                    jsonobject.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }
            if ((s2 = Json.getString(jsonobject, "id")) != null) {
                if (!map.containsKey(s2)) {
                    map.put(s2, jsonobject);
                } else {
                    Config.warn("Duplicate model ID: " + s2);
                }
            }
            if ((playeritemrenderer = PlayerItemParser.parseItemRenderer(jsonobject, dimension)) == null) continue;
            list.add(playeritemrenderer);
        }
        PlayerItemRenderer[] aplayeritemrenderer = list.toArray(new PlayerItemRenderer[0]);
        return new PlayerItemModel(flag, aplayeritemrenderer);
    }

    private static void checkNull(Object obj, String msg) {
        if (obj == null) {
            throw new JsonParseException(msg);
        }
    }

    private static int parseAttachModel(String attachModelStr) {
        if (attachModelStr == null) {
            return 0;
        }
        switch (attachModelStr) {
            case "body": {
                return 0;
            }
            case "head": {
                return 1;
            }
            case "leftArm": {
                return 2;
            }
            case "rightArm": {
                return 3;
            }
            case "leftLeg": {
                return 4;
            }
            case "rightLeg": {
                return 5;
            }
            case "cape": {
                return 6;
            }
        }
        Config.warn("Unknown attachModel: " + attachModelStr);
        return 0;
    }

    public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
        String s = Json.getString(elem, "type");
        if (!Config.equals(s, "ModelBox")) {
            Config.warn("Unknown model type: " + s);
            return null;
        }
        String s1 = Json.getString(elem, "attachTo");
        int i = PlayerItemParser.parseAttachModel(s1);
        ModelPlayerItem modelbase = new ModelPlayerItem();
        modelbase.textureWidth = textureDim.width;
        modelbase.textureHeight = textureDim.height;
        ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, null, null);
        return new PlayerItemRenderer(i, modelrenderer);
    }

    public static ModelRenderer parseModelRenderer(JsonObject elem, ModelBase modelBase, int[] parentTextureSize, String basePath) {
        JsonArray jsonarray2;
        JsonObject jsonobject1;
        JsonArray jsonarray1;
        JsonArray jsonarray;
        int[] aint;
        float f;
        ModelRenderer modelrenderer = new ModelRenderer(modelBase);
        String s = Json.getString(elem, "id");
        modelrenderer.setId(s);
        modelrenderer.scaleX = f = Json.getFloat(elem, "scale", 1.0f);
        modelrenderer.scaleY = f;
        modelrenderer.scaleZ = f;
        String s1 = Json.getString(elem, "texture");
        if (s1 != null) {
            modelrenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, s1, ".png"));
        }
        if ((aint = Json.parseIntArray(elem.get("textureSize"), 2)) == null) {
            aint = parentTextureSize;
        } else {
            modelrenderer.setTextureSize(aint[0], aint[1]);
        }
        String s2 = Json.getString(elem, "invertAxis", "").toLowerCase();
        boolean flag = s2.contains("x");
        boolean flag1 = s2.contains("y");
        boolean flag2 = s2.contains("z");
        float[] afloat = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
        if (flag) {
            afloat[0] = -afloat[0];
        }
        if (flag1) {
            afloat[1] = -afloat[1];
        }
        if (flag2) {
            afloat[2] = -afloat[2];
        }
        float[] afloat1 = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);
        for (int i = 0; i < afloat1.length; ++i) {
            afloat1[i] = afloat1[i] / 180.0f * MathHelper.PI;
        }
        if (flag) {
            afloat1[0] = -afloat1[0];
        }
        if (flag1) {
            afloat1[1] = -afloat1[1];
        }
        if (flag2) {
            afloat1[2] = -afloat1[2];
        }
        modelrenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
        modelrenderer.rotateAngleX = afloat1[0];
        modelrenderer.rotateAngleY = afloat1[1];
        modelrenderer.rotateAngleZ = afloat1[2];
        String s3 = Json.getString(elem, "mirrorTexture", "").toLowerCase();
        boolean flag3 = s3.contains("u");
        boolean flag4 = s3.contains("v");
        if (flag3) {
            modelrenderer.mirror = true;
        }
        if (flag4) {
            modelrenderer.mirrorV = true;
        }
        if ((jsonarray = elem.getAsJsonArray("boxes")) != null) {
            for (int j = 0; j < jsonarray.size(); ++j) {
                JsonObject jsonobject = jsonarray.get(j).getAsJsonObject();
                int[] aint1 = Json.parseIntArray(jsonobject.get("textureOffset"), 2);
                int[][] aint2 = PlayerItemParser.parseFaceUvs(jsonobject);
                if (aint1 == null && aint2 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                float[] afloat2 = Json.parseFloatArray(jsonobject.get("coordinates"), 6);
                if (afloat2 == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (flag) {
                    afloat2[0] = -afloat2[0] - afloat2[3];
                }
                if (flag1) {
                    afloat2[1] = -afloat2[1] - afloat2[4];
                }
                if (flag2) {
                    afloat2[2] = -afloat2[2] - afloat2[5];
                }
                float f1 = Json.getFloat(jsonobject, "sizeAdd", 0.0f);
                if (aint2 != null) {
                    modelrenderer.addBox(aint2, afloat2[0], afloat2[1], afloat2[2], afloat2[3], afloat2[4], afloat2[5], f1);
                    continue;
                }
                modelrenderer.setTextureOffset(aint1[0], aint1[1]);
                modelrenderer.addBox(afloat2[0], afloat2[1], afloat2[2], (int)afloat2[3], (int)afloat2[4], (int)afloat2[5], f1);
            }
        }
        if ((jsonarray1 = elem.getAsJsonArray("sprites")) != null) {
            for (int k = 0; k < jsonarray1.size(); ++k) {
                JsonObject jsonobject2 = jsonarray1.get(k).getAsJsonObject();
                int[] aint3 = Json.parseIntArray(jsonobject2.get("textureOffset"), 2);
                if (aint3 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                float[] afloat3 = Json.parseFloatArray(jsonobject2.get("coordinates"), 6);
                if (afloat3 == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (flag) {
                    afloat3[0] = -afloat3[0] - afloat3[3];
                }
                if (flag1) {
                    afloat3[1] = -afloat3[1] - afloat3[4];
                }
                if (flag2) {
                    afloat3[2] = -afloat3[2] - afloat3[5];
                }
                float f2 = Json.getFloat(jsonobject2, "sizeAdd", 0.0f);
                modelrenderer.setTextureOffset(aint3[0], aint3[1]);
                modelrenderer.addSprite(afloat3[0], afloat3[1], afloat3[2], (int)afloat3[3], (int)afloat3[4], (int)afloat3[5], f2);
            }
        }
        if ((jsonobject1 = (JsonObject)elem.get("submodel")) != null) {
            ModelRenderer modelrenderer2 = PlayerItemParser.parseModelRenderer(jsonobject1, modelBase, aint, basePath);
            modelrenderer.addChild(modelrenderer2);
        }
        if ((jsonarray2 = (JsonArray)elem.get("submodels")) != null) {
            for (int l = 0; l < jsonarray2.size(); ++l) {
                ModelRenderer modelrenderer1;
                JsonObject jsonobject3 = (JsonObject)jsonarray2.get(l);
                ModelRenderer modelrenderer3 = PlayerItemParser.parseModelRenderer(jsonobject3, modelBase, aint, basePath);
                if (modelrenderer3.getId() != null && (modelrenderer1 = modelrenderer.getChild(modelrenderer3.getId())) != null) {
                    Config.warn("Duplicate model ID: " + modelrenderer3.getId());
                }
                modelrenderer.addChild(modelrenderer3);
            }
        }
        return modelrenderer;
    }

    private static int[][] parseFaceUvs(JsonObject box) {
        int[][] aint = new int[][]{Json.parseIntArray(box.get("uvDown"), 4), Json.parseIntArray(box.get("uvUp"), 4), Json.parseIntArray(box.get("uvNorth"), 4), Json.parseIntArray(box.get("uvSouth"), 4), Json.parseIntArray(box.get("uvWest"), 4), Json.parseIntArray(box.get("uvEast"), 4)};
        if (aint[2] == null) {
            aint[2] = Json.parseIntArray(box.get("uvFront"), 4);
        }
        if (aint[3] == null) {
            aint[3] = Json.parseIntArray(box.get("uvBack"), 4);
        }
        if (aint[4] == null) {
            aint[4] = Json.parseIntArray(box.get("uvLeft"), 4);
        }
        if (aint[5] == null) {
            aint[5] = Json.parseIntArray(box.get("uvRight"), 4);
        }
        boolean flag = false;
        for (int[] ints : aint) {
            if (ints == null) continue;
            flag = true;
            break;
        }
        if (!flag) {
            return null;
        }
        return aint;
    }
}

