package com.zerosense.mods.impl.MOVEMENT;

import com.zerosense.Events.Event;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;

public class Step extends Module {

    public Step(){
        super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    public void onEvent(Event e){
        if(mc.thePlayer.isCollidedHorizontally){
            mc.thePlayer.stepHeight = 2F;
        }
    }
}
