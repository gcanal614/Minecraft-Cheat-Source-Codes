package me.module.impl.player;



import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;




public class FastPlace extends Module{
	
	public FastPlace(){
		super("FastPlace", 0, Category.COMBAT);
	}
	
	 @Handler
	 public void onUpdate(EventUpdate event) {
			mc.rightClickDelayTimer = 0;
	   
	}
	
	public void onDisable(){
		mc.rightClickDelayTimer = 6;
		super.onDisable();
	   }
	}
	
