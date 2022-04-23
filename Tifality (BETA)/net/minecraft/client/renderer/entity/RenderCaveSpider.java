/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCaveSpider
extends RenderSpider<EntityCaveSpider> {
    private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");

    public RenderCaveSpider(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize *= 0.7f;
    }

    @Override
    protected void preRenderCallback(EntityCaveSpider entitylivingbaseIn, float partialTickTime) {
        GL11.glScalef(0.7f, 0.7f, 0.7f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCaveSpider entity) {
        return caveSpiderTextures;
    }
}

