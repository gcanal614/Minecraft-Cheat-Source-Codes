package com.zerosense.mods.impl.RENDER;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Utils.EventPlayerRender;
import com.zerosense.Utils.Timer;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;

public class Rotations extends Module {
    public Timer timer = new Timer();

    public Rotations(){
        super("Rotations", Keyboard.KEY_NONE,Category.RENDER);
    }
    public void onDisable() {
    }

    public void onEvent(Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            mc.thePlayer.rotationYawHead = ((EventMotion)e).getYaw();
            mc.thePlayer.renderYawOffset = ((EventMotion)e).getYaw();
        }

        if (e instanceof EventPlayerRender && e.isPre()) {
            e.setPitch(0.0F);
        }

    }



    public void onEnable() {
    }
}