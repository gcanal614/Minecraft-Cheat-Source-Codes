package me.module.impl.player;



import me.Hime;
import me.module.Module;




public class Cape extends Module{
	
	public Cape(){
		super("Cape", 0, Category.PLAYER);
	}
	
	public void onEnable(){
		super.onEnable();
		Hime.instance.cape = true;
	   }
	
	public void onDisable(){
		Hime.instance.cape = false;
		super.onDisable();
	   }
	}
	
