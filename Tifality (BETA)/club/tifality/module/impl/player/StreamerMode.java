/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.RenderBossHealthEvent;
import club.tifality.manager.event.impl.render.RenderGuiTabPlayerEvent;
import club.tifality.manager.event.impl.render.RenderScoreboardEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;

@ModuleInfo(label="StreamerMode", category=ModuleCategory.PLAYER)
public final class StreamerMode
extends Module {
    public static Property<Boolean> skinSpoof = new Property<Boolean>("Skin spoof", false);
    public static Property<Boolean> hideBossbar = new Property<Boolean>("Hide Boss health", false);
    public static Property<Boolean> hideScoreboard = new Property<Boolean>("Hide scoreboard", false);
    public static Property<Boolean> hideTab = new Property<Boolean>("Hide tab", false);

    @Listener
    private void onRenderBossHealthEvent(RenderBossHealthEvent event) {
        if (hideBossbar.get().booleanValue()) {
            event.setCancelled();
        }
    }

    @Listener
    private void onRenderScoreboardEvent(RenderScoreboardEvent event) {
        if (hideScoreboard.get().booleanValue()) {
            event.setCancelled();
        }
    }

    @Listener
    private void onRenderGuiTabPlayerEvent(RenderGuiTabPlayerEvent event) {
        if (hideTab.get().booleanValue()) {
            event.setCancelled();
        }
    }
}

