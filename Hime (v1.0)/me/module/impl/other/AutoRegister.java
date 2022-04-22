package me.module.impl.other;


import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import me.util.TimeUtil;
import net.minecraft.client.gui.inventory.GuiChest;

public class AutoRegister extends Module{

	TimeUtil time = new TimeUtil();
	private boolean registered = false;  
	
	public AutoRegister() {
		super("AutoRegister", 0, Category.MISC);
	
	}
	
	public void onDisable() {
		super.onDisable();
		this.registered = false;
	}
	
	   @Handler
	   public void onUpdate(EventUpdate event) {
			if((mc.currentScreen instanceof GuiChest))
			   return;
			
			if(!mc.isSingleplayer()) {
			 if (mc.getCurrentServerData().serverIP.contains("redesky")) {
				if(!this.registered) {
					mc.thePlayer.sendChatMessage("/register password1 password1");
					NotificationManager.show(new Notification(NotificationType.INFO, "AutoRegister Alert", "Sucefull Register!", 2));
					this.registered = true;
				}
		  }else {
			  if(this.registered) {
					mc.thePlayer.sendChatMessage("/login password1");
				    NotificationManager.show(new Notification(NotificationType.INFO, "AutoRegister Alert", "Sucefull Login!", 2));
					this.registered = true;
				}  
		  }
	     }
	   }
}