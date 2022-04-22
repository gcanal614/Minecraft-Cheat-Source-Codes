package me.module.impl.render;

import me.Hime;
import me.event.impl.EventRenderScoreboard;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreBoard extends Module{
	
	  public Setting x;
	  
	  public Setting y;
	  
	  public Setting remove;

	public ScoreBoard() {
		super("ScoreBoard", 0, Category.RENDER);
		 Hime.instance.settingsManager.rSetting(this.x = new Setting("Scoreboard X", this, 0.0D, 0.0D, 1920.0D, true));
		 Hime.instance.settingsManager.rSetting(this.y = new Setting("Scoreboard Y", this, 0.0D, 0.0D, 1920.0D, true));
		 Hime.instance.settingsManager.rSetting(this.remove = new Setting("Remove ScoreBoard", this, false));
	}
	
	@Handler
	  public void onRenderScoreboard(EventRenderScoreboard event) {
	    if (event.isPost()) {
	      GlStateManager.translate(this.x.getValDouble() / 2.3, -this.y.getValDouble() / 6, 1.0D);
	    } else if(event.isPre()){
	      GlStateManager.translate(-this.x.getValDouble() / 2.3, this.y.getValDouble() / 6, 1.0D);
	    } 
	  }
	}
	

