package me.module.impl.render;


import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;

public class NameProtect extends Module {
  public NameProtect() {
    super("NameProtect", 0, Category.RENDER);
    Hime.instance.settingsManager.rSetting(new Setting("Streamer Mode", this, false));
  }
  
 
  @Handler
  public void onUpdate(EventUpdate event) {
    if(Minecraft.getMinecraft().theWorld != null && mc.thePlayer != null) {
    	//this.setSuffix(Hime.instance.nameprotect);
    }
  }
}
