package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;

public abstract class NodeProcessor
{
    protected IBlockAccess field_176169_a;
    protected IntHashMap field_176167_b;
    protected int field_176168_c;
    protected int field_176165_d;
    protected int field_176166_e;
    
    public NodeProcessor() {
        this.field_176167_b = new IntHashMap();
    }
    
    public void func_176162_a(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
        this.field_176169_a = p_176162_1_;
        this.field_176167_b.clearMap();
        this.field_176168_c = MathHelper.floor_float(p_176162_2_.width + 1.0f);
        this.field_176165_d = MathHelper.floor_float(p_176162_2_.height + 1.0f);
        this.field_176166_e = MathHelper.floor_float(p_176162_2_.width + 1.0f);
    }
    
    public void func_176163_a() {
    }
    
    protected PathPoint func_176159_a(final int p_176159_1_, final int p_176159_2_, final int p_176159_3_) {
        final int var4 = PathPoint.makeHash(p_176159_1_, p_176159_2_, p_176159_3_);
        PathPoint var5 = (PathPoint)this.field_176167_b.lookup(var4);
        if (var5 == null) {
            var5 = new PathPoint(p_176159_1_, p_176159_2_, p_176159_3_);
            this.field_176167_b.addKey(var4, var5);
        }
        return var5;
    }
    
    public abstract PathPoint func_176161_a(final Entity p0);
    
    public abstract PathPoint func_176160_a(final Entity p0, final double p1, final double p2, final double p3);
    
    public abstract int func_176164_a(final PathPoint[] p0, final Entity p1, final PathPoint p2, final PathPoint p3, final float p4);
}
