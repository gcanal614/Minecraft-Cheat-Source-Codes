package me.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.RenderUtil;

public class Crosshair extends Module{
	
	  public Setting x;
	  
	  public Setting y;
	  
	  public Setting mode;

	public Crosshair() {
		super("Crosshair", 0, Category.RENDER);
		ArrayList<String> options = new ArrayList<String>();
        options.add("Normal");
        options.add("Dot");
        options.add("Square");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Crosshair Mode", this, "Normal", options));
	}
	
	@Handler
	public void onRender2D(EventRenderHUD event) {
	        float wMiddle = event.getWidth() / 2;
	        float hMiddle = event.getHeight() / 2 + 3;
	        float hMiddle2 = event.getHeight() / 2;
	 if(this.mode.getValString().equalsIgnoreCase("Normal")) {
		// Left
        RenderUtil.drawBorderedRect(wMiddle - 7, hMiddle - 3, wMiddle - 2, hMiddle - 2, 0.5f, Color.WHITE.getRGB(), Color.BLACK.getRGB(), false);
        // Right
        RenderUtil.drawBorderedRect(wMiddle + 3, hMiddle - 3, wMiddle + 8, hMiddle - 2, 0.5f, Color.WHITE.getRGB(), Color.BLACK.getRGB(), false);
        // Top
        RenderUtil.drawBorderedRect(wMiddle, hMiddle - 10, wMiddle + 1, hMiddle - 5, 0.5f, Color.WHITE.getRGB(), Color.BLACK.getRGB(), false);
        // Bottom
        RenderUtil.drawBorderedRect(wMiddle, hMiddle, wMiddle + 1, hMiddle + 5, 0.5f, Color.WHITE.getRGB(), Color.BLACK.getRGB(), false);
	 }else if(this.mode.getValString().equalsIgnoreCase("Dot")) {
	        RenderUtil.drawBorderedRect(wMiddle, hMiddle2, wMiddle ,
	                hMiddle, 1f, Color.WHITE.getRGB(), Color.BLACK.getRGB(), false);
	 }
	}
	
}
	

	

