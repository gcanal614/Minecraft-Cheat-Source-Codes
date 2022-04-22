package com.zerosense.mods.impl.MOVEMENT;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventUpdate;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Utils.MovementUtil;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

        public static ModeSetting mode = new ModeSetting("Mode", "Sprint", "Omni-Sprint");

        public Sprint() {

                super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
                this.addSettings(mode);
                this.toggled = true;
        }


        @Override
        public void onEvent(Event event) {
                if ( event instanceof EventUpdate && event.isPre() ) {


                        if ( mode.is("Sprint") ) {
                                mc.gameSettings.keyBindSprint.pressed = true;


                        }
                        if ( mode.is("Omni-Sprint") ) {
                                if ( !(!(this.mc.thePlayer.moveForward > 0.0f) || this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isSneaking() || this.mc.thePlayer.isCollidedHorizontally || this.mc.thePlayer.isDead) ) {
                                        this.mc.thePlayer.setSprinting(true);
                                }
                                if ( MovementUtil.isMoving() ) {
                                        this.mc.thePlayer.setSprinting(true);
                                        if ( !this.mc.thePlayer.isAirBorne ) {
                                                this.mc.thePlayer.setSpeed(0.2f);
                                        }
                                }
                        }
                }
        }
        public void onDisable(){
                mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
        }
}
