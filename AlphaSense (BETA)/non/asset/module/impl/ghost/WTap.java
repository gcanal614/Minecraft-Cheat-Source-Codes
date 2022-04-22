package non.asset.module.impl.ghost;

import net.minecraft.network.play.client.C0APacketAnimation;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;

public class WTap extends Module {
	
	
	private TimerUtil timer = new TimerUtil();
	
    public WTap() {
        super("WTap", Category.GHOST);
        setDescription("Increases the target knockback");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	
    	
    }
    @Handler
    public void onPacket(PacketEvent event) {
    	if(mc.thePlayer == null || mc.theWorld == null)
    		return;
    	if(event.isSending() && event.getPacket() instanceof C0APacketAnimation) {
    		if(timer.reach(400)) {
	    		mc.thePlayer.motionX *= -0.08;
	    		mc.thePlayer.motionZ *= -0.08;
    		}
    	}
    }
    
}