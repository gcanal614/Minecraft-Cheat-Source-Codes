package me.module.impl.render;

import me.Hime;
import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class HealthNum extends Module{
	
	public HealthNum() {
		super("HealthCounter", 0, Category.RENDER);
	}
	
	@Handler
	public void onRender2d(EventRenderHUD event) {
		 ScaledResolution sr = new ScaledResolution(mc);
		 //Gui.drawRect(Hime.instance.cfrs.getWidth("§" +this.getHealthColorSuffix((int) mc.thePlayer.getHealth()) + (int)(mc.thePlayer.getHealth())), sr.getScaledHeight() / 2 - 50, Hime.instance.cfrs.getWidth("§" +this.getHealthColorSuffix((int) mc.thePlayer.getHealth()) + (int)(mc.thePlayer.getHealth())), 10, new Color(50,50,50, 150).getRGB());
		 Hime.instance.cfrs.drawStringWithShadow("§" +this.getHealthColorSuffix((int) mc.thePlayer.getHealth()) + (int)(mc.thePlayer.getHealth()), sr.getScaledWidth() / 2 - 4, sr.getScaledHeight() / 2 + 15, -1);
	}
	
	protected String getHealthColorSuffix(int hp) {
		if (hp > 15)
			return "a";
		if (hp > 10)
			return "e";
		if (hp > 5)
			return "6";
		if (hp < 2)
			return "4";
		return "c";
	}
	
}
