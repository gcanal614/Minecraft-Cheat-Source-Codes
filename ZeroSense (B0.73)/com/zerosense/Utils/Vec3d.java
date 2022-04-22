package com.zerosense.Utils;

import net.minecraft.util.Vec3i;

public class Vec3d {
    public final double zCoord;

    public final double yCoord;

    public final double xCoord;

    public static final Vec3d ZERO = new Vec3d(0.0D, 0.0D, 0.0D);

    public Vec3d(Vec3i paramVec3i) {
        this(paramVec3i.getX(), paramVec3i.getY(), paramVec3i.getZ());
    }

    public Vec3d addVector(double paramDouble1, double paramDouble2, double paramDouble3) {
        return new Vec3d(this.xCoord + paramDouble1, this.yCoord + paramDouble2, this.zCoord + paramDouble3);
    }

    public String toString() {
        return String.valueOf((new StringBuilder("(")).append(this.xCoord).append(", ").append(this.yCoord).append(", ").append(this.zCoord).append(")"));
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof Vec3d))
            return false;
        Vec3d vec3d = (Vec3d)paramObject;
        return (Double.compare(vec3d.xCoord, this.xCoord) == 0 && Double.compare(vec3d.yCoord, this.yCoord) == 0 && Double.compare(vec3d.zCoord, this.zCoord) == 0);
    }

    public Vec3d add(Vec3d paramVec3d) {
        return addVector(paramVec3d.xCoord, paramVec3d.yCoord, paramVec3d.zCoord);
    }

    public Vec3d(double paramDouble1, double paramDouble2, double paramDouble3) {
        if (paramDouble1 == -0.0D)
            paramDouble1 = 0.0D;
        if (paramDouble2 == -0.0D)
            paramDouble2 = 0.0D;
        if (paramDouble3 == -0.0D)
            paramDouble3 = 0.0D;
        this.xCoord = paramDouble1;
        this.yCoord = paramDouble2;
        this.zCoord = paramDouble3;
    }

    public Vec3d subtract(Vec3d paramVec3d) {
        return subtract(paramVec3d.xCoord, paramVec3d.yCoord, paramVec3d.zCoord);
    }

    public double squareDistanceTo(Vec3d paramVec3d) {
        double d1 = paramVec3d.xCoord - this.xCoord;
        double d2 = paramVec3d.yCoord - this.yCoord;
        double d3 = paramVec3d.zCoord - this.zCoord;
        return d1 * d1 + d2 * d2 + d3 * d3;
    }

    public Vec3d subtract(double paramDouble1, double paramDouble2, double paramDouble3) {
        return addVector(-paramDouble1, -paramDouble2, -paramDouble3);
    }

    public Vec3d normalize() {
        double d = Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (d < 1.0E-4D) ? ZERO : new Vec3d(this.xCoord / d, this.yCoord / d, this.zCoord / d);
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.xCoord);
        int nu = (int)(l ^ l >>> 32L);
        l = Double.doubleToLongBits(this.yCoord);
        nu = 31 * nu + (int)(l ^ l >>> 32L);
        l = Double.doubleToLongBits(this.zCoord);
        return 31 * nu + (int)(l ^ l >>> 32L);
    }

    public double squareDistanceTo(double paramDouble1, double paramDouble2, double paramDouble3) {
        double d1 = paramDouble1 - this.xCoord;
        double d2 = paramDouble2 - this.yCoord;
        double d3 = paramDouble3 - this.zCoord;
        return d1 * d1 + d2 * d2 + d3 * d3;
    }

    public Vec3d scale(double paramDouble) {
        return new Vec3d(this.xCoord * paramDouble, this.yCoord * paramDouble, this.zCoord * paramDouble);
    }
}
