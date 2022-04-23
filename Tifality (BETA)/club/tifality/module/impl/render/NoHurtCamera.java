/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.HurtShakeEvent;
import club.tifality.manager.event.impl.render.ViewClipEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.property.Property;

@ModuleInfo(label="ViewClip", category=ModuleCategory.RENDER)
public final class NoHurtCamera
extends Module {
    private final Property<Boolean> noHurtShakeProperty = new Property<Boolean>("No Hurt", true);
    private final Property<Boolean> viewClipProperty = new Property<Boolean>("View Clip", true);

    public static boolean isEnabledCached() {
        return ModuleManager.getInstance(NoHurtCamera.class).isEnabled();
    }

    @Listener
    public void onViewClipEvent(ViewClipEvent event) {
        if (this.viewClipProperty.getValue().booleanValue()) {
            event.setCancelled();
        }
    }

    @Listener
    public void onHurtShakeEvent(HurtShakeEvent event) {
        if (!this.noHurtShakeProperty.getValue().booleanValue()) {
            event.setCancelled();
        }
    }
}

