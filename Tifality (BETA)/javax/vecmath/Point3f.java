/*
 * Decompiled with CFR 0.152.
 */
package javax.vecmath;

import java.io.Serializable;
import javax.vecmath.Point3d;
import javax.vecmath.Point4f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;

public class Point3f
extends Tuple3f
implements Serializable {
    static final long serialVersionUID = -8689337816398030143L;

    public Point3f(float f2, float f3, float f4) {
        super(f2, f3, f4);
    }

    public Point3f(float[] fArray) {
        super(fArray);
    }

    public Point3f(Point3f point3f) {
        super(point3f);
    }

    public Point3f(Point3d point3d) {
        super(point3d);
    }

    public Point3f(Tuple3f tuple3f) {
        super(tuple3f);
    }

    public Point3f(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public Point3f() {
    }

    public final float distanceSquared(Point3f point3f) {
        float f2 = this.x - point3f.x;
        float f3 = this.y - point3f.y;
        float f4 = this.z - point3f.z;
        return f2 * f2 + f3 * f3 + f4 * f4;
    }

    public final float distance(Point3f point3f) {
        float f2 = this.x - point3f.x;
        float f3 = this.y - point3f.y;
        float f4 = this.z - point3f.z;
        return (float)Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
    }

    public final float distanceL1(Point3f point3f) {
        return Math.abs(this.x - point3f.x) + Math.abs(this.y - point3f.y) + Math.abs(this.z - point3f.z);
    }

    public final float distanceLinf(Point3f point3f) {
        float f2 = Math.max(Math.abs(this.x - point3f.x), Math.abs(this.y - point3f.y));
        return Math.max(f2, Math.abs(this.z - point3f.z));
    }

    public final void project(Point4f point4f) {
        float f2 = 1.0f / point4f.w;
        this.x = point4f.x * f2;
        this.y = point4f.y * f2;
        this.z = point4f.z * f2;
    }
}

