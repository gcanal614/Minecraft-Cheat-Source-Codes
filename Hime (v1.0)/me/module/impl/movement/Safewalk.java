package me.module.impl.movement;

import org.lwjgl.input.Keyboard;

import me.event.impl.EventSafewalk;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;

public class Safewalk extends Module {
  
  public Safewalk() {
    super("Safewalk", 0, Category.MOVEMENT);
  }
  
  @Handler
 	public void onSafe(EventSafewalk event) {
 		if (mc.thePlayer.onGround) {
 			event.setSafe(true);
 		}
 	}
}