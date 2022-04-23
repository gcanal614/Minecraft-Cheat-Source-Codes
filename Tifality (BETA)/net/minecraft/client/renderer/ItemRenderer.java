/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import club.tifality.Tifality;
import club.tifality.module.impl.other.Animations;
import club.tifality.module.impl.render.Chams;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.optifine.DynamicLights;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    private final Minecraft mc;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;
    public float angle;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private int equippedItemSlot = -1;

    public ItemRenderer(Minecraft mcIn) {
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (heldStack != null) {
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GL11.glPushMatrix();
            if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                if (!(!this.isBlockTranslucent(block) || Config.isShaders() && Shaders.renderItemKeepDepthMask)) {
                    GlStateManager.depthMask(false);
                }
            }
            this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
            if (this.isBlockTranslucent(block)) {
                GlStateManager.depthMask(true);
            }
            GL11.glPopMatrix();
        }
    }

    private boolean isBlockTranslucent(Block blockIn) {
        return blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    private void func_178101_a(float angle, float p_178101_2_) {
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(p_178101_2_, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

    private void func_178109_a(AbstractClientPlayer clientPlayer) {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + (double)clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
        }
        float f = i & 0xFFFF;
        float f1 = i >> 16;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks) {
        float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
        float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
        GL11.glRotatef((entityplayerspIn.rotationPitch - f) * 0.1f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((entityplayerspIn.rotationYaw - f1) * 0.1f, 0.0f, 1.0f, 0.0f);
    }

    private float func_178100_c(float p_178100_1_) {
        float f = 1.0f - p_178100_1_ / 45.0f + 0.1f;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5f + 0.5f;
        return f;
    }

    private void renderRightArm(RenderPlayer renderPlayerIn) {
        GL11.glPushMatrix();
        GL11.glRotatef(54.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(64.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(-62.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.25f, -0.85f, 0.75f);
        renderPlayerIn.renderRightArm(this.mc.thePlayer);
        GL11.glPopMatrix();
    }

    private void renderLeftArm(RenderPlayer renderPlayerIn) {
        GL11.glPushMatrix();
        GL11.glRotatef(92.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(41.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-0.3f, -1.1f, 0.45f);
        renderPlayerIn.renderLeftArm(this.mc.thePlayer);
        GL11.glPopMatrix();
    }

    private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        Render render2 = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer renderplayer = (RenderPlayer)render2;
        if (!clientPlayer.isInvisible()) {
            GlStateManager.disableCull();
            this.renderRightArm(renderplayer);
            this.renderLeftArm(renderplayer);
            GlStateManager.enableCull();
        }
    }

    private void renderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_, float p_178097_4_) {
        float f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI);
        float f1 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI * 2.0f);
        float f2 = -0.2f * MathHelper.sin(p_178097_4_ * (float)Math.PI);
        GL11.glTranslatef(f, f1, f2);
        float f3 = this.func_178100_c(p_178097_2_);
        GL11.glTranslatef(0.0f, 0.04f, -0.72f);
        GL11.glTranslatef(0.0f, p_178097_3_ * -1.2f, 0.0f);
        GL11.glTranslatef(0.0f, f3 * -0.5f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f3 * -85.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
        this.renderPlayerArms(clientPlayer);
        float f4 = MathHelper.sin(p_178097_4_ * p_178097_4_ * (float)Math.PI);
        float f5 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI);
        GL11.glRotatef(f4 * -20.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f5 * -20.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(f5 * -80.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.38f, 0.38f, 0.38f);
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-1.0f, -1.0f, 0.0f);
        GL11.glScalef(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        worldrenderer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        worldrenderer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        worldrenderer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        MapData mapdata = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
        if (mapdata != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
        }
    }

    private void renderArmInFirstPerson(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_) {
        Chams instance = Tifality.INSTANCE.getModuleManager().getModule(Chams.class);
        boolean handChams = instance.shouldRenderHand();
        float f = -0.3f * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        float f1 = 0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI * 2.0f);
        float f2 = -0.4f * MathHelper.sin(p_178095_3_ * (float)Math.PI);
        GL11.glTranslatef(f, f1, f2);
        GL11.glTranslatef(0.64000005f, -0.6f, -0.72f);
        GL11.glTranslatef(0.0f, p_178095_2_ * -0.6f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
        float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float)Math.PI);
        float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        GL11.glRotatef(f4 * 70.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f3 * -20.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
        GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(5.6f, 0.0f, 0.0f);
        GlStateManager.disableCull();
        Render render2 = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer renderPlayer = (RenderPlayer)render2;
        if (handChams) {
            instance.preHandRender();
        } else {
            this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        renderPlayer.renderRightArm(this.mc.thePlayer);
        if (handChams) {
            instance.postHandRender();
        }
        GlStateManager.enableCull();
    }

    private void func_178105_d(float p_178105_1_) {
        float f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI);
        float f1 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI * 2.0f);
        float f2 = -0.2f * MathHelper.sin(p_178105_1_ * (float)Math.PI);
        GL11.glTranslatef(f, f1, f2);
    }

    private void func_178104_a(AbstractClientPlayer clientPlayer, float p_178104_2_) {
        float f = (float)clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0f;
        float f1 = f / (float)this.itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float)Math.PI) * 0.1f);
        if (f1 >= 0.8f) {
            f2 = 0.0f;
        }
        GL11.glTranslatef(0.0f, f2, 0.0f);
        float f3 = 1.0f - (float)Math.pow(f1, 27.0);
        GL11.glTranslatef(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f);
        GL11.glRotatef(f3 * 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(f3 * 30.0f, 0.0f, 0.0f, 1.0f);
    }

    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        GL11.glTranslatef(0.56f, -0.52f, -0.72f);
        GL11.glTranslatef(0.0f, equipProgress * -0.6f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
        if ((double)swingProgress > 0.0) {
            float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
            GL11.glRotatef(f * -20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
        }
        float scale = 0.4f;
        if (Animations.getInstance().isEnabled()) {
            scale *= ((Double)Animations.getInstance().itemScale.getValue()).floatValue();
        }
        GL11.glScalef(scale, scale, scale);
    }

    private void swong(float equipProgress, float swingProgress) {
        GL11.glTranslatef(0.56f, -0.52f, -0.72f);
        GL11.glTranslatef(0.0f, equipProgress * -0.6f, 0.0f);
        GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
        if ((double)swingProgress > 0.0) {
            float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
            GL11.glRotatef(f * -20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
        }
        float scale = 0.4f;
        if (Animations.getInstance().isEnabled()) {
            scale *= ((Double)Animations.getInstance().itemScale.getValue()).floatValue();
        }
        GL11.glScalef(scale, scale, scale);
    }

    private void swongg(float equipProgress, float swingProgress) {
        GL11.glTranslatef(0.56f, -0.52f, -0.72f);
        GL11.glTranslatef(0.0f, equipProgress * -0.6f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
        if ((double)swingProgress > 0.0) {
            float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
            GL11.glRotatef(f * -20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
        }
        float scale = 0.4f;
        if (Animations.getInstance().isEnabled()) {
            scale *= ((Double)Animations.getInstance().itemScale.getValue()).floatValue();
        }
        GL11.glScalef(scale, scale, scale);
    }

    private void func_178098_a(float p_178098_1_, AbstractClientPlayer clientPlayer) {
        GL11.glRotatef(-18.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(-12.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-8.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.9f, 0.2f, 0.0f);
        float f = (float)this.itemToRender.getMaxItemUseDuration() - ((float)clientPlayer.getItemInUseCount() - p_178098_1_ + 1.0f);
        float f1 = f / 20.0f;
        f1 = (f1 * f1 + f1 * 2.0f) / 3.0f;
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        if (f1 > 0.1f) {
            float f2 = MathHelper.sin((f - 0.1f) * 1.3f);
            float f3 = f1 - 0.1f;
            float f4 = f2 * f3;
            GL11.glTranslatef(f4 * 0.0f, f4 * 0.01f, f4 * 0.0f);
        }
        GL11.glTranslatef(f1 * 0.0f, f1 * 0.0f, f1 * 0.1f);
        GL11.glScalef(1.0f, 1.0f, 1.0f + f1 * 0.2f);
    }

    private void func_178103_d() {
        GL11.glTranslatef(-0.5f, 0.2f, 0.0f);
        GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-80.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
    }

    private void sw0ng() {
        GL11.glTranslatef(-0.5f, 0.2f, 0.0f);
        GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-80.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
    }

    private void avatar(float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float f2 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f2 * -40.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void func_178096_j(float equipProgress, float swingProgress) {
        Animations animations = Tifality.INSTANCE.getModuleManager().getModule(Animations.class);
        if (animations.coronaValue.get().booleanValue()) {
            GL11.glTranslatef(0.56f, -0.52f, -0.71999997f);
            GL11.glTranslatef(0.0f, -0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(360.0f * (float)(this.mc.thePlayer.ticksExisted % 20) / 20.0f, 1.0f, -1.0f, 1.0f);
            GL11.glScalef(0.4f, 0.4f, 0.4f);
        } else {
            GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
            GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
            GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(0.4f, 0.4f, 0.4f);
        }
    }

    private void func_178096_b(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, p_178096_1_ * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * (float)Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * (float)Math.PI);
        GlStateManager.rotate(var3 * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(var4 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(var4 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void Jigsaw(float var2, float swingProgress) {
        float smooth = swingProgress * 0.8f - swingProgress * swingProgress * 0.8f;
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, var2 * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 2.0f + smooth * 0.5f, smooth * 3.0f);
        GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.37f, 0.37f, 0.37f);
    }

    private void slide2(float var9) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float var11 = MathHelper.sin(var9 * var9 * (float)Math.PI);
        float var12 = MathHelper.sin(MathHelper.sqrt_float(var9) * (float)Math.PI);
        GlStateManager.rotate(var11 * 0.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(var12 * 0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(var12 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void doBlockTransformations() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }

    public void renderItemInFirstPerson(float partialTicks) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
            float f = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
            EntityPlayerSP abstractclientplayer = this.mc.thePlayer;
            float f1 = abstractclientplayer.getSwingProgress(partialTicks);
            float f2 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
            float f3 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
            this.func_178101_a(f2, f3);
            this.func_178109_a(abstractclientplayer);
            this.func_178110_a(abstractclientplayer, partialTicks);
            GlStateManager.enableRescaleNormal();
            GL11.glPushMatrix();
            if (this.itemToRender != null) {
                if (this.itemToRender.getItem() instanceof ItemMap) {
                    this.renderItemMap(abstractclientplayer, f2, f, f1);
                } else if (abstractclientplayer.getItemInUseCount() > 0) {
                    EnumAction enumaction = this.itemToRender.getItemUseAction();
                    block0 : switch (enumaction) {
                        case NONE: {
                            this.transformFirstPersonItem(f, f1);
                            break;
                        }
                        case EAT: 
                        case DRINK: {
                            this.func_178104_a(abstractclientplayer, partialTicks);
                            this.transformFirstPersonItem(f, f1);
                            break;
                        }
                        case BLOCK: {
                            float sw0ng = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            Animations animations = Tifality.INSTANCE.getModuleManager().getModule(Animations.class);
                            if (animations.isEnabled()) {
                                float eqiupProgress = animations.equipProgressProperty.getValue() != false ? f / ((Double)animations.equipProgMultProperty.getValue()).floatValue() : 0.0f;
                                GL11.glTranslated((Double)animations.xPosProperty.getValue(), (Double)animations.yPosProperty.getValue(), (Double)animations.zPosProperty.getValue());
                                switch ((Animations.AnimationMode)((Object)animations.animationModeProperty.getValue())) {
                                    case OLD: {
                                        this.transformFirstPersonItem(eqiupProgress, f1);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case SWONG: {
                                        GL11.glTranslated(this.mc.thePlayer.isSneaking() ? -0.034 : 0.03, this.mc.thePlayer.isSneaking() ? -0.045 : -0.055, 0.05);
                                        this.swong(f / 2.0f, 0.0f);
                                        GL11.glTranslatef(0.1f, 0.4f, -0.1f);
                                        GL11.glRotated(-sw0ng * 45.0f, sw0ng / 2.0f, 0.0, 45.0);
                                        GL11.glRotated(-sw0ng * 50.0f, 0.8, (double)sw0ng / 1.8, -0.1);
                                        this.sw0ng();
                                        break block0;
                                    }
                                    case SWUNG: {
                                        this.transformFirstPersonItem(eqiupProgress, 0.0f);
                                        GlStateManager.rotate(-sw0ng * 40.0f / 2.0f, MathHelper.sqrt_float(f1) / 2.0f, -0.0f, 9.0f);
                                        GlStateManager.rotate(-MathHelper.sqrt_float(f1) * 30.0f, 1.0f, MathHelper.sqrt_float(f1) / 2.0f, -0.0f);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case AVATAR: {
                                        this.avatar(f1);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case CORONA: {
                                        GlStateManager.translate(0.0f, 0.09f, 0.0f);
                                        GlStateManager.rotate(4.0f, 0.0f, 0.09f, 0.0f);
                                        this.func_178096_j(eqiupProgress, f1);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case LUCKY: {
                                        this.transformFirstPersonItem(0.0f, 0.0f);
                                        this.func_178103_d();
                                        float lucky = MathHelper.sin(MathHelper.sqrt_float(f1) * 0.3215927f);
                                        GlStateManager.translate(-0.05f, -0.0f, 0.3f);
                                        GlStateManager.rotate(-lucky * 60.0f / 2.0f, -15.0f, -0.0f, 9.0f);
                                        GlStateManager.rotate(-lucky * 70.0f, 1.0f, -0.4f, -0.0f);
                                        break block0;
                                    }
                                    case SWACK: {
                                        this.func_178096_b(eqiupProgress, 0.0f);
                                        GL11.glScaled(0.7, 0.7, 0.7);
                                        GL11.glTranslated(-1.0, 0.55, 0.1);
                                        GL11.glRotatef(-sw0ng * 29.0f, 7.0f, -0.6f, -0.0f);
                                        GL11.glRotatef(-sw0ng * 10.0f / 2.0f, -12.0f, 0.0f, 9.0f);
                                        GL11.glRotatef(-sw0ng * 17.0f, -1.0f, -0.6f, -0.7f);
                                        GL11.glTranslatef(sw0ng / 2.5f, 0.1f, sw0ng / 2.5f);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case SWONK: {
                                        GL11.glTranslated(0.0, 0.09, 0.03);
                                        this.Jigsaw(eqiupProgress, f1);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case JELLO: {
                                        this.slide2(f1);
                                        this.func_178103_d();
                                        break block0;
                                    }
                                    case SLIDE: {
                                        GL11.glTranslated(-0.1f, 0.15f, 0.0);
                                        GL11.glTranslated(0.1f, -0.15f, 0.0);
                                        this.transformFirstPersonItem(eqiupProgress, f1);
                                        this.doBlockTransformations();
                                        break block0;
                                    }
                                    case SWANG: {
                                        GL11.glTranslated(-0.1f, 0.15f, 0.0);
                                        this.transformFirstPersonItem(eqiupProgress, f1);
                                        GlStateManager.rotate(sw0ng * 30.0f, -sw0ng, -0.0f, 9.0f);
                                        GlStateManager.rotate(sw0ng * 40.0f, 1.0f, -sw0ng, -0.0f);
                                        this.doBlockTransformations();
                                        break block0;
                                    }
                                    case SWANK: {
                                        this.transformFirstPersonItem(0.0f, f1);
                                        this.doBlockTransformations();
                                        GlStateManager.rotate(sw0ng * 30.0f / 2.0f, -sw0ng, -0.0f, 9.0f);
                                        GlStateManager.rotate(sw0ng * 40.0f, 1.0f, -sw0ng / 2.0f, -0.0f);
                                        break block0;
                                    }
                                    case DEV: {
                                        GL11.glTranslated(this.mc.thePlayer.isSneaking() ? -0.034 : 0.03, this.mc.thePlayer.isSneaking() ? -0.045 : -0.055, 0.05);
                                        this.swongg(eqiupProgress, 0.0f);
                                        GL11.glTranslatef(0.1f, 0.4f, -0.1f);
                                        GL11.glRotated(-sw0ng * 42.0f, sw0ng / 2.0f, 0.0, 9.0);
                                        GL11.glRotated(-sw0ng * 50.0f, 0.8f, sw0ng / 2.0f, 0.0);
                                        this.sw0ng();
                                    }
                                }
                                break;
                            }
                            this.transformFirstPersonItem(f, 0.0f);
                            this.func_178103_d();
                            break;
                        }
                        case BOW: {
                            this.transformFirstPersonItem(f, f1);
                            this.func_178098_a(partialTicks, abstractclientplayer);
                        }
                    }
                } else {
                    this.angle = 0.0f;
                    this.func_178105_d(f1);
                    this.transformFirstPersonItem(f, f1);
                }
                this.renderItem(abstractclientplayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            } else {
                this.renderArmInFirstPerson(abstractclientplayer, f, f1);
            }
            GL11.glPopMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        }
    }

    public void renderOverlays(float partialTicks) {
        GlStateManager.disableAlpha();
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            BlockPos blockpos = new BlockPos(this.mc.thePlayer);
            EntityPlayerSP entityplayer = this.mc.thePlayer;
            for (int i = 0; i < 8; ++i) {
                double d0 = entityplayer.posX + (double)(((float)((i >> 0) % 2) - 0.5f) * entityplayer.width * 0.8f);
                double d1 = entityplayer.posY + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
                double d2 = entityplayer.posZ + (double)(((float)((i >> 2) % 2) - 0.5f) * entityplayer.width * 0.8f);
                BlockPos blockpos1 = new BlockPos(d0, d1 + (double)entityplayer.getEyeHeight(), d2);
                IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);
                if (!iblockstate1.getBlock().isVisuallyOpaque()) continue;
                iblockstate = iblockstate1;
                blockpos = blockpos1;
            }
            if (iblockstate.getBlock().getRenderType() != -1) {
                Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos)) {
                    this.func_178108_a(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }
        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, this.mc.thePlayer, Float.valueOf(partialTicks))) {
                this.renderWaterOverlayTexture(partialTicks);
            }
            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, this.mc.thePlayer, Float.valueOf(partialTicks))) {
                this.renderFireInFirstPerson(partialTicks);
            }
        }
        GlStateManager.enableAlpha();
    }

    private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.5f);
        GL11.glPushMatrix();
        float f6 = p_178108_2_.getMinU();
        float f7 = p_178108_2_.getMaxU();
        float f8 = p_178108_2_.getMinV();
        float f9 = p_178108_2_.getMaxV();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-1.0, -1.0, -0.5).tex(f7, f9).endVertex();
        worldrenderer.pos(1.0, -1.0, -0.5).tex(f6, f9).endVertex();
        worldrenderer.pos(1.0, 1.0, -0.5).tex(f6, f8).endVertex();
        worldrenderer.pos(-1.0, 1.0, -0.5).tex(f7, f8).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderWaterOverlayTexture(float p_78448_1_) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float f = this.mc.thePlayer.getBrightness(p_78448_1_);
            GL11.glColor4f(f, f, f, 0.5f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glPushMatrix();
            float f7 = -this.mc.thePlayer.rotationYaw / 64.0f;
            float f8 = this.mc.thePlayer.rotationPitch / 64.0f;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0, -1.0, -0.5).tex(4.0f + f7, 4.0f + f8).endVertex();
            worldrenderer.pos(1.0, -1.0, -0.5).tex(0.0f + f7, 4.0f + f8).endVertex();
            worldrenderer.pos(1.0, 1.0, -0.5).tex(0.0f + f7, 0.0f + f8).endVertex();
            worldrenderer.pos(-1.0, 1.0, -0.5).tex(4.0f + f7, 0.0f + f8).endVertex();
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
        }
    }

    private void renderFireInFirstPerson(float p_78442_1_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = 1.0f;
        for (int i = 0; i < 2; ++i) {
            GL11.glPushMatrix();
            TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            float f1 = textureatlassprite.getMinU();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMinV();
            float f4 = textureatlassprite.getMaxV();
            float f5 = (0.0f - f) / 2.0f;
            float f6 = f5 + f;
            float f7 = 0.0f - f / 2.0f;
            float f8 = f7 + f;
            float f9 = -0.5f;
            GL11.glTranslatef((float)(-(i * 2 - 1)) * 0.24f, -0.3f, 0.0f);
            GL11.glRotatef((float)(i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.setSprite(textureatlassprite);
            worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
            worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
            worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
            worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP entityplayer = this.mc.thePlayer;
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        boolean flag = false;
        if (this.itemToRender != null && itemstack != null) {
            if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
                boolean flag1;
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists() && !(flag1 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, this.itemToRender, itemstack, this.equippedItemSlot != entityplayer.inventory.currentItem))) {
                    this.itemToRender = itemstack;
                    this.equippedItemSlot = entityplayer.inventory.currentItem;
                    return;
                }
                flag = true;
            }
        } else {
            flag = this.itemToRender != null || itemstack != null;
        }
        float f2 = 0.4f;
        float f = flag ? 0.0f : 1.0f;
        float f1 = MathHelper.clamp_float(f - this.equippedProgress, -f2, f2);
        this.equippedProgress += f1;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = itemstack;
            this.equippedItemSlot = entityplayer.inventory.currentItem;
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(itemstack);
            }
        }
    }

    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }

    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
}

