/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkullRenderer
extends TileEntitySpecialRenderer<TileEntitySkull> {
    private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
    public static TileEntitySkullRenderer instance;
    private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
    private final ModelSkeletonHead humanoidHead = new ModelHumanoidHead();

    @Override
    public void renderTileEntityAt(TileEntitySkull p_renderTileEntityAt_1_, double p_renderTileEntityAt_2_, double p_renderTileEntityAt_4_, double p_renderTileEntityAt_6_, float p_renderTileEntityAt_8_, int p_renderTileEntityAt_9_) {
        EnumFacing lvt_10_1_ = EnumFacing.getFront(p_renderTileEntityAt_1_.getBlockMetadata() & 7);
        this.renderSkull((float)p_renderTileEntityAt_2_, (float)p_renderTileEntityAt_4_, (float)p_renderTileEntityAt_6_, lvt_10_1_, (float)(p_renderTileEntityAt_1_.getSkullRotation() * 360) / 16.0f, p_renderTileEntityAt_1_.getSkullType(), p_renderTileEntityAt_1_.getPlayerProfile(), p_renderTileEntityAt_9_);
    }

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher p_setRendererDispatcher_1_) {
        super.setRendererDispatcher(p_setRendererDispatcher_1_);
        instance = this;
    }

    public void renderSkull(float p_renderSkull_1_, float p_renderSkull_2_, float p_renderSkull_3_, EnumFacing p_renderSkull_4_, float p_renderSkull_5_, int p_renderSkull_6_, GameProfile p_renderSkull_7_, int p_renderSkull_8_) {
        ModelSkeletonHead lvt_9_1_ = this.skeletonHead;
        if (p_renderSkull_8_ >= 0) {
            this.bindTexture(DESTROY_STAGES[p_renderSkull_8_]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        } else {
            switch (p_renderSkull_6_) {
                default: {
                    this.bindTexture(SKELETON_TEXTURES);
                    break;
                }
                case 1: {
                    this.bindTexture(WITHER_SKELETON_TEXTURES);
                    break;
                }
                case 2: {
                    this.bindTexture(ZOMBIE_TEXTURES);
                    lvt_9_1_ = this.humanoidHead;
                    break;
                }
                case 3: {
                    lvt_9_1_ = this.humanoidHead;
                    ResourceLocation lvt_10_1_ = DefaultPlayerSkin.getDefaultSkinLegacy();
                    if (p_renderSkull_7_ != null) {
                        Minecraft lvt_11_1_ = Minecraft.getMinecraft();
                        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> lvt_12_1_ = lvt_11_1_.getSkinManager().loadSkinFromCache(p_renderSkull_7_);
                        if (lvt_12_1_.containsKey((Object)MinecraftProfileTexture.Type.SKIN)) {
                            lvt_10_1_ = lvt_11_1_.getSkinManager().loadSkin(lvt_12_1_.get((Object)MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                        } else {
                            UUID lvt_13_1_ = EntityPlayer.getUUID(p_renderSkull_7_);
                            lvt_10_1_ = DefaultPlayerSkin.getDefaultSkin(lvt_13_1_);
                        }
                    }
                    this.bindTexture(lvt_10_1_);
                    break;
                }
                case 4: {
                    this.bindTexture(CREEPER_TEXTURES);
                }
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        if (p_renderSkull_4_ != EnumFacing.UP) {
            switch (p_renderSkull_4_) {
                case NORTH: {
                    GlStateManager.translate(p_renderSkull_1_ + 0.5f, p_renderSkull_2_ + 0.25f, p_renderSkull_3_ + 0.74f);
                    break;
                }
                case SOUTH: {
                    GlStateManager.translate(p_renderSkull_1_ + 0.5f, p_renderSkull_2_ + 0.25f, p_renderSkull_3_ + 0.26f);
                    p_renderSkull_5_ = 180.0f;
                    break;
                }
                case WEST: {
                    GlStateManager.translate(p_renderSkull_1_ + 0.74f, p_renderSkull_2_ + 0.25f, p_renderSkull_3_ + 0.5f);
                    p_renderSkull_5_ = 270.0f;
                    break;
                }
                default: {
                    GlStateManager.translate(p_renderSkull_1_ + 0.26f, p_renderSkull_2_ + 0.25f, p_renderSkull_3_ + 0.5f);
                    p_renderSkull_5_ = 90.0f;
                    break;
                }
            }
        } else {
            GlStateManager.translate(p_renderSkull_1_ + 0.5f, p_renderSkull_2_, p_renderSkull_3_ + 0.5f);
        }
        float lvt_10_2_ = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        ((ModelBase)lvt_9_1_).render(null, 0.0f, 0.0f, 0.0f, p_renderSkull_5_, 0.0f, lvt_10_2_);
        GlStateManager.popMatrix();
        if (p_renderSkull_8_ >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}

