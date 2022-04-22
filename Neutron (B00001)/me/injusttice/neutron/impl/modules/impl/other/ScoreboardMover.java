package me.injusttice.neutron.impl.modules.impl.other;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventRenderScoreboard;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreboardMover extends Module {

    public DoubleSet x = new DoubleSet("PosX", 30.0D, 0.0D, 0.0D, 1.0D);
    public DoubleSet y = new DoubleSet("PosY", 30.0D, 0.0D, 0.0D, 1.0D);

    public ScoreboardMover() {
        super("Scoreboard", 0, Category.OTHER);
        addSettings(x, y);
    }

    @EventTarget
    public void onRenderScoreboard(EventRenderScoreboard event) {
        if(event.isPre()){
            GlStateManager.translate(-x.getValue(), y.getValue(), 1.0D);
        } else {
            GlStateManager.translate(x.getValue(), -y.getValue(), 1.0D);
        }
    }
}