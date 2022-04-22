/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.DynamicLights
 *  optifine.Reflector
 *  optifine.ReflectorField
 *  optifine.ReflectorMethod
 *  org.lwjgl.opengl.GL11
 *  shadersmod.client.Shaders
 */
package net.minecraft.client.renderer;

import de.fanta.Client;
import de.fanta.setting.settings.DropdownBox;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import optifine.ReflectorField;
import optifine.ReflectorMethod;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public class ItemRenderer {
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    private final Minecraft mc;
    private ItemStack itemToRender;
    public int stage;
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;
    private int equippedItemSlot = -1;
    private static final String __OBFID = "CL_00000953";

    public ItemRenderer(Minecraft mcIn) {
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (heldStack != null) {
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();
            if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                if (!(!this.isBlockTranslucent(block) || Config.isShaders() && Shaders.renderItemKeepDepthMask)) {
                    GlStateManager.depthMask(false);
                }
            }
            this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
            if (this.isBlockTranslucent(block)) {
                GlStateManager.depthMask(true);
            }
            GlStateManager.popMatrix();
        }
    }

    private boolean isBlockTranslucent(Block blockIn) {
        return blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    private void func_178101_a(float angle, float p_178101_2_) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(p_178101_2_, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void func_178109_a(AbstractClientPlayer clientPlayer) {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + (double)clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight((Entity)this.mc.getRenderViewEntity(), (int)i);
        }
        float f = i & 0xFFFF;
        float f1 = i >> 16;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks) {
        float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
        float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1f, 0.0f, 1.0f, 0.0f);
    }

    private float func_178100_c(float p_178100_1_) {
        float f = 1.0f - p_178100_1_ / 45.0f + 0.1f;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5f + 0.5f;
        return f;
    }

    private void renderRightArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.25f, -0.85f, 0.75f);
        renderPlayerIn.renderRightArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderLeftArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.3f, -1.1f, 0.45f);
        renderPlayerIn.renderLeftArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        Render render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer renderplayer = (RenderPlayer)render;
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
        GlStateManager.translate(f, f1, f2);
        float f3 = this.func_178100_c(p_178097_2_);
        GlStateManager.translate(0.0f, 0.04f, -0.72f);
        GlStateManager.translate(0.0f, p_178097_3_ * -1.2f, 0.0f);
        GlStateManager.translate(0.0f, f3 * -0.5f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f3 * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        this.renderPlayerArms(clientPlayer);
        float f4 = MathHelper.sin(p_178097_4_ * p_178097_4_ * (float)Math.PI);
        float f5 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI);
        GlStateManager.rotate(f4 * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f5 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f5 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-1.0f, -1.0f, 0.0f);
        GlStateManager.scale(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
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

    private void func_178095_a(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_) {
        float f = -0.3f * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        float f1 = 0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI * 2.0f);
        float f2 = -0.4f * MathHelper.sin(p_178095_3_ * (float)Math.PI);
        GlStateManager.translate(f, f1, f2);
        GlStateManager.translate(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.translate(0.0f, p_178095_2_ * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float)Math.PI);
        float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        GlStateManager.rotate(f4 * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f3 * -20.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(5.6f, 0.0f, 0.0f);
        Render render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        GlStateManager.disableCull();
        RenderPlayer renderplayer = (RenderPlayer)render;
        renderplayer.renderRightArm(this.mc.thePlayer);
        GlStateManager.enableCull();
    }

    private void func_178105_d(float p_178105_1_) {
        float f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI);
        float f1 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI * 2.0f);
        float f2 = -0.2f * MathHelper.sin(p_178105_1_ * (float)Math.PI);
        GlStateManager.translate(f, f1, f2);
    }

    private void func_178104_a(AbstractClientPlayer clientPlayer, float p_178104_2_) {
        float f = (float)clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0f;
        float f1 = f / (float)this.itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float)Math.PI) * 0.1f);
        if (f1 >= 0.8f) {
            f2 = 0.0f;
        }
        GlStateManager.translate(0.0f, f2, 0.0f);
        float f3 = 1.0f - (float)Math.pow(f1, 27.0);
        GlStateManager.translate(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f);
        GlStateManager.rotate(f3 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f3 * 30.0f, 0.0f, 0.0f, 1.0f);
    }

    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        if (Client.INSTANCE.moduleManager.getModule("Killaura").isState() && this.mc.thePlayer.isSwingInProgress) {
            GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        } else {
            GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        }
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void func_178098_a(float p_178098_1_, AbstractClientPlayer clientPlayer) {
        GlStateManager.rotate(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);
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
            GlStateManager.translate(f4 * 0.0f, f4 * 0.01f, f4 * 0.0f);
        }
        GlStateManager.translate(f1 * 0.0f, f1 * 0.0f, f1 * 0.1f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f + f1 * 0.2f);
    }

    private void func_178103_d() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }

    public void renderItemInFirstPerson(float partialTicks) {
        float f = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        EntityPlayerSP entityplayersp = this.mc.thePlayer;
        float f1 = entityplayersp.getSwingProgress(partialTicks);
        float f2 = entityplayersp.prevRotationPitch + (entityplayersp.rotationPitch - entityplayersp.prevRotationPitch) * partialTicks;
        float f3 = entityplayersp.prevRotationYaw + (entityplayersp.rotationYaw - entityplayersp.prevRotationYaw) * partialTicks;
        this.func_178101_a(f2, f3);
        this.func_178109_a(entityplayersp);
        this.func_178110_a(entityplayersp, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            if (this.itemToRender.getItem() instanceof ItemMap) {
                this.renderItemMap(entityplayersp, f2, f, f1);
            } else if (entityplayersp.getItemInUseCount() > 0) {
                EnumAction enumaction = this.itemToRender.getItemUseAction();
                switch (enumaction) {
                    case NONE: {
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case EAT: 
                    case DRINK: {
                        this.func_178104_a(entityplayersp, partialTicks);
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case BLOCK: {
                        if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Fanta") {
                            this.transformFirstPersonItem(0.0f, f1);
                            this.func_178103_d();
                            GlStateManager.translate(-0.4f, 0.1f, -0.105f);
                            break;
                        }
                        if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Drop") {
                            this.transformFirstPersonItem(f, 0.0f);
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI + 5.0f);
                            GlStateManager.translate(0.0, 0.4, 0.0);
                            GlStateManager.rotate(-rot * 20.0f, 8020.05f, 20.0f, 0.0f);
                            this.func_178103_d();
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Violence") {
                            this.transformFirstPersonItem(0.0f, f1);
                            this.func_178103_d();
                            GlStateManager.translate(-0.2f, 0.2f, -0.5f);
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Flux") {
                            this.transformFirstPersonItem(f, 0.0f);
                            this.func_178103_d();
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            GlStateManager.translate(-0.5f, 0.4f, 0.0f);
                            GlStateManager.rotate(-rot * 50.0f, -8.0f, -0.0f, 9.0f);
                            GlStateManager.rotate(-rot * 70.0f, 1.0f, -0.4f, -0.0f);
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Exhibition") {
                            this.transformFirstPersonItem(f, f);
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            GlStateManager.translate(0.0, 0.3, 0.0);
                            GlStateManager.rotate(-rot * 36.0f, 6.09f, 4.0f, 0.0f);
                            GlStateManager.rotate(-rot * 25.0f, 2.09f, 0.0f, 15.0f);
                            this.func_178103_d();
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Astolfo") {
                            this.transformFirstPersonItem(f, 0.0f);
                            this.func_178103_d();
                            GlStateManager.translate(-0.2, 1.0, 0.5);
                            GlStateManager.rotate(90.0f, 3.0f, 1.0f, 0.0f);
                            float angle = (float)Math.toDegrees((double)System.currentTimeMillis() / 200.0 % (Math.PI * 2));
                            GlStateManager.rotate(angle, 1.0f, 0.0f, 1.0f);
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Centaurus") {
                            this.transformFirstPersonItem(-0.3f, 0.0f);
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            GlStateManager.rotate(-rot * 30.0f / 2.0f, -60.0f, -0.0f, 60.0f);
                            this.func_178103_d();
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "LB") {
                            this.transformFirstPersonItem(f1, f1);
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            GlStateManager.rotate(-rot * 0.0f, 0.0f, rot / 2.0f, -0.0f);
                            GlStateManager.translate(-0.2f, 0.2f, -0.0f);
                            GlStateManager.rotate(-rot * 0.0f / 2.0f, 2.0f, -0.0f, 60.0f);
                            this.func_178103_d();
                        } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Sigma4") {
                            float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                            this.transformFirstPersonItem(f, f);
                            GlStateManager.rotate(-rot * 10.0f, 5.0f, rot / 6.0f, -0.0f);
                            GlStateManager.rotate(-rot * 20.0f, 50.0f, rot / 6.0f, -0.0f);
                            this.func_178103_d();
                            GL11.glTranslated((double)1.0, (double)0.3, (double)0.5);
                            GL11.glTranslatef((float)-1.0f, (float)(this.mc.thePlayer.isSneaking() ? -0.1f : -0.2f), (float)0.2f);
                        } else {
                            this.transformFirstPersonItem(f, f1);
                            this.func_178103_d();
                            GlStateManager.translate(-0.2, -0.0, -0.0);
                        }
                        ++this.stage;
                        break;
                    }
                    case BOW: {
                        this.transformFirstPersonItem(f, 0.0f);
                        this.func_178098_a(partialTicks, entityplayersp);
                    }
                }
            } else {
                try {
                    if (!Client.INSTANCE.moduleManager.getModule("FakeBlock").isState()) {
                        if (Client.INSTANCE.moduleManager.getModule("Killaura").isState() && this.mc.thePlayer.isSwingInProgress && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                            this.func_178105_d(f1);
                            this.transformFirstPersonItem(f, f1);
                        } else {
                            this.func_178105_d(f1);
                            this.transformFirstPersonItem(f, f1);
                        }
                    } else if (this.mc.thePlayer.isSwingInProgress && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                        if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Fanta") {
                            this.transformFirstPersonItem(0.0f, f1);
                            this.func_178103_d();
                            GlStateManager.translate(-0.4f, 0.1f, -0.105f);
                        } else {
                            if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Drop") {
                                this.transformFirstPersonItem(f, 0.0f);
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI + 5.0f);
                                GlStateManager.translate(0.0, 0.4, 0.0);
                                GlStateManager.rotate(-rot * 20.0f, 8020.05f, 20.0f, 0.0f);
                                this.func_178103_d();
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Violence") {
                                this.transformFirstPersonItem(0.0f, f1);
                                this.func_178103_d();
                                GlStateManager.translate(-0.2f, 0.2f, -0.5f);
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Exhibition") {
                                this.transformFirstPersonItem(f, f);
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                                GlStateManager.translate(0.0, 0.3, 0.0);
                                GlStateManager.rotate(-rot * 36.0f, 6.09f, 4.0f, 0.0f);
                                GlStateManager.rotate(-rot * 25.0f, 2.09f, 0.0f, 15.0f);
                                this.func_178103_d();
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Flux") {
                                this.transformFirstPersonItem(f, 0.0f);
                                this.func_178103_d();
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                                GlStateManager.translate(-0.5f, 0.4f, 0.0f);
                                GlStateManager.rotate(-rot * 50.0f, -8.0f, -0.0f, 9.0f);
                                GlStateManager.rotate(-rot * 70.0f, 1.0f, -0.4f, -0.0f);
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Astolfo") {
                                this.transformFirstPersonItem(f, 0.0f);
                                this.func_178103_d();
                                GlStateManager.translate(-0.2, 1.0, 0.5);
                                GlStateManager.rotate(90.0f, 3.0f, 1.0f, 0.0f);
                                float angle = (float)Math.toDegrees((double)System.currentTimeMillis() / 200.0 % (Math.PI * 2));
                                GlStateManager.rotate(angle, 1.0f, 0.0f, 1.0f);
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Centaurus") {
                                this.transformFirstPersonItem(-0.3f, 0.0f);
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                                GlStateManager.rotate(-rot * 30.0f / 2.0f, -60.0f, -0.0f, 60.0f);
                                this.func_178103_d();
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "Sigma4") {
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                                this.transformFirstPersonItem(f, f);
                                GlStateManager.rotate(-rot * 10.0f, 5.0f, rot / 6.0f, -0.0f);
                                GlStateManager.rotate(-rot * 20.0f, 50.0f, rot / 6.0f, -0.0f);
                                this.func_178103_d();
                                GL11.glTranslated((double)1.0, (double)0.3, (double)0.5);
                                GL11.glTranslatef((float)-1.0f, (float)(this.mc.thePlayer.isSneaking() ? -0.1f : -0.2f), (float)0.2f);
                            } else if (Client.INSTANCE.moduleManager.getModule("BlockHit").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"BlockHit").getSetting((String)"AnimationMode").getSetting()).curOption == "LB") {
                                this.transformFirstPersonItem(f1, f1);
                                float rot = MathHelper.sin(MathHelper.sqrt_float(f1) * (float)Math.PI);
                                GlStateManager.rotate(-rot * 0.0f, 0.0f, rot / 2.0f, -0.0f);
                                GlStateManager.translate(-0.2f, 0.2f, -0.0f);
                                GlStateManager.rotate(-rot * 0.0f / 2.0f, 2.0f, -0.0f, 60.0f);
                                this.func_178103_d();
                            } else {
                                this.transformFirstPersonItem(f, f1);
                                this.func_178103_d();
                                GlStateManager.translate(-0.2, -0.0, -0.0);
                            }
                            ++this.stage;
                        }
                    } else {
                        this.func_178105_d(f1);
                        this.transformFirstPersonItem(f1, f1);
                    }
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
            this.renderItem(entityplayersp, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!entityplayersp.isInvisible()) {
            this.func_178095_a(entityplayersp, f, f1);
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float partialTicks) {
        GlStateManager.disableAlpha();
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            BlockPos blockpos = new BlockPos(this.mc.thePlayer);
            EntityPlayerSP entityplayersp = this.mc.thePlayer;
            int i = 0;
            while (i < 8) {
                double d0 = entityplayersp.posX + (double)(((float)((i >> 0) % 2) - 0.5f) * entityplayersp.width * 0.8f);
                double d1 = entityplayersp.posY + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
                double d2 = entityplayersp.posZ + (double)(((float)((i >> 2) % 2) - 0.5f) * entityplayersp.width * 0.8f);
                BlockPos blockpos1 = new BlockPos(d0, d1 + (double)entityplayersp.getEyeHeight(), d2);
                IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);
                if (iblockstate1.getBlock().isVisuallyOpaque()) {
                    iblockstate = iblockstate1;
                    blockpos = blockpos1;
                }
                ++i;
            }
            if (iblockstate.getBlock().getRenderType() != -1) {
                Object object = Reflector.getFieldValue((ReflectorField)Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
                if (!Reflector.callBoolean((ReflectorMethod)Reflector.ForgeEventFactory_renderBlockOverlay, (Object[])new Object[]{this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos})) {
                    this.func_178108_a(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }
        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean((ReflectorMethod)Reflector.ForgeEventFactory_renderWaterOverlay, (Object[])new Object[]{this.mc.thePlayer, Float.valueOf(partialTicks)})) {
                this.renderWaterOverlayTexture(partialTicks);
            }
            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean((ReflectorMethod)Reflector.ForgeEventFactory_renderFireOverlay, (Object[])new Object[]{this.mc.thePlayer, Float.valueOf(partialTicks)})) {
                this.renderFireInFirstPerson(partialTicks);
            }
        }
        GlStateManager.enableAlpha();
    }

    private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float f = 0.1f;
        GlStateManager.color(0.1f, 0.1f, 0.1f, 0.5f);
        GlStateManager.pushMatrix();
        float f1 = -1.0f;
        float f2 = 1.0f;
        float f3 = -1.0f;
        float f4 = 1.0f;
        float f5 = -0.5f;
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
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderWaterOverlayTexture(float p_78448_1_) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float f = this.mc.thePlayer.getBrightness(p_78448_1_);
            GlStateManager.color(f, f, f, 0.5f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            float f1 = 4.0f;
            float f2 = -1.0f;
            float f3 = 1.0f;
            float f4 = -1.0f;
            float f5 = 1.0f;
            float f6 = -0.5f;
            float f7 = -this.mc.thePlayer.rotationYaw / 64.0f;
            float f8 = this.mc.thePlayer.rotationPitch / 64.0f;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0, -1.0, -0.5).tex(4.0f + f7, 4.0f + f8).endVertex();
            worldrenderer.pos(1.0, -1.0, -0.5).tex(0.0f + f7, 4.0f + f8).endVertex();
            worldrenderer.pos(1.0, 1.0, -0.5).tex(0.0f + f7, 0.0f + f8).endVertex();
            worldrenderer.pos(-1.0, 1.0, -0.5).tex(4.0f + f7, 0.0f + f8).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
        }
    }

    private void renderFireInFirstPerson(float p_78442_1_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = 1.0f;
        int i = 0;
        while (i < 2) {
            GlStateManager.pushMatrix();
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
            GlStateManager.translate((float)(-(i * 2 - 1)) * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate((float)(i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
            worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
            worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
            worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            ++i;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP entityplayersp = this.mc.thePlayer;
        ItemStack itemstack = entityplayersp.inventory.getCurrentItem();
        boolean flag = false;
        if (this.itemToRender != null && itemstack != null) {
            if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
                boolean flag1;
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists() && !(flag1 = Reflector.callBoolean((Object)this.itemToRender.getItem(), (ReflectorMethod)Reflector.ForgeItem_shouldCauseReequipAnimation, (Object[])new Object[]{this.itemToRender, itemstack, this.equippedItemSlot != entityplayersp.inventory.currentItem}))) {
                    this.itemToRender = itemstack;
                    this.equippedItemSlot = entityplayersp.inventory.currentItem;
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
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain((ItemStack)itemstack);
            }
            this.itemToRender = itemstack;
            this.equippedItemSlot = entityplayersp.inventory.currentItem;
        }
    }

    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }

    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
}

