package wtf.astronicy.IMPL.module.impl.movement;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.packet.SendPacketEvent;
import wtf.astronicy.API.events.player.BoundingBoxEvent;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.exploit.PacketUtils;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.utils.Logger;
import wtf.astronicy.IMPL.utils.MovementUtils;
import wtf.astronicy.IMPL.utils.PlayerUtils;
import wtf.astronicy.IMPL.utils.TimerUtility;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

@Bind("F")
@ModName("Flight")
@Category(ModuleCategory.MOVEMENT)
@Aliases({"flight", "fly"})
public class Fly extends Module {

	// Verus Things
	private int ticks = 0;
	public TimerUtility timer = new TimerUtility();
	private boolean damaged = false;
	BoolOption damage = new BoolOption("Damage",false) ;
	private int undamagedTicks = 0;
	private int packetsModifiedForDamage = 0;
	private final List<C03PacketPlayer> packetsQueue = new ArrayList<C03PacketPlayer>();
	private boolean registered = false;

	public EnumOption mode;

	public Fly(){
		mode = new EnumOption("Modes", Mode.VERUS);
		this.addOptions(new Option[]{mode,damage});
	}

	@Override
	public void onEnabled() {
		super.onEnabled();
		this.damaged = false;
		this.undamagedTicks = 0;
		this.packetsQueue.clear();
		this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
		this.packetsModifiedForDamage = 0;
		this.registered = false;
		switch ((Mode) mode.getValue()) {
			case VERUS:
				if(damage.getValue()) {
					PlayerUtils.damage();
				}else {
					damaged = true;
				}
				break;
			case COLLISION:
				//mc.thePlayer.capabilities.isFlying = true;
				break;
		}
	}

	@Override
	public void onDisabled() {
		switch ((Mode) mode.getValue()) {
			case VERUS:
				packetsQueue.clear();
				MovementUtils.setSpeed(0.1f);
				mc.thePlayer.jump();
				//PlayerUtils.damage();
				mc.timer.timerSpeed = 1.0f;
				break;
		}
		packetsQueue.clear();

	}

	@Listener(MotionUpdateEvent.class)
	public void onMotion(MotionUpdateEvent event) {
		switch ((Mode) mode.getValue()) {
			case VERUS:
				if(timer.elapsed(300)){
					packetsQueue.add(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY - 3.1	,mc.thePlayer.posZ,false));
					PacketUtils.sendPacketNoEvent((new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY -3.1,mc.thePlayer.posZ,false)));
					PacketUtils.sendPacket((new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY - 3.1,mc.thePlayer.posZ,false)));
					Logger.log("C04 POS - 3");
					timer.reset();
				}



				this.mc.thePlayer.setSprinting(true);
				if (this.packetsModifiedForDamage < 1) {
					++this.packetsModifiedForDamage;
					return;
				}
				if (this.ticks > 0 || !this.damaged) {
					this.mc.thePlayer.fallDistance = 0.0f;
					if (!(this.mc.thePlayer.hurtTime <= 0 || this.damaged)) {

						mc.thePlayer.jump();
						this.ticks = (int) 1000;
						mc.timer.timerSpeed = 0.25f;
						this.damaged = true;
					} else {
						mc.timer.timerSpeed = 0.25f;
					}
					//--this.ticks;
					if (this.ticks <= 0) {
						if (this.damaged) {
							return;
						}
						if (this.undamagedTicks > 40) {
							this.damaged = true;
						}
						++this.undamagedTicks;
					} else {
						MovementUtils.setSpeed(4f);
						this.damaged = true;
					}
				}


				break;

			case COLLISION:
				MovementUtils.setSpeed(0.4f);

				break;
		}
	}


	@Listener(SendPacketEvent.class)
	public void onSendPacket(SendPacketEvent event){
		switch ((Mode) mode.getValue()) {
			case VERUS:
				if (event.getPacket() instanceof C03PacketPlayer) {
					((C03PacketPlayer)event.getPacket()).onGround = true;
				}
				break;
		}
	}

	@Listener(BoundingBoxEvent.class)
	public void onBB(BoundingBoxEvent event) {
		switch ((Mode) mode.getValue()) {
			case VERUS:
				event.setBoundingBox(AxisAlignedBB.fromBounds(event.getBlockPos().getX(), mc.thePlayer.posY, event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, mc.thePlayer.posY, event.getBlockPos().getZ() + 1));
				break;
			case COLLISION:
					event.setBoundingBox(AxisAlignedBB.fromBounds(event.getBlockPos().getX(), mc.thePlayer.posY, event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, mc.thePlayer.posY, event.getBlockPos().getZ() + 1));
				break;
		}
	}

	public static enum Mode {
		COLLISION,
		VERUS
	}

	public static Fly getInstance(){
		return (Fly) Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(Fly.class);
	}

}