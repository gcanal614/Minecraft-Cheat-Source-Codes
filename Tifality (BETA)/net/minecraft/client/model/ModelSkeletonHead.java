/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSkeletonHead
extends ModelBase {
    public ModelRenderer skeletonHead;

    public ModelSkeletonHead() {
        this(0, 35, 64, 64);
    }

    public ModelSkeletonHead(int p_i1155_1_, int p_i1155_2_, int p_i1155_3_, int p_i1155_4_) {
        this.textureWidth = p_i1155_3_;
        this.textureHeight = p_i1155_4_;
        this.skeletonHead = new ModelRenderer(this, p_i1155_1_, p_i1155_2_);
        this.skeletonHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.skeletonHead.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_) {
        this.setRotationAngles(p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_, p_render_1_);
        this.skeletonHead.render(p_render_7_);
    }

    @Override
    public void setRotationAngles(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_) {
        super.setRotationAngles(p_setRotationAngles_1_, p_setRotationAngles_2_, p_setRotationAngles_3_, p_setRotationAngles_4_, p_setRotationAngles_5_, p_setRotationAngles_6_, p_setRotationAngles_7_);
        this.skeletonHead.rotateAngleY = p_setRotationAngles_4_ / 57.295776f;
        this.skeletonHead.rotateAngleX = p_setRotationAngles_5_ / 57.295776f;
    }
}

