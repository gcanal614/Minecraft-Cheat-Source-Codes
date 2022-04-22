package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventTick;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module {
	
	public DoubleSet packets = new DoubleSet("Packets", 2.0D, 1.0D, 300.0D, 1.0D);

	public Regen() {
		super("Regen", 0, Category.EXPLOIT);
		addSettings(packets );
	}

	@EventTarget
	public void onUpdate(EventTick e) {
		setDisplayName(String.valueOf(packets.getValue()));
		for (int a = 0; a < packets.getValue(); a++) {
			if (mc.thePlayer.getHealth() != 20.0F)
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
		}
	}
}
