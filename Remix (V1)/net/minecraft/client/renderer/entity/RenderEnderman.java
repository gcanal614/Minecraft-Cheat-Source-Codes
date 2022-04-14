package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;

public class RenderEnderman extends RenderLiving
{
    private static final ResourceLocation endermanTextures;
    private ModelEnderman endermanModel;
    private Random rnd;
    
    public RenderEnderman(final RenderManager p_i46182_1_) {
        super(p_i46182_1_, new ModelEnderman(0.0f), 0.5f);
        this.rnd = new Random();
        this.endermanModel = (ModelEnderman)super.mainModel;
        this.addLayer(new LayerEndermanEyes(this));
        this.addLayer(new LayerHeldBlock(this));
    }
    
    public void doRender(final EntityEnderman p_76986_1_, double p_76986_2_, final double p_76986_4_, double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.endermanModel.isCarrying = (p_76986_1_.func_175489_ck().getBlock().getMaterial() != Material.air);
        this.endermanModel.isAttacking = p_76986_1_.isScreaming();
        if (p_76986_1_.isScreaming()) {
            final double var10 = 0.02;
            p_76986_2_ += this.rnd.nextGaussian() * var10;
            p_76986_6_ += this.rnd.nextGaussian() * var10;
        }
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation func_180573_a(final EntityEnderman p_180573_1_) {
        return RenderEnderman.endermanTextures;
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180573_a((EntityEnderman)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    }
}
