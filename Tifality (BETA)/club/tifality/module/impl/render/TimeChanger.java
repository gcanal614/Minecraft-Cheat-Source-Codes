/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.Representation;

@ModuleInfo(label="Weather", category=ModuleCategory.RENDER)
public final class TimeChanger
extends Module {
    public static final DoubleProperty time = new DoubleProperty("Time", 13000.0, 0.0, 24000.0, 500.0, Representation.MILLISECONDS);

    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        TimeChanger.mc.theWorld.setWorldTime(((Double)time.get()).intValue());
    }

    public TimeChanger() {
        this.setHidden(true);
    }
}

