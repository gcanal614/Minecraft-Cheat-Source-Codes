/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreDummyCriteria;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria
extends ScoreDummyCriteria {
    public ScoreHealthCriteria(String p_i2312_1_) {
        super(p_i2312_1_);
    }

    @Override
    public int setScore(List<EntityPlayer> p_setScore_1_) {
        float lvt_2_1_ = 0.0f;
        for (EntityPlayer lvt_4_1_ : p_setScore_1_) {
            lvt_2_1_ += lvt_4_1_.getHealth() + lvt_4_1_.getAbsorptionAmount();
        }
        if (p_setScore_1_.size() > 0) {
            lvt_2_1_ /= (float)p_setScore_1_.size();
        }
        return MathHelper.ceiling_float_int(lvt_2_1_);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
    }
}

