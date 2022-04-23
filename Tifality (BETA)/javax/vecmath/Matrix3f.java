/*
 * Decompiled with CFR 0.152.
 */
package javax.vecmath;

import java.io.Serializable;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Quat4f;
import javax.vecmath.SingularMatrixException;
import javax.vecmath.Tuple3f;
import javax.vecmath.VecMathI18N;
import javax.vecmath.VecMathUtil;
import javax.vecmath.Vector3f;

public class Matrix3f
implements Serializable,
Cloneable {
    static final long serialVersionUID = 329697160112089834L;
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;
    private static final double EPS = 1.0E-8;

    public Matrix3f(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.m00 = f2;
        this.m01 = f3;
        this.m02 = f4;
        this.m10 = f5;
        this.m11 = f6;
        this.m12 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
    }

    public Matrix3f(float[] fArray) {
        this.m00 = fArray[0];
        this.m01 = fArray[1];
        this.m02 = fArray[2];
        this.m10 = fArray[3];
        this.m11 = fArray[4];
        this.m12 = fArray[5];
        this.m20 = fArray[6];
        this.m21 = fArray[7];
        this.m22 = fArray[8];
    }

    public Matrix3f(Matrix3d matrix3d) {
        this.m00 = (float)matrix3d.m00;
        this.m01 = (float)matrix3d.m01;
        this.m02 = (float)matrix3d.m02;
        this.m10 = (float)matrix3d.m10;
        this.m11 = (float)matrix3d.m11;
        this.m12 = (float)matrix3d.m12;
        this.m20 = (float)matrix3d.m20;
        this.m21 = (float)matrix3d.m21;
        this.m22 = (float)matrix3d.m22;
    }

    public Matrix3f(Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }

    public Matrix3f() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
    }

    public String toString() {
        return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
    }

    public final void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }

    public final void setScale(float f2) {
        double[] dArray = new double[9];
        double[] dArray2 = new double[3];
        this.getScaleRotate(dArray2, dArray);
        this.m00 = (float)(dArray[0] * (double)f2);
        this.m01 = (float)(dArray[1] * (double)f2);
        this.m02 = (float)(dArray[2] * (double)f2);
        this.m10 = (float)(dArray[3] * (double)f2);
        this.m11 = (float)(dArray[4] * (double)f2);
        this.m12 = (float)(dArray[5] * (double)f2);
        this.m20 = (float)(dArray[6] * (double)f2);
        this.m21 = (float)(dArray[7] * (double)f2);
        this.m22 = (float)(dArray[8] * (double)f2);
    }

    public final void setElement(int n, int n2, float f2) {
        block0 : switch (n) {
            case 0: {
                switch (n2) {
                    case 0: {
                        this.m00 = f2;
                        break block0;
                    }
                    case 1: {
                        this.m01 = f2;
                        break block0;
                    }
                    case 2: {
                        this.m02 = f2;
                        break block0;
                    }
                }
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
            }
            case 1: {
                switch (n2) {
                    case 0: {
                        this.m10 = f2;
                        break block0;
                    }
                    case 1: {
                        this.m11 = f2;
                        break block0;
                    }
                    case 2: {
                        this.m12 = f2;
                        break block0;
                    }
                }
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
            }
            case 2: {
                switch (n2) {
                    case 0: {
                        this.m20 = f2;
                        break block0;
                    }
                    case 1: {
                        this.m21 = f2;
                        break block0;
                    }
                    case 2: {
                        this.m22 = f2;
                        break block0;
                    }
                }
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
            }
        }
    }

    public final void getRow(int n, Vector3f vector3f) {
        if (n == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m01;
            vector3f.z = this.m02;
        } else if (n == 1) {
            vector3f.x = this.m10;
            vector3f.y = this.m11;
            vector3f.z = this.m12;
        } else if (n == 2) {
            vector3f.x = this.m20;
            vector3f.y = this.m21;
            vector3f.z = this.m22;
        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }
    }

    public final void getRow(int n, float[] fArray) {
        if (n == 0) {
            fArray[0] = this.m00;
            fArray[1] = this.m01;
            fArray[2] = this.m02;
        } else if (n == 1) {
            fArray[0] = this.m10;
            fArray[1] = this.m11;
            fArray[2] = this.m12;
        } else if (n == 2) {
            fArray[0] = this.m20;
            fArray[1] = this.m21;
            fArray[2] = this.m22;
        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }
    }

    public final void getColumn(int n, Vector3f vector3f) {
        if (n == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m10;
            vector3f.z = this.m20;
        } else if (n == 1) {
            vector3f.x = this.m01;
            vector3f.y = this.m11;
            vector3f.z = this.m21;
        } else if (n == 2) {
            vector3f.x = this.m02;
            vector3f.y = this.m12;
            vector3f.z = this.m22;
        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }
    }

    public final void getColumn(int n, float[] fArray) {
        if (n == 0) {
            fArray[0] = this.m00;
            fArray[1] = this.m10;
            fArray[2] = this.m20;
        } else if (n == 1) {
            fArray[0] = this.m01;
            fArray[1] = this.m11;
            fArray[2] = this.m21;
        } else if (n == 2) {
            fArray[0] = this.m02;
            fArray[1] = this.m12;
            fArray[2] = this.m22;
        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }
    }

    public final float getElement(int n, int n2) {
        switch (n) {
            case 0: {
                switch (n2) {
                    case 0: {
                        return this.m00;
                    }
                    case 1: {
                        return this.m01;
                    }
                    case 2: {
                        return this.m02;
                    }
                }
                break;
            }
            case 1: {
                switch (n2) {
                    case 0: {
                        return this.m10;
                    }
                    case 1: {
                        return this.m11;
                    }
                    case 2: {
                        return this.m12;
                    }
                }
                break;
            }
            case 2: {
                switch (n2) {
                    case 0: {
                        return this.m20;
                    }
                    case 1: {
                        return this.m21;
                    }
                    case 2: {
                        return this.m22;
                    }
                }
                break;
            }
        }
        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
    }

    public final void setRow(int n, float f2, float f3, float f4) {
        switch (n) {
            case 0: {
                this.m00 = f2;
                this.m01 = f3;
                this.m02 = f4;
                break;
            }
            case 1: {
                this.m10 = f2;
                this.m11 = f3;
                this.m12 = f4;
                break;
            }
            case 2: {
                this.m20 = f2;
                this.m21 = f3;
                this.m22 = f4;
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
            }
        }
    }

    public final void setRow(int n, Vector3f vector3f) {
        switch (n) {
            case 0: {
                this.m00 = vector3f.x;
                this.m01 = vector3f.y;
                this.m02 = vector3f.z;
                break;
            }
            case 1: {
                this.m10 = vector3f.x;
                this.m11 = vector3f.y;
                this.m12 = vector3f.z;
                break;
            }
            case 2: {
                this.m20 = vector3f.x;
                this.m21 = vector3f.y;
                this.m22 = vector3f.z;
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
            }
        }
    }

    public final void setRow(int n, float[] fArray) {
        switch (n) {
            case 0: {
                this.m00 = fArray[0];
                this.m01 = fArray[1];
                this.m02 = fArray[2];
                break;
            }
            case 1: {
                this.m10 = fArray[0];
                this.m11 = fArray[1];
                this.m12 = fArray[2];
                break;
            }
            case 2: {
                this.m20 = fArray[0];
                this.m21 = fArray[1];
                this.m22 = fArray[2];
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
            }
        }
    }

    public final void setColumn(int n, float f2, float f3, float f4) {
        switch (n) {
            case 0: {
                this.m00 = f2;
                this.m10 = f3;
                this.m20 = f4;
                break;
            }
            case 1: {
                this.m01 = f2;
                this.m11 = f3;
                this.m21 = f4;
                break;
            }
            case 2: {
                this.m02 = f2;
                this.m12 = f3;
                this.m22 = f4;
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
            }
        }
    }

    public final void setColumn(int n, Vector3f vector3f) {
        switch (n) {
            case 0: {
                this.m00 = vector3f.x;
                this.m10 = vector3f.y;
                this.m20 = vector3f.z;
                break;
            }
            case 1: {
                this.m01 = vector3f.x;
                this.m11 = vector3f.y;
                this.m21 = vector3f.z;
                break;
            }
            case 2: {
                this.m02 = vector3f.x;
                this.m12 = vector3f.y;
                this.m22 = vector3f.z;
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
            }
        }
    }

    public final void setColumn(int n, float[] fArray) {
        switch (n) {
            case 0: {
                this.m00 = fArray[0];
                this.m10 = fArray[1];
                this.m20 = fArray[2];
                break;
            }
            case 1: {
                this.m01 = fArray[0];
                this.m11 = fArray[1];
                this.m21 = fArray[2];
                break;
            }
            case 2: {
                this.m02 = fArray[0];
                this.m12 = fArray[1];
                this.m22 = fArray[2];
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
            }
        }
    }

    public final float getScale() {
        double[] dArray = new double[9];
        double[] dArray2 = new double[3];
        this.getScaleRotate(dArray2, dArray);
        return (float)Matrix3d.max3(dArray2);
    }

    public final void add(float f2) {
        this.m00 += f2;
        this.m01 += f2;
        this.m02 += f2;
        this.m10 += f2;
        this.m11 += f2;
        this.m12 += f2;
        this.m20 += f2;
        this.m21 += f2;
        this.m22 += f2;
    }

    public final void add(float f2, Matrix3f matrix3f) {
        this.m00 = matrix3f.m00 + f2;
        this.m01 = matrix3f.m01 + f2;
        this.m02 = matrix3f.m02 + f2;
        this.m10 = matrix3f.m10 + f2;
        this.m11 = matrix3f.m11 + f2;
        this.m12 = matrix3f.m12 + f2;
        this.m20 = matrix3f.m20 + f2;
        this.m21 = matrix3f.m21 + f2;
        this.m22 = matrix3f.m22 + f2;
    }

    public final void add(Matrix3f matrix3f, Matrix3f matrix3f2) {
        this.m00 = matrix3f.m00 + matrix3f2.m00;
        this.m01 = matrix3f.m01 + matrix3f2.m01;
        this.m02 = matrix3f.m02 + matrix3f2.m02;
        this.m10 = matrix3f.m10 + matrix3f2.m10;
        this.m11 = matrix3f.m11 + matrix3f2.m11;
        this.m12 = matrix3f.m12 + matrix3f2.m12;
        this.m20 = matrix3f.m20 + matrix3f2.m20;
        this.m21 = matrix3f.m21 + matrix3f2.m21;
        this.m22 = matrix3f.m22 + matrix3f2.m22;
    }

    public final void add(Matrix3f matrix3f) {
        this.m00 += matrix3f.m00;
        this.m01 += matrix3f.m01;
        this.m02 += matrix3f.m02;
        this.m10 += matrix3f.m10;
        this.m11 += matrix3f.m11;
        this.m12 += matrix3f.m12;
        this.m20 += matrix3f.m20;
        this.m21 += matrix3f.m21;
        this.m22 += matrix3f.m22;
    }

    public final void sub(Matrix3f matrix3f, Matrix3f matrix3f2) {
        this.m00 = matrix3f.m00 - matrix3f2.m00;
        this.m01 = matrix3f.m01 - matrix3f2.m01;
        this.m02 = matrix3f.m02 - matrix3f2.m02;
        this.m10 = matrix3f.m10 - matrix3f2.m10;
        this.m11 = matrix3f.m11 - matrix3f2.m11;
        this.m12 = matrix3f.m12 - matrix3f2.m12;
        this.m20 = matrix3f.m20 - matrix3f2.m20;
        this.m21 = matrix3f.m21 - matrix3f2.m21;
        this.m22 = matrix3f.m22 - matrix3f2.m22;
    }

    public final void sub(Matrix3f matrix3f) {
        this.m00 -= matrix3f.m00;
        this.m01 -= matrix3f.m01;
        this.m02 -= matrix3f.m02;
        this.m10 -= matrix3f.m10;
        this.m11 -= matrix3f.m11;
        this.m12 -= matrix3f.m12;
        this.m20 -= matrix3f.m20;
        this.m21 -= matrix3f.m21;
        this.m22 -= matrix3f.m22;
    }

    public final void transpose() {
        float f2 = this.m10;
        this.m10 = this.m01;
        this.m01 = f2;
        f2 = this.m20;
        this.m20 = this.m02;
        this.m02 = f2;
        f2 = this.m21;
        this.m21 = this.m12;
        this.m12 = f2;
    }

    public final void transpose(Matrix3f matrix3f) {
        if (this != matrix3f) {
            this.m00 = matrix3f.m00;
            this.m01 = matrix3f.m10;
            this.m02 = matrix3f.m20;
            this.m10 = matrix3f.m01;
            this.m11 = matrix3f.m11;
            this.m12 = matrix3f.m21;
            this.m20 = matrix3f.m02;
            this.m21 = matrix3f.m12;
            this.m22 = matrix3f.m22;
        } else {
            this.transpose();
        }
    }

    public final void set(Quat4f quat4f) {
        this.m00 = 1.0f - 2.0f * quat4f.y * quat4f.y - 2.0f * quat4f.z * quat4f.z;
        this.m10 = 2.0f * (quat4f.x * quat4f.y + quat4f.w * quat4f.z);
        this.m20 = 2.0f * (quat4f.x * quat4f.z - quat4f.w * quat4f.y);
        this.m01 = 2.0f * (quat4f.x * quat4f.y - quat4f.w * quat4f.z);
        this.m11 = 1.0f - 2.0f * quat4f.x * quat4f.x - 2.0f * quat4f.z * quat4f.z;
        this.m21 = 2.0f * (quat4f.y * quat4f.z + quat4f.w * quat4f.x);
        this.m02 = 2.0f * (quat4f.x * quat4f.z + quat4f.w * quat4f.y);
        this.m12 = 2.0f * (quat4f.y * quat4f.z - quat4f.w * quat4f.x);
        this.m22 = 1.0f - 2.0f * quat4f.x * quat4f.x - 2.0f * quat4f.y * quat4f.y;
    }

    public final void set(AxisAngle4f axisAngle4f) {
        float f2 = (float)Math.sqrt(axisAngle4f.x * axisAngle4f.x + axisAngle4f.y * axisAngle4f.y + axisAngle4f.z * axisAngle4f.z);
        if ((double)f2 < 1.0E-8) {
            this.m00 = 1.0f;
            this.m01 = 0.0f;
            this.m02 = 0.0f;
            this.m10 = 0.0f;
            this.m11 = 1.0f;
            this.m12 = 0.0f;
            this.m20 = 0.0f;
            this.m21 = 0.0f;
            this.m22 = 1.0f;
        } else {
            f2 = 1.0f / f2;
            float f3 = axisAngle4f.x * f2;
            float f4 = axisAngle4f.y * f2;
            float f5 = axisAngle4f.z * f2;
            float f6 = (float)Math.sin(axisAngle4f.angle);
            float f7 = (float)Math.cos(axisAngle4f.angle);
            float f8 = 1.0f - f7;
            float f9 = f3 * f5;
            float f10 = f3 * f4;
            float f11 = f4 * f5;
            this.m00 = f8 * f3 * f3 + f7;
            this.m01 = f8 * f10 - f6 * f5;
            this.m02 = f8 * f9 + f6 * f4;
            this.m10 = f8 * f10 + f6 * f5;
            this.m11 = f8 * f4 * f4 + f7;
            this.m12 = f8 * f11 - f6 * f3;
            this.m20 = f8 * f9 - f6 * f4;
            this.m21 = f8 * f11 + f6 * f3;
            this.m22 = f8 * f5 * f5 + f7;
        }
    }

    public final void set(AxisAngle4d axisAngle4d) {
        double d = Math.sqrt(axisAngle4d.x * axisAngle4d.x + axisAngle4d.y * axisAngle4d.y + axisAngle4d.z * axisAngle4d.z);
        if (d < 1.0E-8) {
            this.m00 = 1.0f;
            this.m01 = 0.0f;
            this.m02 = 0.0f;
            this.m10 = 0.0f;
            this.m11 = 1.0f;
            this.m12 = 0.0f;
            this.m20 = 0.0f;
            this.m21 = 0.0f;
            this.m22 = 1.0f;
        } else {
            d = 1.0 / d;
            double d2 = axisAngle4d.x * d;
            double d3 = axisAngle4d.y * d;
            double d4 = axisAngle4d.z * d;
            double d5 = Math.sin(axisAngle4d.angle);
            double d6 = Math.cos(axisAngle4d.angle);
            double d7 = 1.0 - d6;
            double d8 = d2 * d4;
            double d9 = d2 * d3;
            double d10 = d3 * d4;
            this.m00 = (float)(d7 * d2 * d2 + d6);
            this.m01 = (float)(d7 * d9 - d5 * d4);
            this.m02 = (float)(d7 * d8 + d5 * d3);
            this.m10 = (float)(d7 * d9 + d5 * d4);
            this.m11 = (float)(d7 * d3 * d3 + d6);
            this.m12 = (float)(d7 * d10 - d5 * d2);
            this.m20 = (float)(d7 * d8 - d5 * d3);
            this.m21 = (float)(d7 * d10 + d5 * d2);
            this.m22 = (float)(d7 * d4 * d4 + d6);
        }
    }

    public final void set(Quat4d quat4d) {
        this.m00 = (float)(1.0 - 2.0 * quat4d.y * quat4d.y - 2.0 * quat4d.z * quat4d.z);
        this.m10 = (float)(2.0 * (quat4d.x * quat4d.y + quat4d.w * quat4d.z));
        this.m20 = (float)(2.0 * (quat4d.x * quat4d.z - quat4d.w * quat4d.y));
        this.m01 = (float)(2.0 * (quat4d.x * quat4d.y - quat4d.w * quat4d.z));
        this.m11 = (float)(1.0 - 2.0 * quat4d.x * quat4d.x - 2.0 * quat4d.z * quat4d.z);
        this.m21 = (float)(2.0 * (quat4d.y * quat4d.z + quat4d.w * quat4d.x));
        this.m02 = (float)(2.0 * (quat4d.x * quat4d.z + quat4d.w * quat4d.y));
        this.m12 = (float)(2.0 * (quat4d.y * quat4d.z - quat4d.w * quat4d.x));
        this.m22 = (float)(1.0 - 2.0 * quat4d.x * quat4d.x - 2.0 * quat4d.y * quat4d.y);
    }

    public final void set(float[] fArray) {
        this.m00 = fArray[0];
        this.m01 = fArray[1];
        this.m02 = fArray[2];
        this.m10 = fArray[3];
        this.m11 = fArray[4];
        this.m12 = fArray[5];
        this.m20 = fArray[6];
        this.m21 = fArray[7];
        this.m22 = fArray[8];
    }

    public final void set(Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }

    public final void set(Matrix3d matrix3d) {
        this.m00 = (float)matrix3d.m00;
        this.m01 = (float)matrix3d.m01;
        this.m02 = (float)matrix3d.m02;
        this.m10 = (float)matrix3d.m10;
        this.m11 = (float)matrix3d.m11;
        this.m12 = (float)matrix3d.m12;
        this.m20 = (float)matrix3d.m20;
        this.m21 = (float)matrix3d.m21;
        this.m22 = (float)matrix3d.m22;
    }

    public final void invert(Matrix3f matrix3f) {
        this.invertGeneral(matrix3f);
    }

    public final void invert() {
        this.invertGeneral(this);
    }

    private final void invertGeneral(Matrix3f matrix3f) {
        double[] dArray = new double[9];
        double[] dArray2 = new double[9];
        int[] nArray = new int[3];
        dArray[0] = matrix3f.m00;
        dArray[1] = matrix3f.m01;
        dArray[2] = matrix3f.m02;
        dArray[3] = matrix3f.m10;
        dArray[4] = matrix3f.m11;
        dArray[5] = matrix3f.m12;
        dArray[6] = matrix3f.m20;
        dArray[7] = matrix3f.m21;
        dArray[8] = matrix3f.m22;
        if (!Matrix3f.luDecomposition(dArray, nArray)) {
            throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
        }
        for (int i = 0; i < 9; ++i) {
            dArray2[i] = 0.0;
        }
        dArray2[0] = 1.0;
        dArray2[4] = 1.0;
        dArray2[8] = 1.0;
        Matrix3f.luBacksubstitution(dArray, nArray, dArray2);
        this.m00 = (float)dArray2[0];
        this.m01 = (float)dArray2[1];
        this.m02 = (float)dArray2[2];
        this.m10 = (float)dArray2[3];
        this.m11 = (float)dArray2[4];
        this.m12 = (float)dArray2[5];
        this.m20 = (float)dArray2[6];
        this.m21 = (float)dArray2[7];
        this.m22 = (float)dArray2[8];
    }

    static boolean luDecomposition(double[] dArray, int[] nArray) {
        int n;
        double[] dArray2 = new double[3];
        int n2 = 0;
        int n3 = 0;
        int n4 = 3;
        while (n4-- != 0) {
            double d = 0.0;
            n = 3;
            while (n-- != 0) {
                double d2 = dArray[n2++];
                if (!((d2 = Math.abs(d2)) > d)) continue;
                d = d2;
            }
            if (d == 0.0) {
                return false;
            }
            dArray2[n3++] = 1.0 / d;
        }
        n = 0;
        for (n4 = 0; n4 < 3; ++n4) {
            double d;
            int n5;
            int n6;
            int n7;
            double d3;
            int n8;
            for (n2 = 0; n2 < n4; ++n2) {
                n8 = n + 3 * n2 + n4;
                d3 = dArray[n8];
                int n9 = n2;
                int n10 = n + 3 * n2;
                n7 = n + n4;
                while (n9-- != 0) {
                    d3 -= dArray[n10] * dArray[n7];
                    ++n10;
                    n7 += 3;
                }
                dArray[n8] = d3;
            }
            double d4 = 0.0;
            n3 = -1;
            for (n2 = n4; n2 < 3; ++n2) {
                double d5;
                n8 = n + 3 * n2 + n4;
                d3 = dArray[n8];
                n6 = n4;
                n5 = n + 3 * n2;
                n7 = n + n4;
                while (n6-- != 0) {
                    d3 -= dArray[n5] * dArray[n7];
                    ++n5;
                    n7 += 3;
                }
                dArray[n8] = d3;
                d = dArray2[n2] * Math.abs(d3);
                if (!(d5 >= d4)) continue;
                d4 = d;
                n3 = n2;
            }
            if (n3 < 0) {
                throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
            }
            if (n4 != n3) {
                n6 = 3;
                n5 = n + 3 * n3;
                n7 = n + 3 * n4;
                while (n6-- != 0) {
                    d = dArray[n5];
                    dArray[n5++] = dArray[n7];
                    dArray[n7++] = d;
                }
                dArray2[n3] = dArray2[n4];
            }
            nArray[n4] = n3;
            if (dArray[n + 3 * n4 + n4] == 0.0) {
                return false;
            }
            if (n4 == 2) continue;
            d = 1.0 / dArray[n + 3 * n4 + n4];
            n8 = n + 3 * (n4 + 1) + n4;
            n2 = 2 - n4;
            while (n2-- != 0) {
                int n11 = n8;
                dArray[n11] = dArray[n11] * d;
                n8 += 3;
            }
        }
        return true;
    }

    static void luBacksubstitution(double[] dArray, int[] nArray, double[] dArray2) {
        int n = 0;
        for (int i = 0; i < 3; ++i) {
            int n2;
            int n3 = i;
            int n4 = -1;
            for (int j = 0; j < 3; ++j) {
                int n5 = nArray[n + j];
                double d = dArray2[n3 + 3 * n5];
                dArray2[n3 + 3 * n5] = dArray2[n3 + 3 * j];
                if (n4 >= 0) {
                    n2 = j * 3;
                    for (int k = n4; k <= j - 1; ++k) {
                        d -= dArray[n2 + k] * dArray2[n3 + 3 * k];
                    }
                } else if (d != 0.0) {
                    n4 = j;
                }
                dArray2[n3 + 3 * j] = d;
            }
            n2 = 6;
            int n6 = n3 + 6;
            dArray2[n6] = dArray2[n6] / dArray[n2 + 2];
            dArray2[n3 + 3] = (dArray2[n3 + 3] - dArray[(n2 -= 3) + 2] * dArray2[n3 + 6]) / dArray[n2 + 1];
            dArray2[n3 + 0] = (dArray2[n3 + 0] - dArray[(n2 -= 3) + 1] * dArray2[n3 + 3] - dArray[n2 + 2] * dArray2[n3 + 6]) / dArray[n2 + 0];
        }
    }

    public final float determinant() {
        float f2 = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
        return f2;
    }

    public final void set(float f2) {
        this.m00 = f2;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = f2;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = f2;
    }

    public final void rotX(float f2) {
        float f3 = (float)Math.sin(f2);
        float f4 = (float)Math.cos(f2);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = f4;
        this.m12 = -f3;
        this.m20 = 0.0f;
        this.m21 = f3;
        this.m22 = f4;
    }

    public final void rotY(float f2) {
        float f3;
        float f4 = (float)Math.sin(f2);
        this.m00 = f3 = (float)Math.cos(f2);
        this.m01 = 0.0f;
        this.m02 = f4;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = -f4;
        this.m21 = 0.0f;
        this.m22 = f3;
    }

    public final void rotZ(float f2) {
        float f3;
        float f4 = (float)Math.sin(f2);
        this.m00 = f3 = (float)Math.cos(f2);
        this.m01 = -f4;
        this.m02 = 0.0f;
        this.m10 = f4;
        this.m11 = f3;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }

    public final void mul(float f2) {
        this.m00 *= f2;
        this.m01 *= f2;
        this.m02 *= f2;
        this.m10 *= f2;
        this.m11 *= f2;
        this.m12 *= f2;
        this.m20 *= f2;
        this.m21 *= f2;
        this.m22 *= f2;
    }

    public final void mul(float f2, Matrix3f matrix3f) {
        this.m00 = f2 * matrix3f.m00;
        this.m01 = f2 * matrix3f.m01;
        this.m02 = f2 * matrix3f.m02;
        this.m10 = f2 * matrix3f.m10;
        this.m11 = f2 * matrix3f.m11;
        this.m12 = f2 * matrix3f.m12;
        this.m20 = f2 * matrix3f.m20;
        this.m21 = f2 * matrix3f.m21;
        this.m22 = f2 * matrix3f.m22;
    }

    public final void mul(Matrix3f matrix3f) {
        float f2 = this.m00 * matrix3f.m00 + this.m01 * matrix3f.m10 + this.m02 * matrix3f.m20;
        float f3 = this.m00 * matrix3f.m01 + this.m01 * matrix3f.m11 + this.m02 * matrix3f.m21;
        float f4 = this.m00 * matrix3f.m02 + this.m01 * matrix3f.m12 + this.m02 * matrix3f.m22;
        float f5 = this.m10 * matrix3f.m00 + this.m11 * matrix3f.m10 + this.m12 * matrix3f.m20;
        float f6 = this.m10 * matrix3f.m01 + this.m11 * matrix3f.m11 + this.m12 * matrix3f.m21;
        float f7 = this.m10 * matrix3f.m02 + this.m11 * matrix3f.m12 + this.m12 * matrix3f.m22;
        float f8 = this.m20 * matrix3f.m00 + this.m21 * matrix3f.m10 + this.m22 * matrix3f.m20;
        float f9 = this.m20 * matrix3f.m01 + this.m21 * matrix3f.m11 + this.m22 * matrix3f.m21;
        float f10 = this.m20 * matrix3f.m02 + this.m21 * matrix3f.m12 + this.m22 * matrix3f.m22;
        this.m00 = f2;
        this.m01 = f3;
        this.m02 = f4;
        this.m10 = f5;
        this.m11 = f6;
        this.m12 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
    }

    public final void mul(Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (this != matrix3f && this != matrix3f2) {
            this.m00 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m10 + matrix3f.m02 * matrix3f2.m20;
            this.m01 = matrix3f.m00 * matrix3f2.m01 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m21;
            this.m02 = matrix3f.m00 * matrix3f2.m02 + matrix3f.m01 * matrix3f2.m12 + matrix3f.m02 * matrix3f2.m22;
            this.m10 = matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m20;
            this.m11 = matrix3f.m10 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m21;
            this.m12 = matrix3f.m10 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m12 * matrix3f2.m22;
            this.m20 = matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20;
            this.m21 = matrix3f.m20 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21;
            this.m22 = matrix3f.m20 * matrix3f2.m02 + matrix3f.m21 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22;
        } else {
            float f2 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m10 + matrix3f.m02 * matrix3f2.m20;
            float f3 = matrix3f.m00 * matrix3f2.m01 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m21;
            float f4 = matrix3f.m00 * matrix3f2.m02 + matrix3f.m01 * matrix3f2.m12 + matrix3f.m02 * matrix3f2.m22;
            float f5 = matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m20;
            float f6 = matrix3f.m10 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m21;
            float f7 = matrix3f.m10 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m12 * matrix3f2.m22;
            float f8 = matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20;
            float f9 = matrix3f.m20 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21;
            float f10 = matrix3f.m20 * matrix3f2.m02 + matrix3f.m21 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22;
            this.m00 = f2;
            this.m01 = f3;
            this.m02 = f4;
            this.m10 = f5;
            this.m11 = f6;
            this.m12 = f7;
            this.m20 = f8;
            this.m21 = f9;
            this.m22 = f10;
        }
    }

    public final void mulNormalize(Matrix3f matrix3f) {
        double[] dArray = new double[9];
        double[] dArray2 = new double[9];
        double[] dArray3 = new double[3];
        dArray[0] = this.m00 * matrix3f.m00 + this.m01 * matrix3f.m10 + this.m02 * matrix3f.m20;
        dArray[1] = this.m00 * matrix3f.m01 + this.m01 * matrix3f.m11 + this.m02 * matrix3f.m21;
        dArray[2] = this.m00 * matrix3f.m02 + this.m01 * matrix3f.m12 + this.m02 * matrix3f.m22;
        dArray[3] = this.m10 * matrix3f.m00 + this.m11 * matrix3f.m10 + this.m12 * matrix3f.m20;
        dArray[4] = this.m10 * matrix3f.m01 + this.m11 * matrix3f.m11 + this.m12 * matrix3f.m21;
        dArray[5] = this.m10 * matrix3f.m02 + this.m11 * matrix3f.m12 + this.m12 * matrix3f.m22;
        dArray[6] = this.m20 * matrix3f.m00 + this.m21 * matrix3f.m10 + this.m22 * matrix3f.m20;
        dArray[7] = this.m20 * matrix3f.m01 + this.m21 * matrix3f.m11 + this.m22 * matrix3f.m21;
        dArray[8] = this.m20 * matrix3f.m02 + this.m21 * matrix3f.m12 + this.m22 * matrix3f.m22;
        Matrix3d.compute_svd(dArray, dArray3, dArray2);
        this.m00 = (float)dArray2[0];
        this.m01 = (float)dArray2[1];
        this.m02 = (float)dArray2[2];
        this.m10 = (float)dArray2[3];
        this.m11 = (float)dArray2[4];
        this.m12 = (float)dArray2[5];
        this.m20 = (float)dArray2[6];
        this.m21 = (float)dArray2[7];
        this.m22 = (float)dArray2[8];
    }

    public final void mulNormalize(Matrix3f matrix3f, Matrix3f matrix3f2) {
        double[] dArray = new double[9];
        double[] dArray2 = new double[9];
        double[] dArray3 = new double[3];
        dArray[0] = matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m10 + matrix3f.m02 * matrix3f2.m20;
        dArray[1] = matrix3f.m00 * matrix3f2.m01 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m21;
        dArray[2] = matrix3f.m00 * matrix3f2.m02 + matrix3f.m01 * matrix3f2.m12 + matrix3f.m02 * matrix3f2.m22;
        dArray[3] = matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m20;
        dArray[4] = matrix3f.m10 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m21;
        dArray[5] = matrix3f.m10 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m12 * matrix3f2.m22;
        dArray[6] = matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20;
        dArray[7] = matrix3f.m20 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21;
        dArray[8] = matrix3f.m20 * matrix3f2.m02 + matrix3f.m21 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22;
        Matrix3d.compute_svd(dArray, dArray3, dArray2);
        this.m00 = (float)dArray2[0];
        this.m01 = (float)dArray2[1];
        this.m02 = (float)dArray2[2];
        this.m10 = (float)dArray2[3];
        this.m11 = (float)dArray2[4];
        this.m12 = (float)dArray2[5];
        this.m20 = (float)dArray2[6];
        this.m21 = (float)dArray2[7];
        this.m22 = (float)dArray2[8];
    }

    public final void mulTransposeBoth(Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (this != matrix3f && this != matrix3f2) {
            this.m00 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m01 + matrix3f.m20 * matrix3f2.m02;
            this.m01 = matrix3f.m00 * matrix3f2.m10 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m12;
            this.m02 = matrix3f.m00 * matrix3f2.m20 + matrix3f.m10 * matrix3f2.m21 + matrix3f.m20 * matrix3f2.m22;
            this.m10 = matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m02;
            this.m11 = matrix3f.m01 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m12;
            this.m12 = matrix3f.m01 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m21 * matrix3f2.m22;
            this.m20 = matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02;
            this.m21 = matrix3f.m02 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12;
            this.m22 = matrix3f.m02 * matrix3f2.m20 + matrix3f.m12 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22;
        } else {
            float f2 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m01 + matrix3f.m20 * matrix3f2.m02;
            float f3 = matrix3f.m00 * matrix3f2.m10 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m12;
            float f4 = matrix3f.m00 * matrix3f2.m20 + matrix3f.m10 * matrix3f2.m21 + matrix3f.m20 * matrix3f2.m22;
            float f5 = matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m02;
            float f6 = matrix3f.m01 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m12;
            float f7 = matrix3f.m01 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m21 * matrix3f2.m22;
            float f8 = matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02;
            float f9 = matrix3f.m02 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12;
            float f10 = matrix3f.m02 * matrix3f2.m20 + matrix3f.m12 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22;
            this.m00 = f2;
            this.m01 = f3;
            this.m02 = f4;
            this.m10 = f5;
            this.m11 = f6;
            this.m12 = f7;
            this.m20 = f8;
            this.m21 = f9;
            this.m22 = f10;
        }
    }

    public final void mulTransposeRight(Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (this != matrix3f && this != matrix3f2) {
            this.m00 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m01 + matrix3f.m02 * matrix3f2.m02;
            this.m01 = matrix3f.m00 * matrix3f2.m10 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m12;
            this.m02 = matrix3f.m00 * matrix3f2.m20 + matrix3f.m01 * matrix3f2.m21 + matrix3f.m02 * matrix3f2.m22;
            this.m10 = matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m02;
            this.m11 = matrix3f.m10 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m12;
            this.m12 = matrix3f.m10 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m12 * matrix3f2.m22;
            this.m20 = matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02;
            this.m21 = matrix3f.m20 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12;
            this.m22 = matrix3f.m20 * matrix3f2.m20 + matrix3f.m21 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22;
        } else {
            float f2 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m01 + matrix3f.m02 * matrix3f2.m02;
            float f3 = matrix3f.m00 * matrix3f2.m10 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m12;
            float f4 = matrix3f.m00 * matrix3f2.m20 + matrix3f.m01 * matrix3f2.m21 + matrix3f.m02 * matrix3f2.m22;
            float f5 = matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m02;
            float f6 = matrix3f.m10 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m12;
            float f7 = matrix3f.m10 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m12 * matrix3f2.m22;
            float f8 = matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02;
            float f9 = matrix3f.m20 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12;
            float f10 = matrix3f.m20 * matrix3f2.m20 + matrix3f.m21 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22;
            this.m00 = f2;
            this.m01 = f3;
            this.m02 = f4;
            this.m10 = f5;
            this.m11 = f6;
            this.m12 = f7;
            this.m20 = f8;
            this.m21 = f9;
            this.m22 = f10;
        }
    }

    public final void mulTransposeLeft(Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (this != matrix3f && this != matrix3f2) {
            this.m00 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m10 + matrix3f.m20 * matrix3f2.m20;
            this.m01 = matrix3f.m00 * matrix3f2.m01 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m21;
            this.m02 = matrix3f.m00 * matrix3f2.m02 + matrix3f.m10 * matrix3f2.m12 + matrix3f.m20 * matrix3f2.m22;
            this.m10 = matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m20;
            this.m11 = matrix3f.m01 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m21;
            this.m12 = matrix3f.m01 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m21 * matrix3f2.m22;
            this.m20 = matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20;
            this.m21 = matrix3f.m02 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21;
            this.m22 = matrix3f.m02 * matrix3f2.m02 + matrix3f.m12 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22;
        } else {
            float f2 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m10 + matrix3f.m20 * matrix3f2.m20;
            float f3 = matrix3f.m00 * matrix3f2.m01 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m21;
            float f4 = matrix3f.m00 * matrix3f2.m02 + matrix3f.m10 * matrix3f2.m12 + matrix3f.m20 * matrix3f2.m22;
            float f5 = matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m20;
            float f6 = matrix3f.m01 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m21;
            float f7 = matrix3f.m01 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m21 * matrix3f2.m22;
            float f8 = matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20;
            float f9 = matrix3f.m02 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21;
            float f10 = matrix3f.m02 * matrix3f2.m02 + matrix3f.m12 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22;
            this.m00 = f2;
            this.m01 = f3;
            this.m02 = f4;
            this.m10 = f5;
            this.m11 = f6;
            this.m12 = f7;
            this.m20 = f8;
            this.m21 = f9;
            this.m22 = f10;
        }
    }

    public final void normalize() {
        double[] dArray = new double[9];
        double[] dArray2 = new double[3];
        this.getScaleRotate(dArray2, dArray);
        this.m00 = (float)dArray[0];
        this.m01 = (float)dArray[1];
        this.m02 = (float)dArray[2];
        this.m10 = (float)dArray[3];
        this.m11 = (float)dArray[4];
        this.m12 = (float)dArray[5];
        this.m20 = (float)dArray[6];
        this.m21 = (float)dArray[7];
        this.m22 = (float)dArray[8];
    }

    public final void normalize(Matrix3f matrix3f) {
        double[] dArray = new double[9];
        double[] dArray2 = new double[9];
        double[] dArray3 = new double[3];
        dArray[0] = matrix3f.m00;
        dArray[1] = matrix3f.m01;
        dArray[2] = matrix3f.m02;
        dArray[3] = matrix3f.m10;
        dArray[4] = matrix3f.m11;
        dArray[5] = matrix3f.m12;
        dArray[6] = matrix3f.m20;
        dArray[7] = matrix3f.m21;
        dArray[8] = matrix3f.m22;
        Matrix3d.compute_svd(dArray, dArray3, dArray2);
        this.m00 = (float)dArray2[0];
        this.m01 = (float)dArray2[1];
        this.m02 = (float)dArray2[2];
        this.m10 = (float)dArray2[3];
        this.m11 = (float)dArray2[4];
        this.m12 = (float)dArray2[5];
        this.m20 = (float)dArray2[6];
        this.m21 = (float)dArray2[7];
        this.m22 = (float)dArray2[8];
    }

    public final void normalizeCP() {
        float f2 = 1.0f / (float)Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
        this.m00 *= f2;
        this.m10 *= f2;
        this.m20 *= f2;
        f2 = 1.0f / (float)Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
        this.m01 *= f2;
        this.m11 *= f2;
        this.m21 *= f2;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    public final void normalizeCP(Matrix3f matrix3f) {
        float f2 = 1.0f / (float)Math.sqrt(matrix3f.m00 * matrix3f.m00 + matrix3f.m10 * matrix3f.m10 + matrix3f.m20 * matrix3f.m20);
        this.m00 = matrix3f.m00 * f2;
        this.m10 = matrix3f.m10 * f2;
        this.m20 = matrix3f.m20 * f2;
        f2 = 1.0f / (float)Math.sqrt(matrix3f.m01 * matrix3f.m01 + matrix3f.m11 * matrix3f.m11 + matrix3f.m21 * matrix3f.m21);
        this.m01 = matrix3f.m01 * f2;
        this.m11 = matrix3f.m11 * f2;
        this.m21 = matrix3f.m21 * f2;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    public boolean equals(Matrix3f matrix3f) {
        try {
            return this.m00 == matrix3f.m00 && this.m01 == matrix3f.m01 && this.m02 == matrix3f.m02 && this.m10 == matrix3f.m10 && this.m11 == matrix3f.m11 && this.m12 == matrix3f.m12 && this.m20 == matrix3f.m20 && this.m21 == matrix3f.m21 && this.m22 == matrix3f.m22;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public boolean equals(Object object) {
        try {
            Matrix3f matrix3f = (Matrix3f)object;
            return this.m00 == matrix3f.m00 && this.m01 == matrix3f.m01 && this.m02 == matrix3f.m02 && this.m10 == matrix3f.m10 && this.m11 == matrix3f.m11 && this.m12 == matrix3f.m12 && this.m20 == matrix3f.m20 && this.m21 == matrix3f.m21 && this.m22 == matrix3f.m22;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public boolean epsilonEquals(Matrix3f matrix3f, float f2) {
        boolean bl = true;
        if (Math.abs(this.m00 - matrix3f.m00) > f2) {
            bl = false;
        }
        if (Math.abs(this.m01 - matrix3f.m01) > f2) {
            bl = false;
        }
        if (Math.abs(this.m02 - matrix3f.m02) > f2) {
            bl = false;
        }
        if (Math.abs(this.m10 - matrix3f.m10) > f2) {
            bl = false;
        }
        if (Math.abs(this.m11 - matrix3f.m11) > f2) {
            bl = false;
        }
        if (Math.abs(this.m12 - matrix3f.m12) > f2) {
            bl = false;
        }
        if (Math.abs(this.m20 - matrix3f.m20) > f2) {
            bl = false;
        }
        if (Math.abs(this.m21 - matrix3f.m21) > f2) {
            bl = false;
        }
        if (Math.abs(this.m22 - matrix3f.m22) > f2) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        long l = 1L;
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m00);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m01);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m02);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m10);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m11);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m12);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m20);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m21);
        l = 31L * l + (long)VecMathUtil.floatToIntBits(this.m22);
        return (int)(l ^ l >> 32);
    }

    public final void setZero() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
    }

    public final void negate() {
        this.m00 = -this.m00;
        this.m01 = -this.m01;
        this.m02 = -this.m02;
        this.m10 = -this.m10;
        this.m11 = -this.m11;
        this.m12 = -this.m12;
        this.m20 = -this.m20;
        this.m21 = -this.m21;
        this.m22 = -this.m22;
    }

    public final void negate(Matrix3f matrix3f) {
        this.m00 = -matrix3f.m00;
        this.m01 = -matrix3f.m01;
        this.m02 = -matrix3f.m02;
        this.m10 = -matrix3f.m10;
        this.m11 = -matrix3f.m11;
        this.m12 = -matrix3f.m12;
        this.m20 = -matrix3f.m20;
        this.m21 = -matrix3f.m21;
        this.m22 = -matrix3f.m22;
    }

    public final void transform(Tuple3f tuple3f) {
        float f2 = this.m00 * tuple3f.x + this.m01 * tuple3f.y + this.m02 * tuple3f.z;
        float f3 = this.m10 * tuple3f.x + this.m11 * tuple3f.y + this.m12 * tuple3f.z;
        float f4 = this.m20 * tuple3f.x + this.m21 * tuple3f.y + this.m22 * tuple3f.z;
        tuple3f.set(f2, f3, f4);
    }

    public final void transform(Tuple3f tuple3f, Tuple3f tuple3f2) {
        float f2 = this.m00 * tuple3f.x + this.m01 * tuple3f.y + this.m02 * tuple3f.z;
        float f3 = this.m10 * tuple3f.x + this.m11 * tuple3f.y + this.m12 * tuple3f.z;
        tuple3f2.z = this.m20 * tuple3f.x + this.m21 * tuple3f.y + this.m22 * tuple3f.z;
        tuple3f2.x = f2;
        tuple3f2.y = f3;
    }

    void getScaleRotate(double[] dArray, double[] dArray2) {
        double[] dArray3 = new double[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
        Matrix3d.compute_svd(dArray3, dArray, dArray2);
    }

    public Object clone() {
        Matrix3f matrix3f = null;
        try {
            matrix3f = (Matrix3f)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        return matrix3f;
    }

    public final float getM00() {
        return this.m00;
    }

    public final void setM00(float f2) {
        this.m00 = f2;
    }

    public final float getM01() {
        return this.m01;
    }

    public final void setM01(float f2) {
        this.m01 = f2;
    }

    public final float getM02() {
        return this.m02;
    }

    public final void setM02(float f2) {
        this.m02 = f2;
    }

    public final float getM10() {
        return this.m10;
    }

    public final void setM10(float f2) {
        this.m10 = f2;
    }

    public final float getM11() {
        return this.m11;
    }

    public final void setM11(float f2) {
        this.m11 = f2;
    }

    public final float getM12() {
        return this.m12;
    }

    public final void setM12(float f2) {
        this.m12 = f2;
    }

    public final float getM20() {
        return this.m20;
    }

    public final void setM20(float f2) {
        this.m20 = f2;
    }

    public final float getM21() {
        return this.m21;
    }

    public final void setM21(float f2) {
        this.m21 = f2;
    }

    public final float getM22() {
        return this.m22;
    }

    public final void setM22(float f2) {
        this.m22 = f2;
    }
}

