package me.module.impl.combat;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module {
	
	
public Regen() {
		super("Regen", Keyboard.KEY_NONE, Category.COMBAT);
	}
public Setting mode;
public Setting h;
private boolean doHeal;
private double tries;

private float startHealth;

public TimeUtil time = new TimeUtil();

public Setting v;
@Override
public void setup() {
	Hime.instance.settingsManager.rSetting(this.h = new Setting("RegeneratePackets", this, 25, 1, 100, true));
	 Hime.instance.settingsManager.rSetting(this.v = new Setting("RegenerateRetry delay", this, 4000, 10, 8000, true));
    ArrayList<String> options = new ArrayList<String>();
    options.add("Packet");
    options.add("Timer");
    Hime.instance.settingsManager.rSetting(mode = new Setting("Regen Mode", this, "Packet", options));
}


@Override
public void onEnable() {
	if (mc.thePlayer == null)
		return;
	doHeal = true;
	startHealth = mc.thePlayer.getHealth();
	super.onEnable();
}



@Override
public void onToggle() {
	mc.timer.timerSpeed = 1F;
	tries = 0;
	super.onToggle();
}

@Handler
public void onUpdate(EventUpdate event) {
	  this.setSuffix(mode.getValString());
		if (mode.getValString().equalsIgnoreCase("Packet")) {
			if (doHeal) {
				if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < 20 && mc.thePlayer.getFoodStats().getFoodLevel() >= 18) {
					tries += 0;
					if (tries > 20 && mc.thePlayer.getHealth() <= startHealth) {
						doHeal = false;
					}
					for (int i = 0; i < (int)h.getValDouble(); i++) {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					}
				} else {
					tries = 0;
					time.reset();
				}
			} else {
				tries = 0;
				startHealth = mc.thePlayer.getHealth();
				if (time.hasTimePassed(4000)) {
					doHeal = true;
				}
			}
		} else {
			if (!mc.thePlayer.isMoving() && mc.thePlayer.getHealth() < 20 && mc.gameSettings.keyBindSneak.pressed) {
				mc.timer.timerSpeed = 50F;
			} else {
				mc.timer.timerSpeed = 1F;
			}
		}
	
   }
  
}
