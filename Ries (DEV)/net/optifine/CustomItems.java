/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomItemProperties;
import net.optifine.CustomItemsComparator;
import net.optifine.config.NbtTagValue;
import net.optifine.render.Blender;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class CustomItems {
    private static CustomItemProperties[][] itemProperties = null;
    private static CustomItemProperties[][] enchantmentProperties = null;
    private static Map mapPotionIds = null;
    private static final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
    private static boolean useGlint = true;
    private static final boolean renderOffHand = false;
    private static final int[][] EMPTY_INT2_ARRAY = new int[0][];

    public static void update() {
        itemProperties = null;
        enchantmentProperties = null;
        useGlint = true;
        if (Config.isCustomItems()) {
            CustomItems.readCitProperties();
            IResourcePack[] airesourcepack = Config.getResourcePacks();
            for (int i = airesourcepack.length - 1; i >= 0; --i) {
                IResourcePack iresourcepack = airesourcepack[i];
                CustomItems.update(iresourcepack);
            }
            CustomItems.update(Config.getDefaultResourcePack());
            if (itemProperties.length <= 0) {
                itemProperties = null;
            }
            if (enchantmentProperties.length <= 0) {
                enchantmentProperties = null;
            }
        }
    }

    private static void readCitProperties() {
        try {
            ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/cit.properties");
            InputStream inputstream = Config.getResourceStream(resourcelocation);
            if (inputstream == null) {
                return;
            }
            Config.dbg("CustomItems: Loading mcpatcher/cit.properties");
            PropertiesOrdered properties = new PropertiesOrdered();
            properties.load(inputstream);
            inputstream.close();
            useGlint = Config.parseBoolean(properties.getProperty("useGlint"), true);
        }
        catch (FileNotFoundException resourcelocation) {
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private static void update(IResourcePack rp) {
        Object[] astring = ResUtils.collectFiles(rp, "mcpatcher/cit/", ".properties", null);
        Map map = CustomItems.makeAutoImageProperties(rp);
        if (map.size() > 0) {
            Set set = map.keySet();
            Object[] astring1 = set.toArray(new String[0]);
            astring = (String[])Config.addObjectsToArray(astring, astring1);
        }
        Arrays.sort(astring);
        List list = CustomItems.makePropertyList(itemProperties);
        List list1 = CustomItems.makePropertyList(enchantmentProperties);
        for (Object s : astring) {
            Config.dbg("CustomItems: " + (String)s);
            try {
                CustomItemProperties customitemproperties = null;
                if (map.containsKey(s)) {
                    customitemproperties = (CustomItemProperties)map.get(s);
                }
                if (customitemproperties == null) {
                    ResourceLocation resourcelocation = new ResourceLocation((String)s);
                    InputStream inputstream = rp.getInputStream(resourcelocation);
                    if (inputstream == null) {
                        Config.warn("CustomItems file not found: " + (String)s);
                        continue;
                    }
                    PropertiesOrdered properties = new PropertiesOrdered();
                    properties.load(inputstream);
                    customitemproperties = new CustomItemProperties(properties, (String)s);
                }
                if (!customitemproperties.isValid((String)s)) continue;
                CustomItems.addToItemList(customitemproperties, list);
                CustomItems.addToEnchantmentList(customitemproperties, list1);
            }
            catch (FileNotFoundException var11) {
                Config.warn("CustomItems file not found: " + (String)s);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        itemProperties = CustomItems.propertyListToArray(list);
        enchantmentProperties = CustomItems.propertyListToArray(list1);
        Comparator comparator = CustomItems.getPropertiesComparator();
        for (CustomItemProperties[] acustomitemproperties : itemProperties) {
            if (acustomitemproperties == null) continue;
            Arrays.sort(acustomitemproperties, comparator);
        }
        for (CustomItemProperties[] acustomitemproperties1 : enchantmentProperties) {
            if (acustomitemproperties1 == null) continue;
            Arrays.sort(acustomitemproperties1, comparator);
        }
    }

    private static Comparator getPropertiesComparator() {
        return (o1, o2) -> {
            CustomItemProperties customitemproperties = (CustomItemProperties)o1;
            CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
            return customitemproperties.layer != customitemproperties1.layer ? customitemproperties.layer - customitemproperties1.layer : (customitemproperties.weight != customitemproperties1.weight ? customitemproperties1.weight - customitemproperties.weight : (!customitemproperties.basePath.equals(customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name)));
        };
    }

    public static void updateIcons(TextureMap textureMap) {
        for (CustomItemProperties customitemproperties : CustomItems.getAllProperties()) {
            customitemproperties.updateIcons(textureMap);
        }
    }

    public static void loadModels(ModelBakery modelBakery) {
        for (CustomItemProperties customitemproperties : CustomItems.getAllProperties()) {
            customitemproperties.loadModels(modelBakery);
        }
    }

    public static void updateModels() {
        for (CustomItemProperties customitemproperties : CustomItems.getAllProperties()) {
            if (customitemproperties.type != 1) continue;
            TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
            customitemproperties.updateModelTexture(texturemap, itemModelGenerator);
            customitemproperties.updateModelsFull();
        }
    }

    private static List<CustomItemProperties> getAllProperties() {
        ArrayList<CustomItemProperties> list = new ArrayList<CustomItemProperties>();
        CustomItems.addAll(itemProperties, list);
        CustomItems.addAll(enchantmentProperties, list);
        return list;
    }

    private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
        if (cipsArr != null) {
            for (CustomItemProperties[] acustomitemproperties : cipsArr) {
                if (acustomitemproperties == null) continue;
                for (CustomItemProperties customitemproperties : acustomitemproperties) {
                    if (customitemproperties == null) continue;
                    list.add(customitemproperties);
                }
            }
        }
    }

    private static Map makeAutoImageProperties(IResourcePack rp) {
        HashMap map = new HashMap();
        map.putAll(CustomItems.makePotionImageProperties(rp, "normal", Item.getIdFromItem(Items.potionitem)));
        map.putAll(CustomItems.makePotionImageProperties(rp, "splash", Item.getIdFromItem(Items.potionitem)));
        map.putAll(CustomItems.makePotionImageProperties(rp, "linger", Item.getIdFromItem(Items.potionitem)));
        return map;
    }

    private static Map makePotionImageProperties(IResourcePack rp, String type, int itemId) {
        String[] astring2;
        HashMap<String, CustomItemProperties> map = new HashMap<String, CustomItemProperties>();
        String s = type + "/";
        String[] astring = new String[]{"mcpatcher/cit/potion/" + s, "mcpatcher/cit/Potion/" + s};
        String[] astring1 = new String[]{".png"};
        for (String s1 : astring2 = ResUtils.collectFiles(rp, astring, astring1)) {
            String name = StrUtils.removePrefixSuffix(s1, astring, astring1);
            Properties properties = CustomItems.makePotionProperties(name, type, itemId, s1);
            if (properties == null) continue;
            String s3 = StrUtils.removeSuffix(s1, astring1) + ".properties";
            CustomItemProperties customitemproperties = new CustomItemProperties(properties, s3);
            map.put(s3, customitemproperties);
        }
        return map;
    }

    private static Properties makePotionProperties(String name, String type, int itemId, String path) {
        if (StrUtils.endsWith(name, new String[]{"_n", "_s"})) {
            return null;
        }
        if (name.equals("empty") && type.equals("normal")) {
            itemId = Item.getIdFromItem(Items.glass_bottle);
            PropertiesOrdered properties = new PropertiesOrdered();
            ((Hashtable)properties).put("type", "item");
            ((Hashtable)properties).put("items", "" + itemId);
            return properties;
        }
        int[] aint = (int[])CustomItems.getMapPotionIds().get(name);
        if (aint == null) {
            Config.warn("Potion not found for image: " + path);
            return null;
        }
        StringBuilder stringbuffer = new StringBuilder();
        for (int i = 0; i < aint.length; ++i) {
            int j = aint[i];
            if (type.equals("splash")) {
                j |= 0x4000;
            }
            if (i > 0) {
                stringbuffer.append(" ");
            }
            stringbuffer.append(j);
        }
        int k = 16447;
        if (name.equals("water") || name.equals("mundane")) {
            k |= 0x40;
        }
        PropertiesOrdered properties1 = new PropertiesOrdered();
        ((Hashtable)properties1).put("type", "item");
        ((Hashtable)properties1).put("items", "" + itemId);
        ((Hashtable)properties1).put("damage", "" + stringbuffer);
        ((Hashtable)properties1).put("damageMask", "" + k);
        if (type.equals("splash")) {
            ((Hashtable)properties1).put("texture.potion_bottle_splash", name);
        } else {
            ((Hashtable)properties1).put("texture.potion_bottle_drinkable", name);
        }
        return properties1;
    }

    private static Map getMapPotionIds() {
        if (mapPotionIds == null) {
            mapPotionIds = new LinkedHashMap();
            mapPotionIds.put("water", CustomItems.getPotionId(0, 0));
            mapPotionIds.put("awkward", CustomItems.getPotionId(0, 1));
            mapPotionIds.put("thick", CustomItems.getPotionId(0, 2));
            mapPotionIds.put("potent", CustomItems.getPotionId(0, 3));
            mapPotionIds.put("regeneration", CustomItems.getPotionIds(1));
            mapPotionIds.put("movespeed", CustomItems.getPotionIds(2));
            mapPotionIds.put("fireresistance", CustomItems.getPotionIds(3));
            mapPotionIds.put("poison", CustomItems.getPotionIds(4));
            mapPotionIds.put("heal", CustomItems.getPotionIds(5));
            mapPotionIds.put("nightvision", CustomItems.getPotionIds(6));
            mapPotionIds.put("clear", CustomItems.getPotionId(7, 0));
            mapPotionIds.put("bungling", CustomItems.getPotionId(7, 1));
            mapPotionIds.put("charming", CustomItems.getPotionId(7, 2));
            mapPotionIds.put("rank", CustomItems.getPotionId(7, 3));
            mapPotionIds.put("weakness", CustomItems.getPotionIds(8));
            mapPotionIds.put("damageboost", CustomItems.getPotionIds(9));
            mapPotionIds.put("moveslowdown", CustomItems.getPotionIds(10));
            mapPotionIds.put("leaping", CustomItems.getPotionIds(11));
            mapPotionIds.put("harm", CustomItems.getPotionIds(12));
            mapPotionIds.put("waterbreathing", CustomItems.getPotionIds(13));
            mapPotionIds.put("invisibility", CustomItems.getPotionIds(14));
            mapPotionIds.put("thin", CustomItems.getPotionId(15, 0));
            mapPotionIds.put("debonair", CustomItems.getPotionId(15, 1));
            mapPotionIds.put("sparkling", CustomItems.getPotionId(15, 2));
            mapPotionIds.put("stinky", CustomItems.getPotionId(15, 3));
            mapPotionIds.put("mundane", CustomItems.getPotionId(0, 4));
            mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
            mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
            mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
            mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
            mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
            mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
            mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
            mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
        }
        return mapPotionIds;
    }

    private static int[] getPotionIds(int baseId) {
        return new int[]{baseId, baseId + 16, baseId + 32, baseId + 48};
    }

    private static int[] getPotionId(int baseId, int subId) {
        return new int[]{baseId + subId * 16};
    }

    private static List makePropertyList(CustomItemProperties[][] propsArr) {
        ArrayList<ArrayList<CustomItemProperties>> list = new ArrayList<ArrayList<CustomItemProperties>>();
        if (propsArr != null) {
            for (CustomItemProperties[] acustomitemproperties : propsArr) {
                ArrayList<CustomItemProperties> list1 = null;
                if (acustomitemproperties != null) {
                    list1 = new ArrayList<CustomItemProperties>(Arrays.asList(acustomitemproperties));
                }
                list.add(list1);
            }
        }
        return list;
    }

    private static CustomItemProperties[][] propertyListToArray(List lists) {
        CustomItemProperties[][] acustomitemproperties = new CustomItemProperties[lists.size()][];
        for (int i = 0; i < lists.size(); ++i) {
            List list = (List)lists.get(i);
            if (list == null) continue;
            CustomItemProperties[] acustomitemproperties1 = list.toArray(new CustomItemProperties[0]);
            Arrays.sort(acustomitemproperties1, new CustomItemsComparator());
            acustomitemproperties[i] = acustomitemproperties1;
        }
        return acustomitemproperties;
    }

    private static void addToItemList(CustomItemProperties cp, List itemList) {
        if (cp.items != null) {
            for (int i = 0; i < cp.items.length; ++i) {
                int j = cp.items[i];
                if (j <= 0) {
                    Config.warn("Invalid item ID: " + j);
                    continue;
                }
                CustomItems.addToList(cp, itemList, j);
            }
        }
    }

    private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
        if (cp.type == 2 && cp.enchantmentIds != null) {
            for (int i = 0; i < 256; ++i) {
                if (!cp.enchantmentIds.isInRange(i)) continue;
                CustomItems.addToList(cp, enchantmentList, i);
            }
        }
    }

    private static void addToList(CustomItemProperties cp, List lists, int id) {
        while (id >= lists.size()) {
            lists.add(null);
        }
        ArrayList<CustomItemProperties> list = (ArrayList<CustomItemProperties>)lists.get(id);
        if (list == null) {
            list = new ArrayList<CustomItemProperties>();
            list.set(id, (CustomItemProperties)((Object)list));
        }
        list.add(cp);
    }

    public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel model, ResourceLocation modelLocation, boolean fullModel) {
        if (!fullModel && model.isGui3d()) {
            return model;
        }
        if (itemProperties == null) {
            return model;
        }
        CustomItemProperties customitemproperties = CustomItems.getCustomItemProperties(itemStack, 1);
        if (customitemproperties == null) {
            return model;
        }
        IBakedModel ibakedmodel = customitemproperties.getBakedModel(modelLocation, fullModel);
        return ibakedmodel != null ? ibakedmodel : model;
    }

    public static boolean bindCustomArmorTexture(ItemStack itemStack, int layer, String overlay) {
        if (itemProperties == null) {
            return true;
        }
        ResourceLocation resourcelocation = CustomItems.getCustomArmorLocation(itemStack, layer, overlay);
        if (resourcelocation == null) {
            return true;
        }
        Config.getTextureManager().bindTexture(resourcelocation);
        return false;
    }

    private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int layer, String overlay) {
        String s1;
        ResourceLocation resourcelocation;
        CustomItemProperties customitemproperties = CustomItems.getCustomItemProperties(itemStack, 3);
        if (customitemproperties == null) {
            return null;
        }
        if (customitemproperties.mapTextureLocations == null) {
            return customitemproperties.textureLocation;
        }
        Item item = itemStack.getItem();
        if (!(item instanceof ItemArmor)) {
            return null;
        }
        ItemArmor itemarmor = (ItemArmor)item;
        String s = itemarmor.getArmorMaterial().getName();
        StringBuilder stringbuffer = new StringBuilder();
        stringbuffer.append("texture.");
        stringbuffer.append(s);
        stringbuffer.append("_layer_");
        stringbuffer.append(layer);
        if (overlay != null) {
            stringbuffer.append("_");
            stringbuffer.append(overlay);
        }
        return (resourcelocation = (ResourceLocation)customitemproperties.mapTextureLocations.get(s1 = stringbuffer.toString())) == null ? customitemproperties.textureLocation : resourcelocation;
    }

    private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
        CustomItemProperties[] acustomitemproperties;
        if (itemProperties == null) {
            return null;
        }
        if (itemStack == null) {
            return null;
        }
        Item item = itemStack.getItem();
        int i = Item.getIdFromItem(item);
        if (i >= 0 && i < itemProperties.length && (acustomitemproperties = itemProperties[i]) != null) {
            for (CustomItemProperties customitemproperties : acustomitemproperties) {
                if (customitemproperties.type != type || !CustomItems.matchesProperties(customitemproperties, itemStack, null)) continue;
                return customitemproperties;
            }
        }
        return null;
    }

    private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
        Item item = itemStack.getItem();
        if (cip.damage != null) {
            int i = itemStack.getItemDamage();
            if (cip.damageMask != 0) {
                i &= cip.damageMask;
            }
            if (cip.damagePercent) {
                int j = item.getMaxDamage();
                i = (int)((double)(i * 100) / (double)j);
            }
            if (!cip.damage.isInRange(i)) {
                return false;
            }
        }
        if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize)) {
            return false;
        }
        int[][] aint = enchantmentIdLevels;
        if (cip.enchantmentIds != null) {
            if (enchantmentIdLevels == null) {
                aint = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            boolean flag = false;
            for (int[] ints : aint) {
                int l = ints[0];
                if (!cip.enchantmentIds.isInRange(l)) continue;
                flag = true;
                break;
            }
            if (!flag) {
                return false;
            }
        }
        if (cip.enchantmentLevels != null) {
            if (aint == null) {
                aint = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            boolean flag1 = false;
            for (int[] ints : aint) {
                int k1 = ints[1];
                if (!cip.enchantmentLevels.isInRange(k1)) continue;
                flag1 = true;
                break;
            }
            if (!flag1) {
                return false;
            }
        }
        if (cip.nbtTagValues != null) {
            NBTTagCompound nbttagcompound = itemStack.getTagCompound();
            for (int j1 = 0; j1 < cip.nbtTagValues.length; ++j1) {
                NbtTagValue nbttagvalue = cip.nbtTagValues[j1];
                if (nbttagvalue.matches(nbttagcompound)) continue;
                return false;
            }
        }
        if (cip.hand != 0) {
            if (cip.hand == 1) {
                // empty if block
            }
            return cip.hand != 2;
        }
        return true;
    }

    private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
        NBTTagList nbttaglist;
        Item item = itemStack.getItem();
        NBTTagList nBTTagList = nbttaglist = item == Items.enchanted_book ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
        if (nbttaglist != null && nbttaglist.tagCount() > 0) {
            int[][] aint = new int[nbttaglist.tagCount()][2];
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                short j = nbttagcompound.getShort("id");
                short k = nbttagcompound.getShort("lvl");
                aint[i][0] = j;
                aint[i][1] = k;
            }
            return aint;
        }
        return EMPTY_INT2_ARRAY;
    }

    public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel model) {
        if (enchantmentProperties == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        int[][] aint = CustomItems.getEnchantmentIdLevels(itemStack);
        if (aint.length <= 0) {
            return false;
        }
        HashSet<Integer> set = null;
        boolean flag = false;
        TextureManager texturemanager = Config.getTextureManager();
        for (int[] ints : aint) {
            CustomItemProperties[] acustomitemproperties;
            int j = ints[0];
            if (j < 0 || j >= enchantmentProperties.length || (acustomitemproperties = enchantmentProperties[j]) == null) continue;
            for (CustomItemProperties customitemproperties : acustomitemproperties) {
                if (set == null) {
                    set = new HashSet<Integer>();
                }
                if (!set.add(j) || !CustomItems.matchesProperties(customitemproperties, itemStack, aint) || customitemproperties.textureLocation == null) continue;
                texturemanager.bindTexture(customitemproperties.textureLocation);
                float f = customitemproperties.getTextureWidth(texturemanager);
                if (!flag) {
                    flag = true;
                    GlStateManager.depthMask(false);
                    GlStateManager.depthFunc(514);
                    GlStateManager.disableLighting();
                    GlStateManager.matrixMode(5890);
                }
                Blender.setupBlend(customitemproperties.blend, 1.0f);
                GlStateManager.pushMatrix();
                GlStateManager.scale(f / 2.0f, f / 2.0f, f / 2.0f);
                float f1 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translate(f1, 0.0f, 0.0f);
                GlStateManager.rotate(customitemproperties.rotation, 0.0f, 0.0f, 1.0f);
                renderItem.renderModel(model, -1);
                GlStateManager.popMatrix();
            }
        }
        if (flag) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            texturemanager.bindTexture(TextureMap.locationBlocksTexture);
        }
        return flag;
    }

    public static boolean renderCustomArmorEffect(EntityLivingBase entity, ItemStack itemStack, ModelBase model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
        if (enchantmentProperties == null) {
            return true;
        }
        if (Config.isShaders() && Shaders.isShadowPass) {
            return true;
        }
        if (itemStack == null) {
            return true;
        }
        int[][] aint = CustomItems.getEnchantmentIdLevels(itemStack);
        if (aint.length <= 0) {
            return true;
        }
        HashSet<Integer> set = null;
        boolean flag = false;
        TextureManager texturemanager = Config.getTextureManager();
        for (int[] ints : aint) {
            CustomItemProperties[] acustomitemproperties;
            int j = ints[0];
            if (j < 0 || j >= enchantmentProperties.length || (acustomitemproperties = enchantmentProperties[j]) == null) continue;
            for (CustomItemProperties customitemproperties : acustomitemproperties) {
                if (set == null) {
                    set = new HashSet<Integer>();
                }
                if (!set.add(j) || !CustomItems.matchesProperties(customitemproperties, itemStack, aint) || customitemproperties.textureLocation == null) continue;
                texturemanager.bindTexture(customitemproperties.textureLocation);
                float f = customitemproperties.getTextureWidth(texturemanager);
                if (!flag) {
                    flag = true;
                    if (Config.isShaders()) {
                        ShadersRender.renderEnchantedGlintBegin();
                    }
                    GlStateManager.enableBlend();
                    GlStateManager.depthFunc(514);
                    GlStateManager.depthMask(false);
                }
                Blender.setupBlend(customitemproperties.blend, 1.0f);
                GlStateManager.disableLighting();
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.rotate(customitemproperties.rotation, 0.0f, 0.0f, 1.0f);
                float f1 = f / 8.0f;
                GlStateManager.scale(f1, f1 / 2.0f, f1);
                float f2 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translate(0.0f, f2, 0.0f);
                GlStateManager.matrixMode(5888);
                model.render(entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
            }
        }
        if (flag) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (Config.isShaders()) {
                ShadersRender.renderEnchantedGlintEnd();
            }
        }
        return !flag;
    }

    public static boolean isUseGlint() {
        return useGlint;
    }
}

