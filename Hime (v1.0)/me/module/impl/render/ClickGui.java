package me.module.impl.render;

import java.awt.Frame;
import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.module.Module;
import me.settings.Setting;
import me.ui.clickgui.CSGOGui;


public class ClickGui extends Module {
	public static ArrayList<Frame> frames;
	
	public Setting style;
	
	public Setting sound;
	
	public Setting type;
	
	  public ClickGui() {
	    super("ClickGui", Keyboard.KEY_RSHIFT, Category.RENDER);
	    Hime.instance.settingsManager.rSetting(new Setting("Hue", this, 37, 0, 255));
	    Hime.instance.settingsManager.rSetting(new Setting("Brightness", this, 1, -20, 5, 0));
	    Hime.instance.settingsManager.rSetting(new Setting("Saturation", this, 1, -20, 5, "eee"));
        Hime.instance.settingsManager.rSetting(sound = new Setting("Sound", this, false));
        Hime.instance.settingsManager.rSetting(new Setting("Open Animation", this, false));
	  }
	  
      @Override
	  public void onEnable() {
	     super.onEnable();
	       mc.displayGuiScreen(Hime.clickGui);
	       toggle();
	   }
   }

	
	  

	
	  
	  
  
  