/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterArmorStand;
import net.optifine.entity.model.ModelAdapterBanner;
import net.optifine.entity.model.ModelAdapterBat;
import net.optifine.entity.model.ModelAdapterBlaze;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterBook;
import net.optifine.entity.model.ModelAdapterCaveSpider;
import net.optifine.entity.model.ModelAdapterChest;
import net.optifine.entity.model.ModelAdapterChestLarge;
import net.optifine.entity.model.ModelAdapterChicken;
import net.optifine.entity.model.ModelAdapterCow;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.ModelAdapterDragon;
import net.optifine.entity.model.ModelAdapterEnderChest;
import net.optifine.entity.model.ModelAdapterEnderCrystal;
import net.optifine.entity.model.ModelAdapterEnderman;
import net.optifine.entity.model.ModelAdapterEndermite;
import net.optifine.entity.model.ModelAdapterGhast;
import net.optifine.entity.model.ModelAdapterGuardian;
import net.optifine.entity.model.ModelAdapterHeadHumanoid;
import net.optifine.entity.model.ModelAdapterHeadSkeleton;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.ModelAdapterIronGolem;
import net.optifine.entity.model.ModelAdapterLeadKnot;
import net.optifine.entity.model.ModelAdapterMagmaCube;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.ModelAdapterMinecartMobSpawner;
import net.optifine.entity.model.ModelAdapterMinecartTnt;
import net.optifine.entity.model.ModelAdapterMooshroom;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.ModelAdapterPig;
import net.optifine.entity.model.ModelAdapterPigZombie;
import net.optifine.entity.model.ModelAdapterRabbit;
import net.optifine.entity.model.ModelAdapterSheep;
import net.optifine.entity.model.ModelAdapterSheepWool;
import net.optifine.entity.model.ModelAdapterSign;
import net.optifine.entity.model.ModelAdapterSilverfish;
import net.optifine.entity.model.ModelAdapterSkeleton;
import net.optifine.entity.model.ModelAdapterSlime;
import net.optifine.entity.model.ModelAdapterSnowman;
import net.optifine.entity.model.ModelAdapterSpider;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.ModelAdapterWitch;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.ModelAdapterWitherSkull;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.ModelAdapterZombie;

public class CustomModelRegistry {
    private static Map<String, ModelAdapter> mapModelAdapters = CustomModelRegistry.makeMapModelAdapters();

    private static Map<String, ModelAdapter> makeMapModelAdapters() {
        LinkedHashMap<String, ModelAdapter> map2 = new LinkedHashMap<String, ModelAdapter>();
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterArmorStand());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterBat());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterBlaze());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterBoat());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterCaveSpider());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterChicken());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterCow());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterCreeper());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterDragon());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterEnderCrystal());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterEnderman());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterEndermite());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterGhast());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterGuardian());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterHorse());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterIronGolem());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterLeadKnot());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterMagmaCube());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterMinecart());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterMinecartTnt());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterMinecartMobSpawner());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterMooshroom());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterOcelot());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterPig());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterPigZombie());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterRabbit());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSheep());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSilverfish());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSkeleton());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSlime());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSnowman());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSpider());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSquid());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterVillager());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterWitch());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterWither());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterWitherSkull());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterWolf());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterZombie());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSheepWool());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterBanner());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterBook());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterChest());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterChestLarge());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterEnderChest());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterHeadHumanoid());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterHeadSkeleton());
        CustomModelRegistry.addModelAdapter(map2, new ModelAdapterSign());
        return map2;
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map2, ModelAdapter modelAdapter) {
        CustomModelRegistry.addModelAdapter(map2, modelAdapter, modelAdapter.getName());
        String[] astring = modelAdapter.getAliases();
        if (astring != null) {
            for (int i = 0; i < astring.length; ++i) {
                String s = astring[i];
                CustomModelRegistry.addModelAdapter(map2, modelAdapter, s);
            }
        }
        ModelBase modelbase = modelAdapter.makeModel();
        String[] astring1 = modelAdapter.getModelRendererNames();
        for (int j = 0; j < astring1.length; ++j) {
            String s1 = astring1[j];
            ModelRenderer modelrenderer = modelAdapter.getModelRenderer(modelbase, s1);
            if (modelrenderer != null) continue;
            Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + s1);
        }
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map2, ModelAdapter modelAdapter, String name) {
        if (map2.containsKey(name)) {
            Config.warn("Model adapter already registered for id: " + name + ", class: " + modelAdapter.getEntityClass().getName());
        }
        map2.put(name, modelAdapter);
    }

    public static ModelAdapter getModelAdapter(String name) {
        return mapModelAdapters.get(name);
    }

    public static String[] getModelNames() {
        Set<String> set = mapModelAdapters.keySet();
        String[] astring = set.toArray(new String[set.size()]);
        return astring;
    }
}

