/*
 * Decompiled with CFR 0.152.
 */
package javax.vecmath;

import java.io.Serializable;
import javax.vecmath.Tuple4d;
import javax.vecmath.VecMathUtil;

public abstract class Tuple4f
implements Serializable,
Cloneable {
    static final long serialVersionUID = 7068460319248845763L;
    public float x;
    public float y;
    public float z;
    public float w;

    public Tuple4f(float f2, float f3, float f4, float f5) {
        this.x = f2;
        this.y = f3;
        this.z = f4;
        this.w = f5;
    }

    public Tuple4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[3];
    }

    public Tuple4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public Tuple4f(Tuple4d tuple4d) {
        this.x = (float)tuple4d.x;
        this.y = (float)tuple4d.y;
        this.z = (float)tuple4d.z;
        this.w = (float)tuple4d.w;
    }

    public Tuple4f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public final void set(float f2, float f3, float f4, float f5) {
        this.x = f2;
        this.y = f3;
        this.z = f4;
        this.w = f5;
    }

    public final void set(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[3];
    }

    public final void set(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public final void set(Tuple4d tuple4d) {
        this.x = (float)tuple4d.x;
        this.y = (float)tuple4d.y;
        this.z = (float)tuple4d.z;
        this.w = (float)tuple4d.w;
    }

    public final void get(float[] fArray) {
        fArray[0] = this.x;
        fArray[1] = this.y;
        fArray[2] = this.z;
        fArray[3] = this.w;
    }

    public final void get(Tuple4f tuple4f) {
        tuple4f.x = this.x;
        tuple4f.y = this.y;
        tuple4f.z = this.z;
        tuple4f.w = this.w;
    }

    public final void add(Tuple4f tuple4f, Tuple4f tuple4f2) {
        this.x = tuple4f.x + tuple4f2.x;
        this.y = tuple4f.y + tuple4f2.y;
        this.z = tuple4f.z + tuple4f2.z;
        this.w = tuple4f.w + tuple4f2.w;
    }

    public final void add(Tuple4f tuple4f) {
        this.x += tuple4f.x;
        this.y += tuple4f.y;
        this.z += tuple4f.z;
        this.w += tuple4f.w;
    }

    public final void sub(Tuple4f tuple4f, Tuple4f tuple4f2) {
        this.x = tuple4f.x - tuple4f2.x;
        this.y = tuple4f.y - tuple4f2.y;
        this.z = tuple4f.z - tuple4f2.z;
        this.w = tuple4f.w - tuple4f2.w;
    }

    public final void sub(Tuple4f tuple4f) {
        this.x -= tuple4f.x;
        this.y -= tuple4f.y;
        this.z -= tuple4f.z;
        this.w -= tuple4f.w;
    }

    public final void negate(Tuple4f tuple4f) {
        this.x = -tuple4f.x;
        this.y = -tuple4f.y;
        this.z = -tuple4f.z;
        this.w = -tuple4f.w;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public final void scale(float f2, Tuple4f tuple4f) {
        this.x = f2 * tuple4f.x;
        this.y = f2 * tuple4f.y;
        this.z = f2 * tuple4f.z;
        this.w = f2 * tuple4f.w;
    }

    public final void scale(float f2) {
        this.x *= f2;
        this.y *= f2;
        this.z *= f2;
        this.w *= f2;
    }

    public final void scaleAdd(float f2, Tuple4f tuple4f, Tuple4f tuple4f2) {
        this.x = f2 * tuple4f.x + tuple4f2.x;
        this.y = f2 * tuple4f.y + tuple4f2.y;
        this.z = f2 * tuple4f.z + tuple4f2.z;
        this.w = f2 * tuple4f.w + tuple4f2.w;
    }

    public final void scaleAdd(float f2, Tuple4f tuple4f) {
        this.x = f2 * this.x + tuple4f.x;
        this.y = f2 * this.y + tuple4f.y;
        this.z = f2 * this.z + tuple4f.z;
        this.w = f2 * this.w + tuple4f.w;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public boolean equals(Tuple4f tuple4f) {
        try {
            return this.x == tuple4f.x && this.y == tuple4f.y && this.z == tuple4f.z && this.w == tuple4f.w;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public boolean equals(Object object) {
        try {
            Tuple4f tuple4f = (Tuple4f)object;
            return this.x == tuple4f.x && this.y == tuple4f.y && this.z == tuple4f.z && this.w == tuple4f.w;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public boolean epsilonEquals(Tuple4f tuple4f, float f2) {
        float f3 = this.x - tuple4f.x;
        if (Float.isNaN(f3)) {
            return false;
        }
        float f4 = f3 < 0.0f ? -f3 : f3;
        if (f4 > f2) {
            return false;
        }
        f3 = this.y - tuple4f.y;
        if (Float.isNaN(f3)) {
            return false;
        }
        float f5 = f3 < 0.0f ? -f3 : f3;
        if (f5 > f2) {
            return false;
        }
        f3 = this.z - tuple4f.z;
        if (Float.isNaN(f3)) {
            return false;
        }
        float f6 = f3 < 0.0f ? -f3 : f3;
        if (f6 > f2) {
            return false;
        }
        f3 = this.w - tuple4f.w;
        if (Float.isNaN(f3)) {
            return false;
        }
        float f7 = f3 < 0.0f ? -f3 : f3;
        return !(f7 > f2);
    }

    public int hashCode() {
        long l = 1L;
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.x);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.y);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.z);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.w);
        return (int)(l ^ l >> 32);
    }

    public final void clamp(float f2, float f3, Tuple4f tuple4f) {
        this.x = tuple4f.x > f3 ? f3 : (tuple4f.x < f2 ? f2 : tuple4f.x);
        this.y = tuple4f.y > f3 ? f3 : (tuple4f.y < f2 ? f2 : tuple4f.y);
        this.z = tuple4f.z > f3 ? f3 : (tuple4f.z < f2 ? f2 : tuple4f.z);
        this.w = tuple4f.w > f3 ? f3 : (tuple4f.w < f2 ? f2 : tuple4f.w);
    }

    public final void clampMin(float f2, Tuple4f tuple4f) {
        this.x = tuple4f.x < f2 ? f2 : tuple4f.x;
        this.y = tuple4f.y < f2 ? f2 : tuple4f.y;
        this.z = tuple4f.z < f2 ? f2 : tuple4f.z;
        this.w = tuple4f.w < f2 ? f2 : tuple4f.w;
    }

    public final void clampMax(float f2, Tuple4f tuple4f) {
        this.x = tuple4f.x > f2 ? f2 : tuple4f.x;
        this.y = tuple4f.y > f2 ? f2 : tuple4f.y;
        this.z = tuple4f.z > f2 ? f2 : tuple4f.z;
        this.w = tuple4f.w > f2 ? f2 : tuple4f.z;
    }

    public final void absolute(Tuple4f tuple4f) {
        this.x = Math.abs(tuple4f.x);
        this.y = Math.abs(tuple4f.y);
        this.z = Math.abs(tuple4f.z);
        this.w = Math.abs(tuple4f.w);
    }

    public final void clamp(float f2, float f3) {
        if (this.x > f3) {
            this.x = f3;
        } else if (this.x < f2) {
            this.x = f2;
        }
        if (this.y > f3) {
            this.y = f3;
        } else if (this.y < f2) {
            this.y = f2;
        }
        if (this.z > f3) {
            this.z = f3;
        } else if (this.z < f2) {
            this.z = f2;
        }
        if (this.w > f3) {
            this.w = f3;
        } else if (this.w < f2) {
            this.w = f2;
        }
    }

    public final void clampMin(float f2) {
        if (this.x < f2) {
            this.x = f2;
        }
        if (this.y < f2) {
            this.y = f2;
        }
        if (this.z < f2) {
            this.z = f2;
        }
        if (this.w < f2) {
            this.w = f2;
        }
    }

    public final void clampMax(float f2) {
        if (this.x > f2) {
            this.x = f2;
        }
        if (this.y > f2) {
            this.y = f2;
        }
        if (this.z > f2) {
            this.z = f2;
        }
        if (this.w > f2) {
            this.w = f2;
        }
    }

    public final void absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        this.w = Math.abs(this.w);
    }

    public void interpolate(Tuple4f tuple4f, Tuple4f tuple4f2, float f2) {
        this.x = (1.0f - f2) * tuple4f.x + f2 * tuple4f2.x;
        this.y = (1.0f - f2) * tuple4f.y + f2 * tuple4f2.y;
        this.z = (1.0f - f2) * tuple4f.z + f2 * tuple4f2.z;
        this.w = (1.0f - f2) * tuple4f.w + f2 * tuple4f2.w;
    }

    public void interpolate(Tuple4f tuple4f, float f2) {
        this.x = (1.0f - f2) * this.x + f2 * tuple4f.x;
        this.y = (1.0f - f2) * this.y + f2 * tuple4f.y;
        this.z = (1.0f - f2) * this.z + f2 * tuple4f.z;
        this.w = (1.0f - f2) * this.w + f2 * tuple4f.w;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f2) {
        this.x = f2;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f2) {
        this.y = f2;
    }

    public final float getZ() {
        return this.z;
    }

    public final void setZ(float f2) {
        this.z = f2;
    }

    public final float getW() {
        return this.w;
    }

    public final void setW(float f2) {
        this.w = f2;
    }
}

