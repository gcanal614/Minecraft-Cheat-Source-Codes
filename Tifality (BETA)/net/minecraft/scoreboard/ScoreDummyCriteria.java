/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;

public class ScoreDummyCriteria
implements IScoreObjectiveCriteria {
    private final String dummyName;

    public ScoreDummyCriteria(String p_i2311_1_) {
        this.dummyName = p_i2311_1_;
        IScoreObjectiveCriteria.INSTANCES.put(p_i2311_1_, this);
    }

    @Override
    public String getName() {
        return this.dummyName;
    }

    @Override
    public int setScore(List<EntityPlayer> p_setScore_1_) {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
    }
}

