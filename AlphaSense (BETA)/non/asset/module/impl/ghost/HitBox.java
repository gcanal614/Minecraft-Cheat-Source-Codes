package non.asset.module.impl.ghost;

import net.minecraft.network.play.client.C0APacketAnimation;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.NumberValue;

public class HitBox extends Module {
	
	
	private TimerUtil timer = new TimerUtil();
	

    public static NumberValue<Float> hitboxvalue = new NumberValue<>("Value", 0f, 0F, 2.0F, 0.01F);
	
    public HitBox() {
        super("HitBoxes", Category.GHOST);
        setDescription("Increases the target damage box");
        setRenderLabel("HitBoxes");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	
    }
    
    @Handler
    public void onPacket(PacketEvent event) {
    	
    }
    
}