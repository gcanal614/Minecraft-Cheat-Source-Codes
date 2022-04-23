/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.ModelVariableUpdater;

public class ModelUpdater {
    private final ModelVariableUpdater[] modelVariableUpdaters;

    public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaters) {
        this.modelVariableUpdaters = modelVariableUpdaters;
    }

    public void update() {
        for (ModelVariableUpdater modelvariableupdater : this.modelVariableUpdaters) {
            modelvariableupdater.update();
        }
    }

    public boolean initialize(IModelResolver mr) {
        for (ModelVariableUpdater modelvariableupdater : this.modelVariableUpdaters) {
            if (modelvariableupdater.initialize(mr)) continue;
            return false;
        }
        return true;
    }
}

