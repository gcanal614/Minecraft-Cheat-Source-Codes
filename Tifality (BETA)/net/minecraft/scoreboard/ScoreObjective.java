/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Scoreboard;

public class ScoreObjective {
    private final Scoreboard theScoreboard;
    private final String name;
    private final IScoreObjectiveCriteria objectiveCriteria;
    private IScoreObjectiveCriteria.EnumRenderType renderType;
    private String displayName;

    public ScoreObjective(Scoreboard p_i2307_1_, String p_i2307_2_, IScoreObjectiveCriteria p_i2307_3_) {
        this.theScoreboard = p_i2307_1_;
        this.name = p_i2307_2_;
        this.objectiveCriteria = p_i2307_3_;
        this.displayName = p_i2307_2_;
        this.renderType = p_i2307_3_.getRenderType();
    }

    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }

    public String getName() {
        return this.name;
    }

    public IScoreObjectiveCriteria getCriteria() {
        return this.objectiveCriteria;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String p_setDisplayName_1_) {
        this.displayName = p_setDisplayName_1_;
        this.theScoreboard.func_96532_b(this);
    }

    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(IScoreObjectiveCriteria.EnumRenderType p_setRenderType_1_) {
        this.renderType = p_setRenderType_1_;
        this.theScoreboard.func_96532_b(this);
    }
}

