package net.minecraft.world;

import net.minecraft.util.*;
import net.minecraft.block.*;

public class NextTickListEntry implements Comparable
{
    private static long nextTickEntryID;
    public final BlockPos field_180282_a;
    private final Block field_151352_g;
    public long scheduledTime;
    public int priority;
    private long tickEntryID;
    
    public NextTickListEntry(final BlockPos p_i45745_1_, final Block p_i45745_2_) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.field_180282_a = p_i45745_1_;
        this.field_151352_g = p_i45745_2_;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)p_equals_1_;
        return this.field_180282_a.equals(var2.field_180282_a) && Block.isEqualTo(this.field_151352_g, var2.field_151352_g);
    }
    
    @Override
    public int hashCode() {
        return this.field_180282_a.hashCode();
    }
    
    public NextTickListEntry setScheduledTime(final long p_77176_1_) {
        this.scheduledTime = p_77176_1_;
        return this;
    }
    
    public void setPriority(final int p_82753_1_) {
        this.priority = p_82753_1_;
    }
    
    public int compareTo(final NextTickListEntry p_compareTo_1_) {
        return (this.scheduledTime < p_compareTo_1_.scheduledTime) ? -1 : ((this.scheduledTime > p_compareTo_1_.scheduledTime) ? 1 : ((this.priority != p_compareTo_1_.priority) ? (this.priority - p_compareTo_1_.priority) : ((this.tickEntryID < p_compareTo_1_.tickEntryID) ? -1 : ((this.tickEntryID > p_compareTo_1_.tickEntryID) ? 1 : 0))));
    }
    
    @Override
    public String toString() {
        return Block.getIdFromBlock(this.field_151352_g) + ": " + this.field_180282_a + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
    }
    
    public Block func_151351_a() {
        return this.field_151352_g;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((NextTickListEntry)p_compareTo_1_);
    }
}
