/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ats
 */
package net.minecraft.realms;

public class RealmsLevelSummary
implements Comparable<RealmsLevelSummary> {
    private ats levelSummary;

    public RealmsLevelSummary(ats ats2) {
        this.levelSummary = ats2;
    }

    public int getGameMode() {
        return this.levelSummary.f().a();
    }

    public String getLevelId() {
        return this.levelSummary.a();
    }

    public boolean hasCheats() {
        return this.levelSummary.h();
    }

    public boolean isHardcore() {
        return this.levelSummary.g();
    }

    public boolean isRequiresConversion() {
        return this.levelSummary.d();
    }

    public String getLevelName() {
        return this.levelSummary.b();
    }

    public long getLastPlayed() {
        return this.levelSummary.e();
    }

    @Override
    public int compareTo(ats ats2) {
        return this.levelSummary.a(ats2);
    }

    public long getSizeOnDisk() {
        return this.levelSummary.c();
    }

    @Override
    public int compareTo(RealmsLevelSummary realmsLevelSummary) {
        if (this.levelSummary.e() < realmsLevelSummary.getLastPlayed()) {
            return 1;
        }
        if (this.levelSummary.e() > realmsLevelSummary.getLastPlayed()) {
            return -1;
        }
        return this.levelSummary.a().compareTo(realmsLevelSummary.getLevelId());
    }
}

