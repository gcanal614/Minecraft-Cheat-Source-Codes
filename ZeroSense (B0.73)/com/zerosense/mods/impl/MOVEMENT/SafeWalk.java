package com.zerosense.mods.impl.MOVEMENT;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventSafeWalk;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("SafeWalk", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void onEvent(Event ev) {
        if (ev instanceof EventSafeWalk) {
            if (mc.thePlayer.onGround) {
                ev.setCancelled(true);

            }
        }
    }
}
