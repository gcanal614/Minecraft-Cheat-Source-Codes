/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBShaderObjects
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform4f
extends ShaderUniformBase {
    private float[][] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;

    public ShaderUniform4f(String name) {
        super(name);
        this.resetValue();
    }

    public void setValue(float v0, float v1, float v2, float v3) {
        int i = this.getProgram();
        float[] afloat = this.programValues[i];
        if (afloat[0] != v0 || afloat[1] != v1 || afloat[2] != v2 || afloat[3] != v3) {
            afloat[0] = v0;
            afloat[1] = v1;
            afloat[2] = v2;
            afloat[3] = v3;
            int j = this.getLocation();
            if (j >= 0) {
                ARBShaderObjects.glUniform4fARB((int)j, (float)v0, (float)v1, (float)v2, (float)v3);
                this.checkGLError();
            }
        }
    }

    public float[] getValue() {
        int i = this.getProgram();
        return this.programValues[i];
    }

    @Override
    protected void onProgramSet(int program) {
        if (program >= this.programValues.length) {
            float[][] afloat = this.programValues;
            float[][] afloat1 = new float[program + 10][];
            System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
            this.programValues = afloat1;
        }
        if (this.programValues[program] == null) {
            this.programValues[program] = new float[]{-3.4028235E38f, -3.4028235E38f, -3.4028235E38f, -3.4028235E38f};
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new float[][]{{-3.4028235E38f, -3.4028235E38f, -3.4028235E38f, -3.4028235E38f}};
    }
}

