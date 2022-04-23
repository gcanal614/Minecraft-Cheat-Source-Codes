/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.combat;

import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.property.impl.DoubleProperty;

@ModuleInfo(label="Reach", category=ModuleCategory.COMBAT)
public class Reach
extends Module {
    private final DoubleProperty reachProperty = new DoubleProperty("Amount", 3.5, 3.0, 6.0, 0.05);

    private static Reach getInstance() {
        return ModuleManager.getInstance(Reach.class);
    }

    public static boolean isReachEnabled() {
        return Reach.getInstance().isEnabled();
    }

    public static float getReachValue() {
        return ((Double)Reach.getInstance().reachProperty.getValue()).floatValue();
    }
}

