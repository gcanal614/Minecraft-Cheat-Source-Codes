/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.CustomColors
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import de.fanta.gui.font.BasicFontRenderer;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
    private final ModelSign model = new ModelSign();
    private static final String __OBFID = "CL_00000970";

    public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
        Block block = te.getBlockType();
        GlStateManager.pushMatrix();
        float f = 0.6666667f;
        if (block == Blocks.standing_sign) {
            GlStateManager.translate((float)x + 0.5f, (float)y + 0.75f * f, (float)z + 0.5f);
            float f2 = (float)(te.getBlockMetadata() * 360) / 16.0f;
            GlStateManager.rotate(-f2, 0.0f, 1.0f, 0.0f);
            this.model.signStick.showModel = true;
        } else {
            int k = te.getBlockMetadata();
            float f1 = 0.0f;
            if (k == 2) {
                f1 = 180.0f;
            }
            if (k == 4) {
                f1 = 90.0f;
            }
            if (k == 5) {
                f1 = -90.0f;
            }
            GlStateManager.translate((float)x + 0.5f, (float)y + 0.75f * f, (float)z + 0.5f);
            GlStateManager.rotate(-f1, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.model.signStick.showModel = false;
        }
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(SIGN_TEXTURE);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(f, -f, -f);
        this.model.renderSign();
        GlStateManager.popMatrix();
        BasicFontRenderer fontrenderer = this.getBasicFontRenderer();
        float f3 = 0.015625f * f;
        GlStateManager.translate(0.0f, 0.5f * f, 0.07f * f);
        GlStateManager.scale(f3, -f3, f3);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-1.0f * f3));
        GlStateManager.depthMask(false);
        int i = 0;
        if (Config.isCustomColors()) {
            i = CustomColors.getSignTextColor((int)i);
        }
        if (destroyStage < 0) {
            int j = 0;
            while (j < te.signText.length) {
                if (te.signText[j] != null) {
                    String s;
                    IChatComponent ichatcomponent = te.signText[j];
                    List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(ichatcomponent, 90, fontrenderer, false, true);
                    String string = s = list != null && list.size() > 0 ? list.get(0).getFormattedText() : "";
                    if (j == te.lineBeingEdited) {
                        s = "> " + s + " <";
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
                    } else {
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
                    }
                }
                ++j;
            }
        }
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        this.renderTileEntityAt((TileEntitySign)te, x, y, z, partialTicks, destroyStage);
    }
}

