/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.optifine.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelSprite {
    private final ModelRenderer modelRenderer;
    private final int textureOffsetX;
    private final int textureOffsetY;
    private final float posX;
    private final float posY;
    private final float posZ;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final float sizeAdd;
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;

    public ModelSprite(ModelRenderer modelRenderer, int textureOffsetX, int textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
        this.modelRenderer = modelRenderer;
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.sizeAdd = sizeAdd;
        this.minU = (float)textureOffsetX / modelRenderer.textureWidth;
        this.minV = (float)textureOffsetY / modelRenderer.textureHeight;
        this.maxU = (float)(textureOffsetX + sizeX) / modelRenderer.textureWidth;
        this.maxV = (float)(textureOffsetY + sizeY) / modelRenderer.textureHeight;
    }

    public void render(Tessellator tessellator, float scale) {
        GlStateManager.translate(this.posX * scale, this.posY * scale, this.posZ * scale);
        float f = this.minU;
        float f1 = this.maxU;
        float f2 = this.minV;
        float f3 = this.maxV;
        if (this.modelRenderer.mirror) {
            f = this.maxU;
            f1 = this.minU;
        }
        if (this.modelRenderer.mirrorV) {
            f2 = this.maxV;
            f3 = this.minV;
        }
        ModelSprite.renderItemIn2D(tessellator, f, f2, f1, f3, this.sizeX, this.sizeY, scale * (float)this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
        GlStateManager.translate(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
    }

    public static void renderItemIn2D(Tessellator tess, float minU, float minV, float maxU, float maxV, int sizeX, int sizeY, float width, float texWidth, float texHeight) {
        if (width < 6.25E-4f) {
            width = 6.25E-4f;
        }
        float f = maxU - minU;
        float f1 = maxV - minV;
        double d0 = MathHelper.abs(f) * (texWidth / 16.0f);
        double d1 = MathHelper.abs(f1) * (texHeight / 16.0f);
        WorldRenderer worldrenderer = tess.getWorldRenderer();
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(0.0, d1, 0.0).func_181673_a(minU, maxV).func_181675_d();
        worldrenderer.func_181662_b(d0, d1, 0.0).func_181673_a(maxU, maxV).func_181675_d();
        worldrenderer.func_181662_b(d0, 0.0, 0.0).func_181673_a(maxU, minV).func_181675_d();
        worldrenderer.func_181662_b(0.0, 0.0, 0.0).func_181673_a(minU, minV).func_181675_d();
        tess.draw();
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(0.0, 0.0, width).func_181673_a(minU, minV).func_181675_d();
        worldrenderer.func_181662_b(d0, 0.0, width).func_181673_a(maxU, minV).func_181675_d();
        worldrenderer.func_181662_b(d0, d1, width).func_181673_a(maxU, maxV).func_181675_d();
        worldrenderer.func_181662_b(0.0, d1, width).func_181673_a(minU, maxV).func_181675_d();
        tess.draw();
        float f2 = 0.5f * f / (float)sizeX;
        float f3 = 0.5f * f1 / (float)sizeY;
        GL11.glNormal3f((float)-1.0f, (float)0.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        for (int i = 0; i < sizeX; ++i) {
            float f4 = (float)i / (float)sizeX;
            float f5 = minU + f * f4 + f2;
            worldrenderer.func_181662_b((double)f4 * d0, d1, width).func_181673_a(f5, maxV).func_181675_d();
            worldrenderer.func_181662_b((double)f4 * d0, d1, 0.0).func_181673_a(f5, maxV).func_181675_d();
            worldrenderer.func_181662_b((double)f4 * d0, 0.0, 0.0).func_181673_a(f5, minV).func_181675_d();
            worldrenderer.func_181662_b((double)f4 * d0, 0.0, width).func_181673_a(f5, minV).func_181675_d();
        }
        tess.draw();
        GL11.glNormal3f((float)1.0f, (float)0.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        for (int j = 0; j < sizeX; ++j) {
            float f7 = (float)j / (float)sizeX;
            float f10 = minU + f * f7 + f2;
            float f6 = f7 + 1.0f / (float)sizeX;
            worldrenderer.func_181662_b((double)f6 * d0, 0.0, width).func_181673_a(f10, minV).func_181675_d();
            worldrenderer.func_181662_b((double)f6 * d0, 0.0, 0.0).func_181673_a(f10, minV).func_181675_d();
            worldrenderer.func_181662_b((double)f6 * d0, d1, 0.0).func_181673_a(f10, maxV).func_181675_d();
            worldrenderer.func_181662_b((double)f6 * d0, d1, width).func_181673_a(f10, maxV).func_181675_d();
        }
        tess.draw();
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        for (int k = 0; k < sizeY; ++k) {
            float f8 = (float)k / (float)sizeY;
            float f11 = minV + f1 * f8 + f3;
            float f13 = f8 + 1.0f / (float)sizeY;
            worldrenderer.func_181662_b(0.0, (double)f13 * d1, width).func_181673_a(minU, f11).func_181675_d();
            worldrenderer.func_181662_b(d0, (double)f13 * d1, width).func_181673_a(maxU, f11).func_181675_d();
            worldrenderer.func_181662_b(d0, (double)f13 * d1, 0.0).func_181673_a(maxU, f11).func_181675_d();
            worldrenderer.func_181662_b(0.0, (double)f13 * d1, 0.0).func_181673_a(minU, f11).func_181675_d();
        }
        tess.draw();
        GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        for (int l = 0; l < sizeY; ++l) {
            float f9 = (float)l / (float)sizeY;
            float f12 = minV + f1 * f9 + f3;
            worldrenderer.func_181662_b(d0, (double)f9 * d1, width).func_181673_a(maxU, f12).func_181675_d();
            worldrenderer.func_181662_b(0.0, (double)f9 * d1, width).func_181673_a(minU, f12).func_181675_d();
            worldrenderer.func_181662_b(0.0, (double)f9 * d1, 0.0).func_181673_a(minU, f12).func_181675_d();
            worldrenderer.func_181662_b(d0, (double)f9 * d1, 0.0).func_181673_a(maxU, f12).func_181675_d();
        }
        tess.draw();
    }
}

