/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionCached;
import net.optifine.shaders.uniform.CustomUniform;

public class CustomUniforms {
    private final CustomUniform[] uniforms;
    private final IExpressionCached[] expressionsCached;

    public CustomUniforms(CustomUniform[] uniforms, Map<String, IExpression> mapExpressions) {
        this.uniforms = uniforms;
        ArrayList<IExpressionCached> list = new ArrayList<IExpressionCached>();
        for (String s : mapExpressions.keySet()) {
            IExpression iexpression = mapExpressions.get(s);
            if (!(iexpression instanceof IExpressionCached)) continue;
            IExpressionCached iexpressioncached = (IExpressionCached)((Object)iexpression);
            list.add(iexpressioncached);
        }
        this.expressionsCached = list.toArray(new IExpressionCached[0]);
    }

    public void setProgram(int program) {
        for (CustomUniform customuniform : this.uniforms) {
            customuniform.setProgram(program);
        }
    }

    public void update() {
        this.resetCache();
        for (CustomUniform customuniform : this.uniforms) {
            customuniform.update();
        }
    }

    private void resetCache() {
        for (IExpressionCached iexpressioncached : this.expressionsCached) {
            iexpressioncached.reset();
        }
    }

    public void reset() {
        for (CustomUniform customuniform : this.uniforms) {
            customuniform.reset();
        }
    }
}

