package stellar.skid.modules.move;

import stellar.skid.StellarWare;
import stellar.skid.events.EventTarget;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.events.events.MoveEvent;
import stellar.skid.events.events.PacketEvent;
import stellar.skid.events.events.PlayerUpdateEvent;
import stellar.skid.events.events.TickUpdateEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.*;
import stellar.skid.modules.exploits.Disabler;
import stellar.skid.utils.PlayerUtils;
import stellar.skid.utils.ServerUtils;
import stellar.skid.utils.Servers;
import stellar.skid.utils.notifications.NotificationType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Flight extends AbstractModule {
	
	private int updateTicks = 0;
    private int tick;
    private boolean wait;
    private int key;
    private short id;
    private List<Packet> listPosition = new CopyOnWriteArrayList();
    private List<Packet> listPing = new CopyOnWriteArrayList();

    @Property("fly-mode")
    private final StringProperty fly_mode = PropertyFactory.createString("Vanilla").acceptableValues("Dash", "Vanilla");
    @Property("speed")
    private final DoubleProperty speed = PropertyFactory.createDouble(2.5D).maximum(9.0D).minimum(1.0D);

    public Flight(@NonNull ModuleManager novoline) {
        super(novoline, EnumModuleType.MOVEMENT, "Flight", "Flight");
        Manager.put(new Setting("FLY_MODE", "Fly Mode", SettingType.COMBOBOX, this, fly_mode));
        Manager.put(new Setting("FLY_SPEED", "Speed", SettingType.SLIDER, this, speed, 0.1));
    }

    @Override
    public void onDisable() {
        if (tick > 0) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
            mc.player.motionY = -0.41999998688698;
            mc.timer.timerSpeed = 1.0F;
        }

        getModule(Disabler.class).getTimer().reset();
        wait = false;
        tick = 0;
    }


    @Override
    public void onEnable() {
        checkModule(Speed.class, Scaffold.class);
        setSuffix(fly_mode.get());
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
//        if (event.getState().equals(PacketEvent.State.INCOMING)) {
//            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
//                if (wait) {
//                    wait = false;
//                } else if (tick > 3) {
//                    checkModule(getClass());
//                }
//            }
//        }
    }

    @EventTarget
    public void onTick(TickUpdateEvent event) {
        if (!wait && mc.player.isMoving()) {
            tick++;
        }

        setSuffix(fly_mode.get());
    }

    private int getPacketsSize() {
        int jumps = mc.player.isPotionActive(Potion.jump) ? mc.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
        int hypixel = ServerUtils.serverIs(Servers.UHC) || ServerUtils.serverIs(Servers.SG) || ServerUtils.serverIs(Servers.MW) ? 1 : 0;
        double fallHeight = 3 + jumps + hypixel, amp = 0.125;

        return (int) (fallHeight / amp) * 2 + 2;
    }

    @EventTarget
    public void onPre(MotionUpdateEvent event) {
    	if(fly_mode.get().equalsIgnoreCase("Vanilla")) {
        if (event.getState() == MotionUpdateEvent.State.PRE) {
            if (mc.player.movementInput().jump()) {
                mc.player.motionY = 1.8;
            } else if (mc.player.movementInput().sneak()) {
                mc.player.motionY = -1.8;
            } else if (!mc.player.onGround) {
                mc.player.motionY = 0.0;
            }
        }
    	}
    }
    
    @EventTarget
    public void onUpdate(PlayerUpdateEvent e) {
    	if (fly_mode.get().equalsIgnoreCase("Dash")) {
			updateTicks++;
			mc.player.motionY = 0.0D;
			if(updateTicks % 35 == 0) {
				//mc.player.motionY = 5;
				double directionToRadians = Math.toRadians(PlayerUtils.getDirection() * 180.0 / Math.PI);
				double xMovement = -Math.sin(directionToRadians) * 6.3D;
				double zMovement = Math.cos(directionToRadians) * 6.3D;
				//sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX + 4, mc.player.posY - 1.75D, mc.player.posZ + zMovement, true));
				sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(4, -1.75D, zMovement, true));
				StellarWare.getInstance().notificationManager.pop("Teleporting!", NotificationType.WARNING);
				updateTicks = 0;
			}
    	}
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        
    }

    public DoubleProperty getSpeed() {
        return speed;
    }

    public int getTick() {
        return tick;
    }
}
