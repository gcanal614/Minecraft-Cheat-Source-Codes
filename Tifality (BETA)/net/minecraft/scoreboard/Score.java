/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class Score {
    public static final Comparator<Score> scoreComparator = new Comparator<Score>(){

        @Override
        public int compare(Score p_compare_1_, Score p_compare_2_) {
            if (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints()) {
                return 1;
            }
            return p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints() ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName());
        }
    };
    private final Scoreboard theScoreboard;
    private final ScoreObjective theScoreObjective;
    private final String scorePlayerName;
    private int scorePoints;
    private boolean locked;
    private boolean forceUpdate;

    public Score(Scoreboard p_i2309_1_, ScoreObjective p_i2309_2_, String p_i2309_3_) {
        this.theScoreboard = p_i2309_1_;
        this.theScoreObjective = p_i2309_2_;
        this.scorePlayerName = p_i2309_3_;
        this.forceUpdate = true;
    }

    public void increseScore(int p_increseScore_1_) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() + p_increseScore_1_);
    }

    public void decreaseScore(int p_decreaseScore_1_) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() - p_decreaseScore_1_);
    }

    public void func_96648_a() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.increseScore(1);
    }

    public int getScorePoints() {
        return this.scorePoints;
    }

    public void setScorePoints(int p_setScorePoints_1_) {
        int lvt_2_1_ = this.scorePoints;
        this.scorePoints = p_setScorePoints_1_;
        if (lvt_2_1_ != p_setScorePoints_1_ || this.forceUpdate) {
            this.forceUpdate = false;
            this.getScoreScoreboard().func_96536_a(this);
        }
    }

    public ScoreObjective getObjective() {
        return this.theScoreObjective;
    }

    public String getPlayerName() {
        return this.scorePlayerName;
    }

    public Scoreboard getScoreScoreboard() {
        return this.theScoreboard;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean p_setLocked_1_) {
        this.locked = p_setLocked_1_;
    }

    public void func_96651_a(List<EntityPlayer> p_96651_1_) {
        this.setScorePoints(this.theScoreObjective.getCriteria().setScore(p_96651_1_));
    }
}

