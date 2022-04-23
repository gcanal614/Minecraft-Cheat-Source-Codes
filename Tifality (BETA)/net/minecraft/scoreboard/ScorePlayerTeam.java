/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam
extends Team {
    private final Scoreboard theScoreboard;
    private final String registeredName;
    private final Set<String> membershipSet = Sets.newHashSet();
    private String teamNameSPT;
    private String namePrefixSPT = "";
    private String colorSuffix = "";
    private boolean allowFriendlyFire = true;
    private boolean canSeeFriendlyInvisibles = true;
    private Team.EnumVisible nameTagVisibility = Team.EnumVisible.ALWAYS;
    private Team.EnumVisible deathMessageVisibility = Team.EnumVisible.ALWAYS;
    private EnumChatFormatting chatFormat = EnumChatFormatting.RESET;

    public ScorePlayerTeam(Scoreboard p_i2308_1_, String p_i2308_2_) {
        this.theScoreboard = p_i2308_1_;
        this.registeredName = p_i2308_2_;
        this.teamNameSPT = p_i2308_2_;
    }

    @Override
    public String getRegisteredName() {
        return this.registeredName;
    }

    public String getTeamName() {
        return this.teamNameSPT;
    }

    public void setTeamName(String p_setTeamName_1_) {
        if (p_setTeamName_1_ == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = p_setTeamName_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    @Override
    public Collection<String> getMembershipCollection() {
        return this.membershipSet;
    }

    public String getColorPrefix() {
        return this.namePrefixSPT;
    }

    public void setNamePrefix(String p_setNamePrefix_1_) {
        if (p_setNamePrefix_1_ == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = p_setNamePrefix_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    public String getColorSuffix() {
        return this.colorSuffix;
    }

    public void setNameSuffix(String p_setNameSuffix_1_) {
        this.colorSuffix = p_setNameSuffix_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    @Override
    public String formatString(String p_formatString_1_) {
        return this.getColorPrefix() + p_formatString_1_ + this.getColorSuffix();
    }

    public static String formatPlayerName(Team p_formatPlayerName_0_, String p_formatPlayerName_1_) {
        return p_formatPlayerName_0_ == null ? p_formatPlayerName_1_ : p_formatPlayerName_0_.formatString(p_formatPlayerName_1_);
    }

    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean p_setAllowFriendlyFire_1_) {
        this.allowFriendlyFire = p_setAllowFriendlyFire_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    @Override
    public boolean getSeeFriendlyInvisiblesEnabled() {
        return this.canSeeFriendlyInvisibles;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean p_setSeeFriendlyInvisiblesEnabled_1_) {
        this.canSeeFriendlyInvisibles = p_setSeeFriendlyInvisiblesEnabled_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    @Override
    public Team.EnumVisible getNameTagVisibility() {
        return this.nameTagVisibility;
    }

    @Override
    public Team.EnumVisible getDeathMessageVisibility() {
        return this.deathMessageVisibility;
    }

    public void setNameTagVisibility(Team.EnumVisible p_setNameTagVisibility_1_) {
        this.nameTagVisibility = p_setNameTagVisibility_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    public void setDeathMessageVisibility(Team.EnumVisible p_setDeathMessageVisibility_1_) {
        this.deathMessageVisibility = p_setDeathMessageVisibility_1_;
        this.theScoreboard.sendTeamUpdate(this);
    }

    public int func_98299_i() {
        int lvt_1_1_ = 0;
        if (this.getAllowFriendlyFire()) {
            lvt_1_1_ |= 1;
        }
        if (this.getSeeFriendlyInvisiblesEnabled()) {
            lvt_1_1_ |= 2;
        }
        return lvt_1_1_;
    }

    public void func_98298_a(int p_98298_1_) {
        this.setAllowFriendlyFire((p_98298_1_ & 1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((p_98298_1_ & 2) > 0);
    }

    public void setChatFormat(EnumChatFormatting p_setChatFormat_1_) {
        this.chatFormat = p_setChatFormat_1_;
    }

    public EnumChatFormatting getChatFormat() {
        return this.chatFormat;
    }
}

