package me.module.impl.world;

import java.util.ArrayList;

import me.Hime;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.block.Block;

public class Opacity extends Module {

	public Setting e;
	public Setting a;
	public Setting sports;
	private float oldGamma;
	public ArrayList<Block> blocks = new ArrayList<Block>();

	public Opacity() {
		super("Opacity", 0, Category.WORLD);
		Hime.instance.settingsManager.rSetting(e = new Setting("XRay", this, true));
		Hime.instance.settingsManager.rSetting(a = new Setting("Opacity", this, true));
		Hime.instance.settingsManager.rSetting(sports = new Setting("Opacity Transparency", this, 130, 0, 255, false));
	}
	
	public void onEnable(){
     	super.onEnable();
     	Hime.addClientChatMessage("As of now, Opacity module has been disabled due to block lag");
     	if(this.e.getValBoolean()) {
          this.oldGamma = mc.gameSettings.gammaSetting;
          mc.gameSettings.gammaSetting = 10f;
          mc.gameSettings.ambientOcclusion = 0;
     	}
     	mc.renderGlobal.loadRenderers();
    }
	
	public void onDisable(){
		super.onDisable();
		if(this.e.getValBoolean()) {
         mc.gameSettings.gammaSetting = this.oldGamma;	
         mc.gameSettings.ambientOcclusion = 1;
		 mc.renderGlobal.loadRenderers();
		}
	}
	
	public boolean isBlockCorrect(Block block) {
	  return this.blocks.contains(block);
	}
}
