/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import java.util.Collection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData
extends WorldSavedData {
    private static final Logger logger = LogManager.getLogger();
    private Scoreboard theScoreboard;
    private NBTTagCompound delayedInitNbt;

    public ScoreboardSaveData() {
        this("scoreboard");
    }

    public ScoreboardSaveData(String p_i2310_1_) {
        super(p_i2310_1_);
    }

    public void setScoreboard(Scoreboard p_setScoreboard_1_) {
        this.theScoreboard = p_setScoreboard_1_;
        if (this.delayedInitNbt != null) {
            this.readFromNBT(this.delayedInitNbt);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
        if (this.theScoreboard == null) {
            this.delayedInitNbt = p_readFromNBT_1_;
        } else {
            this.readObjectives(p_readFromNBT_1_.getTagList("Objectives", 10));
            this.readScores(p_readFromNBT_1_.getTagList("PlayerScores", 10));
            if (p_readFromNBT_1_.hasKey("DisplaySlots", 10)) {
                this.readDisplayConfig(p_readFromNBT_1_.getCompoundTag("DisplaySlots"));
            }
            if (p_readFromNBT_1_.hasKey("Teams", 9)) {
                this.readTeams(p_readFromNBT_1_.getTagList("Teams", 10));
            }
        }
    }

    protected void readTeams(NBTTagList p_readTeams_1_) {
        for (int lvt_2_1_ = 0; lvt_2_1_ < p_readTeams_1_.tagCount(); ++lvt_2_1_) {
            Team.EnumVisible lvt_7_2_;
            NBTTagCompound lvt_3_1_ = p_readTeams_1_.getCompoundTagAt(lvt_2_1_);
            String lvt_4_1_ = lvt_3_1_.getString("Name");
            if (lvt_4_1_.length() > 16) {
                lvt_4_1_ = lvt_4_1_.substring(0, 16);
            }
            ScorePlayerTeam lvt_5_1_ = this.theScoreboard.createTeam(lvt_4_1_);
            String lvt_6_1_ = lvt_3_1_.getString("DisplayName");
            if (lvt_6_1_.length() > 32) {
                lvt_6_1_ = lvt_6_1_.substring(0, 32);
            }
            lvt_5_1_.setTeamName(lvt_6_1_);
            if (lvt_3_1_.hasKey("TeamColor", 8)) {
                lvt_5_1_.setChatFormat(EnumChatFormatting.getValueByName(lvt_3_1_.getString("TeamColor")));
            }
            lvt_5_1_.setNamePrefix(lvt_3_1_.getString("Prefix"));
            lvt_5_1_.setNameSuffix(lvt_3_1_.getString("Suffix"));
            if (lvt_3_1_.hasKey("AllowFriendlyFire", 99)) {
                lvt_5_1_.setAllowFriendlyFire(lvt_3_1_.getBoolean("AllowFriendlyFire"));
            }
            if (lvt_3_1_.hasKey("SeeFriendlyInvisibles", 99)) {
                lvt_5_1_.setSeeFriendlyInvisiblesEnabled(lvt_3_1_.getBoolean("SeeFriendlyInvisibles"));
            }
            if (lvt_3_1_.hasKey("NameTagVisibility", 8) && (lvt_7_2_ = Team.EnumVisible.func_178824_a(lvt_3_1_.getString("NameTagVisibility"))) != null) {
                lvt_5_1_.setNameTagVisibility(lvt_7_2_);
            }
            if (lvt_3_1_.hasKey("DeathMessageVisibility", 8) && (lvt_7_2_ = Team.EnumVisible.func_178824_a(lvt_3_1_.getString("DeathMessageVisibility"))) != null) {
                lvt_5_1_.setDeathMessageVisibility(lvt_7_2_);
            }
            this.func_96502_a(lvt_5_1_, lvt_3_1_.getTagList("Players", 8));
        }
    }

    protected void func_96502_a(ScorePlayerTeam p_96502_1_, NBTTagList p_96502_2_) {
        for (int lvt_3_1_ = 0; lvt_3_1_ < p_96502_2_.tagCount(); ++lvt_3_1_) {
            this.theScoreboard.addPlayerToTeam(p_96502_2_.getStringTagAt(lvt_3_1_), p_96502_1_.getRegisteredName());
        }
    }

    protected void readDisplayConfig(NBTTagCompound p_readDisplayConfig_1_) {
        for (int lvt_2_1_ = 0; lvt_2_1_ < 19; ++lvt_2_1_) {
            if (!p_readDisplayConfig_1_.hasKey("slot_" + lvt_2_1_, 8)) continue;
            String lvt_3_1_ = p_readDisplayConfig_1_.getString("slot_" + lvt_2_1_);
            ScoreObjective lvt_4_1_ = this.theScoreboard.getObjective(lvt_3_1_);
            this.theScoreboard.setObjectiveInDisplaySlot(lvt_2_1_, lvt_4_1_);
        }
    }

    protected void readObjectives(NBTTagList p_readObjectives_1_) {
        for (int lvt_2_1_ = 0; lvt_2_1_ < p_readObjectives_1_.tagCount(); ++lvt_2_1_) {
            NBTTagCompound lvt_3_1_ = p_readObjectives_1_.getCompoundTagAt(lvt_2_1_);
            IScoreObjectiveCriteria lvt_4_1_ = IScoreObjectiveCriteria.INSTANCES.get(lvt_3_1_.getString("CriteriaName"));
            if (lvt_4_1_ == null) continue;
            String lvt_5_1_ = lvt_3_1_.getString("Name");
            if (lvt_5_1_.length() > 16) {
                lvt_5_1_ = lvt_5_1_.substring(0, 16);
            }
            ScoreObjective lvt_6_1_ = this.theScoreboard.addScoreObjective(lvt_5_1_, lvt_4_1_);
            lvt_6_1_.setDisplayName(lvt_3_1_.getString("DisplayName"));
            lvt_6_1_.setRenderType(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(lvt_3_1_.getString("RenderType")));
        }
    }

    protected void readScores(NBTTagList p_readScores_1_) {
        for (int lvt_2_1_ = 0; lvt_2_1_ < p_readScores_1_.tagCount(); ++lvt_2_1_) {
            NBTTagCompound lvt_3_1_ = p_readScores_1_.getCompoundTagAt(lvt_2_1_);
            ScoreObjective lvt_4_1_ = this.theScoreboard.getObjective(lvt_3_1_.getString("Objective"));
            String lvt_5_1_ = lvt_3_1_.getString("Name");
            if (lvt_5_1_.length() > 40) {
                lvt_5_1_ = lvt_5_1_.substring(0, 40);
            }
            Score lvt_6_1_ = this.theScoreboard.getValueFromObjective(lvt_5_1_, lvt_4_1_);
            lvt_6_1_.setScorePoints(lvt_3_1_.getInteger("Score"));
            if (!lvt_3_1_.hasKey("Locked")) continue;
            lvt_6_1_.setLocked(lvt_3_1_.getBoolean("Locked"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_writeToNBT_1_) {
        if (this.theScoreboard == null) {
            logger.warn("Tried to save scoreboard without having a scoreboard...");
        } else {
            p_writeToNBT_1_.setTag("Objectives", this.objectivesToNbt());
            p_writeToNBT_1_.setTag("PlayerScores", this.scoresToNbt());
            p_writeToNBT_1_.setTag("Teams", this.func_96496_a());
            this.func_96497_d(p_writeToNBT_1_);
        }
    }

    protected NBTTagList func_96496_a() {
        NBTTagList lvt_1_1_ = new NBTTagList();
        Collection<ScorePlayerTeam> lvt_2_1_ = this.theScoreboard.getTeams();
        for (ScorePlayerTeam lvt_4_1_ : lvt_2_1_) {
            NBTTagCompound lvt_5_1_ = new NBTTagCompound();
            lvt_5_1_.setString("Name", lvt_4_1_.getRegisteredName());
            lvt_5_1_.setString("DisplayName", lvt_4_1_.getTeamName());
            if (lvt_4_1_.getChatFormat().getColorIndex() >= 0) {
                lvt_5_1_.setString("TeamColor", lvt_4_1_.getChatFormat().getFriendlyName());
            }
            lvt_5_1_.setString("Prefix", lvt_4_1_.getColorPrefix());
            lvt_5_1_.setString("Suffix", lvt_4_1_.getColorSuffix());
            lvt_5_1_.setBoolean("AllowFriendlyFire", lvt_4_1_.getAllowFriendlyFire());
            lvt_5_1_.setBoolean("SeeFriendlyInvisibles", lvt_4_1_.getSeeFriendlyInvisiblesEnabled());
            lvt_5_1_.setString("NameTagVisibility", lvt_4_1_.getNameTagVisibility().field_178830_e);
            lvt_5_1_.setString("DeathMessageVisibility", lvt_4_1_.getDeathMessageVisibility().field_178830_e);
            NBTTagList lvt_6_1_ = new NBTTagList();
            for (String lvt_8_1_ : lvt_4_1_.getMembershipCollection()) {
                lvt_6_1_.appendTag(new NBTTagString(lvt_8_1_));
            }
            lvt_5_1_.setTag("Players", lvt_6_1_);
            lvt_1_1_.appendTag(lvt_5_1_);
        }
        return lvt_1_1_;
    }

    protected void func_96497_d(NBTTagCompound p_96497_1_) {
        NBTTagCompound lvt_2_1_ = new NBTTagCompound();
        boolean lvt_3_1_ = false;
        for (int lvt_4_1_ = 0; lvt_4_1_ < 19; ++lvt_4_1_) {
            ScoreObjective lvt_5_1_ = this.theScoreboard.getObjectiveInDisplaySlot(lvt_4_1_);
            if (lvt_5_1_ == null) continue;
            lvt_2_1_.setString("slot_" + lvt_4_1_, lvt_5_1_.getName());
            lvt_3_1_ = true;
        }
        if (lvt_3_1_) {
            p_96497_1_.setTag("DisplaySlots", lvt_2_1_);
        }
    }

    protected NBTTagList objectivesToNbt() {
        NBTTagList lvt_1_1_ = new NBTTagList();
        Collection<ScoreObjective> lvt_2_1_ = this.theScoreboard.getScoreObjectives();
        for (ScoreObjective lvt_4_1_ : lvt_2_1_) {
            if (lvt_4_1_.getCriteria() == null) continue;
            NBTTagCompound lvt_5_1_ = new NBTTagCompound();
            lvt_5_1_.setString("Name", lvt_4_1_.getName());
            lvt_5_1_.setString("CriteriaName", lvt_4_1_.getCriteria().getName());
            lvt_5_1_.setString("DisplayName", lvt_4_1_.getDisplayName());
            lvt_5_1_.setString("RenderType", lvt_4_1_.getRenderType().func_178796_a());
            lvt_1_1_.appendTag(lvt_5_1_);
        }
        return lvt_1_1_;
    }

    protected NBTTagList scoresToNbt() {
        NBTTagList lvt_1_1_ = new NBTTagList();
        Collection<Score> lvt_2_1_ = this.theScoreboard.getScores();
        for (Score lvt_4_1_ : lvt_2_1_) {
            if (lvt_4_1_.getObjective() == null) continue;
            NBTTagCompound lvt_5_1_ = new NBTTagCompound();
            lvt_5_1_.setString("Name", lvt_4_1_.getPlayerName());
            lvt_5_1_.setString("Objective", lvt_4_1_.getObjective().getName());
            lvt_5_1_.setInteger("Score", lvt_4_1_.getScorePoints());
            lvt_5_1_.setBoolean("Locked", lvt_4_1_.isLocked());
            lvt_1_1_.appendTag(lvt_5_1_);
        }
        return lvt_1_1_;
    }
}

