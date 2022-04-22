package non.asset.module.impl.ghost;

import org.lwjgl.input.Mouse;

import net.minecraft.item.ItemSword;
import non.asset.event.bus.Handler;
import non.asset.event.impl.input.ClickMouseEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.NumberValue;

public class TapBlock extends Module {


    private NumberValue<Long> delay = new NumberValue<Long>("Delay", 500L, 10L, 1000L, 1L);
	
    private TimerUtil timer = new TimerUtil();
    
    public TapBlock() {	
        super("BlockHit", Category.GHOST);
        setRenderLabel("BlockHit");
        setDescription("Auto Block Hit");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	
    	if(!(timer.reach(delay.getValue())))
    		return;
    	
		if(mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
	    	if(mc.thePlayer.swingProgress > 0 && mc.thePlayer.swingProgress < 2){
	    		mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
	    	}else {
	    		if(!Mouse.isButtonDown(1)) {
	    			mc.thePlayer.stopUsingItem();
	    		}
	    	
	    	}
		}
    	
    }

    @Handler
    public void onUpdate(ClickMouseEvent event) {
    	
    }
}