package net.minecraft.client.renderer.entityrenderers;

import com.google.common.base.Predicate;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

public class EntityRenderer1 implements Predicate {
	
    public EntityRenderer field_90032_a;

    public EntityRenderer1(EntityRenderer p_i1243_1_)
    {
        this.field_90032_a = p_i1243_1_;
    }

    public boolean apply(Entity p_apply_1_)
    {
        return p_apply_1_.canBeCollidedWith();
    }

    public boolean apply(Object p_apply_1_)
    {
        return this.apply((Entity)p_apply_1_);
    }
}
