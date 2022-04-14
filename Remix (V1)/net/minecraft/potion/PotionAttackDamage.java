package net.minecraft.potion;

import net.minecraft.util.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionAttackDamage extends Potion
{
    protected PotionAttackDamage(final int p_i45900_1_, final ResourceLocation p_i45900_2_, final boolean p_i45900_3_, final int p_i45900_4_) {
        super(p_i45900_1_, p_i45900_2_, p_i45900_3_, p_i45900_4_);
    }
    
    @Override
    public double func_111183_a(final int p_111183_1_, final AttributeModifier p_111183_2_) {
        return (this.id == Potion.weakness.id) ? (-0.5f * (p_111183_1_ + 1)) : (1.3 * (p_111183_1_ + 1));
    }
}
