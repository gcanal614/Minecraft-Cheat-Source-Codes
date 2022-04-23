/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.MinecraftFontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer
extends TileEntitySpecialRenderer<TileEntitySign> {
    private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
    private final ModelSign model = new ModelSign();
    private static double textRenderDistanceSq = 4096.0;

    @Override
    public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
        Block block = te.getBlockType();
        GL11.glPushMatrix();
        float f = 0.6666667f;
        if (block == Blocks.standing_sign) {
            GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f * f, (float)z + 0.5f);
            float f1 = (float)(te.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef(-f1, 0.0f, 1.0f, 0.0f);
            this.model.signStick.showModel = true;
        } else {
            int k = te.getBlockMetadata();
            float f2 = 0.0f;
            if (k == 2) {
                f2 = 180.0f;
            }
            if (k == 4) {
                f2 = 90.0f;
            }
            if (k == 5) {
                f2 = -90.0f;
            }
            GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f * f, (float)z + 0.5f);
            GL11.glRotatef(-f2, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            this.model.signStick.showModel = false;
        }
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glScalef(4.0f, 2.0f, 1.0f);
            GL11.glTranslatef(0.0625f, 0.0625f, 0.0625f);
            GL11.glMatrixMode(5888);
        } else {
            this.bindTexture(SIGN_TEXTURE);
        }
        GlStateManager.enableRescaleNormal();
        GL11.glPushMatrix();
        GL11.glScalef(f, -f, -f);
        this.model.renderSign();
        GL11.glPopMatrix();
        if (TileEntitySignRenderer.isRenderText(te)) {
            MinecraftFontRenderer fontrenderer = this.getFontRenderer();
            float f3 = 0.015625f * f;
            GL11.glTranslatef(0.0f, 0.5f * f, 0.07f * f);
            GL11.glScalef(f3, -f3, f3);
            GL11.glNormal3f(0.0f, 0.0f, -1.0f * f3);
            GlStateManager.depthMask(false);
            int i = 0;
            if (Config.isCustomColors()) {
                i = CustomColors.getSignTextColor(i);
            }
            if (destroyStage < 0) {
                for (int j = 0; j < te.signText.length; ++j) {
                    String s;
                    if (te.signText[j] == null) continue;
                    IChatComponent ichatcomponent = te.signText[j];
                    List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(ichatcomponent, 90, fontrenderer, false, true);
                    String string = s = list != null && list.size() > 0 ? list.get(0).getFormattedText() : "";
                    if (j == te.lineBeingEdited) {
                        s = "> " + s + " <";
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
                        continue;
                    }
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
                }
            }
        }
        GlStateManager.depthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        if (destroyStage >= 0) {
            GL11.glMatrixMode(5890);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
    }

    private static boolean isRenderText(TileEntitySign p_isRenderText_0_) {
        if (Shaders.isShadowPass) {
            return false;
        }
        if (!Config.zoomMode && p_isRenderText_0_.lineBeingEdited < 0) {
            Entity entity = Config.getMinecraft().getRenderViewEntity();
            double d0 = p_isRenderText_0_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (d0 > textRenderDistanceSq) {
                return false;
            }
        }
        return true;
    }

    public static void updateTextRenderDistance() {
        Minecraft minecraft = Config.getMinecraft();
        double d0 = Config.limit(minecraft.gameSettings.fovSetting, 1.0f, 120.0f);
        double d1 = Math.max(1.5 * (double)minecraft.displayHeight / d0, 16.0);
        textRenderDistanceSq = d1 * d1;
    }
}

