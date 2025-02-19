package me.injusttice.neutron.utils.world;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class Vec3d
{
    public static Vec3d ZERO;
    public double xCoord;
    public double yCoord;
    public double zCoord;
    
    public Vec3d mul(float mul) {
        return new Vec3d(this.xCoord * mul, this.yCoord * mul, this.zCoord * mul);
    }
    
    public Vec3d(double x, double y2, double z) {
        if (x == -0.0) {
            x = 0.0;
        }
        if (y2 == -0.0) {
            y2 = 0.0;
        }
        if (z == -0.0) {
            z = 0.0;
        }
        this.xCoord = x;
        this.yCoord = y2;
        this.zCoord = z;
    }
    
    public Vec3d(Vec3i vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public Vec3d normalize() {
        double d0 = Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (d0 < 1.0E-4) ? Vec3d.ZERO : new Vec3d(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
    }
    
    public Vec3d subtract(Vec3d vec) {
        return this.subtract(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public Vec3d subtract(double x, double y2, double z) {
        return this.addVector(-x, -y2, -z);
    }
    
    public Vec3d add(Vec3d vec) {
        return this.addVector(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public Vec3d addVector(double x, double y2, double z) {
        return new Vec3d(this.xCoord + x, this.yCoord + y2, this.zCoord + z);
    }
    
    public double squareDistanceTo(Vec3d vec) {
        double d0 = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public double squareDistanceTo(double xIn, double yIn, double zIn) {
        double d0 = xIn - this.xCoord;
        double d2 = yIn - this.yCoord;
        double d3 = zIn - this.zCoord;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public double distanceTo(Vec3d paramVec3) {
        double d1 = paramVec3.xCoord - this.xCoord;
        double d2 = paramVec3.yCoord - this.yCoord;
        double d3 = paramVec3.zCoord - this.zCoord;
        return MathHelper.sqrt_double(d1 * d1 + d2 * d2 + d3 * d3);
    }
    
    public Vec3d scale(double p_186678_1_) {
        return new Vec3d(this.xCoord * p_186678_1_, this.yCoord * p_186678_1_, this.zCoord * p_186678_1_);
    }
    
    @Override
    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec3d)) {
            return false;
        }
        Vec3d vec3d = (Vec3d)p_equals_1_;
        return Double.compare(vec3d.xCoord, this.xCoord) == 0 && Double.compare(vec3d.yCoord, this.yCoord) == 0 && Double.compare(vec3d.zCoord, this.zCoord) == 0;
    }
    
    @Override
    public int hashCode() {
        long j = Double.doubleToLongBits(this.xCoord);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.yCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.zCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        return i;
    }
    
    @Override
    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }
    
    static {
        ZERO = new Vec3d(0.0, 0.0, 0.0);
    }
}
