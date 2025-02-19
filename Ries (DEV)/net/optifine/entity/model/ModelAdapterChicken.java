/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterChicken
extends ModelAdapter {
    public ModelAdapterChicken() {
        super(EntityChicken.class, "chicken", 0.3f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelChicken();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelChicken)) {
            return null;
        }
        ModelChicken modelchicken = (ModelChicken)model;
        return modelPart.equals("head") ? modelchicken.head : (modelPart.equals("body") ? modelchicken.body : (modelPart.equals("right_leg") ? modelchicken.rightLeg : (modelPart.equals("left_leg") ? modelchicken.leftLeg : (modelPart.equals("right_wing") ? modelchicken.rightWing : (modelPart.equals("left_wing") ? modelchicken.leftWing : (modelPart.equals("bill") ? modelchicken.bill : (modelPart.equals("chin") ? modelchicken.chin : null)))))));
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        return new RenderChicken(rendermanager, modelBase, shadowSize);
    }
}

