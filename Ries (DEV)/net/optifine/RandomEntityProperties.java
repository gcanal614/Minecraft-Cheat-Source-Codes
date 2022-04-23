/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityRule;
import net.optifine.config.ConnectedParser;

public class RandomEntityProperties {
    public final String name;
    public final String basePath;
    public ResourceLocation[] resourceLocations = null;
    public RandomEntityRule[] rules = null;

    public RandomEntityProperties(String path, ResourceLocation[] variants) {
        ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
        this.name = connectedparser.parseName(path);
        this.basePath = connectedparser.parseBasePath(path);
        this.resourceLocations = variants;
    }

    public RandomEntityProperties(Properties props, String path, ResourceLocation baseResLoc) {
        ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
        this.name = connectedparser.parseName(path);
        this.basePath = connectedparser.parseBasePath(path);
        this.rules = this.parseRules(props, path, baseResLoc, connectedparser);
    }

    public ResourceLocation getTextureLocation(ResourceLocation loc, IRandomEntity randomEntity) {
        if (this.rules != null) {
            for (RandomEntityRule randomentityrule : this.rules) {
                if (!randomentityrule.matches(randomEntity)) continue;
                return randomentityrule.getTextureLocation(loc, randomEntity.getId());
            }
        }
        if (this.resourceLocations != null) {
            int j = randomEntity.getId();
            int k = j % this.resourceLocations.length;
            return this.resourceLocations[k];
        }
        return loc;
    }

    private RandomEntityRule[] parseRules(Properties props, String pathProps, ResourceLocation baseResLoc, ConnectedParser cp) {
        ArrayList<RandomEntityRule> list = new ArrayList<RandomEntityRule>();
        int i = props.size();
        for (int j = 0; j < i; ++j) {
            RandomEntityRule randomentityrule;
            int k = j + 1;
            String s = props.getProperty("textures." + k);
            if (s == null) {
                s = props.getProperty("skins." + k);
            }
            if (s == null || !(randomentityrule = new RandomEntityRule(props, pathProps, baseResLoc, k, s, cp)).isValid(pathProps)) continue;
            list.add(randomentityrule);
        }
        return list.toArray(new RandomEntityRule[0]);
    }

    public boolean isValid(String path) {
        if (this.resourceLocations == null && this.rules == null) {
            Config.warn("No skins specified: " + path);
            return false;
        }
        if (this.rules != null) {
            for (RandomEntityRule randomentityrule : this.rules) {
                if (randomentityrule.isValid(path)) continue;
                return false;
            }
        }
        if (this.resourceLocations != null) {
            for (ResourceLocation resourcelocation : this.resourceLocations) {
                if (Config.hasResource(resourcelocation)) continue;
                Config.warn("Texture not found: " + resourcelocation.getResourcePath());
                return false;
            }
        }
        return true;
    }

    public boolean isDefault() {
        return this.rules == null && this.resourceLocations == null;
    }
}

