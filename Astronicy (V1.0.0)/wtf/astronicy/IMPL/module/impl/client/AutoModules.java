package wtf.astronicy.IMPL.module.impl.client;

import wtf.astronicy.Astronicy;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.game.WorldLoadEvent;
import wtf.astronicy.API.events.packet.ReceivePacketEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.exploit.ResetVL;
import wtf.astronicy.IMPL.module.impl.movement.SpeedMod;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModName("ModuleManager")
@Category(ModuleCategory.CLIENT)
public class AutoModules extends Module {
	
	public final BoolOption autoResetVL;

	public AutoModules() {
		autoResetVL = new BoolOption("AutoResetVL", true);
		this.addOptions(new Option[]{autoResetVL});
	}
	
	@Override
	public void onEnabled() {
		super.onEnabled();
	}
	
	@Listener(WorldLoadEvent.class)
	public void onWorldLoad(WorldLoadEvent e) {
	}
	
	@Listener(ReceivePacketEvent.class)
	public void onReceivePacket(ReceivePacketEvent e) {
		if(
			!Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(SpeedMod.class).isEnabled()
		) return;
		if(e.getPacket() instanceof S08PacketPlayerPosLook) {
			if(autoResetVL.getValue()) {
				if(Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(SpeedMod.class).isEnabled()) {
					Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(SpeedMod.class).toggle();
				}
				if(!Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(ResetVL.class).isEnabled()) {
					Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(ResetVL.class).toggle();
				}
			}
		}
	}
}
