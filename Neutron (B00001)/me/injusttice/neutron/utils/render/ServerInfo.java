package me.injusttice.neutron.utils.render;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.visual.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.MathHelper;

public class ServerInfo {

	private static long prevTime, lastServerPacket;
	private static float[] ticks = new float[20];
	private static int currentTick;

	@EventTarget
	public void onRender(float partialTicks) {
		if (lastServerPacket != -1 && Math.abs(System.currentTimeMillis() - lastServerPacket) > 3500 && !Minecraft.getMinecraft().isSingleplayer() || ModuleManager.getModule(HUD.class).isEnabled()) {
			long seconds = (Math.abs(System.currentTimeMillis() - lastServerPacket) / 1000);
			NeutronMain.addDebugMessage("Server not responding " + seconds + "s");
		}
	}

	public static double getTps() {
		int tickCount = 0;
		float tickRate = 0.0f;

		for (int i = 0; i < ticks.length; i++) {
			float tick = ticks[i];

			if (tick > 0.0f) {
				tickRate += tick;
				tickCount++;
			}
		}

		return MathHelper.clamp_double((tickRate / tickCount), 0.0f, 20.0f);
	}

	@EventTarget
	public void onReceive(EventReceivePacket e) {
		if (e.getPacket() instanceof S03PacketTimeUpdate) {
			if (prevTime != -1) {
				ticks[currentTick % ticks.length] = MathHelper.clamp_float((20.0f / ((float) (System.currentTimeMillis() - prevTime) / 1000.0f)), 0.0f, 20.0f);
				currentTick++;
			}

			prevTime = System.currentTimeMillis();
		}
	}

	@EventTarget
	public void onSend(EventSendPacket e) {
		lastServerPacket = System.currentTimeMillis();
	}
}