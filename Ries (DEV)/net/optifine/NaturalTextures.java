/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalProperties;
import net.optifine.util.TextureUtils;

public class NaturalTextures {
    private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

    public static void update() {
        propertiesByIndex = new NaturalProperties[0];
        if (Config.isNaturalTextures()) {
            String s = "optifine/natural.properties";
            try {
                ResourceLocation resourcelocation = new ResourceLocation(s);
                if (!Config.hasResource(resourcelocation)) {
                    Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
                    return;
                }
                boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
                InputStream inputstream = Config.getResourceStream(resourcelocation);
                ArrayList<NaturalProperties> arraylist = new ArrayList<NaturalProperties>(256);
                String s1 = Config.readInputStream(inputstream);
                inputstream.close();
                String[] astring = Config.tokenize(s1, "\n\r");
                if (flag) {
                    Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
                    Config.dbg("Natural Textures: Valid only for textures from default resource pack");
                } else {
                    Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
                }
                TextureMap texturemap = TextureUtils.getTextureMapBlocks();
                for (String value : astring) {
                    String s2 = value.trim();
                    if (s2.startsWith("#")) continue;
                    String[] astring1 = Config.tokenize(s2, "=");
                    if (astring1.length != 2) {
                        Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
                        continue;
                    }
                    String s3 = astring1[0].trim();
                    String s4 = astring1[1].trim();
                    TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);
                    if (textureatlassprite == null) {
                        Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
                        continue;
                    }
                    int j = textureatlassprite.getIndexInMap();
                    if (j < 0) {
                        Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
                        continue;
                    }
                    if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png"))) {
                        return;
                    }
                    NaturalProperties naturalproperties = new NaturalProperties(s4);
                    if (!naturalproperties.isValid()) continue;
                    while (arraylist.size() <= j) {
                        arraylist.add(null);
                    }
                    arraylist.set(j, naturalproperties);
                    Config.dbg("NaturalTextures: " + s3 + " = " + s4);
                }
                propertiesByIndex = arraylist.toArray(new NaturalProperties[0]);
            }
            catch (FileNotFoundException var17) {
                Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static BakedQuad getNaturalTexture(BlockPos blockPosIn, BakedQuad quad) {
        TextureAtlasSprite textureatlassprite = quad.getSprite();
        if (textureatlassprite == null) {
            return quad;
        }
        NaturalProperties naturalproperties = NaturalTextures.getNaturalProperties(textureatlassprite);
        if (naturalproperties == null) {
            return quad;
        }
        int i = ConnectedTextures.getSide(quad.getFace());
        int j = Config.getRandom(blockPosIn, i);
        int k = 0;
        boolean flag = false;
        if (naturalproperties.rotation > 1) {
            k = j & 3;
        }
        if (naturalproperties.rotation == 2) {
            k = k / 2 * 2;
        }
        if (naturalproperties.flip) {
            flag = (j & 4) != 0;
        }
        return naturalproperties.getQuad(quad, k, flag);
    }

    public static NaturalProperties getNaturalProperties(TextureAtlasSprite icon) {
        if (icon == null) {
            return null;
        }
        int i = icon.getIndexInMap();
        if (i >= 0 && i < propertiesByIndex.length) {
            return propertiesByIndex[i];
        }
        return null;
    }
}

