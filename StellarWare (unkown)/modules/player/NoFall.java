package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.events.events.PacketEvent;
import stellar.skid.events.events.TickUpdateEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.modules.EnumModuleType.EXPLOITS;
import static stellar.skid.modules.EnumModuleType.PLAYER;

public final class NoFall extends AbstractModule {

    @Property("no-void")
    private BooleanProperty no_void = PropertyFactory.createBoolean(false);

    /* constructors @on */
    public NoFall(@NonNull ModuleManager novoline) {
        super(novoline, "NoFall", "No Fall", PLAYER, "Prevents you from taking fall damage");
        Manager.put(new Setting("NF_NO_VOID", "Void Check", SettingType.CHECKBOX, this, no_void));
    }

    private double getLastTickYDistance() {
        return Math.hypot(mc.player.posY - mc.player.prevPosY, mc.player.posY - mc.player.prevPosY);
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getState().equals(PacketEvent.State.OUTGOING)) {
                if (mc.player.posY > 0 && mc.player.fallDistance >= 5 && mc.player.lastTickPosY - mc.player.posY > 0 && mc.player.motionY != 0) {
                    if (no_void.get() && !mc.player.isBlockUnder() || mc.player.fallDistance > 5 || !mc.player.isBlockUnder() && mc.player.fallDistance > 5) {
                        return;
                    }

                    if (event.getPacket() instanceof C02PacketUseEntity) {
                        C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();

                        if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                            event.setCancelled(true);
                        }
                    }

                    if (event.getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();

                        if (packet.isMoving() && packet.isRotating()) {
                            sendPacketNoEvent(new C04PacketPlayerPosition(packet.getX(), packet.getY(), packet.getZ(), packet.isOnGround()));
                            event.setCancelled(true);
                        }
                    }
                }
        }
    }

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {
        if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
            if (mc.player.posY > 0 && mc.player.lastTickPosY - mc.player.posY > 0 && mc.player.motionY != 0 && mc.player.fallDistance >= 2.5) {
                if (no_void.get() && !mc.player.isBlockUnder() || mc.player.fallDistance > 5 || !mc.player.isBlockUnder() && mc.player.fallDistance > 5) {
                    return;
                }

                if (mc.player.fallDistance > 2 || mc.player.ticksExisted % 2 == 0) {
                    sendPacketNoEvent(new C03PacketPlayer(true));
                    mc.timer.timerSpeed = 1.0F;
                }
            }
        }
    }

    @EventTarget
    public void onTick(TickUpdateEvent event) {
        setSuffix("Watchdog");
    }

    @Override
    public void onEnable() {
        setSuffix("Watchdog");
    }
}
