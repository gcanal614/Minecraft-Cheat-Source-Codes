/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.expr;

import net.optifine.expr.ConstantFloat;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.Smoother;

public class FunctionFloat
implements IExpressionFloat {
    private final FunctionType type;
    private final IExpression[] arguments;
    private int smoothId = -1;

    public FunctionFloat(FunctionType type, IExpression[] arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    @Override
    public float eval() {
        IExpression iexpression;
        IExpression[] aiexpression = this.arguments;
        if (this.type == FunctionType.SMOOTH && !((iexpression = aiexpression[0]) instanceof ConstantFloat)) {
            float f2;
            float f = FunctionFloat.evalFloat(aiexpression, 0);
            float f1 = aiexpression.length > 1 ? FunctionFloat.evalFloat(aiexpression, 1) : 1.0f;
            float f3 = f2 = aiexpression.length > 2 ? FunctionFloat.evalFloat(aiexpression, 2) : f1;
            if (this.smoothId < 0) {
                this.smoothId = Smoother.getNextId();
            }
            return Smoother.getSmoothValue(this.smoothId, f, f1, f2);
        }
        return this.type.evalFloat(this.arguments);
    }

    private static float evalFloat(IExpression[] exprs, int index) {
        IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
        return iexpressionfloat.eval();
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    public String toString() {
        return "" + (Object)((Object)this.type) + "()";
    }
}

