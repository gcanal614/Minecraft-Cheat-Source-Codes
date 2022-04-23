/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;

public class FunctionBool
implements IExpressionBool {
    private FunctionType type;
    private IExpression[] arguments;

    public FunctionBool(FunctionType type2, IExpression[] arguments2) {
        this.type = type2;
        this.arguments = arguments2;
    }

    @Override
    public boolean eval() {
        return this.type.evalBool(this.arguments);
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.BOOL;
    }

    public String toString() {
        return "" + (Object)((Object)this.type) + "()";
    }
}

