package non.asset.module.impl.movement;

import org.apache.commons.lang3.StringUtils;

import non.asset.event.bus.Handler;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

import java.awt.*;
public class FastLadder extends Module {


    private NumberValue<Float> speed = new NumberValue<>("Climb Speed", 0.3f, 0.1f, 6.0f, 0.05f);
	
    public FastLadder() {
        super("FastLadder", Category.MOVEMENT);
        setDescription("Climb Ladders Soo Faster!");
    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
        if(event.isPre()) {
        	if(mc.thePlayer.moveForward != 0) {
        		if(mc.thePlayer.isOnLadder()) {
        			if(!mc.thePlayer.isOnLiquid()) {
        				if(!(mc.thePlayer.isDead)){
        					mc.thePlayer.motionY = speed.getValue();
        				}
        			}
        		}
        	}
        }
    }
}
