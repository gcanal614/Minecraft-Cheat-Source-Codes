/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  optifine.Config
 *  optifine.Reflector
 *  optifine.ReflectorConstructor
 *  optifine.ReflectorMethod
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 *  shadersmod.client.Shaders
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import de.fanta.Client;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorConstructor;
import optifine.ReflectorMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public abstract class RendererLivingEntity<T extends EntityLivingBase>
extends Render<T> {
    private static final Logger logger = LogManager.getLogger();
    private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
    protected ModelBase mainModel;
    protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
    protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
    protected boolean renderOutlines = false;
    private static final String __OBFID = "CL_00001012";
    public static float NAME_TAG_RANGE = 64.0f;
    public static float NAME_TAG_RANGE_SNEAK = 32.0f;

    static {
        int[] aint = field_177096_e.getTextureData();
        int i = 0;
        while (i < 256) {
            aint[i] = -1;
            ++i;
        }
        field_177096_e.updateDynamicTexture();
    }

    public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn);
        this.mainModel = modelBaseIn;
        this.shadowSize = shadowSizeIn;
    }

    public <V extends EntityLivingBase, U extends LayerRenderer> boolean addLayer(U layer) {
        return this.layerRenderers.add(layer);
    }

    protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
        return this.layerRenderers.remove(layer);
    }

    public ModelBase getMainModel() {
        return this.mainModel;
    }

    protected float interpolateRotation(float par1, float par2, float par3) {
        float f = par2 - par1;
        while (f < -180.0f) {
            f += 360.0f;
        }
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return par1 + par3 * f;
    }

    public void transformHeldFull3DItemLayer() {
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent((ReflectorConstructor)Reflector.RenderLivingEvent_Pre_Constructor, (Object[])new Object[]{entity, this, x, y, z})) {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
            this.mainModel.isRiding = ((Entity)entity).isRiding();
            if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
                this.mainModel.isRiding = ((Entity)entity).isRiding() && ((EntityLivingBase)entity).ridingEntity != null && Reflector.callBoolean((Object)((EntityLivingBase)entity).ridingEntity, (ReflectorMethod)Reflector.ForgeEntity_shouldRiderSit, (Object[])new Object[0]);
            }
            this.mainModel.isChild = ((EntityLivingBase)entity).isChild();
            try {
                if (Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
                    float f = this.interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset - 35.0f, ((EntityLivingBase)entity).renderYawOffset - 35.0f, partialTicks);
                    float f1 = this.interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
                    float f2 = f1 - f;
                    if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
                        f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                        f2 = f1 - f;
                        float f3 = MathHelper.wrapAngleTo180_float(f2);
                        if (f3 < -85.0f) {
                            f3 = -85.0f;
                        }
                        if (f3 >= 85.0f) {
                            f3 = 85.0f;
                        }
                        f = f1 - f3;
                        if (f3 * f3 > 2500.0f) {
                            f += f3 * 0.2f;
                        }
                    }
                    float f8 = entity == this.renderManager.livingPlayer ? ((EntityLivingBase)entity).prevRotationPitchHead + (((EntityLivingBase)entity).rotationPitchHead - ((EntityLivingBase)entity).prevRotationPitchHead) * partialTicks : ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
                    this.renderLivingAt(entity, x, y, z);
                    float f7 = this.handleRotationFloat(entity, partialTicks);
                    this.rotateCorpse(entity, f7, f, partialTicks);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(-1.0f, -1.0f, 1.0f);
                    this.preRenderCallback(entity, partialTicks);
                    float f4 = 0.0625f;
                    GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
                    float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
                    float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0f - partialTicks);
                    if (((EntityLivingBase)entity).isChild()) {
                        f6 *= 3.0f;
                    }
                    if (f5 > 1.0f) {
                        f5 = 1.0f;
                    }
                    GlStateManager.enableAlpha();
                    this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                    this.mainModel.setRotationAngles(f6, f5, f7, f2, f8, 0.0625f, (Entity)entity);
                    if (this.renderOutlines) {
                        boolean flag1 = this.setScoreTeamColor((EntityLivingBase)entity);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag1) {
                            this.unsetScoreTeamColor();
                        }
                    } else {
                        boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag) {
                            this.unsetBrightness();
                        }
                        GlStateManager.depthMask(true);
                        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                            this.renderLayers(entity, f6, f5, partialTicks, f7, f2, f8, 0.0625f);
                        }
                    }
                } else if (Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
                    float f = this.interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset - 35.0f, ((EntityLivingBase)entity).renderYawOffset - 35.0f, partialTicks);
                    float f1 = this.interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
                    float f2 = f1 - f;
                    if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
                        f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                        f2 = f1 - f;
                        float f3 = MathHelper.wrapAngleTo180_float(f2);
                        if (f3 < -85.0f) {
                            f3 = -85.0f;
                        }
                        if (f3 >= 85.0f) {
                            f3 = 85.0f;
                        }
                        f = f1 - f3;
                        if (f3 * f3 > 2500.0f) {
                            f += f3 * 0.2f;
                        }
                    }
                    float f8 = entity == this.renderManager.livingPlayer ? ((EntityLivingBase)entity).prevRotationPitchHead + (((EntityLivingBase)entity).rotationPitchHead - ((EntityLivingBase)entity).prevRotationPitchHead) * partialTicks : ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
                    this.renderLivingAt(entity, x, y, z);
                    float f7 = this.handleRotationFloat(entity, partialTicks);
                    this.rotateCorpse(entity, f7, f, partialTicks);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(-1.0f, -1.0f, 1.0f);
                    this.preRenderCallback(entity, partialTicks);
                    float f4 = 0.0625f;
                    GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
                    float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
                    float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0f - partialTicks);
                    if (((EntityLivingBase)entity).isChild()) {
                        f6 *= 3.0f;
                    }
                    if (f5 > 1.0f) {
                        f5 = 1.0f;
                    }
                    GlStateManager.enableAlpha();
                    this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                    this.mainModel.setRotationAngles(f6, f5, f7, f2, f8, 0.0625f, (Entity)entity);
                    if (this.renderOutlines) {
                        boolean flag1 = this.setScoreTeamColor((EntityLivingBase)entity);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag1) {
                            this.unsetScoreTeamColor();
                        }
                    } else {
                        boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag) {
                            this.unsetBrightness();
                        }
                        GlStateManager.depthMask(true);
                        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                            this.renderLayers(entity, f6, f5, partialTicks, f7, f2, f8, 0.0625f);
                        }
                    }
                } else if (Killaura.hasTarget()) {
                    float f = this.interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
                    float f1 = this.interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
                    float f2 = f1 - f;
                    if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
                        f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                        f2 = f1 - f;
                        float f3 = MathHelper.wrapAngleTo180_float(f2);
                        if (f3 < -85.0f) {
                            f3 = -85.0f;
                        }
                        if (f3 >= 85.0f) {
                            f3 = 85.0f;
                        }
                        f = f1 - f3;
                        if (f3 * f3 > 2500.0f) {
                            f += f3 * 0.2f;
                        }
                    }
                    float f8 = entity == this.renderManager.livingPlayer ? ((EntityLivingBase)entity).prevRotationPitchHead + (((EntityLivingBase)entity).rotationPitchHead - ((EntityLivingBase)entity).prevRotationPitchHead) * partialTicks : ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
                    this.renderLivingAt(entity, x, y, z);
                    float f7 = this.handleRotationFloat(entity, partialTicks);
                    this.rotateCorpse(entity, f7, f, partialTicks);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(-1.0f, -1.0f, 1.0f);
                    this.preRenderCallback(entity, partialTicks);
                    float f4 = 0.0625f;
                    GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
                    float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
                    float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0f - partialTicks);
                    if (((EntityLivingBase)entity).isChild()) {
                        f6 *= 3.0f;
                    }
                    if (f5 > 1.0f) {
                        f5 = 1.0f;
                    }
                    GlStateManager.enableAlpha();
                    this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                    this.mainModel.setRotationAngles(f6, f5, f7, f2, f8, 0.0625f, (Entity)entity);
                    if (this.renderOutlines) {
                        boolean flag1 = this.setScoreTeamColor((EntityLivingBase)entity);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag1) {
                            this.unsetScoreTeamColor();
                        }
                    } else {
                        boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag) {
                            this.unsetBrightness();
                        }
                        GlStateManager.depthMask(true);
                        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                            this.renderLayers(entity, f6, f5, partialTicks, f7, f2, f8, 0.0625f);
                        }
                    }
                } else {
                    float f = this.interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
                    float f1 = this.interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
                    float f2 = f1 - f;
                    if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
                        f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                        f2 = f1 - f;
                        float f3 = MathHelper.wrapAngleTo180_float(f2);
                        if (f3 < -85.0f) {
                            f3 = -85.0f;
                        }
                        if (f3 >= 85.0f) {
                            f3 = 85.0f;
                        }
                        f = f1 - f3;
                        if (f3 * f3 > 2500.0f) {
                            f += f3 * 0.2f;
                        }
                    }
                    float f8 = entity == this.renderManager.livingPlayer ? ((EntityLivingBase)entity).prevRotationPitchHead + (((EntityLivingBase)entity).rotationPitchHead - ((EntityLivingBase)entity).prevRotationPitchHead) * partialTicks : ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
                    this.renderLivingAt(entity, x, y, z);
                    float f7 = this.handleRotationFloat(entity, partialTicks);
                    this.rotateCorpse(entity, f7, f, partialTicks);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(-1.0f, -1.0f, 1.0f);
                    this.preRenderCallback(entity, partialTicks);
                    float f4 = 0.0625f;
                    GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
                    float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
                    float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0f - partialTicks);
                    if (((EntityLivingBase)entity).isChild()) {
                        f6 *= 3.0f;
                    }
                    if (f5 > 1.0f) {
                        f5 = 1.0f;
                    }
                    GlStateManager.enableAlpha();
                    this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                    this.mainModel.setRotationAngles(f6, f5, f7, f2, f8, 0.0625f, (Entity)entity);
                    if (this.renderOutlines) {
                        boolean flag1 = this.setScoreTeamColor((EntityLivingBase)entity);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag1) {
                            this.unsetScoreTeamColor();
                        }
                    } else {
                        boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                        this.renderModel(entity, f6, f5, f7, f2, f8, 0.0625f);
                        if (flag) {
                            this.unsetBrightness();
                        }
                        GlStateManager.depthMask(true);
                        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                            this.renderLayers(entity, f6, f5, partialTicks, f7, f2, f8, 0.0625f);
                        }
                    }
                }
                GlStateManager.disableRescaleNormal();
            }
            catch (Exception exception) {
                logger.error("Couldn't render entity", (Throwable)exception);
            }
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (!this.renderOutlines) {
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }
            if (!Reflector.RenderLivingEvent_Post_Constructor.exists() || !Reflector.postForgeBusEvent((ReflectorConstructor)Reflector.RenderLivingEvent_Post_Constructor, (Object[])new Object[]{entity, this, x, y, z})) {
                // empty if block
            }
        }
    }

    protected boolean setScoreTeamColor(EntityLivingBase entityLivingBaseIn) {
        String s;
        ScorePlayerTeam scoreplayerteam;
        int i = 0xFFFFFF;
        if (entityLivingBaseIn instanceof EntityPlayer && (scoreplayerteam = (ScorePlayerTeam)entityLivingBaseIn.getTeam()) != null && (s = BasicFontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix())).length() >= 2) {
            i = this.getBasicFontRendererFromRenderManager().getColorCode(s.charAt(1));
        }
        float c1 = (float)(((ColorValue)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"Color").getSetting()).color >> 16 & 0xFF) / 255.0f;
        float c2 = (float)(((ColorValue)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"Color").getSetting()).color >> 8 & 0xFF) / 255.0f;
        float c = (float)(((ColorValue)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"Color").getSetting()).color & 0xFF) / 255.0f;
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(c1, c2, c, 1.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void unsetScoreTeamColor() {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        boolean flag1;
        boolean flag = !((Entity)entitylivingbaseIn).isInvisible();
        boolean bl = flag1 = !flag && !((Entity)entitylivingbaseIn).isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
        if (flag || flag1) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }
            if (flag1) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569f);
            }
            if (Client.INSTANCE.moduleManager.getModule("ESP").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"ESPModes").getSetting()).curOption == "NewOutline" && Minecraft.getMinecraft().thePlayer != null && entitylivingbaseIn instanceof EntityPlayer) {
                Color colorForOutline = Color.green;
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                RenderUtil.renderOne();
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                RenderUtil.renderTwo();
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                RenderUtil.renderThree();
                RenderUtil.renderFour();
                RenderUtil.color(Objects.requireNonNull(colorForOutline));
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                RenderUtil.renderFive();
                RenderUtil.color(Color.WHITE);
            }
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            if (flag1) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1f);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }

    protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
        return this.setBrightness(entityLivingBaseIn, partialTicks, true);
    }

    protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
        boolean flag1;
        float f = ((Entity)entitylivingbaseIn).getBrightness(partialTicks);
        int i = this.getColorMultiplier(entitylivingbaseIn, f, partialTicks);
        boolean flag = (i >> 24 & 0xFF) > 0;
        boolean bl = flag1 = ((EntityLivingBase)entitylivingbaseIn).hurtTime > 0 || ((EntityLivingBase)entitylivingbaseIn).deathTime > 0;
        if (!flag && !flag1) {
            return false;
        }
        if (!flag && !combineTextures) {
            return false;
        }
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)OpenGlHelper.GL_INTERPOLATE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE2_RGB, (int)OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND2_RGB, (int)770);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        this.brightnessBuffer.position(0);
        if (flag1) {
            this.brightnessBuffer.put(1.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.3f);
            if (Config.isShaders()) {
                Shaders.setEntityColor((float)1.0f, (float)0.0f, (float)0.0f, (float)0.3f);
            }
        } else {
            float f1 = (float)(i >> 24 & 0xFF) / 255.0f;
            float f2 = (float)(i >> 16 & 0xFF) / 255.0f;
            float f3 = (float)(i >> 8 & 0xFF) / 255.0f;
            float f4 = (float)(i & 0xFF) / 255.0f;
            this.brightnessBuffer.put(f2);
            this.brightnessBuffer.put(f3);
            this.brightnessBuffer.put(f4);
            this.brightnessBuffer.put(1.0f - f1);
            if (Config.isShaders()) {
                Shaders.setEntityColor((float)f2, (float)f3, (float)f4, (float)(1.0f - f1));
            }
        }
        this.brightnessBuffer.flip();
        GL11.glTexEnv((int)8960, (int)8705, (FloatBuffer)this.brightnessBuffer);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(field_177096_e.getGlTextureId());
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void unsetBrightness() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_ALPHA, (int)OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_ALPHA, (int)770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)5890);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)5890);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_RGB, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND1_RGB, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_RGB, (int)5890);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE1_RGB, (int)OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_COMBINE_ALPHA, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_OPERAND0_ALPHA, (int)770);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.GL_SOURCE0_ALPHA, (int)5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (Config.isShaders()) {
            Shaders.setEntityColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        }
    }

    protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
        GlStateManager.rotate(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        if (((EntityLivingBase)bat).deathTime > 0) {
            float f = ((float)((EntityLivingBase)bat).deathTime + partialTicks - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.sqrt_float(f)) > 1.0f) {
                f = 1.0f;
            }
            GlStateManager.rotate(f * this.getDeathMaxRotation(bat), 0.0f, 0.0f, 1.0f);
        } else {
            String s = EnumChatFormatting.getTextWithoutFormattingCodes(((Entity)bat).getName());
            if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0f, ((EntityLivingBase)bat).height + 0.1f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    protected float getSwingProgress(T livingBase, float partialTickTime) {
        return ((EntityLivingBase)livingBase).getSwingProgress(partialTickTime);
    }

    protected float handleRotationFloat(T livingBase, float partialTicks) {
        return (float)((EntityLivingBase)livingBase).ticksExisted + partialTicks;
    }

    protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
        for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
            boolean flag = this.setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
            layerrenderer.doRenderLayer(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
            if (!flag) continue;
            this.unsetBrightness();
        }
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 90.0f;
    }

    protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
        return 0;
    }

    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {
    }

    @Override
    public void renderName(T entity, double x, double y, double z) {
        if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent((ReflectorConstructor)Reflector.RenderLivingEvent_Specials_Pre_Constructor, (Object[])new Object[]{entity, this, x, y, z})) {
            try {
                if (!Client.INSTANCE.moduleManager.getModule((String)"NameTags").state) {
                    if (this.canRenderName(entity)) {
                        float f;
                        double d0 = ((Entity)entity).getDistanceSqToEntity(this.renderManager.livingPlayer);
                        float f2 = f = ((Entity)entity).isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
                        if (d0 < (double)(f * f)) {
                            String s = ((Entity)entity).getDisplayName().getFormattedText();
                            float f1 = 0.02666667f;
                            GlStateManager.alphaFunc(516, 0.1f);
                            if (((Entity)entity).isSneaking()) {
                                BasicFontRenderer fontrenderer = this.getBasicFontRendererFromRenderManager();
                                GlStateManager.pushMatrix();
                                GlStateManager.translate((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5f - (((EntityLivingBase)entity).isChild() ? ((EntityLivingBase)entity).height / 2.0f : 0.0f), (float)z);
                                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                                GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                                GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
                                GlStateManager.translate(0.0f, 9.374999f, 0.0f);
                                GlStateManager.disableLighting();
                                GlStateManager.depthMask(false);
                                GlStateManager.enableBlend();
                                GlStateManager.disableTexture2D();
                                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                                int i = fontrenderer.getStringWidth(s) / 2;
                                Tessellator tessellator = Tessellator.getInstance();
                                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                                worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                                worldrenderer.pos(-i - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                                worldrenderer.pos(-i - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                                worldrenderer.pos(i + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                                worldrenderer.pos(i + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                                tessellator.draw();
                                GlStateManager.enableTexture2D();
                                GlStateManager.depthMask(true);
                                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0.0f, 0x20FFFFFF);
                                GlStateManager.enableLighting();
                                GlStateManager.disableBlend();
                                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                                GlStateManager.popMatrix();
                            } else {
                                this.renderOffsetLivingLabel(entity, x, y - (((EntityLivingBase)entity).isChild() ? (double)(((EntityLivingBase)entity).height / 2.0f) : 0.0), z, s, 0.02666667f, d0);
                            }
                        }
                    }
                    if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists() && !Reflector.postForgeBusEvent((ReflectorConstructor)Reflector.RenderLivingEvent_Specials_Post_Constructor, (Object[])new Object[]{entity, this, x, y, z})) {
                        // empty if block
                    }
                }
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
    }

    @Override
    protected boolean canRenderName(T entity) {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        if (entity instanceof EntityPlayer) {
            Team team = ((EntityLivingBase)entity).getTeam();
            Team team1 = entityplayersp.getTeam();
            if (team != null) {
                Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
                switch (team$enumvisible) {
                    case ALWAYS: {
                        return true;
                    }
                    case NEVER: {
                        return false;
                    }
                    case HIDE_FOR_OTHER_TEAMS: {
                        return team1 == null || team.isSameTeam(team1);
                    }
                    case HIDE_FOR_OWN_TEAM: {
                        return team1 == null || !team.isSameTeam(team1);
                    }
                }
                return true;
            }
        }
        return Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !((Entity)entity).isInvisibleToPlayer(entityplayersp) && ((EntityLivingBase)entity).riddenByEntity == null;
    }

    public void setRenderOutlines(boolean renderOutlinesIn) {
        this.renderOutlines = renderOutlinesIn;
    }
}

