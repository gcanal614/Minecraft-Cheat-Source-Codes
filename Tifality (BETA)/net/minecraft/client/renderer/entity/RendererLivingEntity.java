/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import club.tifality.Tifality;
import club.tifality.manager.event.impl.render.RenderNameTagEvent;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.module.impl.movement.Scaffold;
import club.tifality.module.impl.other.SilentView;
import club.tifality.module.impl.render.Chams;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.render.RenderingUtils;
import com.google.common.collect.Lists;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.MinecraftFontRenderer;
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
import net.minecraft.src.Config;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public abstract class RendererLivingEntity<T extends EntityLivingBase>
extends Render<T> {
    private static final Logger logger = LogManager.getLogger();
    private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
    public static float NAME_TAG_RANGE = 64.0f;
    public static float NAME_TAG_RANGE_SNEAK = 32.0f;
    public ModelBase mainModel;
    public EntityLivingBase renderEntity;
    public float renderLimbSwing;
    public float renderLimbSwingAmount;
    public float renderAgeInTicks;
    public float renderHeadYaw;
    public float renderHeadPitch;
    public float renderScaleFactor;
    public float renderPartialTicks;
    protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
    protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
    protected boolean renderOutlines = false;
    private boolean renderLayersPushMatrix;
    public static final boolean animateModelLiving;

    public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn);
        this.mainModel = modelBaseIn;
        this.shadowSize = shadowSizeIn;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        block34: {
            instance = Tifality.INSTANCE.getModuleManager().getModule(Chams.class);
            if (instance.isEnabled() && instance.getWallHake().get().booleanValue()) {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1000000.0f);
            }
            if (Reflector.RenderLivingEvent_Pre_Constructor.exists() && Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, new Object[]{entity, this, x, y, z})) break block34;
            if (RendererLivingEntity.animateModelLiving) {
                entity.limbSwingAmount = 1.0f;
            }
            GL11.glPushMatrix();
            GlStateManager.disableCull();
            this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
            this.mainModel.isRiding = entity.isRiding();
            if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
                this.mainModel.isRiding = entity.isRiding() != false && entity.ridingEntity != null && Reflector.callBoolean(entity.ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]) != false;
            }
            this.mainModel.isChild = entity.isChild();
            try {
                silentView = Tifality.INSTANCE.getModuleManager().getModule(SilentView.class);
                scaffold = Tifality.INSTANCE.getModuleManager().getModule(Scaffold.class);
                player = null;
                if (!(entity instanceof EntityPlayerSP)) ** GOTO lbl-1000
                player = (EntityPlayerSP)entity;
                if (player.currentEvent.isRotating()) {
                    v0 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v0 = showServerSideRotations = false;
                }
                if (showServerSideRotations && silentView.isEnabled()) {
                    event = player.currentEvent;
                    bodyYaw = yaw = RenderingUtils.interpolate(event.getPrevYaw(), event.getYaw(), partialTicks);
                    headYaw = yaw;
                } else {
                    bodyYaw = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
                    headYaw = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
                }
                yawDif = headYaw - bodyYaw;
                if (this.mainModel.isRiding && entity.ridingEntity instanceof EntityLivingBase) {
                    entitylivingbase = (EntityLivingBase)entity.ridingEntity;
                    bodyYaw = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                    yawDif = headYaw - bodyYaw;
                    f3 = MathHelper.wrapAngleTo180_float(yawDif);
                    if (f3 < -85.0f) {
                        f3 = -85.0f;
                    }
                    if (f3 >= 85.0f) {
                        f3 = 85.0f;
                    }
                    bodyYaw = headYaw - f3;
                    if (f3 * f3 > 2500.0f) {
                        bodyYaw += f3 * 0.2f;
                    }
                    yawDif = headYaw - bodyYaw;
                }
                if (showServerSideRotations && silentView.isEnabled()) {
                    event = player.currentEvent;
                    pitch = RenderingUtils.interpolate(event.getPrevPitch(), event.getPitch(), partialTicks);
                } else {
                    pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                }
                this.renderLivingAt(entity, x, y, z);
                f4 = 0.0625f;
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0f - partialTicks);
                f8 = this.handleRotationFloat(entity, partialTicks);
                f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
                f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
                f2 = f1 - f;
                flag = this.setDoRenderBrightness(entity, partialTicks);
                if (silentView.isEnabled() && SilentView.ghostSilentView.get().booleanValue() && (KillAura.getInstance().getTarget() != null || scaffold.isRotating()) && entity == Minecraft.getMinecraft().thePlayer) {
                    GlStateManager.pushMatrix();
                    this.rotateCorpse(entity, f7, this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks), partialTicks);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(-1.0f, -1.0f, 1.0f);
                    this.preRenderCallback(entity, partialTicks);
                    GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
                    if (entity.isChild()) {
                        f6 *= 3.0f;
                    }
                    if (f5 > 1.0f) {
                        f5 = 1.0f;
                    }
                    GlStateManager.enableAlpha();
                    this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                    this.mainModel.setRotationAngles(f6, f5, f7, f2, f7, f4, (Entity)entity);
                    if (CustomEntityModels.isActive()) {
                        this.renderEntity = entity;
                        this.renderLimbSwing = f6;
                        this.renderLimbSwingAmount = f5;
                        this.renderAgeInTicks = f8;
                        this.renderHeadYaw = f2;
                        this.renderHeadPitch = pitch;
                        this.renderScaleFactor = f4;
                        this.renderPartialTicks = partialTicks;
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 0.3f);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                    GlStateManager.alphaFunc(516, 0.003921569f);
                    this.renderModel(entity, f6, f5, f7, f2, f7, f4);
                    GlStateManager.disableBlend();
                    GlStateManager.alphaFunc(516, 0.1f);
                    GlStateManager.popMatrix();
                    GlStateManager.depthMask(true);
                    if (flag) {
                        this.unsetBrightness();
                    }
                    GlStateManager.depthMask(true);
                    if (!((EntityPlayer)entity).isSpectator()) {
                        this.renderLayers(entity, f6, f5, partialTicks, f7, f2, f7, f4);
                    }
                    GlStateManager.popMatrix();
                }
                this.rotateCorpse(entity, f8, bodyYaw, partialTicks);
                GlStateManager.enableRescaleNormal();
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                this.preRenderCallback(entity, partialTicks);
                GL11.glTranslatef(0.0f, -1.5078125f, 0.0f);
                if (entity.isChild()) {
                    f6 *= 3.0f;
                }
                if (f5 > 1.0f) {
                    f5 = 1.0f;
                }
                GlStateManager.enableAlpha();
                this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
                this.mainModel.setRotationAngles(f6, f5, f8, yawDif, pitch, 0.0625f, (Entity)entity);
                if (this.renderOutlines) {
                    this.renderModel(entity, f6, f5, f8, yawDif, pitch, 0.0625f);
                    flag1 = this.setScoreTeamColor(entity);
                    this.renderModel(entity, f6, f5, f8, yawDif, pitch, 0.0625f);
                    if (flag1) {
                        this.unsetScoreTeamColor();
                    }
                } else if (silentView.isEnabled() && SilentView.ghostSilentView.get().booleanValue() && (KillAura.getInstance().getTarget() != null || scaffold.isRotating()) && entity == Minecraft.getMinecraft().thePlayer) {
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glDisable(2929);
                    GL11.glDisable(3553);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(2896);
                    GL11.glPolygonMode(1032, 6914);
                    RenderingUtils.glColor(SilentView.color.get());
                    this.renderModel(entity, f6, f5, f8, yawDif, pitch, f4);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glColor3d(1.0, 1.0, 1.0);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                } else {
                    this.renderModel(entity, f6, f5, f8, yawDif, pitch, f4);
                    if (flag) {
                        this.unsetBrightness();
                    }
                    GlStateManager.depthMask(true);
                    if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                        this.renderLayers(entity, f6, f5, partialTicks, f8, yawDif, pitch, 0.0625f);
                    }
                }
                if (CustomEntityModels.isActive()) {
                    this.renderEntity = null;
                }
                GlStateManager.disableRescaleNormal();
            }
            catch (Exception exception) {
                RendererLivingEntity.logger.error("Couldn't render entity", (Throwable)exception);
            }
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableCull();
            GL11.glPopMatrix();
            if (!this.renderOutlines) {
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }
            if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
                Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, new Object[]{entity, this, x, y, z});
            }
        }
        if (instance.isEnabled() && instance.getWallHake().get().booleanValue()) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }

    protected void unsetScoreTeamColor() {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    public void renderName(T entity, double x, double y, double z) {
        if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, entity, this, x, y, z)) {
            if (this.canRenderName(entity)) {
                RenderNameTagEvent event = new RenderNameTagEvent((EntityLivingBase)entity);
                Tifality.getInstance().getEventBus().post(event);
                if (!event.isCancelled()) {
                    float f;
                    double d0 = ((Entity)entity).getDistanceSqToEntity(this.renderManager.livingPlayer);
                    float f2 = f = ((Entity)entity).isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
                    if (d0 < (double)(f * f)) {
                        String s = ((Entity)entity).getDisplayName().getFormattedText();
                        GlStateManager.alphaFunc(516, 0.1f);
                        if (((Entity)entity).isSneaking()) {
                            MinecraftFontRenderer fontrenderer = this.getFontRendererFromRenderManager();
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5f - (((EntityLivingBase)entity).isChild() ? ((EntityLivingBase)entity).height / 2.0f : 0.0f), (float)z);
                            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                            GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                            GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                            GL11.glScalef(-0.02666667f, -0.02666667f, 0.02666667f);
                            GL11.glTranslatef(0.0f, 9.374999f, 0.0f);
                            GlStateManager.disableLighting();
                            GlStateManager.depthMask(false);
                            GlStateManager.enableBlend();
                            GlStateManager.disableTexture2D();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                            int i = fontrenderer.getStringWidth(s) / 2;
                            Tessellator tessellator = Tessellator.getInstance();
                            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                            worldrenderer.pos(-i - 1, -1.0, 0.0).color4f(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                            worldrenderer.pos(-i - 1, 8.0, 0.0).color4f(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                            worldrenderer.pos(i + 1, 8.0, 0.0).color4f(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                            worldrenderer.pos(i + 1, -1.0, 0.0).color4f(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                            tessellator.draw();
                            GlStateManager.enableTexture2D();
                            GlStateManager.depthMask(true);
                            fontrenderer.drawString(s, (float)(-fontrenderer.getStringWidth(s)) / 2.0f, 0.0f, 0x20FFFFFF);
                            GlStateManager.enableLighting();
                            GlStateManager.disableBlend();
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            GL11.glPopMatrix();
                        } else {
                            this.renderOffsetLivingLabel(entity, x, y - (((EntityLivingBase)entity).isChild() ? (double)(((EntityLivingBase)entity).height / 2.0f) : 0.0), z, s, 0.02666667f, d0);
                        }
                    }
                }
            }
            if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists()) {
                Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, entity, this, x, y, z);
            }
        }
    }

    @Override
    protected boolean canRenderName(T entity) {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        if (entity instanceof EntityPlayer && entity != entityplayersp) {
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

    public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
        return this.layerRenderers.add(layer);
    }

    protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
        return this.layerRenderers.remove(layer);
    }

    public ModelBase getMainModel() {
        return this.mainModel;
    }

    protected float interpolateRotation(float par1, float par2, float par3) {
        float f;
        for (f = par2 - par1; f < -180.0f; f += 360.0f) {
        }
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return par1 + par3 * f;
    }

    public void transformHeldFull3DItemLayer() {
    }

    protected boolean setScoreTeamColor(T entityLivingBaseIn) {
        String s;
        ScorePlayerTeam scoreplayerteam;
        int i = 0xFFFFFF;
        if (entityLivingBaseIn instanceof EntityPlayer && (scoreplayerteam = (ScorePlayerTeam)((EntityLivingBase)entityLivingBaseIn).getTeam()) != null && (s = MinecraftFontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix())).length() >= 2) {
            i = this.getFontRendererFromRenderManager().getColorCode(s.charAt(1));
        }
        float f1 = (float)(i >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(i >> 8 & 0xFF) / 255.0f;
        float f = (float)(i & 0xFF) / 255.0f;
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glColor4f(f1, f2, f, 1.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        boolean flag = !((Entity)entitylivingbaseIn).isInvisible();
        boolean flag1 = !flag && !((Entity)entitylivingbaseIn).isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
        Hud hud = Tifality.INSTANCE.getModuleManager().getModule(Hud.class);
        Chams chams = Tifality.INSTANCE.getModuleManager().getModule(Chams.class);
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
            if (chams.isEnabled() && (chams.getModeValue().isSelected(Chams.Mode.COLOR) || chams.getModeValue().isSelected(Chams.Mode.CSGO))) {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                if (!chams.getModeValue().isSelected(Chams.Mode.CSGO)) {
                    GL11.glDisable(2896);
                }
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glEnable(10754);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                RenderingUtils.glColor(chams.getRainbow().get() != false ? RenderingUtils.getRainbowFromEntity(((Double)hud.rainbowSpeed.get()).intValue(), ((Double)hud.rainbowWidth.get()).intValue(), (int)System.currentTimeMillis() / 15, false, ((Double)chams.getRainbowAlphaValue().get()).floatValue()) : chams.getInvisibleColorValue().get());
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                RenderingUtils.glColor(chams.getRainbow().get() != false ? RenderingUtils.getRainbowFromEntity(((Double)hud.rainbowSpeed.get()).intValue(), ((Double)hud.rainbowWidth.get()).intValue(), (int)System.currentTimeMillis() / 15, false, ((Double)chams.getRainbowAlphaValue().get()).floatValue()) : chams.getVisibleColorValue().get());
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                GL11.glEnable(3042);
                if (!chams.getModeValue().isSelected(Chams.Mode.CSGO)) {
                    GL11.glEnable(2896);
                }
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
            } else {
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            }
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
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        this.brightnessBuffer.position(0);
        if (flag1) {
            this.brightnessBuffer.put(1.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.3f);
        } else {
            float f1 = (float)(i >> 24 & 0xFF) / 255.0f;
            float f2 = (float)(i >> 16 & 0xFF) / 255.0f;
            float f3 = (float)(i >> 8 & 0xFF) / 255.0f;
            float f4 = (float)(i & 0xFF) / 255.0f;
            this.brightnessBuffer.put(f2);
            this.brightnessBuffer.put(f3);
            this.brightnessBuffer.put(f4);
            this.brightnessBuffer.put(1.0f - f1);
        }
        this.brightnessBuffer.flip();
        GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(field_177096_e.getGlTextureId());
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void unsetBrightness() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (Config.isShaders()) {
            Shaders.setEntityColor(0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
        GL11.glTranslatef((float)x, (float)y, (float)z);
    }

    protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
        GL11.glRotatef(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        if (((EntityLivingBase)bat).deathTime > 0) {
            float f = ((float)((EntityLivingBase)bat).deathTime + partialTicks - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.sqrt_float(f)) > 1.0f) {
                f = 1.0f;
            }
            GL11.glRotatef(f * this.getDeathMaxRotation(bat), 0.0f, 0.0f, 1.0f);
        } else {
            String s = EnumChatFormatting.getTextWithoutFormattingCodes(((Entity)bat).getCommandSenderName());
            if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
                GL11.glTranslatef(0.0f, ((EntityLivingBase)bat).height + 0.1f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
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
            if (EmissiveTextures.isActive()) {
                EmissiveTextures.beginRender();
            }
            if (this.renderLayersPushMatrix) {
                GlStateManager.pushMatrix();
            }
            layerrenderer.doRenderLayer(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
            if (this.renderLayersPushMatrix) {
                GlStateManager.popMatrix();
            }
            if (EmissiveTextures.isActive()) {
                if (EmissiveTextures.hasEmissive()) {
                    this.renderLayersPushMatrix = true;
                    EmissiveTextures.beginRenderEmissive();
                    GlStateManager.pushMatrix();
                    layerrenderer.doRenderLayer(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
                    GlStateManager.popMatrix();
                    EmissiveTextures.endRenderEmissive();
                }
                EmissiveTextures.endRender();
            }
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

    public void setRenderOutlines(boolean renderOutlinesIn) {
        this.renderOutlines = renderOutlinesIn;
    }

    public List<LayerRenderer<T>> getLayerRenderers() {
        return this.layerRenderers;
    }

    static {
        int[] aint = field_177096_e.getTextureData();
        for (int i = 0; i < 256; ++i) {
            aint[i] = -1;
        }
        field_177096_e.updateDynamicTexture();
        animateModelLiving = Boolean.getBoolean("animate.model.living");
    }
}

