package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.Event;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class HypixelFly extends Module {

    public HypixelFly() {
        super("HypixelFly", Category.MOVEMENT);
    }

    int ticks = 0;
    int state = 0;

    public void onEvent(Event event) {
        if (event instanceof EventMotion) {
            if (event.isPre()) {
                ticks ++;
                EventMotion eventMotion = (EventMotion) event;

                mc.timer.timerSpeed = 1.7F;

                if(state == 2 || ticks >= 11) {
                    mc.thePlayer.motionY = 0;
                }
                if (state != 0 ) {
                    return;
                }
                if(mc.thePlayer.ticksExisted / 10 == 0) {
                    mc.timer.timerSpeed = 1.15F;

                }
                if(mc.thePlayer.ticksExisted / 14 == 0) {
                    mc.timer.timerSpeed = 1.14F;

                }
                if(mc.thePlayer.ticksExisted / 18 == 0) {
                    mc.timer.timerSpeed = 1.06F;

                }
                if(ticks == 1) {
                    mc.thePlayer.motionY = 0.39547834756;

                }
                if (ticks == 12) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.39547834756, mc.thePlayer.posZ, true));
                }
                if (ticks == 13) {

                    mc.thePlayer.posY = -0.4057689;
                    //player.setMotionY(-0.4057689);
                }
                if (ticks != 14) {
                    return;
                }
                mc.thePlayer.motionY = -0.4057689;
                state = 1;
            }
        }
    }
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        ticks = 0;
        state = 0;
    }

}
