package me.module.impl.render;


import me.Hime;
import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambiance extends Module {
  public Setting ambiance;



  public Ambiance() {
    super("WorldTime ", 0, Category.WORLD);
    Hime.instance.settingsManager.rSetting(this.ambiance = new Setting("World Time", this, 0.0D, 0.0D, 24000.0D, true));
  }
  @Handler
  public void onMotionEvent(EventUpdate event) {
      this.mc.theWorld.setWorldTime((long)this.ambiance.getValDouble());
  }
  @Handler
  public void onRecieve(EventReceivePacket event) {
	  //g8 made wrong so bomt remade if anyone has src know g8 is ambiance 
	  
    if (event.getPacket() instanceof S03PacketTimeUpdate)
       event.cancel(); 
    
      this.mc.theWorld.setWorldTime((long)this.ambiance.getValDouble());
  }

}