/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class AxisAlignedBB {
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    private static final String __OBFID = "CL_00000607";

    public AxisAlignedBB(double x, double y, double z) {
        this.minX = this.maxX = x;
        this.minY = this.maxY = y;
        this.minZ = this.maxZ = z;
    }

    public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AxisAlignedBB(BlockPos p_i45554_1_, BlockPos p_i45554_2_) {
        this.minX = p_i45554_1_.getX();
        this.minY = p_i45554_1_.getY();
        this.minZ = p_i45554_1_.getZ();
        this.maxX = p_i45554_2_.getX();
        this.maxY = p_i45554_2_.getY();
        this.maxZ = p_i45554_2_.getZ();
    }

    public AxisAlignedBB offsetAndUpdate(double par1, double par3, double par5) {
        this.minX += par1;
        this.minY += par3;
        this.minZ += par5;
        this.maxX += par1;
        this.maxY += par3;
        this.maxZ += par5;
        return this;
    }

    public AxisAlignedBB addCoord(double x, double y, double z) {
        double var7 = this.minX;
        double var9 = this.minY;
        double var11 = this.minZ;
        double var13 = this.maxX;
        double var15 = this.maxY;
        double var17 = this.maxZ;
        if (x < 0.0) {
            var7 += x;
        } else if (x > 0.0) {
            var13 += x;
        }
        if (y < 0.0) {
            var9 += y;
        } else if (y > 0.0) {
            var15 += y;
        }
        if (z < 0.0) {
            var11 += z;
        } else if (z > 0.0) {
            var17 += z;
        }
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public AxisAlignedBB expand(double x, double y, double z) {
        double var7 = this.minX - x;
        double var9 = this.minY - y;
        double var11 = this.minZ - z;
        double var13 = this.maxX + x;
        double var15 = this.maxY + y;
        double var17 = this.maxZ + z;
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public AxisAlignedBB union(AxisAlignedBB other) {
        double var2 = Math.min(this.minX, other.minX);
        double var4 = Math.min(this.minY, other.minY);
        double var6 = Math.min(this.minZ, other.minZ);
        double var8 = Math.max(this.maxX, other.maxX);
        double var10 = Math.max(this.maxY, other.maxY);
        double var12 = Math.max(this.maxZ, other.maxZ);
        return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
    }

    public static AxisAlignedBB fromBounds(double p_178781_0_, double p_178781_2_, double p_178781_4_, double p_178781_6_, double p_178781_8_, double p_178781_10_) {
        double var12 = Math.min(p_178781_0_, p_178781_6_);
        double var14 = Math.min(p_178781_2_, p_178781_8_);
        double var16 = Math.min(p_178781_4_, p_178781_10_);
        double var18 = Math.max(p_178781_0_, p_178781_6_);
        double var20 = Math.max(p_178781_2_, p_178781_8_);
        double var22 = Math.max(p_178781_4_, p_178781_10_);
        return new AxisAlignedBB(var12, var14, var16, var18, var20, var22);
    }

    public AxisAlignedBB offset(double x, double y, double z) {
        return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public double calculateXOffset(AxisAlignedBB other, double p_72316_2_) {
        if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            double var4;
            if (p_72316_2_ > 0.0 && other.maxX <= this.minX) {
                double var42 = this.minX - other.maxX;
                if (var42 < p_72316_2_) {
                    p_72316_2_ = var42;
                }
            } else if (p_72316_2_ < 0.0 && other.minX >= this.maxX && (var4 = this.maxX - other.minX) > p_72316_2_) {
                p_72316_2_ = var4;
            }
            return p_72316_2_;
        }
        return p_72316_2_;
    }

    public double calculateYOffset(AxisAlignedBB other, double p_72323_2_) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            double var4;
            if (p_72323_2_ > 0.0 && other.maxY <= this.minY) {
                double var42 = this.minY - other.maxY;
                if (var42 < p_72323_2_) {
                    p_72323_2_ = var42;
                }
            } else if (p_72323_2_ < 0.0 && other.minY >= this.maxY && (var4 = this.maxY - other.minY) > p_72323_2_) {
                p_72323_2_ = var4;
            }
            return p_72323_2_;
        }
        return p_72323_2_;
    }

    public double calculateZOffset(AxisAlignedBB other, double p_72322_2_) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
            double var4;
            if (p_72322_2_ > 0.0 && other.maxZ <= this.minZ) {
                double var42 = this.minZ - other.maxZ;
                if (var42 < p_72322_2_) {
                    p_72322_2_ = var42;
                }
            } else if (p_72322_2_ < 0.0 && other.minZ >= this.maxZ && (var4 = this.maxZ - other.minZ) > p_72322_2_) {
                p_72322_2_ = var4;
            }
            return p_72322_2_;
        }
        return p_72322_2_;
    }

    public boolean intersectsWith(AxisAlignedBB other) {
        return other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ;
    }

    public boolean isVecInside(Vec3 vec) {
        return vec.xCoord > this.minX && vec.xCoord < this.maxX && vec.yCoord > this.minY && vec.yCoord < this.maxY && vec.zCoord > this.minZ && vec.zCoord < this.maxZ;
    }

    public double getAverageEdgeLength() {
        double var1 = this.maxX - this.minX;
        double var3 = this.maxY - this.minY;
        double var5 = this.maxZ - this.minZ;
        return (var1 + var3 + var5) / 3.0;
    }

    public AxisAlignedBB contract(double x, double y, double z) {
        double var7 = this.minX + x;
        double var9 = this.minY + y;
        double var11 = this.minZ + z;
        double var13 = this.maxX - x;
        double var15 = this.maxY - y;
        double var17 = this.maxZ - z;
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public MovingObjectPosition calculateIntercept(Vec3 p_72327_1_, Vec3 p_72327_2_) {
        Vec3 var3 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, this.minX);
        Vec3 var4 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, this.maxX);
        Vec3 var5 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, this.minY);
        Vec3 var6 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, this.maxY);
        Vec3 var7 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, this.minZ);
        Vec3 var8 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, this.maxZ);
        if (!this.isVecInYZ(var3)) {
            var3 = null;
        }
        if (!this.isVecInYZ(var4)) {
            var4 = null;
        }
        if (!this.isVecInXZ(var5)) {
            var5 = null;
        }
        if (!this.isVecInXZ(var6)) {
            var6 = null;
        }
        if (!this.isVecInXY(var7)) {
            var7 = null;
        }
        if (!this.isVecInXY(var8)) {
            var8 = null;
        }
        Vec3 var9 = null;
        if (var3 != null) {
            var9 = var3;
        }
        if (var4 != null && (var9 == null || p_72327_1_.squareDistanceTo(var4) < p_72327_1_.squareDistanceTo(var9))) {
            var9 = var4;
        }
        if (var5 != null && (var9 == null || p_72327_1_.squareDistanceTo(var5) < p_72327_1_.squareDistanceTo(var9))) {
            var9 = var5;
        }
        if (var6 != null && (var9 == null || p_72327_1_.squareDistanceTo(var6) < p_72327_1_.squareDistanceTo(var9))) {
            var9 = var6;
        }
        if (var7 != null && (var9 == null || p_72327_1_.squareDistanceTo(var7) < p_72327_1_.squareDistanceTo(var9))) {
            var9 = var7;
        }
        if (var8 != null && (var9 == null || p_72327_1_.squareDistanceTo(var8) < p_72327_1_.squareDistanceTo(var9))) {
            var9 = var8;
        }
        if (var9 == null) {
            return null;
        }
        EnumFacing var10 = null;
        var10 = var9 == var3 ? EnumFacing.WEST : (var9 == var4 ? EnumFacing.EAST : (var9 == var5 ? EnumFacing.DOWN : (var9 == var6 ? EnumFacing.UP : (var9 == var7 ? EnumFacing.NORTH : EnumFacing.SOUTH))));
        return new MovingObjectPosition(var9, var10);
    }

    private boolean isVecInYZ(Vec3 vec) {
        return vec != null && vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ;
    }

    private boolean isVecInXZ(Vec3 vec) {
        return vec != null && vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ;
    }

    private boolean isVecInXY(Vec3 vec) {
        return vec != null && vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY;
    }

    public String toString() {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean func_181656_b() {
        return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
    }
}

