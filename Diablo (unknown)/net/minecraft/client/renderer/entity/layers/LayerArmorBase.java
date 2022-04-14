/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomItems;
import optifine.Reflector;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public abstract class LayerArmorBase<T extends ModelBase>
implements LayerRenderer<EntityLivingBase> {
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    protected ModelBase field_177189_c;
    protected ModelBase field_177186_d;
    private final RendererLivingEntity renderer;
    private final float alpha = 1.0f;
    private final float colorR = 1.0f;
    private final float colorG = 1.0f;
    private final float colorB = 1.0f;
    private boolean field_177193_i;
    private static final Map ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private static final String __OBFID = "CL_00002428";

    public LayerArmorBase(RendererLivingEntity rendererIn) {
        this.renderer = rendererIn;
        this.initArmor();
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 4);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 3);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 2);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 1);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    private void renderLayer(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float p_177182_4_, float p_177182_5_, float p_177182_6_, float p_177182_7_, float p_177182_8_, int armorSlot) {
        ItemStack itemstack = this.getCurrentArmor(entitylivingbaseIn, armorSlot);
        if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
            ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
            ModelBase modelbase = this.func_177175_a(armorSlot);
            modelbase.setModelAttributes(this.renderer.getMainModel());
            modelbase.setLivingAnimations(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_4_);
            if (Reflector.ForgeHooksClient.exists()) {
                modelbase = this.getArmorModelHook(entitylivingbaseIn, itemstack, armorSlot, modelbase);
            }
            this.func_177179_a(modelbase, armorSlot);
            boolean flag = this.isSlotForLeggings(armorSlot);
            if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, null)) {
                if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                    this.renderer.bindTexture(this.getArmorResource(entitylivingbaseIn, itemstack, flag ? 2 : 1, null));
                } else {
                    this.renderer.bindTexture(this.getArmorResource(itemarmor, flag));
                }
            }
            if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                int j = itemarmor.getColor(itemstack);
                if (j != -1) {
                    float f3 = (float)(j >> 16 & 0xFF) / 255.0f;
                    float f4 = (float)(j >> 8 & 0xFF) / 255.0f;
                    float f5 = (float)(j & 0xFF) / 255.0f;
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    GlStateManager.color(1.0f * f3, 1.0f * f4, 1.0f * f5, 1.0f);
                    modelbase.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay")) {
                        this.renderer.bindTexture(this.getArmorResource(entitylivingbaseIn, itemstack, flag ? 2 : 1, "overlay"));
                    }
                }
                this.getClass();
                this.getClass();
                this.getClass();
                this.getClass();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                modelbase.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                if (!(this.field_177193_i || !itemstack.isItemEnchanted() || Config.isCustomItems() && CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, modelbase, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_))) {
                    this.func_177183_a(entitylivingbaseIn, modelbase, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                }
                return;
            }
            switch (itemarmor.getArmorMaterial()) {
                case LEATHER: {
                    int i = itemarmor.getColor(itemstack);
                    float f = (float)(i >> 16 & 0xFF) / 255.0f;
                    float f1 = (float)(i >> 8 & 0xFF) / 255.0f;
                    float f2 = (float)(i & 0xFF) / 255.0f;
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    GlStateManager.color(1.0f * f, 1.0f * f1, 1.0f * f2, 1.0f);
                    modelbase.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay")) {
                        this.renderer.bindTexture(this.getArmorResource(itemarmor, flag, "overlay"));
                    }
                }
                case CHAIN: 
                case IRON: 
                case GOLD: 
                case DIAMOND: {
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    this.getClass();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    modelbase.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                }
            }
            if (!(this.field_177193_i || !itemstack.isItemEnchanted() || Config.isCustomItems() && CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, modelbase, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_))) {
                this.func_177183_a(entitylivingbaseIn, modelbase, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
            }
        }
    }

    public ItemStack getCurrentArmor(EntityLivingBase entitylivingbaseIn, int armorSlot) {
        return entitylivingbaseIn.getCurrentArmor(armorSlot - 1);
    }

    public ModelBase func_177175_a(int p_177175_1_) {
        return this.isSlotForLeggings(p_177175_1_) ? this.field_177189_c : this.field_177186_d;
    }

    private boolean isSlotForLeggings(int armorSlot) {
        return armorSlot == 2;
    }

    private void func_177183_a(EntityLivingBase entitylivingbaseIn, ModelBase modelbaseIn, float p_177183_3_, float p_177183_4_, float p_177183_5_, float p_177183_6_, float p_177183_7_, float p_177183_8_, float p_177183_9_) {
        if (!(Config.isCustomItems() && !CustomItems.isUseGlint() || Config.isShaders() && Shaders.isShadowPass)) {
            float f = (float)entitylivingbaseIn.ticksExisted + p_177183_5_;
            this.renderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
            if (Config.isShaders()) {
                ShadersRender.renderEnchantedGlintBegin();
            }
            GlStateManager.enableBlend();
            GlStateManager.depthFunc(514);
            GlStateManager.depthMask(false);
            float f1 = 0.5f;
            GlStateManager.color(f1, f1, f1, 1.0f);
            for (int i = 0; i < 2; ++i) {
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(768, 1);
                float f2 = 0.76f;
                GlStateManager.color(0.5f * f2, 0.25f * f2, 0.8f * f2, 1.0f);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float f3 = 0.33333334f;
                GlStateManager.scale(f3, f3, f3);
                GlStateManager.rotate(30.0f - (float)i * 60.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(0.0f, f * (0.001f + (float)i * 0.003f) * 20.0f, 0.0f);
                GlStateManager.matrixMode(5888);
                modelbaseIn.render(entitylivingbaseIn, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_, p_177183_9_);
            }
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
    }

    private ResourceLocation getArmorResource(ItemArmor p_177181_1_, boolean p_177181_2_) {
        return this.getArmorResource(p_177181_1_, p_177181_2_, null);
    }

    private ResourceLocation getArmorResource(ItemArmor p_177178_1_, boolean p_177178_2_, String p_177178_3_) {
        String s = String.format("textures/models/armor/%s_layer_%d%s.png", p_177178_1_.getArmorMaterial().getName(), p_177178_2_ ? 2 : 1, p_177178_3_ == null ? "" : String.format("_%s", p_177178_3_));
        ResourceLocation resourcelocation = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
        }
        return resourcelocation;
    }

    protected abstract void initArmor();

    protected abstract void func_177179_a(T var1, int var2);

    protected ModelBase getArmorModelHook(EntityLivingBase p_getArmorModelHook_1_, ItemStack p_getArmorModelHook_2_, int p_getArmorModelHook_3_, ModelBase p_getArmorModelHook_4_) {
        return p_getArmorModelHook_4_;
    }

    public ResourceLocation getArmorResource(Entity p_getArmorResource_1_, ItemStack p_getArmorResource_2_, int p_getArmorResource_3_, String p_getArmorResource_4_) {
        ItemArmor itemarmor = (ItemArmor)p_getArmorResource_2_.getItem();
        String s = itemarmor.getArmorMaterial().getName();
        String s1 = "minecraft";
        int i = s.indexOf(58);
        if (i != -1) {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
        }
        String s2 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", s1, s, p_getArmorResource_3_ == 2 ? 2 : 1, p_getArmorResource_4_ == null ? "" : String.format("_%s", p_getArmorResource_4_));
        ResourceLocation resourcelocation = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(s2 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, p_getArmorResource_1_, p_getArmorResource_2_, s2, p_getArmorResource_3_, p_getArmorResource_4_));
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s2);
            ARMOR_TEXTURE_RES_MAP.put(s2, resourcelocation);
        }
        return resourcelocation;
    }
}

