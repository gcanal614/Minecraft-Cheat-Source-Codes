package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventStepConfirm;
import me.injusttice.neutron.api.events.impl.EventStepHeight;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;

public class    Step extends Module {

    public ModeSet mode = new ModeSet("Mode", "Vanilla", "Vanilla", "Motion");

    double stepX = 0.0;
    double stepY = 0.0;
    double stepZ = 0.0;
    int ticks = 0;

    public Step() {
        super("Step", 0, Category.MOVEMENT);
        addSettings(mode);
    }

    @EventTarget
    public void onStepHeight(EventStepHeight e) {
        switch (mode.getMode()) {
            case "Motion":
            case "Vanilla": {
                e.setStepHeight(1.0f);
                if (!mc.thePlayer.onGround || NeutronMain.instance.moduleManager.getMod("Speed").isToggled()) {
                    e.setStepHeight(0.625f);
                }
                if (!(e.getStepHeight() > 0.625f)) break;
                stepX = mc.thePlayer.posX;
                stepY = mc.thePlayer.posY;
                stepZ = mc.thePlayer.posZ;
            }
        }
    }

    @EventTarget
    public void onStepConfirm(EventStepConfirm e) {
        if (NeutronMain.instance.moduleManager.getMod("Speed").isToggled()) {
            return;
        }
        ticks = 0;
        if (mc.thePlayer.getEntityBoundingBox().minY - stepY > 0.5) {
            switch (mode.getMode()) {
                case "Motion": {
                    mc.thePlayer.isAirBorne = true;
                    mc.thePlayer.triggerAchievement(StatList.jumpStat);
                    PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.41999998688698, stepZ, false));
                    PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.7531999805212, stepZ, false));
                    break;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;
    }
}
 