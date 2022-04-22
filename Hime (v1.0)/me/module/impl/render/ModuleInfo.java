package me.module.impl.render;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ModuleInfo extends Module {

	public int currentTab;
	public boolean expanded;
	public Setting add;

	public ModuleInfo() {
		super("ModuleIndicator", Keyboard.KEY_NONE, Category.RENDER);
		Hime.instance.settingsManager.rSetting(add = new Setting("Y Add Rect", this, 0, 0, 180, true));
		
	}
	  @Handler
	  public void onRender(EventRenderHUD event) {
		    ScaledResolution sr = new ScaledResolution(mc);
			 
			//Gui.drawRect(5, 30.5, 70, 30 + Category.values().length * 16 + 1.5, 0x90000000);  // 15 difference start at 5 difference
			Gui.drawRect(sr.getScaledWidth() / 20 - 100, sr.getScaledHeight() / 5+ this.add.getValInt(), sr.getScaledWidth() / 8 - 20, sr.getScaledHeight() / 2.5+ this.add.getValInt(), 0x90000000);
			Hime.instance.rfrs.drawString("Flight: " + (Hime.instance.moduleManager.getModule("Flight").isToggled() ? "§aOn" : "§cOff"), (float)sr.getScaledWidth() / 16 - 40, (float) sr.getScaledHeight() / 5 + 5 + this.add.getValInt(), -1);
			Hime.instance.rfrs.drawString("Killaura: " + (Hime.instance.moduleManager.getModule("Killaura").isToggled() ? "§aOn" : "§cOff"), (float)sr.getScaledWidth() / 16 - 40, (float) sr.getScaledHeight() / 5 + 20+ this.add.getValInt(), -1);
			Hime.instance.rfrs.drawString("Scaffold: " + (Hime.instance.moduleManager.getModule("Scaffold").isToggled() ? "§aOn" : "§cOff"), (float)sr.getScaledWidth() / 16 - 40, (float) sr.getScaledHeight() / 5 + 35+ this.add.getValInt(), -1);
			Hime.instance.rfrs.drawString("Criticals: " + (Hime.instance.moduleManager.getModule("Criticals").isToggled() ? "§aOn" : "§cOff"), (float)sr.getScaledWidth() / 16 - 40, (float) sr.getScaledHeight() / 5 + 50+ this.add.getValInt(), -1);
			Hime.instance.rfrs.drawString("Speed: " + (Hime.instance.moduleManager.getModule("Speed").isToggled() ? "§aOn" : "§cOff"), (float)sr.getScaledWidth() / 16 - 40, (float) sr.getScaledHeight() / 5 + 65+ this.add.getValInt(), -1);
	 }
	}
