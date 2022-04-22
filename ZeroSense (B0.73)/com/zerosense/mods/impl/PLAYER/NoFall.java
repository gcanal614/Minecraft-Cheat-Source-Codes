package com.zerosense.mods.impl.PLAYER;

import com.zerosense.Events.Event;
import com.zerosense.Events.EventTarget;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventUpdate;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Utils.MovementUtil;
import com.zerosense.Utils.Timer;
import com.zerosense.mods.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;



public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_NONE, Category.PLAYER);
        this.addSettings(mode);

    }

    //
    Timer timer = new Timer();
    public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Verus", "DEV");

    @EventTarget
    public void onEvent(Event e) {
        if (mode.is("Vanilla")) {
            if (mc.thePlayer.fallDistance > 2F)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));

        }
        if(mode.is("Verus")){
            if(mc.thePlayer.fallDistance > 1) {
                if(timer.hasTimeElapsed(1000, true)) {
                    mc.thePlayer.motionY = 0;
                    MovementUtil.setOnGround(false);
                    mc.thePlayer.onGround = false;
                } else {
                    MovementUtil.setOnGround(true);
                    mc.thePlayer.onGround = true;
                }
            }
            if(mc.thePlayer.fallDistance > 1 && mc.thePlayer.onGround == true){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}





