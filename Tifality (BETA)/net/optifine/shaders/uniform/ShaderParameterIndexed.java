/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.ShaderParameterFloat;

public class ShaderParameterIndexed
implements IExpressionFloat {
    private ShaderParameterFloat type;
    private int index1;
    private int index2;

    public ShaderParameterIndexed(ShaderParameterFloat type2) {
        this(type2, 0, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat type2, int index1) {
        this(type2, index1, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat type2, int index1, int index2) {
        this.type = type2;
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public float eval() {
        return this.type.eval(this.index1, this.index2);
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    public String toString() {
        return this.type.getIndexNames1() == null ? "" + (Object)((Object)this.type) : (this.type.getIndexNames2() == null ? "" + (Object)((Object)this.type) + "." + this.type.getIndexNames1()[this.index1] : "" + (Object)((Object)this.type) + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2]);
    }
}

