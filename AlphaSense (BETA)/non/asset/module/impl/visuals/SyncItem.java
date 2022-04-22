package non.asset.module.impl.visuals;

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;


public class SyncItem extends Module {
	
	public TimerUtil aa = new TimerUtil();
	
    public SyncItem() {
        super("SyncItem", Category.OTHER);
        setDescription("Sync Item");
    }
    

	@Handler
	public void onUpdate(UpdateEvent event) {
		if (mc.thePlayer == null)
			return;
		
		if(aa.reach(100)) {
			setHidden(false);
        	setRenderLabel("Sync Item");
        	setDescription("Syncs you item with the server view");
		}
	}
	@Handler
	public void onPacket(PacketEvent event) {
		if (getMc().thePlayer == null)
			return;

		if (getMc().theWorld == null)
			return;
		
		
		if(event.getPacket() instanceof C0APacketAnimation) {
			if(event.getPacket() instanceof C0APacketAnimation) {
				for (int slot = 0; slot < 9; slot++) {
					if(aa.reach(100)) {
						mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
						if(!mc.playerController.syncItem()) {
							mc.playerController.syncCurrentPlayItem();
						}
					}
				}
			}
		}else {
			
		}
	}
}
