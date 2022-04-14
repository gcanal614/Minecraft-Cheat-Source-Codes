package javax.vecmath;

import java.io.*;

public class AxisAngle4d implements Serializable, Cloneable
{
    static final long serialVersionUID = 3644296204459140589L;
    public double x;
    public double y;
    public double z;
    public double angle;
    static final double EPS = 1.0E-12;
    
    public AxisAngle4d(final double x, final double y, final double z, final double angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
    
    public AxisAngle4d(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.angle = array[3];
    }
    
    public AxisAngle4d(final AxisAngle4d axisAngle4d) {
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        this.angle = axisAngle4d.angle;
    }
    
    public AxisAngle4d(final AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }
    
    public AxisAngle4d(final Vector3d vector3d, final double angle) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = angle;
    }
    
    public AxisAngle4d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 1.0;
        this.angle = 0.0;
    }
    
    public final void set(final double x, final double y, final double z, final double angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
    
    public final void set(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.angle = array[3];
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        this.angle = axisAngle4d.angle;
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }
    
    public final void set(final Vector3d vector3d, final double angle) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = angle;
    }
    
    public final void get(final double[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.angle;
    }
    
    public final void set(final Matrix4f matrix4f) {
        final Matrix3d matrix3d = new Matrix3d();
        matrix4f.get(matrix3d);
        this.x = (float)(matrix3d.m21 - matrix3d.m12);
        this.y = (float)(matrix3d.m02 - matrix3d.m20);
        this.z = (float)(matrix3d.m10 - matrix3d.m01);
        final double n = this.x * this.x + this.y * this.y + this.z * this.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            this.angle = (float)Math.atan2(0.5 * sqrt, 0.5 * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0));
            final double n2 = 1.0 / sqrt;
            this.x *= n2;
            this.y *= n2;
            this.z *= n2;
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    public final void set(final Matrix4d matrix4d) {
        final Matrix3d matrix3d = new Matrix3d();
        matrix4d.get(matrix3d);
        this.x = (float)(matrix3d.m21 - matrix3d.m12);
        this.y = (float)(matrix3d.m02 - matrix3d.m20);
        this.z = (float)(matrix3d.m10 - matrix3d.m01);
        final double n = this.x * this.x + this.y * this.y + this.z * this.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            this.angle = (float)Math.atan2(0.5 * sqrt, 0.5 * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0));
            final double n2 = 1.0 / sqrt;
            this.x *= n2;
            this.y *= n2;
            this.z *= n2;
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    public final void set(final Matrix3f matrix3f) {
        this.x = matrix3f.m21 - matrix3f.m12;
        this.y = matrix3f.m02 - matrix3f.m20;
        this.z = matrix3f.m10 - matrix3f.m01;
        final double n = this.x * this.x + this.y * this.y + this.z * this.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            this.angle = (float)Math.atan2(0.5 * sqrt, 0.5 * (matrix3f.m00 + matrix3f.m11 + matrix3f.m22 - 1.0));
            final double n2 = 1.0 / sqrt;
            this.x *= n2;
            this.y *= n2;
            this.z *= n2;
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    public final void set(final Matrix3d matrix3d) {
        this.x = (float)(matrix3d.m21 - matrix3d.m12);
        this.y = (float)(matrix3d.m02 - matrix3d.m20);
        this.z = (float)(matrix3d.m10 - matrix3d.m01);
        final double n = this.x * this.x + this.y * this.y + this.z * this.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            this.angle = (float)Math.atan2(0.5 * sqrt, 0.5 * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0));
            final double n2 = 1.0 / sqrt;
            this.x *= n2;
            this.y *= n2;
            this.z *= n2;
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    public final void set(final Quat4f quat4f) {
        final double n = quat4f.x * quat4f.x + quat4f.y * quat4f.y + quat4f.z * quat4f.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            final double n2 = 1.0 / sqrt;
            this.x = quat4f.x * n2;
            this.y = quat4f.y * n2;
            this.z = quat4f.z * n2;
            this.angle = 2.0 * Math.atan2(sqrt, quat4f.w);
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    public final void set(final Quat4d quat4d) {
        final double n = quat4d.x * quat4d.x + quat4d.y * quat4d.y + quat4d.z * quat4d.z;
        if (n > 1.0E-12) {
            final double sqrt = Math.sqrt(n);
            final double n2 = 1.0 / sqrt;
            this.x = quat4d.x * n2;
            this.y = quat4d.y * n2;
            this.z = quat4d.z * n2;
            this.angle = 2.0 * Math.atan2(sqrt, quat4d.w);
        }
        else {
            this.x = 0.0;
            this.y = 1.0;
            this.z = 0.0;
            this.angle = 0.0;
        }
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
    }
    
    public boolean equals(final AxisAngle4d axisAngle4d) {
        try {
            return this.x == axisAngle4d.x && this.y == axisAngle4d.y && this.z == axisAngle4d.z && this.angle == axisAngle4d.angle;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        try {
            final AxisAngle4d axisAngle4d = (AxisAngle4d)o;
            return this.x == axisAngle4d.x && this.y == axisAngle4d.y && this.z == axisAngle4d.z && this.angle == axisAngle4d.angle;
        }
        catch (NullPointerException ex) {
            return false;
        }
        catch (ClassCastException ex2) {
            return false;
        }
    }
    
    public boolean epsilonEquals(final AxisAngle4d axisAngle4d, final double n) {
        final double n2 = this.x - axisAngle4d.x;
        if (((n2 < 0.0) ? (-n2) : n2) > n) {
            return false;
        }
        final double n3 = this.y - axisAngle4d.y;
        if (((n3 < 0.0) ? (-n3) : n3) > n) {
            return false;
        }
        final double n4 = this.z - axisAngle4d.z;
        if (((n4 < 0.0) ? (-n4) : n4) > n) {
            return false;
        }
        final double n5 = this.angle - axisAngle4d.angle;
        return ((n5 < 0.0) ? (-n5) : n5) <= n;
    }
    
    @Override
    public int hashCode() {
        final long n = 31L * (31L * (31L * (31L * 1L + VecMathUtil.doubleToLongBits(this.x)) + VecMathUtil.doubleToLongBits(this.y)) + VecMathUtil.doubleToLongBits(this.z)) + VecMathUtil.doubleToLongBits(this.angle);
        return (int)(n ^ n >> 32);
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    public final double getAngle() {
        return this.angle;
    }
    
    public final void setAngle(final double angle) {
        this.angle = angle;
    }
    
    public double getX() {
        return this.x;
    }
    
    public final void setX(final double x) {
        this.x = x;
    }
    
    public final double getY() {
        return this.y;
    }
    
    public final void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public final void setZ(final double z) {
        this.z = z;
    }
}
